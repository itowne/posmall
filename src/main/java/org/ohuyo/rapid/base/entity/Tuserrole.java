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
 * 后台人员角色表
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_userrole")
public class Tuserrole implements Serializable {

	private static final long serialVersionUID = -9187917966003339059L;

	@Id
	@TableGenerator(name = "i_userrole", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_userrole")
	@Column(name = "i_userrole")
	private Long iuserrole;// 内部编号

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
	
	@ManyToMany(targetEntity =Tuserauth.class, fetch = FetchType.EAGER, cascade = {
		CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "t_userrole_userauth", joinColumns = { @JoinColumn(name = "i_userrole", referencedColumnName = "i_userrole", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "i_userauth", referencedColumnName = "i_userauth", nullable = false) })
	@Fetch(FetchMode.SUBSELECT)
	private Set<Tuserauth> authSet;

	public Long getIuserrole() {
		return iuserrole;
	}

	public void setIuserrole(Long iuserrole) {
		this.iuserrole = iuserrole;
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

	public Set<Tuserauth> getAuthSet() {
		return authSet;
	}

	public void setAuthSet(Set<Tuserauth> authSet) {
		this.authSet = authSet;
	}
	
	
	

}