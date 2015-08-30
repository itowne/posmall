package com.newland.posmall.bean.basebusi.condition;

import org.ohuyo.commons.query.criterion.DetachedCriteriaEx;
import org.ohuyo.commons.query.criterion.annotation.CriteriaClass;
import org.ohuyo.commons.query.criterion.annotation.Expression;
import org.ohuyo.commons.query.criterion.annotation.Operator;

import com.newland.posmall.bean.basebusi.TpdtNo;

@CriteriaClass(TpdtNo.class)
public class TpdtNoCondition extends DetachedCriteriaEx {

	private static final long serialVersionUID = 3338641863923404396L;
	
	@Expression(operator = Operator.eq, propertyName = "ilogisticsOrdAddr")
	private Long ilogisticsOrdAddr; // 物流单地址外键
	
	@Expression(operator = Operator.eq, propertyName = "delFlag")
	private Boolean delFlag;

	public Boolean getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Boolean delFlag) {
		this.delFlag = delFlag;
	}

	public Long getIlogisticsOrdAddr() {
		return ilogisticsOrdAddr;
	}

	public void setIlogisticsOrdAddr(Long ilogisticsOrdAddr) {
		this.ilogisticsOrdAddr = ilogisticsOrdAddr;
	}
	
	
	
}
