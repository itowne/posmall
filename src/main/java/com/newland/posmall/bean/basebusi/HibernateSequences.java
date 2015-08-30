package com.newland.posmall.bean.basebusi;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Hibernate序列号表
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "hibernate_sequences")
public class HibernateSequences implements Serializable {

	private static final long serialVersionUID = -9187917966003339059L;

	@Id
	@Column(name = "sequence_name")
	private String sequenceName; // 序列名

	@Column(name = "sequence_next_hi_value")
	private Integer sequenceNextHiValue; // 序列值

	public String getSequenceName() {
		return sequenceName;
	}

	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}

	public Integer getSequenceNextHiValue() {
		return sequenceNextHiValue;
	}

	public void setSequenceNextHiValue(Integer sequenceNextHiValue) {
		this.sequenceNextHiValue = sequenceNextHiValue;
	}

}
