package org.ohuyo.rapid.base.entity.condition;

import org.ohuyo.commons.query.criterion.DetachedCriteriaEx;
import org.ohuyo.commons.query.criterion.annotation.CriteriaClass;
import org.ohuyo.commons.query.criterion.annotation.Expression;
import org.ohuyo.commons.query.criterion.annotation.Operator;

import com.newland.posmall.bean.common.Tholiday;
import com.newland.posmall.bean.dict.HolidayStatus;

@CriteriaClass(Tholiday.class)
public class TholidayCondition extends DetachedCriteriaEx {

	private static final long serialVersionUID = 3338641863923404396L;

	@Expression(operator = Operator.eq, propertyName = "year")
	private Integer year;
	
	@Expression(operator = Operator.eq, propertyName = "month")
	private Integer month;
	
	@Expression(operator = Operator.eq, propertyName = "day")
	private Integer day;
    
	@Expression(operator = Operator.eq, propertyName = "holiStatus")
	private HolidayStatus holiStatus;
	
	@Expression(operator = Operator.eq, propertyName = "delFlg")
	private Boolean delFlg;

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

	public Boolean getDelFlg() {
		return delFlg;
	}

	public void setDelFlg(Boolean delFlg) {
		this.delFlg = delFlg;
	}

	public HolidayStatus getHoliStatus() {
		return holiStatus;
	}

	public void setHoliStatus(HolidayStatus holiStatus) {
		this.holiStatus = holiStatus;
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
