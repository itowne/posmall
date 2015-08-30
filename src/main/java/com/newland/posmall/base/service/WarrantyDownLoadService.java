package com.newland.posmall.base.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newland.posmall.base.dao.WarrantyDownLoadDao;
import com.newland.posmall.bean.basebusi.WarrantyDownLoad;
import com.newland.posmall.bean.basebusi.condition.WarrantyDownLoadCondition;

@Service
@Transactional
public class WarrantyDownLoadService {

	@Resource
	private WarrantyDownLoadDao warrantyDownLoadDao;
	
	public List<WarrantyDownLoad> findAll() {
		return warrantyDownLoadDao.findAll();
	}

	public WarrantyDownLoad findObjByCondition(WarrantyDownLoad wd) {
		return warrantyDownLoadDao.findObjByCondition(wd);
	}
	
	public void save(WarrantyDownLoad wd){
		Date now = new Date();
		wd.setUpdTime(now);
		wd.setGenTime(now);
		this.warrantyDownLoadDao.save(wd);
	}
	public void update(WarrantyDownLoad wd){
		Date now = new Date();
		wd.setUpdTime(now);
		this.warrantyDownLoadDao.update(wd);
	}
	public List<WarrantyDownLoad> findListByCondition(WarrantyDownLoadCondition condition,Page page){
		return this.warrantyDownLoadDao.findListByCondition(condition, page);
	}
	
}
