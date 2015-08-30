package com.newland.posmall.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.newland.posmall.bean.basebusi.TordDetailPdt;
import com.newland.posmall.bean.basebusi.condition.TordDetailPdtCondition;

@Repository
public class TordDetailPdtDao {
	
	@Resource
	private BaseDao baseDao;
	
	public List<TordDetailPdt> find(){
		return baseDao.getHibernateDaoEx().find(TordDetailPdt.class);
	}
	
	public TordDetailPdt get(Long id){
		return (TordDetailPdt) baseDao.getSession().get(TordDetailPdt.class, id);
	}
	
	public void save(TordDetailPdt tordDetailPdt){
		baseDao.getSession().save(tordDetailPdt);
	}
	
	public void update(TordDetailPdt tordDetailPdt){
		baseDao.getSession().update(tordDetailPdt);
	}
	
	public List<TordDetailPdt> findSelect(TordDetailPdt tordDetailPdt){
		return this.baseDao.getHibernateDaoEx().findByExample(tordDetailPdt);
	}
	
	/**
	 * 订单的明细
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TordDetailPdt> findByItord(Long id){
		
		String hql = "from "+TordDetailPdt.class +" a where  a.iordDetail = ? ";
		return baseDao.getHibernateDaoEx().findByExample(hql, id);
		
	}
	
	@SuppressWarnings("unchecked")
	public List<TordDetailPdt> findByCon(TordDetailPdtCondition tordDetailPdtCondition){
		return this.baseDao.getHibernateDaoEx().findByCriteriaEx(tordDetailPdtCondition);
	}
}
