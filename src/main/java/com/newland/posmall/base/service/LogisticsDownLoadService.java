package com.newland.posmall.base.service;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newland.posmall.base.dao.LogisticsDownLoadDao;
import com.newland.posmall.bean.basebusi.LogisticsDownLoad;
import com.newland.posmall.bean.basebusi.condition.LogisticsDownLoadCondition;

@Service
@Transactional
public class LogisticsDownLoadService {

	@Resource
	private LogisticsDownLoadDao logisticsDownLoadDao;
	
	public List<LogisticsDownLoad> findAll() {
		return logisticsDownLoadDao.findAll();
	}

	public LogisticsDownLoad findObjByCondition(LogisticsDownLoad l) {
		return logisticsDownLoadDao.findObjByCondition(l);
	}
	
	public void save(LogisticsDownLoad ld){
		this.logisticsDownLoadDao.save(ld);
	}
	public List<LogisticsDownLoad> findListByCondition(LogisticsDownLoadCondition condition,Page page){
		return logisticsDownLoadDao.findListByCondition(condition, page);
	}
	
}
