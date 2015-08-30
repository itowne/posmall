package org.ohuyo.rapid.base.service;


import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.rapid.base.dao.TroleAuthDao;
import org.ohuyo.rapid.base.entity.TroleAuth;
import org.springframework.stereotype.Service;

@Service
public class TroleAuthService {
	
	@Resource
	private TroleAuthDao troleAuthDao;
	
	
	public void save(TroleAuth troleAuth){
	
		this.troleAuthDao.save(troleAuth);
				
	}
	
	public void update(TroleAuth troleAuth){
		TroleAuth troleAuthNew = troleAuthDao.get(troleAuth.getIauth());

		this.troleAuthDao.update(troleAuthNew);		
	}
	
	public List<TroleAuth> findByIrole(Long id){
		return this.troleAuthDao.findByIrole(id);
		
	}
		
	public List<TroleAuth> findByIauth(Long id){
		return this.troleAuthDao.findByIrole(id);
		
	}
}
