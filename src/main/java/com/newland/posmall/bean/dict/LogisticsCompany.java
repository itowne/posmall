package com.newland.posmall.bean.dict;

public enum LogisticsCompany {

	yuantong("圆通快递"),
	shunfeng("顺丰快递"),
	ems("邮政特快专递"),
	zhaijisong("宅急送"),
	shentong("申通快递"),
	jialidatong("嘉里大通"),
	shengfeng("盛丰"),
	minhang("民航"),
	yunda("韵达快递");
	
	private String name;
	
	LogisticsCompany(String name){
		this.name=name;
	}

	public String getName() {
		return name;
	}
	
	
}
