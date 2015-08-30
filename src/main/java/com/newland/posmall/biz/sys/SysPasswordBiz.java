package com.newland.posmall.biz.sys;


import javax.annotation.Resource;

import org.ohuyo.rapid.base.TsysSession;
import org.ohuyo.rapid.base.entity.Tsys;
import org.ohuyo.rapid.base.service.PasswordService;
import org.ohuyo.rapid.base.service.TsysService;
import org.ohuyo.rapid.base.servlet.AppSessionFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class SysPasswordBiz {
	
	@Resource
	private TsysService tsysService;
	
	@Resource
	private PasswordService passwordService;
	
	public String passwordMod(String oldPassword,String newPassword) throws Exception{
		TsysSession tsysSession = (TsysSession) AppSessionFilter.getAppSession();
		Tsys sys = this.tsysService.get(tsysSession.getTsys().getIsys());
		
		String	enOldPass = passwordService.decrypt(oldPassword);
		
		if(enOldPass.equals(sys.getPassword())){
			sys.setPassword(passwordService.decrypt(newPassword));
			this.tsysService.update(sys);
		}else{
			return "failure";
		}
		return "ok";
	}
	
	
	
}
