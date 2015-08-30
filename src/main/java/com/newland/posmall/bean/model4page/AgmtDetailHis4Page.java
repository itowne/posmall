package com.newland.posmall.bean.model4page;

import com.newland.posmall.bean.basebusi.TagmtDetailHis;
import com.newland.posmall.bean.basebusi.TpdtHis;

public class AgmtDetailHis4Page {

	/**
	 * 协议变更明细历史
	 */
	private TagmtDetailHis tagmtDetailHis;

	/**
	 * 产品历史
	 */
	private TpdtHis tpdtHis;

	public TagmtDetailHis getTagmtDetailHis() {
		return tagmtDetailHis;
	}

	public void setTagmtDetailHis(TagmtDetailHis tagmtDetailHis) {
		this.tagmtDetailHis = tagmtDetailHis;
	}

	public TpdtHis getTpdtHis() {
		return tpdtHis;
	}

	public void setTpdtHis(TpdtHis tpdtHis) {
		this.tpdtHis = tpdtHis;
	}

}
