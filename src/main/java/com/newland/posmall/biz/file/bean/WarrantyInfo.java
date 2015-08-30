package com.newland.posmall.biz.file.bean;

import java.util.Date;
import org.ohuyo.commons.format.annotation.BeanClass;
import org.ohuyo.commons.format.annotation.BeanElement;
import org.ohuyo.commons.format.annotation.BeanElementType;
import org.ohuyo.commons.format.annotation.BeanField;
import org.ohuyo.rapid.file.Title;

/**
 * 
 * @ClassName: WarrantyInfo
 * @Description: 报修受理信息表
 * @author chenwenjing
 * @date 2014-9-6 上午10:55:31
 * 
 */
@BeanClass
public class WarrantyInfo {

	@Title("受理编号")
	@BeanField(index = 0)
	private Long iwarranty;//受理编号 

	@Title("产品序列号")
	@BeanField(index = 1)
	private String seqNo;// 产品序列号

	@Title("产品型号")
	@BeanField(index = 2)
	private String pdtNo;// 产品型号

	@Title("报修时间")
	@BeanField(index = 3,element = @BeanElement(type = BeanElementType.date, pattern = "yyyy-MM-dd"))
	private Date warrantyTime;// 报修时间

	@Title("受理时间")
	@BeanField(index = 4, element = @BeanElement(type = BeanElementType.date, pattern = "yyyy-MM-dd"))
	private Date acceptTime; // 受理时间

	@Title("客户姓名")
	@BeanField(index = 5)
	private String custName; // 客户姓名

	@Title("备注")
	@BeanField(index = 6)
	private String remark; // 备注

	public Long getIwarranty() {
		return iwarranty;
	}

	public void setIwarranty(Long iwarranty) {
		this.iwarranty = iwarranty;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public String getPdtNo() {
		return pdtNo;
	}

	public void setPdtNo(String pdtNo) {
		this.pdtNo = pdtNo;
	}

	public Date getWarrantyTime() {
		return warrantyTime;
	}

	public void setWarrantyTime(Date warrantyTime) {
		this.warrantyTime = warrantyTime;
	}

	public Date getAcceptTime() {
		return acceptTime;
	}

	public void setAcceptTime(Date acceptTime) {
		this.acceptTime = acceptTime;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	

	

}
