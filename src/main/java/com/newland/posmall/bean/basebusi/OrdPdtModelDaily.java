package com.newland.posmall.bean.basebusi;

public class OrdPdtModelDaily {

	private String date;//预约日期
	
	private String supplyNum;//可供货量
	
	private String orderNumber;//下单数量
	
	private String ordDetailPdtType;//下单类型，库存或日产量

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSupplyNum() {
		return supplyNum;
	}

	public void setSupplyNum(String supplyNum) {
		this.supplyNum = supplyNum;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getOrdDetailPdtType() {
		return ordDetailPdtType;
	}

	public void setOrdDetailPdtType(String ordDetailPdtType) {
		this.ordDetailPdtType = ordDetailPdtType;
	}

}
