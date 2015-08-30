package com.newland.posmall.biz.admin;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.ohuyo.rapid.base.entity.MailContent;
import org.ohuyo.rapid.base.entity.Tuser;
import org.ohuyo.rapid.base.service.TsysParamService;
import org.ohuyo.rapid.base.service.TuserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.newland.posmall.base.exception.BizException;
import com.newland.posmall.base.service.ElecAgmtService;
import com.newland.posmall.base.service.EmailService;
import com.newland.posmall.base.service.TagmtService;
import com.newland.posmall.base.service.TcustRegService;
import com.newland.posmall.base.service.TcustService;
import com.newland.posmall.base.service.TmsgTmpService;
import com.newland.posmall.base.service.TnotifyCfgService;
import com.newland.posmall.base.service.TordService;
import com.newland.posmall.base.service.TpdtService;
import com.newland.posmall.bean.basebusi.Tagmt;
import com.newland.posmall.bean.basebusi.TagmtDetail;
import com.newland.posmall.bean.basebusi.TlogisticsOrd;
import com.newland.posmall.bean.basebusi.Tord;
import com.newland.posmall.bean.basebusi.TpayNotify;
import com.newland.posmall.bean.customer.Tcust;
import com.newland.posmall.bean.customer.TcustReg;
import com.newland.posmall.bean.dict.AgmtStatus;
import com.newland.posmall.bean.dict.DictType;
import com.newland.posmall.bean.dict.MsgTmpType;
import com.newland.posmall.bean.dict.NotifyType;
import com.newland.posmall.bean.dict.PayType;
import com.newland.posmall.biz.common.MapBiz;

/**
 * 后台邮件通知服务
 * 
 * @author rabbit
 * 
 */
@Service
public class AdminMailService {

//	public static final String fromUserName = "福建新大陆支付技术公司";
	
//	public static final String officialWebsiteAddr = "<a target=\"_blank\" href=\"http://192.168.2.254:8080/posmall/cust/login.do\">订单平台网址</a>";
	
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private EmailService emailService;

	@Autowired
	private TmsgTmpService msgTempService;

	@Autowired
	private TagmtService agmtService;
	
	@Autowired
	private TordService ordService;

	@Autowired
	private TpdtService tpdtService;

	@Autowired
	private TcustService custService;

	@Autowired
	private TuserService userService;

	@Autowired
	private TcustRegService custRegService;

	@Autowired
	private TnotifyCfgService tnotifyCfgService;
	
	@Autowired
	private TsysParamService tsysParamService;
	
	@Autowired
	private ElecAgmtService elecagmtService;
	@Autowired
	private MapBiz mapBiz;


	/**
	 * 发送审核结果邮件到客户(修订)
	 * 
	 * @param cust
	 * @param agmt
	 * @throws BizException
	 */
	public void sendRegAuditMail(Tcust cust, TcustReg reg) {
//		String contentTmp = "to:尊敬的{0}，您的注册信息经福建新大陆支付技术有限公司确认，已通过。现在您可登录“{1}”进行相关采购操作。 发送人：{2}";
		if (reg == null) {
			logger.error("【客户注册审核通知】邮件发送失败：客户注册信息不存在");
			return;
		}
		String contentTmp =  msgTempService.queryByTypeAndCode(MsgTmpType.EMAIL, NotifyType.REG_AUDIT_NOTIFY.toString()).getContent();
		MessageFormat formatter = new MessageFormat(contentTmp);
		
		String content = formatter.format(new Object[] { reg.getName(),
				this.tsysParamService.getTsysParam("OTHER_CONF", "OFFICIAL_WEBSITE_ADDR").getValue() , this.tsysParamService.getTsysParam("OTHER_CONF", "FROM_USERNAME").getValue()  });
		
		this.sendEmail(content, "“易收银”YPOS订单平台-客户注册结果通知", reg.getEmail());
		/*if(StringUtils.isNotBlank(reg.getSalesmanEmail())){//发送邮件给业务员
			this.sendEmail(content, "“易收银”YPOS订单平台-客户注册结果通知", reg.getSalesmanEmail());
		}*/
	}

	/**
	 * 发送邮件
	 * 
	 * @param content
	 * @param subject
	 * @param toAddr
	 * @throws BizException
	 */
	private void sendEmail(String content, String subject, String toAddr) {
		try{
			this.emailService.sendEmail(content, subject, toAddr);
		}catch(Exception e){
			logger.error("发送邮件失败", e);
		}
	}


	@SuppressWarnings("unused")
	private String getNotifyType(String notifyType) {
		return "保证金支付";
	}

	
	/**
	 * 发送订单支付审核结果发送至客户 (修订， 审核不通过发送)
	 * 
	 * 发送支付审核结果 至商务部
	 * @param cust
	 * @param pay
	 * @throws BizException
	 */
	public void sendPayAuditMailToCust(TpayNotify payNotify, Tuser fromUser) {
//		String contentTmp = "to:尊敬的{0} 您{1}，付款通知书编号为：{2}的{3}凭证上传异常，请重新登录“{4}”重新上传凭证。感谢您对福建新大陆支付技术有限公司的支持。发送人：{5}";
		String contentTmp =  msgTempService.queryByTypeAndCode(MsgTmpType.EMAIL, NotifyType.PAY_AUDIT_NOTIFY.toString()).getContent();
		MessageFormat formatter = new MessageFormat(contentTmp);
		
		TcustReg custReg = custService.getTcustReg(payNotify.getIcust());
		if (custReg == null) {
			logger.error("【付款审核通知】邮件发送失败：客户注册信息不存在");
			return;
		}
		String no = "";
		String type = "";
		if(payNotify.getPayType() == PayType.BAIL){
			Tagmt agmt = agmtService.findById(payNotify.getIfk());
			no =  "预约的协议[编号:"+ agmt.getAgmtNo()+"]";
			type =  "保证金";
		}else if(payNotify.getPayType() == PayType.ORDER){
			Tord tord = ordService.find(payNotify.getIfk());
			no = "采购的订单[编号:"+ tord.getOrdNo()+"]";
			type = "订单货款";
		}else if(payNotify.getPayType() == PayType.BAIL_SUPPLEMENT) {
			Tagmt agmt = agmtService.findById(payNotify.getIfk());
			no = "预约的协议[编号:"+ agmt.getAgmtNo()+"]";
			type = "保证金补交";
		}
		
		String content = formatter.format(new Object[] {custReg.getName(), no, payNotify.getPayNotifyNo(), type, 
				this.tsysParamService.getTsysParam("OTHER_CONF", "OFFICIAL_WEBSITE_ADDR").getValue() , this.tsysParamService.getTsysParam("OTHER_CONF", "FROM_USERNAME").getValue()});
		this.sendEmail(content, "“易收银”YPOS订单平台-客户支付异常通知", custReg.getEmail());
		/*if(StringUtils.isNotBlank(custReg.getSalesmanEmail())){//发送邮件给业务员
			this.sendEmail(content, "“易收银”YPOS订单平台-客户支付异常通知",custReg.getSalesmanEmail());
		}*/
	}
	
	/**
	 * 发送订单审核结果邮件到客户 (修订)
	 * 
	 * @param cust
	 * @param pay
	 * @throws BizException
	 */
	public void sendOrdConfirmMail(Tcust cust, Tord ord) {
//		String contentTmp = "to:尊敬的{0} 您采购的订单，编号为：{1}, 货款{2},采购订单已生效。现在您可以登陆“{3}”进行通知发货，感谢您对福建新大陆支付技术有限公司的支持。发送人：{4}";
		String contentTmp =  msgTempService.queryByTypeAndCode(MsgTmpType.EMAIL, NotifyType.ORD_CONFIRM_NOTIFY.toString()).getContent();
		MessageFormat formatter = new MessageFormat(contentTmp);
		TcustReg custReg = this.custRegService.findByIcust(cust.getIcust());
		if (custReg == null) {
			logger.error("【订单确认通知】邮件发送失败：客户注册信息不存在");
			return;
		}
		String content = formatter.format(new Object[] { custReg.getName(),
				ord.getOrdNo(), mapBiz.getMapByType(DictType.pay_status.toString()).get(ord.getPayStatus().toString()), this.tsysParamService.getTsysParam("OTHER_CONF", "OFFICIAL_WEBSITE_ADDR").getValue() , this.tsysParamService.getTsysParam("OTHER_CONF", "FROM_USERNAME").getValue() });
		this.sendEmail(content, "“易收银”YPOS订单平台-客户采购订单结果通知", custReg.getEmail());
		/*if(StringUtils.isNotBlank(custReg.getSalesmanEmail())){//发送邮件给业务员
			this.sendEmail(content, "“易收银”YPOS订单平台-客户采购订单结果通知", custReg.getSalesmanEmail());
		}*/
	}



	/**
	 * 发送协议审核结果通知(修订)
	 * 
	 * @param custReg
	 * @param agmt
	 * @throws BizException
	 */
	public void sendAgmtAutitSuccMail(TcustReg custReg, Tagmt agmt) {
//		String contentTmp = "to:尊敬的{0} 您预约的协议，编号为：{1}的保证金:{2,number,#,##0.00 元}已支付成功，协议已生效。现在您可以登陆“{3}”进行采购点单要货，感谢您对福建新大陆支付技术有限公司的支持。发送人：{4}";
		String contentTmp =  msgTempService.queryByTypeAndCode(MsgTmpType.EMAIL, NotifyType.AGMT_AUDIT_NOTIFY.toString()).getContent();
		MessageFormat formatter = new MessageFormat(contentTmp);
		List<TagmtDetail> detls = agmtService.findTagmtDetial(agmt);
		if (CollectionUtils.isEmpty(detls)) {
			logger.error("【协议审核结果通知】邮件发送失败：" + "协议明细为空[协议号："
					+ agmt.getAgmtNo() + " ID:" + agmt.getIagmt() + "]");
			return;
		}
		String content = formatter.format(new Object[] { custReg.getName(),
				agmt.getAgmtNo(), agmt.getDeposit(),
				this.tsysParamService.getTsysParam("OTHER_CONF", "OFFICIAL_WEBSITE_ADDR").getValue() , this.tsysParamService.getTsysParam("OTHER_CONF", "FROM_USERNAME").getValue() });
		if(agmt.getAgmtStatus() == AgmtStatus.CONFIRM){
			MailContent cont = new MailContent();
			cont.setContent(content);
			cont.setSubject("“易收银”YPOS订单平台-客户预约协议结果通知");
			List<File> files = new ArrayList<File>();
			File file = this.elecagmtService.generate(agmt);
			files.add(file);
			cont.setAttachFiles(files);
			try{
				this.emailService.sendMail(cont, custReg.getEmail());
			}catch(Exception e){
				e.printStackTrace();
			}
			return ;
		}
		this.sendEmail(content, "“易收银”YPOS订单平台-客户预约协议结果通知", custReg.getEmail());
		/*if(StringUtils.isNotBlank(custReg.getSalesmanEmail())){//发送邮件给业务员
			this.sendEmail(content, "“易收银”YPOS订单平台-客户预约协议结果通知",custReg.getSalesmanEmail());
		}*/
	}
	
	/**
	 * 客户协议变更，发送审核结果邮件通知
	 * @param custReg
	 * @param agmt
	 */
	public void sendAgmtChangeAuditMail(TcustReg custReg, Tagmt agmt) {
		String contentTmp =  this.msgTempService.queryByTypeAndCode(MsgTmpType.EMAIL, NotifyType.AGMT_CHANGE_AUDIT_NOTIFY.toString()).getContent();
		MessageFormat formatter = new MessageFormat(contentTmp);
		List<TagmtDetail> detls = agmtService.findTagmtDetial(agmt);
		if (CollectionUtils.isEmpty(detls)) {
			logger.error("【协议变更审核结果通知】邮件发送失败：" + "协议明细为空[协议号："
					+ agmt.getAgmtNo() + " ID:" + agmt.getIagmt() + "]");
			return;
		}
		String content = formatter.format(new Object[] { custReg.getName(), agmt.getAgmtNo(),
				this.tsysParamService.getTsysParam("OTHER_CONF", "OFFICIAL_WEBSITE_ADDR").getValue(), 
				this.tsysParamService.getTsysParam("OTHER_CONF", "FROM_USERNAME").getValue() });
		this.sendEmail(content, "“易收银”YPOS订单平台-客户协议变更结果通知", custReg.getEmail());
		/*if(StringUtils.isNotBlank(custReg.getSalesmanEmail())){//发送邮件给业务员
			this.sendEmail(content, "“易收银”YPOS订单平台-客户协议变更结果通知",custReg.getSalesmanEmail());
		}*/
	}

	/**
	 * 发送物流扣费通知
	 * 
	 * @param custReg
	 * @param balace
	 *            保证金余额
	 * @param ord
	 * @throws BizException
	 */
	public void sendLogisticsMail(TcustReg custReg, Tagmt agmt, TpayNotify tpayNotify,
			TlogisticsOrd ord) {
//		String contentTmp = "to:{0} 保证金支付[协议编号：{1}, 客户编号：{2} 发货数量：{3}, 保证金支付运费：{4}] 发送人：{5}";
//		String contentTmp = "to:尊敬的{0} 您的物流单编号：{1} 物流费用通知[协议编号：{2},账单支付编号：{3},保证金支付物流费用：{4,number,#,##0.00 元},账单支付状态:{5}] 发送人：{6}";
		String contentTmp = msgTempService.queryByTypeAndCode(MsgTmpType.EMAIL, NotifyType.LOGISTICS_PAY_NOTIFY.toString()).getContent();
		MessageFormat formatter = new MessageFormat(contentTmp);
		String content = formatter.format(new Object[] { custReg.getName(),ord.getInnerOrdno(),
				agmt.getAgmtNo(), tpayNotify.getPayNotifyNo(), tpayNotify.getHavepayAmt(), mapBiz.getMapByType(DictType.pay_status.toString()).get(tpayNotify.getPayNotifyStatus().toString()), this.tsysParamService.getTsysParam("OTHER_CONF", "FROM_USERNAME").getValue() });
		this.sendEmail(content, "“易收银”YPOS订单平台-客户物流费用支付通知", custReg.getEmail());
		/*if(StringUtils.isNotBlank(custReg.getSalesmanEmail())){//发送邮件给业务员
			this.sendEmail(content, "“易收银”YPOS订单平台-客户物流费用支付通知",custReg.getSalesmanEmail());
		}*/
	}

}
