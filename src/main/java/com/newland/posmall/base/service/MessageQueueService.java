package com.newland.posmall.base.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newland.posmall.base.dao.MessageQueueDao;
import com.newland.posmall.bean.backmanage.MessageQueue;
import com.newland.posmall.bean.backmanage.NotifyStat;
import com.newland.posmall.bean.backmanage.NotifyType;

@Service
public class MessageQueueService {
	
	@Autowired
	private MessageQueueDao messageQueueDao;
	
	public void save(MessageQueue queue){
		queue.setGenTime(new Date());
		queue.setUpdTime(new Date());
		queue.setStat(NotifyStat.PENDING);
		this.messageQueueDao.save(queue);
	}
	
	public void update(MessageQueue queue){
		queue.setUpdTime(new Date());
		this.messageQueueDao.update(queue);
	}
	
	@Transactional
	public List<MessageQueue> findByStat(NotifyType type, Set<NotifyStat> notStat){
		return this.messageQueueDao.findByStat(type, notStat);
	}

}
