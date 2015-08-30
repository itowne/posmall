package com.newland.posmall.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.newland.posmall.bean.basebusi.condition.TcustRegCondition;
import com.newland.posmall.bean.customer.TcustReg;

/**
 * 
 * @author zhouym
 *
 */
@Repository
public class TcustRegDao {

	@Resource
	private BaseDao baseDao;

	public void save(TcustReg tcustReg) {
		baseDao.getSession().save(tcustReg);
	}
	
	public void update(TcustReg tcustReg) {
		baseDao.getSession().update(tcustReg);
	}
	
	public List<TcustReg> find() {
		return baseDao.getHibernateDaoEx().find(TcustReg.class);
	}
	
	public List<TcustReg> findByCondition(TcustReg tcustReg){
		return baseDao.getHibernateDaoEx().findByExample(tcustReg);
	}
	
	public TcustReg get(Long id) {
		return (TcustReg) baseDao.getSession().get(TcustReg.class, id);
	}
	
    @SuppressWarnings("unchecked")
	public List<TcustReg> findByNameCond(TcustRegCondition tc){
    	return baseDao.getHibernateDaoEx().findByCriteriaEx(tc);
    }

}
