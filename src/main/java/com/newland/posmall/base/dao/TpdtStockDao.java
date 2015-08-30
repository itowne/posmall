package com.newland.posmall.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.newland.posmall.bean.basebusi.TpdtStock;

@Repository
public class TpdtStockDao {
	@Resource
	private BaseDao baseDao;

	public void save(TpdtStock tpdtStock) {
		baseDao.getSession().save(tpdtStock);
	}
	
	public void update(TpdtStock tpdtStock){
		baseDao.getSession().update(tpdtStock);
	}
	
	public List<TpdtStock> find(){
		return baseDao.getHibernateDaoEx().find(TpdtStock.class);
	}
	
	public TpdtStock get(Long id){
		return (TpdtStock) baseDao.getSession().get(TpdtStock.class,id);
	}

	public TpdtStock findByExample(TpdtStock tpdtStock) {
		return (TpdtStock) baseDao.getHibernateDaoEx().findByExample(tpdtStock);
	}
}
