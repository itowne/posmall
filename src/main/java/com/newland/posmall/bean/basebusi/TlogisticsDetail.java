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
 * 物流明细
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_logistics_detail")
public class TlogisticsDetail implements Serializable {

	private static final long serialVersionUID = -9187917966003339059L;

	@Id
	@TableGenerator(name = "i_logistics_detail", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_logistics_detail")
	@Column(name = "i_logistics_detail")
	private Long ilogisticsDetail; // 内部编号

	@Column(name = "i_logistics_ord")
	private Long ilogisticsOrd; // 物流单外键

	@Column(name = "i_ord_detail_pdt")
	private Long iordDetailPdt;// 订单产品明细2外键id

	@Column(name = "i_pdt_his")
	private Long ipdtHis;// 产品外键

	@Column(name = "pdt_num")
	private Integer pdtNum;// 产品数量

	@Column(name = "i_cust")
	private Long icust;// 客户外键

	@Column(name = "gen_time", updatable = false)
	private Date genTime;// 生成时间

	@Column(name = "upd_time")
	private Date updTime;// 更新时间

	@Version
	private Long version;// 版本

	public Long getIlogisticsDetail() {
		return ilogisticsDetail;
	}

	public void setIlogisticsDetail(Long ilogisticsDetail) {
		this.ilogisticsDetail = ilogisticsDetail;
	}

	public Long getIlogisticsOrd() {
		return ilogisticsOrd;
	}

	public void setIlogisticsOrd(Long ilogisticsOrd) {
		this.ilogisticsOrd = ilogisticsOrd;
	}

	public Long getIpdtHis() {
		return ipdtHis;
	}

	public void setIpdtHis(Long ipdtHis) {
		this.ipdtHis = ipdtHis;
	}

	public Integer getPdtNum() {
		return pdtNum;
	}

	public void setPdtNum(Integer pdtNum) {
		this.pdtNum = pdtNum;
	}

	public Long getIcust() {
		return icust;
	}

	public void setIcust(Long icust) {
		this.icust = icust;
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

	public Long getIordDetailPdt() {
		return iordDetailPdt;
	}

	public void setIordDetailPdt(Long iordDetailPdt) {
		this.iordDetailPdt = iordDetailPdt;
	}

}
