package com.newland.posmall.bean.basebusi.condition;

import java.util.Date;
import java.util.List;

import org.ohuyo.commons.query.criterion.DetachedCriteriaEx;
import org.ohuyo.commons.query.criterion.annotation.CriteriaClass;
import org.ohuyo.commons.query.criterion.annotation.Expression;
import org.ohuyo.commons.query.criterion.annotation.Operator;

import com.newland.posmall.bean.basebusi.Tagmt;
import com.newland.posmall.bean.dict.AgmtStatus;

@CriteriaClass(Tagmt.class)
public class TagmtCondition extends DetachedCriteriaEx {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1507674400503382522L;

	@Expression(operator = Operator.eq, propertyName = "agmtNo")
	private String agmtNo;

	@Expression(operator = Operator.eq, propertyName = "agmtStatus")
	private AgmtStatus agmtStatus;
	
	@Expression(operator = Operator.in, propertyName = "agmtStatus")
	private List<AgmtStatus> agmtStatusList;

	@Expression(operator = Operator.ge, propertyName = "genTime")
	private Date startDate;

	@Expression(operator = Operator.lt, propertyName = "genTime")
	private Date endDate;

	@Expression(operator = Operator.eq, propertyName = "delFlag")
	private Boolean delFlag;

	@Expression(operator = Operator.eq, propertyName = "icust")
	private Long icust;
	
	@Expression(operator = Operator.le, propertyName = "startTime")
	private Date startTime;
	
	@Expression(operator = Operator.ge, propertyName = "endTime")
	private Date endTime;
	
	@Expression(operator = Operator.le, propertyName = "endTime")
	private Date endEndTime;
	
	@Expression(operator = Operator.ge, propertyName = "efficientTime")
	private Date startEfficientTime;

	@Expression(operator = Operator.lt, propertyName = "efficientTime")
	private Date endEfficientTime;

	public String getAgmtNo() {
		return agmtNo;
	}

	public void setAgmtNo(String agmtNo) {
		this.agmtNo = agmtNo;
	}

	public AgmtStatus getAgmtStatus() {
		return agmtStatus;
	}

	public void setAgmtStatus(AgmtStatus agmtStatus) {
		this.agmtStatus = agmtStatus;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Boolean getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Boolean delFlag) {
		this.delFlag = delFlag;
	}

	public Long getIcust() {
		return icust;
	}

	public void setIcust(Long icust) {
		this.icust = icust;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getEndEndTime() {
		return endEndTime;
	}

	public void setEndEndTime(Date endEndTime) {
		this.endEndTime = endEndTime;
	}

	public List<AgmtStatus> getAgmtStatusList() {
		return agmtStatusList;
	}

	public void setAgmtStatusList(List<AgmtStatus> agmtStatusList) {
		this.agmtStatusList = agmtStatusList;
	}

	public Date getStartEfficientTime() {
		return startEfficientTime;
	}

	public void setStartEfficientTime(Date startEfficientTime) {
		this.startEfficientTime = startEfficientTime;
	}

	public Date getEndEfficientTime() {
		return endEfficientTime;
	}

	public void setEndEfficientTime(Date endEfficientTime) {
		this.endEfficientTime = endEfficientTime;
	}

}
