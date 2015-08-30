package com.newland.posmall.biz.sys;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.TsysSession;
import org.ohuyo.rapid.base.servlet.AppSessionFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newland.posmall.base.service.TholidayService;
import com.newland.posmall.base.service.TlogService;
import com.newland.posmall.bean.common.Tholiday;
import com.newland.posmall.bean.common.Tlog;
import com.newland.posmall.bean.dict.LogType;
import com.newland.posmall.bean.dict.OperType;
import com.newland.posmall.biz.common.MapBiz;
@Service
@Transactional
public class HolidayBiz {

	@Resource
	private TholidayService tholidayService;
	
	@Resource
	private TlogService tlogService;
	
	@Resource
	private MapBiz mapBiz;
	/**
	 * 
	* @Title: queryAllTholiday    
	* @Description: 分页查询
	* @param tholidayCondition
	* @param page
	* @return
	* @author chenwenjing    
	* @date 2014-8-25 下午8:53:53
	 */
	@SuppressWarnings("rawtypes")
	public List queryAllTholiday(Page page, String startTime, String endTime, String holiStatus) {
		return this.tholidayService.findListByInfo(page,startTime,endTime,holiStatus);
	}
	
	public String AddHoliday(Tholiday tholiday){
		Tholiday holiday = new Tholiday();
		holiday.setYear(tholiday.getYear());
		holiday.setMonth(tholiday.getMonth());
		holiday.setDay(tholiday.getDay());
		holiday.setDelFlg(Boolean.FALSE);
		Tholiday holiNew = this.tholidayService.findObjectByInfo(holiday);
		if(holiNew!=null){
			return "failure";
		}
		
		this.tholidayService.save(tholiday);
		
		Map<String, String> holiMap = mapBiz.getMapByType("holiday_status");//获取数据字典
		
		TsysSession tsysSession = (TsysSession) AppSessionFilter.getAppSession();
		Tlog tlog = new Tlog();
		tlog.setIfk(tsysSession.getTsys().getIsys());
		tlog.setLogType(LogType.SYS);
		tlog.setOperType(OperType.SYS_MGR);
		tlog.setMemo("新增节假日");
		tlog.setPreData("");
		StringBuffer sbDate = new StringBuffer();
		sbDate.append("新增节假日:日期是'");
		sbDate.append(tholiday.getAllDate()+"'");
		sbDate.append(",状态是'");
		sbDate.append(holiMap.get(String.valueOf(tholiday.getHoliStatus()))+"'");
		sbDate.append(",备注是'");
		sbDate.append(tholiday.getMemo()+"'");
		tlog.setData(sbDate.toString());
		this.tlogService.save(tlog);
		return "success";
	}
	
	public void DelRoleByIholiday(Long id)throws Exception{
		
		Tholiday holiday = QueryRoleByIholiday(id);
		
		this.tholidayService.delete(id);
		
        Map<String, String> holiMap = mapBiz.getMapByType("holiday_status");//获取数据字典
		
		TsysSession tsysSession = (TsysSession) AppSessionFilter.getAppSession();
		Tlog tlog = new Tlog();
		tlog.setIfk(tsysSession.getTsys().getIsys());
		tlog.setLogType(LogType.SYS);
		tlog.setOperType(OperType.SYS_MGR);
		tlog.setMemo("删除节假日");
		StringBuffer sbPreDate = new StringBuffer();
		sbPreDate.append("日期是'");
		sbPreDate.append(holiday.getAllDate()+"'");
		sbPreDate.append(",状态是'");
		sbPreDate.append(holiMap.get(String.valueOf(holiday.getHoliStatus()))+"'");
		sbPreDate.append(",备注是'");
		sbPreDate.append(holiday.getMemo()+"'");
		tlog.setPreData(sbPreDate.toString());
		tlog.setData("");
		this.tlogService.save(tlog);
	}
	
	public Tholiday QueryRoleByIholiday(Long id)throws Exception{
		return this.tholidayService.find(id);
	}
	
	public void ModifyTholiday(Tholiday tholiday)throws Exception{
		Tholiday tholidayNew = tholidayService.find(tholiday.getIholiday());
		
		tholidayNew.setHoliStatus(tholiday.getHoliStatus());
		tholidayNew.setMemo(tholiday.getMemo());
		
		this.tholidayService.update(tholidayNew);
		
		Map<String, String> holiMap = mapBiz.getMapByType("holiday_status");//获取数据字典
		
		TsysSession tsysSession = (TsysSession) AppSessionFilter.getAppSession();
		Tlog tlog = new Tlog();
		tlog.setIfk(tsysSession.getTsys().getIsys());
		tlog.setLogType(LogType.SYS);
		tlog.setOperType(OperType.SYS_MGR);
		tlog.setMemo("修改节假日管理");
		StringBuffer sbPreDate = new StringBuffer();
		sbPreDate.append("日期是'");
		sbPreDate.append(tholidayNew.getAllDate()+"'");
		sbPreDate.append(",状态是'");
		sbPreDate.append(holiMap.get(String.valueOf(tholiday.getHoliStatus()))+"'");
		sbPreDate.append(",备注是'");
		sbPreDate.append(tholiday.getMemo()+"'");
		tlog.setPreData(sbPreDate.toString());
		StringBuffer sbDate = new StringBuffer();
		sbDate.append("日期是'");
		sbDate.append(tholidayNew.getAllDate()+"'");
		sbDate.append(",状态是 '");
		sbDate.append(holiMap.get(String.valueOf(tholidayNew.getHoliStatus()))+"'");
		sbDate.append(",备注是 '");
		sbDate.append(tholidayNew.getMemo()+"'");
		tlog.setData(sbDate.toString());
		this.tlogService.save(tlog);
	}
}
