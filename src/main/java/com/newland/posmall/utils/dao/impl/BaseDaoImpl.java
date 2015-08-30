package com.newland.posmall.utils.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.ohuyo.commons.query.criterion.DetachedCriteriaEx;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.commons.query.dao.HibernateDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import com.newland.posmall.utils.dao.BaseDao2;

public abstract class BaseDaoImpl<T>  extends HibernateDaoSupport implements BaseDao2<T>{

	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	@Override
	public void save(T entity){
		this.getSession().save(entity);
	}
	@Override
	public void update(T entity){
		this.getSession().update(entity);
	}
	@Override
	public void delete(T entity){
		this.getSession().delete(entity);
	}
	@Override
	public void saveOrUpdate(T entity){
		this.getSession().saveOrUpdate(entity);
	}
	@Override
	public List<T> queryAll(Class<T> clazz){
		return this.getHibernateDaoEx().find(clazz);
	}

	@SuppressWarnings("unchecked")
	public List<T> queryBy(DetachedCriteriaEx criteria){
		return this.getHibernateDaoEx().findByCriteriaEx(criteria);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> queryBy(DetachedCriteriaEx criteria, Page page){
		return this.getHibernateDaoEx().findByCriteriaEx(criteria, page);
	}

	
}
