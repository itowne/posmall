package org.ohuyo.rapid.schedule;

import java.util.Date;

import org.ohuyo.rapid.base.entity.Tsys;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * 任务基类
 * @author rabbit
 *
 */
public abstract class Task {
	
	/**
	 * 任务管理器
	 */
	@Autowired
	TaskManager taskManager;
	/**
	 * 服务名称
	 */
	String service;
	/**
	 * 任务名称
	 */
	String taskName;
	
	/**
	 * 
	 */
	String scheduler;
	
	/**
	 * 任务类型
	 */
	TaskType taskType = TaskType.REPEAT;
	
	protected Task(String descript){
		this(descript, 0);
	}

	
	protected Task(String descript, int interval){
		this.taskName = descript;
		this.taskType = TaskType.REPEAT;
		this.scheduler = String.valueOf(interval);
	}
	
	protected Task(String descript, String schedule){
		this.taskName = descript;
		this.taskType = TaskType.BATCH;
		this.scheduler = schedule;
	}
	
	protected Task(String descript, TaskType type){
		this.taskName = descript;
		this.taskType = type;
	}
	
	public final void runTask() {
		TaskContext ctx = this.taskManager.reloadCtx(this,null);
		Tsys sys = new Tsys();
		sys.setLoginName("SYSTEM");
		sys.setIsys(new Long(11));
		ctx.setUser(sys);
		this.taskManager.execute(this, ctx);
	}
	
	protected abstract void runTask(Date lastDate, TaskContext ctx, ResultData newParam) throws Throwable;


	public String getTaskName() {
		return taskName;
	}


	public TaskManager getTaskManager() {
		return taskManager;
	}



	void setTaskName(String taskName) {
		this.taskName = taskName;
	}


	void setTaskManager(TaskManager taskManager) {
		this.taskManager = taskManager;
	}

	public TaskType getTaskType() {
		return taskType;
	}

	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getScheduler() {
		return scheduler;
	}

	public void setScheduler(String scheduler) {
		this.scheduler = scheduler;
	}

}
