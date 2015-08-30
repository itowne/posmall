package com.newland.posmall.file;



import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.ohuyo.rapid.file.CsvFileTranslator;

import com.newland.posmall.BeanTest;
import com.newland.posmall.file.bean.TestBean;

public class FileTranslatorTest extends BeanTest {
	
	

	@Test
	public void testFromObject() throws Throwable{
		CsvFileTranslator trans = new CsvFileTranslator("|");
		File file = new File("test.csv");
		List<TestBean> list = trans.fromFile(new FileReader(file), TestBean.class, false);
		for (TestBean test:list){
			System.err.println(test.getOne() + "," + test.getTwo() + "," + test.getThree() + "," + test.getFour());
		}
		file = new File("/shizn/test.txt");
		trans.fromObject(list, file, null);
	}

}
