package com.newland.posmall.biz.schedule;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.ohuyo.rapid.schedule.ResultData;
import org.ohuyo.rapid.schedule.Task;
import org.ohuyo.rapid.schedule.TaskContext;
import org.ohuyo.rapid.schedule.TaskType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.newland.posmall.base.service.EmailService;
import com.newland.posmall.base.service.MessageQueueService;
import com.newland.posmall.bean.backmanage.MessageQueue;
import com.newland.posmall.bean.backmanage.NotifyStat;
import com.newland.posmall.bean.backmanage.NotifyType;

@Service
public class MessageQueueRefreshTask extends Task {
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private MessageQueueService queueService;

	protected MessageQueueRefreshTask() {
		super("邮件消息队列重发服务", TaskType.REPEAT);
	}

	@Override
	protected void runTask(Date lastDate, TaskContext ctx, ResultData data) throws Throwable {
		Set<NotifyStat> set = new HashSet<NotifyStat>();
		set.add(NotifyStat.SUCCESS);
		set.add(NotifyStat.CANCEL);
		List<MessageQueue> list = this.queueService.findByStat(NotifyType.EMAIL, set);
		if (CollectionUtils.isEmpty(list)){
			for (MessageQueue queue: list){
				this.emailService.retry(queue);
			}
		}
		data.SUCCESS();
	}

}
