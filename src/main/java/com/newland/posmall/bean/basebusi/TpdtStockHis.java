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
 * 产品存量历史
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_pdt_stock_his")
public class TpdtStockHis implements Serializable {

	private static final long serialVersionUID = 2801092965977700292L;

	@Id
	@TableGenerator(name = "i_pdt_stock_his", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_pdt_stock_his")
	@Column(name = "i_pdt_stock_his")
	private Long ipdtStockHis;

	@Column(name = "i_pdt")
	private Long ipdt;
	
	@Column(name = "num")
	private Integer num;//数量

	@Column(name = "day")
	private Date day;

	@Column(name = "gen_time",updatable = false)
	private Date genTime;//生成时间

	@Column(name = "i_user")
	private Long iuser;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "ver")
	private Long ver;//版本号

	public Long getIpdtStockHis() {
		return ipdtStockHis;
	}

	public void setIpdtStockHis(Long ipdtStockHis) {
		this.ipdtStockHis = ipdtStockHis;
	}

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

	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

	public Date getGenTime() {
		return genTime;
	}

	public void setGenTime(Date genTime) {
		this.genTime = genTime;
	}

	public Long getIuser() {
		return iuser;
	}

	public void setIuser(Long iuser) {
		this.iuser = iuser;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getVer() {
		return ver;
	}

	public void setVer(Long ver) {
		this.ver = ver;
	}

}
