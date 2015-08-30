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


/**
 * 收货地址表
 * 
 * @author zhouym
 * 
 */
@Entity
@Table(name = "t_addr_his")
public class TaddrHis implements Serializable {

	private static final long serialVersionUID = 6327246758366919065L;

	@Id
	@TableGenerator(name = "i_addr_his", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_addr_his")
	@Column(name = "i_addr_his")
	private Long iaddrHis;
	
	@Column(name = "i_addr")
	private Long iaddr;

	/**
	 * 客户id
	 */
	@Column(name = "i_cust")
	private Long icust;

	/**
	 * 收货人姓名
	 */
	@Column(name = "name")
	private String name;

	/**
	 * 省
	 */
	@Column(name = "province")
	private String province;

	/**
	 * 市
	 */
	@Column(name = "city")
	private String city;

	/**
	 * 市、县
	 */
	@Column(name = "county")
	private String county;

	/**
	 * 区
	 */
	@Column(name = "area")
	private String area;

	/**
	 * 电话号码
	 */
	@Column(name = "tel")
	private String tel;

	/**
	 * 手机号
	 */
	@Column(name = "mobile")
	private String mobile;
	
	/**
	 * 版本号
	 */
	@Column(name = "ver")
	private Long ver;

	/**
	 * 生成时间
	 */
	@Column(name = "gen_time")
	private Date genTime;

	/**
	 * 邮编
	 */
	@Column(name = "postal_code")
	private String postalCode;
	
	/**
	 * 地址拼装结果
	 */
	@Column(name = "result")
	private String result;

	public Long getIaddrHis() {
		return iaddrHis;
	}

	public void setIaddrHis(Long iaddrHis) {
		this.iaddrHis = iaddrHis;
	}

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

	public Long getVer() {
		return ver;
	}

	public void setVer(Long ver) {
		this.ver = ver;
	}

	public Date getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
