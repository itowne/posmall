package com.newland.posmall.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.newland.posmall.bean.customer.TcustAppver;
import com.newland.posmall.bean.customer.condition.TcustAppverCondition;

@Repository
public class TcustAppverDao {
	
	@Resource
	private BaseDao baseDao;
	
	public List<TcustAppver> findAll(){
		return baseDao.getHibernateDaoEx().find(TcustAppver.class);
	}
	
	public TcustAppver get(Long id){
		return (TcustAppver) baseDao.getSession().get(TcustAppver.class, id);
	}
	
	public void save(TcustAppver tcustAppver){
		baseDao.getSession().save(tcustAppver);
	}
	
	public void update(TcustAppver tcustAppver){
		baseDao.getSession().update(tcustAppver);
	}
	/**
	 * 
	 * @Description: findByExample不支持主键，不支持关联，不支持null
	 * @author chenwenjing
	 * @date 2013-11-5上午10:32:36
	 */
	public List<TcustAppver> findListByCondition(TcustAppver tcustAppver){		
		return baseDao.getHibernateDaoEx().findByExample(tcustAppver);
		
	}
	public TcustAppver findObjectByCondition(TcustAppver t) {
		return baseDao.getHibernateDaoEx().getFirstOneByExample(t);
	}
	@SuppressWarnings("unchecked")
	public List<TcustAppver> find(TcustAppverCondition condition,Page page){
		return baseDao.getHibernateDaoEx().findByCriteriaEx(condition, page);
	}
	@SuppressWarnings("unchecked")
	public List<TcustAppver> find(TcustAppverCondition condition){
		return baseDao.getHibernateDaoEx().findByCriteriaEx(condition);
	}
	
}
