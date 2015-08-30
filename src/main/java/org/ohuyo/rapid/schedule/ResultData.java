package org.ohuyo.rapid.schedule;

public class ResultData {
	
	private TaskStat status = TaskStat.SUCCESS;
	
	private String message;
	
	private Throwable cause;
	
	private boolean manual = false;

	public void SUCCESS(String msg){
		this.status = TaskStat.SUCCESS;
		this.message = msg;
		manual = true;
	}
	/**
	 * 成功执行
	 */
	public void SUCCESS(){
		SUCCESS(null);
	}
	
	/**
	 * 发生错误
	 * @param msg
	 */
	public void ERROR(String msg){
		this.status = TaskStat.ERROR;
		this.message = msg;
		manual = true;
	}
	
	/**
	 * 发生错误
	 * @param e
	 */
	public void ERROR(Throwable e){
		ERROR(e.getMessage());
	}
	
	/**
	 * 
	 * @param msg
	 * @param e
	 */
	public void ERROR(String msg, Throwable e){
		ERROR(msg + ":[" + e.getMessage() + "]");
		this.cause = e;
	}
	
	public void WARN(String msg){
		this.status = TaskStat.WARN;
		this.message = msg;
		manual = true;
	}
	
	String getMessage(){
		return message;
	}
	
	TaskStat getStatus(){
		return status;
	}
	
	Throwable getCause(){
		return this.cause;
	}
	public boolean isManual() {
		return manual;
	}

}
