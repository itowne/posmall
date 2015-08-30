package com.newland.posmall.biz.common;

import java.util.Map;

import javax.annotation.Resource;

import org.ohuyo.rapid.base.service.TdictService;
import org.ohuyo.rapid.base.service.TsysService;
import org.ohuyo.rapid.base.service.TuserService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newland.posmall.base.service.TcustRegService;
import com.newland.posmall.base.service.TcustService;
import com.newland.posmall.base.service.TlogisticsComService;
import com.newland.posmall.base.service.TordService;
import com.newland.posmall.base.service.TpdtService;

@Service("posmall.mapBiz")
@Transactional
public class MapBiz {
	
	@Resource
	private TdictService tdictService;
	@Resource
	private TpdtService tpdtService;
	
	@Resource
	private TsysService tsysService;
	
	@Resource
	private TuserService tuserService;
	
	@Resource
	private TcustService tcustService;
	
	@Resource
	private TcustRegService tcustRegService;
	
	@Resource
	private TordService tordService;
	
	@Resource
	private TlogisticsComService tlogisticsComService;
	
//	@Cacheable(value = "posmall.cacheMap")
	public Map<String, String> getMapByType(String dictType) {
		Map<String, String> map = null;
		switch (dictType) {
		case "pdt_name":
			//产品map(id,name)
			map =  tpdtService.queryTpdtMap();
			break;
		case "pdt_name_all":
			//所有产品map(包括删除)(id,name)
			map =  tpdtService.queryTpdtMapAll();
			break;
		case "pdt_his_name":
			//产品map(id,name)
			map =  tpdtService.queryTpdtHisMap();
			break;
		case "sys_loginName":
			map =  tsysService.queryTsysMap();
			break;
		case "user_loginName":
			map =  tuserService.queryTuserMap();
			break;
		case "cust_loginName":
			map =  tcustService.queryTcustMap();
			break;
		case "custreg_name":
			map =  tcustRegService.queryTcustRegMap();
			break;
		case "ord_no":
			map =  tordService.queryTordMap();
			break;
		case "logistics_com":
			//物流公司map(id,fullname)
			map =  tlogisticsComService.queryLogisticsComMap();
			break;
		default:
			//数据字典map(code,value)
			map = tdictService.dictMap(dictType);
			break;
		}
		return map;
	}
}
