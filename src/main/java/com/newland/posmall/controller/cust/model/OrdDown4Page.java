package com.newland.posmall.controller.cust.model;

import com.newland.posmall.bean.basebusi.Tord;
import com.newland.posmall.bean.basebusi.TordDetailPdt;

import java.util.List;

/**
 * 订单下载对象构造
 * @author Mr.Towne
 *
 */
public class OrdDown4Page {

	private Tord tord;
	private List<TordDetailPdt> detailPdtList;
	public Tord getTord() {
		return tord;
	}
	public void setTord(Tord tord) {
		this.tord = tord;
	}
	public List<TordDetailPdt> getDetailPdtList() {
		return detailPdtList;
	}
	public void setDetailPdtList(List<TordDetailPdt> detailPdtList) {
		this.detailPdtList = detailPdtList;
	}

}
