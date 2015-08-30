package org.ohuyo.rapid.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.rapid.base.entity.ERPSync;
import org.ohuyo.rapid.base.entity.condition.ERPSyncCondition;
import org.springframework.stereotype.Repository;

/**
 * erp同步接口
 * 
 * @author Mr.Towne
 * 
 */
@Repository
public class ERPSyncDao {

	@Resource
	private BaseDao baseDao;

	public void save(ERPSync erp) {
		baseDao.getSession().save(erp);
	}

	public void update(ERPSync erp) {
		baseDao.getSession().update(erp);
	}

	public List<ERPSync> findAll() {
		return baseDao.getHibernateDaoEx().find(ERPSync.class);
	}

	/**
	 * 通过流水号查找
	 */
	public ERPSync findById(Long id) {
		return (ERPSync) baseDao.getSession().get(ERPSync.class, id);
	}
	
	public void delete(ERPSync erp) {
		baseDao.getSession().delete(erp);
	}
	/**
	 * 
	* @Description: 根据条件查询
	* @author chenwenjing    
	* @date 2014-9-9 下午3:24:39
	 */
	public ERPSync getByCondition(String db,String dh,String xh) {
		ERPSync e = new ERPSync();
		e.setDb(db);
		e.setDh(dh);
		e.setXh(xh);
		return baseDao.getHibernateDaoEx().getFirstOneByExample(e);
	}
	/**
	 * 
	* @Description: 针对session拥有同一标识的不同实体进行更新
	* @author chenwenjing    
	* @date 2014-9-10 下午3:15:08
	 */
	public void merge(ERPSync erp) {
		baseDao.getSession().merge(erp);
	}
	/**
	 * 
	* @Description: 获取当天同步更新过来的erp数据
	* @author chenwenjing    
	* @date 2014-10-15 下午2:17:15
	 */
	@SuppressWarnings("unchecked")
	public List<ERPSync> findBySjtqDate(String genTime){
		ERPSyncCondition e = new ERPSyncCondition();
		e.setSjtqDate(genTime);
		return baseDao.getHibernateDaoEx().findByCriteriaEx(e);
	}
}
