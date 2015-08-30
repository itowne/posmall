package com.newland.posmall.biz.sys;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.Order;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.TsysSession;
import org.ohuyo.rapid.base.entity.Tuser;
import org.ohuyo.rapid.base.entity.TuserUserrole;
import org.ohuyo.rapid.base.entity.condition.TuserCondition;
import org.ohuyo.rapid.base.service.PasswordService;
import org.ohuyo.rapid.base.service.TuserService;
import org.ohuyo.rapid.base.service.TuserUserroleService;
import org.ohuyo.rapid.base.servlet.AppSessionFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newland.posmall.base.service.TlogService;
import com.newland.posmall.bean.backmanage.TuserSub;
import com.newland.posmall.bean.common.Tlog;
import com.newland.posmall.bean.dict.LogType;
import com.newland.posmall.bean.dict.OperType;

@Service
@Transactional
public class UserBiz {

	@Resource
	private TuserService tuserService;

	@Resource
	private TlogService tlogService;
	
	@Resource
	private TuserUserroleService tuserUserroleService;
	
	@Resource
	private PasswordService passwordService;
	
	public List<Tuser> queryAllTuser(TuserCondition tuserConfition,Page page) {
		tuserConfition.setDelFlag(Boolean.FALSE);
		tuserConfition.addOrders(Order.desc("iuser"));
		List<Tuser> tuserList = this.tuserService.queryTuser(tuserConfition,page);
		for(Tuser tuser:tuserList){
			TuserSub tuserSub = this.tuserService.getTuserSub(tuser.getIuser());
			tuser.setTuserSub(tuserSub);
		}
		return tuserList;
	}
	
	public Tuser queryTuserByIuser(Long iuser) {
		return this.tuserService.getTuser(iuser);
	}
	
	public void addTuser(Tuser tuser) {
		tuser.setPassword(this.passwordService.getDefaultDecryptPassword());
		this.tuserService.saveTuser(tuser);
		
		TuserSub tuserSub = new TuserSub();
		tuserSub.setIuser(tuser.getIuser());
		tuserSub.setName(tuser.getTuserSub().getName());
		tuserSub.setDepart(tuser.getTuserSub().getDepart());
		tuserSub.setEmail(tuser.getTuserSub().getEmail());
		this.tuserService.saveTuserSub(tuserSub);
		
		TsysSession tsysSession = (TsysSession) AppSessionFilter.getAppSession();
		
		Tlog tlog = new Tlog();
		tlog.setIfk(tsysSession.getTsys().getIsys());
		tlog.setLogType(LogType.SYS);
		tlog.setOperType(OperType.USER_MGR);
		tlog.setMemo("新增用户");
		tlog.setPreData("");
		StringBuffer sbDate = new StringBuffer();
		sbDate.append("新增用户:登录名是");
		sbDate.append(tuser.getLoginName());
		sbDate.append(",姓名是");
		sbDate.append(tuserSub.getName());
		sbDate.append(",部门是");
		sbDate.append(tuserSub.getDepart());
		sbDate.append(",email是");
		sbDate.append(tuserSub.getEmail());
		tlog.setData(sbDate.toString());
		this.tlogService.save(tlog);
		
	}
	
	public void modifyTuserByIuser(Tuser tuser) {
		Tuser newTuser = this.tuserService.getTuser(tuser.getIuser());
		this.tuserService.updateTuser(newTuser);
		
		TuserSub newTuserSub = this.tuserService.getTuserSub(tuser.getIuser());
		newTuserSub.setName(tuser.getTuserSub().getName());
		newTuserSub.setDepart(tuser.getTuserSub().getDepart());
		newTuserSub.setEmail(tuser.getTuserSub().getEmail());
		this.tuserService.updateTuserSub(newTuserSub);
		
		TsysSession tsysSession = (TsysSession) AppSessionFilter.getAppSession();
		Tlog tlog = new Tlog();
		tlog.setIfk(tsysSession.getTsys().getIsys());
		tlog.setLogType(LogType.SYS);
		tlog.setOperType(OperType.USER_MGR);
		tlog.setMemo("修改用户");
		StringBuffer sbPreDate = new StringBuffer();
		sbPreDate.append("登录名是");
		sbPreDate.append(tuser.getLoginName());
		sbPreDate.append(",姓名是");
		sbPreDate.append(tuser.getTuserSub().getName());
		sbPreDate.append(",部门是");
		sbPreDate.append(tuser.getTuserSub().getDepart());
		sbPreDate.append(",email是");
		sbPreDate.append(tuser.getTuserSub().getEmail());
		tlog.setPreData(sbPreDate.toString());
		StringBuffer sbDate = new StringBuffer();
		sbDate.append("登录名是");
		sbDate.append(newTuser.getLoginName());
		sbDate.append(",姓名是");
		sbDate.append(newTuserSub.getName());
		sbDate.append(",部门是");
		sbDate.append(newTuserSub.getDepart());
		sbDate.append(",email是");
		sbDate.append(newTuserSub.getEmail());
		tlog.setData(sbDate.toString());
		this.tlogService.save(tlog);
	}
	
	public String removeTuserByIuser(Long iuser) {
		
		List<TuserUserrole> glList = this.tuserUserroleService.findByIuser(iuser);
		if(glList!=null&&glList.size()>0){//关联信息不为空提示先删除关联
			return "failure";
		}
		
		Tuser tuser = this.tuserService.getTuser(iuser);
		
		this.tuserService.delete(tuser);
		
		TsysSession tsysSession = (TsysSession) AppSessionFilter.getAppSession();
		
		Tlog tlog = new Tlog();
		tlog.setIfk(tsysSession.getTsys().getIsys());
		tlog.setLogType(LogType.SYS);
		tlog.setOperType(OperType.USER_MGR);
		tlog.setMemo("删除用户");
		StringBuffer sbPreDate = new StringBuffer();
		sbPreDate.append("删除用户:登录名是");
		sbPreDate.append(tuser.getLoginName());
		sbPreDate.append(",姓名是");
		sbPreDate.append(tuser.getTuserSub().getName());
		sbPreDate.append(",部门是");
		sbPreDate.append(tuser.getTuserSub().getDepart());
		sbPreDate.append(",email是");
		sbPreDate.append(tuser.getTuserSub().getEmail());
		tlog.setPreData(sbPreDate.toString());
		tlog.setPreData(sbPreDate.toString());
		tlog.setData("");
		this.tlogService.save(tlog);
		
		return "ok";
	}
	public void resetPassByIuser(Long id) {
		Tuser newTuser = this.tuserService.getTuser(id);
		
		newTuser.setPassword(this.passwordService.getDefaultDecryptPassword());
		
		this.tuserService.updateTuser(newTuser);
		
		TsysSession tsysSession = (TsysSession) AppSessionFilter.getAppSession();
		Tlog tlog = new Tlog();
		tlog.setIfk(tsysSession.getTsys().getIsys());
		tlog.setLogType(LogType.SYS);
		tlog.setOperType(OperType.USER_MGR);
		tlog.setMemo("重置后台人员密码");
		StringBuffer sbPreDate = new StringBuffer();
		sbPreDate.append("修改前密码是原密码");
		tlog.setPreData(sbPreDate.toString());
		StringBuffer sbDate = new StringBuffer();
		sbDate.append("修改后密码是默认密码");
		tlog.setData(sbDate.toString());
		this.tlogService.save(tlog);
	}
}
