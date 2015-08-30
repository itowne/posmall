package org.ohuyo.rapid.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.rapid.base.dao.BaseDao;
import org.ohuyo.rapid.base.entity.Tuserauth;
import org.springframework.stereotype.Repository;

@Repository
public class TuserauthDao {

	@Resource
	private BaseDao baseDao;
	
	public Tuserauth queryByCode(Tuserauth tuserauth){
		return baseDao.getHibernateDaoEx().getFirstOneByExample(tuserauth);
	}

	public List<Tuserauth> findAll(){
		return baseDao.getHibernateDaoEx().find(Tuserauth.class);
	}
    
	public Tuserauth get(Long id) {
		return (Tuserauth) baseDao.getSession().get(Tuserauth.class, id);
	}

	public void save(Tuserauth trole) {
		baseDao.getSession().save(trole);
	}

	public void update(Tuserauth trole) throws Exception{
		baseDao.getSession().update(trole);
	}
	public List<Tuserauth> findSelect(Tuserauth tuserauth) {
		return baseDao.getHibernateDaoEx().findByExample(tuserauth);
	}

}
