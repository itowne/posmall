package com.newland.posmall.biz.file.bean;

import java.util.Date;
import org.ohuyo.commons.format.annotation.BeanClass;
import org.ohuyo.commons.format.annotation.BeanElement;
import org.ohuyo.commons.format.annotation.BeanElementType;
import org.ohuyo.commons.format.annotation.BeanField;
import org.ohuyo.rapid.file.Title;
/**
 * 
* @ClassName: LogisticsOrdReportInfo 
* @Description: YPOS销货执行报表  
* @author chenwenjing
* @date 2014-11-21 上午11:34:03
 */
@BeanClass
public class LogisticsOrdReportInfo {

	@Title("发货日期（客户下达发货通知的日期）")
	@BeanField(index = 0, element = @BeanElement(type = BeanElementType.date, pattern = "yyyy-MM-dd"))
	private Date genTime;// 发货日期（客户下达发货通知的日期）

	@Title("系统物流编号")
	@BeanField(index = 1)
	private String innerOrdno;//系统物流编号
	
	@Title("对应的点单单号")
	@BeanField(index = 2)
	private String ordNo; // 对应的点单单号

	@Title("对应的预约协议编号")
	@BeanField(index = 3)
	private String agmtNo; // 对应的预约协议编号

	@Title("客户名称")
	@BeanField(index = 4)
	private String custName;//客户名称

	@Title("产品名称")
	@BeanField(index = 5)
	private String pdtName;//产品名称
	
	@Title("发货数量")
	@BeanField(index = 6)
	private Integer logisticsNum;// 发货数量
	
	@Title("序列号段")
	@BeanField(index = 7)
	private String realSerial;// 序列号段
	
	@Title("物流公司")
	@BeanField(index = 8)
	private String logisticsComName;//物流公司
	
	@Title("物流单号")
	@BeanField(index = 9)
	private String realOrdno;//物流单号
	
	@Title("备注说明")
	@BeanField(index = 10)
	private String memo;//备注说明（导出时为空）
	
	@Title("物流单状态")
	@BeanField(index = 11)
	private String logisticsOrdStatus; // 物流单状态
	
	@Title("支付状态")
	@BeanField(index = 12)
	private String payStatus; // 支付状态
	
	@Title("联系人")
	@BeanField(index = 13)
	private String consigneeName; // 联系人

	@Title("联系电话")
	@BeanField(index = 14)
	private String consigneeMobile; // 联系电话
	
	@Title("发货地址")
	@BeanField(index = 15)
	private String consigneeAddr; // 发货地址
	
	@Title("实际发货时间")
	@BeanField(index = 16, element = @BeanElement(type = BeanElementType.date, pattern = "yyyy-MM-dd"))
	private Date realDelivery; // 实际发货时间
	
	@Title("实际到达时间")
	@BeanField(index = 17, element = @BeanElement(type = BeanElementType.date, pattern = "yyyy-MM-dd"))
	private Date realArrival; // 实际到达时间

	public Date getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}

	public String getInnerOrdno() {
		return innerOrdno;
	}

	public void setInnerOrdno(String innerOrdno) {
		this.innerOrdno = innerOrdno;
	}

	public String getOrdNo() {
		return ordNo;
	}

	public void setOrdNo(String ordNo) {
		this.ordNo = ordNo;
	}

	public String getAgmtNo() {
		return agmtNo;
	}

	public void setAgmtNo(String agmtNo) {
		this.agmtNo = agmtNo;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getPdtName() {
		return pdtName;
	}

	public void setPdtName(String pdtName) {
		this.pdtName = pdtName;
	}

	public Integer getLogisticsNum() {
		return logisticsNum;
	}

	public void setLogisticsNum(Integer logisticsNum) {
		this.logisticsNum = logisticsNum;
	}

	public String getRealSerial() {
		return realSerial;
	}

	public void setRealSerial(String realSerial) {
		this.realSerial = realSerial;
	}

	public String getLogisticsComName() {
		return logisticsComName;
	}

	public void setLogisticsComName(String logisticsComName) {
		this.logisticsComName = logisticsComName;
	}

	public String getRealOrdno() {
		return realOrdno;
	}

	public void setRealOrdno(String realOrdno) {
		this.realOrdno = realOrdno;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getLogisticsOrdStatus() {
		return logisticsOrdStatus;
	}

	public void setLogisticsOrdStatus(String logisticsOrdStatus) {
		this.logisticsOrdStatus = logisticsOrdStatus;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getConsigneeMobile() {
		return consigneeMobile;
	}

	public void setConsigneeMobile(String consigneeMobile) {
		this.consigneeMobile = consigneeMobile;
	}

	public String getConsigneeAddr() {
		return consigneeAddr;
	}

	public void setConsigneeAddr(String consigneeAddr) {
		this.consigneeAddr = consigneeAddr;
	}

	public Date getRealDelivery() {
		return realDelivery;
	}

	public void setRealDelivery(Date realDelivery) {
		this.realDelivery = realDelivery;
	}

	public Date getRealArrival() {
		return realArrival;
	}

	public void setRealArrival(Date realArrival) {
		this.realArrival = realArrival;
	}
	
	
	
    	
}
