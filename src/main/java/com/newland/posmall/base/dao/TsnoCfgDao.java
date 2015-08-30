package com.newland.posmall.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.newland.posmall.bean.basebusi.TsnoCfg;

@Repository
public class TsnoCfgDao {
	
	@Resource
	private BaseDao baseDao;

	public void save(TsnoCfg tsnoCfg) {
		baseDao.getSession().save(tsnoCfg);
	}
	
	public void update(TsnoCfg tsnoCfg){
		baseDao.getSession().update(tsnoCfg);
	}
	
	public List<TsnoCfg> find(){
		return baseDao.getHibernateDaoEx().find(TsnoCfg.class);
	}
	
	public TsnoCfg get(Long id){
		return (TsnoCfg) baseDao.getSession().get(TsnoCfg.class,id);
	}

}
