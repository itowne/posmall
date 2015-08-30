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
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Type;
/**
 * 日排产计划
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_pdt_plan_day")
public class TpdtPlanDay implements Serializable {

	private static final long serialVersionUID = 2801092965977700292L;

	@Id
	@TableGenerator(name = "i_pdt_plan_day", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_pdt_plan_day")
	@Column(name = "i_pdt_plan_day")
	private Long ipdtPlanDay;//内部编号
	/**
	 * 月排产外键
	 */
	@Column(name = "i_pdt_plan_month")
	private Long ipdtPlanMonth;
	/**
	 * 产品外键
	 */
	@Column(name = "i_pdt")
	private Long ipdt;

	@Column(name = "year")
	private Integer year;//年

	@Column(name = "month")
	private Integer month;//月

	@Column(name = "week")
	private Integer week;//周

	@Column(name = "day")
	private Integer day;//天

	@Column(name = "weekday")
	private Integer weekday;//星期

	@Column(name = "num")
	private Integer num;//数量

	@Column(name = "gen_time",updatable = false)
	private Date genTime;//生成时间

	@Column(name = "upd_time")
	private Date updTime;//更新时间

	@Version
	private Long version;//版本号
	
	@Column(name ="del_flag")
	@Type(type="yes_no")
	private Boolean delFlag;
	
	@Transient
	private TpdtPlanDayQuota tpdtPlanDayQuota;

	public Long getIpdtPlanDay() {
		return ipdtPlanDay;
	}

	public void setIpdtPlanDay(Long ipdtPlanDay) {
		this.ipdtPlanDay = ipdtPlanDay;
	}

	public Long getIpdtPlanMonth() {
		return ipdtPlanMonth;
	}

	public void setIpdtPlanMonth(Long ipdtPlanMonth) {
		this.ipdtPlanMonth = ipdtPlanMonth;
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

	public Boolean getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Boolean delFlag) {
		this.delFlag = delFlag;
	}

	public Long getIpdt() {
		return ipdt;
	}

	public void setIpdt(Long ipdt) {
		this.ipdt = ipdt;
	}

	public TpdtPlanDayQuota getTpdtPlanDayQuota() {
		return tpdtPlanDayQuota;
	}

	public void setTpdtPlanDayQuota(TpdtPlanDayQuota tpdtPlanDayQuota) {
		this.tpdtPlanDayQuota = tpdtPlanDayQuota;
	}
	
	
	
}
