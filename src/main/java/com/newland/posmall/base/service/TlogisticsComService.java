package com.newland.posmall.base.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.newland.posmall.base.dao.TlogisticsComDao;
import com.newland.posmall.bean.basebusi.TlogisticsCom;
import com.newland.posmall.bean.basebusi.condition.LogisticscompanyCondition;
import com.newland.posmall.bean.dict.LogisticsStatus;
import com.newland.posmall.bean.dict.YesNoType;

@Service
public class TlogisticsComService {

	@Resource
	private TlogisticsComDao tlogisticsComDao;
	
	public TlogisticsCom findById(Long id) {
		return this.tlogisticsComDao.get(id);
	}
	
	public List<TlogisticsCom> findBySelect(TlogisticsCom tlogisticsCom) {
		return this.tlogisticsComDao.find(tlogisticsCom);
	}
	
	public List<TlogisticsCom> queryLogisticscompany(LogisticscompanyCondition logisticscompanyCondition,Page page){
		return this.tlogisticsComDao.findCondition(logisticscompanyCondition,page);
	}
	
	public void add(TlogisticsCom tlogisticsCom) {
		tlogisticsCom.setGenTime(new Date());
		tlogisticsCom.setUpdTime(new Date());
		this.tlogisticsComDao.save(tlogisticsCom);
	}
	
	public void modify(TlogisticsCom tlogisticsCom) {
		
		tlogisticsCom.setUpdTime(new Date());
		
		this.tlogisticsComDao.update(tlogisticsCom);
	}
	
	/**
	 * 物流公司map(id,fullname)
	 */
	public Map<String, String> queryLogisticsComMap(){
		Map<String, String> map = null;
		TlogisticsCom tc = new TlogisticsCom();
		tc.setLogisticsStatus(LogisticsStatus.SELECT);
		List<TlogisticsCom> tlogisticsComList = findBySelect(tc);
		if(!CollectionUtils.isEmpty(tlogisticsComList)){
			map = new TreeMap<String, String>();
			for(TlogisticsCom tlogisticsCom : tlogisticsComList){
				map.put(String.valueOf(tlogisticsCom.getIlogisticsCom()),tlogisticsCom.getFullname());
			}
		}
		return map;
	}
	
	/**
	 * 是否付费物流公司
	 * @return
	 */
	public Map<String, TlogisticsCom> queryFeeLogisticsComMap(YesNoType type){
		Map<String, TlogisticsCom> map = null;
		TlogisticsCom tc = new TlogisticsCom();
		tc.setLogisticsStatus(LogisticsStatus.SELECT);
		tc.setFeeFlag(type);
		List<TlogisticsCom> tlogisticsComList = findBySelect(tc);
		if(!CollectionUtils.isEmpty(tlogisticsComList)){
			map = new TreeMap<String, TlogisticsCom>();
			for(TlogisticsCom tlogisticsCom : tlogisticsComList){
				map.put(String.valueOf(tlogisticsCom.getIlogisticsCom()), tlogisticsCom);
			}
		}
		return map;
		
	}
	
	
}
