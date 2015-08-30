package org.ohuyo.rapid.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.rapid.base.dao.BaseDao;
import org.ohuyo.rapid.base.entity.Tsysauth;
import org.springframework.stereotype.Repository;

@Repository
public class TsysauthDao {

	@Resource
	private BaseDao baseDao;
	
	public Tsysauth queryByCode(Tsysauth tsysauth){
		return baseDao.getHibernateDaoEx().getFirstOneByExample(tsysauth);
	}

	public List<Tsysauth> findAll(){
		return baseDao.getHibernateDaoEx().find(Tsysauth.class);
	}
    
	public Tsysauth get(Long id) {
		return (Tsysauth) baseDao.getSession().get(Tsysauth.class, id);
	}

	public void save(Tsysauth trole) {
		baseDao.getSession().save(trole);
	}

	public void update(Tsysauth trole) throws Exception{
		baseDao.getSession().update(trole);
	}
	public List<Tsysauth> findSelect(Tsysauth tsysauth) {
		return baseDao.getHibernateDaoEx().findByExample(tsysauth);
	}

}
