package org.ohuyo.rapid.base.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 客户与客户角色的映射表
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_cust_custrole")
public class TcustCustrole implements Serializable {

	private static final long serialVersionUID = -9187917966003339059L;
	
	@Id
	@Column(name = "i_cust")
	private Long icust;//客户内部编号
    
	@Id
	@Column(name = "i_custrole")
	private Long icustrole;//客户角色内部编号

	public Long getIcust() {
		return icust;
	}

	public void setIcust(Long icust) {
		this.icust = icust;
	}

	public Long getIcustrole() {
		return icustrole;
	}

	public void setIcustrole(Long icustrole) {
		this.icustrole = icustrole;
	}
       
}
