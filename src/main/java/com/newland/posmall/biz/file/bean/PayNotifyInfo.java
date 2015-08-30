package com.newland.posmall.biz.file.bean;

import java.math.BigDecimal;
import java.util.Date;
import org.ohuyo.commons.format.annotation.BeanClass;
import org.ohuyo.commons.format.annotation.BeanElement;
import org.ohuyo.commons.format.annotation.BeanElementType;
import org.ohuyo.commons.format.annotation.BeanField;
import org.ohuyo.rapid.file.Title;

/**
 * 支付通知书信息
 * 
 * @author Administrator
 * 
 */
@BeanClass
public class PayNotifyInfo {

	@Title("通知书编号")
	@BeanField(index = 0)
	private String payNotifyNo;// 通知书编号

	@Title("客户名称")
	@BeanField(index = 1)
	private String custName;

	@Title("协议编号")
	@BeanField(index = 2)
	private String agmtNo;

	@Title("订单编号")
	@BeanField(index = 3)
	private String orderNo;

	@Title("物流编号")
	@BeanField(index = 4)
	private String logisticsNo;

	@Title("总金额")
	@BeanField(index = 5, element = @BeanElement(type = BeanElementType.bigDecimal, pattern = "0.00"))
	private BigDecimal totalAmt;

	@Title("支付类型")
	@BeanField(index = 6)
	private String payType;// 支付类型

	@Title("状态")
	@BeanField(index = 7)
	private String payNotifyStatus;// 状态

	@Title("备注")
	@BeanField(index = 8)
	private String memo;// 备注

	@Title("生成时间")
	@BeanField(index = 9, element = @BeanElement(type = BeanElementType.date, pattern = "yyyy-MM-dd HH:mm:ss"))
	private Date genTime;// 生成时间

	@Title("更新时间")
	@BeanField(index = 10, element = @BeanElement(type = BeanElementType.date, pattern = "yyyy-MM-dd HH:mm:ss"))
	private Date updTime;// 更新时间

	public String getPayNotifyNo() {
		return payNotifyNo;
	}

	public void setPayNotifyNo(String payNotifyNo) {
		this.payNotifyNo = payNotifyNo;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPayNotifyStatus() {
		return payNotifyStatus;
	}

	public void setPayNotifyStatus(String payNotifyStatus) {
		this.payNotifyStatus = payNotifyStatus;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getAgmtNo() {
		return agmtNo;
	}

	public void setAgmtNo(String agmtNo) {
		this.agmtNo = agmtNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getLogisticsNo() {
		return logisticsNo;
	}

	public void setLogisticsNo(String logisticsNo) {
		this.logisticsNo = logisticsNo;
	}

	public BigDecimal getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(BigDecimal totalAmt) {
		this.totalAmt = totalAmt;
	}

}
