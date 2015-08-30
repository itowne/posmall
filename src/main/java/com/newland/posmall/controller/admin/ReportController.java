package com.newland.posmall.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("admin.report")
public class ReportController {
	/**
	 * 客户订单报表详情页
	 * @return
	 */
	@RequestMapping("/admin/report/custorder/custorderDetail.do")
	public String custorderDetail( ) {
		return "/admin/report/custorder/custorderDetail";
	}

	/**
	 * 客户订单报表List页
	 * @return
	 */
	@RequestMapping("/admin/report/custorder/custorderList.do")
	public String custorderList( ) {
		return "/admin/report/custorder/custorderList";
	}

	/**
	 * 客户订货寄货报表List页
	 * @return
	 */
	@RequestMapping("/admin/report/orderplanquarter/orderplanquarterList.do")
	public String orderplanquarterList( ) {
		return "/admin/report/orderplanquarter/orderplanquarterList";
	}

	/**
	 * 客户订货寄货报表修改页
	 * @return
	 */
	@RequestMapping("/admin/report/orderplanquarter/orderplanquarterModify.do")
	public String orderplanquarterModify( ) {
		return "/admin/report/orderplanquarter/orderplanquarterModify";
	}
	
	/**
	 * 客户物流单报表List页		
	 * @return
	 */                
	@RequestMapping("/admin/report/custlogistics/custlogisticsList.do")
	public String custlogisticsList( ) {
		return "/admin/report/custlogistics/custlogisticsList";
	}
	
	/**
	 * 客户物流单报表详情页
	 * @return
	 */		                      
	@RequestMapping("/admin/report/custlogistics/custlogisticsDetail.do")
	public String custlogisticsDetail( ) {
		return "/admin/report/custlogistics/custlogisticsDetail";
	}
}
