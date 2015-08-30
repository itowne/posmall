package com.newland.posmall.controller.admin;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateUtils;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.TuserSession;
import org.ohuyo.rapid.base.entity.AjaxResult;
import org.ohuyo.rapid.base.servlet.AppSessionFilter;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.newland.posmall.base.exception.BizException;
import com.newland.posmall.bean.customer.Trenew;
import com.newland.posmall.bean.customer.TrenewDetail;
import com.newland.posmall.bean.customer.condition.TrenewCondition;
import com.newland.posmall.biz.admin.RenewBiz;
import com.newland.posmall.controller.BaseController;

@Scope("prototype")
@Controller("admin.renew")
public class RenewController extends BaseController{
	
	@Resource
	private RenewBiz renewBiz;
	
	private static final String NAV_TAB_ID = "XBGL";
	
	@RequestMapping("/admin/renew/renewList.do")
	public String renewList(TrenewCondition condition,
			@DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime, 
			@DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
			@RequestParam(required = false) Integer numPerPage,
			@RequestParam(required = false) Integer pageNum,Model model) {
		
		Page page = new Page();
		page.setCount(true);
		page.setCapacity(numPerPage == null ? CAPACITY : numPerPage);
		page.setPageOffset(pageNum == null ? PAGE_OFF_SET : pageNum - 1);
		
		if (startTime != null) {
			condition.setStartDate(startTime);
		}
		if (endTime != null) {
			Calendar endCalendar = Calendar.getInstance();
			endCalendar.setTime(endTime);
			endCalendar.add(Calendar.DATE, 1);
			condition.setEndDate(DateUtils.truncate(endCalendar.getTime(), Calendar.DATE));
		}
		List<Trenew> list = this.renewBiz.find(condition, page);
		
		model.addAttribute("list",list);
		model.addAttribute("condition", condition);
		model.addAttribute("startTime", startTime);
		model.addAttribute("endTime", endTime);
		
		model.addAttribute(NUM_PER_PAGE, page.getCapacity());
		model.addAttribute(PAGE_NUM, pageNum);
		model.addAttribute(TOTAL_COUNT, page.getRecAmt());
		return "/admin/maintenancemanage/renewList";
	}
	
	@RequestMapping("/admin/renew/renewCancel.do")
	public String renewCancel(@RequestParam(required = false) Long irenew, Model model) {
		TuserSession tuserSession = (TuserSession) AppSessionFilter.getAppSession();
        
		try {
			this.renewBiz.renewCancel(tuserSession.getTuser(), irenew);
		} catch (BizException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setCallbackType("");
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
		
		ajaxResult.setMessage("撤销成功");
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType("");
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
	@RequestMapping("/admin/renew/toRenewAudit.do")
	public String toRenewAudit(Model model,
			@RequestParam(required = false) Long irenew) {

		Trenew trenew = this.renewBiz.findById(irenew);
		
		List<TrenewDetail> detailList = this.renewBiz.queryTrenewDetailList(irenew);

		model.addAttribute("trenew", trenew);
		
		model.addAttribute("detailList", detailList);

		return "/admin/maintenancemanage/renewAudit";
	}
	@RequestMapping("/admin/renew/renewAudit.do")
	public String renewAudit(Model model,
			@RequestParam(required = false) Long irenew) {

		Trenew trenew = this.renewBiz.findById(irenew);

		try {
			TuserSession tuserSession = (TuserSession) AppSessionFilter
					.getAppSession();
			renewBiz.auditRenew(trenew, tuserSession.getTuser());
		} catch (BizException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setNavTabId(NAV_TAB_ID);
			ajaxResult.setCallbackType("");
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}

		ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
		ajaxResult.setMessage(AjaxResult.MSG_UPDATE);
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
	@RequestMapping("/admin/renew/renewDetail.do")
	public String renewDetail(Model model,
			@RequestParam(required = false) Long irenew) {
		Trenew trenew = this.renewBiz.findById(irenew);
		
		List<TrenewDetail> detailList = this.renewBiz.queryTrenewDetailList(irenew);

		model.addAttribute("trenew", trenew);
		
		model.addAttribute("detailList", detailList);

		return "/admin/maintenancemanage/renewDetail";
	}

}
