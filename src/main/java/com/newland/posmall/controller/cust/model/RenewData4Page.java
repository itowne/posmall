package com.newland.posmall.controller.cust.model;

import java.math.BigDecimal;
import java.util.List;

public class RenewData4Page {
	
	/**
	 * 续保期限
	 */
	private String renewLife;
	
	/**
	 * 续保合计费用
	 */
	private BigDecimal renewAmt;
	
	private List<RenewData> renewDataList;
	
	public String getRenewLife() {
		return renewLife;
	}

	public void setRenewLife(String renewLife) {
		this.renewLife = renewLife;
	}

	public BigDecimal getRenewAmt() {
		return renewAmt;
	}

	public void setRenewAmt(BigDecimal renewAmt) {
		this.renewAmt = renewAmt;
	}

	public List<RenewData> getRenewDataList() {
		return renewDataList;
	}

	public void setRenewDataList(List<RenewData> renewDataList) {
		this.renewDataList = renewDataList;
	}

}
