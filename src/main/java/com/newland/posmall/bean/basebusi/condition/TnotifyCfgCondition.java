package com.newland.posmall.bean.basebusi.condition;

import org.ohuyo.commons.query.criterion.DetachedCriteriaEx;
import org.ohuyo.commons.query.criterion.annotation.CriteriaClass;
import org.ohuyo.commons.query.criterion.annotation.Expression;
import org.ohuyo.commons.query.criterion.annotation.LikeMatchMode;
import org.ohuyo.commons.query.criterion.annotation.Operator;

import com.newland.posmall.bean.basebusi.TnotifyCfg;
import com.newland.posmall.bean.dict.NotifyType;

@CriteriaClass(TnotifyCfg.class)
public class TnotifyCfgCondition extends DetachedCriteriaEx {

	private static final long serialVersionUID = 3338641863923404396L;

	@Expression(operator = Operator.eq, propertyName = "notifyType")
	private NotifyType notifyType;
	
	@Expression(operator = Operator.like, propertyName = "userName", likeMatchMode=LikeMatchMode.anywhere)
	private String userName;
	
	@Expression(operator = Operator.eq, propertyName = "delFlag")
	private Boolean delFlag;

	public NotifyType getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(NotifyType notifyType) {
		this.notifyType = notifyType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Boolean getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Boolean delFlag) {
		this.delFlag = delFlag;
	}
	
	
	
}
