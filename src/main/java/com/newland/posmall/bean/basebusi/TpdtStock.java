package com.newland.posmall.bean.basebusi;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
/**
 * 产品存量
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_pdt_stock")
public class TpdtStock implements Serializable {

	private static final long serialVersionUID = 2801092965977700292L;

	@Id
//	@TableGenerator(name = "i_pdt", initialValue = 10000, allocationSize = 100)
//	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_pdt")
	@Column(name = "i_pdt")
	private Long ipdt;

	@Column(name = "num")
	private Integer num;//数量

	@Column(name = "gen_time",updatable = false)
	private Date genTime;//生成时间

	@Column(name = "upd_time")
	private Date updTime;//更新时间

	@Version
	private Long version;//版本号

	public Long getIpdt() {
		return ipdt;
	}

	public void setIpdt(Long ipdt) {
		this.ipdt = ipdt;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
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

}
