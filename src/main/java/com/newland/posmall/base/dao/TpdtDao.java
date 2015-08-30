package com.newland.posmall.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.newland.posmall.bean.basebusi.Tpdt;
import com.newland.posmall.bean.basebusi.condition.TpdtCondition;

@Repository
public class TpdtDao {
	
	@Resource
	private BaseDao baseDao;
	
	public List<Tpdt> find(){
		return baseDao.getHibernateDaoEx().find(Tpdt.class);
	}
	
	public Tpdt get(Long id){
		return (Tpdt) baseDao.getSession().get(Tpdt.class, id);
	}
	
	public Tpdt queryObjByIpdt(Tpdt pdt){
		return baseDao.getHibernateDaoEx().getFirstOneByExample(pdt);
	}
	
	public void save(Tpdt tpdt){
		baseDao.getSession().save(tpdt);
	}
	
	public void update(Tpdt tpdt){
		baseDao.getSession().update(tpdt);
	}
	
	public List<Tpdt> findSelect(Tpdt tpdt){
		return baseDao.getHibernateDaoEx().findByExample(tpdt);
	}
	
	@SuppressWarnings("unchecked")
	public List<Tpdt> findCondition(TpdtCondition tpdtCondition,Page page) {
		return baseDao.getHibernateDaoEx().findByCriteriaEx(tpdtCondition, page);
	}
	

}
