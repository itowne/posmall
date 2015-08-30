/*
 * Created on 2004-7-11
 *
 * Project: jasine.base
 */
package com.newland.posmall.utils.formatter;



/**
 * FormatterException
 * Project: jasine.base
 * @author shen
 *
 * 2004-7-11
 */
public class FormatterException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FormatterException(Formatter formatter, String message) {
	    super("格式化器:"+formatter.getName()+", 错误:"+message);
	}

	public FormatterException(Formatter formatter, String message, Throwable cause) {
	    super("格式化器:"+formatter.getName()+", 错误:"+message, cause);
	}
}
