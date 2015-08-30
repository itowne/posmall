package com.newland.posmall.message;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.ohuyo.rapid.base.entity.MailContent;
import org.springframework.beans.factory.annotation.Autowired;

import com.newland.posmall.BeanTest;
import com.newland.posmall.base.exception.BizException;
import com.newland.posmall.base.service.ElecAgmtService;
import com.newland.posmall.base.service.EmailService;
import com.newland.posmall.bean.basebusi.Tagmt;

public class EmailServiceTest extends BeanTest{
	
	@Autowired
	private EmailService emailService;
	@Autowired
	ElecAgmtService elecagmtService;

	@Test
	public void test() throws BizException{
		MailContent content = new MailContent();
		content.setSubject("协议附件测试");
		content.setContent("详见附件");
		List<File> files = new ArrayList<File>();
		Tagmt agmt = this.elecagmtService.getTagmt(10001L);
		File file = this.elecagmtService.generate(agmt);
		files.add(file);
		content.setAttachFiles(files);
		this.emailService.sendMail(content, "170871697@qq.com");
		//this.emailService.sendMail(content, "xieqing@newlandcomputer.com");
	}
}
