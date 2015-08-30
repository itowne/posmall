package com.newland.posmall.base.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.newland.posmall.base.dao.TlogisticsDetailDao;
import com.newland.posmall.base.dao.TlogisticsOrdDao;
import com.newland.posmall.base.dao.TlogisticsTrackingDao;
import com.newland.posmall.bean.basebusi.TlogisticsDetail;
import com.newland.posmall.bean.basebusi.TlogisticsOrd;
import com.newland.posmall.bean.basebusi.TlogisticsOrdAddr;
import com.newland.posmall.bean.customer.condition.TlogisticsOrdCondition;
import com.newland.posmall.bean.dict.LogisticsOrdStatus;
import com.newland.posmall.identifier.IdentifierService;

@Service
public class TlogisticsService {

	@Resource
	private TlogisticsDetailDao tlogisticsDetailDao;

	@Resource
	private TlogisticsOrdDao tlogisticsOrdDao;

	@Resource
	private TlogisticsTrackingDao tlogisticsTrackingDao;

	@Resource(name = "identifierService")
	private IdentifierService identifierService;
	
	@Autowired
	TlogisticsOrdAddrService logisticsOrdAddrService;

	public void addTlogisticsOrd(TlogisticsOrd tlogisticsOrd) {
		Date date = new Date();
		tlogisticsOrd.setInnerOrdno(identifierService.genInterFlowId());
		tlogisticsOrd.setGenTime(date);
		tlogisticsOrd.setUpdTime(date);
		this.tlogisticsOrdDao.save(tlogisticsOrd);
	}

	public void addTlogisticsDetail(TlogisticsDetail tlogisticsDetail) {
		Date date = new Date();
		tlogisticsDetail.setGenTime(date);
		tlogisticsDetail.setUpdTime(date);
		this.tlogisticsDetailDao.save(tlogisticsDetail);
	}

	public List<TlogisticsOrd> queryPageByCondition(Page page,
			TlogisticsOrdCondition condition) {
		return tlogisticsOrdDao.findByCondition(page, condition);
	}
	
	public List<TlogisticsOrd> queryByCondition(
			TlogisticsOrdCondition condition) {
		return tlogisticsOrdDao.findListByCondition(condition);
	}

	public List<TlogisticsOrd> findListByObj(TlogisticsOrd tlo) {
		return tlogisticsOrdDao.findListByObj(tlo);
	}

	public TlogisticsOrd findTlogisticsOrdById(Long ilogisticsOrd) {
		return this.tlogisticsOrdDao.get(ilogisticsOrd);
	}
	
	public void updateTlogisticsOrd(TlogisticsOrd tlogisticsOrd) {
		tlogisticsOrd.setUpdTime(new Date());
		this.tlogisticsOrdDao.update(tlogisticsOrd);
	}
	
	public void deleteTlogisticsOrd(TlogisticsOrd tlogisticsOrd) {
		tlogisticsOrd.setUpdTime(new Date());
		tlogisticsOrd.setLogisticsOrdStatus(LogisticsOrdStatus.REVOKED);
		this.tlogisticsOrdDao.update(tlogisticsOrd);
	}
	
	@Transactional
	public void arrival(){
		TlogisticsOrd tlo = new TlogisticsOrd();
		tlo.setLogisticsOrdStatus(LogisticsOrdStatus.SHIPPED);
		List<TlogisticsOrd> logis = this.findListByObj(tlo);
		if (CollectionUtils.isEmpty(logis)) return;
		for (TlogisticsOrd logi: logis){
			TlogisticsOrdAddr addr = new TlogisticsOrdAddr();
			addr.setIlogisticsOrd(logi.getIlogisticsOrd());
			List<TlogisticsOrdAddr> addrs = this.logisticsOrdAddrService.findListSelect(addr);
			boolean arrival = false;
			if (CollectionUtils.isEmpty(addrs) == false){
				for (TlogisticsOrdAddr address:addrs){
					if (address.getRealArrival() == null){
						arrival = false;
						break;
					}else{
						arrival = true;
					}
				}
			}
			if (arrival){
				logi.setLogisticsOrdStatus(LogisticsOrdStatus.ALL_SERVICE);
				this.updateTlogisticsOrd(logi);
			}
		}
	}
	public TlogisticsOrd getByInnerOrdno(String innerOrdno){
		return this.tlogisticsOrdDao.getByInnerOrdno(innerOrdno);
	}
}
