package org.ohuyo.rapid.base.service;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.dao.TuserUserroleDao;
import org.ohuyo.rapid.base.entity.TuserUserrole;
import org.springframework.stereotype.Service;

@Service
public class TuserUserroleService {

	@Resource
	private TuserUserroleDao tuserUserroleDao;

	public void save(TuserUserrole tuserUserrole) {

		this.tuserUserroleDao.save(tuserUserrole);

	}

	public void update(TuserUserrole tuserUserrole, Long preIuser,
			Long preIuserrole) {
		this.tuserUserroleDao.update(tuserUserrole, preIuser, preIuserrole);
	}

	public List<TuserUserrole> findByIuser(Long id) {
		return this.tuserUserroleDao.findByIuser(id);
	}

	public List<TuserUserrole> findByIrole(Long id) {
		return this.tuserUserroleDao.findByIrole(id);
	}

	public TuserUserrole findUserRole(TuserUserrole tuserUserrole) {
		return this.tuserUserroleDao.findUserRole(tuserUserrole);
	}

	@SuppressWarnings("rawtypes")
	public List findListByInfo(Page page, String loginName, String name) {
		return this.tuserUserroleDao.findListByInfo(page, loginName, name);
	}

	public void delete(TuserUserrole tuserUserrole) {
		this.tuserUserroleDao.delete(tuserUserrole);
	}

}
