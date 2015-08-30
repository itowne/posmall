package org.ohuyo.rapid.base.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.rapid.base.dao.TuserauthDao;
import org.ohuyo.rapid.base.entity.Tuserauth;
import org.springframework.stereotype.Service;

@Service
public class TuserauthService {
	
	@Resource
	private TuserauthDao tuserauthDao;
	
	
	public List<Tuserauth> findAll(){
		return tuserauthDao.findAll();
	}
	
	public Tuserauth  querySysauthByCode(String code){
		Tuserauth tuserauth = new Tuserauth();
		tuserauth.setCode(code);
	    return	this.tuserauthDao.queryByCode(tuserauth);
	}
	
	
	public void save(Tuserauth trole){
		Date date = new Date();
		trole.setGenTime(date);
		trole.setUpdTime(date);
		this.tuserauthDao.save(trole);
				
	}
	
	public void update(Tuserauth trole)throws Exception{
		Date date = new Date();
		trole.setUpdTime(date);
		this.tuserauthDao.update(trole);		
	}
	
	public void delete(Long id)throws Exception{
		Tuserauth troleNew = this.tuserauthDao.get(id);
		troleNew.setUpdTime(new Date());
		this.tuserauthDao.update(troleNew);
	}
	
	public Tuserauth find(Long id){
		return this.tuserauthDao.get(id);
		
	}

	public List<Tuserauth> findSelect(Tuserauth role) {
		return this.tuserauthDao.findSelect(role);
	}

}
