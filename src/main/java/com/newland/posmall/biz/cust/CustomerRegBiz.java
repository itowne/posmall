package com.newland.posmall.biz.cust;

import javax.annotation.Resource;

import org.ohuyo.rapid.base.TcustSession;
import org.ohuyo.rapid.base.servlet.AppSessionFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newland.posmall.base.exception.BizErrCode;
import com.newland.posmall.base.exception.BizException;
import com.newland.posmall.base.service.TcustRegService;
import com.newland.posmall.base.service.TcustService;
import com.newland.posmall.bean.customer.Tcust;
import com.newland.posmall.bean.customer.TcustReg;
import com.newland.posmall.bean.dict.CustStatus;

@Service
@Transactional
public class CustomerRegBiz {

	@Resource
	private TcustRegService  tcustRegService;
	
	@Resource
	private TcustService tcustService;
	
	@Resource
	private CustMailService custMailService;
	
	public TcustReg findByIcust(Long icust) {
		return this.tcustRegService.findByIcust(icust);
	}
	
	/**
	 * 修改公司信息：提交审核，添加注册表信息
	 * @param tcust
	 * @param tg
	 * @throws BizException
	 */
	public void submitTcustReg(Tcust tcust, TcustReg tg) throws BizException {
		if(tcust == null || tcust.getIcust() == null || tg == null) {
			throw new BizException(BizErrCode.SYS_ERR, "数据为空，操作失败！");
		}
		Tcust tcustDB = this.tcustService.find(tcust.getIcust());
		TcustReg tcustRegDB = this.tcustRegService.findByIcust(tcust.getIcust());
		if(tcustRegDB == null) { //不存在注册表信息，添加一条记录
			tg.setIcust(tcust.getIcust());
			this.tcustRegService.add(tg);
		} else {
			switch (tcustDB.getCustStatus()) {
			case REG:
			case AUDIT_NOPASS:
				tcustRegDB.setLongName(tg.getLongName());
				tcustRegDB.setName(tg.getName());
				tcustRegDB.setCustType(tg.getCustType());
				tcustRegDB.setContactName(tg.getContactName());
				tcustRegDB.setTel(tg.getTel());
				tcustRegDB.setMobile(tg.getMobile());
				tcustRegDB.setEmail(tg.getEmail());
				tcustRegDB.setFax(tg.getFax());
				tcustRegDB.setContractNo(tg.getContractNo());
				tcustRegDB.setSignatureDate(tg.getSignatureDate());
				//开票信息
				tcustRegDB.setInvoiceCorporation(tg.getInvoiceCorporation());
				tcustRegDB.setInvoiceType(tg.getInvoiceType());
				tcustRegDB.setTaxID(tg.getTaxID());
				tcustRegDB.setBank(tg.getBank());
				tcustRegDB.setAccount(tg.getAccount());
				tcustRegDB.setRegAddr(tg.getRegAddr());
				tcustRegDB.setRegTel(tg.getRegTel());
				tcustRegDB.setRemarks(tg.getRemarks());
				this.tcustRegService.modify(tcustRegDB);
				break;
			case AUDIT_ING:
				throw new BizException(BizErrCode.EXAMING, "正在审核，不能重复提交！");
			case WAIT_AUDIT:
				throw new BizException(BizErrCode.WAIT_FOR_EXAM, "正在等待审核，不能重复提交！");
			case AUDIT_PASS:
				throw new BizException(BizErrCode.EXAMED, "已通过审核，信息不能修改！");
			default:
				throw new BizException(BizErrCode.CUSTREG_STATUS_ERR, "状态异常！");
			}
		}
		
		tcustDB.setCustStatus(CustStatus.WAIT_AUDIT); //修改客户状态为：待审核
		this.tcustService.modifyTcust(tcustDB);
		
		TcustSession session = (TcustSession) AppSessionFilter.getAppSession(); //重新设置session
		session.setTcust(tcustDB);
		if(tcustRegDB == null) {
			session.setTcustReg(tg);
		}else {
			session.setTcustReg(tcustRegDB);
		}
		
		//发送申请通知邮件
		this.custMailService.sendRegMail(tcustDB, tcustRegDB == null ? tg : tcustRegDB);
	}
	
	/**
	 * 修改公司信息：保存信息
	 * @param tcust
	 * @param tg
	 * @throws BizException
	 */
	public void modifyTcustReg(Tcust tcust, TcustReg tg) throws BizException {
		if(tcust == null || tcust.getIcust() == null || tg == null) {
			throw new BizException(0, "数据为空，操作失败！");
		}
		Tcust tcustDB = this.tcustService.find(tcust.getIcust());
		TcustReg tcustRegDB = this.tcustRegService.findByIcust(tcust.getIcust());
		if(tcustRegDB == null) { //不存在注册表信息，添加一条记录
			tg.setIcust(tcust.getIcust());
			this.tcustRegService.add(tg);
		}else { //已注册、待审核、审核未通过，均可修改保存
			switch (tcustDB.getCustStatus()) {
			case REG:
			case AUDIT_NOPASS:
				tcustRegDB.setLongName(tg.getLongName());
				tcustRegDB.setName(tg.getName());
				tcustRegDB.setCustType(tg.getCustType());
				tcustRegDB.setContactName(tg.getContactName());
				tcustRegDB.setTel(tg.getTel());
				tcustRegDB.setMobile(tg.getMobile());
				tcustRegDB.setEmail(tg.getEmail());
				tcustRegDB.setFax(tg.getFax());
				tcustRegDB.setContractNo(tg.getContractNo());
				tcustRegDB.setSignatureDate(tg.getSignatureDate());
				//开票信息
				tcustRegDB.setInvoiceCorporation(tg.getInvoiceCorporation());
				tcustRegDB.setInvoiceType(tg.getInvoiceType());
				tcustRegDB.setTaxID(tg.getTaxID());
				tcustRegDB.setBank(tg.getBank());
				tcustRegDB.setAccount(tg.getAccount());
				tcustRegDB.setRegAddr(tg.getRegAddr());
				tcustRegDB.setRegTel(tg.getRegTel());
				tcustRegDB.setRemarks(tg.getRemarks());
				this.tcustRegService.modify(tcustRegDB);
				break;
			case AUDIT_PASS:
				tcustRegDB.setContactName(tg.getContactName());
				tcustRegDB.setTel(tg.getTel());
				tcustRegDB.setMobile(tg.getMobile());
				tcustRegDB.setEmail(tg.getEmail());
				tcustRegDB.setFax(tg.getFax());
				tcustRegDB.setRemarks(tg.getRemarks());
				this.tcustRegService.modify(tcustRegDB);
				break;
			case AUDIT_ING:
				throw new BizException(BizErrCode.EXAMING, "正在审核，不能修改！");
			case WAIT_AUDIT:
				throw new BizException(BizErrCode.WAIT_FOR_EXAM, "等待审核，不能修改！");
			default:
				throw new BizException(BizErrCode.CUSTREG_STATUS_ERR, "状态异常！");
			}
		}
		
		TcustSession session = (TcustSession) AppSessionFilter.getAppSession(); //重新设置session
		if(tcustRegDB == null) {			
			session.setTcustReg(tg);
		}else {
			session.setTcustReg(tcustRegDB);
		}
	}
}
