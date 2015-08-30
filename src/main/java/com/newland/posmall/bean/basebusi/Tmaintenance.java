package com.newland.posmall.bean.basebusi;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * 维保协议表
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_maintenance")
public class Tmaintenance implements Serializable {

	private static final long serialVersionUID = -9187917966003339059L;

	@Id
	@TableGenerator(name = "i_maintenance", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_maintenance")
	@Column(name = "i_maintenance")
	private Long imaintenance; // 

	public Long getImaintenance() {
		return imaintenance;
	}

	public void setImaintenance(Long imaintenance) {
		this.imaintenance = imaintenance;
	}

	

}
