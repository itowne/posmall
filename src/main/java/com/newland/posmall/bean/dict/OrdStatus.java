package com.newland.posmall.bean.dict;

/**
 * 订单状态
 */
public enum OrdStatus {

	/**
	 * 待审核
	 */
	WAIT_AUDIT,
	/**
	 * 已审核
	 */
	HAVE_AUDIT,
	
	/**
	 * 订单完成
	 */
	SUCCESS,
	/**
	 * 已撤销
	 */
	REVOKED,
	/**
	 * 已终止
	 */
	STOP;
}
