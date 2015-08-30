package com.newland.posmall.base.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.newland.posmall.bean.basebusi.TpayNotify;
import com.newland.posmall.bean.basebusi.condition.TpayNofityCondition;
import com.newland.posmall.bean.dict.PayStatus;
import com.newland.posmall.bean.dict.PayType;

@Repository
public class TpayNotifyDao {
	
	@Resource
	private BaseDao baseDao;
	
	public List<TpayNotify> find(){
		return baseDao.getHibernateDaoEx().find(TpayNotify.class);
	}
	
	public TpayNotify get(Long id){
		return (TpayNotify) baseDao.getSession().get(TpayNotify.class, id);
	}
	
	public void save(TpayNotify tpayNotify){
		baseDao.getSession().save(tpayNotify);
	}
	
	public void update(TpayNotify tpayNotify){
		baseDao.getSession().update(tpayNotify);
	}
	
	public List<TpayNotify> findSelect(TpayNotify tpayNotify){
		return baseDao.getHibernateDaoEx().findByExample(tpayNotify);
	}
	
	/**
	 * 查询付款通知书
	 * @param icust 客户id
	 * @param payType 支付类型
	 * @param statuses 支付状态
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TpayNotify> findBySql(Long icust, PayType payType, PayStatus[] statuses){
		StringBuffer sb = new StringBuffer("from " + TpayNotify.class.getName() + " p where 1 = ? ");
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(1);
		if(icust != null) {
			sb.append("and p.icust = ? ");
			params.add(icust);
		}
		if(payType != null) {
			sb.append("and p.payType = ? ");
			params.add(payType);
		}
		if(statuses != null && statuses.length >= 1) {
			sb.append("and p.payNotifyStatus in (");
			for (PayStatus payStatus : statuses) {
				sb.append("'" + payStatus + "'" + ",");
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append(") ");
		}
		sb.append("order by p.payType, p.updTime desc");
		return baseDao.getHibernateDaoEx().find(sb.toString(), params.toArray());
	}
	
	@SuppressWarnings("rawtypes")
	public List findListByInfo(Page page, String payType, String payNotifyStatus, String startDate, String endDate, String icust){
		List<Object> params = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder(
				"select a.i_pay_notify , a.i_cust, a.i_fk, a.pay_type, a.pay_notify_no, a.memo, a.gen_time, a.upd_time, a.pay_notify_status , a.pay_notify_no as amt , a.pay_notify_no as agmtNo , a.pay_notify_no as ordNo , a.pay_notify_no as logistics_no from  t_pay_notify a   where a.del_flag = 'N' ");
		if (StringUtils.isNotBlank(payType)) {
			sb.append(" and a.pay_type = ? ");
			params.add(payType);
		}
		if (StringUtils.isNotBlank(payNotifyStatus)) {
			sb.append(" and a.pay_notify_status = ? ");
			params.add(payNotifyStatus);
		}
		if (StringUtils.isNotBlank(startDate)) {
			sb.append(" and DATE_FORMAT(a.gen_time, '%Y-%m-%d') >= ?");
			params.add(startDate);
		}
		if ( StringUtils.isNotBlank(endDate)) {
			sb.append(" and DATE_FORMAT(a.gen_time, '%Y-%m-%d') <= ?");
			params.add(endDate);
		}
		if ( StringUtils.isNotBlank(icust) ) {
			sb.append(" and a.i_cust in ("+icust+")");
		}

		sb.append(" order by a.i_pay_notify  desc");
		return this.baseDao.getHibernateDaoEx().findBySql(page, sb.toString(),
				params.toArray());
	}
	
	@SuppressWarnings("unchecked")
	public List<TpayNotify> findByCondition(TpayNofityCondition condition) {
		return baseDao.getHibernateDaoEx().findByCriteriaEx(condition);
	}

}
