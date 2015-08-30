package com.newland.posmall.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.newland.posmall.bean.customer.TcustStock;
import com.newland.posmall.bean.customer.condition.TcustStockCondition;

/**
 * 
 * @author zhouym
 * 
 */
@Repository
public class TcustStockDao {

	@Resource
	private BaseDao baseDao;

	public void save(TcustStock tcustStock) {
		baseDao.getSession().save(tcustStock);
	}

	public void update(TcustStock tcustStock) {
		baseDao.getSession().update(tcustStock);
	}

	public List<TcustStock> find(TcustStock tcustStock) {
		return baseDao.getHibernateDaoEx().findByExample(tcustStock);
	}

	public TcustStock get(Long id) {
		return (TcustStock) baseDao.getSession().get(TcustStock.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<TcustStock> queryByCondition(TcustStockCondition condition) {
		return baseDao.getHibernateDaoEx().findByCriteriaEx(condition);
	};

}
