package com.newland.posmall.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.newland.posmall.bean.basebusi.LogisticsDownLoad;
import com.newland.posmall.bean.basebusi.condition.LogisticsDownLoadCondition;

@Repository
public class LogisticsDownLoadDao {

	@Resource
	private BaseDao baseDao;

	public List<LogisticsDownLoad> findAll() {
		return baseDao.getHibernateDaoEx().find(LogisticsDownLoad.class);
	}

	public LogisticsDownLoad findObjByCondition(LogisticsDownLoad l) {
		return (LogisticsDownLoad) baseDao.getHibernateDaoEx().findByExample(l);
	}
	
	public void save(LogisticsDownLoad ld){
		this.baseDao.getSession().save(ld);
	}

	@SuppressWarnings("unchecked")
	public List<LogisticsDownLoad> findListByCondition(LogisticsDownLoadCondition condition,Page page){
		return baseDao.getHibernateDaoEx().findByCriteriaEx(condition, page);
	}
}
