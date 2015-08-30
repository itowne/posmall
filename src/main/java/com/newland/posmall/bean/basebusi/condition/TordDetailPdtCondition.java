package com.newland.posmall.bean.basebusi.condition;

import java.util.Date;

import org.ohuyo.commons.query.criterion.DetachedCriteriaEx;
import org.ohuyo.commons.query.criterion.annotation.CriteriaClass;
import org.ohuyo.commons.query.criterion.annotation.Expression;
import org.ohuyo.commons.query.criterion.annotation.Operator;

import com.newland.posmall.bean.basebusi.TordDetailPdt;
import com.newland.posmall.bean.dict.DownLoadStat;
import com.newland.posmall.bean.dict.OrdDetailPdtType;
import com.newland.posmall.bean.dict.ValidStatus;
import com.newland.posmall.bean.dict.YesNoType;

@CriteriaClass(TordDetailPdt.class)
public class TordDetailPdtCondition extends DetachedCriteriaEx {

	private static final long serialVersionUID = 7600972306953480036L;

	@Expression(operator = Operator.eq, propertyName = "ordDetailPdtType")
	private OrdDetailPdtType ordDetailPdtType;

	@Expression(operator = Operator.eq, propertyName = "lockType")
	private YesNoType lockType;
	
	@Expression(operator = Operator.eq, propertyName = "ordDetailPdtStatus")
	private ValidStatus ordDetailPdtStatus;
	
	@Expression(operator = Operator.eq, propertyName = "ipdt")
	private Long ipdt;
	
	@Expression(operator = Operator.eq, propertyName = "iord")
	private Long iord;
	
	@Expression(operator = Operator.lt, propertyName = "genTime")
	private Date genTime;
	
	@Expression(operator = Operator.eq, propertyName = "downloadStat")
	private DownLoadStat downLoadStat;

	public OrdDetailPdtType getOrdDetailPdtType() {
		return ordDetailPdtType;
	}

	public void setOrdDetailPdtType(OrdDetailPdtType ordDetailPdtType) {
		this.ordDetailPdtType = ordDetailPdtType;
	}

	public YesNoType getLockType() {
		return lockType;
	}

	public void setLockType(YesNoType lockType) {
		this.lockType = lockType;
	}

	public ValidStatus getOrdDetailPdtStatus() {
		return ordDetailPdtStatus;
	}

	public void setOrdDetailPdtStatus(ValidStatus ordDetailPdtStatus) {
		this.ordDetailPdtStatus = ordDetailPdtStatus;
	}

	public Long getIpdt() {
		return ipdt;
	}

	public void setIpdt(Long ipdt) {
		this.ipdt = ipdt;
	}

	public Long getIord() {
		return iord;
	}

	public void setIord(Long iord) {
		this.iord = iord;
	}

	public Date getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}

	public DownLoadStat getDownLoadStat() {
		return downLoadStat;
	}

	public void setDownLoadStat(DownLoadStat downLoadStat) {
		this.downLoadStat = downLoadStat;
	}
	
}
