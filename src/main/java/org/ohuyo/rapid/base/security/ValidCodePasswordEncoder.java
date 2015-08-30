package org.ohuyo.rapid.base.security;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.ohuyo.commons.servlet.filters.CommonToolsFilter;
import org.ohuyo.rapid.base.service.PasswordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.code.kaptcha.Constants;

/**
 * 
 * @author rabbit
 *
 */
@Service("validCodePasswordEncoder")
public class ValidCodePasswordEncoder implements PasswordEncoder {
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	RsaService rsaService;

	@Resource
	PasswordService passwordService;

	@Override
	public String encode(CharSequence rawPassword) {
		return rawPassword.toString();
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
//		HttpServletRequest req = (HttpServletRequest) CommonToolsFilter
//				.getRequset();
//		HttpSession session = req.getSession();
//		String sCode = (String) session
//				.getAttribute(Constants.KAPTCHA_SESSION_KEY);
//		logger.error("验证码");
//		if (sCode.equals(req.getParameter("valid")) == false) {
//			throw new RuntimeException("验证码错误");
//		}
//		String pw = passwordService.decrypt(encodedPassword);
//		return StringUtils.equals(pw, rawPassword);
		return true;
	}
}
