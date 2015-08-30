package com.newland.posmall.bean.customer.condition;

import java.util.Date;
import java.util.List;

import org.ohuyo.commons.query.criterion.DetachedCriteriaEx;
import org.ohuyo.commons.query.criterion.annotation.CriteriaClass;
import org.ohuyo.commons.query.criterion.annotation.Expression;
import org.ohuyo.commons.query.criterion.annotation.LikeMatchMode;
import org.ohuyo.commons.query.criterion.annotation.Operator;

import com.newland.posmall.bean.customer.Twarranty;
import com.newland.posmall.bean.dict.WarrantyStatus;

@CriteriaClass(Twarranty.class)
public class TwarrantyCondition extends DetachedCriteriaEx{

	private static final long serialVersionUID = -6479571239042955856L;

	@Expression(operator = Operator.like, propertyName = "seqNo", likeMatchMode = LikeMatchMode.anywhere)
	private String seqNo;// 产品序列号

	@Expression(operator = Operator.like, propertyName = "pdtNo", likeMatchMode = LikeMatchMode.anywhere)
	private String pdtNo;// 产品型号
    
	@Expression(operator = Operator.eq, propertyName = "warrantyStatus")
	private WarrantyStatus warrantyStatus;// 状态
	
	@Expression(operator = Operator.in, propertyName = "warrantyStatus")
	private List<WarrantyStatus> warrantyStatusList;// 状态

	@Expression(operator = Operator.in, propertyName = "icust")
	private List<Long> icustList;// 操作人id
	
	@Expression(operator = Operator.like, propertyName = "custName")
	private String custName;
	
	@Expression(operator = Operator.ge, propertyName = "warrantyTime")
	private Date startDate;
	
	@Expression(operator = Operator.le, propertyName = "warrantyTime")
	private Date endDate;

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

	public List<WarrantyStatus> getWarrantyStatusList() {
		return warrantyStatusList;
	}

	public void setWarrantyStatusList(List<WarrantyStatus> warrantyStatusList) {
		this.warrantyStatusList = warrantyStatusList;
	}

	public List<Long> getIcustList() {
		return icustList;
	}

	public void setIcustList(List<Long> icustList) {
		this.icustList = icustList;
	}

	public WarrantyStatus getWarrantyStatus() {
		return warrantyStatus;
	}

	public void setWarrantyStatus(WarrantyStatus warrantyStatus) {
		this.warrantyStatus = warrantyStatus;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
    
}
