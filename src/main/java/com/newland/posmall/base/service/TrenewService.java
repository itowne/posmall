package com.newland.posmall.base.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.springframework.stereotype.Service;

import com.newland.posmall.base.dao.TrenewDao;
import com.newland.posmall.bean.customer.Trenew;
import com.newland.posmall.bean.customer.condition.TrenewCondition;
import com.newland.posmall.bean.dict.PayStatus;

/**
 * 产品续保service
 * @author zhouym
 * @since 2014-10-21
 */
@Service
public class TrenewService {
	
	@Resource
	private TrenewDao trenewDao;
	
	public List<Trenew> find(TrenewCondition condition,Page page){
		return this.trenewDao.find(condition, page);
	}
	
	public void update(Trenew trenew){
		Date now = new Date();
		trenew.setUpdTime(now);
		this.trenewDao.update(trenew);
	}
	
	public Trenew findById(Long irenew) {
		if(irenew == null) return null;
		return this.trenewDao.get(irenew);
	}
	
	public Trenew addTrenew(Trenew trenew) {
		Date d = new Date();
		trenew.setGenTime(d);
		trenew.setUpdTime(d);
		this.trenewDao.save(trenew);
		return trenew;
	}
	/**
	 * 
	* @Description: 改变续保支付状态
	* @author chenwenjing    
	* @date 2014-10-28 下午3:00:29
	 */
	public void auditTrenew(Trenew trenew, PayStatus payStatus){
		trenew.setPayStatus(payStatus);
		update(trenew);
	}
	
}
