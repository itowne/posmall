package com.newland.posmall.base.dao;

import javax.annotation.Resource;
import java.util.List;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.newland.posmall.bean.basebusi.OrderDownLoad;
import com.newland.posmall.bean.basebusi.condition.OrderDownLoadCondition;

@Repository
public class OrdDownLoadDao {

	@Resource
	private BaseDao baseDao;

	public List<OrderDownLoad> findAll() {
		return baseDao.getHibernateDaoEx().find(OrderDownLoad.class);
	}

	public OrderDownLoad findObjByCondition(OrderDownLoad od) {
		return (OrderDownLoad) baseDao.getHibernateDaoEx().findByExample(od);
	}

	public void save(OrderDownLoad od) {
		this.baseDao.getSession().save(od);
	}
	
	@SuppressWarnings("unchecked")
	public List<OrderDownLoad> findListByCondition(OrderDownLoadCondition cond){
		return baseDao.getHibernateDaoEx().findByCriteriaEx(cond);
	}
	
	public List<OrderDownLoad> findPageByCondition(OrderDownLoadCondition cond,Page page){
		return baseDao.getHibernateDaoEx().findByCriteriaEx(cond,page);
	}
}
