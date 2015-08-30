package org.ohuyo.rapid.base.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;


/**
 * 系统人员角色表
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_sysrole")
public class Tsysrole implements Serializable {

	private static final long serialVersionUID = -9187917966003339059L;

	@Id
	@TableGenerator(name = "i_sysrole", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_sysrole")
	@Column(name = "i_sysrole")
	private Long isysrole;// 内部编号

	@Column(name = "name")
	private String name;// 名称

	@Column(name = "memo")
	private String memo;// 备注

	@Column(name = "gen_time", updatable = false)
	private Date genTime;// 生成时间

	@Column(name = "upd_time")
	private Date updTime;// 更新时间

	@Version
	private Long version;// 版本号
	
	@Column(name = "del_flag")
	@Type(type = "yes_no")
	private Boolean delFlag;//删除标志
	
	@ManyToMany(targetEntity =Tsysauth.class, fetch = FetchType.EAGER, cascade = {
		CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "t_sysrole_sysauth", joinColumns = { @JoinColumn(name = "i_sysrole", referencedColumnName = "i_sysrole", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "i_sysauth", referencedColumnName = "i_sysauth", nullable = false) })
	@Fetch(FetchMode.SUBSELECT)
	private Set<Tsysauth> authSet;

	public Long getIsysrole() {
		return isysrole;
	}

	public void setIsysrole(Long isysrole) {
		this.isysrole = isysrole;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Set<Tsysauth> getAuthSet() {
		return authSet;
	}

	public void setAuthSet(Set<Tsysauth> authSet) {
		this.authSet = authSet;
	}

	

	

}