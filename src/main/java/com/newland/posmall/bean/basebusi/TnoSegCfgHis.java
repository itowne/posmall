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
 * 产品号段历史表
 */
@Entity
@Table(name = "t_no_seg_cfg_his")
public class TnoSegCfgHis implements Serializable {

	private static final long serialVersionUID = -5125084714133072589L;

	@Id
	@TableGenerator(name = "i_no_seg_cfg_his", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_no_seg_cfg_his")
	@Column(name = "i_no_seg_cfg_his")
	private Long inoSegCfgHis;
	
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
	
	@Column(name = "gen_time",updatable = false)
	private Date genTime;     //生成时间

	@Column(name = "ver")
	private Long ver;// 版本号

	public Long getInoSegCfgHis() {
		return inoSegCfgHis;
	}

	public void setInoSegCfgHis(Long inoSegCfgHis) {
		this.inoSegCfgHis = inoSegCfgHis;
	}

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
		this.start = start;
	}

	public Long getEnd() {
		return end;
	}

	public void setEnd(Long end) {
		this.end = end;
	}

	public Long getIdx() {
		return idx;
	}

	public void setIdx(Long idx) {
		this.idx = idx;
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

}
