package com.newland.posmall.bean.basebusi.condition;

import java.util.Date;

import org.ohuyo.commons.query.criterion.DetachedCriteriaEx;
import org.ohuyo.commons.query.criterion.annotation.CriteriaClass;
import org.ohuyo.commons.query.criterion.annotation.Expression;
import org.ohuyo.commons.query.criterion.annotation.LikeMatchMode;
import org.ohuyo.commons.query.criterion.annotation.Operator;

import com.newland.posmall.bean.customer.TcustReg;


@CriteriaClass(TcustReg.class)
public class TcustRegCondition extends DetachedCriteriaEx {
	
	private static final long serialVersionUID = 1L;
	
	@Expression(operator = Operator.like, propertyName = "name", likeMatchMode=LikeMatchMode.anywhere)
	private String name;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
