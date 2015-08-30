package com.newland.posmall.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.newland.posmall.bean.basebusi.Tord;
import com.newland.posmall.bean.basebusi.condition.TordCondition;

@Repository
public class TordDao {
	
	@Resource
	private BaseDao baseDao;
	
	public List<Tord> find(){
		return baseDao.getHibernateDaoEx().find(Tord.class);
	}
	
	public Tord get(Long id){
		return (Tord) baseDao.getSession().get(Tord.class, id);
	}
	
	public void save(Tord tord){
		baseDao.getSession().save(tord);
	}
	
	public void update(Tord tord){
		baseDao.getSession().update(tord);
	}
	
	public List<Tord> findSelect(Tord tord){
		return baseDao.getHibernateDaoEx().findByExample(tord);
	}
	
	@SuppressWarnings("unchecked")
	public List<Tord> findTordByCon(TordCondition tordCondition,Page page){
		return baseDao.getHibernateDaoEx().findByCriteriaEx(tordCondition, page);
	}
	
	@SuppressWarnings("unchecked")
	public List<Tord> findListByCon(TordCondition tordCondition){
		return baseDao.getHibernateDaoEx().findByCriteriaEx(tordCondition);
	}

}
