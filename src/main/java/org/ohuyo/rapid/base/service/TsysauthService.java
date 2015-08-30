package org.ohuyo.rapid.base.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.rapid.base.dao.TsysauthDao;
import org.ohuyo.rapid.base.entity.Tsysauth;
import org.springframework.stereotype.Service;

@Service
public class TsysauthService {
	
	@Resource
	private TsysauthDao tsysauthDao;
	
	
	public List<Tsysauth> findAll(){
		return tsysauthDao.findAll();
	}
	
	public Tsysauth  querySysauthByCode(String code){
		Tsysauth tsysauth = new Tsysauth();
		tsysauth.setCode(code);
	    return	this.tsysauthDao.queryByCode(tsysauth);
	}
	
	
	public void save(Tsysauth trole){
		Date date = new Date();
		trole.setGenTime(date);
		trole.setUpdTime(date);
		this.tsysauthDao.save(trole);
				
	}
	
	public void update(Tsysauth trole)throws Exception{
		Date date = new Date();
		trole.setUpdTime(date);
		this.tsysauthDao.update(trole);		
	}
	
	public void delete(Long id)throws Exception{
		Tsysauth troleNew = this.tsysauthDao.get(id);
		troleNew.setUpdTime(new Date());
		this.tsysauthDao.update(troleNew);
	}
	
	public Tsysauth find(Long id){
		return this.tsysauthDao.get(id);
		
	}

	public List<Tsysauth> findSelect(Tsysauth role) {
		return this.tsysauthDao.findSelect(role);
	}

}
