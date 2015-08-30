package com.newland.posmall.bean.customer.condition;

import java.util.Date;
import java.util.List;

import org.ohuyo.commons.query.criterion.DetachedCriteriaEx;
import org.ohuyo.commons.query.criterion.annotation.CriteriaClass;
import org.ohuyo.commons.query.criterion.annotation.Expression;
import org.ohuyo.commons.query.criterion.annotation.Operator;

import com.newland.posmall.bean.customer.Trenew;
import com.newland.posmall.bean.dict.PayStatus;
import com.newland.posmall.bean.dict.RenewStatus;

@CriteriaClass(Trenew.class)
public class TrenewCondition extends DetachedCriteriaEx {

	private static final long serialVersionUID = 8418382192767862931L;
    
	@Expression(operator = Operator.eq, propertyName = "renewStatus")
	private RenewStatus renewStatus;// 续保状态
	
	@Expression(operator = Operator.eq, propertyName = "payStatus")
	private PayStatus payStatus;// 支付状态
	
	@Expression(operator = Operator.in, propertyName = "renewStatus")
	private List<RenewStatus> renewStatusList;// 续保状态

	@Expression(operator = Operator.in, propertyName = "payStatus")
	private List<PayStatus> payStatusList;// 支付状态

	@Expression(operator = Operator.in, propertyName = "icust")
	private List<Long> icustList;// 续保申请人id
	
	@Expression(operator = Operator.like, propertyName = "custName")
	private String custName;
	
	@Expression(operator = Operator.ge, propertyName = "renewTime")
	private Date startDate;
	
	@Expression(operator = Operator.le, propertyName = "renewTime")
	private Date endDate;

	public List<RenewStatus> getRenewStatusList() {
		return renewStatusList;
	}

	public void setRenewStatusList(List<RenewStatus> renewStatusList) {
		this.renewStatusList = renewStatusList;
	}

	public List<PayStatus> getPayStatusList() {
		return payStatusList;
	}

	public void setPayStatusList(List<PayStatus> payStatusList) {
		this.payStatusList = payStatusList;
	}

	public List<Long> getIcustList() {
		return icustList;
	}

	public void setIcustList(List<Long> icustList) {
		this.icustList = icustList;
	}

	public RenewStatus getRenewStatus() {
		return renewStatus;
	}

	public void setRenewStatus(RenewStatus renewStatus) {
		this.renewStatus = renewStatus;
	}

	public PayStatus getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(PayStatus payStatus) {
		this.payStatus = payStatus;
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
