package com.newland.posmall.base.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.newland.posmall.base.dao.TordDetailDao;
import com.newland.posmall.base.dao.TordDetailHisDao;
import com.newland.posmall.bean.basebusi.TordDetail;
import com.newland.posmall.bean.basebusi.TordDetailHis;
import com.newland.posmall.bean.basebusi.condition.TordDetailCondition;

@Service
public class TordDetailService {

	@Resource
	private TordDetailDao tordDetailDao;

	@Resource
	private TordDetailHisDao tordDetailHisDao;

	public void save(TordDetail tordDetail) {
		Date date = new Date();
		tordDetail.setGenTime(date);
		tordDetail.setUpdTime(date);
		tordDetail.setDelFlag(Boolean.FALSE);
		this.tordDetailDao.save(tordDetail);

		TordDetailHis his = new TordDetailHis();
		his.setGenTime(date);
		his.setAmt(tordDetail.getAmt());
		his.setIord(tordDetail.getIord());
		his.setIordDetail(tordDetail.getIordDetail());
		his.setIpdtHis(tordDetail.getIpdtHis());
		his.setIpdt(tordDetail.getIpdt());
		his.setNum(tordDetail.getNum());
		his.setPrice(tordDetail.getPrice());
		his.setOrdDetailStatus(tordDetail.getOrdDetailStatus());
		his.setRate(tordDetail.getRate());
		his.setIcust(tordDetail.getIcust());
		his.setUsedQuota(tordDetail.getUsedQuota());
		his.setRemainQuota(tordDetail.getRemainQuota());
		his.setDeliveryed(tordDetail.getDeliveryed());
		his.setPendingNum(tordDetail.getPendingNum());
		his.setProducedNum(tordDetail.getProducedNum());
		his.setAmtOfDelivery(tordDetail.getAmtOfDelivery());

		his.setStartSn(tordDetail.getStartSn());
		his.setEndSn(tordDetail.getEndSn());
		this.tordDetailHisDao.save(his);

	}

	public void update(TordDetail tordDetail) {
		Date date = new Date();

		tordDetail.setUpdTime(new Date());

		this.tordDetailDao.update(tordDetail);

		TordDetailHis his = new TordDetailHis();
		his.setGenTime(date);
		his.setAmt(tordDetail.getAmt());
		his.setIord(tordDetail.getIord());
		his.setIordDetail(tordDetail.getIordDetail());
		his.setIpdtHis(tordDetail.getIpdtHis());
		his.setIpdt(tordDetail.getIpdt());
		his.setNum(tordDetail.getNum());
		his.setPrice(tordDetail.getPrice());
		his.setIcust(tordDetail.getIcust());
		his.setRate(tordDetail.getRate());
		his.setOrdDetailStatus(tordDetail.getOrdDetailStatus());
		his.setUsedQuota(tordDetail.getUsedQuota());
		his.setRemainQuota(tordDetail.getRemainQuota());
		his.setDeliveryed(tordDetail.getDeliveryed());
		his.setPendingNum(tordDetail.getPendingNum());
		his.setProducedNum(tordDetail.getProducedNum());
		his.setAmtOfDelivery(tordDetail.getAmtOfDelivery());

		his.setStartSn(tordDetail.getStartSn());
		his.setEndSn(tordDetail.getEndSn());
		this.tordDetailHisDao.save(his);

	}

	public TordDetail find(Long id) {
		return this.tordDetailDao.get(id);
	}

	public List<TordDetail> findSelect(TordDetail tordDetail) {
		return this.tordDetailDao.findSelect(tordDetail);
	}

	public List<TordDetail> findByCon(TordDetailCondition tordDetailCondition) {
		return this.tordDetailDao.findByCon(tordDetailCondition);
	}

}
