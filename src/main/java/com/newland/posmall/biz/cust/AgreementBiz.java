package com.newland.posmall.biz.cust;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.criterion.Order;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.TcustSession;
import org.ohuyo.rapid.base.entity.TsysParam;
import org.ohuyo.rapid.base.service.TsysParamService;
import org.ohuyo.rapid.base.servlet.AppSessionFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.newland.posmall.base.exception.BizErrCode;
import com.newland.posmall.base.exception.BizException;
import com.newland.posmall.base.service.TagmtService;
import com.newland.posmall.base.service.TcustRateService;
import com.newland.posmall.base.service.TdetailTraceService;
import com.newland.posmall.base.service.TlogService;
import com.newland.posmall.base.service.TpayNotifyService;
import com.newland.posmall.base.service.TpdtPlanMonthService;
import com.newland.posmall.base.service.TpdtService;
import com.newland.posmall.bean.basebusi.Tagmt;
import com.newland.posmall.bean.basebusi.TagmtDeposit;
import com.newland.posmall.bean.basebusi.TagmtDetail;
import com.newland.posmall.bean.basebusi.TagmtDetailHis;
import com.newland.posmall.bean.basebusi.TpayNotify;
import com.newland.posmall.bean.basebusi.Tpdt;
import com.newland.posmall.bean.basebusi.TpdtPlanMonth;
import com.newland.posmall.bean.basebusi.condition.TagmtCondition;
import com.newland.posmall.bean.basebusi.condition.TpayNofityCondition;
import com.newland.posmall.bean.common.Tlog;
import com.newland.posmall.bean.customer.Tcust;
import com.newland.posmall.bean.customer.TcustRate;
import com.newland.posmall.bean.dict.AgmtDetailStatus;
import com.newland.posmall.bean.dict.AgmtStatus;
import com.newland.posmall.bean.dict.LogType;
import com.newland.posmall.bean.dict.OperType;
import com.newland.posmall.bean.dict.PayStatus;
import com.newland.posmall.bean.dict.PayType;

@Service
@Transactional(rollbackFor = Throwable.class)
public class AgreementBiz {
	
	private static final Logger loger = LoggerFactory.getLogger(AgreementBiz.class);
	
	@Resource
	private TagmtService tagmtService;
	
	@Resource
	private TpdtPlanMonthService tpdtPlanMonthService;
	
	@Resource
	private TpdtService tpdtService;
	
	@Resource
	private TcustRateService tcustRateService;
	
	@Resource
	private TlogService tlogService;
	
	@Resource
	private TpayNotifyService tpayNotifyService;
	
	@Resource
	private TsysParamService tsysParamService;
	
	@Resource
	private CustMailService custMailService;
	
	@Resource
	private TdetailTraceService tdetailTraceService;
	
	public List<Tagmt> findAllTagmt() {
		return this.tagmtService.findAllTagmt();
	}
	
	public List<TagmtDetail> findTagmtDetailByTagmt(Tagmt tagmt) {
		return this.tagmtService.findTagmtDetial(tagmt);
	}
	
	public List<TagmtDetailHis> findTagmtDetailHis(Long iagmt, Boolean delFlag) {
		if (iagmt == null) return null;
		TagmtDetailHis condition = new TagmtDetailHis();
		condition.setIagmt(iagmt);
		condition.setDelFlag(delFlag);
		return this.tagmtService.findTagmtDetailHis(condition);
	}
	
	public TagmtDeposit findTagmtDepositByTagmtId(Long iagmt) {
		if(iagmt == null) {
			return null;
		}
		return this.tagmtService.findTagmtDepositByTagmtId(iagmt);
	}
	
	public Tagmt findById(Long id) {
		if(id == null) {
			return null;
		}
		return this.tagmtService.findTagmtById(id);
	}
	
	public List<Tagmt> findTagmtBySelect(String agmtNo, Date date) {
		Tagmt tagmt = new Tagmt();
		if(StringUtils.isNotBlank(agmtNo)) {
			tagmt.setAgmtNo(agmtNo);
		}
		if(null != date) {
			tagmt.setGenTime(date);
		}
		List<Tagmt> tagmts = this.tagmtService.findTagmtBySelect(tagmt);
		return tagmts;
	}
	
	/**
	 * 撤销协议
	 * @param iagmt
	 * @param tcust
	 * @throws BizException
	 */
	public void removeCustAgmtByTagmt(Long iagmt, Tcust tcust) throws BizException {

		Tagmt tagmt = this.findById(iagmt);
		if (tagmt == null) {
			throw new BizException(0, "协议为空");
		}
		if (tagmt.getAgmtStatus() != AgmtStatus.AGMT_SUBMIT
				&& tagmt.getAgmtStatus() != AgmtStatus.AGMT_QUOTA_CONFIRM) {
			throw new BizException(0, "协议当前状态不允许撤销");
		}
		if (!tagmt.getIcust().equals(tcust.getIcust())) {
			throw new BizException(0, "非法操作：非当前用户协议");
		}
		this.tagmtService.delete(tagmt);
		List<TagmtDetail> detailList = tagmtService.findTagmtDetial(tagmt);
		//撤销协议详细
		if(!CollectionUtils.isEmpty(detailList)){
			for(TagmtDetail detail : detailList){
				tagmtService.checkTagmtDetail(detail, AgmtDetailStatus.REVOKED);
			}
		}
		
		TpayNotify tpayNotify = this.tpayNotifyService.findByifkAndPayType(tagmt.getIagmt(), PayType.BAIL);
		TcustSession tcustSession = (TcustSession) AppSessionFilter.getAppSession();
		Tlog tlog = new Tlog();
		tlog.setGenTime(new Date());
		tlog.setLogType(LogType.CUST);
		tlog.setOperType(OperType.AGMT_MGR);
		tlog.setIfk(tcustSession.getTcust().getIcust());
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
		tlog.setMemo("客户 协议撤销");
		tlog.setPreData("");
		tlogService.save(tlog);
		
		//添加跟踪信息
		tdetailTraceService.add4Tagmt(tagmt, tcustSession.getTcust(), "客户撤销预约订单 ", null, null);
		
	}
	
	public List<Tagmt> findTagmtPageByCondition(TagmtCondition tagmtCondition,Page page) {
		tagmtCondition.setDelFlag(Boolean.FALSE);
		tagmtCondition.addOrders(Order.desc("genTime")); //按修改时间降序
		List<Tagmt> tagmts = this.tagmtService.findTagmtPageByCondition(page, tagmtCondition);
		return tagmts;
	}
	
	/**
	 * 查询出每种产品的排产计划（默认查询未来3个月）
	 * @param currentDate 计算日期
	 * @param planMonthCount 月份个数
	 * @return
	 * @throws BizException 
	 */
	public List<Tpdt> queryTpdtPlanMonth(Date currentDate, Integer planMonthCount) throws BizException {
		if(planMonthCount == null || planMonthCount <= 0) {
			planMonthCount = 3;
		}
		
		List<Tpdt> pdtList = this.tpdtService.findAll();
		for (Tpdt tpdt : pdtList) {
			boolean hasTpdtPlanMonth = false; //产品是否已制定排产计划，如果没有则不能预约订单
			TpdtPlanMonth tpm = new TpdtPlanMonth();
			TpdtPlanMonth[] tpdtPlanMonthArray = new TpdtPlanMonth[planMonthCount];
			tpm.setIpdt(tpdt.getIpdt());
			Calendar cal = Calendar.getInstance();
			if(currentDate != null) {
				cal.setTime(currentDate);
			}
			for (int i = 0; i < planMonthCount; i++) { //查询未来三个月的排产计划
				cal.add(Calendar.MONTH, 1);
				int year = cal.get(Calendar.YEAR);
				int month = cal.get(Calendar.MONTH) + 1;
				tpm.setYear(year);
				tpm.setMonth(month);
				List<TpdtPlanMonth> plansList = this.tpdtPlanMonthService.findBySelect(tpm);
				if(plansList != null && plansList.size() >= 1) {
					tpdtPlanMonthArray[i] = plansList.get(0);
					hasTpdtPlanMonth = true;
				}
			}
			if(!hasTpdtPlanMonth) {
				throw new BizException(BizErrCode.PLAN_NEED, "产品尚未制定排产计划！");
			}
			tpdt.setTpdtPlanMonthArray(tpdtPlanMonthArray);
		}
		
		return pdtList;
	}
	
	public List<Tpdt> queryTpdtList() {
		return this.tpdtService.findAll();
	}
	
	/**
	 * 根据客户的折扣率设置产品单价
	 * @param tpdts 产品列表
	 * @param tcust 客户
	 */
	public void setTpdtPrice(List<Tpdt> tpdts, Tcust tcust) {
		if(tpdts == null || tpdts.size() < 1 || tcust == null || tcust.getIcust() == null) {
			return;
		}
		for (Tpdt tpdt : tpdts) {
			setTpdtPriceByOne(tpdt, tcust);
		}
	}
	
	/**
	 * 设置产品单价
	 * @param tpdt
	 * @param tcust
	 */
	public void setTpdtPriceByOne(Tpdt tpdt, Tcust tcust) {
		if(tcust == null || tcust.getIcust() == null) {
			return;
		}
		tpdt.setPrice(this.tpdtService.find(tpdt.getIpdt()).getPrice());
		TcustRate rate = new TcustRate();
		rate.setIcust(tcust.getIcust());
		rate.setIpdt(tpdt.getIpdt());
		List<TcustRate> rates = this.tcustRateService.findByCondition(rate);
		if(rates == null || rates.size() < 1 || rates.get(0) == null) {
			tpdt.setCustPdtRate(new BigDecimal(1.00));
			tpdt.setPriceAfterRate(tpdt.getPrice());
		} else {
			BigDecimal custPdtRate = rates.get(0).getRate();
			tpdt.setCustPdtRate(custPdtRate);
			tpdt.setPriceAfterRate(tpdt.getPrice().multiply(custPdtRate).setScale(2, RoundingMode.HALF_UP));
		}
	}
	
	/**
	 * 协议签署
	 * @param tpdts
	 * @param tcust
	 * @param startDateOfAgmt
	 * @param endDateOfAgmt
	 * @return 协议
	 * @throws BizException
	 */
	public Tagmt signAgreement(List<Tpdt> tpdts, Tcust tcust,
			String startDateOfAgmt, String endDateOfAgmt) throws BizException{
		if(tpdts == null || tpdts.size() < 1) {
			throw new BizException(BizErrCode.SYS_ERR, "数据为空！");
		}
		if(StringUtils.isBlank(startDateOfAgmt)) {
			throw new BizException(0, "协议起始日期获取失败！");
		}
		if(StringUtils.isBlank(endDateOfAgmt)) {
			throw new BizException(0, "协议结束日期获取失败！");
		}
		
		//计算保证金
		BigDecimal allAmt = new BigDecimal(0);
		for (Tpdt tpdt : tpdts) {
			if (tpdt.getPdtAgmtNum() == null || tpdt.getPdtAgmtNum() == 0) {
				continue;
			}
			allAmt = allAmt.add(tpdt.getPriceAfterRate().multiply(BigDecimal.valueOf(tpdt.getPdtAgmtNum())).setScale(2, RoundingMode.HALF_UP));
		}
		String depositRateStr = this.tsysParamService.getTsysParam("OTHER_CONF", "BZJBLTZ").getValue();
		float depositRateFloat = new Float(depositRateStr.substring(0, depositRateStr.indexOf("%")))/100;
		BigDecimal depositRate = new BigDecimal(depositRateFloat).setScale(2, RoundingMode.HALF_UP);
		BigDecimal deposit = allAmt.multiply(depositRate).setScale(2, RoundingMode.HALF_UP);
		
		Tagmt newTagmt = new Tagmt();
		newTagmt.setDeposit(deposit);
		newTagmt.setIcust(tcust.getIcust());
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			newTagmt.setStartTime(format.parse(startDateOfAgmt));
			newTagmt.setEndTime(format.parse(endDateOfAgmt));
		} catch (Exception e) {
			throw new BizException(0, "协议起始、结束日期格式化失败，请联系管理员");
		}
		newTagmt.setUsedDeposit(new BigDecimal(0.00));
		newTagmt.setRemainDeposit(newTagmt.getDeposit().subtract(newTagmt.getUsedDeposit()));
		newTagmt.setAgmtStatus(AgmtStatus.AGMT_SUBMIT);
		this.tagmtService.addTagmt(newTagmt); //添加协议
		
		StringBuffer tpdtBuffer = new StringBuffer();
		int tpdtItemNumInAgmt = 0; //协议中签署的产品种类数量
		for (Tpdt tpdt : tpdts) { //添加协议明细
			if(tpdt.getPdtAgmtNum() == null || 0 == tpdt.getPdtAgmtNum()) {
				continue;
			}
			TagmtDetail tDetail = new TagmtDetail();
			tDetail.setIagmt(newTagmt.getIagmt());
			tDetail.setIpdt(tpdt.getIpdt());
			tDetail.setIpdtHis(tpdt.getIpdtHis()); //为了协议签署之后可以查询到当时的产品信息，关联历史表
			tDetail.setNum(tpdt.getPdtAgmtNum());
			tDetail.setRate(tpdt.getCustPdtRate());
			tDetail.setAgmtDetailStatus(AgmtDetailStatus.AGMT_SUBMIT);
			tDetail.setStartTime(newTagmt.getStartTime());
			tDetail.setEndTime(newTagmt.getEndTime());
			tDetail.setUsedQuota(0);
			tDetail.setRemainQuota(tpdt.getPdtAgmtNum());
			this.tagmtService.addTagmtDetail(tDetail);
			tpdtItemNumInAgmt ++;
			tpdtBuffer.append("产品[").append(tpdt.getIpdt() + ",").append(tpdt.getName() + "]").append(tpdt.getPdtAgmtNum() + "台；");
		}
		if(tpdtItemNumInAgmt == 0) {
			throw new BizException(BizErrCode.SYS_ERR, "协议未签署订货量！");
		}
		
		TagmtDeposit tagmtDeposit = new TagmtDeposit();
		tagmtDeposit.setIagmt(newTagmt.getIagmt());
		tagmtDeposit.setAgmtNo(newTagmt.getAgmtNo());
		tagmtDeposit.setDeposit(newTagmt.getDeposit());
		tagmtDeposit.setIcust(tcust.getIcust());
		tagmtDeposit.setUsedDeposit(newTagmt.getUsedDeposit());
		tagmtDeposit.setRemainDeposit(tagmtDeposit.getDeposit().subtract(tagmtDeposit.getUsedDeposit()));
		tagmtDeposit.setVer(newTagmt.getVersion());
		this.tagmtService.addTagmtDeposit(tagmtDeposit); //添加协议保证金历史
		
		Tlog tlog = new Tlog();
		StringBuffer data = new StringBuffer();
		data.append("签署人[").append(tcust.getLoginName() + "]；").
		append("协议编号[").append(newTagmt.getAgmtNo() + "]；").
		append(tpdtBuffer).append("保证金[¥").append(newTagmt.getDeposit() + "]；");
		tlog.setData(data.toString());
		tlog.setIfk(tcust.getIcust());
		tlog.setLogType(LogType.CUST);
		tlog.setMemo("客户预约订单");
		tlog.setOperType(OperType.AGMT_MGR);
		tlog.setPreData("");
		this.tlogService.save(tlog);
		
		List<TagmtDetail> tagmtDetailList = tagmtService.findTagmtDetial(newTagmt);
		//添加跟踪信息
		tdetailTraceService.add4Tagmt(newTagmt, tcust, "客户预约订单(编号："+newTagmt.getAgmtNo()+",预约["+calPdtNum(tagmtDetailList)+"])", null, null);
		
		//协议提交通知,由客户发起到事业部
		this.custMailService.sendAgmtNotify(tcust, newTagmt);
		
		return newTagmt;
	}
	
	/**
	 * 获取协议起始日期
	 * @return
	 */
	public Date getStartDateOfAgmt() {
		Calendar startDate = Calendar.getInstance();
		int spaceDay4Agmt = 5;
		TsysParam param = this.tsysParamService.getTsysParam("OTHER_CONF", "SPACE_DAY_4_AGMT");
		if(param == null || StringUtils.isBlank(param.getValue())) {
			loger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>系统参数【预约订单起始日期距下单日天数】未设置！");
		}else {
			try {
				spaceDay4Agmt = Integer.valueOf(param.getValue());
			} catch (NumberFormatException e) {
				loger.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>系统参数【预约订单起始日期距下单日天数】设置有误，导致格式化失败！");
			}
		}
		startDate.add(Calendar.DATE, spaceDay4Agmt);
		return DateUtils.truncate(startDate.getTime(), Calendar.DATE);
	}
	
	/**
	 * 获取协议终止日期
	 * @param startDate
	 * @return
	 */
	public Date getEndDateOfAgmt(Date startDate) {
		if(startDate == null)
			return null;
		String monthCount = this.tsysParamService.getTsysParam("OTHER_CONF", "XYYXQKD_Y").getValue();
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(startDate);
		endDate.add(Calendar.MONTH, Integer.valueOf(monthCount));
		endDate.add(Calendar.DATE, -1);
		return DateUtils.truncate(endDate.getTime(), Calendar.DATE);
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
	 * 协议变更
	 * @param tpdts
	 * @param iagmt
	 * @param tcust
	 * @return
	 * @throws BizException
	 */
	public Tagmt agmtModify(List<Tpdt> tpdts, Long iagmt, Tcust tcust) throws BizException{
		
		if(CollectionUtils.isEmpty(tpdts)) {
			throw new BizException(BizErrCode.SYS_ERR, "数据为空！");
		}
		if(iagmt == null) {
			throw new BizException(0, "协议主键获取失败，请重新操作");
		}
		Tagmt tagmtDB = this.tagmtService.findById(iagmt);
		if(tagmtDB == null) {
			throw new BizException(0, "协议关联失败");
		}
		if(tagmtDB.getDelFlag().equals(Boolean.TRUE)) {
			throw new BizException(0, "协议已撤销");
		}

		if(!tagmtDB.getAgmtStatus().equals(AgmtStatus.CONFIRM)) {
			throw new BizException(0, "协议当前状态，不能进行变更操作");
		}
		List<TagmtDetail> tagmtDetailListDB = this.tagmtService.findTagmtDetial(tagmtDB);
		Map<Long, TagmtDetail> pdtAndTagmtDetailMap = new HashMap<Long, TagmtDetail>();
		
		int allChangedPdtNum = 0; //变更量总合
		StringBuffer tpdtBuffer = new StringBuffer("");
		for (Tpdt tpdt : tpdts) {
			if (tpdt.getPdtAgmtNum() == null || tpdt.getPdtAgmtNum() == 0) {
				continue;
			}
			allChangedPdtNum = allChangedPdtNum + tpdt.getPdtAgmtNum();
			
			tpdtBuffer.append("产品[").append(tpdt.getIpdt() + ",").append(tpdt.getName() + "]").append(tpdt.getPdtAgmtNum() + "台；");			
		}
		if(allChangedPdtNum <= 0) {
			throw new BizException(0, "变更量为0，不能提交变更");
		}
		int canChangedPdtNum = 0; //允许变更量
		for (TagmtDetail tagmtDetail : tagmtDetailListDB) {
			canChangedPdtNum = canChangedPdtNum + tagmtDetail.getRemainQuota();
			
			pdtAndTagmtDetailMap.put(tagmtDetail.getIpdt(), tagmtDetail);
		}
		if(allChangedPdtNum != canChangedPdtNum) {
			throw new BizException(0, "变更量合计必须等于可变更订货量【" + canChangedPdtNum + "】台");
		}
		
		// 如果之前存在变更数据，删除（针对多次变更问题）
		TagmtDetailHis condition = new TagmtDetailHis();
		condition.setIagmt(iagmt);
		condition.setDelFlag(Boolean.FALSE);
		List<TagmtDetailHis> detailHisList = this.tagmtService.findTagmtDetailHis(condition);
		if (!CollectionUtils.isEmpty(detailHisList)) {
			for (TagmtDetailHis tagmtDetailHis : detailHisList) {
				this.tagmtService.deleteTagmtDetailHis(tagmtDetailHis);
			}
		}
		
		// 变更前保存协议明细数据
		for (TagmtDetail tagmtDetail : tagmtDetailListDB) {
			TagmtDetailHis detailHis = new TagmtDetailHis();
			detailHis.setAgmtDetailStatus(tagmtDetail.getAgmtDetailStatus());
			detailHis.setEndTime(tagmtDetail.getEndTime());
			detailHis.setIagmt(tagmtDetail.getIagmt());
			detailHis.setIagmtDetail(tagmtDetail.getIagmtDetail());
			detailHis.setIpdt(tagmtDetail.getIpdt());
			detailHis.setIpdtHis(tagmtDetail.getIpdtHis());
			detailHis.setNum(tagmtDetail.getNum());
			detailHis.setRate(tagmtDetail.getRate());
			detailHis.setRemainQuota(tagmtDetail.getRemainQuota());
			detailHis.setStartTime(tagmtDetail.getStartTime());
			detailHis.setUsedQuota(tagmtDetail.getUsedQuota());
			detailHis.setVer(tagmtDetail.getVersion());
			this.tagmtService.addTagmtDetailHis(detailHis);
		}
		
		// 协议变更：修改协议明细数据
		// 保证金计算比率
		String depositRateStr = this.tsysParamService.getTsysParam("OTHER_CONF", "BZJBLTZ").getValue();
		float depositRateFloat = new Float(depositRateStr.substring(0, depositRateStr.indexOf("%")))/100;
		BigDecimal depositRate = new BigDecimal(depositRateFloat).setScale(2, RoundingMode.HALF_UP);
		
		BigDecimal depositDifferential = new BigDecimal(0); //保证金差价：大于0表示保证金不足，需要补交；小于0表示保证金多余，需要返还
		int equalNum = 0;
		
		this.setTpdtPrice(tpdts, tcust); //计算产品单价
		
		for (Tpdt tpdt : tpdts) {
			boolean choosed = true;
			if (tpdt.getPdtAgmtNum() == null || tpdt.getPdtAgmtNum() == 0) {
				choosed = false;
			}
			TagmtDetail tagmtDetailDB = pdtAndTagmtDetailMap.get(tpdt.getIpdt());
			if (choosed) { //变更有包含该产品
				if (tagmtDetailDB == null) { //原协议不包含该型号产品
					if (tpdt.getPdtAgmtNum() % 100 != 0) {
						throw new BizException(0, "产品变化量必须整百");
					}
					
					// 新增一条协议明细
					TagmtDetail tDetail = new TagmtDetail();
					tDetail.setIagmt(tagmtDB.getIagmt());
					tDetail.setIpdt(tpdt.getIpdt());
					tDetail.setIpdtHis(tpdt.getIpdtHis()); //为了协议签署之后可以查询到当时的产品信息，关联历史表
					tDetail.setNum(tpdt.getPdtAgmtNum());
					tDetail.setRate(tpdt.getCustPdtRate());
					tDetail.setAgmtDetailStatus(AgmtDetailStatus.SUBMIT_CHANGE);
					tDetail.setStartTime(tagmtDB.getStartTime());
					tDetail.setEndTime(tagmtDB.getEndTime());
					tDetail.setUsedQuota(0);
					tDetail.setRemainQuota(tpdt.getPdtAgmtNum());
					this.tagmtService.addTagmtDetail(tDetail);
					
					depositDifferential = depositDifferential.add(tpdt.getPriceAfterRate()
							.multiply(new BigDecimal(tpdt.getPdtAgmtNum()))
							.multiply(depositRate)
							.setScale(2, RoundingMode.HALF_UP));
				} else if (tagmtDetailDB.getRemainQuota().equals(tpdt.getPdtAgmtNum())) { //没有变化
					equalNum++;
				} else { //该类型产品数量有变化
					if ((tpdt.getPdtAgmtNum() - tagmtDetailDB.getRemainQuota()) % 100 != 0) {
						throw new BizException(0, "产品变化量必须整百");
					}
					
					depositDifferential = depositDifferential.add(tpdt
							.getPriceAfterRate()
							.multiply(new BigDecimal(tpdt.getPdtAgmtNum()
											- tagmtDetailDB.getRemainQuota()))
							.multiply(depositRate)
							.setScale(2, RoundingMode.HALF_UP));
					
					// 修改明细数量
					tagmtDetailDB.setAgmtDetailStatus(AgmtDetailStatus.SUBMIT_CHANGE);
					tagmtDetailDB.setRemainQuota(tpdt.getPdtAgmtNum());
					tagmtDetailDB.setNum(tagmtDetailDB.getUsedQuota() + tagmtDetailDB.getRemainQuota());
					this.tagmtService.modifyTagmtDetail(tagmtDetailDB);
				}
			} else { //变更没有包含该产品
				if (tagmtDetailDB == null) { //原协议不包含该型号产品
					continue;
				} else {
					if (tagmtDetailDB.getRemainQuota() % 100 != 0) {
						throw new BizException(0, "产品变化量必须整百");
					}
					
					// 删除该条协议明细数据
					tagmtDetailDB.setDelFlag(Boolean.TRUE);
					this.tagmtService.modifyTagmtDetail(tagmtDetailDB);
					
					depositDifferential = depositDifferential.add(tpdt.getPriceAfterRate()
							.multiply(new BigDecimal(-tagmtDetailDB.getRemainQuota()))
							.multiply(depositRate)
							.setScale(2, RoundingMode.HALF_UP));
				}
			}
		}
		if (equalNum == tagmtDetailListDB.size()) {
			throw new BizException(0, "变化量为0，无效变更");
		}
		
		// 付款通知书生成
		if (depositDifferential.compareTo(new BigDecimal(0)) > 0) { //保证金不足，需要补交，生成付款通知书
			TpayNotify newTpayNotify = new TpayNotify();
			newTpayNotify.setIcust(tcust.getIcust());
			newTpayNotify.setIfk(tagmtDB.getIagmt());
			newTpayNotify.setIhisFk(tagmtDB.getIagmt());
			newTpayNotify.setMemo("协议变更，补交保证金");
			newTpayNotify.setPayNotifyStatus(PayStatus.WAIT_PAY);
			newTpayNotify.setPayType(PayType.BAIL_SUPPLEMENT);
			this.tpayNotifyService.save(newTpayNotify);
		}
		
		// 修改协议
		tagmtDB.setAgmtStatus(AgmtStatus.SUBMIT_CHANGE);
		tagmtDB.setRedundantDeposit(depositDifferential.negate());
		tagmtDB.setRemainDeposit(tagmtDB.getRemainDeposit().add(depositDifferential));
		tagmtDB.setDeposit(tagmtDB.getUsedDeposit().add(tagmtDB.getRemainDeposit()));
		this.tagmtService.modifyTagmt(tagmtDB);
		
		// 添加保证金变化历史记录
		TagmtDeposit tagmtDeposit = new TagmtDeposit();
		tagmtDeposit.setIagmt(tagmtDB.getIagmt());
		tagmtDeposit.setAgmtNo(tagmtDB.getAgmtNo());
		tagmtDeposit.setDeposit(tagmtDB.getDeposit());
		tagmtDeposit.setIcust(tcust.getIcust());
		tagmtDeposit.setUsedDeposit(tagmtDB.getUsedDeposit());
		tagmtDeposit.setRemainDeposit(tagmtDeposit.getDeposit().subtract(tagmtDeposit.getUsedDeposit()));
		tagmtDeposit.setVer(tagmtDB.getVersion());
		this.tagmtService.addTagmtDeposit(tagmtDeposit); //添加协议保证金历史
		
		// 记录日志
		Tlog tlog = new Tlog();
		StringBuffer data = new StringBuffer();
		data.append("操作人[")
		.append(tcust.getLoginName() + "]；")
		.append("协议[").append(tagmtDB.getIagmt() + "，")
		.append(tagmtDB.getAgmtNo() + "]；").append("变更：" + tpdtBuffer.toString());
		if (depositDifferential.compareTo(new BigDecimal(0)) < 0) { //保证金有多余
			data.append("保证金多余：" + depositDifferential.negate());
		} else {
			data.append("应补交保证金：" + depositDifferential.negate());
		}
		tlog.setData(data.toString());
		tlog.setIfk(tcust.getIcust());
		tlog.setLogType(LogType.CUST);
		tlog.setMemo("客户协议变更");
		tlog.setOperType(OperType.AGMT_MGR);
		tlog.setPreData("");
		this.tlogService.save(tlog);
		
		//添加跟踪信息
		tdetailTraceService.add4Tagmt(tagmtDB, tcust, "客户协议变更(编号："+tagmtDB.getAgmtNo()+", 变更明细["+tpdtBuffer.toString()+"])", null, null);
		
		// TODO 协议变更邮件通知
		
		return tagmtDB;
	}
	
	/**
	 * 取消协议变更
	 * @param iagmt
	 * @param tcust
	 * @return
	 * @throws BizException
	 */
	public Tagmt cancelAgmtModify(Long iagmt, Tcust tcust) throws BizException {
		if (iagmt == null) {
			throw new BizException(0, "协议主键为空");
		}
		Tagmt tagmtDB = this.tagmtService.findById(iagmt);
		if (tagmtDB == null) {
			throw new BizException(0, "协议不存在");
		}
		if (!tagmtDB.getDelFlag().equals(Boolean.FALSE)) {
			throw new BizException(0, "协议已删除在");
		}
		if (!tagmtDB.getAgmtStatus().equals(AgmtStatus.SUBMIT_CHANGE)) { //变更申请审核中才允许取消
			throw new BizException(0, "协议当前状态不符");
		}
		if (!tagmtDB.getIcust().equals(tcust.getIcust())) {
			throw new BizException(0, "协议关联失败");
		}
		
		BigDecimal redundant = tagmtDB.getRedundantDeposit();
		if (redundant.compareTo(BigDecimal.ZERO) < 0) { //协议变更需要补交保证金的
			if (this.hasPaid4RedundantDeposit(iagmt)) {
				throw new BizException(0, "补交保证金已缴纳，不能撤销变更");
			}
			
			// 撤销付款通知书
			TpayNotify notifyDB = this.tpayNotifyService.findByifkAndPayType(iagmt, PayType.BAIL_SUPPLEMENT);
			this.tpayNotifyService.delete(notifyDB);
		}
		
		// 删除变更的数据
		List<TagmtDetail> details = this.tagmtService.findTagmtDetial(tagmtDB);
		for (TagmtDetail tagmtDetail : details) {
			this.tagmtService.deleteTagmtDetail(tagmtDetail);
		}
		
		// 恢复原来的数据
		TagmtDetailHis condition = new TagmtDetailHis();
		condition.setIagmt(iagmt);
		condition.setDelFlag(Boolean.FALSE);
		List<TagmtDetailHis> detailHisList = this.tagmtService.findTagmtDetailHis(condition);
		for (TagmtDetailHis tagmtDetailHis : detailHisList) {
			TagmtDetail tDetail = new TagmtDetail();
			tDetail.setIagmt(tagmtDetailHis.getIagmt());
			tDetail.setIpdt(tagmtDetailHis.getIpdt());
			tDetail.setIpdtHis(tagmtDetailHis.getIpdtHis());
			tDetail.setNum(tagmtDetailHis.getNum());
			tDetail.setRate(tagmtDetailHis.getRate());
			tDetail.setAgmtDetailStatus(tagmtDetailHis.getAgmtDetailStatus());
			tDetail.setStartTime(tagmtDetailHis.getStartTime());
			tDetail.setEndTime(tagmtDetailHis.getEndTime());
			tDetail.setUsedQuota(tagmtDetailHis.getUsedQuota());
			tDetail.setRemainQuota(tagmtDetailHis.getRemainQuota());
			this.tagmtService.addTagmtDetail(tDetail);
		}
		
		// 删除协议变更明细数据
		for (TagmtDetailHis tagmtDetailHis : detailHisList) {
			this.tagmtService.deleteTagmtDetailHis(tagmtDetailHis);
		}
		
		// 修改协议
		tagmtDB.setAgmtStatus(AgmtStatus.CONFIRM);
		tagmtDB.setRemainDeposit(tagmtDB.getRemainDeposit().add(redundant));
		tagmtDB.setDeposit(tagmtDB.getUsedDeposit().add(tagmtDB.getRemainDeposit()));
		tagmtDB.setRedundantDeposit(BigDecimal.ZERO);
		this.tagmtService.modifyTagmt(tagmtDB);
		
		// 添加保证金变化历史记录
		TagmtDeposit tagmtDeposit = new TagmtDeposit();
		tagmtDeposit.setIagmt(tagmtDB.getIagmt());
		tagmtDeposit.setAgmtNo(tagmtDB.getAgmtNo());
		tagmtDeposit.setDeposit(tagmtDB.getDeposit());
		tagmtDeposit.setIcust(tcust.getIcust());
		tagmtDeposit.setUsedDeposit(tagmtDB.getUsedDeposit());
		tagmtDeposit.setRemainDeposit(tagmtDeposit.getDeposit().subtract(tagmtDeposit.getUsedDeposit()));
		tagmtDeposit.setVer(tagmtDB.getVersion());
		this.tagmtService.addTagmtDeposit(tagmtDeposit); //添加协议保证金历史
		
		// 记录日志
		Tlog tlog = new Tlog();
		StringBuffer data = new StringBuffer();
		data.append("操作人[")
		.append(tcust.getLoginName() + "]；")
		.append("协议[").append(tagmtDB.getIagmt() + "，")
		.append(tagmtDB.getAgmtNo() + "]；");
		tlog.setData(data.toString());
		tlog.setIfk(tcust.getIcust());
		tlog.setLogType(LogType.CUST);
		tlog.setMemo("客户取消协议变更");
		tlog.setOperType(OperType.AGMT_MGR);
		tlog.setPreData("");
		this.tlogService.save(tlog);
		
		return tagmtDB;
	}
	
	/**
	 * 是否已支付补交保证金
	 * @param iagmt
	 * @return
	 */
	private boolean hasPaid4RedundantDeposit(Long iagmt) {
		TpayNofityCondition condition = new TpayNofityCondition();
		condition.setDelFlag(Boolean.FALSE);
		condition.setIfk(iagmt);
		condition.setPayTypes(Arrays.asList(PayType.BAIL_SUPPLEMENT));
		List<TpayNotify> list = this.tpayNotifyService.queryListByCondition(condition);
		for (TpayNotify tpayNotify : list) {
			if (!tpayNotify.getPayNotifyStatus().equals(PayStatus.WAIT_PAY) && 
					!tpayNotify.getPayNotifyStatus().equals(PayStatus.REVOKED)) {
				return true;
			}
		}
		return false;
	}
}
