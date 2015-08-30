package com.newland.posmall.biz.admin;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.criterion.Order;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.entity.ERPMaintenance;
import org.ohuyo.rapid.base.entity.TsysParam;
import org.ohuyo.rapid.base.entity.Tuser;
import org.ohuyo.rapid.base.service.MaintenanceManageService;
import org.ohuyo.rapid.base.service.TsysParamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newland.posmall.base.exception.BizException;
import com.newland.posmall.base.service.TlogService;
import com.newland.posmall.base.service.TwarrantyService;
import com.newland.posmall.base.service.WarrantyDownLoadService;
import com.newland.posmall.bean.basebusi.WarrantyDownLoad;
import com.newland.posmall.bean.basebusi.condition.WarrantyDownLoadCondition;
import com.newland.posmall.bean.common.Tlog;
import com.newland.posmall.bean.customer.Twarranty;
import com.newland.posmall.bean.customer.condition.TwarrantyCondition;
import com.newland.posmall.bean.dict.LogType;
import com.newland.posmall.bean.dict.OperType;
import com.newland.posmall.bean.dict.ValidStatus;
import com.newland.posmall.bean.dict.WarrantyStatus;



@Service
@Transactional
public class WarrantyBiz {
	
	private static final Logger logger = LoggerFactory.getLogger(WarrantyBiz.class);
	
	@Resource
	private TwarrantyService twarrantyService;
	
	@Resource
	private TlogService tlogService;
	
	@Resource
	private WarrantyDownLoadService warrantyDownLoadService;
	
	@Resource
	private MaintenanceManageService maintenanceManageService;
	
	@Resource
	private TsysParamService tsysParamService;
	
	public List<Twarranty> find(TwarrantyCondition condition, Page page){
		
		if (condition == null) {
			condition = new TwarrantyCondition();
		}
		condition.addOrders(Order.desc("updTime"));
		return this.twarrantyService.find(condition, page);
	}
	
	public void warrantyCancel(Tuser tuser, Long iwarranty)throws BizException{
		Twarranty twarranty = this.twarrantyService.get(iwarranty);
		if(twarranty != null){
			twarranty.setWarrantyStatus(WarrantyStatus.REVOKED);
			this.twarrantyService.update(twarranty);
		}else{
			logger.debug("无此报修受理单");
			throw new BizException(121, "无此报修受理单");
		}
		
		// 日志记录
		Tlog tlog = new Tlog();
		tlog.setIfk(tuser.getIuser());
		tlog.setLogType(LogType.USER);
		tlog.setOperType(OperType.WARRANTY_MGR);
		tlog.setMemo("后台管理员撤销报修受理单");
		tlog.setPreData("");
		tlog.setData("管理员" + tuser.getTuserSub().getName()
						+ "撤销报修受理单,原受理单编号是" + iwarranty);
		this.tlogService.save(tlog);
	}
	public List<Twarranty> find(TwarrantyCondition condition){
		condition.addOrders(Order.asc("updTime"));
		return this.twarrantyService.find(condition);
	}
	public Twarranty updateTwarranty(Twarranty twa, Tuser tuser) {
		
		// 日志记录
		Tlog tlog = new Tlog();
		tlog.setGenTime(new Date());
		tlog.setIfk(tuser.getIuser());
		tlog.setLogType(LogType.USER);
		tlog.setOperType(OperType.WARRANTY_MGR);
		tlog.setMemo(tuser.getTuserSub().getName()+"受理报修编号:" + twa.getIwarranty());
		StringBuffer sb = new StringBuffer();
		sb.append("修改前 报修编号：").append(twa.getIwarranty()).append(",报修状态:")
				.append(twa.getWarrantyStatus());
		tlog.setPreData(sb.toString());
		
		twa.setWarrantyStatus(WarrantyStatus.HAVE_ACCEPT);
		Date now = new Date();
		twa.setAcceptTime(now);
		this.twarrantyService.update(twa);

		StringBuffer sb2 = new StringBuffer();
		sb2.append("修改后  报修编号：").append(twa.getIwarranty()).append(",报修状态:")
				.append(twa.getWarrantyStatus());
		tlog.setData(sb2.toString());
		tlogService.save(tlog);
		
		return twa;
	}
	public void saveWarrantyDownLoad(WarrantyDownLoad wd){
		this.warrantyDownLoadService.save(wd);
	}
	public List<WarrantyDownLoad> findDownListByCondition(WarrantyDownLoadCondition condition,Page page){
		return this.warrantyDownLoadService.findListByCondition(condition, page);
	}
	public List<Twarranty> findTwarrantyListByCondition(Twarranty twarranty){
		return this.twarrantyService.findByExample(twarranty);
	}
	public void warrantyRepaired(Tuser tuser, Twarranty twarranty)throws BizException{
		Twarranty twarrantyOri = this.twarrantyService.get(twarranty.getIwarranty());
		if(twarrantyOri == null){
			logger.debug("无此报修受理单");
			throw new BizException(121, "无此报修受理单");
		}
		ERPMaintenance em = this.maintenanceManageService.getERPMaintenanceById(twarrantyOri.getIerpMaintenance());
		
		// 更换设备，更换产品序列号
		if (this.inWarrantyPeriod(em)) {
			if (StringUtils.isBlank(twarranty.getNewSeqNo())) {
				throw new BizException(0, "保修期内更换设备，需要更换产品序列号");
			}
			if (twarranty.getNewSeqNo().equals(twarrantyOri.getSeqNo())) {
				throw new BizException(0, "新产品序列号与原序列号冲突");
			}
			ERPMaintenance maintenanceNew = new ERPMaintenance();
			maintenanceNew.setFhDate(em.getFhDate());
			maintenanceNew.setIcust(em.getIcust());
			maintenanceNew.setInnerOrdno(em.getInnerOrdno());
			maintenanceNew.setLastMaintenanceId(em.getIerpMaintenance());
			// maintenanceNew.setLastRepairedDate(em.getLastRepairedDate());
			maintenanceNew.setLifeStartTime(em.getLifeStartTime());
			maintenanceNew.setPh(em.getPh());
			maintenanceNew.setPm(em.getPm());
			maintenanceNew.setPurchaseDate(em.getPurchaseDate());
			maintenanceNew.setRealOrdno(em.getRealOrdno());
			maintenanceNew.setRepairNum(0);
			maintenanceNew.setSn(twarranty.getNewSeqNo());
			maintenanceNew.setValidStatus(ValidStatus.VALID);
			maintenanceNew.setWarrantyPeriod(em.getWarrantyPeriod());
			this.maintenanceManageService.saveERPMaintenance(maintenanceNew);
			
			em.setValidStatus(ValidStatus.INVALID);
			
			twarrantyOri.setNewSeqNo(twarranty.getNewSeqNo());
		}
		Date now = new Date();
		twarrantyOri.setRepairedTime(now);
		twarrantyOri.setWarrantyPerson(twarranty.getWarrantyPerson());
		twarrantyOri.setRepairedRemark(twarranty.getRepairedRemark());
		twarrantyOri.setWarrantyStatus(WarrantyStatus.HAVE_REPAIRED);
		this.twarrantyService.update(twarrantyOri);
		
		//更新保修查询表信息
		em.setLastRepairedDate(now);
		this.maintenanceManageService.updateERPMaintenance(em);
		
		// 日志记录
		Tlog tlog = new Tlog();
		tlog.setIfk(tuser.getIuser());
		tlog.setLogType(LogType.USER);
		tlog.setOperType(OperType.WARRANTY_MGR);
		tlog.setMemo("后台管理员变更报修受理单状态为已修复");
		tlog.setPreData("");
		tlog.setData("管理员" + tuser.getTuserSub().getName()
						+ "变更报修受理单状态为已修复,受理单编号是" + twarranty.getIwarranty());
		this.tlogService.save(tlog);
	}
	public Twarranty queryById(Long id){
		if(id == null) return null;
		return this.twarrantyService.get(id);
	}
	public ERPMaintenance queryBySeqno(String sn){
		return this.maintenanceManageService.getBySn(sn);
	}

	/**
	 * 是否在3个月包换期内
	 * @param maintenance
	 * @return
	 */
	public Boolean inWarrantyPeriod(ERPMaintenance maintenance) {
		if (maintenance != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			Calendar now = Calendar.getInstance();
			try {
				int replacePeriod = 3;
				TsysParam param = this.tsysParamService.getTsysParam("OTHER_CONF", "REPLACE_PERIOD"); //产品包换周期(月)
				if(param != null && StringUtils.isNotBlank(param.getValue())) {
					try {
						replacePeriod = Integer.valueOf(param.getValue());
					} catch (Exception e) {
						logger.error("系统参数【产品包换周期(月)】转化异常", e);
					}
				}
				
				Date startDate = dateFormat.parse(maintenance.getFhDate());
				now.setTime(startDate);
				now.add(Calendar.MONTH, replacePeriod);
				if (DateUtils.truncate(new Date(), Calendar.DATE).compareTo(
						DateUtils.truncate(now.getTime(), Calendar.DATE)) <= 0) {
					return true;
				}
			} catch (Exception e) {
			}
		}
		return false;
	}
}
