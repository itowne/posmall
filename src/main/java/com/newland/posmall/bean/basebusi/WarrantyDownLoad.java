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

/**
 * 维保受理下载关联表
 * @author Mr.Towne
 *
 */
@Entity
@Table(name = "t_warranty_download")
public class WarrantyDownLoad implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2143065593469970080L;
	/**
	 * 逻辑id
	 */
	@Id
	@TableGenerator(name = "i_warranty_download", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_warranty_download")
	@Column(name = "i_warranty_download")
	private Long iwarrantyDownload;
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

	public Long getIwarrantyDownload() {
		return iwarrantyDownload;
	}
	public void setIwarrantyDownload(Long iwarrantyDownload) {
		this.iwarrantyDownload = iwarrantyDownload;
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
