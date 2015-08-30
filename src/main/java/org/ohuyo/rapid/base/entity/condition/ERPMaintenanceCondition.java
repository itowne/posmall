package org.ohuyo.rapid.base.entity.condition;

import org.ohuyo.commons.query.criterion.DetachedCriteriaEx;
import org.ohuyo.commons.query.criterion.annotation.CriteriaClass;
import org.ohuyo.commons.query.criterion.annotation.Expression;
import org.ohuyo.commons.query.criterion.annotation.LikeMatchMode;
import org.ohuyo.commons.query.criterion.annotation.Operator;
import org.ohuyo.rapid.base.entity.ERPMaintenance;

@CriteriaClass(ERPMaintenance.class)
public class ERPMaintenanceCondition extends DetachedCriteriaEx{

	private static final long serialVersionUID = 2551023512444868792L;

	@Expression(operator = Operator.like, propertyName = "sn", likeMatchMode = LikeMatchMode.anywhere)
	private String sn;
	
	@Expression(operator = Operator.eq, propertyName = "icust")
	private Long icust;

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Long getIcust() {
		return icust;
	}

	public void setIcust(Long icust) {
		this.icust = icust;
	}

}
