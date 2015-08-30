package com.newland.posmall.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.newland.posmall.bean.basebusi.condition.TmsgTmpCondition;
import com.newland.posmall.bean.common.TmsgTmp;


@Repository
public class TmsgTmpDao {

	@Resource
	private BaseDao baseDao;
	
	
	@SuppressWarnings("unchecked")
	public List<TmsgTmp> queryTmsgTmp(TmsgTmpCondition tmsgTmpConfition,Page page) {
	   return  baseDao.getHibernateDaoEx().findByCriteriaEx(tmsgTmpConfition, page);
	}
	
	public List<TmsgTmp> findSelect(TmsgTmp tmsgTmp){
		return baseDao.getHibernateDaoEx().findByExample(tmsgTmp);
	}

	public void save(TmsgTmp tmsgTmp) {
		baseDao.getSession().save(tmsgTmp);
	}
	
	public void update(TmsgTmp tmsgTmp) {
		baseDao.getSession().update(tmsgTmp);
	}
	
	public List<TmsgTmp> find() {
		return baseDao.getHibernateDaoEx().find(TmsgTmp.class);
	}
	
	public TmsgTmp get(Long id) {
		return (TmsgTmp) baseDao.getSession().get(TmsgTmp.class, id);
	}

}
