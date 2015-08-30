package com.newland.posmall.base.service;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.entity.condition.TlogCondition;
import org.springframework.stereotype.Service;
import com.newland.posmall.base.dao.TlogDao;
import com.newland.posmall.bean.common.Tlog;


@Service
public class TlogService {
	
	@Resource
	private TlogDao tlogDao;
	
	
	public void save(Tlog tlog){
		Date date = new Date();
		tlog.setGenTime(date);
		this.tlogDao.save(tlog);
				
	}
	
	public void update(Tlog tlog){
		Tlog tlogNew = tlogDao.get(tlog.getIlog());
		
		this.tlogDao.update(tlogNew);		
	}
	
	public Tlog find(Long id){
		return this.tlogDao.get(id);
		
	}
	
	public List<Tlog> queryTlog(TlogCondition tlogCondition,Page page){
		return this.tlogDao.find(tlogCondition, page);
	}
}
