package com.newland.posmall.bean.common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 省、市、县表
 *
 */
@Entity
@Table(name = "t_province")
public class Province implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5253609771923673366L;

	/**
	 * id
	 */
	@Id
	@Column(name = "prov_code")
	private String provCode;
	
	@Column(name = "close_flag")
	private Integer closeFlag;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "pre_prov_code")
	private String preProvCode;

	public String getProvCode() {
		return provCode;
	}

	public void setProvCode(String provCode) {
		this.provCode = provCode;
	}

	public Integer getCloseFlag() {
		return closeFlag;
	}

	public void setCloseFlag(Integer closeFlag) {
		this.closeFlag = closeFlag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPreProvCode() {
		return preProvCode;
	}

	public void setPreProvCode(String preProvCode) {
		this.preProvCode = preProvCode;
	}
	
}
