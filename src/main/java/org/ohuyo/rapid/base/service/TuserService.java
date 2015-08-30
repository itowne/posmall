package org.ohuyo.rapid.base.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.dao.TuserDao;
import org.ohuyo.rapid.base.entity.Tuser;
import org.ohuyo.rapid.base.entity.condition.TuserCondition;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newland.posmall.bean.backmanage.TuserSub;

@Service
@Transactional
public class TuserService {

	@Resource
	private TuserDao tuserDao;

	public Tuser loadUserByLoginName(String loginname)
			throws UsernameNotFoundException {
		return this.tuserDao.getByLoginName(loginname);
	}

	public List<Tuser> queryTuser(TuserCondition tuserConfition, Page page) {
		return this.tuserDao.find(tuserConfition, page);
	}

	public void saveTuser(Tuser tuser) {
		Date now = new Date();
		tuser.setDelFlag(Boolean.FALSE);
		tuser.setGenTime(now);
		tuser.setUpdTime(now);
		this.tuserDao.save(tuser);
	}

	public void saveTuserSub(TuserSub tuserSub) {
		Date now = new Date();
		tuserSub.setGenTime(now);
		tuserSub.setUpdTime(now);
		tuserSub.setLastLogin(now);
		this.tuserDao.saveTuserSub(tuserSub);
	}

	public void updateTuser(Tuser tuser) {
		tuser.setUpdTime(new Date());
		this.tuserDao.update(tuser);
	}

	public void updateTuserSub(TuserSub tuserSub) {
		tuserSub.setUpdTime(new Date());
		this.tuserDao.updateTuserSub(tuserSub);
	}

	public void delete(Tuser tuser) {
		tuser.setUpdTime(new Date());
		tuser.setDelFlag(Boolean.TRUE);
		this.tuserDao.update(tuser);
	}

	public Tuser getTuser(Long iuser) {
		Tuser tuser = this.tuserDao.get(iuser);
		TuserSub tuserSub = this.tuserDao.getTuserSub(iuser);
		if(tuser != null && tuserSub != null) {
			tuser.setTuserSub(tuserSub);			
		}
		return tuser;
	}

	public TuserSub getTuserSub(Long iuser) {
		return this.tuserDao.getTuserSub(iuser);
	}
	
	public List<Tuser> findSelect(Tuser tuser){
		return this.tuserDao.findSelect(tuser);
    }
	public Map<String, String> queryTuserMap(){
		Map<String, String> map = null;
		List<Tuser> userList =  this.tuserDao.find();
		if(userList != null){
			 map = new TreeMap<String, String>();
			for(Tuser t : userList){
				map.put(""+t.getIuser(),t.getLoginName());
			}
		}
		return map;
	}
}
