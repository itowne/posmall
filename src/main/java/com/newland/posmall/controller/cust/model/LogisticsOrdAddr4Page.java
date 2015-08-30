package com.newland.posmall.controller.cust.model;

import com.newland.posmall.bean.basebusi.TlogisticsOrdAddr;

/**
 * 物流单明细地址页面(页面显示用)
 * 
 * @author Administrator
 *
 */
public class LogisticsOrdAddr4Page {

	private TlogisticsOrdAddr tlogisticsOrdAddr;
	private String pdtName;

	public TlogisticsOrdAddr getTlogisticsOrdAddr() {
		return tlogisticsOrdAddr;
	}

	public void setTlogisticsOrdAddr(TlogisticsOrdAddr tlogisticsOrdAddr) {
		this.tlogisticsOrdAddr = tlogisticsOrdAddr;
	}

	public String getPdtName() {
		return pdtName;
	}

	public void setPdtName(String pdtName) {
		this.pdtName = pdtName;
	}

}
