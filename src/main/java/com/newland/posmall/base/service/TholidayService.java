package com.newland.posmall.base.service;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.entity.condition.TholidayCondition;
import org.springframework.stereotype.Service;
import com.newland.posmall.base.dao.TholidayDao;
import com.newland.posmall.bean.common.Tholiday;


@Service
public class TholidayService {
	
	@Resource
	private TholidayDao tholidayDao;
	
	
	public void save(Tholiday tholiday){
		Date date = new Date();
		tholiday.setGenTime(date);
		tholiday.setUpdTime(date);
		tholiday.setDelFlg(Boolean.FALSE);
		this.tholidayDao.save(tholiday);
				
	}
	
	public void update(Tholiday tholiday){
		Date date = new Date();
		tholiday.setUpdTime(date);
		this.tholidayDao.update(tholiday);		
	}
	
	public void delete(Long id)throws Exception{
		Tholiday tholiday = this.tholidayDao.get(id);
		tholiday.setUpdTime(new Date());
		tholiday.setDelFlg(Boolean.TRUE);
		this.tholidayDao.update(tholiday);
	}
	
	public Tholiday find(Long id)throws Exception{
		return this.tholidayDao.get(id);
		
	}
	
	public List<Tholiday> queryTholiday(TholidayCondition tholidayCondition,Page page){
		return this.tholidayDao.find(tholidayCondition, page);
	}
	
	@SuppressWarnings("rawtypes")
	public List findListByInfo(Page page, String startTime, String endTime, String holiStatus) {

		return this.tholidayDao.findListByInfo(page, startTime, endTime, holiStatus);
	}
	public Tholiday findObjectByInfo(Tholiday tholiday){
		return this.tholidayDao.findObjectByInfo(tholiday);
	}
	
}
