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
 * 后台人员权限表
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_userauth")
public class Tuserauth implements Serializable , Auth  {

	private static final long serialVersionUID = -9187917966003339059L;

	@Id
	@TableGenerator(name = "i_userauth", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_userauth")
	@Column(name = "i_userauth")
	private Long iuserauth;// 内部编号

	@Column(name = "name")
	private String name;// 权限名称

	@Column(name = "code")
	private String code;// 权限代码

	@Column(name = "url")
	private String url;// 地址

	@Column(name = "parent_i_userauth")
	private Long parentIuserauth;// 父id

	@Column(name = "gen_time", updatable = false)
	private Date genTime;// 生成时间

	@Column(name = "upd_time")
	private Date updTime;// 更新时间

	@Version
	private Long version;// 版本号

	@Column(name = "auth_type")
	@Enumerated(EnumType.STRING)
	private AuthType authType;// 权限类
	
	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, targetEntity = Tuserrole.class, mappedBy = "authSet")
	private Set<Tuserrole> roleSet;

	public Long getIuserauth() {
		return iuserauth;
	}

	public void setIuserauth(Long iuserauth) {
		this.iuserauth = iuserauth;
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

	public Long getParentIuserauth() {
		return parentIuserauth;
	}

	public void setParentIuserauth(Long parentIuserauth) {
		this.parentIuserauth = parentIuserauth;
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

	public Set<Tuserrole> getRoleSet() {
		return roleSet;
	}

	public void setRoleSet(Set<Tuserrole> roleSet) {
		this.roleSet = roleSet;
	}

	@Override
	public Long getIauth() {
		return this.iuserauth;
	}

	@Override
	public Long getParentIauth() {
		return this.parentIuserauth;
	}

	

}
