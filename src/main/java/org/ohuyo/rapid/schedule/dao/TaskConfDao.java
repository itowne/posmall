package org.ohuyo.rapid.schedule.dao;

import java.util.List;

import org.ohuyo.rapid.base.dao.BaseDao;
import org.ohuyo.rapid.schedule.TaskContext;
import org.ohuyo.rapid.schedule.TaskStat;
import org.ohuyo.rapid.schedule.entity.TaskConf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Repository
@Transactional
public class TaskConfDao {
	@Autowired
	BaseDao baseDao;

	public TaskConf find(String taskName) {
		TaskConf conf = new TaskConf();
		conf.setService(taskName);
		conf.setEndStat(null);
		List<TaskConf> list = this.baseDao.getHibernateDaoEx().findByExample(conf);
		if (CollectionUtils.isEmpty(list)) return null;
		return list.get(0);
	}
	
	public TaskConf get(Long id) {
		return (TaskConf) baseDao.getSession().get(TaskConf.class, id);
	}
	
	public List<TaskConf> findAll() {
		return this.baseDao.getHibernateDaoEx().find(TaskConf.class);
	}

	public void save(TaskConf task) {
		this.baseDao.getSession().save(task);
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
	public void update(TaskConf conf) {
		this.baseDao.getSession().update(conf);
		
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
	public int lock(TaskContext ctx) {
		int lock = this.baseDao.getHibernateDaoEx().executeUpdate("update " + TaskConf.class.getName() 
				+ " tc set tc.endStat = ? where tc.service = ? and tc.endStat != ?",TaskStat.RUNING, ctx.getTaskConf().getService(), TaskStat.RUNING);
		return lock;
	}
}
