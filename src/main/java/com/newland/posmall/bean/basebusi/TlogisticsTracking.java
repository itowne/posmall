package com.newland.posmall.bean.basebusi;

import java.io.Serializable;
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

import com.newland.posmall.bean.dict.LogisticsStatus;
/**
 * 物流跟踪
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_logistics_tracking")
public class TlogisticsTracking implements Serializable {

	private static final long serialVersionUID = -9187917966003339059L;
	
	@Id
	@TableGenerator(name = "i_logistics_tracking", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_logistics_tracking")
	@Column(name = "i_logistics_tracking")
	private Long ilogisticsTracking; //内部编号
	
	@Column(name = "i_logistics_ord")
	private Long ilogisticsOrd;    //物流订单
    
	@Column(name = "logistics_date")
	private Date logisticsDate;//日期
	
	@Column(name = "logistics_desc")
	private String logisticsDesc;//物流描述
	
	@Column(name = "logistics_status")
	@Enumerated(EnumType.STRING)
	private LogisticsStatus logisticsStatus;//收货状态
	
	@Column(name = "gen_time",updatable = false)
	private Date genTime;//生成时间

	public Long getIlogisticsTracking() {
		return ilogisticsTracking;
	}

	public void setIlogisticsTracking(Long ilogisticsTracking) {
		this.ilogisticsTracking = ilogisticsTracking;
	}

	public Long getIlogisticsOrd() {
		return ilogisticsOrd;
	}

	public void setIlogisticsOrd(Long ilogisticsOrd) {
		this.ilogisticsOrd = ilogisticsOrd;
	}

	public Date getLogisticsDate() {
		return logisticsDate;
	}

	public void setLogisticsDate(Date logisticsDate) {
		this.logisticsDate = logisticsDate;
	}

	public String getLogisticsDesc() {
		return logisticsDesc;
	}

	public void setLogisticsDesc(String logisticsDesc) {
		this.logisticsDesc = logisticsDesc;
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

    
	

}
