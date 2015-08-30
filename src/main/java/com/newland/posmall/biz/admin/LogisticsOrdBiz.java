package com.newland.posmall.biz.admin;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Order;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.entity.Tuser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.newland.posmall.base.exception.BizException;
import com.newland.posmall.base.service.LogisticsDownLoadService;
import com.newland.posmall.base.service.TagmtService;
import com.newland.posmall.base.service.TcustRegService;
import com.newland.posmall.base.service.TcustService;
import com.newland.posmall.base.service.TcustStockService;
import com.newland.posmall.base.service.TdetailTraceService;
import com.newland.posmall.base.service.TlogService;
import com.newland.posmall.base.service.TlogisticsComService;
import com.newland.posmall.base.service.TlogisticsDetailService;
import com.newland.posmall.base.service.TlogisticsOrdAddrService;
import com.newland.posmall.base.service.TlogisticsService;
import com.newland.posmall.base.service.TordDetailPdtService;
import com.newland.posmall.base.service.TordDetailService;
import com.newland.posmall.base.service.TordService;
import com.newland.posmall.base.service.TpayDetailService;
import com.newland.posmall.base.service.TpayNotifyService;
import com.newland.posmall.base.service.TpayService;
import com.newland.posmall.base.service.TpdtNoService;
import com.newland.posmall.base.service.TpdtService;
import com.newland.posmall.bean.backmanage.TuserSub;
import com.newland.posmall.bean.basebusi.LogisticsDownLoad;
import com.newland.posmall.bean.basebusi.Tagmt;
import com.newland.posmall.bean.basebusi.TlogisticsCom;
import com.newland.posmall.bean.basebusi.TlogisticsDetail;
import com.newland.posmall.bean.basebusi.TlogisticsOrd;
import com.newland.posmall.bean.basebusi.TlogisticsOrdAddr;
import com.newland.posmall.bean.basebusi.Tord;
import com.newland.posmall.bean.basebusi.TordDetail;
import com.newland.posmall.bean.basebusi.TordDetailPdt;
import com.newland.posmall.bean.basebusi.Tpay;
import com.newland.posmall.bean.basebusi.TpayDetail;
import com.newland.posmall.bean.basebusi.TpayNotify;
import com.newland.posmall.bean.basebusi.TpdtHis;
import com.newland.posmall.bean.basebusi.TpdtNo;
import com.newland.posmall.bean.basebusi.condition.LogisticsDownLoadCondition;
import com.newland.posmall.bean.basebusi.condition.TcustRegCondition;
import com.newland.posmall.bean.basebusi.condition.TlogisticsOrdAddrCondition;
import com.newland.posmall.bean.basebusi.condition.TpdtNoCondition;
import com.newland.posmall.bean.common.Tlog;
import com.newland.posmall.bean.customer.Tcust;
import com.newland.posmall.bean.customer.TcustReg;
import com.newland.posmall.bean.customer.condition.TlogisticsOrdCondition;
import com.newland.posmall.bean.dict.DictType;
import com.newland.posmall.bean.dict.LogType;
import com.newland.posmall.bean.dict.LogisticsOrdStatus;
import com.newland.posmall.bean.dict.OperType;
import com.newland.posmall.bean.dict.PayMethod;
import com.newland.posmall.bean.dict.PayStatus;
import com.newland.posmall.bean.dict.PayType;
import com.newland.posmall.bean.dict.YesNoType;
import com.newland.posmall.biz.common.MapBiz;
import com.newland.posmall.controller.cust.model.LogisticsDown4Page;
import com.newland.posmall.controller.cust.model.LogisticsOrd4Page;
import com.newland.posmall.controller.cust.model.LogisticsOrdAddr4Page;
import com.newland.posmall.controller.cust.model.LogisticsOrderWithMultipleAddrs4Page;


@Service
@Transactional
public class LogisticsOrdBiz {
	
	private static final Logger logger = LoggerFactory.getLogger(LogisticsOrdBiz.class);
	
	@Resource
	private TlogisticsService tlogisticsService;
	
	@Resource	private TcustRegService tcustRegService;
	
	@Resource	private TcustService tcustService;
	
	@Resource
	private TpayNotifyService tpayNotifyService;
	
	@Resource
	private TpayService tpayService;
	
	@Resource
	private TlogService tlogService;
	
	@Resource
	private TordDetailService tordDetailService;
	
	@Resource
	private TpayDetailService tpayDetailService;
	
	@Resource
	private TordService tordService;
	
	@Resource
	private TordDetailPdtService tordDetailPdtService;
	
	@Resource
	private TlogisticsDetailService tlogisticsDetailService;
	
	@Resource
	private TagmtService tagmtService;
	
	@Resource
	private TlogisticsOrdAddrService tlogisticsOrdAddrService;
	
	@Resource
	private LogisticsDownLoadService logisticsDownLoadService;
	
	@Resource
	private TpdtService tpdtService;
	
	@Resource
	private TlogisticsComService tlogisticsComService;
	
	@Resource
	private TcustStockService tcustStockService;
	
	@Resource
	private AdminMailService adminMailService;
	
	@Resource
	private TdetailTraceService tdetailTraceService;
	
	@Resource
	private MapBiz mapBiz;
	
	@Resource
	private TpdtNoService tpdtNoService;
	
	public Tcust genTcustByTlogisticsOrd(TlogisticsOrd tlogisticsOrd){
		return this.tcustService.find(tlogisticsOrd.getIcust());
	}
	
	/**
	 * 审核物流单(单地址)
	 * @param tlogisticsOrd
	 * @param tuser
	 * @return
	 * @throws BizException
	 */
	public TlogisticsOrd auditTlogisticsOrd(TlogisticsOrd tlogisticsOrd, LogisticsOrd4Page logisticsOrd4Page, BigDecimal price, Tuser tuser,TuserSub tuserSub) throws BizException{
		
		if(tlogisticsOrd.getLogisticsOrdStatus() != LogisticsOrdStatus.WAIT_AUDIT){
			logger.debug("物流单状态异常,不是待审核状态");
			throw new BizException(0, "物流单状态异常,不是待审核状态");
		}
		
		tlogisticsOrd.setLogisticsOrdStatus(LogisticsOrdStatus.HAVE_AUDIT);
		tlogisticsOrd.setIuser(tuser.getIuser());
		tlogisticsOrd.setUsername(tuser.getTuserSub().getName());
		
		//
		List<LogisticsOrdAddr4Page>  logisticsOrdAddr4Page = logisticsOrd4Page.getLogisticsOrdAddr4PageList();
		if(CollectionUtils.isEmpty(logisticsOrdAddr4Page)){
			logger.debug("物流单产品地址数据为空");
			throw new BizException(0, "物流单产品地址数据为空");
		}
		BigDecimal oldPrice = logisticsOrdAddr4Page.get(0).getTlogisticsOrdAddr().getPrice();
		//如果单价不同,则修改物流单的运费单价
		if(oldPrice.compareTo(price) != 0){
			BigDecimal amt = new BigDecimal(0);
			TlogisticsOrdAddr tlogisticsOrdAddr;
			for(LogisticsOrdAddr4Page  temp : logisticsOrdAddr4Page){
				tlogisticsOrdAddr = temp.getTlogisticsOrdAddr();
				tlogisticsOrdAddr.setPrice(price);
				this.tlogisticsOrdAddrService.update(tlogisticsOrdAddr);
				amt = amt.add(price.multiply(new BigDecimal(tlogisticsOrdAddr.getNum().toString())).setScale(2,
						RoundingMode.HALF_UP));
			}
			//修改 物流费用
			tlogisticsOrd.setLogisticsFreight(amt);
		}
	
		
		TcustReg tcustReg = this.tcustRegService.find(tlogisticsOrd.getIcust());
		//根据协议的保证金，生成pay ,同时修改通知书状态
		this.autoCreatTpayNotify(tlogisticsOrd, tcustReg, tuser);
	    
	    this.tlogisticsService.updateTlogisticsOrd(tlogisticsOrd);
		
	    //修改客户库存表
	    TlogisticsDetail ld = new TlogisticsDetail();
	    ld.setIlogisticsOrd(tlogisticsOrd.getIlogisticsOrd());
		List<TlogisticsDetail> tlogisticsDetailList = this.tlogisticsDetailService.findListByDetail(ld);
		for(TlogisticsDetail tld:tlogisticsDetailList){
			Long ipdtHis = tld.getIpdtHis();
			Long ipdt = this.tpdtService.findTpdtHisById(ipdtHis).getIpdt();
			int num = tld.getPdtNum();
			this.tcustStockService.shipment(tlogisticsOrd.getIcust(), ipdt, num, tlogisticsOrd.getIlogisticsOrd(), tuser.getIuser(), tuserSub.getName(),tld,ipdtHis);
		}
		
		// 日志记录
		Tlog tlog = new Tlog();
		tlog.setIfk(tuser.getIuser());
		tlog.setLogType(LogType.USER);
		tlog.setOperType(OperType.LOGISTICS_MGR);
		tlog.setMemo("后台管理员审核物流单");
		tlog.setPreData("");
		tlog.setData("管理员" + tuser.getTuserSub().getName() + "审核物流单ID为"+tlogisticsOrd.getIlogisticsOrd());
		this.tlogService.save(tlog);
		
		return tlogisticsOrd;
	}
	
	/**
	 * 审核物流单(多地址)
	 * @param tlogisticsOrd
	 * @param tuse	 * @return
	 * @throws BizException
	 */
	public TlogisticsOrd auditTlogisticsOrdMore(TlogisticsOrd tlogisticsOrd, LogisticsOrderWithMultipleAddrs4Page multipleAddrs4Page, Tuser tuser,TuserSub tuserSub) throws BizException{
		
		if(tlogisticsOrd.getLogisticsOrdStatus() != LogisticsOrdStatus.WAIT_AUDIT){
			logger.debug("物流单状态异常,不是待审核状态");
			throw new BizException(0, "物流单状态异常,不是待审核状态");
		}
		
		tlogisticsOrd.setLogisticsOrdStatus(LogisticsOrdStatus.HAVE_AUDIT);
		tlogisticsOrd.setIuser(tuser.getIuser());
		tlogisticsOrd.setUsername(tuser.getTuserSub().getName());
		
		//
		List<LogisticsOrdAddr4Page>  logisticsOrdAddr4Page = multipleAddrs4Page.getAddrs();
		if(CollectionUtils.isEmpty(logisticsOrdAddr4Page)){
			logger.debug("物流单产品地址数据为空");
			throw new BizException(0, "物流单产品地址数据为空");
		}
		//物流费用计算
		BigDecimal amt = new BigDecimal(0);
		TlogisticsOrdAddr tlogisticsOrdAddrNew = null;
		TlogisticsOrdAddr tlogisticsOrdAddrModify = null;
		for(LogisticsOrdAddr4Page  temp : logisticsOrdAddr4Page){
			tlogisticsOrdAddrModify = temp.getTlogisticsOrdAddr();
			tlogisticsOrdAddrNew = this.tlogisticsOrdAddrService.getTlogisticsOrdAddrById(tlogisticsOrdAddrModify.getIlogisticsOrdAddr());
			tlogisticsOrdAddrNew.setPrice(tlogisticsOrdAddrModify.getPrice());
			tlogisticsOrdAddrNew.setIlogisticsCom(tlogisticsOrdAddrModify.getIlogisticsCom());
			tlogisticsOrdAddrNew.setLogisticsComName(tlogisticsOrdAddrModify.getLogisticsComName());
			this.tlogisticsOrdAddrService.update(tlogisticsOrdAddrNew);
			amt = amt.add(tlogisticsOrdAddrNew.getPrice().multiply(new BigDecimal(tlogisticsOrdAddrNew.getNum().toString())).setScale(2,
					RoundingMode.HALF_UP));
		}
		//修改 物流费用
		tlogisticsOrd.setLogisticsFreight(amt);
	
		
		TcustReg tcustReg = this.tcustRegService.find(tlogisticsOrd.getIcust());
		//根据协议的保证金，生成pay ,同时修改通知书状态
		this.autoCreatTpayNotify(tlogisticsOrd, tcustReg, tuser);
	    
	    this.tlogisticsService.updateTlogisticsOrd(tlogisticsOrd);
		
	    //修改库存变化
	    TlogisticsDetail ld = new TlogisticsDetail();
	    ld.setIlogisticsOrd(tlogisticsOrd.getIlogisticsOrd());
		List<TlogisticsDetail> tlogisticsDetailList = this.tlogisticsDetailService.findListByDetail(ld);
		for(TlogisticsDetail tld:tlogisticsDetailList){
			Long ipdtHis = tld.getIpdtHis();
			Long ipdt = this.tpdtService.findTpdtHisById(ipdtHis).getIpdt();
			int num = tld.getPdtNum();
			this.tcustStockService.shipment(tlogisticsOrd.getIcust(), ipdt, num, tlogisticsOrd.getIlogisticsOrd(), tuser.getIuser(), tuserSub.getName(),tld,ipdtHis);
		}
	    
		// 日志记录
		Tlog tlog = new Tlog();
		tlog.setIfk(tuser.getIuser());
		tlog.setLogType(LogType.USER);
		tlog.setOperType(OperType.LOGISTICS_MGR);
		tlog.setMemo("后台管理员审核物流单");
		tlog.setPreData("");
		tlog.setData("管理员" + tuser.getTuserSub().getName() + "审核物流单ID为"+tlogisticsOrd.getIlogisticsOrd());
		this.tlogService.save(tlog);
		
		//添加跟踪信息
		tdetailTraceService.add4TlogisticsOrd(tlogisticsOrd, tuser, "后台管理员 审核物流单("+genLogisticsTraceInfo(tlogisticsOrd)+")", null, null);
		
		return tlogisticsOrd;
	}
	
	/**
	 * 自动生成通知书
	 * @param tlogisticsOrd
	 * @param tcustReg
	 * @param tuser
	 * @throws BizException 
	 */
	public void autoCreatTpayNotify(TlogisticsOrd tlogisticsOrd , TcustReg tcustReg, Tuser tuser) throws BizException{
		Tord tord = tordService.find(tlogisticsOrd.getIord());
	    Tagmt tagmt = tagmtService.findById(tord.getIagmt());
	    if(tlogisticsOrd.getLogisticsFreight() != null && tlogisticsOrd.getLogisticsFreight().compareTo(BigDecimal.ZERO) > 0){
	    	
	    	// 如果付费，就生成通知书
			TpayNotify tpayNotify = new TpayNotify();
			tpayNotify.setIcust(tlogisticsOrd.getIcust());
			tpayNotify.setPayType(PayType.LOGISTICS);
			tpayNotify.setIfk(tlogisticsOrd.getIlogisticsOrd());
			tpayNotify.setIhisFk(tlogisticsOrd.getIlogisticsOrd());
			tpayNotify.setMemo("客户物流单支付通知书");
			tpayNotify.setPayNotifyStatus(PayStatus.WAIT_PAY);
			this.tpayNotifyService.save(tpayNotify);
	    	
	    	if(tagmt.getRemainDeposit().compareTo(BigDecimal.ZERO)==0){
	    		 //剩余保证金为0，不能自动扣费
	    		tlogisticsOrd.setPayStatus(PayStatus.WAIT_PAY);
	    	}else if(tagmt.getRemainDeposit().compareTo(tlogisticsOrd.getLogisticsFreight()) >= 0){
	    		//如果剩余保证大于 物流费用，用保证金 全额支付
	    		BigDecimal payAmt = tlogisticsOrd.getLogisticsFreight();
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
	    		tpayDetail.setPayType(PayType.LOGISTICS);
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
	    		
	    		tlogisticsOrd.setPayStatus(PayStatus.HAVE_PAY);
	    		adminMailService.sendLogisticsMail(tcustReg, tagmt, tpayNotify, tlogisticsOrd);
	    		
	    	}else if(tagmt.getRemainDeposit().compareTo(tlogisticsOrd.getLogisticsFreight()) < 0){
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
	    		tpayDetail.setPayType(PayType.LOGISTICS);
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
	    		
	    		tlogisticsOrd.setPayStatus(PayStatus.PART_PAY);
	    		adminMailService.sendLogisticsMail(tcustReg, tagmt, tpayNotify, tlogisticsOrd);
	    	}
	    	
	    }else{
	    	//如果免费，就不生成通知书
	    	logger.info("物流单ID为"+tlogisticsOrd.getIlogisticsOrd()+"物流费用为0，不生成通知书，支付状态直接置为已支付");
	    	tlogisticsOrd.setPayStatus(PayStatus.HAVE_PAY);
	    }
	}
	
	/**
	 *  填写实际发货情况， 改为 已发货,送达
	 * @param tlogisticsOrd
	 * @param tuser
	 * @return
	 * @throws BizException 
	 */
	public TlogisticsOrd fillActual( TlogisticsOrd tlogisticsOrd,TlogisticsOrdAddr  tlogisticsOrdAddr, LogisticsOrd4Page logisticsOrd4Page, Tuser tuser) throws BizException{
		
		if(tlogisticsOrd == null){
			logger.debug("物流单不能为空");
			throw new BizException(0, "物流单不能为空");
		}
		
		if(!(tlogisticsOrd.getLogisticsOrdStatus() == LogisticsOrdStatus.HAVE_LIBRARY ||
				tlogisticsOrd.getLogisticsOrdStatus() == LogisticsOrdStatus.SHIPPED ||
				tlogisticsOrd.getLogisticsOrdStatus() == LogisticsOrdStatus.PARTIAL_DELIVERY)){
			logger.debug("填写实际发货情况,物流单状态异常,不是已出库之后的状态");
			throw new BizException(0, "物流单状态异常,不是已出库之后的状态");
		}
		if(logisticsOrd4Page == null || CollectionUtils.isEmpty(logisticsOrd4Page.getLogisticsOrdAddr4PageList())){
			logger.debug("物流单产品地址数据异常");
			throw new BizException(0, "物流单产品地址数据异常");
		}else{
			Long idAddr = logisticsOrd4Page.getLogisticsOrdAddr4PageList().get(0).getTlogisticsOrdAddr().getIlogisticsOrdAddr();
			Long id =this.tlogisticsOrdAddrService.getTlogisticsOrdAddrById(idAddr).getIlogisticsOrd();
			if(!tlogisticsOrd.getIlogisticsOrd().equals(id)){
				logger.debug("物流单数据异常");
				throw new BizException(0, "物流单数据异常");
			}
		}
		Integer realSumNum = 0;
		//填写实际出货信息
		TlogisticsOrdAddr  tlogisticsOrdAddrNew = null;
		for(LogisticsOrdAddr4Page t :  logisticsOrd4Page.getLogisticsOrdAddr4PageList()){
			tlogisticsOrdAddrNew  = this.tlogisticsOrdAddrService.getTlogisticsOrdAddrById(t.getTlogisticsOrdAddr().getIlogisticsOrdAddr());
			tlogisticsOrdAddrNew.setRealOutstockNum(t.getTlogisticsOrdAddr().getRealOutstockNum());
			tlogisticsOrdAddrNew.setRealOrdno(tlogisticsOrdAddr.getRealOrdno());
			tlogisticsOrdAddrNew.setOutstockNo(tlogisticsOrdAddr.getOutstockNo());
			tlogisticsOrdAddrNew.setRealDelivery(tlogisticsOrdAddr.getRealDelivery());
			tlogisticsOrdAddrNew.setRealArrival(tlogisticsOrdAddr.getRealArrival());
			tlogisticsOrdAddrNew.setIuser(tuser.getIuser());
			tlogisticsOrdAddrNew.setUsername(tuser.getTuserSub().getName());
			tlogisticsOrdAddrService.update(tlogisticsOrdAddrNew);
			realSumNum += tlogisticsOrdAddrNew.getRealOutstockNum();
		}
		
		tlogisticsOrd.setRealOutstockNum(realSumNum);
		if(tlogisticsOrdAddr.getRealArrival() == null){
			tlogisticsOrd.setLogisticsOrdStatus(LogisticsOrdStatus.SHIPPED);
		}else{
			tlogisticsOrd.setLogisticsOrdStatus(LogisticsOrdStatus.ALL_SERVICE);
		}
		tlogisticsOrd.setIuser(tuser.getIuser());
		tlogisticsOrd.setUsername(tuser.getTuserSub().getName());
		tlogisticsService.updateTlogisticsOrd(tlogisticsOrd);
		
		// 日志记录
		Tlog tlog = new Tlog();
		tlog.setIfk(tuser.getIuser());
		tlog.setLogType(LogType.USER);
		tlog.setOperType(OperType.LOGISTICS_MGR);
		tlog.setMemo("后台管理员填写物流计划的实际物流情况");
		tlog.setPreData("");
		tlog.setData("管理员" + tuser.getTuserSub().getName() + "实际物流情况");
		this.tlogService.save(tlog);

		return tlogisticsOrd;
	}
	
	public TlogisticsOrd fillActualMore( TlogisticsOrd tlogisticsOrd, LogisticsOrderWithMultipleAddrs4Page multipleAddrs4Page, Tuser tuser) throws BizException, ParseException{
		
		if(tlogisticsOrd == null){
			logger.debug("物流单不能为空");
			throw new BizException(0, "物流单不能为空");
		}
		
		if(!(tlogisticsOrd.getLogisticsOrdStatus() == LogisticsOrdStatus.HAVE_LIBRARY ||
				tlogisticsOrd.getLogisticsOrdStatus() == LogisticsOrdStatus.SHIPPED ||
				tlogisticsOrd.getLogisticsOrdStatus() == LogisticsOrdStatus.PARTIAL_DELIVERY)){
			logger.debug("填写实际发货情况,物流单状态异常,不是已出库之后的状态");
			throw new BizException(0, "物流单状态异常,不是已出库之后的状态");
		}
		if(multipleAddrs4Page == null || CollectionUtils.isEmpty(multipleAddrs4Page.getAddrs())){
			logger.debug("物流单产品地址数据异常");
			throw new BizException(0, "物流单产品地址数据异常");
		}else{
			Long idAddr = multipleAddrs4Page.getAddrs().get(0).getTlogisticsOrdAddr().getIlogisticsOrdAddr();
			Long id =this.tlogisticsOrdAddrService.getTlogisticsOrdAddrById(idAddr).getIlogisticsOrd();
			if(!tlogisticsOrd.getIlogisticsOrd().equals(id)){
				logger.debug("物流单数据异常");
				throw new BizException(0, "物流单数据异常");
			}
		}
		if(multipleAddrs4Page.getRealArrivalDateFormList() == null || multipleAddrs4Page.getRealDeliveryDateFormList() == null){
			logger.debug("实际发货日期和到达日期数据异常");
			throw new BizException(0, "实际发货日期和到达日期数据异常");
		}
		
		Boolean flag = true;
		boolean partFlag = false;
		//实际发货日期和到达日期数据转化类型
		List<LogisticsOrdAddr4Page> llist = multipleAddrs4Page.getAddrs();
		List<String> realDeliveryList = multipleAddrs4Page.getRealDeliveryDateFormList();
		List<String> realArrivalList = multipleAddrs4Page.getRealArrivalDateFormList();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(int i = 0; i < llist.size(); i++){
			TlogisticsOrdAddr old = llist.get(i).getTlogisticsOrdAddr();
			if(StringUtils.isNotBlank(realDeliveryList.get(i))){
				old.setRealDelivery(sdf.parse(realDeliveryList.get(i)));
			}
			if(StringUtils.isNotBlank(realArrivalList.get(i))){
				partFlag = true;
				flag = flag && true;
				old.setRealArrival(sdf.parse(realArrivalList.get(i)));
				if(StringUtils.isBlank(realDeliveryList.get(i))){
				    throw new BizException(0, "实际到达时间非空,实际发货时间不能为空");
				}
			}else{
				flag = false;
			}
			
		}
		
		Integer realSumNum = 0;
		//填写实际出货信息
		TlogisticsOrdAddr  tlogisticsOrdAddrNew = null;
		TlogisticsOrdAddr  tlogisticsOrdAddrModify = null;
	
		for(LogisticsOrdAddr4Page t :  multipleAddrs4Page.getAddrs()){
			tlogisticsOrdAddrModify = t.getTlogisticsOrdAddr();
			tlogisticsOrdAddrNew  = this.tlogisticsOrdAddrService.getTlogisticsOrdAddrById(tlogisticsOrdAddrModify.getIlogisticsOrdAddr());
			
			tlogisticsOrdAddrNew.setRealOutstockNum(t.getTlogisticsOrdAddr().getRealOutstockNum());
			tlogisticsOrdAddrNew.setRealOrdno(tlogisticsOrdAddrModify.getRealOrdno());
			tlogisticsOrdAddrNew.setOutstockNo(tlogisticsOrdAddrModify.getOutstockNo());
			tlogisticsOrdAddrNew.setRealDelivery(tlogisticsOrdAddrModify.getRealDelivery());
			tlogisticsOrdAddrNew.setRealArrival(tlogisticsOrdAddrModify.getRealArrival());
			tlogisticsOrdAddrNew.setIuser(tuser.getIuser());
			tlogisticsOrdAddrNew.setUsername(tuser.getTuserSub().getName());
			tlogisticsOrdAddrService.update(tlogisticsOrdAddrNew);
			realSumNum += tlogisticsOrdAddrNew.getRealOutstockNum();
		}
		
		tlogisticsOrd.setRealOutstockNum(realSumNum);
		if(partFlag && flag){
			tlogisticsOrd.setLogisticsOrdStatus(LogisticsOrdStatus.ALL_SERVICE);
		}else if(partFlag){
			tlogisticsOrd.setLogisticsOrdStatus(LogisticsOrdStatus.PARTIAL_DELIVERY);
		}else{
			tlogisticsOrd.setLogisticsOrdStatus(LogisticsOrdStatus.SHIPPED);
		}
		tlogisticsOrd.setIuser(tuser.getIuser());
		tlogisticsOrd.setUsername(tuser.getTuserSub().getName());
		tlogisticsService.updateTlogisticsOrd(tlogisticsOrd);
		
		// 日志记录
		Tlog tlog = new Tlog();
		tlog.setIfk(tuser.getIuser());
		tlog.setLogType(LogType.USER);
		tlog.setOperType(OperType.LOGISTICS_MGR);
		tlog.setMemo("后台管理员填写物流计划的实际物流情况");
		tlog.setPreData("");
		tlog.setData("管理员" + tuser.getTuserSub().getName() + "实际物流情况");
		this.tlogService.save(tlog);
		
		//添加跟踪信息
		tdetailTraceService.add4TlogisticsOrd(tlogisticsOrd, tuser, "后台管理员 填写实际发货情况("+genLogisticsTraceInfo(tlogisticsOrd)+")", null, null);

		return tlogisticsOrd;
	}
    
	
	public TlogisticsOrd logisticsOrdCancel(Tuser tuser, Long ilogisticsOrd) throws BizException {

		TlogisticsOrd tlogisticsOrd = this.tlogisticsService
				.findTlogisticsOrdById(ilogisticsOrd);

		//查询物流单是否存在
		if(tlogisticsOrd==null){
			throw new BizException(121, "物流单不存在");
		}
		
		//物流单状态不是待审核，则不能撤销
		if(!tlogisticsOrd.getLogisticsOrdStatus().equals(LogisticsOrdStatus.WAIT_AUDIT)){
			throw new BizException(121, "该物流单不能撤销");
		}

		TpayNotify tpayNotify = this.tpayNotifyService.findByifkAndPayType(
				tlogisticsOrd.getIlogisticsOrd(), PayType.LOGISTICS);
		
		// 删除付款通知书
		if (tpayNotify != null) {
			
			//若客户已付款,则不能撤销物流单
			if(PayStatus.WAIT_AUDIT.equals(tpayNotify.getPayNotifyStatus())){
				logger.debug("物流单已付款，不能撤销");
				throw new BizException(121, "物流单已付款，不能撤销");
			}
			
			this.tpayNotifyService.delete(tpayNotify.getIpayNotify());
		}

		// 删除物流单
		this.tlogisticsService.deleteTlogisticsOrd(tlogisticsOrd);

		// 修改订单产品明细
		Long iord = tlogisticsOrd.getIord();
		TlogisticsDetail ld = new TlogisticsDetail();
		ld.setIcust(tlogisticsOrd.getIcust());
		ld.setIlogisticsOrd(ilogisticsOrd);
		List<TlogisticsDetail> tlogisticsDetailList = this.tlogisticsDetailService.findListByDetail(ld);
		
		updateOrdDetailPdtCancel(tlogisticsDetailList, iord);
		
		// 修改订单明细
		updateOrdDetailCancel(tlogisticsDetailList, iord);

		// 日志记录
		Tlog tlog = new Tlog();
		tlog.setIfk(tuser.getIuser());
		tlog.setLogType(LogType.USER);
		tlog.setOperType(OperType.LOGISTICS_MGR);
		tlog.setMemo("后台管理员撤销物流计划");
		tlog.setPreData("");
		tlog.setData("管理员" + tuser.getTuserSub().getName()
				+ "撤销物流计划,原物流编号是" + tlogisticsOrd.getInnerOrdno());
		this.tlogService.save(tlog);
		
		//添加跟踪信息
		tdetailTraceService.add4TlogisticsOrd(tlogisticsOrd, tuser, "后台管理员 撤销 物流单(编号:"+tlogisticsOrd.getInnerOrdno()+")", null, null);

		return tlogisticsOrd;

	}
	
	/**
	 * 撤销物流单-修改订单产品明细
	 */
	private void updateOrdDetailPdtCancel(List<TlogisticsDetail> tlogisticsDetailList, Long iord) {
		for(TlogisticsDetail ld:tlogisticsDetailList){
			int pdtNum = ld.getPdtNum();
			Long iordDetailPdt = ld.getIordDetailPdt();
			
			TordDetailPdt odp = this.tordDetailPdtService.find(iordDetailPdt);
			odp.setPendingNum(odp.getPendingNum()-pdtNum);
			odp.setUsedQuota(odp.getUsedQuota()-pdtNum);
			odp.setRemainQuota(odp.getRemainQuota()+pdtNum);
			
			this.tordDetailPdtService.update(odp);
		}
	}
	
	/**
	 * 撤销物流单-修改订单明细
	 */
	private void updateOrdDetailCancel(List<TlogisticsDetail> tlogisticsDetailList, Long iord) {
		
		for (TlogisticsDetail ld : tlogisticsDetailList) {
			int pdtNum = ld.getPdtNum();
			
			TordDetail td = new TordDetail();
			td.setIord(iord);
			td.setIpdtHis(ld.getIpdtHis());
			TordDetail od = this.tordDetailService.findSelect(td).get(0);
			
			od.setPendingNum(od.getPendingNum() - pdtNum);
			od.setUsedQuota(od.getUsedQuota() - pdtNum);
			od.setRemainQuota(od.getRemainQuota() + pdtNum);
			this.tordDetailService.update(od);
		}
	}
	
	
	public List<TlogisticsOrd> queryOrdListByCon(
			TlogisticsOrdCondition condition) {
		condition.addOrders(Order.asc("updTime"));
		return tlogisticsService.queryByCondition(condition);
	}
	
	/**
	 * 构造物流下载对象
	 */
	public List<LogisticsDown4Page> getLogisticsData(List<TlogisticsOrd> list) {
		List<LogisticsDown4Page> ld4pList = new ArrayList<LogisticsDown4Page>();
		for (int i = 0; i < list.size(); i++) {
			LogisticsDown4Page ld4p = new LogisticsDown4Page();
			ld4p.setTlogisticsOrd(list.get(i));
			if(list.get(i)!=null){//设置erp订单号
				Tord tord = this.tordService.find(list.get(i).getIord());
				if(tord!=null){
					Tagmt tagmt = this.tagmtService.findById(tord.getIagmt());
					ld4p.setErpOrdId(tagmt.getErpOrdId());
				}
			}
			TlogisticsOrdAddr tmp = new TlogisticsOrdAddr();
			tmp.setIlogisticsOrd(list.get(i).getIlogisticsOrd());
			List<TlogisticsOrdAddr> addrList = tlogisticsOrdAddrService
					.findListSelect(tmp);
			for (TlogisticsOrdAddr tlogisticsOrdAddr : addrList) {
				TpdtHis his = tpdtService.findTpdtHisById(tlogisticsOrdAddr.getIpdtHis());
				tlogisticsOrdAddr.setPdtName(his.getName());
			}
			ld4p.setTlogisticsOrdAddrList(addrList);
			ld4pList.add(ld4p);
		}
		return ld4pList;
	}
	
	public List<TlogisticsOrd>  queryOrdByCon(String name, TlogisticsOrd tlogisticsOrd, Page page,String consigneeName) {
		
		TcustRegCondition trc = new TcustRegCondition();
		trc.setName(name);
		List<Long> icusts = tcustRegService.queryIcustListByName(trc);
		if (StringUtils.isNotBlank(name) && CollectionUtils.isEmpty(icusts)) {
			return null;
		}
		
		TlogisticsOrdCondition condition = new TlogisticsOrdCondition();
		
		if(StringUtils.isNotBlank(consigneeName)){//搜索收货人姓名
			List<TlogisticsOrdAddr> addrList = null;
			TlogisticsOrdAddrCondition  tlogisticsOrdAddrCondition = new TlogisticsOrdAddrCondition();
			tlogisticsOrdAddrCondition.setConsigneeName(consigneeName.trim());
			addrList = this.tlogisticsOrdAddrService.findCondition(tlogisticsOrdAddrCondition);
			List<Long> ilogisticsOrdList = new ArrayList<Long>();
			if(addrList!=null&&addrList.size()>0){
				for(TlogisticsOrdAddr addr:addrList){
					if(!ilogisticsOrdList.contains(addr.getIlogisticsOrd())){
						ilogisticsOrdList.add(addr.getIlogisticsOrd());
					}
				}   
			}else{
				ilogisticsOrdList.add(0L);
			}
			condition.setIlogisticsOrd(ilogisticsOrdList);
		}
		condition.setTempFlag(false);
		condition.setInnerOrdno(tlogisticsOrd.getInnerOrdno());
		condition.setLogisticsOrdStatus(tlogisticsOrd.getLogisticsOrdStatus());
		condition.setPayStatus(tlogisticsOrd.getPayStatus());
		condition.setIcustList(icusts);
		condition.addOrders(Order.desc("ilogisticsOrd"));
		List<TlogisticsOrd> list = tlogisticsService.queryPageByCondition(page, condition);
		if(CollectionUtils.isEmpty(list)){
			return null;
		}else{
			for(TlogisticsOrd t : list){
				t.setTord(tordService.find(t.getIord()));
			}
			return list;
		}
	}
	
	public TlogisticsOrd updateTlogisticsOrd(TlogisticsOrd ord, Tuser tuser) {
		this.tlogisticsService.updateTlogisticsOrd(ord);
		if(ord.getLogisticsOrdStatus() == LogisticsOrdStatus.HAVE_LIBRARY){
			//添加跟踪信息
			tdetailTraceService.add4TlogisticsOrd(ord, tuser, "后台管理员 下载 已审核的物流单(编号:"+ord.getInnerOrdno()+")， 已出库", null, null);
		}
		return ord;
	}
	
	public void saveLogistics4Down(LogisticsDownLoad ld) {
		logisticsDownLoadService.save(ld);
	}
	
	public List<LogisticsDownLoad> findDownListByCondition(
			LogisticsDownLoadCondition condition, Page page) {
		return logisticsDownLoadService.findListByCondition(condition, page);
	}
	
	public List<TlogisticsOrd> findListBy(TlogisticsOrd tlo) {
		return tlogisticsService.findListByObj(tlo);
	}
	
	public TlogisticsOrd findLogisticsOrdById(Long id) {
		return this.tlogisticsService.findTlogisticsOrdById(id);
	}
	
	public Map<String, TlogisticsCom> queryFeeLogisticsCom(YesNoType type){
		return this.tlogisticsComService.queryFeeLogisticsComMap(type);
	}

	public String genLogisticsTraceInfo(TlogisticsOrd tlogisticsOrd){
		StringBuffer sb = new StringBuffer();
		
		TlogisticsOrdAddr loa = new TlogisticsOrdAddr();
		loa.setIlogisticsOrd(tlogisticsOrd.getIlogisticsOrd());
		List<TlogisticsOrdAddr> tlogisticsOrdAddrLists = this.tlogisticsOrdAddrService
				.findListSelect(loa);
		sb.append("编号:").append(tlogisticsOrd.getInnerOrdno()).append(",状态：")
		.append(mapBiz.getMapByType(DictType.logistics_ord_status.toString()).get(tlogisticsOrd.getLogisticsOrdStatus().toString()))
		.append(",支付状态：").append(mapBiz.getMapByType(DictType.pay_status.toString()).get(tlogisticsOrd.getPayStatus().toString()));
		if(!CollectionUtils.isEmpty(tlogisticsOrdAddrLists)){
			sb.append("\n 物流详细地址列表[");
			for(TlogisticsOrdAddr t : tlogisticsOrdAddrLists){
				sb.append("地址:").append(t.getConsigneeAddr()).append(",联系人:").append(t.getConsigneeName()).append(",联系方式:").append(t.getConsigneeMobile())
				.append(",产品:").append(tpdtService.findTpdtHisById(t.getIpdtHis()).getName()).append(",数量：").append(t.getNum());
				if(!(tlogisticsOrd.getLogisticsOrdStatus() == LogisticsOrdStatus.WAIT_AUDIT || tlogisticsOrd.getLogisticsOrdStatus() == LogisticsOrdStatus.REVOKED)){
					sb.append("是否付费：").append(t.getFeeFlag()).append(",运费单价(元/台)：").append(t.getPrice());
				}
				if(LogisticsOrdStatus.SHIPPED == tlogisticsOrd.getLogisticsOrdStatus() || LogisticsOrdStatus.PARTIAL_DELIVERY == tlogisticsOrd.getLogisticsOrdStatus()
						|| LogisticsOrdStatus.ALL_SERVICE == tlogisticsOrd.getLogisticsOrdStatus()){
					sb.append(",真实物流单号:").append(t.getRealOrdno()).append(",实际发货时间:").append(t.getRealDelivery()).append(",实际到货时间").append(t.getRealArrival());
				}
				sb.append(";");
			}
			sb.append("]");
		}
		return sb.toString();
		
	}
	public Long removeSingleConfirm(Long ilogisticsOrdAddr,String remType,Integer remNum,Tuser tuser)throws BizException{
		TlogisticsOrdAddr  tlogAddrOri = this.tlogisticsOrdAddrService.getTlogisticsOrdAddrById(ilogisticsOrdAddr);
		if(tlogAddrOri==null){
			logger.debug("此物流地址为空");
			throw new BizException(0,"此物流地址为空");
		}
		if("0".equals(remType)&&tlogAddrOri.getNum()==1){
			logger.debug("数量为1，无法分单");
			throw new BizException(0,"数量为1，无法分单");
		}
		if(tlogAddrOri.getNum()<=remNum){
			logger.debug("分单或撤单数量超过或等于原有数量");
			throw new BizException(0,"分单或撤单数量超过或等于原有数量");
		}
		if("0".equals(remType)&&tlogAddrOri.getNum()==remNum){
			logger.debug("分单出来的数量与原数量一样，毫无意义");
			throw new BizException(0,"分单出来的数量与原数量一样，毫无意义");
		}
		
		//获取最大序列号
		TlogisticsOrdAddrCondition addrCondition = new TlogisticsOrdAddrCondition();
		addrCondition.setIlogisticsOrd(tlogAddrOri.getIlogisticsOrd());
		addrCondition.addOrders(Order.desc("serial"));
		List<TlogisticsOrdAddr> serialAddrList = this.tlogisticsOrdAddrService.findCondition(addrCondition);
		Integer maxSerial = serialAddrList.get(0).getSerial();
		
		if("1".equals(remType)&&tlogAddrOri.getNum()==remNum){//撤单数量若与原数量相等，无需生成新数据
			
		}else{//生成新数据
			TlogisticsOrdAddr tlogAddr = new TlogisticsOrdAddr();
	    	tlogAddr.setIlogisticsOrd(tlogAddrOri.getIlogisticsOrd());
	    	tlogAddr.setRealOrdno(tlogAddrOri.getRealOrdno());
	    	tlogAddr.setIpdtHis(tlogAddrOri.getIpdtHis());
	    	tlogAddr.setNum(tlogAddrOri.getNum()-remNum);
	    	tlogAddr.setSerial(maxSerial+1);
	    	tlogAddr.setRealSerial(tlogAddrOri.getRealSerial());
	    	tlogAddr.setRealOutstockNum(tlogAddrOri.getRealOutstockNum());
	    	tlogAddr.setRealDelivery(tlogAddrOri.getRealDelivery());
	    	tlogAddr.setRealArrival(tlogAddrOri.getRealArrival());
	    	tlogAddr.setIuser(tuser.getIuser());
	    	tlogAddr.setUsername(tuser.getTuserSub().getName());
	    	tlogAddr.setIaddrHis(tlogAddrOri.getIaddrHis());
	    	tlogAddr.setConsigneeAddr(tlogAddrOri.getConsigneeAddr());
	    	tlogAddr.setConsigneeName(tlogAddrOri.getConsigneeName());
	    	tlogAddr.setConsigneeMobile(tlogAddrOri.getConsigneeMobile());
	    	tlogAddr.setOutstockNo(tlogAddrOri.getOutstockNo());
	    	tlogAddr.setIlogisticsCom(tlogAddrOri.getIlogisticsCom());
	    	tlogAddr.setLogisticsComName(tlogAddrOri.getLogisticsComName());
	    	tlogAddr.setPrice(tlogAddrOri.getPrice());
	    	tlogAddr.setFeeFlag(tlogAddrOri.getFeeFlag());
	    	tlogAddr.setPreSerial(tlogAddrOri.getSerial());
	    	tlogisticsOrdAddrService.save(tlogAddr);
		}
		
    	
        if("0".equals(remType)){//分单   将原地址状态变为删除，重新生成两个新数量的记录
        	TlogisticsOrdAddr tlogAddrNew = new TlogisticsOrdAddr();
        	tlogAddrNew.setIlogisticsOrd(tlogAddrOri.getIlogisticsOrd());
        	tlogAddrNew.setRealOrdno(tlogAddrOri.getRealOrdno());
        	tlogAddrNew.setIpdtHis(tlogAddrOri.getIpdtHis());
        	tlogAddrNew.setNum(remNum);
        	tlogAddrNew.setSerial(maxSerial+2);
        	tlogAddrNew.setRealSerial(tlogAddrOri.getRealSerial());
        	tlogAddrNew.setRealOutstockNum(tlogAddrOri.getRealOutstockNum());
        	tlogAddrNew.setRealDelivery(tlogAddrOri.getRealDelivery());
        	tlogAddrNew.setRealArrival(tlogAddrOri.getRealArrival());
        	tlogAddrNew.setIuser(tuser.getIuser());
        	tlogAddrNew.setUsername(tuser.getTuserSub().getName());
        	tlogAddrNew.setIaddrHis(tlogAddrOri.getIaddrHis());
        	tlogAddrNew.setConsigneeAddr(tlogAddrOri.getConsigneeAddr());
        	tlogAddrNew.setConsigneeName(tlogAddrOri.getConsigneeName());
        	tlogAddrNew.setConsigneeMobile(tlogAddrOri.getConsigneeMobile());
        	tlogAddrNew.setOutstockNo(tlogAddrOri.getOutstockNo());
        	tlogAddrNew.setIlogisticsCom(tlogAddrOri.getIlogisticsCom());
        	tlogAddrNew.setLogisticsComName(tlogAddrOri.getLogisticsComName());
        	tlogAddrNew.setPrice(tlogAddrOri.getPrice());
        	tlogAddrNew.setFeeFlag(tlogAddrOri.getFeeFlag());
        	tlogAddrNew.setPreSerial(tlogAddrOri.getSerial());
        	tlogisticsOrdAddrService.save(tlogAddrNew);
		}else{//撤单需根据序列号解锁相应数量的产品
			TpdtNoCondition condition = new TpdtNoCondition();
	        condition.setIlogisticsOrdAddr(ilogisticsOrdAddr);
	        condition.setDelFlag(Boolean.FALSE);
	        condition.addOrders(Order.desc("pdtNo"));
	        List<TpdtNo> tpdtNoList = this.tpdtNoService.findCondition(condition);
	        if(tpdtNoList!=null){
	        	for(int i=tpdtNoList.size()-1;i>=tpdtNoList.size()-remNum;i--){
	        		TpdtNo tpdtNo = tpdtNoList.get(i);
	        		tpdtNo.setIlogisticsOrd(null);
	        		tpdtNo.setIlogisticsOrdAddr(null);
	        		tpdtNo.setLockType(YesNoType.NO);
	        		tpdtNoService.save(tpdtNo);
	        	}
	        }
		}
        
        this.tlogisticsOrdAddrService.delete(tlogAddrOri);//删除原记录
        
        return tlogAddrOri.getIlogisticsOrd();
	}
	public TlogisticsOrdAddr getTlogisticsOrdAddrById(Long id) {
		return tlogisticsOrdAddrService.getTlogisticsOrdAddrById(id);
	}
}
