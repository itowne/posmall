package org.ohuyo.rapid.base.servlet;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ohuyo.rapid.base.service.FileUpDownService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class DownloadServlet extends HttpServlet implements Servlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			this.getBean(req).download(req, resp);
		} catch (Exception e) {
			logger.error("上传文件失败", e);
			resp.sendError(500, "上传文件失败");
		}
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	
	private FileUpDownService getBean(HttpServletRequest req){
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(req.getSession().getServletContext());
		return (FileUpDownService)ctx.getBean(FileUpDownService.class);
	}

	

}
