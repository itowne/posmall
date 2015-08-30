package com.newland.posmall.bean.customer.condition;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.ohuyo.commons.query.criterion.DetachedCriteriaEx;
import org.ohuyo.commons.query.criterion.annotation.CriteriaClass;
import org.ohuyo.commons.query.criterion.annotation.Expression;
import org.ohuyo.commons.query.criterion.annotation.Operator;
import com.newland.posmall.bean.basebusi.TlogisticsOrd;
import com.newland.posmall.bean.dict.LogisticsOrdStatus;
import com.newland.posmall.bean.dict.PayStatus;

@CriteriaClass(TlogisticsOrd.class)
public class TlogisticsOrdCondition extends DetachedCriteriaEx {

	private static final long serialVersionUID = -3509038799726895925L;

	@Expression(operator = Operator.eq, propertyName = "icust")
	private Long icust;// 客户id

	@Expression(operator = Operator.eq, propertyName = "tempFlag")
	private Boolean tempFlag;// 临时标志
	
	@Expression(operator = Operator.le, propertyName = "genTime")
	private Date genTime;
	
	@Expression(operator = Operator.eq, propertyName = "logisticsOrdStatus")
	private LogisticsOrdStatus logisticsOrdStatus;
	
	@Expression(operator = Operator.eq, propertyName = "payStatus")
	private PayStatus payStatus;
	
	@Expression(operator = Operator.eq, propertyName = "innerOrdno")
	private String  innerOrdno;
	
	@Expression(operator = Operator.in, propertyName = "logisticsOrdStatus")
	private List<LogisticsOrdStatus> logisticsOrdStatusList;
	
	@Expression(operator = Operator.in, propertyName = "icust")
	private List<Long> icustList;
	
	@Expression(operator = Operator.in, propertyName = "ilogisticsOrd")
	private List<Long> ilogisticsOrd;
	
	@Expression(operator = Operator.in, propertyName = "iord")
	private Set<Long> iordList;
	
	@Expression(operator = Operator.ge, propertyName = "genTime")
	private Date beginTime;

	@Expression(operator = Operator.lt, propertyName = "genTime")
	private Date endTime;

	public Long getIcust() {
		return icust;
	}

	public void setIcust(Long icust) {
		this.icust = icust;
	}

	public Boolean getTempFlag() {
		return tempFlag;
	}

	public void setTempFlag(Boolean tempFlag) {
		this.tempFlag = tempFlag;
	}

	public Date getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}

	public LogisticsOrdStatus getLogisticsOrdStatus() {
		return logisticsOrdStatus;
	}

	public void setLogisticsOrdStatus(LogisticsOrdStatus logisticsOrdStatus) {
		this.logisticsOrdStatus = logisticsOrdStatus;
	}

	public List<Long> getIcustList() {
		return icustList;
	}

	public void setIcustList(List<Long> icustList) {
		this.icustList = icustList;
	}

	public List<LogisticsOrdStatus> getLogisticsOrdStatusList() {
		return logisticsOrdStatusList;
	}

	public void setLogisticsOrdStatusList(
			List<LogisticsOrdStatus> logisticsOrdStatusList) {
		this.logisticsOrdStatusList = logisticsOrdStatusList;
	}

	public String getInnerOrdno() {
		return innerOrdno;
	}

	public void setInnerOrdno(String innerOrdno) {
		this.innerOrdno = innerOrdno;
	}

	public PayStatus getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(PayStatus payStatus) {
		this.payStatus = payStatus;
	}

	public List<Long> getIlogisticsOrd() {
		return ilogisticsOrd;
	}

	public void setIlogisticsOrd(List<Long> ilogisticsOrd) {
		this.ilogisticsOrd = ilogisticsOrd;
	}

	public Set<Long> getIordList() {
		return iordList;
	}

	public void setIordList(Set<Long> iordList) {
		this.iordList = iordList;
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
