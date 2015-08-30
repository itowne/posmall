package com.newland.posmall.bean.customer.condition;

import org.ohuyo.commons.query.criterion.DetachedCriteriaEx;
import org.ohuyo.commons.query.criterion.annotation.CriteriaClass;
import org.ohuyo.commons.query.criterion.annotation.Expression;
import org.ohuyo.commons.query.criterion.annotation.LikeMatchMode;
import org.ohuyo.commons.query.criterion.annotation.Operator;

import com.newland.posmall.bean.customer.TcustAppver;

@CriteriaClass(TcustAppver.class)
public class TcustAppverCondition extends DetachedCriteriaEx{

	private static final long serialVersionUID = -6479571239042955856L;

	@Expression(operator = Operator.like, propertyName = "custNo", likeMatchMode = LikeMatchMode.anywhere)
	private String custNo;// 客户编号

	@Expression(operator = Operator.like, propertyName = "name", likeMatchMode = LikeMatchMode.anywhere)
	private String name;// 单位名称
    
	@Expression(operator = Operator.like, propertyName = "appver", likeMatchMode = LikeMatchMode.anywhere)
	private String appver;// 应用版本
    
	@Expression(operator = Operator.eq, propertyName = "delFlag")
	private Boolean delFlag;
	
	public String getCustNo() {
		return custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAppver() {
		return appver;
	}

	public void setAppver(String appver) {
		this.appver = appver;
	}

	public Boolean getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Boolean delFlag) {
		this.delFlag = delFlag;
	}
    
	
}
