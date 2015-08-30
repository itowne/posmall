package org.ohuyo.rapid.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.rapid.base.entity.Tfile;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author rabbit
 *
 */
@Repository
public class TfileDao {

	@Resource
	private BaseDao baseDao;

	public Tfile get(String uuid) {
		Tfile t = new Tfile();
		t.setUuid(uuid);
		return baseDao.getHibernateDaoEx().getFirstOneByExample(t);
	}

	public void save(Tfile tfile) {
		baseDao.getSession().save(tfile);
	}

	public List<Tfile> getAll() {
		return this.baseDao.getHibernateDaoEx().findByExample(new Tfile());
	}
	
	public Tfile getById(Long id){
		return (Tfile) baseDao.getSession().get(Tfile.class, id);
	}
	
	public Tfile getFileByObj(Tfile file){
		return (Tfile) baseDao.getHibernateDaoEx().getFirstOneByExample(file);
	}

}
