package com.newland.posmall.datagen;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.ohuyo.rapid.base.entity.Tuser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.newland.posmall.BeanTest;
import com.newland.posmall.base.exception.BizException;
import com.newland.posmall.base.service.TcustService;
import com.newland.posmall.base.service.TpdtService;
import com.newland.posmall.bean.basebusi.Tagmt;
import com.newland.posmall.bean.basebusi.Tord;
import com.newland.posmall.bean.basebusi.Tpdt;
import com.newland.posmall.bean.customer.Tcust;
import com.newland.posmall.bean.dict.PayStatus;
import com.newland.posmall.datagen.service.DatagenService;

@Transactional
public class DataGeneratorTest extends BeanTest {
	@Autowired
	DatagenService genService;
	
	@Autowired
	private TcustService tcustService;
	
	@Autowired
	private TpdtService tpdtService;
	
	private Tcust tcust;
	private Tuser tuser;
	
	// 预约订单数量
	private final int num = 1;
	// 用户号
	private final Long icust = 30L;
	// 管理员号
	private final Long iuser = 11L;
	// 协议用产品号
	private final long ipdt = 1L;
	// 客户折扣率
	private final BigDecimal custPdtRate = new BigDecimal(1);
	// 协议产品数量
	private final int pdtAgmtNum = 10000;
	// 订单数量
	private final int ordNum = 1;
	
	private List<Tagmt> agmts;
	
	// 订单所签署信息
	public List<Long> getIpdts() {
		List<Long> ipdts = new ArrayList<Long>();
		ipdts.add(1L);
		return ipdts;
	}
	
	/**
	 * 预约订单
	 * @throws BizException
	 */
	public void signAgree() throws BizException {
		this.tcust = genService.getCust(icust);
		Tpdt tpdt = this.genService.getTpdt(ipdt);
		tpdt.setCustPdtRate(custPdtRate);
		tpdt.setPdtAgmtNum(pdtAgmtNum);
		List<Tpdt> tpdts = new ArrayList<Tpdt>();
		tpdts.add(tpdt);
		this.agmts = this.genService.signAgmt(num, tpdts, tcust);
	}
	
	/**
	 * 协议确认
	 * @throws BizException
	 */
	public void cofirm() throws BizException {
		this.tuser = genService.getUser(iuser);
		this.genService.cofirm(agmts, tuser);
	}
	
	/**
	 * 上传付款凭证:保证金通知书
	 * @throws BizException
	 */
	public void uploadTicket() throws BizException {
		this.genService.uploadTicket(agmts, tcust);
	}
	
	/**
	 * 保证金通知书审核
	 * @throws BizException
	 */
	public void auditAgmt() throws BizException {
		this.genService.auditAgmt(agmts, PayStatus.HAVE_PAY, tuser);
	}
	
	/**
	 * 保证金通知书付款成功,协议最后审核
	 * @throws BizException
	 */
	public void agmtPayPassCheck() throws BizException {
		this.genService.agmtPayPassCheck(agmts, tuser);
	}
	
	/**
	 * 签署订单
	 * @throws BizException
	 */
	public void createOrd() throws BizException {
		List<Long> ipdts = getIpdts();
		for (int i = 0; i < agmts.size(); i++) {
			this.genService.createOrd(ordNum, ipdts, agmts.get(i), tcust);
		}
	}
	
	/**
	 * 上传付款凭证:订单通知书
	 * @throws BizException
	 */
	public void uploadOrdTicket() throws BizException {
		for (int i = 0; i < agmts.size(); i++) {
			List<Tord> ords = this.genService.getTord(agmts.get(i).getIagmt());
			this.genService.uploadOrdTicket(ords, tcust);
		}
	}
	
	/**
	 * 订单通知书审核
	 * @throws BizException
	 */
	public void auditOrd() throws BizException {
		for (int i = 0; i < agmts.size(); i++) {
			List<Tord> ords = this.genService.getTord(agmts.get(i).getIagmt());
			this.genService.auditOrd(ords, PayStatus.HAVE_PAY, tuser);
		}
	}
	
	/**
	 * 订单通知书付款成功,订单最后审核
	 * @throws BizException
	 */
	public void ordPayPassCheck() throws BizException {
		for (int i = 0; i < agmts.size(); i++) {
			List<Tord> ords = this.genService.getTord(agmts.get(i).getIagmt());
			this.genService.ordPayPassCheck(ords, tuser);
		}
	}
	
	@Test
	public void thewholeProcess() throws BizException {
		signAgree();
		cofirm();
		uploadTicket();
		auditAgmt();
		agmtPayPassCheck();
		createOrd();
		uploadOrdTicket();
		auditOrd();
		ordPayPassCheck();
	}
}
