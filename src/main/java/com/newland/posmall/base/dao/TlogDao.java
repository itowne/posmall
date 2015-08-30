package com.newland.posmall.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.dao.BaseDao;
import org.ohuyo.rapid.base.entity.condition.TlogCondition;
import org.springframework.stereotype.Repository;

import com.newland.posmall.bean.common.Tlog;


@Repository
public class TlogDao {

	@Resource
	private BaseDao baseDao;

	public void save(Tlog tlog) {
		baseDao.getSession().save(tlog);
	}
	
	public void update(Tlog tlog) {
		baseDao.getSession().update(tlog);
	}
	
	public List<Tlog> find() {
		return baseDao.getHibernateDaoEx().find(Tlog.class);
	}
	
	public Tlog get(Long id) {
		return (Tlog) baseDao.getSession().get(Tlog.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Tlog> find(TlogCondition tlogCondition,Page page) {
		return baseDao.getHibernateDaoEx().findByCriteriaEx(tlogCondition, page);
	}

}
