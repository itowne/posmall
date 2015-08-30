package com.newland.posmall.biz.sys;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Order;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.TsysSession;
import org.ohuyo.rapid.base.entity.Tsys;
import org.ohuyo.rapid.base.entity.TsysSysrole;
import org.ohuyo.rapid.base.entity.condition.TsysCondition;
import org.ohuyo.rapid.base.service.PasswordService;
import org.ohuyo.rapid.base.service.TsysService;
import org.ohuyo.rapid.base.service.TsysSysroleService;
import org.ohuyo.rapid.base.servlet.AppSessionFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newland.posmall.base.exception.BizException;
import com.newland.posmall.base.service.TlogService;
import com.newland.posmall.bean.common.Tlog;
import com.newland.posmall.bean.dict.LogType;
import com.newland.posmall.bean.dict.OperType;

@Service
@Transactional(rollbackFor = Throwable.class)
public class SysBiz {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@Resource
	private TsysService tsysService;

	@Resource
	private TlogService tlogService;

	@Resource
	private TsysSysroleService tsysSysroleService;

	@Resource
	private PasswordService passwordService;

	public List<Tsys> queryAllTsys(TsysCondition tsysConfition, Page page) {
		tsysConfition.addOrders(Order.desc("isys"));
		List<Tsys> tsysList = this.tsysService.queryTsys(tsysConfition, page);
		return tsysList;
	}

	public Tsys queryTsysByIsys(Long isys) {
		return this.tsysService.get(isys);
	}

	/**
	 * 新增
	 * @param loginName
	 * @param tsys
	 * @return
	 * @throws BizException
	 */
	public Tsys addTsys(String loginName, Tsys tsys) throws BizException {
		if (StringUtils.isBlank(loginName)) {
			throw new BizException(0, "登录名为空！");
		}
		Tsys newTsys = new Tsys();
		newTsys.setLoginName(loginName);

		List<Tsys> tmp = this.tsysService.findSelect(newTsys);
		if(tmp != null && tmp.size() >= 1) {
			throw new BizException(0, "登录名已存在！");
		}
		
		newTsys.setPassword(this.passwordService.getDefaultDecryptPassword());
		this.tsysService.save(newTsys);

		Tlog tlog = new Tlog();
		tlog.setIfk(tsys.getIsys());
		tlog.setLogType(LogType.SYS);
		tlog.setOperType(OperType.SYS_MGR);
		tlog.setMemo("【新增管理员信息】");
		tlog.setPreData("");
		StringBuffer sbDate = new StringBuffer();
		sbDate.append("登录名: ");
		sbDate.append(newTsys.getLoginName());
		sbDate.append("新增时间为:");
		sbDate.append(sdf.format(newTsys.getGenTime()));
		sbDate.append("修改时间为:");
		sbDate.append(sdf.format(newTsys.getUpdTime()));
		tlog.setData(sbDate.toString());
		this.tlogService.save(tlog);

		return newTsys;
	}

	public void modifyTsysByIsys(Tsys tsys) {

		Tsys newTsys = this.tsysService.getTsys(tsys.getIsys());
		newTsys.setLoginName(tsys.getLoginName());
		newTsys.setPassword(tsys.getPassword());
		this.tsysService.update(newTsys);

		TsysSession tsysSession = (TsysSession) AppSessionFilter
				.getAppSession();
		Tlog tlog = new Tlog();
		tlog.setIfk(tsysSession.getTsys().getIsys());
		tlog.setLogType(LogType.SYS);
		tlog.setOperType(OperType.SYS_MGR);
		tlog.setMemo("修改管理员信息");
		StringBuffer sbPreDate = new StringBuffer();
		sbPreDate.append("登录名是 ");
		sbPreDate.append(tsys.getLoginName());
		sbPreDate.append(",创建时间是");
		sbPreDate.append(tsys.getGenTime());
		sbPreDate.append(",更新时间是");
		sbPreDate.append(tsys.getUpdTime());
		tlog.setPreData(sbPreDate.toString());
		StringBuffer sbDate = new StringBuffer();
		sbDate.append("登录名是 ");
		sbDate.append(newTsys.getLoginName());
		sbDate.append(",创建时间是");
		sbDate.append(newTsys.getGenTime());
		sbDate.append(",更新时间是");
		sbDate.append(newTsys.getUpdTime());
		tlog.setData(sbDate.toString());
		this.tlogService.save(tlog);
	}

	/**
	 * 删除
	 * @param isys
	 * @param operator
	 * @throws BizException
	 */
	public Tsys removeTsysByIsys(Long isys, Tsys operator) throws BizException {
		if (isys == null) {
			throw new BizException(0, "主键获取失败");
		}
		List<TsysSysrole> glList = this.tsysSysroleService.findByIsys(isys);
		if (glList != null && glList.size() > 0) {
			throw new BizException(0, "请先删除该用户的关联角色！");
		}

		Tsys tsys = this.tsysService.getTsys(isys);
		this.tsysService.delete(tsys);

		Tlog tlog = new Tlog();
		tlog.setIfk(operator.getIsys());
		tlog.setLogType(LogType.SYS);
		tlog.setOperType(OperType.SYS_MGR);
		tlog.setMemo("删除管理员信息");
		StringBuffer sbPreDate = new StringBuffer();
		sbPreDate.append("登录名是 ");
		sbPreDate.append(tsys.getLoginName());
		sbPreDate.append(",创建时间是");
		sbPreDate.append(sdf.format(tsys.getGenTime()));
		sbPreDate.append(",更新时间是");
		sbPreDate.append(sdf.format(tsys.getUpdTime()));
		tlog.setPreData(sbPreDate.toString());
		tlog.setData("");
		this.tlogService.save(tlog);
		
		return tsys;
	}

	/**
	 * 密码重置
	 * @param isys
	 * @param operator
	 * @throws BizException
	 */
	public void resetPassByIsys(Long isys, Tsys operator) throws BizException{
		if(isys == null) {
			throw new BizException(0, "主键获取失败");
		}
		Tsys newTsys = this.tsysService.get(isys);
		newTsys.setPassword(this.passwordService.getDefaultDecryptPassword());
		this.tsysService.update(newTsys);

		Tlog tlog = new Tlog();
		tlog.setIfk(operator.getIsys());
		tlog.setLogType(LogType.SYS);
		tlog.setOperType(OperType.SYS_MGR);
		tlog.setMemo("重置系统用户密码");
		tlog.setPreData("");
		tlog.setData("充值密码：123456");
		this.tlogService.save(tlog);
	}

}