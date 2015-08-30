package com.newland.posmall.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.newland.posmall.bean.basebusi.TpdtPlanDayHis;

@Repository
public class TpdtPlanDayHisDao {
	@Resource
	private BaseDao baseDao;

	public void save(TpdtPlanDayHis tpdtPlanDayHis) {
		baseDao.getSession().save(tpdtPlanDayHis);
	}
	
	public void update(TpdtPlanDayHis tpdtPlanDayHis){
		baseDao.getSession().update(tpdtPlanDayHis);
	}
	
	public List<TpdtPlanDayHis> find(){
		return baseDao.getHibernateDaoEx().find(TpdtPlanDayHis.class);
	}
	
	public TpdtPlanDayHis get(Long id){
		return (TpdtPlanDayHis) baseDao.getSession().get(TpdtPlanDayHis.class,id);
	}
	
	public List<TpdtPlanDayHis> findSelect(TpdtPlanDayHis tpdtPlanDayHis){
		return baseDao.getHibernateDaoEx().findByExample(tpdtPlanDayHis);
	}

}
