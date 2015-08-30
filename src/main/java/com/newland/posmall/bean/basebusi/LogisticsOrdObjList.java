package com.newland.posmall.bean.basebusi;

import java.util.ArrayList;
import java.util.List;

import com.newland.posmall.bean.customer.Taddr;

public class LogisticsOrdObjList {

	private List<LogisticsOrdObj> logisticsOrdObjList = new ArrayList<LogisticsOrdObj>();

	private Taddr Taddr;

	private String province;
	private String city;
	private String county;
	private String area;
	private String postalCode;
	private String name;
	private String mobile;
	private String tel;
	private Long ilogisticsCom;
	private String feeFlag;
	private String aging;
	private String fee;
	private String specifyDelivery;
	private String depositFlag;

	public Taddr getTaddr() {
		return Taddr;
	}

	public void setTaddr(Taddr taddr) {
		Taddr = taddr;
	}

	public List<LogisticsOrdObj> getLogisticsOrdObjList() {
		return logisticsOrdObjList;
	}

	public void setLogisticsOrdObjList(List<LogisticsOrdObj> logisticsOrdObjList) {
		this.logisticsOrdObjList = logisticsOrdObjList;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Long getIlogisticsCom() {
		return ilogisticsCom;
	}

	public void setIlogisticsCom(Long ilogisticsCom) {
		this.ilogisticsCom = ilogisticsCom;
	}

	public String getFeeFlag() {
		return feeFlag;
	}

	public void setFeeFlag(String feeFlag) {
		this.feeFlag = feeFlag;
	}

	public String getAging() {
		return aging;
	}

	public void setAging(String aging) {
		this.aging = aging;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getSpecifyDelivery() {
		return specifyDelivery;
	}

	public void setSpecifyDelivery(String specifyDelivery) {
		this.specifyDelivery = specifyDelivery;
	}

	public String getDepositFlag() {
		return depositFlag;
	}

	public void setDepositFlag(String depositFlag) {
		this.depositFlag = depositFlag;
	}

}
