package com.newland.posmall.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.newland.posmall.bean.basebusi.TlogisticsOrdAddr;
import com.newland.posmall.bean.basebusi.condition.TlogisticsOrdAddrCondition;

@Repository
public class TlogisticsOrdAddrDao {

	@Resource
	private BaseDao baseDao;

	public void save(TlogisticsOrdAddr tlogisticsOrdAddr) {
		baseDao.getSession().save(tlogisticsOrdAddr);
	}
	
	public void update(TlogisticsOrdAddr tlogisticsOrdAddr) {
		baseDao.getSession().update(tlogisticsOrdAddr);
	}
	
	public TlogisticsOrdAddr getTlogisticsOrdAddrById(Long id) {
		return (TlogisticsOrdAddr) baseDao.getSession().get(TlogisticsOrdAddr.class, id);
	}
	
	public List<TlogisticsOrdAddr> findListSelect(TlogisticsOrdAddr tlogisticsOrdAddr) {
		return baseDao.getHibernateDaoEx().findByExample(tlogisticsOrdAddr);
	}
	@SuppressWarnings("unchecked")
	public List<TlogisticsOrdAddr> findCondition(TlogisticsOrdAddrCondition condition) {
		return baseDao.getHibernateDaoEx().findByCriteriaEx(condition);
	}
}
