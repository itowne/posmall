package com.newland.posmall.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.newland.posmall.bean.basebusi.TlogisticsDetail;

@Repository
public class TlogisticsDetailDao {

	@Resource
	private BaseDao baseDao;

	public void save(TlogisticsDetail tlogisticsDetail) {
		baseDao.getSession().save(tlogisticsDetail);
	}
	
	public void update(TlogisticsDetail tlogisticsDetail) {
		baseDao.getSession().update(tlogisticsDetail);
	}
	
	public List<TlogisticsDetail> find() {
		return baseDao.getHibernateDaoEx().find(TlogisticsDetail.class);
	}
	
	public TlogisticsDetail getDetailById(Long id) {
		return (TlogisticsDetail) baseDao.getSession().get(TlogisticsDetail.class, id);
	}
	
	public List<TlogisticsDetail> getListById(TlogisticsDetail tDetail) {
		return baseDao.getHibernateDaoEx().findByExample(tDetail);
	}
}
