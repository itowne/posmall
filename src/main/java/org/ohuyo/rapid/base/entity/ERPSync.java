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
import javax.persistence.Version;

import org.ohuyo.commons.format.annotation.BeanClass;
import org.ohuyo.commons.format.annotation.BeanField;

/**
 * ERP同步接口定义
 * 
 * @author Mr.Towne
 * 
 */
@BeanClass
@Entity
@Table(name = "t_erp_sync")
public class ERPSync implements Serializable {

	private static final long serialVersionUID = 1720395733099291829L;

	/**
	 * 逻辑主键
	 * 
	 */
	@Id
	@TableGenerator(name = "i_erp_sync", initialValue = 10000, allocationSize = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "i_erp_sync")
	@Column(name = "i_erp_sync")
	private Long ierpSync;

	/**
	 * 流水号
	 */
	@BeanField(index = 0)
	@Column(name = "id")
	private Long id;
	/**
	 * 业务员code
	 */
	@BeanField(index = 1)
	@Column(name = "ywy_code")
	private String ywyCode;
	/**
	 * 业务员
	 */
	@BeanField(index = 2)
	@Column(name = "ywy")
	private String ywy;
	/**
	 * 部门code
	 */
	@BeanField(index = 3)
	@Column(name = "dept_code")
	private String deptCode;
	/**
	 * 部门
	 */
	@BeanField(index = 4)
	@Column(name = "deptment")
	private String deptment;
	/**
	 * 单别
	 */
	@BeanField(index = 5)
	@Column(name = "db")
	private String db;
	/**
	 * 单号
	 */
	@BeanField(index = 6)
	@Column(name = "dh")
	private String dh;
	/**
	 * 单据日期
	 */
	@BeanField(index = 7)
	@Column(name = "dj_date")
	private String djDate;
	/**
	 * 客户简称
	 */
	@BeanField(index = 8)
	@Column(name = "khjc")
	private String khjc;
	/**
	 * 收款单位
	 */
	@BeanField(index = 9)
	@Column(name = "skdw")
	private String skdw;
	/**
	 * 销货日期
	 */
	@BeanField(index = 10)
	@Column(name = "xhrq")
	private String xhrq;
	/**
	 * 数量
	 */
	@BeanField(index = 11)
	@Column(name = "qty")
	private Long qty;
	/**
	 * 产品目录
	 */
	@BeanField(index = 12)
	@Column(name = "cpml")
	private String cpml;
	/**
	 * 品号
	 */
	@BeanField(index = 13)
	@Column(name = "ph")
	private String ph;
	/**
	 * 仓库code
	 */
	@BeanField(index = 14)
	@Column(name = "ck_code")
	private String ckCode;
	/**
	 * 仓库
	 */
	@BeanField(index = 15)
	@Column(name = "ck_name")
	private String ckName;
	/**
	 * 订单单号
	 */
	@BeanField(index = 16)
	@Column(name = "dddh")
	private String dddh;
	/**
	 * 货运方式
	 */
	@BeanField(index = 17)
	@Column(name = "hyfs")
	private String hyfs;
	/**
	 * 收货地址
	 */
	@BeanField(index = 18)
	@Column(name = "shdz")
	private String shdz;
	/**
	 * 收货电话
	 */
	@BeanField(index = 19)
	@Column(name = "shdh")
	private String shdh;
	/**
	 * 收货人
	 */
	@BeanField(index = 20)
	@Column(name = "shr")
	private String shr;
	/**
	 * 到达时限
	 */
	@BeanField(index = 21)
	@Column(name = "ddsx")
	private String ddsx;
	/**
	 * 提货日期
	 */
	@BeanField(index = 22)
	@Column(name = "th_date")
	private String thDate;
	/**
	 * 发货日期
	 */
	@BeanField(index = 23)
	@Column(name = "fh_date")
	private String fhDate;
	/**
	 * 到货日期
	 */
	@BeanField(index = 24)
	@Column(name = "dh_date")
	private String dhDate;
	/**
	 * 收货确认单回收情况
	 */
	@BeanField(index = 25)
	@Column(name = "qrdhs")
	private String qrdhs;
	/**
	 * 收货确认单是否送达
	 */
	@BeanField(index = 26)
	@Column(name = "qrdsd")
	private String qrdsd;
	/**
	 * 异常反馈
	 */
	@BeanField(index = 27)
	@Column(name = "ycfk")
	private String ycfk;
	/**
	 * 数据提取日期 从ERP同步到业务平台时间
	 */
	@BeanField(index = 28)
	@Column(name = "sjtq_date")
	private String sjtqDate;
	/**
	 * 物流公司帐号 备用字段
	 */
	@BeanField(index = 29)
	@Column(name = "wlgs")
	private String wlgs;
	/**
	 * 品名
	 */
	@BeanField(index = 30)
	@Column(name = "pm")
	private String pm;
	/**
	 * 规格
	 */
	@BeanField(index = 31)
	@Column(name = "gg")
	private String gg;
	/**
	 * 物流单号
	 */
	@BeanField(index = 32)
	@Column(name = "wldh")
	private String wldh;
	/**
	 * 序号 对应易飞销货单单身表序号
	 */
	@BeanField(index = 33)
	@Column(name = "xh")
	private String xh;
	/**
	 * 客户单号
	 */
	@BeanField(index = 34)
	@Column(name = "khdh")
	private String khdh;
	/**
	 * 审核状态 对应易飞系统中的销货单审核状态：Y-已审核；N-未审核
	 */
	@BeanField(index = 35)
	@Column(name = "shzt")
	private String shzt;
	/**
	 * 重量 采用的是字符串存储，而非数字类型
	 */
	@BeanField(index = 36)
	@Column(name = "tbzl")
	private String tbzl;
	/**
	 * 发货数量
	 */
	@BeanField(index = 37)
	@Column(name = "fhsl")
	private Integer fhsl;
	/**
	 * 发货箱数
	 */
	@BeanField(index = 38)
	@Column(name = "fhxs")
	private Integer fhxs;
	/**
	 * 产品序列号
	 */
	@BeanField(index = 39)
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(columnDefinition = "BLOB", name = "sn", nullable = true)
	private String sn;
	
	/**
	 * 点单号
	 */
	@BeanField(index = 40)
	@Column(name = "ddh")
	private String ddh;
	
	/**
	 * ypos序号
	 */
	@BeanField(index = 41)
	@Column(name = "ypos_xh")
	private String yposXh;
	
	@Column(name = "gen_time", updatable = false)
	private Date genTime;// 生成时间

	@Column(name = "upd_time")
	private Date updTime;// 更新时间
	
	@Version
	private Long version;// 版本

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getYwyCode() {
		return ywyCode;
	}

	public void setYwyCode(String ywyCode) {
		this.ywyCode = ywyCode;
	}

	public String getYwy() {
		return ywy;
	}

	public void setYwy(String ywy) {
		this.ywy = ywy;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptment() {
		return deptment;
	}

	public void setDeptment(String deptment) {
		this.deptment = deptment;
	}

	public String getDb() {
		return db;
	}

	public void setDb(String db) {
		this.db = db;
	}

	public String getDh() {
		return dh;
	}

	public void setDh(String dh) {
		this.dh = dh;
	}

	public String getDjDate() {
		return djDate;
	}

	public void setDjDate(String djDate) {
		this.djDate = djDate;
	}

	public String getKhjc() {
		return khjc;
	}

	public void setKhjc(String khjc) {
		this.khjc = khjc;
	}

	public String getSkdw() {
		return skdw;
	}

	public void setSkdw(String skdw) {
		this.skdw = skdw;
	}

	public String getXhrq() {
		return xhrq;
	}

	public void setXhrq(String xhrq) {
		this.xhrq = xhrq;
	}

	public Long getQty() {
		return qty;
	}

	public void setQty(Long qty) {
		this.qty = qty;
	}

	public String getCpml() {
		return cpml;
	}

	public void setCpml(String cpml) {
		this.cpml = cpml;
	}

	public String getPh() {
		return ph;
	}

	public void setPh(String ph) {
		this.ph = ph;
	}

	public String getCkCode() {
		return ckCode;
	}

	public void setCkCode(String ckCode) {
		this.ckCode = ckCode;
	}

	public String getCkName() {
		return ckName;
	}

	public void setCkName(String ckName) {
		this.ckName = ckName;
	}

	public String getDddh() {
		return dddh;
	}

	public void setDddh(String dddh) {
		this.dddh = dddh;
	}

	public String getHyfs() {
		return hyfs;
	}

	public void setHyfs(String hyfs) {
		this.hyfs = hyfs;
	}

	public String getShdz() {
		return shdz;
	}

	public void setShdz(String shdz) {
		this.shdz = shdz;
	}

	public String getShdh() {
		return shdh;
	}

	public void setShdh(String shdh) {
		this.shdh = shdh;
	}

	public String getShr() {
		return shr;
	}

	public void setShr(String shr) {
		this.shr = shr;
	}

	public String getDdsx() {
		return ddsx;
	}

	public void setDdsx(String ddsx) {
		this.ddsx = ddsx;
	}

	public String getThDate() {
		return thDate;
	}

	public void setThDate(String thDate) {
		this.thDate = thDate;
	}

	public String getFhDate() {
		return fhDate;
	}

	public void setFhDate(String fhDate) {
		this.fhDate = fhDate;
	}

	public String getDhDate() {
		return dhDate;
	}

	public void setDhDate(String dhDate) {
		this.dhDate = dhDate;
	}

	public String getQrdhs() {
		return qrdhs;
	}

	public void setQrdhs(String qrdhs) {
		this.qrdhs = qrdhs;
	}

	public String getQrdsd() {
		return qrdsd;
	}

	public void setQrdsd(String qrdsd) {
		this.qrdsd = qrdsd;
	}

	public String getYcfk() {
		return ycfk;
	}

	public void setYcfk(String ycfk) {
		this.ycfk = ycfk;
	}

	public String getSjtqDate() {
		return sjtqDate;
	}

	public void setSjtqDate(String sjtqDate) {
		this.sjtqDate = sjtqDate;
	}

	public String getWlgs() {
		return wlgs;
	}

	public void setWlgs(String wlgs) {
		this.wlgs = wlgs;
	}

	public String getPm() {
		return pm;
	}

	public void setPm(String pm) {
		this.pm = pm;
	}

	public String getGg() {
		return gg;
	}

	public void setGg(String gg) {
		this.gg = gg;
	}

	public String getWldh() {
		return wldh;
	}

	public void setWldh(String wldh) {
		this.wldh = wldh;
	}

	public String getXh() {
		return xh;
	}

	public void setXh(String xh) {
		this.xh = xh;
	}

	public String getKhdh() {
		return khdh;
	}

	public void setKhdh(String khdh) {
		this.khdh = khdh;
	}

	public String getShzt() {
		return shzt;
	}

	public void setShzt(String shzt) {
		this.shzt = shzt;
	}

	public String getTbzl() {
		return tbzl;
	}

	public void setTbzl(String tbzl) {
		this.tbzl = tbzl;
	}

	public Long getIerpSync() {
		return ierpSync;
	}

	public void setIerpSync(Long ierpSync) {
		this.ierpSync = ierpSync;
	}

	public Integer getFhsl() {
		return fhsl;
	}

	public void setFhsl(Integer fhsl) {
		this.fhsl = fhsl;
	}

	public Integer getFhxs() {
		return fhxs;
	}

	public void setFhxs(Integer fhxs) {
		this.fhxs = fhxs;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getDdh() {
		return ddh;
	}

	public void setDdh(String ddh) {
		this.ddh = ddh;
	}

	public String getYposXh() {
		return yposXh;
	}

	public void setYposXh(String yposXh) {
		this.yposXh = yposXh;
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
