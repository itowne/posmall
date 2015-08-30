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
import com.newland.posmall.bean.dict.PayStatus;

/**
 * 仓管费
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_ware_house")
public class TwareHouse implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "i_ware_house", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_ware_house")
	@Column(name = "i_ware_house")
	private Long iwareHouse;// 内部编号

	@Column(name = "i_cust")
	private Long icust;
	
	@Column(name = "i_agmt")
	private Long iagmt;

	@Column(name = "remain_quota")
	private Integer remainQuota;// 剩余数量

	@Column(name = "pay_status")
	@Enumerated(EnumType.STRING)
	private PayStatus payStatus; //支付状态

	@Column(name = "amt")
	private BigDecimal amt;// 仓管费金额

	@Column(name = "gen_time", updatable = false)
	private Date genTime;// 生成时间
	
	@Column(name = "upd_time")
	private Date updTime;// 生成时间

	public Long getIwareHouse() {
		return iwareHouse;
	}

	public void setIwareHouse(Long iwareHouse) {
		this.iwareHouse = iwareHouse;
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

	public Integer getRemainQuota() {
		return remainQuota;
	}

	public void setRemainQuota(Integer remainQuota) {
		this.remainQuota = remainQuota;
	}

	public PayStatus getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(PayStatus payStatus) {
		this.payStatus = payStatus;
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

	public Date getUpdTime() {
		return updTime;
	}

	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}
	
	
	

}
