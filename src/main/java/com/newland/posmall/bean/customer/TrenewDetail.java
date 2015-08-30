package com.newland.posmall.bean.customer;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.hibernate.annotations.Type;

/**
 * 产品续保明细表
 * 
 * @author zhouym
 * @since 2014-10-27
 */
@Entity
@Table(name = "t_renew_detail")
public class TrenewDetail implements Serializable {

	private static final long serialVersionUID = -6616767913875179071L;

	@Id
	@TableGenerator(name = "i_renew_detail", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_renew_detail")
	@Column(name = "i_renew_detail")
	private Long irenewDetail;// 内部编号

	@Column(name = "i_renew")
	private Long irenew; // 产品续保表外键

	@Column(name = "i_erp_maintenance")
	private Long ierpMaintenance;// 维保信息表外键

	@Column(name = "seq_no")
	private String seqNo;// 产品序列号

	@Column(name = "pdt_no")
	private String pdtNo;// 产品型号

	@Column(name = "life_start_time")
	private Date lifeStartTime;// 保修期起始时间

	@Column(name = "life_end_time")
	private Date lifeEndTime;// 保修期结束时间

	@Column(name = "gen_time", updatable = false)
	private Date genTime;// 生成时间

	@Column(name = "upd_time")
	private Date updTime;// 更新时间
	
	@Column(name = "del_flag")
	@Type(type = "yes_no")
	private Boolean delFlag;// 删除标志

	@Version
	private Long version;// 版本

	public Long getIrenewDetail() {
		return irenewDetail;
	}

	public void setIrenewDetail(Long irenewDetail) {
		this.irenewDetail = irenewDetail;
	}

	public Long getIrenew() {
		return irenew;
	}

	public void setIrenew(Long irenew) {
		this.irenew = irenew;
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

	public Date getLifeStartTime() {
		return lifeStartTime;
	}

	public void setLifeStartTime(Date lifeStartTime) {
		this.lifeStartTime = lifeStartTime;
	}

	public Date getLifeEndTime() {
		return lifeEndTime;
	}

	public void setLifeEndTime(Date lifeEndTime) {
		this.lifeEndTime = lifeEndTime;
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

}
