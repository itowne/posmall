package org.ohuyo.rapid.base.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.rapid.base.entity.Tauth;
import org.ohuyo.rapid.base.entity.Tuserrole;
import org.springframework.stereotype.Repository;

@Repository
public class TauthDao {

	@Resource
	private BaseDao baseDao;

	public void save(Tauth tauth) {
		baseDao.getSession().save(tauth);
	}

	public void update(Tauth tauth) {
		baseDao.getSession().update(tauth);
	}

	public List<Tauth> find() {
		return baseDao.getHibernateDaoEx().find(Tauth.class);
	}

	public List<Tauth> findByTroles(List<Tuserrole> troles) {
		List<Long> iroles = new ArrayList<Long>(troles.size());
		for (Tuserrole trole : troles) {
			iroles.add(trole.getIuserrole());
		}
		return findByIroles(iroles);
	}

	@SuppressWarnings("unchecked")
	public List<Tauth> findByIroles(List<Long> iroles) {
		List<?> iauths = baseDao.getHibernateDaoEx().find(
				"select iauth from TroleAuth where irole in ?", iroles);
		return baseDao.getHibernateDaoEx().find("from Tauth where iauth in ?",
				iauths);
	}

	public Tauth get(Long id) {
		return (Tauth) baseDao.getSession().get(Tauth.class, id);
	}
}
