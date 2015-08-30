package org.ohuyo.rapid.schedule.biz;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.Order;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.entity.condition.TaskLogCondition;
import org.ohuyo.rapid.schedule.entity.TaskLog;
import org.ohuyo.rapid.schedule.service.TaskLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TaskLogBiz {

	@Resource
	private TaskLogService taskLogService;
	
	public List<TaskLog> queryByItask(Long itask) {
		TaskLog taskLog = new TaskLog();
		taskLog.setItask(itask);
		return this.taskLogService.find(taskLog);
	}
	
	public List<TaskLog> queryPageByItask(Long itask, Page page) {
		TaskLogCondition taskLogCondition = new TaskLogCondition();
		taskLogCondition.setItask(itask);
		taskLogCondition.addOrders(Order.desc("itaskLog"));
		return this.taskLogService.find(taskLogCondition,page);
	}
	
}
