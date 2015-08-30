package org.ohuyo.rapid.base.entity;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MailAuthenticator extends Authenticator {
	
	String userName;
	
	String password;
	
	public MailAuthenticator(String userName, String password){
		this.userName = userName;
		this.password = password;
	}

	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(this.userName, this.password);
	}
	

}
