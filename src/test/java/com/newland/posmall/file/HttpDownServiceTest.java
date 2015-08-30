package com.newland.posmall.file;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.newland.posmall.BeanTest;
import com.newland.posmall.biz.schedule.service.HttpDownService;

public class HttpDownServiceTest extends BeanTest{
	
	@Autowired
	HttpDownService httpDownService;
	
	@Test
	public void test() throws Throwable{
		File file = httpDownService.download("2014-09-03.txt");
		Assert.assertTrue(file.exists());
	}
}
