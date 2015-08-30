package com.newland.posmall.base.exception;
/**
 * 异常基础类
 * @author rabbit
 *
 */
public class BizException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int code;
	
	private String msg;
	
	public BizException(int code, String msg){
		super(String.valueOf(code) + msg);
		this.code = code;
		this.msg = msg;
	}
	
	public BizException(int code, String msg, Throwable cause){
		super(String.valueOf(code) + msg, cause);
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

}
