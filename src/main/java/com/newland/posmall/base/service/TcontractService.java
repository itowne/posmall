package com.newland.posmall.base.service;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.newland.posmall.base.dao.TcontractDao;
import com.newland.posmall.bean.basebusi.Tcontract;
import com.newland.posmall.bean.basebusi.condition.TcontractCondition;


@Service
public class TcontractService {
	
	@Resource
	private TcontractDao tcontractDao;
	
	
	public void save(Tcontract tcontract){
		Date date = new Date();
		tcontract.setGenTime(date);
		tcontract.setUpdTime(date);
		tcontract.setDelFlag(Boolean.FALSE);
		this.tcontractDao.save(tcontract);
	}
	
	public void update(Tcontract tcontract){
		tcontract.setUpdTime(new Date());
		this.tcontractDao.update(tcontract);		
	}
	
	public Tcontract find(Long id){
		return this.tcontractDao.get(id);
		
	}
	
	public Tcontract queryByName(String contractName){
		Tcontract t = new Tcontract();
		t.setContractName(contractName);
		t.setDelFlag(Boolean.FALSE);
		List<Tcontract> list = this.tcontractDao.find(t);
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}
	
	public List<Tcontract> queryTcontract(TcontractCondition tcontractCondition,Page page){
		return this.tcontractDao.find(tcontractCondition, page);
	}
}
