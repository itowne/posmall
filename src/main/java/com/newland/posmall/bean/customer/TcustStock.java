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

/**
 * 客户库存表
 * 
 * @author zhouym
 *
 */
@Entity
@Table(name = "t_cust_stock")
public class TcustStock implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7017227702928576170L;

	/**
	 * 内部编号
	 */
	@Id
	@TableGenerator(name = "i_cust_stock", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_cust_stock")
	@Column(name = "i_cust_stock")
	private Long icustStock;
	
	/**
	 * 客户id
	 */
	@Column(name = "i_cust")
	private Long icust;
	
	/**
	 * 产品id
	 */
	@Column(name = "i_pdt")
	private Long ipdt;
	
	/**
	 * 汇总：库存（订单付款前）
	 */
	@Column(name = "sum_ord_num_4pay")
	private Long sumOrdNum4Pay;
	
	/**
	 * 汇总：库存（订单付款后）
	 */
	@Column(name = "sum_ord_num_paid ")
	private Long sumOrdNumPaid ;
	
	/**
	 * 汇总：订单总量
	 */
	@Column(name = "sum_ord_num_sum")
	private Long sumOrdNumSum;
	
	/**
	 * 汇总：已撤销订单
	 */
	@Column(name = "sum_ord_num_cancel")
	private Long sumOrdNumCancel;
	
	/**
	 * 汇总：库存订单（ 未发货）
	 */
	@Column(name = "sum_ord_num")
	private Long sumOrdNum;
	
	/**
	 * 汇总：已发货量
	 */
	@Column(name = "sum_shipment_num")
	private Long sumShipmentNum;
	
	/**
	 * 汇总：现货量（已生产）
	 */
	@Column(name = "sum_spod_num")
	private Long sumSpodNum;
	
	/**
	 * 生成时间
	 */
	@Column(name = "gen_time",updatable = false)
	private Date genTime;
	
	/**
	 * 更新时间
	 */
	@Column(name = "upd_time")
	private Date updTime;
	
	@Version
	private Long version;

	public Long getIcustStock() {
		return icustStock;
	}

	public void setIcustStock(Long icustStock) {
		this.icustStock = icustStock;
	}

	public Long getIcust() {
		return icust;
	}

	public void setIcust(Long icust) {
		this.icust = icust;
	}

	public Long getIpdt() {
		return ipdt;
	}

	public void setIpdt(Long ipdt) {
		this.ipdt = ipdt;
	}

	public Long getSumOrdNumPaid() {
		return sumOrdNumPaid;
	}

	public void setSumOrdNumPaid(Long sumOrdNumPaid) {
		this.sumOrdNumPaid = sumOrdNumPaid;
	}

	public Long getSumOrdNum4Pay() {
		return sumOrdNum4Pay;
	}

	public void setSumOrdNum4Pay(Long sumOrdNum4Pay) {
		this.sumOrdNum4Pay = sumOrdNum4Pay;
	}

	public Long getSumOrdNumCancel() {
		return sumOrdNumCancel;
	}

	public void setSumOrdNumCancel(Long sumOrdNumCancel) {
		this.sumOrdNumCancel = sumOrdNumCancel;
	}

	public Long getSumOrdNum() {
		return sumOrdNum;
	}

	public void setSumOrdNum(Long sumOrdNum) {
		this.sumOrdNum = sumOrdNum;
	}

	public Long getSumOrdNumSum() {
		return sumOrdNumSum;
	}

	public void setSumOrdNumSum(Long sumOrdNumSum) {
		this.sumOrdNumSum = sumOrdNumSum;
	}

	public Long getSumSpodNum() {
		return sumSpodNum;
	}

	public void setSumSpodNum(Long sumSpodNum) {
		this.sumSpodNum = sumSpodNum;
	}

	public Long getSumShipmentNum() {
		return sumShipmentNum;
	}

	public void setSumShipmentNum(Long sumShipmentNum) {
		this.sumShipmentNum = sumShipmentNum;
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
	
}
