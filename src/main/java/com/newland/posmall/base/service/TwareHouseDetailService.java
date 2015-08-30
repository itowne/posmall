package com.newland.posmall.base.service;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.newland.posmall.base.dao.TwareHouseDetailDao;
import com.newland.posmall.bean.basebusi.TwareHouseDetail;


@Service
public class TwareHouseDetailService {
	
	@Resource
	private TwareHouseDetailDao twareHouseDetailDao;
	
	
	public void save(TwareHouseDetail twareHouseDetail){
		if(twareHouseDetail.getGenTime() == null){
			Date date = new Date();
			twareHouseDetail.setGenTime(date);
		}
		this.twareHouseDetailDao.save(twareHouseDetail);
	}
	
	public void update(TwareHouseDetail twareHouseDetail){
		this.twareHouseDetailDao.update(twareHouseDetail);		
	}
	
	public TwareHouseDetail find(Long id){
		return this.twareHouseDetailDao.get(id);
		
	}
	
	public List<TwareHouseDetail> queryListByiwareHouse(Long iwareHouse){
		TwareHouseDetail t = new TwareHouseDetail();
		t.setIwareHouse(iwareHouse);
		List<TwareHouseDetail> list = this.twareHouseDetailDao.find(t);
		return list;
	}
}
