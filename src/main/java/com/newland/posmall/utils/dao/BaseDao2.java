package com.newland.posmall.utils.dao;

import java.util.List;

import org.ohuyo.commons.query.criterion.DetachedCriteriaEx;
import org.ohuyo.commons.query.criterion.Page;

public interface BaseDao2<T> {
	
	void save(T entity);
	
	void update(T entity);
	
	void delete(T entity);
	
	void saveOrUpdate(T entity);
	
	List<T> queryBy(DetachedCriteriaEx criteria);
	
	List<T> queryBy(DetachedCriteriaEx criteria, Page page);

	List<T> queryAll(Class<T> clazz);

}
