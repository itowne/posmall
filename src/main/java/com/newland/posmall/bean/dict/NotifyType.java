package com.newland.posmall.bean.dict;

/**
 * 通知类型
 * @author rabbit
 *
 */
public enum NotifyType {
	/**
	 * <p>注册通知</p>
	 * <li>由客户发起，发送至商务管理部进行审核</li>
	 * <li>需要无指定收件人</li>
	 * 
	 */
	REG_APPLY_NOTIFY,
	/**
	 * <p>客户注册审核通知</p
	 * <li>由商务部发起至客户邮箱</li>
	 */
	REG_AUDIT_NOTIFY,
	/**
	 * <p>协议提交通知</p>
	 * <li>由客户发起到事业部</li>
	 * <li>需指定收件人</li>
	 */
	AGMT_APPLY_NOTIFY,
	/**
	 * <p>协义确认通知</p>
	 * <li>事业部确认额度后发送到商务部</li>
	 * <li>需要指定收件人</li>
	 */
	AGMT_CONFIRM_NOTIFY,
	/**
	 * <p>协议审核结果通知</p>
	 * <li>由商务部发起到客户邮箱</li>
	 */
	AGMT_AUDIT_NOTIFY,
	/**
	 * <p>付款通知</p>
	 * <li>协议保证金付款通知,由系统发起</li>
	 * <li>订单付款通知</li>
	 * <li>物流付款通知
	 */
	PAY_NOTIFY,
	/**
	 * <p>订单支付，客户凭证上传通知</p>
	 * <li>由客户发起至账务部</li>
	 * <li>需要指定收件人</li>
	 */
	CUST_PAY_NOTIFY,
	/**
	 * <p>付款审核通知</p>
	 * <li>由账务部发起至商务部</li>
	 * <li>需指定收件人</li>
	 */
	PAY_AUDIT_NOTIFY,
	/**
	 * <p>订单确认通知</p>
	 * <li>由商户部发起至客户邮箱</li>
	 */
	ORD_CONFIRM_NOTIFY,
	/**
	 * <p>物流扣费通知</p>
	 * <li>由商户部发起至客户邮箱</li>
	 */
	LOGISTICS_PAY_NOTIFY,
	/**
	 * <p>序号确认通知</p>
	 * <li>由商户部发起至生产部门确认序列号</li>
	 * <li>需要指定收件人</li>
	 */
	SERIAL_CONFIRM_NOTIFY,
	/**
	 * <p>客户点单通知</p>
	 * <li>由客户发送至销管</li>
	 */
	CUST_ORDER_NOTIFY,
	/**
	 * <p>保证金支付，客户凭证上传通知</p>
	 * <li>由客户发送至财务部门</li>
	 */
	DEPOSIT_PAY_NOTIFY,
	/**
	 * <p>客户制定物流单通知</p>
	 * <li>由客户发送至销管</li>
	 */
	CUST_LOGISTICS_NOTIFY,
	/**
	 * <p>协议变更审核结果通知</p>
	 * <li>由商务部发起到客户邮箱</li>
	 */
	AGMT_CHANGE_AUDIT_NOTIFY;
}
