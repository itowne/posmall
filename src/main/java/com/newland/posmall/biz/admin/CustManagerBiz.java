package com.newland.posmall.biz.admin;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Order;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.entity.TcustCustrole;
import org.ohuyo.rapid.base.entity.Tcustrole;
import org.ohuyo.rapid.base.entity.Tuser;
import org.ohuyo.rapid.base.service.TcustCustroleService;
import org.ohuyo.rapid.base.service.TcustroleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newland.posmall.base.exception.BizErrCode;
import com.newland.posmall.base.exception.BizException;
import com.newland.posmall.base.service.TcustCodeService;
import com.newland.posmall.base.service.TcustRateService;
import com.newland.posmall.base.service.TcustRegService;
import com.newland.posmall.base.service.TcustService;
import com.newland.posmall.base.service.TlogService;
import com.newland.posmall.base.service.TpdtService;
import com.newland.posmall.bean.basebusi.Tpdt;
import com.newland.posmall.bean.common.Tlog;
import com.newland.posmall.bean.customer.Tcust;
import com.newland.posmall.bean.customer.TcustCode;
import com.newland.posmall.bean.customer.TcustRate;
import com.newland.posmall.bean.customer.TcustReg;
import com.newland.posmall.bean.customer.condition.TcustCodeCondition;
import com.newland.posmall.bean.dict.CustCodeStatus;
import com.newland.posmall.bean.dict.CustStatus;
import com.newland.posmall.bean.dict.LogType;
import com.newland.posmall.bean.dict.OperType;
import com.newland.posmall.bean.dict.ValidStatus;

@Service
@Transactional(rollbackFor = Throwable.class)
public class CustManagerBiz {

	@Resource
	private TcustService tcustService;

	@Resource
	private TcustRegService tcustRegService;

	@Resource
	private TcustRateService tcustRateService;

	@Resource
	private TcustCustroleService tcustCustroleService;

	@Resource
	private TcustroleService tcustroleService;

	@Resource
	private AdminMailService adminMailService;
	
	@Resource
	private TpdtService tpdtService;
	
	@Resource
	private TlogService tlogService;

	@Resource
	private TcustCodeService tcustCodeService;

	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 
	 * 
	 * @param tcustReg
	 * @throws BizException
	 */
	public void modifyCustReg(TcustReg tcustReg, Tcust tcust, Tuser tuser)
			throws BizException {

		if (tcustReg != null) {
			TcustReg tcustRegNew = this.tcustRegService.find(tcustReg
					.getIcust());
			
			Tlog tlog = new Tlog();
			tlog.setIfk(tuser.getIuser());
			tlog.setLogType(LogType.USER);
			tlog.setOperType(OperType.CUST_MGR);
			tlog.setMemo("修改客户信息");
			tlog.setPreData("");
			StringBuffer sbDate = new StringBuffer();
			sbDate.append("修改前客户信息:客户id是");
			sbDate.append(tcustRegNew.getIcust());
			
			tlog.setPreData(sbDate.toString());
			
			tcustRegNew.setSignatureDate(tcustReg.getSignatureDate());
			tcustRegNew.setContractNo(tcustReg.getContractNo());
			tcustRegNew.setAccount(tcustReg.getAccount());
			tcustRegNew.setBusLic(tcustReg.getBusLic());
			tcustRegNew.setCompanyType(tcustReg.getCompanyType());
			tcustRegNew.setContactName(tcustReg.getContactName());
			tcustRegNew.setCorporationName(tcustReg.getCorporationName());
			tcustRegNew.setCustType(tcustReg.getCustType());
			tcustRegNew.setEmail(tcustReg.getEmail());
			tcustRegNew.setBank(tcustReg.getBank());
			tcustRegNew.setFax(tcustReg.getFax());
			tcustRegNew.setLongName(tcustReg.getLongName());
			tcustRegNew.setMobile(tcustReg.getMobile());
			tcustRegNew.setName(tcustReg.getName());
			tcustRegNew.setOrgCode(tcustReg.getOrgCode());
			tcustRegNew.setRegAddr(tcustReg.getRegAddr());
			tcustRegNew.setRegDate(tcustReg.getRegDate());
			tcustRegNew.setRetCap(tcustReg.getRetCap());
			tcustRegNew.setSummary(tcustReg.getSummary());
			tcustRegNew.setTaxReg(tcustReg.getTaxReg());
			tcustRegNew.setTel(tcustReg.getTel());
			tcustRegNew.setSalesmanName(tcustReg.getSalesmanName());
			tcustRegNew.setSalesmanEmail(tcustReg.getSalesmanEmail());
			this.tcustRegService.modify(tcustRegNew);

			this.tcustService.modifyTcust(tcust);
			
			//添加日志
			StringBuffer sbDate2 = new StringBuffer();
			sbDate2.append("修改后客户信息:客户id是");
			sbDate2.append(tcustRegNew.getIcust());
			
			tlog.setData(sbDate2.toString());
			this.tlogService.save(tlog);
			

		} else {
			logger.info("没有查询到客户ID为"
					+ (tcust == null ? null : tcust.getIcust()) + "的注册信息");
			throw new BizException(BizErrCode.CUSTREG_NOFOUND, "没有找到客户的注册信息");
		}

	}

	/**
	 * 查询条件 查询 客户列表
	 * 
	 * @param custStaus
	 * @param creditStaus
	 * @param name
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List findListByInfo(Page page, String custStatus, String creditLevel, String name) {
		
		return tcustService.findListByInfo(page, custStatus, creditLevel, name);
	}

	public List<Tcust> findListByCondition(Tcust tcust) {
		return tcustService.findBySelect(tcust);
	}

	/**
	 * 到 审核页,将用户状态锁定到 审核中
	 * 
	 * @param tcust
	 */
	public void custAuditIng(Tcust tcust) {
		tcust.setCustStatus(CustStatus.AUDIT_ING);
		tcustService.modifyTcust(tcust);
	}

	/**
	 * 客户资料审核
	 * 
	 * @param tcust
	 * @param tcustReg
	 * @throws BizException
	 */
	public void custAudit(Tcust tcust, TcustReg tcustReg, String custStatus,
			String refuseReason,String salesmanName,String salesmanEmail) throws BizException {

		if (CustStatus.AUDIT_PASS.toString().equals(custStatus)) {

			// if(tcustReg == null || StringUtils.isBlank(tcustReg.getCustNo())
			// || StringUtils.isBlank(tcustReg.getBusLic()) ||
			// StringUtils.isBlank(tcustReg.getOrgCode()) ||
			// StringUtils.isBlank(tcustReg.getTaxReg())
			// || tcustReg.getBusLicIfile() == null ||
			// tcustReg.getOrgCodeIfile() == null || tcustReg.getTaxRegIfile()
			// == null){
			// logger.info("资料不全不能通过审核");
			// throw new BizException(1,"资料不全不能通过审核");
			// }
			if (tcustReg == null
					|| !CustStatus.AUDIT_ING.equals(tcust.getCustStatus())) {
				logger.info("客户状态异常，请重新获取审核信息");
				throw new BizException(BizErrCode.CUSTREG_STATUS_ERR,
						"客户状态异常，请重新获取审核信息");
			}

			// 变更 客户状态为审核通过
			tcust.setCustStatus(CustStatus.AUDIT_PASS);
			tcustService.modifyTcust(tcust);
			
			//编写业务员信息
			tcustReg.setSalesmanName(salesmanName);
			tcustReg.setSalesmanEmail(salesmanEmail);
			tcustRegService.modify(tcustReg);
			
			
			// 注册码表数据回填
			TcustCode code = this.tcustCodeService.findTcustCode(tcust.getCustCode(), CustCodeStatus.USED);
			if(code == null) {
				throw new BizException(0, "注册码获取异常！");
			}
			code.setIcust(tcust.getIcust());
			code.setCompanyName(tcustReg.getLongName());
			this.tcustCodeService.modify(code);
			
			// 添加 客户的产品折扣率
			Tpdt tpdt = new Tpdt();
			tpdt.setDelFlag(Boolean.FALSE);
			List<Tpdt> tpdtList = tpdtService.findBySelect(tpdt);
			if (tpdtList != null && tpdtList.size() > 0) {
				TcustRate tct = null;
				for (Tpdt t : tpdtList) {
					tct = new TcustRate();
					tct.setIcust(tcust.getIcust());
					tct.setIpdt(t.getIpdt());

					tct.setCustRateStatus(ValidStatus.VALID);
					tct.setRate(new BigDecimal(1));
					tcustRateService.add(tct);
				}
			}

			// 角色分配(已审核的角色)
			Tcustrole tcustrole = tcustroleService.findTcustrole("已审核的客户角色");
			if (tcustrole != null) {
				TcustCustrole tcc = new TcustCustrole();
				tcc.setIcustrole(tcustrole.getIcustrole());
				tcc.setIcust(tcust.getIcust());
				tcustCustroleService.save(tcc);
			}

			// 发送邮件
			adminMailService.sendRegAuditMail(tcust, tcustReg);
			

		} else if (CustStatus.AUDIT_NOPASS.toString().equals(custStatus)) {

			if (!CustStatus.AUDIT_ING.equals(tcust.getCustStatus())) {
				logger.info("客户状态异常，请重新获取审核信息");
				throw new BizException(BizErrCode.CUSTREG_STATUS_ERR,
						"客户状态异常，请重新获取审核信息");
			}

			// 变更 客户状态为审核未通过
			tcust.setCustStatus(CustStatus.AUDIT_NOPASS);
			tcustService.modifyTcust(tcust);
			
			tcustReg.setRefuseReason(refuseReason);
			tcustRegService.modify(tcustReg);
			// 发送邮件 ，要理由发送给客户refuseReason (取消)
//			adminMailService.sendRegAuditMail(tcust, tcustReg);
			

		}

	}

	/**
	 * 查询客户的产品折扣率
	 */
	public List<TcustRate> queryCustRate(Long icust) {
		TcustRate tcustRate = new TcustRate();
		tcustRate.setIcust(icust);
		Tpdt tpdt = new Tpdt();
		tpdt.setDelFlag(Boolean.FALSE);
		List<TcustRate> tcustRateList = tcustRateService
				.findByCondition(tcustRate);
		List<Tpdt> tpdtList = tpdtService.findBySelect(tpdt);
		if (tpdtList != null) {
			TcustRate tcr = null;
			for (Tpdt t : tpdtList) {
				Boolean flag = false;
				if (tcustRateList != null && tcustRateList.size() > 0) {
					for (TcustRate temp : tcustRateList) {
						if (t.getIpdt().equals(temp.getIpdt())) {
							flag = true;
							break;
						}
					}
					if (!flag) {
						// 如果没有找到产品的折扣率，则添加数据
						tcr = new TcustRate();
						tcr.setIcust(icust);
						tcr.setIpdt(t.getIpdt());

						tcr.setCustRateStatus(ValidStatus.VALID);
						tcr.setRate(new BigDecimal(1));
						tcustRateService.add(tcr);
					}
				} else {
					// 初始化所有产品的折扣率
					tcr = new TcustRate();
					tcr.setIcust(icust);
					tcr.setIpdt(t.getIpdt());

					tcr.setCustRateStatus(ValidStatus.VALID);
					tcr.setRate(new BigDecimal(1));
					tcustRateService.add(tcr);
				}

			}

		}

		return tcustRateService.findByCondition(tcustRate);

	}

	/**
	 * 修改客户折扣率
	 * 
	 * @param tcustRateList
	 */
	public void modifyCustRateList(List<TcustRate> tcustRateList, Tuser tuser) {
		Tlog tlog = new Tlog();
		tlog.setIfk(tuser.getIuser());
		tlog.setLogType(LogType.USER);
		tlog.setOperType(OperType.CUST_MGR);
		tlog.setMemo("修改客户折扣率");
		tlog.setPreData("");
		StringBuffer sbDate = new StringBuffer();
		StringBuffer sbDate2 = new StringBuffer();
		
		tlog.setPreData(sbDate.toString());
		if (tcustRateList != null && tcustRateList.size() > 0) {
			for (TcustRate tcustRate : tcustRateList) {
				
				TcustRate tcustRateNew =  tcustRateService.find(tcustRate.getIcustRate());
				if(tcustRate.getRate().compareTo(tcustRateNew.getRate()) != 0){
					sbDate.append("修改前客户信息:客户id:").append(tcustRate.getIcust()).append(",产品id:").append(tcustRate.getIpdt()).append(",折扣率：").append(tcustRateNew.getRate());
					tcustRateNew.setRate(tcustRate.getRate());
					sbDate2.append("修改后客户信息:客户id:").append(tcustRateNew.getIcust()).append(",产品id:").append(tcustRateNew.getIpdt()).append(",折扣率：").append(tcustRateNew.getRate());
					tcustRateService.update(tcustRateNew);
					tlog.setPreData(sbDate.toString());
					tlog.setData(sbDate2.toString());
					tlogService.save(tlog);
				}
			}
			
		}
		
	}

	public TcustReg findTcustReg(Long icust) {

		return tcustRegService.find(icust);
	}

	public Tcust findTcust(Long icust) {

		return tcustService.find(icust);
	}

	/**
	 * 分页查询注册码
	 * @param custCode
	 * @param companyName
	 * @param custCodeStatus
	 * @return
	 */
	public List<TcustCode> queryTcustCodePage(String custCode, String companyName, String custCodeStatus, Page page) {
		TcustCodeCondition condition = new TcustCodeCondition();
		if(StringUtils.isNotBlank(custCode)) {
			condition.setCustCode(custCode);
		}
		if(StringUtils.isNotBlank(companyName)) {
			condition.setCompanyName(companyName);
		}
		if(StringUtils.isNotBlank(custCodeStatus)) {
			condition.setCustCodeStatus(CustCodeStatus.valueOf(custCodeStatus));
		}
		condition.addOrders(Order.desc("updTime"));
		condition.addOrders(Order.desc("custCode"));
		return this.tcustCodeService.findPageByCondition(condition, page);
	}
	
	/**
	 * 生成注册码
	 * @param num 个数（默认为10）
	 * @param tuser
	 * @return 生成个数
	 * @throws BizException
	 */
	public synchronized int generateCustCode(Integer num, Tuser tuser) throws BizException{
		if (num == null || num.intValue() <= 0) {
			num = 10;
		}
		if(tuser == null || tuser.getIuser() == null) {
			throw new BizException(0, "用户数据为空，生成注册码失败！");
		}
		for (int idx = 0; idx < num; idx++) {
			TcustCode code = new TcustCode();
			code.setCustCodeStatus(CustCodeStatus.NO_USED);
			code.setIuser(tuser.getIuser());
			code.setUserName(tuser.getLoginName());
			int count = 0;
			String newCustCode = "KH" + RandomStringUtils.randomNumeric(8);
			while (this.tcustCodeService.isCustCodeExists(newCustCode)) {
				newCustCode = "KH" + RandomStringUtils.randomNumeric(8);
				count++;
				if(count > 5) { //避免死循环
					throw new BizException(0, "注册码生成异常，请重试");
				}
			}
			code.setCustCode(newCustCode);
			this.tcustCodeService.add(code);
		}
		return num;
	}
}
