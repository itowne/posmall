package org.ohuyo.rapid.base.entity.condition;

import org.ohuyo.commons.query.criterion.DetachedCriteriaEx;
import org.ohuyo.commons.query.criterion.annotation.CriteriaClass;
import org.ohuyo.commons.query.criterion.annotation.Expression;
import org.ohuyo.commons.query.criterion.annotation.Operator;
import org.ohuyo.rapid.base.entity.Tsysrole;

@CriteriaClass(Tsysrole.class)
public class TsysroleCondition extends DetachedCriteriaEx {

	private static final long serialVersionUID = 3338641863923404396L;

	@Expression(operator = Operator.like, propertyName = "name")
	private String name;

	@Expression(operator = Operator.eq, propertyName = "delFlag")
	private Boolean delFlag;

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
