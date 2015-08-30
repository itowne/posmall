package org.ohuyo.rapid.base.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

/**
 * 数据字典表
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_dict")
public class Tdict implements Serializable {

	private static final long serialVersionUID = -9187917966003339059L;

	@Id
	@TableGenerator(name = "i_dict", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_dict")
	@Column(name = "i_dict")
	private Long idict;// 主键

	@Column(name = "dict_type")
	private String dictType;// 数据字典类型

	@Column(name = "code")
	private String code;// 代码

	@Column(name = "value")
	private String value;// 值

	@Column(name = "memo")
	private String memo;// 说明

	@Column(name = "gen_time", updatable = false)
	private Date genTime;// 生成时间

	@Column(name = "upd_time")
	private Date updTime;// 更新时间

	@Version
	private Long version;// 版本号

	public Long getIdict() {
		return idict;
	}

	public void setIdict(Long idict) {
		this.idict = idict;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getDictType() {
		return dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	public Date getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}

	public Date getUpdTime() {
		return updTime;
	}

	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}

}
