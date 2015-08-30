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
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Type;

import com.newland.posmall.bean.dict.LogisticsOrdStatus;
import com.newland.posmall.bean.dict.PayStatus;

/**
 * 物流单
 * 
 * @author Administrator
 * 
 */
@Entity
@Table(name = "t_logistics_ord")
public class TlogisticsOrd implements Serializable {

	private static final long serialVersionUID = -9187917966003339059L;

	@Id
	@TableGenerator(name = "i_logistics_ord", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_logistics_ord")
	@Column(name = "i_logistics_ord")
	private Long ilogisticsOrd; // 内部编号

	@Column(name = "inner_ordno")
	private String innerOrdno; // 内部单号

	@Column(name = "gen_time", updatable = false)
	private Date genTime;// 生成时间

	@Column(name = "upd_time")
	private Date updTime;// 更新时间

	@Version
	private Long version;// 版本

	@Column(name = "specify_delivery")
	private Date specifyDelivery;// 预计发货日期

	@Column(name = "expected_arrival")
	private Date expectedArrival;// 预计到达日期

	@Column(name = "i_cust")
	private Long icust;// 客户外键

	@Column(name = "i_user")
	private Long iuser;// 用户外键

	@Column(name = "username")
	private String username;// 姓名

	@Column(name = "real_outstock_num")
	private Integer realOutstockNum;// 实际发货数量

	@Column(name = "i_logistics_ord_trace")
	private Long ilogisticsOrdTrace;// 物流同步数据跟踪表id

	@Column(name = "logistics_ord_status")
	@Enumerated(EnumType.STRING)
	private LogisticsOrdStatus logisticsOrdStatus;

	@Column(name = "pay_status")
	@Enumerated(EnumType.STRING)
	private PayStatus payStatus;

	@Column(name = "temp_flag")
	@Type(type = "yes_no")
	private Boolean tempFlag;// 临时标志

	@Column(name = "i_ord")
	private Long iord;// 订单id

	@Column(name = "num")
	private Integer num;// 数量

	@Column(name = "logistics_freight")
	private BigDecimal logisticsFreight;// 物流费用

	@Column(name = "i_file")
	private Long ifile;// 文件外键

	@Column(name = "amt")
	private BigDecimal amt;// 货款金额
	
	/**
	 * 追踪号(下载文件回写)
	 */
	@Column(name = "trace_no")
	private String traceNo;

	@Transient
	private Tord tord;
	
	/**
	 * 欠费
	 */
	@Transient
	private BigDecimal debts;

	public Long getIlogisticsOrd() {
		return ilogisticsOrd;
	}

	public void setIlogisticsOrd(Long ilogisticsOrd) {
		this.ilogisticsOrd = ilogisticsOrd;
	}

	public String getInnerOrdno() {
		return innerOrdno;
	}

	public void setInnerOrdno(String innerOrdno) {
		this.innerOrdno = innerOrdno;
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

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Date getSpecifyDelivery() {
		return specifyDelivery;
	}

	public void setSpecifyDelivery(Date specifyDelivery) {
		this.specifyDelivery = specifyDelivery;
	}

	public Date getExpectedArrival() {
		return expectedArrival;
	}

	public void setExpectedArrival(Date expectedArrival) {
		this.expectedArrival = expectedArrival;
	}

	public Long getIcust() {
		return icust;
	}

	public void setIcust(Long icust) {
		this.icust = icust;
	}

	public Long getIuser() {
		return iuser;
	}

	public void setIuser(Long iuser) {
		this.iuser = iuser;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getRealOutstockNum() {
		return realOutstockNum;
	}

	public void setRealOutstockNum(Integer realOutstockNum) {
		this.realOutstockNum = realOutstockNum;
	}

	public Long getIlogisticsOrdTrace() {
		return ilogisticsOrdTrace;
	}

	public void setIlogisticsOrdTrace(Long ilogisticsOrdTrace) {
		this.ilogisticsOrdTrace = ilogisticsOrdTrace;
	}

	public Boolean getTempFlag() {
		return tempFlag;
	}

	public void setTempFlag(Boolean tempFlag) {
		this.tempFlag = tempFlag;
	}

	public Long getIord() {
		return iord;
	}

	public void setIord(Long iord) {
		this.iord = iord;
	}

	public String getTraceNo() {
		return traceNo;
	}

	public void setTraceNo(String traceNo) {
		this.traceNo = traceNo;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public BigDecimal getLogisticsFreight() {
		return logisticsFreight;
	}

	public void setLogisticsFreight(BigDecimal logisticsFreight) {
		this.logisticsFreight = logisticsFreight;
	}

	public LogisticsOrdStatus getLogisticsOrdStatus() {
		return logisticsOrdStatus;
	}

	public void setLogisticsOrdStatus(LogisticsOrdStatus logisticsOrdStatus) {
		this.logisticsOrdStatus = logisticsOrdStatus;
	}

	public PayStatus getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(PayStatus payStatus) {
		this.payStatus = payStatus;
	}

	public Long getIfile() {
		return ifile;
	}

	public void setIfile(Long ifile) {
		this.ifile = ifile;
	}

	public Tord getTord() {
		return tord;
	}

	public void setTord(Tord tord) {
		this.tord = tord;
	}

	public BigDecimal getDebts() {
		return debts;
	}

	public void setDebts(BigDecimal debts) {
		this.debts = debts;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}
	
}
