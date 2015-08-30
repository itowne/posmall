package com.newland.posmall.biz.admin;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Order;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.entity.Tuser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newland.posmall.base.exception.BizException;
import com.newland.posmall.base.service.TcustAppverService;
import com.newland.posmall.base.service.TcustRegService;
import com.newland.posmall.base.service.TlogService;
import com.newland.posmall.bean.common.Tlog;
import com.newland.posmall.bean.customer.TcustAppver;
import com.newland.posmall.bean.customer.TcustReg;
import com.newland.posmall.bean.customer.condition.TcustAppverCondition;
import com.newland.posmall.bean.dict.LogType;
import com.newland.posmall.bean.dict.OperType;



@Service
@Transactional
public class CustAppverBiz {
	
	private static final Logger logger = LoggerFactory.getLogger(CustAppverBiz.class);
	
	@Resource
	private TcustAppverService tcustAppverService;
	
	@Resource
	private TlogService tlogService;
	
	@Resource
	private TcustRegService tcustRegService;
	
	
	public List<TcustAppver> find(TcustAppver tcustAppver,Page page){
		
		TcustAppverCondition condition = new TcustAppverCondition();
		
		if(tcustAppver!=null){
			if(StringUtils.isNotBlank(tcustAppver.getCustNo())){
				condition.setCustNo(tcustAppver.getCustNo().trim());
			}
			if(StringUtils.isNotBlank(tcustAppver.getName())){
				condition.setName(tcustAppver.getName().trim());
			}
			if(StringUtils.isNotBlank(tcustAppver.getAppver())){
				condition.setAppver(tcustAppver.getAppver().trim());
			}
		}
		condition.addOrders(Order.desc("updTime"));
		return this.tcustAppverService.find(condition, page);
	}
	
	public void custAppverDel(Tuser tuser, Long icustAppver)throws BizException{
		TcustAppver tcustAppver = this.tcustAppverService.get(icustAppver);
		if(tcustAppver != null&&Boolean.FALSE.equals(tcustAppver.getDelFlag())){
			this.tcustAppverService.delete(tcustAppver);
		}else{
			logger.debug("无此条记录");
			throw new BizException(121, "无此条记录");
		}
		
		// 日志记录
		Tlog tlog = new Tlog();
		tlog.setIfk(tuser.getIuser());
		tlog.setLogType(LogType.USER);
		tlog.setOperType(OperType.CUSTAPPVER_MGR);
		tlog.setMemo("后台管理员删除应用版本号");
		tlog.setPreData("");
		tlog.setData("管理员" + tuser.getTuserSub().getName()
						+ "删除客户应用版本号,原应用版本内部编号是" + icustAppver);
		this.tlogService.save(tlog);
	}
	public TcustAppver updateTcustAppver(TcustAppver custApp, Tuser tuser)throws BizException {
		
		TcustAppver custAppOri = this.tcustAppverService.get(custApp.getIcustAppver());
		
		
		
		if(custAppOri==null){//找不到这条记录
			logger.debug("找不到此条记录");
			throw new BizException(121, "找不到此条记录");
		}
		
		TcustAppver appCon = new TcustAppver();
		appCon.setCustNo(custApp.getCustNo());
		appCon.setAppver(custApp.getAppver());
		TcustAppver tcustAppverOri = this.tcustAppverService.findObjectByCondition(appCon);
		if(tcustAppverOri!=null){
			logger.debug("该客户已添加此版本号");
			throw new BizException(121, "该客户已添加此版本号");
		}
		
		// 日志记录
		//记录更新前的日记
		Tlog tlog = new Tlog();
		tlog.setGenTime(new Date());
		tlog.setIfk(tuser.getIuser());
		tlog.setLogType(LogType.USER);
		tlog.setOperType(OperType.CUSTAPPVER_MGR);
		tlog.setMemo(tuser.getTuserSub().getName()+"修改客户应用版本内部编号:" + custApp.getIcustAppver());
		StringBuffer sb = new StringBuffer();
		sb.append("修改前 应用版本内部编号：").append(custAppOri.getIcustAppver())
		        .append(",客户编号:").append(custAppOri.getCustNo())
		        .append(",单位名称:").append(custAppOri.getName())
		        .append(",应用版本号:").append(custAppOri.getAppver());
		tlog.setPreData(sb.toString());
		
		//更新要更新的属性
		custAppOri.setAppver(custApp.getAppver());
		this.tcustAppverService.update(custAppOri);
        
		//记录更新后的日志
		StringBuffer sb2 = new StringBuffer();
		sb2.append("修改后   应用版本内部编号：").append(custAppOri.getIcustAppver())
		        .append(",客户编号:").append(custAppOri.getCustNo())
		        .append(",单位名称:").append(custAppOri.getName())
		        .append(",应用版本号:").append(custAppOri.getAppver());
		tlog.setData(sb2.toString());
		tlogService.save(tlog);
		
		return custAppOri;
	}
	public TcustAppver getCustAppverDetail(Long icustAppver){
		return this.tcustAppverService.get(icustAppver);
	}
	public void addCustAppver(Tuser tuser,TcustAppver tcustAppver)throws BizException{
		TcustReg custReg = this.tcustRegService.findByIcust(tcustAppver.getIcust());
		TcustAppver appCon = new TcustAppver();
		appCon.setCustNo(custReg.getCustNo());
		appCon.setAppver(tcustAppver.getAppver());
		TcustAppver tcustAppverOri = this.tcustAppverService.findObjectByCondition(appCon);
		
		if(tcustAppverOri!=null){
			logger.debug("这个客户已经添加此版本号");
			throw new BizException(121, "这个客户已经添加此版本号");
		}
		
		
		tcustAppver.setCustNo(custReg.getCustNo());
		tcustAppver.setName(custReg.getName());
		this.tcustAppverService.save(tcustAppver);
		
		Tlog tlog = new Tlog();
		tlog.setIfk(tuser.getIuser());
		tlog.setLogType(LogType.USER);
		tlog.setOperType(OperType.CUSTAPPVER_MGR);
		tlog.setMemo("后台人员新增客户应用版本");
		tlog.setPreData("");
		StringBuffer sbDate = new StringBuffer();
		sbDate.append("新增客户应用版本:客户编号是:").append(tcustAppver.getCustNo())
		         .append(",单位名称：").append(tcustAppver.getName())
		         .append(",应用版本号：").append(tcustAppver.getAppver());
		tlog.setData(sbDate.toString());
		this.tlogService.save(tlog);
	}

}
