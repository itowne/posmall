package org.ohuyo.rapid.base.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 客户角色与客户权限的映射表
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_custrole_custauth")
public class TcustroleCustauth implements Serializable {

	private static final long serialVersionUID = -9187917966003339059L;
	
	@Id
	@Column(name = "i_custrole")
	private Long icustrole;//客户角色内部编号
    
	@Column(name = "i_custauth")
	private Long icustauth;//客户权限内部编号

	public Long getIcustrole() {
		return icustrole;
	}

	public void setIcustrole(Long icustrole) {
		this.icustrole = icustrole;
	}

	public Long getIcustauth() {
		return icustauth;
	}

	public void setIcustauth(Long icustauth) {
		this.icustauth = icustauth;
	}

	
       
}
