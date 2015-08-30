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
import javax.persistence.Version;

import com.newland.posmall.bean.dict.OrdDetailPdtType;
import com.newland.posmall.bean.dict.YesNoType;

@Entity
@Table(name = "t_pdt_plan_day_quota")
public class TpdtPlanDayQuota implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "i_pdt_plan_day_quota", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_pdt_plan_day_quota")
	@Column(name = "i_pdt_plan_day_quota")
	private Long ipdtPlanDayQuota;// 内部编号

	@Column(name = "i_pdt")
	private Long ipdt;// 产品外键

	@Column(name = "year")
	private Integer year;// 年

	@Column(name = "month")
	private Integer month;// 月

	@Column(name = "week")
	private Integer week;// 周

	@Column(name = "day")
	private Integer day;// 天

	@Column(name = "weekday")
	private Integer weekday;// 星期

	@Column(name = "num")
	private Integer num;// 数量

	@Column(name = "gen_time")
	private Date genTime;// 生成时间

	@Column(name = "upd_time")
	private Date updTime;// 修改时间

	@Column(name = "ord_detail_pdt_type")
	@Enumerated(EnumType.STRING)
	private OrdDetailPdtType ordDetailPdtType;// 存量类型
	
	@Column(name = "collection_flag")
	@Enumerated(EnumType.STRING)
	private YesNoType collectionFlag;

	@Version
	private Long version;// 版本

	/**
	 * 已用额度
	 */
	@Column(name = "used_quota")
	private Integer usedQuota;
	/**
	 * 剩余额度
	 */
	@Column(name = "remain_quota")
	private Integer remainQuota;

	public Long getIpdtPlanDayQuota() {
		return ipdtPlanDayQuota;
	}

	public void setIpdtPlanDayQuota(Long ipdtPlanDayQuota) {
		this.ipdtPlanDayQuota = ipdtPlanDayQuota;
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

	public Integer getWeek() {
		return week;
	}

	public void setWeek(Integer week) {
		this.week = week;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Integer getWeekday() {
		return weekday;
	}

	public void setWeekday(Integer weekday) {
		this.weekday = weekday;
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

	public Long getIpdt() {
		return ipdt;
	}

	public void setIpdt(Long ipdt) {
		this.ipdt = ipdt;
	}

	public OrdDetailPdtType getOrdDetailPdtType() {
		return ordDetailPdtType;
	}

	public void setOrdDetailPdtType(OrdDetailPdtType ordDetailPdtType) {
		this.ordDetailPdtType = ordDetailPdtType;
	}

	public YesNoType getCollectionFlag() {
		return collectionFlag;
	}

	public void setCollectionFlag(YesNoType collectionFlag) {
		this.collectionFlag = collectionFlag;
	}

}
