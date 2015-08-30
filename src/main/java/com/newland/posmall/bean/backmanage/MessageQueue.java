package com.newland.posmall.bean.backmanage;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "t_msg_queue")
public class MessageQueue {
	
	@Id
	@TableGenerator(name = "i_queue", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_queue")
	@Column(name = "i_queue")
	private Long iqueue;
	
	@Column(name = "to_user")
	private String toUser;
	
	@Column(name = "from_user")
	private String fromUser;
	
	@Column(name = "notify_type")
	@Enumerated(EnumType.STRING)
	private NotifyType notifyType;
	
	@Column(name = "target")
	private String target;
	
	@Column (name = "subject")
	private String subject;
	
	@Basic(fetch=FetchType.LAZY)
    @Column(columnDefinition = "BLOB", name = "content", nullable=true)
	private byte[] content;
	
	@Column(name = "gen_time")
	private Date genTime;
	
	@Column(name = "upd_time")
	private Date updTime;
	
	@Column(name = "stat")
	@Enumerated(EnumType.STRING)
	private NotifyStat stat;
	
	@Basic(fetch=FetchType.LAZY)
    @Column(columnDefinition = "BLOB", name = "attachmentt", nullable=true)
	private byte[] attachment;
	
	@Column (name = "message")
	private String message;

	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Date getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}

	public Date getUpdTime() {
		return updTime;
	}

	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}

	public NotifyStat getStat() {
		return stat;
	}

	public void setStat(NotifyStat stat) {
		this.stat = stat;
	}


	public NotifyType getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(NotifyType notifyType) {
		this.notifyType = notifyType;
	}

	public Long getIqueue() {
		return iqueue;
	}

	public void setIqueue(Long iqueue) {
		this.iqueue = iqueue;
	}

	public byte[] getAttachment() {
		return attachment;
	}

	public void setAttachment(byte[] attachment) {
		this.attachment = attachment;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
