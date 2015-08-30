package com.newland.posmall.base.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.dao.BaseDao;
import org.ohuyo.rapid.base.entity.condition.TholidayCondition;
import org.springframework.stereotype.Repository;

import com.newland.posmall.bean.common.Tholiday;


@Repository
public class TholidayDao {

	@Resource
	private BaseDao baseDao;

	public void save(Tholiday tholiday) {
		baseDao.getSession().save(tholiday);
	}
	
	public void update(Tholiday tholiday) {
		baseDao.getSession().update(tholiday);
	}
	
	public List<Tholiday> find() {
		return baseDao.getHibernateDaoEx().find(Tholiday.class);
	}
	
	public Tholiday get(Long id) {
		return (Tholiday) baseDao.getSession().get(Tholiday.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Tholiday> find(TholidayCondition tholidayCondition,Page page) {
		return baseDao.getHibernateDaoEx().findByCriteriaEx(tholidayCondition, page);
	}
	/**
	 * 
	* @Description: 条件分页查询
	* @author chenwenjing    
	* @date 2014-8-29 下午12:14:23
	 */
	@SuppressWarnings("rawtypes")
	public List findListByInfo(Page page, String startTime, String endTime, String holiStatus){
		List<Object> params = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder(
				"select a.i_holiday,a.year,a.month,a.day,a.holi_status,a.memo from t_holiday a  where del_flg='N' ");
		if (StringUtils.isNotBlank(startTime)) {
			sb.append(" and str_to_date(CONCAT(year,'-',month,'-',day),'%Y-%m-%d') >= ?");
			params.add(startTime);
		}
		if (StringUtils.isNotBlank(endTime)) {
			sb.append(" and str_to_date(CONCAT(year,'-',month,'-',day),'%Y-%m-%d') <= ?");
			params.add(endTime);
		}
		if ( StringUtils.isNotBlank(holiStatus) ) {
			sb.append(" and holi_status = ?"  );
			params.add(holiStatus);
		}
		sb.append(" order by a.year desc,a.month desc,a.day desc ");
		return this.baseDao.getHibernateDaoEx().findBySql(page, sb.toString(),
				params.toArray());
		
	}
	
	public Tholiday findObjectByInfo(Tholiday tholiday){
		return this.baseDao.getHibernateDaoEx().getFirstOneByExample(tholiday);
	}

}
