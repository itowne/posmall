package com.newland.posmall.init;

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

import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

public class DatabaseService {
	
	public void initDatabase(ApplicationContext context) throws IOException {
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		Resource[] resources = resourcePatternResolver
				.getResources(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
						+ "init/**/*.sql");
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
