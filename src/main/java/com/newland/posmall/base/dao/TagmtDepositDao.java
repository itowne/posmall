package com.newland.posmall.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.newland.posmall.bean.basebusi.TagmtDeposit;

@Repository
public class TagmtDepositDao {
	
	@Resource
	private BaseDao baseDao;
	
	public List<TagmtDeposit> find(){
		return baseDao.getHibernateDaoEx().find(TagmtDeposit.class);
	}
	
	public TagmtDeposit findByiagmt(Long iagmt){
		TagmtDeposit t = new TagmtDeposit();
		t.setIagmt(iagmt);
		return (TagmtDeposit) baseDao.getHibernateDaoEx().getFirstOneByExample(t);
	}
	
	public TagmtDeposit get(Long id){
		return (TagmtDeposit) baseDao.getSession().get(TagmtDeposit.class, id);
	}
	
	public void save(TagmtDeposit tagmtDeposit){
		baseDao.getSession().save(tagmtDeposit);
	}
	
	public void update(TagmtDeposit tagmtDeposit){
		baseDao.getSession().update(tagmtDeposit);
	}

}
