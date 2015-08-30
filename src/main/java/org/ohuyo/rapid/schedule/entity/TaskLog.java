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

import org.ohuyo.rapid.schedule.ExecType;
import org.ohuyo.rapid.schedule.TaskStat;
/**
 * 任务执行日志
 * @author rabbit
 *
 */
@Entity
@Table (name = "t_task_log")
public class TaskLog {
	
	@Id
	@TableGenerator(name = "i_task_log", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_task_log")
	@Column (name = "i_task_log")
	Long itaskLog;
	
	@Column (name = "i_task")
	Long itask;
	
	@Column (name = "start_time")
	Date startTime;
	
	@Column (name = "end_time")
	Date endTime;
	
	@Column (name = "task_no")
	String taskNo;
	
	@Column (name = "task_name")
	String taskName;
	
	@Column (name = "service")
	String trigger;
	
	@Column (name = "status")
	@Enumerated(EnumType.STRING)
	TaskStat status;
	
	@Column (name = "i_user")
	Long iuser;
	
	@Column (name = "user_name")
	String userName;
	
	@Column (name = "err_msg")
	String errMsg;
	
	@Column (name = "exec_type")
	@Enumerated(EnumType.STRING)
	ExecType execType;

	public Long getItaskLog() {
		return itaskLog;
	}

	public void setItaskLog(Long itaskLog) {
		this.itaskLog = itaskLog;
	}

	public Long getItask() {
		return itask;
	}

	public void setItask(Long itask) {
		this.itask = itask;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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

	public String getTrigger() {
		return trigger;
	}

	public void setTrigger(String trigger) {
		this.trigger = trigger;
	}


	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public Long getIuser() {
		return iuser;
	}

	public void setIuser(Long iuser) {
		this.iuser = iuser;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setStatus(TaskStat status) {
		this.status = status;
	}

	public ExecType getExecType() {
		return execType;
	}

	public void setExecType(ExecType execType) {
		this.execType = execType;
	}

	public TaskStat getStatus() {
		return status;
	}

}
