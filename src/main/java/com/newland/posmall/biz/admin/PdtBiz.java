package com.newland.posmall.biz.admin;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Order;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.entity.TsysParam;
import org.ohuyo.rapid.base.entity.Tuser;
import org.ohuyo.rapid.base.service.TsysParamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.newland.posmall.base.exception.BizException;
import com.newland.posmall.base.service.TlogService;
import com.newland.posmall.base.service.TnoSegCfgService;
import com.newland.posmall.base.service.TpdtPlanDayQuotaService;
import com.newland.posmall.base.service.TpdtPlanDayService;
import com.newland.posmall.base.service.TpdtPlanMonthService;
import com.newland.posmall.base.service.TpdtService;
import com.newland.posmall.base.service.TpdtStockService;
import com.newland.posmall.bean.basebusi.TnoSegCfg;
import com.newland.posmall.bean.basebusi.Tpdt;
import com.newland.posmall.bean.basebusi.TpdtHis;
import com.newland.posmall.bean.basebusi.TpdtPlanDay;
import com.newland.posmall.bean.basebusi.TpdtPlanDayQuota;
import com.newland.posmall.bean.basebusi.TpdtPlanMonth;
import com.newland.posmall.bean.basebusi.TpdtStock;
import com.newland.posmall.bean.basebusi.condition.TpdtCondition;
import com.newland.posmall.bean.common.Tlog;
import com.newland.posmall.bean.dict.LogType;
import com.newland.posmall.bean.dict.OperType;
import com.newland.posmall.bean.dict.OrdDetailPdtType;
import com.newland.posmall.bean.dict.YesNoType;

@Service
@Transactional(rollbackFor = Throwable.class)
public class PdtBiz {
	
	private static final Logger logger = LoggerFactory.getLogger(PdtBiz.class);
	
	@Resource
	private TpdtService tpdtService;
	
	@Resource
	private TpdtStockService tpdtStockService;
	
	@Resource
	private TpdtPlanDayQuotaService tpdtPlanDayQuotaService;
	
	@Resource
	private TlogService tlogService;
	

	@Resource
	private TpdtPlanMonthService tpdtPlanMonthService;
	
	@Resource
	private TsysParamService tsysParamService;
	
	@Resource
	private TpdtPlanDayService tpdtPlanDayService; 
	
	@Resource
	private TnoSegCfgService tnoSegCfgService;
	
	@Resource(name = "posmall.cacheManager")
	private EhCacheCacheManager ecCacheManager;
	
	public void removePdtMap(){
		CacheManager cacheManager = ecCacheManager.getCacheManager();
		
		Cache cache = cacheManager.getCache("posmall.cacheMap");
		
		cache.remove("pdt_name");
	}
	
	/**
	 * 查询 产品 列表（分页）
	 * @param tpdtConfition
	 * @param page
	 * @return
	 */
	public List<Tpdt> queryAllTpdt(TpdtCondition tpdtConfition,Page page) {
		tpdtConfition.setDelFlag(Boolean.FALSE);
		tpdtConfition.addOrders(Order.desc("pdtNo"));
		return this.tpdtService.queryTpdt(tpdtConfition,page);
	}
	
	public Tpdt queryTpdtByIpdt(Long id) {
		return this.tpdtService.find(id);
	}
	
	/**
	 * 查询 产品历史价格
	 * @param ipdt
	 * @return
	 */
	public List<TpdtHis> queryTpdtHisByIpdt(Long ipdt){
		return this.tpdtService.findTpdtHis(ipdt);
	}
	
	
	public Tpdt addTpdt(Tpdt tpdt, String pre, Long start, Long end, Tuser tuser) throws BizException{
		
		if(!validatePdtNameOrNo(tpdt.getName(), tpdt.getPdtNo(), null)){
			throw new BizException(3, "产品名称和编号不能重复");
		}
		
		tpdt.setIuser(tuser.getIuser());
		tpdt.setUserName(tuser.getTuserSub().getName());
		this.tpdtService.save(tpdt);
		
		//添加 产品号段
		TnoSegCfg tnoSegCfg = this.tnoSegCfgService.get(tpdt.getInoSegCfg());
		
		removePdtMap();
		TpdtStock tpdtStock = new TpdtStock();
		tpdtStock.setIpdt(tpdt.getIpdt());
		tpdtStock.setNum(0);
		this.tpdtStockService.add(tpdtStock);
		
		//添加 操作日志
		Tlog tlog = new Tlog();
		tlog.setIfk(tuser.getIuser());
		tlog.setLogType(LogType.USER);
		tlog.setOperType(OperType.PDT_MGR);
		tlog.setMemo("新增产品");
		tlog.setPreData("");
		StringBuffer sbDate = new StringBuffer();
		sbDate.append("新增产品:产品全称是");
		sbDate.append(tpdt.getLongName());
		sbDate.append(",产品型号是");
		sbDate.append(tpdt.getPdtNo());
		sbDate.append(",产品名称是");
		sbDate.append(tpdt.getName());
		sbDate.append(",产品号段[" + tnoSegCfg.getPre() + "," + tnoSegCfg.getStart() + "-" + tnoSegCfg.getEnd() + "]");
		tlog.setData(sbDate.toString());
		this.tlogService.save(tlog);
		
		return tpdt;
	}
	
	public Tpdt modifyTpdtByIpdt(Tpdt tpdt, Long inoSegCfg, String pre, 
			Long start, Long end, Tuser tuser) throws BizException {
		
		if(!validatePdtNameOrNo(tpdt.getName(), tpdt.getPdtNo(), tpdt.getIpdt())){
			throw new BizException(3, "产品名称和编号不能重复");
		}
		
		tpdt.setIuser(tuser.getIuser());
		tpdt.setUserName(tuser.getTuserSub().getName());
		
		Tpdt tpdtNew = tpdtService.find(tpdt.getIpdt());
		TnoSegCfg segCfgDB = this.tnoSegCfgService.get(tpdt.getInoSegCfg());
		if(segCfgDB == null) {
			throw new BizException(0, "产品号段数据不存在");
		}
		//添加 操作日志
		Tlog tlog = new Tlog();
		tlog.setIfk(tuser.getIuser());
		tlog.setLogType(LogType.USER);
		tlog.setOperType(OperType.PDT_MGR);
		tlog.setMemo("修改产品");
		StringBuffer sbDate = new StringBuffer();
		sbDate.append("修改前产品:产品ID是");
		sbDate.append(tpdtNew.getIpdt());
		sbDate.append(",产品全称是");
		sbDate.append(tpdtNew.getLongName());
		sbDate.append(",产品型号是");
		sbDate.append(tpdtNew.getPdtNo());
		sbDate.append(",产品名称是");
		sbDate.append(tpdtNew.getName());
		sbDate.append(",产品价格是");
		sbDate.append(tpdtNew.getPrice());
		sbDate.append(",产品说明是");
		sbDate.append(tpdtNew.getMemo());
		sbDate.append(",产品号段是【" + segCfgDB.getPre() + "," + segCfgDB.getStart() + "-" + segCfgDB.getEnd() + "】");
		tlog.setPreData(sbDate.toString());
		
		tpdtNew.setIuser(tpdt.getIuser());
		tpdtNew.setName(tpdt.getName());
		tpdtNew.setLongName(tpdt.getLongName());
		tpdtNew.setMemo(tpdt.getMemo());
		tpdtNew.setPrice(tpdt.getPrice());
		tpdtNew.setUserName(tpdt.getUserName());
		tpdtNew.setPdtNo(tpdt.getPdtNo());
		tpdtNew.setInoSegCfg(tpdt.getInoSegCfg());
		
		this.tpdtService.update(tpdtNew);
		
		removePdtMap();
		
		StringBuffer sbDate2 = new StringBuffer();
		sbDate2.append("修改后产品:产品ID是");
		sbDate2.append(tpdtNew.getIpdt());
		sbDate2.append(",产品全称是");
		sbDate2.append(tpdtNew.getLongName());
		sbDate2.append(",产品型号是");
		sbDate2.append(tpdtNew.getPdtNo());
		sbDate2.append(",产品名称是");
		sbDate2.append(tpdtNew.getName());
		sbDate2.append(",产品价格是");
		sbDate2.append(tpdtNew.getPrice());
		sbDate2.append(",产品说明是");
		sbDate2.append(tpdtNew.getMemo());
		sbDate2.append(",产品号段是【" + pre + "," + start + "-" + end + "】");
		tlog.setData(sbDate2.toString());
		this.tlogService.save(tlog);
		
		return tpdt;
	}
	
	/**
	 * 当（超过50天无排产（排产量为零）自动跳出）
	 * 获取产品的 点单最后一天日期
	 * @param ipdt
	 * @return
	 * (与PdtPlanMonthBiz)
	 */
	public Calendar queryReleaseDate(Long ipdt){
		
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
		
		//产品日排产（获取releaseDay天的日排产）
		Calendar newCal = Calendar.getInstance(Locale.CHINA);
		newCal.add(Calendar.DATE, spaceDay);
		List<TpdtPlanDay> tmpList = null;
		int count = 0;
		int falseCount = 0;
		while (count < releaseDay) {
			TpdtPlanDay tpd = new TpdtPlanDay();
			tpd.setIpdt(ipdt);
			tpd.setYear(newCal.get(Calendar.YEAR));
			tpd.setMonth(newCal.get(Calendar.MONTH) + 1);
			tpd.setDay(newCal.get(Calendar.DATE));
			tpd.setDelFlag(Boolean.FALSE);
			tmpList = this.tpdtPlanDayService.findBySelect(tpd);
			if(tmpList == null || tmpList.size() == 0 || 
					tmpList.get(0) == null || tmpList.get(0).getNum() <= 0) { //没有排产计划，顺延一天
				newCal.add(Calendar.DATE, 1);
				falseCount++;
			}else {
				count++;
				if(count - releaseDay == 0 ){
					break;
				}
				newCal.add(Calendar.DATE, 1);
				
			}
			if(falseCount > 40){
				logger.debug("产品id:"+tpd.getIpdt()+"超过50天无排产计划");
				break;
			}
		}
		return newCal;
	
	}
	
	public Tpdt removeTpdtByIpdt(Long ipdt, Tuser tuser) throws BizException {
		
		Tpdt tpdt = this.tpdtService.find(ipdt);
		
		Calendar cal = this.queryReleaseDate(ipdt);
	    
		TpdtPlanMonth planMonth = new TpdtPlanMonth();
		
		planMonth.setIpdt(ipdt);
		planMonth.setDelFlag(Boolean.FALSE);
		
		List<TpdtPlanMonth>  tppmList = this.tpdtPlanMonthService.findBySelect(planMonth);
		
		//开放日
		if(!CollectionUtils.isEmpty(tppmList)){
			Calendar c = Calendar.getInstance(Locale.CHINA);
			for(TpdtPlanMonth t : tppmList){
				if(t.getYear() > cal.get(Calendar.YEAR)){
					throw new BizException(0, "该产品在"+cal.get(Calendar.YEAR)+"年"+(cal.get(Calendar.MONTH)+1)+"月后已有排产计划,不能删除");
				}else if((t.getYear() - cal.get(Calendar.YEAR)==0) &&  t.getMonth() >= cal.get(Calendar.MONTH)+1){
					throw new BizException(0, "该产品在"+cal.get(Calendar.YEAR)+"年"+(cal.get(Calendar.MONTH)+1)+"月后已有排产计划,不能删除");
				}else{
					//本月还有
					if(t.getYear() == c.get(Calendar.YEAR) && t.getMonth() == c.get(Calendar.MONTH)+1){
						throw new BizException(0, "该产品在"+c.get(Calendar.YEAR)+"年"+(c.get(Calendar.MONTH)+1)+"月计划排产已锁定,不能删除");
					}
				}
			}
		}
		
		
	
		
		//取实际操作者信息
		tpdt.setIuser(tuser.getIuser());
		tpdt.setUserName(tuser.getTuserSub().getName());
		this.tpdtService.delete(tpdt);
		
		removePdtMap();
		
		//添加 操作日志
		Tlog tlog = new Tlog();
		tlog.setIfk(tuser.getIuser());
		tlog.setLogType(LogType.USER);
		tlog.setOperType(OperType.PDT_MGR);
		tlog.setMemo("删除产品");
		tlog.setPreData("");
		StringBuffer sbDate = new StringBuffer();
		sbDate.append("删除产品:产品全称是");
		sbDate.append(tpdt.getLongName());
		sbDate.append(",产品型号是");
		sbDate.append(tpdt.getPdtNo());
		sbDate.append(",产品名称是");
		sbDate.append(tpdt.getName());
		tlog.setData(sbDate.toString());
		this.tlogService.save(tlog);
		
		return tpdt;
	}
	
	/**
	 * 校验 产品名称 或 编写 的唯 一性校验
	 * @param pdtName
	 * @param pdtNo
	 * @param operType
	 * @return
	 */
	public Boolean  validatePdtNameOrNo(String pdtName, String pdtNo, Long ipdt){
		Boolean result = true;
		Tpdt tpdt = new Tpdt();
		tpdt.setDelFlag(Boolean.FALSE);
		List<Tpdt> tpdtsList = null;
		if(StringUtils.isNotBlank(pdtName)){
			tpdt.setName(pdtName);
		}
		if(StringUtils.isNotBlank(pdtNo)){
			tpdt.setPdtNo(pdtNo);
		}
		if(ipdt == null){
			tpdtsList = tpdtService.findBySelect(tpdt);
			if(tpdtsList != null && tpdtsList.size() > 0){
				result = false;
			}
		}else{
			tpdtsList = tpdtService.findBySelect(tpdt);
			if(tpdtsList != null && tpdtsList.size() > 0){
				Tpdt tpdtOld = tpdtService.find(ipdt);
				if(StringUtils.isNotBlank(pdtName) && pdtName.equals(tpdtOld.getName())){
					result = true;
				}else if(StringUtils.isNotBlank(pdtNo) && pdtNo.equals(tpdtOld.getPdtNo())){
					result = true;
				}else{
					result = false;
				}
			}else{
				result = true;
			}
		}
		return result;
	}
	
	/**
	 * 根据id查询产品库存
	 * @param id
	 * @return
	 */
	public TpdtStock queryTpdtStockById(Long id) {
		if(id == null) {
			return null;
		}
		return this.tpdtStockService.findById(id);
	}
	
	/**
	 * 关联产品对应的库存量
	 * @param tpdtList
	 */
	public void setStock4Tpdt(List<Tpdt> tpdtList) {
		if(tpdtList == null || tpdtList.size() < 1) {
			return;
		}
		for (Tpdt tpdt : tpdtList) {
			TpdtStock stock = this.tpdtStockService.findById(tpdt.getIpdt());
			if(stock != null) {				
				tpdt.setStockNum(stock.getNum());
			}else {
				tpdt.setStockNum(0);
			}
		}
	}
	
	/**
	 * 修改产品库存
	 * @param tpdtId
	 * @param stockNum
	 * @param tuser
	 * @return
	 */
	public void modifyStock(Long tpdtId, Integer stockNum, Tuser tuser) throws BizException{
		if(tpdtId == null) {
			throw new BizException(0, "产品主键为空！");
		}
		if(stockNum == null) {
			throw new BizException(0, "产品库存量不能为空！");
		}
		if(tuser == null) {
			throw new BizException(0, "用户数据为空异常！");
		}
		TpdtStock tpdtStockDB = this.tpdtStockService.findById(tpdtId);
		if(tpdtStockDB == null) {
			throw new BizException(0, "不存在产品库存数据！");
		}
//		int oldNum = tpdtStockDB.getNum(); //原库存量
		TpdtStock tpdtStockCondition = new TpdtStock();
		tpdtStockCondition.setIpdt(tpdtId);
		tpdtStockCondition.setNum(stockNum);
		this.tpdtStockService.modify(tpdtStockCondition, tuser);
		
		this.modifyTpdtPlanDayQuota(tpdtId, stockNum, tuser);
	}
	
	/**
	 * 修改产品额度
	 * @param tpdtId 产品id
	 * @param stockNum 库存量
	 * @throws BizException
	 */
	public void modifyTpdtPlanDayQuota(Long tpdtId, Integer stockNum, Tuser tuser) throws BizException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar curr = Calendar.getInstance();
		int spaceDay = 0;
		TsysParam param = this.tsysParamService.getTsysParam("OTHER_CONF", "SPACE_DAY_4_ORDER"); //查询点单间隔天数，即可以点几天之后的单
		if(param != null && StringUtils.isNotBlank(param.getValue())) {
			String spaceStr = param.getValue();
			try {
				spaceDay = Integer.valueOf(spaceStr);
			} catch (Exception e) {
				logger.error("系统参数【点单间隔天数】转化异常", e);
			}
		}
		curr.add(Calendar.DATE, spaceDay);
		
		TpdtPlanDayQuota quotaCondition = new TpdtPlanDayQuota();
		quotaCondition.setIpdt(tpdtId);
		quotaCondition.setOrdDetailPdtType(OrdDetailPdtType.STOCK);
		quotaCondition.setYear(curr.get(Calendar.YEAR));
		quotaCondition.setMonth(curr.get(Calendar.MONTH) + 1);
		quotaCondition.setDay(curr.get(Calendar.DATE));
		List<TpdtPlanDayQuota> quotaList = this.tpdtPlanDayQuotaService.findBySelect(quotaCondition);
		if(quotaList == null || quotaList.size() < 1) {
			logger.error("产品ID【" + tpdtId + "】不存在" + sdf.format(curr.getTime()) + "的库存数据，自动添加一条。变化量【" + stockNum + "】");
//			throw new BizException(0, "不存在产品额度数据");
			TpdtPlanDayQuota newTpdtStock = new TpdtPlanDayQuota();
			newTpdtStock.setIpdt(tpdtId);
			newTpdtStock.setYear(curr.get(Calendar.YEAR));
			newTpdtStock.setMonth(curr.get(Calendar.MONTH) + 1);
			newTpdtStock.setDay(curr.get(Calendar.DATE));
			newTpdtStock.setWeek(curr.get(Calendar.WEEK_OF_YEAR));
			newTpdtStock.setWeekday(curr.get(Calendar.DAY_OF_WEEK) - 1);
			newTpdtStock.setNum(stockNum);
			newTpdtStock.setUsedQuota(0);
			newTpdtStock.setRemainQuota(stockNum);
			newTpdtStock.setOrdDetailPdtType(OrdDetailPdtType.STOCK);
			newTpdtStock.setCollectionFlag(YesNoType.NO);
			this.tpdtPlanDayQuotaService.save(newTpdtStock);
			
			//添加 操作日志
			Tlog tlog = new Tlog();
			tlog.setIfk(tuser.getIuser());
			tlog.setLogType(LogType.USER);
			tlog.setOperType(OperType.PDT_MGR);
			tlog.setMemo("产品ID【" + tpdtId + "】不存在" + sdf.format(curr.getTime()) + "的库存数据，自动添加一条。变化量【" + stockNum + "】");
			tlog.setPreData("");
			tlog.setData("");
			tlogService.save(tlog);
		}else {
			TpdtPlanDayQuota quotaDB = quotaList.get(0);
			int oldNum = quotaDB.getNum();
			int oldUsedQuota = quotaDB.getUsedQuota();
			int oldRemainQuota = quotaDB.getRemainQuota();
			if(stockNum < oldUsedQuota) {
				throw new BizException(0, "产品库存量不能小于已使用额度【" + oldUsedQuota + "】");
			}
			quotaDB.setNum(stockNum);
			quotaDB.setRemainQuota(stockNum - oldUsedQuota);
			this.tpdtPlanDayQuotaService.update(quotaDB);
			
			//添加 操作日志
			Tlog tlog = new Tlog();
			tlog.setIfk(tuser.getIuser());
			tlog.setLogType(LogType.USER);
			tlog.setOperType(OperType.PDT_MGR);
			tlog.setMemo("修改产品库存:"+quotaCondition.getYear()+"年"+quotaCondition.getMonth()+"月"+quotaCondition.getDay()+"日的产品ID是"+tpdtId+"的库存的变化量为"+(stockNum - oldNum));
			StringBuffer sb = new StringBuffer();
			sb.append("修改前产品库存:当前数量");
			sb.append(oldNum);
			sb.append(",剩余存量是");
			sb.append(oldRemainQuota);
			tlog.setPreData(sb.toString());
			
			StringBuffer sb2 = new StringBuffer();
			sb2.append("修改后产品库存:当前数量");
			sb2.append(quotaDB.getNum());
			sb2.append(",剩余存量是");
			sb2.append(quotaDB.getRemainQuota());
			tlog.setData(sb2.toString());
			tlogService.save(tlog);
		}
	}
	
	/**
	 * 关联产品号段
	 * @param tpdtList
	 */
	public void setTnoSegCfg4Tpdt(List<Tpdt> tpdtList) {
		if(tpdtList == null || tpdtList.size() < 1) {
			return;
		}
		for (Tpdt tpdt : tpdtList) {
//			TnoSegCfg condition = new TnoSegCfg();
//			condition.setIpdt(tpdt.getIpdt());
//			List<TnoSegCfg> segList = this.tnoSegCfgService.findTnoSegCfg();
//			if(segList != null && segList.size() >= 1) {
				tpdt.setTnoSegCfg(this.tnoSegCfgService.get(tpdt.getInoSegCfg()));
//			}
		}
	}
	
	public Map<String, TnoSegCfg> genNoSegCfgMap(){
	   List<TnoSegCfg> list =	this.tnoSegCfgService.findAll();
	   if(CollectionUtils.isEmpty(list)){
		   return null;
	   }else{
		   Map<String, TnoSegCfg> map = new TreeMap<String, TnoSegCfg>();
		   for(TnoSegCfg t : list){
			   map.put(t.getInoSegCfg().toString(), t);
		   }
		   return map;
	   }
	}
}
