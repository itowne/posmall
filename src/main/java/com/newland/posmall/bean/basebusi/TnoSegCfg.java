package com.newland.posmall.bean.basebusi;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 * 产品号段表
 */
@Entity
@Table(name = "t_no_seg_cfg")
public class TnoSegCfg implements Serializable {

	private static final long serialVersionUID = 3836405621614931020L;

	@Id
	@TableGenerator(name = "i_no_seg_cfg", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_no_seg_cfg")
	@Column(name = "i_no_seg_cfg")
	private Long inoSegCfg;

	@Column(name = "pre")
	private String pre;// 前缀

	@Column(name = "start")
	private Long start;// 开始

	@Column(name = "end")
	private Long end;// 结束

	@Column(name = "idx")
	private Long idx;// 索引位置
	
	@Version
	private Long version;// 版本号
	
	@Transient
	private String startSeq;
	
	@Transient
	private String endSeq;

	public Long getInoSegCfg() {
		return inoSegCfg;
	}

	public void setInoSegCfg(Long inoSegCfg) {
		this.inoSegCfg = inoSegCfg;
	}

	public String getPre() {
		return pre;
	}

	public void setPre(String pre) {
		this.pre = pre;
	}

	public Long getStart() {
		return start;
	}

	public void setStart(Long start) {
		if (start < (this.idx + 1)) throw new RuntimeException("起始序列号不能小于索引位置");
		this.idx = start - 1;
		this.start = start;
	}

	public Long getEnd() {
		return end;
	}

	public void setEnd(Long end) {
		if (end < this.idx + 1) throw new RuntimeException("结束号段不能小于索引位置");
		this.end = end;
	}

	public Long getIdx() {
		return idx;
	}

	public void setIdx(Long idx) {
		this.idx = idx;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getStartSeq() {
		return this.startSeq = pre + String.format("%1$0" + 8 + "d", start);
	}

	public String getEndSeq() {
		return this.endSeq = pre + String.format("%1$0" + 8 + "d", end);
	}

}
