package org.ohuyo.rapid.base.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Type;

/**
 * 
 * @author rabbit
 *
 */
@Entity
@Table(name = "t_file")
public class Tfile implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -222693692424295458L;
	
	public static final String LOCATION_DISK = "0";
	
	public static final String LOCATION_DATABASE = "1";

	public Long getIfile() {
		return ifile;
	}

	public void setIfile(Long ifile) {
		this.ifile = ifile;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Long getLength() {
		return length;
	}

	public void setLength(Long length) {
		this.length = length;
	}

	public String getSha() {
		return sha;
	}

	public void setSha(String sha) {
		this.sha = sha;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public Date getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}

	@Id
	@TableGenerator(name = "ifile", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ifile")
	@Column(name = "i_file")
	private Long ifile;

	/**
	 * uuid 用于存放文件时转换
	 */
	@Column(name = "uuid", updatable = false)
	private String uuid;

	/**
	 * 文件长度
	 */
	@Column(name = "length", updatable = false)
	private Long length;

	/**
	 * sha签名串
	 */
	@Column(name = "sha", updatable = false)
	private String sha;

	/**
	 * 文件名
	 */
	@Column(name = "name", updatable = false)
	private String name;
	/**
	 * 扩展名
	 */
	@Column(name = "ext_name", updatable = false)
	private String ext;

	
	/**
	 * 文件存放位置 0-数据库 1-磁盘
	 */
	@Column(name = "location", updatable = false)
	private String location;
	
	/**
	 * 数据类型
	 */
	@Column (name = "content_type")
	private String contentType;

	/**
	 * 文件内容
	 */
	@Lob
    @Basic(fetch=FetchType.LAZY)
    @Column(columnDefinition = "BLOB", name = "content", nullable=true)
	private byte[] content;

	/**
	 * 生成时间
	 */
	@Column(name = "gen_time", updatable = false)
	private Date genTime;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
}
