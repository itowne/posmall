package org.ohuyo.rapid.base.entity;

/**
 * Ajax结果
 */
public class AjaxResult {

	public static final String STATUS_CODE_OK = "200";
	public static final String STATUS_CODE_NO = "300";
	public static final String MSG_OK = "操作成功";
	public static final String MSG_ADD = "添加成功";
	public static final String MSG_UPDATE = "修改成功";
	public static final String MSG_DELETE = "删除成功";
	public static final String MSG_FAILED = "操作失败";
	public static final String CALL_BACK_TYPE_CLOSE_CURRENT = "closeCurrent";
	public static final String CALL_BACK_TYPE_FORWARD = "forward";
	
	private String statusCode = STATUS_CODE_OK;
	private String message = MSG_OK;
	private String navTabId = "";
	private String rel = "";
	private String callbackType = "";
	private String forwardUrl = "";

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getNavTabId() {
		return navTabId;
	}

	public void setNavTabId(String navTabId) {
		this.navTabId = navTabId;
	}

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	public String getCallbackType() {
		return callbackType;
	}

	public void setCallbackType(String callbackType) {
		this.callbackType = callbackType;
	}

	public String getForwardUrl() {
		return forwardUrl;
	}

	public void setForwardUrl(String forwardUrl) {
		this.forwardUrl = forwardUrl;
	}

}
