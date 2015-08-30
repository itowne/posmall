package org.ohuyo.rapid.base.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.dao.TsysroleDao;
import org.ohuyo.rapid.base.entity.Tsysauth;
import org.ohuyo.rapid.base.entity.Tsysrole;
import org.ohuyo.rapid.base.entity.condition.TsysroleCondition;
import org.springframework.stereotype.Service;

@Service
public class TsysroleService {
	
	@Resource
	private TsysroleDao tsysroleDao;
	
	
	public void save(Tsysrole trole){
		Date date = new Date();
		trole.setGenTime(date);
		trole.setUpdTime(date);
		trole.setDelFlag(Boolean.FALSE);
		this.tsysroleDao.save(trole);
				
	}
	
	public void update(Tsysrole trole)throws Exception{
		Date date = new Date();
		trole.setUpdTime(date);
		this.tsysroleDao.update(trole);		
	}
	
	public void delete(Long id)throws Exception{
		Tsysrole troleNew = this.tsysroleDao.get(id);
		troleNew.setUpdTime(new Date());
		troleNew.setDelFlag(Boolean.TRUE);
		Set<Tsysauth> authSet = new HashSet<Tsysauth>();
		troleNew.setAuthSet(authSet);
		this.tsysroleDao.update(troleNew);
	}
	
	public Tsysrole find(Long id){
		return this.tsysroleDao.get(id);
		
	}
	public List<Tsysrole> queryTrole(TsysroleCondition troleCondition,Page page){
		return this.tsysroleDao.find(troleCondition, page);
	}

	public List<Tsysrole> findSelect(Tsysrole role) {
		return this.tsysroleDao.findSelect(role);
	}

}
