package org.ohuyo.rapid.schedule.dao;

import java.util.List;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.dao.BaseDao;
import org.ohuyo.rapid.base.entity.condition.TaskLogCondition;
import org.ohuyo.rapid.schedule.entity.TaskLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Repository
@Transactional
public class TaskLogDao {
	@Autowired
	BaseDao baseDao;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void save(TaskLog log) {
		this.baseDao.getSession().save(log);
	}

	public void update(TaskLog log) {
		this.baseDao.getSession().update(log);
	}
	
	public TaskLog get(Long id) {
		return (TaskLog) this.baseDao.getSession().get(TaskLog.class, id);
	}
	
	public List<TaskLog> find(TaskLog taskLog) {
		List<TaskLog> list = this.baseDao.getHibernateDaoEx().findByExample(
				taskLog);
		if (CollectionUtils.isEmpty(list))
			return null;
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<TaskLog> find(TaskLogCondition taskLongCondition, Page page) {
		List<TaskLog> list = this.baseDao.getHibernateDaoEx().findByCriteriaEx(taskLongCondition, page);
		if (CollectionUtils.isEmpty(list))
			return null;
		return list;
	}
}
