package com.newland.posmall.biz.sys;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;



import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.TsysSession;
import org.ohuyo.rapid.base.entity.TcustCustrole;
import org.ohuyo.rapid.base.entity.Tcustrole;
import org.ohuyo.rapid.base.service.TcustCustroleService;
import org.ohuyo.rapid.base.service.TcustroleService;
import org.ohuyo.rapid.base.servlet.AppSessionFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newland.posmall.base.service.TcustService;
import com.newland.posmall.base.service.TlogService;
import com.newland.posmall.bean.common.Tlog;
import com.newland.posmall.bean.customer.Tcust;
import com.newland.posmall.bean.dict.LogType;
import com.newland.posmall.bean.dict.OperType;
@Service
@Transactional
public class CustCustroleBiz {

	@Resource
	private TcustCustroleService tcustCustroleService;
	
	@Resource
	private TcustroleService troleService;
	
	@Resource
	private TcustService tcustService;
	
	@Resource
	private TlogService tlogService;
	
	public Map<String, Object> queryUserAndRole() {
		Tcust cust = new Tcust();
		
		List<Tcust> custList = this.tcustService.findBySelect(cust);
		
		Tcustrole role = new Tcustrole();
		role.setDelFlag(Boolean.FALSE);
		
		List<Tcustrole> roleList = this.troleService.findSelect(role);
		
		Map<String,Object> custRoleMap = new HashMap<String, Object>();
		custRoleMap.put("custList", custList);
		custRoleMap.put("roleList", roleList);
		return custRoleMap;
	}
	
	public String addCustRole(TcustCustrole tcustCustrole){
		TcustCustrole tcustCustroleNew = this.tcustCustroleService.findCustRole(tcustCustrole);
		
		if(tcustCustroleNew!=null){
			return "failure";
		}
		
		
		this.tcustCustroleService.save(tcustCustrole);
		
		Tcust cust = new Tcust();
		cust = this.tcustService.find(tcustCustrole.getIcust());
		
		Tcustrole custrole = new Tcustrole();
		custrole= this.troleService.find(tcustCustrole.getIcustrole());
		
		TsysSession tsysSession = (TsysSession) AppSessionFilter.getAppSession();
		Tlog tlog = new Tlog();
		tlog.setIfk(tsysSession.getTsys().getIsys());
		tlog.setLogType(LogType.SYS);
		tlog.setOperType(OperType.CUST_MGR);
		tlog.setMemo("新增客户与角色关联");
		tlog.setPreData("");
		StringBuffer sbDate = new StringBuffer();
		sbDate.append("新增客户与角色关联:客户登录名是'");
		sbDate.append(cust.getLoginName()+"'");
		sbDate.append(",角色名是'");
		sbDate.append(custrole.getName()+"'");
		tlog.setData(sbDate.toString());
		this.tlogService.save(tlog);
		return "success";
	}
	
	@SuppressWarnings("rawtypes")
	public List queryCustRole(Page page,String loginName,String name){
		return this.tcustCustroleService.findListByInfo(page, loginName, name);
	}
	
	public void delete(TcustCustrole tcustRole){
		
		this.tcustCustroleService.delete(tcustRole);
		
		Tcust cust = new Tcust();
		cust = this.tcustService.find(tcustRole.getIcust());
		Tcustrole custrole = new Tcustrole();
		custrole= this.troleService.find(tcustRole.getIcustrole());
		
		TsysSession tsysSession = (TsysSession) AppSessionFilter.getAppSession();
		Tlog tlog = new Tlog();
		tlog.setIfk(tsysSession.getTsys().getIsys());
		tlog.setLogType(LogType.SYS);
		tlog.setOperType(OperType.CUST_MGR);
		tlog.setMemo("删除客户与角色关联");
		StringBuffer sbPreDate = new StringBuffer();
		sbPreDate.append("客户登录名是'");
		sbPreDate.append(cust.getLoginName()+"'");
		sbPreDate.append(",角色名是'");
		sbPreDate.append(custrole.getName()+"'");
		tlog.setPreData(sbPreDate.toString());
		tlog.setData("");
		this.tlogService.save(tlog);
		
		
	}
	/**
	 * 
	* @Description: 修改客户与角色的关联
	* @author chenwenjing    
	* @date 2014-8-28 下午9:05:04
	 */
	public String updCustRole(TcustCustrole tcustCustrole,Long preIcust,Long preIcustrole){
		
        if(preIcust.equals(tcustCustrole.getIcust())&&preIcustrole.equals(tcustCustrole.getIcustrole())){//若修改前后数据不变则直接返回更新成功
			
        	return "success";
        	
		}
        
		TcustCustrole tcustCustroleNew = this.tcustCustroleService.findCustRole(tcustCustrole);
		
		if(tcustCustroleNew!=null){
			
			return "failure";
			
		}
		
		this.tcustCustroleService.update(tcustCustrole,preIcust,preIcustrole);
		
		Tcust cust = new Tcust();
		cust = this.tcustService.find(preIcust);
		Tcustrole custrole = new Tcustrole();
		custrole= this.troleService.find(preIcustrole);
		
		Tcust custNew = new Tcust();
		custNew = this.tcustService.find(tcustCustrole.getIcust());
		Tcustrole custroleNew = new Tcustrole();
		custroleNew = this.troleService.find(tcustCustrole.getIcustrole());
		
		TsysSession tsysSession = (TsysSession) AppSessionFilter.getAppSession();
		Tlog tlog = new Tlog();
		tlog.setIfk(tsysSession.getTsys().getIsys());
		tlog.setLogType(LogType.SYS);
		tlog.setOperType(OperType.CUST_MGR);
		tlog.setMemo("修改客户与角色关联");
		StringBuffer sbPreDate = new StringBuffer();
		sbPreDate.append("客户登录名是'");
		sbPreDate.append(cust.getLoginName()+"'");
		sbPreDate.append(",角色名是'");
		sbPreDate.append(custrole.getName()+"'");
		tlog.setPreData(sbPreDate.toString());
		StringBuffer sbDate = new StringBuffer();
		sbDate.append("客户登陆名是'");
		sbDate.append(custNew.getLoginName()+"'");
		sbDate.append(",角色名是 '");
		sbDate.append(custroleNew.getName()+"'");
		tlog.setData(sbDate.toString());
		this.tlogService.save(tlog);
		return "success";
	}
	
}
