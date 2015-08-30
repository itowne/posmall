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
import javax.persistence.Version;

import com.newland.posmall.bean.dict.PayStatus;
import com.newland.posmall.bean.dict.RenewStatus;

/**
 * 产品续保信息表
 * 
 * @author zhouym
 * @since 2014-10-21
 */
@Entity
@Table(name = "t_renew")
public class Trenew implements Serializable {

	private static final long serialVersionUID = -8224739266967358181L;

	@Id
	@TableGenerator(name = "i_renew", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_renew")
	@Column(name = "i_renew")
	private Long irenew;// 内部编号

	@Column(name = "renew_status")
	@Enumerated(EnumType.STRING)
	private RenewStatus renewStatus;// 续保状态

	@Column(name = "pay_status")
	@Enumerated(EnumType.STRING)
	private PayStatus payStatus;// 支付状态

	@Column(name = "renew_time")
	private Date renewTime;// 续保申请时间

	@Column(name = "renew_life")
	private Integer renewLife;// 续保期限
	
	@Column(name = "renew_amt")
	private BigDecimal renewAmt;// 续保合计费用

	@Column(name = "i_cust")
	private Long icust;// 续保申请人id

	@Column(name = "cust_name")
	private String custName; // 续保申请人姓名

	@Column(name = "i_user")
	private Long iuser;// 续保审批人id

	@Column(name = "user_name")
	private String userName; // 续保审批人姓名

	@Column(name = "gen_time", updatable = false)
	private Date genTime;// 生成时间

	@Column(name = "upd_time")
	private Date updTime;// 更新时间

	@Version
	private Long version;// 版本

	public Long getIrenew() {
		return irenew;
	}

	public void setIrenew(Long irenew) {
		this.irenew = irenew;
	}

	public RenewStatus getRenewStatus() {
		return renewStatus;
	}

	public void setRenewStatus(RenewStatus renewStatus) {
		this.renewStatus = renewStatus;
	}

	public PayStatus getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(PayStatus payStatus) {
		this.payStatus = payStatus;
	}

	public Date getRenewTime() {
		return renewTime;
	}

	public void setRenewTime(Date renewTime) {
		this.renewTime = renewTime;
	}

	public Integer getRenewLife() {
		return renewLife;
	}

	public void setRenewLife(Integer renewLife) {
		this.renewLife = renewLife;
	}

	public BigDecimal getRenewAmt() {
		return renewAmt;
	}

	public void setRenewAmt(BigDecimal renewAmt) {
		this.renewAmt = renewAmt;
	}

	public Long getIcust() {
		return icust;
	}

	public void setIcust(Long icust) {
		this.icust = icust;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public Long getIuser() {
		return iuser;
	}

	public void setIuser(Long iuser) {
		this.iuser = iuser;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

}
