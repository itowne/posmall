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
import org.ohuyo.rapid.base.entity.Tuserauth;
import org.ohuyo.rapid.base.entity.Tuserrole;
import org.ohuyo.rapid.base.entity.condition.TuserroleCondition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.newland.posmall.biz.sys.UserroleBiz;
import com.newland.posmall.controller.BaseController;

@Scope("prototype")
@Controller("sys.userrole")
public class UserroleController extends BaseController {
	
	@Resource
	private UserroleBiz roleBiz;
	
	private static final String NAV_TAB_ID = "HTRYJSGL";
	/**
	 * 
	* @Description: 角色分页查询
	* @author chenwenjing    
	* @date 2014-8-27 下午4:54:48
	 */
	@RequestMapping("/sys/userrole/userroleList.do")
	public String userroleList(@RequestParam(required = false) String name,
			@RequestParam(required = false) Integer numPerPage,
			@RequestParam(required = false) Integer pageNum,Model model) {
		
		Page page = new Page();
		page.setCount(true);
		page.setCapacity(numPerPage == null ? CAPACITY : numPerPage);
		page.setPageOffset(pageNum == null ? PAGE_OFF_SET : pageNum - 1);
		
		TuserroleCondition roleCondition = new TuserroleCondition();
		if (StringUtils.isNotBlank(name)) {
			roleCondition.setName(name);
		}
		
		List<Tuserrole> roleList = this.roleBiz.queryAllTrole(roleCondition, page);
		model.addAttribute("roleList",roleList);
		model.addAttribute(NUM_PER_PAGE, page.getCapacity());
		model.addAttribute(PAGE_NUM, pageNum);
		model.addAttribute(TOTAL_COUNT, page.getRecAmt());
		model.addAttribute("name",name);
		return "/sys/userrole/userroleList";
	}
	
	/**
	 * 
	* @Description:去角色添加页面
	* @author chenwenjing    
	* @date 2014-8-27 下午4:55:34
	 */
	@RequestMapping("/sys/userrole/userroleAdd.do")
	public String userroleAdd(Model model) {
		MenuNode mn = this.roleBiz.list();
		Collection<MenuNode> mnColl = mn.getSubMenus().values();
		
		model.addAttribute("mn", mn);
		model.addAttribute("mnColl", mnColl);
		return "/sys/userrole/userroleAdd";
	}
	
	@RequestMapping("/sys/userrole/userroleToAdd.do")
	public String userroleToAdd(Model model,
			  Tuserrole trole, HttpServletRequest request) {
		String[] auth = request.getParameterValues("auth");
		Set<Tuserauth> authSet = new HashSet<Tuserauth>();
		if (auth != null) {
			for (String authCode : auth) {
				Tuserauth newAuth = this.roleBiz.querySysauthByCode(authCode);
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
	* @Title: roleDel    
	* @Description: 删除角色
	* @param irole
	* @param model
	* @return
	* @author chenwenjing    
	* @date 2014-8-24 下午9:22:54
	 */
	@RequestMapping("/sys/userrole/userroleDel.do")
	public String userroleDel(@RequestParam(required = false) Long iuserrole,Model model) throws Exception{
		
		String result = this.roleBiz.DelRoleByIrole(iuserrole);
		
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
	@RequestMapping("/sys/userrole/userroleMod.do")
	public String roleModify(@RequestParam(required = false) Long iuserrole,Model model) {
		
		MenuNode mn = this.roleBiz.list();
		Collection<MenuNode> mnColl =  mn.getSubMenus().values();

		Tuserrole role = this.roleBiz.QueryRoleByIrole(iuserrole);
		List<String> authCodeList = new ArrayList<String>();
		for (Tuserauth auth : role.getAuthSet()) {
			authCodeList.add(auth.getCode());
		}

		model.addAttribute("mn",mn);
		model.addAttribute("mnColl",mnColl);
		model.addAttribute("authCodeList",authCodeList);
		model.addAttribute("role",role);
		
		return "/sys/userrole/userroleMod";
	}
	
	/**
	 * 
	* @Description: 修改角色
	* @author chenwenjing    
	* @date 2014-8-24 下午10:28:57
	 */
	@RequestMapping("/sys/userrole/userroleToMod.do")
	public String roleToModify(Tuserrole tuserrole, HttpServletRequest request,
			Model model) throws Exception{
		String[] auth = request.getParameterValues("auth");
		Set<Tuserauth> authSet = new HashSet<Tuserauth>();
		if (auth != null) {
			for (String authCode : auth) {
				Tuserauth newAuth = this.roleBiz.querySysauthByCode(authCode);
				authSet.add(newAuth);
			}
		}
		tuserrole.setAuthSet(authSet);
		
		this.roleBiz.ModifyRole(tuserrole);
		
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
	@RequestMapping("/sys/userrole/userroleDetail.do")
	public String roleDetail(@RequestParam(required = false) Long iuserrole,Model model) {
		
		MenuNode mn = this.roleBiz.list();
		Collection<MenuNode> mnColl =  mn.getSubMenus().values();

		Tuserrole role = this.roleBiz.QueryRoleByIrole(iuserrole);
		List<String> authCodeList = new ArrayList<String>();
		for (Tuserauth auth : role.getAuthSet()) {
			authCodeList.add(auth.getCode());
		}

		model.addAttribute("mn",mn);
		model.addAttribute("mnColl",mnColl);
		model.addAttribute("authCodeList",authCodeList);
		model.addAttribute("role",role);
		return "/sys/userrole/userroleDetail";
	}
	

}
