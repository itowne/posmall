package com.newland.posmall.controller.cust.model;

/**
 * 订单支付数据Model
 * @author zhouym
 *
 */
public class OrderPay4Page {

	/**
	 * 付款通知书id
	 */
	private Long ipayNotify;
	
	/**
	 * 本次订单支付是否使用保证金标志
	 */
	private String useDepositFlag;
	
	/**
	 * 本次订单支付所使用的保证金
	 */
	private String depositUsed4Tord;
	
	/**
	 * 汇款单编号
	 */
	private String remittanceNo;
	
	/**
	 * 本次订单支付的汇款金额
	 */
	private String remittanceAmt;
	
	/**
	 * 银行账号
	 */
	private String account;
	
	/**
	 * 开户行
	 */
	private String bank;
	
	/**
	 * 协议id
	 */
	private Long iagmt;
	
	/**
	 * 本次支付的订单id
	 */
	private Long iord;
	
	public Long getIpayNotify() {
		return ipayNotify;
	}

	public void setIpayNotify(Long ipayNotify) {
		this.ipayNotify = ipayNotify;
	}

	public String getDepositUsed4Tord() {
		return depositUsed4Tord;
	}

	public void setDepositUsed4Tord(String depositUsed4Tord) {
		this.depositUsed4Tord = depositUsed4Tord;
	}

	public String getRemittanceNo() {
		return remittanceNo;
	}

	public void setRemittanceNo(String remittanceNo) {
		this.remittanceNo = remittanceNo;
	}

	public String getRemittanceAmt() {
		return remittanceAmt;
	}

	public void setRemittanceAmt(String remittanceAmt) {
		this.remittanceAmt = remittanceAmt;
	}

	public Long getIagmt() {
		return iagmt;
	}

	public void setIagmt(Long iagmt) {
		this.iagmt = iagmt;
	}

	public Long getIord() {
		return iord;
	}

	public void setIord(Long iord) {
		this.iord = iord;
	}

	public String getUseDepositFlag() {
		return useDepositFlag;
	}

	public void setUseDepositFlag(String useDepositFlag) {
		this.useDepositFlag = useDepositFlag;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}
	
}
