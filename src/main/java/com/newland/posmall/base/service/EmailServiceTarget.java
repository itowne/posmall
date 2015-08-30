package com.newland.posmall.base.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.ohuyo.rapid.base.entity.MailContent;
import org.ohuyo.rapid.base.entity.MailServerInfo;
import org.ohuyo.rapid.base.entity.TsysParam;
import org.ohuyo.rapid.base.service.MailSender;
import org.ohuyo.rapid.base.service.TsysParamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.jdbc.StringUtils;
import com.newland.posmall.base.exception.BizErrCode;
import com.newland.posmall.base.exception.BizException;
import com.newland.posmall.bean.backmanage.MessageQueue;
import com.newland.posmall.bean.backmanage.NotifyStat;
import com.newland.posmall.bean.backmanage.NotifyType;
import com.newland.posmall.bean.dict.SysParamType;

@Service("emailServiceTarget")
public class EmailServiceTarget implements InitializingBean {

	private MailServerInfo mailServerInfo;

	private static Logger logger = LoggerFactory.getLogger(EmailService.class);
	
	@Autowired
	private MessageQueueService queueService;
	
	@Autowired
	private TsysParamService sysParamService;

	@Transactional
	public void sendMail(MailContent content, String tgtAddr) throws BizException{
		MessageQueue queue = new MessageQueue();
		queue.setTarget(tgtAddr);
		queue.setSubject(content.getSubject());
		queue.setToUser(tgtAddr);
		try {
			queue.setContent(StringUtils.getBytes(content.getContent(), "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			logger.error("字符串转换错误", e1);
			queue.setContent(StringUtils.getBytes(content.getContent()));
		}
		queue.setNotifyType(NotifyType.EMAIL);
		queue.setStat(NotifyStat.PENDING);
		queue.setAttachment(objectToByteArray(content.getAttachFiles()));
		this.queueService.save(queue);
		try{
			MailSender.sendHtmlMail(mailServerInfo, content, tgtAddr);
			queue.setStat(NotifyStat.SUCCESS);
		}catch(Throwable e){
			logger.error("发送邮件失败", e);
			queue.setStat(NotifyStat.ERROR);
			queue.setMessage(e.getMessage());
			throw new BizException(BizErrCode.SEND_MAIL_ERR, "发送邮件失败", e);
		}finally{
			this.queueService.update(queue);
		}
	}
	
	@Transactional
	public void retry(MessageQueue queue){
		if (queue.getGenTime().compareTo(new Date()) < 1){
			queue.setStat(NotifyStat.CANCEL);
		}
		MailContent content = new MailContent();
		try {
			content.setContent(new String(queue.getContent(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			content.setContent(new String(queue.getContent()));
		}
		content.setSubject(content.getSubject());
		String tgtAddr = queue.getTarget();
		content.setAttachFiles((List<File>)this.byteArrayToObject(queue.getAttachment()));
		try{
			MailSender.sendHtmlMail(mailServerInfo, content, tgtAddr);
			queue.setStat(NotifyStat.SUCCESS);
		}catch(Exception e){
			queue.setStat(NotifyStat.ERROR);
			queue.setMessage(e.getMessage());
		}finally{
			this.queueService.update(queue);
		}
	}

	public static byte[] objectToByteArray(Object obj) {
		byte[] bytes = null;
		ByteArrayOutputStream bo = null;
		ObjectOutputStream oo = null;
		try {
			bo = new ByteArrayOutputStream();
			oo = new ObjectOutputStream(bo);
			oo.writeObject(obj);
			bytes = bo.toByteArray();
		} catch (Exception e) {
			logger.error("对象转数组失败", e);
		}finally{
			IOUtils.closeQuietly(bo);
			IOUtils.closeQuietly(oo);
		}
		return bytes;
	}

	public static Object byteArrayToObject(byte[] data) {
		Object obj = null;
		ByteArrayInputStream bi = null;
		ObjectInputStream oi = null;
		try {
			bi = new ByteArrayInputStream(data);
			oi = new ObjectInputStream(bi);
			obj = oi.readObject();
		} catch (Exception e) {
			logger.error("数组转对象失败", e);
		}finally{
			IOUtils.closeQuietly(bi);
			IOUtils.closeQuietly(oi);
		}
		return obj;
	}

	/**
	 * 发送邮件
	 * 
	 * @param content
	 * @param subject
	 * @param toAddr
	 * @throws BizException
	 */
	public void sendEmail(String content, String subject, String toAddr)
			throws BizException {
		MailContent mctx = new MailContent();
		mctx.setContent(content);
		mctx.setSubject(subject);
		this.sendMail(mctx, toAddr);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO 需要改为从系统参数表中读取
		TsysParam param = this.sysParamService.getTsysParam(SysParamType.EMAIL_CONF.name(), "FROM_ADDR");
		if (param == null) throw new RuntimeException("邮件发送人未配置");
		String fromAddr = param.getValue();
		String userName = fromAddr.substring(0, fromAddr.indexOf("@"));
		param = this.sysParamService.getTsysParam(SysParamType.EMAIL_CONF.name(), "MAIL_PWD");
		if (param == null) throw new RuntimeException("邮件发送人未配置");
		String passwd = param.getValue();
		mailServerInfo = new MailServerInfo();
		mailServerInfo.setFromAddress(fromAddr);
		mailServerInfo.setMailServerHost("email.newlandcomputer.com");
		mailServerInfo.setMailServerPort("25");
		mailServerInfo.setUserName(userName);
		mailServerInfo.setPassword(passwd);
		mailServerInfo.setValidate(true);
	}

}
