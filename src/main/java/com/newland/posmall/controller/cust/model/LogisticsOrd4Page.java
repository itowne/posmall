package com.newland.posmall.controller.cust.model;

import java.util.List;

import com.newland.posmall.bean.basebusi.TlogisticsOrd;

/**
 * 物流单页面(页面显示用)
 * 
 * @author Administrator
 *
 */
public class LogisticsOrd4Page {

	/**
	 * 物流单主表
	 */
	private TlogisticsOrd tlogisticsOrd;
	
	/**
	 * 物流地址信息明细
	 */
	private List<LogisticsOrdAddr4Page> logisticsOrdAddr4PageList;
	
	private Long iaddr;
	private String specifyDelivery;
	private Long ilogisticsCom;

	public TlogisticsOrd getTlogisticsOrd() {
		return tlogisticsOrd;
	}

	public void setTlogisticsOrd(TlogisticsOrd tlogisticsOrd) {
		this.tlogisticsOrd = tlogisticsOrd;
	}

	public Long getIaddr() {
		return iaddr;
	}

	public void setIaddr(Long iaddr) {
		this.iaddr = iaddr;
	}

	public String getSpecifyDelivery() {
		return specifyDelivery;
	}

	public void setSpecifyDelivery(String specifyDelivery) {
		this.specifyDelivery = specifyDelivery;
	}

	public Long getIlogisticsCom() {
		return ilogisticsCom;
	}

	public void setIlogisticsCom(Long ilogisticsCom) {
		this.ilogisticsCom = ilogisticsCom;
	}

	public List<LogisticsOrdAddr4Page> getLogisticsOrdAddr4PageList() {
		return logisticsOrdAddr4PageList;
	}

	public void setLogisticsOrdAddr4PageList(
			List<LogisticsOrdAddr4Page> logisticsOrdAddr4PageList) {
		this.logisticsOrdAddr4PageList = logisticsOrdAddr4PageList;
	}

}
