package com.newland.posmall.bean.dict;

/**
 * 协议状态 
 */
public enum AgmtStatus {

	/**
	 * 协议已提交 
	 */
	AGMT_SUBMIT,
	/**
	 * 待付款 
	 */
	AGMT_QUOTA_CONFIRM,
	/**
	 * 已付款待审核
	 */
	PAY,
	/**
	 * 付款成功
	 */
	PAY_PASS,
	/**
	 * 已生效
	 */
	CONFIRM,
	/**
	 * 协议终止
	 */
	FINISH,
	/**
	 * 已撤销
	 */
	REVOKED,
	/**
	 * 变更申请审核中
	 */
	SUBMIT_CHANGE,
	/**
	 * 已变更
	 */
	HAVE_CHANGED;
}