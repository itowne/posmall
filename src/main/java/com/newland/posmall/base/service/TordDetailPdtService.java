package com.newland.posmall.base.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.newland.posmall.base.dao.TordDetailPdtDao;
import com.newland.posmall.bean.basebusi.TordDetailPdt;
import com.newland.posmall.bean.basebusi.condition.TordDetailPdtCondition;

@Service
public class TordDetailPdtService {

	@Resource
	private TordDetailPdtDao tordDetailPdtDao;

	public void save(TordDetailPdt tordDetailPdt) {
		Date date = new Date();
		tordDetailPdt.setGenTime(date);
		tordDetailPdt.setUpdTime(date);
		this.tordDetailPdtDao.save(tordDetailPdt);
	}

	public void update(TordDetailPdt tordDetailPdt) {
		tordDetailPdt.setUpdTime(new Date());
		this.tordDetailPdtDao.update(tordDetailPdt);
	}

	public TordDetailPdt find(Long id) {
		return this.tordDetailPdtDao.get(id);
	}

	public List<TordDetailPdt> findSelect(TordDetailPdt tordDetailPdt) {
		return this.tordDetailPdtDao.findSelect(tordDetailPdt);
	}

	public List<TordDetailPdt> findByCon(
			TordDetailPdtCondition tordDetailPdtCondition) {
		return this.tordDetailPdtDao.findByCon(tordDetailPdtCondition);
	}

}
