package org.ohuyo.rapid.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.rapid.base.dao.BaseDao;
import org.ohuyo.rapid.base.entity.TroleAuth;
import org.springframework.stereotype.Repository;
@Repository
public class TroleAuthDao {
	
	@Resource
	private BaseDao baseDao;
	
	public List<TroleAuth> find(){
		return baseDao.getHibernateDaoEx().find(TroleAuth.class);
	}
	
	public TroleAuth get(Long id){
		return (TroleAuth) baseDao.getSession().get(TroleAuth.class, id);
	}
	
	public void save(TroleAuth troleAuth){
		baseDao.getSession().save(troleAuth);
	}
	
	public void update(TroleAuth troleAuth){
		baseDao.getSession().update(troleAuth);
	}
    
	public List<TroleAuth> findByIrole(Long id){
		TroleAuth troleAuth = new TroleAuth();
		troleAuth.setIrole(id);
		return this.baseDao.getHibernateDaoEx().findByExample(troleAuth);
	}
	
	public List<TroleAuth> findByIauth(Long id){
		TroleAuth troleAuth = new TroleAuth();
		troleAuth.setIauth(id);
		return this.baseDao.getHibernateDaoEx().findByExample(troleAuth);
	}
}
