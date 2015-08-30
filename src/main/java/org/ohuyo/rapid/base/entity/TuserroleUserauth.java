package org.ohuyo.rapid.base.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 后台人员和后台人员角色映射表
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_userrole_userauth")
public class TuserroleUserauth implements Serializable {

	private static final long serialVersionUID = -9187917966003339059L;
	
	@Id
	@Column(name = "i_userrole")
	private Long iuserrole;//后台人员角色内部编号
    
	@Column(name = "i_userauth")
	private Long iuserauth;//后台人员权限内部编号

	public Long getIuserrole() {
		return iuserrole;
	}

	public void setIuserrole(Long iuserrole) {
		this.iuserrole = iuserrole;
	}

	public Long getIuserauth() {
		return iuserauth;
	}

	public void setIuserauth(Long iuserauth) {
		this.iuserauth = iuserauth;
	}

	

       
}
