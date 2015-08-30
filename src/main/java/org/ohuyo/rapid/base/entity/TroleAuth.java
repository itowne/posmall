package org.ohuyo.rapid.base.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 角色权限表
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_role_auth")
public class TroleAuth implements Serializable {

	private static final long serialVersionUID = -9187917966003339059L;
	@Id
	@Column(name = "i_role")
	private Long irole;//角色内部编号
    
	@Column(name = "i_auth")
	private Long iauth;//权限内部编号

	public Long getIrole() {
		return irole;
	}

	public void setIrole(Long irole) {
		this.irole = irole;
	}

	public Long getIauth() {
		return iauth;
	}

	public void setIauth(Long iauth) {
		this.iauth = iauth;
	}
    
	
}
