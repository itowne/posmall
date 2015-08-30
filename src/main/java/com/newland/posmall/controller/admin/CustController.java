package com.newland.posmall.controller.admin;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.TuserSession;
import org.ohuyo.rapid.base.entity.AjaxResult;
import org.ohuyo.rapid.base.entity.Tfile;
import org.ohuyo.rapid.base.service.FileUpDownService;
import org.ohuyo.rapid.base.service.TfileService;
import org.ohuyo.rapid.base.servlet.AppSessionFilter;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.newland.posmall.base.exception.BizException;
import com.newland.posmall.base.service.TcustRegService;
import com.newland.posmall.bean.customer.Tcust;
import com.newland.posmall.bean.customer.TcustCode;
import com.newland.posmall.bean.customer.TcustRate;
import com.newland.posmall.bean.customer.TcustReg;
import com.newland.posmall.bean.dict.CreditLevel;
import com.newland.posmall.bean.dict.CustStatus;
import com.newland.posmall.biz.admin.CustManagerBiz;
import com.newland.posmall.biz.file.FileDownload;
import com.newland.posmall.controller.BaseController;
import com.newland.posmall.controller.jspmodel.CustRateListCon;

@Scope("prototype")
@Controller("admin.cust")
public class CustController extends BaseController {

	private static String NAV_TAB_ID = "KHXXGL";

	@Resource
	private CustManagerBiz custManagerBiz;

	@Resource
	private TfileService tfileService;

	@Resource
	private FileDownload fileDownload;

	@Resource
	private FileUpDownService fileUpDownService;

	@Resource
	private TcustRegService tcustRegService;

	/**
	 * 查询 分页列表
	 * 
	 * @param model
	 * @param pageNum
	 * @param numPerPage
	 * @param custStatus
	 * @param creditLevel
	 * @param name
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/admin/cust/custList.do")
	public String custList(Model model,
			@RequestParam(required = false) Integer pageNum,
			@RequestParam(required = false) Integer numPerPage,
			@RequestParam(required = false) String custStatus,
			@RequestParam(required = false) String creditLevel,
			@RequestParam(required = false) String name) {

		Page page = new Page();
		page.setCount(true);
		page.setCapacity(numPerPage == null ? CAPACITY : numPerPage);
		page.setPageOffset(pageNum == null ? PAGE_OFF_SET : pageNum - 1);
		
		List custList = custManagerBiz.findListByInfo(page, custStatus, creditLevel, name);

		model.addAttribute("custList", custList);
		model.addAttribute("custStatus", custStatus);
		model.addAttribute("creditLevel", creditLevel);
		model.addAttribute("name", name);

		model.addAttribute(NUM_PER_PAGE, page.getCapacity());
		model.addAttribute(PAGE_NUM, pageNum);
		model.addAttribute(TOTAL_COUNT, page.getRecAmt());

		return "/admin/cust/custList";
	}

	/**
	 * 客户详细信息
	 * 
	 * @return
	 */
	@RequestMapping("/admin/cust/toCustModify.do")
	public String toCustModify(@RequestParam(required = false) Long icust,
			Model model) {

		Tcust tcust = this.custManagerBiz.findTcust(icust);
		TcustReg tcustReg = this.custManagerBiz.findTcustReg(icust);

		Tfile busLicIfile = (tcustReg.getTaxRegIfile() == null) ? null
				: tfileService.getById(tcustReg.getBusLicIfile());
		Tfile orgCodeIfile = (tcustReg.getOrgCodeIfile() == null) ? null
				: tfileService.getById(tcustReg.getOrgCodeIfile());
		Tfile taxRegIfile = (tcustReg.getTaxRegIfile() == null) ? null
				: tfileService.getById(tcustReg.getTaxRegIfile());

		model.addAttribute("busLicIfile", busLicIfile);
		model.addAttribute("orgCodeIfile", orgCodeIfile);
		model.addAttribute("taxRegIfile", taxRegIfile);

		model.addAttribute("tcust", tcust);
		model.addAttribute("tcustReg", tcustReg);
		return "/admin/cust/custModify";
	}

	@RequestMapping(value = "/admin/cust/custModify.do", method = RequestMethod.POST)
	public String custModify(@DateTimeFormat(pattern = "yyyy-MM-dd") Date signatureDateForm,
			@RequestParam(required = false) CreditLevel creditLevel,
			@RequestParam(required = false) Long icust, TcustReg tcustReg,
			Model model) {

		try {
			Tcust tcust = this.custManagerBiz.findTcust(icust);
			TuserSession tuserSession = (TuserSession) AppSessionFilter.getAppSession();
			// 提供 信用状态 修改。
			tcust.setCreditLevel(creditLevel);
			tcustReg.setSignatureDate(signatureDateForm);
			this.custManagerBiz.modifyCustReg(tcustReg, tcust, tuserSession.getTuser());
		} catch (BizException e) {

			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setNavTabId(NAV_TAB_ID);
			ajaxResult.setCallbackType("");
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		} catch (Exception e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage("系统异常");
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

	/**
	 * 客户详细信息
	 * 
	 * @return
	 */
	@RequestMapping("/admin/cust/custDetail.do")
	public String toCustDetail(@RequestParam(required = false) Long icust,
			Model model) {

		Tcust tcust = this.custManagerBiz.findTcust(icust);
		TcustReg tcustReg = this.custManagerBiz.findTcustReg(icust);

		Tfile busLicIfile = (tcustReg.getTaxRegIfile() == null) ? null
				: tfileService.getById(tcustReg.getBusLicIfile());
		Tfile orgCodeIfile = (tcustReg.getOrgCodeIfile() == null) ? null
				: tfileService.getById(tcustReg.getOrgCodeIfile());
		Tfile taxRegIfile = (tcustReg.getTaxRegIfile() == null) ? null
				: tfileService.getById(tcustReg.getTaxRegIfile());

		model.addAttribute("busLicIfile", busLicIfile);
		model.addAttribute("orgCodeIfile", orgCodeIfile);
		model.addAttribute("taxRegIfile", taxRegIfile);
		model.addAttribute("tcust", tcust);
		model.addAttribute("tcustReg", tcustReg);
		return "/admin/cust/custDetail";
	}

	/**
	 * 审核客户资料页面
	 * 
	 * @return
	 */
	@RequestMapping("/admin/cust/toCustAudit.do")
	public String toCustAudit(@RequestParam(required = false) Long icust,
			Model model) {

		TcustReg tcustReg = this.custManagerBiz.findTcustReg(icust);

		Tfile busLicIfile = (tcustReg.getTaxRegIfile() == null) ? null
				: tfileService.getById(tcustReg.getBusLicIfile());
		Tfile orgCodeIfile = (tcustReg.getOrgCodeIfile() == null) ? null
				: tfileService.getById(tcustReg.getOrgCodeIfile());
		Tfile taxRegIfile = (tcustReg.getTaxRegIfile() == null) ? null
				: tfileService.getById(tcustReg.getTaxRegIfile());

		Tcust tcust = this.custManagerBiz.findTcust(icust);
		custManagerBiz.custAuditIng(tcust);

		model.addAttribute("busLicIfile", busLicIfile);
		model.addAttribute("orgCodeIfile", orgCodeIfile);
		model.addAttribute("taxRegIfile", taxRegIfile);

		model.addAttribute("tcustReg", tcustReg);
		model.addAttribute("tcust", tcust);
		model.addAttribute("icust", icust);
		return "/admin/cust/custAudit";
	}

	/**
	 * 填写拒绝原因
	 * 
	 * @param icust
	 * @param custStatus
	 * @param model
	 * @return
	 */
	@RequestMapping("/admin/cust/toRefuseReason.do")
	public String toRefuseReason(@RequestParam(required = false) Long icust,
			Model model) {

		model.addAttribute("icust", icust);
		return "/admin/cust/refuseReason";
	}

	/**
	 * 审核客户
	 * 
	 * @param icust
	 * @param custStatus
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/admin/cust/custAudit.do", method = RequestMethod.POST)
	public String custAudit(@RequestParam(required = false) Long icust,
			@RequestParam(required = false) CustStatus custStatus,
			@RequestParam(required = false) String refuseReason, 
			@RequestParam(required = false) String salesmanName,
			@RequestParam(required = false) String salesmanEmail,Model model) {

		TcustReg tcustReg = this.custManagerBiz.findTcustReg(icust);
		Tcust tcust = this.custManagerBiz.findTcust(icust);

		try {
			this.custManagerBiz.custAudit(tcust, tcustReg,
					(custStatus == null) ? null : custStatus.toString(),
					refuseReason,salesmanName,salesmanEmail);
		} catch (BizException e) {

			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setNavTabId(NAV_TAB_ID);
			ajaxResult.setCallbackType("");
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}

		if (CustStatus.AUDIT_PASS.equals(custStatus)) {
			ajaxResult.setMessage("客户审核已通过");
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
		} else {
			ajaxResult.setMessage("客户审核被拒绝");
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);

		}
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}

	/**
	 * 客户折扣率 修改
	 * 
	 * @return
	 */
	@RequestMapping("/admin/cust/custRateModify.do")
	public String custRateModify(CustRateListCon custRateListCon, Model model) {
		
		TuserSession tuserSession = (TuserSession) AppSessionFilter.getAppSession();
		custManagerBiz.modifyCustRateList(custRateListCon.getTcustRateList(), tuserSession.getTuser());

		ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
		ajaxResult.setMessage("修改折扣率成功");
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}

	/**
	 * 客户折扣率 修改
	 * 
	 * @return
	 */
	@RequestMapping("/admin/cust/toCustRateModify.do")
	public String toCustRateModify(@RequestParam(required = false) Long icust,
			Model model) {

		Tcust tcust = this.custManagerBiz.findTcust(icust);
		List<TcustRate> tcustRateList = custManagerBiz.queryCustRate(icust);

		model.addAttribute("tcustRateList", tcustRateList);
		model.addAttribute("tcust", tcust);
		return "/admin/cust/custRateModify";
	}

	@RequestMapping("/admin/cust/downLoadCustInfo.do")
	public void downLoadCustInfo(
			@RequestParam(required = false) String custStatus,
			@RequestParam(required = false) String creditLevel,
			@RequestParam(required = false) String name,
			HttpServletResponse response) {

		Tcust tcust = new Tcust();
		if (StringUtils.isNotBlank(custStatus)) {
			tcust.setCustStatus(CustStatus.valueOf(custStatus));
		}
		if (StringUtils.isNotBlank(creditLevel)) {
			tcust.setCreditLevel(CreditLevel.valueOf(creditLevel));
		}
		if (StringUtils.isNotBlank(name)) {
			tcust.setLoginName(name);
		}

		List<Tcust> tCustList = custManagerBiz.findListByCondition(tcust);

		for (int i = 0; i < tCustList.size(); i++) {
			Tcust tc = tCustList.get(i);
			TcustReg tr = tcustRegService.find(tc.getIcust());
			if (tr == null) {
				throw new RuntimeException();
			}
			tc.setTcustReg(tr);
		}
		File file = null;
		try {
			file = fileDownload.custInfoDown(tCustList);
			fileUpDownService.download(file, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 注册码列表
	 * @param model
	 * @param pageNum
	 * @param numPerPage
	 * @param custCode
	 * @param companyName
	 * @param custCodeStatus
	 * @return
	 */
	@RequestMapping("/admin/cust/custCodeList.do")
	public String custCodeList(Model model,
			@RequestParam(required = false) Integer pageNum,
			@RequestParam(required = false) Integer numPerPage,
			@RequestParam(required = false) String custCode,
			@RequestParam(required = false) String companyName,
			@RequestParam(required = false) String custCodeStatus) {

		Page page = new Page();
		page.setCount(true);
		page.setCapacity(numPerPage == null ? CAPACITY : numPerPage);
		page.setPageOffset(pageNum == null ? PAGE_OFF_SET : pageNum - 1);
		
		List<TcustCode> tcustCodeList = this.custManagerBiz.queryTcustCodePage(custCode, companyName, custCodeStatus, page);

		model.addAttribute("tcustCodeList", tcustCodeList);
		model.addAttribute("custCode", custCode);
		model.addAttribute("companyName", companyName);
		model.addAttribute("custCodeStatus", custCodeStatus);

		model.addAttribute(NUM_PER_PAGE, page.getCapacity());
		model.addAttribute(PAGE_NUM, pageNum);
		model.addAttribute(TOTAL_COUNT, page.getRecAmt());

		return "/admin/cust/custCodeList";
	}
	
	/**
	 * 生成注册码
	 * @param model
	 * @return
	 */
	@RequestMapping("/admin/cust/generateCustCode.do")
	public String generateCustCode(Model model) {
		TuserSession session = (TuserSession) AppSessionFilter.getAppSession();
		int num = 0;
		try {
			num = this.custManagerBiz.generateCustCode(null, session.getTuser());
		} catch (BizException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		} catch (Exception e2) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e2.getMessage());
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
		ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
		ajaxResult.setMessage("已成功生成【" + num + "个】注册码");
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
}
