package com.newland.posmall.bean.basebusi;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

/**
 * 仓管费详细信息
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_ware_house_detail")
public class TwareHouseDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "i_ware_house_detail", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_ware_house_detail")
	@Column(name = "i_ware_house_detail")
	private Long iwareHouseDetail;// 内部编号
	
	@Column(name = "i_ware_house")
	private Long iwareHouse;// 仓管费ID

	@Column(name = "i_cust")
	private Long icust;
	
	@Column(name = "i_agmt")
	private Long iagmt;

	@Column(name = "i_ord")
	private Long iord;// 订单id
	
	@Column(name = "i_ord_detail_pdt")
	private Long iordDetailPdt;
	
	@Column(name = "i_pdt")
	private Long ipdt;// 产品id
	
	@Column(name = "date_varchar")
	private String dateVarchar; //下单产品的日期

	@Column(name = "remain_quota")
	private Integer remainQuota;// 可发货数量

	@Column(name = "used_quota")
	private Integer usedQuota;// 已用额度

	@Column(name = "amt")
	private BigDecimal amt;// 仓管费金额

	@Column(name = "gen_time", updatable = false)
	private Date genTime;// 生成时间

	@Transient
	private String pdtName;// 产品名称
	
	public String getPdtName() {
		return pdtName;
	}

	public void setPdtName(String pdtName) {
		this.pdtName = pdtName;
	}

	public Long getIord() {
		return iord;
	}

	public void setIord(Long iord) {
		this.iord = iord;
	}

	public Long getIpdt() {
		return ipdt;
	}

	public void setIpdt(Long ipdt) {
		this.ipdt = ipdt;
	}

	public Long getIwareHouseDetail() {
		return iwareHouseDetail;
	}

	public void setIwareHouseDetail(Long iwareHouseDetail) {
		this.iwareHouseDetail = iwareHouseDetail;
	}

	public Long getIcust() {
		return icust;
	}

	public void setIcust(Long icust) {
		this.icust = icust;
	}

	public Long getIagmt() {
		return iagmt;
	}

	public void setIagmt(Long iagmt) {
		this.iagmt = iagmt;
	}

	public Long getIordDetailPdt() {
		return iordDetailPdt;
	}

	public void setIordDetailPdt(Long iordDetailPdt) {
		this.iordDetailPdt = iordDetailPdt;
	}

	public String getDateVarchar() {
		return dateVarchar;
	}

	public void setDateVarchar(String dateVarchar) {
		this.dateVarchar = dateVarchar;
	}

	public Integer getRemainQuota() {
		return remainQuota;
	}

	public void setRemainQuota(Integer remainQuota) {
		this.remainQuota = remainQuota;
	}

	public Integer getUsedQuota() {
		return usedQuota;
	}

	public void setUsedQuota(Integer usedQuota) {
		this.usedQuota = usedQuota;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public Date getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}

	public Long getIwareHouse() {
		return iwareHouse;
	}

	public void setIwareHouse(Long iwareHouse) {
		this.iwareHouse = iwareHouse;
	}

	
	

}
