package com.newland.posmall.controller.cust.model;

import java.math.BigDecimal;
import java.util.List;

import com.newland.posmall.bean.basebusi.Tord;

/**
 * 订单信息(页面显示用)
 * 
 * @author Administrator
 *
 */
public class Ord4Page {

	private Tord tord;//订单
	private List<OrdDetail4Page> ordDetail4PageList;//订单详情的集合
	private String tips;//提示
	private BigDecimal havePaid;//已支付货款

	public Tord getTord() {
		return tord;
	}

	public void setTord(Tord tord) {
		this.tord = tord;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public List<OrdDetail4Page> getOrdDetail4PageList() {
		return ordDetail4PageList;
	}

	public void setOrdDetail4PageList(List<OrdDetail4Page> ordDetail4PageList) {
		this.ordDetail4PageList = ordDetail4PageList;
	}

	public BigDecimal getHavePaid() {
		return havePaid;
	}

	public void setHavePaid(BigDecimal havePaid) {
		this.havePaid = havePaid;
	}

}
