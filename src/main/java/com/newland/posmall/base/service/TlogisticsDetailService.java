package com.newland.posmall.base.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.newland.posmall.base.dao.TlogisticsDetailDao;
import com.newland.posmall.bean.basebusi.TlogisticsDetail;

@Service
public class TlogisticsDetailService {
	@Resource
	private TlogisticsDetailDao tlogisticsDetailDao;

	public void add(TlogisticsDetail tlogisticsDetail) {
		tlogisticsDetail.setGenTime(new Date());
		tlogisticsDetail.setUpdTime(new Date());
		this.tlogisticsDetailDao.save(tlogisticsDetail);
	}

	public void modify(TlogisticsDetail tlogisticsDetail) {
		tlogisticsDetail.setUpdTime(new Date());
		this.tlogisticsDetailDao.update(tlogisticsDetail);
	}

	public TlogisticsDetail findDetailById(Long id) {
		return this.tlogisticsDetailDao.getDetailById(id);
	}

	public List<TlogisticsDetail> findListByDetail(TlogisticsDetail tDetail) {
		return tlogisticsDetailDao.getListById(tDetail);
	}
}
