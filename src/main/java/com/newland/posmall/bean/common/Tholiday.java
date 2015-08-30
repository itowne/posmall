package com.newland.posmall.bean.common;

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

import org.hibernate.annotations.Type;

import com.newland.posmall.bean.dict.HolidayStatus;
/**
 * 假日表
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_holiday")
public class Tholiday implements Serializable {

	private static final long serialVersionUID = -9187917966003339059L;
	
	@Id
	@TableGenerator(name = "i_holiday", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_holiday")
	@Column(name = "i_holiday")
	private Long iholiday;//主键
    
	@Column(name = "year")
	private Integer year;//年
	
	@Column(name = "month")
	private Integer month;//月
	
	@Column(name = "day")
	private Integer day;//日
	
	@Column(name = "holi_status")
	@Enumerated(EnumType.STRING)
	private HolidayStatus holiStatus;//状态
	
	@Column(name = "memo")
	private String memo;//说明
	
	@Column(name = "gen_time",updatable = false)
	private Date genTime;//生成时间
	
	@Column(name = "upd_time")
	private Date updTime;//更新时间
	
	@Column(name = "del_flg")
	@Type(type = "yes_no")
	private Boolean delFlg;//删除标识
	
	@Version
	private Long version;//版本号
    
	
	public Long getIholiday() {
		return iholiday;
	}

	public void setIholiday(Long iholiday) {
		this.iholiday = iholiday;
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

	public HolidayStatus getHoliStatus() {
		return holiStatus;
	}

	public void setHoliStatus(HolidayStatus holiStatus) {
		this.holiStatus = holiStatus;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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

	public Boolean getDelFlg() {
		return delFlg;
	}

	public void setDelFlg(Boolean delFlg) {
		this.delFlg = delFlg;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
	
	public void setAllDate(String AllDate){
		String[] allDateArray = AllDate.split("-");
		this.year = Integer.parseInt(allDateArray[0]);
		this.month = Integer.parseInt(allDateArray[1]);
		this.day = Integer.parseInt(allDateArray[2]);
	}
	
	public String getAllDate() {
		String holiMonth = "";
		String holiDay = "";
		if(String.valueOf(this.month).length()==1){
			holiMonth = "0"+ this.month;
		}else{
			holiMonth = ""+ this.month;
		}
		if(String.valueOf(this.day).length()==1){
			holiDay = "0"+ this.day;
		}else{
			holiDay = ""+ this.day;
		}
		return this.year+"-"+holiMonth+"-"+holiDay;
	}


}
