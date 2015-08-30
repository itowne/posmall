package com.newland.posmall.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.newland.posmall.bean.basebusi.TlogisticsTracking;

@Repository
public class TlogisticsTrackingDao {

	@Resource
	private BaseDao baseDao;

	public void save(TlogisticsTracking tlogisticsTracking) {
		baseDao.getSession().save(tlogisticsTracking);
	}
	
	public void update(TlogisticsTracking tlogisticsTracking) {
		baseDao.getSession().update(tlogisticsTracking);
	}
	
	public List<TlogisticsTracking> find() {
		return baseDao.getHibernateDaoEx().find(TlogisticsTracking.class);
	}
	
	public TlogisticsTracking get(Long id) {
		return (TlogisticsTracking) baseDao.getSession().get(TlogisticsTracking.class, id);
	}
}
