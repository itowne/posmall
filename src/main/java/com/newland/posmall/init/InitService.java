package com.newland.posmall.init;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class InitService {

	Logger logger = LoggerFactory.getLogger(getClass());

	public void init() throws IOException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:database.xml");
		logger.info("开始初始化数据库...");
		while (context.isRunning() == false) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.initDatabase(context);
		context.destroy();
		logger.info("初始化结束...");
	}

	

	private void initDatabase(ClassPathXmlApplicationContext context) throws IOException {
		DatabaseService service = new DatabaseService();
		service.initDatabase(context);
	}



	public static void main(String[] args) throws IOException {
		InitService init = new InitService();
		init.init();
	}
}
