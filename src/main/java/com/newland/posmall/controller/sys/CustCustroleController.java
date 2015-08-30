package com.newland.posmall.controller.sys;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.entity.AjaxResult;
import org.ohuyo.rapid.base.entity.TcustCustrole;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.newland.posmall.base.service.TcustService;
import com.newland.posmall.bean.customer.Tcust;
import com.newland.posmall.biz.sys.CustCustroleBiz;
import com.newland.posmall.controller.BaseController;

@Scope("prototype")
@Controller("sys.custcustrole")
public class CustCustroleController extends BaseController {
	
	@Resource
	private CustCustroleBiz custCustroleBiz;
	
	@Resource
	private TcustService tcustService;
	
	private static final String NAV_TAB_ID = "KHJSGLGL";
	
	/**
	 * 
	* @Description:分页条件查询
	* @author chenwenjing    
	* @date 2014-8-30 下午12:04:01
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/sys/custcustrole/custCustroleList.do")
	public String custCustroleList(@RequestParam(required = false) String name,
			@RequestParam(required = false) String loginName,
			@RequestParam(required = false) Integer numPerPage,
			@RequestParam(required = false) Integer pageNum,Model model) {
		Page page = new Page();
		page.setCount(true);
		page.setCapacity(numPerPage == null ? CAPACITY : numPerPage);
		page.setPageOffset(pageNum == null ? PAGE_OFF_SET : pageNum - 1);
		
		
		List custCustroleList = this.custCustroleBiz.queryCustRole(page, loginName,name);
		model.addAttribute("custCustroleList",custCustroleList);
		model.addAttribute("loginName",loginName);
		model.addAttribute("name",name);
		
		model.addAttribute(NUM_PER_PAGE, page.getCapacity());
		model.addAttribute(PAGE_NUM, pageNum);
		model.addAttribute(TOTAL_COUNT, page.getRecAmt());
		return "/sys/custcustrole/custCustroleList";
	}
	
	/**
	 * 
	* @Description: 去添加页面
	* @author chenwenjing    
	* @date 2014-8-30 下午12:04:19
	 */
	@RequestMapping("/sys/custcustrole/custCustroleAdd.do")
	public String custCustroleAdd(Model model) {
		
        Map<String,Object> custCustroleMap = this.custCustroleBiz.queryUserAndRole();
		model.addAllAttributes(custCustroleMap);
		return "/sys/custcustrole/custCustroleAdd";
	}
	
	/**
	 * 
	* @Description: 添加客户与角色关联
	* @author chenwenjing    
	* @date 2014-8-26 下午8:27:16
	 */
	@RequestMapping("/sys/custcustrole/custCustroleToAdd.do")
	public String custRoleToAdd(Model model,@RequestParam(required = false) Long icust,@RequestParam(required = false) Long icustrole) {
		
		TcustCustrole tcustCustrole = new TcustCustrole();
		tcustCustrole.setIcust(icust);
		tcustCustrole.setIcustrole(icustrole);
		
		String result = this.custCustroleBiz.addCustRole(tcustCustrole);
		if("failure".equals(result)){
			ajaxResult.setMessage("添加失败，该客户已与该角色关联，请重新选择！");
			ajaxResult.setNavTabId(NAV_TAB_ID);
			ajaxResult.setCallbackType("");
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			model.addAttribute(ajaxResult);
		}else{
			ajaxResult.setMessage(AjaxResult.MSG_ADD);
			ajaxResult.setNavTabId(NAV_TAB_ID);
			ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
			model.addAttribute(ajaxResult);
		}
		
		
		return "/common/ajaxResult";
	}
	
	/**
	 * 
	* @Description: 删除客户与角色的关联
	* @author chenwenjing    
	* @date 2014-8-28 下午7:48:53
	 */
	@RequestMapping("/sys/custcustrole/custCustroleDel.do")
	public String custCustroleDel(Model model,@RequestParam(required = false) Long icust,@RequestParam(required = false) Long icustrole) {
		
		TcustCustrole tcustCustrole = new TcustCustrole();
		tcustCustrole.setIcust(icust);
		tcustCustrole.setIcustrole(icustrole);
		
		this.custCustroleBiz.delete(tcustCustrole);
		
		ajaxResult.setMessage(AjaxResult.MSG_DELETE);
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType("");
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
	
	/**
	 * 
	* @Description: 去修改页面
	* @author chenwenjing    
	* @date 2014-8-30 下午12:04:34
	 */
	@RequestMapping("/sys/custcustrole/custCustroleMod.do")
	public String custCustroleMod(Model model,@RequestParam(required = false) Long icust,@RequestParam(required = false) Long icustrole) {
		Tcust tcust = this.tcustService.find(icust);
        Map<String,Object> custCustroleMap = this.custCustroleBiz.queryUserAndRole();
		model.addAllAttributes(custCustroleMap);
		model.addAttribute("tcust",tcust);
		model.addAttribute("icustrole",icustrole);
		return "/sys/custcustrole/custCustroleMod";
	}
	/**
	 * 
	* @Description:修改客户与角色关联
	* @author chenwenjing    
	* @date 2014-8-28 下午9:03:55
	 */
	@RequestMapping("/sys/custcustrole/custCustroleToMod.do")
	public String custCustroleToMod(Model model,
			@RequestParam(required = false) Long icust,
			@RequestParam(required = false) Long icustrole,
			@RequestParam(required = false) Long preIcust,
			@RequestParam(required = false) Long preIcustrole) {
		
		TcustCustrole tcustCustrole = new TcustCustrole();
		tcustCustrole.setIcust(icust);
		tcustCustrole.setIcustrole(icustrole);
		
		String result = this.custCustroleBiz.updCustRole(tcustCustrole,preIcust,preIcustrole);
		if("failure".equals(result)){
			ajaxResult.setMessage("修改失败，该客户已与该角色关联，请重新选择！");
			ajaxResult.setNavTabId(NAV_TAB_ID);
			ajaxResult.setCallbackType("");
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			model.addAttribute(ajaxResult);
		}else{
			ajaxResult.setMessage(AjaxResult.MSG_UPDATE);
			ajaxResult.setNavTabId(NAV_TAB_ID);
			ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
			model.addAttribute(ajaxResult);
		}
		
		
		return "/common/ajaxResult";
	}
	
	
}
