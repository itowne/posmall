package com.newland.posmall.biz.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.TsysSession;
import org.ohuyo.rapid.base.entity.Tsys;
import org.ohuyo.rapid.base.entity.TsysSysrole;
import org.ohuyo.rapid.base.entity.Tsysrole;
import org.ohuyo.rapid.base.service.TsysService;
import org.ohuyo.rapid.base.service.TsysSysroleService;
import org.ohuyo.rapid.base.service.TsysroleService;
import org.ohuyo.rapid.base.service.TuserService;
import org.ohuyo.rapid.base.servlet.AppSessionFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newland.posmall.base.service.TlogService;
import com.newland.posmall.bean.common.Tlog;
import com.newland.posmall.bean.dict.LogType;
import com.newland.posmall.bean.dict.OperType;

@Service
@Transactional(rollbackFor = Throwable.class)
public class SysSysroleBiz {

	@Resource
	private TsysSysroleService tsysSysroleService;

	@Resource
	private TsysService tsysService;

	@Resource
	private TsysroleService troleService;

	@Resource
	private TuserService tuserService;

	@Resource
	private TlogService tlogService;

	public Map<String, Object> queryUserAndRole() {
		Map<String, Object> userRoleMap = new HashMap<String, Object>();
		List<Tsys> sysList = this.tsysService.findAll();
		if(sysList == null || sysList.size() < 1) {
			return userRoleMap;
		}
		
		Tsysrole role = new Tsysrole();
		role.setDelFlag(Boolean.FALSE);
		List<Tsysrole> roleList = this.troleService.findSelect(role);

		userRoleMap.put("sysList", sysList);
		userRoleMap.put("roleList", roleList);
		return userRoleMap;
	}

	public String addUserRole(TsysSysrole tsysSysrole) {
		TsysSysrole tsysSysroleNew = this.tsysSysroleService
				.findSysRole(tsysSysrole);

		if (tsysSysroleNew != null) {
			return "failure";
		}

		this.tsysSysroleService.save(tsysSysrole);

		Tsys sys = new Tsys();
		// sys = this.tsysService.getTsys(tsysSysrole.getIsys());
		Tsysrole sysrole = new Tsysrole();
		sysrole = this.troleService.find(tsysSysrole.getIsysrole());

		TsysSession tsysSession = (TsysSession) AppSessionFilter
				.getAppSession();
		Tlog tlog = new Tlog();
		tlog.setIfk(tsysSession.getTsys().getIsys());
		tlog.setLogType(LogType.SYS);
		tlog.setOperType(OperType.SYS_MGR);
		tlog.setMemo("新增系统用户与角色关联");
		tlog.setPreData("");
		StringBuffer sbDate = new StringBuffer();
		sbDate.append("系统用户登录名是");
		sbDate.append(sys.getLoginName());
		sbDate.append(",角色名称是");
		sbDate.append(sysrole.getName());
		tlog.setData(sbDate.toString());
		this.tlogService.save(tlog);
		return "success";
	}

	@SuppressWarnings("rawtypes")
	public List queryUserRole(Page page, String loginName, String name) {
		return this.tsysSysroleService.findListByInfo(page, loginName, name);
	}

	public void delete(TsysSysrole tsysSysrole) {
		this.tsysSysroleService.delete(tsysSysrole);

		Tsys sys = new Tsys();
		// sys = this.tsysService.getTsys(tsysSysrole.getIsys());
		Tsysrole sysrole = new Tsysrole();
		sysrole = this.troleService.find(tsysSysrole.getIsysrole());

		TsysSession tsysSession = (TsysSession) AppSessionFilter
				.getAppSession();
		Tlog tlog = new Tlog();
		tlog.setIfk(tsysSession.getTsys().getIsys());
		tlog.setLogType(LogType.SYS);
		tlog.setOperType(OperType.SYS_MGR);
		tlog.setMemo("删除系统用户与角色关联");
		StringBuffer sbPreDate = new StringBuffer();
		sbPreDate.append("系统用户登录名是");
		sbPreDate.append(sys.getLoginName());
		sbPreDate.append(",角色名称是");
		sbPreDate.append(sysrole.getName());
		tlog.setPreData(sbPreDate.toString());
		tlog.setData("");
		this.tlogService.save(tlog);
	}

	/**
	 * 
	 * @Description: 修改系统用户与角色的关联
	 * @author chenwenjing
	 * @date 2014-8-28 下午9:05:04
	 */
	public String updSysRole(TsysSysrole tsyssysRole, Long preIsys,
			Long preIsysrole) {
		TsysSysrole tuserRoleNew = this.tsysSysroleService
				.findSysRole(tsyssysRole);

		if (tuserRoleNew != null) {
			return "failure";
		}

		this.tsysSysroleService.update(tsyssysRole, preIsys, preIsysrole);

		Tsys sys = new Tsys();
		// sys = this.tsysService.getTsys(tsyssysRole.getIsys());
		Tsysrole sysrole = new Tsysrole();
		sysrole = this.troleService.find(tsyssysRole.getIsysrole());

		TsysSession tsysSession = (TsysSession) AppSessionFilter
				.getAppSession();
		Tlog tlog = new Tlog();
		tlog.setIfk(tsysSession.getTsys().getIsys());
		tlog.setLogType(LogType.SYS);
		tlog.setOperType(OperType.SYS_MGR);
		tlog.setMemo("修改系统用户与角色关联");
		StringBuffer sbPreDate = new StringBuffer();
		sbPreDate.append("系统用户登录名是");
		sbPreDate.append(sys.getLoginName());
		sbPreDate.append(",角色名称是");
		sbPreDate.append(sysrole.getName());
		tlog.setPreData(sbPreDate.toString());
		StringBuffer sbDate = new StringBuffer();
		sbDate.append("系统用户登录名是");
		sbDate.append(sys.getLoginName());
		sbDate.append(",角色名称是");
		sbDate.append(sysrole.getName());
		tlog.setData(sbDate.toString());
		this.tlogService.save(tlog);
		return "success";
	}

}
