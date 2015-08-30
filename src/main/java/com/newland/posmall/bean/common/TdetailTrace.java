package com.newland.posmall.bean.common;

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

import com.newland.posmall.bean.dict.LogType;

/**
 * 详情追踪表
 * 记录协议订单物流的状态变化
 */
@Entity
@Table(name = "t_detail_trace")
public class TdetailTrace implements Serializable {

	private static final long serialVersionUID = -7854161216196109998L;

	@Id
	@TableGenerator(name = "i_detail_trace", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_detail_trace")
	@Column(name = "i_detail_trace")
	private Long idetailTrace;//内部编号
	
	@Column(name = "i_cust")
	private Long icust;//客户外键
	
	@Column(name = "cust_name")
	private String custName;//客户名称
	
	@Column(name = "i_agmt")
	private Long iagmt;//协议外键
	
	@Column(name = "i_ord")
	private Long iord;//订单外键
	
	@Column(name = "i_logistics_ord")
	private Long ilogisticsOrd;//物流单外键
	
	@Column(name = "agmt_no")
	private String agmtNo;//协议编号
	
	@Column(name = "ord_no")
	private String ordNo;//订单编号
	
	@Column(name = "logistics_ord_no")
	private String logisticsOrdNo; // 物流编号
	
	@Column(name = "amt")
	private BigDecimal amt;//金额
	
	@Column(name = "num")
	private Integer num;//数量
	
	@Column(name = "memo")
	private String memo;//说明
	
	@Column(name = "log_type")
	@Enumerated(EnumType.STRING)
	private LogType logType;//日志类型(cust,user,sys)
	
	@Column(name = "i_operator")
	private Long ioperator;//操作人员id
	
	@Column(name = "gen_time",updatable = false)
	private Date genTime;//生成时间

	public Long getIdetailTrace() {
		return idetailTrace;
	}

	public void setIdetailTrace(Long idetailTrace) {
		this.idetailTrace = idetailTrace;
	}

	public Long getIcust() {
		return icust;
	}

	public void setIcust(Long icust) {
		this.icust = icust;
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

	public Long getIlogisticsOrd() {
		return ilogisticsOrd;
	}

	public void setIlogisticsOrd(Long ilogisticsOrd) {
		this.ilogisticsOrd = ilogisticsOrd;
	}

	public String getAgmtNo() {
		return agmtNo;
	}

	public void setAgmtNo(String agmtNo) {
		this.agmtNo = agmtNo;
	}

	public String getOrdNo() {
		return ordNo;
	}

	public void setOrdNo(String ordNo) {
		this.ordNo = ordNo;
	}

	public String getLogisticsOrdNo() {
		return logisticsOrdNo;
	}

	public void setLogisticsOrdNo(String logisticsOrdNo) {
		this.logisticsOrdNo = logisticsOrdNo;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}

	public LogType getLogType() {
		return logType;
	}

	public void setLogType(LogType logType) {
		this.logType = logType;
	}

	public Long getIoperator() {
		return ioperator;
	}

	public void setIoperator(Long ioperator) {
		this.ioperator = ioperator;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

}
