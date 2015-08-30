package com.newland.posmall.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.stereotype.Repository;
import com.newland.posmall.bean.basebusi.TwareHouse;


@Repository
public class TwareHouseDao {

	@Resource
	private BaseDao baseDao;
	
	public List<TwareHouse> find(TwareHouse twareHouse){
		return baseDao.getHibernateDaoEx().findByExample(twareHouse);
	}

	public void save(TwareHouse twareHouse) {
		baseDao.getSession().save(twareHouse);
	}
	
	public void update(TwareHouse twareHouse) {
		baseDao.getSession().update(twareHouse);
	}
	
	public List<TwareHouse> find() {
		return baseDao.getHibernateDaoEx().find(TwareHouse.class);
	}
	
	public TwareHouse get(Long id) {
		return (TwareHouse) baseDao.getSession().get(TwareHouse.class, id);
	}

}
