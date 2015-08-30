package org.ohuyo.rapid.base.service;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.ohuyo.rapid.base.entity.MailContent;
import org.ohuyo.rapid.base.entity.MailServerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.newland.posmall.Application;
import com.sun.mail.util.BASE64EncoderStream;

public class MailSender {

	static final Logger logger = LoggerFactory.getLogger(MailSender.class);

	public static boolean sendHtmlMail(MailServerInfo serverInfo,
			MailContent content, String tgtAddr) throws Exception{

		Properties pro = serverInfo.getProperties();
		pro.setProperty("mail.smtp.timeout", "10000");
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session.getDefaultInstance(pro,
				serverInfo.getAuthenticator());
		if (Application.isDebug()){
			sendMailSession.setDebug(true);
			sendMailSession.setDebugOut(System.out);
		}
		try {
			// 根据session创建一个邮件消息
			Message mailMessage = new MimeMessage(sendMailSession);
			// 创建邮件发送者地址
			Address from = new InternetAddress(serverInfo.getFromAddress());
			// 设置邮件消息的发送者
			mailMessage.setFrom(from);
			// 创建邮件的接收者地址，并设置到邮件消息中
			Address to = new InternetAddress(tgtAddr);
			// Message.RecipientType.TO属性表示接收者的类型为TO
			mailMessage.setRecipient(Message.RecipientType.TO, to);
			// 设置邮件消息的主题
			mailMessage.setSubject(content.getSubject());
			// 设置邮件消息发送的时间
			mailMessage.setSentDate(new Date());
			// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
			Multipart mainPart = new MimeMultipart();
			// 创建一个包含HTML内容的MimeBodyPart
			BodyPart html = new MimeBodyPart();
			// 设置HTML内容
			html.setContent(content.getContent(), "text/html; charset=utf-8");
			mainPart.addBodyPart(html);
			addAttch(mainPart, content.getAttachFiles());
			// 将MiniMultipart对象设置为邮件内容
			mailMessage.setContent(mainPart);
			mailMessage.saveChanges();
			// 发送邮件
			Transport.send(mailMessage, mailMessage.getAllRecipients());
			return true;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage(), ex);
		}
	}

	private static void addAttch(Multipart multiPart, List<File> files)
			throws MessagingException, UnsupportedEncodingException {
		if (CollectionUtils.isEmpty(files) ==false){
			for (File file : files) {
				if (file.exists() == false) continue;
				BodyPart bodyPart = new MimeBodyPart();
				DataSource source = new FileDataSource(file.getAbsolutePath());
				bodyPart.setDataHandler(new DataHandler(source));
				// 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
				String fileName = new String(BASE64EncoderStream.encode(file.getName().getBytes("gbk")));
				bodyPart.setFileName(("=?GBK?B?" + fileName + "?="));
				multiPart.addBodyPart(bodyPart);
	
			}
		}
	}
}
