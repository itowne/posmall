package org.ohuyo.rapid.schedule.biz;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.rapid.schedule.entity.TaskConf;
import org.ohuyo.rapid.schedule.service.TaskConfService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TaskConfBiz {

	@Resource
	private TaskConfService taskConfService;
	
	public List<TaskConf> queryAll() {
		return this.taskConfService.findAll();
	}

	public TaskConf queryById(Long itask) {
		if(itask == null) {
			return null;
		}
		return this.taskConfService.findById(itask);
	}

	public void modifyTaskConf(TaskConf taskConf) {
		this.taskConfService.update(taskConf);
	}
}
