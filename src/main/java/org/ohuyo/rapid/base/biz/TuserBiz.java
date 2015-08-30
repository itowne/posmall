package org.ohuyo.rapid.base.biz;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.ohuyo.rapid.base.User;
import org.ohuyo.rapid.base.entity.Tsys;
import org.ohuyo.rapid.base.entity.Tuser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newland.posmall.base.service.TlogService;
import com.newland.posmall.bean.common.Tlog;
import com.newland.posmall.bean.customer.Tcust;
import com.newland.posmall.bean.dict.LogType;
import com.newland.posmall.bean.dict.OperType;

/**
 * 
 * @author rabbit
 *
 */
@Service
@Transactional
public class TuserBiz {
	
	private static final Logger log = LoggerFactory.getLogger(TuserBiz.class);

	@Resource
	private TlogService tlogService;
	
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 用户登录记录日志
	 * @param user
	 */
	public void log4Login(User user) {
		Tlog tlog = new Tlog();
		tlog.setOperType(OperType.USER_LOGIN);
		tlog.setData("");
		tlog.setPreData("");
		if (user instanceof Tcust) {
			Tcust tcust = (Tcust) user;
			tlog.setIfk(tcust.getIcust());
			tlog.setLogType(LogType.CUST);
			tlog.setMemo("【用户登录】，用户：" + tcust.getLoginName() + "，登录时间：" + df.format(new Date()));
		} else if (user instanceof Tuser) {
			Tuser tuser = (Tuser) user;
			tlog.setIfk(tuser.getIuser());
			tlog.setLogType(LogType.USER);
			tlog.setMemo("【用户登录】，用户：" + tuser.getLoginName() + "，登录时间：" + df.format(new Date()));
		} else if (user instanceof Tsys) {
			Tsys tsys = (Tsys) user;
			tlog.setIfk(tsys.getIsys());
			tlog.setLogType(LogType.SYS);
			tlog.setMemo("【用户登录】，用户：" + tsys.getLoginName() + "，登录时间：" + df.format(new Date()));
		}
		log.debug(tlog.getMemo());
		tlogService.save(tlog);
	}
	
	/**
	 * 用户退出系统记录日志
	 * @param user
	 */
	public void log4Logout(User user) {
		Tlog tlog = new Tlog();
		tlog.setOperType(OperType.USER_LOGOUT);
		tlog.setData("");
		tlog.setPreData("");
		if (user instanceof Tcust) {
			Tcust tcust = (Tcust) user;
			tlog.setIfk(tcust.getIcust());
			tlog.setLogType(LogType.CUST);
			tlog.setMemo("【用户退出系统】，用户：" + tcust.getLoginName() + "，退出时间：" + df.format(new Date()));
		} else if (user instanceof Tuser) {
			Tuser tuser = (Tuser) user;
			tlog.setIfk(tuser.getIuser());
			tlog.setLogType(LogType.USER);
			tlog.setMemo("【用户退出系统】，用户：" + tuser.getLoginName() + "，退出时间：" + df.format(new Date()));
		} else if (user instanceof Tsys) {
			Tsys tsys = (Tsys) user;
			tlog.setIfk(tsys.getIsys());
			tlog.setLogType(LogType.SYS);
			tlog.setMemo("【用户退出系统】，用户：" + tsys.getLoginName() + "，退出时间：" + df.format(new Date()));
		}
		log.debug(tlog.getMemo());
		tlogService.save(tlog);
	}
}
