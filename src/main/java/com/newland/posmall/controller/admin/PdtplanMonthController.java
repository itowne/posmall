package com.newland.posmall.controller.admin;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.criterion.Order;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.entity.AjaxResult;
import org.ohuyo.rapid.base.service.TsysParamService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.newland.posmall.base.exception.BizException;
import com.newland.posmall.bean.basebusi.TpdtPlanDay;
import com.newland.posmall.bean.basebusi.TpdtPlanMonth;
import com.newland.posmall.bean.basebusi.condition.TpdtPlanMonthCondition;
import com.newland.posmall.biz.admin.PdtPlanMonthBiz;
import com.newland.posmall.biz.common.MapBiz;
import com.newland.posmall.controller.BaseController;
import com.newland.posmall.controller.jspmodel.PdtPlanDays;

@Scope("prototype")
@Controller("admin.pdtplanmonth")
public class PdtplanMonthController extends BaseController{
	
	private static String NAV_TAB_ID = "YPCGL";
	
	@Resource
	private PdtPlanMonthBiz pdtPlanMonthBiz;
	
	@Resource
	private MapBiz mapBiz;
	
	@Resource
	private TsysParamService tsysParamService;
	
	@RequestMapping(value="/admin/pdtplanmonth/pdtPlanMonthAdd.do", method = RequestMethod.POST)
	public String pdtPlanMonthAdd(
			@RequestParam(required = false) Integer year,
			@RequestParam(required = false) Integer month,
			@RequestParam(required = false) Long ipdt,
			PdtPlanDays pdtPlanDays,
			Model model){
		TpdtPlanMonth tpdtPlanMonth = new TpdtPlanMonth();
		tpdtPlanMonth.setIpdt(ipdt);
		tpdtPlanMonth.setYear(year);
		tpdtPlanMonth.setMonth(month);
		Integer monthNum = 0;
		List<TpdtPlanDay> tpdtPlanDayList = null;
		if(pdtPlanDays != null && pdtPlanDays.getTpdtPlanDayList() != null){
			tpdtPlanDayList = pdtPlanDays.getTpdtPlanDayList();
			for(TpdtPlanDay temp : tpdtPlanDayList ){
				monthNum += temp.getNum();
			}
		}
		tpdtPlanMonth.setNum(monthNum);
		
		try {
			pdtPlanMonthBiz.addPdtPlanMonth(tpdtPlanMonth, tpdtPlanDayList);
		} catch (BizException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setNavTabId(NAV_TAB_ID);
			ajaxResult.setCallbackType("");
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
			
		}  catch (ParseException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage("请输入年月");
			ajaxResult.setNavTabId(NAV_TAB_ID);
			ajaxResult.setCallbackType("");
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
			
		} 
		
		ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
		ajaxResult.setMessage(AjaxResult.MSG_ADD);
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
		
		
	}
	
	/**
	 * 日排产输入
	 * @param year
	 * @param month
	 * @param ipdt
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("/admin/pdtplanmonth/pdtPlanDayList.do")
	public String genPdtPlanDayList(
		@RequestParam(required = false) Integer year,
		@RequestParam(required = false) Integer month,
		@RequestParam(required = false) Long ipdt,
		 Model model) {
		
		try {
			if(!pdtPlanMonthBiz.valeidateUnqi(year, month, ipdt)){
				model.addAttribute("errorMsg", year+"年"+month+"月 该产品已有月排 产计划不能重复");
				return "/admin/pdtplanmonth/pdtplanday";
			}
		} catch (BizException e) {
			model.addAttribute("errorMsg", e.getMsg());
			return "/admin/pdtplanmonth/pdtplanday";
		}
		
		int dayNumOfMonth = 0;
		Date date;
		try {
			date = new SimpleDateFormat("yyyy-M-d").parse(year+"-"+month+"-1");
		
			Date date2 = DateUtils.addMonths(date, 1);
			
			date = DateUtils.addDays(date2, -1);
			
			dayNumOfMonth = date.getDate();
			
			List<TpdtPlanDay> tpdtPlanDayList = new ArrayList<TpdtPlanDay>();
			TpdtPlanDay tpdtPlanDay = null;
			// 取默认 参数
			int pdtDayNum =  Integer.valueOf(this.tsysParamService.getTsysParam("OTHER_CONF", "MRRCL").getValue());
			for(int i = 0; i < dayNumOfMonth; i++){
				tpdtPlanDay = new TpdtPlanDay();
				tpdtPlanDay.setYear(year);
				tpdtPlanDay.setMonth(month);
				tpdtPlanDay.setDay(i+1);
				tpdtPlanDayList.add(tpdtPlanDay);
				tpdtPlanDay.setNum(pdtPlanMonthBiz.genDayNum(pdtDayNum, year, month, i+1));
			}
			model.addAttribute("ipdt", ipdt);
			model.addAttribute("tpdtPlanDayList",tpdtPlanDayList);
		} catch (ParseException e) {
			model.addAttribute("errorMsg", e.getMessage());
			return "/admin/pdtplanmonth/pdtplanday";
		}
		
		return "/admin/pdtplanmonth/pdtplanday";
	}
	
	
	@RequestMapping("/admin/pdtplanmonth/pdtPlanMonthList.do")
	public String pdtPlanMonthList(
			@RequestParam(required = false) Integer year,
			@RequestParam(required = false) Integer month,
			@RequestParam(required = false) Long ipdt,
			@RequestParam(required = false) Integer numPerPage,
			@RequestParam(required = false) Integer pageNum, Model model) {
		
		Page page = new Page();
		page.setCount(true);
		page.setCapacity(numPerPage == null ? CAPACITY : numPerPage);
		page.setPageOffset(pageNum == null ? PAGE_OFF_SET : pageNum - 1);

		TpdtPlanMonthCondition tpdtPlanMonthCondition = new TpdtPlanMonthCondition();
		tpdtPlanMonthCondition.setYear(year);
		tpdtPlanMonthCondition.setMonth(month);
		tpdtPlanMonthCondition.setIpdt(ipdt);
		tpdtPlanMonthCondition.setDelFlag(Boolean.FALSE);
		List<TpdtPlanMonth> tpdtPlanMonthList = this.pdtPlanMonthBiz.queryAllTpdtPlanMonth(tpdtPlanMonthCondition, page);
		
		model.addAttribute("yearMap",pdtPlanMonthBiz.initSelectYearMap());
		model.addAttribute("tpdtPlanMonthList",tpdtPlanMonthList);
		model.addAttribute("pdtNameMap",mapBiz.getMapByType("pdt_name"));
		model.addAttribute(NUM_PER_PAGE, page.getCapacity());
		model.addAttribute(PAGE_NUM, pageNum);
		model.addAttribute(TOTAL_COUNT, page.getRecAmt());
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("ipdt", ipdt);
		
		return "/admin/pdtplanmonth/pdtplanmonthList";
	}
	
	/**
	 * 查询明细
	 */
	@RequestMapping("/admin/pdtplanmonth/pdtPlanMonthDetail.do")
	public String pdtPlanMonthDetail(@RequestParam(required = false) Long ipdtPlanMonth,
			Model model) {

		TpdtPlanMonth tpdtPlanMonth = this.pdtPlanMonthBiz.queryTpdtPlanMonthByIpdtPlanMonth(ipdtPlanMonth);
		List<TpdtPlanDay> tpdtPlanDayList = this.pdtPlanMonthBiz.queryTpdtPlanDayByIpdtPlanMonth(ipdtPlanMonth);
		model.addAttribute("tpdtPlanMonth",tpdtPlanMonth);
		model.addAttribute("tpdtPlanDayList",tpdtPlanDayList);
		
		return "/admin/pdtplanmonth/pdtplanmonthDetail";
	}

	/**
	 * 去修改页面
	 */
	@RequestMapping("/admin/pdtplanmonth/toPdtPlanMonthModify.do")
	public String toPdtPlanMonthModify(@RequestParam(required = false) Long ipdtPlanMonth,
			Model model) {
		TpdtPlanMonth tpdtPlanMonth = this.pdtPlanMonthBiz.queryTpdtPlanMonthByIpdtPlanMonth(ipdtPlanMonth);
		List<TpdtPlanDay> tpdtPlanDayList = this.pdtPlanMonthBiz.queryTpdtPlanDayAndQuotaByIpdtPlanMonth(ipdtPlanMonth);
		String validateMsg = null;
		
		String dateStr = null;
		if(tpdtPlanMonth.getMonth() > 9){
			dateStr = tpdtPlanMonth.getYear()+"-"+tpdtPlanMonth.getMonth();
		}else{
			dateStr = tpdtPlanMonth.getYear()+"-0"+tpdtPlanMonth.getMonth();
		}
		if(!pdtPlanMonthBiz.validatDate(dateStr, PdtPlanMonthBiz.VALIDATE_MODIFY)){
			validateMsg = "只能修改当前月及之后"+this.tsysParamService.getTsysParam("OTHER_CONF", "XYYXQKD_Y").getValue()+"个月的计划";
		}
		
		Calendar cal = this.pdtPlanMonthBiz.queryReleaseDate(tpdtPlanMonth.getIpdt());
		Date date = cal.getTime();
		SimpleDateFormat sdf = new  SimpleDateFormat("yyyy-MM");
		Calendar minCal = this.pdtPlanMonthBiz.queryReleaseDateForDelete(tpdtPlanMonth.getIpdt());
		Date minDate = minCal.getTime();
		Boolean flag = false;
		int modifyDay = cal.get(Calendar.DAY_OF_MONTH);;
		Boolean minFlag = false;
		int modifyMinday = minCal.get(Calendar.DAY_OF_MONTH);
		if(dateStr.compareTo(sdf.format(date)) == 0){
			//等于间隔点
			flag = true;
		}else if(dateStr.compareTo(sdf.format(date)) < 0){
			//当小于间隔点
			validateMsg = "该月排产已锁定，不能修改";
		}else if(dateStr.compareTo(sdf.format(minDate)) <= 0){
			//大于间隔点  ,小等于点单点
			flag = false;
			if(dateStr.compareTo(sdf.format(minDate)) < 0){
				minFlag = null;
			}else{
				minFlag = true;
			}
		}else if(dateStr.compareTo(sdf.format(minDate)) > 0){
			//大于间隔点
			flag = false;
			minFlag = false;
		}
		if(sdf.format(minDate).compareTo(sdf.format(date)) == 0){
			minFlag = flag;
		}
		
		model.addAttribute("flag",flag);
		model.addAttribute("modifyDay",modifyDay);
		model.addAttribute("minFlag",minFlag);
		model.addAttribute("modifyMinday",modifyMinday);
		model.addAttribute("yearMap",pdtPlanMonthBiz.initSelectYearMap());
		model.addAttribute("year",tpdtPlanMonth.getYear());
		model.addAttribute("month",tpdtPlanMonth.getMonth());
		model.addAttribute("pdtNameMap",mapBiz.getMapByType("pdt_name"));
		model.addAttribute("tpdtPlanMonth",tpdtPlanMonth);
		model.addAttribute("tpdtPlanDayList",tpdtPlanDayList);
		model.addAttribute("validateMsg",validateMsg);
		return "/admin/pdtplanmonth/pdtplanmonthModify";
	}
	
	/**
	 * 去翻新增页面
	 */
	@RequestMapping("/admin/pdtplanmonth/toPdtPlanMonthAdd.do")
	public String toPdtPlanMonthAdd(Model model) {
		
		model.addAttribute("yearMap",pdtPlanMonthBiz.initSelectYearMap());
		model.addAttribute("year", pdtPlanMonthBiz.initYear(null));
		model.addAttribute("month", pdtPlanMonthBiz.initMonth(null));
		model.addAttribute("pdtNameMap",mapBiz.getMapByType("pdt_name"));
		return "/admin/pdtplanmonth/pdtplanmonthAdd";
	}
	

	/**
	 * 修改
	 */
	@RequestMapping(value = "/admin/pdtplanmonth/pdtPlanMonthModify.do",method = RequestMethod.POST)
	public String pdtPlanMonthModify(@RequestParam(required = false) Long ipdtPlanMonth,
			@RequestParam(required = false) Integer year,
			@RequestParam(required = false) Integer month,
			@RequestParam(required = false) Long ipdt,
			PdtPlanDays pdtPlanDays, Model model) {
		
		TpdtPlanMonth tpdtPlanMonth = this.pdtPlanMonthBiz.queryTpdtPlanMonthByIpdtPlanMonth(ipdtPlanMonth);
		if(!((year - tpdtPlanMonth.getYear() == 0) && (month - tpdtPlanMonth.getMonth() == 0) && (ipdt - tpdtPlanMonth.getIpdt() == 0))){
			TpdtPlanMonth tt = new TpdtPlanMonth();
			tt.setIpdt(ipdt);
			tt.setYear(year);
			tt.setMonth(month);
			tt.setDelFlag(Boolean.FALSE);
			tt = pdtPlanMonthBiz.queryTpdtPlanMonth(tt);
			if(tt != null){
				ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
				ajaxResult.setMessage(year+"年"+month+"月 该产品已有月排 产计划不能重复");
				ajaxResult.setNavTabId(NAV_TAB_ID);
				ajaxResult.setCallbackType("");
				model.addAttribute(ajaxResult);
				return "/common/ajaxResult";
			}
		}
		
		TpdtPlanDay tpdtPlanDay = new TpdtPlanDay();
		if((year - tpdtPlanMonth.getYear() == 0) && (month - tpdtPlanMonth.getMonth() == 0) ){
			tpdtPlanDay = null;
		}else{
			tpdtPlanDay.setYear(tpdtPlanMonth.getYear());
			tpdtPlanDay.setMonth(tpdtPlanMonth.getMonth());
			tpdtPlanDay.setIpdtPlanMonth(tpdtPlanMonth.getIpdtPlanMonth());
			tpdtPlanDay.setIpdt(tpdtPlanMonth.getIpdt());
		}

		Calendar cal = this.pdtPlanMonthBiz.queryReleaseDate(tpdtPlanMonth.getIpdt());
		Date date = cal.getTime();
		SimpleDateFormat sdf = new  SimpleDateFormat("yyyy-MM");
		Boolean flag = false;
		int modifyDay = 0;
		String dateStr = null;
		if(tpdtPlanMonth.getMonth() > 9){
			dateStr = tpdtPlanMonth.getYear()+"-"+tpdtPlanMonth.getMonth();
		}else{
			dateStr = tpdtPlanMonth.getYear()+"-0"+tpdtPlanMonth.getMonth();
		}
		if(dateStr.compareTo(sdf.format(date)) == 0){
			flag = true;
			modifyDay = cal.get(Calendar.DAY_OF_MONTH);
		}
		if(dateStr.compareTo(sdf.format(date)) < 0){
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage("只能修改当前月之后"+this.tsysParamService.getTsysParam("OTHER_CONF", "XYYXQKD_Y").getValue()+"个月的计划");
			ajaxResult.setNavTabId(NAV_TAB_ID);
			ajaxResult.setCallbackType("");
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
		
		tpdtPlanMonth.setIpdt(ipdt);
		tpdtPlanMonth.setYear(year);
		tpdtPlanMonth.setMonth(month);
		Integer monthNum = 0;
		
		
		if(pdtPlanDays != null && pdtPlanDays.getTpdtPlanDayList() != null){
			for(TpdtPlanDay temp : pdtPlanDays.getTpdtPlanDayList() ){
				monthNum += temp.getNum();
			}
		}else{
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage("缺少日排产计划");
			ajaxResult.setNavTabId(NAV_TAB_ID);
			ajaxResult.setCallbackType("");
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
		tpdtPlanMonth.setNum(monthNum);
		try {
				this.pdtPlanMonthBiz.ModifyPdtPlanMonth(tpdtPlanMonth, pdtPlanDays.getTpdtPlanDayList(),tpdtPlanDay ,flag, modifyDay);
		} catch (BizException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setNavTabId(NAV_TAB_ID);
			ajaxResult.setCallbackType("");
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
			
		}  catch (ParseException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage("请输入年月");
			ajaxResult.setNavTabId(NAV_TAB_ID);
			ajaxResult.setCallbackType("");
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
			
		} 
		
		ajaxResult.setMessage(AjaxResult.MSG_UPDATE);
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
	
	
	@RequestMapping(value = "/admin/pdtplanmonth/pdtPlanMonthRemove.do",method = RequestMethod.POST)
	public String pdtPlanMonthRemove(Model model, @RequestParam(required = false) Long ipdtPlanMonth){
		
		this.pdtPlanMonthBiz.removePdtPlanMonth(ipdtPlanMonth);
	
		ajaxResult.setMessage(AjaxResult.MSG_DELETE);
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType("");
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
		
	


}
