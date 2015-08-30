package com.newland.posmall.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.newland.posmall.bean.basebusi.TpdtPlanDay;

@Repository
public class TpdtPlanDayDao {
	@Resource
	private BaseDao baseDao;
	
	public void save(TpdtPlanDay tpdtPlanDay) {
		baseDao.getSession().save(tpdtPlanDay);
	}
	
	public void update(TpdtPlanDay tpdtPlanDay){
		baseDao.getSession().update(tpdtPlanDay);
	}
	
	public List<TpdtPlanDay> find(){
		return baseDao.getHibernateDaoEx().find(TpdtPlanDay.class);
	}
	
	public TpdtPlanDay get(Long id){
		return (TpdtPlanDay) baseDao.getSession().get(TpdtPlanDay.class,id);
	}
	
	public List<TpdtPlanDay> findSelect(TpdtPlanDay tpdtPlanDay){
		return baseDao.getHibernateDaoEx().findByExample(tpdtPlanDay);
	}

}
