package org.ohuyo.rapid.base.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author rabbit
 * 
 */
public abstract class HttpFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public final void doFilter(ServletRequest request,
			ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		if (!(request instanceof HttpServletRequest)) {
			chain.doFilter(request, response);
		}

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		doHttpFilter(req, resp, chain);
	}

	protected abstract void doHttpFilter(HttpServletRequest req,
			HttpServletResponse resp, FilterChain chain) throws IOException,
			ServletException;

	@Override
	public void destroy() {
	}
}