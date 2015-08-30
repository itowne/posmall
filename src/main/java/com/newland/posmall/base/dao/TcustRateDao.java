package com.newland.posmall.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.stereotype.Repository;
import com.newland.posmall.bean.customer.TcustRate;

/**
 * 
 * @author zhouym
 *
 */
@Repository
public class TcustRateDao {

	@Resource
	private BaseDao baseDao;

	public void save(TcustRate tcustRate) {
		baseDao.getSession().save(tcustRate);
	}
	
	public void update(TcustRate tcustRate) {
		baseDao.getSession().update(tcustRate);
	}
	
	public List<TcustRate> find() {
		return baseDao.getHibernateDaoEx().find(TcustRate.class);
	}
	
	public List<TcustRate> findByCondition(TcustRate tcustRate){
		return baseDao.getHibernateDaoEx().findByExample(tcustRate);
	}
	
	public TcustRate get(Long id) {
		return (TcustRate) baseDao.getSession().get(TcustRate.class, id);
	}

}
