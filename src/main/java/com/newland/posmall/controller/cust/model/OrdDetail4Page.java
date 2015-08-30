package com.newland.posmall.controller.cust.model;

import java.util.List;

import com.newland.posmall.bean.basebusi.TordDetail;
import com.newland.posmall.bean.basebusi.Tpdt;

public class OrdDetail4Page {

	private TordDetail tordDetail;// 订单详情
	private Tpdt tpdt;// 详情对应产品
	private List<OrdDetailPdt4Page> ordDetailPdt4PageList;// 订单产品明细的集合
	private Long ipdt;// 产品id
	private String pdtName;// 产品名称
	private Long spodNum;// 现货
	private String shipmentNum;//
	private String specifyDelivery;// 最快发货日期

	public TordDetail getTordDetail() {
		return tordDetail;
	}

	public void setTordDetail(TordDetail tordDetail) {
		this.tordDetail = tordDetail;
	}

	public List<OrdDetailPdt4Page> getOrdDetailPdt4PageList() {
		return ordDetailPdt4PageList;
	}

	public void setOrdDetailPdt4PageList(
			List<OrdDetailPdt4Page> ordDetailPdt4PageList) {
		this.ordDetailPdt4PageList = ordDetailPdt4PageList;
	}

	public Long getIpdt() {
		return ipdt;
	}

	public void setIpdt(Long ipdt) {
		this.ipdt = ipdt;
	}

	public String getPdtName() {
		return pdtName;
	}

	public void setPdtName(String pdtName) {
		this.pdtName = pdtName;
	}

	public Long getSpodNum() {
		return spodNum;
	}

	public void setSpodNum(Long spodNum) {
		this.spodNum = spodNum;
	}

	public String getShipmentNum() {
		return shipmentNum;
	}

	public void setShipmentNum(String shipmentNum) {
		this.shipmentNum = shipmentNum;
	}

	public String getSpecifyDelivery() {
		return specifyDelivery;
	}

	public void setSpecifyDelivery(String specifyDelivery) {
		this.specifyDelivery = specifyDelivery;
	}

	public Tpdt getTpdt() {
		return tpdt;
	}

	public void setTpdt(Tpdt tpdt) {
		this.tpdt = tpdt;
	}
}