package org.ohuyo.rapid.base.entity.condition;

import java.util.Date;

import org.ohuyo.commons.query.criterion.DetachedCriteriaEx;
import org.ohuyo.commons.query.criterion.annotation.CriteriaClass;
import org.ohuyo.commons.query.criterion.annotation.Expression;
import org.ohuyo.commons.query.criterion.annotation.Operator;
import org.ohuyo.rapid.base.entity.ERPSync;

@CriteriaClass(ERPSync.class)
public class ERPSyncCondition extends DetachedCriteriaEx{

	private static final long serialVersionUID = 2551023512444868792L;

	@Expression(operator = Operator.ge, propertyName = "xhrq")
	private Date beginTime;
	
	@Expression(operator = Operator.lt, propertyName = "xhrq")
	private Date endTime;
	
	@Expression(operator = Operator.eq, propertyName = "id")
	private Long logisticsNo;
	
	@Expression(operator = Operator.like, propertyName = "sjtqDate")
	private String sjtqDate;

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Long getLogisticsNo() {
		return logisticsNo;
	}

	public void setLogisticsNo(Long logisticsNo) {
		this.logisticsNo = logisticsNo;
	}

	public String getSjtqDate() {
		return sjtqDate;
	}

	public void setSjtqDate(String sjtqDate) {
		this.sjtqDate = sjtqDate;
	}
	
}
