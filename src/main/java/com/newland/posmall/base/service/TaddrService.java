package com.newland.posmall.base.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.springframework.stereotype.Service;

import com.newland.posmall.base.dao.TaddrDao;
import com.newland.posmall.bean.customer.Taddr;
import com.newland.posmall.bean.customer.TaddrHis;
import com.newland.posmall.bean.customer.condition.TaddrCondition;

/**
 * 客户签订协议
 * @author zhouym
 *
 */
@Service
public class TaddrService {
	
	@Resource
	private TaddrDao taddrDao;
	
	public Taddr findById(Long id) {
		return this.taddrDao.get(id);
	}
	
	public List<Taddr> findPageByCondition(Page page, TaddrCondition condition) {
		return this.taddrDao.findPageByCondition(page, condition);
	}

	public List<Taddr> findPageByCondition(TaddrCondition condition) {
		return this.taddrDao.findPageByCondition(condition);
	}
	
	public void save(Taddr taddr) {
		Date date = new Date();
		taddr.setGenTime(date);
		taddr.setUpdTime(date);
		taddr.setDelFlag(Boolean.FALSE);
		this.taddrDao.save(taddr);
	}
	
	public void saveTaddrHis(TaddrHis taddrHis) {
		taddrHis.setGenTime(new Date());
		this.taddrDao.saveTaddrHis(taddrHis);
	}
	
	public void update(Taddr taddr) {
		taddr.setUpdTime(new Date());
		this.taddrDao.update(taddr);
	}
	
	public void delete(Taddr taddr) {
		taddr.setDelFlag(Boolean.TRUE);
		taddr.setUpdTime(new Date());
		this.taddrDao.update(taddr);
	}
}
