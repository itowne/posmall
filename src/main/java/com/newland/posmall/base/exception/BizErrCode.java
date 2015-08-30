package com.newland.posmall.base.exception;
/**
 * 异常代码
 * @author rabbit
 *
 */
public class BizErrCode {
	
	/**
	 * 发送邮件失败
	 */
	public static final int SEND_MAIL_ERR = 1;
	
	/**
	 * 客户注册信息不存在
	 */
	public static final int CUSTREG_NOFOUND = 2;

	/**
	 * 客户状态异常
	 */
	public static final int CUSTREG_STATUS_ERR = 3;
	
	/**
	 * 后台用户不存在（用户不存在）
	 */
	public static final int USER_NOFOUND = 4;
	
	/**
	 * 缺少日排产计划
	 */
	public static final int D_PLAN_LACK = 5;
	
	/**
	 * 月排产计划已经存在
	 */
	public static final int M_PLAN_EXIST = 6;
	
	/**
	 * 月排产计划缺少月份
	 */
	public static final int M_MONTH_NEED = 7;
	
	/**
	 * 月排产计划缺少年份
	 */
	public static final int M_YEAR_NEED = 8;
	
	/**
	 * 月排产计划缺少产品
	 */
	public static final int M_PRODUCT_NEED = 9;
	
	/**
	 * 年排产计划已经存在
	 */
	public static final int Y_PLAN_EXIST = 10;
	
	/**
	 * 未制定排产计划
	 */
	public static final int PLAN_NEED = 11;
	
	/**
	 * 已审核
	 */
	public static final int EXAMED = 12;	
	
	/**
	 * 审核中
	 */
	public static final int EXAMING = 13;
	
	/**
	 * 未审核
	 */
	public static final int WAIT_FOR_EXAM = 14;
	/**
	 * 排产计划时间限制
	 */
	public static final int PDTPLAN_DATE_MONTH = 15;
	
	/**
	 * 排产计划已使用额度
	 */
	public static final int PDTPLAN_QUOTA_USED = 16;
	
	/**
	 * 系统异常
	 * eg.1.协议明细为空
	 *    2.消息通知人未配置
	 *    3.数据为空
	 *    4.协议缺少订货量
	 */
	public static int SYS_ERR = 0;
	
}
