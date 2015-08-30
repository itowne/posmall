package com.newland.posmall.bean.model4page;

import com.newland.posmall.bean.basebusi.TagmtDetail;
import com.newland.posmall.bean.basebusi.TpdtHis;

public class AgmtDetail4Page {

	/**
	 * 协议详情model
	 */
	private TagmtDetail tagmtDetail;

	/**
	 * 产品历史
	 */
	private TpdtHis tpdtHis;

	/**
	 * 已发货数量
	 */
	private Integer deliveryed;

	/**
	 * 待发货数量
	 */
	private Integer pendingNum;

	public TagmtDetail getTagmtDetail() {
		return tagmtDetail;
	}

	public void setTagmtDetail(TagmtDetail tagmtDetail) {
		this.tagmtDetail = tagmtDetail;
	}

	public TpdtHis getTpdtHis() {
		return tpdtHis;
	}

	public void setTpdtHis(TpdtHis tpdtHis) {
		this.tpdtHis = tpdtHis;
	}

	public Integer getDeliveryed() {
		return deliveryed;
	}

	public void setDeliveryed(Integer deliveryed) {
		this.deliveryed = deliveryed;
	}

	public Integer getPendingNum() {
		return pendingNum;
	}

	public void setPendingNum(Integer pendingNum) {
		this.pendingNum = pendingNum;
	}

}
