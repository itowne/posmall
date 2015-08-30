package com.newland.posmall.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.newland.posmall.bean.customer.TcustCode;
import com.newland.posmall.bean.customer.condition.TcustCodeCondition;

/**
 * 
 * @author zhouym
 *
 */
@Repository
public class TcustCodeDao{

	@Resource
	private BaseDao baseDao;
	
	public TcustCode get(Long id) {
		return (TcustCode) baseDao.getSession().get(TcustCode.class, id);
	}

	public void save(TcustCode tcustCode) {
		baseDao.getSession().save(tcustCode);
	}
	
	public void update(TcustCode tcustCode) {
		baseDao.getSession().update(tcustCode);
	}
	
	public List<TcustCode> findByExample(TcustCode tcustCode) {
		return baseDao.getHibernateDaoEx().findByExample(tcustCode);
	}

	@SuppressWarnings("unchecked")
	public List<TcustCode> findPageByCondition(TcustCodeCondition condition, Page page) {
		return this.baseDao.getHibernateDaoEx().findByCriteriaEx(condition, page);
	}
}
