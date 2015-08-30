package com.newland.posmall.base.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.newland.posmall.base.dao.TpayDetailDao;
import com.newland.posmall.bean.basebusi.TpayDetail;
import com.newland.posmall.bean.dict.PayStatus;
import com.newland.posmall.bean.dict.PayType;

@Service
public class TpayDetailService {
	
	
	@Resource 
	private TpayDetailDao tpayDetailDao;
	
	
	/**
	 * 保存
	 * @param tpayDetail
	 */
	public void save(TpayDetail tpayDetail) {
		
		Date date = new Date();
		tpayDetail.setGenTime(date);
		tpayDetail.setUpdTime(date);
		
		tpayDetailDao.save(tpayDetail);
		
	}
	
	/**
	 * 
	 * @param 
	 */
	public void update(TpayDetail tpayDetail){
		tpayDetail.setUpdTime(new Date());
		tpayDetailDao.update(tpayDetail);
		
	}
	
	public List<TpayDetail> findSelect(Long ifk, PayType payType){
		TpayDetail tpayDetail = new TpayDetail();
		tpayDetail.setIfk(ifk);
		tpayDetail.setPayType(payType);
		return this.tpayDetailDao.findSelect(tpayDetail);
	}
	
	/**
	 * 查询 待审核的 凭证详细
	 * @param ifk
	 * @param payType
	 * @return
	 */
	public List<TpayDetail> findSelectWaitAudit(Long ifk, PayType payType){
		TpayDetail tpayDetail = new TpayDetail();
		tpayDetail.setIfk(ifk);
		tpayDetail.setPayType(payType);
		tpayDetail.setPayStatus(PayStatus.WAIT_AUDIT);
		List<TpayDetail> list = this.tpayDetailDao.findSelect(tpayDetail);
		return list;
	}
	
	
	
	public TpayDetail find(Long id){
		return this.tpayDetailDao.get(id);
		
	}
	
	
	

}
