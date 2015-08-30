/**    
 * @Title: FileDownload.java  
 * @Package com.newland.posmall.biz.file  
 * @Description: TODO(用一句话描述该文件做什么)  
 * @author chenwenjing    
 * @date 2014-9-5 上午11:14:00  
 * @version V1.0    
 */

package com.newland.posmall.biz.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.ohuyo.rapid.base.entity.ERPMaintenance;
import org.ohuyo.rapid.base.entity.ERPSync;
import org.ohuyo.rapid.base.entity.TsysParam;
import org.ohuyo.rapid.base.service.ERPSyncService;
import org.ohuyo.rapid.base.service.MaintenanceManageService;
import org.ohuyo.rapid.base.service.TsysParamService;
import org.ohuyo.rapid.file.CsvFileTranslator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.newland.posmall.Application;
import com.newland.posmall.base.service.TagmtService;
import com.newland.posmall.base.service.TcustRegService;
import com.newland.posmall.base.service.TlogisticsOrdAddrService;
import com.newland.posmall.base.service.TlogisticsService;
import com.newland.posmall.base.service.TordService;
import com.newland.posmall.base.service.TpdtService;
import com.newland.posmall.bean.basebusi.Tagmt;
import com.newland.posmall.bean.basebusi.TlogisticsOrd;
import com.newland.posmall.bean.basebusi.TlogisticsOrdAddr;
import com.newland.posmall.bean.basebusi.Tord;
import com.newland.posmall.bean.basebusi.TordDetailPdt;
import com.newland.posmall.bean.basebusi.Tpay;
import com.newland.posmall.bean.basebusi.TpayNotify;
import com.newland.posmall.bean.basebusi.Tpdt;
import com.newland.posmall.bean.basebusi.TpdtHis;
import com.newland.posmall.bean.customer.Tcust;
import com.newland.posmall.bean.customer.TcustReg;
import com.newland.posmall.bean.customer.Twarranty;
import com.newland.posmall.bean.dict.LogisticsOrdStatus;
import com.newland.posmall.bean.dict.OrdDetailPdtType;
import com.newland.posmall.bean.dict.PayType;
import com.newland.posmall.bean.dict.SysParamType;
import com.newland.posmall.bean.model4page.Agmt4Page;
import com.newland.posmall.bean.model4page.AgmtDetail4Page;
import com.newland.posmall.biz.common.MapBiz;
import com.newland.posmall.biz.cust.Agmt4PageBiz;
import com.newland.posmall.biz.file.bean.AgmtInfo;
import com.newland.posmall.biz.file.bean.CustInfo;
import com.newland.posmall.biz.file.bean.LogisticsOrdInfo;
import com.newland.posmall.biz.file.bean.OrderInfo;
import com.newland.posmall.biz.file.bean.PayInfo;
import com.newland.posmall.biz.file.bean.PayNotifyInfo;
import com.newland.posmall.biz.file.bean.WarrantyInfo;
import com.newland.posmall.controller.cust.model.LogisticsDown4Page;
import com.newland.posmall.controller.cust.model.OrdDown4Page;
import com.newland.posmall.utils.CtUtils;

/**
 * @ClassName: FileDownload
 * @Description: 文件下载
 * @author chenwenjing
 * @date 2014-9-5 上午11:14:00
 * 
 */
@Service
@Transactional
public class FileDownload {

	@Resource
	private ERPSyncService eRPSyncService;

	@Resource
	private MapBiz mapBiz;

	@Resource
	private TcustRegService tcustRegService;

	@Resource
	private TpdtService tpdtService;

	@Resource
	private TordService tordService;

	@Resource
	private TlogisticsService tlogisticsService;

	@Resource
	private TagmtService tagmtService;
	
	@Resource
	private Agmt4PageBiz agmt4PageBiz;
	
	@Autowired
	TlogisticsOrdAddrService logisticsOrdAddrService;
	
	@Resource
	private MaintenanceManageService maintenanceManageService;
	
	@Autowired
	private TsysParamService sysParamService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 
	 * @Description: 下载客户资料
	 * @author chenwenjing
	 * @date 2014-9-5 下午9:02:37
	 */
	public File custInfoDown(List<Tcust> list) throws Exception {
		List<CustInfo> custInfoList = new ArrayList<CustInfo>();
		Map<String, String> custTypeMap = mapBiz.getMapByType("cust_type");
		Map<String, String> companyTypeMap = mapBiz
				.getMapByType("company_type");
		Map<String, String> custStatusMap = mapBiz.getMapByType("cust_status");
		Map<String, String> creditLevelMap = mapBiz
				.getMapByType("credit_level");
		for (int i = 0; i < list.size(); i++) {
			Tcust cust = list.get(i);
			CustInfo custInfo = new CustInfo();
			custInfo.setIcust(cust.getIcust());
			custInfo.setLoginName(cust.getLoginName());
			custInfo.setCustStatus(custStatusMap.get(String.valueOf(cust
					.getCustStatus())));
			custInfo.setCreditLevel(creditLevelMap.get(String.valueOf(cust
					.getCreditLevel())));
			custInfo.setName(cust.getTcustReg().getName());
			custInfo.setLongName(cust.getTcustReg().getLongName());
			custInfo.setRegAddr(cust.getTcustReg().getRegAddr());
			custInfo.setCustType(custTypeMap.get(String.valueOf(cust
					.getTcustReg().getCustType())));
			custInfo.setContactName(cust.getTcustReg().getContactName());
			custInfo.setTel(cust.getTcustReg().getTel());
			custInfo.setMobile(cust.getTcustReg().getMobile());
			custInfo.setEmail(cust.getTcustReg().getEmail());
			custInfo.setFax(cust.getTcustReg().getFax());
			custInfo.setRegDate(cust.getTcustReg().getRegDate());
			custInfo.setRetCap(cust.getTcustReg().getRetCap());
			custInfo.setSummary(cust.getTcustReg().getSummary());
			custInfo.setBusLic(cust.getTcustReg().getBusLic());
			custInfo.setOrgCode(cust.getTcustReg().getOrgCode());
			custInfo.setTaxReg(cust.getTcustReg().getTaxReg());
			custInfo.setCorporationName(cust.getTcustReg().getCorporationName());
			custInfo.setAccount(cust.getTcustReg().getAccount());
			custInfo.setCompanyType(companyTypeMap.get(String.valueOf(cust
					.getTcustReg().getCompanyType())));
			custInfo.setBank(cust.getTcustReg().getBank());
			custInfo.setBusLicIfile(cust.getTcustReg().getBusLicIfile());
			custInfo.setOrgCodeIfile(cust.getTcustReg().getOrgCodeIfile());
			custInfo.setCustNo(cust.getTcustReg().getCustNo());
			custInfo.setTaxRegIfile(cust.getTcustReg().getTaxRegIfile());
			custInfoList.add(custInfo);
		}
		CsvFileTranslator trans = new CsvFileTranslator(",");
		Date currDate = new Date();
		String curr = new SimpleDateFormat("yyyyMMdd").format(currDate);
		String fileName = curr + ".csv";
		File file = File.createTempFile("CIF", fileName,
				new File(Application.getTemplatePath()));
		return trans.fromObject(custInfoList, file, "gbk");
	}

	/**
	 * 
	 * @Description: 订单资料下载
	 * @author chenwenjing
	 * @date 2014-9-6 上午10:25:00
	 */
	public File orderInfoDown(List<OrdDown4Page> list, String trace)
			throws Exception {
		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();
		Map<String, String> ordStatusMap = mapBiz.getMapByType("ord_status");
		Map<String, String> payStatusMap = mapBiz.getMapByType("pay_status");
		for (int i = 0; i < list.size(); i++) {
			Tord tord = list.get(i).getTord();
			TcustReg tcustReg = tcustRegService.find(tord.getIcust());
			for (int j = 0; j < list.get(i).getDetailPdtList().size(); j++) {
				TordDetailPdt dt = list.get(i).getDetailPdtList().get(j);
				OrderInfo orderInfo = new OrderInfo();
				orderInfo.setCustName(tcustReg.getName());
				orderInfo.setOrdNo(tord.getOrdNo());
				orderInfo.setOrdStatus(ordStatusMap.get(String.valueOf(tord
						.getOrdStatus())));
				orderInfo.setPayStatus(payStatusMap.get(String.valueOf(tord
						.getPayStatus())));
				
				String ordDetailPdtType="";
				if(dt.getOrdDetailPdtType().name().equals(OrdDetailPdtType.STOCK.name())){
					ordDetailPdtType = "库存";
				}
				
				if(dt.getOrdDetailPdtType().name().equals(OrdDetailPdtType.DAILY_OUTPUT.name())){
					ordDetailPdtType = "日产量";
				}
				orderInfo.setOrdDetailPdtType(ordDetailPdtType);
				orderInfo.setNum(dt.getNum());
				orderInfo.setStartSn(dt.getStartSn());
				orderInfo.setEndSn(dt.getEndSn());
				orderInfo.setGenTime(tord.getGenTime());
				orderInfo.setRequiredTime(dt.getYear() + "/" + dt.getMonth()
						+ "/" + dt.getDay());
				orderInfo.setPdtName(tpdtService.find(dt.getIpdt()).getName());
				orderInfoList.add(orderInfo);
			}
		}
		CsvFileTranslator trans = new CsvFileTranslator(",");
		String fileName = trace + ".csv";
		File file = new File(Application.getTemplatePath() + File.separator
				+ "ORD" + fileName);
		// File file = File.createTempFile("ORD", fileName,
		// new File(Application.getTemplatePath()));
		return trans.fromObject(orderInfoList, file, "gbk");
	}

	/**
	 * 
	 * @Description: 订货协议信息下载
	 * @author chenwenjing
	 * @date 2014-9-6 上午11:04:15
	 */
	public File agmtInfoDown(List<Tagmt> list) throws Exception {
		List<AgmtInfo> agmtInfoList = new ArrayList<AgmtInfo>();
		Map<String, String> agmtStatusMap = mapBiz.getMapByType("agmt_status");
		Map<String, String> agmtDetailStatusMap = mapBiz
				.getMapByType("agmt_detail_status");
		for (int i = 0; i < list.size(); i++) {
			Tagmt agmt = list.get(i);
			
			Agmt4Page agmt4Page = this.agmt4PageBiz.findAgmt4Page(agmt);
			
			List<AgmtDetail4Page> agmtDetails = agmt4Page.getAgmtDetail4PageList();
			for (int j = 0; j < agmtDetails.size(); j++) {
				AgmtInfo atIf = new AgmtInfo();
				atIf.setStartTime(agmt.getStartTime());
				atIf.setEndTime(agmt.getEndTime());
				atIf.setDeposit(agmt.getDeposit());
				atIf.setEfficientTime(agmt.getEfficientTime());
				atIf.setUsedDeposit(agmt.getUsedDeposit());
				atIf.setRemainDeposit(agmt.getRemainDeposit());
				atIf.setAgmtStatus(agmtStatusMap.get(String.valueOf(agmt
						.getAgmtStatus())));
				atIf.setAgmtNo(agmt.getAgmtNo());
//				atIf.setTotalNum(agmt.getTotalNum());
//				atIf.setTotalAmt(agmt.getTotalAmt());
//				atIf.setTpdtNames(agmt.getTpdtNames());
				atIf.setGenTime(agmt.getGenTime());
				atIf.setUpdTime(agmt.getUpdTime());
				atIf.setNum(agmtDetails.get(j).getTagmtDetail().getNum());
				atIf.setAgmtDetailStatus(agmtDetailStatusMap.get(String
						.valueOf(agmtDetails.get(j).getTagmtDetail().getAgmtDetailStatus())));
				atIf.setRate(agmtDetails.get(j).getTagmtDetail().getRate());
				atIf.setUsedQuota(agmtDetails.get(j).getTagmtDetail().getUsedQuota());
				atIf.setRemainQuota(agmtDetails.get(j).getTagmtDetail().getRemainQuota());
				atIf.setPdtNo(agmtDetails.get(j).getTpdtHis().getPdtNo());
				atIf.setName(agmtDetails.get(j).getTpdtHis().getName());
				atIf.setLongName(agmtDetails.get(j).getTpdtHis().getLongName());
				atIf.setPrice(agmtDetails.get(j).getTpdtHis().getPrice());
				atIf.setUserName(agmtDetails.get(j).getTpdtHis().getUserName());
				atIf.setMemo(agmtDetails.get(j).getTpdtHis().getMemo());
				agmtInfoList.add(atIf);
			}
		}
		CsvFileTranslator trans = new CsvFileTranslator(",");
		Date currDate = new Date();
		String curr = new SimpleDateFormat("yyyyMMdd").format(currDate);
		String fileName = curr + ".csv";
		File file = File.createTempFile("AGMT", fileName,
				new File(Application.getTemplatePath()));
		return trans.fromObject(agmtInfoList, file, "gbk");
	}

	/**
	 * 
	 * @Description: 物流单下载
	 * @author chenwenjing
	 * @date 2014-9-6 上午11:07:20
	 */
	public File lgcsOrdInfoDown(List<LogisticsDown4Page> list, String trace)
			throws Exception {
		List<LogisticsOrdInfo> logisticsOrdInfoList = new ArrayList<LogisticsOrdInfo>();
		for (int i = 0; i < list.size(); i++) {
			TlogisticsOrd tlgtsOrd = list.get(i).getTlogisticsOrd();
			List<TlogisticsOrdAddr> addrList = list.get(i)
					.getTlogisticsOrdAddrList();
			for (int j = 0; j < addrList.size(); j++) {
				if(Boolean.TRUE.equals(addrList.get(j).getDelFlag()))continue;//如果为删除地址则此地址为被拆单的地址不打印出来
				LogisticsOrdInfo lgtsOrdIf = new LogisticsOrdInfo();
				lgtsOrdIf.setInnerOrdno(tlgtsOrd.getInnerOrdno()+"_"+addrList.get(j).getSerial());
				lgtsOrdIf.setErpOrdId(list.get(i).getErpOrdId());
				lgtsOrdIf.setNum(addrList.get(j).getNum());
				lgtsOrdIf.setSpecifyDelivery(tlgtsOrd.getSpecifyDelivery());
				lgtsOrdIf.setGenTime(tlgtsOrd.getGenTime());
				TpdtHis his = tpdtService.findTpdtHisById(addrList.get(j).getIpdtHis());
				lgtsOrdIf.setFreightWay(addrList.get(j).getLogisticsComName());
				lgtsOrdIf.setConsignee(addrList.get(j).getConsigneeName());
				lgtsOrdIf.setPhone(addrList.get(j).getConsigneeMobile());
				BigDecimal price = addrList.get(j).getPrice().multiply(new BigDecimal(addrList.get(j).getNum())).setScale(2, BigDecimal.ROUND_HALF_UP);
				lgtsOrdIf.setFreight(price);
				lgtsOrdIf.setIsPay(addrList.get(j).getFeeFlag());
				lgtsOrdIf.setPdtName(his.getName());
				lgtsOrdIf.setModel(his.getLongName());
				lgtsOrdIf.setSn("");
				lgtsOrdIf.setAddrName(addrList.get(j).getConsigneeAddr());
				lgtsOrdIf.setCustName(tcustRegService.find(tlgtsOrd.getIcust()).getName());
				lgtsOrdIf.setOrdNo(tordService.find(tlogisticsService.findTlogisticsOrdById(addrList.get(j).getIlogisticsOrd()).getIord()).getOrdNo());
				logisticsOrdInfoList.add(lgtsOrdIf);
			}
		}
		CsvFileTranslator trans = new CsvFileTranslator(",");
		String fileName = trace + ".csv";
		File file = new File(Application.getTemplatePath() + File.separator
				+ "WL" + fileName);
		// File file = File.createTempFile("LGCSORD", fileName, new File(
		// Application.getTemplatePath()));
		return trans.fromObject(logisticsOrdInfoList, file, "gbk");
	}

	/**
	 * 
	 * @Description: 支付通知书下载
	 * @author chenwenjing
	 * @date 2014-9-11 下午12:38:35
	 */
	public File PayNotifyInfoDown(List<TpayNotify> list) throws Exception {
		List<PayNotifyInfo> payNotifyInfoList = new ArrayList<PayNotifyInfo>();
		Map<String, String> payStatusMap = mapBiz.getMapByType("pay_status");
		Map<String, String> payTypeMap = mapBiz.getMapByType("pay_type");
		for (int i = 0; i < list.size(); i++) {
			TpayNotify tpayNotify = list.get(i);
			PayNotifyInfo payNotifyInfo = new PayNotifyInfo();
			TcustReg custReg = tcustRegService.find(tpayNotify.getIcust());
			payNotifyInfo.setCustName(custReg.getName());

			if (tpayNotify.getPayType() == PayType.BAIL) {
				Tagmt tagmt = tagmtService.findById(tpayNotify.getIfk());
				if (tagmt != null) {
					payNotifyInfo.setAgmtNo(tagmt.getAgmtNo());
					payNotifyInfo.setTotalAmt(tagmt.getDeposit());
				}
			}
			if (tpayNotify.getPayType() == PayType.ORDER) {
				Tord ord = tordService.find(tpayNotify.getIfk());
				if (ord != null) {
					payNotifyInfo.setOrderNo(ord.getOrdNo());
					payNotifyInfo.setTotalAmt(ord.getAmt());
					Tagmt tagmt = tagmtService.findById(ord.getIagmt());
					if (tagmt != null) {
						payNotifyInfo.setAgmtNo(tagmt.getAgmtNo());
					}
				}
			}
			if (tpayNotify.getPayType() == PayType.LOGISTICS) {
				TlogisticsOrd logisticsOrd = tlogisticsService
						.findTlogisticsOrdById(tpayNotify.getIfk());
				if (logisticsOrd != null) {
					payNotifyInfo.setLogisticsNo(logisticsOrd.getInnerOrdno());
					payNotifyInfo.setTotalAmt(logisticsOrd.getLogisticsFreight());
					Tord ord = tordService.find(logisticsOrd.getIord());
					if (ord != null) {
						payNotifyInfo.setOrderNo(ord.getOrdNo());
						Tagmt tagmt = tagmtService.findById(ord.getIagmt());
						if (tagmt != null) {
							payNotifyInfo.setAgmtNo(tagmt.getAgmtNo());
						}
					}
				}
			}

			payNotifyInfo.setPayType(payTypeMap.get(String.valueOf(tpayNotify
					.getPayType())));
			payNotifyInfo.setPayNotifyNo(tpayNotify.getPayNotifyNo());
			payNotifyInfo.setMemo(tpayNotify.getMemo());
			payNotifyInfo.setGenTime(tpayNotify.getGenTime());
			payNotifyInfo.setUpdTime(tpayNotify.getUpdTime());
			payNotifyInfo.setPayNotifyStatus(payStatusMap.get(String
					.valueOf(tpayNotify.getPayNotifyStatus())));
			payNotifyInfoList.add(payNotifyInfo);
		}
		CsvFileTranslator trans = new CsvFileTranslator(",");
		Date currDate = new Date();
		String curr = new SimpleDateFormat("yyyyMMdd").format(currDate);
		String fileName = curr + ".csv";
		File file = File.createTempFile("PAY", fileName,
				new File(Application.getTemplatePath()));
		return trans.fromObject(payNotifyInfoList, file, "gbk");
	}

	/**
	 * 
	 * @Description:下载付款凭证
	 * @author chenwenjing
	 * @date 2014-9-6 上午11:25:51
	 */
	public File PayInfoDown(List<Tpay> list) throws Exception {
		List<PayInfo> PayInfoList = new ArrayList<PayInfo>();
		Map<String, String> payStatusMap = mapBiz.getMapByType("pay_status");
		Map<String, String> payTypeMap = mapBiz.getMapByType("pay_type");
		for (int i = 0; i < list.size(); i++) {
			Tpay tpay = list.get(i);
			PayInfo payInfo = new PayInfo();
			payInfo.setPayStatus(payStatusMap.get(String.valueOf(tpay
					.getPayStatus())));
			payInfo.setAmt(tpay.getAmt());
			payInfo.setPayNo(tpay.getPayNo());
			payInfo.setRemittanceNo(tpay.getRemittanceNo());
			// payInfo.setAccount(tpay.getAccount());
			// payInfo.setBank(tpay.getBank());
			payInfo.setGenTime(tpay.getGenTime());
			payInfo.setUpdTime(tpay.getUpdTime());
			payInfo.setPayType(payTypeMap.get(String.valueOf(tpay
					.getTpayDetail().getPayType())));
			payInfo.setCustName(tpay.getTpayDetail().getCustName());
			payInfo.setUserName(tpay.getTpayDetail().getUserName());
			PayInfoList.add(payInfo);
		}
		CsvFileTranslator trans = new CsvFileTranslator(",");
		Date currDate = new Date();
		String curr = new SimpleDateFormat("yyyyMMdd").format(currDate);
		String fileName = curr + ".csv";
		File file = File.createTempFile("PAY", fileName,
				new File(Application.getTemplatePath()));
		return trans.fromObject(PayInfoList, file, "gbk");
	}
	/**
	 * 
	* @Description: 维保受理申请单下载
	* @author chenwenjing    
	* @date 2014-10-27 上午9:28:15
	 */
	public File warrantyInfoDown(List<Twarranty> list, String trace)
			throws Exception {
		List<WarrantyInfo> warrantyInfoList = new ArrayList<WarrantyInfo>();
		for (int i = 0; i < list.size(); i++) {
			Twarranty twarranty = list.get(i);
			WarrantyInfo warrantyInfo = new WarrantyInfo();
			warrantyInfo.setIwarranty(twarranty.getIwarranty());
			warrantyInfo.setSeqNo(twarranty.getSeqNo());
			warrantyInfo.setPdtNo(twarranty.getPdtNo());
			warrantyInfo.setWarrantyTime(twarranty.getWarrantyTime());
			warrantyInfo.setAcceptTime(twarranty.getAcceptTime());
			warrantyInfo.setCustName(twarranty.getCustName());
			warrantyInfo.setRemark(twarranty.getRemark());
			warrantyInfoList.add(warrantyInfo);
		}
		CsvFileTranslator trans = new CsvFileTranslator(",");
		String fileName = trace + ".csv";
		File file = new File(Application.getTemplatePath() + File.separator
				+ "WTY" + fileName);
		return trans.fromObject(warrantyInfoList, file, "gbk");
	}

	/**
	 * 
	 * @Description:将erp销货单保存到数据库
	 * @author chenwenjing
	 * @date 2014-9-6 上午11:23:55
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
	public void insertSalesOrd(String fileName) throws Exception {
		CsvFileTranslator trans = new CsvFileTranslator("|");
		File file = new File(Application.getTemplatePath() + "/" + fileName);
		if (!file.exists()) {
			throw new RuntimeException("文件不存在");
		}
		InputStreamReader read = new InputStreamReader(
				new FileInputStream(file), "gbk");// 考虑到编码格式

		List<ERPSync> list = trans.fromFile(read, ERPSync.class, false);

		if (CollectionUtils.isEmpty(list))
			throw new RuntimeException("列表为空");

		for (ERPSync eRPSync : list) {// 先判断记录是否存在，不存在则插入，存在则更新
			if(StringUtils.isBlank(eRPSync.getDb())||
					StringUtils.isBlank(eRPSync.getDh())||
					StringUtils.isBlank(eRPSync.getXh())) {//没有单别、单号、序号的数据不插入
				continue;
			}
			ERPSync eRPSyncOld = eRPSyncService.getByCondition(eRPSync.getDb(),
					eRPSync.getDh(), eRPSync.getXh());
			if (eRPSyncOld == null) {
				logger.info(eRPSync.getShdh());
				eRPSyncService.save(eRPSync);
			} else {// 因hibernate不允许同一标识不同实体做update，所以这里需用merge
				eRPSync.setIerpSync(eRPSyncOld.getIerpSync());
				eRPSync.setGenTime(eRPSyncOld.getGenTime());
				eRPSync.setVersion(eRPSyncOld.getVersion());
				eRPSyncService.merge(eRPSync);
			}
			if (StringUtils.isBlank(eRPSync.getDdh())){
				logger.warn("同步物流单：[" + eRPSync.getDh() + "], 无本系统单号");
				continue;
			}
			TlogisticsOrd ord = new TlogisticsOrd();
			ord.setInnerOrdno(eRPSync.getDdh());
			List<TlogisticsOrd> logis = this.tlogisticsService.findListByObj(ord);
			if (CollectionUtils.isEmpty(logis)){
				logger.warn("未匹配到内部单号：[内部单号：" + eRPSync.getDdh() + ", 易飞单号:" + eRPSync.getDh() + "]");
				continue;
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			for (TlogisticsOrd logi: logis){
				TlogisticsOrdAddr addr = new TlogisticsOrdAddr();
				addr.setIlogisticsOrd(logi.getIlogisticsOrd());
				addr.setSerial(Integer.valueOf(eRPSync.getYposXh()));
				List<TlogisticsOrdAddr> addrs = this.logisticsOrdAddrService.findListSelect(addr);
				if (CollectionUtils.isEmpty(addrs) == false){
					addr = addrs.get(0);
					addr.setRealOrdno(eRPSync.getWldh());
					if (StringUtils.isNotBlank(eRPSync.getFhDate()))
						addr.setRealDelivery(sdf.parse(eRPSync.getFhDate()));
					if(StringUtils.isNotBlank(eRPSync.getDhDate()))
						addr.setRealArrival(sdf.parse(eRPSync.getDhDate()));
					addr.setRealOutstockNum(eRPSync.getFhsl());
					addr.setLogisticsComName(eRPSync.getHyfs());
					addr.setRealSerial(StringUtils.trim(eRPSync.getSn()));
					try{
						this.logisticsOrdAddrService.update(addr);
					}catch(Exception e){
						logger.error("更新数据失败", e);
					}
				}
				if (StringUtils.isNotBlank(eRPSync.getWldh())){//当物流单号不为空的时候设置为发货
					if (logi.getLogisticsOrdStatus() ==  LogisticsOrdStatus.HAVE_LIBRARY){
						logi.setLogisticsOrdStatus(LogisticsOrdStatus.SHIPPED);
						this.tlogisticsService.updateTlogisticsOrd(logi);
					}
				}
			}
			
		}
		this.tlogisticsService.arrival();
		read.close();
	}
	/**
	 * 
	* @Description: 同步当天相关物流信息到产品维保表
	* @author chenwenjing    
	* @date 2014-10-15 上午11:07:54
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
	public void maintenanceSync(String genTime) throws Exception{
		List<ERPSync> eRPSyncList = this.eRPSyncService.findBySjtqDate(genTime);
		TsysParam param = this.sysParamService.getTsysParam(SysParamType.OTHER_CONF.name(),"REPLACE_PERIOD");
		if (param == null) throw new RuntimeException("包换周期(月)未配置");
		int period=Integer.parseInt(String.valueOf(param.getValue()));//默认维保日期长度，月份为单位
		if(eRPSyncList!=null){
			for(ERPSync e : eRPSyncList){
				if(StringUtils.isBlank(e.getDdh())) continue;//点单号为空不插入
				if(StringUtils.isBlank(e.getFhDate()))continue;//发货时间为空不插入
				if(StringUtils.isBlank(e.getSn()))continue;//序列号为空不插入
				List<String> snList = CtUtils.getSn(e.getSn());
				for(String sn:snList){
					ERPMaintenance oriEm = this.maintenanceManageService.getBySn(sn);
					if(oriEm!=null)continue;//已插入的序列号不再重复插入
					ERPMaintenance em = new ERPMaintenance();
					em.setFhDate(e.getFhDate());
					em.setRealOrdno(e.getWldh());
					em.setSn(sn);
					em.setPh(e.getPh());
					Tpdt tpdt = new Tpdt();
					tpdt.setPdtNo(e.getPh());
					tpdt = this.tpdtService.queryObjByIpdt(tpdt);
					em.setPm(tpdt.getName());
					em.setInnerOrdno(e.getDdh());
					TlogisticsOrd tlogisticsOrd = tlogisticsService.getByInnerOrdno(e.getDdh());
					em.setIcust(tlogisticsOrd.getIcust());
					Date tmpDate = DateUtils.parseDate(e.getFhDate(),new String[]{"yyyyMMdd"});//将发货日期由字符串转为时间格式
					em.setLifeStartTime(tmpDate);
					em.setWarrantyPeriod(DateUtils.addMonths(tmpDate, period));
					em.setRepairNum(0);
					this.maintenanceManageService.saveERPMaintenance(em);
				}
			}
		}
	}

	/**
	 * 
	 * @Description: object数组列表转为指定对象的列表
	 * @author chenwenjing
	 * @date 2014-9-6 上午9:33:02
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> getBean(List<?> list, T object) throws Exception {
		Class<?> classType = object.getClass();
		ArrayList<T> objList = new ArrayList<T>();
		Field[] fields = classType.getDeclaredFields();// 得到对象中的字段
		if (fields.length != ((Object[]) list.get(0)).length) {
			throw new RuntimeException("传入数组字段数与对象字段数不符");
		}
		for (int i = 0; i < list.size(); i++) {
			Object[] ob = (Object[]) list.get(i);
			// 每次循环时，重新实例化一个与传过来的对象类型一样的对象
			T objectCopy = (T) classType.getConstructor(new Class[] {})
					.newInstance(new Object[] {});
			for (int j = 0; j < fields.length; j++) {

				Field field = fields[j];
				String fieldName = field.getName();
				Object value = null;

				// 根据字段类型决定结果集中使用哪种get方法从数据中取到数据
				if (field.getType().equals(String.class)) {
					if (ob[j] == null) {
						value = "";
					} else {
						value = String.valueOf(ob[j]);
					}
				}
				if (field.getType().equals(Integer.class)) {
					if (ob[j] == null || "".equals(ob[j])) {
						value = null;
					} else {
						value = Integer.parseInt(String.valueOf(ob[j]));
					}
				}
				if (field.getType().equals(Long.class)) {
					if (ob[j] == null || "".equals(ob[j])) {
						value = null;
					} else {
						value = Long.parseLong(String.valueOf(ob[j]));
					}
				}
				if (field.getType().equals(BigDecimal.class)) {
					if (ob[j] == null || "".equals(ob[j])) {
						value = null;
					} else {
						value = BigDecimal.valueOf(Double.parseDouble(String
								.valueOf(ob[j])));
					}
				}
				if (field.getType().equals(java.util.Date.class)) {
					if (ob[j] == null || "".equals(ob[j])) {
						value = null;
					} else {
						value = new SimpleDateFormat("yyyyMMddHHmmss")
								.parse(String.valueOf(ob[j]));
					}
				}
				// 获得属性的首字母并转换为大写，与setXXX对应
				String firstLetter = fieldName.substring(0, 1).toUpperCase();
				String setMethodName = "set" + firstLetter
						+ fieldName.substring(1);
				Method setMethod = classType.getMethod(setMethodName,
						new Class[] { field.getType() });
				setMethod.invoke(objectCopy, new Object[] { value });// 调用对象的setXXX方法
			}
			objList.add(objectCopy);
		}
		return objList;
	}

	public static void main(String[] args) throws Exception {
		// List list = new ArrayList<>();
		// String[] a =
		// {null,null,"1212","1212","SUPPLIER","1212","1212","1212","1212","1212","2014-09-05 09:07:45","1212","1212","1212","1212","1212","1212","1212","LTD","1212","1212","1212","1212","1212"};
		// String[] c =
		// {"1212","1212","1212","1212","SUPPLIER","1212","1212","1212","1212","1212",null,"1212","1212","1212","1212","1212","1212","1212","LTD","1212","1212","1212","1212","1212"};
		// list.add(a);
		// list.add(c);
		/*
		 * List<CustInfo> b = getBean(list, new CustInfo()); for(CustInfo cu:b){
		 * System.out.println(cu.getIcust());
		 * System.out.println(cu.getLongName());
		 * System.out.println(cu.getRegDate()); }
		 */
		// FileDownload d = new FileDownload();
		// File file = d.orderInfoDown(list);
		// System.out.println(file.getName());
		// System.out.println(file);

	}
}
