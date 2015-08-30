package org.ohuyo.rapid.base.entity.condition;

import org.ohuyo.commons.query.criterion.DetachedCriteriaEx;
import org.ohuyo.commons.query.criterion.annotation.CriteriaClass;
import org.ohuyo.commons.query.criterion.annotation.Expression;
import org.ohuyo.commons.query.criterion.annotation.Operator;
import org.ohuyo.rapid.base.entity.TsysParam;

@CriteriaClass(TsysParam.class)
public class TsysParamCondition extends DetachedCriteriaEx {

	private static final long serialVersionUID = 3338641863923404396L;

	@Expression(operator = Operator.like, propertyName = "name")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
