package com.newland.posmall.controller.admin;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.ohuyo.rapid.base.MenuNode;
import org.ohuyo.rapid.base.TuserSession;
import org.ohuyo.rapid.base.biz.AuthenticateBiz;
import org.ohuyo.rapid.base.biz.TuserBiz;
import org.ohuyo.rapid.base.entity.Tuser;
import org.ohuyo.rapid.base.service.TuserService;
import org.ohuyo.rapid.base.servlet.AppSessionFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.newland.posmall.bean.backmanage.TuserSub;
import com.newland.posmall.biz.sys.UserroleBiz;

@Controller("admin.index")
public class Index {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private AuthenticateBiz authenticateBiz;
	
	@Resource
	private UserroleBiz userroleBiz;

	@Resource
	private TuserService tuserService;
	
	@Resource
	private TuserBiz tuserBiz;

	@RequestMapping("/admin/login.do")
	public String login() {
		return "/admin/login";
	}
	
	/**
	 * 用户退出系统
	 * @return
	 */
	@RequestMapping("/admin/logout.do")
	public String logout() {
		TuserSession session = (TuserSession) AppSessionFilter.getAppSession();
		tuserBiz.log4Logout(session.getTuser());
		AppSessionFilter.setAppSession(null); //清空session
		return "/admin/login";
	}

	@RequestMapping("/admin/authenticate.do")
	public String authenticate(@RequestParam("loginName") String loginName,
			@RequestParam("password") String password,
			@RequestParam("validCode") String validCode, HttpServletRequest req, Model model) {
		if (StringUtils.isBlank(loginName)) {
			return "/admin/login";
		}
		Tuser tuser = tuserService.loadUserByLoginName(loginName);
		if (tuser == null) {
			model.addAttribute("errMsg", "用户名无效！");
			return "/admin/login";
		}
		TuserSession se = new TuserSession();
		try {
			authenticateBiz.authenticate(loginName, password, validCode, tuser, req,
					se);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			model.addAttribute("errMsg", e.getMessage());
			return "/admin/login";
		}
		TuserSub tuserSub = this.tuserService.getTuserSub(tuser.getIuser());
		tuser.setTuserSub(tuserSub);
		se.setTuser(tuser);
		se.setTuserSub(tuserSub);
		se.setAuth(userroleBiz.queryAllSysauth(),userroleBiz.queryUserAuthById(tuser.getIuser()));
		List<MenuNode> authList = se.getMenuNode().getSubNodes();
		model.addAttribute("authList", authList);
		
		return "redirect:/admin/index.do";
	}

	@RequestMapping("/admin/index.do")
	public String index(HttpServletRequest request) {
		TuserSession session = (TuserSession) AppSessionFilter.getAppSession();
		tuserBiz.log4Login(session.getTuser());
		
		return "/admin/index";
	}
}
