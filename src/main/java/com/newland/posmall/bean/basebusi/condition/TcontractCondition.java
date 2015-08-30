package com.newland.posmall.bean.basebusi.condition;

import java.util.List;

import org.ohuyo.commons.query.criterion.DetachedCriteriaEx;
import org.ohuyo.commons.query.criterion.annotation.CriteriaClass;
import org.ohuyo.commons.query.criterion.annotation.Expression;
import org.ohuyo.commons.query.criterion.annotation.Operator;

import com.newland.posmall.bean.basebusi.Tcontract;

@CriteriaClass(Tcontract.class)
public class TcontractCondition extends DetachedCriteriaEx {

	private static final long serialVersionUID = 3338641863923404396L;
	

	@Expression(operator = Operator.like, propertyName = "contractName")
	private String contractName;
	
	@Expression(operator = Operator.eq, propertyName = "icust")
	private Long icust;
	
	@Expression(operator = Operator.in, propertyName = "icust")
	private List<Long> icustList;
	
	@Expression(operator = Operator.eq, propertyName = "custCode")
	private String custCode;

	@Expression(operator = Operator.eq, propertyName = "contractNo")
	private String contractNo;
	
	@Expression(operator = Operator.eq, propertyName = "delFlag")
	private Boolean delFlag;

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Boolean getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Boolean delFlag) {
		this.delFlag = delFlag;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public List<Long> getIcustList() {
		return icustList;
	}

	public void setIcustList(List<Long> icustList) {
		this.icustList = icustList;
	}

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public Long getIcust() {
		return icust;
	}

	public void setIcust(Long icust) {
		this.icust = icust;
	}
	
	
	
}
