package com.newland.posmall.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.newland.posmall.bean.customer.Twarranty;
import com.newland.posmall.bean.customer.condition.TwarrantyCondition;

@Repository
public class TwarrantyDao {
	
	@Resource
	private BaseDao baseDao;
	
	public List<Twarranty> findAll(){
		return baseDao.getHibernateDaoEx().find(Twarranty.class);
	}
	
	public Twarranty get(Long id){
		return (Twarranty) baseDao.getSession().get(Twarranty.class, id);
	}
	
	public void save(Twarranty twarranty){
		baseDao.getSession().save(twarranty);
	}
	
	public void update(Twarranty twarranty){
		baseDao.getSession().update(twarranty);
	}
	
	public List<Twarranty> findSelect(Twarranty twarranty){		
		return baseDao.getHibernateDaoEx().findByExample(twarranty);
		
	}
	@SuppressWarnings("unchecked")
	public List<Twarranty> find(TwarrantyCondition condition,Page page){
		return baseDao.getHibernateDaoEx().findByCriteriaEx(condition, page);
	}
	@SuppressWarnings("unchecked")
	public List<Twarranty> find(TwarrantyCondition condition){
		return baseDao.getHibernateDaoEx().findByCriteriaEx(condition);
	}
	
}
