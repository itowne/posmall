package com.newland.posmall.base.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.newland.posmall.base.dao.TcustCreditDao;
import com.newland.posmall.bean.customer.TcustCredit;

/**
 * 客户信用
 * @author zhouym
 *
 */
@Service
public class TcustCreditService {

	@Resource
	private TcustCreditDao tcustCreditDao;
	
	public TcustCredit find(Long id){
		return this.tcustCreditDao.get(id);
	}
	
	public List<TcustCredit> findByCondition(TcustCredit tcustCredit){
		return this.tcustCreditDao.findByCondition(tcustCredit);
	}

	public void add(TcustCredit tcustCredit) {
		this.tcustCreditDao.save(tcustCredit);
	}
}
