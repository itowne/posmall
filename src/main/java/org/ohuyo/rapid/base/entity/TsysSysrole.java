package org.ohuyo.rapid.base.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 系统管理员与系统管理角色的映射表
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_sys_sysrole")
public class TsysSysrole implements Serializable {

	private static final long serialVersionUID = -9187917966003339059L;
	
	@Id
	@Column(name = "i_sys")
	private Long isys;//系统人员内部编号
    
	@Column(name = "i_sysrole")
	private Long isysrole;//系统角色内部编号

	public Long getIsys() {
		return isys;
	}

	public void setIsys(Long isys) {
		this.isys = isys;
	}

	public Long getIsysrole() {
		return isysrole;
	}

	public void setIsysrole(Long isysrole) {
		this.isysrole = isysrole;
	}

	

	
       
}
