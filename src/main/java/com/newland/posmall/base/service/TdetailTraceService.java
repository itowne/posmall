package com.newland.posmall.base.service;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.User;
import org.ohuyo.rapid.base.entity.Tsys;
import org.ohuyo.rapid.base.entity.Tuser;
import org.ohuyo.rapid.base.entity.condition.TdetailTraceCondition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newland.posmall.base.dao.TdetailTraceDao;
import com.newland.posmall.bean.basebusi.Tagmt;
import com.newland.posmall.bean.basebusi.TlogisticsOrd;
import com.newland.posmall.bean.basebusi.Tord;
import com.newland.posmall.bean.common.TdetailTrace;
import com.newland.posmall.bean.customer.Tcust;
import com.newland.posmall.bean.dict.LogType;


@Service
@Transactional(rollbackFor = Throwable.class)
public class TdetailTraceService {
	
	@Resource
	private TdetailTraceDao tdetailTraceDao;
	
	@Resource
	private TagmtService tagmtService;
	
	@Resource
	private TordService tordService;
	
	
	public void save(TdetailTrace tdetailTrace) {
		tdetailTrace.setGenTime(new Date());
		this.tdetailTraceDao.save(tdetailTrace);
	}
	
	public TdetailTrace findById(Long id) {
		return this.tdetailTraceDao.get(id);
	}
	
	public List<TdetailTrace> queryByCondition(TdetailTraceCondition condition, Page page) {
		return this.tdetailTraceDao.findByCondition(condition, page);
	}
	
	public TdetailTrace addTdetailTrace(Tagmt tagmt, Tord tord, TlogisticsOrd tlogisticsOrd, Tcust tcust) {
		return null;
	}
	
	/**
	 * 添加 协议操作
	 * @param tagmt
	 * @param user
	 * @param memo
	 * @return
	 */
	public TdetailTrace add4Tagmt(Tagmt tagmt, User user , String memo ,BigDecimal amt, Integer num) {
		TdetailTrace newTdetailTrace = new TdetailTrace();
		newTdetailTrace.setAgmtNo(tagmt.getAgmtNo());
		newTdetailTrace.setIagmt(tagmt.getIagmt());
		newTdetailTrace.setIcust(tagmt.getIcust());
		newTdetailTrace.setMemo(memo);
		newTdetailTrace.setAmt(amt);
		newTdetailTrace.setNum(num);
		if(user instanceof Tcust){
			Tcust tcust  =  (Tcust)user;
			newTdetailTrace.setLogType(LogType.CUST);
			newTdetailTrace.setIoperator(tcust.getIcust());
		}else if(user instanceof Tuser){
			Tuser tuser  =  (Tuser) user;
			newTdetailTrace.setLogType(LogType.USER);
			newTdetailTrace.setIoperator(tuser.getIuser());
		}else if(user instanceof Tsys){
			Tsys tsys  =  (Tsys) user;
			newTdetailTrace.setLogType(LogType.SYS);
			newTdetailTrace.setIoperator(tsys.getIsys());
		}
		this.save(newTdetailTrace);
		return newTdetailTrace;
	}
	
	/**
	 * 订单操作
	 * @param tord
	 * @param tcust
	 * @return
	 */
	public TdetailTrace add4Tord(Tord tord, User user, String memo,BigDecimal amt, Integer num) {
		TdetailTrace newTdetailTrace = new TdetailTrace();
		Tagmt tagmt = this.tagmtService.findById(tord.getIagmt());
		newTdetailTrace.setAgmtNo(tagmt.getAgmtNo());
		newTdetailTrace.setIagmt(tagmt.getIagmt());
		newTdetailTrace.setIcust(tagmt.getIcust());
		newTdetailTrace.setIord(tord.getIord());
		newTdetailTrace.setOrdNo(tord.getOrdNo());
		newTdetailTrace.setMemo(memo);
		newTdetailTrace.setAmt(amt);
		newTdetailTrace.setNum(num);
		if(user instanceof Tcust){
			Tcust tcust  =  (Tcust)user;
			newTdetailTrace.setLogType(LogType.CUST);
			newTdetailTrace.setIoperator(tcust.getIcust());
		}else if(user instanceof Tuser){
			Tuser tuser  =  (Tuser) user;
			newTdetailTrace.setLogType(LogType.USER);
			newTdetailTrace.setIoperator(tuser.getIuser());
		}else if(user instanceof Tsys){
			Tsys tsys  =  (Tsys) user;
			newTdetailTrace.setLogType(LogType.SYS);
			newTdetailTrace.setIoperator(tsys.getIsys());
		}
		this.save(newTdetailTrace);
		return newTdetailTrace;
	}
	
	/**
	 * 物流单操作
	 * @param tlogisticsOrd
	 * @param user
	 * @param memo
	 * @param amt
	 * @param num
	 * @return
	 */
	public TdetailTrace add4TlogisticsOrd(TlogisticsOrd tlogisticsOrd, User user, String memo,BigDecimal amt, Integer num) {
		TdetailTrace newTdetailTrace = new TdetailTrace();
		Tord tord = this.tordService.find(tlogisticsOrd.getIord());
		Tagmt tagmt = this.tagmtService.findById(tord.getIagmt());
		newTdetailTrace.setAgmtNo(tagmt.getAgmtNo());
		newTdetailTrace.setIagmt(tagmt.getIagmt());
		newTdetailTrace.setIcust(tagmt.getIcust());
		newTdetailTrace.setIord(tord.getIord());
		newTdetailTrace.setOrdNo(tord.getOrdNo());
		newTdetailTrace.setIlogisticsOrd(tlogisticsOrd.getIlogisticsOrd());
		newTdetailTrace.setLogisticsOrdNo(tlogisticsOrd.getInnerOrdno());
		newTdetailTrace.setMemo(memo);
		newTdetailTrace.setAmt(amt);
		newTdetailTrace.setNum(num);
		if(user instanceof Tcust){
			Tcust tcust  =  (Tcust)user;
			newTdetailTrace.setLogType(LogType.CUST);
			newTdetailTrace.setIoperator(tcust.getIcust());
		}else if(user instanceof Tuser){
			Tuser tuser  =  (Tuser) user;
			newTdetailTrace.setLogType(LogType.USER);
			newTdetailTrace.setIoperator(tuser.getIuser());
		}else if(user instanceof Tsys){
			Tsys tsys  =  (Tsys) user;
			newTdetailTrace.setLogType(LogType.SYS);
			newTdetailTrace.setIoperator(tsys.getIsys());
		}
		this.save(newTdetailTrace);
		return newTdetailTrace;
	}
	
}
