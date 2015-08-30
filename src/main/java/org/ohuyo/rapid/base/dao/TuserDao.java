package org.ohuyo.rapid.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.entity.Tuser;
import org.ohuyo.rapid.base.entity.condition.TuserCondition;
import org.springframework.stereotype.Repository;

import com.newland.posmall.bean.backmanage.TuserSub;

/**
 * 
 * @author rabbit
 *
 */
@Repository
public class TuserDao {

	@Resource
	private BaseDao baseDao;
	
	public List<Tuser> find() {
		return baseDao.getHibernateDaoEx().find(Tuser.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Tuser> findAll(TuserCondition tuserConfition){
		return baseDao.getHibernateDaoEx().findByCriteriaEx(tuserConfition);
	}

	public void save(Tuser tuser) {
		baseDao.getSession().save(tuser);
	}

	public Tuser getByLoginName(String loginName) {
		Tuser t = new Tuser();
		t.setLoginName(loginName);
		return baseDao.getHibernateDaoEx().getFirstOneByExample(t);
	}

	@SuppressWarnings("unchecked")
	public List<Tuser> find(TuserCondition tuserConfition,Page page) {
		return baseDao.getHibernateDaoEx().findByCriteriaEx(tuserConfition, page);
	}
	
	public Tuser get(Long id){
		return (Tuser) baseDao.getSession().get(Tuser.class, id);
	}
	
	public void update(Tuser tuser){
		baseDao.getSession().update(tuser);
	}

	public void saveTuserSub(TuserSub tuserSub) {
		baseDao.getSession().save(tuserSub);
	}
	
	public void updateTuserSub(TuserSub tuserSub){
		baseDao.getSession().update(tuserSub);
	}
	
	public TuserSub getTuserSub(Long id){
		return (TuserSub) baseDao.getSession().get(TuserSub.class, id);
	}
	
	/**
	 * 
	* @Description: 根据对象条件查找对象信息（主键除外）
	* @author chenwenjing    
	* @date 2014-8-29 上午9:48:45
	 */
	public List<Tuser> findSelect(Tuser tuser) {
		return baseDao.getHibernateDaoEx().findByExample(tuser);
	}
}
