package com.newland.posmall.controller.admin;

import java.io.File;
import java.util.Date;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.time.DateUtils;
import org.ohuyo.rapid.base.service.FileUpDownService;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.newland.posmall.biz.file.ReportFormDownload;
import com.newland.posmall.controller.BaseController;

@Scope("prototype")
@Controller("admin.reportform")
public class ReportFormController extends BaseController {
	
	@Resource
	private ReportFormDownload reportFormDownload;
	@Resource
	private FileUpDownService fileUpDownService;
    
	@RequestMapping("/admin/reportForm/reportFormList.do")
	public String reportFormList(Model model) {
		
		return "/admin/report/reportFormList";
		
	}
	@RequestMapping("/admin/reportForm/reportFormDownLoad.do")
	public void reportFormDownLoad(String reportType,
			@DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
			@DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
			HttpServletResponse response,Model model) {
		
		File file = null;
		Date endTime=null;
		if(endDate!=null){
			endTime = DateUtils.addDays(endDate,1);
		}
		try {
			if("0".equals(reportType)){
			    file = reportFormDownload.agmtReport(startDate,endTime);
			}else if("1".equals(reportType)){
				file = reportFormDownload.ordReport(startDate, endTime);
			}else if("2".equals(reportType)){
				file = reportFormDownload.logisticsOrdReport(startDate, endTime);
			}
			fileUpDownService.download(file, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
