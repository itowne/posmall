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

import com.newland.posmall.bean.dict.OrdStatus;
import com.newland.posmall.bean.dict.PayStatus;
/**
 * 订单历史表
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_ord_his")
public class TordHis implements Serializable {

	private static final long serialVersionUID = 2801092965977700292L;

	@Id
	@TableGenerator(name = "i_ord_his", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_ord_his")
	@Column(name = "i_ord_his")
	private Long iordHis;//订单内部编号
	
	@Column(name = "i_ord")
	private Long iord;//订单外键

	@Column(name = "i_agmt")
	private Long iagmt;//协议内部编号
	
	@Column(name = "i_cust")
	private Long icust;//客户外键

	@Column(name = "upd_time")
	private Date updTime;//更新时间

	@Column(name = "gen_time",updatable = false)
	private Date genTime;//生成时间

	@Column(name = "amt")
	private BigDecimal amt;//金额

	@Column(name = "ord_status")
	@Enumerated(EnumType.STRING)
	private OrdStatus ordStatus;//订单状态

	@Column(name = "pay_status")
	@Enumerated(EnumType.STRING)
	private PayStatus payStatus;//支付状态
	
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

	public Long getIordHis() {
		return iordHis;
	}

	public void setIordHis(Long iordHis) {
		this.iordHis = iordHis;
	}

	public Long getIagmt() {
		return iagmt;
	}

	public void setIagmt(Long iagmt) {
		this.iagmt = iagmt;
	}

	public Long getIord() {
		return iord;
	}

	public void setIord(Long iord) {
		this.iord = iord;
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

	public Long getIcust() {
		return icust;
	}

	public void setIcust(Long icust) {
		this.icust = icust;
	}

	public String getTrace() {
		return trace;
	}

	public void setTrace(String trace) {
		this.trace = trace;
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
    
}
