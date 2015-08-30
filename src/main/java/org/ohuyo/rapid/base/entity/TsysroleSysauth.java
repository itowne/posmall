package org.ohuyo.rapid.base.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 系统人员角色与系统人员权限的映射表
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_sysrole_sysauth")
public class TsysroleSysauth implements Serializable {

	private static final long serialVersionUID = -9187917966003339059L;
	
	@Id
	@Column(name = "i_sysrole")
	private Long isysrole;//系统人员角色内部编号
    
	@Column(name = "i_sysauth")
	private Long isysauth;//系统人员权限内部编号

	public Long getIsysrole() {
		return isysrole;
	}

	public void setIsysrole(Long isysrole) {
		this.isysrole = isysrole;
	}

	public Long getIsysauth() {
		return isysauth;
	}

	public void setIsysauth(Long isysauth) {
		this.isysauth = isysauth;
	}

	

	
       
}
