package com.newland.posmall.controller.admin;
import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.entity.condition.TdetailTraceCondition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.newland.posmall.bean.common.TdetailTrace;
import com.newland.posmall.biz.admin.AdminDetailTraceBiz;
import com.newland.posmall.controller.BaseController;

@Scope("prototype")
@Controller("admin.trace")
public class DetailTraceController extends BaseController {
	
	@Resource
	private AdminDetailTraceBiz adminDetailTraceBiz;
	
	/**
	 * 跟踪数据列表
	 * @param tdetailTraceCondition
	 * @param pageNum
	 * @param numPerPage
	 * @param model
	 * @return
	 */
	@RequestMapping("/admin/trace/detailTraceList.do")
	public String list(TdetailTraceCondition tdetailTraceCondition, 
			@RequestParam(required = false) Integer pageNum, 
			@RequestParam(required = false) Integer numPerPage, Model model) {
		Page page = new Page();
		page.setCount(true);
		page.setCapacity(numPerPage == null ? CAPACITY : numPerPage);
		page.setPageOffset(pageNum == null ? PAGE_OFF_SET : pageNum - 1);
		
		List<TdetailTrace> list = this.adminDetailTraceBiz.pageByCondition(tdetailTraceCondition,page);
		model.addAttribute("list", list);
		model.addAttribute("tdetailTraceCondition", tdetailTraceCondition);
		
		model.addAttribute(NUM_PER_PAGE, page.getCapacity());
		model.addAttribute(PAGE_NUM, pageNum);
		model.addAttribute(TOTAL_COUNT, page.getRecAmt());
		return "/admin/trace/detailTraceList";
	}
}
