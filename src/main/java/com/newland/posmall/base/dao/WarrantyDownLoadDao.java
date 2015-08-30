package com.newland.posmall.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.newland.posmall.bean.basebusi.WarrantyDownLoad;
import com.newland.posmall.bean.basebusi.condition.WarrantyDownLoadCondition;

@Repository
public class WarrantyDownLoadDao {

	@Resource
	private BaseDao baseDao;

	public List<WarrantyDownLoad> findAll() {
		return baseDao.getHibernateDaoEx().find(WarrantyDownLoad.class);
	}

	public WarrantyDownLoad findObjByCondition(WarrantyDownLoad wd) {
		return (WarrantyDownLoad) baseDao.getHibernateDaoEx().findByExample(wd);
	}
	
	public void save(WarrantyDownLoad wd){
		this.baseDao.getSession().save(wd);
	}
	public void update(WarrantyDownLoad wd){
		this.baseDao.getSession().update(wd);
	}
	@SuppressWarnings("unchecked")
	public List<WarrantyDownLoad> findListByCondition(WarrantyDownLoadCondition condition,Page page){
		return baseDao.getHibernateDaoEx().findByCriteriaEx(condition, page);
	}
}
