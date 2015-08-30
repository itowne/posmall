package com.newland.posmall.controller.cust;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.TcustSession;
import org.ohuyo.rapid.base.entity.AjaxResult;
import org.ohuyo.rapid.base.entity.Tfile;
import org.ohuyo.rapid.base.service.FileUpDownService;
import org.ohuyo.rapid.base.service.TsysParamService;
import org.ohuyo.rapid.base.servlet.AppSessionFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.newland.posmall.Application;
import com.newland.posmall.base.exception.BizException;
import com.newland.posmall.bean.customer.Trenew;
import com.newland.posmall.bean.customer.TrenewDetail;
import com.newland.posmall.bean.customer.condition.TrenewCondition;
import com.newland.posmall.biz.cust.CustRenewBiz;
import com.newland.posmall.controller.BaseController;
import com.newland.posmall.controller.cust.model.RenewData;
import com.newland.posmall.controller.cust.model.RenewData4Page;

/**
 * 产品售后维护
 * @author zhouym
 * @since 2014-10-21
 */
@Scope("prototype")
@Controller("cust.renew")
public class RenewController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(RenewController.class);
	
	@Resource
	private CustRenewBiz custRenewBiz;
	
	@Resource
	private FileUpDownService fileUpDownService;
	
	@Resource
	private TsysParamService tsysParamService;
	
	/**
	 * 续保分页列表
	 * @param condition
	 * @param startTime
	 * @param endTime
	 * @param pageNum
	 * @param numPerPage
	 * @param model
	 * @return
	 */
	@RequestMapping("/cust/renew/renewList.do")
	public String renewList(TrenewCondition condition,
			@DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime, 
			@DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime,
			@RequestParam(required = false) Integer pageNum,
			@RequestParam(required = false) Integer numPerPage, Model model) {
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
		List<Trenew> trenewList = this.custRenewBiz.queryPageList(condition, session.getTcust(), page);
		
		model.addAttribute("trenewList", trenewList);
		model.addAttribute("condition", condition);
		model.addAttribute("startTime", startTime);
		model.addAttribute("endTime", endTime);
		
		model.addAttribute(NUM_PER_PAGE, page.getCapacity());
		model.addAttribute(PAGE_NUM, pageNum);
		model.addAttribute(TOTAL_COUNT, page.getRecAmt());
		return "/cust/renew/renewList";
	}
	
	/**
	 * 续保第一步：跳转至 上传续保csv数据文件 页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/cust/renew/toUploadExcel.do")
	public String toUploadExcel(Model model) {
		return "/cust/renew/uploadExcel";
	}
	
	/**
	 * 下载续保csv数据模板
	 * @param response
	 */
	@RequestMapping("/cust/renew/downTempCsv.do")
	public void downTempCsv(HttpServletResponse response) {
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		org.springframework.core.io.Resource res = resolver.getResource("renewTmp.csv");
		if (res == null) {
			throw new RuntimeException("模板文件未找到");
		}
		try {
			File file = File.createTempFile("tmp", ".csv", new File(Application.getTemplatePath()));
			FileOutputStream fout = new FileOutputStream(file);
			IOUtils.copy(res.getInputStream(), fout);
			this.fileUpDownService.download(file, response);
		} catch (FileNotFoundException e) {
			logger.error("本地临时文件创建失败", e);
		} catch (IOException e) {
			logger.error("IO异常", e);
		} catch (Exception e) {
			logger.error("文件下载失败", e);
		}
	}
	
	/**
	 * 续保第二步(1)：上传续保csv数据文件
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/cust/renew/uploadTempCsvAndGetData.do")
	public String uploadTempCsvAndGetData(HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, Tfile> files = null;
		String[] limitExts = { "xls", "xlsx", "csv" };// {"xls","xlsx","csv"};
		String[] limitContentType = { "application/octet-stream",
				"application/vnd.ms-excel",
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" };
		try {
			files = this.fileUpDownService.upload(request, response, limitExts,
					limitContentType, -1);
		} catch (Exception e) {
			ajaxResult.setMessage(e.getMessage());
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
		
		if(files == null || files.get("renewDataTmp") == null) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage("上传文件失败!");
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
		
		Tfile tfile = files.get("renewDataTmp");

		ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
		ajaxResult.setMessage("");
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_FORWARD);
		StringBuffer forwordUrlSb = new StringBuffer();
		forwordUrlSb.append(request.getContextPath());
		forwordUrlSb.append("/cust/renew/toShowDataForward.do");
		forwordUrlSb.append("?ifile=");
		forwordUrlSb.append(tfile.getIfile());

		ajaxResult.setForwardUrl(forwordUrlSb.toString());
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}

	/**
	 * 续保第二步(2)：转发 跳转至下一页 数据展示
	 * @param ifile
	 * @param model
	 * @return
	 */
	@RequestMapping("/cust/renew/toShowDataForward.do")
	public String toShowDataForward(@RequestParam Long ifile, Model model) {
		List<RenewData> renewDataList = null;
		try {
			renewDataList = this.custRenewBiz.getRenewDataFromTfile(ifile);
		} catch (BizException e) {
			logger.error(e.getMsg(), e);
			ajaxResult.setMessage(e.getMessage());
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ajaxResult.setMessage(e.getMessage());
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
		
		if(CollectionUtils.isEmpty(renewDataList)) {
			ajaxResult.setMessage("数据为空");
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
		
		TcustSession session = (TcustSession) AppSessionFilter.getAppSession();
		String errMsg = this.custRenewBiz.validateRenewData(renewDataList, session.getTcust());
		if(StringUtils.isNotBlank(errMsg)) {
			model.addAttribute("valid", false);
		}
		
		// 续保单价(单位：元/台/年)
		model.addAttribute("renewPrice", this.tsysParamService.getTsysParam("OTHER_CONF", "RENEW_PRICE").getValue());
		
		model.addAttribute("renewDataList", renewDataList);
		model.addAttribute("pdtNum", renewDataList.size());
		model.addAttribute("errMsg", errMsg);
		
		return "/cust/renew/renewAdd";
	}
	
	/**
	 * 续保第三步(1)：跳转至数据确认页，数据校验
	 * @param renewData4Page
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/cust/renew/toRenewAddConfirm.do")
	public String toRenewAddConfirm(RenewData4Page renewData4Page,
			HttpServletRequest request, Model model) {
		if(renewData4Page == null || CollectionUtils.isEmpty(renewData4Page.getRenewDataList())) {
			ajaxResult.setMessage("数据为空");
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
		TcustSession session = (TcustSession) AppSessionFilter.getAppSession();
		String errMsg = this.custRenewBiz.validateRenewData(renewData4Page.getRenewDataList(), session.getTcust());
		boolean valid = true;
		if(StringUtils.isNotBlank(errMsg)) {
			valid = false;
		}
		
		//清空session
		request.getSession().removeAttribute("renewData4Page");
		request.getSession().removeAttribute("errMsg");
		request.getSession().removeAttribute("valid");
		
		request.getSession().setAttribute("renewData4Page", renewData4Page);
		request.getSession().setAttribute("errMsg", errMsg);
		request.getSession().setAttribute("valid", valid);
		
		ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
		ajaxResult.setMessage("");
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_FORWARD);
		StringBuffer forwordUrlSb = new StringBuffer();
		forwordUrlSb.append(request.getContextPath());
		forwordUrlSb.append("/cust/renew/toRenewAddConfirmForward.do");

		ajaxResult.setForwardUrl(forwordUrlSb.toString());
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
	
	/**
	 * 续保第三步(2)：跳转至数据确认页
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/cust/renew/toRenewAddConfirmForward.do")
	public String toRenewAddConfirmForward(HttpServletRequest request, Model model) {
		
		RenewData4Page renewData4Page = (RenewData4Page) request.getSession().getAttribute("renewData4Page");
		String errMsg = (String) request.getSession().getAttribute("errMsg");
		boolean valid = (boolean) request.getSession().getAttribute("valid");
		model.addAttribute("renewData4Page", renewData4Page);
		model.addAttribute("errMsg", errMsg);
		model.addAttribute("valid", valid);
		
		//清空session
		request.getSession().removeAttribute("renewData4Page");
		request.getSession().removeAttribute("errMsg");
		request.getSession().removeAttribute("valid");
		return "/cust/renew/renewAddConfirm";
	}
	
	/**
	 * 续保第四步/最后一步：提交续保申请
	 * @param renewData4Page
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/cust/renew/renewAdd.do")
	public String renewAdd(RenewData4Page renewData4Page,
			HttpServletRequest request, Model model) {
		
		TcustSession session = (TcustSession) AppSessionFilter.getAppSession();
		try {
			this.custRenewBiz.renewAdd(renewData4Page, session.getTcust(), session.getTcustReg());
		} catch (BizException e) {
			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		} catch (Exception e) {
			ajaxResult.setMessage(e.getMessage());
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
		
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		ajaxResult.setMessage("申请已提交，请到“我要付款”模块上传付款凭证");
		ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
		ajaxResult.setNavTabId("CPXB");
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
	
	/**
	 * 详细
	 * @param model
	 * @param irenew
	 * @return
	 */
	@RequestMapping("/cust/renew/renewDetail.do")
	public String renewDetail(Model model, @RequestParam(required = false) Long irenew) {
		Trenew trenew = this.custRenewBiz.queryById(irenew);
		
		List<TrenewDetail> detailList = this.custRenewBiz.queryTrenewDetailList(irenew);

		model.addAttribute("trenew", trenew);
		
		model.addAttribute("detailList", detailList);

		return "/cust/renew/renewDetail";
	}
	
	/**
	 * 续保撤销
	 * @param model
	 * @param irenew
	 * @return
	 */
	@RequestMapping("/cust/renew/renewCacel.do")
	public String renewCacel(Model model, @RequestParam(required = false) Long irenew) {
		
		TcustSession session = (TcustSession) AppSessionFilter.getAppSession();
		try {
			this.custRenewBiz.renewCacel(irenew, session.getTcust());
		} catch (BizException e) {
			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
		
		ajaxResult.setMessage(AjaxResult.MSG_OK);
		ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
}
