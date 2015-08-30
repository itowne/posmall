package com.newland.posmall.bean.basebusi.condition;

import org.ohuyo.commons.query.criterion.DetachedCriteriaEx;
import org.ohuyo.commons.query.criterion.annotation.CriteriaClass;
import org.ohuyo.commons.query.criterion.annotation.Expression;
import org.ohuyo.commons.query.criterion.annotation.Operator;

import com.newland.posmall.bean.basebusi.TordHis;

@CriteriaClass(TordHis.class)
public class TordHisCondition extends DetachedCriteriaEx {

	private static final long serialVersionUID = -5085499310663309283L;
	
	@Expression(operator = Operator.eq, propertyName = "iord")
	private Long iord;

	public Long getIord() {
		return iord;
	}

	public void setIord(Long iord) {
		this.iord = iord;
	}

}
