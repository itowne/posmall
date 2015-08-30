package com.newland.posmall.biz.schedule;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.ohuyo.rapid.schedule.ResultData;
import org.ohuyo.rapid.schedule.Task;
import org.ohuyo.rapid.schedule.TaskContext;
import org.ohuyo.rapid.schedule.TaskType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.newland.posmall.base.service.TcustStockService;
import com.newland.posmall.base.service.TordDetailPdtService;
import com.newland.posmall.base.service.TordDetailService;
import com.newland.posmall.base.service.TordService;
import com.newland.posmall.bean.basebusi.Tord;
import com.newland.posmall.bean.basebusi.TordDetail;
import com.newland.posmall.bean.basebusi.TordDetailPdt;
import com.newland.posmall.bean.dict.OrdDetailPdtType;
import com.newland.posmall.bean.dict.ValidStatus;
import com.newland.posmall.bean.dict.YesNoType;

@Service
public class CustPdtStockCollectionTask extends Task {
	
	@Autowired
	private TcustStockService stockService;
	
	@Autowired
	private TordService ordService;
	
	@Autowired
	private TordDetailPdtService ordDetailPdtService;
	
	@Autowired
	private TordDetailService ordDetailService;
	
	public CustPdtStockCollectionTask(){
		super("客户库存量归集定时任务", TaskType.BATCH);
	}

	@Override
	protected void runTask(Date lastDate, TaskContext ctx, ResultData data) throws Throwable {
		lastDate = DateUtils.truncate(lastDate, Calendar.DATE);
		Date today = DateUtils.truncate(new Date(), Calendar.DATE);
		StringBuffer sb = new StringBuffer();
		Map<Long, Integer> map = new HashMap<Long, Integer>();
		while((today.compareTo(lastDate)) > 0){
			List<TordDetailPdt> pdtPlanDays = this.queryOrdDetail(lastDate);
			if (CollectionUtils.isEmpty(pdtPlanDays)) {
				sb.append(lastDate.toString() + " 未查询到订单信息！\n");
				lastDate = DateUtils.addDays(lastDate,  1);
				continue;
			}
			for (TordDetailPdt planDay:pdtPlanDays){
				Tord ord = this.ordService.find(planDay.getIord());
				if (ord == null) {
					sb.append("订单不存在！订单号：[" + planDay.getIord() + "]");
				}
				this.stockService.produce(ord.getIcust(), planDay.getIpdt(), planDay.getNum(), planDay.getIord(),1L,"系统");
				
				//修改订单产品明细2
				planDay.setProduceStatus(YesNoType.YES);
				planDay.setProducedNum(planDay.getNum());
				this.ordDetailPdtService.update(planDay);
				
				//修改订单明细
				TordDetail td = new TordDetail();
				td.setIord(planDay.getIord());
				td.setIpdt(planDay.getIpdt());
				td.setOrdDetailStatus(ValidStatus.VALID);
				td.setDelFlag(false);
				List<TordDetail> tordDetailList = this.ordDetailService.findSelect(td);
				TordDetail tordDetail = tordDetailList.get(0);
				tordDetail.setProducedNum(tordDetail.getProducedNum() + planDay.getNum());
				this.ordDetailService.update(tordDetail);
				
			}
			lastDate = DateUtils.addDays(lastDate,  1);
		}
		data.SUCCESS(sb.toString());

	}
	
	private List<TordDetailPdt> queryOrdDetail(Date today){
		TordDetailPdt ordDetailPdt = new TordDetailPdt();
		ordDetailPdt.setOrdDetailPdtType(OrdDetailPdtType.DAILY_OUTPUT);
		ordDetailPdt.setOrdDetailPdtStatus(ValidStatus.VALID);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String date = sdf.format(today);
		Integer year = Integer.valueOf(date.substring(0, 4));
		Integer month = Integer.valueOf(date.substring(4, 6));
		Integer day = Integer.valueOf(date.substring(6, 8));
		ordDetailPdt.setYear(year);
		ordDetailPdt.setMonth(month);
		ordDetailPdt.setDay(day);
		return this.ordDetailPdtService.findSelect(ordDetailPdt);
	}

}
