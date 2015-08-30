package org.ohuyo.rapid.base.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.ohuyo.rapid.base.Auth;
import org.ohuyo.rapid.base.dist.AuthType;

/**
 * 系统人员权限表
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_sysauth")
public class Tsysauth implements Serializable, Auth  {

	private static final long serialVersionUID = -9187917966003339059L;

	@Id
	@TableGenerator(name = "i_sysauth", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_sysauth")
	@Column(name = "i_sysauth")
	private Long isysauth;// 内部编号

	@Column(name = "name")
	private String name;// 权限名称

	@Column(name = "code")
	private String code;// 权限代码

	@Column(name = "url")
	private String url;// 地址

	@Column(name = "parent_i_sysauth")
	private Long parentIsysauth;// 父id

	@Column(name = "gen_time", updatable = false)
	private Date genTime;// 生成时间

	@Column(name = "upd_time")
	private Date updTime;// 更新时间

	@Version
	private Long version;// 版本号

	@Column(name = "auth_type")
	@Enumerated(EnumType.STRING)
	private AuthType authType;// 权限类
	
	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, targetEntity = Tsysrole.class, mappedBy = "authSet")
	private Set<Tsysrole> roleSet;

	public Long getIsysauth() {
		return isysauth;
	}

	public void setIsysauth(Long isysauth) {
		this.isysauth = isysauth;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getParentIsysauth() {
		return parentIsysauth;
	}

	public void setParentIsysauth(Long parentIsysauth) {
		this.parentIsysauth = parentIsysauth;
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

	public AuthType getAuthType() {
		return authType;
	}

	public void setAuthType(AuthType authType) {
		this.authType = authType;
	}
	
	
	
	@Override
	public Long getIauth() {
		return this.isysauth;
	}

	@Override
	public Long getParentIauth() {
		return this.parentIsysauth;
	}

	public Set<Tsysrole> getRoleSet() {
		return roleSet;
	}

	public void setRoleSet(Set<Tsysrole> roleSet) {
		this.roleSet = roleSet;
	}

	

}