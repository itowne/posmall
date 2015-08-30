package com.newland.posmall.bean.customer;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import com.newland.posmall.bean.dict.WarrantyStatus;

/**
 * 产品报修信息表
 * 
 * @author zhouym
 * @since 2014-10-21
 */
@Entity
@Table(name = "t_warranty")
public class Twarranty implements Serializable {

	private static final long serialVersionUID = 3025700377329437729L;

	@Id
	@TableGenerator(name = "i_warranty", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_warranty")
	@Column(name = "i_warranty")
	private Long iwarranty;// 内部编号

	@Column(name = "i_erp_maintenance")
	private Long ierpMaintenance;// 维保信息表外键

	@Column(name = "seq_no")
	private String seqNo;// 产品序列号

	@Column(name = "pdt_no")
	private String pdtNo;// 产品型号

	@Column(name = "warranty_time")
	private Date warrantyTime;// 报修时间

	@Column(name = "accept_time")
	private Date acceptTime; // 受理时间

	@Column(name = "repaired_time")
	private Date repairedTime;// 修复时间

	@Column(name = "warranty_status")
	@Enumerated(EnumType.STRING)
	private WarrantyStatus warrantyStatus;// 状态

	@Column(name = "i_cust")
	private Long icust;// 操作人id

	@Column(name = "cust_name")
	private String custName; // 操作人姓名

	@Column(name = "warranty_person")
	private String warrantyPerson; // 指定修复人
	
	@Column(name = "trace_no")
	private String traceNo;//追踪号(下载文件回写)

	@Column(name = "remark")
	private String remark; // 备注
	
	@Column(name = "repaired_remark")
	private String repairedRemark; // 修复反馈

	@Column(name = "gen_time", updatable = false)
	private Date genTime;// 生成时间

	@Column(name = "upd_time")
	private Date updTime;// 更新时间

	@Version
	private Long version;// 版本
	
	@Column(name = "new_seq_no")
	private String newSeqNo;// 3个月内包换，更换机器，新的产品序列号

	public Long getIwarranty() {
		return iwarranty;
	}

	public void setIwarranty(Long iwarranty) {
		this.iwarranty = iwarranty;
	}

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

	public Date getWarrantyTime() {
		return warrantyTime;
	}

	public void setWarrantyTime(Date warrantyTime) {
		this.warrantyTime = warrantyTime;
	}

	public Date getAcceptTime() {
		return acceptTime;
	}

	public void setAcceptTime(Date acceptTime) {
		this.acceptTime = acceptTime;
	}

	public Date getRepairedTime() {
		return repairedTime;
	}

	public void setRepairedTime(Date repairedTime) {
		this.repairedTime = repairedTime;
	}

	public WarrantyStatus getWarrantyStatus() {
		return warrantyStatus;
	}

	public void setWarrantyStatus(WarrantyStatus warrantyStatus) {
		this.warrantyStatus = warrantyStatus;
	}

	public Long getIcust() {
		return icust;
	}

	public void setIcust(Long icust) {
		this.icust = icust;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getWarrantyPerson() {
		return warrantyPerson;
	}

	public void setWarrantyPerson(String warrantyPerson) {
		this.warrantyPerson = warrantyPerson;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}

	public Date getUpdTime() {
		return updTime;
	}

	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getTraceNo() {
		return traceNo;
	}

	public void setTraceNo(String traceNo) {
		this.traceNo = traceNo;
	}

	public String getRepairedRemark() {
		return repairedRemark;
	}

	public void setRepairedRemark(String repairedRemark) {
		this.repairedRemark = repairedRemark;
	}

	public String getNewSeqNo() {
		return newSeqNo;
	}

	public void setNewSeqNo(String newSeqNo) {
		this.newSeqNo = newSeqNo;
	}
	
   
}
