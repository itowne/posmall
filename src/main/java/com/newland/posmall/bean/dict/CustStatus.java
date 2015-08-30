package com.newland.posmall.bean.dict;

/**
 * 客户状态
 */
public enum CustStatus {

	/**
	 * 已注册
	 */
	REG,
	/**
	 * 待审核
	 */
	WAIT_AUDIT,
	/**
	 * 审核中
	 */
	AUDIT_ING,
	/**
	 * 审核通过
	 */
	AUDIT_PASS,
	/**
	 * 审核未通过
	 */
	AUDIT_NOPASS,
	/**
	 * 无效
	 */
	INVALID;
}
