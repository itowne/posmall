package org.ohuyo.rapid.schedule;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.ohuyo.rapid.base.entity.Tsys;
import org.ohuyo.rapid.schedule.entity.TaskConf;
import org.ohuyo.rapid.schedule.entity.TaskLog;

public class TaskContext {
	/**
	 * 数据库记录
	 */
	private TaskConf taskConf;
	/**
	 * 任务类
	 */
	private Task task;
	/**
	 * 执行来源
	 */
	private ExecType executeType = ExecType.SYSTEM;
	/**
	 * 触发用户，如果手工触发需要从controller传值
	 */
	private Tsys user;
	/**
	 * 任务开始时间
	 */
	private Date beginDate;
	/**
	 * 任务结束时间
	 */
	private Date endDate;

	/**
	 * 任务执行状态
	 */
	private TaskStat status = TaskStat.SUCCESS;
	/**
	 * 错误信息
	 */
	private String errMsg;
	/**
	 * 手动触发，指定日期
	 */
	private String manualDate;

	public void setTask(Task task) {
		this.task = task;
	}

	public void blockingPeriod() {
		// TODO Auto-generated method stub
		
	}

	public TaskConf getTaskConf() {
		taskConf.setErrMsg(errMsg);
		taskConf.setUpdTime(new Date());
		if (status != null)
			taskConf.setEndStat(status);
		return taskConf;
	}

	public void setTaskConf(TaskConf taskConf) {
		this.taskConf = taskConf;
	}

	public Task getTask() {
		return task;
	}

	public void setBeginDate(Date date) {
		this.beginDate = date;
		
	}


	public void setEndDate(Date date) {
		this.endDate = date;
		
	}


	public void setErrMsg(String message) {
		this.errMsg = message;
	}

	public TaskLog getTaskLog() {
		if (this.status == null) throw new RuntimeException("任务未执行");
		TaskLog log = new TaskLog();
		if (endDate != null)
			log.setEndTime(endDate);
		else
			log.setEndTime(new Date());
		log.setErrMsg(errMsg);
		log.setItask(this.taskConf.getItask());
		log.setStartTime(beginDate);
		log.setTaskName(task.taskName);
		log.setTaskNo(taskConf.getTaskNo());
		log.setTrigger(task.service);
		log.setExecType(this.executeType);
		log.setStatus(status);
		if (user != null){
			log.setIuser(user.getIsys());
			log.setUserName(user.getLoginName());
		}
		return log;
	}


	public void setExecuteType(ExecType executeType) {
		this.executeType = executeType;
	}

	public void setUser(Tsys user) {
		this.user = user;
	}

	public Date getEndDate() {
		return endDate;
	}

	public Date getLastDate() {
		return this.taskConf.getLastDate();
	}
	
	public void setLastDate(Date date){
		if (date != null)
			this.taskConf.setLastDate(DateUtils.truncate(date, Calendar.DATE));
	}
	
	public boolean isEnabled(){
		if (this.taskConf.getEnabledFlag() == Enabled.TRUE){
			return true;
		}
		return false;
	}
	
	public boolean isRunable(){
		if (task.getTaskType() == TaskType.BATCH){
			Date lastDate = this.taskConf.getLastDate();
			if (DateUtils.isSameDay(lastDate, new Date())){
				return false;
			}else{
				return true;
			}
		}
		return true;
	}

	public TaskStat getStatus() {
		return status;
	}

	public void setStatus(TaskStat status) {
		this.status = status;
	}

	void increaseCount() {
		this.taskConf.setCount(taskConf.getCount() + 1);
		
	}

	public TaskType getTaskType() {
		return this.task.getTaskType();
	}

	public String getManualDate() {
		return manualDate;
	}

	public void setManualDate(String manualDate) {
		this.manualDate = manualDate;
	}
	
	
}
