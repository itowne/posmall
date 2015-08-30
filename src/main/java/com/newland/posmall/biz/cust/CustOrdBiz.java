package com.newland.posmall.biz.cust;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Order;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.entity.TsysParam;
import org.ohuyo.rapid.base.service.TsysParamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.newland.posmall.base.exception.BizErrCode;
import com.newland.posmall.base.exception.BizException;
import com.newland.posmall.base.service.TagmtService;
import com.newland.posmall.base.service.TcustStockService;
import com.newland.posmall.base.service.TdetailTraceService;
import com.newland.posmall.base.service.TlogService;
import com.newland.posmall.base.service.TnoSegCfgService;
import com.newland.posmall.base.service.TnoSegCfgService.PdtNoSeq;
import com.newland.posmall.base.service.TordDetailPdtService;
import com.newland.posmall.base.service.TordDetailService;
import com.newland.posmall.base.service.TordService;
import com.newland.posmall.base.service.TpayNotifyService;
import com.newland.posmall.base.service.TpdtPlanDayQuotaService;
import com.newland.posmall.base.service.TpdtService;
import com.newland.posmall.bean.basebusi.OrdPdtModel4Page;
import com.newland.posmall.bean.basebusi.OrdPdtModelDaily;
import com.newland.posmall.bean.basebusi.OrdPdtModelList4Page;
import com.newland.posmall.bean.basebusi.Tagmt;
import com.newland.posmall.bean.basebusi.TagmtDetail;
import com.newland.posmall.bean.basebusi.Tord;
import com.newland.posmall.bean.basebusi.TordDetail;
import com.newland.posmall.bean.basebusi.TordDetailPdt;
import com.newland.posmall.bean.basebusi.TordHis;
import com.newland.posmall.bean.basebusi.TpayNotify;
import com.newland.posmall.bean.basebusi.Tpdt;
import com.newland.posmall.bean.basebusi.TpdtHis;
import com.newland.posmall.bean.basebusi.TpdtPlanDayQuota;
import com.newland.posmall.bean.basebusi.condition.TagmtCondition;
import com.newland.posmall.bean.basebusi.condition.TordCondition;
import com.newland.posmall.bean.common.Tlog;
import com.newland.posmall.bean.customer.Tcust;
import com.newland.posmall.bean.customer.TcustReg;
import com.newland.posmall.bean.dict.AgmtDetailStatus;
import com.newland.posmall.bean.dict.AgmtStatus;
import com.newland.posmall.bean.dict.DictType;
import com.newland.posmall.bean.dict.DownLoadStat;
import com.newland.posmall.bean.dict.LogType;
import com.newland.posmall.bean.dict.OperType;
import com.newland.posmall.bean.dict.OrdDetailPdtType;
import com.newland.posmall.bean.dict.OrdStatus;
import com.newland.posmall.bean.dict.PayStatus;
import com.newland.posmall.bean.dict.PayType;
import com.newland.posmall.bean.dict.ValidStatus;
import com.newland.posmall.bean.dict.YesNoType;
import com.newland.posmall.biz.common.MapBiz;

@Service
@Transactional
public class CustOrdBiz {
	
	private static final Logger logger = LoggerFactory.getLogger(CustOrdBiz.class);

	@Resource
	private TsysParamService tsysParamService;
	
	@Resource
	private TordService tordService;
	
	@Resource
	private TpdtService tpdtService;
	
	@Resource
	private TagmtService tagmtService;
	
	@Resource
	private TordDetailPdtService tordDetailPdtService;
	
	@Resource
	private TordDetailService tordDetailService;
	
	@Resource
	private TpayNotifyService tpayNotifyService;
	
	@Resource
	private TcustStockService tcustStockService;
	
	@Resource
	private TpdtPlanDayQuotaService tpdtPlanDayQuotaService;
	
	@Resource
	private TlogService tlogService;
	
	@Resource
	private TnoSegCfgService tnoSegCfgService;
	
	@Resource
	private CustMailService custMailService;
	
	@Resource
	private TdetailTraceService tdetailTraceService;
	
	@Resource
	private MapBiz mapBiz;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	public Tord queryTordByIord(Long id) {
		if(id == null) {
			return null;
		}
		return this.tordService.find(id);
	}
	
	public Tagmt queryAgmtById(Long iagmt){
		if(iagmt == null) {
			return null;
		}
		return this.tagmtService.findById(iagmt);
	}
	
	/**
	 * 查询订单详情（并关联设置订单产品明细）
	 * @param tordId 订单id
	 * @return
	 */
	public List<TordDetail> queryTordDetailList(Long tordId) {
		if(tordId == null) {
			return null;
		}
		TordDetail detail = new TordDetail();
		detail.setIord(tordId);
		List<TordDetail> detailList = this.tordDetailService.findSelect(detail);
		if(detailList == null || detailList.size() < 1 || detailList.get(0) == null) {
			return null;
		}
		for (TordDetail tordDetail : detailList) {
			TpdtHis his = this.tpdtService.findTpdtHisById(tordDetail.getIpdtHis());
			TordDetailPdt tdp = new TordDetailPdt();
			tdp.setIord(tordId);
			tdp.setIpdt(his.getIpdt());
			List<TordDetailPdt> tdpList = this.tordDetailPdtService.findSelect(tdp);
			tordDetail.setPdtName(his.getName());
			tordDetail.setTordDetailPdtList(tdpList);
		}
		return detailList;
	}
	
	public List<Tord> findListByCon(TordCondition tordCondition,Page page){
		tordCondition.setDelFlag(false);
		tordCondition.addOrders(Order.desc("iord"));
		List<Tord> tordList = this.tordService.findTordByCon(tordCondition, page);
		if (CollectionUtils.isEmpty(tordList) == false) {
			for (Tord tord : tordList) {
				tord.setTagmt(this.tagmtService.findById(tord.getIagmt()));
			}
		}
		return tordList;
	}
	
	/**
	 * 查询结束日期在今天之后的协议客户列表
	 * @param tcust
	 * @return
	 */
	public List<Tagmt> queryTagmtList(Tcust tcust){
		
		TagmtCondition tagmtCondition = new TagmtCondition();
		tagmtCondition.setIcust(tcust.getIcust());
		tagmtCondition.setEndTime(DateUtils.truncate(new Date(), Calendar.DATE));
//		tagmtCondition.setAgmtStatus(AgmtStatus.CONFIRM);
		tagmtCondition.setAgmtStatusList(Arrays.asList(AgmtStatus.CONFIRM, AgmtStatus.HAVE_CHANGED));
		tagmtCondition.addOrders(Order.desc("iagmt"));
		List<Tagmt> tagmtList = this.tagmtService.findTagmtListByCondition(tagmtCondition);
		
		return tagmtList;
	}
	
	/**
	 * 根据协议获取点单页面数据
	 * @param tagmt 协议
	 * @return
	 * @throws BizException
	 */
	public List<OrdPdtModel4Page> queryObjectArr(Tagmt tagmt) throws BizException{
		
		List<OrdPdtModel4Page> modelList = new ArrayList<OrdPdtModel4Page>();
		
		int spaceDay = 2;
		TsysParam param = this.tsysParamService.getTsysParam("OTHER_CONF", "SPACE_DAY_4_ORDER"); //查询点单间隔天数，即可以点几天之后的单
		if(param != null && StringUtils.isNotBlank(param.getValue())) {
			String spaceStr = param.getValue();
			try {
				spaceDay = Integer.valueOf(spaceStr);
			} catch (Exception e) {
				logger.error("系统参数【点单间隔天数】转化异常", e);
			}
		}
		
		int releaseDay = 5;
		param = this.tsysParamService.getTsysParam("OTHER_CONF", "RELEASE_DAY_4_ORDER"); //查询点单释放天数，即可以点几天的单
		if(param != null && StringUtils.isNotBlank(param.getValue())) {
			String releaseStr = param.getValue();
			try {
				releaseDay = Integer.valueOf(releaseStr);
			} catch (Exception e) {
				logger.error("系统参数【点单释放天数】转化异常", e);
			}
		}
		
		//结合协议起始时间，判断可点单的时间范围
		Date startTime = tagmt.getStartTime();
		Date endTime = tagmt.getEndTime();
		
//		//是否显示库存（如果下单日期在协议起始日期之前，则不显示库存）
//		boolean isShowStock = true;
//		if (startTime.after(new Date())) {
//			isShowStock = false;
//		}
		
		/*******************************************************************/
		/**遍历规则：以协议明细（即具体产品）为最外层循环；
		 * 1、先判断是否显示库存：如果下单日期在协议起始日期之前，则不显示库存；
		 * 2、释放N（系统参数）天可以进行预订；
		 * 3、如果N天中，某款产品某天没有排产计划，则该产品顺延一天**/
		/*******************************************************************/
		List<TagmtDetail> tagmtDetailList = this.tagmtService.findTagmtDetial(tagmt);
		for(TagmtDetail tagmtDetail : tagmtDetailList){
			OrdPdtModel4Page model = new OrdPdtModel4Page();
			List<OrdPdtModelDaily> modelDailyList = new ArrayList<OrdPdtModelDaily>();
			
			Long ipdt = tagmtDetail.getIpdt();
			Long ipdtHis = tagmtDetail.getIpdtHis();
			Tpdt tpdt = this.tpdtService.find(ipdt);
			model.setIpdt(ipdt);
			model.setPdtName(tpdt.getName());
			model.setIpdtHis(ipdtHis);
			model.setPrice(tpdt.getPrice());
			model.setRate(tagmtDetail.getRate());
			model.setPriceAfterRate(tpdt.getPrice().multiply(tagmtDetail.getRate()).setScale(2, RoundingMode.HALF_UP));
			model.setRemainAgmtQuota(tagmtDetail.getRemainQuota());
			
//			if(isShowStock) { //产品库存
			OrdPdtModelDaily modelOfStock = new OrdPdtModelDaily();
			modelOfStock.setDate(sdf.format(new Date()));
			modelOfStock.setOrdDetailPdtType(OrdDetailPdtType.STOCK.toString());
			modelOfStock.setSupplyNum(String.valueOf(queryQuotaNum(ipdt,DateUtils.addDays(new Date(), spaceDay), OrdDetailPdtType.STOCK)));
			modelDailyList.add(modelOfStock);
//			}
			
			//产品日排产（获取releaseDay天的日排产）
			Calendar newCal = Calendar.getInstance(Locale.CHINA);
			newCal.add(Calendar.DATE, spaceDay);
			int count = 0;
			boolean breakFlag = false;
			while (count < releaseDay) {
				OrdPdtModelDaily modelOfDaily = new OrdPdtModelDaily();
				while (true) {
					Date ordDate = newCal.getTime();
					if(ordDate.after(endTime) && !DateUtils.isSameDay(ordDate, endTime)) { //如果到协议最后一天，则退出循环
						breakFlag = true;
						break;
					}
					TpdtPlanDayQuota tpdq = new TpdtPlanDayQuota();
					tpdq.setIpdt(ipdt);
					tpdq.setYear(newCal.get(Calendar.YEAR));
					tpdq.setMonth(newCal.get(Calendar.MONTH) + 1);
					tpdq.setDay(newCal.get(Calendar.DATE));
					tpdq.setOrdDetailPdtType(OrdDetailPdtType.DAILY_OUTPUT);
					List<TpdtPlanDayQuota> tmpList = this.tpdtPlanDayQuotaService.findBySelect(tpdq);
					if(CollectionUtils.isEmpty(tmpList)
							|| tmpList.get(0) == null
							|| tmpList.get(0).getNum() == 0) { //没有排产计划，顺延一天
						newCal.add(Calendar.DATE, 1);
						continue;
					}else {
						if((ordDate.after(startTime) && ordDate.before(endTime)) || 
								DateUtils.isSameDay(ordDate, startTime) ||
								DateUtils.isSameDay(ordDate, endTime)) {//有排产计划，且预约日期在协议有效期内
							modelOfDaily.setDate(sdf.format(ordDate));
							modelOfDaily.setOrdDetailPdtType(OrdDetailPdtType.DAILY_OUTPUT.toString());
							modelOfDaily.setSupplyNum(
									String.valueOf(queryQuotaNum(ipdt, ordDate, OrdDetailPdtType.DAILY_OUTPUT)));
							modelDailyList.add(modelOfDaily);
							count++;
						}else {//有排产计划，但预约日期不在协议有效期内，跳过该天
						}
						newCal.add(Calendar.DATE, 1);
//						count++;
						break;
					}
				}
				if(breakFlag) {
					break;
				}
			}
			model.setOrdPdtModelList(modelDailyList);
			modelList.add(model);
		}
		
		boolean canOrd = false; //是否可以下单
		for (OrdPdtModel4Page model : modelList) {
			if(model.getOrdPdtModelList().size() >= 1) {
				canOrd = true;
			}
		}
		if(!canOrd) {
			throw new BizException(0, "很抱歉，当前时间不允许下订单！");
		}
		return modelList;
	}
	
	/**
	 * 从订货额度表t_pdt_plan_day_quota 查找产品的 库存量或日供货量
	 */
	private int queryQuotaNum(Long ipdt,Date date,OrdDetailPdtType ordDetailPdtType){
		int tpdtStockNum = 0;
		TpdtPlanDayQuota tpdq = new TpdtPlanDayQuota();
		tpdq.setIpdt(ipdt);
		tpdq.setOrdDetailPdtType(ordDetailPdtType);
		
		String dateStr = sdf.format(date);
		int year = Integer.parseInt(dateStr.split("-")[0]);
		int month = Integer.parseInt(dateStr.split("-")[1]);
		int day = Integer.parseInt(dateStr.split("-")[2]);
		tpdq.setYear(year);
		tpdq.setMonth(month);
		tpdq.setDay(day);
		
		List<TpdtPlanDayQuota> tpdtPlanDayQuotaList = this.tpdtPlanDayQuotaService.findBySelect(tpdq);
		if(!CollectionUtils.isEmpty(tpdtPlanDayQuotaList)){
			tpdtStockNum = tpdtPlanDayQuotaList.get(0).getRemainQuota();
		}
		return tpdtStockNum;
	}
	
	/**
	 * 下单
	 * @param iagmt
	 * @param ordPdtModel4PageList
	 * @param tcust
	 * @param tcustReg
	 * @return
	 * @throws BizException
	 */
	public List<OrdPdtModel4Page> addOrd(Long iagmt, List<OrdPdtModel4Page> ordPdtModel4PageList, 
			Tcust tcust, TcustReg tcustReg) throws BizException{
		List<OrdPdtModel4Page> newList = new ArrayList<OrdPdtModel4Page>();
		
		int spaceDay = 2;
		TsysParam param = this.tsysParamService.getTsysParam("OTHER_CONF", "SPACE_DAY_4_ORDER"); //查询点单间隔天数，即可以点几天之后的单
		if(param != null && StringUtils.isNotBlank(param.getValue())) {
			String spaceStr = param.getValue();
			try {
				spaceDay = Integer.valueOf(spaceStr);
			} catch (Exception e) {
				logger.error("系统参数【点单间隔天数】转化异常", e);
			}
		}
		
		//判断单日点单数量是否超过可点单额度
		BigDecimal amt = new BigDecimal(0);
		int allCount = 0;
		for(OrdPdtModel4Page ordPdtObj : ordPdtModel4PageList){
			Long ipdt = ordPdtObj.getIpdt();
			BigDecimal priceAfterRate = ordPdtObj.getPriceAfterRate();
			int allnumOfDaily = 0;
			for (OrdPdtModelDaily modelDaily : ordPdtObj.getOrdPdtModelList()) {
				String orderNumberStr = modelDaily.getOrderNumber();
				//不为空或不为0
				if(StringUtils.isBlank(orderNumberStr) || orderNumberStr.startsWith("0") || 
						Integer.parseInt(orderNumberStr) == 0) {
					continue;
				}
				
				int orderNum = Integer.parseInt(orderNumberStr);
				allCount += orderNum;
				allnumOfDaily += orderNum;
				
				if(OrdDetailPdtType.STOCK.toString().equals(modelDaily.getOrdDetailPdtType())){
					//判断库存额度
					int quotaNum = queryQuotaNum(ipdt, DateUtils.addDays(new Date(), spaceDay), OrdDetailPdtType.STOCK);
					if(orderNum > quotaNum){
						throw new BizException(100, ordPdtObj.getPdtName()+"点单库存超过可选库存额度");
					}
				}else if(OrdDetailPdtType.DAILY_OUTPUT.toString().equals(modelDaily.getOrdDetailPdtType())){
					//判断日产量额度
					String dateStr = modelDaily.getDate();
					Date date = null;
					try {
						date = sdf.parse(dateStr);
					} catch (ParseException e) {
						throw new BizException(0,"系统异常,请联系管理员");
					}
					int quotaNum = queryQuotaNum(ipdt, date, OrdDetailPdtType.DAILY_OUTPUT);
					if(orderNum>quotaNum){
						throw new BizException(101,
								dateStr+"的"+ordPdtObj.getPdtName()+"点单超过可供货量！可能已被其他客户抢购，请关闭点单页面重新打开");
					}
				}else {
					throw new BizException(0, ordPdtObj.getPdtName()+"存量类型有误，请联系管理员");
				}
				
				BigDecimal num = new BigDecimal(orderNum);
				amt = amt.add(priceAfterRate.multiply(num).setScale(2, RoundingMode.HALF_UP));
			}
			ordPdtObj.setAllOrderNumOfDaily(allnumOfDaily);
			newList.add(ordPdtObj);
		}
		
		//判断点单总台数是否大于0
		if(allCount <= 0){
			throw new BizException(102,"采购订单台数必须大于0");
		}
		
		//判断产品订单量是否超过协议剩余额度
		for(OrdPdtModel4Page ordPdtObj : ordPdtModel4PageList){
			TagmtDetail tagmtDetail = new TagmtDetail();
			tagmtDetail.setIagmt(iagmt);
			tagmtDetail.setIpdt(ordPdtObj.getIpdt());
			tagmtDetail.setIpdtHis(ordPdtObj.getIpdtHis());
			tagmtDetail.setDelFlag(Boolean.FALSE);
			tagmtDetail.setAgmtDetailStatus(AgmtDetailStatus.CONFIRM);
			List<TagmtDetail> list = this.tagmtService.findTagmtDetailBySelect(tagmtDetail);
			if(CollectionUtils.isEmpty(list)) {
				tagmtDetail.setAgmtDetailStatus(AgmtDetailStatus.HAVE_CHANGED); // 查询已变更的协议明细
				list = this.tagmtService.findTagmtDetailBySelect(tagmtDetail);
				if(CollectionUtils.isEmpty(list)) {
					throw new BizException(0, "协议明细数据异常，请联系管理员");
				}
			}
			if(ordPdtObj.getAllOrderNumOfDaily() > list.get(0).getRemainQuota()) {
				throw new BizException(0, "产品"+ordPdtObj.getPdtName()+"的采购订单总量超过预约订单剩余额度，请重新点单");
			}
		}
		
		return newList;
	}
	
	public Tord addOrdConfirm(Long iagmt, OrdPdtModelList4Page list4Page, 
			Tcust tcust, TcustReg tcustReg) throws BizException{
		int spaceDay = 2;
		TsysParam param = this.tsysParamService.getTsysParam("OTHER_CONF", "SPACE_DAY_4_ORDER"); //查询点单间隔天数，即可以点几天之后的单
		if(param != null && StringUtils.isNotBlank(param.getValue())) {
			String spaceStr = param.getValue();
			try {
				spaceDay = Integer.valueOf(spaceStr);
			} catch (Exception e) {
				logger.error("系统参数【点单间隔天数】转化异常", e);
				throw new BizException(BizErrCode.SYS_ERR, "系统参数【点单间隔天数】转化异常");
			}
		}
		//判断单日点单数量是否超过可点单额度
		BigDecimal amt = new BigDecimal(0);
		int allCount = 0;
		for(OrdPdtModel4Page ordPdtObj : list4Page.getOrdPdtModel4PageList()){
			Long ipdt = ordPdtObj.getIpdt();
			BigDecimal priceAfterRate = ordPdtObj.getPriceAfterRate();
			int allnumOfDaily = 0;
			for (OrdPdtModelDaily modelDaily : ordPdtObj.getOrdPdtModelList()) {
				String orderNumberStr = modelDaily.getOrderNumber();
				//不为空或不为0
				if(StringUtils.isBlank(orderNumberStr) || orderNumberStr.startsWith("0") || 
						Integer.parseInt(orderNumberStr) == 0) {
					continue;
				}
				
				int orderNum = Integer.parseInt(orderNumberStr);
				allCount += orderNum;
				allnumOfDaily += orderNum;
				
				if(OrdDetailPdtType.STOCK.toString().equals(modelDaily.getOrdDetailPdtType())){
					//判断库存额度
					int quotaNum = queryQuotaNum(ipdt, DateUtils.addDays(new Date(), spaceDay), OrdDetailPdtType.STOCK);
					if(orderNum > quotaNum){
						throw new BizException(100, ordPdtObj.getPdtName()+"点单库存超过可选库存额度");
					}
				}else if(OrdDetailPdtType.DAILY_OUTPUT.toString().equals(modelDaily.getOrdDetailPdtType())){
					//判断日产量额度
					String dateStr = modelDaily.getDate();
					Date date = null;
					try {
						date = sdf.parse(dateStr);
					} catch (ParseException e) {
						throw new BizException(0,"系统异常,请联系管理员");
					}
					int quotaNum = queryQuotaNum(ipdt, date, OrdDetailPdtType.DAILY_OUTPUT);
					if(orderNum>quotaNum){
						throw new BizException(101,
								dateStr+"的"+ordPdtObj.getPdtName()+"采购订单超过可供货量！可能已被其他客户抢购，请重新输入采购订单数量");
					}
				}else {
					throw new BizException(0, ordPdtObj.getPdtName()+"存量类型有误，请联系管理员");
				}
				
				BigDecimal num = new BigDecimal(orderNum);
				amt = amt.add(priceAfterRate.multiply(num).setScale(2, RoundingMode.HALF_UP));
			}
			ordPdtObj.setAllOrderNumOfDaily(allnumOfDaily);
		}
		
		//判断点单总台数是否大于0
		if(allCount <= 0){
			throw new BizException(102,"采购订单台数必须大于0");
		}
		
		//判断产品订单量是否超过协议剩余额度
		for(OrdPdtModel4Page ordPdtObj : list4Page.getOrdPdtModel4PageList()){
			TagmtDetail tagmtDetail = new TagmtDetail();
			tagmtDetail.setIagmt(iagmt);
			tagmtDetail.setIpdt(ordPdtObj.getIpdt());
			tagmtDetail.setIpdtHis(ordPdtObj.getIpdtHis());
			tagmtDetail.setDelFlag(Boolean.FALSE);
			tagmtDetail.setAgmtDetailStatus(AgmtDetailStatus.CONFIRM);
			List<TagmtDetail> list = this.tagmtService.findTagmtDetailBySelect(tagmtDetail);
			if(CollectionUtils.isEmpty(list)) {
				tagmtDetail.setAgmtDetailStatus(AgmtDetailStatus.HAVE_CHANGED); // 查询已变更的协议明细
				list = this.tagmtService.findTagmtDetailBySelect(tagmtDetail);
				if(CollectionUtils.isEmpty(list)) {
					throw new BizException(0, "协议明细数据异常，请联系管理员");
				}
			}
			if(ordPdtObj.getAllOrderNumOfDaily() > list.get(0).getRemainQuota()) {
				throw new BizException(0, "产品"+ordPdtObj.getPdtName()+"的采购订单总量超过预约订单剩余额度，请重新点单");
			}
		}
		
		Tagmt tagmt = this.tagmtService.findById(iagmt);
		
		//保存订单
		Tord tord = new Tord();
		tord.setIagmt(iagmt);
		tord.setIcust(tcust.getIcust());
		tord.setAmt(amt);
		tord.setOrdStatus(OrdStatus.WAIT_AUDIT);
		tord.setPayStatus(PayStatus.WAIT_PAY);
		tord.setLockAmtOfDeposit(this.getLockAmtOfDeposit(tagmt, tord));
		tord.setAmtOfDelivery(new BigDecimal(0));
		tord.setRemark(list4Page.getRemark());
		this.tordService.save(tord);
		
		//同步保存订单历史数据
		TordHis tordHis = new TordHis();
		tordHis.setAmt(amt);
		tordHis.setIagmt(iagmt);
		tordHis.setIord(tord.getIord());
		tordHis.setIcust(tcust.getIcust());
		tordHis.setOrdStatus(OrdStatus.WAIT_AUDIT);
		tordHis.setPayStatus(PayStatus.WAIT_PAY);
		tordHis.setLockAmtOfDeposit(tord.getLockAmtOfDeposit());
		tordHis.setAmtOfDelivery(new BigDecimal(0));
		this.tordService.saveTordHis(tordHis);
		
		List<TordDetailPdt> tordDetailPdts = new ArrayList<TordDetailPdt>();
		
		//保存订单明细
		for(OrdPdtModel4Page ordPdtObj : list4Page.getOrdPdtModel4PageList()) {
			Integer orderNumber = ordPdtObj.getAllOrderNumOfDaily();
			//不为空或不为0
			if(orderNumber == null || orderNumber.intValue() == 0) {
				continue;
			}
			
			TordDetail tordDetail = new TordDetail();
			tordDetail.setIcust(tcust.getIcust());
			tordDetail.setIord(tord.getIord());
			tordDetail.setIpdtHis(ordPdtObj.getIpdtHis());
			tordDetail.setIpdt(this.tpdtService.findTpdtHisById(ordPdtObj.getIpdtHis()).getIpdt());
			tordDetail.setNum(ordPdtObj.getAllOrderNumOfDaily());
			tordDetail.setRate(ordPdtObj.getRate());
			BigDecimal price = this.tpdtService.find(ordPdtObj.getIpdt()).getPrice();
			tordDetail.setPrice(price);
			tordDetail.setAmt(price.multiply(new BigDecimal(ordPdtObj.getAllOrderNumOfDaily())).
					multiply(ordPdtObj.getRate()).setScale(2, RoundingMode.HALF_UP));
			tordDetail.setUsedQuota(0);
			tordDetail.setRemainQuota(ordPdtObj.getAllOrderNumOfDaily());
			tordDetail.setOrdDetailStatus(ValidStatus.VALID);
			tordDetail.setDeliveryed(0);
			tordDetail.setPendingNum(0);
			tordDetail.setProducedNum(0);
			tordDetail.setAmtOfDelivery(new BigDecimal(0));
			
			//分配产品号段
			PdtNoSeq pdtNoSeq = this.tnoSegCfgService.getSeq(orderNumber.intValue(), tpdtService.find(ordPdtObj.getIpdt()).getInoSegCfg());
			tordDetail.setStartSn(pdtNoSeq.getStartSeq());
			tordDetail.setEndSn(pdtNoSeq.getEndSeq());
			
			this.tordDetailService.save(tordDetail);
			
			//记录客户库存和出入库明细
			this.tcustStockService.add4pay(tcust.getIcust(), ordPdtObj.getIpdt(), ordPdtObj.getAllOrderNumOfDaily(), tord.getIord(), tcust.getIcust(), tcustReg.getName());
			
			
			//锁定额度
			//修改tordDetailPdt,tpdtPlanDayQuota，tagmtDetail 
			for (OrdPdtModelDaily modelDaily : ordPdtObj.getOrdPdtModelList()) {
				String orderNumberStr = modelDaily.getOrderNumber();
				//不为空或不为0
				if(StringUtils.isBlank(orderNumberStr) || orderNumberStr.startsWith("0") || 
						Integer.parseInt(orderNumberStr) == 0) {
					continue;
				}

				int orderNum = Integer.parseInt(orderNumberStr);
				String dateStr = modelDaily.getDate();
				int year = Integer.parseInt(dateStr.split("-")[0]);
				int month = Integer.parseInt(dateStr.split("-")[1]);
				int day = Integer.parseInt(dateStr.split("-")[2]);
				TordDetailPdt tordDetailPdt = new TordDetailPdt();
				tordDetailPdt.setNum(orderNum);
				tordDetailPdt.setYear(year);
				tordDetailPdt.setMonth(month);
				tordDetailPdt.setDay(day);
				tordDetailPdt.setIpdt(ordPdtObj.getIpdt());
				tordDetailPdt.setDownloadStat(DownLoadStat.UNDOWNLOAD);
				tordDetailPdt.setLockType(YesNoType.YES);
				tordDetailPdt.setIord(tord.getIord());
				tordDetailPdt.setOrdDetailPdtType(OrdDetailPdtType.valueOf(modelDaily.getOrdDetailPdtType()));
				tordDetailPdt.setOrdDetailPdtStatus(ValidStatus.VALID);
				tordDetailPdt.setDeliveryed(0);
				tordDetailPdt.setPendingNum(0);
				if(modelDaily.getOrdDetailPdtType().equals(OrdDetailPdtType.STOCK.toString())){
					tordDetailPdt.setProduceStatus(YesNoType.YES);
					tordDetailPdt.setProducedNum(orderNum);
				}else if(modelDaily.getOrdDetailPdtType().equals(OrdDetailPdtType.DAILY_OUTPUT.toString())){
					tordDetailPdt.setProduceStatus(YesNoType.NO);
					tordDetailPdt.setProducedNum(0);
				}else {
					throw new BizException(0, ordPdtObj.getPdtName()+"存量类型有误，请联系管理员");
				}
				tordDetailPdt.setUsedQuota(0);
				tordDetailPdt.setRemainQuota(orderNum);
				
				//分配产品预订日期号段
				pdtNoSeq.setNum(orderNum);
				tordDetailPdt.setStartSn(pdtNoSeq.getStartSeq());
				tordDetailPdt.setEndSn(pdtNoSeq.getEndSeq());
				
				this.tordDetailPdtService.save(tordDetailPdt);
				tordDetailPdts.add(tordDetailPdt);
				
				TpdtPlanDayQuota tpdq = new TpdtPlanDayQuota();
				tpdq.setIpdt(ordPdtObj.getIpdt());
			
				tpdq.setOrdDetailPdtType(OrdDetailPdtType.valueOf(modelDaily.getOrdDetailPdtType()));
				if(OrdDetailPdtType.valueOf(modelDaily.getOrdDetailPdtType()) == OrdDetailPdtType.STOCK){
					tpdq.setCollectionFlag(YesNoType.NO);//取当前末归集最新的库存
				}else{
					tpdq.setYear(year);
					tpdq.setMonth(month);
					tpdq.setDay(day);
				}
				List<TpdtPlanDayQuota> tpdtPlanDayQuotaList = this.tpdtPlanDayQuotaService.findBySelect(tpdq);
				
				TpdtPlanDayQuota newTpdtPlanDayQuota = tpdtPlanDayQuotaList.get(0);
				newTpdtPlanDayQuota.setUsedQuota(newTpdtPlanDayQuota.getUsedQuota()+orderNum);
				newTpdtPlanDayQuota.setRemainQuota(newTpdtPlanDayQuota.getRemainQuota()-orderNum);
				this.tpdtPlanDayQuotaService.update(newTpdtPlanDayQuota);
				
			}
			//修改协议明细剩余额度
			TagmtDetail td = new TagmtDetail();
			td.setIagmt(iagmt);
			td.setIpdt(ordPdtObj.getIpdt());
			TagmtDetail newTagmtDetail = this.tagmtService.findTagmtDetailBySelect(td).get(0);
			newTagmtDetail.setUsedQuota(newTagmtDetail.getUsedQuota() + ordPdtObj.getAllOrderNumOfDaily());
			newTagmtDetail.setRemainQuota(newTagmtDetail.getRemainQuota() - ordPdtObj.getAllOrderNumOfDaily());
			this.tagmtService.modifyTagmtDetail(newTagmtDetail);
		}
			
		//付款通知书
		TpayNotify tpayNotify = new TpayNotify();
		tpayNotify.setIcust(tcust.getIcust());
		tpayNotify.setPayType(PayType.ORDER);
		tpayNotify.setIfk(tord.getIord());
		tpayNotify.setIhisFk(tordHis.getIordHis());
		tpayNotify.setMemo("客户订单支付通知书");
		tpayNotify.setPayNotifyStatus(PayStatus.WAIT_PAY);
		this.tpayNotifyService.save(tpayNotify);
		
		//日志记录
		Tlog tlog = new Tlog();
		tlog.setIfk(tcust.getIcust());
		tlog.setLogType(LogType.CUST);
		tlog.setOperType(OperType.ORD_MGR);
		tlog.setMemo("客户点单");
		tlog.setPreData("");
		tlog.setData("客户"+tcustReg.getName()+"点单,订单编号是"+tord.getOrdNo());
		this.tlogService.save(tlog);
		
		//添加跟踪信息
		tdetailTraceService.add4Tord(tord, tcust, "客户采购订单("+genTordDetailStr(tord,tordDetailPdts)+"),并生成账单", tord.getAmt(), null);
		
		//发送邮件通知
		this.custMailService.sendCustOrderNotify(tcust, tord);
		
		return tord;
	}
	
	/**
	 * 撤销订单
	 * @param iord
	 * @param iagmt
	 * @param tcust
	 * @param tcustReg
	 */
	public void deleteTord(Long iord, Long iagmt, Tcust tcust, TcustReg tcustReg) throws BizException{
		Tord tord = this.tordService.find(iord);
		if (tord.getOrdStatus() != OrdStatus.WAIT_AUDIT
				|| tord.getPayStatus() != PayStatus.WAIT_PAY) {
			throw new BizException(0, "采购订单已付款，无法撤消");
		}
		if (!tord.getIcust().equals(tcust.getIcust())) {
			throw new BizException(0, "非法操作：非当前用户订单");
		}
		
		//支付通知书删除
		TpayNotify tpayNotify = new TpayNotify();
		tpayNotify.setIcust(tcust.getIcust());
		tpayNotify.setPayType(PayType.ORDER);
		tpayNotify.setIfk(iord);
		List<TpayNotify> tpayNotifyList = this.tpayNotifyService.findBySelect(tpayNotify);
		if(!CollectionUtils.isEmpty(tpayNotifyList)){
			this.tpayNotifyService.delete(tpayNotifyList.get(0).getIpayNotify());
		}
		
		//释放额度
		TordDetailPdt tordDetailPdt = new TordDetailPdt();
		tordDetailPdt.setIord(iord);
		tordDetailPdt.setLockType(YesNoType.YES);
		List<TordDetailPdt> tordDetailPdtList = this.tordDetailPdtService.findSelect(tordDetailPdt);
		if(!CollectionUtils.isEmpty(tordDetailPdtList)){
			int spaceDay = 2;
			TsysParam param = this.tsysParamService.getTsysParam("OTHER_CONF", "SPACE_DAY_4_ORDER"); //查询点单间隔天数，即可以点几天之后的单
			if(param != null && StringUtils.isNotBlank(param.getValue())) {
				String spaceStr = param.getValue();
				try {
					spaceDay = Integer.valueOf(spaceStr);
				} catch (Exception e) {
					logger.error("系统参数【点单间隔天数】转化异常", e);
					throw new BizException(BizErrCode.SYS_ERR, "系统参数【点单间隔天数】转化异常");
				}
			}
			for(TordDetailPdt newTordDetailPdt:tordDetailPdtList){
				newTordDetailPdt.setLockType(YesNoType.NO);
				this.tordDetailPdtService.update(newTordDetailPdt);

				//修改tpdtPlanDayQuota
				int year = newTordDetailPdt.getYear();
				int month = newTordDetailPdt.getMonth();
				int day = newTordDetailPdt.getDay();
				
				Calendar newTordDetailPdtCal = Calendar.getInstance(Locale.CHINA);
				Date now = newTordDetailPdtCal.getTime();
				now = DateUtils.addDays(now, spaceDay);
				newTordDetailPdtCal.set(year, month-1, day);
				
				TpdtPlanDayQuota condition = new TpdtPlanDayQuota();
				TpdtPlanDayQuota quota4Update = null;
				if(newTordDetailPdtCal.getTime().before(now)) { //如果所撤订单的日期在今天之前，则撤单的量应归集到当前库存
					newTordDetailPdtCal.setTime(now);
					
					condition.setYear(newTordDetailPdtCal.get(Calendar.YEAR));
					condition.setMonth(newTordDetailPdtCal.get(Calendar.MONTH)+1);
					condition.setDay(newTordDetailPdtCal.get(Calendar.DATE));
					condition.setIpdt(newTordDetailPdt.getIpdt());
					condition.setOrdDetailPdtType(OrdDetailPdtType.STOCK); //当天库存
					quota4Update = this.tpdtPlanDayQuotaService.findBySelect(condition).get(0);
					quota4Update.setRemainQuota(quota4Update.getRemainQuota() + newTordDetailPdt.getNum()); //库存剩余额度增加
				}else if(DateUtils.isSameDay(newTordDetailPdtCal.getTime(), now)) { //当天撤单
					newTordDetailPdtCal.setTime(now);
					
					condition.setYear(newTordDetailPdtCal.get(Calendar.YEAR));
					condition.setMonth(newTordDetailPdtCal.get(Calendar.MONTH)+1);
					condition.setDay(newTordDetailPdtCal.get(Calendar.DATE));
					condition.setIpdt(newTordDetailPdt.getIpdt());
					condition.setOrdDetailPdtType(OrdDetailPdtType.DAILY_OUTPUT); //当天库存
					quota4Update = this.tpdtPlanDayQuotaService.findBySelect(condition).get(0);
					quota4Update.setRemainQuota(quota4Update.getRemainQuota() + newTordDetailPdt.getNum()); //剩余额度增加
					quota4Update.setUsedQuota(quota4Update.getUsedQuota() - newTordDetailPdt.getNum()); //当天已使用额度减少
				}else { //否则回退哪天的订单，额度就释放到哪一天
					condition.setYear(newTordDetailPdtCal.get(Calendar.YEAR));
					condition.setMonth(newTordDetailPdtCal.get(Calendar.MONTH)+1);
					condition.setDay(newTordDetailPdtCal.get(Calendar.DATE));
					condition.setIpdt(newTordDetailPdt.getIpdt());
					condition.setOrdDetailPdtType(OrdDetailPdtType.DAILY_OUTPUT); //具体日期的额度
					quota4Update = this.tpdtPlanDayQuotaService.findBySelect(condition).get(0);
					quota4Update.setRemainQuota(quota4Update.getRemainQuota() + newTordDetailPdt.getNum()); //具体日期剩余额度增加
					quota4Update.setUsedQuota(quota4Update.getUsedQuota() - newTordDetailPdt.getNum()); //具体日期已使用额度减少
				}
				this.tpdtPlanDayQuotaService.update(quota4Update);
				
				//修改tagmtDetail
				TagmtDetail td = new TagmtDetail();
				td.setIagmt(iagmt);
				td.setIpdt(newTordDetailPdt.getIpdt());
				TagmtDetail newTagmtDetail = this.tagmtService.findTagmtDetailBySelect(td).get(0);
				newTagmtDetail.setUsedQuota(newTagmtDetail.getUsedQuota()-newTordDetailPdt.getNum());
				newTagmtDetail.setRemainQuota(newTagmtDetail.getRemainQuota()+newTordDetailPdt.getNum());
				this.tagmtService.modifyTagmtDetail(newTagmtDetail);
			}
		}
		
		//订单删除
		this.tordService.delete(tord);
		
		//订单明细删除
		TordDetail tordDetail = new TordDetail();
		tordDetail.setIcust(tcust.getIcust());
		tordDetail.setIord(iord);
		List<TordDetail> tordDetailList = this.tordDetailService.findSelect(tordDetail);
		if(!CollectionUtils.isEmpty(tordDetailList)){
			for(TordDetail newTordDetail:tordDetailList){
				newTordDetail.setOrdDetailStatus(ValidStatus.INVALID);
				this.tordDetailService.update(newTordDetail);
				
				Long ipdt = this.tpdtService.findTpdtHisById(newTordDetail.getIpdtHis()).getIpdt();
				
				//记录客户库存和出入库明细
				this.tcustStockService.cancel(tcust.getIcust(),ipdt , newTordDetail.getNum(), tord.getIord(),tcust.getIcust(),tcustReg.getName());
			
			}
		}
		
		//日志记录
		Tlog tlog = new Tlog();
		tlog.setIfk(tcust.getIcust());
		tlog.setLogType(LogType.CUST);
		tlog.setOperType(OperType.ORD_MGR);
		tlog.setMemo("客户撤销订单");
		tlog.setPreData("");
		tlog.setData("客户"+tcustReg.getName()+"撤销订单,订单编号是"+tord.getOrdNo());
		this.tlogService.save(tlog);
		
		//添加跟踪信息
		tdetailTraceService.add4Tord(tord, tcust, "客户撤销 采购订单("+genTordDetailStr(tord,tordDetailPdtList)+"),", null, null);
		
	}
	
	/**
	 * 获取订单的 保证金锁定金额
	 * @param tagmt
	 * @param tord
	 * @return
	 */
	public BigDecimal getLockAmtOfDeposit(Tagmt tagmt, Tord tord) {
		BigDecimal depositUsed4Tord = new BigDecimal(0);
		BigDecimal depositScale4Tord = new BigDecimal(0);
		TsysParam param = this.tsysParamService.getTsysParam("OTHER_CONF", "DEPOSIT_SCALE_4_TORD"); //订单支付保证金使用比例
		if(param != null && StringUtils.isNotBlank(param.getValue())) {
			String scale = param.getValue();
			try {
				depositScale4Tord = new BigDecimal(new Float(scale.substring(0, scale.indexOf("%")))/100).setScale(2, RoundingMode.HALF_UP);
			} catch (Exception e) {
				logger.error("系统参数【订单支付保证金使用比例】格式化失败", e);
			}
		} else {
			logger.debug("系统参数【订单支付保证金使用比例】为空");
		}
		depositUsed4Tord = tord.getAmt().multiply(depositScale4Tord).setScale(2, RoundingMode.HALF_UP);
		if(tagmt.getRemainDeposit().compareTo(depositUsed4Tord) < 0) {
			depositUsed4Tord = tagmt.getRemainDeposit();
		}
		return depositUsed4Tord;
	}
	
	public String  genTordDetailStr(Tord tord, List<TordDetailPdt> tordDetailPdts){
		StringBuffer sb = new StringBuffer();
		sb.append("编号：").append(tord.getOrdNo()).append(",状态：").append(mapBiz.getMapByType(DictType.ord_status.toString()).get(tord.getOrdStatus().toString()))
		.append(",支付状态：").append(mapBiz.getMapByType(DictType.pay_status.toString()).get(tord.getPayStatus().toString()));
		if(!CollectionUtils.isEmpty(tordDetailPdts)){
			sb.append("\n 订单详细[");
			for(TordDetailPdt  t : tordDetailPdts){
				sb.append("下单日期：").append(t.getYear()).append("-").append(t.getMonth()).append("-").append(t.getDay())
				.append("(").append(mapBiz.getMapByType(DictType.ord_detail_pdt_type.toString()).get(t.getOrdDetailPdtType().toString())).append(")")
				.append(",产品：").append(tpdtService.find(t.getIpdt()).getName()).append(",数量：").append(t.getNum()).append(",使用额度：")
				.append(t.getUsedQuota()).append(",剩余额度：").append(t.getRemainQuota()).append(";");
			}
			sb.append("]");
		}
		return sb.toString();
	}
	
	/**
	 * 查询当前未归集的库存
	 * @return
	 */
	public TpdtPlanDayQuota genNoCollectStock(Long ipdt){
	   return this.tpdtPlanDayQuotaService.findNoCollectionStock(ipdt);
    }
}
