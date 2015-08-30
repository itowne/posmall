package com.newland.posmall;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

public class Application {

	public static String getTemplatePath() {
		String temp = System.getProperty("TEMP_PATH");
		if (StringUtils.isBlank(temp))
			temp = "/tmp";
		File file = new File(temp);
		if (file.exists() == false) file.mkdirs();
		return temp;
	}

	public static boolean isDebug() {
		return false;
	}

}
