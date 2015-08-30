package com.newland.posmall.biz.admin;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.Order;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.entity.ERPMaintenance;
import org.ohuyo.rapid.base.entity.Tuser;
import org.ohuyo.rapid.base.service.MaintenanceManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newland.posmall.base.exception.BizException;
import com.newland.posmall.base.service.TcustService;
import com.newland.posmall.base.service.TlogService;
import com.newland.posmall.base.service.TrenewDetailService;
import com.newland.posmall.base.service.TrenewService;
import com.newland.posmall.bean.common.Tlog;
import com.newland.posmall.bean.customer.Trenew;
import com.newland.posmall.bean.customer.TrenewDetail;
import com.newland.posmall.bean.customer.condition.TrenewCondition;
import com.newland.posmall.bean.dict.LogType;
import com.newland.posmall.bean.dict.OperType;
import com.newland.posmall.bean.dict.PayStatus;
import com.newland.posmall.bean.dict.RenewStatus;



@Service
@Transactional
public class RenewBiz {
	
	private static final Logger logger = LoggerFactory.getLogger(RenewBiz.class);
	
	@Resource
	private TrenewService trenewService;
	
	@Resource
	private TlogService tlogService;
	
	@Resource
	private TcustService tcustService;
	
	@Resource
    private AdminMailService adminMailService;
	
	@Resource
	private TrenewDetailService trenewDetailService;
	
	@Resource
	private MaintenanceManageService maintenanceManageService;
	
	public List<Trenew> find(TrenewCondition condition, Page page){
		
		if (condition == null) {
			condition = new TrenewCondition();
		}
		condition.addOrders(Order.desc("irenew"));
		return this.trenewService.find(condition, page);
	}
	
	public void renewCancel(Tuser tuser, Long irenew)throws BizException{
		Trenew trenew = this.trenewService.findById(irenew);
		if(trenew != null){
			trenew.setRenewStatus(RenewStatus.REVOKED);
			this.trenewService.update(trenew);
		}else{
			logger.debug("无此续保申请");
			throw new BizException(121, "无此续保申请");
		}
		
		// 日志记录
		Tlog tlog = new Tlog();
		tlog.setIfk(tuser.getIuser());
		tlog.setLogType(LogType.USER);
		tlog.setOperType(OperType.RENEW_MGR);
		tlog.setMemo("后台管理员撤销续保申请");
		tlog.setPreData("");
		tlog.setData("管理员" + tuser.getTuserSub().getName()
						+ "撤销续保申请,原续保申请编号是" + irenew);
		this.tlogService.save(tlog);
	}
	public Trenew findById(Long irenew) {
		return this.trenewService.findById(irenew);
	}
	/**
	 * 
	* @Description: 审核续保申请
	* @author chenwenjing    
	* @date 2014-10-27 上午11:14:50
	 */
	public Trenew auditRenew(Trenew trenew, Tuser tuser) throws BizException {
		if (trenew.getRenewStatus() != RenewStatus.WAIT_AUDIT) {
			throw new BizException(10, "续保状态异常，不是待审核状态");
		}
		if (!(trenew.getPayStatus() == PayStatus.HAVE_PAY || trenew.getPayStatus() == PayStatus.PART_PAY)) {
			throw new BizException(10, "支付状态未通过，不能审核通过");
		}

		Tlog tlog = new Tlog();
		tlog.setGenTime(new Date());
		tlog.setIfk(tuser.getIuser());
		tlog.setLogType(LogType.USER);
		tlog.setOperType(OperType.RENEW_MGR);
		tlog.setMemo("用户审核续保申请编号:" + trenew.getIrenew());
		StringBuffer sb = new StringBuffer();
		sb.append("修改前 续保申请编号：").append(trenew.getIrenew()).append(",续保状态:")
				.append(trenew.getRenewStatus()).append(",支付状态:")
				.append(trenew.getPayStatus());
		tlog.setPreData(sb.toString());
		trenew.setRenewStatus(RenewStatus.AUDIT_PASS);
		trenew.setIuser(tuser.getIuser());
		trenew.setUserName(tuser.getTuserSub().getName());
		trenewService.update(trenew);
		
		List<TrenewDetail> detailList = this.trenewDetailService.findListByTrenewId(trenew.getIrenew());
		for(TrenewDetail detail:detailList){//更新主表
			ERPMaintenance em = this.maintenanceManageService.getERPMaintenanceById(detail.getIerpMaintenance());
			em.setLifeStartTime(detail.getLifeStartTime());
			em.setWarrantyPeriod(detail.getLifeEndTime());
			this.maintenanceManageService.updateERPMaintenance(em);
		}
		

		//添加邮件暂时不加adminMailService.sendOrdConfirmMail(cust, tord);

		StringBuffer sb2 = new StringBuffer();
		sb2.append("修改后  续保申请编号：").append(trenew.getIrenew()).append(",续保状态:")
				.append(trenew.getRenewStatus()).append(",支付状态:")
				.append(trenew.getPayStatus());
		tlog.setData(sb2.toString());
		tlogService.save(tlog);

		return trenew;

	}
	
	public List<TrenewDetail> queryTrenewDetailList(Long irenew) {
		return this.trenewDetailService.findListByTrenewId(irenew);
	}

}
