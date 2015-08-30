package org.ohuyo.rapid.base.dao;

import java.util.List;

import javax.annotation.Resource;
import org.ohuyo.rapid.base.entity.Tdict;
import org.springframework.stereotype.Repository;

@Repository
public class TdictDao {

	@Resource
	private BaseDao baseDao;
	
	public List<Tdict> list(String dictType) {
		Tdict tdict = new Tdict();
		tdict.setDictType(dictType);
		return baseDao.getHibernateDaoEx().findByExample(tdict);
	}
	
	public List<Tdict> find(){
		return baseDao.getHibernateDaoEx().find(Tdict.class);
	}
	
	public Tdict get(Long id){
		return (Tdict) baseDao.getSession().get(Tdict.class, id);
	}
	
	public void save(Tdict tdict){
		baseDao.getSession().save(tdict);
	}
	
	public void update(Tdict tdict){
		baseDao.getSession().update(tdict);
	}

}
