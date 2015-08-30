package org.ohuyo.rapid.base.entity;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public class MailContent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 邮件主题
	private String subject;
	// 邮件的文本内容
	private String content;
	// 邮件附件的文件名
	private List<File> attachFiles;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<File> getAttachFiles() {
		return attachFiles;
	}

	public void setAttachFiles(List<File> attachFiles) {
		this.attachFiles = attachFiles;
	}

}
