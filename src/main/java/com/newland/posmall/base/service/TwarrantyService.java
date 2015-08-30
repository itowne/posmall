package com.newland.posmall.base.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.springframework.stereotype.Service;

import com.newland.posmall.base.dao.TwarrantyDao;
import com.newland.posmall.bean.customer.Twarranty;
import com.newland.posmall.bean.customer.condition.TwarrantyCondition;
import com.newland.posmall.bean.dict.WarrantyStatus;

/**
 * 产品保修service
 * @author zhouym
 * @since 2014-10-21
 */
@Service
public class TwarrantyService {
	
	@Resource
	private TwarrantyDao twarrantyDao;
	
	public List<Twarranty> find(TwarrantyCondition condition,Page page){
		return this.twarrantyDao.find(condition, page);
	}
	
	public Twarranty get(Long id){
		return this.twarrantyDao.get(id);
	}
	
	public List<Twarranty> findByExample(Twarranty condition){
		return this.twarrantyDao.findSelect(condition);
	}
	
	public void update(Twarranty twarranty){
		twarranty.setUpdTime(new Date());
		this.twarrantyDao.update(twarranty);
	}
	
	public void add(Twarranty twarranty) {
		Date now = new Date();
		twarranty.setUpdTime(now);
		twarranty.setGenTime(now);
		this.twarrantyDao.save(twarranty);
	}
	
	public void delete(Twarranty twarranty) {
		twarranty.setWarrantyStatus(WarrantyStatus.REVOKED);
		twarranty.setUpdTime(new Date());
		this.twarrantyDao.update(twarranty);
	}
	public List<Twarranty> find(TwarrantyCondition condition){
		return this.twarrantyDao.find(condition);
	}
	
}
