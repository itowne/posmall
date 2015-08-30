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
import javax.persistence.Transient;
import javax.persistence.Version;

import org.ohuyo.rapid.base.User;

import com.newland.posmall.bean.dict.CreditLevel;
import com.newland.posmall.bean.dict.CustStatus;

/**
 * 客户表
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_cust")
public class Tcust implements Serializable, User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1057018511940661188L;

	@Id
	@TableGenerator(name = "i_cust", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_cust")
	@Column(name = "i_cust")
	private Long icust;// 内部编号

	@Column(name = "login_name")
	private String loginName;// 登录名

	@Column(name = "password")
	private String password;// 密码

	/**
	 * 状态
	 */
	@Column(name = "cust_status")
	@Enumerated(EnumType.STRING)
	private CustStatus custStatus;

	/**
	 * 信用等级
	 */
	@Column(name = "credit_level")
	@Enumerated(EnumType.STRING)
	private CreditLevel creditLevel;
	
	/**
	 * 客户注册信息
	 */
	@Transient
	private TcustReg tcustReg;
	
	@Column(name = "gen_time", updatable = false)
	private Date genTime;// 生成时间

	@Column(name = "upd_time")
	private Date updTime;// 更新时间

	@Version
	private Long version;// 版本号
	
	/**
	 * 注册码
	 */
	@Column(name = "cust_code")
	private String custCode;

	public Long getIcust() {
		return icust;
	}

	public void setIcust(Long icust) {
		this.icust = icust;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public CustStatus getCustStatus() {
		return custStatus;
	}

	public void setCustStatus(CustStatus custStatus) {
		this.custStatus = custStatus;
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

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public TcustReg getTcustReg() {
		return tcustReg;
	}

	public void setTcustReg(TcustReg tcustReg) {
		this.tcustReg = tcustReg;
	}

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
}
