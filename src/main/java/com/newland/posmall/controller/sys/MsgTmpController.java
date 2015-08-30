package com.newland.posmall.controller.sys;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.entity.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysql.jdbc.StringUtils;
import com.newland.posmall.bean.basebusi.condition.TmsgTmpCondition;
import com.newland.posmall.bean.common.TmsgTmp;
import com.newland.posmall.bean.dict.MsgTmpType;
import com.newland.posmall.biz.sys.MsgTmpBiz;
import com.newland.posmall.controller.BaseController;

@Scope("prototype")
@Controller("sys.msgtmp")
public class MsgTmpController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(MsgTmpController.class);
	
	@Resource
	private MsgTmpBiz msgTmpBiz;
	
	private static final String NAV_TAB_ID = "XXMBGL";
	
	@RequestMapping("/sys/msgtmp/msgtmpList.do")
	public String msgtmpList(@RequestParam(required = false) String noteName,
			@RequestParam(required = false) MsgTmpType tmpType,
			@RequestParam(required = false) Integer numPerPage,
			@RequestParam(required = false) Integer pageNum,Model model) {
		
		Page page = new Page();
		page.setCount(true);
		page.setCapacity(numPerPage == null ? CAPACITY : numPerPage);
		page.setPageOffset(pageNum == null ? PAGE_OFF_SET : pageNum - 1);
		
		TmsgTmpCondition msgTmpCondition = new TmsgTmpCondition();
		msgTmpCondition.setDelFlag(Boolean.FALSE);
		msgTmpCondition.setNoteName(noteName);
		msgTmpCondition.setTmpType(tmpType);
		
		List<TmsgTmp> tmsgTmpList = this.msgTmpBiz.queryAllTmsgTmp(msgTmpCondition, page);
		
		model.addAttribute("tmsgTmpList",tmsgTmpList);
		
		model.addAttribute("noteName",noteName);
		model.addAttribute("tmpType",tmpType);

		model.addAttribute(NUM_PER_PAGE, page.getCapacity());
		model.addAttribute(PAGE_NUM, pageNum);
		model.addAttribute(TOTAL_COUNT, page.getRecAmt());
		return "/sys/msgtmp/msgtmpList";
	}
	
	/**
	 * 查询明细
	 */
	@RequestMapping("/sys/msgtmp/msgtmpDetail.do")
	public String msgtmpDetail(@RequestParam(required = false) Long imsgtmp,
			Model model) {

		TmsgTmp tmsgtmp = this.msgTmpBiz.queryTmsgTmpByImsgTmp(imsgtmp);
		model.addAttribute("tmsgtmp",tmsgtmp);
		return "/sys/msgtmp/msgtmpDetail";
	}

	/**
	 * 去修改页面
	 */
	@RequestMapping("/sys/msgtmp/toMsgTmpModify.do")
	public String msgtmpModify(@RequestParam(required = false) Long imsgtmp,
			Model model) {
		TmsgTmp tmsgtmp = this.msgTmpBiz.queryTmsgTmpByImsgTmp(imsgtmp);
		model.addAttribute("tmsgtmp",tmsgtmp);
		return "/sys/msgtmp/msgtmpModify";
	}
	
	/**
	 * 去翻新增页面
	 */
	@RequestMapping("/sys/msgtmp/toMsgTmpAdd.do")
	public String toMsgTmpAdd() {
		return "/sys/msgtmp/msgtmpAdd";
	}
	
	/**
	 * 新增
	 */
	@RequestMapping(value = "/sys/msgtmp/msgtmpAdd.do", method = RequestMethod.POST)
	public String msgtmpAdd(TmsgTmp tmsgtmp, Model model) {

		this.msgTmpBiz.addTmsgTmp(tmsgtmp);

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
	@RequestMapping(value = "/sys/msgtmp/msgtmpModify.do", method = RequestMethod.POST)
	public String msgtmpModify(TmsgTmp tmsgtmp, Model model) {

		this.msgTmpBiz.modifyTmsgTmp(tmsgtmp);

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
	@RequestMapping("/sys/msgtmp/msgtmpRemove.do")
	public String msgtmpRemove(@RequestParam(required = false) Long imsgtmp,
			Model model) {

		this.msgTmpBiz.removeTmsgTmp(imsgtmp);
		ajaxResult.setMessage(AjaxResult.MSG_DELETE);
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType("");
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
	
	
    @RequestMapping(value = "/sys/msgtmp/validateMsgTmpUniq.do", method = RequestMethod.GET) 
    public @ResponseBody String validateMsgTmpUniq(
    		@RequestParam(required = false) MsgTmpType tmpType, 
    		@RequestParam(required = false) String tmpCode, 
    		@RequestParam(required = false) Long id) { 
    	
      	Boolean flag = msgTmpBiz.validateMsgTmpUniq(tmpType, tmpCode, id);
    	return "{\"result\": \""+flag+"\"}";
    } 
	

}
