package com.newland.posmall.controller.sys;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.MenuNode;
import org.ohuyo.rapid.base.entity.AjaxResult;
import org.ohuyo.rapid.base.entity.Tcustauth;
import org.ohuyo.rapid.base.entity.Tcustrole;
import org.ohuyo.rapid.base.entity.condition.TcustroleCondition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.newland.posmall.biz.sys.CustroleBiz;
import com.newland.posmall.controller.BaseController;

@Scope("prototype")
@Controller("sys.custrole")
public class CustroleController extends BaseController {
	
	@Resource
	private CustroleBiz roleBiz;
	
	private static final String NAV_TAB_ID = "KHJSGL";
	/**
	 * 
	* @Description: 角色分页查询
	* @author chenwenjing    
	* @date 2014-8-27 下午4:54:48
	 */
	@RequestMapping("/sys/custrole/custroleList.do")
	public String custroleList(@RequestParam(required = false) String name,
			@RequestParam(required = false) Integer numPerPage,
			@RequestParam(required = false) Integer pageNum,Model model) {
		
		Page page = new Page();
		page.setCount(true);
		page.setCapacity(numPerPage == null ? CAPACITY : numPerPage);
		page.setPageOffset(pageNum == null ? PAGE_OFF_SET : pageNum - 1);
		
		TcustroleCondition roleCondition = new TcustroleCondition();
		if (StringUtils.isNotBlank(name)) {
			roleCondition.setName(name);
		}
		
		List<Tcustrole> roleList = this.roleBiz.queryAllTrole(roleCondition, page);
		model.addAttribute("roleList",roleList);
		model.addAttribute(NUM_PER_PAGE, page.getCapacity());
		model.addAttribute(PAGE_NUM, pageNum);
		model.addAttribute(TOTAL_COUNT, page.getRecAmt());
		model.addAttribute("name",name);
		return "/sys/custrole/custroleList";
	}
	
	/**
	 * 
	* @Description:去角色添加页面
	* @author chenwenjing    
	* @date 2014-8-27 下午4:55:34
	 */
	@RequestMapping("/sys/custrole/custroleAdd.do")
	public String custroleAdd(Model model) {
		MenuNode mn = this.roleBiz.list();
		Collection<MenuNode> mnColl = mn.getSubMenus().values();
		
		model.addAttribute("mn", mn);
		model.addAttribute("mnColl", mnColl);
		return "/sys/custrole/custroleAdd";
	}
	
	@RequestMapping("/sys/custrole/custroleToAdd.do")
	public String custroleToAdd(Model model,
			  Tcustrole trole ,HttpServletRequest request) {
		String[] auth = request.getParameterValues("auth");
		Set<Tcustauth> authSet = new HashSet<Tcustauth>();
		if (auth != null) {
			for (String authCode : auth) {
				Tcustauth newAuth = this.roleBiz.querySysauthByCode(authCode);
				authSet.add(newAuth);
			}
		}
		trole.setAuthSet(authSet);
		this.roleBiz.AddRole(trole);
		
		ajaxResult.setMessage(AjaxResult.MSG_ADD);
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
	
	/**
	 * 
	* @Description: 删除角色
	* @author chenwenjing    
	* @date 2014-8-24 下午9:22:54
	 */
	@RequestMapping("/sys/custrole/custroleDel.do")
	public String roleDel(@RequestParam(required = false) Long icustrole,Model model) throws Exception{
		
		String result = this.roleBiz.DelRoleByIrole(icustrole);
		
		if("failure".equals(result)){
			ajaxResult.setMessage("此角色已关联，请先删除关联！");
			ajaxResult.setNavTabId(NAV_TAB_ID);
			ajaxResult.setCallbackType("");
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
		}else{
			ajaxResult.setMessage(AjaxResult.MSG_DELETE);
			ajaxResult.setNavTabId(NAV_TAB_ID);
			ajaxResult.setCallbackType("");
		}
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
	
	/**
	 * 
	* @Description: 去修改页面
	* @author chenwenjing    
	* @date 2014-8-24 下午9:43:16
	 */
	@RequestMapping("/sys/custrole/custroleMod.do")
	public String roleModify(@RequestParam(required = false) Long icustrole,Model model) {
		
		MenuNode mn = this.roleBiz.list();
		Collection<MenuNode> mnColl =  mn.getSubMenus().values();

		Tcustrole role = this.roleBiz.QueryRoleByIrole(icustrole);
		List<String> authCodeList = new ArrayList<String>();
		for (Tcustauth auth : role.getAuthSet()) {
			authCodeList.add(auth.getCode());
		}

		model.addAttribute("mn",mn);
		model.addAttribute("mnColl",mnColl);
		model.addAttribute("authCodeList",authCodeList);
		model.addAttribute("role",role);
		
		return "/sys/custrole/custroleMod";
	}
	
	/**
	 * 
	* @Description: 修改角色
	* @author chenwenjing    
	* @date 2014-8-24 下午10:28:57
	 */
	@RequestMapping("/sys/custrole/custroleToMod.do")
	public String roleToModify(Tcustrole tcustrole, HttpServletRequest request,
			Model model) throws Exception{
		String[] auth = request.getParameterValues("auth");
		Set<Tcustauth> authSet = new HashSet<Tcustauth>();
		if (auth != null) {
			for (String authCode : auth) {
				Tcustauth newAuth = this.roleBiz.querySysauthByCode(authCode);
				authSet.add(newAuth);
			}
		}
		tcustrole.setAuthSet(authSet);
		
		this.roleBiz.ModifyRole(tcustrole);
		
		ajaxResult.setMessage(AjaxResult.MSG_UPDATE);
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
	
	/**
	 * 
	* @Description: 角色详情
	* @author chenwenjing    
	* @date 2014-8-24 下午10:52:21
	 */
	@RequestMapping("/sys/custrole/custroleDetail.do")
	public String roleDetail(@RequestParam(required = false) Long icustrole,Model model) {
		MenuNode mn = this.roleBiz.list();
		Collection<MenuNode> mnColl =  mn.getSubMenus().values();

		Tcustrole role = this.roleBiz.QueryRoleByIrole(icustrole);
		List<String> authCodeList = new ArrayList<String>();
		for (Tcustauth auth : role.getAuthSet()) {
			authCodeList.add(auth.getCode());
		}

		model.addAttribute("mn",mn);
		model.addAttribute("mnColl",mnColl);
		model.addAttribute("authCodeList",authCodeList);
		model.addAttribute("role",role);
		return "/sys/custrole/custroleDetail";
	}
	

}
