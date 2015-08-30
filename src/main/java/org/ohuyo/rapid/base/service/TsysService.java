package org.ohuyo.rapid.base.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.dao.TsysDao;
import org.ohuyo.rapid.base.entity.Tsys;
import org.ohuyo.rapid.base.entity.condition.TsysCondition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class TsysService {

	@Resource
	private TsysDao tsysDao;
	
	public List<Tsys> findAll() {
		return this.tsysDao.findAll();
	}

	public List<Tsys> queryTsys(TsysCondition tsysConfition, Page page) {
		return this.tsysDao.find(tsysConfition, page);
	}
	
	public void save(Tsys tsys) {
		Date date = new Date();
		tsys.setGenTime(date);
		tsys.setUpdTime(date);
		this.tsysDao.save(tsys);

	}
	
	public void delete(Tsys tsys) {
		this.tsysDao.delete(tsys);
	}
	
	public void update(Tsys tsys) {
		tsys.setUpdTime(new Date());
		this.tsysDao.update(tsys);
	}

	public Tsys get(Long id) {
		return this.tsysDao.get(id);
	}

	public Tsys getByLoginName(String loginName) {
		return tsysDao.getByLoginName(loginName);
	}
	
	public Tsys getTsys(Long isys) {
		Tsys tsys = this.tsysDao.get(isys);
		return tsys;
	}
	
	public List<Tsys> findSelect(Tsys tsys){
		return this.tsysDao.findSelect(tsys);
    }
	
	public Map<String, String> queryTsysMap(){
		Map<String, String> map = null;
		List<Tsys> tsysList =  this.tsysDao.findAll();
		if(tsysList != null){
			 map = new TreeMap<String, String>();
			for(Tsys t : tsysList){
				map.put(""+t.getIsys(),t.getLoginName());
			}
		}
		return map;
	}

}
