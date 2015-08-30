package com.newland.posmall.bean.basebusi;

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
import javax.persistence.Transient;
import javax.persistence.Version;

import com.newland.posmall.bean.dict.DownLoadStat;
import com.newland.posmall.bean.dict.OrdDetailPdtType;
import com.newland.posmall.bean.dict.ValidStatus;
import com.newland.posmall.bean.dict.YesNoType;

/**
 * 订单产品明细表2
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_ord_detail_pdt")
public class TordDetailPdt implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "i_ord_detail_pdt", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_ord_detail_pdt")
	@Column(name = "i_ord_detail_pdt")
	private Long iordDetailPdt;// 内部编号

	@Column(name = "year")
	private Integer year;// 年

	@Column(name = "month")
	private Integer month;// 月

	@Column(name = "day")
	private Integer day;// 日

	@Column(name = "ord_detail_pdt_type")
	@Enumerated(EnumType.STRING)
	private OrdDetailPdtType ordDetailPdtType;// 存量类型

	@Column(name = "num")
	private Integer num;// 数量

	@Column(name = "gen_time", updatable = false)
	private Date genTime;// 生成时间

	@Column(name = "upd_time")
	private Date updTime;// 更新时间

	@Version
	private Long version;// 版本号

	@Column(name = "lock_type")
	@Enumerated(EnumType.STRING)
	private YesNoType lockType;// 锁定类型

	@Column(name = "ord_detail_pdt_status")
	@Enumerated(EnumType.STRING)
	private ValidStatus ordDetailPdtStatus;// 明细状态

	@Column(name = "i_pdt")
	private Long ipdt;// 产品id

	@Column(name = "i_ord")
	private Long iord;// 订单id
	
	@Column(name = "produce_status")
	@Enumerated(EnumType.STRING)
	private YesNoType produceStatus;// 生产状态   YES表示已生产 NO表示未生产 
	
	@Column(name = "deliveryed")
	private Integer deliveryed;// 已发货数量

	@Column(name = "pending_num")
	private Integer pendingNum;// 待发货数量

	@Column(name = "produced_num")
	private Integer producedNum;// 已生产数量

	@Column(name = "remain_quota")
	private Integer remainQuota;// 可发货数量

	@Column(name = "used_quota")
	private Integer usedQuota;// 已用额度
	
	/**
	 * 下载状态 0-未下载 ,1-已下载
	 */
	@Column(name = "download_stat")
	@Enumerated(EnumType.STRING)
	private DownLoadStat downloadStat;//
	
	@Column(name="start_sn")
	private String startSn;// 号段起始值
	
	@Column(name="end_sn")
	private String endSn;// 号段结束值
	
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

	public Long getIordDetailPdt() {
		return iordDetailPdt;
	}

	public void setIordDetailPdt(Long iordDetailPdt) {
		this.iordDetailPdt = iordDetailPdt;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
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

	public YesNoType getLockType() {
		return lockType;
	}

	public void setLockType(YesNoType lockType) {
		this.lockType = lockType;
	}

	public OrdDetailPdtType getOrdDetailPdtType() {
		return ordDetailPdtType;
	}

	public void setOrdDetailPdtType(OrdDetailPdtType ordDetailPdtType) {
		this.ordDetailPdtType = ordDetailPdtType;
	}

	public ValidStatus getOrdDetailPdtStatus() {
		return ordDetailPdtStatus;
	}

	public void setOrdDetailPdtStatus(ValidStatus ordDetailPdtStatus) {
		this.ordDetailPdtStatus = ordDetailPdtStatus;
	}

	public YesNoType getProduceStatus() {
		return produceStatus;
	}

	public void setProduceStatus(YesNoType produceStatus) {
		this.produceStatus = produceStatus;
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

	public Integer getUsedQuota() {
		return usedQuota;
	}

	public void setUsedQuota(Integer usedQuota) {
		this.usedQuota = usedQuota;
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

	public DownLoadStat getDownloadStat() {
		return downloadStat;
	}

	public void setDownloadStat(DownLoadStat downloadStat) {
		this.downloadStat = downloadStat;
	}

}
