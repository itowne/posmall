package com.newland.posmall.biz.cust;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Order;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.entity.ERPMaintenance;
import org.ohuyo.rapid.base.entity.condition.ERPMaintenanceCondition;
import org.ohuyo.rapid.base.service.MaintenanceManageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.newland.posmall.base.exception.BizException;
import com.newland.posmall.base.service.TlogService;
import com.newland.posmall.base.service.TwarrantyService;
import com.newland.posmall.bean.common.Tlog;
import com.newland.posmall.bean.customer.Tcust;
import com.newland.posmall.bean.customer.TcustReg;
import com.newland.posmall.bean.customer.Twarranty;
import com.newland.posmall.bean.customer.condition.TwarrantyCondition;
import com.newland.posmall.bean.dict.LogType;
import com.newland.posmall.bean.dict.OperType;
import com.newland.posmall.bean.dict.WarrantyStatus;
import com.newland.posmall.controller.cust.model.Warranty4Page;

@Service
@Transactional(rollbackFor = Throwable.class)
public class CustWarrantyBiz {

	@Resource
	private TwarrantyService twarrantyService;
	
	@Resource
	private MaintenanceManageService maintenanceManageService;

	@Resource
	private TlogService tlogService;

	/**
	 * 分页查询
	 * @param condition
	 * @param tcust
	 * @param page
	 * @return
	 */
	public List<Twarranty> queryPageList(TwarrantyCondition condition, Tcust tcust, Page page) {

		if (condition == null) {
			condition = new TwarrantyCondition();
		}
		// 查询自己的数据
		condition.setIcustList(Arrays.asList(tcust.getIcust()));
		
		condition.addOrders(Order.desc("updTime"));
		condition.addOrders(Order.asc("pdtNo"));
		condition.addOrders(Order.asc("seqNo"));
		return this.twarrantyService.find(condition, page);
	}
	
	
	public Twarranty queryById(Long iwarranty) {
		if(iwarranty == null) return null;
		
		return this.twarrantyService.get(iwarranty);
	}
	
	/**
	 * 根据产品序列号查询
	 * @param seqNo
	 * @return
	 */
	public ERPMaintenance queryBySeqno(String seqNo) {
		return this.maintenanceManageService.findBySeqNo(seqNo);
	}
	
	/**
	 * 添加报修数据
	 * @param warranty4Page
	 * @param tcust
	 * @param tcustReg
	 * @return
	 * @throws BizException
	 */
	public Twarranty warrantyAdd(Warranty4Page warranty4Page, Tcust tcust, TcustReg tcustReg) throws BizException {
		if(warranty4Page == null || 
				StringUtils.isBlank(warranty4Page.getSeqNo()) || 
				warranty4Page.getIerpMaintenance() == null) {
			throw new BizException(0, "数据获取失败，请关闭页面重试");
		}
		ERPMaintenance maintenanceDB = this.queryBySeqno(warranty4Page.getSeqNo());
		if(maintenanceDB.getIerpMaintenance().equals(warranty4Page.getIerpMaintenance()) == false) {
			throw new BizException(0, "产品序列号不存在，请关闭页面重试");
		}
		
		// 判断报修申请是否重复提交
		if(this.isResubmit(maintenanceDB.getSn())) {
			throw new BizException(0, "报修申请不能重复提交");
		}
		
		// 添加报修记录
		Twarranty twarranty = new Twarranty();
		twarranty.setIcust(tcust.getIcust());
		twarranty.setCustName(tcustReg.getName());
		twarranty.setIerpMaintenance(maintenanceDB.getIerpMaintenance());
		twarranty.setPdtNo(maintenanceDB.getPh());
		twarranty.setRemark(warranty4Page.getRemark());
		twarranty.setSeqNo(maintenanceDB.getSn());
		twarranty.setWarrantyTime(new Date());
		twarranty.setWarrantyStatus(WarrantyStatus.HAVE_SUBMIT);
		this.twarrantyService.add(twarranty);
		
		// 修改报修次数
		maintenanceDB.setRepairNum(maintenanceDB.getRepairNum() + 1);
		this.maintenanceManageService.updateERPMaintenance(maintenanceDB);
		
		// 记录日志
		Tlog tlog = new Tlog();
		tlog.setIfk(tcust.getIcust());
		tlog.setLogType(LogType.CUST);
		tlog.setOperType(OperType.WARRANTY_MGR);
		tlog.setMemo("客户产品报修申请");
		tlog.setPreData("");
		tlog.setData("报修数据：id[" + twarranty.getIwarranty() + ",产品序列号[" + twarranty.getSeqNo() + "]");
		this.tlogService.save(tlog);
		
		return twarranty;
	}
	
	/**
	 * 判断报修申请是否重复提交
	 * @param seqNo
	 * @return
	 */
	private boolean isResubmit(String seqNo) {
		Twarranty condition = new Twarranty();
		condition.setSeqNo(seqNo);
		List<Twarranty> list = this.twarrantyService.findByExample(condition);
		if(CollectionUtils.isEmpty(list)) {
			return false;
		}
		for (Twarranty twarranty : list) {
			if(twarranty.getWarrantyStatus().equals(WarrantyStatus.HAVE_SUBMIT) ||
					twarranty.getWarrantyStatus().equals(WarrantyStatus.HAVE_ACCEPT)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 报修撤销：修改状态
	 * @param iwarranty
	 * @param tcust
	 * @param tcustReg
	 * @return
	 * @throws BizException
	 */
	public Twarranty warrantyCacel(Long iwarranty, Tcust tcust, TcustReg tcustReg) throws BizException {
		
		if(iwarranty == null) {
			throw new BizException(0, "主键获取失败，请刷新页面重试");
		}
		Twarranty twarrantyDel = this.twarrantyService.get(iwarranty);
		if(twarrantyDel == null) {
			throw new BizException(0, "报修记录不存在，请刷新页面重试");
		}
		if(twarrantyDel.getWarrantyStatus().equals(WarrantyStatus.REVOKED)) {
			throw new BizException(0, "已撤销，不能重复操作");
		}
		if(twarrantyDel.getWarrantyStatus().equals(WarrantyStatus.HAVE_REPAIRED)) {
			throw new BizException(0, "操作有误：报修申请已修复");
		}
		if(twarrantyDel.getWarrantyStatus().equals(WarrantyStatus.HAVE_ACCEPT)) {
			throw new BizException(0, "报修申请已受理，如需撤销请联系公司业务员");
		}
		if (!twarrantyDel.getIcust().equals(tcust.getIcust())) {
			throw new BizException(0, "非法操作：非当前用户报修申请");
		}
		
		// 修改状态
		this.twarrantyService.delete(twarrantyDel);
		
		// 修改报修次数
		ERPMaintenance maintenanceDB = this.queryBySeqno(twarrantyDel.getSeqNo());
		if(maintenanceDB.getIerpMaintenance().equals(twarrantyDel.getIerpMaintenance()) == false) {
			throw new BizException(0, "报修数据异常：主键关联失败");
		}
		maintenanceDB.setRepairNum(maintenanceDB.getRepairNum() - 1);
		this.maintenanceManageService.updateERPMaintenance(maintenanceDB);
		
		// 记录日志
		Tlog tlog = new Tlog();
		tlog.setIfk(tcust.getIcust());
		tlog.setLogType(LogType.CUST);
		tlog.setOperType(OperType.WARRANTY_MGR);
		tlog.setMemo("客户产品报修撤销");
		tlog.setPreData("");
		tlog.setData("报修数据：id[" + twarrantyDel.getIwarranty() + ",产品序列号[" + twarrantyDel.getSeqNo() + "]");
		this.tlogService.save(tlog);
		
		return twarrantyDel;
	}
	
	/**
	 * 分页查询保修数据
	 * @param seqNo
	 * @param tcust
	 * @param page
	 * @return
	 */
	public List<ERPMaintenance> queryERPMaintenancePageList(String seqNo, Tcust tcust, Page page){
		if (tcust == null || tcust.getIcust() == null) {
			return null;
		}
		ERPMaintenanceCondition condition = new ERPMaintenanceCondition();
		if(StringUtils.isNotBlank(seqNo)) {
			condition.setSn(seqNo);
		}
		
		condition.setIcust(tcust.getIcust());
		
		condition.addOrders(Order.asc("validStatus"));
		condition.addOrders(Order.desc("updTime"));
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
}
