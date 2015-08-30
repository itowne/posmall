package com.newland.posmall.identifier;

import org.junit.Test;
import org.ohuyo.rapid.schedule.TaskStat;
import org.springframework.beans.factory.annotation.Autowired;

import com.newland.posmall.BeanTest;

public class IdentifierServiceTest extends BeanTest {
	@Autowired
	IdentifierService idService;
	@Test
	public void test(){
		System.err.println(this.idService.genArgId());
		System.err.println(this.idService.genCustId());
		System.err.println(this.idService.genInterFlowId());
		System.err.println(this.idService.genMonthId());
		System.err.println(this.idService.genNotifyId());
		System.err.println(this.idService.genOrderId());
		System.err.println(this.idService.genPayId());
	}

}
