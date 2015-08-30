package com.newland.posmall.bean.customer;

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

import com.newland.posmall.bean.dict.ValidStatus;

/**
 * 客户折扣率表
 * 
 * @author zhouym
 *
 */
@Entity
@Table(name = "t_cust_rate")
public class TcustRate implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3010180650629068631L;

	/**
	 * 内部编号
	 */
	@Id
	@TableGenerator(name = "i_cust_rate", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_cust_rate")
	@Column(name = "i_cust_rate")
	private Long icustRate;
	
	/**
	 * 客户id
	 */
	@Column(name = "i_cust")
	private Long icust;
	
	/**
	 * 产品id
	 */
	@Column(name = "i_pdt")
	private Long ipdt;
	
	/**
	 * 折扣率
	 */
	@Column(name = "rate")
	private BigDecimal rate;
	
	/**
	 * 状态
	 */
	@Column(name = "cust_rate_status")
	@Enumerated(EnumType.STRING)
	private ValidStatus custRateStatus;
	
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

	public Long getIcustRate() {
		return icustRate;
	}

	public void setIcustRate(Long icustRate) {
		this.icustRate = icustRate;
	}

	public Long getIcust() {
		return icust;
	}

	public void setIcust(Long icust) {
		this.icust = icust;
	}

	public Long getIpdt() {
		return ipdt;
	}

	public void setIpdt(Long ipdt) {
		this.ipdt = ipdt;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public void setCustRateStatus(ValidStatus custRateStatus) {
		this.custRateStatus = custRateStatus;
	}
	
	
	public ValidStatus getCustRateStatus() {
		return custRateStatus;
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
