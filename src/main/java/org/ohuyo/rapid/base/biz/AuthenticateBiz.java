package org.ohuyo.rapid.base.biz;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.lang3.StringUtils;
import org.ohuyo.rapid.base.AppSession;
import org.ohuyo.rapid.base.User;
import org.ohuyo.rapid.base.service.PasswordService;
import org.ohuyo.rapid.base.servlet.AppSessionFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.code.kaptcha.Constants;

@Service
public class AuthenticateBiz {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private PasswordService passwordService;

	/**
	 * 
	 * @param loginName
	 * @param password
	 * @param validCode
	 * @param user
	 * @param req
	 * @return
	 */
	public void authenticate(String loginName, String password,
			String validCode, User user, HttpServletRequest req,
			AppSession appSess) {

		HttpSession session = req.getSession();
		String sCode = (String) session
				.getAttribute(Constants.KAPTCHA_SESSION_KEY);
		if (!StringUtils.equals(sCode, validCode)) {
			logger.error("验证码错误，输入[" + validCode + "],session code[" + sCode + "]");
			throw new RuntimeException("验证码错误");
		}
		if (user == null) {
			logger.error("用户[{}]不存在", loginName);
			throw new RuntimeException("用户名或密码不正确");
		}
		String p;
		try {
			p = passwordService.decrypt(password);
		} catch (DecoderException e) {
			logger.error("用户[{}]密码编码格式不正确", loginName, e);
			throw new RuntimeException("用户名或密码不正确");
		}
		if (!StringUtils.equals(p, user.getPassword())) {
			logger.error("用户[{}]密码错误", loginName);
			throw new RuntimeException("用户名或密码不正确");
		}
		AppSessionFilter.setAppSession(appSess);
	}
}
