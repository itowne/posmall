package com.newland.posmall.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.dao.BaseDao;
import org.ohuyo.rapid.base.entity.condition.TdetailTraceCondition;
import org.springframework.stereotype.Repository;

import com.newland.posmall.bean.common.TdetailTrace;


@Repository
public class TdetailTraceDao {

	@Resource
	private BaseDao baseDao;

	public void save(TdetailTrace tdetailTrace) {
		baseDao.getSession().save(tdetailTrace);
	}
	
	public List<TdetailTrace> findAll() {
		return baseDao.getHibernateDaoEx().find(TdetailTrace.class);
	}
	
	public TdetailTrace get(Long id) {
		return (TdetailTrace) baseDao.getSession().get(TdetailTrace.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<TdetailTrace> findByCondition(TdetailTraceCondition condition, Page page) {
		return baseDao.getHibernateDaoEx().findByCriteriaEx(condition, page);
	}

}
