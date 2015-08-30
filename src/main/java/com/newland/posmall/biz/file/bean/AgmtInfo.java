package com.newland.posmall.biz.file.bean;

import java.math.BigDecimal;
import java.util.Date;
import org.ohuyo.commons.format.annotation.BeanClass;
import org.ohuyo.commons.format.annotation.BeanElement;
import org.ohuyo.commons.format.annotation.BeanElementType;
import org.ohuyo.commons.format.annotation.BeanField;
import org.ohuyo.rapid.file.Title;

/**
 * 
 * @ClassName: AgmtInfo
 * @Description: 订货协议信息表
 * @author chenwenjing
 * @date 2014-9-6 上午10:42:20
 * 
 */
@BeanClass
public class AgmtInfo {

	@Title("协议编号")
	@BeanField(index = 0)
	private String agmtNo;// 协议编号

	@Title("协议起始时间")
	@BeanField(index = 1, element = @BeanElement(type = BeanElementType.date, pattern = "yyyy-MM-dd"))
	private Date startTime;// 协议起始时间

	@Title("协议终止时间")
	@BeanField(index = 2, element = @BeanElement(type = BeanElementType.date, pattern = "yyyy-MM-dd"))
	private Date endTime;// 协议终止时间

	@Title("保证金")
	@BeanField(index = 3, element = @BeanElement(type = BeanElementType.bigDecimal, pattern = "0.00"))
	private BigDecimal deposit; // 保证金

	@Title("协议生效时间")
	@BeanField(index = 4, element = @BeanElement(type = BeanElementType.date, pattern = "yyyy-MM-dd HH:mm:ss"))
	private Date efficientTime;// 协议生效时间（审核通过的时间）

	@Title("已用保证金")
	@BeanField(index = 5, element = @BeanElement(type = BeanElementType.bigDecimal, pattern = "0.00"))
	private BigDecimal usedDeposit;// 已用保证金

	@Title("剩余保证金")
	@BeanField(index = 6, element = @BeanElement(type = BeanElementType.bigDecimal, pattern = "0.00"))
	private BigDecimal remainDeposit;// 剩余保证金

	@Title("协议状态")
	@BeanField(index = 7)
	private String agmtStatus;// 协议状态

	// @Title("协议订货量")
	// @BeanField(index = 8)
	// private Integer totalNum;//协议订货量
	//
	// @Title("总货款金额")
	// @BeanField(index = 9,element = @BeanElement(type =
	// BeanElementType.bigDecimal, pattern="0.00"))
	// private BigDecimal totalAmt;//总货款金额
	//
	// @Title("产品名称集合")
	// @BeanField(index = 10)
	// private String tpdtNames;//产品名称集合

	@Title("产品型号")
	@BeanField(index = 8)
	private String pdtNo;// 产品型号

	@Title("产品名称")
	@BeanField(index = 9)
	private String name;// 产品名称

	@Title("产品全称")
	@BeanField(index = 10)
	private String longName;// 产品全称

	@Title("数量")
	@BeanField(index = 11)
	private Integer num;// 数量

	@Title("产品单价")
	@BeanField(index = 12, element = @BeanElement(type = BeanElementType.bigDecimal, pattern = "0.00"))
	private BigDecimal price;// 产品单价

	@Title("状态")
	@BeanField(index = 13)
	private String agmtDetailStatus; // 状态

	@Title("折扣率")
	@BeanField(index = 14, element = @BeanElement(type = BeanElementType.bigDecimal, pattern = "0.00"))
	private BigDecimal rate;// 折扣率

	@Title("已使用额度")
	@BeanField(index = 15)
	private Integer usedQuota;// 已使用额度

	@Title("剩余额度")
	@BeanField(index = 16)
	private Integer remainQuota;// 剩余额度

	@Title("修改人姓名")
	@BeanField(index = 17)
	private String userName;// 修改人姓名

	@Title("备注")
	@BeanField(index = 18)
	private String memo;// 备注

	@Title("生成时间")
	@BeanField(index = 19, element = @BeanElement(type = BeanElementType.date, pattern = "yyyy-MM-dd HH:mm:ss"))
	private Date genTime;// 生成时间

	@Title("更新时间")
	@BeanField(index = 20, element = @BeanElement(type = BeanElementType.date, pattern = "yyyy-MM-dd HH:mm:ss"))
	private Date updTime;// 更新时间

	public String getAgmtNo() {
		return agmtNo;
	}

	public void setAgmtNo(String agmtNo) {
		this.agmtNo = agmtNo;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public BigDecimal getDeposit() {
		return deposit;
	}

	public void setDeposit(BigDecimal deposit) {
		this.deposit = deposit;
	}

	public Date getEfficientTime() {
		return efficientTime;
	}

	public void setEfficientTime(Date efficientTime) {
		this.efficientTime = efficientTime;
	}

	public BigDecimal getUsedDeposit() {
		return usedDeposit;
	}

	public void setUsedDeposit(BigDecimal usedDeposit) {
		this.usedDeposit = usedDeposit;
	}

	public BigDecimal getRemainDeposit() {
		return remainDeposit;
	}

	public void setRemainDeposit(BigDecimal remainDeposit) {
		this.remainDeposit = remainDeposit;
	}

	public String getAgmtStatus() {
		return agmtStatus;
	}

	public void setAgmtStatus(String agmtStatus) {
		this.agmtStatus = agmtStatus;
	}

	// public Integer getTotalNum() {
	// return totalNum;
	// }
	//
	// public void setTotalNum(Integer totalNum) {
	// this.totalNum = totalNum;
	// }
	//
	// public BigDecimal getTotalAmt() {
	// return totalAmt;
	// }
	//
	// public void setTotalAmt(BigDecimal totalAmt) {
	// this.totalAmt = totalAmt;
	// }
	//
	// public String getTpdtNames() {
	// return tpdtNames;
	// }
	//
	// public void setTpdtNames(String tpdtNames) {
	// this.tpdtNames = tpdtNames;
	// }

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

	public String getPdtNo() {
		return pdtNo;
	}

	public void setPdtNo(String pdtNo) {
		this.pdtNo = pdtNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getAgmtDetailStatus() {
		return agmtDetailStatus;
	}

	public void setAgmtDetailStatus(String agmtDetailStatus) {
		this.agmtDetailStatus = agmtDetailStatus;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public Integer getUsedQuota() {
		return usedQuota;
	}

	public void setUsedQuota(Integer usedQuota) {
		this.usedQuota = usedQuota;
	}

	public Integer getRemainQuota() {
		return remainQuota;
	}

	public void setRemainQuota(Integer remainQuota) {
		this.remainQuota = remainQuota;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
