package com.newland.posmall.bean.common;

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

import com.newland.posmall.bean.dict.LogType;
import com.newland.posmall.bean.dict.OperType;
/**
 * 日志表
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_log")
public class Tlog implements Serializable {

	private static final long serialVersionUID = -9187917966003339059L;
	
	@Id
	@TableGenerator(name = "i_log", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_log")
	@Column(name = "i_log")
	private Long ilog;//内部编号
	
	@Column(name = "i_fk")
	private Long ifk;//外键
	
	@Column(name = "log_type")
	@Enumerated(EnumType.STRING)
	private LogType logType;//日志类型
	
	@Column(name = "oper_type")
	@Enumerated(EnumType.STRING)
	private OperType operType;//操作类型
	
	@Column(name = "memo")
	private String memo;//说明
	
	@Column(name = "pre_data")
	private String preData;//修改前数据
	
	@Column(name = "data")
	private String data;//修改后数据
	
	@Column(name = "gen_time",updatable = false)
	private Date genTime;//生成时间

	public Long getIlog() {
		return ilog;
	}

	public void setIlog(Long ilog) {
		this.ilog = ilog;
	}

	public Long getIfk() {
		return ifk;
	}

	public void setIfk(Long ifk) {
		this.ifk = ifk;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getPreData() {
		return preData;
	}

	public void setPreData(String preData) {
		this.preData = preData;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Date getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}

	public LogType getLogType() {
		return logType;
	}

	public void setLogType(LogType logType) {
		this.logType = logType;
	}

	public OperType getOperType() {
		return operType;
	}

	public void setOperType(OperType operType) {
		this.operType = operType;
	}


    
	
}
