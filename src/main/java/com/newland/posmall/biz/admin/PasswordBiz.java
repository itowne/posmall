package com.newland.posmall.biz.admin;


import javax.annotation.Resource;

import org.ohuyo.rapid.base.TuserSession;
import org.ohuyo.rapid.base.entity.Tuser;
import org.ohuyo.rapid.base.service.PasswordService;
import org.ohuyo.rapid.base.service.TuserService;
import org.ohuyo.rapid.base.servlet.AppSessionFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PasswordBiz {
	
	@Resource
	private TuserService tuserService;
	
	@Resource
	private PasswordService passwordService;
	
	public String passwordMod(String oldPassword,String newPassword) throws Exception{
		TuserSession tuserSession = (TuserSession) AppSessionFilter.getAppSession();
		
		Tuser user = this.tuserService.getTuser(tuserSession.getTuser().getIuser());
		
		String	enOldPass = passwordService.decrypt(oldPassword);
		
		if(enOldPass.equals(user.getPassword())){
			user.setPassword(passwordService.decrypt(newPassword));
			this.tuserService.updateTuser(user);
		}else{
			return "failure";
		}
		return "ok";
	}
	
	
	
}
