package com.newland.posmall.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.newland.posmall.bean.customer.Taddr;
import com.newland.posmall.bean.customer.TaddrHis;
import com.newland.posmall.bean.customer.condition.TaddrCondition;

/**
 * 
 * @author zhouym
 *
 */
@Repository
public class TaddrDao {

	@Resource
	private BaseDao baseDao;

	public void save(Taddr taddr) {
		baseDao.getSession().save(taddr);
	}
	
	public void saveTaddrHis(TaddrHis taddrHis) {
		baseDao.getSession().save(taddrHis);
	}
	
	public void update(Taddr taddr) {
		baseDao.getSession().update(taddr);
	}
	
	public List<Taddr> find() {
		return baseDao.getHibernateDaoEx().find(Taddr.class);
	}
	
	public Taddr get(Long id) {
		return (Taddr) baseDao.getSession().get(Taddr.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Taddr> findPageByCondition(Page page, TaddrCondition condition) {
		return baseDao.getHibernateDaoEx().findByCriteriaEx(condition, page);
	}

	@SuppressWarnings("unchecked")
	public List<Taddr> findPageByCondition(TaddrCondition condition) {
		return baseDao.getHibernateDaoEx().findByCriteriaEx(condition);
	}
}
