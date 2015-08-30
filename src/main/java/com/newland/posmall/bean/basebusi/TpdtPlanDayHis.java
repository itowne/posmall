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
/**
 * 日排产计划历史表
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_pdt_plan_day_his")
public class TpdtPlanDayHis implements Serializable {

	private static final long serialVersionUID = 2801092965977700292L;

	@Id
	@TableGenerator(name = "i_pdt_plan_day_his", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_pdt_plan_day_his")
	@Column(name = "i_pdt_plan_day_his")
	private Long ipdtPlanDayHis;//内部编号
	
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

	@Column(name = "ver")
	private Long ver;//版本号

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
	
	public Long getIpdt() {
		return ipdt;
	}

	public void setIpdt(Long ipdt) {
		this.ipdt = ipdt;
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

	public Long getIpdtPlanDayHis() {
		return ipdtPlanDayHis;
	}

	public void setIpdtPlanDayHis(Long ipdtPlanDayHis) {
		this.ipdtPlanDayHis = ipdtPlanDayHis;
	}

	public Long getVer() {
		return ver;
	}

	public void setVer(Long ver) {
		this.ver = ver;
	}

	
}
