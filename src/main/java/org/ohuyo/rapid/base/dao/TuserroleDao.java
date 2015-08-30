package org.ohuyo.rapid.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.dao.BaseDao;
import org.ohuyo.rapid.base.entity.Tuserrole;
import org.ohuyo.rapid.base.entity.condition.TuserroleCondition;
import org.springframework.stereotype.Repository;

@Repository
public class TuserroleDao {

	@Resource
	private BaseDao baseDao;
	
	@SuppressWarnings("unchecked")
	public List<Tuserrole> find(TuserroleCondition tuserroleCondition,Page page) {
		return baseDao.getHibernateDaoEx().findByCriteriaEx(tuserroleCondition, page);
	}

	@SuppressWarnings("unchecked")
	public List<Tuserrole> findByIuser(Long iuser) {
		List<?> iuserroles = baseDao.getHibernateDaoEx().find(
				"select iuserrole from TuserUserrole where iuser=?", iuser);
		return baseDao.getHibernateDaoEx().find("from Tuserrole where iuserrole in ?",
				iuserroles);
	}
    
	public Tuserrole get(Long id) {
		return (Tuserrole) baseDao.getSession().get(Tuserrole.class, id);
	}

	public void save(Tuserrole trole) {
		baseDao.getSession().save(trole);
	}

	public void update(Tuserrole trole) throws Exception{
		baseDao.getSession().update(trole);
	}
    
	public List<Tuserrole> findSelect(Tuserrole tuserrole) {
		return baseDao.getHibernateDaoEx().findByExample(tuserrole);
	}
}
