package com.newland.posmall.bean.dict;

/**
 * 产品续保状态
 */
public enum RenewStatus {

	/**
	 * 待审核
	 */
	WAIT_AUDIT,
	/**
	 * 已生效
	 */
	AUDIT_PASS,
	/**
	 * 审核不通过
	 */
	AUDIT_NO_PASS,
	/**
	 * 已撤销
	 */
	REVOKED;
}
