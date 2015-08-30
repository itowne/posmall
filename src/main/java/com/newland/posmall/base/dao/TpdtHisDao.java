package com.newland.posmall.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.newland.posmall.bean.basebusi.TpdtHis;

@Repository
public class TpdtHisDao {
	@Resource
	private BaseDao baseDao;

	public void save(TpdtHis tpdtHis) {
		baseDao.getSession().save(tpdtHis);
	}
	
	public void update(TpdtHis tpdtHis){
		baseDao.getSession().update(tpdtHis);
	}
	
	public List<TpdtHis> findSelect(TpdtHis tpdtHis){
		return baseDao.getHibernateDaoEx().findByExample(tpdtHis);
	}
	
	@SuppressWarnings("unchecked")
	public List<TpdtHis> findByIpdt(Long id){
		String hql = "from TpdtHis a  where  a.ipdt = ?  order by a.ver desc ";
		return baseDao.getHibernateDaoEx().find(hql, id);
	}
	
	public TpdtHis get(Long id){
		return (TpdtHis) baseDao.getSession().get(TpdtHis.class,id);
	}

}
