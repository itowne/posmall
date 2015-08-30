package com.newland.posmall.bean.basebusi;

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

/**
 * 订单产品明细表3
 */
@Entity
@Table(name = "t_ord_detail_pdt_appver")
public class TordDetailPdtAppver implements Serializable {

	private static final long serialVersionUID = -539668920344356077L;

	@Id
	@TableGenerator(name = "i_ord_detail_pdt_appver", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_ord_detail_pdt_appver")
	@Column(name = "i_ord_detail_pdt_appver")
	private Long iordDetailPdtAppver;// 内部编号

	@Column(name = "i_ord_detail_pdt")
	private Long iordDetailPdt;// 订单产品明细2外键
	
	@Column(name = "i_cust_appver")
	private Long icustAppver;// 客户应用版本外键
	
	@Column(name = "appver")
	private String appver;// 客户应用版本描述
	
	@Column(name = "num")
	private Integer num;// 数量

	@Column(name = "gen_time", updatable = false)
	private Date genTime;// 生成时间

	@Column(name = "upd_time")
	private Date updTime;// 更新时间

	@Version
	private Long version;// 版本号
	
	@Column(name="start_sn")
	private String startSn;// 号段起始值
	
	@Column(name="end_sn")
	private String endSn;// 号段结束值

	public Long getIordDetailPdtAppver() {
		return iordDetailPdtAppver;
	}

	public void setIordDetailPdtAppver(Long iordDetailPdtAppver) {
		this.iordDetailPdtAppver = iordDetailPdtAppver;
	}

	public Long getIordDetailPdt() {
		return iordDetailPdt;
	}

	public void setIordDetailPdt(Long iordDetailPdt) {
		this.iordDetailPdt = iordDetailPdt;
	}

	public Long getIcustAppver() {
		return icustAppver;
	}

	public void setIcustAppver(Long icustAppver) {
		this.icustAppver = icustAppver;
	}

	public String getAppver() {
		return appver;
	}

	public void setAppver(String appver) {
		this.appver = appver;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
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

	public String getStartSn() {
		return startSn;
	}

	public void setStartSn(String startSn) {
		this.startSn = startSn;
	}

	public String getEndSn() {
		return endSn;
	}

	public void setEndSn(String endSn) {
		this.endSn = endSn;
	}

}
