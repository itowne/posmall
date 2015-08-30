package com.newland.posmall.bean.dict;

/**
 * 支付状态
 */
public enum PayStatus {

	/**
	 * 待付款
	 */
	WAIT_PAY,
	/**
	 * 待审核
	 */
	WAIT_AUDIT,
	/**
	 * 已支付
	 */
	HAVE_PAY,
	/**
	 * 部分支付
	 */
	PART_PAY,
	/**
	 * 审核不通过
	 */
	NO_PASS,
	/**
	 * 已撤销
	 */
	REVOKED;
}
