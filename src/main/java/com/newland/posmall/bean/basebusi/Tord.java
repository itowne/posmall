package com.newland.posmall.bean.basebusi;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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

import org.hibernate.annotations.Type;

import com.newland.posmall.bean.dict.OrdStatus;
import com.newland.posmall.bean.dict.PayStatus;
/**
 * 订单表
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_ord")
public class Tord implements Serializable {

	private static final long serialVersionUID = 2801092965977700292L;
	
	@Id
	@TableGenerator(name = "i_ord", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_ord")
	@Column(name = "i_ord")
	private Long iord;    //订单内部编号

	@Column(name = "i_agmt")
	private Long iagmt;  //协议内部编号
	
	@Column(name = "i_cust")
	private Long icust;//客户外键
	
	/**
	 * 订单编号
	 */
	@Column(name = "ord_no")
	private String ordNo;

	@Column(name = "upd_time")
	private Date updTime; //更新时间

	@Column(name = "gen_time",updatable = false)
	private Date genTime;  //生成时间

	@Column(name = "amt")
	private BigDecimal amt;  //金额

	@Column(name = "ord_status")
	@Enumerated(EnumType.STRING)
	private OrdStatus ordStatus; //订单状态

	@Column(name = "pay_status")
	@Enumerated(EnumType.STRING)
	private PayStatus payStatus; //支付状态

	@Column(name ="del_flag")
	@Type(type="yes_no")
	private Boolean delFlag;//删除标志
	
	/**
	 * 订单下载回写追踪号
	 */
	@Column(name="trace_no")
	private String trace;
	
	/**
	 * 保证金锁定金额
	 */
	@Column(name = "lock_amt_of_deposit")
	private BigDecimal lockAmtOfDeposit;
	
	/**
	 * 已发货部分货款
	 */
	@Column(name = "amt_of_delivery")
	private BigDecimal amtOfDelivery;
		
	@Transient
	private List<TordDetail> tordDetailList;//订单明细
	
	/**
	 * 订单支付所用保证金
	 */
	@Transient
	private BigDecimal depositUsed4Tord;
	
	/**
	 * 已支付货款
	 */
	@Transient
	private BigDecimal havePaid;
	
	/**
	 * 还应支付货款
	 */
	@Transient
	private BigDecimal remainShouldPay;
	
	@Transient
	private Tagmt tagmt;
	
	@Column(name = "remark")
	private String remark;//备注
	
	public Long getIord() {
		return iord;
	}

	public void setIord(Long iord) {
		this.iord = iord;
	}

	public Long getIagmt() {
		return iagmt;
	}

	public void setIagmt(Long iagmt) {
		this.iagmt = iagmt;
	}

	public Date getUpdTime() {
		return updTime;
	}

	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}

	public Date getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public OrdStatus getOrdStatus() {
		return ordStatus;
	}

	public void setOrdStatus(OrdStatus ordStatus) {
		this.ordStatus = ordStatus;
	}

	public PayStatus getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(PayStatus payStatus) {
		this.payStatus = payStatus;
	}

	public Boolean getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Boolean delFlag) {
		this.delFlag = delFlag;
	}

	public Long getIcust() {
		return icust;
	}

	public void setIcust(Long icust) {
		this.icust = icust;
	}

	public String getOrdNo() {
		return ordNo;
	}

	public void setOrdNo(String ordNo) {
		this.ordNo = ordNo;
	}

	public List<TordDetail> getTordDetailList() {
		return tordDetailList;
	}

	public void setTordDetailList(List<TordDetail> tordDetailList) {
		this.tordDetailList = tordDetailList;
	}

	public String getTrace() {
		return trace;
	}

	public void setTrace(String trace) {
		this.trace = trace;
	}

	public BigDecimal getDepositUsed4Tord() {
		return depositUsed4Tord;
	}

	public void setDepositUsed4Tord(BigDecimal depositUsed4Tord) {
		this.depositUsed4Tord = depositUsed4Tord;
	}

	public BigDecimal getHavePaid() {
		return havePaid;
	}

	public void setHavePaid(BigDecimal havePaid) {
		this.havePaid = havePaid;
	}

	public BigDecimal getRemainShouldPay() {
		return remainShouldPay;
	}

	public void setRemainShouldPay(BigDecimal remainShouldPay) {
		this.remainShouldPay = remainShouldPay;
	}

	public Tagmt getTagmt() {
		return tagmt;
	}

	public void setTagmt(Tagmt tagmt) {
		this.tagmt = tagmt;
	}

	public BigDecimal getLockAmtOfDeposit() {
		return lockAmtOfDeposit;
	}

	public void setLockAmtOfDeposit(BigDecimal lockAmtOfDeposit) {
		this.lockAmtOfDeposit = lockAmtOfDeposit;
	}

	public BigDecimal getAmtOfDelivery() {
		return amtOfDelivery;
	}

	public void setAmtOfDelivery(BigDecimal amtOfDelivery) {
		this.amtOfDelivery = amtOfDelivery;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	

}
