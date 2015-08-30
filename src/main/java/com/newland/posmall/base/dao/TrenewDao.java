package com.newland.posmall.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.newland.posmall.bean.customer.Trenew;
import com.newland.posmall.bean.customer.condition.TrenewCondition;

@Repository
public class TrenewDao {
	
	@Resource
	private BaseDao baseDao;
	
	public List<Trenew> findAll(){
		return baseDao.getHibernateDaoEx().find(Trenew.class);
	}
	
	public Trenew get(Long id){
		return (Trenew) baseDao.getSession().get(Trenew.class, id);
	}
	
	public void save(Trenew trenew){
		baseDao.getSession().save(trenew);
	}
	
	public void update(Trenew trenew){
		baseDao.getSession().update(trenew);
	}
	
	public List<Trenew> findByExample(Trenew trenew){		
		return baseDao.getHibernateDaoEx().findByExample(trenew);	
	}
	@SuppressWarnings("unchecked")
	public List<Trenew> find(TrenewCondition condition,Page page){
		return baseDao.getHibernateDaoEx().findByCriteriaEx(condition, page);
	}
}
