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

import com.newland.posmall.bean.dict.LogisticsStatus;
import com.newland.posmall.bean.dict.YesNoType;
/**
 * 物流公司
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_logistics_com")
public class TlogisticsCom implements Serializable {

	private static final long serialVersionUID = -9187917966003339059L;
	
	@Id
	@TableGenerator(name = "i_logistics_com", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_logistics_com")
	@Column(name = "i_logistics_com")
	private Long ilogisticsCom; //内部编号
	
	@Column(name = "name")
	private String name;    // 
    
	@Column(name = "fullname")
	private String fullname;//
	
	@Column(name = "aging")
	private Integer aging;//时效
	
	@Column(name = "fee_flag")
	@Enumerated(EnumType.STRING)
	private YesNoType feeFlag;//收费标识（是否收费） 
	
	@Column(name = "logistics_status")
	@Enumerated(EnumType.STRING)
	private LogisticsStatus logisticsStatus;
	
	@Column(name = "gen_time",updatable = false)
	private Date genTime;//生成时间
	
	@Column(name = "upd_time")
	private Date updTime;//更新时间
	
	/**
	 * 物流公司代号(快递追踪查询使用)
	 */
	@Column(name="code")
	private String code;
	
	@Version
	private Long version;//版本  
	
	/**
	 * 单价（元/台）
	 */
	@Column(name = "price")
	private BigDecimal price;

	public Long getIlogisticsCom() {
		return ilogisticsCom;
	}

	public void setIlogisticsCom(Long ilogisticsCom) {
		this.ilogisticsCom = ilogisticsCom;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public Integer getAging() {
		return aging;
	}

	public void setAging(Integer aging) {
		this.aging = aging;
	}

	public YesNoType getFeeFlag() {
		return feeFlag;
	}
	
	public void setFeeFlag(YesNoType feeFlag) {
		this.feeFlag = feeFlag;
	}

	public LogisticsStatus getLogisticsStatus() {
		return logisticsStatus;
	}

	public void setLogisticsStatus(LogisticsStatus logisticsStatus) {
		this.logisticsStatus = logisticsStatus;
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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
    
}
