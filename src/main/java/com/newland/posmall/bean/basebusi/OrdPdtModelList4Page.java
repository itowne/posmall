package com.newland.posmall.bean.basebusi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrdPdtModelList4Page implements Serializable{
	
	private static final long serialVersionUID = 2285055710578655399L;

	List<OrdPdtModel4Page> ordPdtModel4PageList = new ArrayList<OrdPdtModel4Page>();
	
	private String remark="";

	public List<OrdPdtModel4Page> getOrdPdtModel4PageList() {
		return ordPdtModel4PageList;
	}

	public void setOrdPdtModel4PageList(List<OrdPdtModel4Page> ordPdtModel4PageList) {
		this.ordPdtModel4PageList = ordPdtModel4PageList;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	

}
