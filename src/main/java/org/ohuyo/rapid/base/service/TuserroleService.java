package org.ohuyo.rapid.base.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.dao.TuserroleDao;
import org.ohuyo.rapid.base.entity.Tuserauth;
import org.ohuyo.rapid.base.entity.Tuserrole;
import org.ohuyo.rapid.base.entity.condition.TuserroleCondition;
import org.springframework.stereotype.Service;

@Service
public class TuserroleService {
	
	@Resource
	private TuserroleDao tuserroleDao;
	
	
	public void save(Tuserrole trole){
		Date date = new Date();
		trole.setGenTime(date);
		trole.setUpdTime(date);
		trole.setDelFlag(Boolean.FALSE);
		this.tuserroleDao.save(trole);
				
	}
	
	public void update(Tuserrole trole)throws Exception{
		Date date = new Date();
		trole.setUpdTime(date);
		this.tuserroleDao.update(trole);		
	}
	
	public void delete(Long id)throws Exception{
		Tuserrole troleNew = this.tuserroleDao.get(id);
		troleNew.setUpdTime(new Date());
		troleNew.setDelFlag(Boolean.TRUE);
		Set<Tuserauth> authSet = new HashSet<Tuserauth>();
		troleNew.setAuthSet(authSet);
		this.tuserroleDao.update(troleNew);
	}
	
	public Tuserrole find(Long id){
		return this.tuserroleDao.get(id);
		
	}
	
	public Tuserrole findBy(Long id){
		return this.tuserroleDao.get(id);
		
	}
	public List<Tuserrole> queryTrole(TuserroleCondition troleCondition,Page page){
		return this.tuserroleDao.find(troleCondition, page);
	}
	
	public List<Tuserrole> findSelect(Tuserrole tuserrole){
		return this.tuserroleDao.findSelect(tuserrole);
	}

}
