package com.newland.posmall.biz.schedule.service;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.ohuyo.rapid.base.entity.TsysParam;
import org.ohuyo.rapid.base.service.TsysParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newland.posmall.Application;
import com.newland.posmall.bean.dict.SysParamType;
import com.newland.posmall.utils.HttpUtil;
@Service
public class HttpDownService {
	
	//private String downloadUrl = "http://192.168.5.108:8080/QueryForOrderAction.do";
	@Autowired
	private TsysParamService sysParamService;
	
	public File download(String fileName) throws Exception{
		TsysParam param = this.sysParamService.getTsysParam(SysParamType.OTHER_CONF.name(), "ERPURL");
		if (param == null) throw new RuntimeException("ERP物流同步地址未配置");
		String downloadUrl = param.getValue();
		String url = downloadUrl + "?actionType=download";
		if (StringUtils.isNotBlank(fileName)){
			url = url + "&fileName=" + fileName;
		}
		//String url = "http://192.168.2.254:8080/posmall/file/download.do?fileid=d26cacaf11aa4af98f8f862cebb47294";
		File file = HttpUtil.getFileFromUrl(url, Application.getTemplatePath(), fileName,  "UTF-8");
		if (file.exists() == false) throw new RuntimeException("文件下载失败：[" + fileName + "]");
		return file;
	}

}
