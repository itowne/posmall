package com.newland.posmall.schedule;

import org.junit.Test;
import org.ohuyo.rapid.base.entity.Tsys;
import org.ohuyo.rapid.base.entity.Tuser;
import org.ohuyo.rapid.schedule.TaskManager;
import org.springframework.beans.factory.annotation.Autowired;

import com.newland.posmall.BeanTest;

public class TaskTest extends BeanTest{
	@Autowired
	public TaskManager taskManager;
	
	//@Test
	public void test(){
		for (int i = 0 ; i < 10 ; i++){
			TestThread tt = new TestThread(this.taskManager);
			tt.start();
		}
		/*Tuser user = new Tuser();
		user.setLoginName("shizn");
		user.setIuser(new Long(0));
		this.taskManager.trigger("testTask", user);*/
		while(true){
			try {
				Thread.currentThread().sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static class TestThread extends Thread{
		TaskManager taskManager;
		public TestThread(TaskManager task){
			this.taskManager = task;
		}

		@Override
		public void run() {
			Tsys user = new Tsys();
			user.setLoginName("shizn");
			user.setIsys(new Long(0));
			this.taskManager.trigger("testTask", user , null);
		}
		
	}
	@Test
	public void testDown(){
		Tsys user = new Tsys();
		user.setLoginName("shizn");
		user.setIsys(new Long(0));
		this.taskManager.trigger("logisticSyncTask", user, null);
	}

}
