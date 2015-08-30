package com.newland.posmall.controller.admin;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.TuserSession;
import org.ohuyo.rapid.base.entity.AjaxResult;
import org.ohuyo.rapid.base.servlet.AppSessionFilter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.newland.posmall.base.exception.BizException;
import com.newland.posmall.bean.customer.TcustAppver;
import com.newland.posmall.biz.admin.CustAppverBiz;
import com.newland.posmall.controller.BaseController;

@Scope("prototype")
@Controller("admin.custappver")
public class CustAppverController extends BaseController{
	
	private static final String NAV_TAB_ID = "YYBBGL";
	
	@Resource
	private CustAppverBiz custAppverBiz;

	/**
	 * 
	 * @Description: TODO分页查询
	 * @author chenwenjing
	 * @date 2013-11-5下午2:19:58
	 */
	@RequestMapping("/admin/custappver/custAppverList.do")
	public String custAppverList(TcustAppver tcustAppver,
			@RequestParam(required = false) Integer numPerPage,
			@RequestParam(required = false) Integer pageNum,Model model) {
		
		Page page = new Page();
		page.setCount(true);
		page.setCapacity(numPerPage == null ? CAPACITY : numPerPage);
		page.setPageOffset(pageNum == null ? PAGE_OFF_SET : pageNum - 1);
		
		List<TcustAppver> list = this.custAppverBiz.find(tcustAppver, page);
		
		model.addAttribute("list",list);
		model.addAttribute(NUM_PER_PAGE, page.getCapacity());
		model.addAttribute(PAGE_NUM, pageNum);
		model.addAttribute(TOTAL_COUNT, page.getRecAmt());
		return "/admin/custappver/custAppverList";
	}
    /**
     * 
     * @Description: 根据编号删除
     * @author chenwenjing
     * @date 2013-11-5下午2:20:18
     */
	@RequestMapping("/admin/custappver/custAppverDel.do")
	public String custAppverDel(@RequestParam(required = false) Long icustAppver, Model model) {
		TuserSession tuserSession = (TuserSession) AppSessionFilter.getAppSession();
        
		try {
			this.custAppverBiz.custAppverDel(tuserSession.getTuser(), icustAppver);
		} catch (BizException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setCallbackType("");
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
		
		ajaxResult.setMessage("删除成功");
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType("");
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
	/**
	 * 
	 * @Description: TODO查看明细
	 * @author chenwenjing
	 * @date 2013-11-5下午2:35:07
	 */
	@RequestMapping("/admin/custappver/custAppverDetail.do")
	public String custAppverDetail(@RequestParam(required = false)Long icustAppver, Model model) {

		TcustAppver custAppver = custAppverBiz.getCustAppverDetail(icustAppver);
		model.addAttribute("custAppver", custAppver);
		return "/admin/custappver/custAppverDetail";
	}
	/**
	 * 
	 * @Description: TODO前往添加页面
	 * @author chenwenjing
	 * @date 2013-11-5下午2:42:58
	 */
	@RequestMapping("/admin/custappver/toCustAppverAdd.do")
	public String toCustAppverAdd(Model model) {

		return "/admin/custappver/custAppverAdd";
	}
	/**
	 * 
	 * @Description: TODO添加应用版本
	 * @author chenwenjing
	 * @date 2013-11-5下午3:12:08
	 */
	@RequestMapping("/admin/custappver/custAppverAdd.do")
	public String custAppverAdd(TcustAppver tcustAppver,Model model) {
		
		TuserSession tuserSession = (TuserSession) AppSessionFilter.getAppSession();
		try {
			this.custAppverBiz.addCustAppver(tuserSession.getTuser(), tcustAppver);
		} catch (BizException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setCallbackType("");
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
        
		ajaxResult.setMessage(AjaxResult.MSG_ADD);
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
	/**
	 * 
	 * @Description: TODO跳转到更新页面
	 * @author chenwenjing
	 * @date 2013-11-5下午3:15:26
	 */
	@RequestMapping("/admin/custappver/toCustAppverMod.do")
	public String toCustAppverMod(@RequestParam(required = false)Long icustAppver,Model model) {
		TcustAppver tcustAppver = this.custAppverBiz.getCustAppverDetail(icustAppver);
		
		model.addAttribute("tcustAppver",tcustAppver);
		return "/admin/custappver/custAppverMod";
	}
	@RequestMapping("/admin/custappver/custAppverMod.do")
	public String custAppverMod(TcustAppver tcustAppver,Model model) {
		
		TuserSession tuserSession = (TuserSession) AppSessionFilter.getAppSession();
		
		try {
			this.custAppverBiz.updateTcustAppver(tcustAppver, tuserSession.getTuser());
		} catch (BizException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setCallbackType("");
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
        
		ajaxResult.setMessage(AjaxResult.MSG_ADD);
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}

}
