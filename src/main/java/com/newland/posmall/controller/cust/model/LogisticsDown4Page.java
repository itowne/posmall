package com.newland.posmall.controller.cust.model;

import java.util.List;

import com.newland.posmall.bean.basebusi.TlogisticsOrd;
import com.newland.posmall.bean.basebusi.TlogisticsOrdAddr;

/**
 * 物流单嵌套信息对象
 * 
 * @author Mr.Towne
 * 
 */
public class LogisticsDown4Page {

	private TlogisticsOrd tlogisticsOrd;
	private List<TlogisticsOrdAddr> tlogisticsOrdAddrList;
	private String erpOrdId;

	public List<TlogisticsOrdAddr> getTlogisticsOrdAddrList() {
		return tlogisticsOrdAddrList;
	}
	public void setTlogisticsOrdAddrList(
			List<TlogisticsOrdAddr> tlogisticsOrdAddrList) {
		this.tlogisticsOrdAddrList = tlogisticsOrdAddrList;
	}
	public TlogisticsOrd getTlogisticsOrd() {
		return tlogisticsOrd;
	}
	public void setTlogisticsOrd(TlogisticsOrd tlogisticsOrd) {
		this.tlogisticsOrd = tlogisticsOrd;
	}
	public String getErpOrdId() {
		return erpOrdId;
	}
	public void setErpOrdId(String erpOrdId) {
		this.erpOrdId = erpOrdId;
	}
	

}
