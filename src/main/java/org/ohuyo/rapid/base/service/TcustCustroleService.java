package org.ohuyo.rapid.base.service;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.dao.TcustCustroleDao;
import org.ohuyo.rapid.base.entity.TcustCustrole;
import org.ohuyo.rapid.base.entity.condition.TcustCustroleCondition;
import org.springframework.stereotype.Service;

@Service
public class TcustCustroleService {
	
	@Resource
	private TcustCustroleDao tcustCustroleDao;
	
	
	public void save(TcustCustrole tcustCustrole){
		
		this.tcustCustroleDao.save(tcustCustrole);
				
	}
	
	public void update(TcustCustrole tcustCustrole,Long preIcust,Long preIcustrole){
		this.tcustCustroleDao.update(tcustCustrole,preIcust,preIcustrole);		
	}
	
	
	public List<TcustCustrole> findByIcust(Long id){
		return this.tcustCustroleDao.findByIcust(id);
	}
	
	public List<TcustCustrole> findByIrole(Long id){
		return this.tcustCustroleDao.findByIrole(id);
	}
	
	public TcustCustrole findCustRole(TcustCustrole tcustCustrole){
		return this.tcustCustroleDao.findCustRole(tcustCustrole);
	}
	
	public List<TcustCustrole> findAllCustRole(TcustCustroleCondition condition,Page page){
		return this.tcustCustroleDao.find(condition, page);
	}
	
	@SuppressWarnings("rawtypes")
	public List findListByInfo(Page page, String loginName,String name){
		return this.tcustCustroleDao.findListByInfo(page, loginName, name);
	}
	
	public void delete(TcustCustrole tcustRole){
		this.tcustCustroleDao.delete(tcustRole);
	}

}
