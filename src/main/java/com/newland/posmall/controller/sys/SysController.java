package com.newland.posmall.controller.sys;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.TsysSession;
import org.ohuyo.rapid.base.entity.AjaxResult;
import org.ohuyo.rapid.base.entity.Tsys;
import org.ohuyo.rapid.base.entity.condition.TsysCondition;
import org.ohuyo.rapid.base.servlet.AppSessionFilter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.newland.posmall.base.exception.BizException;
import com.newland.posmall.biz.sys.SysBiz;
import com.newland.posmall.controller.BaseController;

@Scope("prototype")
@Controller("sys.sys")
public class SysController extends BaseController {

	@Resource
	private SysBiz sysBiz;

	private static final String NAV_TAB_ID = "GLYXXGL";

	/**
	 * 查询列表
	 */
	@RequestMapping("/sys/sys/sysList.do")
	public String userList(@RequestParam(required = false) String loginName,
			@RequestParam(required = false) Integer numPerPage,
			@RequestParam(required = false) Integer pageNum, Model model) {

		Page page = new Page();
		page.setCount(true);
		page.setCapacity(numPerPage == null ? CAPACITY : numPerPage);
		page.setPageOffset(pageNum == null ? PAGE_OFF_SET : pageNum - 1);

		TsysCondition tsysConfition = new TsysCondition();
		if (StringUtils.isNotBlank(loginName)) {
			tsysConfition.setLoginName(loginName);
		}

		List<Tsys> tsysList = this.sysBiz.queryAllTsys(tsysConfition, page);

		model.addAttribute(tsysList);
		model.addAttribute(NUM_PER_PAGE, page.getCapacity());
		model.addAttribute(PAGE_NUM, pageNum);
		model.addAttribute(TOTAL_COUNT, page.getRecAmt());
		model.addAttribute("loginName", loginName);
		return "/sys/sys/sysList";
	}

	/**
	 * 查询明细
	 */
	@RequestMapping("/sys/sys/sysDetail.do")
	public String sysDetail(@RequestParam(required = false) Long isys,
			Model model) {

		Tsys tsys = this.sysBiz.queryTsysByIsys(isys);
		model.addAttribute(tsys);

		return "/sys/sys/sysDetail";
	}

	/**
	 * 去新增页面
	 */
	@RequestMapping("/sys/sys/sysAdd.do")
	public String sysAdd(@RequestParam(required = false) Long isys, Model model) {
		return "/sys/sys/sysAdd";
	}

	/**
	 * 新增
	 */
	@RequestMapping(value = "/sys/sys/sysAdd.do", method = RequestMethod.POST)
	public String sysAdd(@RequestParam String loginName, Model model) {
		TsysSession tsysSession = (TsysSession) AppSessionFilter.getAppSession();
		try {
			this.sysBiz.addTsys(loginName, tsysSession.getTsys());
		} catch (BizException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setNavTabId(NAV_TAB_ID);
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}

		ajaxResult.setMessage("添加成功，初始密码【123456】");
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}


	/**
	 * 删除
	 * @param isys
	 * @param model
	 * @return
	 */
	@RequestMapping("/sys/sys/sysRemove.do")
	public String sysRemove(@RequestParam(required = false) Long isys, Model model) {
		TsysSession tsysSession = (TsysSession) AppSessionFilter.getAppSession();
		try {
			this.sysBiz.removeTsysByIsys(isys, tsysSession.getTsys());
		} catch (BizException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setNavTabId(NAV_TAB_ID);
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
		
		ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
		ajaxResult.setMessage(AjaxResult.MSG_DELETE);
		ajaxResult.setNavTabId(NAV_TAB_ID);
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
	
	/**
	 * 重置密码
	 * @param isys
	 * @param model
	 * @return
	 */
	@RequestMapping("/sys/sys/resetPass.do")
	public String resetPass(@RequestParam(required = false) Long isys, Model model) {
		TsysSession session = (TsysSession) AppSessionFilter.getAppSession();
		try {
			this.sysBiz.resetPassByIsys(isys, session.getTsys());
		} catch (BizException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setNavTabId(NAV_TAB_ID);
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
		
		ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
		ajaxResult.setMessage("重置密码成功【123456】");
		ajaxResult.setNavTabId(NAV_TAB_ID);
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
}
