package com.newland.posmall.base;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.newland.posmall.BeanTest;
import com.newland.posmall.base.service.TnoSegCfgService;
import com.newland.posmall.base.service.TnoSegCfgService.PdtNoSeq;

public class TnoSegCfgTest extends BeanTest {
	
	@Autowired
	TnoSegCfgService cfgService;
	
	@Test
	public void test(){
		ThreadGroup group = new ThreadGroup("测试线程组");
		group.setDaemon(true);
		for (int i = 0 ; i < 100; i ++){
			TestThread tt = new TestThread(this.cfgService, group);
			tt.start();
		}
		while(group.activeCount() > 0){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static class TestThread extends Thread{
		
		TnoSegCfgService cfgService;
		
		public static Set<PdtNoSeq> set = new HashSet<PdtNoSeq>();
		
		public TestThread(TnoSegCfgService cfgService, ThreadGroup group){
			super(group, "测试线程");
			this.cfgService = cfgService;
		}

		@Override
		public void run() {
			for (int i = 0 ; i < 5 ; i++){
				StringBuffer sb = new StringBuffer();
				long begin = System.currentTimeMillis();
				sb.append("开始计获取：\n");
				PdtNoSeq seq = this.cfgService.getSeq(333, 1L);
				sb.append(seq.getStartSeq() + " - " + seq.getEndSeq());
				long end = System.currentTimeMillis();
				sb.append("结束获取，耗时:[" + (end-begin) + "ms]");
				if (set.contains(seq)) throw new RuntimeException("重复序列号");
				set.add(seq);
				System.err.println(sb.toString());
				try {
					sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		
		
	}
	
	public static void main(String[] args){
		Date date = DateUtils.setYears(new Date(), 2014);
		date = DateUtils.setMonths(date, 11);
		 date = DateUtils.setDays(date, 31);
		System.err.println(DateUtils.addDays(date, 1));
	}

}
