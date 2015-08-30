package com.newland.posmall.bean.basebusi;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrdPdtModel4Page {
	
	private Long ipdt;//产品id
	
	private Long ipdtHis;//产品历史id
	
	private String pdtName;//产品名称
	
	private BigDecimal price; //单价
	
	private BigDecimal rate;//折扣率
	
	private BigDecimal priceAfterRate; //折扣价
	
	private Integer remainAgmtQuota;//剩余协议额度
	
	private Integer allOrderNumOfDaily; //下订单时该产品的总量

	List<OrdPdtModelDaily> ordPdtModelList = new ArrayList<OrdPdtModelDaily>();

	public Long getIpdt() {
		return ipdt;
	}

	public void setIpdt(Long ipdt) {
		this.ipdt = ipdt;
	}

	public Long getIpdtHis() {
		return ipdtHis;
	}

	public void setIpdtHis(Long ipdtHis) {
		this.ipdtHis = ipdtHis;
	}

	public String getPdtName() {
		return pdtName;
	}

	public void setPdtName(String pdtName) {
		this.pdtName = pdtName;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public BigDecimal getPriceAfterRate() {
		return priceAfterRate;
	}

	public void setPriceAfterRate(BigDecimal priceAfterRate) {
		this.priceAfterRate = priceAfterRate;
	}

	public Integer getRemainAgmtQuota() {
		return remainAgmtQuota;
	}

	public void setRemainAgmtQuota(Integer remainAgmtQuota) {
		this.remainAgmtQuota = remainAgmtQuota;
	}

	public List<OrdPdtModelDaily> getOrdPdtModelList() {
		return ordPdtModelList;
	}
	
	public void setOrdPdtModelList(List<OrdPdtModelDaily> ordPdtModelList) {
		this.ordPdtModelList = ordPdtModelList;
	}
	
	public Integer getAllOrderNumOfDaily() {
		return allOrderNumOfDaily;
	}
	
	public void setAllOrderNumOfDaily(Integer allOrderNumOfDaily) {
		this.allOrderNumOfDaily = allOrderNumOfDaily;
	}
}
