package com.newland.posmall.base.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.newland.posmall.base.dao.TpayNotifyDao;
import com.newland.posmall.bean.basebusi.TpayNotify;
import com.newland.posmall.bean.basebusi.condition.TpayNofityCondition;
import com.newland.posmall.bean.dict.PayStatus;
import com.newland.posmall.bean.dict.PayType;
import com.newland.posmall.identifier.IdentifierService;

@Service
public class TpayNotifyService {
	
	
	@Resource 
	private TpayNotifyDao tpayNotifyDao;
	
	@Resource(name = "identifierService")
	private IdentifierService identifierService;
	
	
	@SuppressWarnings("rawtypes")
	public List findListByInfo(Page page, String payType, String payNotifyStatus, String startDate, String endDate, String icust){
		return tpayNotifyDao.findListByInfo(page, payType, payNotifyStatus, startDate, endDate, icust);
	}
	
	/**
	 * 新增付款通知书
	 * @param tpayNotify
	 */
	public void save(TpayNotify tpayNotify) {
		
		Date date = new Date();
		tpayNotify.setGenTime(date);
		tpayNotify.setUpdTime(date);
		tpayNotify.setDelFlag(Boolean.FALSE);
		tpayNotify.setPayNotifyNo(identifierService.genNotifyId());
		tpayNotify.setHavepayAmt(new BigDecimal(0));
		
		tpayNotifyDao.save(tpayNotify);
		
	}
	
	/**
	 * 
	 * @param 
	 */
	public void update(TpayNotify tpayNotify){
		
		tpayNotify.setUpdTime(new Date());
		
		tpayNotifyDao.update(tpayNotify);
		
	}
	
	
	public TpayNotify find(Long id){
		return this.tpayNotifyDao.get(id);
		
	}
	
	/**
	 * 由类型和fk 获取 通知书
	 * @param ifk
	 * @param payType
	 * @return
	 */
	public TpayNotify findByifkAndPayType(Long ifk, PayType payType){
		TpayNotify tpayNotify = new TpayNotify();
		tpayNotify.setDelFlag(Boolean.FALSE);
		tpayNotify.setIfk(ifk);
		tpayNotify.setPayType(payType);
		List<TpayNotify> list = findBySelect(tpayNotify);
		return (CollectionUtils.isEmpty(list))?null:list.get(0);
		
	}
	
	public List<TpayNotify> findBySelect(TpayNotify tpayNotify){
		tpayNotify.setDelFlag(Boolean.FALSE);
		return this.tpayNotifyDao.findSelect(tpayNotify);
	}
	
	/**
	 * 查询付款通知书
	 * @param icust 客户id
	 * @param payType 支付类型
	 * @param statuses 支付状态
	 * @return
	 */
	public List<TpayNotify> findBySql(Long icust, PayType payType, PayStatus[] statuses){
		return this.tpayNotifyDao.findBySql(icust, payType, statuses);
	}
	
	/**
	 * 撤销通知书
	 * @param ipayNotify
	 */
	public void delete(Long ipayNotify){
		TpayNotify tpayNotify = this.tpayNotifyDao.get(ipayNotify);
		this.delete(tpayNotify);
	}
	
	public void delete(TpayNotify tpayNotify){
		tpayNotify.setUpdTime(new Date());
		tpayNotify.setPayNotifyStatus(PayStatus.REVOKED);
		this.tpayNotifyDao.update(tpayNotify);
	}
	
	public List<TpayNotify> queryListByCondition(TpayNofityCondition condition) {
		return this.tpayNotifyDao.findByCondition(condition);
	}
}
