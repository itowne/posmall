package com.newland.posmall.base.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.newland.posmall.base.dao.TcustRateDao;
import com.newland.posmall.bean.customer.TcustRate;

/**
 * 客户折扣率
 * @author zhouym
 *
 */
@Service
public class TcustRateService {

	@Resource
	private TcustRateDao tcustRateDao;
	
	public TcustRate find(Long id){
		return this.tcustRateDao.get(id);
	}
	
	public List<TcustRate> findByCondition(TcustRate tcustRate){
		return this.tcustRateDao.findByCondition(tcustRate);
	}

	public void add(TcustRate tcustRate) {
		tcustRate.setGenTime(new Date());
		tcustRate.setUpdTime(new Date());
		this.tcustRateDao.save(tcustRate);
	}
	
	public void update(TcustRate tcustRate){
		
		tcustRate.setUpdTime(new Date());
		this.tcustRateDao.update(tcustRate);
	}
}
