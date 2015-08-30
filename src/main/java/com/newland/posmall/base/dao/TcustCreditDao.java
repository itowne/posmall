package com.newland.posmall.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.newland.posmall.bean.customer.TcustCredit;

/**
 * 
 * @author zhouym
 *
 */
@Repository
public class TcustCreditDao {

	@Resource
	private BaseDao baseDao;

	public void save(TcustCredit tcustCredit) {
		baseDao.getSession().save(tcustCredit);
	}
	
	public void update(TcustCredit tcustCredit) {
		baseDao.getSession().update(tcustCredit);
	}
	
	public List<TcustCredit> find() {
		return baseDao.getHibernateDaoEx().find(TcustCredit.class);
	}
	
	public List<TcustCredit> findByCondition(TcustCredit tcustCredit) {
		return baseDao.getHibernateDaoEx().findByExample(tcustCredit);
	}
	
	public TcustCredit get(Long id) {
		return (TcustCredit) baseDao.getSession().get(TcustCredit.class, id);
	}

}
