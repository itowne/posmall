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
 * @ClassName: OrderInfo
 * @Description: 订单列表
 * @author chenwenjing
 * @date 2014-9-6 上午9:37:01
 * 
 */
@BeanClass
public class OrderInfo {

	@Title("订单编号")
	@BeanField(index = 0)
	private String ordNo;// 订单编号

	@Title("产品名称")
	@BeanField(index = 1)
	private String pdtName;

	@Title("数量")
	@BeanField(index = 2)
	private Integer num;// 数量

	@Title("号段区间起始值")
	@BeanField(index = 3)
	private String startSn;

	@Title("号段区间结束值")
	@BeanField(index = 4)
	private String endSn;

	@Title("客户名称")
	@BeanField(index = 5)
	private String custName;

	@Title("订单状态")
	@BeanField(index = 6)
	private String ordStatus; // 订单状态

	@Title("支付状态")
	@BeanField(index = 7)
	private String payStatus; // 支付状态

	@Title("存量类型")
	@BeanField(index = 8)
	private String ordDetailPdtType;

	@Title("需求时间")
	@BeanField(index = 9)
	private String requiredTime;

	@Title("生成时间")
	@BeanField(index = 10, element = @BeanElement(type = BeanElementType.date, pattern = "yyyy/MM/dd HH:mm"))
	private Date genTime;// 生成时间

	public String getOrdNo() {
		return ordNo;
	}

	public void setOrdNo(String ordNo) {
		this.ordNo = ordNo;
	}

	public String getOrdStatus() {
		return ordStatus;
	}

	public void setOrdStatus(String ordStatus) {
		this.ordStatus = ordStatus;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Date getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}

	public String getPdtName() {
		return pdtName;
	}

	public void setPdtName(String pdtName) {
		this.pdtName = pdtName;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getRequiredTime() {
		return requiredTime;
	}

	public void setRequiredTime(String requiredTime) {
		this.requiredTime = requiredTime;
	}

	public String getStartSn() {
		return startSn;
	}

	public void setStartSn(String startSn) {
		this.startSn = startSn;
	}

	public String getEndSn() {
		return endSn;
	}

	public void setEndSn(String endSn) {
		this.endSn = endSn;
	}

	public String getOrdDetailPdtType() {
		return ordDetailPdtType;
	}

	public void setOrdDetailPdtType(String ordDetailPdtType) {
		this.ordDetailPdtType = ordDetailPdtType;
	}

}
