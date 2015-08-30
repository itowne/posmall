package com.newland.posmall.bean.basebusi.condition;

import java.util.Date;
import java.util.Set;
import java.util.List;

import org.ohuyo.commons.query.criterion.DetachedCriteriaEx;
import org.ohuyo.commons.query.criterion.annotation.CriteriaClass;
import org.ohuyo.commons.query.criterion.annotation.Expression;
import org.ohuyo.commons.query.criterion.annotation.Operator;

import com.newland.posmall.bean.basebusi.Tord;
import com.newland.posmall.bean.dict.OrdStatus;
import com.newland.posmall.bean.dict.PayStatus;

@CriteriaClass(Tord.class)
public class TordCondition extends DetachedCriteriaEx {

	private static final long serialVersionUID = -2200424795054412799L;

	@Expression(operator = Operator.eq, propertyName = "ordStatus")
	private OrdStatus ordStatus;

	@Expression(operator = Operator.eq, propertyName = "payStatus")
	private PayStatus payStatus;

	@Expression(operator = Operator.ge, propertyName = "genTime")
	private Date beginTime;

	@Expression(operator = Operator.lt, propertyName = "genTime")
	private Date endTime;
	
	@Expression(operator = Operator.lt, propertyName = "genTime")
	private Date genTime;

	@Expression(operator = Operator.eq, propertyName = "delFlag")
	private Boolean delFlag;

	@Expression(operator = Operator.eq, propertyName = "icust")
	private Long icust;
	
	@Expression(operator = Operator.in, propertyName = "iord")
	private Set<Long> iord;
	
	@Expression(operator = Operator.in, propertyName = "icust")
	private List<Long> icustList;
	
	@Expression(operator = Operator.in, propertyName = "iagmt")
	private Set<Long> iagmtList;
	
	public OrdStatus getOrdStatus() {
		return ordStatus;
	}

	public void setOrdStatus(OrdStatus ordStatus) {
		this.ordStatus = ordStatus;
	}

	public PayStatus getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(PayStatus payStatus) {
		this.payStatus = payStatus;
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

	public Set<Long> getIord() {
		return iord;
	}

	public void setIord(Set<Long> iord) {
		this.iord = iord;
	}

	public List<Long> getIcustList() {
		return icustList;
	}

	public void setIcustList(List<Long> icustList) {
		this.icustList = icustList;
	}

	public Date getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}

	public Set<Long> getIagmtList() {
		return iagmtList;
	}

	public void setIagmtList(Set<Long> iagmtList) {
		this.iagmtList = iagmtList;
	}

	
}
