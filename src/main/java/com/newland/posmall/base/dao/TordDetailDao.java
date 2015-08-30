package com.newland.posmall.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.newland.posmall.bean.basebusi.TordDetail;
import com.newland.posmall.bean.basebusi.condition.TordDetailCondition;

@Repository
public class TordDetailDao {
	
	@Resource
	private BaseDao baseDao;
	
	public List<TordDetail> find(){
		return baseDao.getHibernateDaoEx().find(TordDetail.class);
	}
	
	public TordDetail get(Long id){
		return (TordDetail) baseDao.getSession().get(TordDetail.class, id);
	}
	
	public void save(TordDetail tordDetail){
		baseDao.getSession().save(tordDetail);
	}
	
	public void update(TordDetail tordDetail){
		baseDao.getSession().update(tordDetail);
	}
	
	public List<TordDetail> findSelect(TordDetail tordDetail){
		return this.baseDao.getHibernateDaoEx().findByExample(tordDetail);
	}

	@SuppressWarnings("unchecked")
	public List<TordDetail> findByCon(TordDetailCondition tordDetailCondition){
		return this.baseDao.getHibernateDaoEx().findByCriteriaEx(tordDetailCondition);
	}
	
}
