package org.ohuyo.rapid.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.dao.BaseDao;
import org.ohuyo.rapid.base.entity.TuserUserrole;
import org.springframework.stereotype.Repository;
@Repository
public class TuserUserroleDao {
	
	@Resource
	private BaseDao baseDao;
	/**
	 * 
	* @Description: 查找所有
	* @author chenwenjing    
	* @date 2014-8-29 上午10:14:11
	 */
	public List<TuserUserrole> find(){
		return baseDao.getHibernateDaoEx().find(TuserUserrole.class);
	}
	/**
	 * 
	* @Description: 根据编号查找
	* @author chenwenjing    
	* @date 2014-8-29 上午10:14:36
	 */
	public TuserUserrole get(Long id){
		return (TuserUserrole) baseDao.getSession().get(TuserUserrole.class, id);
	}
	
	/**
	 * 
	* @Description: 保存对象
	* @author chenwenjing    
	* @date 2014-8-29 上午10:16:00
	 */
	public void save(TuserUserrole tuserUserrole){
		baseDao.getSession().save(tuserUserrole);
	}
	
	/**
	 * 
	* @Description: 更新对象
	* @author chenwenjing    
	* @date 2014-8-29 上午10:16:22
	 */
	public void update(TuserUserrole tuserUserrole,Long preIuser,Long preIuserrole){
		String sql="update t_user_userrole as a set a.i_user='"+tuserUserrole.getIuser()+"',a.i_userrole='"+tuserUserrole.getIuserrole()+"' where a.i_user='"+preIuser+"' and a.i_userrole='"+preIuserrole+"'";
		baseDao.getHibernateDaoEx().executeUpdateBySql(sql);
	}
	
	/**
	 * 
	* @Description: 删除对象
	* @author chenwenjing    
	* @date 2014-8-29 上午10:17:21
	 */
	public void delete(TuserUserrole tuserUserrole){
		baseDao.getSession().delete(tuserUserrole);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<TuserUserrole> findByIuser(Long id){
		final DetachedCriteria query = DetachedCriteria.forClass(TuserUserrole.class);   
	    Criteria criteria = query.getExecutableCriteria(baseDao.getSession());   
	    criteria.add(Restrictions.eq("iuser",id));
	  
	    return  criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<TuserUserrole> findByIrole(Long id){
		final DetachedCriteria query = DetachedCriteria.forClass(TuserUserrole.class);   
	    Criteria criteria = query.getExecutableCriteria(baseDao.getSession());   
	    criteria.add(Restrictions.eq("iuserrole",id));
	  
	    return  criteria.list(); 
	}
	
    /**
     * 
    * @Description: 根据双主键查找对象
    * @author chenwenjing    
    * @date 2014-8-29 上午10:20:46
     */
	public TuserUserrole findUserRole(TuserUserrole tuserUserrole){
		final DetachedCriteria query = DetachedCriteria.forClass(TuserUserrole.class);   
	    Criteria criteria = query.getExecutableCriteria(baseDao.getSession());   
	    criteria.add(Restrictions.eq("iuser", tuserUserrole.getIuser()));
	    criteria.add(Restrictions.eq("iuserrole", tuserUserrole.getIuserrole()));
	  
	    @SuppressWarnings("unchecked")   
	    List<TuserUserrole> list = criteria.list();   
	    if (list != null && !list.isEmpty()) {   
	        return list.get(0);   
	    }   
	    return null;   
	}
	
	/**
	 * 
	* @Description: 根据相关条件分页查询
	* @author chenwenjing    
	* @date 2014-8-29 上午10:21:29
	 */
	@SuppressWarnings("rawtypes")
	public List findListByInfo(Page page, String loginName,String name) {
		StringBuilder sb = new StringBuilder(
				"select a.i_user,a.i_userrole,b.login_name,c.name from t_user_userrole a,t_user b,t_userrole c where a.i_user=b.i_user and a.i_userrole=c.i_userrole ");

		if (StringUtils.isNotBlank(loginName)) {
			sb.append(" and b.login_name like '%" + loginName + "%'");
		}
		
		if (StringUtils.isNotBlank(name)) {
			sb.append(" and c.name like '%" + name + "%'");
		}

		return this.baseDao.getHibernateDaoEx().findBySql(page, sb.toString());

	}

}
