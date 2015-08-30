package org.ohuyo.rapid.base.service;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.dao.TsysSysroleDao;
import org.ohuyo.rapid.base.entity.TsysSysrole;
import org.springframework.stereotype.Service;

@Service
public class TsysSysroleService {

	@Resource
	private TsysSysroleDao tsysSysroleDao;
	
	public List<TsysSysrole> findAll(){
		return this.tsysSysroleDao.findAll();
	}

	public void save(TsysSysrole tsysSysrole) {
		this.tsysSysroleDao.save(tsysSysrole);
	}

	public void update(TsysSysrole tsysSysrole, Long preIsys,Long preIsysrole) {
		this.tsysSysroleDao.update(tsysSysrole, preIsys, preIsysrole);
	}

	public List<TsysSysrole> findByIsys(Long id) {
		return this.tsysSysroleDao.findByIsys(id);
	}

	public List<TsysSysrole> findByIrole(Long id) {
		return this.tsysSysroleDao.findByIrole(id);
	}

	public TsysSysrole findSysRole(TsysSysrole tsysSysrole) {
		return this.tsysSysroleDao.findSysRole(tsysSysrole);
	}

	@SuppressWarnings("rawtypes")
	public List findListByInfo(Page page, String loginName, String name) {
		return this.tsysSysroleDao.findListByInfo(page, loginName, name);
	}

	public void delete(TsysSysrole tsysSysrole) {
		this.tsysSysroleDao.delete(tsysSysrole);
	}

}
