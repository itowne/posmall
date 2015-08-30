package com.newland.posmall.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.ohuyo.rapid.base.entity.AjaxResult;
import org.ohuyo.rapid.base.entity.Tfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

public abstract class BaseController {
	
	/**
	 * Ajax结果
	 */
	protected AjaxResult ajaxResult = new AjaxResult();

	/**
	 * 分页Page的capacity默认值
	 */
	protected static final int CAPACITY = 10;
	/**
	 * 分页Page的pageOffset默认值
	 */
	protected static final int PAGE_OFF_SET = 0;
	
	protected static final String PAGE_NUM = "pageNum";
	protected static final String NUM_PER_PAGE = "numPerPage";
	protected static final String TOTAL_COUNT = "totalCount";
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@ExceptionHandler({Exception.class})
	public String handleIOException(Exception ex, HttpServletRequest request) throws Exception{
		logger.error("系统错误", ex);
		throw ex;
	}

	public AjaxResult getAjaxResult() {
		return ajaxResult;
	}

	public void setAjaxResult(AjaxResult ajaxResult) {
		this.ajaxResult = ajaxResult;
	}
	
	protected void downloadFile(HttpServletRequest request, HttpServletResponse response, File file) throws Exception{
		
		request.setCharacterEncoding("UTF-8");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		
		if (file.exists() == false) {
			response.sendError(404, "文件不存在");
			return;
		}
		String fileName = file.getName();
		bis = new BufferedInputStream(new FileInputStream(file));
		try {
			response.setHeader("Content-disposition", "attachment; filename="
					+ URLEncoder.encode(fileName, "UTF-8"));
			response.setHeader("Content-Length",
					Long.toString(file.length()));
			response.setContentType("application/octet-stream;charset=utf-8");
			bos = new BufferedOutputStream(response.getOutputStream());
			IOUtils.copy(bis, bos);
		} catch (Exception e) {
			logger.error("文件下载失败", e);
			throw e;
		} finally {
			IOUtils.closeQuietly(bis);
			IOUtils.closeQuietly(bos);
		}
		return;
		
	}

}
