package org.ohuyo.rapid.base.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.rapid.base.dao.TcustauthDao;
import org.ohuyo.rapid.base.entity.Tcustauth;
import org.springframework.stereotype.Service;

@Service
public class TcustauthService {
	
	@Resource
	private TcustauthDao tcustauthDao;
	
	
	public List<Tcustauth> findAll(){
		return tcustauthDao.findAll();
	}
	
	public Tcustauth  querySysauthByCode(String code){
		Tcustauth tcustauth = new Tcustauth();
		tcustauth.setCode(code);
	    return	this.tcustauthDao.queryByCode(tcustauth);
	}
	
	
	public void save(Tcustauth trole){
		Date date = new Date();
		trole.setGenTime(date);
		trole.setUpdTime(date);
		this.tcustauthDao.save(trole);
				
	}
	
	public void update(Tcustauth trole)throws Exception{
		Date date = new Date();
		trole.setUpdTime(date);
		this.tcustauthDao.update(trole);		
	}
	
	public void delete(Long id)throws Exception{
		Tcustauth troleNew = this.tcustauthDao.get(id);
		troleNew.setUpdTime(new Date());
		this.tcustauthDao.update(troleNew);
	}
	
	public Tcustauth find(Long id){
		return this.tcustauthDao.get(id);
		
	}

	public List<Tcustauth> findSelect(Tcustauth role) {
		return this.tcustauthDao.findSelect(role);
	}

}
