package com.newland.posmall.base.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.newland.posmall.base.dao.TpdtPlanDayQuotaDao;
import com.newland.posmall.bean.basebusi.TpdtPlanDay;
import com.newland.posmall.bean.basebusi.TpdtPlanDayQuota;
import com.newland.posmall.bean.dict.OrdDetailPdtType;
import com.newland.posmall.bean.dict.YesNoType;

@Service
public class TpdtPlanDayQuotaService {
	
	@Resource 
	private TpdtPlanDayQuotaDao tpdtPlanDayQuotaDao;
	
	public void save(TpdtPlanDayQuota tpdq) {
		Date date = new Date();
		tpdq.setGenTime(date);
		tpdq.setUpdTime(date);
		tpdtPlanDayQuotaDao.save(tpdq);
	}
	
	public synchronized void update(TpdtPlanDayQuota tpdq){
		tpdq.setUpdTime(new Date());
		tpdtPlanDayQuotaDao.update(tpdq);
	}
	
	public TpdtPlanDayQuota find(Long id){
		return this.tpdtPlanDayQuotaDao.get(id);
	}
	
	public synchronized List<TpdtPlanDayQuota> findBySelect(TpdtPlanDayQuota tpdq){
		return this.tpdtPlanDayQuotaDao.findSelect(tpdq);
	}
	 
	public TpdtPlanDayQuota findQuotaByPdtPlanDay(TpdtPlanDay tpdtPlanDay){
		TpdtPlanDayQuota tpdq = new TpdtPlanDayQuota();
		tpdq.setYear(tpdtPlanDay.getYear());
		tpdq.setMonth(tpdtPlanDay.getMonth());
		tpdq.setDay(tpdtPlanDay.getDay());
		tpdq.setOrdDetailPdtType(OrdDetailPdtType.DAILY_OUTPUT);
		List<TpdtPlanDayQuota> list = this.findBySelect(tpdq);
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}
	
	public void delete(TpdtPlanDayQuota t){
		this.tpdtPlanDayQuotaDao.delete(t);
	}
	
	/**
	 * 寻找 未归集的 库存(当前库存)
	 * @return
	 */
	public TpdtPlanDayQuota findNoCollectionStock(Long ipdt){
		TpdtPlanDayQuota tpdq = new TpdtPlanDayQuota();
		tpdq.setIpdt(ipdt);
		tpdq.setOrdDetailPdtType(OrdDetailPdtType.STOCK);
		tpdq.setCollectionFlag(YesNoType.NO);
		List<TpdtPlanDayQuota> list = this.findBySelect(tpdq);
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}
	
}
