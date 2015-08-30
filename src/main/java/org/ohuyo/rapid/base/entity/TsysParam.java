package org.ohuyo.rapid.base.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.newland.posmall.bean.dict.SysParamType;

/**
 * 系统参数表
 * @author rabbit
 *
 */
@Entity
@Table(name = "t_sys_param")
public class TsysParam implements Serializable {

	private static final long serialVersionUID = -1351056487179599734L;
    
	@Id
	@TableGenerator(name = "i_sys_param", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_sys_param")
	@Column(name = "i_sys_param")
	private Long isysParam;//内部编号
	
	@Column(name = "sys_param_type")
	@Enumerated(EnumType.STRING)
	private SysParamType sysParamType;//类型(dict)
	
	@Column(name = "name")
	private String name;//名称
	
	@Column(name = "code")
	private String code;//代码
	
	@Column(name = "value")
	private String value;//值
	
	@Column(name = "memo")
	private String memo;//备注
	
	@Column(name = "gen_time",updatable = false)
	private Date genTime;//生成时间
	
	@Column(name = "upd_time")
	private Date updTime;//更新时间
	
	/**
	 * 有效起始时间（字符串，格式HH:mm）
	 */
	@Column(name = "start_time")
	private String startTime;
	
	/**
	 * 有效起始时间对应的java.util.Date
	 */
	@Transient
	private Date startTimeDate;
	
	/**
	 * 有效结束时间（字符串，格式HH:mm）
	 */
	@Column(name = "end_time")
	private String endTime;
	
	/**
	 * 有效结束时间对应的java.util.Date
	 */
	@Transient
	private Date endTimeDate;
	
	@Version
	private Long version;//版本号

	public Long getIsysParam() {
		return isysParam;
	}

	public void setIsysParam(Long isysParam) {
		this.isysParam = isysParam;
	}

	public SysParamType getSysParamType() {
		return sysParamType;
	}

	public void setSysParamType(SysParamType sysParamType) {
		this.sysParamType = sysParamType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Date getStartTimeDate() {
		return startTimeDate;
	}

	public void setStartTimeDate(Date startTimeDate) {
		this.startTimeDate = startTimeDate;
	}

	public Date getEndTimeDate() {
		return endTimeDate;
	}

	public void setEndTimeDate(Date endTimeDate) {
		this.endTimeDate = endTimeDate;
	}

}
