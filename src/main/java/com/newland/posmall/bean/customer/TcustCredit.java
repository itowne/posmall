package com.newland.posmall.bean.customer;

import java.io.Serializable;
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

import com.newland.posmall.bean.dict.CreditLevel;

/**
 * 客户信用表
 * 
 * @author zhouym
 *
 */
@Entity
@Table(name = "t_cust_credit")
public class TcustCredit implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2996200833409283986L;

	/**
	 * 内部编号
	 */
	@Id
	@TableGenerator(name = "i_cust_credit", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_cust_credit")
	@Column(name = "i_cust_credit")
	private Long icustCredit;
	
	/**
	 * 客户id
	 */
	@Column(name = "i_cust")
	private Long icust;
	
	/**
	 * 订单外键
	 */
	@Column(name = "i_ord")
	private Long iord;
	
	/**
	 * 变更原因
	 */
	@Column(name = "change_reason")
	private String changeReason;
	
	/**
	 * 信用等级（优良中差）
	 */
	@Column(name = "credit_level")
	@Enumerated(EnumType.STRING)
	private CreditLevel creditLevel;
	
	/**
	 * 生成时间
	 */
	@Column(name = "gen_time",updatable = false)
	private Date genTime;
	
	/**
	 * 更新时间
	 */
	@Column(name = "upd_time")
	private Date updTime;

	public Long getIcustCredit() {
		return icustCredit;
	}

	public void setIcustCredit(Long icustCredit) {
		this.icustCredit = icustCredit;
	}

	public Long getIcust() {
		return icust;
	}

	public void setIcust(Long icust) {
		this.icust = icust;
	}

	public Long getIord() {
		return iord;
	}

	public void setIord(Long iord) {
		this.iord = iord;
	}

	public String getChangeReason() {
		return changeReason;
	}

	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}

	public CreditLevel getCreditLevel() {
		return creditLevel;
	}

	public void setCreditLevel(CreditLevel creditLevel) {
		this.creditLevel = creditLevel;
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
}
