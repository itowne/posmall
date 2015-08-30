package com.newland.posmall.controller.sys;
import java.util.List;
import javax.annotation.Resource;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.entity.AjaxResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.newland.posmall.bean.basebusi.TnotifyCfg;
import com.newland.posmall.bean.basebusi.condition.TnotifyCfgCondition;
import com.newland.posmall.bean.dict.NotifyType;
import com.newland.posmall.biz.sys.NotifyCfgBiz;
import com.newland.posmall.controller.BaseController;

@Scope("prototype")
@Controller("sys.notifycfg")
public class NotifyCfgController extends BaseController {
	
	@Resource
	private NotifyCfgBiz notifyCfgBiz;
	
	private static final String NAV_TAB_ID = "YJTZRGL";
	
	@RequestMapping("/sys/notifycfg/notifycfgList.do")
	public String notifycfgList(@RequestParam(required = false) String userName,
			@RequestParam(required = false) NotifyType notifyType,
			@RequestParam(required = false) Integer numPerPage,
			@RequestParam(required = false) Integer pageNum,Model model) {
		
		Page page = new Page();
		page.setCount(true);
		page.setCapacity(numPerPage == null ? CAPACITY : numPerPage);
		page.setPageOffset(pageNum == null ? PAGE_OFF_SET : pageNum - 1);
		
		TnotifyCfgCondition notifyCfgCondition = new TnotifyCfgCondition();
		notifyCfgCondition.setDelFlag(Boolean.FALSE);
		notifyCfgCondition.setUserName(userName);
		notifyCfgCondition.setNotifyType(notifyType);
		
		List<TnotifyCfg> tnotifyCfgList = this.notifyCfgBiz.queryAllTnotifyCfg(notifyCfgCondition, page);
		
		model.addAttribute("tnotifyCfgList",tnotifyCfgList);
		
		model.addAttribute("userName",userName);
		model.addAttribute("notifyType",notifyType);

		
		model.addAttribute(NUM_PER_PAGE, page.getCapacity());
		model.addAttribute(PAGE_NUM, pageNum);
		model.addAttribute(TOTAL_COUNT, page.getRecAmt());
		return "/sys/notifycfg/notifycfgList";
	}
	
	/**
	 * 查询明细
	 */
	@RequestMapping("/sys/notifycfg/notifycfgDetail.do")
	public String notifycfgDetail(@RequestParam(required = false) Long inotifycfg,
			Model model) {

		TnotifyCfg tnotifycfg = this.notifyCfgBiz.queryTnotifyCfgByInotifyCfg(inotifycfg);
		model.addAttribute(tnotifycfg);
		return "/sys/notifycfg/notifycfgDetail";
	}

	/**
	 * 去修改页面
	 */
	@RequestMapping("/sys/notifycfg/toNotifycfgModify.do")
	public String notifycfgModify(@RequestParam(required = false) Long inotifycfg,
			Model model) {
		TnotifyCfg tnotifycfg = this.notifyCfgBiz.queryTnotifyCfgByInotifyCfg(inotifycfg);
		model.addAttribute(tnotifycfg);
		return "/sys/notifycfg/notifycfgModify";
	}
	
	/**
	 * 去翻新增页面
	 */
	@RequestMapping("/sys/notifycfg/toNotifycfgAdd.do")
	public String toNotifycfgAdd() {
		return "/sys/notifycfg/notifycfgAdd";
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value = "/sys/notifycfg/notifycfgAdd.do",method = RequestMethod.POST)
	public String notifycfgAdd(TnotifyCfg tnotifycfg, Model model) {
		
			this.notifyCfgBiz.addTnotifyCfg(tnotifycfg);
			
		ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
		ajaxResult.setMessage(AjaxResult.MSG_ADD);
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}

	/**
	 * 修改
	 */
	@RequestMapping(value = "/sys/notifycfg/notifycfgModify.do",method = RequestMethod.POST)
	public String notifycfgModify(
			TnotifyCfg tnotifycfg, Model model) {
		
		this.notifyCfgBiz.modifyTnotifyCfg(tnotifycfg);
		
		ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
		ajaxResult.setMessage(AjaxResult.MSG_UPDATE);
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}

	/**
	 * 删除
	 */
	@RequestMapping("/sys/notifycfg/notifycfgRemove.do")
	public String notifycfgRemove(@RequestParam(required = false) Long inotifycfg,
			Model model) {

		this.notifyCfgBiz.removeTnotifyCfg(inotifycfg);
		ajaxResult.setMessage(AjaxResult.MSG_DELETE);
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType("");
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
	
	
    @RequestMapping(value = "/sys/notifycfg/validateNotifyTypeUniq.do", method = RequestMethod.GET) 
    public @ResponseBody String validateNotifyTypeUniq(
    		@RequestParam(required = false) NotifyType notifyType, 
    		@RequestParam(required = false) Long id,
    		@RequestParam(required = false) Long userId) {
    	
      	String errMsg = notifyCfgBiz.validateNotifyType(notifyType, id, userId);
    	return "{\"result\": \""+errMsg+"\"}";
    } 
	

}
