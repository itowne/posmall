package com.newland.posmall.bean.basebusi;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Type;

import com.newland.posmall.bean.dict.NotifyType;

/**
 * 通知人配置表
 * @author rabbit
 *
 */
@Entity
@Table(name = "t_notify_cfg")
public class TnotifyCfg implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@TableGenerator(name = "i_notify_cfg", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_notify_cfg")
	@Column(name = "i_notify_cfg")
	private Long inotifyCfg;
	
	@Column (name = "notify_type")
	@Enumerated(EnumType.STRING)
	private NotifyType notifyType;
	
	@Column (name = "i_user")
	private Long iuser;
	
	@Column (name = "user_name")
	private String userName;
	
	@Column (name = "memo")
	private String memo;
	
	@Column (name = "gen_time")
	private Date genTime;
	
	@Column (name = "upd_time")
	private Date updTime;
	
	@Column (name = "del_flag")
	@Type(type = "yes_no")
	private Boolean delFlag;

	public Long getInotifyCfg() {
		return inotifyCfg;
	}

	public void setInotifyCfg(Long inotifyCfg) {
		this.inotifyCfg = inotifyCfg;
	}

	public NotifyType getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(NotifyType notifyType) {
		this.notifyType = notifyType;
	}

	public Long getIuser() {
		return iuser;
	}

	public void setIuser(Long iuser) {
		this.iuser = iuser;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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

	public Boolean getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Boolean delFlag) {
		this.delFlag = delFlag;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


}
