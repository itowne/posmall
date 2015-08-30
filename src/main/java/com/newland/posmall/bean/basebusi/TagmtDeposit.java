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
 * 协议保证金历史表
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_agmt_deposit")
public class TagmtDeposit implements Serializable {

	private static final long serialVersionUID = -9187917966003339059L;
	
	@Id
	@TableGenerator(name = "i_agmt_deposit", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_agmt_deposit")
	@Column(name = "i_agmt_deposit")
	private Long iagmtDeposit; //内部编号
	
	@Column(name = "i_agmt")
	private Long iagmt;    //内部编号     
    
	@Column(name = "agmt_no")
	private String agmtNo;//协议编号
	
	@Column(name = "i_cust")
	private Long icust;//客户内部编号  
	
	@Column(name = "deposit")
	private BigDecimal deposit;//保证金  
	
	@Column(name = "gen_time",updatable = false)
	private Date genTime;//生成时间  
	
	@Column(name = "ver")
	private Long ver;
	
	@Column(name = "used_deposit")
	private BigDecimal usedDeposit;//已用保证金  
	
	@Column(name = "remain_deposit")
	private BigDecimal remainDeposit;//剩余保证金

	public Long getIagmtDeposit() {
		return iagmtDeposit;
	}

	public void setIagmtDeposit(Long iagmtDeposit) {
		this.iagmtDeposit = iagmtDeposit;
	}

	public Long getIagmt() {
		return iagmt;
	}

	public void setIagmt(Long iagmt) {
		this.iagmt = iagmt;
	}

	public String getAgmtNo() {
		return agmtNo;
	}

	public void setAgmtNo(String agmtNo) {
		this.agmtNo = agmtNo;
	}

	public Long getIcust() {
		return icust;
	}

	public void setIcust(Long icust) {
		this.icust = icust;
	}

	public BigDecimal getDeposit() {
		return deposit;
	}

	public void setDeposit(BigDecimal deposit) {
		this.deposit = deposit;
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

	public BigDecimal getUsedDeposit() {
		return usedDeposit;
	}

	public void setUsedDeposit(BigDecimal usedDeposit) {
		this.usedDeposit = usedDeposit;
	}

	public BigDecimal getRemainDeposit() {
		return remainDeposit;
	}

	public void setRemainDeposit(BigDecimal remainDeposit) {
		this.remainDeposit = remainDeposit;
	}

}
