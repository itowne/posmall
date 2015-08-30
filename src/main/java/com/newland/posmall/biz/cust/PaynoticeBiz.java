package com.newland.posmall.biz.cust;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.entity.Tfile;
import org.ohuyo.rapid.base.service.TfileService;
import org.ohuyo.rapid.base.service.TsysParamService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.newland.posmall.bean.basebusi.TagmtDeposit;
import com.newland.posmall.bean.basebusi.TagmtDetail;
import com.newland.posmall.bean.basebusi.TlogisticsOrd;
import com.newland.posmall.bean.basebusi.Tord;
import com.newland.posmall.bean.basebusi.Tpay;
import com.newland.posmall.bean.basebusi.TpayDetail;
import com.newland.posmall.bean.basebusi.TpayNotify;
import com.newland.posmall.bean.basebusi.TwareHouse;
import com.newland.posmall.bean.basebusi.TwareHouseDetail;
import com.newland.posmall.bean.basebusi.condition.TpayNofityCondition;
import com.newland.posmall.bean.common.Tlog;
import com.newland.posmall.bean.customer.Tcust;
import com.newland.posmall.bean.customer.Trenew;
import com.newland.posmall.bean.customer.TrenewDetail;
import com.newland.posmall.bean.dict.AgmtDetailStatus;
import com.newland.posmall.bean.dict.AgmtStatus;
import com.newland.posmall.bean.dict.LogType;
import com.newland.posmall.bean.dict.OperType;
import com.newland.posmall.bean.dict.PayMethod;
import com.newland.posmall.bean.dict.PayStatus;
import com.newland.posmall.bean.dict.PayType;
import com.newland.posmall.bean.dict.RenewStatus;
import com.newland.posmall.controller.cust.model.OrderPay4Page;

@Service
@Transactional(rollbackFor = Throwable.class)
public class PaynoticeBiz {
	
	@Resource
	private TpayNotifyService tpayNotifyService;
	
	@Resource
	private TpayService tpayService;
	
	@Resource
	private TpayDetailService tpayDetailService;
	
	@Resource
	private TfileService tfileService;
	
	@Resource
	private TagmtService tagmtService;
	
	@Resource
	private TordService tordService;
	
	@Resource
	private TlogisticsService tlogisticsService;
	
	@Resource
	private TsysParamService tsysParamService;
	
	@Resource
	private TlogService tlogService;
	
	@Resource
	private TwareHouseService twareHouseService;
	
	@Resource
	private TwareHouseDetailService twareHouseDetailService;
	
	@Resource
	private TcustRegService tcustRegService;
	
	@Resource
	private CustMailService custMailService;
	
	@Resource
	private TdetailTraceService tdetailTraceService;
	
	@Resource
	private TrenewService trenewService;
	
	@Resource
	private TrenewDetailService trenewDetailService;
	
	/**
	 * 分页查询付款通知书列表
	 * @param page
	 * @param payType 支付类型
	 * @param payNotifyStatus 通知书状态
	 * @param startDate
	 * @param endDate
	 * @param tcust 客户信息
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List findTpayNotifyByPage(Page page, String payType, String payNotifyStatus, String startDate, String endDate, Tcust tcust){
		if(tcust == null || tcust.getIcust() == null)
			return null;
		List list = tpayNotifyService.findListByInfo(page, payType, payNotifyStatus, startDate, endDate, String.valueOf(tcust.getIcust()));
		if(list != null && list.size() > 0){
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
	
	public List<TpayNotify> findTpayNotify4UploadVoucher(Tcust tcust) {
		PayStatus[] statuses = new PayStatus[]{PayStatus.WAIT_PAY, PayStatus.PART_PAY};
		return this.tpayNotifyService.findBySql(tcust.getIcust(), null, statuses);
	}
	
	public TpayNotify queryTpayNotifyById(Long id) {
		if(id == null) {
			return null;
		}
		return this.tpayNotifyService.find(id);
	}
	
	public List<TpayNotify> queryListByIds(String ids) {
		if(StringUtils.isBlank(ids)) {
			return null;
		}
		String[] idArray = StringUtils.split(ids, ",");
		if(idArray == null || idArray.length < 1) {
			return null;
		}
		List<Long> notifyIds = new ArrayList<Long>();
		for (String id : idArray) {
			notifyIds.add(Long.valueOf(id));
		}
		TpayNofityCondition condition = new TpayNofityCondition();
		condition.setIpayNotifyIds(notifyIds);
		condition.setDelFlag(Boolean.FALSE);
		return this.tpayNotifyService.queryListByCondition(condition);
	}
	
	public void addTpayNotify(TpayNotify tpayNotify){
		this.tpayNotifyService.save(tpayNotify);
	}
	
	public void modifyTpayNotifyByIpayNotify(TpayNotify tpayNotify) {
		this.tpayNotifyService.update(tpayNotify);
	}
	
	
	/**
	 * 支付保证金
	 * @param tpayNotify
	 * @param file
	 * @param tcust
	 * @param tpay
	 * @throws BizException
	 */
	public void pay4Deposit(TpayNotify tpayNotify, Tfile file, Tcust tcust, Tpay tpay) throws BizException {
		if(tpay == null) {
			throw new BizException(0, "获取支付数据失败！");
		}
		if(tpay.getAmt() == null || BigDecimal.ZERO == tpay.getAmt()) {
			throw new BizException(0, "支付金额未输入！");
		}
		if(tpayNotify == null || tpayNotify.getIpayNotify() == null) {
			throw new BizException(0, "获取支付通知书数据失败！");
		}
		if(file == null) {
			throw new BizException(0, "上传付款凭证失败：文件列表为空！");
		}
		if(StringUtils.isBlank(tpay.getRemittanceNo())) {
			throw new BizException(0, "汇款单编号为空！");
		}
		if(StringUtils.isBlank(tpay.getAccount())) {
			throw new BizException(0, "汇款账号为空！");
		}
		if(StringUtils.isBlank(tpay.getBank())) {
			throw new BizException(0, "开户行为空！");
		}
		//添加支付信息
		TpayNotify tpayNotifyDB = this.tpayNotifyService.find(tpayNotify.getIpayNotify());
		if(tpayNotifyDB == null) {
			throw new BizException(0, "支付通知书数据缺失！");
		}
		if(tpayNotifyDB.getPayType() != PayType.BAIL || !(tpayNotifyDB.getIcust().equals(tcust.getIcust()))) {
			throw new BizException(0, "支付通知书匹配失败！");
		}
		Tpay newTpay = new Tpay();
		newTpay.setIcust(tcust.getIcust());
		newTpay.setPayStatus(PayStatus.WAIT_AUDIT);
		newTpay.setPayMethod(PayMethod.REMITTANCE);
		newTpay.setAmt(tpay.getAmt());
		newTpay.setIfile(file.getIfile()); //付款凭证文件id
		newTpay.setRemittanceNo(tpay.getRemittanceNo());
		newTpay.setAccount(tpay.getAccount());
		newTpay.setBank(tpay.getBank());
		this.tpayService.add(newTpay);
		
		//添加支付明细数据
		TpayDetail newPayDetail = new TpayDetail();
		newPayDetail.setIcust(tcust.getIcust());
		newPayDetail.setCustName(tcust.getLoginName());
		newPayDetail.setIfk(tpayNotifyDB.getIfk());
		newPayDetail.setIhisFk(tpayNotifyDB.getIhisFk());
		newPayDetail.setIpay(newTpay.getIpay());
		newPayDetail.setIuser(tcust.getIcust());
		newPayDetail.setUserName(tcust.getLoginName());
		newPayDetail.setPayStatus(PayStatus.WAIT_AUDIT);
		newPayDetail.setPayType(PayType.BAIL);
		newPayDetail.setPayMethod(PayMethod.REMITTANCE);
		this.tpayDetailService.save(newPayDetail);
		
		//修改付款通知书状态
		tpayNotifyDB.setPayNotifyStatus(PayStatus.WAIT_AUDIT);
		this.tpayNotifyService.update(tpayNotifyDB);
		
		//修改协议状态
		Tagmt tagmtDB = this.tagmtService.findById(tpayNotifyDB.getIfk());
		if(tagmtDB == null) {
			throw new BizException(0, "协议数据缺失！");
		}
		tagmtDB.setAgmtStatus(AgmtStatus.PAY);
		this.tagmtService.modifyTagmt(tagmtDB);
		
		//修改协议明细状态
		List<TagmtDetail> details = this.tagmtService.findTagmtDetial(tagmtDB);
		if(details == null || details.size() <1 || details.get(0) == null) {
			throw new BizException(0, "协议明细数据缺失！");
		}
		for (TagmtDetail tagmtDetail : details) {
			tagmtDetail.setAgmtDetailStatus(AgmtDetailStatus.PAY);
			this.tagmtService.modifyTagmtDetail(tagmtDetail);
		}
		
		//添加日志
		Tlog addTlog = new Tlog();
		addTlog.setIfk(tcust.getIcust());
		addTlog.setLogType(LogType.CUST);
		addTlog.setOperType(OperType.AGMT_MGR);
		addTlog.setMemo("【协议保证金支付】");
		addTlog.setPreData("");
		StringBuffer sb = new StringBuffer();
		sb.append("协议[" + tagmtDB.getIagmt() + "," + tagmtDB.getAgmtNo() + "]，");
		sb.append("用户[" + tcust.getIcust() + "," + tcust.getLoginName() + "]，");
		sb.append("支付金额[" + tpay.getAmt() + "]元，");
		sb.append("付款凭证[" + file.getIfile() + "," + file.getUuid() + "]");
		addTlog.setData(sb.toString());
		this.tlogService.save(addTlog);
		
		//添加跟踪信息
		StringBuffer memo = new StringBuffer("客户  上传凭证  支付保证金(账单编号："+tpayNotifyDB.getPayNotifyNo());
		memo.append(" ,上传凭证 金额：").append(tpay.getAmt()).append(" 元)");
		tdetailTraceService.add4Tagmt(tagmtDB, tcust, memo.toString(), tpay.getAmt(), null);
		
		//客户凭证上传通知,由客户发起至账务部
		this.custMailService.sendDepositPayNotify(tcust, tagmtDB, newTpay);
	}
	
	/**
	 * 订单付款
	 * @param file
	 * @param tcust
	 * @param orderPay4Page
	 * @throws BizException
	 */
	public void pay4Order(Tfile file, Tcust tcust, OrderPay4Page orderPay4Page) throws BizException {
		if(orderPay4Page == null) {
			throw new BizException(0, "请求异常！");
		}
		Long ipayNotify = orderPay4Page.getIpayNotify();
		String remittanceNo = orderPay4Page.getRemittanceNo();
		String account = orderPay4Page.getAccount();
		String bank = orderPay4Page.getBank();
		Long iagmt = orderPay4Page.getIagmt();
		Long iord = orderPay4Page.getIord();
		if(ipayNotify == null) {
			throw new BizException(0, "付款通知书主键缺失！");
		}
		if(iagmt == null) {
			throw new BizException(0, "协议主键获取失败！");
		}
		if(iord == null) {
			throw new BizException(0, "订单主键获取失败！");
		}
		TpayNotify tpayNotifyDB = this.tpayNotifyService.find(ipayNotify);
		Tagmt tagmtDB = this.tagmtService.findById(iagmt);
		Tord tordDB = this.tordService.find(iord);
		
		if(tpayNotifyDB == null || !(tpayNotifyDB.getIcust().equals(tcust.getIcust()))) {
			throw new BizException(0, "付款通知书为空或者与用户关联失败");
		}
		
		/******************************************订单支付方式：保证金**********************************************/
		BigDecimal depositUsed4Tord = new BigDecimal(0);
		if(this.canUseDeposit4Order(tpayNotifyDB)) { //本次使用保证金支付
			
			depositUsed4Tord = this.getDepositUse4Tord(tordDB);
			
			//添加支付信息：使用保证金
			Tpay depositTpay = new Tpay();
			depositTpay.setIcust(tcust.getIcust());
			depositTpay.setPayStatus(PayStatus.WAIT_AUDIT);
			depositTpay.setPayMethod(PayMethod.DEPOSIT);
			depositTpay.setAmt(depositUsed4Tord);
			this.tpayService.add(depositTpay);

			//添加支付明细数据：使用保证金
			TpayDetail depositTpayDetail = new TpayDetail();
			depositTpayDetail.setIcust(tcust.getIcust());
			depositTpayDetail.setCustName(tcust.getLoginName());
			depositTpayDetail.setIfk(tpayNotifyDB.getIfk());
			depositTpayDetail.setIhisFk(tpayNotifyDB.getIhisFk());
			depositTpayDetail.setIpay(depositTpay.getIpay());
			depositTpayDetail.setIuser(tcust.getIcust());
			depositTpayDetail.setUserName(tcust.getLoginName());
			depositTpayDetail.setPayStatus(PayStatus.WAIT_AUDIT);
			depositTpayDetail.setPayType(PayType.ORDER); //订单
			depositTpayDetail.setPayMethod(PayMethod.DEPOSIT);
			this.tpayDetailService.save(depositTpayDetail);

			//修改客户保证金
			tagmtDB.setRemainDeposit(tagmtDB.getRemainDeposit().subtract(depositUsed4Tord).setScale(2, RoundingMode.HALF_UP));
			tagmtDB.setUsedDeposit(tagmtDB.getUsedDeposit().add(depositUsed4Tord).setScale(2, RoundingMode.HALF_UP));
			if((tagmtDB.getRemainDeposit().add(tagmtDB.getUsedDeposit())).compareTo(tagmtDB.getDeposit()) > 0) {
				throw new BizException(0, "协议保证金异常，请联系系统管理员！");
			}
			this.tagmtService.modifyTagmt(tagmtDB);

			//添加协议保证金历史
			TagmtDeposit newTagmtDeposit = new TagmtDeposit();
			newTagmtDeposit.setIagmt(tagmtDB.getIagmt());
			newTagmtDeposit.setAgmtNo(tagmtDB.getAgmtNo());
			newTagmtDeposit.setIcust(tcust.getIcust());
			newTagmtDeposit.setDeposit(tagmtDB.getDeposit());
			newTagmtDeposit.setUsedDeposit(tagmtDB.getUsedDeposit());
			newTagmtDeposit.setRemainDeposit(newTagmtDeposit.getDeposit().subtract(newTagmtDeposit.getUsedDeposit()));
			newTagmtDeposit.setVer(tagmtDB.getVersion());
			this.tagmtService.addTagmtDeposit(newTagmtDeposit);
			
		}
		
		
		/******************************************订单支付方式：多余保证金**********************************************/
		// 在没有全部支付的情况下：订单货款tordDB.getAmt()=tpayNotifyDB.getHavepayAmt() + tordDB.getLockAmtOfDeposit() + 未支付金额
		// 因为此时tpayNotifyDB.getHavepayAmt()没有包含保证金支付的部分
		BigDecimal remainShouldPay = tordDB.getAmt().subtract(tordDB.getLockAmtOfDeposit())
				.subtract(tpayNotifyDB.getHavepayAmt())
				.setScale(2, RoundingMode.HALF_UP); //剩余应支付
		BigDecimal redundantDepositUse4Tord = new BigDecimal(0);
		if (this.canUseRedundantDeposit4Order(tordDB)) { //使用多余保证金 支付
			redundantDepositUse4Tord = this.getRedundantDeposit4Tord(tordDB, tordDB.getLockAmtOfDeposit(), 
					tpayNotifyDB.getHavepayAmt());
			remainShouldPay = tordDB.getAmt().subtract(tordDB.getLockAmtOfDeposit())
					.subtract(tpayNotifyDB.getHavepayAmt())
					.subtract(redundantDepositUse4Tord)
					.setScale(2, RoundingMode.HALF_UP);
			
			//添加支付信息：多余保证金
			Tpay redundantDepositTpay = new Tpay();
			redundantDepositTpay.setIcust(tcust.getIcust());
			redundantDepositTpay.setPayStatus(PayStatus.WAIT_AUDIT);
			redundantDepositTpay.setPayMethod(PayMethod.REDUNDANT_DEPOSIT);
			redundantDepositTpay.setAmt(redundantDepositUse4Tord);
			this.tpayService.add(redundantDepositTpay);

			//添加支付明细数据：多余保证金
			TpayDetail redundantDepositPayDetail = new TpayDetail();
			redundantDepositPayDetail.setIcust(tcust.getIcust());
			redundantDepositPayDetail.setCustName(tcust.getLoginName());
			redundantDepositPayDetail.setIfk(tpayNotifyDB.getIfk());
			redundantDepositPayDetail.setIhisFk(tpayNotifyDB.getIhisFk());
			redundantDepositPayDetail.setIpay(redundantDepositTpay.getIpay());
			redundantDepositPayDetail.setIuser(tcust.getIcust());
			redundantDepositPayDetail.setUserName(tcust.getLoginName());
			redundantDepositPayDetail.setPayStatus(PayStatus.WAIT_AUDIT);
			redundantDepositPayDetail.setPayType(PayType.ORDER); //订单
			redundantDepositPayDetail.setPayMethod(PayMethod.REDUNDANT_DEPOSIT);
			this.tpayDetailService.save(redundantDepositPayDetail);
			
			// 修改协议多余保证金（审核不通过的时候需要还原）
			if(tagmtDB.getRedundantDeposit().compareTo(new BigDecimal(0)) < 0 || 
					tagmtDB.getRedundantDeposit().compareTo(redundantDepositUse4Tord) < 0) {
				throw new BizException(0, "协议多余保证金数据异常");
			}
			tagmtDB.setRedundantDeposit(tagmtDB.getRedundantDeposit().subtract(redundantDepositUse4Tord));
			this.tagmtService.modifyTagmt(tagmtDB);
		}
		
		
		/******************************************订单支付方式：汇款**********************************************/
		String remittanceAmtStr = orderPay4Page.getRemittanceAmt();
		BigDecimal remittanceAmt = null;
		if (remainShouldPay.compareTo(new BigDecimal(0)) < 0) {
			throw new BizException(0, "支付异常：剩余应支付货款为0，请检查");
		} else if (remainShouldPay.compareTo(new BigDecimal(0)) == 0) { //无需再汇款
			if (StringUtils.isNotBlank(remittanceAmtStr)) {
				throw new BizException(0, "支付异常：无需额外汇款，请检查");
			}
		} else { // 订单货款 > 保证金 + 多余保证金 + 已支付金额，此时需要汇款
			if(StringUtils.isBlank(remittanceAmtStr)) {
				throw new BizException(0, "汇款金额获取失败！");
			}else {
				try {
					remittanceAmt = new BigDecimal(remittanceAmtStr);
				} catch (Exception e) {
					throw new BizException(0, "汇款金额获取异常！");
				}
			}
			if(file == null) {
				throw new BizException(0, "上传付款凭证失败：文件列表为空！");
			}
			if(StringUtils.isBlank(remittanceNo)) {
				throw new BizException(0, "汇款单编号获取失败！");
			}
			if(StringUtils.isBlank(account)) {
				throw new BizException(0, "汇款账号获取失败！");
			}
			if(StringUtils.isBlank(bank)) {
				throw new BizException(0, "开户行获取失败！");
			}

			//添加支付信息：汇款
			Tpay remittanceTpay = new Tpay();
			remittanceTpay.setIcust(tcust.getIcust());
			remittanceTpay.setPayStatus(PayStatus.WAIT_AUDIT);
			remittanceTpay.setPayMethod(PayMethod.REMITTANCE);
			remittanceTpay.setAmt(remittanceAmt);
			remittanceTpay.setIfile(file.getIfile()); //付款凭证文件id
			remittanceTpay.setRemittanceNo(remittanceNo);
			remittanceTpay.setAccount(account);
			remittanceTpay.setBank(bank);
			this.tpayService.add(remittanceTpay);

			//添加支付明细数据：汇款
			TpayDetail remittanceTpayDetail = new TpayDetail();
			remittanceTpayDetail.setIcust(tcust.getIcust());
			remittanceTpayDetail.setCustName(tcust.getLoginName());
			remittanceTpayDetail.setIfk(tpayNotifyDB.getIfk());
			remittanceTpayDetail.setIhisFk(tpayNotifyDB.getIhisFk());
			remittanceTpayDetail.setIpay(remittanceTpay.getIpay());
			remittanceTpayDetail.setIuser(tcust.getIcust());
			remittanceTpayDetail.setUserName(tcust.getLoginName());
			remittanceTpayDetail.setPayStatus(PayStatus.WAIT_AUDIT);
			remittanceTpayDetail.setPayType(PayType.ORDER); //订单
			remittanceTpayDetail.setPayMethod(PayMethod.REMITTANCE);
			this.tpayDetailService.save(remittanceTpayDetail);
		}
		 

		//修改付款通知书状态
		tpayNotifyDB.setPayNotifyStatus(PayStatus.WAIT_AUDIT);
		this.tpayNotifyService.update(tpayNotifyDB);

		//修改订单状态
//		tordDB.setOrdStatus(OrdStatus.WAIT_AUDIT);
		if(tordDB.getPayStatus() == PayStatus.WAIT_PAY || tordDB.getPayStatus() == PayStatus.NO_PASS){
			tordDB.setPayStatus(PayStatus.WAIT_AUDIT);
			this.tordService.update(tordDB);
		}
		
		//添加日志
		Tlog addTlog = new Tlog();
		addTlog.setIfk(tcust.getIcust());
		addTlog.setLogType(LogType.CUST);
		addTlog.setOperType(OperType.ORD_MGR);
		addTlog.setMemo("【订单支付】");
		addTlog.setPreData("");
		StringBuffer sb = new StringBuffer();
		sb.append("订单[" + tordDB.getIord() + "," + tordDB.getOrdNo() + "]，");
		sb.append("用户[" + tcust.getIcust() + "," + tcust.getLoginName() + "]，");
		sb.append("使用保证金支付[" + depositUsed4Tord + "]元，");
		if (redundantDepositUse4Tord.compareTo(new BigDecimal(0)) > 0) {
			sb.append("使用多余保证金支付[" + redundantDepositUse4Tord + "]元，");
		}
		sb.append("汇款支付[" + remittanceAmt + "]元，汇款单编号[" + remittanceNo + "]，银行账号[" + account + "]；");
		addTlog.setData(sb.toString());
		this.tlogService.save(addTlog);
		
		//添加跟踪信息
		StringBuffer memo = new StringBuffer("客户  上传凭证  支付订单货款(账单编号："+tpayNotifyDB.getPayNotifyNo());
		if(BigDecimal.ZERO.compareTo(depositUsed4Tord) != 0){
			memo.append(", 保证金抵扣 :").append(depositUsed4Tord).append(" 元");
		}
		if (redundantDepositUse4Tord.compareTo(new BigDecimal(0)) > 0) {
			memo.append(", 使用多余保证金支付[" + redundantDepositUse4Tord + "] 元");
		}
		memo.append(" ,上传凭证 金额：").append(remittanceAmt).append(" 元)");
		tdetailTraceService.add4Tord(tordDB, tcust, memo.toString(), remittanceAmt, null);
		
		//发送订单支付通知
		this.custMailService.sendOrderPayNotify(tcust, tagmtDB, tordDB, depositUsed4Tord, remittanceAmt);
	}
	
	/**
	 * 物流单支付
	 * @param file
	 * @param ipayNotify
	 * @param tpay
	 * @param tcust
	 * @throws BizException
	 */
	public void pay4Logistics(Tfile file, Long ipayNotify, Tpay tpay, Tcust tcust) throws BizException {
		if(file == null) {
			throw new BizException(0, "上传付款凭证失败：文件列表为空！");
		}
		if(ipayNotify == null) {
			throw new BizException(0, "付款通知书主键缺失！");
		}
		if(tpay == null) {
			throw new BizException(0, "支付数据获取失败！");
		}
		if(StringUtils.isBlank(tpay.getRemittanceNo())) {
			throw new BizException(0, "汇款单编号获取失败！");
		}
		if(StringUtils.isBlank(tpay.getAccount())) {
			throw new BizException(0, "汇款账号获取失败！");
		}
		if(StringUtils.isBlank(tpay.getBank())) {
			throw new BizException(0, "开户行获取失败！");
		}
		if(tpay.getAmt() == null || tpay.getAmt() == BigDecimal.ZERO) {
			throw new BizException(0, "汇款金额获取失败或为0！");
		}
		TpayNotify tpayNotifyDB = this.tpayNotifyService.find(ipayNotify);
		if (tpayNotifyDB == null) {
			throw new BizException(0, "付款通知书不存在！");
		}
		if(!tpayNotifyDB.getIcust().equals(tcust.getIcust())) {
			throw new BizException(0, "付款通知书与用户数据不匹配！");
		}
		if(tpayNotifyDB.getPayType() != PayType.LOGISTICS
				|| (tpayNotifyDB.getPayNotifyStatus() != PayStatus.WAIT_PAY 
				&& tpayNotifyDB.getPayNotifyStatus() != PayStatus.PART_PAY
				&& tpayNotifyDB.getPayNotifyStatus() != PayStatus.NO_PASS)) {
			throw new BizException(0, "付款通知书类型或者状态有误！");
		}
		TlogisticsOrd tlogisticsOrd = this.tlogisticsService.findTlogisticsOrdById(tpayNotifyDB.getIfk());
		if(tlogisticsOrd == null
				|| (tlogisticsOrd.getPayStatus() != PayStatus.WAIT_PAY 
				&& tlogisticsOrd.getPayStatus() != PayStatus.PART_PAY
				&& tlogisticsOrd.getPayStatus() != PayStatus.NO_PASS)) {
			throw new BizException(0, "物流单不存在或者状态有误！");
		}
			
		Tord tord = this.tordService.find(tlogisticsOrd.getIord());
		Tagmt tagmt = this.tagmtService.findById(tord.getIagmt());
		BigDecimal depositUsed4Logistics = null; //物流费用支付使用保证金
		BigDecimal freight = tlogisticsOrd.getLogisticsFreight();
		if(freight != null && freight.compareTo(BigDecimal.ZERO) != 0) {
			if(tagmt.getRemainDeposit().compareTo(new BigDecimal(0)) <= 0) { //剩余保证金小于等于0
				depositUsed4Logistics = new BigDecimal(0.00).setScale(2, RoundingMode.HALF_UP);
			}else { //理论上不存在，因为如果还有剩余保证金，在审核时已经自动支付
				if(freight.compareTo(tagmt.getRemainDeposit()) > 0) {
					depositUsed4Logistics = tagmt.getRemainDeposit();
				}else {
					depositUsed4Logistics = freight;
				}
				
				//添加支付信息：使用保证金
				Tpay depositTpay = new Tpay();
				depositTpay.setIcust(tcust.getIcust());
				depositTpay.setPayStatus(PayStatus.WAIT_AUDIT);
				depositTpay.setPayMethod(PayMethod.DEPOSIT);
				depositTpay.setAmt(depositUsed4Logistics);
				this.tpayService.add(depositTpay);

				//添加支付明细数据：使用保证金
				TpayDetail depositTpayDetail = new TpayDetail();
				depositTpayDetail.setIcust(tcust.getIcust());
				depositTpayDetail.setCustName(tcust.getLoginName());
				depositTpayDetail.setIfk(tpayNotifyDB.getIfk());
				depositTpayDetail.setIhisFk(tpayNotifyDB.getIhisFk());
				depositTpayDetail.setIpay(depositTpay.getIpay());
				depositTpayDetail.setIuser(tcust.getIcust());
				depositTpayDetail.setUserName(tcust.getLoginName());
				depositTpayDetail.setPayStatus(PayStatus.WAIT_AUDIT);
				depositTpayDetail.setPayType(PayType.LOGISTICS); //物流单
				depositTpayDetail.setPayMethod(PayMethod.DEPOSIT);
				this.tpayDetailService.save(depositTpayDetail);

				//修改客户保证金
				tagmt.setRemainDeposit(tagmt.getRemainDeposit().subtract(depositUsed4Logistics).setScale(2, RoundingMode.HALF_UP));
				tagmt.setUsedDeposit(tagmt.getUsedDeposit().add(depositUsed4Logistics).setScale(2, RoundingMode.HALF_UP));
				if((tagmt.getRemainDeposit().add(tagmt.getUsedDeposit())).compareTo(tagmt.getDeposit()) > 0) {
					throw new BizException(0, "协议保证金异常，请联系系统管理员！");
				}
				this.tagmtService.modifyTagmt(tagmt);

				//添加协议保证金历史
				TagmtDeposit newTagmtDeposit = new TagmtDeposit();
				newTagmtDeposit.setIagmt(tagmt.getIagmt());
				newTagmtDeposit.setAgmtNo(tagmt.getAgmtNo());
				newTagmtDeposit.setIcust(tcust.getIcust());
				newTagmtDeposit.setDeposit(tagmt.getDeposit());
				newTagmtDeposit.setUsedDeposit(tagmt.getUsedDeposit());
				newTagmtDeposit.setRemainDeposit(newTagmtDeposit.getDeposit().subtract(newTagmtDeposit.getUsedDeposit()));
				newTagmtDeposit.setVer(tagmt.getVersion());
				this.tagmtService.addTagmtDeposit(newTagmtDeposit);
			}
			
			//添加支付信息：汇款
			tpay.setIcust(tcust.getIcust());
			tpay.setPayStatus(PayStatus.WAIT_AUDIT);
			tpay.setPayMethod(PayMethod.REMITTANCE);
			tpay.setIfile(file.getIfile()); //付款凭证文件id
			this.tpayService.add(tpay);

			//添加支付明细数据：汇款
			TpayDetail remittanceTpayDetail = new TpayDetail();
			remittanceTpayDetail.setIcust(tcust.getIcust());
			remittanceTpayDetail.setCustName(tcust.getLoginName());
			remittanceTpayDetail.setIfk(tpayNotifyDB.getIfk());
			remittanceTpayDetail.setIhisFk(tpayNotifyDB.getIhisFk());
			remittanceTpayDetail.setIpay(tpay.getIpay());
			remittanceTpayDetail.setIuser(tcust.getIcust());
			remittanceTpayDetail.setUserName(tcust.getLoginName());
			remittanceTpayDetail.setPayStatus(PayStatus.WAIT_AUDIT);
			remittanceTpayDetail.setPayType(PayType.LOGISTICS); //物流单
			remittanceTpayDetail.setPayMethod(PayMethod.REMITTANCE);
			this.tpayDetailService.save(remittanceTpayDetail);
		}
		
		//修改物流状态
		if(tlogisticsOrd.getPayStatus() == PayStatus.WAIT_PAY || tlogisticsOrd.getPayStatus() == PayStatus.NO_PASS){
			tlogisticsOrd.setPayStatus(PayStatus.WAIT_AUDIT);
		}
		this.tlogisticsService.updateTlogisticsOrd(tlogisticsOrd);

		//修改付款通知书状态
		tpayNotifyDB.setPayNotifyStatus(PayStatus.WAIT_AUDIT);
		this.tpayNotifyService.update(tpayNotifyDB);

		//添加日志
		Tlog addTlog = new Tlog();
		addTlog.setIfk(tcust.getIcust());
		addTlog.setLogType(LogType.CUST);
		addTlog.setOperType(OperType.LOGISTICS_MGR);
		addTlog.setMemo("【物流单支付】");
		addTlog.setPreData("");
		StringBuffer sb = new StringBuffer();
		sb.append("物流单[" + tlogisticsOrd.getIlogisticsOrd() + "," + tlogisticsOrd.getInnerOrdno() + "]，");
		sb.append("用户[" + tcust.getIcust() + "," + tcust.getLoginName() + "]，");
		sb.append("使用保证金支付[" + depositUsed4Logistics + "]元，");
		sb.append("汇款支付[" + tpay.getAmt() + "]元，汇款单编号[" + tpay.getRemittanceNo() + "]，银行账号[" + tpay.getAccount() + "]，");
		sb.append("付款凭证[" + file.getIfile() + "," + file.getUuid() + "]");
		addTlog.setData(sb.toString());
		this.tlogService.save(addTlog);
		
		//添加跟踪信息
		StringBuffer memo = new StringBuffer("客户  上传凭证  支付物流单费用(账单编号："+tpayNotifyDB.getPayNotifyNo());
		memo.append(" ,上传凭证 金额：").append(tpay.getAmt()).append(" 元)");
		tdetailTraceService.add4TlogisticsOrd(tlogisticsOrd, tcust, memo.toString(), tpay.getAmt(), null);
	}
	
	/**
	 * 支付仓管费
	 * @param tpayNotify
	 * @param file
	 * @param tcust
	 * @param tpay
	 * @throws BizException
	 */
	public void pay4Warehouse(TpayNotify tpayNotify, Tfile file, Tcust tcust, Tpay tpay) throws BizException {
		if(tpay == null) {
			throw new BizException(0, "获取支付数据失败！");
		}
		if(tpay.getAmt() == null || BigDecimal.ZERO == tpay.getAmt()) {
			throw new BizException(0, "支付金额未输入！");
		}
		if(tpayNotify == null || tpayNotify.getIpayNotify() == null) {
			throw new BizException(0, "获取支付通知书数据失败！");
		}
		if(file == null) {
			throw new BizException(0, "上传付款凭证失败：文件列表为空！");
		}
		if(StringUtils.isBlank(tpay.getRemittanceNo())) {
			throw new BizException(0, "汇款单编号为空！");
		}
		if(StringUtils.isBlank(tpay.getAccount())) {
			throw new BizException(0, "汇款账号为空！");
		}
		if(StringUtils.isBlank(tpay.getBank())) {
			throw new BizException(0, "开户行为空！");
		}
		//添加支付信息
		TpayNotify tpayNotifyDB = this.tpayNotifyService.find(tpayNotify.getIpayNotify());
		if(tpayNotifyDB == null) {
			throw new BizException(0, "支付通知书数据缺失！");
		}
		if(tpayNotifyDB.getPayType() != PayType.WAREHOUSE || !(tpayNotifyDB.getIcust().equals(tcust.getIcust()))) {
			throw new BizException(0, "支付通知书匹配失败！");
		}
		Tpay newTpay = new Tpay();
		newTpay.setIcust(tcust.getIcust());
		newTpay.setPayStatus(PayStatus.WAIT_AUDIT);
		newTpay.setPayMethod(PayMethod.REMITTANCE);
		newTpay.setAmt(tpay.getAmt());
		newTpay.setIfile(file.getIfile()); //付款凭证文件id
		newTpay.setRemittanceNo(tpay.getRemittanceNo());
		newTpay.setAccount(tpay.getAccount());
		newTpay.setBank(tpay.getBank());
		this.tpayService.add(newTpay);
		
		//添加支付明细数据
		TpayDetail newPayDetail = new TpayDetail();
		newPayDetail.setIcust(tcust.getIcust());
		newPayDetail.setCustName(tcust.getLoginName());
		newPayDetail.setIfk(tpayNotifyDB.getIfk());
		newPayDetail.setIhisFk(tpayNotifyDB.getIhisFk());
		newPayDetail.setIpay(newTpay.getIpay());
		newPayDetail.setIuser(tcust.getIcust());
		newPayDetail.setUserName(tcust.getLoginName());
		newPayDetail.setPayStatus(PayStatus.WAIT_AUDIT);
		newPayDetail.setPayType(PayType.WAREHOUSE);
		newPayDetail.setPayMethod(PayMethod.REMITTANCE);
		this.tpayDetailService.save(newPayDetail);
		
		//修改付款通知书状态
		tpayNotifyDB.setPayNotifyStatus(PayStatus.WAIT_AUDIT);
		this.tpayNotifyService.update(tpayNotifyDB);
		
		//修改仓管费状态
		TwareHouse twareHouseDB = this.twareHouseService.find(tpayNotifyDB.getIfk());
		if(twareHouseDB == null) {
			throw new BizException(0, "仓管费数据缺失！");
		}
		if(twareHouseDB.getPayStatus() != PayStatus.NO_PASS && 
		   twareHouseDB.getPayStatus() != PayStatus.PART_PAY &&
		   twareHouseDB.getPayStatus() != PayStatus.WAIT_PAY) {
			throw new BizException(0, "仓管费数据状态异常！");
		}
		twareHouseDB.setPayStatus(PayStatus.WAIT_AUDIT);
		this.twareHouseService.update(twareHouseDB);
		
		//添加日志
		Tlog addTlog = new Tlog();
		addTlog.setIfk(tcust.getIcust());
		addTlog.setLogType(LogType.CUST);
		addTlog.setOperType(OperType.WAREHOUSE_MGR);
		addTlog.setMemo("【仓管费支付】");
		addTlog.setPreData("");
		StringBuffer sb = new StringBuffer();
		sb.append("仓管费数据，ID[" + twareHouseDB.getIwareHouse() + "]，");
		sb.append("用户[" + tcust.getIcust() + "," + tcust.getLoginName() + "]，");
		sb.append("支付金额[" + tpay.getAmt() + "]元，");
		sb.append("付款凭证[" + file.getIfile() + "," + file.getUuid() + "]");
		addTlog.setData(sb.toString());
		this.tlogService.save(addTlog);
		
		//客户凭证上传通知,由客户发起至账务部
//		this.custMailService.sendDepositPayNotify(tcust, tagmtDB, newTpay);
	}
	
	/**
	 * 支付续保费用
	 * @param tpayNotify
	 * @param file
	 * @param tcust
	 * @param tpay
	 * @throws BizException
	 */
	public void pay4RenewAmt(TpayNotify tpayNotify, Tfile file, Tcust tcust, Tpay tpay) throws BizException {
		if(tpay == null) {
			throw new BizException(0, "获取支付数据失败！");
		}
		if(tpay.getAmt() == null || BigDecimal.ZERO == tpay.getAmt()) {
			throw new BizException(0, "支付金额未输入！");
		}
		if(tpayNotify == null || tpayNotify.getIpayNotify() == null) {
			throw new BizException(0, "获取支付通知书数据失败！");
		}
		if(file == null) {
			throw new BizException(0, "上传付款凭证失败：文件列表为空！");
		}
		if(StringUtils.isBlank(tpay.getRemittanceNo())) {
			throw new BizException(0, "汇款单编号为空！");
		}
		if(StringUtils.isBlank(tpay.getAccount())) {
			throw new BizException(0, "汇款账号为空！");
		}
		if(StringUtils.isBlank(tpay.getBank())) {
			throw new BizException(0, "开户行为空！");
		}
		TpayNotify tpayNotifyDB = this.tpayNotifyService.find(tpayNotify.getIpayNotify());
		if(tpayNotifyDB == null) {
			throw new BizException(0, "支付通知书数据缺失！");
		}
		if(tpayNotifyDB.getPayType() != PayType.RENEW_AMT || !(tpayNotifyDB.getIcust().equals(tcust.getIcust()))) {
			throw new BizException(0, "支付通知书匹配失败！");
		}
		Trenew trenewDB = this.trenewService.findById(tpayNotifyDB.getIfk());
		if(trenewDB == null) {
			throw new BizException(0, "续保数据缺失！");
		}
		if(trenewDB.getRenewAmt().compareTo(tpay.getAmt()) != 0) {
			throw new BizException(0, "支付金额与续保费用不符！");
		}
		
		//添加支付信息
		Tpay newTpay = new Tpay();
		newTpay.setIcust(tcust.getIcust());
		newTpay.setPayStatus(PayStatus.WAIT_AUDIT);
		newTpay.setPayMethod(PayMethod.REMITTANCE);
		newTpay.setAmt(tpay.getAmt());
		newTpay.setIfile(file.getIfile()); //付款凭证文件id
		newTpay.setRemittanceNo(tpay.getRemittanceNo());
		newTpay.setAccount(tpay.getAccount());
		newTpay.setBank(tpay.getBank());
		this.tpayService.add(newTpay);
		
		//添加支付明细数据
		TpayDetail newPayDetail = new TpayDetail();
		newPayDetail.setIcust(tcust.getIcust());
		newPayDetail.setCustName(tcust.getLoginName());
		newPayDetail.setIfk(tpayNotifyDB.getIfk());
		newPayDetail.setIhisFk(tpayNotifyDB.getIhisFk());
		newPayDetail.setIpay(newTpay.getIpay());
		newPayDetail.setIuser(tcust.getIcust());
		newPayDetail.setUserName(tcust.getLoginName());
		newPayDetail.setPayStatus(PayStatus.WAIT_AUDIT);
		newPayDetail.setPayType(PayType.RENEW_AMT);
		newPayDetail.setPayMethod(PayMethod.REMITTANCE);
		this.tpayDetailService.save(newPayDetail);
		
		//修改付款通知书状态
		tpayNotifyDB.setPayNotifyStatus(PayStatus.WAIT_AUDIT);
		this.tpayNotifyService.update(tpayNotifyDB);
		
		//修改续保状态
		if(trenewDB.getRenewStatus().equals(RenewStatus.REVOKED)) {
			throw new BizException(0, "续保申请已撤销，请核实！");
		}
		if(trenewDB.getRenewStatus().equals(RenewStatus.AUDIT_PASS)) {
			throw new BizException(0, "续保申请已生效，请核实！");
		}
		if(trenewDB.getPayStatus() != PayStatus.NO_PASS && 
				trenewDB.getPayStatus() != PayStatus.PART_PAY &&
						trenewDB.getPayStatus() != PayStatus.WAIT_PAY) {
			throw new BizException(0, "续保支付状态异常！");
		}
		trenewDB.setRenewStatus(RenewStatus.WAIT_AUDIT);
		trenewDB.setPayStatus(PayStatus.WAIT_AUDIT);
		this.trenewService.update(trenewDB);
		
		// 计算续保保修期起始、结束日期
		List<TrenewDetail> details = this.trenewDetailService.findListByTrenewId(trenewDB.getIrenew());
		for (TrenewDetail trenewDetail : details) {
			this.trenewDetailService.setLifeTimeOfRenew(trenewDetail, 
					trenewDetail.getLifeStartTime(), trenewDetail.getLifeEndTime(), trenewDB.getRenewLife());
			this.trenewDetailService.update(trenewDetail);
		}
		
		//添加日志
		Tlog addTlog = new Tlog();
		addTlog.setIfk(tcust.getIcust());
		addTlog.setLogType(LogType.CUST);
		addTlog.setOperType(OperType.RENEW_MGR);
		addTlog.setMemo("【续保费用支付】");
		addTlog.setPreData("");
		StringBuffer sb = new StringBuffer();
		sb.append("续保数据，ID[" + trenewDB.getIrenew() + "]，");
		sb.append("用户[" + tcust.getIcust() + "," + tcust.getLoginName() + "]，");
		sb.append("支付金额[" + tpay.getAmt() + "]元，");
		sb.append("付款凭证[" + file.getIfile() + "," + file.getUuid() + "]");
		addTlog.setData(sb.toString());
		this.tlogService.save(addTlog);
		
		// TODO 客户凭证上传通知,由客户发起至账务部
	}
	
	/**
	 * 协议变更，保证金补交（协议状态不变，仍为：变更申请审核中）
	 * @param tpayNotify
	 * @param file
	 * @param tcust
	 * @param tpay
	 * @throws BizException
	 */
	public void pay4SupplementDeposit(TpayNotify tpayNotify, Tfile file, Tcust tcust, Tpay tpay) throws BizException {
		if(tpay == null) {
			throw new BizException(0, "获取支付数据失败！");
		}
		if(tpay.getAmt() == null || BigDecimal.ZERO == tpay.getAmt()) {
			throw new BizException(0, "支付金额未输入！");
		}
		if(tpayNotify == null || tpayNotify.getIpayNotify() == null) {
			throw new BizException(0, "获取支付通知书数据失败！");
		}
		if(file == null) {
			throw new BizException(0, "上传付款凭证失败：文件列表为空！");
		}
		if(StringUtils.isBlank(tpay.getRemittanceNo())) {
			throw new BizException(0, "汇款单编号为空！");
		}
		if(StringUtils.isBlank(tpay.getAccount())) {
			throw new BizException(0, "汇款账号为空！");
		}
		if(StringUtils.isBlank(tpay.getBank())) {
			throw new BizException(0, "开户行为空！");
		}
		//添加支付信息
		TpayNotify tpayNotifyDB = this.tpayNotifyService.find(tpayNotify.getIpayNotify());
		if(tpayNotifyDB == null) {
			throw new BizException(0, "支付通知书数据缺失！");
		}
		if(tpayNotifyDB.getPayType() != PayType.BAIL_SUPPLEMENT || !(tpayNotifyDB.getIcust().equals(tcust.getIcust()))) {
			throw new BizException(0, "支付通知书匹配失败！");
		}
		Tagmt tagmtDB = this.tagmtService.findById(tpayNotifyDB.getIfk());
		if(tagmtDB == null) {
			throw new BizException(0, "协议数据缺失！");
		}
		
		Tpay newTpay = new Tpay();
		newTpay.setIcust(tcust.getIcust());
		newTpay.setPayStatus(PayStatus.WAIT_AUDIT);
		newTpay.setPayMethod(PayMethod.REMITTANCE);
		newTpay.setAmt(tpay.getAmt());
		newTpay.setIfile(file.getIfile()); //付款凭证文件id
		newTpay.setRemittanceNo(tpay.getRemittanceNo());
		newTpay.setAccount(tpay.getAccount());
		newTpay.setBank(tpay.getBank());
		this.tpayService.add(newTpay);
		
		//添加支付明细数据
		TpayDetail newPayDetail = new TpayDetail();
		newPayDetail.setIcust(tcust.getIcust());
		newPayDetail.setCustName(tcust.getLoginName());
		newPayDetail.setIfk(tpayNotifyDB.getIfk());
		newPayDetail.setIhisFk(tpayNotifyDB.getIhisFk());
		newPayDetail.setIpay(newTpay.getIpay());
		newPayDetail.setIuser(tcust.getIcust());
		newPayDetail.setUserName(tcust.getLoginName());
		newPayDetail.setPayStatus(PayStatus.WAIT_AUDIT);
		newPayDetail.setPayType(PayType.BAIL_SUPPLEMENT);
		newPayDetail.setPayMethod(PayMethod.REMITTANCE);
		this.tpayDetailService.save(newPayDetail);
		
		//修改付款通知书状态
		tpayNotifyDB.setPayNotifyStatus(PayStatus.WAIT_AUDIT);
		this.tpayNotifyService.update(tpayNotifyDB);
		
		//添加日志
		Tlog addTlog = new Tlog();
		addTlog.setIfk(tcust.getIcust());
		addTlog.setLogType(LogType.CUST);
		addTlog.setOperType(OperType.AGMT_MGR);
		addTlog.setMemo("【协议变更，保证金补交】");
		addTlog.setPreData("");
		StringBuffer sb = new StringBuffer();
		sb.append("协议[" + tagmtDB.getIagmt() + "," + tagmtDB.getAgmtNo() + "]，");
		sb.append("用户[" + tcust.getIcust() + "," + tcust.getLoginName() + "]，");
		sb.append("补交保证金[" + tpay.getAmt() + "]元，");
		sb.append("付款凭证[" + file.getIfile() + "," + file.getUuid() + "]");
		addTlog.setData(sb.toString());
		this.tlogService.save(addTlog);
		
		//添加跟踪信息
		StringBuffer memo = new StringBuffer("客户  上传凭证  协议变更补交保证金(账单编号："+tpayNotifyDB.getPayNotifyNo());
		memo.append(" ,上传凭证 补交金额：").append(tpay.getAmt()).append(" 元)");
		tdetailTraceService.add4Tagmt(tagmtDB, tcust, memo.toString(), tpay.getAmt(), null);
		
		//客户凭证上传通知,由客户发起至账务部
		this.custMailService.sendDepositPayNotify(tcust, tagmtDB, newTpay);
	}
	
	/**
	 * 通过付款通知书查看付款信息列表
	 * @param tpayNotify
	 * @return
	 */
	public List<Tpay> queryTpayByTapyNofity(TpayNotify tpayNotify) {
		if(tpayNotify == null || tpayNotify.getIfk() == null || tpayNotify.getPayType() == null) {
			return null;
		}
		List<Tpay> tpayList = new ArrayList<Tpay>();
		List<TpayDetail> tpayDetailList = this.tpayDetailService.findSelect(tpayNotify.getIfk(), tpayNotify.getPayType());
		if(tpayDetailList == null || tpayDetailList.size() < 1){
			return null;
		}
		for (TpayDetail tpayDetail : tpayDetailList) {
			Tpay temp = this.tpayService.find(tpayDetail.getIpay());
			if(temp == null)
				continue;
			temp.setRefuseReason(tpayDetail.getRefuseReason());
			tpayList.add(temp);
		}
		return tpayList;
	}
	
	public void setTfile4Tpay(List<Tpay> tpayList) {
		if(tpayList == null || tpayList.size() < 1)
			return;
		for (Tpay tpay : tpayList) {
			if(tpay.getPayMethod() == PayMethod.REMITTANCE) {
				Tfile file = this.tfileService.getById(tpay.getIfile());
				if (file == null)
					continue;
				tpay.setTfile(file);
			}
		}
	}
	
	public void setTpayDetail4Tpay(List<Tpay> tpayList) {
		if(tpayList == null || tpayList.size() < 1)
			return;
		List<TpayDetail> details = null;
		for (Tpay tpay : tpayList) {
			details = this.tpayService.findByIpay(tpay.getIpay());
			if(details != null && details.size() >= 1) {
				tpay.setTpayDetail(details.get(0));
			}
		}
	}
	
	/**
	 * 订单支付是否可以使用保证金抵扣
	 * @param tpayNotify
	 * @return
	 */
	public boolean canUseDeposit4Order(TpayNotify tpayNotify) {
		
		boolean flag = true;
		//最后判断：如果该笔订单已经使用保证金支付过，则不再使用
		List<Tpay> tpayList = this.queryTpayByTapyNofity(tpayNotify);
		if(tpayList != null && tpayList.size() >= 1) {
			for (Tpay tpay : tpayList) {
				if(tpay.getPayMethod() == PayMethod.DEPOSIT && 
						tpay.getPayStatus() == PayStatus.HAVE_PAY) { //如果已经使用保证金支付过，且审核通过，则不能再使用保证金支付
					flag = false;
					break;
				}
			}
		}
		return flag;
	}
	
	/**
	 * 查询本次订单支付所使用的保证金
	 * @param tord
	 * @return
	 */
	public BigDecimal getDepositUse4Tord(Tord tord) {
		Tagmt tagmt = this.tagmtService.findById(tord.getIagmt());
		BigDecimal depositUsed4Tord = tord.getLockAmtOfDeposit(); //下订单时，计算出来的保证金抵扣理论值
		
		if(depositUsed4Tord.compareTo(tagmt.getRemainDeposit()) > 0) { //当保证金抵扣理论值大于剩余保证金时，实际抵扣值=剩余保证金
			depositUsed4Tord = tagmt.getRemainDeposit();
			
			//更新保证金锁定额度
			tord.setLockAmtOfDeposit(depositUsed4Tord);
			this.tordService.update(tord);
		}
		return depositUsed4Tord;
	}
	
	/**
	 * 返回订单已经抵扣的保证金
	 * @param tpayNotify 订单的付款通知书
	 * @return
	 */
	public BigDecimal getDepositPaid4Tord(TpayNotify tpayNotify) {
		BigDecimal ret = new BigDecimal(0);
		List<TpayDetail> tpayDetailList = this.tpayDetailService.findSelect(tpayNotify.getIfk(), tpayNotify.getPayType());
		if(tpayDetailList == null || tpayDetailList.size() < 1){
			return ret;
		}
		for (TpayDetail tpayDetail : tpayDetailList) {
			if(tpayDetail.getPayStatus() == PayStatus.HAVE_PAY && tpayDetail.getPayMethod() == PayMethod.DEPOSIT) {
				ret = tpayDetail.getPayAmt();
				break;
			}
		}
		return ret;
	}
	
	/**
	 * 是否存在多余保证金 可供订单支付使用
	 * @param tord
	 * @return
	 */
	public boolean canUseRedundantDeposit4Order(Tord tord) {
		Tagmt tagmt = this.tagmtService.findById(tord.getIagmt());
		BigDecimal redundantDeposit = tagmt.getRedundantDeposit();
		if (tagmt.getAgmtStatus().equals(AgmtStatus.HAVE_CHANGED) 
				&& redundantDeposit != null 
				&& redundantDeposit.compareTo(new BigDecimal(0)) > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 返回本次订单支付所使用的多余保证金
	 * @param tord
	 * @param depositUsed4Tord
	 * @param tpayNotify
	 * @return
	 */
	public BigDecimal getRedundantDeposit4Tord(Tord tord, BigDecimal depositUsed4Tord, BigDecimal havePayAmt) {
		BigDecimal useRedundantDeposit = new BigDecimal(0);
		Tagmt tagmt = this.tagmtService.findById(tord.getIagmt());
		BigDecimal redundantDeposit = tagmt.getRedundantDeposit();
		if (tagmt.getAgmtStatus().equals(AgmtStatus.HAVE_CHANGED) 
				&& redundantDeposit != null 
				&& redundantDeposit.compareTo(new BigDecimal(0)) > 0) {
			if (tord.getAmt().compareTo(depositUsed4Tord.add(redundantDeposit)
					.add(havePayAmt)) >= 0) { // 订单货款 >= 保证金支付 + 多余保证金 + 已支付（协议变更之后产生的）
				useRedundantDeposit = redundantDeposit; //多余保证金全部用于订单支付
			} else { // 订单货款 < 保证金支付 + 多余保证金 + 已支付
				useRedundantDeposit = tord.getAmt()
						.subtract(depositUsed4Tord)
						.subtract(havePayAmt)
						.setScale(2, RoundingMode.HALF_UP); //多余保证金部分用于订单支付
			}
		}
		return useRedundantDeposit;
	}
	
	public TwareHouse queryTwareHouseById(Long iwareHouse){
		return twareHouseService.find(iwareHouse);
	}
	
	public List<TwareHouseDetail> queryTwareHouseDetailListById(Long iwareHouse){
		return twareHouseDetailService.queryListByiwareHouse(iwareHouse);
	}
}
