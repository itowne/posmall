package com.newland.posmall.base.dao;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.dao.BaseDao;
import org.ohuyo.rapid.base.entity.condition.TcustCondition;
import org.springframework.stereotype.Repository;
import com.newland.posmall.bean.customer.Tcust;
import com.newland.posmall.bean.customer.TcustReg;

/**
 * 
 * @author zhouym
 *
 */
@Repository
public class TcustDao{

	@Resource
	private BaseDao baseDao;

	public void save(Tcust tcust) {
		baseDao.getSession().save(tcust);
	}

	public Tcust getByLoginName(String loginName) {
		Tcust t = new Tcust();
		t.setLoginName(loginName);
		return baseDao.getHibernateDaoEx().getFirstOneByExample(t);
	}

	public void update(Tcust tcust) {
		baseDao.getSession().update(tcust);
	}

	public List<Tcust> find() {
		return baseDao.getHibernateDaoEx().find(Tcust.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Tcust> find(TcustCondition tcustConfition,Page page) {
		return baseDao.getHibernateDaoEx().findByCriteriaEx(tcustConfition, page);
	}

	public Tcust get(Long id) {
		return (Tcust) baseDao.getSession().get(Tcust.class, id);
	}

	public List<Tcust> findSelect(Tcust tcust) {
		return baseDao.getHibernateDaoEx().findByExample(tcust);
	}

	public TcustReg getTcustReg(Long id){
		return (TcustReg) baseDao.getSession().get(TcustReg.class, id);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Tcust> findCustByLoginName(String loginName) {
		String hql = "from "+Tcust.class.getName()+ " where loginName like '%"+loginName+"%'";
		return baseDao.getHibernateDaoEx().find(hql);
	}
	
//	/**
//	 * 分页查询 客户列表
//	 * @param condition
//	 * @param page
//	 * @return
//	 */
//	public List<?> findCustByCon(TcustViewCondition condition, Page page) {
//		return baseDao.getHibernateDaoEx().findByViewCondition(condition, page);
//	}
	
	/**
	 * 客户分页查询
	 * @param page
	 * @param custStatus
	 * @param creditStatus
	 * @param name
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List findListByInfo(Page page, String custStatus,
			String creditLevel, String name) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder(
				"select a.i_cust, a.cust_status, a.credit_level, a.gen_time, b.cust_no, b.name, b.contact_name, b.mobile, a.login_name, a.upd_time from  t_cust  a left join t_cust_reg b on  a.i_cust = b.i_cust where 1=1 ");
		if (StringUtils.isNotBlank(custStatus)) {
			sb.append(" and a.cust_status = ? ");
			params.add(custStatus);
		}
		if (StringUtils.isNotBlank(creditLevel)) {
			sb.append(" and a.credit_level = ? ");
			params.add(creditLevel);
		}
		if (StringUtils.isNotBlank(name)) {
			sb.append(" and b.name like '%" + name + "%'");
		}

		sb.append(" order by a.upd_time desc ");
		return this.baseDao.getHibernateDaoEx().findBySql(page, sb.toString(),
				params.toArray());

	}

}
