package com.newland.posmall.bean.basebusi.condition;

import org.ohuyo.commons.query.criterion.DetachedCriteriaEx;
import org.ohuyo.commons.query.criterion.annotation.CriteriaClass;
import org.ohuyo.commons.query.criterion.annotation.Expression;
import org.ohuyo.commons.query.criterion.annotation.LikeMatchMode;
import org.ohuyo.commons.query.criterion.annotation.Operator;
import com.newland.posmall.bean.common.TmsgTmp;
import com.newland.posmall.bean.dict.MsgTmpType;

@CriteriaClass(TmsgTmp.class)
public class TmsgTmpCondition extends DetachedCriteriaEx {

	private static final long serialVersionUID = 3338641863923404396L;

	@Expression(operator = Operator.eq, propertyName = "tmpType")
	private MsgTmpType tmpType;
	
	@Expression(operator = Operator.like, propertyName = "noteName", likeMatchMode=LikeMatchMode.anywhere)
	private String noteName;
	
	@Expression(operator = Operator.eq, propertyName = "delFlg")
	private Boolean delFlag;

	public MsgTmpType getTmpType() {
		return tmpType;
	}

	public void setTmpType(MsgTmpType tmpType) {
		this.tmpType = tmpType;
	}

	public String getNoteName() {
		return noteName;
	}

	public void setNoteName(String noteName) {
		this.noteName = noteName;
	}

	public Boolean getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Boolean delFlag) {
		this.delFlag = delFlag;
	}

}
