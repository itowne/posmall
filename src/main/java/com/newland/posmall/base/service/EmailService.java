package com.newland.posmall.base.service;

import org.ohuyo.rapid.base.entity.MailContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newland.posmall.base.exception.BizException;
import com.newland.posmall.bean.backmanage.MessageQueue;

@Service("emailService")
public class EmailService {
	
	@Autowired
	EmailServiceTarget emailService;
	
	public void sendMail(MailContent content, String tgtAddr) throws BizException{
		EmailThread email = new EmailThread(this.emailService, content, tgtAddr);
		email.setDaemon(true);
		email.start();
	}
	
	public void retry(MessageQueue queue){
		this.emailService.retry(queue);
	}
	
	public void sendEmail(String content, String subject, String toAddr)
			throws BizException {
		MailContent mctx = new MailContent();
		mctx.setContent(content);
		mctx.setSubject(subject);
		this.sendMail(mctx, toAddr);
	}
	
	private static class EmailThread extends Thread{
		
		EmailServiceTarget target;
		
		MailContent content;
		
		String tgtAddr;
		
		public EmailThread(EmailServiceTarget target, MailContent content, String tgtAddr){
			this.target = target;
			this.content = content;
			this.tgtAddr = tgtAddr;
		}

		@Override
		public void run(){
			try {
				this.target.sendMail(content, tgtAddr);
			} catch (BizException e) {
				throw new RuntimeException(e);
			}
		}
		
		
	}

}
