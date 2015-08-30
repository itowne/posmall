package com.newland.posmall.bean.common;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
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
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Type;

import com.newland.posmall.bean.dict.MsgTmpType;
/**
 * 消息模板表
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_msg_tmp")
public class TmsgTmp implements Serializable {

	private static final long serialVersionUID = -9187917966003339059L;
	
	@Id
	@TableGenerator(name = "i_msg_tmp", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_msg_tmp")
	@Column(name = "i_msg_tmp")
	private Long imsgTmp;//主键
    
	@Column(name = "tmp_type")
	@Enumerated(EnumType.STRING)
	private MsgTmpType tmpType;//模板类型
	
	@Column(name = "tmp_code")
	private String tmpCode;//模板代码
	
	@Column(name = "note_name")
	private String noteName;//模板名称
	
	@Column(name = "content")
	private String content;//模板内容

	@Column(name = "gen_time",updatable = false)
	private Date genTime;//生成时间
	
	@Column(name = "upd_time")
	private Date updTime;//更新时间
	
	@Column(name = "del_flg")
	@Type(type = "yes_no")
	private Boolean delFlg;//删除标识
	
	@Version
	private Long version;//版本号

	public Long getImsgTmp() {
		return imsgTmp;
	}

	public void setImsgTmp(Long imsgTmp) {
		this.imsgTmp = imsgTmp;
	}

	public MsgTmpType getTmpType() {
		return tmpType;
	}

	public void setTmpType(MsgTmpType tmpType) {
		this.tmpType = tmpType;
	}

	public String getTmpCode() {
		return tmpCode;
	}

	public void setTmpCode(String tmpCode) {
		this.tmpCode = tmpCode;
	}

	public String getNoteName() {
		return noteName;
	}

	public void setNoteName(String noteName) {
		this.noteName = noteName;
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

	public Boolean getDelFlg() {
		return delFlg;
	}

	public void setDelFlg(Boolean delFlg) {
		this.delFlg = delFlg;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
    
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
