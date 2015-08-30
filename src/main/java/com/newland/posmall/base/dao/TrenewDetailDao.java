package com.newland.posmall.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.newland.posmall.bean.customer.TrenewDetail;

@Repository
public class TrenewDetailDao {

	@Resource
	private BaseDao baseDao;

	public List<TrenewDetail> findAll() {
		return baseDao.getHibernateDaoEx().find(TrenewDetail.class);
	}

	public TrenewDetail get(Long id) {
		return (TrenewDetail) baseDao.getSession().get(TrenewDetail.class, id);
	}

	public void save(TrenewDetail trenewDetail) {
		baseDao.getSession().save(trenewDetail);
	}

	public void update(TrenewDetail trenewDetail) {
		baseDao.getSession().update(trenewDetail);
	}

	public List<TrenewDetail> findByExample(TrenewDetail trenewDetail) {
		return baseDao.getHibernateDaoEx().findByExample(trenewDetail);
	}

}
