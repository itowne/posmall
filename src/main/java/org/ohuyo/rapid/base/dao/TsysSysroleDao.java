package org.ohuyo.rapid.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.dao.BaseDao;
import org.ohuyo.rapid.base.entity.TsysSysrole;
import org.springframework.stereotype.Repository;

@Repository
public class TsysSysroleDao {
	
	@Resource
	private BaseDao baseDao;
	
	public List<TsysSysrole> findAll(){
		return baseDao.getHibernateDaoEx().find(TsysSysrole.class);
	}
	
//	@SuppressWarnings("unchecked")
//	public List<TsysSysrole> find(TsysSysroleCondition condition,Page page){
//		return baseDao.getHibernateDaoEx().findByCriteriaEx(condition, page);
//	}
	
	public TsysSysrole get(Long id){
		return (TsysSysrole) baseDao.getSession().get(TsysSysrole.class, id);
	}
	
	public void save(TsysSysrole tsysRole){
		baseDao.getSession().save(tsysRole);
	}
	
	public void update(TsysSysrole tsysRole,Long preIsys,Long preIsysrole){
		String sql="update t_sys_sysrole as a set a.i_sys='"+tsysRole.getIsys()+"',a.i_sysrole='"+tsysRole.getIsysrole()+"' where a.i_sys='"+preIsys+"' and a.i_sysrole='"+preIsysrole+"'";
		baseDao.getHibernateDaoEx().executeUpdateBySql(sql);
	}
	
	public void delete(TsysSysrole tsysRole){
		baseDao.getSession().delete(tsysRole);
	}
	
	@SuppressWarnings("unchecked")
	public List<TsysSysrole> findByIsys(Long id){
		final DetachedCriteria query = DetachedCriteria.forClass(TsysSysrole.class);   
	    Criteria criteria = query.getExecutableCriteria(baseDao.getSession());   
	    criteria.add(Restrictions.eq("isys",id));
	  
	    return  criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<TsysSysrole> findByIrole(Long id){
		final DetachedCriteria query = DetachedCriteria.forClass(TsysSysrole.class);   
	    Criteria criteria = query.getExecutableCriteria(baseDao.getSession());   
	    criteria.add(Restrictions.eq("isysrole",id));
	  
	    return  criteria.list(); 
	}
	
    
	public TsysSysrole findSysRole(TsysSysrole tsysSysrole){
		final DetachedCriteria query = DetachedCriteria.forClass(TsysSysrole.class);   
	    Criteria criteria = query.getExecutableCriteria(baseDao.getSession());   
	    criteria.add(Restrictions.eq("isys", tsysSysrole.getIsys()));
	    criteria.add(Restrictions.eq("isysrole", tsysSysrole.getIsysrole()));
	  
	    @SuppressWarnings("unchecked")   
	    List<TsysSysrole> list = criteria.list();   
	    if (list != null && !list.isEmpty()) {   
	        return list.get(0);   
	    }   
	    return null;   
	}
	
	@SuppressWarnings("rawtypes")
	public List findListByInfo(Page page, String loginName,String name) {
		StringBuilder sb = new StringBuilder(
				"select a.i_sys,a.i_sysrole,b.login_name,c.name from t_sys_sysrole a,t_sys b,t_sysrole c where a.i_sys=b.i_sys and a.i_sysrole=c.i_sysrole ");

		if (StringUtils.isNotBlank(loginName)) {
			sb.append(" and b.login_name like '%" + loginName + "%'");
		}
		
		if (StringUtils.isNotBlank(name)) {
			sb.append(" and c.name like '%" + name + "%'");
		}

		return this.baseDao.getHibernateDaoEx().findBySql(page, sb.toString());

	}
}
