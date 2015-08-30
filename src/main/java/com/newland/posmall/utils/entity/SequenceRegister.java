package com.newland.posmall.utils.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Index;

@Entity
@Table(name = "t_sno_cfg")
@org.hibernate.annotations.Table(appliesTo = "t_sno_cfg", indexes = { @Index(name = "idx_sno_cfg", columnNames = { "sno_type" }) })
public class SequenceRegister implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@TableGenerator(name = "sno_id", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sno_id")
	@Column(name = "sno_id")
	private Integer snoId;
	@Column(name = "sno_type", length = 50, nullable = false)
	private String snoType;
	@Column(name = "max_val")
	private Integer maxVal;

	public Integer getSnoId() {
		return snoId;
	}

	public void setSnoId(Integer snoId) {
		this.snoId = snoId;
	}

	public String getSnoType() {
		return snoType;
	}

	public void setSnoType(String snoType) {
		this.snoType = snoType;
	}

	public Integer getMaxVal() {
		return maxVal;
	}

	public void setMaxVal(Integer maxVal) {
		this.maxVal = maxVal;
	}

}
