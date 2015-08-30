package com.newland.posmall.controller.sys;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.entity.AjaxResult;
import org.ohuyo.rapid.base.entity.Tuser;
import org.ohuyo.rapid.base.entity.condition.TuserCondition;
import org.ohuyo.rapid.base.security.RsaService;
import org.ohuyo.rapid.base.service.PasswordService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.newland.posmall.biz.sys.UserBiz;
import com.newland.posmall.controller.BaseController;

@Controller("sys.user")
public class UserController extends BaseController {

	@Resource
	private UserBiz userBiz;

	@Resource
	private PasswordService passwordService;
	
	@Resource
	private RsaService rsaService;
	
	private static final String NAV_TAB_ID = "HTRYXXGL";

	/**
	 * 查询列表
	 */
	@RequestMapping("/sys/user/userList.do")
	public String userList(@RequestParam(required = false) String loginName,
			@RequestParam(required = false) Integer numPerPage,
			@RequestParam(required = false) Integer pageNum, Model model) {

		Page page = new Page();
		page.setCount(true);
		page.setCapacity(numPerPage == null ? CAPACITY : numPerPage);
		page.setPageOffset(pageNum == null ? PAGE_OFF_SET : pageNum - 1);

		TuserCondition tuserConfition = new TuserCondition();
		if (StringUtils.isNotBlank(loginName)) {
			tuserConfition.setLoginName(loginName);
		}

		List<Tuser> tuserList = this.userBiz
				.queryAllTuser(tuserConfition, page);
		
		model.addAttribute(tuserList);
		model.addAttribute(NUM_PER_PAGE, page.getCapacity());
		model.addAttribute(PAGE_NUM, pageNum);
		model.addAttribute(TOTAL_COUNT, page.getRecAmt());
		model.addAttribute("loginName", loginName);
		return "/sys/user/userList";
	}

	/**
	 * 查询明细
	 */
	@RequestMapping("/sys/user/userDetail.do")
	public String userDetail(@RequestParam(required = false) Long iuser,
			Model model) {

		Tuser tuser = this.userBiz.queryTuserByIuser(iuser);
		model.addAttribute(tuser);
		
		return "/sys/user/userDetail";
	}

	/**
	 * 去新增页面
	 */
	@RequestMapping("/sys/user/userAdd.do")
	public String userAdd(@RequestParam(required = false) Long iuser,
			Model model) {
		return "/sys/user/userAdd";
	}

	/**
	 * 新增
	 */
	@RequestMapping(value = "/sys/user/userAdd.do",method = RequestMethod.POST)
	public String userAdd(Tuser tuser,Model model) {
		
		this.userBiz.addTuser(tuser);
		
		ajaxResult.setMessage("用户添加成功，初始密码【123456】");
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
	
	/**
	 * 去修改页面
	 */
	@RequestMapping("/sys/user/userModify.do")
	public String userModify(@RequestParam(required = false) Long iuser,
			Model model) {
		Tuser tuser = this.userBiz.queryTuserByIuser(iuser);
		model.addAttribute(tuser);
		return "/sys/user/userModify";
	}

	/**
	 * 修改
	 */
	@RequestMapping(value = "/sys/user/userModify.do",method = RequestMethod.POST)
	public String userModify(Tuser tuser,Model model) {
		
		this.userBiz.modifyTuserByIuser(tuser);
		
		ajaxResult.setMessage(AjaxResult.MSG_UPDATE);
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}

	/**
	 * 删除
	 */
	@RequestMapping("/sys/user/userRemove.do")
	public String userRemove(@RequestParam(required = false) Long iuser,
			Model model) {

		String result = this.userBiz.removeTuserByIuser(iuser);
		if("failure".equals(result)){
			ajaxResult.setMessage("此后台人员已关联，请先删除关联！");
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
	 * 重置密码
	 */
	@RequestMapping("/sys/user/resetPass.do")
	public String resetPass(@RequestParam(required = false) Long iuser,
			Model model) {

		this.userBiz.resetPassByIuser(iuser);
		ajaxResult.setMessage("重置密码成功【123456】");
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType("");
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
}
