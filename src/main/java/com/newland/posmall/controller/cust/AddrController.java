package com.newland.posmall.controller.cust;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ohuyo.commons.format.json.JSONObject;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.TcustSession;
import org.ohuyo.rapid.base.entity.AjaxResult;
import org.ohuyo.rapid.base.servlet.AppSessionFilter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.newland.posmall.base.exception.BizException;
import com.newland.posmall.base.service.ProvinceService;
import com.newland.posmall.bean.customer.Taddr;
import com.newland.posmall.biz.cust.AddrBiz;
import com.newland.posmall.controller.BaseController;

/**
 * 
 * @author zhouym
 *
 */
@Scope("prototype")
@Controller("cust.addr")
public class AddrController extends BaseController {
	
	private static final String NAVTABID = "CYSHDZGL";
	
	@Resource
	private AddrBiz addrBiz;
	
	@Resource
	private ProvinceService provinceService;

	/**
	 * 收货地址列表
	 * @param name
	 * @param pageNum
	 * @param numPerPage
	 * @param model
	 * @return
	 */
	@RequestMapping("/cust/addr/addrList.do")
	public String addrList(@RequestParam(required = false) String name, Integer pageNum, Integer numPerPage, Model model) {
		Page page = new Page();
		page.setCount(true);
		page.setCapacity(numPerPage == null ? CAPACITY : numPerPage);
		page.setPageOffset(pageNum == null ? PAGE_OFF_SET : pageNum - 1);
		
		TcustSession session = (TcustSession) AppSessionFilter.getAppSession();
		List<Taddr> taddrList = this.addrBiz.queryTaddrPageByCondition(page, session.getTcust(), name);
		if(taddrList != null && taddrList.size() >= 1) {
			for (Taddr taddr : taddrList) {
				taddr.setProvince(this.provinceService.getProvinceMap().get(taddr.getProvince()));
				taddr.setCity(this.provinceService.getProvinceMap().get(taddr.getCity()));
				taddr.setCounty(this.provinceService.getProvinceMap().get(taddr.getCounty()));
			}
		}
		model.addAttribute("taddrList", taddrList);
		model.addAttribute("name", name);
		
		model.addAttribute(NUM_PER_PAGE, page.getCapacity());
		model.addAttribute(PAGE_NUM, pageNum);
		model.addAttribute(TOTAL_COUNT, page.getRecAmt());
		return "/cust/addr/addrList";
	}
	
	/**
	 * 收货地址列表
	 * 查找带回
	 */
	@RequestMapping("/cust/addr/addrLookupSingle.do")
	public String addrLookupSingle(Integer pageNum, Integer numPerPage, Model model) {
		Page page = new Page();
		page.setCount(true);
		page.setCapacity(numPerPage == null ? CAPACITY : numPerPage);
		page.setPageOffset(pageNum == null ? PAGE_OFF_SET : pageNum - 1);
		
		TcustSession session = (TcustSession) AppSessionFilter.getAppSession();
		List<Taddr> taddrList = this.addrBiz.queryTaddrPageByCondition(page, session.getTcust(), null);
		if(taddrList != null && taddrList.size() >= 1) {
			for (Taddr taddr : taddrList) {
				taddr.setProvince(this.provinceService.getProvinceMap().get(taddr.getProvince()));
				taddr.setCity(this.provinceService.getProvinceMap().get(taddr.getCity()));
				taddr.setCounty(this.provinceService.getProvinceMap().get(taddr.getCounty()));
			}
		}
		model.addAttribute("taddrList", taddrList);
		
		model.addAttribute(NUM_PER_PAGE, page.getCapacity());
		model.addAttribute(PAGE_NUM, pageNum);
		model.addAttribute(TOTAL_COUNT, page.getRecAmt());
		return "/cust/addr/addrLookupSingle";
	}
	
	
	/**
	 * 跳转至新增页面
	 * @return
	 */
	@RequestMapping("/cust/addr/toAdd.do")
	public String toAdd(@RequestParam(required = false) Long ilogisticsOrd,@RequestParam(required = false) String specifyDelivery, Model model) {
		model.addAttribute("ilogisticsOrd", ilogisticsOrd);
		model.addAttribute("specifyDelivery", specifyDelivery);
		return "/cust/addr/addrAdd";
	}
	
	/**
	 * 新增收货地址
	 * @param taddr
	 * @param model
	 * @return
	 */
	@RequestMapping("/cust/addr/add.do")
	public String add(Taddr taddr, @RequestParam(required = false) Long ilogisticsOrd,HttpServletRequest req,Model model,
			@RequestParam(required = false) String specifyDelivery) {
		TcustSession session = (TcustSession) AppSessionFilter.getAppSession();
		try {
			this.addrBiz.addTaddr(session.getTcust(), taddr);
		} catch (BizException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setCallbackType("");
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
		if(ilogisticsOrd != null){
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
			ajaxResult.setMessage("");

			ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_FORWARD);
			StringBuffer forwordUrlSb = new StringBuffer();
			forwordUrlSb.append(req.getContextPath());
			forwordUrlSb.append("/cust/logistics/toAddStep3Forword.do");
			forwordUrlSb.append("?ilogisticsOrd=");
			forwordUrlSb.append(ilogisticsOrd);
			forwordUrlSb.append("&specifyDelivery=");
			forwordUrlSb.append(specifyDelivery);

			ajaxResult.setForwardUrl(forwordUrlSb.toString());
			ajaxResult.setRel("CZWLXXGL_XZ");
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
		ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
		ajaxResult.setMessage(AjaxResult.MSG_OK);
		ajaxResult.setNavTabId(NAVTABID);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
	
	/**
	 * 跳转至修改页面
	 * @return
	 */
	@RequestMapping("/cust/addr/toModify.do")
	public String toModify(Long iaddr, Model model) {
		Taddr taddr = this.addrBiz.queryTaddrById(iaddr);
		model.addAttribute("taddr", taddr);
		return "/cust/addr/addrModify";
	}
	
	/**
	 * 修改收货地址
	 * @param taddr
	 * @param model
	 * @return
	 */
	@RequestMapping("/cust/addr/modify.do")
	public String modify(Taddr taddr, Model model) {
		TcustSession session = (TcustSession) AppSessionFilter.getAppSession();
		try {
			this.addrBiz.modifyTaddr(session.getTcust(), taddr);
		} catch (BizException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setCallbackType("");
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
		ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
		ajaxResult.setMessage(AjaxResult.MSG_OK);
		ajaxResult.setNavTabId(NAVTABID);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
	
	/**
	 * 删除收货地址
	 * @param taddr
	 * @param model
	 * @return
	 */
	@RequestMapping("/cust/addr/delete.do")
	public String delete(Long id, Model model) {
		this.addrBiz.deleteTaddr(id);
		ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
		ajaxResult.setMessage(AjaxResult.MSG_OK);
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
	
	/**
	 * 获取全国省份
	 * @param response
	 */
	@RequestMapping(value = "/cust/addr/getProvince.do", method = RequestMethod.POST)
	public void getProvince(HttpServletResponse response) {
		Map<String, String> provinceMap = this.provinceService.getProvince();
		Object rst = JSONObject.wrap(provinceMap);
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().println(rst);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取市、县
	 * @param provCode
	 * @param response
	 */
	@RequestMapping(value = "/cust/addr/getCity.do", method = RequestMethod.POST)
	public void getCity(String provCode, HttpServletResponse response) {
		Map<String, String> cityMap = this.provinceService.getProvince(provCode);
		Object rst = JSONObject.wrap(cityMap);
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().println(rst);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
