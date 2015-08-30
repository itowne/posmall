package com.newland.posmall.biz.schedule;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.ohuyo.rapid.base.entity.TsysParam;
import org.ohuyo.rapid.base.entity.Tuser;
import org.ohuyo.rapid.base.service.TsysParamService;
import org.ohuyo.rapid.base.service.TuserService;
import org.ohuyo.rapid.schedule.ResultData;
import org.ohuyo.rapid.schedule.Task;
import org.ohuyo.rapid.schedule.TaskContext;
import org.ohuyo.rapid.schedule.TaskType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.newland.posmall.base.exception.BizException;
import com.newland.posmall.base.service.TagmtService;
import com.newland.posmall.base.service.TcustService;
import com.newland.posmall.base.service.TpayDetailService;
import com.newland.posmall.base.service.TpayNotifyService;
import com.newland.posmall.base.service.TpayService;
import com.newland.posmall.base.service.TwareHouseDetailService;
import com.newland.posmall.base.service.TwareHouseService;
import com.newland.posmall.bean.basebusi.Tagmt;
import com.newland.posmall.bean.basebusi.Tpay;
import com.newland.posmall.bean.basebusi.TpayDetail;
import com.newland.posmall.bean.basebusi.TpayNotify;
import com.newland.posmall.bean.basebusi.TwareHouse;
import com.newland.posmall.bean.basebusi.TwareHouseDetail;
import com.newland.posmall.bean.customer.TcustReg;
import com.newland.posmall.bean.dict.PayMethod;
import com.newland.posmall.bean.dict.PayStatus;
import com.newland.posmall.bean.dict.PayType;

@Service
public class CustStockFeeCountTask extends Task {
	
	@Autowired
	private TagmtService tagmtService;
	
	@Autowired
	private TuserService tuserService;
	
	@Autowired
	private TcustService tcustService;
	
	@Autowired
	private TpayService tpayService;
	
	@Autowired
	private TpayDetailService tpayDetailService;
	
	@Autowired
	private TpayNotifyService tpayNotifyService;
	
	@Autowired
	private TwareHouseDetailService twareHouseDetailService;
	
	@Autowired
	private TwareHouseService twareHouseService;
	
	@Autowired
	private TsysParamService tsysParamService;
	
	public CustStockFeeCountTask(){
		super("客户库存管理费用计费任务", TaskType.BATCH);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void runTask(Date lastDate, TaskContext ctx, ResultData data) throws Throwable {
		lastDate = DateUtils.truncate(lastDate, Calendar.DATE);
		Date today = DateUtils.truncate(new Date(), Calendar.DATE);
		StringBuffer sb = new StringBuffer();
		Map<String, Long> map = new TreeMap<String, Long>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		while((today.compareTo(lastDate)) > 0){

			List list = queryCustTagmtStockNum(sdf.format(lastDate));
			if (CollectionUtils.isEmpty(list)) {
				sb.append(sdf.format(lastDate) + " 未查询超过保存期限的协议信息！\n");
				lastDate = DateUtils.addDays(lastDate,  1);
				continue;
			}else{
				List<TwareHouse> twareHouseList = new ArrayList();
				map = new TreeMap<String, Long>();
				Object[] obj = null;
				int tempNum  = 0;
				TwareHouse twareHouse = null;
				if(list.size() == 1){
					Object[] obj2 = (Object[]) list.get(0);
					twareHouse = new TwareHouse();
					twareHouse.setIcust(Long.valueOf(String.valueOf(obj2[0])));
					twareHouse.setIagmt(Long.valueOf(String.valueOf(obj2[1])));
					twareHouse.setRemainQuota(Integer.valueOf(String.valueOf(obj2[7])));
					twareHouse.setGenTime(lastDate);
					twareHouseList.add(twareHouse);
				}else{
					for(int i = 0; i < list.size(); i++){
						
						if(obj == null){
						   obj = (Object[]) list.get(i);
						   tempNum = Integer.valueOf(String.valueOf(obj[7]));
						}else{
							//如果协议相等
						   if(String.valueOf(obj[1]).equals(String.valueOf(((Object[])list.get(i))[1]))){
							   tempNum += Integer.valueOf(String.valueOf(((Object[])list.get(i))[7]));
						   }else{
								twareHouse = new TwareHouse();
								twareHouse.setIcust(Long.valueOf(String.valueOf(obj[0])));
								twareHouse.setIagmt(Long.valueOf(String.valueOf(obj[1])));
								twareHouse.setRemainQuota(tempNum);
								twareHouseList.add(twareHouse);
							   //重新计算协议下的剩余数量
							   obj = (Object[]) list.get(i);
							   tempNum =  Integer.valueOf(String.valueOf(obj[7]));
						   }
						   if(i == list.size() -1){
							   twareHouse = new TwareHouse();
							   twareHouse.setIcust(Long.valueOf(String.valueOf(obj[0])));
							   twareHouse.setIagmt(Long.valueOf(String.valueOf(obj[1])));
							   twareHouse.setRemainQuota(tempNum);
							   twareHouseList.add(twareHouse);
						   }
						}
						
					}
				}
				
				// 生成 通知书
				for(int i = 0; i < twareHouseList.size(); i++){
					twareHouse = twareHouseList.get(i);
					twareHouse.setAmt(genWareHousePrice().multiply(new  BigDecimal(twareHouse.getRemainQuota())).setScale(2,RoundingMode.HALF_UP));
					twareHouse.setPayStatus(PayStatus.WAIT_AUDIT);
					this.twareHouseService.save(twareHouse);
					this.autoCreatTpayNotify(twareHouse.getIcust(), twareHouse.getIagmt(), twareHouse.getRemainQuota(), twareHouse,map);
				}
				
				//添加 仓管费详细
				this.addWareHouseDetailList(list, map, sdf.format(lastDate));
				lastDate = DateUtils.addDays(lastDate,  1);
			}
		}
		data.SUCCESS(sb.toString());
		
		
		
	}
	
	@SuppressWarnings("rawtypes")
	private List queryCustTagmtStockNum(String str){
		//未提货的超期天数
		TsysParam param = this.tsysParamService.getTsysParam("OTHER_CONF", "WTHCQTS"); 
//		return this.tagmtService.findCustStockFeeCount(Integer.valueOf(param.getValue()), str);
		return this.tagmtService.findCustStockFeeCount(3, str);
	}
	
	private BigDecimal genWareHousePrice(){
		//未提货的超期天数
		TsysParam param = this.tsysParamService.getTsysParam("OTHER_CONF", "CQWTHSFPZ"); 
		return new BigDecimal(param.getValue());
	}
	
	@SuppressWarnings("rawtypes")
	public void addWareHouseDetailList(List list, Map<String,Long> map, String manualDate) throws ParseException{
		if(!CollectionUtils.isEmpty(list)){
			Date date = null;
			if(!StringUtils.isEmpty(manualDate)){
				date = new SimpleDateFormat("yyyy-MM-dd").parse(manualDate);
			}
			for(Object objTemp : list){
				Object[] obj = (Object[]) objTemp;
				TwareHouseDetail twareHouseDetail = new TwareHouseDetail();
				twareHouseDetail.setIcust(Long.valueOf(obj[0].toString()));
				twareHouseDetail.setIagmt(Long.valueOf(obj[1].toString()));
				twareHouseDetail.setIord(Long.valueOf(obj[2].toString()));
				twareHouseDetail.setIordDetailPdt(Long.valueOf(obj[3].toString()));
				twareHouseDetail.setIpdt(Long.valueOf(obj[4].toString()));
				twareHouseDetail.setDateVarchar(obj[5].toString());
				twareHouseDetail.setUsedQuota(Integer.valueOf(obj[6].toString()));
				twareHouseDetail.setRemainQuota(Integer.valueOf(obj[7].toString()));
				twareHouseDetail.setIwareHouse(map.get(obj[1].toString()));
				twareHouseDetail.setAmt(new BigDecimal(obj[7].toString()).multiply(genWareHousePrice()).setScale(2,RoundingMode.HALF_UP));
				if(!StringUtils.isEmpty(manualDate)){
					twareHouseDetail.setGenTime(date);
				}
				this.twareHouseDetailService.save(twareHouseDetail);
			}
		}
	}
	
	/**
	 * 自动生成通知书
	 * @param tlogisticsOrd
	 * @param tcustReg
	 * @param tuser
	 * @throws BizException 
	 */
	public void autoCreatTpayNotify(Long icust, Long iagmt , int custStockRemainNum , TwareHouse twareHouse, Map<String, Long> map) throws BizException{
	    Tagmt tagmt = tagmtService.findById(iagmt);
	    BigDecimal bigDecimal = genWareHousePrice().multiply(new  BigDecimal(custStockRemainNum)).setScale(2,RoundingMode.HALF_UP);
	    Tuser tuser = this.tuserService.getTuser(1L);
	    TcustReg tcustReg = this.tcustService.getTcustReg(icust);
	    if(bigDecimal.compareTo(BigDecimal.ZERO) > 0){
	    	
	    	// 如果付费，就生成通知书
			TpayNotify tpayNotify = new TpayNotify();
			tpayNotify.setIcust(icust);
			tpayNotify.setPayType(PayType.WAREHOUSE);
			tpayNotify.setIfk(twareHouse.getIwareHouse());
			tpayNotify.setIhisFk(twareHouse.getIwareHouse());
			tpayNotify.setMemo("客户物流单支付通知书");
			tpayNotify.setPayNotifyStatus(PayStatus.WAIT_PAY);
			this.tpayNotifyService.save(tpayNotify);
	    	
	    	if(tagmt.getRemainDeposit().compareTo(BigDecimal.ZERO)==0){
	    		 //剩余保证金为0，不能自动扣费
	    		twareHouse.setPayStatus(PayStatus.WAIT_PAY);
	    	}else if(tagmt.getRemainDeposit().compareTo(bigDecimal) >= 0){
	    		//如果剩余保证大于 物流费用，用保证金 全额支付
	    		BigDecimal payAmt = bigDecimal;
	    		Tpay tpay = new Tpay();
	    		tpay.setPayStatus(PayStatus.HAVE_PAY);
	    		tpay.setIcust(tagmt.getIcust());
	    		tpay.setAmt(payAmt);
	    		tpay.setPayMethod(PayMethod.DEPOSIT);
	    		tpay.setRemainAmt(new BigDecimal(0));
	    		tpayService.add(tpay);
	    		TpayDetail tpayDetail = new TpayDetail();
	    		tpayDetail.setIpay(tpay.getIpay());
	    		tpayDetail.setIfk(tpayNotify.getIfk());
	    		tpayDetail.setIhisFk(tpayNotify.getIhisFk());
	    		tpayDetail.setPayType(PayType.WAREHOUSE);
	    		tpayDetail.setPayStatus(PayStatus.HAVE_PAY);
	    		tpayDetail.setIcust(tpayNotify.getIcust());
	    		tpayDetail.setCustName(tcustReg.getName());
	    		tpayDetail.setIuser(tuser.getIuser());
	    		tpayDetail.setUserName(tuser.getTuserSub().getName());
	    		tpayDetail.setPayMethod(PayMethod.DEPOSIT);
	    		tpayDetail.setPayAmt(payAmt);
	    		tpayDetailService.save(tpayDetail);
	    		//变更协议保证金
	    		tagmt.setRemainDeposit(tagmt.getRemainDeposit().subtract(payAmt));
	    		tagmt.setUsedDeposit(tagmt.getUsedDeposit().add(payAmt));
	    		tagmtService.modifyTamgtDeposit(tagmt);
	    		
	    		//更新通知书状态
	    		tpayNotify.setPayNotifyStatus(PayStatus.HAVE_PAY);
	    		tpayNotify.setHavepayAmt(payAmt);
	    		tpayNotifyService.update(tpayNotify);
	    		
	    		twareHouse.setPayStatus(PayStatus.HAVE_PAY);
//	    		adminMailService.sendLogisticsMail(tcustReg, tagmt, tpayNotify, tlogisticsOrd);
	    		
	    	}else if(tagmt.getRemainDeposit().compareTo(bigDecimal) < 0){
	    		//如果剩余保证金小于 物流费用， 部分支付
	    		BigDecimal payAmt = tagmt.getRemainDeposit();
	    		Tpay tpay = new Tpay();
	    		tpay.setPayStatus(PayStatus.HAVE_PAY);
	    		tpay.setIcust(tagmt.getIcust());
	    		tpay.setAmt(payAmt);
	    		tpay.setPayMethod(PayMethod.DEPOSIT);
	    		tpay.setRemainAmt(new BigDecimal(0));
	    		tpayService.add(tpay);
	    		TpayDetail tpayDetail = new TpayDetail();
	    		tpayDetail.setIpay(tpay.getIpay());
	    		tpayDetail.setIfk(tpayNotify.getIfk());
	    		tpayDetail.setIhisFk(tpayNotify.getIhisFk());
	    		tpayDetail.setPayType(PayType.WAREHOUSE);
	    		tpayDetail.setPayStatus(PayStatus.HAVE_PAY);
	    		tpayDetail.setIcust(tpayNotify.getIcust());
	    		tpayDetail.setCustName(tcustReg.getName());
	    		tpayDetail.setIuser(tuser.getIuser());
	    		tpayDetail.setUserName(tuser.getTuserSub().getName());
	    		tpayDetail.setPayMethod(PayMethod.DEPOSIT);
	    		tpayDetail.setPayAmt(payAmt);
	    		tpayDetailService.save(tpayDetail);
	    		
	    		//变更协议保证金
	    		tagmt.setUsedDeposit(tagmt.getUsedDeposit().add(payAmt));
	    		tagmt.setRemainDeposit(new BigDecimal(0));
	    		tagmtService.modifyTamgtDeposit(tagmt);
	    		
	    		//更新通知书状态
	    		tpayNotify.setPayNotifyStatus(PayStatus.PART_PAY);
	    		tpayNotify.setHavepayAmt(payAmt);
	    		tpayNotifyService.update(tpayNotify);
	    		
	    		twareHouse.setPayStatus(PayStatus.PART_PAY);
//	    		adminMailService.sendLogisticsMail(tcustReg, tagmt, tpayNotify, tlogisticsOrd);
	    	}
	    	
	    	map.put(iagmt.toString(), twareHouse.getIwareHouse());
	    	this.twareHouseService.update(twareHouse);
	    }
	    
	}

}
