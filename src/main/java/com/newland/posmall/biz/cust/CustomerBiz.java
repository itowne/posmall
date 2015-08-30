package com.newland.posmall.biz.cust;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.lang.StringUtils;
import org.ohuyo.rapid.base.entity.TcustCustrole;
import org.ohuyo.rapid.base.entity.Tcustrole;
import org.ohuyo.rapid.base.service.PasswordService;
import org.ohuyo.rapid.base.service.TcustCustroleService;
import org.ohuyo.rapid.base.service.TcustroleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newland.posmall.base.exception.BizException;
import com.newland.posmall.base.service.TcustCodeService;
import com.newland.posmall.base.service.TcustService;
import com.newland.posmall.bean.customer.Tcust;
import com.newland.posmall.bean.customer.TcustCode;
import com.newland.posmall.bean.dict.CreditLevel;
import com.newland.posmall.bean.dict.CustCodeStatus;
import com.newland.posmall.bean.dict.CustStatus;

@Service
@Transactional
public class CustomerBiz {
	
	@Resource
	private TcustService tcustService;
	
	@Resource
	private TcustroleService tcustroleService;
	
	@Resource
	private TcustCustroleService tcustCustroleService;
	
	@Resource
	private PasswordService passwordService;
	
	@Resource
	private TcustCodeService tcustCodeService;
	
	public List<Tcust> findBySelect(Tcust tcust) {
		return this.tcustService.findBySelect(tcust);
	}
	
	/**
	 * 注册信息校验
	 * @param tcust
	 * @param validCode
	 * @param sCode
	 * @param custCode 注册码
	 * @return
	 */
	public String validateRegInfo(Tcust tcust, String validCode, String sCode, String custCode) {
		if(null == tcust) {
			return "注册信息为空！";
		}
		if(StringUtils.isBlank(tcust.getLoginName())) {
			return "用户名为空！";
		}
		if(StringUtils.isBlank(tcust.getPassword())) {
			return "登录密码为空！";
		}
		if(StringUtils.isBlank(custCode)) {
			return "注册码为空！";
		}
		if (!StringUtils.equals(sCode, validCode)) {
			return "验证码错误！";
		}
		Tcust tcustCondition = new Tcust();
		tcustCondition.setLoginName(tcust.getLoginName());
		List<Tcust> tcusts = this.tcustService.findBySelect(tcustCondition);
		if(null != tcusts && tcusts.size() >= 1) {
			return "用户名已注册！";
		}
		
		TcustCode tcustCode = this.tcustCodeService.findTcustCode(custCode, null);
		if(tcustCode == null) {
			return "注册码不存在！";
		}
		if(tcustCode.getCustCodeStatus() != CustCodeStatus.NO_USED) {
			return "注册码已被使用！";
		}
		return null;
	}
	
	/**
	 * 注册
	 * @param tcust
	 * @throws DecoderException 密码加密异常
	 * @throws BizException 
	 */
	public void registerCust(Tcust tcust) throws DecoderException, BizException {
		if(tcust == null) {
			throw new BizException(0, "数据获取异常！");
		}
		tcust.setCustStatus(CustStatus.REG);
		tcust.setCreditLevel(CreditLevel.EXCELLENT);
		tcust.setPassword(passwordService.decrypt(tcust.getPassword()));
		this.tcustService.add(tcust);
		
		//修改注册码对应的状态
		TcustCode tcustCode = this.tcustCodeService.findTcustCode(tcust.getCustCode(), CustCodeStatus.NO_USED);
		if(tcustCode == null) {
			throw new BizException(0, "注册码为空异常！");
		}
		tcustCode.setCustCodeStatus(CustCodeStatus.USED);
		this.tcustCodeService.modify(tcustCode);
		
		//角色分配(已注册的角色)
		Tcustrole tcustrole = tcustroleService.findTcustrole("已注册的客户角色");
		if(tcustrole != null){
			TcustCustrole tcc = new TcustCustrole();
			tcc.setIcustrole(tcustrole.getIcustrole());
			tcc.setIcust(tcust.getIcust());
			tcustCustroleService.save(tcc);
		}
	}
}
