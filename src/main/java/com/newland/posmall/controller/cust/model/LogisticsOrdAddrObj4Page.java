package com.newland.posmall.controller.cust.model;

import org.ohuyo.commons.format.annotation.BeanClass;
import org.ohuyo.commons.format.annotation.BeanField;
import org.ohuyo.rapid.file.Title;

@BeanClass
public class LogisticsOrdAddrObj4Page {

	@Title("产品型号")
	@BeanField(index = 0)
	private String pdtNo;

	@Title("数量")
	@BeanField(index = 1)
	private String num;

	@Title("收货地址")
	@BeanField(index = 2)
	private String consigneeAddr;

	@Title("姓名")
	@BeanField(index = 3)
	private String name;
	
	@Title("手机")
	@BeanField(index = 4)
	private String mobile;
	
	@Title("是否收费物流")
	@BeanField(index = 5)
	private String feeFlag;
	
	public String getConsigneeAddr() {
		return consigneeAddr;
	}

	public void setConsigneeAddr(String consigneeAddr) {
		this.consigneeAddr = consigneeAddr;
	}

	public String getPdtNo() {
		return pdtNo;
	}

	public void setPdtNo(String pdtNo) {
		this.pdtNo = pdtNo;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getFeeFlag() {
		return feeFlag;
	}

	public void setFeeFlag(String feeFlag) {
		this.feeFlag = feeFlag;
	}

}
