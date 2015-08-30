package org.ohuyo.rapid.base.entity;

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
import javax.persistence.Version;

import com.newland.posmall.bean.dict.ValidStatus;

/**
 * 
* @ClassName: ERPMaintenance  
* @Description: 同步维保信息表 
* @author chenwenjing
* @date 2014-10-15 上午9:59:30  
*
 */
@Entity
@Table(name = "t_erp_maintenance")
public class ERPMaintenance implements Serializable {

	private static final long serialVersionUID = 1720395733099291829L;

	@Id
	@TableGenerator(name = "i_erp_maintenance", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_erp_maintenance")
	@Column(name = "i_erp_maintenance")
	private Long ierpMaintenance;//逻辑主键

	@Column(name = "fh_date")
	private String fhDate;//发货日期
	
	@Column(name = "real_ordno")
	private String realOrdno;//真实物流单号
	
	@Column(name = "inner_ordno")
	private String innerOrdno;//系统内部物流单号
	
	@Column(name = "sn")
	private String sn;//产品序列号
	
	@Column(name = "ph")
	private String ph;//产品型号

	@Column(name = "pm")
	private String pm;//产品名称
	
	@Column(name = "i_cust")
	private Long icust;// 客户外键
	
	@Column(name = "purchase_date")
	private Date purchaseDate;//购买日期
	
	@Column(name = "life_start_time")
	private Date lifeStartTime;// 保修期起始时间
	
	@Column(name = "warranty_period")
	private Date warrantyPeriod;//保修期限
	
	@Column(name = "last_repaired_date")
	private Date lastRepairedDate;//最后修复日期
	
	@Column(name = "repair_num")
	private Integer repairNum;//报修次数
	
	@Column(name = "gen_time", updatable = false)
	private Date genTime;// 生成时间
	
	@Column(name = "upd_time")
	private Date updTime;// 更新时间

	@Version
	private Long version;// 版本号
	
	@Column(name = "valid_status")
	@Enumerated(EnumType.STRING)
	private ValidStatus validStatus;// 是否有效
	
	@Column(name = "last_maintenance_id")
	private Long lastMaintenanceId; //更换设备前对应的记录id

	public Long getIerpMaintenance() {
		return ierpMaintenance;
	}

	public void setIerpMaintenance(Long ierpMaintenance) {
		this.ierpMaintenance = ierpMaintenance;
	}

	public String getFhDate() {
		return fhDate;
	}

	public void setFhDate(String fhDate) {
		this.fhDate = fhDate;
	}

	public String getRealOrdno() {
		return realOrdno;
	}

	public void setRealOrdno(String realOrdno) {
		this.realOrdno = realOrdno;
	}

	public String getInnerOrdno() {
		return innerOrdno;
	}

	public void setInnerOrdno(String innerOrdno) {
		this.innerOrdno = innerOrdno;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getPh() {
		return ph;
	}

	public void setPh(String ph) {
		this.ph = ph;
	}

	public String getPm() {
		return pm;
	}

	public void setPm(String pm) {
		this.pm = pm;
	}

	public Long getIcust() {
		return icust;
	}

	public void setIcust(Long icust) {
		this.icust = icust;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public Date getWarrantyPeriod() {
		return warrantyPeriod;
	}

	public void setWarrantyPeriod(Date warrantyPeriod) {
		this.warrantyPeriod = warrantyPeriod;
	}

	public Date getLastRepairedDate() {
		return lastRepairedDate;
	}

	public void setLastRepairedDate(Date lastRepairedDate) {
		this.lastRepairedDate = lastRepairedDate;
	}

	public Integer getRepairNum() {
		return repairNum;
	}

	public void setRepairNum(Integer repairNum) {
		this.repairNum = repairNum;
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

	public Date getLifeStartTime() {
		return lifeStartTime;
	}

	public void setLifeStartTime(Date lifeStartTime) {
		this.lifeStartTime = lifeStartTime;
	}

	public ValidStatus getValidStatus() {
		return validStatus;
	}

	public void setValidStatus(ValidStatus validStatus) {
		this.validStatus = validStatus;
	}

	public Long getLastMaintenanceId() {
		return lastMaintenanceId;
	}

	public void setLastMaintenanceId(Long lastMaintenanceId) {
		this.lastMaintenanceId = lastMaintenanceId;
	}
    
}
