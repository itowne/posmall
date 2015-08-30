package com.newland.posmall.bean.basebusi;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Type;

import com.newland.posmall.bean.dict.ValidStatus;

/**
 * 订单明细
 * 
 * @author Administrator
 * 
 */
@Entity
@Table(name = "t_ord_detail")
public class TordDetail implements Serializable {

	private static final long serialVersionUID = 2801092965977700292L;

	@Id
	@TableGenerator(name = "i_ord_detail", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_ord_detail")
	@Column(name = "i_ord_detail")
	private Long iordDetail;// 内部编号

	@Column(name = "i_ord")
	private Long iord;// 订单内部编号

	@Column(name = "i_pdt_his")
	private Long ipdtHis;// 产品历史外键
	
	@Column(name = "i_pdt")
	private Long ipdt;// 产品外键

	@Column(name = "i_cust")
	private Long icust;// 客户外键

	@Column(name = "num")
	private Integer num;// 数量

	@Column(name = "price")
	private BigDecimal price;// 单价

	@Column(name = "rate")
	private BigDecimal rate;// 折扣率

	@Column(name = "amt")
	private BigDecimal amt;// 金额

	@Column(name = "ord_detail_status")
	@Enumerated(EnumType.STRING)
	private ValidStatus ordDetailStatus;// 状态

	@Version
	private Long version;// 版本号

	@Column(name = "upd_time")
	private Date updTime; // 更新时间

	@Column(name = "gen_time", updatable = false)
	private Date genTime; // 生成时间

	@Column(name = "used_quota")
	private Integer usedQuota;// 已使用额度

	@Column(name = "remain_quota")
	private Integer remainQuota;// 剩余额度

	@Column(name = "deliveryed")
	private Integer deliveryed;// 已发货数量

	@Column(name = "pending_num")
	private Integer pendingNum;// 待发货数量

	@Column(name = "produced_num")
	private Integer producedNum;// 已生产数量
	
	@Column(name = "del_flag")
	@Type(type = "yes_no")
	private Boolean delFlag;// 删除标志
	
	@Column(name="start_sn")
	private String startSn;// 号段起始值
	
	@Column(name="end_sn")
	private String endSn;// 号段结束值
	
	/**
	 * 已发货部分货款
	 */
	@Column(name = "amt_of_delivery")
	private BigDecimal amtOfDelivery;
	
	@Transient
	private List<TordDetailPdt> tordDetailPdtList;
	
	@Transient
	private String pdtName;// 产品名称
	
	@Transient
	private String custName;//客户名称

	public Long getIordDetail() {
		return iordDetail;
	}

	public void setIordDetail(Long iordDetail) {
		this.iordDetail = iordDetail;
	}

	public Long getIord() {
		return iord;
	}

	public void setIord(Long iord) {
		this.iord = iord;
	}

	public Long getIpdtHis() {
		return ipdtHis;
	}

	public void setIpdtHis(Long ipdtHis) {
		this.ipdtHis = ipdtHis;
	}
	
	public Long getIpdt() {
		return ipdt;
	}
	
	public void setIpdt(Long ipdt) {
		this.ipdt = ipdt;
	}

	public Long getIcust() {
		return icust;
	}

	public void setIcust(Long icust) {
		this.icust = icust;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public ValidStatus getOrdDetailStatus() {
		return ordDetailStatus;
	}

	public void setOrdDetailStatus(ValidStatus ordDetailStatus) {
		this.ordDetailStatus = ordDetailStatus;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Date getUpdTime() {
		return updTime;
	}

	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}

	public Date getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}

	public Integer getUsedQuota() {
		return usedQuota;
	}

	public void setUsedQuota(Integer usedQuota) {
		this.usedQuota = usedQuota;
	}
	

	public Integer getDeliveryed() {
		return deliveryed;
	}

	public void setDeliveryed(Integer deliveryed) {
		this.deliveryed = deliveryed;
	}

	public Integer getPendingNum() {
		return pendingNum;
	}

	public void setPendingNum(Integer pendingNum) {
		this.pendingNum = pendingNum;
	}

	public Integer getProducedNum() {
		return producedNum;
	}

	public void setProducedNum(Integer producedNum) {
		this.producedNum = producedNum;
	}

	public Integer getRemainQuota() {
		return remainQuota;
	}

	public void setRemainQuota(Integer remainQuota) {
		this.remainQuota = remainQuota;
	}

	public Boolean getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Boolean delFlag) {
		this.delFlag = delFlag;
	}

	public List<TordDetailPdt> getTordDetailPdtList() {
		return tordDetailPdtList;
	}

	public void setTordDetailPdtList(List<TordDetailPdt> tordDetailPdtList) {
		this.tordDetailPdtList = tordDetailPdtList;
	}

	public String getPdtName() {
		return pdtName;
	}

	public void setPdtName(String pdtName) {
		this.pdtName = pdtName;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
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

	public BigDecimal getAmtOfDelivery() {
		return amtOfDelivery;
	}

	public void setAmtOfDelivery(BigDecimal amtOfDelivery) {
		this.amtOfDelivery = amtOfDelivery;
	}

}
