package com.newland.posmall.biz.admin;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.TuserSession;
import org.ohuyo.rapid.base.entity.Tsys;
import org.ohuyo.rapid.base.entity.Tuser;
import org.ohuyo.rapid.base.service.TsysParamService;
import org.ohuyo.rapid.base.service.TsysService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.newland.posmall.base.exception.BizException;
import com.newland.posmall.base.service.TagmtService;
import com.newland.posmall.base.service.TcustRegService;
import com.newland.posmall.base.service.TcustService;
import com.newland.posmall.base.service.TdetailTraceService;
import com.newland.posmall.base.service.TlogService;
import com.newland.posmall.base.service.TordService;
import com.newland.posmall.base.service.TpayDetailService;
import com.newland.posmall.base.service.TpayNotifyService;
import com.newland.posmall.base.service.TpayService;
import com.newland.posmall.base.service.TpdtService;
import com.newland.posmall.bean.basebusi.Tagmt;
import com.newland.posmall.bean.basebusi.TagmtDetail;
import com.newland.posmall.bean.basebusi.Tpay;
import com.newland.posmall.bean.basebusi.TpayDetail;
import com.newland.posmall.bean.basebusi.TpayNotify;
import com.newland.posmall.bean.basebusi.Tpdt;
import com.newland.posmall.bean.basebusi.TpdtHis;
import com.newland.posmall.bean.basebusi.condition.TagmtCondition;
import com.newland.posmall.bean.basebusi.condition.TcustRegCondition;
import com.newland.posmall.bean.basebusi.condition.TpayNofityCondition;
import com.newland.posmall.bean.common.Tlog;
import com.newland.posmall.bean.customer.Tcust;
import com.newland.posmall.bean.customer.TcustReg;
import com.newland.posmall.bean.dict.AgmtDetailStatus;
import com.newland.posmall.bean.dict.AgmtStatus;
import com.newland.posmall.bean.dict.LogType;
import com.newland.posmall.bean.dict.OperType;
import com.newland.posmall.bean.dict.PayMethod;
import com.newland.posmall.bean.dict.PayStatus;
import com.newland.posmall.bean.dict.PayType;

@Service
@Transactional(rollbackFor = Throwable.class)
public class CustagmtBiz {

	@Resource
	private TagmtService tagmtService;

	@Resource
	private TpayNotifyService tpayNotifyService;

	@Resource
	private TpayDetailService tpayDetailService;

	@Resource
	private TpayService tpayService;

	@Resource
	private TcustService tcustService;
	
	@Resource
	private TcustRegService tcustRegService;
	
	@Resource
	private TpdtService tPdtService;
	
	@Resource
	private TordService tordService;
	
	@Resource
	private TpdtService tpdtService;
	
	@Resource
	private TlogService tlogService;
	@Resource
	private TsysParamService tsysParamService;
	
	@Resource
	private AdminMailService adminMailService;
	
	@Resource
	private TdetailTraceService tdetailTraceService;
	
	@Resource
	private TsysService tsysService;

	@SuppressWarnings("rawtypes")
	public List findListByInfo(Page page, String startTime, String endTime,
			String agmtStatus, String name) {
		TcustRegCondition trc = new TcustRegCondition();
		trc.setName(name);
        String icusts = tcustRegService.queryIcustsByName(trc);
        
        if(StringUtils.isNotBlank(name) && StringUtils.isBlank(icusts)){
        	return null;
        }
		List list = tagmtService.findListByInfo(page, startTime, endTime,
				agmtStatus, icusts);
		
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				obj[13] = new BigDecimal(String.valueOf(obj[13])).setScale(2,
						RoundingMode.HALF_UP);
				if(obj[6] != null && new BigDecimal(String.valueOf(obj[6])).compareTo(BigDecimal.ZERO)==0){
					Object temp = this.findLogisticsAmtByTagmt(Long.valueOf(String.valueOf(obj[0])));
					if(temp != null){
						Object[] object = (Object[]) temp;
						BigDecimal logisticsPay = new BigDecimal(object[1].toString()).subtract(new BigDecimal(object[2].toString()));
						if(logisticsPay.compareTo(BigDecimal.ZERO) == 0){
							obj[14] = false;
						}else{
							obj[14] = true;
						}
					}else{
						obj[14] = false;
					}
				}else{
					obj[14] = false;
				}
			}
		}

		return list;
	}

	public Tagmt findTagmtByIagmt(Long id) {
		if (id == null) {
			return null;
		}
		return this.tagmtService.findById(id);
	}

	/**
	 * 根据 协议ID查明 协议下的所有产品
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List findPdtByIagmt(Long id) {
		if (id == null) {
			return null;
		}
		List list = this.tagmtService.findPdtByIagmt(id);

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				obj[6] = new BigDecimal(String.valueOf(obj[6])).setScale(2,
						RoundingMode.HALF_UP);
			   //计算协议下所有订单的出货数量与剩余量
				Long ipdt = Long.valueOf(obj[11].toString());
				Object temp = this.tagmtService.findPdtLogisticsByIagmt(id, ipdt);
				if(temp != null){
					Object[] obj2 = (Object[])temp;
					if(obj2[1] == null){
						obj[9] = 0;
					}else{
						obj[9] = obj2[1];
					}
					if(obj2[2] == null){
						obj[10] = 0;
					}else{
						obj[10] = obj2[2];
					}
				}else{
					obj[9] = 0;
					obj[10] = 0;
				}
			}
		}

		return list;
	}
	
	/**
	 * 查询协议变更前产品明细
	 * @param iagmt
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List findPdtHisByIagmt(Long iagmt) {
		return this.tagmtService.findPdtHisByIagmt(iagmt);
	}
	
	public List<TagmtDetail> findTagmtDetailByTagmt(Tagmt tagmt) {
		return this.tagmtService.findTagmtDetial(tagmt);
	}

	/**
	 * 变更 协议状态，并发送邮件信息
	 * 
	 * @param tagmt
	 * @param agmtStatus
	 * @param agmtDetailStatus
	 * @throws BizException 
	 */
	@SuppressWarnings("unused")
	public Tagmt custagmtCheck(Tagmt tagmt, AgmtStatus agmtStatus,
			AgmtDetailStatus agmtDetailStatus, List<TagmtDetail> detailList, Tuser tuser) throws BizException {
		
		if (tagmt == null) {
			throw new BizException(0, "协议获取失败");
		}
		if(agmtStatus == AgmtStatus.AGMT_QUOTA_CONFIRM){
			if(tagmt.getAgmtStatus() != AgmtStatus.AGMT_SUBMIT){
				throw new BizException(0, "协议状态异常，不是已提交状态");
			}
			
			BigDecimal agmtDeposit = new BigDecimal(0);
			//计算协议总金额 和 保证金
			if(!CollectionUtils.isEmpty(detailList)){
				TagmtDetail td = null;
				TpdtHis tpdtHis = null;
				for(TagmtDetail tagmtDetail : detailList){
				    td = tagmtService.findTagmtDetailById(tagmtDetail.getIagmtDetail());
				    tpdtHis = tPdtService.findTpdtHisById(td.getIpdtHis());
				    agmtDeposit = agmtDeposit.add(tpdtHis.getPrice().multiply(tagmtDetail.getRate().multiply(new BigDecimal(tagmtDetail.getNum()))));
				}
				agmtDeposit = agmtDeposit.setScale(2,RoundingMode.HALF_UP);
				//保证金比例
				String depositRate = this.tsysParamService.getTsysParam("OTHER_CONF", "BZJBLTZ").getValue();
				float depositRateFloat = new Float(depositRate.substring(0, depositRate.indexOf("%")))/100;
				if(agmtDeposit.multiply(new BigDecimal(""+depositRateFloat)).setScale(2,RoundingMode.HALF_UP).compareTo(tagmt.getDeposit()) != 0){
					throw new BizException(0,"协议保证金数据异常！");
				}
			}else{
				throw new BizException(0,"协议数据异常！");
			}
		}
		
		if(agmtStatus == AgmtStatus.CONFIRM){
			if(tagmt.getAgmtStatus() != AgmtStatus.PAY_PASS){
				throw new BizException(0, "协议状态异常，不是已支付状态");
			}
		}else if (agmtStatus == AgmtStatus.HAVE_CHANGED) { //协议变更
			if(tagmt.getAgmtStatus() != AgmtStatus.SUBMIT_CHANGE){
				throw new BizException(0, "协议状态异常，不是 变更申请审核中");
			}
		}
		
		Tcust tcust = null;
		Tlog tlog = new Tlog();
		tlog.setGenTime(new Date());
		tlog.setIfk(tuser.getIuser());
		tlog.setLogType(LogType.USER);
		tlog.setOperType(OperType.AGMT_MGR);
		StringBuffer sb = new StringBuffer();
		sb.append("审核前 协议id:").append(tagmt.getIagmt()).append(",协议编号：").append(tagmt.getAgmtNo()).append(",协议状态：").append(tagmt.getAgmtStatus());
		tlog.setPreData(sb.toString());
		
		this.tagmtService.checkTagmt(tagmt, agmtStatus);
		// 更新协议明细状态
		List<TagmtDetail> tagmtDetailList = tagmtService.findTagmtDetial(tagmt);
		if (CollectionUtils.isEmpty(tagmtDetailList)) {
			throw new BizException(0, "数据异常：协议明细不存在");
		}
		for (TagmtDetail tagmtDetail : tagmtDetailList) {
			tagmtService.checkTagmtDetail(tagmtDetail, agmtDetailStatus);
		}
		
		
		TcustReg custReg = null;
		switch (agmtStatus) {
		case AGMT_QUOTA_CONFIRM:
			tlog.setMemo("用户审核协议，确认额度");
			// 确认修改协议详情
			tagmtService.confirmQuota(tagmt, detailList);

			// 生成支付通知书
			TpayNotify tpayNotify = new TpayNotify();
			tpayNotify.setIcust(tagmt.getIcust());
			tpayNotify.setPayType(PayType.BAIL);
			tpayNotify.setIfk(tagmt.getIagmt());
			tpayNotify.setMemo("客户协议保证金支付通知书添加");
			tpayNotify.setPayNotifyStatus(PayStatus.WAIT_PAY);
			tpayNotify.setIhisFk(tagmt.getIagmt());
			tpayNotifyService.save(tpayNotify);
			
			//添加跟踪信息
			tdetailTraceService.add4Tagmt(tagmt, tuser, "预约订单(编号："+tagmt.getAgmtNo()+")的额度确认["+calPdtNum(tagmtDetailList)+"]并生成账单", tagmt.getDeposit(), null);
			
//			tcust = tcustService.find(tagmt.getIcust());
			// 发送邮件 支付通知书 给 客户（取消）
//			adminMailService.sendPayNotifyMail(tcust, tpayNotify, tagmt.getDeposit());
			break;
		case CONFIRM:
			tlog.setMemo("用户审核协议");
			tagmt.setEfficientTime(new Date());
			// 发送协议确认 给 客户
			
			//添加跟踪信息
			tdetailTraceService.add4Tagmt(tagmt, tuser, "预约订单(协议编号："+tagmt.getAgmtNo()+")审核确认,预约订单生效", null, null);
			
			custReg = tcustService.getTcustReg(tagmt.getIcust());
			adminMailService.sendAgmtAutitSuccMail(custReg, tagmt);
			break;
		case HAVE_CHANGED:
			tlog.setMemo("用户审核协议");
			//添加跟踪信息
			this.tdetailTraceService.add4Tagmt(tagmt, tuser, "协议变更(协议编号："+tagmt.getAgmtNo()+")审核确认,协议已变更", null, null);
			
			custReg = this.tcustService.getTcustReg(tagmt.getIcust());
			this.adminMailService.sendAgmtChangeAuditMail(custReg, tagmt);
			break;
		default : 
			tlog.setMemo("");
		}
		StringBuffer sb2 = new StringBuffer();
		sb2.append("审核后 协议id:").append(tagmt.getIagmt()).append(",协议编号：").append(tagmt.getAgmtNo()).append(",协议状态：").append(tagmt.getAgmtStatus());
		tlog.setData(sb2.toString());
		tlogService.save(tlog);
		
		return tagmt;
	}
	
	/**
	 * 查询 是否 撤销 协议
	 * @param id
	 * @return
	 */
	public String validateDeleteAgmt(Long id){
		String result = "";
		Tagmt tagmt = tagmtService.findById(id);
		TpayNotify tpayNotify = tpayNotifyService.findByifkAndPayType(tagmt.getIagmt(), PayType.BAIL);

		//协议下有订单不允许撤销
	    if(tordService.queryTordByAgmtId(tagmt.getIagmt())){
	    	
	    //未产生金额交易
	    }else if(tagmt.getAgmtStatus()== AgmtStatus.AGMT_SUBMIT || tpayNotify == null || 
		    (tagmt.getAgmtStatus() == AgmtStatus.AGMT_QUOTA_CONFIRM && tpayNotify.getPayNotifyStatus() == PayStatus.WAIT_PAY) ){
			result = "N";
	    }else if(tagmt.getAgmtStatus() == AgmtStatus.AGMT_QUOTA_CONFIRM && tpayNotify.getPayNotifyStatus() == PayStatus.WAIT_PAY && 
	    		(tpayNotify.getHavepayAmt() == null || tpayNotify.getHavepayAmt().compareTo(BigDecimal.ZERO) ==0)){
	    	result = "N";
		}else if(tagmt.getAgmtStatus() == AgmtStatus.AGMT_QUOTA_CONFIRM && tpayNotify.getPayNotifyStatus() == PayStatus.WAIT_AUDIT){
			result = "Y";
		}else if(tagmt.getAgmtStatus() == AgmtStatus.PAY){
			result = tagmt.getRemainDeposit().toString();
		}else if(tagmt.getAgmtStatus() == AgmtStatus.PAY_PASS){
			result = tagmt.getRemainDeposit().toString();
		}else if( tagmt.getAgmtStatus() == AgmtStatus.CONFIRM){
			result = tagmt.getRemainDeposit().toString();
		}else{
			result = tagmt.getRemainDeposit().toString();
		}
	    
		return result;
		
	}
	

	public Tagmt removeCustAgmtByIagmt(Long iagmt, TuserSession tuserSession) {

		Tagmt tagmt = this.tagmtService.findById(iagmt);
		this.tagmtService.delete(tagmt);
		List<TagmtDetail> detailList = tagmtService.findTagmtDetial(tagmt);
		//撤销协议详细
		if(!CollectionUtils.isEmpty(detailList)){
			for(TagmtDetail detail : detailList){
				tagmtService.checkTagmtDetail(detail, AgmtDetailStatus.REVOKED);
			}
		}
		
		TpayNotify tpayNotify = this.tpayNotifyService.findByifkAndPayType(tagmt.getIagmt(), PayType.BAIL);
		Tlog tlog = new Tlog();
		tlog.setGenTime(new Date());
		tlog.setLogType(LogType.USER);
		tlog.setOperType(OperType.AGMT_MGR);
		tlog.setIfk(tuserSession.getTuser().getIuser());
		StringBuffer sb = new StringBuffer();
		sb.append("撤销 协议:协议id:");
		sb.append(tagmt.getIagmt());
		sb.append(",协议编号:").append(tagmt.getAgmtNo());
		
		
		
		if(tpayNotify != null){
			//如果有通知书
			tpayNotifyService.delete(tpayNotify.getIpayNotify());
			sb.append(",同时撤销付款通知书 id：").append(tpayNotify.getIpayNotify()).append(",通知书编号:").append(tpayNotify.getPayNotifyNo());
			
		}
		tlog.setData(sb.toString());
		tlog.setMemo("协议撤销");
		tlog.setPreData("");
		tlogService.save(tlog);
		
		
		//添加跟踪信息
		StringBuffer memo = new StringBuffer();
		memo.append("后台管理员 撤销 预约订单(编号：").append(tagmt.getAgmtNo()).append(",剩余保证金：").append(tagmt.getRemainDeposit()).append("),预约订单已撤销");
		tdetailTraceService.add4Tagmt(tagmt, tuserSession.getTuser(), memo.toString(), null, null);
		
		return tagmt;
		
	}
	
	/**
	 * 协议终止
	 * @param agmt
	 * @param iuser
	 * @param userName
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
	public void invalidate(Tagmt agmt, Long iuser, String userName){
		if (agmt == null) return;
		if (agmt.getAgmtStatus() != AgmtStatus.CONFIRM){
			throw new RuntimeException("协议未生效:[" + agmt.getAgmtNo() + "]");
		}
		agmt.setAgmtStatus(AgmtStatus.FINISH);
		this.tagmtService.modifyTagmt(agmt);
		
		List<TagmtDetail> tagmtDetails = this.tagmtService.findTagmtDetial(agmt);
		
		if (CollectionUtils.isEmpty(tagmtDetails)){
			throw new RuntimeException("协议明细为空：[" + agmt.getAgmtNo() + "]");
		}
		boolean success = true;
		for (TagmtDetail td: tagmtDetails){
			if (td.getRemainQuota() != 0){
				success = false;
				break;
			}
		}
		TcustReg reg = this.tcustRegService.findByIcust(agmt.getIcust());
		if (success == false){
			
			TpayNotify notify = new TpayNotify();
			notify.setHavepayAmt(agmt.getRemainDeposit());
			notify.setIcust(agmt.getIagmt());
			notify.setIfk(agmt.getIagmt());
			notify.setPayType(PayType.VIOLATE);
			this.tpayNotifyService.save(notify);
			Tpay pay = new Tpay();
			pay.setAmt(agmt.getRemainDeposit());
			pay.setIcust(agmt.getIcust());
			pay.setPayAmt(agmt.getRemainDeposit());
			pay.setPayMethod(PayMethod.DEPOSIT);
			pay.setPayStatus(PayStatus.HAVE_PAY);
			pay.setRemainAmt(new BigDecimal(0));
			this.tpayService.add(pay);
			TpayDetail pd = new TpayDetail();
			pd.setCustName(getCustName(reg));
			pd.setIcust(agmt.getIcust());
			pd.setIfk(agmt.getIagmt());
			pd.setIpay(pay.getIpay());
			pd.setIuser(iuser);
			pd.setPayAmt(pay.getPayAmt());
			pd.setPayMethod(pay.getPayMethod());
			pd.setPayStatus(PayStatus.HAVE_PAY);
			pd.setPayType(PayType.VIOLATE);
			pd.setUserName(userName);
			this.tpayDetailService.save(pd);
		}
		
		//添加跟踪信息
		StringBuffer memo = new StringBuffer();
		memo.append("系统定时 终止 预约订单(编号：").append(agmt.getAgmtNo()).append(",剩余保证金：").append(agmt.getRemainDeposit()).append("),预约订单已终止");
		Tsys tsys = this.tsysService.get(1L);
		tdetailTraceService.add4Tagmt(agmt, tsys, memo.toString(), null, null);
		
	}
	
	private String getCustName(TcustReg custReg){
		if (custReg == null) return "";
		return custReg.getCorporationName();
	}
	
	public List<Tagmt> queryExpireAgmt(){
		TagmtCondition trc = new TagmtCondition();
		Date endTime = DateUtils.truncate(new Date(), Calendar.DATE);
		trc.setEndEndTime(endTime);
		trc.setAgmtStatus(AgmtStatus.CONFIRM);
		List<Tagmt> agmts =  this.tagmtService.findTagmtListByCondition(trc);
		if (CollectionUtils.isEmpty(agmts)) return null;
		return agmts;
	}

	public List<Tagmt> findTagmtBySelect(Tagmt tagmt) {
		return tagmtService.findTagmtBySelect(tagmt);
	}

	public Tcust findByLoginName(String loginName) {
		return tcustService.getByLoginName(loginName);
	}

	public List<TagmtDetail> findTagmtDetailBySelect(TagmtDetail detail) {
		return tagmtService.findTagmtDetailBySelect(detail);
	}
	
	public TpdtHis findTpdtHisById(Long id){
		return tPdtService.findTpdtHisById(id);
	}
	
	public Object findLogisticsAmtByTagmt(Long iagmt){
		return tagmtService.findLogisticsAmtByTagmt(iagmt);
	}
	
	@SuppressWarnings("rawtypes")
	public List findLogisticsListByTagmt(Page page, Long iagmt){
		return tagmtService.findLogisticsListByTagmt(page, iagmt);
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
	 * 判断协议是否有未支付或者支付未审核通过
	 * @param iagmt 协议id
	 * @return
	 */
	public boolean hasPayNotChecked(Long iagmt) {
		TpayNofityCondition condition = new TpayNofityCondition();
		condition.setDelFlag(Boolean.FALSE);
		condition.setIfk(iagmt);
		condition.setPayNotifyStatuses(Arrays.asList(PayStatus.NO_PASS, PayStatus.PART_PAY, 
				PayStatus.WAIT_AUDIT, PayStatus.WAIT_PAY));
		condition.setPayTypes(Arrays.asList(PayType.BAIL, PayType.BAIL_SUPPLEMENT));
		List<TpayNotify> list = this.tpayNotifyService.queryListByCondition(condition);
		if (CollectionUtils.isEmpty(list)) {
			return false;
		}
		return true;
	}
	/**
	 * 
	 * @Description:设置erp订单编号
	 * @author chenwenjing
	 * @date 2014-11-27下午2:28:05
	 */
	public void setErpOrdId(Tagmt tagmt,Tuser tuser) throws BizException {
		Tagmt tamtOri = this.tagmtService.findById(tagmt.getIagmt());
		if (tamtOri == null) {
			throw new BizException(0, "设置erp订单号失败，请确认是否为有效协议");
		}
		
		Tlog tlog = new Tlog();
		tlog.setGenTime(new Date());
		tlog.setIfk(tuser.getIuser());
		tlog.setLogType(LogType.USER);
		tlog.setOperType(OperType.AGMT_MGR);
		tlog.setMemo("设置erp订单号");
		if(StringUtils.isNotBlank(tamtOri.getErpOrdId())){
			StringBuffer sbPre = new StringBuffer();
			sbPre.append("协议编号为:").append(tamtOri.getAgmtNo()).append("的原erp订单号为：").append(tamtOri.getErpOrdId());
			tlog.setPreData(sbPre.toString());
		}else{
			tlog.setPreData("");
		}
		
		tamtOri.setErpOrdId(tagmt.getErpOrdId());
		this.tagmtService.modifyTagmt(tamtOri);
		
		
		StringBuffer sb = new StringBuffer();
		sb.append("设置协议编号为:").append(tamtOri.getAgmtNo()).append("的erp订单号为：").append(tamtOri.getErpOrdId());
		tlog.setData(sb.toString());
		tlogService.save(tlog);
		
	}
}
