package com.newland.posmall.base.service;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.newland.posmall.base.dao.ReportFormDao;

/**
 * 
* @ClassName: ReportFormService 
* @Description: 报表  
* @author chenwenjing
* @date 2014-11-19 下午5:05:09
 */
@Service
public class ReportFormService {
	
	@Resource
	private ReportFormDao reportFormDao; 
	
	
}
