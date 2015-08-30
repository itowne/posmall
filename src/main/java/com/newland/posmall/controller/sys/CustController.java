package com.newland.posmall.controller.sys;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.entity.condition.TcustCondition;
import org.ohuyo.rapid.base.security.RsaService;
import org.ohuyo.rapid.base.service.PasswordService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.newland.posmall.bean.customer.Tcust;
import com.newland.posmall.biz.sys.CustBiz;
import com.newland.posmall.controller.BaseController;

@Scope("prototype")
@Controller("sys.cust")
public class CustController extends BaseController {

	@Resource
	private CustBiz custBiz;

	@Resource
	private PasswordService passwordService;
	
	@Resource
	private RsaService rsaService;
	
	private static final String NAV_TAB_ID = "KHXXGL";

	/**
	 * 查询列表
	 */
	@RequestMapping("/sys/cust/custList.do")
	public String custList(@RequestParam(required = false) String loginName,
			@RequestParam(required = false) Integer numPerPage,
			@RequestParam(required = false) Integer pageNum, Model model) {

		Page page = new Page();
		page.setCount(true);
		page.setCapacity(numPerPage == null ? CAPACITY : numPerPage);
		page.setPageOffset(pageNum == null ? PAGE_OFF_SET : pageNum - 1);

		TcustCondition tcustConfition = new TcustCondition();
		if (StringUtils.isNotBlank(loginName)) {
			tcustConfition.setLoginName(loginName);
		}

		List<Tcust> tcustList = this.custBiz.queryAllTcust(tcustConfition, page);
		
		model.addAttribute(tcustList);
		model.addAttribute(NUM_PER_PAGE, page.getCapacity());
		model.addAttribute(PAGE_NUM, pageNum);
		model.addAttribute(TOTAL_COUNT, page.getRecAmt());
		model.addAttribute("loginName", loginName);
		return "/sys/cust/custList";
	}

	/**
	 * 查询明细
	 */
	@RequestMapping("/sys/cust/custDetail.do")
	public String custDetail(@RequestParam(required = false) Long icust,
			Model model) {

		Tcust tcust = this.custBiz.queryTcustByIcust(icust);
		model.addAttribute(tcust);
		
		return "/sys/cust/custDetail";
	}
	/**
	 * 重置密码
	 */
	@RequestMapping("/sys/cust/resetPass.do")
	public String resetPass(@RequestParam(required = false) Long icust,
			Model model) {

		this.custBiz.resetPassByIcust(icust);
		ajaxResult.setMessage("重置密码成功【123456】");
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType("");
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
}
