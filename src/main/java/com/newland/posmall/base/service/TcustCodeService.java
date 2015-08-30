package com.newland.posmall.base.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.newland.posmall.base.dao.TcustCodeDao;
import com.newland.posmall.bean.customer.TcustCode;
import com.newland.posmall.bean.customer.condition.TcustCodeCondition;
import com.newland.posmall.bean.dict.CustCodeStatus;

@Service
public class TcustCodeService {
	
	@Resource
	private TcustCodeDao tcustCodeDao;
	
	public TcustCode findById(Long id) {
		return this.tcustCodeDao.get(id);
	}
	
	public void add(TcustCode tcustCode) {
		tcustCode.setGenTime(new Date());
		tcustCode.setUpdTime(new Date());
		this.tcustCodeDao.save(tcustCode);
	}
	
	public void modify(TcustCode tcustCode) {
		tcustCode.setUpdTime(new Date());
		this.tcustCodeDao.update(tcustCode);
	}

	/**
	 * 根据注册码、状态查询
	 * @param custCode
	 * @param status
	 * @return
	 */
	public TcustCode findTcustCode(String custCode, CustCodeStatus status) {
		TcustCode tcustCode = new TcustCode();
		tcustCode.setCustCode(custCode);
		tcustCode.setCustCodeStatus(status);
		List<TcustCode> list = this.tcustCodeDao.findByExample(tcustCode);
		if(CollectionUtils.isEmpty(list)) {
			return null;
		}
		return list.get(0);
	}
	
	/**
	 * 分页查询注册码
	 * @param condition
	 * @param page
	 * @return
	 */
	public List<TcustCode> findPageByCondition(TcustCodeCondition condition, Page page) {
		return this.tcustCodeDao.findPageByCondition(condition, page);
	}
	
	/**
	 * 注册码是否已存在
	 * @param custCode
	 * @return
	 */
	public boolean isCustCodeExists(String custCode) {
		TcustCode tcustCode = new TcustCode();
		tcustCode.setCustCode(custCode);
		List<TcustCode> list = this.tcustCodeDao.findByExample(tcustCode);
		if(CollectionUtils.isEmpty(list)) {
			return false;
		}
		return true;
	}
}
