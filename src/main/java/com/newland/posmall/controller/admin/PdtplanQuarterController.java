package com.newland.posmall.controller.admin;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Scope("prototype")
@Controller("admin.pdtplanquarter")
public class PdtplanQuarterController {
	@RequestMapping("/admin/pdtplanquarterDetail.do")
	public String pdtplanquarterDetail( ) {
		return "/admin/pdtplanquarter/pdtplanquarterDetail";
	}

	@RequestMapping("/admin/orderplanquarterList.do")
	public String pdtplanquarterList( ) {
		return "/admin/pdtplanquarter/pdtplanquarterList";
	}
	
	@RequestMapping("/admin/orderplanquarterModify.do")
	public String pdtplanquarterModify( ) {
		return "/admin/pdtplanquarter/pdtplanquarterModify";
	}

}
