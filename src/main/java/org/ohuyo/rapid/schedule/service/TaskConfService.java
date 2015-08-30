package org.ohuyo.rapid.schedule.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.rapid.schedule.Enabled;
import org.ohuyo.rapid.schedule.TaskContext;
import org.ohuyo.rapid.schedule.dao.TaskConfDao;
import org.ohuyo.rapid.schedule.entity.TaskConf;
import org.springframework.stereotype.Service;

@Service
public class TaskConfService {

	@Resource
	private TaskConfDao taskConfDao;
	
	public void save(TaskConf task) {
		Date date = new Date();
		task.setGenTime(date);
		task.setUpdTime(date);
		task.setEnabledFlag(Enabled.FALSE);
		this.taskConfDao.save(task);
	}
	
	public void update(TaskConf task) {
		Date date = new Date();
		task.setUpdTime(date);
		this.taskConfDao.update(task);
	}
	
	public TaskConf findByName(String taskName) {
		return this.taskConfDao.find(taskName);
	}
	
	public TaskConf findById(Long id) {
		return this.taskConfDao.get(id);
	}
	
	public int lock(TaskContext ctx) {
		return this.taskConfDao.lock(ctx);
	}

	public List<TaskConf> findAll() {
		return this.taskConfDao.findAll();
	}
}
