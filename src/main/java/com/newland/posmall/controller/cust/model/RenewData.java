package com.newland.posmall.controller.cust.model;

import java.util.Date;

import org.ohuyo.commons.format.annotation.BeanClass;
import org.ohuyo.commons.format.annotation.BeanField;
import org.ohuyo.rapid.file.Title;

@BeanClass
public class RenewData {

	@Title("产品序列号")
	@BeanField(index = 0)
	private String seqNo;

	@Title("产品型号")
	@BeanField(index = 1)
	private String pdtNo;
	
	private Long ierpMaintenance;// 维保信息表外键

	private Date lifeStartTime;// 保修期起始时间

	private Date lifeEndTime;// 保修期结束时间

	private String errTips;// 数据错误提示

	public Long getIerpMaintenance() {
		return ierpMaintenance;
	}

	public void setIerpMaintenance(Long ierpMaintenance) {
		this.ierpMaintenance = ierpMaintenance;
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

	public Date getLifeStartTime() {
		return lifeStartTime;
	}

	public void setLifeStartTime(Date lifeStartTime) {
		this.lifeStartTime = lifeStartTime;
	}

	public Date getLifeEndTime() {
		return lifeEndTime;
	}

	public void setLifeEndTime(Date lifeEndTime) {
		this.lifeEndTime = lifeEndTime;
	}

	public String getErrTips() {
		return errTips;
	}

	public void setErrTips(String errTips) {
		this.errTips = errTips;
	}
}
