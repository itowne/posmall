package com.newland.posmall.biz.admin;

import java.util.ArrayList;
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
import org.ohuyo.rapid.base.entity.Tuser;
import org.ohuyo.rapid.base.service.TsysParamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.newland.posmall.base.exception.BizErrCode;
import com.newland.posmall.base.exception.BizException;
import com.newland.posmall.base.service.OrderDownLoadService;
import com.newland.posmall.base.service.TagmtService;
import com.newland.posmall.base.service.TcustRegService;
import com.newland.posmall.base.service.TcustService;
import com.newland.posmall.base.service.TcustStockService;
import com.newland.posmall.base.service.TdetailTraceService;
import com.newland.posmall.base.service.TlogService;
import com.newland.posmall.base.service.TordDetailPdtService;
import com.newland.posmall.base.service.TordDetailService;
import com.newland.posmall.base.service.TordService;
import com.newland.posmall.base.service.TpayNotifyService;
import com.newland.posmall.base.service.TpdtPlanDayQuotaService;
import com.newland.posmall.base.service.TpdtService;
import com.newland.posmall.bean.basebusi.OrderDownLoad;
import com.newland.posmall.bean.basebusi.Tagmt;
import com.newland.posmall.bean.basebusi.TagmtDetail;
import com.newland.posmall.bean.basebusi.Tord;
import com.newland.posmall.bean.basebusi.TordDetail;
import com.newland.posmall.bean.basebusi.TordDetailPdt;
import com.newland.posmall.bean.basebusi.TpayNotify;
import com.newland.posmall.bean.basebusi.Tpdt;
import com.newland.posmall.bean.basebusi.TpdtHis;
import com.newland.posmall.bean.basebusi.TpdtPlanDayQuota;
import com.newland.posmall.bean.basebusi.condition.OrderDownLoadCondition;
import com.newland.posmall.bean.basebusi.condition.TcustRegCondition;
import com.newland.posmall.bean.basebusi.condition.TordCondition;
import com.newland.posmall.bean.basebusi.condition.TordDetailPdtCondition;
import com.newland.posmall.bean.common.Tlog;
import com.newland.posmall.bean.customer.Tcust;
import com.newland.posmall.bean.customer.TcustReg;
import com.newland.posmall.bean.dict.DictType;
import com.newland.posmall.bean.dict.LogType;
import com.newland.posmall.bean.dict.OperType;
import com.newland.posmall.bean.dict.OrdDetailPdtType;
import com.newland.posmall.bean.dict.OrdStatus;
import com.newland.posmall.bean.dict.PayStatus;
import com.newland.posmall.bean.dict.PayType;
import com.newland.posmall.bean.dict.ValidStatus;
import com.newland.posmall.bean.dict.YesNoType;
import com.newland.posmall.biz.common.MapBiz;
import com.newland.posmall.biz.cust.CustOrdBiz;
import com.newland.posmall.controller.cust.model.OrdDown4Page;

@Service
@Transactional
public class OrderBiz {

	private static final Logger logger = LoggerFactory.getLogger(CustOrdBiz.class);
	
	@Resource
	private TordService tordService;

	@Resource
	private TordDetailService tordDetailService;

	@Resource
	private TordDetailPdtService tordDetailPdtService;

	@Resource
	private TpdtService tpdtService;

	@Resource
	private TagmtService tagmtService;

	@Resource
	private TlogService tlogService;

	@Resource
	private TcustStockService tcustStockService;

	@Resource
	private TcustRegService tcustRegService;

	@Resource
	private TcustService tcustService;

	@Resource
	private TpayNotifyService tpayNotifyService;

	@Resource
	private TpdtPlanDayQuotaService tpdtPlanDayQuotaService;

	@Resource
	private OrderDownLoadService orderDownLoadService;

	@Resource
	private AdminMailService adminMailService;

	@Resource
	private TsysParamService tsysParamService;
	
	@Resource
	private TdetailTraceService tdetailTraceService;
	
	@Resource
	private MapBiz mapBiz;
	
	/**
	 * 用户撤销订单
	 * 
	 * @param iord
	 * @param iagmt
	 * @throws BizException 
	 */
	public Tord deleteTord(Long iord, Long iagmt, Tuser tuser) throws BizException {
		Tord tord = this.tordService.find(iord);
		if(!(tord.getOrdStatus() == OrdStatus.WAIT_AUDIT && (tord.getPayStatus() == PayStatus.WAIT_PAY || tord.getPayStatus() == PayStatus.NO_PASS) )){
			throw new BizException(0, "订单状态异常， 订单已支付不能撤销！");
		}
		
		Long iuser = tuser.getIuser();
		// 支付通知书删除
		TpayNotify tpayNotify = new TpayNotify();
		tpayNotify.setPayType(PayType.ORDER);
		tpayNotify.setIfk(iord);
		List<TpayNotify> tpayNotifyList = this.tpayNotifyService
				.findBySelect(tpayNotify);
		if (!CollectionUtils.isEmpty(tpayNotifyList)) {
			this.tpayNotifyService
					.delete(tpayNotifyList.get(0).getIpayNotify());
		}

		// 释放额度
		TordDetailPdt tordDetailPdt = new TordDetailPdt();
		tordDetailPdt.setIord(iord);
		tordDetailPdt.setLockType(YesNoType.YES);
		List<TordDetailPdt> tordDetailPdtList = this.tordDetailPdtService
				.findSelect(tordDetailPdt);
		if (!CollectionUtils.isEmpty(tordDetailPdtList)) {
			
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
			
			for (TordDetailPdt newTordDetailPdt : tordDetailPdtList) {
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
					quota4Update.setRemainQuota(quota4Update.getRemainQuota() + newTordDetailPdt.getNum()); //库存剩余额度增加
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

				// 修改tagmtDetail
				TagmtDetail td = new TagmtDetail();
				td.setIagmt(iagmt);
				td.setIpdt(newTordDetailPdt.getIpdt());
				TagmtDetail newTagmtDetail = this.tagmtService
						.findTagmtDetailBySelect(td).get(0);
				newTagmtDetail.setUsedQuota(newTagmtDetail.getUsedQuota()
						- newTordDetailPdt.getNum());
				newTagmtDetail.setRemainQuota(newTagmtDetail.getRemainQuota()
						+ newTordDetailPdt.getNum());
				this.tagmtService.modifyTagmtDetail(newTagmtDetail);
			}
		}

		// 订单删除
		this.tordService.delete(tord);

		// 订单明细删除
		TordDetail tordDetail = new TordDetail();
		tordDetail.setIord(iord);
		List<TordDetail> tordDetailList = this.tordDetailService
				.findSelect(tordDetail);
		if (!CollectionUtils.isEmpty(tordDetailList)) {
			for (TordDetail newTordDetail : tordDetailList) {
				newTordDetail.setOrdDetailStatus(ValidStatus.INVALID);
				this.tordDetailService.update(newTordDetail);

				Long ipdt = this.tpdtService.findTpdtHisById(
						newTordDetail.getIpdtHis()).getIpdt();

				// 记录客户库存和出入库明细
				this.tcustStockService.cancel(newTordDetail.getIcust(), ipdt,
						newTordDetail.getNum(), tord.getIord(), iuser,
						tuser.getTuserSub().getName());

			}
		}

		// 日志记录
		Tlog tlog = new Tlog();
		tlog.setIfk(iuser);
		tlog.setLogType(LogType.USER);
		tlog.setOperType(OperType.ORD_MGR);
		tlog.setMemo("用户撤销订单");
		tlog.setPreData("");
		tlog.setData("用户" + tuser.getTuserSub().getName() + "撤销订单,订单编号是"
				+ tord.getOrdNo());
		this.tlogService.save(tlog);
		
		//添加跟踪信息
		tdetailTraceService.add4Tord(tord, tuser, "后台 管理员 撤销 采购订单("+genTordDetailStr(tord,tordDetailPdtList)+"),", null, null);
		
		return tord;
	}
	
	/**
	 * 终止 订单
	 * 
	 * @param tord
	 * @throws BizException
	 */
	public Tord stopTord(Tord tord, Tuser tuser) {


		Tlog tlog = new Tlog();
		tlog.setGenTime(new Date());
		tlog.setIfk(tuser.getIuser());
		tlog.setLogType(LogType.USER);
		tlog.setOperType(OperType.ORD_MGR);
		tlog.setMemo("用户终止订单id:" + tord.getIord());
		StringBuffer sb = new StringBuffer();
		sb.append("终止前 订单编号：").append(tord.getOrdNo()).append(",订单状态:")
				.append(tord.getOrdStatus()).append(",支付状态:")
				.append(tord.getPayStatus());
		tlog.setPreData(sb.toString());
		tord.setOrdStatus(OrdStatus.STOP);
		tordService.update(tord);

		StringBuffer sb2 = new StringBuffer();
		sb2.append("终止后 订单编号：").append(tord.getOrdNo()).append(",订单状态:")
				.append(tord.getOrdStatus()).append(",支付状态:")
				.append(tord.getPayStatus());
		tlog.setData(sb2.toString());
		tlogService.save(tlog);
		
		TordDetailPdt tordDetailPdt = new TordDetailPdt();
		tordDetailPdt.setIord(tord.getIord());
		List<TordDetailPdt> tordDetailPdtList = this.tordDetailPdtService
				.findSelect(tordDetailPdt);
		//添加跟踪信息
		tdetailTraceService.add4Tord(tord, tuser, "后台 管理员 终止 采购订单("+genTordDetailStr(tord,tordDetailPdtList)+"),", null, null);

		return tord;

	}

	/**
	 * 审核订单
	 * 
	 * @param tord
	 * @throws BizException
	 */
	public Tord auditTord(Tord tord, Tuser tuser) throws BizException {
		if (tord.getOrdStatus() != OrdStatus.WAIT_AUDIT) {
			throw new BizException(10, "订单状态异常，不是待审核状态");
		}
		if (!(tord.getPayStatus() == PayStatus.HAVE_PAY || tord.getPayStatus() == PayStatus.PART_PAY)) {
			throw new BizException(10, "订单支付状态未通过，订单不能审核通过");
		}

		Tlog tlog = new Tlog();
		tlog.setGenTime(new Date());
		tlog.setIfk(tuser.getIuser());
		tlog.setLogType(LogType.USER);
		tlog.setOperType(OperType.ORD_MGR);
		tlog.setMemo("用户审核订单id:" + tord.getIord());
		StringBuffer sb = new StringBuffer();
		sb.append("修改前 订单编号：").append(tord.getOrdNo()).append(",订单状态:")
				.append(tord.getOrdStatus()).append(",支付状态:")
				.append(tord.getPayStatus());
		tlog.setPreData(sb.toString());
		tord.setOrdStatus(OrdStatus.HAVE_AUDIT);
		tordService.update(tord);

		Tcust cust = tcustService.find(tord.getIcust());
		adminMailService.sendOrdConfirmMail(cust, tord);

		StringBuffer sb2 = new StringBuffer();
		sb2.append("修改后 订单编号：").append(tord.getOrdNo()).append(",订单状态:")
				.append(tord.getOrdStatus()).append(",支付状态:")
				.append(tord.getPayStatus());
		tlog.setData(sb2.toString());
		tlogService.save(tlog);

		TordDetailPdt ordDetailPdt = new TordDetailPdt();
		ordDetailPdt.setIord(tord.getIord());
		List<TordDetailPdt> list = tordDetailPdtService
				.findSelect(ordDetailPdt);
		if (list != null && list.size() > 0) {
			for (TordDetailPdt td : list) {
				tcustStockService.pay(tord.getIcust(), td.getIpdt(), td
						.getNum(), tord.getIord(), tuser
						.getIuser(), tuser.getTuserSub().getName());
			}
		}
		
		//添加跟踪信息
		tdetailTraceService.add4Tord(tord, tuser, "后台 管理员 审核 采购订单("+genTordDetailStr(tord,list)+"),", null, null);
		
		return tord;

	}

	public Tagmt queryTagmtByIagmt(Long iagmt) {
		return tagmtService.findById(iagmt);
	}

	/**
	 * 查询 产品 列表（分页）
	 * 
	 * @param tordConfition
	 * @param page
	 * @return
	 */
	public List<Tord> findListByInfo(Page page, OrdStatus ordStatus,
			PayStatus payStatus, Date startDate, Date endDate, String name) {
		TcustRegCondition trc = new TcustRegCondition();
		trc.setName(name);
		List<Long> icusts = tcustRegService.queryIcustListByName(trc);
		// String icusts = tcustRegService.queryIcustsByName(trc);
		if (StringUtils.isNotBlank(name) && CollectionUtils.isEmpty(icusts)) {
			return null;
		}
		TordCondition tordCondition = new TordCondition();
		tordCondition.setOrdStatus(ordStatus);
		tordCondition.setDelFlag(Boolean.FALSE);
		tordCondition.setPayStatus(payStatus);
		tordCondition.setBeginTime(startDate);
		tordCondition.setEndTime(endDate);
		tordCondition.setIcustList(icusts);

		tordCondition.addOrders(Order.desc("iord"));
		return tordService.findTordByCon(tordCondition, page);
	}

	/**
	 * 查询订单详情（并关联设置订单产品明细）
	 * 
	 * @param tordId
	 *            订单id
	 * @return
	 */
	public List<TordDetail> queryTordDetailList(Long tordId) {
		if (tordId == null) {
			return null;
		}
		TordDetail detail = new TordDetail();
		detail.setIord(tordId);
		List<TordDetail> detailList = this.tordDetailService.findSelect(detail);
		if (detailList == null || detailList.size() < 1
				|| detailList.get(0) == null) {
			return null;
		}
		for (TordDetail tordDetail : detailList) {
			TpdtHis his = this.tpdtService.findTpdtHisById(tordDetail
					.getIpdtHis());
			TordDetailPdt tdp = new TordDetailPdt();
			tdp.setIord(tordId);
			tdp.setIpdt(his.getIpdt());
			List<TordDetailPdt> tdpList = this.tordDetailPdtService
					.findSelect(tdp);
			tordDetail.setPdtName(his.getName());
			tordDetail.setTordDetailPdtList(tdpList);
		}
		return detailList;
	}

	public List<TordDetailPdt> queryObjectDetailArr(Long iord) {

		TordDetailPdt tordDetailPdt = new TordDetailPdt();
		tordDetailPdt.setIord(iord);
		tordDetailPdt.setLockType(YesNoType.YES);
		List<TordDetailPdt> tordDetailPdtList = this.tordDetailPdtService
				.findSelect(tordDetailPdt);
		for (TordDetailPdt newTordDetailPdt : tordDetailPdtList) {
			Tpdt tpdt = this.tpdtService.find(newTordDetailPdt.getIpdt());
			newTordDetailPdt.setPdtName(tpdt.getName());
		}

		return tordDetailPdtList;
	}

	public Tord queryTordByIord(Long id) {

		return this.tordService.find(id);
	}

	public List<Tord> findBySelect(Tord ord) {
		return tordService.findBySelect(ord);
	}

	public List<TordDetail> findDetailByIord(Long iord) {

		TordDetail tordDetail = new TordDetail();
		tordDetail.setIord(iord);
		tordDetail.setDelFlag(false);
		tordDetail.setOrdDetailStatus(ValidStatus.VALID);
		return tordDetailService.findSelect(tordDetail);
	}

	/**
	 * 构造订单下载对象
	 * 
	 * @param ordList
	 * @return
	 */
	public List<OrdDown4Page> getOrderDownData(List<Tord> ordList) {
		List<OrdDown4Page> od4pList = new ArrayList<OrdDown4Page>();
		for (int i = 0; i < ordList.size(); i++) {
			OrdDown4Page od4p = new OrdDown4Page();
			Tord tord = ordList.get(i);
			od4p.setTord(tord);
			TordDetailPdt tmp = new TordDetailPdt();
			tmp.setIord(tord.getIord());
			List<TordDetailPdt> detailPdtList = tordDetailPdtService
					.findSelect(tmp);
			for (int j = 0; j < detailPdtList.size(); j++) {
				TordDetailPdt detailPdt = detailPdtList.get(j);
				Tpdt tpdt = tpdtService.find(detailPdt.getIpdt());
				detailPdt.setPdtName(tpdt.getName());
			}
			od4p.setDetailPdtList(detailPdtList);
			od4pList.add(od4p);
		}
		return od4pList;
	}

	public List<TordDetail> findDetailList() {
		TordDetail detail = new TordDetail();
		detail.setDelFlag(false);
		return tordDetailService.findSelect(detail);
	}

	public List<Tord> findListByCond(TordCondition tordCondition) {
		return tordService.findListdByCon(tordCondition);
	}

	public void save(OrderDownLoad od) {
		orderDownLoadService.save(od);
	}

	public List<OrderDownLoad> findPageByCondition(OrderDownLoadCondition cond,
			Page page) {
		return orderDownLoadService.findPageByCondition(cond, page);
	}

	public void updateOrd(Tord ord) {
		tordService.updateTord(ord);
	}

	public List<TordDetailPdt> queryListByCondition(
			TordDetailPdtCondition condition) {
		return tordDetailPdtService.findByCon(condition);
	}

	public void updatOrdDetailPdt(TordDetailPdt tordDetailPdt) {
		this.tordDetailPdtService.update(tordDetailPdt);
	}

	public TordDetailPdt findDetailPdtById(Long id){
		return tordDetailPdtService.find(id);
	}
	
	public TcustReg findTcustReg(Long icust){
		return tcustRegService.findByIcust(icust);
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
}
