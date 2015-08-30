package org.ohuyo.rapid.base.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.dao.MaintenanceManageDao;
import org.ohuyo.rapid.base.entity.ERPMaintenance;
import org.ohuyo.rapid.base.entity.condition.ERPMaintenanceCondition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.newland.posmall.bean.dict.ValidStatus;

/**
 * 
* @ClassName: MaintenanceManageService  
* @Description: 产品维保service层 
* @author chenwenjing
* @date 2014-10-15 上午10:42:27  
*
 */

@Service
@Transactional
public class MaintenanceManageService {

	@Resource
	private MaintenanceManageDao maintenanceManageDao;

	public ERPMaintenance getBySn(String sn){
		return this.maintenanceManageDao.getBySn(sn);
	}

	public void saveERPMaintenance(ERPMaintenance eRPMaintenance) {
		Date now = new Date();
		eRPMaintenance.setGenTime(now);
		eRPMaintenance.setUpdTime(now);
		this.maintenanceManageDao.saveERPMaintenance(eRPMaintenance);
	}
	
	public void updateERPMaintenance(ERPMaintenance eRPMaintenance) {
		Date now = new Date();
		eRPMaintenance.setUpdTime(now);
		this.maintenanceManageDao.updateERPMaintenance(eRPMaintenance);
	}

	public ERPMaintenance getERPMaintenanceById(Long id) {
		return this.maintenanceManageDao.get(id);
	}
	
	public List<ERPMaintenance> findSelect(ERPMaintenance eRPMaintenance){
		return this.maintenanceManageDao.findSelect(eRPMaintenance);
    }
    
	public List<ERPMaintenance> find(ERPMaintenanceCondition condition,Page page){
		return this.maintenanceManageDao.find(condition, page);
	}
	
	/**
	 * 根据产品序列号查找
	 * @param seqNo
	 * @return
	 */
	public ERPMaintenance findBySeqNo(String seqNo) {
		if(StringUtils.isBlank(seqNo)) return null;
		ERPMaintenance condition = new ERPMaintenance();
		condition.setSn(seqNo);
		
		condition.setValidStatus(ValidStatus.VALID); //更换设备之后，该条记录无效，所以需要加查询条件
		
		List<ERPMaintenance> list = this.maintenanceManageDao.findSelect(condition);
		return (CollectionUtils.isEmpty(list)) ? null : list.get(0);
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
		return this.maintenanceManageDao.findPageBySql(sn, custName, page);
	}
}
