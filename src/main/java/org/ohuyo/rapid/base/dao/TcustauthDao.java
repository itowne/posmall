package org.ohuyo.rapid.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.rapid.base.dao.BaseDao;
import org.ohuyo.rapid.base.entity.Tcustauth;
import org.springframework.stereotype.Repository;

@Repository
public class TcustauthDao {

	@Resource
	private BaseDao baseDao;
	
	public Tcustauth queryByCode(Tcustauth tcustauth){
		return baseDao.getHibernateDaoEx().getFirstOneByExample(tcustauth);
	}

	public List<Tcustauth> findAll(){
		return baseDao.getHibernateDaoEx().find(Tcustauth.class);
	}
    
	public Tcustauth get(Long id) {
		return (Tcustauth) baseDao.getSession().get(Tcustauth.class, id);
	}

	public void save(Tcustauth trole) {
		baseDao.getSession().save(trole);
	}

	public void update(Tcustauth trole) throws Exception{
		baseDao.getSession().update(trole);
	}
	public List<Tcustauth> findSelect(Tcustauth tcustauth) {
		return baseDao.getHibernateDaoEx().findByExample(tcustauth);
	}

}
