package org.ohuyo.rapid.base.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 后台人员与后台人员角色的映射表
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_user_userrole")
public class TuserUserrole implements Serializable {

	private static final long serialVersionUID = -9187917966003339059L;
	
	@Id
	@Column(name = "i_user")
	private Long iuser;//后台人员内部编号
	
	@Id
	@Column(name = "i_userrole")
	private Long iuserrole;//后台人员角色内部编号

	public Long getIuser() {
		return iuser;
	}

	public void setIuser(Long iuser) {
		this.iuser = iuser;
	}

	public Long getIuserrole() {
		return iuserrole;
	}

	public void setIuserrole(Long iuserrole) {
		this.iuserrole = iuserrole;
	}

	

	

	
       
}
