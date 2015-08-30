package com.newland.posmall.controller.admin;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.Order;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.TuserSession;
import org.ohuyo.rapid.base.entity.AjaxResult;
import org.ohuyo.rapid.base.servlet.AppSessionFilter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.newland.posmall.bean.basebusi.TlogisticsCom;
import com.newland.posmall.bean.basebusi.condition.LogisticscompanyCondition;
import com.newland.posmall.bean.dict.LogisticsStatus;
import com.newland.posmall.bean.dict.YesNoType;
import com.newland.posmall.biz.admin.LogisticscompanyBiz;
import com.newland.posmall.controller.BaseController;

@Scope("prototype")
@Controller("admin.logistics.logisticsCompany")
public class LogisticsCompanyController extends BaseController{

	@Resource
	private LogisticscompanyBiz logisticscompanyBiz;
	
	private static final String NAV_TAB_ID = "WLGSGL";
	
	@RequestMapping("/admin/logistics/logisticscompany/logisticscompanyList.do")
	public String logisticscompanyList(
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String fullname,
			@RequestParam(required = false) Integer aging,
			@RequestParam(required = false) YesNoType feeFlag,
			@RequestParam(required = false) LogisticsStatus logisticsStatus,
			@RequestParam(required = false) Integer numPerPage,
			@RequestParam(required = false) Integer pageNum, Model model) {
		
		Page page = new Page();
		page.setCount(true);
		page.setCapacity(numPerPage == null ? CAPACITY : numPerPage);
		page.setPageOffset(pageNum == null ? PAGE_OFF_SET : pageNum - 1);

		LogisticscompanyCondition logisticscompanyCondition = new LogisticscompanyCondition();
		logisticscompanyCondition.setName(name);
		logisticscompanyCondition.setFullname(fullname);
		logisticscompanyCondition.setAging(aging);
		logisticscompanyCondition.setFeeFlag(feeFlag);
		logisticscompanyCondition.setLogisticsStatus(logisticsStatus);
			
		logisticscompanyCondition.addOrders(Order.desc("updTime"));
		List<TlogisticsCom> tlogisticsComList = this.logisticscompanyBiz.queryAllLogisticsCom(logisticscompanyCondition, page);
		model.addAttribute(tlogisticsComList);
		model.addAttribute(NUM_PER_PAGE, page.getCapacity());
		model.addAttribute(PAGE_NUM, pageNum);
		model.addAttribute(TOTAL_COUNT, page.getRecAmt());
		model.addAttribute("name", name);
		model.addAttribute("fullname", fullname);
		model.addAttribute("aging", aging);
		model.addAttribute("feeFlag", feeFlag);
		model.addAttribute("logisticsStatus", logisticsStatus);
		
		return "/admin/logistics/logisticscompany/logisticscompanyList";
	}
	
	/**
	 * 去新增页面
	 */
	@RequestMapping("/admin/logistics/logisticscompany/tologisticscompanyAdd.do")
	public String toLogisticscompanyAdd() {
		return "/admin/logistics/logisticscompany/logisticscompanyAdd";
	}
	/**
	 * 新增
	 */
	@RequestMapping(value = "/admin/logistics/logisticscompany/logisticscompanyAdd.do",method = RequestMethod.POST)
	public String logisticscompanyAdd(TlogisticsCom tlogisticsCom, Model model) {
		TuserSession tuserSession = (TuserSession) AppSessionFilter.getAppSession();
		this.logisticscompanyBiz.addTlogisticsCom(tlogisticsCom, tuserSession.getTuser());
		
		ajaxResult.setMessage(AjaxResult.MSG_ADD);
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		model.addAttribute(ajaxResult);
		
		return "/common/ajaxResult";
	}

	/**
	 * 去修改页面
	 */
	@RequestMapping("/admin/logistics/logisticscompany/tologisticscompanyModify.do")
	public String toLogisticsCompanyModify(@RequestParam(required = false) Long ilogisticsCom,
			Model model) {
		TlogisticsCom tlogisticsCom = this.logisticscompanyBiz.queryLogisticsComByIpdt(ilogisticsCom);
		model.addAttribute(tlogisticsCom);
		return "/admin/logistics/logisticscompany/logisticscompanyModify";
	}
	/**
	 * 修改
	 */
	@RequestMapping(value = "/admin/logistics/logisticscompany/logisticscompanyModify.do",method = RequestMethod.POST)
	public String logisticsCompanyModify(TlogisticsCom tlogisticsCom, Model model) {
		TuserSession tuserSession = (TuserSession) AppSessionFilter.getAppSession();
		this.logisticscompanyBiz.modifyTlogisticsComByIlogisticsCom(tlogisticsCom, tuserSession.getTuser());
		
		ajaxResult.setMessage(AjaxResult.MSG_UPDATE);
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}

	/**
	 * 去详细页面
	 */
	@RequestMapping("/admin/logistics/logisticscompany/tologisticscompanyDetail.do")
	public String logisticsCompanyDetail(@RequestParam(required = false) Long ilogisticsCom,
			Model model) {
		TlogisticsCom tlogisticsCom = this.logisticscompanyBiz.queryLogisticsComByIpdt(ilogisticsCom);
		model.addAttribute(tlogisticsCom);
		return "/admin/logistics/logisticscompany/logisticscompanyDetail";
	}
}
