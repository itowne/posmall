package com.newland.posmall.biz.schedule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.ohuyo.rapid.base.entity.TsysParam;
import org.ohuyo.rapid.base.service.TsysParamService;
import org.ohuyo.rapid.schedule.ResultData;
import org.ohuyo.rapid.schedule.Task;
import org.ohuyo.rapid.schedule.TaskContext;
import org.ohuyo.rapid.schedule.TaskType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.newland.posmall.base.exception.BizErrCode;
import com.newland.posmall.base.exception.BizException;
import com.newland.posmall.base.service.TpdtPlanDayQuotaService;
import com.newland.posmall.base.service.TpdtService;
import com.newland.posmall.base.service.TpdtStockService;
import com.newland.posmall.bean.basebusi.Tpdt;
import com.newland.posmall.bean.basebusi.TpdtPlanDayQuota;
import com.newland.posmall.bean.basebusi.TpdtStock;
import com.newland.posmall.bean.dict.OrdDetailPdtType;
import com.newland.posmall.bean.dict.YesNoType;
@Service
public class PdtStockCollectionTask extends Task {
	
	@Autowired
	private TpdtStockService pdtStockService;
	
	@Autowired
	private TpdtPlanDayQuotaService planDayQuotaService;
	
	@Autowired
	private TpdtService pdtService;
	
	@Autowired
	private TsysParamService tsysParamService;
	
	public PdtStockCollectionTask(){
		super("产品库存归集定时任务", TaskType.REPEAT);
	}
	

	@Override
	protected void runTask(Date lastDate, TaskContext ctx, ResultData data) throws Throwable {
		StringBuffer sb = new StringBuffer();
		Tpdt cond = new Tpdt();
		cond.setDelFlag(false);
		List<Tpdt> pdts = this.pdtService.findBySelect(cond);
		if (CollectionUtils.isEmpty(pdts)) data.SUCCESS("无可用产品");
		for (Tpdt pdt: pdts){
			List<CollectionStock> stocks = getCollectonStock(pdt);
			if (CollectionUtils.isEmpty(stocks)) continue;
			for (CollectionStock stock: stocks){
				TpdtPlanDayQuota quota = findTomorrowQuota(stock, stocks);
				quota.setNum(quota.getNum() + stock.getNum());
				quota.setRemainQuota(quota.getNum() - quota.getUsedQuota());
				this.planDayQuotaService.update(quota);
				this.writeStock(quota);
				for (TpdtPlanDayQuota day: stock.getQuotas()){
					day.setCollectionFlag(YesNoType.YES);
					this.planDayQuotaService.update(day);
				}
			}
		}

		data.SUCCESS(sb.toString());
	}
	
	private TpdtPlanDayQuota findTomorrowQuota(CollectionStock stock,List<CollectionStock> stocks) {
		Date curr = createDate(stock.year, stock.month, stock.day);
		curr = DateUtils.addDays(curr, 1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String date = sdf.format(curr);
		Integer year = Integer.valueOf(date.substring(0, 4));
		Integer month = Integer.valueOf(date.substring(4, 6));
		Integer day = Integer.valueOf(date.substring(6, 8));
		TpdtPlanDayQuota cond = new TpdtPlanDayQuota();
		cond.setYear(year);
		cond.setMonth(month);
		cond.setDay(day);
		cond.setIpdt(stock.ipdt);
		cond.setOrdDetailPdtType(OrdDetailPdtType.STOCK);
		List<TpdtPlanDayQuota> quotas = this.planDayQuotaService.findBySelect(cond);
		CollectionStock coll = new CollectionStock(stock.ipdt, year, month, day);
		int idx = stocks.indexOf(coll);
		if (idx >= 0) {
			coll = stocks.get(idx);
		}else{
			coll = null;
		}
		if (CollectionUtils.isEmpty(quotas)) {
			TpdtPlanDayQuota quota = new TpdtPlanDayQuota();
			quota.setCollectionFlag(YesNoType.NO);
			quota.setDay(day);
			quota.setYear(year);
			quota.setIpdt(stock.ipdt);
			quota.setMonth(month);
			quota.setNum(0);
			quota.setOrdDetailPdtType(OrdDetailPdtType.STOCK);
			quota.setRemainQuota(0);
			quota.setUsedQuota(0);
			quota.setWeek(0);
			quota.setWeekday(0);
			this.planDayQuotaService.save(quota);
			if (coll != null) coll.addQuota(quota);
			return quota;
		}
		if (coll != null)
			coll.addQuota(quotas.get(0));
		return quotas.get(0);
	}
	
	private Date createDate(int year, int month, int day){
		Date date = new Date();
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.set(year, month-1, day);
		date = DateUtils.truncate(cal.getTime(), Calendar.DATE);
		
		return date;
	}


	private List<CollectionStock> getCollectonStock(Tpdt pdt) throws BizException {
		TpdtPlanDayQuota cond = new TpdtPlanDayQuota();
		cond.setIpdt(pdt.getIpdt());
		cond.setCollectionFlag(YesNoType.NO);
		List<TpdtPlanDayQuota> quotas = this.planDayQuotaService.findBySelect(cond);
		if (CollectionUtils.isEmpty(quotas)) return null;
		List<CollectionStock> stocks = new ArrayList<CollectionStock>();
		int spaceDay = 2;
		TsysParam param = this.tsysParamService.getTsysParam("OTHER_CONF", "SPACE_DAY_4_ORDER"); //查询点单间隔天数，即可以点几天之后的单
		if(param != null && StringUtils.isNotBlank(param.getValue())) {
			String spaceStr = param.getValue();
			try {
				spaceDay = Integer.valueOf(spaceStr);
			} catch (Exception e) {
				throw new BizException(BizErrCode.SYS_ERR, "系统参数【点单间隔天数】转化异常");
			}
		}
		Date today = DateUtils.truncate(DateUtils.addDays(new Date(), spaceDay), Calendar.DATE);
		for (TpdtPlanDayQuota quota: quotas){
			Date date = this.createDate(quota.getYear(), quota.getMonth(), quota.getDay());
			if (date.compareTo(today) >= 0){
				continue;
			}
			CollectionStock stock = new CollectionStock(quota.getIpdt(), quota.getYear(), quota.getMonth(), quota.getDay());
			int idx = stocks.indexOf(stock);
			if (idx >= 0){
				stock = stocks.get(idx);
				stock.addQuota(quota);
			}else{
				stock.addQuota(quota);
				stocks.add(stock);
			}
		}
		return stocks;
	}
	/**
	 * 写入库存记录
	 * @param ipdt
	 * @return
	 */
	private void writeStock(TpdtPlanDayQuota quota){
		TpdtStock pdtStock = this.pdtStockService.findById(quota.getIpdt());
		if (pdtStock == null) {
			pdtStock = new TpdtStock();
			pdtStock.setIpdt(quota.getIpdt());
			pdtStock.setGenTime(new Date());
			pdtStock.setUpdTime(new Date());
			pdtStock.setNum(quota.getNum());
			this.pdtStockService.add(pdtStock);
		}else{
			pdtStock.setNum(quota.getNum());
			this.pdtStockService.modify(pdtStock, null);
		}
	}

	public static class CollectionStock {
		
		private List<TpdtPlanDayQuota> quotas;
		
		private Long ipdt;
		
		private Integer year;
		
		private Integer month;
		
		private Integer day;
		
		public CollectionStock (Long ipdt, Integer year, Integer month, Integer day){
			this.ipdt = ipdt;
			this.year = year;
			this.month = month;
			this.day = day;
		}
		
		public void addQuota(TpdtPlanDayQuota quota){
			if (CollectionUtils.isEmpty(quotas)) quotas = new ArrayList<TpdtPlanDayQuota>();
			quotas.add(quota);
		}
		
		public List<TpdtPlanDayQuota> getQuotas(){
			return this.quotas;
		}
		
		public Integer getNum(){
			if (CollectionUtils.isEmpty(quotas)) return new Integer(0);
			Integer num = 0;
			for (TpdtPlanDayQuota quota: quotas){
				num += quota.getRemainQuota();
			}
			return num;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((day == null) ? 0 : day.hashCode());
			result = prime * result + ((ipdt == null) ? 0 : ipdt.hashCode());
			result = prime * result + ((month == null) ? 0 : month.hashCode());
			result = prime * result + ((year == null) ? 0 : year.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			CollectionStock other = (CollectionStock) obj;
			if (day == null) {
				if (other.day != null)
					return false;
			} else if (!day.equals(other.day))
				return false;
			if (ipdt == null) {
				if (other.ipdt != null)
					return false;
			} else if (!ipdt.equals(other.ipdt))
				return false;
			if (month == null) {
				if (other.month != null)
					return false;
			} else if (!month.equals(other.month))
				return false;
			if (year == null) {
				if (other.year != null)
					return false;
			} else if (!year.equals(other.year))
				return false;
			return true;
		}
	}

}
