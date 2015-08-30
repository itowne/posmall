package com.newland.posmall.identifier;
/**
 * 序列号生成器服务
 * @author rabbit
 *
 */
public interface IdentifierService {
	/**
	 * 生成客户编号
	 * @return
	 */
	String genCustId();
	/**
	 * 生成协议编号
	 * @return
	 */
	String genArgId();
	/**
	 * 生成月排产编号
	 * @return
	 */
	String genMonthId();
	/**
	 * 生成物流编号
	 * @return
	 */
	String genInterFlowId();
	/**
	 * 生成订单编号
	 * @return
	 */
	String genOrderId();
	/**
	 * 生成付款凭证号
	 * @return
	 */
	String genPayId();
	/**
	 * 生成通知编号
	 * @return
	 */
	String genNotifyId();
	/**
	 * 
	 * @return
	 */
	String getFileId();
	/**
	 * 下载追踪号
	 */
	String genBatchId();
	
	/**
	 * 采购框架合同编号
	 * @return
	 */
	String genContractId();
}
