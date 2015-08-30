package com.newland.posmall.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.newland.posmall.bean.basebusi.TordHis;
import com.newland.posmall.bean.basebusi.condition.TordHisCondition;

@Repository
public class TordHisDao {
	
	@Resource
	private BaseDao baseDao;
	
	@SuppressWarnings("unchecked")
	public List<TordHis> findByCon(TordHisCondition tordHisCondition){
		return baseDao.getHibernateDaoEx().findByCriteriaEx(tordHisCondition);
	}
	
	public TordHis get(Long id){
		return (TordHis) baseDao.getSession().get(TordHis.class, id);
	}
	
	public void save(TordHis tordHis){
		baseDao.getSession().save(tordHis);
	}
	
	public void update(TordHis tordHis){
		baseDao.getSession().update(tordHis);
	}
	
}
