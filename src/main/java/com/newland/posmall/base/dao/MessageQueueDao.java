package com.newland.posmall.base.dao;

import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.newland.posmall.bean.backmanage.MessageQueue;
import com.newland.posmall.bean.backmanage.NotifyStat;
import com.newland.posmall.bean.backmanage.NotifyType;

@Repository
public class MessageQueueDao {

	@Autowired
	private BaseDao baseDao;
	
	public void save(MessageQueue queue){
		this.baseDao.getSession().save(queue);
	}
	
	public void update(MessageQueue queue){
		this.baseDao.getSession().update(queue);
	}
	
	@SuppressWarnings("unchecked")
	public List<MessageQueue> findByStat(NotifyType type, Set<NotifyStat> stats){
		String hql = "from " + MessageQueue.class.getName() + " mq where mq.notifyType = :type and mq.stat not in (:stats)";
		Query query = this.baseDao.getSession().createQuery(hql);
		query.setParameter("type", type);
		query.setParameterList("stats", stats);
		return query.list();
	}
	
}
