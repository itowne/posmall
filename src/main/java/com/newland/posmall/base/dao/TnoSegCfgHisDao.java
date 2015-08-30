package com.newland.posmall.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.newland.posmall.bean.basebusi.TnoSegCfgHis;

@Repository
public class TnoSegCfgHisDao {

	@Resource
	private BaseDao baseDao;

	public void save(TnoSegCfgHis tnoSegCfgHis) {
		baseDao.getSession().save(tnoSegCfgHis);
	}

	public void update(TnoSegCfgHis tnoSegCfgHis) {
		baseDao.getSession().update(tnoSegCfgHis);
	}

	public TnoSegCfgHis get(Long id) {
		return (TnoSegCfgHis) baseDao.getSession().get(TnoSegCfgHis.class, id);
	}

	public List<TnoSegCfgHis> findTnoSegCfgHis(TnoSegCfgHis tnoSegCfgHis) {
		return this.baseDao.getHibernateDaoEx().findByExample(tnoSegCfgHis);
	}

}
