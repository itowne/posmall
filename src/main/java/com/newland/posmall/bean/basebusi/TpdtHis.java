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

/**
 * 产品历史表
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_pdt_his")
public class TpdtHis implements Serializable {

	private static final long serialVersionUID = 2801092965977700292L;

	@Id
	@TableGenerator(name = "i_pdt_his", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_pdt_his")
	@Column(name = "i_pdt_his")
	private Long ipdtHis;//内部编号
	
	/**
	 * 产品外键
	 */
	@Column(name = "i_pdt")
	private Long ipdt;
	
	@Column(name = "ver")
	private Long ver;//版本号
	
	/**
	 * 上一产品历史外键
	 */
	@Column(name = "pre_i_pdt_his")
	private Long preIpdtHis;
	
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
	 * 修改人姓名
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
	
	/**
	 * 号段配置外键
	 */
	@Column(name = "i_no_seg_cfg")
	private Long inoSegCfg;
	

	public Long getIpdtHis() {
		return ipdtHis;
	}

	public void setIpdtHis(Long ipdtHis) {
		this.ipdtHis = ipdtHis;
	}

	public Long getIpdt() {
		return ipdt;
	}

	public void setIpdt(Long ipdt) {
		this.ipdt = ipdt;
	}

	public Long getPreIpdtHis() {
		return preIpdtHis;
	}

	public void setPreIpdtHis(Long preIpdtHis) {
		this.preIpdtHis = preIpdtHis;
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

	public Long getVer() {
		return ver;
	}

	public void setVer(Long ver) {
		this.ver = ver;
	}

	public String getPdtNo() {
		return pdtNo;
	}

	public void setPdtNo(String pdtNo) {
		this.pdtNo = pdtNo;
	}

	public Long getInoSegCfg() {
		return inoSegCfg;
	}

	public void setInoSegCfg(Long inoSegCfg) {
		this.inoSegCfg = inoSegCfg;
	}

    
}
