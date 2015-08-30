package com.newland.posmall.controller.sys;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.ohuyo.rapid.base.MenuNode;
import org.ohuyo.rapid.base.TsysSession;
import org.ohuyo.rapid.base.biz.AuthenticateBiz;
import org.ohuyo.rapid.base.biz.TuserBiz;
import org.ohuyo.rapid.base.entity.Tsys;
import org.ohuyo.rapid.base.service.TsysService;
import org.ohuyo.rapid.base.servlet.AppSessionFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.newland.posmall.biz.sys.SysroleBiz;

@Controller("sys.index")
public class Index {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private AuthenticateBiz authenticateBiz;

	@Resource
	private SysroleBiz sysroleBiz;

	@Resource
	TsysService tsysService;
	
	@Resource
	private TuserBiz tuserBiz;

	@RequestMapping("/sys/login.do")
	public String login() {
		return "/sys/login";
	}
	
	/**
	 * 用户退出系统
	 * @return
	 */
	@RequestMapping("/sys/logout.do")
	public String logout() {
		TsysSession session = (TsysSession) AppSessionFilter.getAppSession();
		tuserBiz.log4Logout(session.getTsys());
		AppSessionFilter.setAppSession(null); //清空session
		return "/sys/login";
	}

	@RequestMapping("/sys/authenticate.do")
	public String authenticate(@RequestParam("loginName") String loginName,
			@RequestParam("password") String password,
			@RequestParam("validCode") String validCode, HttpServletRequest req, Model model) {
		if (StringUtils.isBlank(loginName)) {
			return "/sys/login";
		}
		Tsys tsys = tsysService.getByLoginName(loginName);
		if (tsys == null) {
			model.addAttribute("errMsg", "用户名无效！");
			return "/sys/login";
		}
		TsysSession se = new TsysSession();
		try {
			authenticateBiz.authenticate(loginName, password, validCode, tsys, req,
					se);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			model.addAttribute("errMsg", e.getMessage());
			return "/sys/login";
		}
		se.setTsys(tsys);
		
		se.setAuth(sysroleBiz.queryAllSysauth(),sysroleBiz.querySysAuthById(tsys.getIsys()));
		List<MenuNode> authList = se.getMenuNode().getSubNodes();
		model.addAttribute("authList", authList);
		return "redirect:/sys/index.do";
	}
	
	@RequestMapping("/sys/index.do")
	public String index(HttpServletRequest request) {
		TsysSession session = (TsysSession) AppSessionFilter.getAppSession();
		tuserBiz.log4Login(session.getTsys());
		
		return "/sys/index";
	}
}
