package org.ohuyo.rapid.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

public class CustomerMappingExceptionResolver extends
		SimpleMappingExceptionResolver {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response,
			Object handler, Exception ex) {
		logger.error("发生错误", ex);
		return super.doResolveException(request, response, handler, ex);
	}

}
