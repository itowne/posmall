package com.newland.posmall.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.newland.posmall.bean.basebusi.TpdtPlanDayQuota;

@Repository
public class TpdtPlanDayQuotaDao {
	
	@Resource
	private BaseDao baseDao;
	
	public List<TpdtPlanDayQuota> find(){
		return baseDao.getHibernateDaoEx().find(TpdtPlanDayQuota.class);
	}
	
	public TpdtPlanDayQuota get(Long id){
		return (TpdtPlanDayQuota) baseDao.getSession().get(TpdtPlanDayQuota.class, id);
	}
	
	public void save(TpdtPlanDayQuota tpdtPlanDayQuota){
		baseDao.getSession().save(tpdtPlanDayQuota);
	}
	
	public void update(TpdtPlanDayQuota tpdtPlanDayQuota){
		baseDao.getSession().update(tpdtPlanDayQuota);
	}
	
	public void delete(TpdtPlanDayQuota tpdtPlanDayQuota){
		baseDao.getSession().delete(tpdtPlanDayQuota);
	}
	public List<TpdtPlanDayQuota> findSelect(TpdtPlanDayQuota tpdtPlanDayQuota){
		return this.baseDao.getHibernateDaoEx().findByExample(tpdtPlanDayQuota);
	}
	

}
