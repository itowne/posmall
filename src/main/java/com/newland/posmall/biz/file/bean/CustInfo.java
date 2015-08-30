package com.newland.posmall.biz.file.bean;

import java.util.Date;

import org.ohuyo.commons.format.annotation.BeanClass;
import org.ohuyo.commons.format.annotation.BeanElement;
import org.ohuyo.commons.format.annotation.BeanElementType;
import org.ohuyo.commons.format.annotation.BeanField;
import org.ohuyo.rapid.file.Title;
/**
 * 客户信息表
 * @author Administrator
 *
 */
@BeanClass
public class CustInfo{
	
	@Title("客户编号")
	@BeanField(index = 0)
	private String custNo;//客户编号
	
	@Title("单位名称")
	@BeanField(index = 1)
	private String name;//单位名称
	
	@Title("单位全称")
	@BeanField(index = 2)
	private String longName;//单位全称
	
	@Title("信用等级")
	@BeanField(index = 3)
	private String creditLevel;//信用等级
	
	@Title("客户类型")
	@BeanField(index = 4)
	private String custType;//客户类型
	
	@Title("法人姓名")
	@BeanField(index = 5)
	private String corporationName;//法人姓名
	
	@Title("注册地址")
	@BeanField(index = 6)
	private String regAddr;//注册地址
	
	@Title("注册时间")
	@BeanField(index = 7, element = @BeanElement(type = BeanElementType.date, pattern="yyyy-MM-dd HH:mm:ss"))
	private Date regDate;//注册时间
	
	@Title("注册资金")
	@BeanField(index = 8)
	private String retCap;//注册资金
	
	@Title("公司简介")
	@BeanField(index = 9)
	private String summary;//公司简介
	
	@Title("营业执照编号")
	@BeanField(index = 10)
	private String busLic;//营业执照编号
	
	@Title("组织机构代码")
	@BeanField(index = 11)
	private String orgCode;//组织机构代码证编号
	
	@Title("税务登记证号")
	@BeanField(index = 12)
	private String taxReg;//税务登记证编号
	
	
	@Title("银行账号")
	@BeanField(index = 13)
	private String account;//银行账号
	
	@Title("公司类型")
	@BeanField(index = 14)
	private String companyType;//公司类型
	
	@Title("开户行")
	@BeanField(index = 15)
	private String bank;//开户行
	
	@Title("联系人姓名")
	@BeanField(index = 16)
	private String contactName;//联系人姓名
	
	@Title("电话")
	@BeanField(index = 17)
	private String tel;//电话
	
	@Title("移动电话")
	@BeanField(index = 18)
	private String mobile;//移动电话
	
	@Title("email")
	@BeanField(index = 19)
	private String email;
	
	@Title("传真")
	@BeanField(index = 20)
	private String fax;//传真
	
	@Title("状态")
	@BeanField(index = 21)
	private String custStatus;//状态
	
	@Title("生成时间")
	@BeanField(index = 22,element = @BeanElement(type = BeanElementType.date, pattern="yyyy-MM-dd HH:mm:ss"))
	private Date genTime;// 生成时间


	private Date updTime;// 更新时间
	
	private Long icust;//内部编号
	
	private String loginName;// 登录名

	private Long busLicIfile;//营业执照文件

	private Long orgCodeIfile;//组织机构代码证文件
	
	private Long taxRegIfile;//税务登记证文件
	
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

	public String getCustStatus() {
		return custStatus;
	}

	public void setCustStatus(String custStatus) {
		this.custStatus = custStatus;
	}

	public String getCreditLevel() {
		return creditLevel;
	}

	public void setCreditLevel(String creditLevel) {
		this.creditLevel = creditLevel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	public String getRegAddr() {
		return regAddr;
	}

	public void setRegAddr(String regAddr) {
		this.regAddr = regAddr;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public String getRetCap() {
		return retCap;
	}

	public void setRetCap(String retCap) {
		this.retCap = retCap;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getBusLic() {
		return busLic;
	}

	public void setBusLic(String busLic) {
		this.busLic = busLic;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getTaxReg() {
		return taxReg;
	}

	public void setTaxReg(String taxReg) {
		this.taxReg = taxReg;
	}

	public String getCorporationName() {
		return corporationName;
	}

	public void setCorporationName(String corporationName) {
		this.corporationName = corporationName;
	}

	public String getAccount() {
		return account;
	}
	
	public void setAccount(String account) {
		this.account = account;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public Long getBusLicIfile() {
		return busLicIfile;
	}

	public void setBusLicIfile(Long busLicIfile) {
		this.busLicIfile = busLicIfile;
	}

	public Long getOrgCodeIfile() {
		return orgCodeIfile;
	}

	public void setOrgCodeIfile(Long orgCodeIfile) {
		this.orgCodeIfile = orgCodeIfile;
	}

	public String getCustNo() {
		return custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	public Long getTaxRegIfile() {
		return taxRegIfile;
	}

	public void setTaxRegIfile(Long taxRegIfile) {
		this.taxRegIfile = taxRegIfile;
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
