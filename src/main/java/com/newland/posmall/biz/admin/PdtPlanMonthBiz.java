package com.newland.posmall.biz.admin;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.criterion.Order;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.TuserSession;
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
import com.newland.posmall.base.service.TholidayService;
import com.newland.posmall.base.service.TlogService;
import com.newland.posmall.base.service.TpdtPlanDayQuotaService;
import com.newland.posmall.base.service.TpdtPlanDayService;
import com.newland.posmall.base.service.TpdtPlanMonthService;
import com.newland.posmall.base.service.TpdtService;
import com.newland.posmall.bean.basebusi.Tpdt;
import com.newland.posmall.bean.basebusi.TpdtPlanDay;
import com.newland.posmall.bean.basebusi.TpdtPlanDayQuota;
import com.newland.posmall.bean.basebusi.TpdtPlanMonth;
import com.newland.posmall.bean.basebusi.condition.TpdtPlanMonthCondition;
import com.newland.posmall.bean.common.Tholiday;
import com.newland.posmall.bean.common.Tlog;
import com.newland.posmall.bean.dict.HolidayStatus;
import com.newland.posmall.bean.dict.LogType;
import com.newland.posmall.bean.dict.OperType;

@Service
@Transactional(rollbackFor = Throwable.class)
public class PdtPlanMonthBiz {
	
	@Resource
	private TpdtPlanMonthService tpdtPlanMonthService;
	
	@Resource
	private TpdtPlanDayService tpdtPlanDayService;
	
	@Resource
	private TlogService tlogService;
	
	@Resource
	private TpdtService tpdtService;
	
	@Resource
	private TholidayService tholidayService;

	@Resource
	private TsysParamService tsysParamService;
	
	@Resource
	private TpdtPlanDayQuotaService tpdtPlanDayQuotaService;
	
	
	private Logger logger  = LoggerFactory.getLogger(getClass());
	
	public final static String VALIDATE_ADD = "add";
	public final static String VALIDATE_MODIFY = "modify";
	
	/**
	 * 当（超过50天无排产（排产量为零）自动跳出）
	 * 间隔最后一天
	 * @param ipdt
	 * @return
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
		
		//产品日排产（获取releaseDay天的日排产）
		Calendar newCal = Calendar.getInstance(Locale.CHINA);
		newCal.add(Calendar.DATE, spaceDay-1);
		return newCal;
	
	}
	
	/**
	 * 当（超过50天无排产（排产量为零）自动跳出）  删除
	 * 获取产品的 点单最后一天日期
	 * @param ipdt
	 * @return
	 */
    public Calendar queryReleaseDateForDelete(Long ipdt){
		
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
	
	
	public TpdtPlanMonth removePdtPlanMonth(Long ipdtPlanMonth){
		TpdtPlanMonth tpdtPlanMonth = this.tpdtPlanMonthService.find(ipdtPlanMonth);
		this.tpdtPlanMonthService.delete(tpdtPlanMonth);
		TpdtPlanDay tpdtPlanDay = new TpdtPlanDay();
		tpdtPlanDay.setDelFlag(Boolean.FALSE);
		tpdtPlanDay.setIpdtPlanMonth(ipdtPlanMonth);
		List <TpdtPlanDay> tpdtPlanDayList = this.tpdtPlanDayService.findBySelect(tpdtPlanDay);
		if(!CollectionUtils.isEmpty(tpdtPlanDayList)){
			for(TpdtPlanDay tpd : tpdtPlanDayList){
				this.tpdtPlanDayService.delete(tpd);
			}
		}
		
		return tpdtPlanMonth;
	}
	
	/**
	 * 获取  日的排产量
	 * @param pdtDayNum
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 * @throws ParseException
	 */
	public Integer genDayNum(int pdtDayNum, int year , int month, int day) throws ParseException{
		Integer result = pdtDayNum;
		Tholiday  tholiday = new Tholiday();
		tholiday.setYear(year);
		tholiday.setMonth(month);
		tholiday.setDay(day);
		tholiday.setDelFlg(Boolean.FALSE);
		
		tholiday  = tholidayService.findObjectByInfo(tholiday);
		if(tholiday != null){
			if(tholiday.getHoliStatus() == HolidayStatus.IS_HOLIDAY){
				result = 0;
			}
		}else{
			Calendar cal = Calendar.getInstance();
			Date date = new SimpleDateFormat("yyyy-M-d").
					   parse(year+"-"+month+"-"+day);
			cal.setTime(date);
			
			if(cal.get(Calendar.DAY_OF_WEEK) ==1){
			    //如果是周天，就是假日	
				result = 0;
			}
		}
		
		return result;
	}
	
	
	/**
	 * 新增修改， 限制时间(当前月后的EFF_MONTH_NUM个月)
	 * @param date
	 * @return
	 */
	public Boolean validatDate(String dateStr, String validate){
		Date current = new Date();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		if( dateStr.compareTo(sdf.format(current)) < 0){
			//在当且月或 之前的月份
			return false;
		}if(dateStr.compareTo(sdf.format(current)) == 0){
			if(PdtPlanMonthBiz.VALIDATE_MODIFY.equals(validate)){
				return true;
			}else{
				return false;
			}
		}else{
			int EFF_MONTH_NUM = Integer.valueOf(this.tsysParamService.getTsysParam("OTHER_CONF", "XYYXQKD_Y").getValue());
		    Date max = DateUtils.addMonths(current, EFF_MONTH_NUM);
		    if(dateStr.compareTo(sdf.format(max)) <= 0){
		    	return true;
		    }else{
		    	return false;
		    }
		}
		
	}
	
	public int initYear(Integer year){
		if(year == null){
			if(Calendar.getInstance().get(Calendar.MONTH) == 11){
				return Calendar.getInstance().get(Calendar.YEAR)+1;
			}else{
				return Calendar.getInstance().get(Calendar.YEAR);
			}
		}else{
			return year;
		}
	}
	
	/**
	 * 
	 * @param month
	 * @return
	 */
	public int initMonth(Integer month){
		if(month == null){
			return Calendar.getInstance().get(Calendar.MONTH)+2;
		}else{
			return month;
		}
	}
	
	
	/**
	 * 初始化年度Map
	 */
	public Map<String, Integer> initSelectYearMap() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("yyyy");
		String yearStr = df.format(date);
		int yearInt = Integer.valueOf(yearStr);
		Map<String, Integer> selectYearMap = new LinkedHashMap<String, Integer>();
		selectYearMap.put(yearStr, yearInt);
		selectYearMap.put(String.valueOf(yearInt + 1), (yearInt + 1));
		selectYearMap.put(String.valueOf(yearInt + 2), (yearInt + 2));
		return selectYearMap;
	}
	
	
	/**
	 * 新增 月排产计划
	 * @param tpdtPlanMonth
	 * @param tpdtPlanDayList
	 * @throws ParseException 
	 * @throws BizException 
	 */
	public TpdtPlanMonth addPdtPlanMonth(TpdtPlanMonth tpdtPlanMonth, List<TpdtPlanDay> tpdtPlanDayList) throws ParseException, BizException{
		
		if(!valeidateUnqi(tpdtPlanMonth.getYear(), tpdtPlanMonth.getMonth(), tpdtPlanMonth.getIpdt())){
			throw new BizException(BizErrCode.M_PLAN_EXIST, tpdtPlanMonth.getYear()+"年"+tpdtPlanMonth.getMonth()+"月 该产品已有月排 产计划不能重复");
		}
		
		if(tpdtPlanDayList != null){
			tpdtPlanMonthService.save(tpdtPlanMonth);
			Date date = null;
			Calendar cal=Calendar.getInstance();
			TuserSession tuserSession = (TuserSession) AppSessionFilter.getAppSession();
			//添加 操作日志
			Tlog tlog = new Tlog();
			tlog.setIfk(tuserSession.getTuser().getIuser());
			tlog.setLogType(LogType.USER);
			tlog.setOperType(OperType.PDTPLANMONTH_MGR);
			StringBuffer sb = new StringBuffer();
			sb.append("新增 月排产计划,").append(tpdtPlanMonth.getYear()).append(",编号:").append(tpdtPlanMonth.getPdtPlanMonthNo()+",").append("年,").append(tpdtPlanMonth.getMonth()).append("月,产品id:")
			.append(tpdtPlanMonth.getIpdt()).append(",数量:").append(tpdtPlanMonth.getNum());
			tlog.setMemo(sb.toString());
			tlog.setPreData("");
			StringBuffer sb2 = new StringBuffer();
		   for(TpdtPlanDay tpdtPlanDay : tpdtPlanDayList){
			   tpdtPlanDay.setYear(tpdtPlanMonth.getYear());
			   tpdtPlanDay.setMonth(tpdtPlanMonth.getMonth());
			   tpdtPlanDay.setIpdtPlanMonth(tpdtPlanMonth.getIpdtPlanMonth());
			   tpdtPlanDay.setIpdt(tpdtPlanMonth.getIpdt());
			   date = new SimpleDateFormat("yyyy-M-d").
					   parse(tpdtPlanDay.getYear()+"-"+tpdtPlanDay.getMonth()+"-"+tpdtPlanDay.getDay());
			   cal.setTime(date);
			   tpdtPlanDay.setWeek(cal.get(Calendar.WEEK_OF_YEAR)); 
			   tpdtPlanDay.setWeekday(((cal.get(Calendar.DAY_OF_WEEK)-1)==0)?7:(cal.get(Calendar.DAY_OF_WEEK)-1));
			   tpdtPlanDayService.save(tpdtPlanDay);
			   sb2.append(tpdtPlanDay.getDay()).append("日,数量:").append(tpdtPlanDay.getNum()).append(";");
		   }
		   tlog.setData(sb2.toString());
		   tlogService.save(tlog);
			
		}else{
			logger.info(tpdtPlanMonth.getYear()+"年"+tpdtPlanMonth.getMonth()+"月"+"");
			throw new BizException(BizErrCode.D_PLAN_LACK, tpdtPlanMonth.getYear()+"年"+tpdtPlanMonth.getMonth()+"月 该产品的月排产不能没有日排产计划");
		
		}
		
		return tpdtPlanMonth;
	}
	
	
	
	/**
	 * 修改 月排产计划
	 * @param tpdtPlanMonth
	 * @param tpdtPlanDayList
	 * @throws ParseException
	 * @throws BizException 
	 */
	public TpdtPlanMonth ModifyPdtPlanMonth(TpdtPlanMonth tpdtPlanMonth, List<TpdtPlanDay> tpdtPlanDayList, TpdtPlanDay tpd, Boolean flag, int modifyDay) throws ParseException, BizException{
		
		String dateStr = null;
		Tpdt tpdt = this.tpdtService.find(tpdtPlanMonth.getIpdt());
		if(tpdt.getDelFlag() == Boolean.TRUE){
			throw new BizException(0, "产品已删除不能修改排产计划");
		}
		
		if(tpdtPlanMonth.getMonth() > 9){
			dateStr = tpdtPlanMonth.getYear()+"-"+tpdtPlanMonth.getMonth();
		}else{
			dateStr = tpdtPlanMonth.getYear()+"-0"+tpdtPlanMonth.getMonth();
		}
		if(!validatDate(dateStr, PdtPlanMonthBiz.VALIDATE_MODIFY)){
			throw new BizException(BizErrCode.PDTPLAN_DATE_MONTH, "只能新增修改当前月及之后"+this.tsysParamService.getTsysParam("OTHER_CONF", "XYYXQKD_Y").getValue()+"个月的计划");
		}
		
		tpdtPlanMonthService.update(tpdtPlanMonth);
		TuserSession tuserSession = (TuserSession) AppSessionFilter.getAppSession();
		//添加 操作日志
		Tlog tlog = new Tlog();
		tlog.setIfk(tuserSession.getTuser().getIuser());
		tlog.setLogType(LogType.USER);
		tlog.setOperType(OperType.PDTPLANMONTH_MGR);
		StringBuffer sb = new StringBuffer();
		sb.append("修改后 月排产计划id:").append(tpdtPlanMonth.getIpdtPlanMonth()).append(",月排产编号:").append(tpdtPlanMonth.getPdtPlanMonthNo())
		.append(",").append(tpdtPlanMonth.getYear()).append("年,").append(tpdtPlanMonth.getMonth()).append("月").append(",产品id:").append(tpdtPlanMonth.getIpdt());
		tlog.setMemo(sb.toString());
		
		if(tpdtPlanDayList != null && tpdtPlanDayList.size() > 0){
			TpdtPlanDay tpdtPlanDayNew = null;
			TpdtPlanDayQuota tpdtPlanDayQuota = null;
		   if(tpdtPlanDayList.get(0).getIpdtPlanDay() != null){
			   //如果 日排产有ID，只改变日排产的产量
			   StringBuffer sb3 = new StringBuffer();
			   StringBuffer sb4 = new StringBuffer("修改后日排产:");
			   Long ipdtLong;
			   for(TpdtPlanDay tpdtPlanDay : tpdtPlanDayList){
				   tpdtPlanDayNew = tpdtPlanDayService.find(tpdtPlanDay.getIpdtPlanDay());
				   sb3.append("<").append(tpdtPlanDayNew.getDay()).append("日,产品ID:").append(tpdtPlanDayNew.getIpdt())
				   .append(",数量:").append(tpdtPlanDay.getNum()).append(">");
				   ipdtLong =  tpdtPlanDayNew.getIpdt();
				   tpdtPlanDayQuota = this.tpdtPlanDayQuotaService.findQuotaByPdtPlanDay(tpdtPlanDayNew);  
				   if(flag == true ){
					   if(tpdtPlanDayNew.getDay() > modifyDay ){
						   if(tpdtPlanDayQuota.getUsedQuota() > tpdtPlanDay.getNum() ){
							   throw  new BizException(BizErrCode.PDTPLAN_QUOTA_USED, "修改日排产不能小于已使用额度！");
						   }

						   tpdtPlanDayNew.setNum(tpdtPlanDay.getNum());
						   tpdtPlanDayNew.setIpdt(tpdtPlanMonth.getIpdt());
						   tpdtPlanDayService.update(tpdtPlanDayNew,ipdtLong);
						   sb4.append("<").append(tpdtPlanDayNew.getDay()).append("日,产品ID:").append(tpdtPlanDayNew.getIpdt())
						   .append(",数量:").append(tpdtPlanDay.getNum()).append(">");
					   }
				   }else{
					   if(tpdtPlanDayQuota.getUsedQuota() > tpdtPlanDay.getNum() ){
						   throw  new BizException(BizErrCode.PDTPLAN_QUOTA_USED, "修改日排产不能小于已使用额度！");
					   }

					   tpdtPlanDayNew.setNum(tpdtPlanDay.getNum());
					   tpdtPlanDayNew.setIpdt(tpdtPlanMonth.getIpdt());
					   tpdtPlanDayService.update(tpdtPlanDayNew,ipdtLong);
					   sb4.append("<").append(tpdtPlanDayNew.getDay()).append("日,产品ID:").append(tpdtPlanDayNew.getIpdt())
					   .append(",数量:").append(tpdtPlanDay.getNum()).append(">");
				   }
				   
			   }
			   tlog.setPreData(sb3.toString());
			   tlog.setData(sb4.toString());
		   }else{
			  //否则重新生成的日排产计划
			  //先删除旧的日排产
			   if(tpd != null){
				   StringBuffer sb2 = new StringBuffer();
				   List<TpdtPlanDay> list = tpdtPlanDayService.findBySelect(tpd);
				   sb2.append("先删除旧日排产计划:");
				   for(TpdtPlanDay t : list){
					   sb2.append("<").append(t.getYear()).append("年").append(t.getMonth()).append("月").append(t.getDay()).append("日,产品ID:").append(t.getIpdt())
					   .append(",数量:").append(t.getNum()).append(">");
					   tpdtPlanDayService.delete(t);
				   }
				   tlog.setPreData(sb2.toString());
			   }
			   //添加加新排产
			   Date date = null;
			   Calendar cal=Calendar.getInstance();
			   StringBuffer sb5 = new StringBuffer("新增   日排产:");
			   for(TpdtPlanDay tpdtPlanDay : tpdtPlanDayList){
				   tpdtPlanDay.setYear(tpdtPlanMonth.getYear());
				   tpdtPlanDay.setMonth(tpdtPlanMonth.getMonth());
				   tpdtPlanDay.setIpdtPlanMonth(tpdtPlanMonth.getIpdtPlanMonth());
				   tpdtPlanDay.setIpdt(tpdtPlanMonth.getIpdt());
				   date = new SimpleDateFormat("yyyy-M-d").parse(tpdtPlanDay.getYear()+"-"+tpdtPlanDay.getMonth()+"-"+tpdtPlanDay.getDay());
				   cal.setTime(date);
				   tpdtPlanDay.setWeek(cal.get(Calendar.WEEK_OF_YEAR)); 
				   tpdtPlanDay.setWeekday(((cal.get(Calendar.DAY_OF_WEEK)-1)==0)?7:(cal.get(Calendar.DAY_OF_WEEK)-1));
				   tpdtPlanDayService.save(tpdtPlanDay);
				   sb5.append("<").append(tpdtPlanDay.getDay()).append("日,产品ID:").append(tpdtPlanDay.getIpdt())
				   .append(",数量:").append(tpdtPlanDay.getNum()).append(">");
			   }
			   tlog.setData(sb5.toString());
		   }
		   tlogService.save(tlog);
		}else{
			logger.info(tpdtPlanMonth.getYear()+"年"+tpdtPlanMonth.getMonth()+"月"+"无日排产计划");
		}
		
		return tpdtPlanMonth;
	}
	
	/**
	 * 查询 月排产下的日排产列表
	 * @param id
	 * @return
	 */
	public List<TpdtPlanDay> queryTpdtPlanDayByIpdtPlanMonth(Long id){
		TpdtPlanDay tpdtPlanDay = new TpdtPlanDay();
		tpdtPlanDay.setIpdtPlanMonth(id);
		
		return tpdtPlanDayService.findBySelect(tpdtPlanDay);
		
	}
	
	/**
	 * 查询 月排产下的日排产列表
	 * @param id
	 * @return
	 */
	public List<TpdtPlanDay> queryTpdtPlanDayAndQuotaByIpdtPlanMonth(Long id){
		TpdtPlanDay tpdtPlanDay = new TpdtPlanDay();
		tpdtPlanDay.setIpdtPlanMonth(id);
		
		List<TpdtPlanDay> list = tpdtPlanDayService.findBySelect(tpdtPlanDay);
		if(CollectionUtils.isEmpty(list)){
			return null;
		}else{
			for(TpdtPlanDay  t : list){
				t.setTpdtPlanDayQuota(tpdtPlanDayQuotaService.findQuotaByPdtPlanDay(t));
			}
			return list;
		}
		
	}
	
	
	/**
	 * 查询 列表（分页）
	 * @param tpdtPlanMonthConfition
	 * @param page
	 * @return
	 */
	public List<TpdtPlanMonth> queryAllTpdtPlanMonth(TpdtPlanMonthCondition tpdtPlanMonthConfition,Page page) {
		tpdtPlanMonthConfition.addOrders(Order.desc("year"));
		tpdtPlanMonthConfition.addOrders(Order.desc("month"));
		tpdtPlanMonthConfition.addOrders(Order.desc("ipdt"));
		List<TpdtPlanMonth> list = this.tpdtPlanMonthService.queryTpdtPlanMonth(tpdtPlanMonthConfition,page);
		Calendar cal = null;
		if(!CollectionUtils.isEmpty(list)){
			for(TpdtPlanMonth tpdtPlanMonth : list){
				cal = this.queryReleaseDateForDelete(tpdtPlanMonth.getIpdt());
			    if(tpdtPlanMonth.getYear() > cal.get(Calendar.YEAR) ){
			    	tpdtPlanMonth.setCanDelete(Boolean.TRUE);
			    }else if((cal.get(Calendar.YEAR) - tpdtPlanMonth.getYear() == 0) &&  (tpdtPlanMonth.getMonth() > cal.get(Calendar.MONTH)+1 )){
			    	tpdtPlanMonth.setCanDelete(Boolean.TRUE);
			    }else{
			    	tpdtPlanMonth.setCanDelete(Boolean.FALSE);
			    }
			}
			return list;
		}else{
			return null;
		}
	
	}
	
	public TpdtPlanMonth queryTpdtPlanMonthByIpdtPlanMonth(Long id) {
		return this.tpdtPlanMonthService.find(id);
	}
	
	public TpdtPlanMonth queryTpdtPlanMonth(TpdtPlanMonth tpdtPlanMonth){
		List<TpdtPlanMonth> list = this.tpdtPlanMonthService.findBySelect(tpdtPlanMonth);
		return (CollectionUtils.isEmpty(list)?null:list.get(0));
	}
	
	
	/**
	 * 校验  产品的  月排产计划 唯一性
	 * @param pdtName
	 * @param pdtNo
	 * @param operType
	 * @return
	 * @throws BizException 
	 */
	public Boolean  valeidateUnqi(Integer year, Integer month, Long ipdt) throws BizException{
		Boolean result = true;
		TpdtPlanMonth tpdtPlanMonth = new TpdtPlanMonth();
		List<TpdtPlanMonth> tpdtPlanMonthList = null;
		if(year != null){
			tpdtPlanMonth.setYear(year);
		}else{
			throw new BizException(BizErrCode.M_YEAR_NEED, "月排产请输入年份");
		}
		if(month != null){
			tpdtPlanMonth.setMonth(month);
		}else{
			throw new BizException(BizErrCode.M_MONTH_NEED, "月排产请输入月份");
		}
		if(ipdt != null){
			tpdtPlanMonth.setIpdt(ipdt);
		}else{
			throw new BizException(BizErrCode.M_PRODUCT_NEED, "月排产请选择产品");
		}
		tpdtPlanMonth.setDelFlag(Boolean.FALSE);
		tpdtPlanMonthList = tpdtPlanMonthService.findBySelect(tpdtPlanMonth);
		if(tpdtPlanMonthList != null && tpdtPlanMonthList.size() > 0){
			result = false;
		}
		
		String dateStr = null;
		if(tpdtPlanMonth.getMonth() > 9){
			dateStr = tpdtPlanMonth.getYear()+"-"+tpdtPlanMonth.getMonth();
		}else{
			dateStr = tpdtPlanMonth.getYear()+"-0"+tpdtPlanMonth.getMonth();
		}
		if(!validatDate(dateStr, PdtPlanMonthBiz.VALIDATE_ADD)){
			throw new BizException(BizErrCode.PDTPLAN_DATE_MONTH, "只能新增当前月之后"+this.tsysParamService.getTsysParam("OTHER_CONF", "XYYXQKD_Y").getValue()+"个月的计划");
		}
		
		
		return result;
	}
	
	
	
}
