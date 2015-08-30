package com.newland.posmall.controller.cust;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.ohuyo.rapid.base.TcustSession;
import org.ohuyo.rapid.base.entity.AjaxResult;
import org.ohuyo.rapid.base.service.FileUpDownService;
import org.ohuyo.rapid.base.servlet.AppSessionFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.code.kaptcha.Constants;
import com.newland.posmall.Application;
import com.newland.posmall.base.exception.BizException;
import com.newland.posmall.bean.customer.Tcust;
import com.newland.posmall.bean.customer.TcustReg;
import com.newland.posmall.biz.cust.CustomerBiz;
import com.newland.posmall.biz.cust.CustomerRegBiz;
import com.newland.posmall.controller.BaseController;

@Scope("prototype")
@Controller("cust.customer")
public class CustomerController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(CustomerController.class);
	
	@Resource
	private CustomerBiz customerBiz;	
	
	@Resource
	private CustomerRegBiz customerRegBiz;
	
	@Resource
	private FileUpDownService fileUpDownService;
	
	/**
	 * 跳转至注册页面
	 * @return
	 */
	@RequestMapping("/toRegister.do")
	public String toRegister() {
		return "/register_custom";
	}
	
	/**
	 * 注册信息校验（用户名、注册码等）
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/validateReg.do")
	public String validateReg(HttpServletRequest request, HttpServletResponse response) {
		Tcust tcust = new Tcust();
		tcust.setLoginName(request.getParameter("loginName"));
		tcust.setPassword(request.getParameter("password"));
		
		String validCode = request.getParameter("validCode");
		HttpSession session = request.getSession();
		String sCode = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
		
		String custCode = request.getParameter("custCode");
		
		String validateMsg = this.customerBiz.validateRegInfo(tcust,validCode,sCode, custCode);
		String rst = "";
		response.setCharacterEncoding("UTF-8");
		if(StringUtils.isNotBlank(validateMsg)) { //注册失败
			rst = "1-" + validateMsg;
		} else {
			rst = "0-成功";
		}
		try {
			response.getWriter().println(rst);
		} catch (IOException e) {
		}
		return null;
	}
	
	/**
	 * 客户注册
	 * @param tcust
	 * @param response
	 * @return
	 */
	@RequestMapping("/register.do")
	public String register(Tcust tcust, HttpServletResponse response) {
		try {
			this.customerBiz.registerCust(tcust);
		} catch (DecoderException e) {
			logger.error(e.getMessage(), e);
			response.setCharacterEncoding("UTF-8");
			try {
				response.getWriter().println(e.getMessage());
			} catch (IOException e1) {
			}
			return "/register_custom";
		} catch (BizException e) {
			logger.error(e.getMsg(), e);
			response.setCharacterEncoding("UTF-8");
			try {
				response.getWriter().println(e.getMsg());
			} catch (IOException e1) {
			}
			return "/register_custom";
		}
//		try {
//			response.sendRedirect("cust/login.do");
//		} catch (IOException e) {
//		}
		return "/result";
	}
	
	/**
	 * 跳转至客户信息页面
	 * @param icust
	 * @param model
	 * @return
	 */
	@RequestMapping("/cust/info/infoDetail.do")
	public String toModifyRegInfo(Model model) {
		TcustSession session = (TcustSession) AppSessionFilter.getAppSession();
		Tcust loginTcust = session.getTcust();
		TcustReg tcustReg = this.customerRegBiz.findByIcust(loginTcust.getIcust());
		session.setTcustReg(tcustReg);
		model.addAttribute("loginTcust", loginTcust);
		model.addAttribute("tcustReg", tcustReg);
		return "/cust/info/infoDetail";
	}
	
	/**
	 * 保存客户信息或者修改信息提交审核
	 * @param loginTcust
	 * @param tcustReg
	 * @param operType 操作标志：1为修改提交审核，否则为保存
	 * @param model
	 * @return
	 */
	@RequestMapping("/cust/info/saveOrSubmitRegInfo.do")
	public String saveOrSubmitRegInfo(Tcust loginTcust, TcustReg tcustReg,
			@DateTimeFormat(pattern = "yyyy-MM-dd") Date signatureDateForm,
			@RequestParam(required = false) String operType, Model model) {
		try {
			tcustReg.setSignatureDate(signatureDateForm);
			if("1".equals(operType)) { //提交审核
				this.customerRegBiz.submitTcustReg(loginTcust, tcustReg);
			}else { //修改信息
				this.customerRegBiz.modifyTcustReg(loginTcust, tcustReg);
			}
		} catch (BizException e) {
			logger.error(e.getMessage(), e);
			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setNavTabId("GSXXGL");
			ajaxResult.setCallbackType("");
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
		
		if("1".equals(operType)) { //提交审核			
			ajaxResult.setMessage("注册审核结果将以邮件形式发送至注册邮箱，请及时查收");
		}else {
			ajaxResult.setMessage("已保存");
		}
		ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
		ajaxResult.setCallbackType("");
		ajaxResult.setNavTabId("GSXXGL");
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
	
	/** 下载客户操作手册 */
	@RequestMapping("/cust/info/downOperationManual.do")
	public void downTempCsv(HttpServletResponse response){
		try{
			PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			org.springframework.core.io.Resource res = resolver.getResource("CustOperationManual.doc");
			if (res == null) throw new RuntimeException("");
			File file = File.createTempFile("tmp", ".doc", new File(Application.getTemplatePath()));
			FileOutputStream fout = new FileOutputStream(file);
			IOUtils.copy(res.getInputStream(), fout);
			fileUpDownService.download(file, response);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("-文件下载异常-", e);
		}
	}
}
