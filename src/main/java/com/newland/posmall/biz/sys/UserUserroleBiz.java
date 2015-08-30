package com.newland.posmall.biz.sys;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.TsysSession;
import org.ohuyo.rapid.base.entity.Tuser;
import org.ohuyo.rapid.base.entity.TuserUserrole;
import org.ohuyo.rapid.base.entity.Tuserrole;
import org.ohuyo.rapid.base.service.TuserroleService;
import org.ohuyo.rapid.base.service.TuserService;
import org.ohuyo.rapid.base.service.TuserUserroleService;
import org.ohuyo.rapid.base.servlet.AppSessionFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.newland.posmall.base.service.TlogService;
import com.newland.posmall.bean.common.Tlog;
import com.newland.posmall.bean.dict.LogType;
import com.newland.posmall.bean.dict.OperType;
@Service
@Transactional
public class UserUserroleBiz {

	@Resource
	private TuserUserroleService tuserUserroleService;
	
	@Resource
	private TuserroleService troleService;
	
	@Resource
	private TuserService tuserService;
	
	@Resource
	private TlogService tlogService;
	
	public Map<String, Object> queryUserAndRole() {
		Tuser user = new Tuser();
		
		List<Tuser> userList = this.tuserService.findSelect(user);
		
		Tuserrole role = new Tuserrole();
		role.setDelFlag(Boolean.FALSE);
		
		List<Tuserrole> roleList = this.troleService.findSelect(role);
		
		Map<String,Object> userRoleMap = new HashMap<String, Object>();
		userRoleMap.put("userList", userList);
		userRoleMap.put("roleList", roleList);
		return userRoleMap;
	}
	
	public String addUserRole(TuserUserrole tuserUserrole){
		TuserUserrole tuserUserroleNew = this.tuserUserroleService.findUserRole(tuserUserrole);
		
		if(tuserUserroleNew!=null){
			return "failure";
		}
		
		this.tuserUserroleService.save(tuserUserrole);
		
		Tuser user = new Tuser();
		user = this.tuserService.getTuser(tuserUserrole.getIuser());
		
		Tuserrole userrole = new Tuserrole();
		userrole= this.troleService.find(tuserUserrole.getIuserrole());
		
		TsysSession tsysSession = (TsysSession) AppSessionFilter.getAppSession();
		Tlog tlog = new Tlog();
		tlog.setIfk(tsysSession.getTsys().getIsys());
		tlog.setLogType(LogType.SYS);
		tlog.setOperType(OperType.USER_MGR);
		tlog.setMemo("新增后台用户与角色关联");
		tlog.setPreData("");
		StringBuffer sbDate = new StringBuffer();
		sbDate.append("新增后台用户与角色关联:后台用户登录名是'");
		sbDate.append(user.getLoginName()+"'");
		sbDate.append(",角色名是'");
		sbDate.append(userrole.getName()+"'");
		tlog.setData(sbDate.toString());
		this.tlogService.save(tlog);
		return "success";
	}
	
	/**
	 * 
	* @Description: 根据条件分页查询
	* @author chenwenjing    
	* @date 2014-8-29 上午9:58:17
	 */
	@SuppressWarnings("rawtypes")
	public List queryUserRole(Page page,String loginName,String name){
		return this.tuserUserroleService.findListByInfo(page, loginName, name);
	}
	
	public void delete(TuserUserrole tuserRole){
		this.tuserUserroleService.delete(tuserRole);
		
		Tuser user = new Tuser();
		user = this.tuserService.getTuser(tuserRole.getIuser());
		
		Tuserrole userrole = new Tuserrole();
		userrole= this.troleService.find(tuserRole.getIuserrole());
		
		TsysSession tsysSession = (TsysSession) AppSessionFilter.getAppSession();
		Tlog tlog = new Tlog();
		tlog.setIfk(tsysSession.getTsys().getIsys());
		tlog.setLogType(LogType.SYS);
		tlog.setOperType(OperType.USER_MGR);
		tlog.setMemo("删除后台用户与角色关联");
		StringBuffer sbPreDate = new StringBuffer();
		sbPreDate.append("后台用户登录名是'");
		sbPreDate.append(user.getLoginName()+"'");
		sbPreDate.append(",角色名是'");
		sbPreDate.append(userrole.getName()+"'");
		tlog.setPreData(sbPreDate.toString());
		tlog.setData("");
		this.tlogService.save(tlog);
	}
	/**
	 * 
	* @Description: 修改后台用户与角色的关联
	* @author chenwenjing    
	* @date 2014-8-28 下午9:05:04
	 */
	public String updUserRole(TuserUserrole tuserRole,Long preIuser,Long preIuserrole){
		
        if(preIuser.equals(tuserRole.getIuser())&&preIuserrole.equals(tuserRole.getIuserrole())){//若修改前后数据不变则直接返回更新成功
			
        	return "success";
        	
		}
        
		TuserUserrole tuserRoleNew = this.tuserUserroleService.findUserRole(tuserRole);
		
		if(tuserRoleNew!=null){
			return "failure";
		}
		
		this.tuserUserroleService.update(tuserRole,preIuser,preIuserrole);
		
		Tuser user = new Tuser();
		user = this.tuserService.getTuser(preIuser);
		Tuserrole userrole = new Tuserrole();
		userrole= this.troleService.find(preIuserrole);
		
		Tuser userNew = new Tuser();
		userNew = this.tuserService.getTuser(tuserRole.getIuser());
		Tuserrole userroleNew = new Tuserrole();
		userroleNew = this.troleService.find(tuserRole.getIuserrole());
		
		TsysSession tsysSession = (TsysSession) AppSessionFilter.getAppSession();
		Tlog tlog = new Tlog();
		tlog.setIfk(tsysSession.getTsys().getIsys());
		tlog.setLogType(LogType.SYS);
		tlog.setOperType(OperType.USER_MGR);
		tlog.setMemo("修改后台用户与角色关联");
		StringBuffer sbPreDate = new StringBuffer();
		sbPreDate.append("后台用户登录名是'");
		sbPreDate.append(user.getLoginName()+"'");
		sbPreDate.append(",角色名是'");
		sbPreDate.append(userrole.getName()+"'");
		tlog.setPreData(sbPreDate.toString());
		StringBuffer sbDate = new StringBuffer();
		sbDate.append("后台用户登录名是'");
		sbDate.append(userNew.getLoginName()+"'");
		sbDate.append(",角色名是'");
		sbDate.append(userroleNew.getName()+"'");
		tlog.setData(sbDate.toString());
		this.tlogService.save(tlog);
		return "success";
	}
	
}
