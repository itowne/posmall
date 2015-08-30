package com.newland.posmall.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.stereotype.Repository;
import com.newland.posmall.bean.basebusi.TpdtPlanMonth;
import com.newland.posmall.bean.basebusi.condition.TpdtPlanMonthCondition;

@Repository
public class TpdtPlanMonthDao {
	@Resource
	private BaseDao baseDao;

	public void save(TpdtPlanMonth tpdtPlanMonth) {
		baseDao.getSession().save(tpdtPlanMonth);
	}
	
	public void update(TpdtPlanMonth tpdtPlanMonth){
		baseDao.getSession().update(tpdtPlanMonth);
	}
	
	public List<TpdtPlanMonth> find(){
		return baseDao.getHibernateDaoEx().find(TpdtPlanMonth.class);
	}
	
	public TpdtPlanMonth get(Long id){
		return (TpdtPlanMonth) baseDao.getSession().get(TpdtPlanMonth.class,id);
	}
	
	public List<TpdtPlanMonth> findSelect(TpdtPlanMonth tpdtPlanMonth){
		return baseDao.getHibernateDaoEx().findByExample(tpdtPlanMonth);
	}
	
	@SuppressWarnings("unchecked")
	public List<TpdtPlanMonth> findCondition(TpdtPlanMonthCondition tpdtPlanMonthCondition,Page page) {
		return baseDao.getHibernateDaoEx().findByCriteriaEx(tpdtPlanMonthCondition, page);
	}

}
