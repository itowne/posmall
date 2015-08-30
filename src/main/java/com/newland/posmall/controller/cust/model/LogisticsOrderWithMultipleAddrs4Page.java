package com.newland.posmall.controller.cust.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newland.posmall.bean.basebusi.Tord;

/**
 * 
 * @author Administrator
 *
 */
public class LogisticsOrderWithMultipleAddrs4Page {
	/**
	 * 物流单订单信息
	 */
	private Tord tord;
	
	/**
	 * 物流订单地址详细
	 */
	private List<LogisticsOrdAddr4Page> addrs;

	/**
	 * 物流订单产品数量汇总信息
	 */
	private List<LogisticsOrdPdt4Page> pdts;
	
	/**
	 * 实际发货时间(字符串)
	 */
	private List<String> realDeliveryDateFormList;
	
	/**
	 * 实际到达时间 (字符串)
	 */
	private List<String> realArrivalDateFormList;

	public synchronized List<LogisticsOrdPdt4Page> getPdts() {
		if (pdts != null) {
			return pdts;
		}
		List<LogisticsOrdPdt4Page> ret = new ArrayList<LogisticsOrdPdt4Page>();
		Map<String, LogisticsOrdPdt4Page> map = new HashMap<String, LogisticsOrdPdt4Page>();
		for (LogisticsOrdAddr4Page add : addrs) {
			String pdtName = add.getPdtName();
			LogisticsOrdPdt4Page lop = map.get(pdtName);
			if (lop == null) {
				lop = new LogisticsOrdPdt4Page();
				lop.ipdtHis = add.getTlogisticsOrdAddr().getIpdtHis();
				lop.pdtName = pdtName;
				map.put(pdtName, lop);
			}
			lop.num += add.getTlogisticsOrdAddr().getNum();
		}
		ret.addAll(map.values());
		pdts = ret;
		return pdts;
	}

	public class LogisticsOrdPdt4Page {
		private Long ipdtHis;
		private String pdtName;
		private Integer num = 0;

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

		public Integer getNum() {
			return num;
		}

		public void setNum(Integer num) {
			this.num = num;
		}

	}

	public List<LogisticsOrdAddr4Page> getAddrs() {
		return addrs;
	}

	public void setAddrs(List<LogisticsOrdAddr4Page> addrs) {
		this.addrs = addrs;
	}

	public void setPdts(List<LogisticsOrdPdt4Page> pdts) {
		this.pdts = pdts;
	}

	public Tord getTord() {
		return tord;
	}

	public void setTord(Tord tord) {
		this.tord = tord;
	}

	public List<String> getRealDeliveryDateFormList() {
		return realDeliveryDateFormList;
	}

	public void setRealDeliveryDateFormList(List<String> realDeliveryDateFormList) {
		this.realDeliveryDateFormList = realDeliveryDateFormList;
	}

	public List<String> getRealArrivalDateFormList() {
		return realArrivalDateFormList;
	}

	public void setRealArrivalDateFormList(List<String> realArrivalDateFormList) {
		this.realArrivalDateFormList = realArrivalDateFormList;
	}

}
