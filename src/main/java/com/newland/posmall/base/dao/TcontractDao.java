package com.newland.posmall.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.newland.posmall.bean.basebusi.Tcontract;
import com.newland.posmall.bean.basebusi.condition.TcontractCondition;



@Repository
public class TcontractDao {

	@Resource
	private BaseDao baseDao;
	
	public List<Tcontract> find(Tcontract tcontract){
		return baseDao.getHibernateDaoEx().findByExample(tcontract);
	}

	public void save(Tcontract tcontract) {
		baseDao.getSession().save(tcontract);
	}
	
	public void update(Tcontract tcontract) {
		baseDao.getSession().update(tcontract);
	}
	
	public List<Tcontract> find() {
		return baseDao.getHibernateDaoEx().find(Tcontract.class);
	}
	
	public Tcontract get(Long id) {
		return (Tcontract) baseDao.getSession().get(Tcontract.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Tcontract> find(TcontractCondition tcontractCondition,Page page) {
		return baseDao.getHibernateDaoEx().findByCriteriaEx(tcontractCondition, page);
	}

}
