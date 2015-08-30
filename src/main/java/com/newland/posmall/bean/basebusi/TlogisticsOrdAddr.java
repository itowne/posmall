package com.newland.posmall.bean.basebusi;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Type;

import com.newland.posmall.bean.dict.LogisticsCompany;

/**
 * 物流单地址表
 * 
 */
@Entity
@Table(name = "t_logistics_ord_addr")
public class TlogisticsOrdAddr implements Serializable {

	private static final long serialVersionUID = 2135859784513464654L;

	@Id
	@TableGenerator(name = "i_logistics_ord_addr", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_logistics_ord_addr")
	@Column(name = "i_logistics_ord_addr")
	private Long ilogisticsOrdAddr; // 内部编号

	@Column(name = "i_logistics_ord")
	private Long ilogisticsOrd; // 物流单外键

	@Column(name = "real_ordno")
	private String realOrdno;// 真实物流单号

	@Column(name = "i_pdt_his")
	private Long ipdtHis;// 产品历史外键

	@Column(name = "num")
	private Integer num;// 数量

	@Column(name = "serial")
	private Integer serial;//物流序号
	
	@Column(name = "real_serial")
	private String realSerial;//真实物流序号
	
	@Column(name = "real_outstock_num")
	private Integer realOutstockNum;// 实际发货数量

	@Column(name = "real_delivery")
	private Date realDelivery;// 实际发货日期

	@Column(name = "real_arrival")
	private Date realArrival;// 实际到达日期

	@Column(name = "i_user")
	private Long iuser;// 用户外键

	@Column(name = "username")
	private String username;// 姓名

	@Column(name = "i_addr_his")
	private Long iaddrHis;// 地址外键

	@Column(name = "consignee_addr")
	private String consigneeAddr;// 收货地址

	@Column(name = "consignee_name")
	private String consigneeName;// 收货人姓名
	
	@Column(name = "consignee_mobile")
	private String consigneeMobile;// 收货人电话
	
	@Column(name = "outstock_no")
	private String outstockNo;// 出库单号

	@Column(name = "i_logistics_com")
	private Long ilogisticsCom;// 物流公司外键

	@Column(name = "logistics_com_name")
	private String logisticsComName;// 物流公司名称

	@Column(name = "price")
	private BigDecimal price;//物流费用单价（元/台）

	@Column(name = "fee_flag")
	private String feeFlag;//是否选择收费物流
	
	@Column(name = "gen_time", updatable = false)
	private Date genTime;// 生成时间

	@Column(name = "upd_time")
	private Date updTime;// 更新时间
	
	@Transient
	private String pdtName;
	
	@Transient
	private LogisticsCompany logisticsCompany;

	@Version
	private Long version;// 版本
	
	@Column(name = "del_flag")
	@Type(type = "yes_no")
	private Boolean delFlag;// 删除标志
	
	@Column(name = "pre_serial")
	private Integer preSerial;//父类物流序号

	public Long getIlogisticsOrdAddr() {
		return ilogisticsOrdAddr;
	}

	public void setIlogisticsOrdAddr(Long ilogisticsOrdAddr) {
		this.ilogisticsOrdAddr = ilogisticsOrdAddr;
	}

	public Long getIlogisticsOrd() {
		return ilogisticsOrd;
	}

	public void setIlogisticsOrd(Long ilogisticsOrd) {
		this.ilogisticsOrd = ilogisticsOrd;
	}

	public Long getIaddrHis() {
		return iaddrHis;
	}

	public void setIaddrHis(Long iaddrHis) {
		this.iaddrHis = iaddrHis;
	}

	public String getConsigneeAddr() {
		return consigneeAddr;
	}

	public void setConsigneeAddr(String consigneeAddr) {
		this.consigneeAddr = consigneeAddr;
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

	public String getRealOrdno() {
		return realOrdno;
	}

	public void setRealOrdno(String realOrdno) {
		this.realOrdno = realOrdno;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getRealOutstockNum() {
		return realOutstockNum;
	}

	public void setRealOutstockNum(Integer realOutstockNum) {
		this.realOutstockNum = realOutstockNum;
	}

	public Date getRealDelivery() {
		return realDelivery;
	}

	public void setRealDelivery(Date realDelivery) {
		this.realDelivery = realDelivery;
	}

	public Date getRealArrival() {
		return realArrival;
	}

	public void setRealArrival(Date realArrival) {
		this.realArrival = realArrival;
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

	public String getOutstockNo() {
		return outstockNo;
	}

	public void setOutstockNo(String outstockNo) {
		this.outstockNo = outstockNo;
	}

	public Long getIlogisticsCom() {
		return ilogisticsCom;
	}

	public void setIlogisticsCom(Long ilogisticsCom) {
		this.ilogisticsCom = ilogisticsCom;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getLogisticsComName() {
		return logisticsComName;
	}

	public void setLogisticsComName(String logisticsComName) {
		this.logisticsComName = logisticsComName;
	}

	public Long getIpdtHis() {
		return ipdtHis;
	}

	public void setIpdtHis(Long ipdtHis) {
		this.ipdtHis = ipdtHis;
	}

	public Integer getSerial() {
		return serial;
	}

	public void setSerial(Integer serial) {
		this.serial = serial;
	}

	public String getPdtName() {
		return pdtName;
	}

	public void setPdtName(String pdtName) {
		this.pdtName = pdtName;
	}

	public String getFeeFlag() {
		return feeFlag;
	}

	public void setFeeFlag(String feeFlag) {
		this.feeFlag = feeFlag;
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getConsigneeMobile() {
		return consigneeMobile;
	}

	public void setConsigneeMobile(String consigneeMobile) {
		this.consigneeMobile = consigneeMobile;
	}

	public LogisticsCompany getLogisticsCompany() {
		return logisticsCompany;
	}

	public void setLogisticsCompany(LogisticsCompany logisticsCompany) {
		this.logisticsCompany = logisticsCompany;
	}

	public String getRealSerial() {
		return realSerial;
	}

	public void setRealSerial(String realSerial) {
		this.realSerial = realSerial;
	}

	public Boolean getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Boolean delFlag) {
		this.delFlag = delFlag;
	}

	public Integer getPreSerial() {
		return preSerial;
	}

	public void setPreSerial(Integer preSerial) {
		this.preSerial = preSerial;
	}
	

}
