package org.ohuyo.rapid.schedule.service;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.entity.condition.TaskLogCondition;
import org.ohuyo.rapid.schedule.dao.TaskLogDao;
import org.ohuyo.rapid.schedule.entity.TaskLog;
import org.springframework.stereotype.Service;

@Service
public class TaskLogService {
	
	@Resource
	private TaskLogDao taskLogDao;
	
	public TaskLog find(Long id) {
		return this.taskLogDao.get(id);
	}
	
	public List<TaskLog> find(TaskLog taskLog) {
		return this.taskLogDao.find(taskLog);
	}
	
	public List<TaskLog> find(TaskLogCondition taskLongCondition, Page page) {
		return this.taskLogDao.find(taskLongCondition, page);
	}
	
	public void save(TaskLog log) {
		this.taskLogDao.save(log);
	}
	
	public void update(TaskLog log) {
		this.taskLogDao.update(log);
	}
}
