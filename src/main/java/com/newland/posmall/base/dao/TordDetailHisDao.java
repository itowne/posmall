package com.newland.posmall.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.newland.posmall.bean.basebusi.TordDetailHis;

@Repository
public class TordDetailHisDao {
	
	@Resource
	private BaseDao baseDao;
	
	public List<TordDetailHis> find(){
		return baseDao.getHibernateDaoEx().find(TordDetailHis.class);
	}
	
	public TordDetailHis get(Long id){
		return (TordDetailHis) baseDao.getSession().get(TordDetailHis.class, id);
	}
	
	public void save(TordDetailHis tordDetailHis){
		baseDao.getSession().save(tordDetailHis);
	}
	
	public void update(TordDetailHis tordDetailHis){
		baseDao.getSession().update(tordDetailHis);
	}

}
