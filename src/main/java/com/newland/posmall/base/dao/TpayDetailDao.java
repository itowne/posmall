package com.newland.posmall.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.newland.posmall.bean.basebusi.TpayDetail;

@Repository
public class TpayDetailDao {
	
	@Resource
	private BaseDao baseDao;
	
	public List<TpayDetail> find(){
		return baseDao.getHibernateDaoEx().find(TpayDetail.class);
	}
	
	public TpayDetail get(Long id){
		return (TpayDetail) baseDao.getSession().get(TpayDetail.class, id);
	}
	
	public void save(TpayDetail tpayDetail){
		baseDao.getSession().save(tpayDetail);
	}
	
	public void update(TpayDetail tpayDetail){
		baseDao.getSession().update(tpayDetail);
	}

	public List<TpayDetail> findByIpay(Long ipay) {
		TpayDetail det = new TpayDetail();
		det.setIpay(ipay);
		return baseDao.getHibernateDaoEx().findByExample(det);
	}
	
	public List<TpayDetail> findSelect(TpayDetail tpayDetail){
		return baseDao.getHibernateDaoEx().findByExample(tpayDetail);
	}
	
	

}
