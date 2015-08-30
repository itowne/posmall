/*
 * Created on 2004-8-3
 *
 * Project: CashManagement
 */
package com.newland.posmall.utils.formatter;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DateFormatter
 * Project: CashManagement
 * @author shen
 *
 * 2004-8-3
 */
public class DateFormatter extends Formatter {
	private final static String[] patterns = {"yyyy-MM-dd",
								 "yyyyMMdd",
								 "yyyy/MM/dd",
								 "yy-MM-dd",
								 "yy/MM/dd",
								 "yyMMdd"};
    private String pattern = "yyyy/MM/dd";
    protected static Logger logger = LoggerFactory.getLogger(DateFormatter.class);
    public DateFormatter(String pattern) {
        super();
        this.setPattern(pattern);
    }

    public DateFormatter() {
        super("DateFormatter");
    }
    /**
     * @see com.jasine.format.Formatter#format(java.lang.Object)
     */
    public String format(Object aObject) {
        if (aObject == null) return null;
        try {
        	SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        	if (aObject instanceof Number) {
        		aObject = new Date(((Number)aObject).longValue());
        	} else if (aObject instanceof java.sql.Date) {
        		aObject = new Date(((java.sql.Date)aObject).getTime());
        	}
        	if (aObject instanceof java.util.Date || aObject instanceof java.util.Calendar) {
        		return sdf.format(aObject);
        	}

        	// 对非Date类型进行转换
            String theText = aObject.toString();
            aObject = unformat(aObject.toString());
            if (aObject == null) return theText;
            return sdf.format(aObject);
        } catch (Throwable e) {
            throw new FormatterException(this, "格式化器异常", e);
        }
    }

    /**
     * @see com.jasine.format.Formatter#format(java.lang.String)
     */
    public Object unformat(String aString) {
        if (aString == null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(aString);
        } catch (Exception e) {
            if(logger.isDebugEnabled())
                logger.debug("FORMAT_ERROR", e);
        }

        for (int i=0; i<patterns.length; i++) {
        	sdf.applyPattern(patterns[i]);
        	try {
        		return sdf.parse(aString);
        	} catch (Exception e) {
            if(logger.isDebugEnabled())
                logger.debug("FORMAT_ERROR", e);
            }
        }
        return null;
    }

    /**
     * @return Returns the pattern.
     */
    public String getPattern() {
        return pattern;
    }
    /**
     * @param pattern The pattern to set.
     */
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public static String format(Date date, String pattern) {
    	SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    	return sdf.format(date);
    }

    public static Date unformat(String value, String pattern) {
    	if (value == null || "".equals(value.trim())) return null;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(value);
        } catch (Exception e) {}

        for (int i=0; i<patterns.length; i++) {
        	sdf.applyPattern(patterns[i]);
        	try {
        		return sdf.parse(value);
        	} catch (Exception e) {}
        }
        return null;
    }

}
