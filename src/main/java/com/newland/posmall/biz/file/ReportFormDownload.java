package com.newland.posmall.biz.file;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.ohuyo.rapid.file.CsvFileTranslator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newland.posmall.Application;
import com.newland.posmall.base.service.ReportFormService;
import com.newland.posmall.base.service.TagmtService;
import com.newland.posmall.base.service.TcustRegService;
import com.newland.posmall.base.service.TcustService;
import com.newland.posmall.base.service.TlogisticsService;
import com.newland.posmall.base.service.TordDetailService;
import com.newland.posmall.base.service.TordService;
import com.newland.posmall.bean.basebusi.Tagmt;
import com.newland.posmall.bean.basebusi.TagmtDetail;
import com.newland.posmall.bean.basebusi.TlogisticsOrd;
import com.newland.posmall.bean.basebusi.Tord;
import com.newland.posmall.bean.basebusi.TordDetail;
import com.newland.posmall.bean.basebusi.condition.TagmtCondition;
import com.newland.posmall.bean.basebusi.condition.TordCondition;
import com.newland.posmall.bean.customer.Tcust;
import com.newland.posmall.bean.customer.TcustReg;
import com.newland.posmall.bean.customer.condition.TlogisticsOrdCondition;
import com.newland.posmall.bean.dict.AgmtStatus;
import com.newland.posmall.bean.dict.LogisticsOrdStatus;
import com.newland.posmall.bean.dict.OrdStatus;
import com.newland.posmall.biz.admin.OrderBiz;
import com.newland.posmall.biz.common.MapBiz;
import com.newland.posmall.biz.cust.LogisticsOrderWithMultipleAddrs4PageBiz;
import com.newland.posmall.biz.file.bean.AgmtReportInfo;
import com.newland.posmall.biz.file.bean.LogisticsOrdReportInfo;
import com.newland.posmall.biz.file.bean.OrdReportInfo;
import com.newland.posmall.controller.cust.model.LogisticsOrdAddr4Page;
import com.newland.posmall.controller.cust.model.LogisticsOrderWithMultipleAddrs4Page;


/**
 * 
* @ClassName: ReportFormDownload 
* @Description: 报表下载  
* @author chenwenjing
* @date 2014-11-19 下午5:15:42
 */
@Service
@Transactional
public class ReportFormDownload {

	@Resource
	private ReportFormService reportFormService;
	
	@Resource
	private TagmtService tagmtService;
	
	@Resource
	private TcustRegService tcustRegService;
	
	@Resource
	private TordService tordService;
	
	@Resource
	private TordDetailService tordDetailService;
	
	@Resource
	private TlogisticsService tlogisticsService;
	
	@Resource
	private OrderBiz orderBiz;
	
	@Resource
	private MapBiz mapBiz;
	
	@Resource
	private TcustService tcustService;
	
	@Resource
	private LogisticsOrderWithMultipleAddrs4PageBiz logisticsOrderWithMultipleAddrs4PageBiz;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	public File agmtReport(Date startTime,Date endTime) throws Exception{
		
		List<AgmtReportInfo> ariList = new ArrayList<AgmtReportInfo>();
		List<TcustReg> custRegList = this.tcustRegService.find();
		Integer totalAgmtNum = 0;
		Integer totalOrdNum = 0;
		Integer totalLogisticsNum = 0;
		for(TcustReg tcustReg:custRegList){
			if(tcustReg.getIcust()==10002||tcustReg.getIcust()==11)continue;
			Integer agmtNum = 0;
			Integer ordNum = 0;
			Integer logisticsNum = 0;
			
			TagmtCondition tagmtCondition = new TagmtCondition();
			tagmtCondition.setAgmtStatus(AgmtStatus.CONFIRM);
			tagmtCondition.setStartEfficientTime(startTime);
			tagmtCondition.setEndEfficientTime(endTime);
			tagmtCondition.setIcust(tcustReg.getIcust());
			List<Tagmt> agmtList = tagmtService.findTagmtListByCondition(tagmtCondition);
			Set<Long> iagmts = new HashSet<Long>();
			
			for(Tagmt agmt:agmtList){
				List<TagmtDetail> agmtDetailList = this.tagmtService.findTagmtDetial(agmt);
				for(TagmtDetail agmtDetail:agmtDetailList){
					agmtNum += agmtDetail.getNum();
				}
				iagmts.add(agmt.getIagmt());
			}
			if(agmtList!=null&&agmtList.size()>0){
				TordCondition tordCondition = new TordCondition();
				tordCondition.setOrdStatus(OrdStatus.HAVE_AUDIT);
				tordCondition.setIagmtList(iagmts);
				List<Tord> ordList = this.tordService.findListdByCon(tordCondition);
				Set<Long> iords = new HashSet<Long>();
				for(Tord ord:ordList){
					TordDetail ordDetail = new TordDetail();
					ordDetail.setIord(ord.getIord());
					List<TordDetail> ordDetailList = this.tordDetailService.findSelect(ordDetail);
					for(TordDetail orderDetail:ordDetailList){
						ordNum += orderDetail.getNum();
					}
					iords.add(ord.getIord());
				}
				if(ordList!=null&&ordList.size()>0){
					TlogisticsOrdCondition tlogisticsOrdCondition = new TlogisticsOrdCondition();
					tlogisticsOrdCondition.setIordList(iords);
					List<LogisticsOrdStatus> logisticsOrdStatusList = new ArrayList<LogisticsOrdStatus>();
					logisticsOrdStatusList.add(LogisticsOrdStatus.HAVE_LIBRARY);
					logisticsOrdStatusList.add(LogisticsOrdStatus.SHIPPED);
					logisticsOrdStatusList.add(LogisticsOrdStatus.PARTIAL_DELIVERY);
					logisticsOrdStatusList.add(LogisticsOrdStatus.ALL_SERVICE);
					tlogisticsOrdCondition.setLogisticsOrdStatusList(logisticsOrdStatusList);
					List<TlogisticsOrd> tlogisticsOrdList = this.tlogisticsService.queryByCondition(tlogisticsOrdCondition);
					for(TlogisticsOrd logisticsOrd:tlogisticsOrdList){
						logisticsNum += logisticsOrd.getNum();
					}
				}
			}
			
			AgmtReportInfo ari = new AgmtReportInfo();
			ari.setCustName(tcustReg.getName());
			ari.setAgmtNum(agmtNum);
			ari.setOrdNum(ordNum);
			ari.setLogisticsNum(logisticsNum);
			ari.setDeviation(ordNum-logisticsNum);
			ari.setMemo("");
			ariList.add(ari);
			totalAgmtNum += agmtNum;
			totalOrdNum += ordNum;
			totalLogisticsNum += logisticsNum;
			
		}
		AgmtReportInfo ari = new AgmtReportInfo();
		ari.setCustName("合计：");
		ari.setAgmtNum(totalAgmtNum);
		ari.setOrdNum(totalOrdNum);
		ari.setLogisticsNum(totalLogisticsNum);
		ari.setDeviation(totalOrdNum-totalLogisticsNum);
		ari.setMemo("");
		ariList.add(ari);
		
		CsvFileTranslator trans = new CsvFileTranslator(",");
		Date now = new Date();
		String genTime = new SimpleDateFormat("yyyyMMddHHmmss").format(now);
		String fileName = genTime + ".csv";
		File file = new File(Application.getTemplatePath() + File.separator+"AREPI" + fileName);
		return trans.fromObject(ariList, file, "gbk");
	}
	public File ordReport(Date startTime,Date endTime) throws Exception{
		List<OrdReportInfo> oriList = new ArrayList<OrdReportInfo>();
		Map<String, String> ordStatusMap = mapBiz.getMapByType("ord_status");
		Map<String, String> payStatusMap = mapBiz.getMapByType("pay_status");
		TordCondition tordCondition = new TordCondition();
		tordCondition.setBeginTime(endTime);
		tordCondition.setEndTime(endTime);
		tordCondition.setDelFlag(Boolean.FALSE);
		List<Tord> ordList = this.tordService.findListdByCon(tordCondition);
		if(ordList!=null&&ordList.size()>0){
			for(Tord tord:ordList){
				TcustReg tcustReg = tcustRegService.findByIcust(tord.getIcust());
				Tagmt tagmt = tagmtService.findById(tord.getIagmt());
				List<TordDetail> tordDetailList = orderBiz.queryTordDetailList(tord.getIord());
				for(TordDetail ordDetail:tordDetailList){
					OrdReportInfo ordReportInfo = new OrdReportInfo();
					ordReportInfo.setGenTime(tord.getGenTime());
					ordReportInfo.setOrdNo(tord.getOrdNo());
					ordReportInfo.setAgmtNo(tagmt.getAgmtNo());
					ordReportInfo.setCustName(tcustReg.getName());
					ordReportInfo.setPdtName(ordDetail.getPdtName());
					ordReportInfo.setOrdNum(ordDetail.getNum());
					ordReportInfo.setUsedQuota(ordDetail.getUsedQuota());
					ordReportInfo.setRemainQuota(ordDetail.getRemainQuota());
					ordReportInfo.setRemark(tord.getRemark());
					ordReportInfo.setMemo("");
					ordReportInfo.setOrdStatus(ordStatusMap.get(String.valueOf(tord.getOrdStatus())));
					ordReportInfo.setPayStatus(payStatusMap.get(String.valueOf(tord.getPayStatus())));
					ordReportInfo.setAmt(ordDetail.getAmt());
					oriList.add(ordReportInfo);
				}
			}
			
		}
		
		CsvFileTranslator trans = new CsvFileTranslator(",");
		Date now = new Date();
		String genTime = new SimpleDateFormat("yyyyMMddHHmmss").format(now);
		String fileName = genTime + ".csv";
		File file = new File(Application.getTemplatePath() + File.separator+"ORDREPI" + fileName);
		return trans.fromObject(oriList, file, "gbk");
		
	}
	public File logisticsOrdReport(Date startTime,Date endTime) throws Exception{
		List<LogisticsOrdReportInfo> loriList = new ArrayList<LogisticsOrdReportInfo>();
		Map<String, String> logisticsOrdStatusMap = mapBiz.getMapByType("logistics_ord_status");
		Map<String, String> payStatusMap = mapBiz.getMapByType("pay_status");
		TlogisticsOrdCondition tlogisticsOrdCondition = new TlogisticsOrdCondition();
		tlogisticsOrdCondition.setBeginTime(startTime);
		tlogisticsOrdCondition.setEndTime(endTime);
		tlogisticsOrdCondition.setTempFlag(Boolean.FALSE);
		List<TlogisticsOrd> tlogisticsOrdList = tlogisticsService.queryByCondition(tlogisticsOrdCondition);
		if(tlogisticsOrdList!=null&&tlogisticsOrdList.size()>0){
			for(TlogisticsOrd tlogisticsOrd:tlogisticsOrdList){
				TcustReg tcustReg = tcustRegService.findByIcust(tlogisticsOrd.getIcust());
				Tcust tcust = tcustService.find(tlogisticsOrd.getIcust());
				LogisticsOrderWithMultipleAddrs4Page p = this.logisticsOrderWithMultipleAddrs4PageBiz.getLogisticsOrderWithMultipleAddrs4Page(tlogisticsOrd.getIlogisticsOrd(), tcust);
				Tagmt tagmt = tagmtService.findById(p.getTord().getIagmt());
				for(LogisticsOrdAddr4Page addr:p.getAddrs()){
					LogisticsOrdReportInfo lori = new LogisticsOrdReportInfo();
					lori.setGenTime(tlogisticsOrd.getGenTime());
					lori.setInnerOrdno(tlogisticsOrd.getInnerOrdno());
					lori.setOrdNo(p.getTord().getOrdNo());
					lori.setAgmtNo(tagmt.getAgmtNo());
					lori.setCustName(tcustReg.getName());
					lori.setPdtName(addr.getPdtName());
					lori.setLogisticsNum(addr.getTlogisticsOrdAddr().getNum());
					lori.setRealSerial(addr.getTlogisticsOrdAddr().getRealSerial());
					lori.setLogisticsComName(addr.getTlogisticsOrdAddr().getLogisticsComName());
					lori.setRealOrdno(addr.getTlogisticsOrdAddr().getRealOrdno());
					lori.setMemo("");
					lori.setLogisticsOrdStatus(logisticsOrdStatusMap.get(String.valueOf(tlogisticsOrd.getLogisticsOrdStatus())));
					lori.setPayStatus(payStatusMap.get(String.valueOf(tlogisticsOrd.getPayStatus())));
					lori.setConsigneeName(addr.getTlogisticsOrdAddr().getConsigneeName());
					lori.setConsigneeMobile(addr.getTlogisticsOrdAddr().getConsigneeMobile());
					lori.setConsigneeAddr(addr.getTlogisticsOrdAddr().getConsigneeAddr());
					lori.setRealDelivery(addr.getTlogisticsOrdAddr().getRealDelivery());
					lori.setRealArrival(addr.getTlogisticsOrdAddr().getRealArrival());
					loriList.add(lori);
				}
			}
		}
		
		CsvFileTranslator trans = new CsvFileTranslator(",");
		Date now = new Date();
		String genTime = new SimpleDateFormat("yyyyMMddHHmmss").format(now);
		String fileName = genTime + ".csv";
		File file = new File(Application.getTemplatePath() + File.separator+"LORIREPI" + fileName);
		return trans.fromObject(loriList, file, "gbk");
		
	}
}
