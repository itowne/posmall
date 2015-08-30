package com.newland.posmall.controller.sys;


import javax.annotation.Resource;

import org.ohuyo.rapid.base.entity.AjaxResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.newland.posmall.biz.sys.SysPasswordBiz;
import com.newland.posmall.controller.BaseController;

@Scope("prototype")
@Controller("sys.sysPassword")
public class SysPasswordController extends BaseController {

	@Resource
	private SysPasswordBiz sysPasswordBiz;

	private static final String NAV_TAB_ID = "SYSMMXG";
	
	/**
	 * 去密码修改页面
	 */
	@RequestMapping("/sys/sysPassword/passwordMod.do")
	public String passwordMod(Model model) {
		return "/sys/passwordMod";
	}
	
	/**
	 * 修改密码
	 * @throws Exception 
	 */
	@RequestMapping("/sys/sysPassword/passwordToMod.do")
	public String passwordToMod(@RequestParam(required = false) String oldPassword,
			@RequestParam(required = false) String newPassword,Model model) throws Exception {
		String result = "failure";
		
		result = this.sysPasswordBiz.passwordMod(oldPassword, newPassword);
		
		if("ok".equals(result)){
			ajaxResult.setMessage(AjaxResult.MSG_UPDATE);
			ajaxResult.setNavTabId(NAV_TAB_ID);
			ajaxResult.setCallbackType("");
			model.addAttribute(ajaxResult);
		}else{
			ajaxResult.setMessage("旧密码输入错误,请重新输入");
			ajaxResult.setNavTabId(NAV_TAB_ID);
			ajaxResult.setCallbackType("");
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			model.addAttribute(ajaxResult);
		}
		return "/common/ajaxResult";
	}
}