package com.newland.posmall.bean.customer.condition;

import org.ohuyo.commons.query.criterion.DetachedCriteriaEx;
import org.ohuyo.commons.query.criterion.annotation.CriteriaClass;
import org.ohuyo.commons.query.criterion.annotation.Expression;
import org.ohuyo.commons.query.criterion.annotation.Operator;

import com.newland.posmall.bean.customer.TcustStock;

@CriteriaClass(TcustStock.class)
public class TcustStockCondition extends DetachedCriteriaEx {

	private static final long serialVersionUID = 8539007243220439611L;

	@Expression(operator = Operator.eq, propertyName = "icust")
	private Long icust;

	@Expression(operator = Operator.eq, propertyName = "ipdt")
	private Long ipdt;

	public Long getIcust() {
		return icust;
	}

	public void setIcust(Long icust) {
		this.icust = icust;
	}

	public Long getIpdt() {
		return ipdt;
	}

	public void setIpdt(Long ipdt) {
		this.ipdt = ipdt;
	}

}
