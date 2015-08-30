package com.newland.posmall.biz.schedule;

import java.util.Date;

import org.ohuyo.rapid.schedule.ResultData;
import org.ohuyo.rapid.schedule.Task;
import org.ohuyo.rapid.schedule.TaskContext;
import org.ohuyo.rapid.schedule.TaskType;

public class CustLogisticTask extends Task {
	
	public CustLogisticTask(){
		super("客户物流单单状态定时任务", TaskType.BATCH);
	}

	@Override
	protected void runTask(Date lastDate, TaskContext ctx, ResultData newParam) throws Throwable {
		// TODO Auto-generated method stub

	}

}
