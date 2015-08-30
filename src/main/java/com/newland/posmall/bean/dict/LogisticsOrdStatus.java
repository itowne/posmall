package com.newland.posmall.bean.dict;

public enum LogisticsOrdStatus {

	/**
	 * 待审核
	 */
	WAIT_AUDIT,
	/**
	 * 已审核
	 */
	HAVE_AUDIT,
	/**
	 * 已撤销
	 */
	REVOKED,
	/**
	 * 已下销货单
	 */
	HAVE_LIBRARY,
	/**
	 * 已发货
	 */
	SHIPPED,
	/**
	 * 部分送达
	 */
	PARTIAL_DELIVERY,
	/**
	 * 全部送达
	 */
	ALL_SERVICE;

}
