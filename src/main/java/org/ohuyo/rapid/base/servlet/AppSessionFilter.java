package org.ohuyo.rapid.base.servlet;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.ohuyo.rapid.base.AppSession;
import org.ohuyo.rapid.base.SessionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 登陆过滤器
 * 
 * @author rabbit
 * 
 */
public class AppSessionFilter extends HttpFilter {

	/**
	 * 登陆标志tag
	 */
	public static final String RAPID_BASE_APP_SESSION = "RAPID_BASE_APP_SESSION";

	/**
	 * 
	 */
	private static ThreadLocal<AppSession> appSessSet = new ThreadLocal<AppSession>();

	/**
	 * 
	 */
	private static ThreadLocal<HttpServletRequest> reqSet = new ThreadLocal<HttpServletRequest>();

	/**
	 * 
	 */
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * 需要登陆的url前缀
	 */
	private String baseUrl = null;

	/**
	 * 登录页
	 */
	private String loginUrl = null;

	/**
	 * 验证身份
	 */
	private String authenticateUrl = null;

	/**
	 * 无权限的跳转页面
	 */
	private String denyAuthRedirectUrl = null;

	/**
	 * 
	 */
	private SessionType sessionType = null;

	/**
	 * filter参数名
	 */
	private static final String BASE_URL = "baseUrl";

	/**
	 * filter参数名
	 */
	private static final String LOGIN_URL = "loginUrl";

	/**
	 * 
	 */
	private static final String AUTHENTICATE_URL = "authenticateUrl";

	/**
	 * 
	 */
	private static final String DENY_AUTH_REDIRECT_URL = "denyAuthRedirectUrl";

	private static final String SESSION_TYPE = "sessionType";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		baseUrl = filterConfig.getInitParameter(BASE_URL);
		if (StringUtils.isBlank(baseUrl)) {
			new ServletException();
		}

		loginUrl = filterConfig.getInitParameter(LOGIN_URL);
		if (StringUtils.isBlank(loginUrl)) {
			new ServletException();
		}

		authenticateUrl = filterConfig.getInitParameter(AUTHENTICATE_URL);
		if (StringUtils.isBlank(authenticateUrl)) {
			new ServletException();
		}

		denyAuthRedirectUrl = filterConfig
				.getInitParameter(DENY_AUTH_REDIRECT_URL);
		if (StringUtils.isBlank(denyAuthRedirectUrl)) {
			new ServletException();
		}

		String sessionTypeStr = filterConfig.getInitParameter(SESSION_TYPE);
		if (StringUtils.isBlank(sessionTypeStr)) {
			new ServletException();
		}
		sessionType = SessionType.valueOf(sessionTypeStr);

		String ctx = filterConfig.getServletContext().getContextPath();
		if (StringUtils.equals(ctx, "/")) {

		} else {
			loginUrl = addctx(ctx, loginUrl);
			baseUrl = addctx(ctx, baseUrl);
			authenticateUrl = addctx(ctx, authenticateUrl);
			denyAuthRedirectUrl = addctx(ctx, denyAuthRedirectUrl);
		}

	}

	private String addctx(String ctx, String url) {
		if (url.startsWith("/")) {
			url = ctx + url;
		} else {
			url = ctx + "/" + url;
		}
		return url;
	}

	@Override
	protected void doHttpFilter(HttpServletRequest req,
			HttpServletResponse resp, FilterChain chain) throws IOException,
			ServletException {
		reqSet.set(req);
		try {
			doHttpFilterImpl(req, resp, chain);
		} finally {
			reqSet.remove();
		}
	}

	private void doHttpFilterImpl(HttpServletRequest req,
			HttpServletResponse resp, FilterChain chain) throws IOException,
			ServletException {
		String url = req.getRequestURI();
		if (url.startsWith(loginUrl) || url.startsWith(authenticateUrl)
				|| url.startsWith(denyAuthRedirectUrl)) {
			chain.doFilter(req, resp);
			return;
		}

		if (!url.startsWith(baseUrl)) {
			chain.doFilter(req, resp);
			return;
		}

		String target = renderParam(req, url);
		HttpSession sess = req.getSession(false);
		if (sess == null || sess.isNew()) {
			String s = "";
			if (StringUtils.isNotBlank(target)) {
				s = "?target=" + target;
			}
			resp.sendRedirect(loginUrl + s);
			return;
		}

		AppSession appSess = (AppSession) sess
				.getAttribute(RAPID_BASE_APP_SESSION);
		if (appSess == null) {
			// 未登录状态访问需要登陆页面，作废原有session
			log.error("未登录状态访问需要登陆页面[{}]，作废原有session", url);
			sess.invalidate();
			resp.sendRedirect(loginUrl + "?target=" + target);
			return;
		}

		if (!ObjectUtils.equals(appSess.getSessionType(), sessionType)) {
			// 未登录状态访问需要登陆页面，作废原有session
			log.debug("session类型不匹配，实际类型[{}], 需要类型[{}]，作废原有session",
					appSess.getSessionType(), sessionType);
			sess.invalidate();
			resp.sendRedirect(loginUrl + "?target=" + target);
			return;
		}

		if (!appSess.hasUrlAuth(url)) {
			log.error("未授权状态访问页面[{}]，作废原有session", url);
			sess.invalidate();
			resp.sendRedirect(denyAuthRedirectUrl);
			return;
		}

		appSessSet.set(appSess);
		try {
			chain.doFilter(req, resp);
		} finally {
			appSessSet.remove();
		}

	}

	private String renderParam(HttpServletRequest req, String url) {
		String q = req.getQueryString();
		String s = "";
		if (StringUtils.isNotBlank(q)) {
			s = "?" + q;
		}
		return url + s;
	}

	public static AppSession getAppSession() {
		return appSessSet.get();
	}

	public static void setAppSession(AppSession appSess) {
		HttpServletRequest req = reqSet.get();
		HttpSession sess = req.getSession();
		sess.setAttribute(RAPID_BASE_APP_SESSION, appSess);
		appSessSet.set(appSess);
	}
}
