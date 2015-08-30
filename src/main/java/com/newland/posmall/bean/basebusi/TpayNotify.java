package com.newland.posmall.bean.basebusi;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.hibernate.annotations.Type;

import com.newland.posmall.bean.dict.PayStatus;
import com.newland.posmall.bean.dict.PayType;

/**
 * 支付通知书
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_pay_notify")
public class TpayNotify implements Serializable {

	private static final long serialVersionUID = 2801092965977700292L;

	@Id
	@TableGenerator(name = "i_pay_notify", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_pay_notify")
	@Column(name = "i_pay_notify")
	private Long ipayNotify;// 支付通知书

	@Column(name = "i_cust")
	private Long icust;// 客户

	@Column(name = "i_fk")
	private Long ifk;// 外键

	@Column(name = "i_his_fk")
	private Long ihisFk;// 历史表

	@Column(name = "pay_type")
	@Enumerated(EnumType.STRING)
	private PayType payType;// 支付类型

	@Column(name = "pay_notify_no")
	private String payNotifyNo;// 通知书编号

	@Column(name = "memo")
	private String memo;

	@Column(name = "gen_time", updatable = false)
	private Date genTime;// 生成时间

	@Column(name = "upd_time")
	private Date updTime;// 更新时间

	@Version
	private Long version;// 版本号

	@Column(name = "pay_notify_status")
	@Enumerated(EnumType.STRING)
	private PayStatus payNotifyStatus;// 状态

	@Column(name = "del_flag")
	@Type(type = "yes_no")
	private Boolean delFlag;
	
	/**
	 * 已支付金额
	 */
	@Column(name="havepay_amt")
	private BigDecimal havepayAmt;

	public Long getIpayNotify() {
		return ipayNotify;
	}

	public void setIpayNotify(Long ipayNotify) {
		this.ipayNotify = ipayNotify;
	}

	public Long getIcust() {
		return icust;
	}

	public void setIcust(Long icust) {
		this.icust = icust;
	}

	public Long getIfk() {
		return ifk;
	}

	public void setIfk(Long ifk) {
		this.ifk = ifk;
	}

	public Long getIhisFk() {
		return ihisFk;
	}

	public void setIhisFk(Long ihisFk) {
		this.ihisFk = ihisFk;
	}

	public String getPayNotifyNo() {
		return payNotifyNo;
	}

	public void setPayNotifyNo(String payNotifyNo) {
		this.payNotifyNo = payNotifyNo;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Date getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}

	public Date getUpdTime() {
		return updTime;
	}

	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}

	public PayType getPayType() {
		return payType;
	}

	public void setPayType(PayType payType) {
		this.payType = payType;
	}

	public PayStatus getPayNotifyStatus() {
		return payNotifyStatus;
	}

	public void setPayNotifyStatus(PayStatus payNotifyStatus) {
		this.payNotifyStatus = payNotifyStatus;
	}

	public Boolean getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Boolean delFlag) {
		this.delFlag = delFlag;
	}

	public BigDecimal getHavepayAmt() {
		return havepayAmt;
	}

	public void setHavepayAmt(BigDecimal havepayAmt) {
		this.havepayAmt = havepayAmt;
	}
	
}
