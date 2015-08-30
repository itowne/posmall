package com.newland.posmall.controller.sys;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.entity.AjaxResult;
import org.ohuyo.rapid.base.entity.Tsys;
import org.ohuyo.rapid.base.entity.TsysSysrole;
import org.ohuyo.rapid.base.service.TsysService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.newland.posmall.biz.sys.SysSysroleBiz;
import com.newland.posmall.controller.BaseController;

@Scope("prototype")
@Controller("sys.syssysrole")
public class SysSysroleController extends BaseController {
	
	@Resource
	private SysSysroleBiz sysSysroleBiz;
	
	@Resource
	private TsysService tsysService;
	
	private static final String NAV_TAB_ID = "GLYJSGLGL";
	
	/**
	 * 
	* @Description:分页条件查询
	* @author chenwenjing    
	* @date 2014-8-30 下午12:01:57
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/sys/syssysrole/sysSysroleList.do")
	public String roleList(@RequestParam(required = false) String name,
			@RequestParam(required = false) String loginName,
			@RequestParam(required = false) Integer numPerPage,
			@RequestParam(required = false) Integer pageNum,Model model) {
		
		Page page = new Page();
		page.setCount(true);
		page.setCapacity(numPerPage == null ? CAPACITY : numPerPage);
		page.setPageOffset(pageNum == null ? PAGE_OFF_SET : pageNum - 1);
		
		
		List sysSysroleList = this.sysSysroleBiz.queryUserRole(page, loginName,name);
		model.addAttribute("sysSysroleList",sysSysroleList);
		model.addAttribute("loginName",loginName);
		model.addAttribute("name",name);
		
		model.addAttribute(NUM_PER_PAGE, page.getCapacity());
		model.addAttribute(PAGE_NUM, pageNum);
		model.addAttribute(TOTAL_COUNT, page.getRecAmt());
		return "/sys/syssysrole/sysSysroleList";
	}
	
	/**
	 * 
	* @Description: 去添加页面
	* @author chenwenjing    
	* @date 2014-8-30 下午12:01:41
	 */
	@RequestMapping("/sys/syssysrole/sysSysroleAdd.do")
	public String roleAdd(Model model) {
		Map<String,Object> sysSysroleMap = this.sysSysroleBiz.queryUserAndRole();
		model.addAllAttributes(sysSysroleMap);
		return "/sys/syssysrole/sysSysroleAdd";
	}
	
	/**
	 * 
	* @Description: 添加系统管理员与角色关联
	* @author chenwenjing    
	* @date 2014-8-26 下午8:27:16
	 */
	@RequestMapping("/sys/syssysrole/sysSysroleToAdd.do")
	public String sysRoleToAdd(Model model,@RequestParam(required = false) Long isys,@RequestParam(required = false) Long isysrole) {
		
		TsysSysrole tsysSysrole = new TsysSysrole();
		tsysSysrole.setIsys(isys);
		tsysSysrole.setIsysrole(isysrole);
		
		String result = this.sysSysroleBiz.addUserRole(tsysSysrole);
		if("failure".equals(result)){
			ajaxResult.setMessage("添加失败，该系统用户已与该角色关联，请重新选择！");
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
	* @Description: 删除系统管理员与角色的关联
	* @author chenwenjing    
	* @date 2014-8-28 下午7:48:53
	 */
	@RequestMapping("/sys/syssysrole/sysSysroleDel.do")
	public String sysSysroleDel(Model model,@RequestParam(required = false) Long isys,@RequestParam(required = false) Long isysrole) {
		
		TsysSysrole tsysSysrole = new TsysSysrole();
		tsysSysrole.setIsys(isys);
		tsysSysrole.setIsysrole(isysrole);
		
		this.sysSysroleBiz.delete(tsysSysrole);
		
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
	* @date 2014-8-30 下午12:03:41
	 */
	@RequestMapping("/sys/syssysrole/sysSysroleMod.do")
	public String sysSysroleMod(Model model,@RequestParam(required = false) Long isys,@RequestParam(required = false) Long isysrole) {
		Tsys tsys  = this.tsysService.getTsys(isys);
        Map<String,Object> sysSysroleMap = this.sysSysroleBiz.queryUserAndRole();
		model.addAllAttributes(sysSysroleMap);
		model.addAttribute("tsys",tsys);
		model.addAttribute("isysrole",isysrole);
		return "/sys/syssysrole/sysSysroleMod";
	}
	/**
	 * 
	* @Description:修改系统管理员与角色关联
	* @author chenwenjing    
	* @date 2014-8-28 下午9:03:55
	 */
	@RequestMapping("/sys/syssysrole/sysSysroleToMod.do")
	public String sysSysroleToMod(Model model,
			@RequestParam(required = false) Long isys,
			@RequestParam(required = false) Long isysrole,
			@RequestParam(required = false) Long preIsys,
			@RequestParam(required = false) Long preIsysrole) {
		
		TsysSysrole tsysSysrole = new TsysSysrole();
		tsysSysrole.setIsys(isys);
		tsysSysrole.setIsysrole(isysrole);
		
		String result = "";
		//判断两个对象是否一致
		if(preIsys!=null && preIsys.equals(isys)){
			if(preIsysrole!=null && preIsysrole.equals(isysrole)){
				result = "the_same";
			}
		}
		//两个对象不一致时，判断修改结果是否存在
		if(!"the_same".equals(result)){
			result = this.sysSysroleBiz.updSysRole(tsysSysrole,preIsys,preIsysrole);
		}
		
		if("failure".equals(result)){
			ajaxResult.setMessage("修改失败，该系统用户已与该角色关联，请重新选择！");
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
