package org.ohuyo.rapid.schedule;

import java.util.Date;

import org.springframework.stereotype.Service;
@Service
public class TestTask extends Task {

	protected TestTask() {
		super("测试");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void runTask(Date lastDate, TaskContext ctx, ResultData data) throws Throwable {
		System.err.println("运行了一下。。。。");
		data.WARN("有点问题");
	}

}
