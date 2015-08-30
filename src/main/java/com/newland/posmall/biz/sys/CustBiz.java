package com.newland.posmall.biz.sys;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.Order;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.TsysSession;
import org.ohuyo.rapid.base.entity.condition.TcustCondition;
import org.ohuyo.rapid.base.service.PasswordService;
import org.ohuyo.rapid.base.servlet.AppSessionFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newland.posmall.base.service.TcustService;
import com.newland.posmall.base.service.TlogService;
import com.newland.posmall.bean.common.Tlog;
import com.newland.posmall.bean.customer.Tcust;
import com.newland.posmall.bean.customer.TcustReg;
import com.newland.posmall.bean.dict.LogType;
import com.newland.posmall.bean.dict.OperType;

@Service
@Transactional
public class CustBiz {

	@Resource
	private TcustService tcustService;

	@Resource
	private TlogService tlogService;
	
	@Resource
	private PasswordService passwordService;
	
	public List<Tcust> queryAllTcust(TcustCondition tcustConfition,Page page) {
		
		tcustConfition.addOrders(Order.desc("icust"));
		List<Tcust> tcustList = this.tcustService.queryTcust(tcustConfition,page);
		for(Tcust tcust:tcustList){
			TcustReg tcustReg = this.tcustService.getTcustReg(tcust.getIcust());
			tcust.setTcustReg(tcustReg);
		}
		return tcustList;
	}
	
	public Tcust queryTcustByIcust(Long icust) {
		return this.tcustService.find(icust);
	}
	
	/**
	 * 重置密码
	 * @param id
	 */
	public void resetPassByIcust(Long id) {
		
		Tcust newTcust = this.tcustService.find(id);
		newTcust.setPassword(this.passwordService.getDefaultDecryptPassword());
		this.tcustService.update(newTcust);
		
		TsysSession tsysSession = (TsysSession) AppSessionFilter.getAppSession();
		Tlog tlog = new Tlog();
		tlog.setIfk(tsysSession.getTsys().getIsys());
		tlog.setLogType(LogType.SYS);
		tlog.setOperType(OperType.CUST_MGR);
		tlog.setMemo("重置客户密码");
		StringBuffer sbPreDate = new StringBuffer();
		sbPreDate.append("修改前密码是原密码");
		tlog.setPreData(sbPreDate.toString());
		StringBuffer sbDate = new StringBuffer();
		sbDate.append("修改后密码是默认密码");
		tlog.setData(sbDate.toString());
		this.tlogService.save(tlog);
	}
}
