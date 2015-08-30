package org.ohuyo.rapid.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.dao.BaseDao;
import org.ohuyo.rapid.base.entity.Tsys;
import org.ohuyo.rapid.base.entity.condition.TsysCondition;
import org.springframework.stereotype.Repository;


@Repository
public class TsysDao {

	@Resource
	private BaseDao baseDao;

	public List<Tsys> findAll() {
		return baseDao.getHibernateDaoEx().find(Tsys.class);
	}

	@SuppressWarnings("unchecked")
	public List<Tsys> find(TsysCondition tsysConfition,Page page) {
		return baseDao.getHibernateDaoEx().findByCriteriaEx(tsysConfition, page);
	}
	//获取session中的对象
	public Tsys get(Long id) {
		return (Tsys) baseDao.getSession().get(Tsys.class, id);
	}

	public void save(Tsys tsys) {
		baseDao.getSession().save(tsys);
	}
	
	public void delete(Tsys tsys){
		baseDao.getSession().delete(tsys);
	}
	
	public void update(Tsys tsys) {
		baseDao.getSession().update(tsys);
	}
	
	public Tsys getByLoginName(String loginName) {
		Tsys t = new Tsys();
		t.setLoginName(loginName);
		return baseDao.getHibernateDaoEx().getFirstOneByExample(t);

	}
	
	public List<Tsys> findSelect(Tsys tsys) {
		return baseDao.getHibernateDaoEx().findByExample(tsys);
	}
}
