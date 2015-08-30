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
 * 产品额度
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_pdt_quota")
public class TpdtQuota implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "i_pdt_quota", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_pdt_quota")
	@Column(name = "i_pdt_quota")
	private Long ipdtQuota;

	public Long getIpdtQuota() {
		return ipdtQuota;
	}

	public void setIpdtQuota(Long ipdtQuota) {
		this.ipdtQuota = ipdtQuota;
	}
	
    
}
