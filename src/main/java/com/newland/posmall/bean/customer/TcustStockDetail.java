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

import com.newland.posmall.bean.dict.CustStockDetailType;

/**
 * 出入库明细表
 * 
 * @author zhouym
 *
 */
@Entity
@Table(name = "t_cust_stock_detail")
public class TcustStockDetail implements Serializable {

	private static final long serialVersionUID = 8717511677466352164L;

	/**
	 * 内部编号
	 */
	@Id
	@TableGenerator(name = "i_cust_stock_detail", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_cust_stock_detail")
	@Column(name = "i_cust_stock_detail")
	private Long icustStockDetail;
	
	/**
	 * 客户库存表id
	 */
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
	 * 数量
	 */
	@Column(name = "num")
	private int num;
	
	/**
	 * 出入库操作类型（下订单，撤销，生产，出货，付款）
	 */
	@Column(name = "cust_stock_detail_type")
	@Enumerated(EnumType.STRING)
	private CustStockDetailType custStockDetailType;
	
	/**
	 * 出入库操作对应来源（订单，物流，付款，手工调整）
	 */
	@Column(name = "i_fk")
	private Long ifk;
	
	/**
	 * 操作人id
	 */
	@Column(name = "i_user")
	private Long iuser;
	
	/**
	 * 操作人姓名
	 */
	@Column(name = "user_name")
	private String userName;
	
	/*********************变化量****************************/
	/**
	 * 变化量：库存订单(付款前）
	 */
	@Column(name = "delta_ord_num_4pay")
	private Long deltaOrdNum4Pay;
	
	/**
	 * 变化量：库存订单（付款后）
	 */
	@Column(name = "delta_ord_num_paid")
	private Long deltaOrdNumPaid;
	
	/**
	 * 变化量：订单总量
	 */
	@Column(name = "delta_ord_num_sum")
	private Long deltaOrdNumSum;
	
	/**
	 * 变化量：取消订单
	 */
	@Column(name = "delta_ord_num_cancel")
	private Long deltaOrdNumCancel;
	
	/**
	 * 变化量：库存订单（ 未发货）
	 */
	@Column(name = "delta_ord_num")
	private Long deltaOrdNum;
	
	/**
	 * 变化量：出货量
	 */
	@Column(name = "delta_shipment_num")
	private Long deltaShipmentNum;
	
	/**
	 * 变化量：库存现货
	 */
	@Column(name = "delta_spot_num")
	private Long deltaSpotNum;
	
	
	/*********************汇总量****************************/
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
	
	public Long getIcustStockDetail() {
		return icustStockDetail;
	}

	public void setIcustStockDetail(Long icustStockDetail) {
		this.icustStockDetail = icustStockDetail;
	}

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

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public CustStockDetailType getCustStockDetailType() {
		return custStockDetailType;
	}

	public void setCustStockDetailType(CustStockDetailType custStockDetailType) {
		this.custStockDetailType = custStockDetailType;
	}

	public Long getIfk() {
		return ifk;
	}

	public void setIfk(Long ifk) {
		this.ifk = ifk;
	}

	public Long getIuser() {
		return iuser;
	}

	public void setIuser(Long iuser) {
		this.iuser = iuser;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getDeltaOrdNumPaid() {
		return deltaOrdNumPaid;
	}

	public void setDeltaOrdNumPaid(Long deltaOrdNumPaid) {
		this.deltaOrdNumPaid = deltaOrdNumPaid;
	}

	public Long getDeltaOrdNum4Pay() {
		return deltaOrdNum4Pay;
	}

	public void setDeltaOrdNum4Pay(Long deltaOrdNum4Pay) {
		this.deltaOrdNum4Pay = deltaOrdNum4Pay;
	}

	public Long getDeltaOrdNumCancel() {
		return deltaOrdNumCancel;
	}

	public void setDeltaOrdNumCancel(Long deltaOrdNumCancel) {
		this.deltaOrdNumCancel = deltaOrdNumCancel;
	}

	public Long getDeltaOrdNum() {
		return deltaOrdNum;
	}

	public void setDeltaOrdNum(Long deltaOrdNum) {
		this.deltaOrdNum = deltaOrdNum;
	}

	public Long getDeltaOrdNumSum() {
		return deltaOrdNumSum;
	}

	public void setDeltaOrdNumSum(Long deltaOrdNumSum) {
		this.deltaOrdNumSum = deltaOrdNumSum;
	}

	public Long getDeltaSpotNum() {
		return deltaSpotNum;
	}

	public void setDeltaSpotNum(Long deltaSpotNum) {
		this.deltaSpotNum = deltaSpotNum;
	}

	public Long getDeltaShipmentNum() {
		return deltaShipmentNum;
	}

	public void setDeltaShipmentNum(Long deltaShipmentNum) {
		this.deltaShipmentNum = deltaShipmentNum;
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
	
}
