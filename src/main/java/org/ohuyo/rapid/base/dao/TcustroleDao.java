package org.ohuyo.rapid.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.dao.BaseDao;
import org.ohuyo.rapid.base.entity.Tcustrole;
import org.ohuyo.rapid.base.entity.condition.TcustroleCondition;
import org.springframework.stereotype.Repository;

@Repository
public class TcustroleDao {

	@Resource
	private BaseDao baseDao;
	
	@SuppressWarnings("unchecked")
	public List<Tcustrole> find(TcustroleCondition tcustroleCondition,Page page) {
		return baseDao.getHibernateDaoEx().findByCriteriaEx(tcustroleCondition, page);
	}

	@SuppressWarnings("unchecked")
	public List<Tcustrole> findByIcust(Long icust) {
		List<?> icustroles = baseDao.getHibernateDaoEx().find(
				"select icustrole from TcustCustrole where icust=?", icust);
		return baseDao.getHibernateDaoEx().find("from Tcustrole where icustrole in ?",
				icustroles);
	}
    
	public Tcustrole get(Long id) {
		return (Tcustrole) baseDao.getSession().get(Tcustrole.class, id);
	}

	public void save(Tcustrole trole) {
		baseDao.getSession().save(trole);
	}

	public void update(Tcustrole trole) throws Exception{
		baseDao.getSession().update(trole);
	}
	/**
	 * 
	* @Description: 根据非主键的条件查找
	* @author chenwenjing    
	* @date 2014-8-29 下午3:12:33
	 */
	public List<Tcustrole> findSelect(Tcustrole tcustrole) {
		return baseDao.getHibernateDaoEx().findByExample(tcustrole);
	}

}
