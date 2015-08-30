package org.ohuyo.rapid.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.dao.BaseDao;
import org.ohuyo.rapid.base.entity.TcustCustrole;
import org.ohuyo.rapid.base.entity.condition.TcustCustroleCondition;
import org.springframework.stereotype.Repository;
@Repository
public class TcustCustroleDao {
	
	@Resource
	private BaseDao baseDao;
	
	public List<TcustCustrole> find(){
		return baseDao.getHibernateDaoEx().find(TcustCustrole.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<TcustCustrole> find(TcustCustroleCondition condition,Page page){
		return baseDao.getHibernateDaoEx().findByCriteriaEx(condition, page);
	}
	
	public TcustCustrole get(Long id){
		return (TcustCustrole) baseDao.getSession().get(TcustCustrole.class, id);
	}
	
	public void save(TcustCustrole tcustRole){
		baseDao.getSession().save(tcustRole);
	}
	
	public void update(TcustCustrole tcustRole,Long preIcust,Long preIcustrole){
		String sql="update t_cust_custrole as a set a.i_cust='"+tcustRole.getIcust()+"',a.i_custrole='"+tcustRole.getIcustrole()+"' where a.i_cust='"+preIcust+"' and a.i_custrole='"+preIcustrole+"'";
		baseDao.getHibernateDaoEx().executeUpdateBySql(sql);
	}
	
	public void delete(TcustCustrole tcustRole){
		baseDao.getSession().delete(tcustRole);
	}
	
	@SuppressWarnings("unchecked")
	public List<TcustCustrole> findByIcust(Long id){
		final DetachedCriteria query = DetachedCriteria.forClass(TcustCustrole.class);   
	    Criteria criteria = query.getExecutableCriteria(baseDao.getSession());   
	    criteria.add(Restrictions.eq("icust",id));
	  
	    return  criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<TcustCustrole> findByIrole(Long id){
		final DetachedCriteria query = DetachedCriteria.forClass(TcustCustrole.class);   
	    Criteria criteria = query.getExecutableCriteria(baseDao.getSession());   
	    criteria.add(Restrictions.eq("icustrole",id));
	  
	    return  criteria.list(); 
	}
	
    
	public TcustCustrole findCustRole(TcustCustrole tcustCustrole){
		final DetachedCriteria query = DetachedCriteria.forClass(TcustCustrole.class);   
	    Criteria criteria = query.getExecutableCriteria(baseDao.getSession());   
	    criteria.add(Restrictions.eq("icust", tcustCustrole.getIcust()));
	    criteria.add(Restrictions.eq("icustrole", tcustCustrole.getIcustrole()));
	  
	    @SuppressWarnings("unchecked")   
	    List<TcustCustrole> list = criteria.list();   
	    if (list != null && !list.isEmpty()) {   
	        return list.get(0);   
	    }   
	    return null;   
	}
	
	@SuppressWarnings("rawtypes")
	public List findListByInfo(Page page, String loginName,String name) {
		StringBuilder sb = new StringBuilder(
				"select a.i_cust,a.i_custrole,b.login_name,c.name from t_cust_custrole a,t_cust b,t_custrole c where a.i_cust=b.i_cust and a.i_custrole=c.i_custrole ");

		if (StringUtils.isNotBlank(loginName)) {
			sb.append(" and b.login_name like '%" + loginName + "%'");
		}
		
		if (StringUtils.isNotBlank(name)) {
			sb.append(" and c.name like '%" + name + "%'");
		}

		return this.baseDao.getHibernateDaoEx().findBySql(page, sb.toString());

	}
}
