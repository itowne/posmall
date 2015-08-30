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
 * 序列号表
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_sno_cfg")
public class TsnoCfg implements Serializable {

	private static final long serialVersionUID = -9187917966003339059L;

	@Id
	@TableGenerator(name = "no_id", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "no_id")
	@Column(name = "no_id")
	private Long noId; // 内部编号

	@Column(name = "sno_type")
	private String snoType; // 种类

	@Column(name = "max_val")
	private Integer maxVal; // 最大值

	public Long getNoId() {
		return noId;
	}

	public void setNoId(Long noId) {
		this.noId = noId;
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
