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

import com.newland.posmall.bean.dict.PayMethod;
import com.newland.posmall.bean.dict.PayStatus;
import com.newland.posmall.bean.dict.PayType;
/**
 * 支付明细表
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_pay_detail")
public class TpayDetail implements Serializable {

	private static final long serialVersionUID = 2801092965977700292L;
	
	@Id
	@TableGenerator(name = "i_pay_detail", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_pay_detail")
	@Column(name = "i_pay_detail")
	private Long ipayDetail;//内部编号
	
	@Column(name = "i_pay",updatable = false)
	private Long ipay;//内部编号
	
	@Column(name = "i_fk",updatable = false)
	private Long ifk;//支付外键(订单、保证金)
	
	@Column(name = "i_his_fk",updatable = false)
	private Long ihisFk;//历史表
	
	@Column(name = "pay_type")
	@Enumerated(EnumType.STRING)
	private PayType payType;//支付类型
	
	@Column(name = "pay_status")
	@Enumerated(EnumType.STRING)
	private PayStatus payStatus;//状态
	
	@Column(name = "i_cust",updatable = false)
	private Long icust;//客户
	
	@Column(name = "gen_time",updatable = false)
	private Date genTime;//生成时间
	
	@Version
	private Long version;//版本号
	
	@Column(name = "upd_time")
	private Date updTime;//更新时间
	
	@Column(name = "i_user")
	private Long iuser;//用户外键
	
	@Column(name = "cust_name")
	private String custName;//客户姓名
	
	@Column(name = "user_name")
	private String userName;//用户姓名
	
	/**
	 * 支付金额
	 */
	@Column(name = "pay_amt")
	private BigDecimal payAmt;
	
	/**
	 * 审核不通过理由
	 */
	@Column(name = "refuse_reason")
	private String refuseReason;
	
	/**
	 * 支付方式
	 */
	@Column(name = "pay_method")
	@Enumerated(EnumType.STRING)
	private PayMethod payMethod;

	public Long getIpayDetail() {
		return ipayDetail;
	}

	public void setIpayDetail(Long ipayDetail) {
		this.ipayDetail = ipayDetail;
	}

	public Long getIpay() {
		return ipay;
	}

	public void setIpay(Long ipay) {
		this.ipay = ipay;
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

	public Long getIcust() {
		return icust;
	}

	public void setIcust(Long icust) {
		this.icust = icust;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Long getIuser() {
		return iuser;
	}

	public void setIuser(Long iuser) {
		this.iuser = iuser;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public PayType getPayType() {
		return payType;
	}

	public void setPayType(PayType payType) {
		this.payType = payType;
	}

	public PayStatus getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(PayStatus payStatus) {
		this.payStatus = payStatus;
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

	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	public BigDecimal getPayAmt() {
		return payAmt;
	}

	public void setPayAmt(BigDecimal payAmt) {
		this.payAmt = payAmt;
	}

	public PayMethod getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(PayMethod payMethod) {
		this.payMethod = payMethod;
	}
	
}
