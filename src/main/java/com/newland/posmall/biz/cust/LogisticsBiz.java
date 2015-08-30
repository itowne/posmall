package com.newland.posmall.biz.cust;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Order;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.entity.Tfile;
import org.ohuyo.rapid.base.service.TfileService;
import org.ohuyo.rapid.file.CsvFileTranslator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.newland.posmall.base.exception.BizException;
import com.newland.posmall.base.service.LogisticsDownLoadService;
import com.newland.posmall.base.service.TaddrService;
import com.newland.posmall.base.service.TcustStockService;
import com.newland.posmall.base.service.TdetailTraceService;
import com.newland.posmall.base.service.TlogService;
import com.newland.posmall.base.service.TlogisticsComService;
import com.newland.posmall.base.service.TlogisticsDetailService;
import com.newland.posmall.base.service.TlogisticsOrdAddrService;
import com.newland.posmall.base.service.TlogisticsService;
import com.newland.posmall.base.service.TordDetailPdtService;
import com.newland.posmall.base.service.TordDetailService;
import com.newland.posmall.base.service.TordService;
import com.newland.posmall.base.service.TpayNotifyService;
import com.newland.posmall.base.service.TpdtService;
import com.newland.posmall.bean.basebusi.LogisticsDownLoad;
import com.newland.posmall.bean.basebusi.TlogisticsCom;
import com.newland.posmall.bean.basebusi.TlogisticsDetail;
import com.newland.posmall.bean.basebusi.TlogisticsOrd;
import com.newland.posmall.bean.basebusi.TlogisticsOrdAddr;
import com.newland.posmall.bean.basebusi.Tord;
import com.newland.posmall.bean.basebusi.TordDetail;
import com.newland.posmall.bean.basebusi.TordDetailPdt;
import com.newland.posmall.bean.basebusi.TpayNotify;
import com.newland.posmall.bean.basebusi.Tpdt;
import com.newland.posmall.bean.basebusi.TpdtHis;
import com.newland.posmall.bean.basebusi.condition.LogisticsDownLoadCondition;
import com.newland.posmall.bean.basebusi.condition.TlogisticsOrdAddrCondition;
import com.newland.posmall.bean.basebusi.condition.TordDetailCondition;
import com.newland.posmall.bean.basebusi.condition.TordDetailPdtCondition;
import com.newland.posmall.bean.common.Tlog;
import com.newland.posmall.bean.customer.Taddr;
import com.newland.posmall.bean.customer.Tcust;
import com.newland.posmall.bean.customer.TcustReg;
import com.newland.posmall.bean.customer.TcustStock;
import com.newland.posmall.bean.customer.condition.TaddrCondition;
import com.newland.posmall.bean.customer.condition.TcustStockCondition;
import com.newland.posmall.bean.customer.condition.TlogisticsOrdCondition;
import com.newland.posmall.bean.dict.DictType;
import com.newland.posmall.bean.dict.LogType;
import com.newland.posmall.bean.dict.LogisticsCompany;
import com.newland.posmall.bean.dict.LogisticsOrdStatus;
import com.newland.posmall.bean.dict.OperType;
import com.newland.posmall.bean.dict.OrdDetailPdtType;
import com.newland.posmall.bean.dict.OrdStatus;
import com.newland.posmall.bean.dict.PayStatus;
import com.newland.posmall.bean.dict.PayType;
import com.newland.posmall.bean.dict.ValidStatus;
import com.newland.posmall.bean.dict.YesNoType;
import com.newland.posmall.biz.common.MapBiz;
import com.newland.posmall.controller.cust.model.LogisticsDown4Page;
import com.newland.posmall.controller.cust.model.LogisticsOrd4Page;
import com.newland.posmall.controller.cust.model.LogisticsOrdAddrObj4Page;
import com.newland.posmall.controller.cust.model.Ord4Page;
import com.newland.posmall.controller.cust.model.OrdDetail4Page;

@Service
@Transactional(rollbackFor = Throwable.class)
public class LogisticsBiz {

	@Resource
	private TcustStockService tcustStockService;

	@Resource
	private TlogisticsService tLogisticsService;

	@Resource
	private TpdtService tpdtService;

	@Resource
	private TlogisticsDetailService tlogisticsDetailService;

	@Resource
	private TordService tordService;

	@Resource
	private TordDetailService tordDetailService;

	@Resource
	private TordDetailPdtService tordDetailPdtService;

	@Resource
	private LogisticsDownLoadService logisticsDownLoadService;

	@Resource
	private TaddrService taddrService;

	@Resource
	private TlogisticsComService tlogisticsComService;

	@Resource
	private TlogisticsOrdAddrService tlogisticsOrdAddrService;

	@Resource
	private TlogService tlogService;

	@Resource
	private TpayNotifyService tpayNotifyService;

	@Resource
	private TfileService tfileService;
	
	@Resource
	private TdetailTraceService tdetailTraceService;
	
	@Resource
	private CustMailService custMailService;
	
	@Resource
	private MapBiz mapBiz;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 查询客户库存
	 * 
	 * @param tcust
	 * @return
	 */
	public List<TcustStock> queryTcustStock(Tcust tcust) {

		TcustStockCondition condition = new TcustStockCondition();
		condition.setIcust(tcust.getIcust());
		condition.addOrders(Order.asc("ipdt"));
		return tcustStockService.findPageByCondition(condition);
	}

	public List<TlogisticsOrd> queryOrdByCon( TlogisticsOrd tlogisticsOrd, Tcust tcust, Page page,String consigneeName) {
		TlogisticsOrdCondition condition = new TlogisticsOrdCondition();
		
		if(StringUtils.isNotBlank(consigneeName)){//搜索收货人姓名
			List<TlogisticsOrdAddr> addrList = null;
			TlogisticsOrdAddrCondition  tlogisticsOrdAddrCondition = new TlogisticsOrdAddrCondition();
			tlogisticsOrdAddrCondition.setConsigneeName(consigneeName.trim());
			addrList = this.tlogisticsOrdAddrService.findCondition(tlogisticsOrdAddrCondition);
			List<Long> ilogisticsOrdList = new ArrayList<Long>();
			if(addrList!=null&&addrList.size()>0){
				for(TlogisticsOrdAddr addr:addrList){
					if(!ilogisticsOrdList.contains(addr.getIlogisticsOrd())){
						ilogisticsOrdList.add(addr.getIlogisticsOrd());
					}
				}   
			}else{
				ilogisticsOrdList.add(0L);
			}
			condition.setIlogisticsOrd(ilogisticsOrdList);
		}
		condition.setIcust(tcust.getIcust());
		condition.setTempFlag(false);
		condition.setInnerOrdno(tlogisticsOrd.getInnerOrdno());
		condition.setLogisticsOrdStatus(tlogisticsOrd.getLogisticsOrdStatus());
		condition.setPayStatus(tlogisticsOrd.getPayStatus());
		condition.addOrders(Order.desc("ilogisticsOrd"));
		return tLogisticsService.queryPageByCondition(page, condition);
	}

	public List<TlogisticsOrd> queryOrdListByCon(
			TlogisticsOrdCondition condition) {
		condition.addOrders(Order.asc("updTime"));
		return tLogisticsService.queryByCondition(condition);
	}

	public List<TlogisticsOrd> findListBy(TlogisticsOrd tlo) {
		return tLogisticsService.findListByObj(tlo);
	}

	public TlogisticsDetail findDetailById(Long id) {
		return tlogisticsDetailService.findDetailById(id);
	}

	public List<TlogisticsDetail> findDetailListById(TlogisticsDetail tDetail) {
		return tlogisticsDetailService.findListByDetail(tDetail);
	}

	public TlogisticsOrd getTlogisticsOrd(Long ilogisticsOrd) {
		return tLogisticsService.findTlogisticsOrdById(ilogisticsOrd);
	}

	public List<Tord> findTordByCon(Tcust tcust) {

		TordDetailCondition tordDetailCondition = new TordDetailCondition();
		tordDetailCondition.setDelFlag(false);
		tordDetailCondition.setIcust(tcust.getIcust());
		tordDetailCondition.setOrdDetailStatus(ValidStatus.VALID);
		tordDetailCondition.setRemainQuota(0);
		tordDetailCondition.addOrders(Order.asc("iord"));
		List<TordDetail> tordDetailList = this.tordDetailService
				.findByCon(tordDetailCondition);

		List<Tord> tordList = new ArrayList<Tord>();
		if (!CollectionUtils.isEmpty(tordDetailList)) {

			Set<Long> set = new LinkedHashSet<Long>();
			for (TordDetail tordDetail : tordDetailList) {
				set.add(tordDetail.getIord());
			}

			Iterator<Long> it = set.iterator();
			while (it.hasNext()) {
				Long iord = it.next();
				Tord tord = this.tordService.find(iord);

				if (tord.getOrdStatus().equals(OrdStatus.HAVE_AUDIT)
						&& (tord.getPayStatus().equals(PayStatus.HAVE_PAY) || tord.getPayStatus().equals(PayStatus.PART_PAY))) {
					tordList.add(tord);
				}
			}
		}

		return tordList;
	}

	public Tord findTordByIord(Long iord) {
		return this.tordService.find(iord);
	}

	public TlogisticsOrd logisticsOrdAdd1(Tcust tcust, TcustReg tcustReg,
			Ord4Page ord4Page) throws BizException {

		// 验证填写数据
		List<OrdDetail4Page> ordDetail4PageList = ord4Page
				.getOrdDetail4PageList();
		int count = 0;
		for (OrdDetail4Page ordDetail4Page : ordDetail4PageList) {
			String shipmentNumStr = ordDetail4Page.getShipmentNum();
			if (StringUtils.isNotBlank(shipmentNumStr)
					&& !"0".equals(shipmentNumStr)) {
				int shipmentNum = Integer.parseInt(shipmentNumStr);
				Long iordDetail = ordDetail4Page.getTordDetail()
						.getIordDetail();

				TordDetail tordDetail = this.tordDetailService.find(iordDetail);

				if (tordDetail.getRemainQuota() < shipmentNum) {
					throw new BizException(111, "发货量不能大于可发货数量");
				}

				count += shipmentNum;
				
			}
		}

		// 判断出货台数是否大于0
		if (count <= 0) {
			throw new BizException(110, "发货量必须大于0");
		}

		//如果订单是部分支付
		//验证发货量的金额是否大于订单部分支付的金额
		BigDecimal totleAmt = new BigDecimal(0);//本次物流单需要的付款金额
		for (OrdDetail4Page ordDetail4Page : ordDetail4PageList) {
			String shipmentNumStr = ordDetail4Page.getShipmentNum();
			if (StringUtils.isNotBlank(shipmentNumStr)&& !"0".equals(shipmentNumStr)) {
				BigDecimal shipmentNum = new BigDecimal(ordDetail4Page.getShipmentNum());
				
				Long ipdtHis = ordDetail4Page.getTordDetail().getIpdtHis();
	
				TpdtHis tpdtHis = this.tpdtService.findTpdtHisById(ipdtHis);
				BigDecimal price = tpdtHis.getPrice();
				BigDecimal pdtAmt = price.multiply(shipmentNum);
				
				totleAmt = totleAmt.add(pdtAmt);
			}
		}
		
		Long iord = ord4Page.getTord().getIord();
		Tord tord = this.tordService.find(iord);
		validateOrdAmt(tord, totleAmt);
		
		// 保存物流单
		TlogisticsOrd lo = new TlogisticsOrd();
		lo.setIcust(tcust.getIcust());
		lo.setLogisticsOrdStatus(LogisticsOrdStatus.WAIT_AUDIT);
		lo.setPayStatus(PayStatus.WAIT_PAY);
		lo.setTempFlag(true);
		lo.setNum(count);
		lo.setIuser(1L);
		lo.setUsername("系统");
		lo.setIord(ord4Page.getTord().getIord());
		lo.setIfile((long) -1);
		lo.setPayStatus(PayStatus.WAIT_PAY);
		lo.setAmt(totleAmt);
		this.tLogisticsService.addTlogisticsOrd(lo);

		// 保存物流单地址表
		int i = 0;
		for (OrdDetail4Page ordDetail4Page : ordDetail4PageList) {
			String shipmentNumStr = ordDetail4Page.getShipmentNum();
			if (StringUtils.isNotBlank(shipmentNumStr)
					&& !"0".equals(shipmentNumStr)) {

				int shipmentNum = Integer.parseInt(shipmentNumStr);
				TlogisticsOrdAddr loa = new TlogisticsOrdAddr();
				loa.setIlogisticsOrd(lo.getIlogisticsOrd());
				loa.setIpdtHis(ordDetail4Page.getTordDetail().getIpdtHis());
				loa.setNum(shipmentNum);
				loa.setIuser(tcust.getIcust());
				loa.setUsername(tcustReg.getName());
				loa.setSerial(i);
				this.tlogisticsOrdAddrService.save(loa);
				i++;
			}
		}
		return lo;
	}

	/**
	 * 验证订单已付款金额
	 */
	private void validateOrdAmt(Tord tord, BigDecimal totleAmt)
			throws BizException {
		if(PayStatus.PART_PAY.equals(tord.getPayStatus())){
			Long iord = tord.getIord();
			BigDecimal amtOfdelivery = tord.getAmtOfDelivery();
			
			TpayNotify tpayNotify = this.tpayNotifyService.findByifkAndPayType(iord,PayType.ORDER);
			
			BigDecimal havepayAmt = tpayNotify.getHavepayAmt();
			
			if(havepayAmt.subtract(amtOfdelivery).compareTo(totleAmt)<0){
				throw new BizException(110, "发货数量需要金额大于剩余可用货款金额，请减少发货数量或重新选择订单");
			}
		}
	}

	public TlogisticsOrd findLogisticsOrdById(Long id) {
		return this.tLogisticsService.findTlogisticsOrdById(id);
	}

	public List<Taddr> findTaddrList(Tcust tcust) {
		TaddrCondition condition = new TaddrCondition();
		condition.setDelFlag(false);
		condition.setIcust(tcust.getIcust());
		return this.taddrService.findPageByCondition(condition);
	}

	public List<Taddr> findTaddrList(Page page, Tcust tcust, String name) {

		Long icust = tcust.getIcust();
		TaddrCondition condition = new TaddrCondition();
		condition.setDelFlag(false);
		condition.setIcust(icust);
		condition.setName(name);
		return this.taddrService.findPageByCondition(page, condition);
	}

	public void updateTlogisticsOrd(TlogisticsOrd ord) {
		this.tLogisticsService.updateTlogisticsOrd(ord);
	}

	/**
	 * 构造物流下载对象
	 */
	public List<LogisticsDown4Page> getLogisticsData(List<TlogisticsOrd> list) {
		List<LogisticsDown4Page> ld4pList = new ArrayList<LogisticsDown4Page>();
		for (int i = 0; i < list.size(); i++) {
			LogisticsDown4Page ld4p = new LogisticsDown4Page();
			ld4p.setTlogisticsOrd(list.get(i));
			TlogisticsOrdAddr tmp = new TlogisticsOrdAddr();
			tmp.setIlogisticsOrd(list.get(i).getIlogisticsOrd());
			List<TlogisticsOrdAddr> addrList = tlogisticsOrdAddrService
					.findListSelect(tmp);
			ld4p.setTlogisticsOrdAddrList(addrList);
			ld4pList.add(ld4p);
		}
		return ld4pList;
	}

	public void saveLogistics4Down(LogisticsDownLoad ld) {
		logisticsDownLoadService.save(ld);
	}

	public List<LogisticsDownLoad> findDownListByCondition(
			LogisticsDownLoadCondition condition, Page page) {
		return logisticsDownLoadService.findListByCondition(condition, page);
	}

	public String getSpecifyDeliveryStr(String shipmentNum, Long ipdtHis,
			Long iord) {

		int shipmentInt = Integer.parseInt(shipmentNum);
		String specifyDeliveryStr = "不能超过可发货量";
		TordDetailPdt tdp = new TordDetailPdt();
		TpdtHis tpdtHis = this.tpdtService.findTpdtHisById(ipdtHis);
		tdp.setOrdDetailPdtStatus(ValidStatus.VALID);
		tdp.setOrdDetailPdtType(OrdDetailPdtType.STOCK);
		tdp.setLockType(YesNoType.YES);
		tdp.setIpdt(tpdtHis.getIpdt());
		tdp.setIord(iord);
		List<TordDetailPdt> tordDetailPdtList = this.tordDetailPdtService
				.findSelect(tdp);

		int remainQuota = 0;
		if (!CollectionUtils.isEmpty(tordDetailPdtList)) {
			remainQuota = tordDetailPdtList.get(0).getRemainQuota();
		}
		// 若当时的库存剩余额度大于等于此次发货量，则返回当前日期
		if (remainQuota >= shipmentInt) {
			specifyDeliveryStr = sdf.format(new Date());
		} else {
			int remainNum = shipmentInt - remainQuota;

			// 查询日产量的订单产品明细表，按年月日顺序排序
			TordDetailPdtCondition tdpc = new TordDetailPdtCondition();
			tdpc.setOrdDetailPdtStatus(ValidStatus.VALID);
			tdpc.setOrdDetailPdtType(OrdDetailPdtType.DAILY_OUTPUT);
			tdpc.setLockType(YesNoType.YES);
			tdpc.setIpdt(tpdtHis.getIpdt());
			tdpc.setIord(iord);
			List<Order> orders = new ArrayList<Order>();
			orders.add(Order.asc("year"));
			orders.add(Order.asc("month"));
			orders.add(Order.asc("day"));
			tdpc.setOrders(orders);
			List<TordDetailPdt> list = this.tordDetailPdtService
					.findByCon(tdpc);
			for (TordDetailPdt tordDetailPdt : list) {
				int year = tordDetailPdt.getYear();
				int month = tordDetailPdt.getMonth();
				int day = tordDetailPdt.getDay();
				int tmpNum = tordDetailPdt.getRemainQuota();

				remainNum = remainNum - tmpNum;
				if (remainNum <= 0) {
					specifyDeliveryStr = year
							+ "-"
							+ StringUtils
									.leftPad(String.valueOf(month), 2, "0")
							+ "-" + day;
					break;
				}

			}
		}

		return specifyDeliveryStr;
	}

	public String getSpecifyDeliveryStr(Ord4Page ord4Page) {
		Calendar speCal = Calendar.getInstance(Locale.CHINA);
		for (OrdDetail4Page p : ord4Page.getOrdDetail4PageList()) {
			String specifyDeliveryStr = p.getSpecifyDelivery();
			if (StringUtils.isNotBlank(specifyDeliveryStr)) {

				String[] sds = specifyDeliveryStr.split("-");
				Calendar cal = Calendar.getInstance(Locale.CHINA);
				cal.set(Calendar.YEAR, Integer.parseInt(sds[0]));
				cal.set(Calendar.MONTH, Integer.parseInt(sds[1]) - 1);
				cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(sds[2]));

				if (cal.after(speCal)) {
					speCal = cal;
				}
			}
		}

		return sdf.format(speCal.getTime());
	}

	public void logisticsOrdAdd2(Tcust tcust, TcustReg tcustReg,
			LogisticsOrd4Page logisticsOrd4Page) throws BizException {
		Long iaddr = logisticsOrd4Page.getIaddr();
		if (iaddr == null) {
			throw new BizException(121, "请选择收货地址");
		}
		Long ilogisticsCom = logisticsOrd4Page.getIlogisticsCom();
		if (ilogisticsCom == null) {
			throw new BizException(122, "请选择物流公司");
		}
		if (logisticsOrd4Page.getTlogisticsOrd() == null
				|| logisticsOrd4Page.getTlogisticsOrd().getIlogisticsOrd() == null) {
			throw new BizException(0, "获取物流订单数据失败");
		}
		if (StringUtils.isBlank(logisticsOrd4Page.getSpecifyDelivery())) {
			throw new BizException(0, "获取发货日期失败");
		}
		TlogisticsCom tlogisticsCom = this.tlogisticsComService
				.findById(ilogisticsCom);

		// 修改物流单
		Long ilogisticsOrd = logisticsOrd4Page.getTlogisticsOrd()
				.getIlogisticsOrd();
		String specifyDeliveryStr = logisticsOrd4Page.getSpecifyDelivery();
		Date specifyDelivery = new Date();
		try {
			specifyDelivery = sdf.parse(specifyDeliveryStr);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new BizException(0, "发货日期获取异常：" + e.getMessage());
		}
		TlogisticsOrd tlogisticsOrd = this.tLogisticsService
				.findTlogisticsOrdById(ilogisticsOrd);
		tlogisticsOrd.setTempFlag(false);
		tlogisticsOrd.setSpecifyDelivery(specifyDelivery);
		tlogisticsOrd.setLogisticsFreight(tlogisticsCom.getPrice()
				.multiply(new BigDecimal(tlogisticsOrd.getNum()))
				.setScale(2, RoundingMode.HALF_UP));
		this.tLogisticsService.updateTlogisticsOrd(tlogisticsOrd);

		Long iord = tlogisticsOrd.getIord();

		TlogisticsOrdAddr loa = new TlogisticsOrdAddr();
		loa.setIlogisticsOrd(ilogisticsOrd);
		List<TlogisticsOrdAddr> tlogisticsOrdAddrList = this.tlogisticsOrdAddrService
				.findListSelect(loa);

		// 保存物流单地址信息
		updateLogisticsOrdAddr(tlogisticsOrdAddrList, iaddr, tlogisticsCom);

		// 保存物流单明细
		saveLogisticsDetail(tlogisticsOrdAddrList, iord, tcust.getIcust());

		// 修改订单明细
		updateOrdDetail(tlogisticsOrdAddrList, iord);

		// 修改订单产品明细
		updateOrdDetailPdt(tlogisticsOrdAddrList, iord, tcust.getIcust());

		// 修改订单表 已发货货款
		BigDecimal amt = tlogisticsOrd.getAmt();
		Tord tord = this.tordService.find(iord);
		tord.setAmtOfDelivery(tord.getAmtOfDelivery().add(amt));
		this.tordService.update(tord);
		
		// 日志记录
		Tlog tlog = new Tlog();
		tlog.setIfk(tcust.getIcust());
		tlog.setLogType(LogType.CUST);
		tlog.setOperType(OperType.LOGISTICS_MGR);
		tlog.setMemo("客户制定物流计划");
		tlog.setPreData("");
		tlog.setData("客户" + tcustReg.getLongName() + "制定物流计划,物流编号是"
				+ tlogisticsOrd.getInnerOrdno());
		this.tlogService.save(tlog);
	}

	/**
	 * 新增物流单-修改物流单地址表
	 */
	private void updateLogisticsOrdAddr(
			List<TlogisticsOrdAddr> tlogisticsOrdAddrList, Long iaddr,
			TlogisticsCom tlogisticsCom) {
		Taddr taddr = this.taddrService.findById(iaddr);
		for (TlogisticsOrdAddr loa : tlogisticsOrdAddrList) {
			loa.setIaddrHis(iaddr);
			loa.setConsigneeAddr(taddr.getResult());
			loa.setConsigneeName(taddr.getName());
			String consigneeMobile = "";
			if(StringUtils.isNotBlank(taddr.getMobile())){
				consigneeMobile = taddr.getMobile();
			}
			if(StringUtils.isNotBlank(taddr.getTel())){
				consigneeMobile += " "+taddr.getTel();
			}
			loa.setConsigneeMobile(StringUtils.trim(consigneeMobile));
			loa.setIlogisticsCom(tlogisticsCom.getIlogisticsCom());
			loa.setLogisticsComName(tlogisticsCom.getFullname());

			loa.setPrice(tlogisticsCom.getPrice());
			this.tlogisticsOrdAddrService.update(loa);
		}
	}

	/**
	 * 新增物流单-保存物流单明细
	 */
	private void saveLogisticsDetail(
			List<TlogisticsOrdAddr> tlogisticsOrdAddrList, Long iord, Long icust) {
		for (TlogisticsOrdAddr loa : tlogisticsOrdAddrList) {
			int num = loa.getNum();
			Long ipdtHis = loa.getIpdtHis();
			Long ipdt = this.tpdtService.findTpdtHisById(ipdtHis).getIpdt();

			// 查询订单产品明细表，按年月日顺序排序
			TordDetailPdtCondition tdpc = new TordDetailPdtCondition();
			tdpc.setOrdDetailPdtStatus(ValidStatus.VALID);
			tdpc.setLockType(YesNoType.YES);
			tdpc.setIpdt(ipdt);
			tdpc.setIord(iord);
			List<Order> orders = new ArrayList<Order>();
			orders.add(Order.asc("year"));
			orders.add(Order.asc("month"));
			orders.add(Order.asc("day"));
			tdpc.setOrders(orders);
			List<TordDetailPdt> tordDetailPdtList = this.tordDetailPdtService
					.findByCon(tdpc);
			int tmpNum = num;
			for (TordDetailPdt odp : tordDetailPdtList) {
				int remainQuota = odp.getRemainQuota();
				if (remainQuota <= 0) {
					continue;
				}
				if (remainQuota >= tmpNum) {
					TlogisticsDetail ld = new TlogisticsDetail();
					ld.setIlogisticsOrd(loa.getIlogisticsOrd());
					ld.setIordDetailPdt(odp.getIordDetailPdt());
					ld.setIpdtHis(ipdtHis);
					ld.setIcust(icust);
					ld.setPdtNum(tmpNum);
					this.tLogisticsService.addTlogisticsDetail(ld);
					break;
				} else {
					TlogisticsDetail ld = new TlogisticsDetail();
					ld.setIlogisticsOrd(loa.getIlogisticsOrd());
					ld.setIordDetailPdt(odp.getIordDetailPdt());
					ld.setIpdtHis(ipdtHis);
					ld.setIcust(icust);
					ld.setPdtNum(remainQuota);
					this.tLogisticsService.addTlogisticsDetail(ld);
					tmpNum = tmpNum - remainQuota;
				}
			}
		}
	}

	/*
	 * 新增物流单-修改订单明细
	 */
	private void updateOrdDetail(List<TlogisticsOrdAddr> tlogisticsOrdAddrList,
			Long iord) {
		for (TlogisticsOrdAddr loa : tlogisticsOrdAddrList) {
			int num = loa.getNum();
			Long ipdtHis = loa.getIpdtHis();

			TordDetail od = new TordDetail();
			od.setIord(iord);
			od.setIpdtHis(ipdtHis);
			List<TordDetail> list = tordDetailService.findSelect(od);
			od = list.get(0);
			od.setPendingNum(od.getPendingNum() + num);
			od.setUsedQuota(od.getUsedQuota() + num);
			od.setRemainQuota(od.getRemainQuota() - num);
			tordDetailService.update(od);
		}
	}

	/*
	 * 新增物流单-修改订单产品明细
	 */
	private void updateOrdDetailPdt(
			List<TlogisticsOrdAddr> tlogisticsOrdAddrList, Long iord, Long icust) {
		for (TlogisticsOrdAddr loa : tlogisticsOrdAddrList) {
			int num = loa.getNum();
			Long ipdtHis = loa.getIpdtHis();
			Long ipdt = this.tpdtService.findTpdtHisById(ipdtHis).getIpdt();

			// 查询订单产品明细表，按年月日顺序排序
			TordDetailPdtCondition tdpc = new TordDetailPdtCondition();
			tdpc.setOrdDetailPdtStatus(ValidStatus.VALID);
			tdpc.setLockType(YesNoType.YES);
			tdpc.setIpdt(ipdt);
			tdpc.setIord(iord);
			List<Order> orders = new ArrayList<Order>();
			orders.add(Order.asc("year"));
			orders.add(Order.asc("month"));
			orders.add(Order.asc("day"));
			tdpc.setOrders(orders);
			List<TordDetailPdt> tordDetailPdtList = this.tordDetailPdtService
					.findByCon(tdpc);
			int tmpNum = num;
			for (TordDetailPdt odp : tordDetailPdtList) {
				int remainQuota = odp.getRemainQuota();
				if (remainQuota <= 0) {
					continue;
				}
				if (remainQuota >= tmpNum) {
					odp.setRemainQuota(remainQuota - tmpNum);
					odp.setPendingNum(odp.getPendingNum() + tmpNum);
					odp.setUsedQuota(odp.getUsedQuota() + tmpNum);
					this.tordDetailPdtService.update(odp);
					break;
				} else {
					odp.setPendingNum(odp.getPendingNum() + remainQuota);
					odp.setUsedQuota(odp.getUsedQuota() + remainQuota);
					odp.setRemainQuota(0);
					this.tordDetailPdtService.update(odp);
					tmpNum = tmpNum - remainQuota;
				}
			}
		}
	}

	public void logisticsOrdCancel(Tcust tcust, TcustReg tcustReg,
			Long ilogisticsOrd) throws BizException {

		TlogisticsOrd tlogisticsOrd = this.tLogisticsService
				.findTlogisticsOrdById(ilogisticsOrd);
		//查询物流单是否存在
		if(tlogisticsOrd==null){
			throw new BizException(0, "物流单不存在");
		}
		
		if (tlogisticsOrd.getLogisticsOrdStatus() != LogisticsOrdStatus.WAIT_AUDIT || 
				 tlogisticsOrd.getPayStatus() != PayStatus.WAIT_PAY) {
			throw new BizException(0, "当前状态不允许撤销操作");
		}
		if (!tlogisticsOrd.getIcust().equals(tcust.getIcust())) {
			throw new BizException(0, "非法操作：非当前用户物流单");
		}
		
		TpayNotify tpayNotify = this.tpayNotifyService.findByifkAndPayType(
				tlogisticsOrd.getIlogisticsOrd(), PayType.LOGISTICS);

		// 删除付款通知书
		if (tpayNotify != null) {

			// 若客户已付款,则不能撤销物流单
			if (PayStatus.WAIT_AUDIT.equals(tpayNotify.getPayNotifyStatus())) {
				throw new BizException(121, "已付款，不能撤销物流单");
			}

			this.tpayNotifyService.delete(tpayNotify.getIpayNotify());
		}

		// 删除物流单
		this.tLogisticsService.deleteTlogisticsOrd(tlogisticsOrd);

		// 修改订单产品明细
		Long iord = tlogisticsOrd.getIord();
		TlogisticsDetail ld = new TlogisticsDetail();
		ld.setIcust(tcust.getIcust());
		ld.setIlogisticsOrd(ilogisticsOrd);
		List<TlogisticsDetail> tlogisticsDetailList = this.tlogisticsDetailService
				.findListByDetail(ld);

		updateOrdDetailPdtCancel(tlogisticsDetailList, iord);

		// 修改订单明细
		updateOrdDetailCancel(tlogisticsDetailList, iord);

		// 日志记录
		Tlog tlog = new Tlog();
		tlog.setIfk(tcust.getIcust());
		tlog.setLogType(LogType.CUST);
		tlog.setOperType(OperType.LOGISTICS_MGR);
		tlog.setMemo("客户撤销物流计划");
		tlog.setPreData("");
		tlog.setData("客户" + tcustReg.getLongName() + "撤销物流计划,原物流编号是"
				+ tlogisticsOrd.getInnerOrdno());

		//添加跟踪信息
		tdetailTraceService.add4TlogisticsOrd(tlogisticsOrd, tcust, "客户 撤销 物流单(编号："+tlogisticsOrd.getInnerOrdno()+")", null, null);
		
		this.tlogService.save(tlog);

	}

	/**
	 * 撤销物流单-修改订单产品明细
	 */
	private void updateOrdDetailPdtCancel(
			List<TlogisticsDetail> tlogisticsDetailList, Long iord) {
		for (TlogisticsDetail ld : tlogisticsDetailList) {
			int pdtNum = ld.getPdtNum();
			Long iordDetailPdt = ld.getIordDetailPdt();

			TordDetailPdt odp = this.tordDetailPdtService.find(iordDetailPdt);
			odp.setPendingNum(odp.getPendingNum() - pdtNum);
			odp.setUsedQuota(odp.getUsedQuota() - pdtNum);
			odp.setRemainQuota(odp.getRemainQuota() + pdtNum);

			this.tordDetailPdtService.update(odp);
		}
	}

	/**
	 * 撤销物流单-修改订单明细
	 */
	private void updateOrdDetailCancel(List<TlogisticsDetail> tlogisticsDetailList, Long iord) {

		for (TlogisticsDetail ld : tlogisticsDetailList) {
			int pdtNum = ld.getPdtNum();
			
			TordDetail td = new TordDetail();
			td.setIord(iord);
			td.setIpdtHis(ld.getIpdtHis());
			TordDetail od = this.tordDetailService.findSelect(td).get(0);
			
			od.setPendingNum(od.getPendingNum() - pdtNum);
			od.setUsedQuota(od.getUsedQuota() - pdtNum);
			od.setRemainQuota(od.getRemainQuota() + pdtNum);
			this.tordDetailService.update(od);
		}
		
	}

	public TlogisticsCom queryLogisticsComByIpdt(Long id) {
		if (id == null)
			return null;
		return this.tlogisticsComService.findById(id);
	}

	public TlogisticsOrd saveTmpTlogisticsOrd(Tcust tcust, Long ifile) {
		TlogisticsOrd lo = new TlogisticsOrd();
		lo.setIcust(tcust.getIcust());
		lo.setLogisticsOrdStatus(LogisticsOrdStatus.WAIT_AUDIT);
		lo.setTempFlag(true);
		lo.setIuser(1L);
		lo.setUsername("系统");
		lo.setIfile(ifile);
		lo.setPayStatus(PayStatus.WAIT_PAY);
		this.tLogisticsService.addTlogisticsOrd(lo);
		return lo;
	}

	public List<LogisticsOrdAddrObj4Page> findLogisticsOrdAddrObj4PageList(
			Long ifile, List<Boolean> flagList) throws BizException {
		Boolean flag = true;
		Tfile tfile = this.tfileService.getById(ifile);

		CsvFileTranslator trans = new CsvFileTranslator(",");
		InputStreamReader reader = null;
		try {
			reader = new InputStreamReader(new ByteArrayInputStream(
					tfile.getContent()), "gbk");
		} catch (UnsupportedEncodingException e) {
			throw new BizException(1, "文件解析失败");
		}
		List<LogisticsOrdAddrObj4Page> list = trans.fromFile(reader,
				LogisticsOrdAddrObj4Page.class, true);
		for (LogisticsOrdAddrObj4Page loao4p : list) {
			
			String pdtNo = loao4p.getPdtNo().trim();
			String num = loao4p.getNum().trim();

			// 验证产品名称是否存在
			Tpdt tpdt = new Tpdt();
			tpdt.setPdtNo(pdtNo);
			tpdt.setDelFlag(false);
			List<Tpdt> tpdtList = this.tpdtService.findBySelect(tpdt);
			if (CollectionUtils.isEmpty(tpdtList)) {
				flag = (flag && false);
				loao4p.setPdtNo("<font color='#FF0000'>" + pdtNo + "</font>");
			}

			// 验证数量是否为大于0的整数
			int numInt = 0;
			try {
				numInt = Integer.parseInt(num);
				if (numInt <= 0) {
					flag = (flag && false);
					loao4p.setNum("<font color='#FF0000'>" + num + "</font>");
				}
			} catch (Exception e) {
				flag = (flag && false);
				loao4p.setNum("<font color='#FF0000'>" + num + "</font>");
			}
			
			if(StringUtils.isBlank(loao4p.getConsigneeAddr())){
				flag = (flag && false);
				loao4p.setConsigneeAddr("<font color='#FF0000'>此处必填</font>");
			}
			if(StringUtils.isBlank(loao4p.getFeeFlag())){
				flag = (flag && false);
				loao4p.setFeeFlag("<font color='#FF0000'>此处必填</font>");
			}
			if(StringUtils.isBlank(loao4p.getMobile())){
				flag = (flag && false);
				loao4p.setMobile("<font color='#FF0000'>此处必填</font>");
			}
			if(StringUtils.isBlank(loao4p.getName())){
				flag = (flag && false);
				loao4p.setName("<font color='#FF0000'>此处必填</font>");
			}
			if(StringUtils.isBlank(loao4p.getNum())){
				flag = (flag && false);
				loao4p.setNum("<font color='#FF0000'>此处必填</font>");
			}
			if(StringUtils.isBlank(loao4p.getPdtNo())){
				flag = (flag && false);
				loao4p.setPdtNo("<font color='#FF0000'>此处必填</font>");
			}

		}
		flagList.add(flag);
		return list;
	}

	public void analyzeAddrFileAndSaveAddr(Long ilogisticsOrd) throws BizException {
		TlogisticsOrd tlogisticsOrd = this.tLogisticsService.findTlogisticsOrdById(ilogisticsOrd);
		Long ifile = tlogisticsOrd.getIfile();
		// 解析文件
		Tfile tfile = this.tfileService.getById(ifile);
		CsvFileTranslator trans = new CsvFileTranslator(",");
		InputStreamReader reader = null;
		try {
			reader = new InputStreamReader(new ByteArrayInputStream(
					tfile.getContent()), "gbk");
		} catch (UnsupportedEncodingException e) {
			throw new BizException(1, "文件解析失败");
		}
		List<LogisticsOrdAddrObj4Page> list = trans.fromFile(reader,LogisticsOrdAddrObj4Page.class, true);

		// 按产品分类
		Map<Long, List<LogisticsOrdAddrObj4Page>> loao4pMap = new HashMap<Long, List<LogisticsOrdAddrObj4Page>>();
		for (LogisticsOrdAddrObj4Page loao4p : list) {
			String pdtNo = loao4p.getPdtNo().trim();
			TpdtHis ph = new TpdtHis();
			ph.setPdtNo(pdtNo);
			List<TpdtHis> tpdtHisList = this.tpdtService
					.findTpdtHisBySelect(ph);
			Long ipdtHis = tpdtHisList.get(0).getIpdtHis();
			if (CollectionUtils.isEmpty(loao4pMap.get(ipdtHis))) {
				loao4pMap.put(ipdtHis,
						new ArrayList<LogisticsOrdAddrObj4Page>());
			}
			loao4pMap.get(ipdtHis).add(loao4p);
		}
		
		TlogisticsOrdAddr loa = new TlogisticsOrdAddr();
		loa.setIlogisticsOrd(ilogisticsOrd);
		List<TlogisticsOrdAddr> tlogisticsOrdAddrList = this.tlogisticsOrdAddrService
				.findListSelect(loa);
		if(CollectionUtils.isEmpty(tlogisticsOrdAddrList)){
			Iterator<Long> iter = loao4pMap.keySet().iterator();
			int serial = 0;
			while (iter.hasNext()) {
				Long ipdtHis = iter.next();
				List<LogisticsOrdAddrObj4Page> pList = loao4pMap.get(ipdtHis);
				for (LogisticsOrdAddrObj4Page p : pList) {
					int num = Integer.parseInt(p.getNum());
					loa = new TlogisticsOrdAddr();
					loa.setIlogisticsOrd(ilogisticsOrd);
					loa.setIpdtHis(ipdtHis);
					loa.setNum(num);
					loa.setSerial(serial++);
					loa.setIuser(1L);
					loa.setUsername("系统");
					
					loa.setConsigneeAddr(p.getConsigneeAddr());
					loa.setConsigneeName(p.getName());
					loa.setConsigneeMobile(p.getMobile());
					loa.setFeeFlag(p.getFeeFlag());
					this.tlogisticsOrdAddrService.save(loa);
				}
			}
		}
	}

	public String updateLogisticsOrdAndGetSpecifyDelivery(Long ilogisticsOrd, Long iord) throws BizException {

		//验证订单额度和发货量
		TordDetail td = new TordDetail();
		td.setIord(iord);
		List<TordDetail> details = this.tordDetailService.findSelect(td);
		int totleNum = 0;
		Set<Long> ipdtHisSet = new HashSet<Long>();
		
		for (TordDetail tordDetail : details) {
			int quota = tordDetail.getRemainQuota();
			Long ipdtHis = tordDetail.getIpdtHis();

			TlogisticsOrdAddr loa = new TlogisticsOrdAddr();
			loa.setIlogisticsOrd(ilogisticsOrd);
			loa.setIpdtHis(ipdtHis);
			List<TlogisticsOrdAddr> tlogisticsOrdAddrList = this.tlogisticsOrdAddrService
					.findListSelect(loa);
			int num = 0;
			for (TlogisticsOrdAddr tlogisticsOrdAddr : tlogisticsOrdAddrList) {
				num += tlogisticsOrdAddr.getNum();
				totleNum += tlogisticsOrdAddr.getNum();
			}

			String pdtName = this.tpdtService.findTpdtHisById(ipdtHis)
					.getName();
			if (quota < num) {
				throw new BizException(1, pdtName + "的发货量超过订单可用额度");
			}
			
			ipdtHisSet.add(ipdtHis);
		}

		TlogisticsOrdAddr loa = new TlogisticsOrdAddr();
		loa.setIlogisticsOrd(ilogisticsOrd);
		List<TlogisticsOrdAddr> tlogisticsOrdAddrList = this.tlogisticsOrdAddrService.findListSelect(loa);
		BigDecimal totleAmt = new BigDecimal(0);
		for(TlogisticsOrdAddr tlogisticsOrdAddr:tlogisticsOrdAddrList){
			
			Long ipdtHis = tlogisticsOrdAddr.getIpdtHis();
			String pdtName = this.tpdtService.findTpdtHisById(ipdtHis)
					.getName();
			if(!ipdtHisSet.contains(ipdtHis)){
				throw new BizException(1, "订单未包含"+pdtName);
			}
			
			BigDecimal price = this.tpdtService.findTpdtHisById(ipdtHis).getPrice();
			BigDecimal num = new BigDecimal(tlogisticsOrdAddr.getNum());
			BigDecimal amt = price.multiply(num);
			totleAmt = totleAmt.add(amt);
		}
		
		Tord tord = this.tordService.find(iord);
		validateOrdAmt(tord, totleAmt);
		
		//修改物流单
		TlogisticsOrd tlogisticsOrd = this.tLogisticsService.findTlogisticsOrdById(ilogisticsOrd);
		tlogisticsOrd.setIord(iord);
		tlogisticsOrd.setNum(totleNum);
		tlogisticsOrd.setAmt(totleAmt);
		this.tLogisticsService.updateTlogisticsOrd(tlogisticsOrd);
		
		//计算最快发货日期
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		for (TordDetail tordDetail : details) {
			Long ipdtHis = tordDetail.getIpdtHis();

			TlogisticsOrdAddr addr = new TlogisticsOrdAddr();
			addr.setIlogisticsOrd(ilogisticsOrd);
			addr.setIpdtHis(ipdtHis);
			List<TlogisticsOrdAddr> addrList = this.tlogisticsOrdAddrService
					.findListSelect(addr);
			int num = 0;
			for (TlogisticsOrdAddr tlogisticsOrdAddr : addrList) {
				num += tlogisticsOrdAddr.getNum();
			}
			
			String specifyDeliveryStr = getSpecifyDeliveryStr(String.valueOf(num), ipdtHis, iord);
			String[] sdArr = specifyDeliveryStr.split("-");
			int year = Integer.parseInt(sdArr[0]);
			int month = Integer.parseInt(sdArr[1]);
			int day = Integer.parseInt(sdArr[2]);
			
			Calendar sdCal = Calendar.getInstance(Locale.CHINA);
			sdCal.set(year, month-1, day);
			
			if(sdCal.after(cal)){
				cal = sdCal;
			}
			
		}
		
		return sdf.format(cal.getTime());
	}

	public void updateLogisticsOrd(Long ilogisticsOrd, String specifyDelivery,Tcust tcust,TcustReg tcustReg){
		TlogisticsOrd tlogisticsOrd = this.tLogisticsService.findTlogisticsOrdById(ilogisticsOrd);
		
		Date date = null;
		try {
			date = sdf.parse(specifyDelivery);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		tlogisticsOrd.setSpecifyDelivery(date);
		tlogisticsOrd.setTempFlag(false);
		this.tLogisticsService.updateTlogisticsOrd(tlogisticsOrd);
		
		Long iord = tlogisticsOrd.getIord();

		TlogisticsOrdAddr loa = new TlogisticsOrdAddr();
		loa.setIlogisticsOrd(ilogisticsOrd);
		List<TlogisticsOrdAddr> tlogisticsOrdAddrList = this.tlogisticsOrdAddrService
				.findListSelect(loa);

		// 保存物流单明细
		saveLogisticsDetail(tlogisticsOrdAddrList, iord, tcust.getIcust());

		// 修改订单明细
		updateOrdDetail(tlogisticsOrdAddrList, iord);

		// 修改订单产品明细
		updateOrdDetailPdt(tlogisticsOrdAddrList, iord, tcust.getIcust());

		// 修改订单表 已发货货款
		BigDecimal amt = tlogisticsOrd.getAmt();
		Tord tord = this.tordService.find(iord);
		tord.setAmtOfDelivery(tord.getAmtOfDelivery().add(amt));
		this.tordService.update(tord);
				
		// 日志记录
		Tlog tlog = new Tlog();
		tlog.setIfk(tcust.getIcust());
		tlog.setLogType(LogType.CUST);
		tlog.setOperType(OperType.LOGISTICS_MGR);
		tlog.setMemo("客户制定物流计划");
		tlog.setPreData("");
		tlog.setData("客户" + tcustReg.getLongName() + "制定物流计划,物流编号是"
				+ tlogisticsOrd.getInnerOrdno());
		this.tlogService.save(tlog);
		
		//添加跟踪信息
		tdetailTraceService.add4TlogisticsOrd(tlogisticsOrd, tcust, "客户发货：("+this.genLogisticsTraceInfo(tlogisticsOrd)+");", null, null);
		
		//发送邮件
		custMailService.sendCustLogisticsNotify(tcust, tlogisticsOrd);
	}
	
	/**
	 * 关联设置物流订单对应的订单信息
	 * 
	 * @param list
	 */
	public void setTord4TlogisticsOrd(List<TlogisticsOrd> list) {
		if (list == null || list.size() < 1 || list.get(0) == null)
			return;
		for (TlogisticsOrd tlogisticsOrd : list) {
			tlogisticsOrd.setTord(this.tordService.find(tlogisticsOrd.getIord()));
			tlogisticsOrd.setDebts(tlogisticsOrd.getLogisticsFreight()); //欠费：默认为物流费
			
			//计算欠费
			TpayNotify notify = this.tpayNotifyService.findByifkAndPayType(tlogisticsOrd.getIlogisticsOrd(), PayType.LOGISTICS);
			if(notify != null && (notify.getPayNotifyStatus() == PayStatus.HAVE_PAY
					|| notify.getPayNotifyStatus() == PayStatus.PART_PAY)) {
				tlogisticsOrd.setDebts(tlogisticsOrd.getDebts().subtract(
						notify.getHavepayAmt()).setScale(2, RoundingMode.HALF_UP)); //扣去已支付的金额
			}
		}
	}

	/**
	 * 
	 * @param tlogisticsOrd
	 */
	public void setTordAlone(TlogisticsOrd tlogisticsOrd) {
		if (tlogisticsOrd == null)
			return;
		tlogisticsOrd.setTord(this.tordService.find(tlogisticsOrd.getIord()));
	}
	
	public TlogisticsOrdAddr getTlogisticsOrdAddrById(Long id){
		return tlogisticsOrdAddrService.getTlogisticsOrdAddrById(id);
	}
	
	public LogisticsCompany getlogisticsComCode(String name){
		if(name.indexOf("韵达")>-1){
			return LogisticsCompany.yunda;
		}else if(name.indexOf("宅急送")>-1){
			return LogisticsCompany.zhaijisong;
		}else if(name.indexOf("顺丰")>-1){
			return LogisticsCompany.shunfeng;
		}else if(name.indexOf("ems")>-1){
			return LogisticsCompany.ems;
		}else if(name.indexOf("申通")>-1){
			return LogisticsCompany.shentong;
		}else if(name.indexOf("嘉里大通")>-1){
			return LogisticsCompany.jialidatong;
		}else if(name.indexOf("盛丰")>-1){
			return LogisticsCompany.shengfeng;
		}else if(name.indexOf("民航")>-1){
			return LogisticsCompany.minhang;
		}
		return LogisticsCompany.ems;
	}
	
	public String genLogisticsTraceInfo(TlogisticsOrd tlogisticsOrd){
		StringBuffer sb = new StringBuffer();
		
		TlogisticsOrdAddr loa = new TlogisticsOrdAddr();
		loa.setIlogisticsOrd(tlogisticsOrd.getIlogisticsOrd());
		List<TlogisticsOrdAddr> tlogisticsOrdAddrLists = this.tlogisticsOrdAddrService
				.findListSelect(loa);
		sb.append("编号:").append(tlogisticsOrd.getInnerOrdno()).append(",状态：")
		.append(mapBiz.getMapByType(DictType.logistics_ord_status.toString()).get(tlogisticsOrd.getLogisticsOrdStatus().toString()))
		.append(",支付状态：").append(mapBiz.getMapByType(DictType.pay_status.toString()).get(tlogisticsOrd.getPayStatus().toString()));
		if(!CollectionUtils.isEmpty(tlogisticsOrdAddrLists)){
			sb.append("\n 物流详细地址列表[");
			for(TlogisticsOrdAddr t : tlogisticsOrdAddrLists){
				sb.append("地址:").append(t.getConsigneeAddr()).append(",联系人:").append(t.getConsigneeName()).append(",联系方式:").append(t.getConsigneeMobile())
				.append(",产品:").append(tpdtService.findTpdtHisById(t.getIpdtHis()).getName()).append(",数量：").append(t.getNum()).append(";");
			}
			sb.append("]");
		}
		return sb.toString();
		
	}
}
