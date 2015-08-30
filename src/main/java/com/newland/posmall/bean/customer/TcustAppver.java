package com.newland.posmall.bean.customer;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;
import org.hibernate.annotations.Type;

/**
 * 
* @ClassName: TcustAppver 
* @Description: 客户应用版本表 
* @author chenwenjing
* @date 2013-11-5 上午9:47:23
 */
@Entity
@Table(name = "t_cust_appver")
public class TcustAppver implements Serializable {

	private static final long serialVersionUID = 3025700377329437729L;

	@Id
	@TableGenerator(name = "i_cust_appver", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_cust_appver")
	@Column(name = "i_cust_appver")
	private Long icustAppver;// 内部编号

	@Column(name = "cust_no")
	private String custNo;// 客户编号

	@Column(name = "name")
	private String name;// 单位名称

	@Column(name = "i_cust")
	private Long icust;// 客户内部编号

	@Column(name = "appver")
	private String appver;// 应用版本

	@Column(name = "gen_time", updatable = false)
	private Date genTime;// 生成时间

	@Column(name = "upd_time")
	private Date updTime;// 更新时间

	@Version
	private Long version;// 版本
	
	@Column(name = "del_flag")
	@Type(type = "yes_no")
	private Boolean delFlag;// 删除标志

	public Long getIcustAppver() {
		return icustAppver;
	}

	public void setIcustAppver(Long icustAppver) {
		this.icustAppver = icustAppver;
	}

	public String getCustNo() {
		return custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getIcust() {
		return icust;
	}

	public void setIcust(Long icust) {
		this.icust = icust;
	}

	public String getAppver() {
		return appver;
	}

	public void setAppver(String appver) {
		this.appver = appver;
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

	public Boolean getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Boolean delFlag) {
		this.delFlag = delFlag;
	}

	
	
   
}
