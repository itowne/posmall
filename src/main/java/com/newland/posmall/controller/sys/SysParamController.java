package com.newland.posmall.controller.sys;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.entity.AjaxResult;
import org.ohuyo.rapid.base.entity.TsysParam;
import org.ohuyo.rapid.base.entity.condition.TsysParamCondition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.newland.posmall.biz.sys.SysParamBiz;
import com.newland.posmall.controller.BaseController;

@Scope("prototype")
@Controller("sys.sysparam")
public class SysParamController extends BaseController {

	@Resource
	private SysParamBiz sysParamBiz;

	private static final String NAV_TAB_ID = "CSGL";

	/**
	 * 查询列表
	 */
	@RequestMapping("/sys/sysparam/sysparamList.do")
	public String sysparamList(@RequestParam(required = false) String name,
			@RequestParam(required = false) Integer numPerPage,
			@RequestParam(required = false) Integer pageNum, Model model) {

		Page page = new Page();
		page.setCount(true);
		page.setCapacity(numPerPage == null ? CAPACITY : numPerPage);
		page.setPageOffset(pageNum == null ? PAGE_OFF_SET : pageNum - 1);

		TsysParamCondition tsysParamCondition = new TsysParamCondition();
		if (StringUtils.isNotBlank(name)) {
			tsysParamCondition.setName(name);
		}

		List<TsysParam> tsysParamList = this.sysParamBiz.queryAllTsysParam(tsysParamCondition, page);
		
		model.addAttribute(tsysParamList);
		model.addAttribute(NUM_PER_PAGE, page.getCapacity());
		model.addAttribute(PAGE_NUM, pageNum);
		model.addAttribute(TOTAL_COUNT, page.getRecAmt());
		model.addAttribute("name", name);
		return "/sys/sysparam/sysparamList";
	}
	
	
	/**
	 * 去修改页面
	 */
	@RequestMapping("/sys/sysparam/sysparamModify.do")
	public String sysParamModify(@RequestParam(required = false) Long isysParam,
			Model model) {
		TsysParam tsysParam = this.sysParamBiz.queryTsysParamByIsysParam(isysParam);
		model.addAttribute(tsysParam);
		return "/sys/sysparam/sysparamModify";
	}

	/**
	 * 修改
	 */
	@RequestMapping(value = "/sys/sysparam/sysparamModify.do",method = RequestMethod.POST)
	public String sysParamModify(TsysParam tsysParam,Model model) {
		
		this.sysParamBiz.modifyTsysParamByIsysParam(tsysParam);
		
		ajaxResult.setMessage(AjaxResult.MSG_UPDATE);
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}

}
