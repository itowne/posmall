package com.newland.posmall.base.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.newland.posmall.base.dao.TpayDao;
import com.newland.posmall.base.dao.TpayDetailDao;
import com.newland.posmall.bean.basebusi.Tpay;
import com.newland.posmall.bean.basebusi.TpayDetail;
import com.newland.posmall.bean.dict.PayType;
import com.newland.posmall.identifier.IdentifierService;

@Service
public class TpayService {
	
	
	@Resource 
	private TpayDao tpayDao;
	@Resource
	private TpayDetailDao payDetailDao;
	

	@Resource(name = "identifierService")
	private IdentifierService identifierService;
	
	
	/**
	 * 新增支付
	 * @param tpay
	 * @param tpds
	 */
	public void add(Tpay tpay) {
		
		Date date = new Date();
		tpay.setGenTime(date);
		tpay.setUpdTime(date);
		if(tpay.getRemainAmt() == null){
			tpay.setRemainAmt(tpay.getAmt());
		}
		
		tpay.setPayNo(identifierService.genPayId());
		
		tpayDao.save(tpay);
		
	}
	
	/**
	 * 
	 * @param 
	 */
	public void modify(Tpay tpay){
		
		tpay.setUpdTime(new Date());
		tpayDao.update(tpay);
		
	}
	
	
	public Tpay find(Long id){
		return this.tpayDao.get(id);
		
	}
	
	/**
	 * 由通知书 对象  通过  payType，fk 查询  关联的所有  pay
	 * @param fk
	 * @param payType
	 * @return
	 */
	public Map<String, Long> queryPayMapByfk(Long fk, PayType payType){
		TpayDetail tpayDetail = new TpayDetail();
		tpayDetail.setIfk(fk);
		tpayDetail.setPayType(payType);
		Map<String, Long> map = new TreeMap<String, Long>();
	    List<TpayDetail> pdList = this.payDetailDao.findSelect(tpayDetail);
	    if(pdList != null && pdList.size() > 0 ){
	    	for(TpayDetail td : pdList){
	    		map.put(td.getIpay().toString(), td.getIpay());
	    	}
	    	return map;
	    }else{
	    	return null;
	    }
	}
	
	/**
	 * 通过 ipay 查询所有 TpayDetail
	 * @param ipay
	 * @return
	 */
	public List<TpayDetail> findByIpay(Long ipay){
		return this.payDetailDao.findByIpay(ipay);
	}
	
	
	
	

}
