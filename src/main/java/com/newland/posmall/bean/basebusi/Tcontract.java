package com.newland.posmall.bean.basebusi;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.hibernate.annotations.Type;

/**
 * 合同
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_contract")
public class Tcontract implements Serializable {

	private static final long serialVersionUID = -9187917966003339059L;
	
	@Id
	@TableGenerator(name = "i_contract", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_contract")
	@Column(name = "i_contract")
	private Long icontract;//主键
	
	@Column(name = "i_cust")
	private Long icust;//客户ID
	
	@Column(name = "cust_code")
	private String custCode;//注册码
	
	@Column(name = "contract_name")
	private String contractName;//合同名称
	
	@Column(name = "contract_no")
	private String contractNo;//合同编码
	
	@Column(name = "content")
	private String content;//合同内容

	@Column(name = "gen_time",updatable = false)
	private Date genTime;//生成时间
	
	@Column(name = "upd_time")
	private Date updTime;//更新时间
	
	@Column(name = "del_flag")
	@Type(type = "yes_no")
	private Boolean delFlag;//删除标识
	
	@Version
	private Long version;//版本号
	

	public Long getIcontract() {
		return icontract;
	}

	public void setIcontract(Long icontract) {
		this.icontract = icontract;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
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

	public Boolean getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Boolean delFlag) {
		this.delFlag = delFlag;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
    
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Long getIcust() {
		return icust;
	}

	public void setIcust(Long icust) {
		this.icust = icust;
	}

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
}
