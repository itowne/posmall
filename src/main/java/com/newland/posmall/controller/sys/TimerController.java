package com.newland.posmall.controller.sys;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.TsysSession;
import org.ohuyo.rapid.base.entity.AjaxResult;
import org.ohuyo.rapid.base.servlet.AppSessionFilter;
import org.ohuyo.rapid.schedule.Enabled;
import org.ohuyo.rapid.schedule.TaskManager;
import org.ohuyo.rapid.schedule.biz.TaskConfBiz;
import org.ohuyo.rapid.schedule.biz.TaskLogBiz;
import org.ohuyo.rapid.schedule.entity.TaskConf;
import org.ohuyo.rapid.schedule.entity.TaskLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.newland.posmall.controller.BaseController;

@Scope("prototype")
@Controller("sys.timer")
public class TimerController extends BaseController {

	private static final String NAVTABID = "DSQGL";

	@Resource
	private TaskConfBiz taskConfBiz;

	@Resource
	private TaskLogBiz taskLogBiz;

	@Autowired
	private TaskManager taskManager;

	/**
	 * 定时器任务列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/sys/timer/timerList.do")
	public String timerList(Model model) {
		List<TaskConf> taskConfList = this.taskConfBiz.queryAll();
		model.addAttribute("taskConfList", taskConfList);
		return "/sys/timer/timerList";
	}

	/**
	 * 跳转至修改页面
	 * 
	 * @param itask
	 * @param model
	 * @return
	 */
	@RequestMapping("/sys/timer/toModify.do")
	public String toModify(Long itask, Model model) {
		TaskConf taskConf = this.taskConfBiz.queryById(itask);
		model.addAttribute("taskConf", taskConf);
		return "/sys/timer/timerModify";
	}

	/**
	 * 修改定时器任务
	 * 
	 * @param taskConf
	 * @param model
	 * @return
	 */
	@RequestMapping("/sys/timer/modify.do")
	public String modify(Long itask, String enabledFlag, Model model) {
		TaskConf taskConf = this.taskConfBiz.queryById(itask);
		taskConf.setEnabledFlag(Enabled.valueOf(enabledFlag));
		this.taskConfBiz.modifyTaskConf(taskConf);
		ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
		ajaxResult.setMessage(AjaxResult.MSG_OK);
		ajaxResult.setNavTabId(NAVTABID);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}

	/**
	 * 去定时器任务详细情况页
	 * 
	 * @param itask
	 * @param model
	 * @return
	 */
	@RequestMapping("/sys/timer/toDetail.do")
	public String toDetail(Long itask, @RequestParam(required = false) Integer numPerPage,
			@RequestParam(required = false) Integer pageNum,Model model) {
		
		Page page = new Page();
		page.setCount(true);
		page.setCapacity(numPerPage == null ? CAPACITY : numPerPage);
		page.setPageOffset(pageNum == null ? PAGE_OFF_SET : pageNum - 1);
		List<TaskLog> taskLogList = this.taskLogBiz.queryPageByItask(itask, page);
		model.addAttribute("itask", itask);
		model.addAttribute("taskLogList", taskLogList);
		model.addAttribute(NUM_PER_PAGE, page.getCapacity());
		model.addAttribute(PAGE_NUM, pageNum);
		model.addAttribute(TOTAL_COUNT, page.getRecAmt());
		return "/sys/timer/timerDetail";
	}
	
	@RequestMapping("/sys/timer/toTaskManager.do")
	public String toTaskManager(String service, Model model) {
		model.addAttribute("service", service);
		return "/sys/timer/timerControll";
	}

	/**
	 * 去触发任务
	 * 
	 * @param service
	 * @param model
	 * @return
	 */
	@RequestMapping("/sys/timer/taskManager.do")
	public String taskManager(String service, Model model,@RequestParam(required = false) String manualDate) {
		TsysSession session = (TsysSession) AppSessionFilter.getAppSession();
		try {
			this.taskManager.trigger(service, session.getTsys(), manualDate);
		} catch (Exception e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
			ajaxResult.setMessage(AjaxResult.MSG_FAILED);
			ajaxResult.setNavTabId(NAVTABID);
			ajaxResult.setMessage(e.getMessage());
			if(!StringUtils.isEmpty(manualDate) || "maintenanceInfoSyncTask".equals(service)){
				ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
			}
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
		ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
		ajaxResult.setMessage(AjaxResult.MSG_OK);
		ajaxResult.setNavTabId(NAVTABID);
		if(!StringUtils.isEmpty(manualDate) || "maintenanceInfoSyncTask".equals(service)){
			ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		}
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
}
