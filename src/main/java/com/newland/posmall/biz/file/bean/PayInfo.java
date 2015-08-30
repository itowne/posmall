package com.newland.posmall.biz.file.bean;

import java.math.BigDecimal;
import java.util.Date;
import org.ohuyo.commons.format.annotation.BeanClass;
import org.ohuyo.commons.format.annotation.BeanElement;
import org.ohuyo.commons.format.annotation.BeanElementType;
import org.ohuyo.commons.format.annotation.BeanField;
import org.ohuyo.rapid.file.Title;

/**
 * 
* @ClassName: PayInfo  
* @Description: 付款凭证信息表  
* @author chenwenjing
* @date 2014-9-6 上午11:29:25  
*
 */
@BeanClass
public class PayInfo{
    
	@Title("支付序列号")
	@BeanField(index = 0)
	private String payNo;//支付序列号
	
	@Title("支付类型")
	@BeanField(index = 1)
	private String payType;//支付类型
    
	@Title("状态")
	@BeanField(index = 2)
	private String payStatus;//状态
	
	@Title("金额")
	@BeanField(index = 3,element = @BeanElement(type = BeanElementType.bigDecimal, pattern="0.00"))
	private BigDecimal amt;//金额
    
	@Title("汇款单编号")
	@BeanField(index = 4)
	private String remittanceNo;// 汇款单编号
	
//	@Title("汇款账号")
//	@BeanField(index = 5)
//	private String account;//汇款账号
//    
//	@Title("开户行")
//	@BeanField(index = 6)
//	private String bank;//开户行	
	
	@Title("客户姓名")
	@BeanField(index = 5)
	private String custName;//客户姓名
	
	@Title("用户姓名")
	@BeanField(index = 6)
	private String userName;//用户姓名
	
	@Title("生成时间")
	@BeanField(index = 7,element = @BeanElement(type = BeanElementType.date, pattern="yyyy-MM-dd HH:mm:ss"))
	private Date genTime;//生成时间
    
	@Title("更新时间")
	@BeanField(index = 8,element = @BeanElement(type = BeanElementType.date, pattern="yyyy-MM-dd HH:mm:ss"))
	private Date updTime;//更新时间

	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public String getRemittanceNo() {
		return remittanceNo;
	}

	public void setRemittanceNo(String remittanceNo) {
		this.remittanceNo = remittanceNo;
	}

//	public String getAccount() {
//		return account;
//	}
//
//	public void setAccount(String account) {
//		this.account = account;
//	}
//
//	public String getBank() {
//		return bank;
//	}
//
//	public void setBank(String bank) {
//		this.bank = bank;
//	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
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
	
	
	
}
