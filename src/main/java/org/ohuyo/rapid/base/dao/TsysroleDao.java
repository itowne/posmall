package org.ohuyo.rapid.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.dao.BaseDao;
import org.ohuyo.rapid.base.entity.Tsysrole;
import org.ohuyo.rapid.base.entity.condition.TsysroleCondition;
import org.springframework.stereotype.Repository;

@Repository
public class TsysroleDao {

	@Resource
	private BaseDao baseDao;
	
	@SuppressWarnings("unchecked")
	public List<Tsysrole> find(TsysroleCondition tsysroleCondition,Page page) {
		return baseDao.getHibernateDaoEx().findByCriteriaEx(tsysroleCondition, page);
	}

	@SuppressWarnings("unchecked")
	public List<Tsysrole> findByIuser(Long isys) {
		List<?> isysroles = baseDao.getHibernateDaoEx().find(
				"select isysrole from TsysSysrole where isys=?", isys);
		return baseDao.getHibernateDaoEx().find("from Tsysrole where isysrole in ?",
				isysroles);
	}
    
	public Tsysrole get(Long id) {
		return (Tsysrole) baseDao.getSession().get(Tsysrole.class, id);
	}

	public void save(Tsysrole trole) {
		baseDao.getSession().save(trole);
	}

	public void update(Tsysrole trole) throws Exception{
		baseDao.getSession().update(trole);
	}
	public List<Tsysrole> findSelect(Tsysrole tsysrrole) {
		return baseDao.getHibernateDaoEx().findByExample(tsysrrole);
	}

}
