package org.ohuyo.rapid.base.entity;

import java.io.Serializable;
import java.util.Properties;

import javax.mail.PasswordAuthentication;

import org.apache.commons.lang3.StringUtils;

public class MailServerInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 发送邮件的服务器的IP(或主机地址)
	private String mailServerHost;
	// 发送邮件的服务器的端口
	private String mailServerPort = "25";
	// 发件人邮箱地址
	private String fromAddress;
	// 登陆邮件发送服务器的用户名
	private String userName;
	// 登陆邮件发送服务器的密码
	private String password;
	// 是否需要身份验证
	private boolean validate = false;

	public Properties getProperties() {
		Properties p = new Properties();
		p.put("mail.smtp.host", this.mailServerHost);
		p.put("mail.smtp.port", this.mailServerPort);
		p.put("mail.smtp.auth", validate ? "true" : "false");
		return p;
	}

	public String getMailServerHost() {
		return mailServerHost;
	}

	public void setMailServerHost(String mailServerHost) {
		this.mailServerHost = mailServerHost;
	}

	public String getMailServerPort() {
		return mailServerPort;
	}

	public void setMailServerPort(String mailServerPort) {
		this.mailServerPort = mailServerPort;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isValidate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	public MailAuthenticator getAuthenticator(){
		if (validate) {
			if (StringUtils.isBlank(this.userName)){
				throw new RuntimeException("用户名为空");
			}
			if (StringUtils.isBlank(this.password)){
				throw new RuntimeException("密码为空");
			}
			return new MailAuthenticator(this.userName, this.password);
		}else{
			return null;
		}
	}

}
