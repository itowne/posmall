package com.newland.posmall.bean.basebusi;

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

import org.hibernate.annotations.Type;

import com.newland.posmall.bean.dict.YesNoType;

/**
 * 产品号段表
 */
@Entity
@Table(name = "t_pdt_no")
public class TpdtNo implements Serializable {

	private static final long serialVersionUID = -6427514646649266609L;

	@Id
	@TableGenerator(name = "i_pdt_no", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_pdt_no")
	@Column(name = "i_pdt_no")
	private Long ipdtNo; // 内部编号

	@Column(name = "pdt_no")
	private String pdtNo;// 序列号

	@Column(name = "appver_type")
	@Enumerated(EnumType.STRING)
	private YesNoType appverType;// 是否有应用版本

	@Column(name = "appver_memo")
	private String appverMemo;// 应用版本描述

	@Column(name = "i_cust")
	private Long icust;// 客户外键

	@Column(name = "i_ord")
	private Long iord;// 订单外键

	@Column(name = "i_ord_detail")
	private Long iordDetail; // 订单明细

	@Column(name = "i_logistics_ord")
	private Long ilogisticsOrd; // 物流单号外键

	@Column(name = "i_logistics_ord_addr")
	private Long ilogisticsOrdAddr; // 物流单地址外键

	@Column(name = "lock_type")
	@Enumerated(EnumType.STRING)
	private YesNoType lockType;// 锁定类型

	@Column(name = "del_flag")
	@Type(type = "yes_no")
	private Boolean delFlag;// 删除标志

	@Column(name = "gen_time", updatable = false)
	private Date genTime; // 生成时间

	@Column(name = "upd_time")
	private Date updTime; // 更新时间

	@Version
	private Long version; // 版本号

	public Long getIpdtNo() {
		return ipdtNo;
	}

	public void setIpdtNo(Long ipdtNo) {
		this.ipdtNo = ipdtNo;
	}

	public String getPdtNo() {
		return pdtNo;
	}

	public void setPdtNo(String pdtNo) {
		this.pdtNo = pdtNo;
	}

	public YesNoType getAppverType() {
		return appverType;
	}

	public void setAppverType(YesNoType appverType) {
		this.appverType = appverType;
	}

	public String getAppverMemo() {
		return appverMemo;
	}

	public void setAppverMemo(String appverMemo) {
		this.appverMemo = appverMemo;
	}

	public Long getIcust() {
		return icust;
	}

	public void setIcust(Long icust) {
		this.icust = icust;
	}

	public Long getIord() {
		return iord;
	}

	public void setIord(Long iord) {
		this.iord = iord;
	}

	public Long getIordDetail() {
		return iordDetail;
	}

	public void setIordDetail(Long iordDetail) {
		this.iordDetail = iordDetail;
	}

	public Long getIlogisticsOrd() {
		return ilogisticsOrd;
	}

	public void setIlogisticsOrd(Long ilogisticsOrd) {
		this.ilogisticsOrd = ilogisticsOrd;
	}

	public Long getIlogisticsOrdAddr() {
		return ilogisticsOrdAddr;
	}

	public void setIlogisticsOrdAddr(Long ilogisticsOrdAddr) {
		this.ilogisticsOrdAddr = ilogisticsOrdAddr;
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

	public Boolean getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Boolean delFlag) {
		this.delFlag = delFlag;
	}

	public YesNoType getLockType() {
		return lockType;
	}

	public void setLockType(YesNoType lockType) {
		this.lockType = lockType;
	}

}
