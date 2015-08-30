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
import org.ohuyo.rapid.base.entity.TcustCustrole;
import org.ohuyo.rapid.base.entity.Tcustrole;
import org.ohuyo.rapid.base.entity.Tcustauth;
import org.ohuyo.rapid.base.entity.condition.TcustroleCondition;
import org.ohuyo.rapid.base.service.TcustCustroleService;
import org.ohuyo.rapid.base.service.TcustroleService;
import org.ohuyo.rapid.base.service.TcustauthService;
import org.ohuyo.rapid.base.servlet.AppSessionFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newland.posmall.base.service.TlogService;
import com.newland.posmall.bean.common.Tlog;
import com.newland.posmall.bean.dict.LogType;
import com.newland.posmall.bean.dict.OperType;
@Service
@Transactional
public class CustroleBiz {

	@Resource
	private TcustroleService tcustroleService;
	
	@Resource
	private TcustCustroleService tcustCustroleService;
	
	@Resource
	private TcustauthService tcustauthService;
	
	@Resource
	private TlogService tlogService;
	
	/**
	 * 获取客户的 权限列表
	 * @param iuser
	 * @return
	 */
	public List<Tcustauth> queryCustAuthById(Long icust){
		
		Comparator<Tcustauth> tc = new Comparator<Tcustauth>() {
			@Override
			public int compare(Tcustauth o1, Tcustauth o2) {
				return (int) (o1.getIauth() - o2.getIauth());
			}
		};
		Set<Tcustauth> allowAuths = new TreeSet<Tcustauth>(tc);
		List<TcustCustrole> custCustroleList = tcustCustroleService.findByIcust(icust);
		if (custCustroleList != null && custCustroleList.size() > 0) {
			for (TcustCustrole tcustCustrole : custCustroleList) {
				// 通过角色id查找该角色拥有的权限
				Tcustrole tcustrole = this.tcustroleService.find(tcustCustrole
						.getIcustrole());
				Set<Tcustauth> authSet = tcustrole.getAuthSet();
				allowAuths.addAll(authSet);
			}
		}

		List<Tcustauth> ret = new ArrayList<Tcustauth>();
		ret.addAll(allowAuths);
		return ret;
	}
	
	
	public Tcustauth querySysauthByCode(String code){
		return tcustauthService.querySysauthByCode(code);
	}
	
	
	public List<Tcustauth> queryAllSysauth(){
		return  tcustauthService.findAll();
	}
	
	public MenuNode list() {
		List<Tcustauth> auths = tcustauthService.findAll();
		MenuNode mn = new MenuNode();
		add(mn.getSubMenus(), auths, null);
		return mn;

	}
	
	public MenuNode list(Long id) {
		List<Tcustauth> auths = this.queryCustAuthById(id);
		MenuNode mn = new MenuNode();
		add(mn.getSubMenus(), auths, null);
		return mn;

	}
	
	public void add(Map<Long, MenuNode> nodes, List<Tcustauth> auths, Long piinstAuth) {
		for (Tcustauth ia : auths) {
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
	
	public List<Tcustrole> queryAllTrole(TcustroleCondition troleCondition,Page page) {
		troleCondition.setDelFlag(Boolean.FALSE);
		troleCondition.addOrders(Order.desc("icustrole"));
		return this.tcustroleService.queryTrole(troleCondition, page);
	}
	
	public void AddRole(Tcustrole trole){
		this.tcustroleService.save(trole);
		
		TsysSession tsysSession = (TsysSession) AppSessionFilter.getAppSession();
		
		Tlog tlog = new Tlog();
		tlog.setIfk(tsysSession.getTsys().getIsys());
		tlog.setLogType(LogType.SYS);
		tlog.setOperType(OperType.CUST_MGR);
		tlog.setMemo("新增客户角色");
		tlog.setPreData("");
		StringBuffer sbDate = new StringBuffer();
		sbDate.append("新增客户角色:角色名是");
		sbDate.append(trole.getName());
		sbDate.append(",备注是");
		sbDate.append(trole.getMemo());
		tlog.setData(sbDate.toString());
		this.tlogService.save(tlog);
	}
	
	public String DelRoleByIrole(Long id)throws Exception{
		List<TcustCustrole> glList = this.tcustCustroleService.findByIrole(id);
		if(glList!=null&&glList.size()>0){//关联信息不为空提示先删除关联
			return "failure";
		}
		Tcustrole trole = this.tcustroleService.find(id);
		
		this.tcustroleService.delete(id);
		
		TsysSession tsysSession = (TsysSession) AppSessionFilter.getAppSession();
		Tlog tlog = new Tlog();
		tlog.setIfk(tsysSession.getTsys().getIsys());
		tlog.setLogType(LogType.SYS);
		tlog.setOperType(OperType.CUST_MGR);
		tlog.setMemo("删除客户角色");
		StringBuffer sbPreDate = new StringBuffer();
		sbPreDate.append("客户角色名是");
		sbPreDate.append(trole.getName());
		sbPreDate.append(",备注是");
		sbPreDate.append(trole.getMemo());
		tlog.setPreData(sbPreDate.toString());
		tlog.setData("");
		this.tlogService.save(tlog);
		return "ok";
	}
	
	public Tcustrole QueryRoleByIrole(Long id){
		return this.tcustroleService.find(id);
	}
	
	public void ModifyRole(Tcustrole trole)throws Exception{
		Tcustrole troleNew = tcustroleService.find(trole.getIcustrole());
		troleNew.setName(trole.getName());
		troleNew.setMemo(trole.getMemo());
		troleNew.setAuthSet(trole.getAuthSet());
		this.tcustroleService.update(troleNew);
		
		TsysSession tsysSession = (TsysSession) AppSessionFilter.getAppSession();
		Tlog tlog = new Tlog();
		tlog.setIfk(tsysSession.getTsys().getIsys());
		tlog.setLogType(LogType.SYS);
		tlog.setOperType(OperType.CUST_MGR);
		tlog.setMemo("修改客户角色");
		StringBuffer sbPreDate = new StringBuffer();
		sbPreDate.append("客户角色名是");
		sbPreDate.append(trole.getName());
		sbPreDate.append(",备注是");
		sbPreDate.append(trole.getMemo());
		tlog.setPreData(sbPreDate.toString());
		StringBuffer sbDate = new StringBuffer();
		sbDate.append("客户角色名是");
		sbDate.append(troleNew.getName());
		sbDate.append(",备注是");
		sbDate.append(troleNew.getMemo());
		tlog.setData(sbDate.toString());
		this.tlogService.save(tlog);
	}
}
