package com.newland.posmall.controller.jspmodel;

import java.math.BigDecimal;
import java.util.List;

import com.newland.posmall.bean.basebusi.Tpdt;

public class AgmtAddObject {
	private List<Tpdt> agmtTpdtList;
	
	private BigDecimal agmtTpdtRate;
	
	/**
	 * 协议订货量
	 */
	private BigDecimal agmtTpdtTotalAmt;
	
	/**
	 * 协议保证金
	 */
	private BigDecimal agmtDeposit;
	
	/**
	 * 协议起始日期(yyyy-MM-dd)
	 */
	private String startDateOfAgmt;
	
	/**
	 * 协议终止日期(yyyy-MM-dd)
	 */
	private String endDateOfAgmt;

	public List<Tpdt> getAgmtTpdtList() {
		return agmtTpdtList;
	}

	public void setAgmtTpdtList(List<Tpdt> agmtTpdtList) {
		this.agmtTpdtList = agmtTpdtList;
	}

	public BigDecimal getAgmtTpdtRate() {
		return agmtTpdtRate;
	}

	public void setAgmtTpdtRate(BigDecimal agmtTpdtRate) {
		this.agmtTpdtRate = agmtTpdtRate;
	}

	public BigDecimal getAgmtTpdtTotalAmt() {
		return agmtTpdtTotalAmt;
	}

	public void setAgmtTpdtTotalAmt(BigDecimal agmtTpdtTotalAmt) {
		this.agmtTpdtTotalAmt = agmtTpdtTotalAmt;
	}

	public BigDecimal getAgmtDeposit() {
		return agmtDeposit;
	}

	public void setAgmtDeposit(BigDecimal agmtDeposit) {
		this.agmtDeposit = agmtDeposit;
	}

	public String getStartDateOfAgmt() {
		return startDateOfAgmt;
	}
	
	public void setStartDateOfAgmt(String startDateOfAgmt) {
		this.startDateOfAgmt = startDateOfAgmt;
	}
	
	public String getEndDateOfAgmt() {
		return endDateOfAgmt;
	}
	
	public void setEndDateOfAgmt(String endDateOfAgmt) {
		this.endDateOfAgmt = endDateOfAgmt;
	}
	
}