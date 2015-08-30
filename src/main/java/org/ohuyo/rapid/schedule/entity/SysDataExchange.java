package org.ohuyo.rapid.schedule.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 系统间数据交换日志
 * @author rabbit
 *
 */
//@Entity
//@Table(name = "t_sys_data_exchange")
public class SysDataExchange {
	
	@Id
	@Column (name = "i_sys_data_exchange")
	private Long isysDataExchange;
	
	@Column (name = "request")
	private String request;
	
	@Column (name = "response")
	private String response;
	
	@Column (name = "target")
	private String target;
	
	@Column (name = "trans_time")
	private Date transTime;
	
	@Column (name = "upd_time")
	private Date updTime;
	
	@Column (name = "status")
	private String status;
	
	@Column (name = " record_num")
	private Integer recordNum;
	
	@Column (name = "exch_type")
	private String exchType;
	
	@Column (name = "file_name")
	private String fileName;
	
	@Column (name = "i_file")
	private Long ifile;
	
	@Column (name = "uuid")
	private String uuid;
	
	@Column (name = "proc_serv")
	private String procServ;
	
	@Column (name = "proc_stat")
	private String procStat;

	public Long getIsysDataExchange() {
		return isysDataExchange;
	}

	public void setIsysDataExchange(Long isysDataExchange) {
		this.isysDataExchange = isysDataExchange;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Date getTransTime() {
		return transTime;
	}

	public void setTransTime(Date transTime) {
		this.transTime = transTime;
	}

	public Date getUpdTime() {
		return updTime;
	}

	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getRecordNum() {
		return recordNum;
	}

	public void setRecordNum(Integer recordNum) {
		this.recordNum = recordNum;
	}

	public String getExchType() {
		return exchType;
	}

	public void setExchType(String exchType) {
		this.exchType = exchType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getIfile() {
		return ifile;
	}

	public void setIfile(Long ifile) {
		this.ifile = ifile;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getProcServ() {
		return procServ;
	}

	public void setProcServ(String procServ) {
		this.procServ = procServ;
	}

	public String getProcStat() {
		return procStat;
	}

	public void setProcStat(String procStat) {
		this.procStat = procStat;
	}

}
