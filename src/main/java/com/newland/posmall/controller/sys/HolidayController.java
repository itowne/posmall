package com.newland.posmall.controller.sys;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.entity.AjaxResult;
import org.ohuyo.rapid.base.service.TdictService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.newland.posmall.bean.common.Tholiday;
import com.newland.posmall.bean.dict.HolidayStatus;
import com.newland.posmall.biz.sys.HolidayBiz;
import com.newland.posmall.controller.BaseController;

@Scope("prototype")
@Controller("sys.holiday")
public class HolidayController extends BaseController {
	
	@Resource
	private HolidayBiz holidayBiz;
	
	@Resource(name = "posmall.tdictService")
	private TdictService tdictService;
	
	private static final String NAV_TAB_ID = "JJRGL";
	
	/**
	 * 
	* @Description: 分页查询
	* @author chenwenjing    
	* @date 2014-8-25 下午8:59:52
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/sys/holiday/holidayList.do")
	public String holidayList(@RequestParam(required = false) String beginHoliDate,
			@RequestParam(required = false) String endHoliDate,
			@RequestParam(required = false) String holiStatus,
			@RequestParam(required = false) Integer numPerPage,
			@RequestParam(required = false) Integer pageNum,Model model) {
		
		Page page = new Page();
		page.setCount(true);
		page.setCapacity(numPerPage == null ? CAPACITY : numPerPage);
		page.setPageOffset(pageNum == null ? PAGE_OFF_SET : pageNum - 1);
		
		if (StringUtils.isBlank(beginHoliDate)) {
			Date beginTime = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(beginTime);
			cal.set(Calendar.HOUR_OF_DAY, 0);       
			cal.set(Calendar.SECOND, 0);       
			cal.set(Calendar.MINUTE, 0);
			beginHoliDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		}
		
		if (StringUtils.isBlank(endHoliDate)) {
			Calendar cal = Calendar.getInstance(); 
			int year = cal.get(Calendar.YEAR); 
			cal.clear();
			cal.set(Calendar.YEAR, year);
	        cal.roll(Calendar.DAY_OF_YEAR, -1);   
	        endHoliDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		}
		
		List list = this.holidayBiz.queryAllTholiday(page, beginHoliDate, endHoliDate, holiStatus);
		
		List<Tholiday> tholidayList = new ArrayList<Tholiday>();
		for(int i=0;i<list.size();i++){
			Object[] ob = (Object[]) list.get(i);
			Tholiday holi = new Tholiday();
			holi.setIholiday(Long.parseLong(String.valueOf(ob[0])));
			holi.setYear(Integer.parseInt(String.valueOf(ob[1])));
			holi.setMonth(Integer.parseInt(String.valueOf(ob[2])));
			holi.setDay(Integer.parseInt(String.valueOf(ob[3])));
			holi.setHoliStatus(HolidayStatus.valueOf(String.valueOf(ob[4])));
			holi.setMemo(String.valueOf(ob[5]));
			tholidayList.add(holi);
		}
		
		model.addAttribute("holidayList",tholidayList);
		model.addAttribute(NUM_PER_PAGE, page.getCapacity());
		model.addAttribute(PAGE_NUM, pageNum);
		model.addAttribute(TOTAL_COUNT, page.getRecAmt());
		model.addAttribute("beginHoliDate",beginHoliDate);
		model.addAttribute("endHoliDate",endHoliDate);
		model.addAttribute("holiStatus",holiStatus);
		return "/sys/holiday/holidayList";
	}
	
	/**
	 * 
	* @Title: holidayAdd    
	* @Description: 去假日添加页面
	* @param model
	* @return
	* @author chenwenjing    
	* @date 2014-8-25 下午9:00:16
	 */
	@RequestMapping("/sys/holiday/holidayAdd.do")
	public String holidayAdd(Model model) {
		return "/sys/holiday/holidayAdd";
	}
	
	/**
	 * 
	* @Title: roleToAdd    
	* @Description: 添加假日
	* @param model
	* @param trole
	* @return
	* @author chenwenjing    
	* @date 2014-8-25 下午9:00:38
	 */
	@RequestMapping("/sys/holiday/holidayToAdd.do")
	public String holidayToAdd(Model model,Tholiday tholiday,@RequestParam(required = false) String holiDate) {
		if (StringUtils.isNotBlank(holiDate)) {
			tholiday.setAllDate(holiDate);
		}
		String result = this.holidayBiz.AddHoliday(tholiday);
		if("failure".equals(result)){
			ajaxResult.setMessage("添加失败，该日期已设置，请重新选择日期！");
			ajaxResult.setNavTabId(NAV_TAB_ID);
			ajaxResult.setCallbackType("");
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			model.addAttribute(ajaxResult);
		}else{
			ajaxResult.setMessage(AjaxResult.MSG_ADD);
			ajaxResult.setNavTabId(NAV_TAB_ID);
			ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
			model.addAttribute(ajaxResult);
		}
		
		return "/common/ajaxResult";
	}
	
	/**
	 * 
	* @Title: holidayDel    
	* @Description: 删除假日
	* @param iholiday
	* @param model
	* @return
	* @author chenwenjing    
	* @date 2014-8-24 下午9:22:54
	 */
	@RequestMapping("/sys/holiday/holidayDel.do")
	public String holidayDel(@RequestParam(required = false) Long iholiday,Model model) throws Exception{
		
		this.holidayBiz.DelRoleByIholiday(iholiday);
		
		ajaxResult.setMessage(AjaxResult.MSG_DELETE);
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType("");
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
	
	/**
	 * 
	* @Title: holidayModify    
	* @Description: 去修改页面
	* @param iholiday
	* @param model
	* @return
	* @author chenwenjing    
	* @date 2014-8-24 下午9:43:16
	 */
	@RequestMapping("/sys/holiday/holidayMod.do")
	public String holidayModify(@RequestParam(required = false) Long iholiday,Model model) throws Exception{
		Tholiday tholiday= this.holidayBiz.QueryRoleByIholiday(iholiday);
		model.addAttribute(tholiday);
		return "/sys/holiday/holidayMod";
	}
	
	/**
	 * 
	* @Title: holidayToModify    
	* @Description: 修改假日
	* @param tholiday
	* @param model
	* @return
	* @throws Exception
	* @author chenwenjing    
	* @date 2014-8-24 下午10:28:57
	 */
	@RequestMapping("/sys/holiday/holidayToMod.do")
	public String holidayToModify(Tholiday tholiday,Model model,@RequestParam(required = false) String holiDate) throws Exception{
		if (StringUtils.isNotBlank(holiDate)) {
			tholiday.setAllDate(holiDate);
		}
		
		this.holidayBiz.ModifyTholiday(tholiday);
		
		ajaxResult.setMessage(AjaxResult.MSG_UPDATE);
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
	
	/**
	 * 
	* @Title: holidayDetail    
	* @Description: 假日详情
	* @param iholiday
	* @param model
	* @return
	* @author chenwenjing    
	* @date 2014-8-24 下午10:52:21
	 */
	@RequestMapping("/sys/holiday/holidayDetail.do")
	public String holidayDetail(@RequestParam(required = false) Long iholiday,Model model) throws Exception{
		Tholiday tholiday= this.holidayBiz.QueryRoleByIholiday(iholiday);
		model.addAttribute(tholiday);
		return "/sys/holiday/holidayDetail";
	}
	

}
