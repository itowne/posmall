package com.newland.posmall.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.newland.posmall.bean.customer.TcustStockDetail;

/**
 * 
 * @author zhouym
 *
 */
@Repository
public class TcustStockDetailDao {

	@Resource
	private BaseDao baseDao;

	public void save(TcustStockDetail tcustStockDetail) {
		baseDao.getSession().save(tcustStockDetail);
	}
	
	public void update(TcustStockDetail tcustStockDetail) {
		baseDao.getSession().update(tcustStockDetail);
	}
	
	public List<TcustStockDetail> find() {
		return baseDao.getHibernateDaoEx().find(TcustStockDetail.class);
	}
	
	public TcustStockDetail get(Long id) {
		return (TcustStockDetail) baseDao.getSession().get(TcustStockDetail.class, id);
	}

}
