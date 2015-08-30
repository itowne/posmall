package com.newland.posmall.bean.basebusi.condition;

import org.ohuyo.commons.query.criterion.DetachedCriteriaEx;
import org.ohuyo.commons.query.criterion.annotation.CriteriaClass;
import org.ohuyo.commons.query.criterion.annotation.Expression;
import org.ohuyo.commons.query.criterion.annotation.LikeMatchMode;
import org.ohuyo.commons.query.criterion.annotation.Operator;

import com.newland.posmall.bean.basebusi.TlogisticsOrdAddr;

@CriteriaClass(TlogisticsOrdAddr.class)
public class TlogisticsOrdAddrCondition extends DetachedCriteriaEx {

	private static final long serialVersionUID = 3338641863923404396L;
	
	@Expression(operator = Operator.eq, propertyName = "ilogisticsOrd")
	private Long ilogisticsOrd; // 物流单外键
	
	@Expression(operator = Operator.like, propertyName = "consigneeName", likeMatchMode = LikeMatchMode.anywhere)
	private String consigneeName;// 收货人姓名
	
	@Expression(operator = Operator.eq, propertyName = "delFlag")
	private Boolean delFlag;

	public Boolean getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Boolean delFlag) {
		this.delFlag = delFlag;
	}

	public Long getIlogisticsOrd() {
		return ilogisticsOrd;
	}

	public void setIlogisticsOrd(Long ilogisticsOrd) {
		this.ilogisticsOrd = ilogisticsOrd;
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}
	
	
	
}
