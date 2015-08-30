package com.newland.posmall.biz.cust;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.ohuyo.rapid.base.entity.Tuser;
import org.ohuyo.rapid.base.service.TuserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.newland.posmall.base.exception.BizException;
import com.newland.posmall.base.service.EmailService;
import com.newland.posmall.base.service.TagmtService;
import com.newland.posmall.base.service.TcustRegService;
import com.newland.posmall.base.service.TmsgTmpService;
import com.newland.posmall.base.service.TnotifyCfgService;
import com.newland.posmall.base.service.TordDetailService;
import com.newland.posmall.base.service.TpdtService;
import com.newland.posmall.bean.basebusi.Tagmt;
import com.newland.posmall.bean.basebusi.TagmtDetail;
import com.newland.posmall.bean.basebusi.TlogisticsOrd;
import com.newland.posmall.bean.basebusi.TnotifyCfg;
import com.newland.posmall.bean.basebusi.Tord;
import com.newland.posmall.bean.basebusi.TordDetail;
import com.newland.posmall.bean.basebusi.Tpay;
import com.newland.posmall.bean.basebusi.Tpdt;
import com.newland.posmall.bean.customer.Tcust;
import com.newland.posmall.bean.customer.TcustReg;
import com.newland.posmall.bean.dict.MsgTmpType;
import com.newland.posmall.bean.dict.NotifyType;

/**
 * 客户邮件通知服务
 * @author rabbit
 *
 */
@Service
public class CustMailService {
	
	private static final Logger logger = LoggerFactory.getLogger(CustMailService.class);
	
	public static final String toUserName = "新大陆支付技术有限公司 商户管理部";
	
	@Autowired
	private TmsgTmpService msgTmpService;
	
	@Autowired
	private EmailService emailService;
	
	@Resource
	private TcustRegService tcustRegService;
	
	@Autowired
	private TnotifyCfgService tnotifyCfgService;
	
	@Autowired
	private TuserService tuserService;

	@Autowired
	private TagmtService agmtService;
	
	@Autowired
	private TordDetailService tordDetailService;
	
	@Autowired
	private TpdtService tpdtService;

	@Autowired
	private TuserService userService;

	@Autowired
	private TcustRegService custRegService;


	/**
	 * 发送注册申请信息至商务管理部
	 * @param cust
	 * @param reg
	 * @throws BizException
	 */
	public void sendRegMail(Tcust cust, TcustReg reg) {
		String contentTmp = this.msgTmpService.queryByTypeAndCode(MsgTmpType.EMAIL, NotifyType.REG_APPLY_NOTIFY.toString()).
				getContent();
		MessageFormat formatter = new MessageFormat(contentTmp);
		List<TnotifyCfg> cfgs = this.tnotifyCfgService.findMultByNotifyType(NotifyType.REG_APPLY_NOTIFY);
		if (CollectionUtils.isEmpty(cfgs)) {
			logger.error("【注册通知】邮件发送失败：消息通知人未配置");
			return;
		}
		for (TnotifyCfg cfg : cfgs) {
			Tuser toUser = this.tuserService.getTuser(cfg.getIuser());
			if(toUser == null || toUser.getTuserSub() == null) {
				logger.error("【注册通知】邮件发送失败：消息通知人id[" + cfg.getIuser() + "]不存在");
				continue;
			}
			String content = formatter.format(new Object[]{toUserName, cust.getLoginName(), reg.getCustNo(), reg.getName(), reg.getTel(), reg.getMobile(), reg.getName()});
			this.sendEmail(content, "客户注册申请", toUser.getTuserSub().getEmail());
		}
	}
	
	/**
	 * 发送保证金支付通知
	 * @param cust
	 * @param tagmt
	 * @param pay
	 * @throws BizException
	 */
	public void sendDepositPayNotify(Tcust cust, Tagmt tagmt, Tpay pay) {
//		String contentTmp = "to:{0} 保证金支付通知[协议编号：{1}, 保证金支付金额：{2,number,#,##0.00  元}] from：{3}";
		String contentTmp = this.msgTmpService.queryByTypeAndCode(MsgTmpType.EMAIL, NotifyType.DEPOSIT_PAY_NOTIFY.toString()).
				getContent();
		MessageFormat formatter = new MessageFormat(contentTmp);
		List<TnotifyCfg> cfgs = this.tnotifyCfgService.findMultByNotifyType(NotifyType.DEPOSIT_PAY_NOTIFY);
		if (CollectionUtils.isEmpty(cfgs)) {
			logger.error("【保证金支付，客户凭证上传通知】邮件发送失败：消息通知人未配置");
			return;
		}
		for (TnotifyCfg cfg : cfgs) {
			Tuser toUser = this.tuserService.getTuser(cfg.getIuser());
			if(toUser == null || toUser.getTuserSub() == null) {
				logger.error("【保证金支付，客户凭证上传通知】邮件发送失败：消息通知人id[" + cfg.getIuser() + "]不存在");
				continue;
			}
			TcustReg reg = this.tcustRegService.findByIcust(cust.getIcust());
			String content = formatter.format(new Object[]{toUser.getTuserSub().getName(), tagmt.getAgmtNo(), 
					pay.getRemittanceNo(), pay.getAmt(), reg.getName()});
			this.sendEmail(content, "保证金支付通知", toUser.getTuserSub().getEmail());
			/*if(StringUtils.isNotBlank(reg.getSalesmanEmail())){//发送邮件给业务员
				this.sendEmail(content, "保证金支付通知", reg.getSalesmanEmail());
			}*/
		}
	}
	
	/**
	 * 发送订单支付通知
	 * @param cust
	 * @param tagmt
	 * @param tord
	 * @param deposit 保证金抵扣金额
	 * @param payAmt 支付金额
	 * @throws BizException
	 */
	public void sendOrderPayNotify(Tcust cust, Tagmt tagmt, Tord tord, BigDecimal deposit, BigDecimal payAmt) {
		if(deposit == null) {
			deposit = new BigDecimal(0);
		}
//		String contentTmp = "to:{0} 订单支付通知[协议编号：{1}, 订单编号：{2}, 保证金抵扣金额：{3,number,#,##0.00  元} 支付金额：{4,number,#,##0.00  元}] from：{5}";
		String contentTmp = this.msgTmpService.queryByTypeAndCode(MsgTmpType.EMAIL, NotifyType.CUST_PAY_NOTIFY.toString()).
				getContent();
		MessageFormat formatter = new MessageFormat(contentTmp);
		List<TnotifyCfg> cfgs = this.tnotifyCfgService.findMultByNotifyType(NotifyType.CUST_PAY_NOTIFY);
		if (CollectionUtils.isEmpty(cfgs)) {
			logger.error("【订单支付，客户凭证上传通知】邮件发送失败：消息通知人未配置");
			return;
		}
		for (TnotifyCfg cfg : cfgs) {
			Tuser toUser = this.tuserService.getTuser(cfg.getIuser());
			if(toUser == null || toUser.getTuserSub() == null) {
				logger.error("【订单支付，客户凭证上传通知】邮件发送失败：消息通知人id[" + cfg.getIuser() + "]不存在");
				continue;
			}
			TcustReg reg = this.tcustRegService.findByIcust(cust.getIcust());
			String content = formatter.format(new Object[]{toUser.getTuserSub().getName(), 
					tagmt.getAgmtNo(), tord.getOrdNo(), deposit, payAmt, reg.getName()});
			this.sendEmail(content, "订单支付通知", toUser.getTuserSub().getEmail());
			/*if(StringUtils.isNotBlank(reg.getSalesmanEmail())){//发送邮件给业务员
				this.sendEmail(content, "订单支付通知",reg.getSalesmanEmail());
			}*/
		}
	}
	
	/**
	 * 发送协议通知
	 * @param cust
	 * @param agmt
	 * @throws BizException 
	 */
	public void sendAgmtNotify(Tcust cust, Tagmt agmt) {
//		String contentTmp = "to:{0} 确认额度[协议编号：{1}, 客户名称：{2} 订货数量：{3}, 协议起止日期：{4}] 发送人：{5}";
		String contentTmp = this.msgTmpService.queryByTypeAndCode(MsgTmpType.EMAIL, NotifyType.AGMT_APPLY_NOTIFY.toString()).
				getContent();
		MessageFormat formatter = new MessageFormat(contentTmp);
		List<TagmtDetail> detls = agmtService.findTagmtDetial(agmt);
		if (CollectionUtils.isEmpty(detls)) {
			logger.error("【协议提交通知】邮件发送失败：" + "协议明细为空[协议号："
					+ agmt.getAgmtNo() + " ID:" + agmt.getIagmt() + "]");
			return;
		}
		List<TnotifyCfg> cfgs = this.tnotifyCfgService.findMultByNotifyType(NotifyType.AGMT_APPLY_NOTIFY);
		if (CollectionUtils.isEmpty(cfgs)) {
			logger.error("【协议提交通知】邮件发送失败：消息通知人未配置");
			return;
		}
		for (TnotifyCfg cfg : cfgs) {
			Tuser toUser = this.userService.getTuser(cfg.getIuser());
			if(toUser == null || toUser.getTuserSub() == null) {
				logger.error("【协议提交通知】邮件发送失败：消息通知人id[" + cfg.getIuser() + "]不存在");
				continue;
			}
			String str = calPdtNum(detls);
			String date = calBEdate(agmt);
			TcustReg custReg = this.custRegService.findByIcust(agmt.getIcust());
			String content = formatter.format(new Object[] {
					toUser.getTuserSub().getName(), agmt.getAgmtNo(),
					custReg.getLongName(), str, date, custReg.getName()});
			this.sendEmail(content, "订单预约申请", toUser.getTuserSub().getEmail());	
			if(StringUtils.isNotBlank(custReg.getSalesmanEmail())){//发送邮件给业务员
				this.sendEmail(content, "订单预约申请", custReg.getSalesmanEmail());
			}
		}
	}
	
	/**
	 * 客户点单发送邮件
	 * @param cust
	 * @param tord
	 * @throws BizException
	 */
	public void sendCustOrderNotify(Tcust cust, Tord tord) {
//		String contentTmp = "to:{0} 客户点单通知[订单编号：{1}, 订货数量：{2  台} 发送人：{3}";
		String contentTmp = this.msgTmpService.queryByTypeAndCode(MsgTmpType.EMAIL, NotifyType.CUST_ORDER_NOTIFY.toString()).
				getContent();
		MessageFormat formatter = new MessageFormat(contentTmp);
		List<TnotifyCfg> cfgs = this.tnotifyCfgService.findMultByNotifyType(NotifyType.CUST_ORDER_NOTIFY);
		if (CollectionUtils.isEmpty(cfgs)) {			
			logger.error("【客户点单通知】邮件发送失败：消息通知人未配置");
			return;
		}
		TordDetail condition = new TordDetail();
		condition.setIord(tord.getIord());
		List<TordDetail> details = this.tordDetailService.findSelect(condition);
		if(CollectionUtils.isEmpty(details)) {
			logger.error("【客户点单通知】邮件发送失败：" + "订单明细为空[订单号："
					+ tord.getOrdNo() + " ID:" + tord.getIord() + "]");
			return;
		}
		int ordNum = 0;
		for (TordDetail tordDetail : details) {
			ordNum = ordNum + tordDetail.getNum();
		}
		for (TnotifyCfg cfg : cfgs) {
			Tuser toUser = this.userService.getTuser(cfg.getIuser());
			if(toUser == null || toUser.getTuserSub() == null) {
				logger.error("【客户点单通知】邮件发送失败：消息通知人id[" + cfg.getIuser() + "]不存在");
				continue;
			}
			TcustReg custReg = this.custRegService.findByIcust(cust.getIcust());
			String content = formatter.format(new Object[] {toUser.getTuserSub().getName(), 
					tord.getOrdNo(), ordNum, custReg.getName()});
			this.sendEmail(content, "客户点单通知", toUser.getTuserSub().getEmail());	
			if(StringUtils.isNotBlank(custReg.getSalesmanEmail())){//发送邮件给业务员
				this.sendEmail(content, "客户点单通知",custReg.getSalesmanEmail());
			}
		}
	}
	
	/**
	 * 客户制定物流单发送邮件
	 * @param cust
	 * @param tord
	 * @throws BizException
	 */
	public void sendCustLogisticsNotify(Tcust cust, TlogisticsOrd tlogisticsOrd) {
//		String contentTmp = "to:{0} 客户物流单通知[物流单编号：{1}, 发货数量：{2}台] 发送人：{3}";
		String contentTmp = this.msgTmpService.queryByTypeAndCode(MsgTmpType.EMAIL, NotifyType.CUST_LOGISTICS_NOTIFY.toString()).
				getContent();
		MessageFormat formatter = new MessageFormat(contentTmp);
		List<TnotifyCfg> cfgs = this.tnotifyCfgService.findMultByNotifyType(NotifyType.CUST_LOGISTICS_NOTIFY);
		if (CollectionUtils.isEmpty(cfgs)) {			
			logger.error("【客户制定物流单通知】邮件发送失败：消息通知人未配置");
			return;
		}
		
		for (TnotifyCfg cfg : cfgs) {
			Tuser toUser = this.userService.getTuser(cfg.getIuser());
			if(toUser == null || toUser.getTuserSub() == null) {
				logger.error("【客户制定物流单通知】邮件发送失败：消息通知人id[" + cfg.getIuser() + "]不存在");
				continue;
			}
			TcustReg custReg = this.custRegService.findByIcust(cust.getIcust());
			String content = formatter.format(new Object[] {toUser.getTuserSub().getName(), 
					tlogisticsOrd.getInnerOrdno(), tlogisticsOrd.getNum(), custReg.getName()});
			this.sendEmail(content, "客户制定物流单通知", toUser.getTuserSub().getEmail());
			if(StringUtils.isNotBlank(custReg.getSalesmanEmail())){//发送邮件给业务员
				this.sendEmail(content, "客户制定物流单通知",custReg.getSalesmanEmail());
			}
		}
	}

	/**
	 * 计算起止日期
	 * 
	 * @param agmt
	 * @return
	 */
	private String calBEdate(Tagmt agmt) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		return sdf.format(agmt.getStartTime()) + " 至  " + sdf.format(agmt.getEndTime());
	}

	/**
	 * 计算产品数量
	 * 
	 * @param detls
	 * @return
	 */
	private String calPdtNum(List<TagmtDetail> detls) {
		Map<Long, Integer> map = new HashMap<Long, Integer>();
		for (TagmtDetail detl : detls) {
			Integer amount = map.get(detl.getIpdt());
			if (amount == null) {
				map.put(detl.getIpdt(), detl.getNum());
			} else {
				amount = amount + detl.getNum();
			}
		}
		String str = "";
		for (Map.Entry<Long, Integer> entry : map.entrySet()) {
			Long key = entry.getKey();
			Tpdt pdt = this.tpdtService.find(key);
			String pdtName = "未知";
			if (pdt != null) {
				pdtName = pdt.getName();
			}
			str += "名称：" + pdtName + "-";
			str += "数量：" + entry.getValue() + "台,";
		}
		return str;
	}
	
	/**
	 * 发送邮件
	 * @param content
	 * @param subject
	 * @param toAddr
	 * @throws BizException
	 */
	private void sendEmail(String content,String subject, String toAddr) {
		logger.debug("email content:" + content + "===to email addr:" + toAddr);
		try {
			this.emailService.sendEmail(content, subject, toAddr);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
}
