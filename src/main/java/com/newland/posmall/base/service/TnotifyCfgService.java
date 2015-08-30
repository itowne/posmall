package com.newland.posmall.base.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.springframework.stereotype.Service;

import com.newland.posmall.base.dao.TnotifyCfgDao;
import com.newland.posmall.bean.basebusi.TnotifyCfg;
import com.newland.posmall.bean.basebusi.condition.TnotifyCfgCondition;
import com.newland.posmall.bean.dict.NotifyType;

/**
 * 通知类型服务
 * @author rabbit
 *
 */
@Service
public class TnotifyCfgService {
	@Resource
	private TnotifyCfgDao tnodifyCfgDao;
	
	public List<TnotifyCfg> findByCondition(TnotifyCfg tnotifyCfg){
		return this.tnodifyCfgDao.findByCondition(tnotifyCfg);
	}
	
	public List<TnotifyCfg> findMultByNotifyType(NotifyType type){
		return this.tnodifyCfgDao.findMultByNotifyType(type);
	}
	
	public void add(TnotifyCfg tnotifyCfg){
		tnotifyCfg.setGenTime(new Date());
		tnotifyCfg.setUpdTime(new Date());
		tnotifyCfg.setDelFlag(Boolean.FALSE);
		
		this.tnodifyCfgDao.save(tnotifyCfg);
	}
	
	public TnotifyCfg get(Long inotifyCfg){
		return tnodifyCfgDao.get(inotifyCfg);
	}
	
	public void update(TnotifyCfg tnotifyCfg){
		
		tnotifyCfg.setUpdTime(new Date());
		this.tnodifyCfgDao.update(tnotifyCfg);
	}
	
	public void delete(TnotifyCfg tnotifyCfg){
		
		tnotifyCfg.setUpdTime(new Date());
		tnotifyCfg.setDelFlag(Boolean.TRUE);
		
		this.tnodifyCfgDao.update(tnotifyCfg);
	}
	
	public  List<TnotifyCfg> queryTnotifyCfg(TnotifyCfgCondition tnotifyCfgConfition,Page page){
		return this.tnodifyCfgDao.findTnotifyCfg(tnotifyCfgConfition,page);
	}

}
