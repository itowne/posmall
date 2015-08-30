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
* @ClassName: OrdReportInfo 
* @Description: YPOS点单执行报表  
* @author chenwenjing
* @date 2014-11-21 上午9:29:34
 */
@BeanClass
public class OrdReportInfo {

	@Title("点单日期（客户下达的日期）")
	@BeanField(index = 0, element = @BeanElement(type = BeanElementType.date, pattern = "yyyy-MM-dd"))
	private Date genTime;// 点单日期（客户下达的日期）

	@Title("点单单号")
	@BeanField(index = 1)
	private String ordNo;//点单单号

	@Title("对应的预约协议编号")
	@BeanField(index = 2)
	private String agmtNo; // 对应的预约协议编号

	@Title("客户名称")
	@BeanField(index = 3)
	private String custName;//客户名称

	@Title("产品名称")
	@BeanField(index = 4)
	private String pdtName;//产品名称
	
	@Title("点单数量")
	@BeanField(index = 5)
	private Integer ordNum;// 点单数量
	
	@Title("已出货量")
	@BeanField(index = 6)
	private Integer usedQuota;// 已出货量

	@Title("剩余出货量")
	@BeanField(index = 7)
	private Integer remainQuota;// 剩余出货量
	
	@Title("备注（系统的灌装程序备注）")
	@BeanField(index = 8)
	private String remark;//备注（系统的灌装程序备注）
	
	@Title("备注")
	@BeanField(index = 9)
	private String memo;//备注
	
	@Title("订单状态")
	@BeanField(index = 10)
	private String ordStatus;//订单状态
	
	@Title("支付状态")
	@BeanField(index = 11)
	private String payStatus; // 支付状态
	
	@Title("点单金额")
	@BeanField(index = 12, element = @BeanElement(type = BeanElementType.bigDecimal, pattern = "0.00"))
	private BigDecimal amt; // 点单金额

	public Date getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = genTime;
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

	public Integer getOrdNum() {
		return ordNum;
	}

	public void setOrdNum(Integer ordNum) {
		this.ordNum = ordNum;
	}

	public Integer getUsedQuota() {
		return usedQuota;
	}

	public void setUsedQuota(Integer usedQuota) {
		this.usedQuota = usedQuota;
	}

	public Integer getRemainQuota() {
		return remainQuota;
	}

	public void setRemainQuota(Integer remainQuota) {
		this.remainQuota = remainQuota;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}
	
    	
}
