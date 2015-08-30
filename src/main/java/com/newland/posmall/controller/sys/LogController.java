package com.newland.posmall.controller.sys;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.entity.condition.TlogCondition;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.newland.posmall.bean.common.Tlog;
import com.newland.posmall.bean.dict.LogType;
import com.newland.posmall.bean.dict.OperType;
import com.newland.posmall.biz.common.MapBiz;
import com.newland.posmall.biz.sys.LogBiz;
import com.newland.posmall.controller.BaseController;

@Scope("prototype")
@Controller("sys.log")
public class LogController extends BaseController {
	
	@Resource
	private LogBiz logBiz;
	
	@Resource
	private MapBiz mapBiz;

	/**
	 * 
	* @Description: 分页查询
	* @author chenwenjing    
	* @date 2014-8-26 下午3:15:27
	 */
	@RequestMapping("/sys/log/logList.do")
	public String logList(@RequestParam(required = false) String logType,
			@RequestParam(required = false) String operType,
			@DateTimeFormat(pattern = "yyyy-MM-dd") Date beginTime, 
			@DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime, 
			@RequestParam(required = false) Integer numPerPage,
			@RequestParam(required = false) Integer pageNum,Model model) {
		
		Page page = new Page();
		page.setCount(true);
		page.setCapacity(numPerPage == null ? CAPACITY : numPerPage);
		page.setPageOffset(pageNum == null ? PAGE_OFF_SET : pageNum - 1);
		
		
		TlogCondition tlogCondition = new TlogCondition();
		
		if (beginTime!=null&&!"".equals(beginTime)) {
			tlogCondition.setBeginTime(beginTime);
		}else{
			beginTime = new Date();
			Calendar cal = Calendar.getInstance();   
			cal.setTime(beginTime);
			cal.set(Calendar.HOUR_OF_DAY, 0);       
			cal.set(Calendar.SECOND, 0);       
			cal.set(Calendar.MINUTE, 0);
			tlogCondition.setBeginTime(cal.getTime());
		}
		
		if (endTime!=null&&!"".equals(endTime)) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(endTime);
			cal.add(Calendar.DATE, 1);
			tlogCondition.setEndTime(cal.getTime());
		}else{
			endTime = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(endTime);
			cal.set(Calendar.HOUR_OF_DAY, 0);       
			cal.set(Calendar.SECOND, 0);       
			cal.set(Calendar.MINUTE, 0);
			cal.add(Calendar.DATE, 1);
			tlogCondition.setEndTime(cal.getTime());
		}
		if (logType!=null&&!"".equals(logType)) {
			tlogCondition.setLogType(LogType.valueOf(logType));
		}
		
		if (operType!=null&&!"".equals(operType)) {
			tlogCondition.setOperType(OperType.valueOf(operType));
		}
		
		List<Tlog> logList = this.logBiz.queryAllLog(tlogCondition, page);
		
		model.addAttribute("logList",logList);
		model.addAttribute(NUM_PER_PAGE, page.getCapacity());
		model.addAttribute(PAGE_NUM, pageNum);
		model.addAttribute(TOTAL_COUNT, page.getRecAmt());
		model.addAttribute("logType",logType);
		model.addAttribute("operType",operType);
		model.addAttribute("beginTime",beginTime);
		model.addAttribute("endTime",endTime);
		return "/sys/log/logList";
	}
	
	
	/**
	 * 
	* @Description: 日志详情
	* @author chenwenjing    
	* @date 2014-8-24 下午10:52:21
	 */
	@RequestMapping("/sys/log/logDetail.do")
	public String logDetail(@RequestParam(required = false) Long ilog,Model model) throws Exception{
		Tlog tlog = this.logBiz.QueryLogByIlog(ilog);
		
		model.addAttribute(tlog);
		return "/sys/log/logDetail";
	}
	

}
