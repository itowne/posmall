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
/**
 * 产品表
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_pdt")
public class Tpdt implements Serializable {

	private static final long serialVersionUID = 2801092965977700292L;

	@Id
	@TableGenerator(name = "i_pdt", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_pdt")
	@Column(name = "i_pdt")
	private Long ipdt;
	
	/**
	 * 产品历史外键
	 */
	@Column(name = "i_pdt_his")
	private Long ipdtHis;

	/**
	 * 产品型号
	 */
	@Column(name = "pdt_no")
	private String pdtNo;
	
	/**
	 * 产品名称
	 */
	@Column(name = "name")
	private String name;
	
	/**
	 * 产品全称
	 */
	@Column(name = "long_name")
	private String longName;
	
	/**
	 * 产品单价
	 */
	@Column(name = "price")
	private BigDecimal price;
	
	/**
	 * 修改人
	 */
	@Column(name = "i_user")
	private Long iuser;
	
	/**
	 * 修改姓名
	 */
	@Column(name = "user_name")
	private String userName;
	
	/**
	 * 备注
	 */
	@Column(name = "memo")
	private String memo;

	@Column(name = "gen_time",updatable = false)
	private Date genTime;//生成时间

	@Column(name = "upd_time")
	private Date updTime;//更新时间

	@Version
	private Long version;//版本号
	
	@Column(name ="del_flag")
	@Type(type="yes_no")
	private Boolean delFlag;
	
	/**
	 * 号段配置外键
	 */
	@Column(name = "i_no_seg_cfg")
	private Long inoSegCfg;
	
	/**
	 * 产品对应的月排产计划
	 */
	@Transient
	private TpdtPlanMonth[] tpdtPlanMonthArray;
	
	/**
	 * 客户对应的产品折扣率
	 */
	@Transient
	private BigDecimal custPdtRate;
	
	/**
	 * 按照客户折扣率折算后的产品单价
	 */
	@Transient
	private BigDecimal priceAfterRate;
	
	/**
	 * 签订协议时，该产品的订货量
	 */
	@Transient
	private Integer pdtAgmtNum;

	/**
	 * 产品库存量
	 */
	@Transient
	private Integer stockNum;
	
	/**
	 * 产品号段
	 */
	@Transient
	private TnoSegCfg tnoSegCfg;

	public Long getIpdt() {
		return ipdt;
	}

	public void setIpdt(Long ipdt) {
		this.ipdt = ipdt;
	}

	public Long getIpdtHis() {
		return ipdtHis;
	}

	public void setIpdtHis(Long ipdtHis) {
		this.ipdtHis = ipdtHis;
	}

	public String getPdtNo() {
		return pdtNo;
	}

	public void setPdtNo(String pdtNo) {
		this.pdtNo = pdtNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Long getIuser() {
		return iuser;
	}

	public void setIuser(Long iuser) {
		this.iuser = iuser;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public Boolean getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Boolean delFlag) {
		this.delFlag = delFlag;
	}

	public TpdtPlanMonth[] getTpdtPlanMonthArray() {
		return tpdtPlanMonthArray;
	}

	public void setTpdtPlanMonthArray(TpdtPlanMonth[] tpdtPlanMonthArray) {
		this.tpdtPlanMonthArray = tpdtPlanMonthArray;
	}

	public BigDecimal getCustPdtRate() {
		return custPdtRate;
	}

	public void setCustPdtRate(BigDecimal custPdtRate) {
		this.custPdtRate = custPdtRate;
	}

	public BigDecimal getPriceAfterRate() {
		return priceAfterRate;
	}

	public void setPriceAfterRate(BigDecimal priceAfterRate) {
		this.priceAfterRate = priceAfterRate;
	}

	public Integer getPdtAgmtNum() {
		return pdtAgmtNum;
	}

	public void setPdtAgmtNum(Integer pdtAgmtNum) {
		this.pdtAgmtNum = pdtAgmtNum;
	}

	public Integer getStockNum() {
		return stockNum;
	}

	public void setStockNum(Integer stockNum) {
		this.stockNum = stockNum;
	}

	public TnoSegCfg getTnoSegCfg() {
		return tnoSegCfg;
	}

	public void setTnoSegCfg(TnoSegCfg tnoSegCfg) {
		this.tnoSegCfg = tnoSegCfg;
	}

	public Long getInoSegCfg() {
		return inoSegCfg;
	}

	public void setInoSegCfg(Long inoSegCfg) {
		this.inoSegCfg = inoSegCfg;
	}
	
	
}
