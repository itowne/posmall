package com.newland.posmall.controller.cust.model;


/**
 * 报修页面数据
 * @author zhouym
 *
 * @since 2014-10-29
 */
public class Warranty4Page {

	private Long ierpMaintenance;// 逻辑主键

	private String seqNo;// 产品序列号

	private String pdtNo;// 产品型号

	private String pm;// 产品名称

	private String purchaseDate;// 发货日期

	private String warrantyPeriod;// 保修期限

	private String lastRepairedDate;// 最后修复日期

	private Integer repairNum;// 报修次数

	private String remark; // 报修备注（客户填写）

	public Long getIerpMaintenance() {
		return ierpMaintenance;
	}

	public void setIerpMaintenance(Long ierpMaintenance) {
		this.ierpMaintenance = ierpMaintenance;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public String getPdtNo() {
		return pdtNo;
	}

	public void setPdtNo(String pdtNo) {
		this.pdtNo = pdtNo;
	}

	public String getPm() {
		return pm;
	}

	public void setPm(String pm) {
		this.pm = pm;
	}

	public String getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getWarrantyPeriod() {
		return warrantyPeriod;
	}

	public void setWarrantyPeriod(String warrantyPeriod) {
		this.warrantyPeriod = warrantyPeriod;
	}

	public String getLastRepairedDate() {
		return lastRepairedDate;
	}

	public void setLastRepairedDate(String lastRepairedDate) {
		this.lastRepairedDate = lastRepairedDate;
	}

	public Integer getRepairNum() {
		return repairNum;
	}

	public void setRepairNum(Integer repairNum) {
		this.repairNum = repairNum;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
