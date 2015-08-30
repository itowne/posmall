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
import org.ohuyo.rapid.base.entity.TuserUserrole;
import org.ohuyo.rapid.base.entity.Tuserauth;
import org.ohuyo.rapid.base.entity.Tuserrole;
import org.ohuyo.rapid.base.entity.condition.TuserroleCondition;
import org.ohuyo.rapid.base.service.TuserUserroleService;
import org.ohuyo.rapid.base.service.TuserauthService;
import org.ohuyo.rapid.base.service.TuserroleService;
import org.ohuyo.rapid.base.servlet.AppSessionFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newland.posmall.base.service.TlogService;
import com.newland.posmall.bean.common.Tlog;
import com.newland.posmall.bean.dict.LogType;
import com.newland.posmall.bean.dict.OperType;

@Service
@Transactional
public class UserroleBiz {

	@Resource
	private TuserroleService troleService;

	@Resource
	private TuserauthService tuserauthService;

	@Resource
	private TuserroleService tuserroleService;

	@Resource
	private TuserUserroleService tuseruserroleService;

	@Resource
	private TlogService tlogService;

	public Tuserauth querySysauthByCode(String code) {
		return tuserauthService.querySysauthByCode(code);
	}

	public List<Tuserauth> queryAllSysauth() {
		return tuserauthService.findAll();
	}

	public MenuNode list() {
		List<Tuserauth> auths = tuserauthService.findAll();
		MenuNode mn = new MenuNode();
		add(mn.getSubMenus(), auths, null);
		return mn;

	}

	/**
	 * 获取 管理人员的 权限列表
	 * 
	 * @param iuser
	 * @return
	 */
	public List<Tuserauth> queryUserAuthById(Long iuser) {
		Comparator<Tuserauth> tc = new Comparator<Tuserauth>() {
			@Override
			public int compare(Tuserauth o1, Tuserauth o2) {
				return (int) (o1.getIauth() - o2.getIauth());
			}
		};
		Set<Tuserauth> allowAuths = new TreeSet<Tuserauth>(tc);
		List<TuserUserrole> userUserroleList = tuseruserroleService
				.findByIuser(iuser);
		if (userUserroleList != null && userUserroleList.size() > 0) {
			for (TuserUserrole tuserUserrole : userUserroleList) {
				// 通过角色id查找该角色拥有的权限
				Tuserrole tuserrole = this.tuserroleService.find(tuserUserrole
						.getIuserrole());
				Set<Tuserauth> authSet = tuserrole.getAuthSet();
				allowAuths.addAll(authSet);
			}
		}

		List<Tuserauth> ret = new ArrayList<Tuserauth>();
		ret.addAll(allowAuths);
		return ret;

	}

	public MenuNode list(Long id) {
		List<Tuserauth> auths = this.queryUserAuthById(id);
		MenuNode mn = new MenuNode();
		add(mn.getSubMenus(), auths, null);
		return mn;

	}

	public void add(Map<Long, MenuNode> nodes, List<Tuserauth> auths,
			Long piinstAuth) {
		for (Tuserauth ia : auths) {
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

	public List<Tuserrole> queryAllTrole(TuserroleCondition troleCondition,
			Page page) {
		troleCondition.setDelFlag(Boolean.FALSE);
		troleCondition.addOrders(Order.desc("iuserrole"));
		return this.troleService.queryTrole(troleCondition, page);
	}

	public void AddRole(Tuserrole trole) {
		this.troleService.save(trole);

		TsysSession tsysSession = (TsysSession) AppSessionFilter
				.getAppSession();
		Tlog tlog = new Tlog();
		tlog.setIfk(tsysSession.getTsys().getIsys());
		tlog.setLogType(LogType.SYS);
		tlog.setOperType(OperType.CUST_MGR);
		tlog.setMemo("新增后台角色");
		tlog.setPreData("");
		StringBuffer sbDate = new StringBuffer();
		sbDate.append("新增后台角色:角色名是");
		sbDate.append(trole.getName());
		sbDate.append(",备注是");
		sbDate.append(trole.getMemo());
		tlog.setData(sbDate.toString());
		this.tlogService.save(tlog);
	}

	public String DelRoleByIrole(Long id) throws Exception {
		List<TuserUserrole> glList = this.tuseruserroleService.findByIrole(id);
		if(glList!=null&&glList.size()>0){//关联信息不为空提示先删除关联
			return "failure";
		}
		Tuserrole trole = this.troleService.find(id);

		this.troleService.delete(id);

		TsysSession tsysSession = (TsysSession) AppSessionFilter
				.getAppSession();
		Tlog tlog = new Tlog();
		tlog.setIfk(tsysSession.getTsys().getIsys());
		tlog.setLogType(LogType.SYS);
		tlog.setOperType(OperType.CUST_MGR);
		tlog.setMemo("删除后台角色");
		StringBuffer sbPreDate = new StringBuffer();
		sbPreDate.append("后台角色名是");
		sbPreDate.append(trole.getName());
		sbPreDate.append(",备注是");
		sbPreDate.append(trole.getMemo());
		tlog.setPreData(sbPreDate.toString());
		tlog.setData("");
		this.tlogService.save(tlog);
		
		return "ok";
	}

	public Tuserrole QueryRoleByIrole(Long id) {
		return this.troleService.find(id);
	}

	public void ModifyRole(Tuserrole trole) throws Exception {
		Tuserrole troleNew = troleService.find(trole.getIuserrole());
		troleNew.setName(trole.getName());
		troleNew.setMemo(trole.getMemo());
		troleNew.setAuthSet(trole.getAuthSet());
		this.troleService.update(troleNew);

		TsysSession tsysSession = (TsysSession) AppSessionFilter
				.getAppSession();
		Tlog tlog = new Tlog();
		tlog.setIfk(tsysSession.getTsys().getIsys());
		tlog.setLogType(LogType.SYS);
		tlog.setOperType(OperType.CUST_MGR);
		tlog.setMemo("修改后台角色");
		StringBuffer sbPreDate = new StringBuffer();
		sbPreDate.append("后台角色名是");
		sbPreDate.append(trole.getName());
		sbPreDate.append(",备注是");
		sbPreDate.append(trole.getMemo());
		tlog.setPreData(sbPreDate.toString());
		StringBuffer sbDate = new StringBuffer();
		sbDate.append("后台角色名是");
		sbDate.append(troleNew.getName());
		sbDate.append(",备注是");
		sbDate.append(troleNew.getMemo());
		tlog.setData(sbDate.toString());
		this.tlogService.save(tlog);
	}
}
