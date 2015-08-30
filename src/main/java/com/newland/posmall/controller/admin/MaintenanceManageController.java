package com.newland.posmall.controller.admin;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.entity.ERPMaintenance;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.newland.posmall.biz.admin.MaintenanceManageBiz;
import com.newland.posmall.controller.BaseController;

@Scope("prototype")
@Controller("admin.maintenancemanage")
public class MaintenanceManageController extends BaseController{
	
	@Resource
	private MaintenanceManageBiz maintenanceManageBiz;
	
	@RequestMapping("/admin/maintenancemanage/maintenancemanageDetail.do")
	public String maintenancemanageDetail( ) {
		return "/admin/maintenancemanage/maintenancemanageDetail";
	}
	
	/**
	 * 分页查询
	 * @param sn 序列号
	 * @param custName 客户名称
	 * @param numPerPage
	 * @param pageNum
	 * @param model
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/admin/maintenancemanage/maintenancemanageList.do")
	public String maintenancemanageList(@RequestParam(required = false) String sn,
			@RequestParam(required = false) String custName,
			@RequestParam(required = false) Integer numPerPage,
			@RequestParam(required = false) Integer pageNum,Model model) {
		
		Page page = new Page();
		page.setCount(true);
		page.setCapacity(numPerPage == null ? CAPACITY : numPerPage);
		page.setPageOffset(pageNum == null ? PAGE_OFF_SET : pageNum - 1);
		
		List list = this.maintenanceManageBiz.queryPageBySql(sn, custName, page);
		
		model.addAttribute("list",list);
		model.addAttribute("sn",sn);
		model.addAttribute("custName",custName);
		
		model.addAttribute(NUM_PER_PAGE, page.getCapacity());
		model.addAttribute(PAGE_NUM, pageNum);
		model.addAttribute(TOTAL_COUNT, page.getRecAmt());
		
		return "/admin/maintenancemanage/maintenancemanageList";
	}
	
	@RequestMapping("/admin/maintenancemanage/maintenancemanageModify.do")
	public String maintenancemanageModify( ) {
		return "/admin/maintenancemanage/maintenancemanageModify";
	}
	
	/**
	 * 保修明细
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/admin/maintenancemanage/detail.do")
	public String detail(@RequestParam Long id, Model model) {
		
		ERPMaintenance maintenance = this.maintenanceManageBiz.queryErpMaintenanceById(id);
		// 查询更换设备前序列号
		if (maintenance != null && maintenance.getLastMaintenanceId() != null) {
			ERPMaintenance last = this.maintenanceManageBiz.queryErpMaintenanceById(maintenance.getLastMaintenanceId());
			model.addAttribute("last", last);
		}
		// 查询更换设备后新的序列号
		ERPMaintenance newMaintenance = this.maintenanceManageBiz.queryByLastMaintenanceId(id);
		if (newMaintenance != null) {
			model.addAttribute("newMaintenance", newMaintenance);
		}
		model.addAttribute("maintenance", maintenance);
		
		return "/admin/maintenancemanage/maintenanceDetail";
	}

}
