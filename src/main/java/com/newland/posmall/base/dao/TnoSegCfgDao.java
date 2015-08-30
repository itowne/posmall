package com.newland.posmall.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.newland.posmall.bean.basebusi.TnoSegCfg;

@Repository
public class TnoSegCfgDao {

	@Resource
	private BaseDao baseDao;
	
	public List<TnoSegCfg> find(){
		return baseDao.getHibernateDaoEx().find(TnoSegCfg.class);
	}

	public void save(TnoSegCfg tnoSegCfg) {
		baseDao.getSession().save(tnoSegCfg);
	}

	public void update(TnoSegCfg tnoSegCfg) {
		baseDao.getSession().update(tnoSegCfg);
	}

	public TnoSegCfg get(Long id) {
		return (TnoSegCfg) baseDao.getSession().get(TnoSegCfg.class, id);
	}

	public List<TnoSegCfg> findTnoSegCfg(TnoSegCfg tnoSegCfg) {
		return this.baseDao.getHibernateDaoEx().findByExample(tnoSegCfg);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE, rollbackFor = Throwable.class)
	public TnoSegCfg lock(int count, Long inoSegCfg) {
		TnoSegCfg cfg = (TnoSegCfg) this.baseDao.getSession().get(TnoSegCfg.class, inoSegCfg);
		if (cfg == null)
			throw new RuntimeException("产品序列号配置不存在");
		Long end = cfg.getEnd();
		Long idx = cfg.getIdx();
		if (idx >= end) throw new RuntimeException("序列号库已满");
		if ((end - idx) < count) throw new RuntimeException("剩余序列号不足");
		int lock = this.baseDao.getHibernateDaoEx().executeUpdate(
				"update " + TnoSegCfg.class.getName()
						+ " ts set ts.idx = ts.idx + " + count + " where ts.inoSegCfg = ?", cfg.getInoSegCfg());
		if (lock > 0){
			return cfg;
		}
		return null;
	}

}
