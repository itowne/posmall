package org.ohuyo.rapid.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.entity.ERPMaintenance;
import org.ohuyo.rapid.base.entity.condition.ERPMaintenanceCondition;
import org.springframework.stereotype.Repository;

/**
 * 
* @ClassName: MaintenanceManageDao  
* @Description: 产品维保持久层  
* @author chenwenjing
* @date 2014-10-15 上午10:18:46  
*
 */
@Repository
public class MaintenanceManageDao {

	@Resource
	private BaseDao baseDao;
	
	public List<ERPMaintenance> find() {
		return baseDao.getHibernateDaoEx().find(ERPMaintenance.class);
	}

	public void saveERPMaintenance(ERPMaintenance eRPMaintenance) {
		baseDao.getSession().save(eRPMaintenance);
	}
	
	public void updateERPMaintenance(ERPMaintenance eRPMaintenance) {
		baseDao.getSession().update(eRPMaintenance);
	}

	public ERPMaintenance getBySn(String sn) {
		ERPMaintenance t = new ERPMaintenance();
		t.setSn(sn);
		return baseDao.getHibernateDaoEx().getFirstOneByExample(t);
	}
	
	public ERPMaintenance get(Long id){
		return (ERPMaintenance) baseDao.getSession().get(ERPMaintenance.class, id);
	}
	
	/**
	 * 
	* @Description: 根据对象条件查找对象信息（主键除外）
	* @author chenwenjing    
	* @date 2014-8-29 上午9:48:45
	 */
	public List<ERPMaintenance> findSelect(ERPMaintenance eRPMaintenance) {
		return baseDao.getHibernateDaoEx().findByExample(eRPMaintenance);
	}
	
	@SuppressWarnings("unchecked")
	public List<ERPMaintenance> find(ERPMaintenanceCondition condition,Page page){
		return baseDao.getHibernateDaoEx().findByCriteriaEx(condition, page);
	}
	
	/**
	 * 根据查询序列号、客户名称分页查询
	 * @param sn
	 * @param custName
	 * @param page
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List findPageBySql(String sn, String custName, Page page){
		StringBuffer sql = new StringBuffer("select a.i_erp_maintenance, a.fh_date, a.real_ordno, a.inner_ordno, "
				+ "a.sn, a.ph, a.pm, a.i_cust, a.purchase_date, a.life_start_time, a.warranty_period, "
				+ "a.last_repaired_date, a.repair_num, a.gen_time, a.upd_time, a.valid_status, a.last_maintenance_id, "
				+ "b.name from t_erp_maintenance a left join t_cust_reg b on a.i_cust = b.i_cust where 1=1");
		if (StringUtils.isNotBlank(sn)) {
			sql.append(" and a.sn like '%" + sn + "%'");
		}
		if (StringUtils.isNotBlank(custName)) {
			sql.append(" and b.name like '%" + custName + "%'");
		}
		sql.append(" order by a.i_cust, a.valid_status, a.upd_time desc");
		return baseDao.getHibernateDaoEx().findBySql(page, sql.toString());
	}
}
