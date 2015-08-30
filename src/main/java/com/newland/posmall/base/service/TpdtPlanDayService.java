package com.newland.posmall.base.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.newland.posmall.base.dao.TpdtPlanDayDao;
import com.newland.posmall.base.dao.TpdtPlanDayHisDao;
import com.newland.posmall.bean.basebusi.TpdtPlanDay;
import com.newland.posmall.bean.basebusi.TpdtPlanDayHis;
import com.newland.posmall.bean.basebusi.TpdtPlanDayQuota;
import com.newland.posmall.bean.dict.OrdDetailPdtType;
import com.newland.posmall.bean.dict.YesNoType;

@Service
public class TpdtPlanDayService {
	
	
	@Resource 
	private TpdtPlanDayDao tpdtPlanDayDao;
	
	@Resource 
	private TpdtPlanDayHisDao tpdtPlanDayHisDao;
	
	@Resource 
	private TpdtPlanDayQuotaService tpdtPlanDayQuotaService ;
	
	/**
	 * 新增月排产计划
	 * @param tpm
	 * @param tpds
	 */
	public void save(TpdtPlanDay tpm) {
		
		Date date = new Date();
		tpm.setGenTime(date);
		tpm.setUpdTime(date);
		tpm.setDelFlag(Boolean.FALSE);
		
		tpdtPlanDayDao.save(tpm);
		
		//添加历史表
		TpdtPlanDayHis tpdtPlanDayHis = new TpdtPlanDayHis();
		tpdtPlanDayHis.setDay(tpm.getDay());
		tpdtPlanDayHis.setGenTime(date);
		tpdtPlanDayHis.setIpdtPlanDay(tpm.getIpdtPlanDay());
		tpdtPlanDayHis.setIpdtPlanMonth(tpm.getIpdtPlanMonth());
		tpdtPlanDayHis.setMonth(tpm.getMonth());
		tpdtPlanDayHis.setNum(tpm.getNum());
		tpdtPlanDayHis.setVer(tpm.getVersion());
		tpdtPlanDayHis.setWeek(tpm.getWeek());
		tpdtPlanDayHis.setWeekday(tpm.getWeekday());
		tpdtPlanDayHis.setYear(tpm.getYear());
		tpdtPlanDayHis.setIpdt(tpm.getIpdt());
		
		tpdtPlanDayHisDao.save(tpdtPlanDayHis);
		
		//添加额度表
		TpdtPlanDayQuota tpdtPlanDayQuota = new  TpdtPlanDayQuota();
		tpdtPlanDayQuota.setDay(tpm.getDay());
		tpdtPlanDayQuota.setGenTime(date);
		tpdtPlanDayQuota.setUpdTime(date);
		tpdtPlanDayQuota.setMonth(tpm.getMonth());
		tpdtPlanDayQuota.setNum(tpm.getNum());
		tpdtPlanDayQuota.setWeek(tpm.getWeek());
		tpdtPlanDayQuota.setWeekday(tpm.getWeekday());
		tpdtPlanDayQuota.setYear(tpm.getYear());
		tpdtPlanDayQuota.setIpdt(tpm.getIpdt());
		tpdtPlanDayQuota.setUsedQuota(0);
		tpdtPlanDayQuota.setRemainQuota(tpm.getNum());
		tpdtPlanDayQuota.setOrdDetailPdtType(OrdDetailPdtType.DAILY_OUTPUT);
		tpdtPlanDayQuota.setCollectionFlag(YesNoType.NO);
		tpdtPlanDayQuotaService.save(tpdtPlanDayQuota);
		
	}
	
	/**
	 * 
	 * @param 
	 */
	public void update(TpdtPlanDay tpm , Long ipdtOld){
		
		tpm.setUpdTime(new Date());
		tpdtPlanDayDao.update(tpm);
		
		//添加历史表
		TpdtPlanDayHis tpdtPlanDayHis = new TpdtPlanDayHis();
		tpdtPlanDayHis.setDay(tpm.getDay());
		tpdtPlanDayHis.setGenTime(new Date());
		tpdtPlanDayHis.setIpdtPlanDay(tpm.getIpdtPlanDay());
		tpdtPlanDayHis.setIpdtPlanMonth(tpm.getIpdtPlanMonth());
		tpdtPlanDayHis.setMonth(tpm.getMonth());
		tpdtPlanDayHis.setNum(tpm.getNum());
		tpdtPlanDayHis.setVer((tpm.getVersion()==null)?0:(tpm.getVersion()+1));
		tpdtPlanDayHis.setWeek(tpm.getWeek());
		tpdtPlanDayHis.setWeekday(tpm.getWeekday());
		tpdtPlanDayHis.setYear(tpm.getYear());
		tpdtPlanDayHis.setIpdt(tpm.getIpdt());
		
		tpdtPlanDayHisDao.save(tpdtPlanDayHis);
		
		//更新额度表
		TpdtPlanDayQuota tpdtPlanDayQuota  = new TpdtPlanDayQuota();
		tpdtPlanDayQuota.setYear(tpm.getYear());
		tpdtPlanDayQuota.setMonth(tpm.getMonth());
		tpdtPlanDayQuota.setDay(tpm.getDay());
		tpdtPlanDayQuota.setIpdt(ipdtOld);
		tpdtPlanDayQuota.setOrdDetailPdtType(OrdDetailPdtType.DAILY_OUTPUT);
		List<TpdtPlanDayQuota> list = tpdtPlanDayQuotaService.findBySelect(tpdtPlanDayQuota);
		if(list != null && list.size() > 0){
			tpdtPlanDayQuota =  list.get(0);
			if(tpm.getIpdt().equals(ipdtOld)){
				tpdtPlanDayQuota.setIpdt(tpm.getIpdt());
				tpdtPlanDayQuota.setNum(tpm.getNum());
				tpdtPlanDayQuota.setRemainQuota(tpm.getNum()-tpdtPlanDayQuota.getUsedQuota());
			}else{
				tpdtPlanDayQuota.setIpdt(tpm.getIpdt());
				tpdtPlanDayQuota.setNum(0);
				tpdtPlanDayQuota.setRemainQuota(tpm.getNum());
			}
			tpdtPlanDayQuota.setUpdTime(new Date());
			tpdtPlanDayQuotaService.update(tpdtPlanDayQuota);
		}
		
		
	}
	
	public TpdtPlanDay find(Long id){
		return this.tpdtPlanDayDao.get(id);
		
	}
	
	public void delete(TpdtPlanDay tpdtPlanDay){
		tpdtPlanDay.setDelFlag(Boolean.TRUE);
		this.tpdtPlanDayDao.update(tpdtPlanDay);
		
		//删除额度表
		TpdtPlanDayQuota tpdtPlanDayQuota  = new TpdtPlanDayQuota();
		tpdtPlanDayQuota.setYear(tpdtPlanDay.getYear());
		tpdtPlanDayQuota.setMonth(tpdtPlanDay.getMonth());
		tpdtPlanDayQuota.setDay(tpdtPlanDay.getDay());
		tpdtPlanDayQuota.setIpdt(tpdtPlanDay.getIpdt());
		tpdtPlanDayQuota.setOrdDetailPdtType(OrdDetailPdtType.DAILY_OUTPUT);
		List<TpdtPlanDayQuota> list = tpdtPlanDayQuotaService.findBySelect(tpdtPlanDayQuota);
		if(list != null && list.size() > 0){
			for(TpdtPlanDayQuota t : list){
				tpdtPlanDayQuotaService.delete(t);
			}
		}
	}
	
	
	public List<TpdtPlanDay> findBySelect(TpdtPlanDay tpd){
		tpd.setDelFlag(Boolean.FALSE);
		return this.tpdtPlanDayDao.findSelect(tpd);
	}
	
	public TpdtPlanDay findByIpdtAndDay(TpdtPlanDay tpd){
		List<TpdtPlanDay> list = this.tpdtPlanDayDao.findSelect(tpd);
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}
	
	
	

}
