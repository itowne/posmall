/*
s * Created on 2004-7-11
 *
 * Project: jasine.base
 */
package com.newland.posmall.utils.formatter;

import java.text.DecimalFormat;


/**
 * NumberFormatter
 * Project: jasine.base
 * @author shen
 *
 * 2004-7-11
 */
public class NumberFormatter extends Formatter {
    String pattern = "0.0";

    public NumberFormatter(String name) {
        super(name);
    }

    public NumberFormatter() {
        super("NumberFormatter");
    }

    /**
     * @see com.jasine.format.Formatter#format(java.lang.Object)
     */
    public String format(Object aObject) {
        if (aObject == null) return null;
        DecimalFormat df = new DecimalFormat(pattern);
        if (aObject instanceof String) {
        	try {
        		aObject = unformat((String)aObject, "#0.0");
        	} catch (Exception e) {}
        }
        if (aObject instanceof Number) {
            try {

                return df.format(((Number)aObject).doubleValue());
            }  catch (Exception e) {
                throw new FormatterException(this, "格式化器异常", e);
            }
        }
        throw new FormatterException(this, "不支持数据类型:"+aObject.getClass().getName());
    }

    /**
     * @see com.jasine.format.Formatter#unformat(java.lang.String)
     */
    public Object unformat(String aString) {
        if (aString == null) return null;
        try {
            DecimalFormat df = new DecimalFormat(pattern);
            return df.parse(aString);
        } catch (Exception e) {
            throw new FormatterException(this, "格式化器异常", e);
        }
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

    public static String format(Number number, String pattern) {
    	DecimalFormat df = new DecimalFormat(pattern);
    	if (number == null) return "-";
    	else return df.format(number.doubleValue());
    }

    public static Number unformat(String number, String pattern) {
    	try {
    		DecimalFormat df = new DecimalFormat(pattern);
    		if (number == null || "".equals(number.trim())) return null;
    		else return df.parse(number.trim());
    	} catch (Exception e) {
    		return null;
    	}
    }
}
