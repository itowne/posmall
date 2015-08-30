package com.newland.posmall.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.stereotype.Repository;
import com.newland.posmall.bean.basebusi.TwareHouseDetail;


@Repository
public class TwareHouseDetailDao {

	@Resource
	private BaseDao baseDao;
	
	public List<TwareHouseDetail> find(TwareHouseDetail twareHouseDetail){
		return baseDao.getHibernateDaoEx().findByExample(twareHouseDetail);
	}

	public void save(TwareHouseDetail twareHouseDetail) {
		baseDao.getSession().save(twareHouseDetail);
	}
	
	public void update(TwareHouseDetail twareHouseDetail) {
		baseDao.getSession().update(twareHouseDetail);
	}
	
	public List<TwareHouseDetail> find() {
		return baseDao.getHibernateDaoEx().find(TwareHouseDetail.class);
	}
	
	public TwareHouseDetail get(Long id) {
		return (TwareHouseDetail) baseDao.getSession().get(TwareHouseDetail.class, id);
	}

}
