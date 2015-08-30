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
import javax.persistence.Version;

import com.newland.posmall.bean.dict.CustCodeStatus;

/**
 * 注册码
 * @author zhouym
 *
 */
@Entity
@Table(name = "t_cust_code")
public class TcustCode implements Serializable {

	private static final long serialVersionUID = -4882323505456278307L;

	/**
	 * id
	 */
	@Id
	@TableGenerator(name = "i_cust_code", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_cust_code")
	@Column(name = "i_cust_code")
	private Long icustCode;

	/**
	 * 注册码
	 */
	@Column(name = "cust_code")
	private String custCode;

	/**
	 * 状态
	 */
	@Column(name = "cust_code_status")
	@Enumerated(EnumType.STRING)
	private CustCodeStatus custCodeStatus;
	
	@Column(name = "i_cust")
	private Long icust;
	
	/**
	 * 公司名称
	 */
	@Column(name = "company_name")
	private String companyName;
	
	/**
	 * 操作人编号
	 */
	@Column(name = "i_user")
	private Long iuser;
	
	/**
	 * 操作人姓名
	 */
	@Column(name = "user_name")
	private String userName;
	
	/**
	 * 生成时间
	 */
	@Column(name = "gen_time")
	private Date genTime;

	/**
	 * 更新时间
	 */
	@Column(name = "upd_time")
	private Date updTime;// 更新时间

	/**
	 * 版本
	 */
	@Version
	private Long version;// 版本号

	public Long getIcustCode() {
		return icustCode;
	}

	public void setIcustCode(Long icustCode) {
		this.icustCode = icustCode;
	}

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public CustCodeStatus getCustCodeStatus() {
		return custCodeStatus;
	}

	public void setCustCodeStatus(CustCodeStatus custCodeStatus) {
		this.custCodeStatus = custCodeStatus;
	}

	public Long getIcust() {
		return icust;
	}

	public void setIcust(Long icust) {
		this.icust = icust;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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
