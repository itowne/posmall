package com.newland.posmall.biz.sys;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.apache.commons.lang.ObjectUtils;
import org.hibernate.criterion.Order;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.MenuNode;
import org.ohuyo.rapid.base.TsysSession;
import org.ohuyo.rapid.base.entity.TsysSysrole;
import org.ohuyo.rapid.base.entity.Tsysauth;
import org.ohuyo.rapid.base.entity.Tsysrole;
import org.ohuyo.rapid.base.entity.condition.TsysroleCondition;
import org.ohuyo.rapid.base.service.TsysSysroleService;
import org.ohuyo.rapid.base.service.TsysauthService;
import org.ohuyo.rapid.base.service.TsysroleService;
import org.ohuyo.rapid.base.servlet.AppSessionFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newland.posmall.base.service.TlogService;
import com.newland.posmall.bean.common.Tlog;
import com.newland.posmall.bean.dict.LogType;
import com.newland.posmall.bean.dict.OperType;
@Service
@Transactional
public class SysroleBiz {

	@Resource
	private TsysroleService troleService;
	
	@Resource
	private TsysauthService tsysauthService;
	
	@Resource
	private TlogService tlogService;
	
	@Resource
	private TsysSysroleService tsysSysroleService;
	
	@Resource
	private TsysroleService tsysroleService;
	
	
	/**
	 * 获取系统管理人员的 权限列表
	 * @param isys
	 * @return
	 */
	public List<Tsysauth> querySysAuthById(Long isys){
		Comparator<Tsysauth> tc = new Comparator<Tsysauth>() {
			@Override
			public int compare(Tsysauth o1, Tsysauth o2) {
				return (int) (o1.getIauth() - o2.getIauth());
			}
		};
		Set<Tsysauth> allowAuths = new TreeSet<Tsysauth>(tc);
		List<TsysSysrole> sysSysroleList = tsysSysroleService
				.findByIsys(isys);
		if (sysSysroleList != null && sysSysroleList.size() > 0) {
			for (TsysSysrole tsysSysrole : sysSysroleList) {
				// 通过角色id查找该角色拥有的权限
				Tsysrole tsysrole = this.tsysroleService.find(tsysSysrole
						.getIsysrole());
				Set<Tsysauth> authSet = tsysrole.getAuthSet();
				allowAuths.addAll(authSet);
			}
		}

		List<Tsysauth> ret = new ArrayList<Tsysauth>();
		ret.addAll(allowAuths);
		return ret;
	}
	
	public MenuNode list(Long id) {
		List<Tsysauth> auths = this.querySysAuthById(id);
		MenuNode mn = new MenuNode();
		add(mn.getSubMenus(), auths, null);
		return mn;

	}
	
	public Tsysauth querySysauthByCode(String code){
		return tsysauthService.querySysauthByCode(code);
	}
	
	
	public List<Tsysauth> queryAllSysauth(){
		return  tsysauthService.findAll();
	}
	
	public MenuNode list() {
		List<Tsysauth> auths = tsysauthService.findAll();
		MenuNode mn = new MenuNode();
		add(mn.getSubMenus(), auths, null);
		return mn;

	}

	public void add(Map<Long, MenuNode> nodes, List<Tsysauth> auths, Long piinstAuth) {
		for (Tsysauth ia : auths) {
			if (!ObjectUtils.equals(ia.getParentIauth(), piinstAuth)) {
				continue;
			}
			Long iia = ia.getIauth();
			if (!nodes.containsKey(iia)) {
				MenuNode n = new MenuNode();
				n.setAuth(ia);
				Map<Long, MenuNode> subMenus = n.getSubMenus();
				nodes.put(iia, n);
				add(subMenus, auths, iia);
			}
		}
	}
	
	
	
	public List<Tsysrole> queryAllTrole(TsysroleCondition troleCondition,Page page) {
		troleCondition.setDelFlag(Boolean.FALSE);
		troleCondition.addOrders(Order.desc("isysrole"));
		return this.troleService.queryTrole(troleCondition, page);
	}
	
	public void AddRole(Tsysrole trole){
		this.troleService.save(trole);
		
		TsysSession tsysSession = (TsysSession) AppSessionFilter.getAppSession();
		Tlog tlog = new Tlog();
		tlog.setIfk(tsysSession.getTsys().getIsys());
		tlog.setLogType(LogType.SYS);
		tlog.setOperType(OperType.SYS_MGR);
		tlog.setMemo("新增系统角色");
		tlog.setPreData("");
		StringBuffer sbDate = new StringBuffer();
		sbDate.append("新增系统角色:角色名是");
		sbDate.append(trole.getName());
		sbDate.append(",备注是");
		sbDate.append(trole.getMemo());
		tlog.setData(sbDate.toString());
		this.tlogService.save(tlog);
	}
	
	public String DelRoleByIrole(Long id)throws Exception{
		
		List<TsysSysrole> glList = this.tsysSysroleService.findByIrole(id);
		
		if(glList!=null&&glList.size()>0){//关联信息不为空提示先删除关联
			return "failure";
		}
		
		Tsysrole trole = this.troleService.find(id);
	
		this.troleService.delete(id);
		
		TsysSession tsysSession = (TsysSession) AppSessionFilter.getAppSession();
		Tlog tlog = new Tlog();
		tlog.setIfk(tsysSession.getTsys().getIsys());
		tlog.setLogType(LogType.SYS);
		tlog.setOperType(OperType.SYS_MGR);
		tlog.setMemo("删除系统角色");
		StringBuffer sbPreDate = new StringBuffer();
		sbPreDate.append("系统角色名是");
		sbPreDate.append(trole.getName());
		sbPreDate.append(",备注是");
		sbPreDate.append(trole.getMemo());
		tlog.setPreData(sbPreDate.toString());
		tlog.setData("");
		this.tlogService.save(tlog);
		
		return "ok";
	}
	
	public Tsysrole QueryRoleByIrole(Long id){
		return this.troleService.find(id);
	}
	
	public void ModifyRole(Tsysrole trole)throws Exception{
		Tsysrole troleNew = troleService.find(trole.getIsysrole());
		troleNew.setName(trole.getName());
		troleNew.setMemo(trole.getMemo());
		troleNew.setAuthSet(trole.getAuthSet());
		this.troleService.update(troleNew);
		
		TsysSession tsysSession = (TsysSession) AppSessionFilter.getAppSession();
		Tlog tlog = new Tlog();
		tlog.setIfk(tsysSession.getTsys().getIsys());
		tlog.setLogType(LogType.SYS);
		tlog.setOperType(OperType.SYS_MGR);
		tlog.setMemo("修改系统角色");
		StringBuffer sbPreDate = new StringBuffer();
		sbPreDate.append("系统角色名是");
		sbPreDate.append(trole.getName());
		sbPreDate.append(",备注是");
		sbPreDate.append(trole.getMemo());
		tlog.setPreData(sbPreDate.toString());
		StringBuffer sbDate = new StringBuffer();
		sbDate.append("系统角色名是");
		sbDate.append(troleNew.getName());
		sbDate.append(",备注是");
		sbDate.append(troleNew.getMemo());
		tlog.setData(sbDate.toString());
		this.tlogService.save(tlog);
	}
}
