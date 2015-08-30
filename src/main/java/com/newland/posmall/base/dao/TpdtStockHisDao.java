package com.newland.posmall.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.newland.posmall.bean.basebusi.TpdtStockHis;

@Repository
public class TpdtStockHisDao {
	@Resource
	private BaseDao baseDao;

	public void save(TpdtStockHis tpdStockHis) {
		baseDao.getSession().save(tpdStockHis);
	}
	
	public void update(TpdtStockHis tpdStockHis){
		baseDao.getSession().update(tpdStockHis);
	}
	
	public List<TpdtStockHis> find(){
		return baseDao.getHibernateDaoEx().find(TpdtStockHis.class);
	}
	
	public TpdtStockHis get(Long id){
		return (TpdtStockHis) baseDao.getSession().get(TpdtStockHis.class, id);
	}

	public List<TpdtStockHis> findSelect(TpdtStockHis tpdStockHis){
		return baseDao.getHibernateDaoEx().findByExample(tpdStockHis);
	}
}
