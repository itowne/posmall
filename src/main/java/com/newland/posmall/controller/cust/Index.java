package com.newland.posmall.controller.cust;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.ohuyo.rapid.base.MenuNode;
import org.ohuyo.rapid.base.TcustSession;
import org.ohuyo.rapid.base.biz.AuthenticateBiz;
import org.ohuyo.rapid.base.biz.TuserBiz;
import org.ohuyo.rapid.base.servlet.AppSessionFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.newland.posmall.base.service.TcustService;
import com.newland.posmall.bean.customer.Tcust;
import com.newland.posmall.bean.customer.TcustReg;
import com.newland.posmall.biz.sys.CustroleBiz;

@Scope("prototype")
@Controller("cust.index")
public class Index {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private AuthenticateBiz authenticateBiz;
	
	@Resource
	private TcustService tcustService;

	@Resource
	private CustroleBiz custroleBiz;
	
	@Resource
	private TuserBiz tuserBiz;

	@RequestMapping("/cust/login.do")
	public String login() {
		return "/cust/login";
	}
	
	/**
	 * 用户退出系统
	 * @return
	 */
	@RequestMapping("/cust/logout.do")
	public String logout() {
		TcustSession session = (TcustSession) AppSessionFilter.getAppSession();
		tuserBiz.log4Logout(session.getTcust());
		AppSessionFilter.setAppSession(null); //清空session
		return "/cust/login";
	}

	@RequestMapping("/cust/authenticate.do")
	public String authenticate(@RequestParam("loginName") String loginName,
			@RequestParam("password") String password,
			@RequestParam("validCode") String validCode,
			HttpServletRequest req, Model model) {
		if (StringUtils.isBlank(loginName)) {
			return "/cust/login";
		}
		Tcust tcust = tcustService.getByLoginName(loginName);
		if (tcust == null) {
			model.addAttribute("errMsg", "请先注册！");
			return "/cust/login";
		}
		TcustSession se = new TcustSession();
		try {
			authenticateBiz.authenticate(loginName, password, validCode, tcust,
					req, se);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			model.addAttribute("errMsg", e.getMessage());
			return "/cust/login";
		}
		TcustReg tcustReg = this.tcustService.getTcustReg(tcust.getIcust());
		tcust.setTcustReg(tcustReg);
		se.setTcust(tcust);
		se.setTcustReg(tcustReg);
        
		se.setAuth(custroleBiz.queryAllSysauth(),
				custroleBiz.queryCustAuthById(tcust.getIcust()));
		List<MenuNode> authList = se.getMenuNode().getSubNodes();
		model.addAttribute("authList", authList);
		return "redirect:/cust/index.do";
	}

	@RequestMapping("/cust/index.do")
	public String index(HttpServletRequest request) {
		TcustSession session = (TcustSession) AppSessionFilter.getAppSession();
		tuserBiz.log4Login(session.getTcust());
		return "/cust/index";
	}
}
