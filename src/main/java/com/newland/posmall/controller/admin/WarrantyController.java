package com.newland.posmall.controller.admin;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.criterion.Order;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.TuserSession;
import org.ohuyo.rapid.base.entity.AjaxResult;
import org.ohuyo.rapid.base.entity.ERPMaintenance;
import org.ohuyo.rapid.base.entity.Tfile;
import org.ohuyo.rapid.base.service.FileUpDownService;
import org.ohuyo.rapid.base.servlet.AppSessionFilter;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.newland.posmall.base.exception.BizException;
import com.newland.posmall.bean.basebusi.WarrantyDownLoad;
import com.newland.posmall.bean.basebusi.condition.WarrantyDownLoadCondition;
import com.newland.posmall.bean.customer.Twarranty;
import com.newland.posmall.bean.customer.condition.TwarrantyCondition;
import com.newland.posmall.bean.dict.WarrantyStatus;
import com.newland.posmall.biz.admin.WarrantyBiz;
import com.newland.posmall.biz.common.TfileBiz;
import com.newland.posmall.biz.file.FileDownload;
import com.newland.posmall.controller.BaseController;
import com.newland.posmall.identifier.IdentifierService;

@Scope("prototype")
@Controller("admin.warranty")
public class WarrantyController extends BaseController{
	
	private static final String NAV_TAB_ID = "BXSL";
	
	@Resource
	private WarrantyBiz warrantyBiz;
	
	@Resource(name = "identifierService")
	private IdentifierService identifierService;
	
	@Resource
	private FileDownload fileDownload;
	
	@Resource
	private TfileBiz tfileBiz;
	
	@Resource
	private FileUpDownService fileUpDownService;
	
	@RequestMapping("/admin/warranty/warrantyList.do")
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
		List<Twarranty> list = this.warrantyBiz.find(condition, page);
		
		model.addAttribute("list",list);
		model.addAttribute("startTime", startTime);
		model.addAttribute("endTime", endTime);
		model.addAttribute("condition", condition);
		
		model.addAttribute(NUM_PER_PAGE, page.getCapacity());
		model.addAttribute(PAGE_NUM, pageNum);
		model.addAttribute(TOTAL_COUNT, page.getRecAmt());
		return "/admin/maintenancemanage/warrantyList";
	}

	@RequestMapping("/admin/warranty/warrantyCancel.do")
	public String warrantyCancel(@RequestParam(required = false) Long iwarranty, Model model) {
		TuserSession tuserSession = (TuserSession) AppSessionFilter.getAppSession();
        
		try {
			this.warrantyBiz.warrantyCancel(tuserSession.getTuser(), iwarranty);
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
	@RequestMapping("/admin/warranty/checkDownLoad.do")
	public String checkDownLoad(Model model){
		TwarrantyCondition condition = new TwarrantyCondition();
		condition.setWarrantyStatus(WarrantyStatus.HAVE_SUBMIT);
		List<Twarranty> list = warrantyBiz.find(condition);
		if(CollectionUtils.isEmpty(list)){
			throw new RuntimeException("没有需要受理的报修申请单");
		}
		return "redirect:/admin/warranty/warrantyDownLoad.do";
	}
	@RequestMapping("/admin/warranty/warrantyDownLoad.do")
	public void warrantyDownLoad(HttpServletResponse response) {
		TwarrantyCondition condition = new TwarrantyCondition();
		condition.setWarrantyStatus(WarrantyStatus.HAVE_SUBMIT);
		List<Twarranty> list = warrantyBiz.find(condition);
		File file = null;
		try {
			TuserSession tuserSession = (TuserSession) AppSessionFilter.getAppSession();
			String trace = identifierService.genBatchId();
			file = fileDownload.warrantyInfoDown(list, trace);
			Tfile tfile = tfileBiz.getTfileByFile(file, "csv","application/octet-stream");
			tfileBiz.save(tfile);
			// 做下载标记,下载后将报修申请单标记为已受理
			for (Twarranty twa : list) {
				twa.setTraceNo(trace);
				warrantyBiz.updateTwarranty(twa, tuserSession.getTuser());
			}

			// 新增报修受理下载关联表
			WarrantyDownLoad wd = new WarrantyDownLoad();
			wd.setBatchNo(trace);
			wd.setGenTime(new Date());
			wd.setUpdTime(new Date());
			wd.setFileUUid(tfile.getUuid());
			wd.setNum(1);
			wd.setiUsr(tuserSession.getTuser().getIuser());
			warrantyBiz.saveWarrantyDownLoad(wd);

			fileUpDownService.download(file, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping("/admin/warranty/warrantyDownLoadHis.do")
	public String warrantyDownLoadHis(Integer pageNum, Integer numPerPage,
			Model model) {
		Page page = new Page();
		page.setCount(true);
		page.setCapacity(numPerPage == null ? CAPACITY : numPerPage);
		page.setPageOffset(pageNum == null ? PAGE_OFF_SET : pageNum - 1);
		WarrantyDownLoadCondition condition = new WarrantyDownLoadCondition();
		condition.addOrders(Order.desc("genTime"));
		List<WarrantyDownLoad> list = warrantyBiz.findDownListByCondition(condition, page);

		model.addAttribute("warrantyDownList", list);
		model.addAttribute(NUM_PER_PAGE, page.getCapacity());
		model.addAttribute(PAGE_NUM, pageNum);
		model.addAttribute(TOTAL_COUNT, page.getRecAmt());
		return "/admin/maintenancemanage/warrantyDownList";
	}
	@RequestMapping("/admin/warranty/warrantyDownDetail.do")
	public String warrantyDownDetail(String id, Model model) {
		if (StringUtils.isBlank(id)) {
			throw new RuntimeException("找不到该记录!");
		}

		Twarranty twa = new Twarranty();
		twa.setTraceNo(id);
		List<Twarranty> twaList = warrantyBiz.findTwarrantyListByCondition(twa);
		model.addAttribute("twaList", twaList);
		return "/admin/maintenancemanage/warrantyDownDetail";
	}
	@RequestMapping("/admin/warranty/toWarrantyRepaired.do")
	public String toWarrantyRepaired(@RequestParam(required = false) Long iwarranty, Model model) {
		
		Twarranty twarranty = this.warrantyBiz.queryById(iwarranty);
		ERPMaintenance erpMaintenance = this.warrantyBiz.queryBySeqno(twarranty.getSeqNo());
		model.addAttribute("twarranty", twarranty);
		model.addAttribute("erpMaintenance", erpMaintenance);
		if (this.warrantyBiz.inWarrantyPeriod(erpMaintenance)) {
			model.addAttribute("inWarrantyPeriod", Boolean.TRUE);
		}
		return "/admin/maintenancemanage/warrantyRepaired";
		
	}
	@RequestMapping("/admin/warranty/warrantyRepaired.do")
	public String warrantyRepaired(Twarranty twarranty, Model model) {
		TuserSession tuserSession = (TuserSession) AppSessionFilter.getAppSession();
		try {
			this.warrantyBiz.warrantyRepaired(tuserSession.getTuser(), twarranty);
		} catch (BizException e) {
			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);

			return "/common/ajaxResult";
		}
		
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		ajaxResult.setMessage("修复成功");
		ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
		ajaxResult.setNavTabId(NAV_TAB_ID);
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
	/**
	 * 报修明细
	 * @param iwarranty
	 * @param model
	 * @return
	 */
	@RequestMapping("/admin/warranty/warrantyDetail.do")
	public String warrantyDetail(@RequestParam Long iwarranty, Model model) {
		
		Twarranty twarranty = this.warrantyBiz.queryById(iwarranty);
		ERPMaintenance maintenance = this.warrantyBiz.queryBySeqno((twarranty != null) ? twarranty.getSeqNo() : null);
		model.addAttribute("twarranty", twarranty);
		model.addAttribute("maintenance", maintenance);
		
		return "/admin/maintenancemanage/warrantyDetail";
	}

}
