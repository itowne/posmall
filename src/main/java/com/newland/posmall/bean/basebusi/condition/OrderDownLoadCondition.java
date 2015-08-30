package com.newland.posmall.bean.basebusi.condition;

import java.util.Date;

import org.ohuyo.commons.query.criterion.DetachedCriteriaEx;
import org.ohuyo.commons.query.criterion.annotation.CriteriaClass;
import org.ohuyo.commons.query.criterion.annotation.Expression;
import org.ohuyo.commons.query.criterion.annotation.Operator;

import com.newland.posmall.bean.basebusi.OrderDownLoad;

@CriteriaClass(OrderDownLoad.class)
public class OrderDownLoadCondition extends DetachedCriteriaEx{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3199510894965484780L;

	@Expression(operator = Operator.eq, propertyName = "genTime")
	private Date genTime;

	public Date getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}
}
