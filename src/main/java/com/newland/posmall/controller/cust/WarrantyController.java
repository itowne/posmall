package com.newland.posmall.controller.cust;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateUtils;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.TcustSession;
import org.ohuyo.rapid.base.entity.AjaxResult;
import org.ohuyo.rapid.base.entity.ERPMaintenance;
import org.ohuyo.rapid.base.servlet.AppSessionFilter;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.newland.posmall.base.exception.BizException;
import com.newland.posmall.bean.customer.Twarranty;
import com.newland.posmall.bean.customer.condition.TwarrantyCondition;
import com.newland.posmall.biz.cust.CustWarrantyBiz;
import com.newland.posmall.controller.BaseController;
import com.newland.posmall.controller.cust.model.Warranty4Page;

@Scope("prototype")
@Controller("cust.warranty")
public class WarrantyController extends BaseController{
	
	@Resource
	private CustWarrantyBiz custWarrantyBiz;
	
	private static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 产品报修信息列表
	 * @param twarranty
	 * @param numPerPage
	 * @param pageNum
	 * @param model
	 * @return
	 */
	@RequestMapping("/cust/warranty/warrantyList.do")
	public String warrantyList(TwarrantyCondition condition,
			@DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime, 
			@DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
			@RequestParam(required = false) Integer numPerPage,
			@RequestParam(required = false) Integer pageNum, Model model) {
		
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
		TcustSession session = (TcustSession) AppSessionFilter.getAppSession();
		List<Twarranty> list = this.custWarrantyBiz.queryPageList(condition, session.getTcust(), page);
		
		model.addAttribute("condition", condition);
		model.addAttribute("list",list);
		model.addAttribute("startTime", startTime);
		model.addAttribute("endTime", endTime);
		
		model.addAttribute(NUM_PER_PAGE, page.getCapacity());
		model.addAttribute(PAGE_NUM, pageNum);
		model.addAttribute(TOTAL_COUNT, page.getRecAmt());
		return "/cust/warranty/warrantyList";
	}

	@RequestMapping("/cust/warranty/toAdd")
	public String toAdd(Model model) {
		return "/cust/warranty/warrantyAdd";
	}
	
	/**
	 * 报修页面输入序列号进行查询
	 * @param seqNo
	 * @return
	 */
	@RequestMapping(value = "/cust/warranty/searchBySeqno.do", method = RequestMethod.POST)
	public @ResponseBody String searchBySeqno(@RequestParam String seqNo) {
		
		ERPMaintenance maintenance = this.custWarrantyBiz.queryBySeqno(seqNo);
		if(maintenance == null) return null;
		StringBuffer rst = new StringBuffer();
		rst.append("{");
		rst.append("\"ierpMaintenance\":");
		rst.append("\"" + maintenance.getIerpMaintenance() + "\",");
		rst.append("\"pdtNo\":");
		rst.append("\"" + maintenance.getPh() + "\",");
		rst.append("\"pm\":");
		rst.append("\"" + maintenance.getPm() + "\",");
		rst.append("\"purchaseDate\":");
		rst.append("\"" + maintenance.getFhDate() + "\",");
		rst.append("\"warrantyPeriod\":");
		rst.append("\"" + ( maintenance.getWarrantyPeriod() == null ? "" : SDF.format(maintenance.getWarrantyPeriod()) ) + "\",");
		rst.append("\"repairNum\":");
		rst.append("\"" + maintenance.getRepairNum() + "\",");
		rst.append("\"lastRepairedDate\":");
		rst.append("\"" + ( maintenance.getLastRepairedDate() == null ? "" : SDF.format(maintenance.getLastRepairedDate()) ) + "\"");
		rst.append("}");
		return rst.toString();
	}
	
	/**
	 * 产品报修
	 * @param warranty4Page
	 * @param model
	 * @return
	 */
	@RequestMapping("/cust/warranty/warrantyAdd.do")
	public String warrantyAdd(Warranty4Page warranty4Page, Model model) {
		
		TcustSession session = (TcustSession) AppSessionFilter.getAppSession();
		try {
			this.custWarrantyBiz.warrantyAdd(warranty4Page, session.getTcust(), session.getTcustReg());
		} catch (BizException e) {
			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
		
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		ajaxResult.setMessage("您的报修申请已提交，我们将尽快为您处理");
		ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
		ajaxResult.setNavTabId("CPBX");
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
	
	/**
	 * 报修申请撤销
	 * @param iwarranty
	 * @param model
	 * @return
	 */
	@RequestMapping("/cust/warranty/warrantyCacel.do")
	public String warrantyCacel(@RequestParam Long iwarranty, Model model) {
		
		TcustSession session = (TcustSession) AppSessionFilter.getAppSession();
		try {
			this.custWarrantyBiz.warrantyCacel(iwarranty, session.getTcust(), session.getTcustReg());
		} catch (BizException e) {
			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			model.addAttribute(ajaxResult);
		}
		
		ajaxResult.setMessage("您的报修申请已撤销");
		ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
		ajaxResult.setNavTabId("CPBX");
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
	
	/**
	 * 报修明细
	 * @param iwarranty
	 * @param model
	 * @return
	 */
	@RequestMapping("/cust/warranty/warrantyDetail.do")
	public String warrantyDetail(@RequestParam Long iwarranty, Model model) {
		
		Twarranty twarranty = this.custWarrantyBiz.queryById(iwarranty);
		model.addAttribute("twarranty", twarranty);
		
		return "/cust/warranty/warrantyDetail";
	}
	
	/**
	 * 保修查询
	 * @param sn
	 * @param numPerPage
	 * @param pageNum
	 * @param model
	 * @return
	 */
	@RequestMapping("/cust/warranty/maintenancemanageList.do")
	public String maintenancemanageList(@RequestParam(required = false) String seqNo,
			@RequestParam(required = false) Integer numPerPage,
			@RequestParam(required = false) Integer pageNum, Model model) {
		
		Page page = new Page();
		page.setCount(true);
		page.setCapacity(numPerPage == null ? CAPACITY : numPerPage);
		page.setPageOffset(pageNum == null ? PAGE_OFF_SET : pageNum - 1);
		
		TcustSession session = (TcustSession) AppSessionFilter.getAppSession();
		List<ERPMaintenance> list = this.custWarrantyBiz.queryERPMaintenancePageList(seqNo, session.getTcust(), page);
		
		model.addAttribute("list",list);
		model.addAttribute("seqNo",seqNo);
		
		model.addAttribute(NUM_PER_PAGE, page.getCapacity());
		model.addAttribute(PAGE_NUM, pageNum);
		model.addAttribute(TOTAL_COUNT, page.getRecAmt());
		
		return "/cust/warranty/maintenancemanageList";
	}
	
	/**
	 * 保修明细
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/cust/warranty/detail.do")
	public String detail(@RequestParam Long id, Model model) {
		
		ERPMaintenance maintenance = this.custWarrantyBiz.queryErpMaintenanceById(id);
		// 查询更换设备前序列号
		if (maintenance != null && maintenance.getLastMaintenanceId() != null) {
			ERPMaintenance last = this.custWarrantyBiz.queryErpMaintenanceById(maintenance.getLastMaintenanceId());
			model.addAttribute("last", last);
		}
		// 查询更换设备后新的序列号
		ERPMaintenance newMaintenance = this.custWarrantyBiz.queryByLastMaintenanceId(id);
		if (newMaintenance != null) {
			model.addAttribute("newMaintenance", newMaintenance);
		}
		model.addAttribute("maintenance", maintenance);
		
		return "/cust/warranty/maintenanceDetail";
	}
}
