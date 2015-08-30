package com.newland.posmall.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.newland.posmall.bean.common.Province;

@Repository
public class ProvinceDao {

	@Resource
	private BaseDao baseDao;

	public void save(Province province) {
		baseDao.getSession().save(province);

	}

	public void update(Province provincd) {
		baseDao.getSession().update(provincd);

	}

	@SuppressWarnings("unchecked")
	public List<Province> queryAll() {
		return baseDao.getHibernateDaoEx().find("from " + Province.class.getName());
	}

	public List<Province> findByPreProvCode(String preCodeCode) {
		Province p = new Province();
		p.setPreProvCode(preCodeCode);
		return baseDao.getHibernateDaoEx().findByExample(p);
	}

	public Province queryByCityCode(String provCode) {
		return (Province) baseDao.getSession().get(Province.class, provCode);
	}
}
