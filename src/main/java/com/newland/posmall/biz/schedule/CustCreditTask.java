package com.newland.posmall.biz.schedule;

import java.util.Date;

import org.ohuyo.rapid.schedule.ResultData;
import org.ohuyo.rapid.schedule.Task;
import org.ohuyo.rapid.schedule.TaskContext;
import org.ohuyo.rapid.schedule.TaskType;

/**
 * 客户信用定时器
 * @author rabbit
 *
 */
public class CustCreditTask extends Task{

	protected CustCreditTask() {
		super("客户信用定时器", TaskType.BATCH);
	}

	@Override
	protected void runTask(Date lastDate, TaskContext ctx, ResultData data) throws Throwable {
		data.SUCCESS();
	}

}
