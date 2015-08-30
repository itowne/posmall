package com.newland.posmall.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.stereotype.Repository;
import com.newland.posmall.bean.basebusi.TagmtDetailHis;

@Repository
public class TagmtDetailHisDao {
	
	@Resource
	private BaseDao baseDao;
	
	public List<TagmtDetailHis> find(TagmtDetailHis tagmtDetailHis){
		return baseDao.getHibernateDaoEx().findByExample(tagmtDetailHis);
	}
	
	public TagmtDetailHis get(Long id){
		return (TagmtDetailHis) baseDao.getSession().get(TagmtDetailHis.class, id);
	}
	
	public void save(TagmtDetailHis tagmtDetailHis){
		baseDao.getSession().save(tagmtDetailHis);
	}
	
	public void update(TagmtDetailHis tagmtDetailHis){
		baseDao.getSession().update(tagmtDetailHis);
	}

}
