package com.newland.posmall.bean.basebusi.condition;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.ohuyo.commons.query.criterion.DetachedCriteriaEx;
import org.ohuyo.commons.query.criterion.annotation.CriteriaClass;
import org.ohuyo.commons.query.criterion.annotation.Expression;
import org.ohuyo.commons.query.criterion.annotation.Operator;

import com.newland.posmall.bean.basebusi.TlogisticsCom;
import com.newland.posmall.bean.dict.LogisticsStatus;
import com.newland.posmall.bean.dict.YesNoType;

@CriteriaClass(TlogisticsCom.class)
public class LogisticscompanyCondition extends DetachedCriteriaEx {

	private static final long serialVersionUID = 1L;

	@Expression(operator = Operator.like, propertyName = "name")
	private String name;

	@Expression(operator = Operator.like, propertyName = "fullname")
	private String fullname;
	
	@Expression(operator = Operator.eq, propertyName = "aging")
	private Integer aging;

	@Expression(operator = Operator.eq, propertyName = "feeFlag")
	@Enumerated(EnumType.STRING)
	private YesNoType feeFlag;
	
	@Expression(operator = Operator.eq, propertyName = "logisticsStatus")
	@Enumerated(EnumType.STRING)
	private LogisticsStatus logisticsStatus;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public YesNoType getFeeFlag() {
		return feeFlag;
	}

	public void setFeeFlag(YesNoType feeFlag) {
		this.feeFlag = feeFlag;
	}

	public Integer getAging() {
		return aging;
	}

	public void setAging(Integer aging) {
		this.aging = aging;
	}
	
	public LogisticsStatus getLogisticsStatus() {
		return logisticsStatus;
	}

	public void setLogisticsStatus(LogisticsStatus logisticsStatus) {
		this.logisticsStatus = logisticsStatus;
	}

	

}
