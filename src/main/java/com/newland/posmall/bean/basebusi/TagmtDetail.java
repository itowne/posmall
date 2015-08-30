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

import com.newland.posmall.bean.dict.AgmtDetailStatus;

/**
 * 协议明细表
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_agmt_detail")
public class TagmtDetail implements Serializable {

	private static final long serialVersionUID = 2801092965977700292L;

	@Id
	@TableGenerator(name = "i_agmt_detail", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_agmt_detail")
	@Column(name = "i_agmt_detail")
	private Long iagmtDetail; // 内部编号

	@Column(name = "i_agmt", updatable = false)
	private Long iagmt; // 协议内部编号

	@Column(name = "i_pdt", updatable = false)
	private Long ipdt; // 产品

	@Column(name = "i_pdt_his", updatable = false)
	private Long ipdtHis;

	@Column(name = "num")
	private Integer num; // 数量

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

	@Column(name = "gen_time", updatable = false)
	private Date genTime; // 生成时间

	@Column(name = "upd_time")
	private Date updTime; // 更新时间

	@Column(name = "agmt_detail_status")
	@Enumerated(EnumType.STRING)
	private AgmtDetailStatus agmtDetailStatus; // 状态

	@Version
	private Long version; // 版本号

	/**
	 * 折扣率
	 */
	@Column(name = "rate")
	private BigDecimal rate;

	@Column(name = "del_flag")
	@Type(type = "yes_no")
	private Boolean delFlag;

	@Column(name = "used_quota")
	private Integer usedQuota;// 已使用额度

	@Column(name = "remain_quota")
	private Integer remainQuota;// 剩余额度

	public Long getIagmtDetail() {
		return iagmtDetail;
	}

	public void setIagmtDetail(Long iagmtDetail) {
		this.iagmtDetail = iagmtDetail;
	}

	public Long getIagmt() {
		return iagmt;
	}

	public void setIagmt(Long iagmt) {
		this.iagmt = iagmt;
	}

	public Long getIpdt() {
		return ipdt;
	}

	public void setIpdt(Long ipdt) {
		this.ipdt = ipdt;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
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

	public AgmtDetailStatus getAgmtDetailStatus() {
		return agmtDetailStatus;
	}

	public void setAgmtDetailStatus(AgmtDetailStatus agmtDetailStatus) {
		this.agmtDetailStatus = agmtDetailStatus;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public Boolean getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Boolean delFlag) {
		this.delFlag = delFlag;
	}

	public Integer getUsedQuota() {
		return usedQuota;
	}

	public void setUsedQuota(Integer usedQuota) {
		this.usedQuota = usedQuota;
	}

	public Integer getRemainQuota() {
		return remainQuota;
	}

	public void setRemainQuota(Integer remainQuota) {
		this.remainQuota = remainQuota;
	}

	public Long getIpdtHis() {
		return ipdtHis;
	}

	public void setIpdtHis(Long ipdtHis) {
		this.ipdtHis = ipdtHis;
	}

}
