package com.newland.posmall.base.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.newland.posmall.base.dao.TlogisticsOrdAddrDao;
import com.newland.posmall.bean.basebusi.TlogisticsOrdAddr;
import com.newland.posmall.bean.basebusi.condition.TlogisticsOrdAddrCondition;

@Service
public class TlogisticsOrdAddrService {

	@Resource
	private TlogisticsOrdAddrDao tlogisticsOrdAddrDao;
	
	public void save(TlogisticsOrdAddr tlogisticsOrdAddr) {
		tlogisticsOrdAddr.setGenTime(new Date());
		tlogisticsOrdAddr.setUpdTime(new Date());
		tlogisticsOrdAddr.setDelFlag(Boolean.FALSE);
		this.tlogisticsOrdAddrDao.save(tlogisticsOrdAddr);
	}
	
	public void update(TlogisticsOrdAddr tlogisticsOrdAddr) {
		tlogisticsOrdAddr.setUpdTime(new Date());
		this.tlogisticsOrdAddrDao.update(tlogisticsOrdAddr);
	}
	
	public TlogisticsOrdAddr getTlogisticsOrdAddrById(Long id) {
		return this.tlogisticsOrdAddrDao.getTlogisticsOrdAddrById(id);
	}
	
	public List<TlogisticsOrdAddr> findListSelect(TlogisticsOrdAddr tlogisticsOrdAddr) {
		tlogisticsOrdAddr.setDelFlag(Boolean.FALSE);
		return this.tlogisticsOrdAddrDao.findListSelect(tlogisticsOrdAddr);
	}
	public void delete(TlogisticsOrdAddr tlogisticsOrdAddr) {
		tlogisticsOrdAddr.setUpdTime(new Date());
		tlogisticsOrdAddr.setDelFlag(Boolean.TRUE);
		this.tlogisticsOrdAddrDao.update(tlogisticsOrdAddr);
	}
	public List<TlogisticsOrdAddr> findCondition(TlogisticsOrdAddrCondition condition) {
		return this.tlogisticsOrdAddrDao.findCondition(condition);
	}
}
