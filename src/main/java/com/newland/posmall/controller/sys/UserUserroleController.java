package com.newland.posmall.controller.sys;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.entity.AjaxResult;
import org.ohuyo.rapid.base.entity.Tuser;
import org.ohuyo.rapid.base.entity.TuserUserrole;
import org.ohuyo.rapid.base.service.TuserService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.newland.posmall.biz.sys.UserUserroleBiz;
import com.newland.posmall.controller.BaseController;

@Scope("prototype")
@Controller("sys.useruserrole")
public class UserUserroleController extends BaseController {
	
	private static final String NAV_TAB_ID = "HTRYJSGLGL";

	@Resource
	private UserUserroleBiz userUserroleBiz;
	
	@Resource
	private TuserService tuserService;
	
	/**
	 * 
	* @Description: 分页条件查询
	* @author chenwenjing    
	* @date 2014-8-30 下午12:02:42
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/sys/useruserrole/userUserroleList.do")
	public String roleList(@RequestParam(required = false) String name,
			@RequestParam(required = false) String loginName,
			@RequestParam(required = false) Integer numPerPage,
			@RequestParam(required = false) Integer pageNum,Model model) {
		
		Page page = new Page();
		page.setCount(true);
		page.setCapacity(numPerPage == null ? CAPACITY : numPerPage);
		page.setPageOffset(pageNum == null ? PAGE_OFF_SET : pageNum - 1);
		
		
		List userUserroleList = this.userUserroleBiz.queryUserRole(page, loginName,name);
		model.addAttribute("userUserroleList",userUserroleList);
		model.addAttribute("loginName",loginName);
		model.addAttribute("name",name);
		
		model.addAttribute(NUM_PER_PAGE, page.getCapacity());
		model.addAttribute(PAGE_NUM, pageNum);
		model.addAttribute(TOTAL_COUNT, page.getRecAmt());
		return "/sys/useruserrole/userUserroleList";
	}
	
	/**
	 * 
	* @Description: 去添加页面
	* @author chenwenjing    
	* @date 2014-8-30 下午12:02:59
	 */
	@RequestMapping("/sys/useruserrole/userUserroleAdd.do")
	public String roleAdd(Model model) {
		Map<String,Object> userUserroleMap = this.userUserroleBiz.queryUserAndRole();
		model.addAllAttributes(userUserroleMap);
		return "/sys/useruserrole/userUserroleAdd";
	}
	
	/**
	 * 
	* @Description: 添加后台用户与角色关联
	* @author chenwenjing    
	* @date 2014-8-26 下午8:27:16
	 */
	@RequestMapping("/sys/useruserrole/userUserroleToAdd.do")
	public String userRoleToAdd(Model model,@RequestParam(required = false) Long iuser,@RequestParam(required = false) Long iuserrole) {
		
		TuserUserrole tuserUserrole = new TuserUserrole();
		tuserUserrole.setIuser(iuser);
		tuserUserrole.setIuserrole(iuserrole);
		
		String result = this.userUserroleBiz.addUserRole(tuserUserrole);
		if("failure".equals(result)){
			ajaxResult.setMessage("添加失败，该后台用户已与该角色关联，请重新选择！");
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
	@RequestMapping("/sys/useruserrole/userUserroleDel.do")
	public String userUserroleDel(Model model,@RequestParam(required = false) Long iuser,@RequestParam(required = false) Long iuserrole) {
		
		TuserUserrole tuserUserrole = new TuserUserrole();
		tuserUserrole.setIuser(iuser);
		tuserUserrole.setIuserrole(iuserrole);
		
		this.userUserroleBiz.delete(tuserUserrole);
		
		ajaxResult.setMessage(AjaxResult.MSG_DELETE);
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType("");
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
	
	/**
	 * 
	* @Description:去修改页面
	* @author chenwenjing    
	* @date 2014-8-30 下午12:03:18
	 */
	@RequestMapping("/sys/useruserrole/userUserroleMod.do")
	public String userUserroleMod(Model model,@RequestParam(required = false) Long iuser,@RequestParam(required = false) Long iuserrole) {
		Tuser tuser = this.tuserService.getTuser(iuser);
        Map<String,Object> userUserroleMap = this.userUserroleBiz.queryUserAndRole();
		model.addAllAttributes(userUserroleMap);
		model.addAttribute("tuser",tuser);
		model.addAttribute("iuserrole",iuserrole);
		return "/sys/useruserrole/userUserroleMod";
	}
	/**
	 * 
	* @Description:修改后台用户与角色关联
	* @author chenwenjing    
	* @date 2014-8-28 下午9:03:55
	 */
	@RequestMapping("/sys/useruserrole/userUserroleToMod.do")
	public String userUserroleToMod(Model model,
			@RequestParam(required = false) Long iuser,
			@RequestParam(required = false) Long iuserrole,
			@RequestParam(required = false) Long preIuser,
			@RequestParam(required = false) Long preIuserrole) {
		
		TuserUserrole tuserUserrole = new TuserUserrole();
		tuserUserrole.setIuser(iuser);
		tuserUserrole.setIuserrole(iuserrole);
		
		String result = this.userUserroleBiz.updUserRole(tuserUserrole,preIuser,preIuserrole);
		if("failure".equals(result)){
			ajaxResult.setMessage("修改失败，该后台用户已与该角色关联，请重新选择！");
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
