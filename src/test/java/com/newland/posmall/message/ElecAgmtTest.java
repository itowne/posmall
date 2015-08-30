package com.newland.posmall.message;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.newland.posmall.BeanTest;
import com.newland.posmall.base.service.ElecAgmtService;
import com.newland.posmall.base.service.TagmtService;
import com.newland.posmall.bean.basebusi.Tagmt;

public class ElecAgmtTest extends BeanTest {
	@Autowired
	private ElecAgmtService elecagmtService;

	@Test
	public void test(){
		Tagmt agmt = this.elecagmtService.getTagmt(101L);
		this.elecagmtService.generate(agmt);
	}

}
