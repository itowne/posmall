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

import com.newland.posmall.bean.dict.DownLoadStat;
import com.newland.posmall.bean.dict.ValidStatus;

/**
 * 订单明细历史表
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_ord_detail_his")
public class TordDetailHis implements Serializable {

	private static final long serialVersionUID = 2801092965977700292L;

	@Id
	@TableGenerator(name = "i_ord_detail_his", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_ord_detail_his")
	@Column(name = "i_ord_detail_his")
	private Long iordDetailHis;// 订单明细历史编号

	@Column(name = "i_ord_his", updatable = false)
	private Long iordHis;// 订单历史表编号

	@Column(name = "i_ord_detail", updatable = false)
	private Long iordDetail;// 内部编号

	@Column(name = "i_ord", updatable = false)
	private Long iord;// 订单内部编号

	@Column(name = "i_cust")
	private Long icust;// 客户外键

	@Column(name = "i_pdt_his", updatable = false)
	private Long ipdtHis;// 产品历史外键
	
	@Column(name = "i_pdt", updatable = false)
	private Long ipdt;// 产品外键

	@Column(name = "num", updatable = false)
	private Integer num;// 数量

	@Column(name = "price", updatable = false)
	private BigDecimal price;// 单价

	@Column(name = "amt", updatable = false)
	private BigDecimal amt;// 金额

	@Column(name = "ord_detail_status")
	@Enumerated(EnumType.STRING)
	private ValidStatus ordDetailStatus;// 状态

	@Column(name = "gen_time", updatable = false)
	private Date genTime; // 生成时间

	@Column(name = "rate")
	private BigDecimal rate;// 折扣率

	@Column(name = "used_quota")
	private Integer usedQuota;// 已使用额度

	@Column(name = "deliveryed")
	private Integer deliveryed;// 已发货数量

	@Column(name = "pending_num")
	private Integer pendingNum;// 待发货数量

	@Column(name = "produced_num")
	private Integer producedNum;// 已生产数量

	@Column(name = "remain_quota")
	private Integer remainQuota;// 可发货数量
	
	/**
	 * 下载状态 0-未下载 ,1-已下载
	 */
	@Column(name = "download_stat")
	@Enumerated(EnumType.STRING)
	private DownLoadStat downloadStat;
	
	@Column(name="start_sn")
	private String startSn;// 号段起始值
	
	@Column(name="end_sn")
	private String endSn;// 号段结束值
	
	/**
	 * 已发货部分货款
	 */
	@Column(name = "amt_of_delivery")
	private BigDecimal amtOfDelivery;

	public Long getIordDetailHis() {
		return iordDetailHis;
	}

	public void setIordDetailHis(Long iordDetailHis) {
		this.iordDetailHis = iordDetailHis;
	}

	public Long getIordHis() {
		return iordHis;
	}

	public void setIordHis(Long iordHis) {
		this.iordHis = iordHis;
	}

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

	public Date getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public Long getIcust() {
		return icust;
	}

	public void setIcust(Long icust) {
		this.icust = icust;
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

	public DownLoadStat getDownloadStat() {
		return downloadStat;
	}

	public void setDownloadStat(DownLoadStat downloadStat) {
		this.downloadStat = downloadStat;
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
