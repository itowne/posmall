package com.newland.posmall.biz.cust;


import javax.annotation.Resource;

import org.ohuyo.rapid.base.TcustSession;
import org.ohuyo.rapid.base.service.PasswordService;
import org.ohuyo.rapid.base.servlet.AppSessionFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newland.posmall.base.service.TcustService;
import com.newland.posmall.bean.customer.Tcust;

@Service
@Transactional
public class CustPasswordBiz {
	
	@Resource
	private TcustService tcustService;
	
	@Resource
	private PasswordService passwordService;
	
	public String passwordMod(String oldPassword,String newPassword)throws Exception{
		TcustSession tcustSession = (TcustSession)AppSessionFilter.getAppSession();
		
		Tcust cust = this.tcustService.find(tcustSession.getTcust().getIcust());
		
		String	enOldPass = passwordService.decrypt(oldPassword);
		
		if(enOldPass.equals(cust.getPassword())){
			cust.setPassword(passwordService.decrypt(newPassword));
			this.tcustService.modifyTcust(cust);
		}else{
			return "failure";
		}
		return "ok";
	}
	
	
	
}
