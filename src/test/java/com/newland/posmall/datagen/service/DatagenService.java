package com.newland.posmall.datagen.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.ohuyo.rapid.base.entity.Tfile;
import org.ohuyo.rapid.base.entity.Tuser;
import org.ohuyo.rapid.base.service.TuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newland.posmall.base.exception.BizException;
import com.newland.posmall.base.service.TagmtService;
import com.newland.posmall.base.service.TcustRegService;
import com.newland.posmall.base.service.TcustService;
import com.newland.posmall.base.service.TordService;
import com.newland.posmall.base.service.TpayNotifyService;
import com.newland.posmall.base.service.TpdtService;
import com.newland.posmall.bean.basebusi.OrdPdtModel4Page;
import com.newland.posmall.bean.basebusi.OrdPdtModelList4Page;
import com.newland.posmall.bean.basebusi.Tagmt;
import com.newland.posmall.bean.basebusi.TagmtDetail;
import com.newland.posmall.bean.basebusi.Tord;
import com.newland.posmall.bean.basebusi.Tpay;
import com.newland.posmall.bean.basebusi.TpayNotify;
import com.newland.posmall.bean.basebusi.Tpdt;
import com.newland.posmall.bean.customer.Tcust;
import com.newland.posmall.bean.dict.AgmtDetailStatus;
import com.newland.posmall.bean.dict.AgmtStatus;
import com.newland.posmall.bean.dict.PayMethod;
import com.newland.posmall.bean.dict.PayStatus;
import com.newland.posmall.bean.dict.PayType;
import com.newland.posmall.biz.admin.CustagmtBiz;
import com.newland.posmall.biz.admin.OrderBiz;
import com.newland.posmall.biz.admin.PayNotifyBiz;
import com.newland.posmall.biz.cust.AgreementBiz;
import com.newland.posmall.biz.cust.CustOrdBiz;
import com.newland.posmall.biz.cust.PaynoticeBiz;
import com.newland.posmall.controller.cust.model.OrderPay4Page;

@Transactional
@Service
public class DatagenService {

	// 协议起始时间
	private String startDateOfAgmt = "2014-09-01";
	// 协议中止时间
	private String endDateOfAgmt = "2014-12-01";
	// 汇款单编号
	private final String remittanceNo = "3501000";
	// 汇款单金额
	private final BigDecimal amt = new BigDecimal(1000000);
	// 审核拒绝理由
	private final String refuseReason = "测试-审核不通过";
	// 订单付款凭证:付款通知书id
	private String useDepositFlag = Boolean.TRUE.toString();
	// 订单付款凭证:本次订单支付所使用的保证金
	private String depositUsed4Tord = "10000";
	// 订单付款凭证:汇款单编号
	private String ordRemittanceNo = "3502001";
	// 订单付款凭证:本次订单支付的汇款金额
	private String ordRemittanceAmt = "90000";
	// 订单付款凭证:银行账号
	private String account = "62170001000200";
	// 订单付款凭证:开户行
	private String bank = "中国银行";
	// 订单数量
	private String orderNumber = "100";

	@Autowired
	private AgreementBiz agreementBiz;

	@Autowired
	private CustagmtBiz custagmtBiz;

	@Autowired
	private CustOrdBiz custOrdBiz;

	@Autowired
	private PaynoticeBiz paynoticeBiz;

	@Autowired
	private PayNotifyBiz payNotifyBiz;

	@Autowired
	private OrderBiz orderBiz;

	@Autowired
	private TcustService tcustService;

	@Autowired
	private TuserService tuserService;

	@Autowired
	private TpdtService tpdtService;

	@Autowired
	private TagmtService tagmtService;

	@Autowired
	private TpayNotifyService tpayNotifyService;

	@Autowired
	private TcustRegService tcustRegService;

	@Autowired
	private TordService tordService;

	public Tcust getCust(Long icust) {
		return tcustService.find(icust);
	}

	public Tpdt getTpdt(Long ipdt) {
		return tpdtService.find(ipdt);
	}

	public Tuser getUser(Long iuser) {
		return tuserService.getTuser(iuser);
	}

	public List<Tord> getTord(Long iagmt) {
		Tord tord = new Tord();
		tord.setIagmt(iagmt);
		return this.tordService.findBySelect(tord);
	}

	/**
	 * 预约订单
	 * 
	 * @param num
	 * @param tpdts
	 * @param tcust
	 * @return
	 * @throws BizException
	 */
	public List<Tagmt> signAgmt(int num, List<Tpdt> tpdts, Tcust tcust)
			throws BizException {
		this.agreementBiz.setTpdtPrice(tpdts, tcust);
		List<Tagmt> agmts = new ArrayList<Tagmt>();
		for (int i = 0; i < num; i++) {
			Tagmt agmt = this.agreementBiz.signAgreement(tpdts, tcust,
					startDateOfAgmt, endDateOfAgmt);
			agmts.add(agmt);
		}
		return agmts;
	}

	/**
	 * 协议确认
	 * 
	 * @param agmts
	 * @param user
	 * @return
	 * @throws BizException
	 */
	public List<Tagmt> cofirm(List<Tagmt> agmts, Tuser user)
			throws BizException {
		for (int i = 0; i < agmts.size(); i++) {
			Tagmt tagmt = agmts.get(i);
			List<TagmtDetail> tagmtDetails = this.tagmtService
					.findTagmtDetial(tagmt);
			user.setTuserSub(this.tuserService.getTuserSub(user.getIuser()));
			this.custagmtBiz.custagmtCheck(tagmt,
					AgmtStatus.AGMT_QUOTA_CONFIRM,
					AgmtDetailStatus.AGMT_QUOTA_CONFIRM, tagmtDetails, user);
		}
		return agmts;
	}

	/**
	 * 上传付款凭证:保证金通知书
	 * 
	 * @param agmts
	 * @param tcust
	 * @return
	 * @throws BizException
	 */
	public List<Tagmt> uploadTicket(List<Tagmt> agmts, Tcust tcust)
			throws BizException {
		Tfile file = new Tfile();
		file.setIfile(1001L);
		Tpay tpay = new Tpay();
		tpay.setAmt(amt);
		tpay.setPayMethod(PayMethod.REMITTANCE);
		tpay.setBank("中国工商银行");
		for (int i = 0; i < agmts.size(); i++) {
			tpay.setRemittanceNo(remittanceNo + i);
			tpay.setAccount("6217 0018 0000 00" + i);
			TpayNotify tpayNotify = this.tpayNotifyService.findByifkAndPayType(
					agmts.get(i).getIagmt(), PayType.BAIL);
			this.paynoticeBiz.pay4Deposit(tpayNotify, file, tcust, tpay);
		}
		return agmts;
	}

	/**
	 * 保证金通知书审核
	 * 
	 * @param agmts
	 * @param payStatus
	 * @param user
	 * @return
	 * @throws BizException
	 */
	public List<Tagmt> auditAgmt(List<Tagmt> agmts, PayStatus payStatus,
			Tuser user) throws BizException {
		user.setTuserSub(this.tuserService.getTuserSub(user.getIuser()));
		switch (payStatus) {
		case HAVE_PAY:
			for (int i = 0; i < agmts.size(); i++) {
				TpayNotify tpayNotify = this.tpayNotifyService
						.findByifkAndPayType(agmts.get(i).getIagmt(),
								PayType.BAIL);
				this.payNotifyBiz.payAudit(tpayNotify, payStatus, user);
			}
			return agmts;
		case NO_PASS:
			for (int i = 0; i < agmts.size(); i++) {
				TpayNotify tpayNotify = this.tpayNotifyService
						.findByifkAndPayType(agmts.get(i).getIagmt(),
								PayType.BAIL);
				this.payNotifyBiz.payAuditNo(tpayNotify, refuseReason, user);
			}
			return agmts;
		default:
			return agmts;
		}
	}

	/**
	 * 保证金通知书付款成功,协议最后审核
	 * 
	 * @param agmts
	 * @param user
	 * @return
	 * @throws BizException
	 */
	public List<Tagmt> agmtPayPassCheck(List<Tagmt> agmts, Tuser user)
			throws BizException {
		user.setTuserSub(this.tuserService.getTuserSub(user.getIuser()));
		for (int i = 0; i < agmts.size(); i++) {
			Tagmt tagmt = this.custagmtBiz.findTagmtByIagmt(agmts.get(i)
					.getIagmt());
			this.custagmtBiz.custagmtCheck(tagmt, AgmtStatus.CONFIRM,
					AgmtDetailStatus.CONFIRM, null, user);
		}
		return agmts;
	}

	/**
	 * 签署订单
	 * 
	 * @param iagmt
	 * @param num
	 * @param ipdts
	 * @param agmt
	 * @param tcust
	 * @return
	 * @throws BizException
	 */
	public List<Tord> createOrd(int num, List<Long> ipdts, Tagmt agmt,
			Tcust tcust) throws BizException {
		List<OrdPdtModel4Page> ordPdtModel4PageList = this.custOrdBiz
				.queryObjectArr(agmt);
		ordPdtModel4PageList.get(0).getOrdPdtModelList().get(1)
				.setOrderNumber(orderNumber);
		List<Tord> tords = new ArrayList<Tord>();
		OrdPdtModelList4Page list4Page = new OrdPdtModelList4Page();
		list4Page.setOrdPdtModel4PageList(ordPdtModel4PageList);
		for (int i = 0; i < num; i++) {
			Tord tord = this.custOrdBiz.addOrdConfirm(agmt.getIagmt(),
					list4Page, tcust,
					this.tcustRegService.findByIcust(tcust.getIcust()));
			tords.add(tord);
		}
		return tords;
	}

	/**
	 * 上传付款凭证:订单通知书
	 * 
	 * @param ords
	 * @param tcust
	 * @return
	 * @throws BizException
	 */
	public List<Tord> uploadOrdTicket(List<Tord> ords, Tcust tcust)
			throws BizException {
		Tfile file = new Tfile();
		file.setIfile(1001L);
		Tpay tpay = new Tpay();
		tpay.setAmt(amt);
		tpay.setRemittanceNo(remittanceNo);
		tpay.setPayMethod(PayMethod.REMITTANCE);
		OrderPay4Page orderPay4Page = new OrderPay4Page();
		orderPay4Page.setUseDepositFlag(useDepositFlag);
		orderPay4Page.setDepositUsed4Tord(depositUsed4Tord);
		orderPay4Page.setRemittanceNo(ordRemittanceNo);
		orderPay4Page.setRemittanceAmt(ordRemittanceAmt);
		orderPay4Page.setBank(bank);
		for (int i = 0; i < ords.size(); i++) {
			orderPay4Page.setAccount(account + i);
			TpayNotify tpayNotify = this.tpayNotifyService.findByifkAndPayType(
					ords.get(i).getIord(), PayType.ORDER);
			orderPay4Page.setIpayNotify(tpayNotify.getIpayNotify());
			orderPay4Page.setIord(ords.get(i).getIord());
			orderPay4Page.setIagmt(ords.get(i).getIagmt());
			this.paynoticeBiz.pay4Order(file, tcust, orderPay4Page);
		}
		return ords;
	}

	/**
	 * 订单通知书审核
	 * 
	 * @param ords
	 * @param payStatus
	 * @param user
	 * @return
	 * @throws BizException
	 */
	public List<Tord> auditOrd(List<Tord> ords, PayStatus payStatus, Tuser user)
			throws BizException {
		user.setTuserSub(this.tuserService.getTuserSub(user.getIuser()));
		switch (payStatus) {
		case HAVE_PAY:
			for (int i = 0; i < ords.size(); i++) {
				TpayNotify tpayNotify = this.tpayNotifyService
						.findByifkAndPayType(ords.get(i).getIord(),
								PayType.ORDER);
				this.payNotifyBiz.payAudit(tpayNotify, payStatus, user);
			}
			return ords;
		case NO_PASS:
			for (int i = 0; i < ords.size(); i++) {
				TpayNotify tpayNotify = this.tpayNotifyService
						.findByifkAndPayType(ords.get(i).getIord(),
								PayType.ORDER);
				this.payNotifyBiz.payAuditNo(tpayNotify, refuseReason, user);
			}
			return ords;
		default:
			return ords;
		}
	}

	/**
	 * 订单通知书付款成功,订单最后审核
	 * 
	 * @param ords
	 * @param user
	 * @return
	 * @throws BizException
	 */
	public List<Tord> ordPayPassCheck(List<Tord> ords, Tuser user)
			throws BizException {
		user.setTuserSub(this.tuserService.getTuserSub(user.getIuser()));
		for (int i = 0; i < ords.size(); i++) {
			Tord tord = ords.get(i);
			this.orderBiz.auditTord(tord, user);
		}
		return ords;
	}
}
