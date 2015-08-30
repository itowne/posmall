package com.newland.posmall.file;

import javax.annotation.Resource;

import org.junit.Test;

import com.newland.posmall.BeanTest;
import com.newland.posmall.biz.file.FileDownload;

public class LogisticTest extends BeanTest {

	@Resource
	private FileDownload fileDownload;
	
	@Test
	public void downTest() throws Exception{
		String fileName = "2014-10-22.txt";
		fileDownload.insertSalesOrd(fileName);
	}

}
