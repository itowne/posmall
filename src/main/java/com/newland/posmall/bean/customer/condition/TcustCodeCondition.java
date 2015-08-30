package com.newland.posmall.bean.customer.condition;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.ohuyo.commons.query.criterion.DetachedCriteriaEx;
import org.ohuyo.commons.query.criterion.annotation.CriteriaClass;
import org.ohuyo.commons.query.criterion.annotation.Expression;
import org.ohuyo.commons.query.criterion.annotation.LikeMatchMode;
import org.ohuyo.commons.query.criterion.annotation.Operator;

import com.newland.posmall.bean.customer.TcustCode;
import com.newland.posmall.bean.dict.CustCodeStatus;

@CriteriaClass(TcustCode.class)
public class TcustCodeCondition extends DetachedCriteriaEx {

	private static final long serialVersionUID = 7094365904406691659L;

	@Expression(operator = Operator.like, propertyName = "custCode", likeMatchMode = LikeMatchMode.anywhere)
	private String custCode;
	
	@Expression(operator = Operator.eq, propertyName = "custCodeStatus")
	@Enumerated(EnumType.STRING)
	private CustCodeStatus custCodeStatus;
	
	@Expression(operator = Operator.like, propertyName = "companyName", likeMatchMode = LikeMatchMode.anywhere)
	private String companyName;

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public CustCodeStatus getCustCodeStatus() {
		return custCodeStatus;
	}

	public void setCustCodeStatus(CustCodeStatus custCodeStatus) {
		this.custCodeStatus = custCodeStatus;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}
