package com.newland.posmall.biz.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.entity.ERPMaintenance;
import org.ohuyo.rapid.base.entity.Tuser;
import org.ohuyo.rapid.base.service.MaintenanceManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.newland.posmall.base.exception.BizException;
import com.newland.posmall.base.service.TagmtService;
import com.newland.posmall.base.service.TcustRegService;
import com.newland.posmall.base.service.TdetailTraceService;
import com.newland.posmall.base.service.TlogService;
import com.newland.posmall.base.service.TlogisticsService;
import com.newland.posmall.base.service.TordService;
import com.newland.posmall.base.service.TpayDetailService;
import com.newland.posmall.base.service.TpayNotifyService;
import com.newland.posmall.base.service.TpayService;
import com.newland.posmall.base.service.TrenewDetailService;
import com.newland.posmall.base.service.TrenewService;
import com.newland.posmall.base.service.TwareHouseDetailService;
import com.newland.posmall.base.service.TwareHouseService;
import com.newland.posmall.bean.basebusi.Tagmt;
import com.newland.posmall.bean.basebusi.TagmtDetail;
import com.newland.posmall.bean.basebusi.TlogisticsOrd;
import com.newland.posmall.bean.basebusi.Tord;
import com.newland.posmall.bean.basebusi.Tpay;
import com.newland.posmall.bean.basebusi.TpayDetail;
import com.newland.posmall.bean.basebusi.TpayNotify;
import com.newland.posmall.bean.basebusi.TwareHouse;
import com.newland.posmall.bean.basebusi.TwareHouseDetail;
import com.newland.posmall.bean.basebusi.condition.TcustRegCondition;
import com.newland.posmall.bean.common.Tlog;
import com.newland.posmall.bean.customer.TcustReg;
import com.newland.posmall.bean.customer.Trenew;
import com.newland.posmall.bean.customer.TrenewDetail;
import com.newland.posmall.bean.dict.AgmtDetailStatus;
import com.newland.posmall.bean.dict.AgmtStatus;
import com.newland.posmall.bean.dict.LogType;
import com.newland.posmall.bean.dict.OperType;
import com.newland.posmall.bean.dict.PayMethod;
import com.newland.posmall.bean.dict.PayStatus;
import com.newland.posmall.bean.dict.PayType;

@Service
@Transactional(rollbackFor = Throwable.class)
public class PayNotifyBiz {
	
	private static final Logger logger = LoggerFactory.getLogger(PayNotifyBiz.class);
	
	@Resource
	private TpayNotifyService tpayNotifyService;

	@Resource
	private TpayService tpayService;
	
	@Resource
	private TcustRegService tcustRegService;

	@Resource
	private TpayDetailService tpayDetailService;

	@Resource
	private TagmtService tagmtService;

	@Resource
	private TordService tordService;
	
	@Resource
	private TlogisticsService tlogisticsService;
	
	@Resource
	private TlogService tlogService;
	
	@Resource
	private AdminMailService adminMailService;
	
	@Resource
	private TwareHouseService twareHouseService;
	
	@Resource
	private TwareHouseDetailService twareHouseDetailService;
	
	@Resource
	private TdetailTraceService tdetailTraceService;
	
	@Resource
	private TrenewService trenewService;
	
	@Resource
	private TrenewDetailService trenewDetailService;
	
	@Resource
	private MaintenanceManageService maintenanceManageService;
	
	public Boolean vaidatePartPay(TpayNotify tpayNotify){
		
		//查询待审核
		List<TpayDetail> tdList = this.tpayDetailService.findSelectWaitAudit(tpayNotify.getIfk(), tpayNotify.getPayType());
 		//待审核总金额
		BigDecimal sumAmt = new BigDecimal(0);
		Tpay tpay = null;
 		if(!CollectionUtils.isEmpty(tdList)){
			for(TpayDetail td : tdList){
				if(tpayNotify.getPayType() == PayType.ORDER && td.getPayMethod() != PayMethod.DEPOSIT){
					//过滤 订单类型的 保证金
					tpay = tpayService.find(td.getIpay());
					sumAmt = sumAmt.add(tpay.getRemainAmt());
				}else if(tpayNotify.getPayType() != PayType.ORDER){
					tpay = tpayService.find(td.getIpay());
					sumAmt = sumAmt.add(tpay.getRemainAmt());
				}
			}
 		}
 		if(tpayNotify.getHavepayAmt() != null){
			sumAmt = sumAmt.add(tpayNotify.getHavepayAmt() );
		}
 		Boolean flag = false;
 		switch (tpayNotify.getPayType()) {
		case BAIL:
			Tagmt tagmt = this.tagmtService.findById(tpayNotify.getIfk());
			//协议待审核
			if(tagmt.getDeposit().compareTo(sumAmt) <= 0 ){
				flag = true;
			}
			break;

		case LOGISTICS:
			TlogisticsOrd tlogisticsOrd = this.tlogisticsService.findTlogisticsOrdById(tpayNotify.getIfk());
			if(tlogisticsOrd.getLogisticsFreight().compareTo(sumAmt) <= 0 ){
				flag = true;
			}
			break;
		case ORDER:
			Tord tord = this.tordService.find(tpayNotify.getIfk());
			if(tord.getLockAmtOfDeposit() != null && tord.getLockAmtOfDeposit().compareTo(BigDecimal.ZERO) > 0){
				//如果有保证金锁定，待审核金额相加
				sumAmt = sumAmt.add(tord.getLockAmtOfDeposit());
			}
			if(tord.getAmt().compareTo(sumAmt) <= 0 ){
				flag = true;
			}
			break;
		case RENEW_AMT:
			Trenew trenew = this.trenewService.findById(tpayNotify.getIfk());
			//协议待审核
			if(trenew.getRenewAmt().compareTo(sumAmt) <= 0 ){
				flag = true;
			}
			break;
		case BAIL_SUPPLEMENT:
			tagmt = this.tagmtService.findById(tpayNotify.getIfk());
			//协议待审核
			if(tagmt.getRedundantDeposit().negate().compareTo(sumAmt) <= 0 ){
				flag = true;
			}
			break;
		 default:
	    }
 		
 		return flag;
	}
	
	/**
	 * 付款通知书的 付款审核(通过和部分支付) 部分支付的话， pay 和 payDetail 已支付,通知书为部分支付
	 * @throws BizException 
	 * 
	 */
	public TpayNotify payAudit(TpayNotify tpayNotify, PayStatus payStatus, Tuser tuser) throws BizException {
		if(tpayNotify == null){
			logger.debug("未找到付款通知书");
			throw new BizException(0, "未找到付款通知书");
		}
		if(tpayNotify.getPayNotifyStatus() != PayStatus.WAIT_AUDIT){
			logger.debug("付款通知书状态异常，不是待审核状态");
			throw new BizException(0, "付款通知书状态异常，不是待审核状态");
		}
		//待审核的凭证
		Tpay tpay = null;
		TpayDetail tpayDetail = null;
		//支付金额
	    BigDecimal havepay = null;
	    List<Tpay> tpayList = new ArrayList<Tpay>();
	    //添加 操作日志
		Tlog tlog = new Tlog();
		tlog.setIfk(tuser.getIuser());
		tlog.setLogType(LogType.USER);
		tlog.setOperType(OperType.PAYNOTIFY_MGR);
		StringBuffer sb = new StringBuffer();
		sb.append("用户审核付款通知书,编号:").append(tpayNotify.getPayNotifyNo()).append("，类型").append(tpayNotify.getPayType()).append(",id:").append(tpayNotify.getIfk());
		tlog.setMemo(sb.toString());
		tlog.setPreData("审核前状态为"+tpayNotify.getPayNotifyStatus());
	    
	    // 变更 付款通知书菜单
 		tpayNotify.setPayNotifyStatus(payStatus);
 		List<TpayDetail> tdList = this.tpayDetailService.findSelectWaitAudit(tpayNotify.getIfk(), tpayNotify.getPayType());
 		
	 	if(CollectionUtils.isEmpty(tdList)){
	 		logger.debug("通知书没有待审核的凭证");
	 		throw new BizException(0, "通知书没有待审核的凭证");
 		}
	 	BigDecimal sumAmt = new BigDecimal(0); //待审核金额
	 	for(TpayDetail td : tdList){
	 		tpay = tpayService.find(td.getIpay());
	 		tpayList.add(tpay);
	 		if(tpayNotify.getPayType() == PayType.ORDER && td.getPayMethod() != PayMethod.DEPOSIT){
	 			//过滤订单类型的保证金
	 			sumAmt = sumAmt.add(tpay.getRemainAmt());
	 		}else if(tpayNotify.getPayType() != PayType.ORDER){
	 			sumAmt = sumAmt.add(tpay.getRemainAmt());
	 		}
	 	}
	 	switch (tpayNotify.getPayType()) {
	 	case BAIL:
	 		Tagmt tagmt = this.tagmtService.findById(tpayNotify.getIfk());
	 		//协议待审核
	 		tpay = this.tpayService.find(tdList.get(0).getIpay());
	 		// 协议付款审核
	 		if (payStatus == PayStatus.HAVE_PAY) {
	 			this.agmtPayAudit(tagmt, AgmtStatus.PAY_PASS,
	 					AgmtDetailStatus.PAY_PASS);
	 			if(tagmt.getDeposit().compareTo(sumAmt.add(tpayNotify.getHavepayAmt())) > 0 ){
	 				logger.debug("金额不足以全额支付，只能部分支付");
	 				throw new BizException(100, "金额不足以全额支付，只能部分支付");
	 			}else{
	 				
	 				if(tpayNotify.getHavepayAmt() == null){
	 					havepay = tagmt.getDeposit() ;
	 				}else{
	 					havepay = tagmt.getDeposit().subtract(tpayNotify.getHavepayAmt()) ;
	 				}
	 				
	 				for(int i = 0; i < tdList.size(); i++ ){
	 					tpayDetail = tdList.get(i);
	 					tpay = tpayList.get(i);
	 					tpayDetail.setPayAmt(havepay);
	 					tpay.setRemainAmt(tpay.getAmt().subtract(havepay));
	 					tpayDetail.setPayStatus(PayStatus.HAVE_PAY);
	 					tpay.setPayStatus(PayStatus.HAVE_PAY);
	 					tpayDetail.setIuser(tuser.getIuser());
	 					tpayDetail.setUserName(tuser.getTuserSub().getName());
	 					this.tpayDetailService.update(tpayDetail);
	 					this.tpayService.modify(tpay);
	 				}
	 				// 凭证足够全额支付
	 				if(tpayNotify.getHavepayAmt() != null){
	 					tpayNotify.setHavepayAmt(tpayNotify.getHavepayAmt().add(havepay));
	 				}else{
	 					tpayNotify.setHavepayAmt(havepay);
	 				}
	 				logger.info("保证金全额支付");
	 			}
	 			//添加跟踪信息
	 			StringBuffer memo = new StringBuffer("后台管理员 审核  保证金支付(账单编号："+tpayNotify.getPayNotifyNo());
	 			memo.append(") 通过， 全额支付:").append(tpayNotify.getHavepayAmt()).append(" 元");
	 			tdetailTraceService.add4Tagmt(tagmt, tuser, memo.toString(), null, null);
	 		} else {
	 			// 发送部分支付通知
	 			if(tagmt.getDeposit().compareTo(sumAmt.add(tpayNotify.getHavepayAmt())) <= 0 ){
	 				logger.debug("金额足以全额支付");
	 				throw new BizException(100, "金额足以全额支付");
	 			}else{
	 				havepay = tpay.getRemainAmt();
	 				
	 				for(int i = 0; i < tdList.size(); i++ ){
	 					tpayDetail = tdList.get(i);
	 					tpay = tpayList.get(i);
	 					
	 					tpayDetail.setPayAmt(havepay);
	 					tpay.setRemainAmt(tpay.getRemainAmt().subtract(havepay));
	 					tpayDetail.setPayStatus(PayStatus.HAVE_PAY);
	 					tpay.setPayStatus(PayStatus.HAVE_PAY);
	 					tpayDetail.setIuser(tuser.getIuser());
	 					tpayDetail.setUserName(tuser.getTuserSub().getName());
	 					this.tpayDetailService.update(tpayDetail);
	 					this.tpayService.modify(tpay);
	 				}
	 				
	 				// 凭证 部分支付
	 				if(tpayNotify.getHavepayAmt() != null){
	 					tpayNotify.setHavepayAmt(tpayNotify.getHavepayAmt().add(havepay));
	 				}else{
	 					tpayNotify.setHavepayAmt(havepay);
	 				}
	 			}
	 			logger.info("保证金部分支付");
	 			//添加跟踪信息
	 			StringBuffer memo = new StringBuffer("后台管理员 审核  保证金支付(账单编号："+tpayNotify.getPayNotifyNo());
	 			memo.append(") 通过，部分支付:").append(tpayNotify.getHavepayAmt()).append(" 元");
	 			tdetailTraceService.add4Tagmt(tagmt, tuser, memo.toString(), null, null);
	 		}
	 		break;
	 		
	 	case LOGISTICS:
	 		TlogisticsOrd tlogisticsOrd = this.tlogisticsService.findTlogisticsOrdById(tpayNotify.getIfk());
	 		
	 		// 订单付款审核
	 		if (payStatus == PayStatus.HAVE_PAY) {
	 			this.tlogisticsOrdPayAudit(tlogisticsOrd, PayStatus.HAVE_PAY, tuser);
	 			if(tlogisticsOrd.getLogisticsFreight().compareTo(sumAmt.add(tpayNotify.getHavepayAmt())) > 0 ){
	 				throw new BizException(100, "金额不足以全额支付，只能部分支付");
	 			}else{
	 				if(tpayNotify.getHavepayAmt() == null){
	 					havepay = tlogisticsOrd.getLogisticsFreight() ;
	 				}else{
	 					havepay = tlogisticsOrd.getLogisticsFreight().subtract(tpayNotify.getHavepayAmt()) ;
	 				}
	 				BigDecimal bd = new BigDecimal(0);
	 				//更新凭证详细 和凭证(保证金)
	 				for(int i = 0; i < tdList.size(); i++ ){
	 					tpayDetail = tdList.get(i);
	 					tpay = tpayList.get(i);
	 					if(tpayDetail.getPayMethod() == PayMethod.DEPOSIT){
	 						tpayDetail.setPayAmt(tpay.getRemainAmt());
	 						tpay.setRemainAmt(new BigDecimal(0));
	 						tpayDetail.setPayStatus(PayStatus.HAVE_PAY);
	 						tpay.setPayStatus(PayStatus.HAVE_PAY);
	 						tpayDetail.setIuser(tuser.getIuser());
	 						tpayDetail.setUserName(tuser.getTuserSub().getName());
	 						this.tpayDetailService.update(tpayDetail);
	 						this.tpayService.modify(tpay);
	 						bd = havepay.subtract(tpayDetail.getPayAmt());
	 					}
	 				}
	 				
	 				//如果bd为0，没有保证金
	 				if(bd.compareTo(BigDecimal.ZERO) == 0){
	 					bd = havepay;
	 				}
	 				
	 				//更新凭证详细 和凭证(保证金)
	 				for(int i = 0; i < tdList.size(); i++ ){
	 					tpayDetail = tdList.get(i);
	 					tpay = tpayList.get(i);
	 					//TODO 只有一个
	 					if(tpayDetail.getPayMethod() == PayMethod.REMITTANCE){
	 						tpayDetail.setPayAmt(bd);
	 						tpay.setRemainAmt(tpay.getAmt().subtract(bd));
	 						tpayDetail.setPayStatus(PayStatus.HAVE_PAY);
	 						tpay.setPayStatus(PayStatus.HAVE_PAY);
	 						tpayDetail.setIuser(tuser.getIuser());
	 						tpayDetail.setUserName(tuser.getTuserSub().getName());
	 						this.tpayDetailService.update(tpayDetail);
	 						this.tpayService.modify(tpay);
	 					}
	 				}
	 				
	 				// 凭证足够全额支付
	 				if(tpayNotify.getHavepayAmt() != null){
	 					tpayNotify.setHavepayAmt(tpayNotify.getHavepayAmt().add(havepay));
	 				}else{
	 					tpayNotify.setHavepayAmt(havepay);
	 				}
	 			}
	 			//添加跟踪信息
	 			StringBuffer memo2 = new StringBuffer("后台管理员 审核  物流单费用支付(账单编号："+tpayNotify.getPayNotifyNo());
	 			memo2.append(") 通过，全额支付：").append(tpayNotify.getHavepayAmt()).append(" 元");
	 			tdetailTraceService.add4TlogisticsOrd(tlogisticsOrd, tuser, memo2.toString(), null, null);
	 			
	 		} else {
	 			// 发送部分支付通知
	 			this.tlogisticsOrdPayAudit(tlogisticsOrd, PayStatus.PART_PAY, tuser);
	 			if(tlogisticsOrd.getLogisticsFreight().compareTo(sumAmt.add(tpayNotify.getHavepayAmt())) <= 0 ){
	 				throw new BizException(100, "金额足以全额支付");
	 			}else{
	 				//更新凭证详细 和凭证
	 				for(int i = 0; i < tdList.size(); i++ ){
	 					tpayDetail = tdList.get(i);
	 					tpay = tpayList.get(i);
	 					
	 					tpayDetail.setPayAmt(tpay.getRemainAmt());
	 					tpay.setRemainAmt(new BigDecimal(0));
	 					tpayDetail.setPayStatus(PayStatus.HAVE_PAY);
	 					tpay.setPayStatus(PayStatus.HAVE_PAY);
	 					tpayDetail.setIuser(tuser.getIuser());
	 					tpayDetail.setUserName(tuser.getTuserSub().getName());
	 					this.tpayDetailService.update(tpayDetail);
	 					this.tpayService.modify(tpay);
	 				}
	 				
	 				havepay = sumAmt;
	 				// 凭证 部分支付
	 				if(tpayNotify.getHavepayAmt() != null){
	 					tpayNotify.setHavepayAmt(tpayNotify.getHavepayAmt().add(havepay));
	 				}else{
	 					tpayNotify.setHavepayAmt(havepay);
	 				}
	 			}
	 			//添加跟踪信息
	 			StringBuffer memo2 = new StringBuffer("后台管理员 审核  物流单费用支付(账单编号："+tpayNotify.getPayNotifyNo());
	 			memo2.append(") 通过， 部分支付：").append(tpayNotify.getHavepayAmt()).append(" 元");
	 			tdetailTraceService.add4TlogisticsOrd(tlogisticsOrd, tuser, memo2.toString(), null, null);
	 		}
	 		break;
	 	case ORDER:
	 		// 订单付款审核
	 		// 公式：订单货款 = 待审核金额（sumAmt） + 已支付金额（tpayNotify.getHavepayAmt()） + 锁定保证金（tord.getLockAmtOfDeposit()）
	 		// 如果订单全部支付，则tord.getLockAmtOfDeposit() == 0，tpayNotify.getHavepayAmt() == 订单货款；
	 		// 如果订单部分支付，则tord.getLockAmtOfDeposit() > 0，tpayNotify.getHavepayAmt()不包含保证金
	 		Tord tord = this.tordService.find(tpayNotify.getIfk());
	 		if (payStatus == PayStatus.HAVE_PAY) {

	 			if(tord.getAmt().compareTo(sumAmt.add(tpayNotify.getHavepayAmt()).add(tord.getLockAmtOfDeposit())) != 0){
	 				throw new BizException(100, "金额不足以全额支付，只能部分支付");
	 			}
	 			//更新凭证详细 和凭证(保证金)
	 			for(int i = 0; i < tdList.size(); i++ ){
	 				tpayDetail = tdList.get(i);
	 				tpay = tpayList.get(i);
	 				tpayDetail.setPayAmt(tpay.getRemainAmt());
	 				tpay.setRemainAmt(new BigDecimal(0));
	 				tpayDetail.setPayStatus(PayStatus.HAVE_PAY);
	 				tpay.setPayStatus(PayStatus.HAVE_PAY);
	 				tpayDetail.setIuser(tuser.getIuser());
	 				tpayDetail.setUserName(tuser.getTuserSub().getName());
	 				this.tpayDetailService.update(tpayDetail);
	 				this.tpayService.modify(tpay);
	 			}
	 			
	 			tpayNotify.setHavepayAmt(tord.getAmt()); // 凭证足够全额支付，已支付金额=订单货款
	 			
	 			tord.setLockAmtOfDeposit(new BigDecimal(0)); // 订单全额支付时，锁定保证金置为0
	 			this.ordPayAudit(tord, PayStatus.HAVE_PAY);
	 			
	 			//添加跟踪信息
	 			StringBuffer memo3 = new StringBuffer("后台管理员 审核  订单货款支付(账单编号："+tpayNotify.getPayNotifyNo());
	 			memo3.append(") 通过， 全额支付：").append(tpayNotify.getHavepayAmt());
	 			tdetailTraceService.add4Tord(tord, tuser, memo3.toString(), null, null);
	 		} else {
	 			
	 			if(tord.getAmt().compareTo(sumAmt.add(tpayNotify.getHavepayAmt()).add(tord.getLockAmtOfDeposit())) <= 0){
	 				throw new BizException(100, "金额足以全额支付");
	 			}
	 			//更新凭证详细 和凭证
	 			for(int i = 0; i < tdList.size(); i++ ){
	 				tpayDetail = tdList.get(i);
	 				tpay = tpayList.get(i);
	 				tpayDetail.setPayAmt(tpay.getRemainAmt());
	 				tpay.setRemainAmt(new BigDecimal(0));
	 				tpayDetail.setPayStatus(PayStatus.HAVE_PAY);
	 				tpay.setPayStatus(PayStatus.HAVE_PAY);
	 				tpayDetail.setIuser(tuser.getIuser());
	 				tpayDetail.setUserName(tuser.getTuserSub().getName());
	 				this.tpayDetailService.update(tpayDetail);
	 				this.tpayService.modify(tpay);
	 			}
	 			
	 			tpayNotify.setHavepayAmt(tpayNotify.getHavepayAmt().add(sumAmt)); // 凭证 部分支付 tpayNotify.getHavepayAmt()不包含保证金；锁定保证金不变
	 			
	 			// 发送部分支付通知
	 			this.ordPayAudit(tord, PayStatus.PART_PAY);
	 			
	 			//添加跟踪信息
	 			StringBuffer memo3 = new StringBuffer("后台管理员 审核  订单货款支付(账单编号："+tpayNotify.getPayNotifyNo());
	 			memo3.append(") 通过， 部分支付：").append(tpayNotify.getHavepayAmt()).append(" 元, 保证金锁定金额：").append(tord.getLockAmtOfDeposit()).append(" 元。");
	 			tdetailTraceService.add4Tord(tord, tuser, memo3.toString(), null, null);
	 		}
	 		break;
	 		
	 	case WAREHOUSE:
	 		TwareHouse twareHouse = this.twareHouseService.find(tpayNotify.getIfk());
	 		
	 		// 订单付款审核
	 		if (payStatus == PayStatus.HAVE_PAY) {
	 			this.twareHousePayAudit(twareHouse, PayStatus.HAVE_PAY);
	 			if(twareHouse.getAmt().compareTo(sumAmt.add(tpayNotify.getHavepayAmt())) > 0 ){
	 				throw new BizException(100, "金额不足以全额支付，只能部分支付");
	 			}else{
	 				if(tpayNotify.getHavepayAmt() == null){
	 					havepay = twareHouse.getAmt() ;
	 				}else{
	 					havepay = twareHouse.getAmt().subtract(tpayNotify.getHavepayAmt()) ;
	 				}
	 				BigDecimal bd = new BigDecimal(0);
	 				//更新凭证详细 和凭证(保证金)
	 				for(int i = 0; i < tdList.size(); i++ ){
	 					tpayDetail = tdList.get(i);
	 					tpay = tpayList.get(i);
	 					if(tpayDetail.getPayMethod() == PayMethod.DEPOSIT){
	 						tpayDetail.setPayAmt(tpay.getRemainAmt());
	 						tpay.setRemainAmt(new BigDecimal(0));
	 						tpayDetail.setPayStatus(PayStatus.HAVE_PAY);
	 						tpay.setPayStatus(PayStatus.HAVE_PAY);
	 						tpayDetail.setIuser(tuser.getIuser());
	 						tpayDetail.setUserName(tuser.getTuserSub().getName());
	 						this.tpayDetailService.update(tpayDetail);
	 						this.tpayService.modify(tpay);
	 						bd = havepay.subtract(tpayDetail.getPayAmt());
	 					}
	 				}
	 				
	 				//如果bd为0，没有保证金
	 				if(bd.compareTo(BigDecimal.ZERO) == 0){
	 					bd = havepay;
	 				}
	 				
	 				//更新凭证详细 和凭证(保证金)
	 				for(int i = 0; i < tdList.size(); i++ ){
	 					tpayDetail = tdList.get(i);
	 					tpay = tpayList.get(i);
	 					//TODO 只有一个
	 					if(tpayDetail.getPayMethod() == PayMethod.REMITTANCE){
	 						tpayDetail.setPayAmt(bd);
	 						tpay.setRemainAmt(tpay.getAmt().subtract(bd));
	 						tpayDetail.setPayStatus(PayStatus.HAVE_PAY);
	 						tpay.setPayStatus(PayStatus.HAVE_PAY);
	 						tpayDetail.setIuser(tuser.getIuser());
	 						tpayDetail.setUserName(tuser.getTuserSub().getName());
	 						this.tpayDetailService.update(tpayDetail);
	 						this.tpayService.modify(tpay);
	 					}
	 				}
	 				
	 				// 凭证足够全额支付
	 				if(tpayNotify.getHavepayAmt() != null){
	 					tpayNotify.setHavepayAmt(tpayNotify.getHavepayAmt().add(havepay));
	 				}else{
	 					tpayNotify.setHavepayAmt(havepay);
	 				}
	 			}
	 		} else {
	 			// 发送部分支付通知
	 			this.twareHousePayAudit(twareHouse, PayStatus.PART_PAY);
	 			if(twareHouse.getAmt().compareTo(sumAmt.add(tpayNotify.getHavepayAmt())) <= 0 ){
	 				throw new BizException(100, "金额足以全额支付");
	 			}else{
	 				//更新凭证详细 和凭证
	 				for(int i = 0; i < tdList.size(); i++ ){
	 					tpayDetail = tdList.get(i);
	 					tpay = tpayList.get(i);
	 					
	 					tpayDetail.setPayAmt(tpay.getRemainAmt());
	 					tpay.setRemainAmt(new BigDecimal(0));
	 					tpayDetail.setPayStatus(PayStatus.HAVE_PAY);
	 					tpay.setPayStatus(PayStatus.HAVE_PAY);
	 					tpayDetail.setIuser(tuser.getIuser());
	 					tpayDetail.setUserName(tuser.getTuserSub().getName());
	 					this.tpayDetailService.update(tpayDetail);
	 					this.tpayService.modify(tpay);
	 				}
	 				
	 				havepay = sumAmt;
	 				// 凭证 部分支付
	 				if(tpayNotify.getHavepayAmt() != null){
	 					tpayNotify.setHavepayAmt(tpayNotify.getHavepayAmt().add(havepay));
	 				}else{
	 					tpayNotify.setHavepayAmt(havepay);
	 				}
	 			}
	 			
	 		}
	 		break;
	 	case RENEW_AMT:
	 		Trenew trenew = this.trenewService.findById(tpayNotify.getIfk());
	 		// 订单付款审核
	 		if (payStatus == PayStatus.HAVE_PAY) {//全额支付
	 			
	 			if(trenew.getRenewAmt().compareTo(sumAmt.add(tpayNotify.getHavepayAmt())) > 0 ){
	 				throw new BizException(100, "金额不足以全额支付，只能部分支付");
	 			}else{
	 				if(tpayNotify.getHavepayAmt() == null){
	 					havepay = trenew.getRenewAmt() ;
	 				}else{
	 					havepay = trenew.getRenewAmt().subtract(tpayNotify.getHavepayAmt()) ;
	 				}
	 				BigDecimal bd = new BigDecimal(0);
	 				
	 				//如果bd为0，没有保证金
	 				if(bd.compareTo(BigDecimal.ZERO) == 0){
	 					bd = havepay;
	 				}
	 				
	 				//更新凭证详细 和凭证(保证金)
	 				for(int i = 0; i < tdList.size(); i++ ){
	 					tpayDetail = tdList.get(i);
	 					tpay = tpayList.get(i);
	 					//TODO 只有一个
	 					if(tpayDetail.getPayMethod() == PayMethod.REMITTANCE){
	 						
	 						tpayDetail.setPayAmt(bd);
	 						tpay.setRemainAmt(tpay.getAmt().subtract(bd));
	 						tpayDetail.setPayStatus(PayStatus.HAVE_PAY);
	 						tpay.setPayStatus(PayStatus.HAVE_PAY);
	 						tpayDetail.setIuser(tuser.getIuser());
	 						tpayDetail.setUserName(tuser.getTuserSub().getName());
	 						this.tpayDetailService.update(tpayDetail);
	 						this.tpayService.modify(tpay);
	 					}
	 				}
	 				
	 				// 凭证足够全额支付
	 				if(tpayNotify.getHavepayAmt() != null){
	 					tpayNotify.setHavepayAmt(tpayNotify.getHavepayAmt().add(havepay));
	 				}else{
	 					tpayNotify.setHavepayAmt(havepay);
	 				}
	 				this.renewPayAudit(trenew, PayStatus.HAVE_PAY);
	 			}
	 			
	 		} else {
	 			
	 			if(trenew.getRenewAmt().compareTo(sumAmt.add(tpayNotify.getHavepayAmt())) <= 0 ){
	 				throw new BizException(100, "金额足以全额支付");
	 			}else{
	 				//更新凭证详细 和凭证
	 				for(int i = 0; i < tdList.size(); i++ ){
	 					tpayDetail = tdList.get(i);
	 					tpay = tpayList.get(i);
	 					
	 					tpayDetail.setPayAmt(tpay.getRemainAmt());
	 					tpay.setRemainAmt(new BigDecimal(0));
	 					tpayDetail.setPayStatus(PayStatus.HAVE_PAY);
	 					tpay.setPayStatus(PayStatus.HAVE_PAY);
	 					tpayDetail.setIuser(tuser.getIuser());
	 					tpayDetail.setUserName(tuser.getTuserSub().getName());
	 					this.tpayDetailService.update(tpayDetail);
	 					this.tpayService.modify(tpay);
	 					
	 				}
	 				
	 				havepay = sumAmt;
	 				// 凭证 部分支付
	 				if(tpayNotify.getHavepayAmt() != null){
	 					tpayNotify.setHavepayAmt(tpayNotify.getHavepayAmt().add(havepay));
	 				}else{
	 					tpayNotify.setHavepayAmt(havepay);
	 				}
	 				
	 				// 发送部分支付通知
	 				this.renewPayAudit(trenew, PayStatus.PART_PAY);
	 			}
	 		}
	 		break;
	 	case BAIL_SUPPLEMENT:
	 		tagmt = this.tagmtService.findById(tpayNotify.getIfk());
	 		//协议待审核
	 		tpay = this.tpayService.find(tdList.get(0).getIpay());
	 		// 协议付款审核
	 		if (payStatus == PayStatus.HAVE_PAY) {
	 			if(tagmt.getRedundantDeposit().negate().compareTo(sumAmt.add(tpayNotify.getHavepayAmt())) > 0 ){
	 				logger.debug("金额不足以全额支付，只能部分支付");
	 				throw new BizException(100, "金额不足以全额支付，只能部分支付");
	 			}else{
	 				
	 				if(tpayNotify.getHavepayAmt() == null){
	 					havepay = tagmt.getRedundantDeposit().negate();
	 				}else{
	 					havepay = tagmt.getRedundantDeposit().negate().subtract(tpayNotify.getHavepayAmt());
	 				}
	 				
	 				for(int i = 0; i < tdList.size(); i++ ){
	 					tpayDetail = tdList.get(i);
	 					tpay = tpayList.get(i);
	 					tpayDetail.setPayAmt(havepay);
	 					tpay.setRemainAmt(tpay.getAmt().subtract(havepay));
	 					tpayDetail.setPayStatus(PayStatus.HAVE_PAY);
	 					tpay.setPayStatus(PayStatus.HAVE_PAY);
	 					tpayDetail.setIuser(tuser.getIuser());
	 					tpayDetail.setUserName(tuser.getTuserSub().getName());
	 					this.tpayDetailService.update(tpayDetail);
	 					this.tpayService.modify(tpay);
	 				}
	 				// 凭证足够全额支付
	 				tpayNotify.setHavepayAmt(tagmt.getRedundantDeposit().negate());
	 				logger.info("保证金全额支付");
	 			}
	 			//添加跟踪信息
	 			StringBuffer memo = new StringBuffer("后台管理员 审核  保证金补交(账单编号："+tpayNotify.getPayNotifyNo());
	 			memo.append(") 通过， 补交全额:").append(tpayNotify.getHavepayAmt()).append(" 元");
	 			tdetailTraceService.add4Tagmt(tagmt, tuser, memo.toString(), null, null);
	 		} else {
	 			// 发送部分支付通知
	 			if(tagmt.getRedundantDeposit().negate().compareTo(sumAmt.add(tpayNotify.getHavepayAmt())) <= 0 ){
	 				logger.debug("金额足以全额支付");
	 				throw new BizException(100, "金额足以全额支付");
	 			}else{
	 				havepay = tpay.getRemainAmt();
	 				
	 				for(int i = 0; i < tdList.size(); i++ ){
	 					tpayDetail = tdList.get(i);
	 					tpay = tpayList.get(i);
	 					
	 					tpayDetail.setPayAmt(havepay);
	 					tpay.setRemainAmt(tpay.getRemainAmt().subtract(havepay));
	 					tpayDetail.setPayStatus(PayStatus.HAVE_PAY);
	 					tpay.setPayStatus(PayStatus.HAVE_PAY);
	 					tpayDetail.setIuser(tuser.getIuser());
	 					tpayDetail.setUserName(tuser.getTuserSub().getName());
	 					this.tpayDetailService.update(tpayDetail);
	 					this.tpayService.modify(tpay);
	 				}
	 				
	 				// 凭证 部分支付
	 				if(tpayNotify.getHavepayAmt() != null){
	 					tpayNotify.setHavepayAmt(tpayNotify.getHavepayAmt().add(havepay));
	 				}else{
	 					tpayNotify.setHavepayAmt(havepay);
	 				}
	 			}
	 			logger.info("保证金补交部分支付");
	 			//添加跟踪信息
	 			StringBuffer memo = new StringBuffer("后台管理员 审核  保证金补交(账单编号："+tpayNotify.getPayNotifyNo());
	 			memo.append(") 通过，部分支付:").append(tpayNotify.getHavepayAmt()).append(" 元");
	 			tdetailTraceService.add4Tagmt(tagmt, tuser, memo.toString(), null, null);
	 		}
	 		break;
	 	default:
	 	}
	 	
	 	this.tpayNotifyService.update(tpayNotify);
	 	tlog.setData("审核后状态为"+tpayNotify.getPayNotifyStatus());
	 	tlogService.save(tlog);
        //支付审核通过取消邮件发送
//		//审核结果 发送邮件通知 客户
//		adminMailService.sendPayAuditMailToCust(tpayNotify, tuser);
//		if(tpayNotify.getPayNotifyStatus() == PayStatus.HAVE_PAY){
//			//如果已支付，向销管发送邮件提醒
//			adminMailService.sendPayAuditMail(tpayNotify, tuser);
//		}
		return tpayNotify;

	}

	/**
	 * 付款通知书的 付款审核 不通过
	 * @throws BizException 
	 */
	public TpayNotify payAuditNo(TpayNotify tpayNotify, String refuseReason, Tuser tuser) throws BizException {
		Tagmt tagmt = null;
		Tord tord = null;
		TlogisticsOrd tlogisticsOrd = null;
		Trenew trenew = null;
		//待审核的凭证
		Tpay tpay = null;
		if(tpayNotify == null){
			logger.debug("未找到付款通知书");
			throw new BizException(0, "未找到付款通知书");
		}
		if(tpayNotify.getPayNotifyStatus() != PayStatus.WAIT_AUDIT){
			logger.debug("付款通知书状态异常，不是待审核状态");
			throw new BizException(0, "付款通知书状态异常，不是待审核状态");
		}
		
		switch (tpayNotify.getPayType()) {
		case BAIL:
			// 协议付款审核状态不变
			tagmt= this.tagmtService.findById(tpayNotify.getIfk());
			
			//添加跟踪信息
			StringBuffer memo = new StringBuffer("后台管理员 审核  保证金支付(账单编号："+tpayNotify.getPayNotifyNo());
			memo.append(") 不通过， 理由：").append(refuseReason);
			tdetailTraceService.add4Tagmt(tagmt, tuser, memo.toString(), null, null);
			break;

		case LOGISTICS:
			tlogisticsOrd = this.tlogisticsService.findTlogisticsOrdById(tpayNotify.getIfk());
			this.tlogisticsOrdPayAudit(tlogisticsOrd,  PayStatus.NO_PASS, tuser);
			tord = this.tordService.find(tlogisticsOrd.getIord());
			tagmt = this.tagmtService.findById(tord.getIagmt());
			//添加跟踪信息
			StringBuffer memo2 = new StringBuffer("后台管理员 审核  物流单费用支付(账单编号："+tpayNotify.getPayNotifyNo());
			memo2.append(") 不通过， 理由：").append(refuseReason);
			tdetailTraceService.add4TlogisticsOrd(tlogisticsOrd, tuser, memo2.toString(), null, null);
			break;
		case ORDER:
			tord = this.tordService.find(tpayNotify.getIfk());
			tagmt= this.tagmtService.findById(tord.getIagmt());
			if(tpayNotify.getHavepayAmt() != null && tpayNotify.getHavepayAmt().compareTo(BigDecimal.ZERO) > 0){
				//如果 已支付过金额， 就为部分支付
				this.ordPayAudit(tord, PayStatus.PART_PAY);
			}else{
				this.ordPayAudit(tord, PayStatus.NO_PASS);
			}
			//添加跟踪信息
			StringBuffer memo3 = new StringBuffer("后台管理员 审核  订单货款支付(账单编号："+tpayNotify.getPayNotifyNo());
			memo3.append(") 不通过， 理由：").append(refuseReason);
			tdetailTraceService.add4Tord(tord, tuser, memo3.toString(), null, null);
			break;
		case WAREHOUSE:
			TwareHouse twareHouse = this.twareHouseService.find(tpayNotify.getIfk());
			if(tpayNotify.getHavepayAmt() != null && tpayNotify.getHavepayAmt().compareTo(BigDecimal.ZERO) > 0){
				twareHouse.setPayStatus(PayStatus.PART_PAY);
			}else{
				twareHouse.setPayStatus(PayStatus.NO_PASS);
			}
			this.twareHouseService.update(twareHouse);
			break;
		case RENEW_AMT:
			trenew = this.trenewService.findById(tpayNotify.getIfk());
			if(tpayNotify.getHavepayAmt() != null && tpayNotify.getHavepayAmt().compareTo(BigDecimal.ZERO) > 0){
				//如果 已支付过金额， 就为部分支付
				this.renewPayAudit(trenew, PayStatus.PART_PAY);
			}else{
				this.renewPayAudit(trenew, PayStatus.NO_PASS);
			}	
			break;
		case BAIL_SUPPLEMENT:
			// 协议付款审核状态不变
			tagmt= this.tagmtService.findById(tpayNotify.getIfk());
			
			//添加跟踪信息
			StringBuffer memo4 = new StringBuffer("后台管理员 审核  保证金补交(账单编号："+tpayNotify.getPayNotifyNo());
			memo4.append(") 不通过， 理由：").append(refuseReason);
			tdetailTraceService.add4Tagmt(tagmt, tuser, memo4.toString(), null, null);
			break;
		default:
		}
		 //添加 操作日志
		Tlog tlog = new Tlog();
		tlog.setIfk(tuser.getIuser());
		tlog.setLogType(LogType.USER);
		tlog.setOperType(OperType.PAYNOTIFY_MGR);
		StringBuffer sb = new StringBuffer();
		sb.append("用户审核付款通知书,编号:").append(tpayNotify.getPayNotifyNo()).append("，类型").append(tpayNotify.getPayType()).append(",id:").append(tpayNotify.getIfk());
		tlog.setMemo(sb.toString());
		tlog.setPreData("审核前状态为"+tpayNotify.getPayNotifyStatus());
		
		// 变更 付款通知书菜单
		tpayNotify.setPayNotifyStatus(PayStatus.NO_PASS);
		this.tpayNotifyService.update(tpayNotify);
		List<TpayDetail> tdList = this.tpayDetailService.findSelectWaitAudit(tpayNotify.getIfk(), tpayNotify.getPayType());
		// 如果详单已支付或不通过，就不获取
		if(CollectionUtils.isEmpty(tdList)){
			throw new BizException(0, "数据异常：不存在待审核的账单");
		}
		for(TpayDetail td : tdList){
			td.setPayStatus(PayStatus.NO_PASS);
			td.setIuser(tuser.getIuser());
			td.setUserName(tuser.getTuserSub().getName());
			td.setRefuseReason(refuseReason);
			this.tpayDetailService.update(td);

			tpay = this.tpayService.find(td.getIpay());
			tpay.setPayStatus(PayStatus.NO_PASS);
			this.tpayService.modify(tpay);
			
			//把不通过的保证金， 返回给协议
			if(tpay.getPayMethod() == PayMethod.DEPOSIT){
				tagmt.setUsedDeposit(tagmt.getUsedDeposit().subtract(tpay.getAmt()));
				tagmt.setRemainDeposit(tagmt.getRemainDeposit().add(tpay.getAmt()));
				tagmtService.modifyTamgtDeposit(tagmt);
			}
			
			// 审核不通过时，使用多余保证金支付的金额要退返
			if(tpay.getPayMethod() == PayMethod.REDUNDANT_DEPOSIT){
				tagmt.setRedundantDeposit(tagmt.getRedundantDeposit().add(tpay.getAmt()));
				tagmtService.modifyTamgtDeposit(tagmt);
			}
		}
		if(tpayNotify.getPayType() == PayType.BAIL 
				|| tpayNotify.getPayType() == PayType.ORDER 
				|| tpayNotify.getPayType() == PayType.BAIL_SUPPLEMENT){
			//审核不通过 发送邮件通知 客户,发送不通过理由refuseReason给客户
			adminMailService.sendPayAuditMailToCust(tpayNotify, tuser);
		}
		
		tlog.setData("审核后状态为"+tpayNotify.getPayNotifyStatus());
		tlogService.save(tlog);
		
		return tpayNotify;

	}

	/**
	 * 查询 产品 列表（分页）
	 * 
	 * @param tpayNotifyConfition
	 * @param page
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List findListByInfo(Page page, String payType,
			String payNotifyStatus, String startDate, String endDate, String name) {
		TcustRegCondition trc = new TcustRegCondition();
		trc.setName(name);
		String icusts = tcustRegService.queryIcustsByName(trc);
		if(StringUtils.isNotBlank(name) && StringUtils.isBlank(icusts)){
        	return null;
        }
		List list = tpayNotifyService.findListByInfo(page, payType,
				payNotifyStatus, startDate, endDate, icusts);

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] listStr = (Object[]) list.get(i);

				if (PayType.BAIL.toString().equals(listStr[3])) {
					Tagmt tagmt = tagmtService.findById(Long.valueOf(String
							.valueOf(listStr[2])));
					if (tagmt != null) {
						// 金额
						listStr[9] = tagmt.getDeposit();
						listStr[10] = tagmt.getAgmtNo();
						listStr[11] = null;
						listStr[12] = null;

					} else {
						listStr[9] = null;
						listStr[10] = null;
						listStr[11] = null;
						listStr[12] = null;
					}

				} else if (PayType.ORDER.toString().equals(listStr[3])) {
					Tord tord = tordService.find(Long.valueOf(String
							.valueOf(listStr[2])));
					if (tord != null) {
						// 金额
						listStr[9] = tord.getAmt();

						listStr[10] = tagmtService.findById(tord.getIagmt())
								.getAgmtNo();
						listStr[11] = tord.getOrdNo();
						listStr[12] = null;

					} else {
						listStr[9] = null;
						listStr[10] = null;
						listStr[11] = null;
						listStr[12] = null;
					}

				} else if (PayType.LOGISTICS.toString().equals(listStr[3])) {
					TlogisticsOrd tlogisticsOrd  = tlogisticsService.findTlogisticsOrdById(Long.valueOf(String
							.valueOf(listStr[2])));
					Tord tord = tordService.find(tlogisticsOrd.getIord());
					
					listStr[9] = tlogisticsOrd.getLogisticsFreight();
					listStr[10] = tagmtService.findById(tord.getIagmt()).getAgmtNo();
					listStr[11] = tord.getOrdNo();
					listStr[12] = tlogisticsOrd.getInnerOrdno();

				} else if (PayType.WAREHOUSE.toString().equals(listStr[3])) {
					TwareHouse twareHouse = this.twareHouseService.find(Long.valueOf(String.valueOf(listStr[2])));
					Tagmt tagmt = tagmtService.findById(twareHouse.getIagmt());
					listStr[9] = twareHouse.getAmt();
					listStr[10] = tagmt.getAgmtNo();
					listStr[11] = null;
					listStr[12] = null;
					
				} else if (PayType.RENEW_AMT.toString().equals(listStr[3])) {
					Trenew trenew = this.trenewService.findById(Long.valueOf(String.valueOf(listStr[2])));
					listStr[9] = trenew.getRenewAmt();
					listStr[10] = null;
					listStr[11] = null;
					listStr[12] = null;
				} else if (PayType.BAIL_SUPPLEMENT.toString().equals(listStr[3])) {
					Tagmt tagmt = tagmtService.findById(Long.valueOf(String.valueOf(listStr[2])));
					listStr[9] = tagmt.getRedundantDeposit().negate(); //多余的保证金是负值，代表需要补交。取反
					listStr[10] = tagmt.getAgmtNo();
					listStr[11] = null;
					listStr[12] = null;
				}
			}
		}

		return list;
	}
	
	/**
	 * 撤销通知书
	 * @return
	 */
	public void revokedPayNotify(Long ipayNotify){
		TpayNotify tpayNotify = this.tpayNotifyService.find(ipayNotify);
		tpayNotify.setPayNotifyStatus(PayStatus.REVOKED);
		tpayNotifyService.update(tpayNotify);
	}
	
	/**
	 * 查询 是否 撤销 协议
	 * @param id
	 * @return
	 */
	public String validateDeleteAgmt(Long id){
		String result = "";
		Tagmt tagmt = tagmtService.findById(id);
		TpayNotify tpayNotify = tpayNotifyService.findByifkAndPayType(tagmt.getIagmt(), PayType.BAIL);

		//协议下有订单不允许撤销
	    if(tordService.queryTordByAgmtId(tagmt.getIagmt())){
	    	
	    //未产生金额交易
	    }else if(tagmt.getAgmtStatus()== AgmtStatus.AGMT_SUBMIT || tpayNotify == null || 
		    (tagmt.getAgmtStatus() == AgmtStatus.AGMT_QUOTA_CONFIRM && tpayNotify.getPayNotifyStatus() == PayStatus.WAIT_PAY) ){
			result = "N";
	    }else if(tpayNotify.getPayNotifyStatus() == PayStatus.WAIT_PAY && 
	    		(tpayNotify.getHavepayAmt() == null || tpayNotify.getHavepayAmt().compareTo(BigDecimal.ZERO) ==0)){
	    	result = "N";
		}else if(tagmt.getAgmtStatus() == AgmtStatus.AGMT_QUOTA_CONFIRM && tpayNotify.getPayNotifyStatus() == PayStatus.WAIT_AUDIT){
			result = "Y";
		}else if(tagmt.getAgmtStatus() == AgmtStatus.PAY_PASS){
			result = tagmt.getRemainDeposit().toString();
		}else if( tagmt.getAgmtStatus() == AgmtStatus.CONFIRM){
			result = tagmt.getRemainDeposit().toString();
		}
	    
		return result;
		
	}
	

	public TpayNotify queryTpayNotifyByIpayNotify(Long id) {

		return this.tpayNotifyService.find(id);
	}

	public void addTpayNotify(TpayNotify tpayNotify) {
		this.tpayNotifyService.save(tpayNotify);
	}

	/**
	 * 协议付款审核
	 * 
	 * @param tagmt
	 * @param agmtStatus
	 * @param agmtDetailStatus
	 * @throws BizException 
	 */

	public void agmtPayAudit(Tagmt tagmt, AgmtStatus agmtStatus,
			AgmtDetailStatus agmtDetailStatus) throws BizException {
		
		if(tagmt.getAgmtStatus() != AgmtStatus.PAY){
			throw new BizException(0, "协议状态异常，不是已支付待审核！");
		}
		
		this.tagmtService.checkTagmt(tagmt, agmtStatus);

		// 更新协议明细状态
		List<TagmtDetail> tagmtDetailList = tagmtService.findTagmtDetial(tagmt);
		if (tagmtDetailList != null) {
			for (TagmtDetail tagmtDetail : tagmtDetailList) {
				tagmtService.checkTagmtDetail(tagmtDetail, agmtDetailStatus);
			}
		}

	}

	/**
	 * 
	 * @param tord
	 * @param ordStatus
	 * @throws BizException 
	 */
	public Tord ordPayAudit(Tord tord, PayStatus payStatus) throws BizException {
		if(!(tord.getPayStatus() == PayStatus.WAIT_AUDIT || tord.getPayStatus() == PayStatus.PART_PAY)){
			throw new BizException(0, "订单支付状态异常！");
		}

		this.tordService.auditTord(tord, payStatus);
		
		return tord;

	}
	
	public TlogisticsOrd tlogisticsOrdPayAudit(TlogisticsOrd tlogisticsOrd, PayStatus payStatus, Tuser tuser) throws BizException {
		if(!(tlogisticsOrd.getPayStatus() == PayStatus.WAIT_AUDIT || tlogisticsOrd.getPayStatus() == PayStatus.PART_PAY)){
			throw new BizException(0, "物流单支付状态异常！");
		}
		
		tlogisticsOrd.setPayStatus(payStatus);
		tlogisticsOrd.setIuser(tuser.getIuser());
		tlogisticsOrd.setUsername(tuser.getTuserSub().getName());
		this.tlogisticsService.updateTlogisticsOrd(tlogisticsOrd);
		
		return tlogisticsOrd;
	}
	
	public TwareHouse twareHousePayAudit(TwareHouse twareHouse, PayStatus payStatus) throws BizException {
		if(twareHouse.getPayStatus() != PayStatus.WAIT_AUDIT){
			throw new BizException(0, "仓管费支付状态异常，不是待审核！");
		}
		
		twareHouse.setPayStatus(payStatus);
		this.twareHouseService.update(twareHouse);
		
		return twareHouse;
	}
	/**
	 * 
	* @Description: 审核续保支付状态
	* @author chenwenjing    
	* @date 2014-10-28 下午3:03:40
	 */
	public void renewPayAudit(Trenew trenew, PayStatus payStatus) throws BizException {
		
		if(!(trenew.getPayStatus() == PayStatus.WAIT_AUDIT||trenew.getPayStatus()==PayStatus.PART_PAY)){
			throw new BizException(0, "续保支付状态异常，不是已支付待审核！");
		}
		
		this.trenewService.auditTrenew(trenew, payStatus);
		if(payStatus==PayStatus.HAVE_PAY){
			List<TrenewDetail> detailList = this.trenewDetailService.findListByTrenewId(trenew.getIrenew());
			for(TrenewDetail detail:detailList){//更新主表
				ERPMaintenance em = this.maintenanceManageService.getERPMaintenanceById(detail.getIerpMaintenance());
				Date now = new Date();
				em.setPurchaseDate(now);
				this.maintenanceManageService.updateERPMaintenance(em);
			}
		}
	}

	/**
	 * 查询 已审核过的 凭证
	 * 
	 * @param ifk
	 * @return
	 */
	public List<Tpay> queryAuditedPayByfk(Long ifk, PayType payType) {
		List<Tpay> tpayList = null;
		List<TpayDetail> tpayDetailList = this.tpayDetailService.findSelect(
				ifk, payType);
		Map<String, String> payMap = new TreeMap<String, String>();
		
		//变更 payDetail的支付状态
		if(tpayDetailList != null && tpayDetailList.size() > 0){
			for(TpayDetail td :tpayDetailList){
				//对已支付过的详单不重新操作
				if(td.getPayStatus()==PayStatus.HAVE_PAY || td.getPayStatus()==PayStatus.NO_PASS){
					payMap.put(""+td.getIpay(),td.getIpayDetail().toString());
				}
			}
		}
		// 变更 pay的支付状态
		Set<String> key = payMap.keySet();
		if (key != null && key.size() > 0) {
			tpayList = new ArrayList<Tpay>();
			String temp = null;
			Tpay tpay = null;
			TpayDetail tpayDetail = null;
			for (Iterator<String> it = key.iterator(); it.hasNext();) {
				temp = it.next();
				tpay = this.tpayService.find(Long.valueOf(temp));
				tpayDetail = this.tpayDetailService.find(Long.valueOf(payMap.get(temp)));
				tpay.setRefuseReason(tpayDetail.getRefuseReason());
				tpay.setPayAmt(tpayDetail.getPayAmt());
				tpayList.add(tpay);
			}
		}

		return tpayList;
	}

	/**
	 * 查询 待审核的凭证
	 * 
	 * @param
	 * @return
	 */
	public List<Tpay> queryWaitAuditPayByfk(Long ifk, PayType payType) {
		List<Tpay> tpayList = null;
		List<TpayDetail> tpayDetailList = this.tpayDetailService.findSelect(
				ifk, payType);
		Map<String, String> payMap = new TreeMap<String, String>();
		
		//变更 payDetail的支付状态
		if(tpayDetailList != null && tpayDetailList.size() > 0){
			for(TpayDetail td :tpayDetailList){
				//待审核
				if(td.getPayStatus()==PayStatus.WAIT_AUDIT){
					payMap.put(""+td.getIpay(),td.getRefuseReason());
				}
			}
		}
		// 变更 pay的支付状态
		Set<String> key = payMap.keySet();
		if (key != null && key.size() > 0) {
			tpayList = new ArrayList<Tpay>();
			String temp = null;
			for (Iterator<String> it = key.iterator(); it.hasNext();) {
				temp = it.next();
				Tpay tpay = this.tpayService.find(Long.valueOf(temp));
				tpay.setRefuseReason(payMap.get(temp));
				tpayList.add(tpay);
			}
		}
		return tpayList;
	}
	
	
	public TlogisticsOrd findTlogisticsOrdById(Long ilogisticsOrd){
		return tlogisticsService.findTlogisticsOrdById(ilogisticsOrd);
	}
	
	public TcustReg findTcustReg(Long icust){
		return tcustRegService.findByIcust(icust);
	}
	
	public Tord queryTordByIord(Long iord){
		return tordService.find(iord);
	}
	
	public TwareHouse queryTwareHouseById(Long iwareHouse){
		return twareHouseService.find(iwareHouse);
	}
	
	public List<TwareHouseDetail> queryTwareHouseDetailListById(Long iwareHouse){
		return twareHouseDetailService.queryListByiwareHouse(iwareHouse);
	}
	
	public Tagmt findTagmtByIagmt(Long iagmt){
		return tagmtService.findTagmtById(iagmt);
	}
	
	@SuppressWarnings("rawtypes")
	public List findPdtByIagmt(Long iagmt){
		return tagmtService.findPdtByIagmt(iagmt);
	}

	public List<TpayNotify> findBySelect(TpayNotify notify) {
		return tpayNotifyService.findBySelect(notify);
	}
	public Trenew findTrenewByIrenew(Long irenew){
		return trenewService.findById(irenew);
	}
}