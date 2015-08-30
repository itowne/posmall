package org.ohuyo.rapid.base.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.rapid.base.dao.ERPSyncDao;
import org.ohuyo.rapid.base.entity.ERPSync;
import org.springframework.stereotype.Service;

@Service
public class ERPSyncService {

	@Resource
	private ERPSyncDao erpSyncDao;

	public void save(ERPSync erp) {
		Date now = new Date();
		erp.setGenTime(now);
		erp.setUpdTime(now);
		erpSyncDao.save(erp);
	}

	public void update(ERPSync erp) {
		erp.setUpdTime(new Date());
		erpSyncDao.update(erp);
	}

	public List<ERPSync> findAll() {
		return erpSyncDao.findAll();
	}

	/**
	 * 通过流水号查找
	 */
	public ERPSync findById(Long id) {
		return (ERPSync) erpSyncDao.findById(id);
	}
	
	public void delete(ERPSync erp) {
		erpSyncDao.delete(erp);
	}
	/**
	 * 
	* @Description: 根据条件查找
	* @author chenwenjing    
	* @date 2014-9-9 下午3:28:25
	 */
	public ERPSync getByCondition(String db,String dh,String xh) {
		return erpSyncDao.getByCondition(db, dh, xh);
	}
	public void merge(ERPSync erp) {
		erp.setUpdTime(new Date());
		erpSyncDao.merge(erp);
	}
	/**
	 * 
	* @Description: 获取当天erp同步更新的数据
	* @author chenwenjing    
	* @date 2014-10-15 下午2:19:24
	 */
	public List<ERPSync> findBySjtqDate(String genTime){
		return this.erpSyncDao.findBySjtqDate(genTime);
	}
}
