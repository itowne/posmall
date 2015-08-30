package com.newland.posmall.bean.customer.condition;

import org.ohuyo.commons.query.criterion.DetachedCriteriaEx;
import org.ohuyo.commons.query.criterion.annotation.CriteriaClass;
import org.ohuyo.commons.query.criterion.annotation.Expression;
import org.ohuyo.commons.query.criterion.annotation.LikeMatchMode;
import org.ohuyo.commons.query.criterion.annotation.Operator;

import com.newland.posmall.bean.customer.Taddr;

@CriteriaClass(Taddr.class)
public class TaddrCondition extends DetachedCriteriaEx {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8810436605241598662L;

	@Expression(operator = Operator.eq, propertyName = "icust")
	private Long icust;

	@Expression(operator = Operator.like, propertyName = "name", likeMatchMode = LikeMatchMode.anywhere)
	private String name;
	
	@Expression(operator = Operator.eq, propertyName = "delFlag")
	private Boolean delFlag;

	public Long getIcust() {
		return icust;
	}

	public void setIcust(Long icust) {
		this.icust = icust;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Boolean delFlag) {
		this.delFlag = delFlag;
	}

}
