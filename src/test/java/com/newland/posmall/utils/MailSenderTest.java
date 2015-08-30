package com.newland.posmall.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.ohuyo.rapid.base.entity.MailContent;
import org.ohuyo.rapid.base.entity.MailServerInfo;
import org.ohuyo.rapid.base.service.MailSender;

public class MailSenderTest {
	
	public static void main(String[] args) throws Exception{
		MailServerInfo server = new MailServerInfo();
		server.setFromAddress("shizn@newlandpayment.com");
		server.setMailServerHost("email.newlandcomputer.com");
		server.setMailServerPort("25");
		server.setUserName("shizn");
		server.setPassword("s1y2n3c4m5a6@");
		server.setValidate(true);
		MailContent content = new MailContent();
		content.setContent("测试");
		content.setSubject("测试");
		List<File> files = new ArrayList<File>();
/*		File file = new File("c:/shizn/中文的.ini");
		files.add(file);
		file = new File("c:/shizn/问题3.txt");
		files.add(file);
		file = new File("c:/shizn/业务需求说明书.doc");
		files.add(file);*/
		content.setAttachFiles(files);
		MailSender.sendHtmlMail(server, content, "13960902343@139.com");
	}

}
