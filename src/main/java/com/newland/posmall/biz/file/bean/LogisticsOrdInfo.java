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
 * @ClassName: LogisticsOrdInfo
 * @Description: 物流单信息表
 * @author chenwenjing
 * @date 2014-9-6 上午10:55:31
 * 
 */
@BeanClass
public class LogisticsOrdInfo {
	
	@Title("客户名称")
	@BeanField(index = 0)
	private String custName;
	
	@Title("订单编号")
	@BeanField(index = 1)
	private String ordNo;
	

	@Title("预计发货日期")
	@BeanField(index = 2, element = @BeanElement(type = BeanElementType.date, pattern = "yyyy-MM-dd"))
	private Date specifyDelivery;// 预计发货日期
	
	@Title("订单系统物流单号")
	@BeanField(index = 3)
	private String innerOrdno; // 物流单号
	
	@Title("ERP订单号")
	@BeanField(index = 4)
	private String erpOrdId; // ERP订单号

	@Title("产品型号")
	@BeanField(index = 5)
	private String pdtName;

	@Title("数量")
	@BeanField(index = 6)
	private Integer num;// 数量
	

	@Title("收货地址")
	@BeanField(index = 7)
	private String addrName;

	@Title("收货人")
	@BeanField(index = 8)
	private String consignee;
	
	@Title("收货电话")
	@BeanField(index = 9)
	private String phone;
	
	@Title("是否收费")
	@BeanField(index = 10)
	private String isPay;

	@Title("规格")
	@BeanField(index = 11)
	private String model;

	@Title("产品序号")
	@BeanField(index = 12)
	private String sn;

	@Title("生成日期")
	@BeanField(index = 13, element = @BeanElement(type = BeanElementType.date, pattern = "yyyy-MM-dd HH:mm"))
	private Date genTime;

	@Title("货运方式")
	@BeanField(index = 14)
	private String freightWay;

	@Title("运费")
	@BeanField(index = 15,element = @BeanElement(type = BeanElementType.bigDecimal, pattern = "0.00"))
	private BigDecimal freight;

	public String getInnerOrdno() {
		return innerOrdno;
	}

	public void setInnerOrdno(String innerOrdno) {
		this.innerOrdno = innerOrdno;
	}

	public String getPdtName() {
		return pdtName;
	}

	public void setPdtName(String pdtName) {
		this.pdtName = pdtName;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Date getSpecifyDelivery() {
		return specifyDelivery;
	}

	public void setSpecifyDelivery(Date specifyDelivery) {
		this.specifyDelivery = specifyDelivery;
	}

	public Date getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}

	public String getFreightWay() {
		return freightWay;
	}

	public void setFreightWay(String freightWay) {
		this.freightWay = freightWay;
	}

	public String getIsPay() {
		return isPay;
	}

	public void setIsPay(String isPay) {
		this.isPay = isPay;
	}

	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public String getAddrName() {
		return addrName;
	}

	public void setAddrName(String addrName) {
		this.addrName = addrName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getOrdNo() {
		return ordNo;
	}

	public void setOrdNo(String ordNo) {
		this.ordNo = ordNo;
	}

	public String getErpOrdId() {
		return erpOrdId;
	}

	public void setErpOrdId(String erpOrdId) {
		this.erpOrdId = erpOrdId;
	}
    
}
