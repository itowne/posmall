package org.ohuyo.rapid.base.dao;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.ohuyo.commons.query.dao.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * DAO
 * 
 * @author rabbit
 * 
 */
@Repository
public class BaseDao extends HibernateDaoSupport {

	/**
	 * add
	 */
	@Resource(name = "posmall.sessionFactory")
	public void setInjectSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
}