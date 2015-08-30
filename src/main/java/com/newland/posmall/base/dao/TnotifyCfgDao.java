package com.newland.posmall.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.stereotype.Service;

import com.newland.posmall.bean.basebusi.TnotifyCfg;
import com.newland.posmall.bean.basebusi.condition.TnotifyCfgCondition;
import com.newland.posmall.bean.dict.NotifyType;

@Service
public class TnotifyCfgDao {
	
	@Resource
	private BaseDao baseDao;
	
	@SuppressWarnings("unchecked")
	public List<TnotifyCfg> findTnotifyCfg(TnotifyCfgCondition tnotifyCfgConfition,Page page){ 
		return baseDao.getHibernateDaoEx().findByCriteriaEx(tnotifyCfgConfition, page);
	}
	
	public List<TnotifyCfg> findByCondition(TnotifyCfg tnotifyCfg){
		return baseDao.getHibernateDaoEx().findByExample(tnotifyCfg);
	}
	
	public List<TnotifyCfg> findMultByNotifyType(NotifyType notifyType){
		TnotifyCfg cfg = new TnotifyCfg();
		cfg.setNotifyType(notifyType);
		cfg.setDelFlag(Boolean.FALSE);
		return this.baseDao.getHibernateDaoEx().findByExample(cfg);
	}
	
	public TnotifyCfg get(Long inotifyCfg){
		return (TnotifyCfg) this.baseDao.getSession().get(TnotifyCfg.class, inotifyCfg);
	}
	
	public void save(TnotifyCfg cfg){
		this.baseDao.getSession().save(cfg);
	}
	
	public void update(TnotifyCfg cfg){
		this.baseDao.getSession().update(cfg);
	}

}
