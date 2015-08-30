package com.newland.posmall.bean.dict;

/**
 * 支付类型
 */
public enum PayType {

	/**
	 * 保证金
	 */
	BAIL,
	/**
	 * 协议违约
	 */
	VIOLATE,
	/**
	 * 订单
	 */
	ORDER,
	/**
	 * 物流
	 */
	LOGISTICS,
	/**
	 * 仓管费
	 */
	WAREHOUSE,
	/**
	 * 续保费用
	 */
	RENEW_AMT,
	/**
	 * 保证金补交
	 */
	BAIL_SUPPLEMENT;
}
