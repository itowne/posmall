package org.ohuyo.rapid.schedule;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.time.DateUtils;
import org.ohuyo.rapid.base.entity.Tsys;
import org.ohuyo.rapid.schedule.dao.TaskConfDao;
import org.ohuyo.rapid.schedule.dao.TaskLogDao;
import org.ohuyo.rapid.schedule.entity.TaskConf;
import org.ohuyo.rapid.schedule.entity.TaskLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class TaskManager implements BeanFactoryAware, InitializingBean{
	@Autowired
	private TaskConfDao taskConfDao;
	@Autowired
	private TaskLogDao taskLogDao;
	/**
	 * 系统日切时间点距离当日零时时间（小时）
	 */
	private int sysBatchTime = 2;
	
	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;
	
	private Map<String, Task> taskMap = new HashMap<String, Task>();
	
	
	Task getTaskByName(String taskName){
		return taskMap.get(taskName);
	}
	
	@Transactional
	public void trigger(String service, Tsys user, String manualDate){
		Task task = this.taskMap.get(service);
		if (task == null) throw new RuntimeException("未找到任务:[" + service + "]");
		TaskContext ctx = this.reloadCtx(task, manualDate);
		if (ctx.isEnabled()){
			if (ctx.isRunable() == false) throw new RuntimeException("任务当日不可重复运行");
			ctx.setExecuteType(ExecType.MANUAL);
			ctx.setUser(user);
			this.execute(task, ctx);
			return ;
		}
		throw new RuntimeException("该任务已停用");
	}

	
	/**
	 * 获取任务列表及任务说明
	 * @return
	 */
	public Map<String, String> getTaskList(){
		Map<String, String> names = new HashMap<String, String>();
		for (Map.Entry<String, Task> entry : taskMap.entrySet()){
			names.put(entry.getKey(), entry.getValue().taskName);
		}
		return names;
	}
	@Transactional
	void refresh(TaskContext ctx){
		TaskConf conf = ctx.getTaskConf();
		this.taskConfDao.update(conf);
	}
	
	@Transactional
	TaskContext reloadCtx(Task task, String manualDate){
		TaskConf conf = this.taskConfDao.find(task.service);
		if (conf == null) {
			this.register(task);
		}
		TaskContext ctx = new TaskContext();
		ctx.setTask(task);
		ctx.setTaskConf(conf);
		ctx.setManualDate(manualDate);
		return ctx;
	}
	
	private String getUUID(String service){
		String uuid = UUID.randomUUID().toString();
		return uuid.replace("-", "");
	}
	
	private BeanFactory beanFactory;

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean execute(Task task, TaskContext ctx) {
		if (ctx == null) {
			logger.error("任务配置不存在：[" + task.getTaskName() + "]");
			return false;
		}
		try{
			if (ctx.isEnabled()){
				if (lock(ctx)){
					logger.info("启动定时任务：[" + task.taskName + "@" + task.service + "]" );
					ctx.setBeginDate(new Date());
					ResultData data = new ResultData();
					Date rundate = ctx.getLastDate();
					try {
						Date lastDate = getLastDate(task,ctx);
						ctx.blockingPeriod();
						rundate = DateUtils.truncate(rundate, Calendar.DATE);
						ctx.increaseCount();
						task.runTask(rundate, ctx, data);
						ctx.setStatus(data.getStatus());
						ctx.setErrMsg(data.getMessage());
						ctx.setEndDate(new Date());
						this.saveLog(ctx);
						if (data.getStatus() == TaskStat.ERROR){
							if (data.getCause() != null){
								logger.error(data.getMessage(), data.getCause());
							}else{
								logger.error(data.getMessage());
							}
						}else
							ctx.setLastDate(lastDate);
						this.refresh(ctx);
						return true;
					}catch (Throwable e) {
						logger.error("执行失败:[" + task.getTaskName() + "]", e);
						if (data.isManual()){
							ctx.setStatus(data.getStatus());
							ctx.setErrMsg(data.getMessage());
						}else{
							ctx.setStatus(TaskStat.ERROR);
							ctx.setErrMsg(e.getMessage());
						}
						this.saveLog(ctx);
						this.refresh(ctx);
						return false;
					}
				}
				return false;
			}
		}catch(Throwable e){
			logger.error("执行失败:[" + task.getTaskName() + "]", e);
		}
		return false;
	}
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void saveLog(TaskContext ctx){
		TaskLog log = ctx.getTaskLog();
		this.taskLogDao.save(log);
	}
	
	private Date getLastDate(Task task, TaskContext ctx){
		switch (task.getTaskType()){
		case BATCH:
			Date lastDate = ctx.getLastDate();
			Date now = DateUtils.truncate(new Date(), Calendar.DATE);
			boolean round = calTime();
			if (round){
				while (lastDate.compareTo(now) <= 0){
					lastDate = DateUtils.addDays(lastDate, 1);
				}
			}else{
				while (lastDate.compareTo(now) < 0){
					lastDate = DateUtils.addDays(lastDate, 1);
				}
			}
			return lastDate;
		case REPEAT:
			return new Date();
		default:throw new RuntimeException("无法识别的操作");
		}
	}
	
	private boolean calTime() {
		long hour = Calendar.getInstance(Locale.CHINA).get(Calendar.HOUR_OF_DAY);
		if (hour > this.sysBatchTime && hour < 24-this.sysBatchTime) throw new RuntimeException("不在系统日切时间段");
		if (hour > 24 - this.sysBatchTime && hour > this.sysBatchTime){
			return true;
		}else{
			return false;
		}
	}


	Logger logger = LoggerFactory.getLogger(getClass());

	public final boolean lock(TaskContext ctx) {
		try{
			int lock = this.taskConfDao.lock(ctx);
			if (lock <=0 ) {
				logger.debug("锁定失败");
				return false;
			}
			return true;
		}catch(Throwable e){
			logger.error("锁定失败");
			return false;
		}
	}
	
	private Task register(Task task){
		TaskConf conf = this.taskConfDao.find(task.service);
		if (conf == null){
			logger.info("新增定时任务：[" + task.taskName + "@" + task.service + "]" );
			conf = new TaskConf();
			conf.setTaskName(task.taskName);
			conf.setService(task.service);
			conf.setGenTime(new Date());
			Date lastDate = DateUtils.truncate(new Date(), Calendar.DATE);
			conf.setLastDate(lastDate);
			conf.setTaskNo(getUUID(task.service));
			conf.setUpdTime(new Date());
			conf.setEndStat(TaskStat.SUCCESS);
			conf.setEnabledFlag(Enabled.TRUE);
			/*conf.setTaskType(task.getTaskType());
			conf.setSchedule(task.getScheduler());*/
			conf.setCount(0);
			this.taskConfDao.save(conf);
		}else{
			logger.info("已注册定时任务：[" + task.taskName + "@" + task.service + "]" );
		}
		return task;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		taskMap = ((ListableBeanFactory)beanFactory).getBeansOfType(Task.class);
		for (Map.Entry<String, Task> entry: taskMap.entrySet()){
			entry.getValue().setService(entry.getKey());
			this.register(entry.getValue());
		}
	}

}
