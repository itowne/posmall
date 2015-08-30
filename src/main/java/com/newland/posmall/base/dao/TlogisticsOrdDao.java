package com.newland.posmall.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.newland.posmall.bean.basebusi.TlogisticsOrd;
import com.newland.posmall.bean.customer.condition.TlogisticsOrdCondition;

@Repository
public class TlogisticsOrdDao {

	@Resource
	private BaseDao baseDao;

	public void save(TlogisticsOrd tlogisticsOrd) {
		baseDao.getSession().save(tlogisticsOrd);
	}
	
	public void update(TlogisticsOrd tlogisticsOrd) {
		baseDao.getSession().update(tlogisticsOrd);
	}
	
	public List<TlogisticsOrd> find() {
		return baseDao.getHibernateDaoEx().find(TlogisticsOrd.class);
	}
	
	public TlogisticsOrd get(Long id) {
		return (TlogisticsOrd) baseDao.getSession().get(TlogisticsOrd.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<TlogisticsOrd> findByCondition(Page page,TlogisticsOrdCondition condition){
		return baseDao.getHibernateDaoEx().findByCriteriaEx(condition,page);
	}
	
	@SuppressWarnings("unchecked")
	public List<TlogisticsOrd> findListByCondition(TlogisticsOrdCondition condition){
		return baseDao.getHibernateDaoEx().findByCriteriaEx(condition);
	}
	
	public List<TlogisticsOrd> findListByObj(TlogisticsOrd tlo){
		return baseDao.getHibernateDaoEx().findByExample(tlo);
	}
	/**
	 * 
	* @Description: 根据内部物流单号查找信息
	* @author chenwenjing    
	* @date 2014-10-21 下午4:21:53
	 */
	public TlogisticsOrd getByInnerOrdno(String innerOrdno) {
		TlogisticsOrd t = new TlogisticsOrd();
		t.setInnerOrdno(innerOrdno);
		return baseDao.getHibernateDaoEx().getFirstOneByExample(t);
	}
}
