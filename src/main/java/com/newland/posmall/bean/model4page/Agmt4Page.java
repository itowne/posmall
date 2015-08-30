package com.newland.posmall.bean.model4page;

import java.math.BigDecimal;
import java.util.List;

import com.newland.posmall.bean.basebusi.Tagmt;

public class Agmt4Page {

	/**
	 * 协议model
	 */
	private Tagmt tagmt;

	/**
	 * 协议订货量
	 */
	private Integer totalNum;

	/**
	 * 订货剩余量
	 */
	private Integer remainNum;

	/**
	 * 总货款金额
	 */
	private BigDecimal totalAmt;

	/**
	 * 产品名称集合
	 */
	private String tpdtNames;

	/**
	 * 协议明细
	 */
	private List<AgmtDetail4Page> agmtDetail4PageList;
	
	/**
	 * 变更明细
	 */
	private List<AgmtDetailHis4Page> agmtDetailHis4PageList;

	/**
	 * 剩余天数
	 */
	private Integer remainDay;

	public Tagmt getTagmt() {
		return tagmt;
	}

	public void setTagmt(Tagmt tagmt) {
		this.tagmt = tagmt;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Integer getRemainNum() {
		return remainNum;
	}

	public void setRemainNum(Integer remainNum) {
		this.remainNum = remainNum;
	}

	public BigDecimal getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(BigDecimal totalAmt) {
		this.totalAmt = totalAmt;
	}

	public String getTpdtNames() {
		return tpdtNames;
	}

	public void setTpdtNames(String tpdtNames) {
		this.tpdtNames = tpdtNames;
	}

	public List<AgmtDetail4Page> getAgmtDetail4PageList() {
		return agmtDetail4PageList;
	}

	public void setAgmtDetail4PageList(List<AgmtDetail4Page> agmtDetail4PageList) {
		this.agmtDetail4PageList = agmtDetail4PageList;
	}

	public Integer getRemainDay() {
		return remainDay;
	}

	public void setRemainDay(Integer remainDay) {
		this.remainDay = remainDay;
	}

	public List<AgmtDetailHis4Page> getAgmtDetailHis4PageList() {
		return agmtDetailHis4PageList;
	}

	public void setAgmtDetailHis4PageList(
			List<AgmtDetailHis4Page> agmtDetailHis4PageList) {
		this.agmtDetailHis4PageList = agmtDetailHis4PageList;
	}

}
