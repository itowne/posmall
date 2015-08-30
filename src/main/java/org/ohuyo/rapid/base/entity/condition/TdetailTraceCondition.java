package org.ohuyo.rapid.base.entity.condition;

import java.util.Date;
import java.util.List;

import org.ohuyo.commons.query.criterion.DetachedCriteriaEx;
import org.ohuyo.commons.query.criterion.annotation.CriteriaClass;
import org.ohuyo.commons.query.criterion.annotation.Expression;
import org.ohuyo.commons.query.criterion.annotation.Operator;

import com.newland.posmall.bean.common.TdetailTrace;

@CriteriaClass(TdetailTrace.class)
public class TdetailTraceCondition extends DetachedCriteriaEx {

	private static final long serialVersionUID = -1329844720602797083L;
	
	@Expression(operator = Operator.eq, propertyName = "icust")
	private Long icust;
	
	@Expression(operator = Operator.in, propertyName = "icust")
	private List<Long> icustList;

	@Expression(operator = Operator.like, propertyName = "custName")
	private String custName;
	
	@Expression(operator = Operator.eq, propertyName = "agmtNo")
	private String agmtNo;
	
	@Expression(operator = Operator.eq, propertyName = "ordNo")
	private String ordNo;
	
	@Expression(operator = Operator.eq, propertyName = "logisticsOrdNo")
	private String logisticsOrdNo;
	
	@Expression(operator = Operator.ge, propertyName = "genTime")
	private Date startTime;
	
	private String startTimeStr;
	
	@Expression(operator = Operator.le, propertyName = "genTime")
	private Date endTime;
	
	private String endTimeStr;

	public Long getIcust() {
		return icust;
	}

	public void setIcust(Long icust) {
		this.icust = icust;
	}

	public String getAgmtNo() {
		return agmtNo;
	}

	public void setAgmtNo(String agmtNo) {
		this.agmtNo = agmtNo;
	}

	public String getOrdNo() {
		return ordNo;
	}

	public void setOrdNo(String ordNo) {
		this.ordNo = ordNo;
	}

	public String getLogisticsOrdNo() {
		return logisticsOrdNo;
	}

	public void setLogisticsOrdNo(String logisticsOrdNo) {
		this.logisticsOrdNo = logisticsOrdNo;
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

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

	public List<Long> getIcustList() {
		return icustList;
	}

	public void setIcustList(List<Long> icustList) {
		this.icustList = icustList;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

}
