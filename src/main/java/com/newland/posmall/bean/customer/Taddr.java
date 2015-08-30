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
 * 收货地址表
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_addr")
public class Taddr implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3735768781567672972L;

	@Id
	@TableGenerator(name = "i_addr", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_addr")
	@Column(name = "i_addr")
	private Long iaddr;//内部编号
    
	@Column(name = "i_cust")
	private Long icust;//客户id
	
	@Column(name = "name")
	private String name;//姓名
	
	@Column(name = "province")
	private String province;//省
	
	@Column(name = "city")
	private String city;//市
	
	/**
	 * 市、县
	 */
	@Column(name = "county")
	private String county;
	
	@Column(name = "area")
	private String area;//区
	
	@Column(name = "tel")
	private String tel;//电话
	
	@Column(name = "mobile")
	private String mobile;//手机
		
	@Column(name = "gen_time",updatable = false)
	private Date genTime;//生成时间
	
	@Column(name = "upd_time")
	private Date updTime;//更新时间
	
	/**
	 * 邮编
	 */
	@Column(name = "postal_code")
	private String postalCode;
	
	@Version
	private Long version;//版本
	
	@Column(name ="del_flag")
	@Type(type="yes_no")
	private Boolean delFlag;
	
	/**
	 * 地址拼装结果
	 */
	@Column(name = "result")
	private String result;
	
	public Long getIaddr() {
		return iaddr;
	}

	public void setIaddr(Long iaddr) {
		this.iaddr = iaddr;
	}

	public Long getIcust() {
		return icust;
	}

	public void setIcust(Long icust) {
		this.icust = icust;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
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

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
