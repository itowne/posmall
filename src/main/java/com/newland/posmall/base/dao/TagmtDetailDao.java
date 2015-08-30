package com.newland.posmall.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.stereotype.Repository;
import com.newland.posmall.bean.basebusi.TagmtDetail;

@Repository
public class TagmtDetailDao {
	
	@Resource
	private BaseDao baseDao;
	
	public List<TagmtDetail> find(TagmtDetail tagmtDetail){
		return baseDao.getHibernateDaoEx().findByExample(tagmtDetail);
	}
	
	public TagmtDetail get(Long id){
		return (TagmtDetail) baseDao.getSession().get(TagmtDetail.class, id);
	}
	
	public void save(TagmtDetail tagmtDetail){
		baseDao.getSession().save(tagmtDetail);
	}
	
	public void update(TagmtDetail tagmtDetail){
		baseDao.getSession().update(tagmtDetail);
	}
	
	/**
	 * 当协议下有效 的协议明细
	 * @return
	 */
	public List<TagmtDetail> findByIagmt(Long id){
		TagmtDetail td = new TagmtDetail();
		td.setDelFlag(Boolean.FALSE);
		td.setIagmt(id);
		return baseDao.getHibernateDaoEx().findByExample(td);
		
	}


}
