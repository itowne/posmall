package com.newland.posmall.biz.file.bean;

import org.ohuyo.commons.format.annotation.BeanClass;
import org.ohuyo.commons.format.annotation.BeanField;
import org.ohuyo.rapid.file.Title;

/**
 * 
* @ClassName: AgmtReportInfo 
* @Description: 协议报表 
* @author chenwenjing
* @date 2014-11-20 上午8:56:57
 */
@BeanClass
public class AgmtReportInfo {

	@Title("客户名称")
	@BeanField(index = 0)
	private String custName;// 客户名称

	@Title("协议数量(已审核过的协议总数量)")
	@BeanField(index = 1)
	private Integer agmtNum;// 协议数量(已审核过的协议总数量)

	@Title("点单数量(已审核过的点单总数量)")
	@BeanField(index = 2)
	private Integer ordNum;// 点单数量(已审核过的点单总数量)

	@Title("已发货数量(已生产销货单的总数量)")
	@BeanField(index = 3)
	private Integer logisticsNum; // 已发货数量(已生产销货单的总数量)

	@Title("差异(等于点单数量减去已发货数量)")
	@BeanField(index = 4)
	private Integer deviation;// 差异(等于点单数量减去已发货数量)

	@Title("备注说明")
	@BeanField(index = 5)
	private String memo;//备注说明(导出时为空)

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public Integer getAgmtNum() {
		return agmtNum;
	}

	public void setAgmtNum(Integer agmtNum) {
		this.agmtNum = agmtNum;
	}

	public Integer getOrdNum() {
		return ordNum;
	}

	public void setOrdNum(Integer ordNum) {
		this.ordNum = ordNum;
	}

	public Integer getLogisticsNum() {
		return logisticsNum;
	}

	public void setLogisticsNum(Integer logisticsNum) {
		this.logisticsNum = logisticsNum;
	}

	public Integer getDeviation() {
		return deviation;
	}

	public void setDeviation(Integer deviation) {
		this.deviation = deviation;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

    	
}
