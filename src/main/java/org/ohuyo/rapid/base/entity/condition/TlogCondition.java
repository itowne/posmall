package org.ohuyo.rapid.base.entity.condition;

import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.ohuyo.commons.query.criterion.DetachedCriteriaEx;
import org.ohuyo.commons.query.criterion.annotation.CriteriaClass;
import org.ohuyo.commons.query.criterion.annotation.Expression;
import org.ohuyo.commons.query.criterion.annotation.Operator;

import com.newland.posmall.bean.common.Tlog;
import com.newland.posmall.bean.dict.LogType;
import com.newland.posmall.bean.dict.OperType;

@CriteriaClass(Tlog.class)
public class TlogCondition extends DetachedCriteriaEx {

	private static final long serialVersionUID = 3338641863923404396L;

	@Expression(operator = Operator.eq, propertyName = "logType")
	private LogType logType;
	
	@Expression(operator = Operator.eq, propertyName = "operType")
	private OperType operType;
	
	@Expression(operator = Operator.ge, propertyName = "genTime")
	private Date beginTime;
	
	@Expression(operator = Operator.lt, propertyName = "genTime")
	private Date endTime;

	public LogType getLogType() {
		return logType;
	}

	public void setLogType(LogType logType) {
		this.logType = logType;
	}

	public OperType getOperType() {
		return operType;
	}

	public void setOperType(OperType operType) {
		this.operType = operType;
	}

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

	
}
