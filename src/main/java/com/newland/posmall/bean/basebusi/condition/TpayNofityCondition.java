package com.newland.posmall.bean.basebusi.condition;

import java.util.List;

import org.ohuyo.commons.query.criterion.DetachedCriteriaEx;
import org.ohuyo.commons.query.criterion.annotation.CriteriaClass;
import org.ohuyo.commons.query.criterion.annotation.Expression;
import org.ohuyo.commons.query.criterion.annotation.Operator;

import com.newland.posmall.bean.basebusi.TpayNotify;
import com.newland.posmall.bean.dict.PayStatus;
import com.newland.posmall.bean.dict.PayType;

@CriteriaClass(TpayNotify.class)
public class TpayNofityCondition extends DetachedCriteriaEx {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1516366324745411903L;

	@Expression(operator = Operator.in, propertyName = "ipayNotify")
	private List<Long> ipayNotifyIds;
	
	@Expression(operator = Operator.in, propertyName = "payNotifyStatus")
	private List<PayStatus> payNotifyStatuses;
	
	@Expression(operator = Operator.eq, propertyName = "delFlag")
	private Boolean delFlag;
	
	@Expression(operator = Operator.eq, propertyName = "ifk")
	private Long ifk;
	
	@Expression(operator = Operator.in, propertyName = "payType")
	private List<PayType> payTypes;

	public List<Long> getIpayNotifyIds() {
		return ipayNotifyIds;
	}

	public void setIpayNotifyIds(List<Long> ipayNotifyIds) {
		this.ipayNotifyIds = ipayNotifyIds;
	}

	public List<PayStatus> getPayNotifyStatuses() {
		return payNotifyStatuses;
	}

	public void setPayNotifyStatuses(List<PayStatus> payNotifyStatuses) {
		this.payNotifyStatuses = payNotifyStatuses;
	}

	public Boolean getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Boolean delFlag) {
		this.delFlag = delFlag;
	}

	public Long getIfk() {
		return ifk;
	}

	public void setIfk(Long ifk) {
		this.ifk = ifk;
	}

	public List<PayType> getPayTypes() {
		return payTypes;
	}

	public void setPayTypes(List<PayType> payTypes) {
		this.payTypes = payTypes;
	}

}
