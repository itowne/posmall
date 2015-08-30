package com.newland.posmall.bean.basebusi;

import java.io.Serializable;
import java.math.BigDecimal;
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

import com.newland.posmall.bean.dict.AgmtStatus;

/**
 * 协议表
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_agmt")
public class Tagmt implements Serializable {

	private static final long serialVersionUID = -9187917966003339059L;

	@Id
	@TableGenerator(name = "i_agmt", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_agmt")
	@Column(name = "i_agmt")
	private Long iagmt; // 内部编号

	@Column(name = "i_cust", updatable = false)
	private Long icust;// 客户内部编号

	/**
	 * 协议起始时间
	 */
	@Column(name = "start_time")
	private Date startTime;

	/**
	 * 协议终止时间
	 */
	@Column(name = "end_time")
	private Date endTime;

	@Column(name = "deposit")
	private BigDecimal deposit; // 保证金

	@Column(name = "gen_time", updatable = false)
	private Date genTime; // 生成时间

	@Column(name = "upd_time")
	private Date updTime; // 更新时间

	/**
	 * 协议生效时间（审核通过的时间）
	 */
	@Column(name = "efficient_time")
	private Date efficientTime;

	/**
	 * 已用保证金
	 */
	@Column(name = "used_deposit")
	private BigDecimal usedDeposit;

	/**
	 * 剩余保证金
	 */
	@Column(name = "remain_deposit")
	private BigDecimal remainDeposit;

	/**
	 * 协议状态
	 */
	@Column(name = "agmt_status")
	@Enumerated(EnumType.STRING)
	private AgmtStatus agmtStatus;

	@Version
	private Long version; // 版本号

	/**
	 * 协议编号
	 */
	@Column(name = "agmt_no")
	private String agmtNo;

	@Column(name = "del_flag")
	@Type(type = "yes_no")
	private Boolean delFlag;
	
	/**
	 * 协议变更之后多余的保证金
	 * <h1>如果是负值，代表保证金不足需要补交，取反
	 */
	@Column(name = "redundant_deposit")
	private BigDecimal redundantDeposit;
	
	@Column(name = "erp_ord_id")
	private String erpOrdId;

	public Long getIagmt() {
		return iagmt;
	}

	public void setIagmt(Long iagmt) {
		this.iagmt = iagmt;
	}

	public Long getIcust() {
		return icust;
	}

	public void setIcust(Long icust) {
		this.icust = icust;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public BigDecimal getDeposit() {
		return deposit;
	}

	public void setDeposit(BigDecimal deposit) {
		this.deposit = deposit;
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

	public Date getEfficientTime() {
		return efficientTime;
	}

	public void setEfficientTime(Date efficientTime) {
		this.efficientTime = efficientTime;
	}

	public BigDecimal getUsedDeposit() {
		return usedDeposit;
	}

	public void setUsedDeposit(BigDecimal usedDeposit) {
		this.usedDeposit = usedDeposit;
	}

	public BigDecimal getRemainDeposit() {
		return remainDeposit;
	}

	public void setRemainDeposit(BigDecimal remainDeposit) {
		this.remainDeposit = remainDeposit;
	}

	public AgmtStatus getAgmtStatus() {
		return agmtStatus;
	}

	public void setAgmtStatus(AgmtStatus agmtStatus) {
		this.agmtStatus = agmtStatus;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getAgmtNo() {
		return agmtNo;
	}

	public void setAgmtNo(String agmtNo) {
		this.agmtNo = agmtNo;
	}

	public Boolean getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Boolean delFlag) {
		this.delFlag = delFlag;
	}

	public BigDecimal getRedundantDeposit() {
		return redundantDeposit;
	}

	public void setRedundantDeposit(BigDecimal redundantDeposit) {
		this.redundantDeposit = redundantDeposit;
	}

	public String getErpOrdId() {
		return erpOrdId;
	}

	public void setErpOrdId(String erpOrdId) {
		this.erpOrdId = erpOrdId;
	}
    
}
