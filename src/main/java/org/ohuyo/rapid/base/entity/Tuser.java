package org.ohuyo.rapid.base.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Type;
import org.ohuyo.rapid.base.User;

import com.newland.posmall.bean.backmanage.TuserSub;

/**
 * 用户表
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_user")
public class Tuser implements Serializable, User {

	private static final long serialVersionUID = -9187917966003339059L;

	@Id
	@TableGenerator(name = "i_user", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_user")
	@Column(name = "i_user")
	private Long iuser;// 内部编号

	@Column(name = "login_name")
	private String loginName;// 登录名

	@Column(name = "password")
	private String password;// 密码

	@Column(name = "gen_time", updatable = false)
	private Date genTime;// 生成时间

	@Column(name = "upd_time")
	private Date updTime;// 更新时间

	@Version
	private Long version;// 版本号

	@Column(name = "del_flag")
	@Type(type = "yes_no")
	private Boolean delFlag;

	@Transient
	private TuserSub tuserSub;

	public TuserSub getTuserSub() {
		return tuserSub;
	}

	public void setTuserSub(TuserSub tuserSub) {
		this.tuserSub = tuserSub;
	}

	public Long getIuser() {
		return iuser;
	}

	public void setIuser(Long iuser) {
		this.iuser = iuser;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Boolean getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Boolean delFlag) {
		this.delFlag = delFlag;
	}

}
