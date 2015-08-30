package com.newland.posmall.controller.admin;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Order;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.TuserSession;
import org.ohuyo.rapid.base.entity.AjaxResult;
import org.ohuyo.rapid.base.entity.Tfile;
import org.ohuyo.rapid.base.service.FileUpDownService;
import org.ohuyo.rapid.base.servlet.AppSessionFilter;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.newland.posmall.base.exception.BizException;
import com.newland.posmall.bean.basebusi.LogisticsDownLoad;
import com.newland.posmall.bean.basebusi.TlogisticsCom;
import com.newland.posmall.bean.basebusi.TlogisticsOrd;
import com.newland.posmall.bean.basebusi.TlogisticsOrdAddr;
import com.newland.posmall.bean.basebusi.condition.LogisticsDownLoadCondition;
import com.newland.posmall.bean.customer.Tcust;
import com.newland.posmall.bean.customer.condition.TlogisticsOrdCondition;
import com.newland.posmall.bean.dict.LogisticsOrdStatus;
import com.newland.posmall.biz.admin.LogisticsOrdBiz;
import com.newland.posmall.biz.common.TfileBiz;
import com.newland.posmall.biz.cust.Logistics4PageBiz;
import com.newland.posmall.biz.cust.LogisticsOrderWithMultipleAddrs4PageBiz;
import com.newland.posmall.biz.file.FileDownload;
import com.newland.posmall.controller.BaseController;
import com.newland.posmall.controller.cust.model.LogisticsDown4Page;
import com.newland.posmall.controller.cust.model.LogisticsOrd4Page;
import com.newland.posmall.controller.cust.model.LogisticsOrderWithMultipleAddrs4Page;
import com.newland.posmall.identifier.IdentifierService;

@Scope("prototype")
@Controller("admin.logisticsorder")
public class LogisticsOrderController extends BaseController {
	
	private final static String NAV_TAB_ID = "WLDGL";

	@Resource
	private LogisticsOrdBiz logisticsOrdBiz;
	
	@Resource
	private Logistics4PageBiz logistics4PageBiz;
	
	@Resource
	private LogisticsOrderWithMultipleAddrs4PageBiz logisticsOrderWithMultipleAddrs4PageBiz;
	
	@Resource
	private FileDownload fileDownload;
	@Resource
	private FileUpDownService fileUpDownService;
	@Resource
	private TfileBiz tfileBiz;

	@Resource(name = "identifierService")
	private IdentifierService identifierService;

	@RequestMapping("/admin/logistics/logisticsorder/logisticsorderDetail.do")
	public String logisticsorderDetail(
			@RequestParam(required = false) Long ilogisticsOrd, Model model) {
		TlogisticsOrd tlogisticsOrd = this.logisticsOrdBiz.findLogisticsOrdById(ilogisticsOrd);
		LogisticsOrd4Page logisticsOrd4Page = this.logistics4PageBiz.findLogisticsOrd4Page(tlogisticsOrd);
		
		model.addAttribute("logisticsOrd4Page", logisticsOrd4Page);
		
		return "/admin/logistics/logisticsorder/logisticsorderDetail";
	}
	
	@RequestMapping("/admin/logistics/logisticsorder/logisticsorderDetailMore.do")
	public String logisticsorderDetailMore(
			@RequestParam(required = false) Long ilogisticsOrd, Model model) {
		TlogisticsOrd tlogisticsOrd = this.logisticsOrdBiz.findLogisticsOrdById(ilogisticsOrd);
		Tcust tcust = this.logisticsOrdBiz.genTcustByTlogisticsOrd(tlogisticsOrd);
		LogisticsOrderWithMultipleAddrs4Page p = this.logisticsOrderWithMultipleAddrs4PageBiz.getLogisticsOrderWithMultipleAddrs4Page(ilogisticsOrd, tcust);
		
		
		model.addAttribute("p", p);
		model.addAttribute("ilogisticsOrd", ilogisticsOrd);
		model.addAttribute("tlogisticsOrd", tlogisticsOrd);
		return "/admin/logistics/logisticsorder/logisticsorderDetailMore";
	}

	/**
	 * 跳转 审核页面（单地址）
	 * @param ilogisticsOrd
	 * @param model
	 * @return
	 */
	@RequestMapping("/admin/logistics/logisticsorder/toAuditLogisticsOrd.do")
	public String toAuditLogisticsOrd(
			@RequestParam(required = false) Long ilogisticsOrd, Model model) {
		TlogisticsOrd tlogisticsOrd = this.logisticsOrdBiz.findLogisticsOrdById(ilogisticsOrd);
		LogisticsOrd4Page logisticsOrd4Page = this.logistics4PageBiz.findLogisticsOrd4Page(tlogisticsOrd);
		
		model.addAttribute("logisticsOrd4Page", logisticsOrd4Page);
		
		return "/admin/logistics/logisticsorder/logisticsorderAudit";
	}
	
	/**
	 * 跳转 审核页面（多地址）
	 * @param ilogisticsOrd
	 * @param model
	 * @return
	 */
	@RequestMapping("/admin/logistics/logisticsorder/toAuditMoreLogisticsOrd.do")
	public String toAuditMoreLogisticsOrd(@RequestParam(required = false) String specifyDelivery,
			@RequestParam(required = false) Long ilogisticsOrd, Model model) {
		TlogisticsOrd tlogisticsOrd = this.logisticsOrdBiz.findLogisticsOrdById(ilogisticsOrd);
		Tcust tcust = this.logisticsOrdBiz.genTcustByTlogisticsOrd(tlogisticsOrd);
		LogisticsOrderWithMultipleAddrs4Page p = this.logisticsOrderWithMultipleAddrs4PageBiz.getLogisticsOrderWithMultipleAddrs4Page(ilogisticsOrd, tcust);
		Map<String, TlogisticsCom>  yesFeeMap = this.logisticsOrdBiz.queryFeeLogisticsCom(null);
//		Map<String, TlogisticsCom>  noFeeMap = this.logisticsOrdBiz.queryFeeLogisticsCom(YesNoType.NO);
		
		
		model.addAttribute("p", p);
		model.addAttribute("ilogisticsOrd", ilogisticsOrd);
		model.addAttribute("tlogisticsOrd", tlogisticsOrd);
		model.addAttribute("yesFeeMap", yesFeeMap);
//		model.addAttribute("noFeeMap", noFeeMap);
		return "/admin/logistics/logisticsorder/logisticsorderAuditMore";
	
	}
	
	
	
	@RequestMapping("/admin/logistics/logisticsorder/toFillActual.do")
	public String toFillActual(
			@RequestParam(required = false) Long ilogisticsOrd, Model model) {
		TlogisticsOrd tlogisticsOrd = this.logisticsOrdBiz.findLogisticsOrdById(ilogisticsOrd);
		LogisticsOrd4Page logisticsOrd4Page = this.logistics4PageBiz.findLogisticsOrd4Page(tlogisticsOrd);
		
		model.addAttribute("logisticsOrd4Page", logisticsOrd4Page);
		
		return "/admin/logistics/logisticsorder/fillActual";
	}
	
	@RequestMapping("/admin/logistics/logisticsorder/toFillActualMore.do")
	public String toFillActualMore(
			@RequestParam(required = false) Long ilogisticsOrd, Model model) {
		TlogisticsOrd tlogisticsOrd = this.logisticsOrdBiz.findLogisticsOrdById(ilogisticsOrd);
		Tcust tcust = this.logisticsOrdBiz.genTcustByTlogisticsOrd(tlogisticsOrd);
		LogisticsOrderWithMultipleAddrs4Page p = this.logisticsOrderWithMultipleAddrs4PageBiz.getLogisticsOrderWithMultipleAddrs4Page(ilogisticsOrd, tcust);
		
		model.addAttribute("p", p);
		model.addAttribute("ilogisticsOrd", ilogisticsOrd);
		model.addAttribute("tlogisticsOrd", tlogisticsOrd);
		
		return "/admin/logistics/logisticsorder/fillActualMore";
	}
	
	@RequestMapping("/admin/logistics/logisticsorder/fillActual.do")
	public String fillActual(TlogisticsOrdAddr  tlogisticsOrdAddr, LogisticsOrd4Page logisticsOrd4Page, Model model,
			@RequestParam(required = false) Long ilogisticsOrd, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date realDeliveryDateForm,@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date realArrivalDateForm) {
		TlogisticsOrd tlogisticsOrd = this.logisticsOrdBiz.findLogisticsOrdById(ilogisticsOrd);
		TuserSession tuserSession = (TuserSession) AppSessionFilter.getAppSession();
		
		try {
			tlogisticsOrdAddr.setRealDelivery(realDeliveryDateForm);
			tlogisticsOrdAddr.setRealArrival(realArrivalDateForm);
			this.logisticsOrdBiz.fillActual(tlogisticsOrd, tlogisticsOrdAddr, logisticsOrd4Page, tuserSession.getTuser());
		} catch (BizException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setCallbackType("");
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
		
		ajaxResult.setMessage("填写实际发货信息成功");
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
	
	@RequestMapping("/admin/logistics/logisticsorder/fillActualMore.do")
	public String fillActualMore(TlogisticsOrdAddr  tlogisticsOrdAddr, LogisticsOrderWithMultipleAddrs4Page multipleAddrs4Page, Model model,
			@RequestParam(required = false) Long ilogisticsOrd) {
		TlogisticsOrd tlogisticsOrd = this.logisticsOrdBiz.findLogisticsOrdById(ilogisticsOrd);
		TuserSession tuserSession = (TuserSession) AppSessionFilter.getAppSession();
		
		try {
			this.logisticsOrdBiz.fillActualMore(tlogisticsOrd, multipleAddrs4Page, tuserSession.getTuser());
		} catch (BizException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setCallbackType("");
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		} catch (ParseException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMessage());
			ajaxResult.setCallbackType("");
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
		
		ajaxResult.setMessage("填写实际发货信息成功");
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
	
	@RequestMapping(value = "/admin/logistics/logisticsorder/logisticsorderAudit.do", method = RequestMethod.POST)
	public String auditLogisticsOrd(
			@RequestParam(required = false) Long ilogisticsOrd, 
			@RequestParam(required = false) BigDecimal price, 
			Model model) {
		TlogisticsOrd tlogisticsOrd = this.logisticsOrdBiz.findLogisticsOrdById(ilogisticsOrd);
		LogisticsOrd4Page logisticsOrd4Page = this.logistics4PageBiz.findLogisticsOrd4Page(tlogisticsOrd);
		
		TuserSession tuserSession = (TuserSession) AppSessionFilter.getAppSession();
		try {
			this.logisticsOrdBiz.auditTlogisticsOrd(tlogisticsOrd, logisticsOrd4Page, price ,tuserSession.getTuser(),tuserSession.getTuserSub());
		} catch (BizException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setCallbackType("");
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
		
		ajaxResult.setMessage("物流单审核通过");
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
	
	@RequestMapping(value = "/admin/logistics/logisticsorder/logisticsorderAuditMore.do", method = RequestMethod.POST)
	public String auditLogisticsOrdMore(
			@RequestParam(required = false) Long ilogisticsOrd, 
			LogisticsOrderWithMultipleAddrs4Page  multipleAddrs4Page,
			Model model) {
		TlogisticsOrd tlogisticsOrd = this.logisticsOrdBiz.findLogisticsOrdById(ilogisticsOrd);
		
		TuserSession tuserSession = (TuserSession) AppSessionFilter.getAppSession();
		try {
			this.logisticsOrdBiz.auditTlogisticsOrdMore(tlogisticsOrd, multipleAddrs4Page,tuserSession.getTuser(),tuserSession.getTuserSub());
		} catch (BizException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setCallbackType("");
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
		
		ajaxResult.setMessage("物流单审核通过");
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
	
	@RequestMapping("/admin/logistics/logisticsOrdCancel.do")
	public String logisticsOrdCancel(@RequestParam(required = false) Long ilogisticsOrd, Model model) {
		TuserSession tuserSession = (TuserSession) AppSessionFilter.getAppSession();
		try {
			this.logisticsOrdBiz.logisticsOrdCancel(tuserSession.getTuser(), ilogisticsOrd);
		} catch (BizException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setCallbackType("");
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
		
		ajaxResult.setMessage(AjaxResult.MSG_DELETE);
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType("");
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
	

	@RequestMapping("/admin/logistics/logisticsorder/logisticsorderList.do")
	public String logisticsOrdList(
			@RequestParam(required = false) String name,
			@RequestParam(required = false) Integer pageNum, 
			@RequestParam(required = false) Integer numPerPage, 
			TlogisticsOrd tlogisticsOrd,
			@RequestParam(required = false) String consigneeName,Model model) {
		Page page = new Page();
		page.setCount(true);
		page.setCapacity(numPerPage == null ? CAPACITY : numPerPage);
		page.setPageOffset(pageNum == null ? PAGE_OFF_SET : pageNum - 1);

		List<TlogisticsOrd> tLogisticsOrdList = logisticsOrdBiz.queryOrdByCon(name, tlogisticsOrd,page,consigneeName);

		model.addAttribute("tLogisticsOrdList", tLogisticsOrdList);
		model.addAttribute(NUM_PER_PAGE, page.getCapacity());
		model.addAttribute(PAGE_NUM, pageNum);
		model.addAttribute(TOTAL_COUNT, page.getRecAmt());
		model.addAttribute("name", name);
		model.addAttribute("consigneeName", consigneeName);
		return "/admin/logistics/logisticsorder/logisticsorderList";
	}

	@RequestMapping("/admin/logistics/logisticsorder/logisticsordDownLoad.do")
	public void logisticsordDownLoad(HttpServletResponse response) {
		TlogisticsOrdCondition condition = new TlogisticsOrdCondition();
		condition.setGenTime(new Date());
		condition.setLogisticsOrdStatus(LogisticsOrdStatus.HAVE_AUDIT);
		List<TlogisticsOrd> list = logisticsOrdBiz.queryOrdListByCon(condition);
		List<LogisticsDown4Page> ld4pList = logisticsOrdBiz.getLogisticsData(list);
		File file = null;
		try {
			TuserSession tuserSession = (TuserSession) AppSessionFilter.getAppSession();
			String trace = identifierService.genBatchId();
			file = fileDownload.lgcsOrdInfoDown(ld4pList,trace);
			Tfile tfile = tfileBiz.getTfileByFile(file, "csv","application/octet-stream");
			tfileBiz.save(tfile);
			// 做下载标记,下载后将物流单标记为已出库
			for (TlogisticsOrd ord : list) {
				ord.setLogisticsOrdStatus(LogisticsOrdStatus.HAVE_LIBRARY);
				ord.setTraceNo(trace);
				logisticsOrdBiz.updateTlogisticsOrd(ord, tuserSession.getTuser());
			}

			// 新增物流下载关联表
			LogisticsDownLoad ld = new LogisticsDownLoad();
			ld.setBatchNo(trace);
			ld.setGenTime(new Date());
			ld.setUpdTime(new Date());
			ld.setFileUUid(tfile.getUuid());
			ld.setNum(1);
			ld.setiUsr(tuserSession.getTuser().getIuser());
			logisticsOrdBiz.saveLogistics4Down(ld);

			fileUpDownService.download(file, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/admin/logistics/logisticsorder/logisticsordDownLoadHis.do")
	public String logisticsordDownLoadHis(Integer pageNum, Integer numPerPage,
			Model model) {
		Page page = new Page();
		page.setCount(true);
		page.setCapacity(numPerPage == null ? CAPACITY : numPerPage);
		page.setPageOffset(pageNum == null ? PAGE_OFF_SET : pageNum - 1);
		LogisticsDownLoadCondition condition = new LogisticsDownLoadCondition();
		condition.addOrders(Order.desc("genTime"));
		List<LogisticsDownLoad> list = logisticsOrdBiz.findDownListByCondition(
				condition, page);

		model.addAttribute("tLogisticsDownList", list);
		model.addAttribute(NUM_PER_PAGE, page.getCapacity());
		model.addAttribute(PAGE_NUM, pageNum);
		model.addAttribute(TOTAL_COUNT, page.getRecAmt());
		return "/admin/logistics/logisticsorder/logisticsDownList";
	}

	@RequestMapping("/admin/logistics/logisticsorder/logisticsordDownDetail.do")
	public String logisticsordDownDteail(String id, Model model) {
		if (StringUtils.isBlank(id)) {
			throw new RuntimeException("找不到该记录!");
		}

		TlogisticsOrd tlo = new TlogisticsOrd();
		tlo.setTraceNo(id);
		List<TlogisticsOrd> ordList = logisticsOrdBiz.findListBy(tlo);
		List<LogisticsDown4Page> ld4pList = logisticsOrdBiz
				.getLogisticsData(ordList);
		model.addAttribute("ld4pList", ld4pList);
		return "/admin/logistics/logisticsorder/logisticsordDownDetail";
	}
	
	@RequestMapping("/admin/logistics/logisticsorder/checkDownLoad.do")
	public String checkDownLoad(){
		TlogisticsOrdCondition condition = new TlogisticsOrdCondition();
		condition.setGenTime(new Date());
		condition.setLogisticsOrdStatus(LogisticsOrdStatus.HAVE_AUDIT);
		List<TlogisticsOrd> list = logisticsOrdBiz.queryOrdListByCon(condition);
		if(CollectionUtils.isEmpty(list)){
			throw new RuntimeException("没有可下载的数据!");
		}
		return "redirect:/admin/logistics/logisticsorder/logisticsordDownLoad.do";
	}
	@RequestMapping("/admin/logistics/logisticsorder/toRemoveSingleLogisticsOrd.do")
	public String toRemoveSingleLogisticsOrd(
			@RequestParam(required = false) Long ilogisticsOrd, Model model) {
		TlogisticsOrd tlogisticsOrd = this.logisticsOrdBiz.findLogisticsOrdById(ilogisticsOrd);
		Tcust tcust = this.logisticsOrdBiz.genTcustByTlogisticsOrd(tlogisticsOrd);
		LogisticsOrderWithMultipleAddrs4Page p = this.logisticsOrderWithMultipleAddrs4PageBiz.getLogisticsOrderWithMultipleAddrs4Page(ilogisticsOrd, tcust);
		
		
		model.addAttribute("p", p);
		model.addAttribute("ilogisticsOrd", ilogisticsOrd);
		model.addAttribute("tlogisticsOrd", tlogisticsOrd);
		return "/admin/logistics/logisticsorder/toRemoveSingleLogisticsOrd";
	}
	@RequestMapping("/admin/logistics/logisticsorder/removeSingleLogisticsOrd.do")
	public String removeSingleLogisticsOrd(
			@RequestParam(required = false) Long ilogisticsOrdAddr, Model model) {
		TlogisticsOrdAddr tlogisticsOrdAddr = this.logisticsOrdBiz.getTlogisticsOrdAddrById(ilogisticsOrdAddr);
		TlogisticsOrd tlogisticsOrd =  this.logisticsOrdBiz.findLogisticsOrdById(tlogisticsOrdAddr.getIlogisticsOrd());
		model.addAttribute("tlogisticsOrdAddr", tlogisticsOrdAddr);
		model.addAttribute("tlogisticsOrd", tlogisticsOrd);
		return "/admin/logistics/logisticsorder/removeSingleLogisticsOrd";
	}
	@RequestMapping("/admin/logistics/logisticsorder/removeSingleConfirm.do")
	public String removeSingleConfirm(
			@RequestParam(required = false) Long ilogisticsOrdAddr, 
			@RequestParam(required = false) String remType,
			@RequestParam(required = false) Integer remNum,
			HttpServletRequest request,Model model) {
		TuserSession tuserSession = (TuserSession) AppSessionFilter.getAppSession();
		Long ilogisticsOrd = null;
		try {
			ilogisticsOrd = this.logisticsOrdBiz.removeSingleConfirm(ilogisticsOrdAddr, remType, remNum, tuserSession.getTuser());
		} catch (BizException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setCallbackType("");
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
		if("0".equals(remType)){//分单
			ajaxResult.setMessage("分单成功");
		}else{//撤单
			ajaxResult.setMessage("撤单成功");
		}
		
		ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_FORWARD);
		StringBuffer forwordUrlSb = new StringBuffer();
		forwordUrlSb.append(request.getContextPath());
		forwordUrlSb.append("/admin/logistics/logisticsorder/toRemoveSingleLogisticsOrd.do");
		forwordUrlSb.append("?ilogisticsOrd=");
		forwordUrlSb.append(ilogisticsOrd);
		ajaxResult.setForwardUrl(forwordUrlSb.toString());
		ajaxResult.setRel("WLDGL_CD");
		
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
}
