package com.newland.posmall.base.service;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newland.posmall.base.dao.OrdDownLoadDao;
import com.newland.posmall.bean.basebusi.OrderDownLoad;
import com.newland.posmall.bean.basebusi.condition.OrderDownLoadCondition;

@Service
@Transactional
public class OrderDownLoadService {

	@Resource
	private OrdDownLoadDao ordDownLoadDao;
	
	public List<OrderDownLoad> findAll() {
		return ordDownLoadDao.findAll();
	}

	public OrderDownLoad findObjByCondition(OrderDownLoad od) {
		return ordDownLoadDao.findObjByCondition(od);
	}

	public void save(OrderDownLoad od) {
		this.ordDownLoadDao.save(od);
	}
	
	@SuppressWarnings("unchecked")
	public List<OrderDownLoad> findListByCondition(OrderDownLoadCondition cond){
		return ordDownLoadDao.findListByCondition(cond);
	}
	
	public List<OrderDownLoad> findPageByCondition(OrderDownLoadCondition cond,Page page){
		return ordDownLoadDao.findPageByCondition(cond, page);
	}
}
