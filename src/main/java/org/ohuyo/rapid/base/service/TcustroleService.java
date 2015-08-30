package org.ohuyo.rapid.base.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.dao.TcustroleDao;
import org.ohuyo.rapid.base.entity.Tcustauth;
import org.ohuyo.rapid.base.entity.Tcustrole;
import org.ohuyo.rapid.base.entity.condition.TcustroleCondition;
import org.springframework.stereotype.Service;

@Service
public class TcustroleService {
	
	@Resource
	private TcustroleDao tcustroleDao;
	
	/**
	 * 角色名查询
	 * @param name
	 * @return
	 */
	public Tcustrole findTcustrole(String name){
		Tcustrole tcustrole = new Tcustrole();
		tcustrole.setName(name);
		tcustrole.setDelFlag(Boolean.FALSE);
		List<Tcustrole> list = tcustroleDao.findSelect(tcustrole);
		return ((list != null && list.size()>0)?list.get(0):null);
	}
	
	public void save(Tcustrole trole){
		Date date = new Date();
		trole.setGenTime(date);
		trole.setUpdTime(date);
		trole.setDelFlag(Boolean.FALSE);
		this.tcustroleDao.save(trole);
				
	}
	
	public void update(Tcustrole trole)throws Exception{
		Date date = new Date();
		trole.setUpdTime(date);
		this.tcustroleDao.update(trole);		
	}
	
	public void delete(Long id)throws Exception{
		Tcustrole troleNew = this.tcustroleDao.get(id);
		troleNew.setUpdTime(new Date());
		troleNew.setDelFlag(Boolean.TRUE);
		Set<Tcustauth> authSet = new HashSet<Tcustauth>();
		troleNew.setAuthSet(authSet);
		this.tcustroleDao.update(troleNew);
	}
	
	public Tcustrole find(Long id){
		return this.tcustroleDao.get(id);
		
	}
	public List<Tcustrole> queryTrole(TcustroleCondition troleCondition,Page page){
		return this.tcustroleDao.find(troleCondition, page);
	}
    
	public List<Tcustrole> findSelect(Tcustrole tcustrole) {
		return this.tcustroleDao.findSelect(tcustrole);
	}
}
