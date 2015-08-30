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
import javax.persistence.Transient;

import org.ohuyo.rapid.base.entity.Tfile;

/**
 * 物流下载关联表
 * @author Mr.Towne
 *
 */
@Entity
@Table(name = "t_logistics_download")
public class LogisticsDownLoad implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2143065593469970080L;
	/**
	 * 逻辑id
	 */
	@Id
	@TableGenerator(name = "i_logistics_download", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_logistics_download")
	@Column(name = "i_logistics_download")
	private Long iLogisticsDownload;
	/**
	 * 批次号
	 */
	@Column(name = "batch_no")
	private String batchNo;
	/**
	 * 用户id
	 */
	@Column(name = "i_usr")
	private Long iUsr;
	/**
	 * 下载次数
	 */
	@Column(name = "num")
	private Integer num;
	/**
	 * 下载文件uuid
	 */
	@Column(name = "file_uuid")
	private String fileUUid;

	/**
	 * 生成时间
	 */
	@Column(name = "gen_time", updatable = false)
	private Date genTime;
	/**
	 * 更新时间
	 */
	@Column(name = "upd_time")
	private Date updTime;
		
	public Long getiLogisticsDownload() {
		return iLogisticsDownload;
	}
	public void setiLogisticsDownload(Long iLogisticsDownload) {
		this.iLogisticsDownload = iLogisticsDownload;
	}

	public Long getiUsr() {
		return iUsr;
	}
	public void setiUsr(Long iUsr) {
		this.iUsr = iUsr;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getFileUUid() {
		return fileUUid;
	}
	public void setFileUUid(String fileUUid) {
		this.fileUUid = fileUUid;
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
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
}
