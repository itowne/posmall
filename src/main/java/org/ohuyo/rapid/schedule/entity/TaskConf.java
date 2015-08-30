package org.ohuyo.rapid.schedule.entity;

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

import org.ohuyo.rapid.schedule.Enabled;
import org.ohuyo.rapid.schedule.TaskStat;
import org.ohuyo.rapid.schedule.TaskType;
/**
 * 任务配置类
 * @author rabbit
 *
 */
@Entity
@Table(name = "t_task_conf")
public class TaskConf {
	@Id
	@TableGenerator(name = "i_task", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_task")
	@Column (name = "i_task")
	Long itask;
	
	@Column (name = "task_no")
	String taskNo;
	
	@Column (name = "task_name")
	String taskName;
	
	@Column (name = "last_date")
	Date lastDate;
	
	@Column (name = "gen_time")
	Date genTime;
	
	//@Column (name = "task_type")
	//@Enumerated(EnumType.STRING)
	@Transient
	TaskType taskType;
	
	//@Column (name = "schedule")
	@Transient
	String schedule;
	
	@Column (name = "upd_time")
	Date updTime;
	
	@Column (name = "end_stat")
	@Enumerated(EnumType.STRING)
	TaskStat endStat;
	
	@Column (name = "service")
	String service;
	
	@Column (name = "err_msg")
	String errMsg;
	
	@Column (name = "count")
	Integer count;
	
	@Column (name = "enabled")
	@Enumerated(EnumType.STRING)
	Enabled enabledFlag;

	public Long getItask() {
		return itask;
	}

	public void setItask(Long itask) {
		this.itask = itask;
	}

	public String getTaskNo() {
		return taskNo;
	}

	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
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


	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public TaskStat getEndStat() {
		return endStat;
	}

	public void setEndStat(TaskStat endStat) {
		this.endStat = endStat;
	}

	public Enabled getEnabledFlag() {
		return enabledFlag;
	}

	public void setEnabledFlag(Enabled enabledFlag) {
		this.enabledFlag = enabledFlag;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public TaskType getTaskType() {
		return taskType;
	}

	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
}
