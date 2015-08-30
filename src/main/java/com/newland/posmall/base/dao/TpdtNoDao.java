package com.newland.posmall.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.newland.posmall.bean.basebusi.TpdtNo;
import com.newland.posmall.bean.basebusi.condition.TpdtNoCondition;

@Repository
public class TpdtNoDao {
	
	@Resource
	private BaseDao baseDao;
	
	public List<TpdtNo> find(){
		return baseDao.getHibernateDaoEx().find(TpdtNo.class);
	}
	
	public TpdtNo get(Long id){
		return (TpdtNo) baseDao.getSession().get(TpdtNo.class, id);
	}
	
	public void save(TpdtNo tpdtNo){
		baseDao.getSession().save(tpdtNo);
	}
	
	public void update(TpdtNo tpdtNo){
		baseDao.getSession().update(tpdtNo);
	}
	
	public List<TpdtNo> findSelect(TpdtNo tpdtNo){
		return baseDao.getHibernateDaoEx().findByExample(tpdtNo);
	}
	@SuppressWarnings("unchecked")
	public List<TpdtNo> findCondition(TpdtNoCondition condition) {
		return baseDao.getHibernateDaoEx().findByCriteriaEx(condition);
	}
}
