package com.newland.posmall.biz.schedule;

import java.util.Date;
import java.util.List;

import org.ohuyo.rapid.schedule.ResultData;
import org.ohuyo.rapid.schedule.Task;
import org.ohuyo.rapid.schedule.TaskContext;
import org.ohuyo.rapid.schedule.TaskType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.newland.posmall.bean.basebusi.Tagmt;
import com.newland.posmall.biz.admin.CustagmtBiz;

@Service
public class AgmtInvildateTask extends Task {
	@Autowired
	private CustagmtBiz custagmtBiz;

	
	
	
	public AgmtInvildateTask(){
		super("协议终止定时任务", TaskType.REPEAT);
	}

	@Override
	protected void runTask(Date lastDate, TaskContext ctx, ResultData data)
			throws Throwable {
		List<Tagmt> agmts = this.custagmtBiz.queryExpireAgmt();
		if (CollectionUtils.isEmpty(agmts)) {
			data.SUCCESS("未查询到过期协议");
			return;
		}
		for (Tagmt agmt: agmts){
			this.custagmtBiz.invalidate(agmt, 1L, "SYSTEM");
		}
	}

}
