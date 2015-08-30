package org.ohuyo.rapid.base.entity.condition;


import org.ohuyo.commons.query.criterion.DetachedCriteriaEx;
import org.ohuyo.commons.query.criterion.annotation.CriteriaClass;
import org.ohuyo.commons.query.criterion.annotation.Expression;
import org.ohuyo.commons.query.criterion.annotation.Operator;
import org.ohuyo.rapid.schedule.entity.TaskLog;

@CriteriaClass(TaskLog.class)
public class TaskLogCondition extends DetachedCriteriaEx {

	private static final long serialVersionUID = 3338641863923404396L;

	@Expression(operator = Operator.eq, propertyName = "itask")
	private Long itask;

	public Long getItask() {
		return itask;
	}

	public void setItask(Long itask) {
		this.itask = itask;
	}


	

	
}
