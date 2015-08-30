package com.newland.posmall.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.newland.posmall.bean.basebusi.Tpay;

@Repository
public class TpayDao {
	
	@Resource
	private BaseDao baseDao;
	
	public List<Tpay> find(){
		return baseDao.getHibernateDaoEx().find(Tpay.class);
	}
	
	public Tpay get(Long id){
		return (Tpay) baseDao.getSession().get(Tpay.class, id);
	}
	
	public void save(Tpay tpay){
		baseDao.getSession().save(tpay);
	}
	
	public void update(Tpay tpay){
		baseDao.getSession().update(tpay);
	}

}
