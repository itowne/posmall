package com.newland.posmall.bean.basebusi.condition;

import org.ohuyo.commons.query.criterion.DetachedCriteriaEx;
import org.ohuyo.commons.query.criterion.annotation.CriteriaClass;
import org.ohuyo.commons.query.criterion.annotation.Expression;
import org.ohuyo.commons.query.criterion.annotation.Operator;

import com.newland.posmall.bean.basebusi.TordDetail;
import com.newland.posmall.bean.dict.ValidStatus;

@CriteriaClass(TordDetail.class)
public class TordDetailCondition extends DetachedCriteriaEx {

	private static final long serialVersionUID = 8739113608552419166L;

	@Expression(operator = Operator.eq, propertyName = "delFlag")
	private Boolean delFlag;

	@Expression(operator = Operator.eq, propertyName = "icust")
	private Long icust;

	@Expression(operator = Operator.eq, propertyName = "ordDetailStatus")
	private ValidStatus ordDetailStatus;

	@Expression(operator = Operator.gt, propertyName = "remainQuota")
	private Integer remainQuota;

	@Expression(operator = Operator.eq, propertyName = "ipdtHis")
	private Long ipdtHis;// 产品历史外键

	@Expression(operator = Operator.eq, propertyName = "iord")
	private Long iord;// 订单内部编号
	
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

	public Integer getRemainQuota() {
		return remainQuota;
	}

	public void setRemainQuota(Integer remainQuota) {
		this.remainQuota = remainQuota;
	}

	public ValidStatus getOrdDetailStatus() {
		return ordDetailStatus;
	}

	public void setOrdDetailStatus(ValidStatus ordDetailStatus) {
		this.ordDetailStatus = ordDetailStatus;
	}

	public Long getIpdtHis() {
		return ipdtHis;
	}

	public void setIpdtHis(Long ipdtHis) {
		this.ipdtHis = ipdtHis;
	}

	public Long getIord() {
		return iord;
	}

	public void setIord(Long iord) {
		this.iord = iord;
	}

}
