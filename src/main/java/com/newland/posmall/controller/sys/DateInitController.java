package com.newland.posmall.controller.sys;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller("sys.dateinit")
public class DateInitController {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping("/sys/date/dateInit.do")
	public String toInit() {
		return "/sys/date/initDateBase";
	}
	
	@RequestMapping("/sys/date/initDate.do")
	public String initDate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String sqlName = request.getParameter("sqlName");
		logger.info("sqlName" + sqlName);
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:init/database.xml");
		logger.info("开始初始化数据库...");
		while (context.isRunning() == false) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.initDatabase(context, sqlName);
		context.destroy();
		logger.info("初始化结束...");
		response.getWriter().println("0-初始化数据库成功！");
		return null;
	}
	
	private void initDatabase(ClassPathXmlApplicationContext context, String sqlName) throws IOException {
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		Resource[] resources = resourcePatternResolver
				.getResources(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
						+ "init/" + sqlName + ".sql");
		for (Resource res : resources) {
			final List<String> sqlList = new ArrayList<String>();
			try {
				InputStream in = res.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(
						in, "UTF-8"));
				String instring;
				String temp = "";
				while ((instring = br.readLine()) != null) {
					if (0 != instring.length()) {
						if (instring.trim().indexOf("-") == 0) continue;
						temp += instring;
						if (instring.indexOf(";") >= 0){
							sqlList.add(temp);
//							logger.info("sqlList添加内容:" + temp);
							temp = "";
						}
					}
				}
				br.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			SessionFactory sessionFactory = context.getBean(SessionFactory.class);
			sessionFactory.openSession().doWork(new Work(){
			
				@Override
				public void execute(Connection c) throws SQLException {
					try {
						Statement st = c.createStatement();
						for (String sql:sqlList){
							//st.addBatch(sql);st.executeBatch(); 
//							logger.info("sql:" + sql);
							st.executeUpdate(sql);
							System.out.println(sql);
						}
						st.close();
						c.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			});
			
		}
	}
}
