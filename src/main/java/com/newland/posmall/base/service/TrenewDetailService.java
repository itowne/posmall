package com.newland.posmall.base.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import com.newland.posmall.base.dao.TrenewDetailDao;
import com.newland.posmall.bean.customer.TrenewDetail;

/**
 * 产品续保明细service
 * @author zhouym
 * @since 2014-10-27
 */
@Service
public class TrenewDetailService {
	
	@Resource
	private TrenewDetailDao trenewDetailDao;
	
	public TrenewDetail get(Long id){
		return this.trenewDetailDao.get(id);
	}
	
	public void update(TrenewDetail trenewDetail){
		trenewDetail.setUpdTime(new Date());
		this.trenewDetailDao.update(trenewDetail);
	}
	
	public TrenewDetail findById(Long id) {
		if(id == null) return null;
		return this.trenewDetailDao.get(id);
	}
	
	public TrenewDetail addTrenewDetail(TrenewDetail trenewDetail) {
		Date d = new Date();
		trenewDetail.setDelFlag(Boolean.FALSE);
		trenewDetail.setGenTime(d);
		trenewDetail.setUpdTime(d);
		this.trenewDetailDao.save(trenewDetail);
		return trenewDetail;
	}
	
	public List<TrenewDetail> findByExample(TrenewDetail trenewDetail) {
		return this.trenewDetailDao.findByExample(trenewDetail);
	}
	
	public List<TrenewDetail> findListByTrenewId(Long irenew) {
		if(irenew == null) return null;
		TrenewDetail condition = new TrenewDetail();
		condition.setIrenew(irenew);
		return this.trenewDetailDao.findByExample(condition);
	}
	
	public List<TrenewDetail> findBySeqNo(String seqNo) {
		if(StringUtils.isBlank(seqNo)) return null;
		TrenewDetail condition = new TrenewDetail();
		condition.setSeqNo(seqNo);
		return this.trenewDetailDao.findByExample(condition);
	}
	
	/**
	 * 设置保修期起始、结束日期
	 * @param trenewDetail
	 * @param lastStartDate
	 * @param lastEndDate
	 * @param life
	 */
	public void setLifeTimeOfRenew(TrenewDetail trenewDetail, Date lastStartDate, Date lastEndDate, int life) {
		if(trenewDetail == null) return;
		if(lastStartDate.compareTo(lastEndDate) >= 0) {
			throw new RuntimeException("起始日期不应该大于结束日期");
		}
		if(life <= 0) {
			throw new RuntimeException("续保期限必须大于0");
		}
		
		Calendar now = Calendar.getInstance();
		//上一个保修期的结束日期大于当前日期，则新的保修期起始日期不变
		if(DateUtils.truncate(lastEndDate, Calendar.DATE).compareTo(DateUtils.truncate(new Date(), Calendar.DATE)) > 0) {
			trenewDetail.setLifeStartTime(lastStartDate);
			now.setTime(lastEndDate);
			now.add(Calendar.YEAR, life);
			trenewDetail.setLifeEndTime(now.getTime());
		}else {//上一个保修期的结束日期小于等于当前日期，则新的保修期起始日期 = now + 1天，保修期是一个新的轮回
			now.add(Calendar.DATE, 1);
			trenewDetail.setLifeStartTime(now.getTime());
			now.add(Calendar.YEAR, life);
			trenewDetail.setLifeEndTime(now.getTime());
		}
	}
}
