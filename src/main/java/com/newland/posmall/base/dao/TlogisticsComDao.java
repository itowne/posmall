package com.newland.posmall.base.dao;

import java.util.List;
import javax.annotation.Resource;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.newland.posmall.bean.basebusi.TlogisticsCom;
import com.newland.posmall.bean.basebusi.condition.LogisticscompanyCondition;

@Repository
public class TlogisticsComDao {

	@Resource
	private BaseDao baseDao;

	public void save(TlogisticsCom tlogisticsCom) {
		baseDao.getSession().save(tlogisticsCom);
	}
	
	public void update(TlogisticsCom tlogisticsCom) {
		baseDao.getSession().update(tlogisticsCom);
	}
	
	public List<TlogisticsCom> find(TlogisticsCom tlogisticsCom) {
		return baseDao.getHibernateDaoEx().findByExample(tlogisticsCom);
	}
	
	public TlogisticsCom get(Long id) {
		return (TlogisticsCom) baseDao.getSession().get(TlogisticsCom.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<TlogisticsCom> findCondition(LogisticscompanyCondition logisticscompanyCondition,Page page) {
		return baseDao.getHibernateDaoEx().findByCriteriaEx(logisticscompanyCondition, page);
	}
}
