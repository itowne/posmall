package com.newland.posmall.biz.admin;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.Order;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.entity.ERPMaintenance;
import org.ohuyo.rapid.base.entity.condition.ERPMaintenanceCondition;
import org.ohuyo.rapid.base.service.MaintenanceManageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;



@Service
@Transactional
public class MaintenanceManageBiz {
	
	@Resource
	private MaintenanceManageService maintenanceManageService;
	
	public List<ERPMaintenance> find(ERPMaintenanceCondition condition, Page page){
		condition.addOrders(Order.desc("ierpMaintenance"));
		return this.maintenanceManageService.find(condition, page);
	}
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public ERPMaintenance queryErpMaintenanceById(Long id) {
		if (id == null) return null;
		return this.maintenanceManageService.getERPMaintenanceById(id);
	}
	
	/**
	 * 根据更换设备前id查询
	 * @param lastId
	 * @return
	 */
	public ERPMaintenance queryByLastMaintenanceId(Long lastId) {
		if (lastId == null) return null;
		ERPMaintenance condition= new ERPMaintenance();
		condition.setLastMaintenanceId(lastId);
		List<ERPMaintenance> list = this.maintenanceManageService.findSelect(condition);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	/**
	 * 根据查询序列号、客户名称分页查询
	 * @param sn
	 * @param custName
	 * @param page
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List queryPageBySql(String sn, String custName, Page page){
		return this.maintenanceManageService.findPageBySql(sn, custName, page);
	}
}
