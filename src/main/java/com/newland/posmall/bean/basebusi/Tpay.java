package com.newland.posmall.bean.basebusi;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import org.ohuyo.rapid.base.entity.Tfile;

import com.newland.posmall.bean.dict.PayMethod;
import com.newland.posmall.bean.dict.PayStatus;
/**
 * 支付表
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_pay")
public class Tpay implements Serializable {

	private static final long serialVersionUID = 2801092965977700292L;

	@Id
	@TableGenerator(name = "i_pay", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_pay")
	@Column(name = "i_pay")
	private Long ipay;//内部编号

	@Column(name = "pay_status")
	@Enumerated(EnumType.STRING)
	private PayStatus payStatus;//状态

	@Column(name = "i_cust")
	private Long icust;//客户外键
	
	@Column(name = "amt")
	private BigDecimal amt;//金额
	
	@Column(name = "pay_no")
	private String payNo;//支付序列号
	
	@Column(name = "i_file")
	private Long ifile;//文件外键

	@Column(name = "gen_time",updatable = false)
	private Date genTime;//生成时间

	@Column(name = "upd_time")
	private Date updTime;//更新时间

	/**
	 * 汇款单编号
	 */
	@Column(name = "remittance_no")
	private String remittanceNo;
	
	/**
	 * 汇款账号
	 */
	@Column(name = "account")
	private String account;
	
	/**
	 * 开户行
	 */
	@Column(name = "bank")
	private String bank;
	
	/**
	 * 支付方式
	 */
	@Column(name = "pay_method")
	@Enumerated(EnumType.STRING)
	private PayMethod payMethod;
	
	@Transient
	private TpayDetail tpayDetail;
	
	@Transient
	private Tfile tfile; //付款凭证
	
	/**
	 * 剩余金额
	 */
	@Column(name="remain_amt")
	private BigDecimal remainAmt;
	
	/**
	 * 支付金额
	 */
	@Transient
	private BigDecimal payAmt;
	
	/**
	 * 审核不通过理由
	 */
	@Transient
	private String refuseReason;
	
	public Long getIpay() {
		return ipay;
	}

	public void setIpay(Long ipay) {
		this.ipay = ipay;
	}


	public Long getIcust() {
		return icust;
	}

	public void setIcust(Long icust) {
		this.icust = icust;
	}

	public PayStatus getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(PayStatus payStatus) {
		this.payStatus = payStatus;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	public Long getIfile() {
		return ifile;
	}

	public void setIfile(Long ifile) {
		this.ifile = ifile;
	}

	public Date getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}

	public Date getUpdTime() {
		return updTime;
	}

	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}

	public String getRemittanceNo() {
		return remittanceNo;
	}

	public void setRemittanceNo(String remittanceNo) {
		this.remittanceNo = remittanceNo;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public TpayDetail getTpayDetail() {
		return tpayDetail;
	}

	public void setTpayDetail(TpayDetail tpayDetail) {
		this.tpayDetail = tpayDetail;
	}

	public Tfile getTfile() {
		return tfile;
	}

	public void setTfile(Tfile tfile) {
		this.tfile = tfile;
	}

	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	public PayMethod getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(PayMethod payMethod) {
		this.payMethod = payMethod;
	}

	public BigDecimal getRemainAmt() {
		return remainAmt;
	}

	public void setRemainAmt(BigDecimal remainAmt) {
		this.remainAmt = remainAmt;
	}

	public BigDecimal getPayAmt() {
		return payAmt;
	}

	public void setPayAmt(BigDecimal payAmt) {
		this.payAmt = payAmt;
	}
	
}
