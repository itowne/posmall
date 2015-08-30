package org.ohuyo.rapid.base.entity.condition;

import org.ohuyo.commons.query.criterion.DetachedCriteriaEx;
import org.ohuyo.commons.query.criterion.annotation.CriteriaClass;
import org.ohuyo.commons.query.criterion.annotation.Expression;
import org.ohuyo.commons.query.criterion.annotation.Operator;
import org.ohuyo.rapid.base.entity.Tsys;

@CriteriaClass(Tsys.class)
public class TsysCondition extends DetachedCriteriaEx {

	private static final long serialVersionUID = 3338641863923404396L;

	@Expression(operator = Operator.like, propertyName = "loginName")
	private String loginName;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

}
