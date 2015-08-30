package com.newland.posmall.bean.customer;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import com.newland.posmall.bean.dict.CompanyType;
import com.newland.posmall.bean.dict.CustType;
import com.newland.posmall.bean.dict.InvoiceType;
/**
 * 客户注册信息表
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_cust_reg")
public class TcustReg implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4074811757430592178L;

	@Id
	@Column(name = "i_cust")
	private Long icust;//内部编号
    
	@Column(name = "name")
	private String name;//单位名称
	
	@Column(name = "long_name")
	private String longName;//单位全称
	
	@Column(name = "cust_type")
	@Enumerated(EnumType.STRING)
	private CustType custType;//客户类型
	
	@Column(name = "contact_name")
	private String contactName;//联系人姓名
	
	@Column(name = "tel")
	private String tel;//电话
	
	@Column(name = "mobile")
	private String mobile;//移动电话
	
	@Column (name = "email")
	private String email;
	
	@Column(name = "fax")
	private String fax;//传真
	
	@Column(name = "reg_date")
	private Date regDate;//注册时间
	
	@Column(name = "ret_cap")
	private String retCap;//注册资金
	
	@Column(name = "summary")
	private String summary;//公司简介
	
	@Column(name = "bus_lic")
	private String busLic;//营业执照编号
	
	@Column(name = "org_code")
	private String orgCode;//组织机构代码证编号
	
	@Column(name = "tax_reg")
	private String taxReg;//税务登记证编号
	
	@Column(name = "corporation_name")
	private String corporationName;//法人姓名
	
	@Column(name = "company_type")
	@Enumerated(EnumType.STRING)
	private CompanyType companyType;//公司类型
	
	/**
	 * 营业执照文件
	 */
	@Column(name = "bus_lic_ifile")
	private Long busLicIfile;
	
	/**
	 * 组织机构代码证文件
	 */
	@Column(name = "org_code_ifile")
	private Long orgCodeIfile;
	
	/**
	 * 客户编号
	 */
	@Column(name = "cust_no")
	private String custNo;
	
	/**
	 * 税务登记证文件
	 */
	@Column(name = "tax_reg_ifile")
	private Long taxRegIfile;
	
	@Column(name = "upd_time")
	private Date updTime;//更新时间
	
	@Column(name = "gen_time",updatable = false)
	private Date genTime;//生成时间
	
	@Version
	private Long version;//版本
	
	/**
	 * 审核不通过理由
	 */
	@Column(name = "refuse_reason")
	private String refuseReason;
	
	/**
	 * 采购框架合同编号
	 */
	@Column(name = "contract_no")
	private String contractNo;
	
	/**
	 * 签署合同时间
	 */
	@Column(name = "signature_date")
	private Date signatureDate;
	
	
	/********************开票信息*********************/
	/**
	 * 票面单位
	 */
	@Column(name = "invoice_corporation")
	private String invoiceCorporation;
	
	/**
	 * 发票类型
	 */
	@Column(name = "invoice_type")
	@Enumerated(EnumType.STRING)
	private InvoiceType invoiceType;
	
	/**
	 * 税号
	 */
	@Column(name = "tax_id")
	private String taxID;
	
	/**
	 * 开户行
	 */
	@Column(name = "bank")
	private String bank;
	
	/**
	 * 银行账号
	 */
	@Column(name = "account")
	private String account;
	
	/**
	 * 注册地址
	 */
	@Column(name = "reg_addr")
	private String regAddr;
	
	/**
	 * 注册电话
	 */
	@Column(name = "reg_tel")
	private String regTel;
	
	/**
	 * 其它
	 */
	@Column(name = "remarks")
	private String remarks;
	
	@Column(name = "salesman_name")
	private String salesmanName;//业务员姓名
	
	@Column(name = "salesman_email")
	private String salesmanEmail;//业务员邮箱

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

	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	public CustType getCustType() {
		return custType;
	}

	public void setCustType(CustType custType) {
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

	public CompanyType getCompanyType() {
		return companyType;
	}

	public void setCompanyType(CompanyType companyType) {
		this.companyType = companyType;
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

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Date getSignatureDate() {
		return signatureDate;
	}

	public void setSignatureDate(Date signatureDate) {
		this.signatureDate = signatureDate;
	}

	public String getInvoiceCorporation() {
		return invoiceCorporation;
	}

	public void setInvoiceCorporation(String invoiceCorporation) {
		this.invoiceCorporation = invoiceCorporation;
	}

	public InvoiceType getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(InvoiceType invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getTaxID() {
		return taxID;
	}

	public void setTaxID(String taxID) {
		this.taxID = taxID;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getRegAddr() {
		return regAddr;
	}

	public void setRegAddr(String regAddr) {
		this.regAddr = regAddr;
	}

	public String getRegTel() {
		return regTel;
	}

	public void setRegTel(String regTel) {
		this.regTel = regTel;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getSalesmanName() {
		return salesmanName;
	}

	public void setSalesmanName(String salesmanName) {
		this.salesmanName = salesmanName;
	}

	public String getSalesmanEmail() {
		return salesmanEmail;
	}

	public void setSalesmanEmail(String salesmanEmail) {
		this.salesmanEmail = salesmanEmail;
	}
	

}
