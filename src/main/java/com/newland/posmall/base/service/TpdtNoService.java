package com.newland.posmall.base.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.newland.posmall.base.dao.TpdtNoDao;
import com.newland.posmall.bean.basebusi.TpdtNo;
import com.newland.posmall.bean.basebusi.condition.TpdtNoCondition;

@Service
public class TpdtNoService {

	@Resource
	private TpdtNoDao tpdtNoDao;

	public void save(TpdtNo tpdtNo) {
		Date date = new Date();
		tpdtNo.setGenTime(date);
		tpdtNo.setUpdTime(date);
		tpdtNo.setDelFlag(Boolean.FALSE);
		this.tpdtNoDao.save(tpdtNo);
	}

	public void update(TpdtNo tpdtNo) {
		Date date = new Date();
		tpdtNo.setUpdTime(date);
		this.tpdtNoDao.update(tpdtNo);
	}

	public TpdtNo find(Long id) {
		return this.tpdtNoDao.get(id);
	}
	public List<TpdtNo> findCondition(TpdtNoCondition condition){
		condition.setDelFlag(Boolean.FALSE);
		return this.tpdtNoDao.findCondition(condition);
	}

}
