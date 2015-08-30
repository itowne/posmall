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
import org.ohuyo.rapid.base.entity.Tsysauth;
import org.ohuyo.rapid.base.entity.Tsysrole;
import org.ohuyo.rapid.base.entity.condition.TsysroleCondition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.newland.posmall.biz.sys.SysroleBiz;
import com.newland.posmall.controller.BaseController;

@Scope("prototype")
@Controller("sys.sysrole")
public class SysroleController extends BaseController {
	
	@Resource
	private SysroleBiz roleBiz;
	
	private static final String NAV_TAB_ID = "GLYJSGL";
	/**
	 * 
	* @Description: 角色分页查询
	* @author chenwenjing    
	* @date 2014-8-27 下午4:54:48
	 */
	@RequestMapping("/sys/sysrole/sysroleList.do")
	public String sysroleList(@RequestParam(required = false) String name,
			@RequestParam(required = false) Integer numPerPage,
			@RequestParam(required = false) Integer pageNum,Model model) {
		
		Page page = new Page();
		page.setCount(true);
		page.setCapacity(numPerPage == null ? CAPACITY : numPerPage);
		page.setPageOffset(pageNum == null ? PAGE_OFF_SET : pageNum - 1);
		
		TsysroleCondition roleCondition = new TsysroleCondition();
		if (StringUtils.isNotBlank(name)) {
			roleCondition.setName(name);
		}
		
		List<Tsysrole> roleList = this.roleBiz.queryAllTrole(roleCondition, page);
		model.addAttribute("roleList",roleList);
		model.addAttribute(NUM_PER_PAGE, page.getCapacity());
		model.addAttribute(PAGE_NUM, pageNum);
		model.addAttribute(TOTAL_COUNT, page.getRecAmt());
		model.addAttribute("name",name);
		return "/sys/sysrole/sysroleList";
	}
	
	/**
	 * 
	* @Description:去角色添加页面
	* @author chenwenjing    
	* @date 2014-8-27 下午4:55:34
	 */
	@RequestMapping("/sys/sysrole/sysroleAdd.do")
	public String sysroleAdd(Model model) {
		
		MenuNode mn = this.roleBiz.list();
		Collection<MenuNode> mnColl = mn.getSubMenus().values();
		
		model.addAttribute("mn", mn);
		model.addAttribute("mnColl", mnColl);
		return "/sys/sysrole/sysroleAdd";
	}
	
	@RequestMapping("/sys/sysrole/sysroleToAdd.do")
	public String sysroleToAdd(Model model,
		  Tsysrole tsysrole, HttpServletRequest request) {
		String[] auth = request.getParameterValues("auth");
		Set<Tsysauth> authSet = new HashSet<Tsysauth>();
		if (auth != null) {
			for (String authCode : auth) {
				Tsysauth newAuth = this.roleBiz.querySysauthByCode(authCode);
				authSet.add(newAuth);
			}
		}
		tsysrole.setAuthSet(authSet);
		this.roleBiz.AddRole(tsysrole);

		
		ajaxResult.setMessage(AjaxResult.MSG_ADD);
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
	
	/**
	 * 
	* @Title: roleDel    
	* @Description: 删除角色
	* @param irole
	* @param model
	* @return
	* @author chenwenjing    
	* @date 2014-8-24 下午9:22:54
	 */
	@RequestMapping("/sys/sysrole/sysroleDel.do")
	public String userroleDel(@RequestParam(required = false) Long isysrole,Model model) throws Exception{
		
		String result = this.roleBiz.DelRoleByIrole(isysrole);
		
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
	* @Title: roleModify    
	* @Description: 去修改页面
	* @param irole
	* @param model
	* @return
	* @author chenwenjing    
	* @date 2014-8-24 下午9:43:16
	 */
	@RequestMapping("/sys/sysrole/sysroleMod.do")
	public String roleModify(@RequestParam(required = false) Long isysrole,Model model) {
		
		
		MenuNode mn = this.roleBiz.list();
		Collection<MenuNode> mnColl =  mn.getSubMenus().values();

		Tsysrole role = this.roleBiz.QueryRoleByIrole(isysrole);
		List<String> authCodeList = new ArrayList<String>();
		for (Tsysauth auth : role.getAuthSet()) {
			authCodeList.add(auth.getCode());
		}

		model.addAttribute("mn",mn);
		model.addAttribute("mnColl",mnColl);
		model.addAttribute("authCodeList",authCodeList);
		model.addAttribute("role",role);
		
		return "/sys/sysrole/sysroleMod";
	}
	
	/**
	 * 
	* @Description: 修改角色
	* @author chenwenjing    
	* @date 2014-8-24 下午10:28:57
	 */
	@RequestMapping("/sys/sysrole/sysroleToMod.do")
	public String roleToModify(Tsysrole tsysrole, HttpServletRequest request
			,Model model) throws Exception{
		String[] auth = request.getParameterValues("auth");
		Set<Tsysauth> authSet = new HashSet<Tsysauth>();
		if (auth != null) {
			for (String authCode : auth) {
				Tsysauth newAuth = this.roleBiz.querySysauthByCode(authCode);
				authSet.add(newAuth);
			}
		}
		tsysrole.setAuthSet(authSet);
		
		this.roleBiz.ModifyRole(tsysrole);

		ajaxResult.setMessage(AjaxResult.MSG_UPDATE);
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
	
	/**
	 * 
	* @Title: roleDetail    
	* @Description: 角色详情
	* @param irole
	* @param model
	* @return
	* @author chenwenjing    
	* @date 2014-8-24 下午10:52:21
	 */
	@RequestMapping("/sys/sysrole/sysroleDetail.do")
	public String roleDetail(@RequestParam(required = false) Long isysrole,Model model) {
		MenuNode mn = this.roleBiz.list();
		Collection<MenuNode> mnColl =  mn.getSubMenus().values();

		Tsysrole role = this.roleBiz.QueryRoleByIrole(isysrole);
		List<String> authCodeList = new ArrayList<String>();
		for (Tsysauth auth : role.getAuthSet()) {
			authCodeList.add(auth.getCode());
		}

		model.addAttribute("mn",mn);
		model.addAttribute("mnColl",mnColl);
		model.addAttribute("authCodeList",authCodeList);
		model.addAttribute("role",role);
		return "/sys/sysrole/sysroleDetail";
	}
	

}
