package com.newland.posmall.base.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import org.ohuyo.commons.query.criterion.Page;
import org.springframework.stereotype.Service;
import com.newland.posmall.base.dao.TcustAppverDao;
import com.newland.posmall.bean.customer.TcustAppver;
import com.newland.posmall.bean.customer.condition.TcustAppverCondition;

/**
 * 
* @ClassName: TcustAppverService 
* @Description: 客户应用版本管理service 
* @author chenwenjing
* @date 2013-11-5 上午10:37:43
 */
@Service
public class TcustAppverService {
	
	@Resource
	private TcustAppverDao tcustAppverDao;
	
	/**
	 * 
	 * @Description: 列出此表所有数据
	 * @author chenwenjing
	 * @date 2013-11-5上午10:39:28
	 */
	public List<TcustAppver> findAll(){
		return this.tcustAppverDao.findAll();
	}
	/**
	 * 
	 * @Description: TODO根据主键查找对象
	 * @author chenwenjing
	 * @date 2013-11-5上午10:40:22
	 */
	
	public TcustAppver get(Long id){
		return this.tcustAppverDao.get(id);
	}
	/**
	 * 
	 * @Description: TODO保存对象
	 * @author chenwenjing
	 * @date 2013-11-5上午10:42:48
	 */
	
	public void save(TcustAppver tcustAppver){
		Date now = new Date();
		tcustAppver.setGenTime(now);
		tcustAppver.setUpdTime(now);
		tcustAppver.setDelFlag(Boolean.FALSE);
		this.tcustAppverDao.save(tcustAppver);
	}
	/**
	 * 
	 * @Description: TODO更新对象
	 * @author chenwenjing
	 * @date 2013-11-5上午10:50:06
	 */
	public void update(TcustAppver tcustAppver){
		tcustAppver.setUpdTime(new Date());
		this.tcustAppverDao.update(tcustAppver);
	}
	/**
	 * 
	 * @Description: TODO删除对象
	 * @author chenwenjing
	 * @date 2013-11-5上午10:50:18
	 */
	public void delete(TcustAppver tcustAppver) {
		tcustAppver.setDelFlag(Boolean.TRUE);
		tcustAppver.setUpdTime(new Date());
		this.tcustAppverDao.update(tcustAppver);
	}
	/**
	 * 
	 * @Description: TODO根据对象属性查找列表
	 * @author chenwenjing
	 * @date 2013-11-5上午10:50:53
	 */
	public List<TcustAppver> findListByCondition(TcustAppver tcustAppver){
		tcustAppver.setDelFlag(Boolean.FALSE);
		return this.tcustAppverDao.findListByCondition(tcustAppver);
	}
	/**
	 * 
	 * @Description: TODO根据对象属性查找对象
	 * @author chenwenjing
	 * @date 2013-11-5上午10:53:12
	 */
	public TcustAppver findObjectByCondition(TcustAppver t) {
		t.setDelFlag(Boolean.FALSE);
		return this.tcustAppverDao.findObjectByCondition(t);
	}
	/**
	 * 
	 * @Description: TODO根据条件分页查询列表
	 * @author chenwenjing
	 * @date 2013-11-5上午10:54:09
	 */
	public List<TcustAppver> find(TcustAppverCondition condition,Page page){
		condition.setDelFlag(Boolean.FALSE);
		return this.tcustAppverDao.find(condition, page);
	}
	/**
	 * 
	 * @Description: TODO根据条件查询列表
	 * @author chenwenjing
	 * @date 2013-11-5上午10:54:56
	 */
	public List<TcustAppver> find(TcustAppverCondition condition){
		condition.setDelFlag(Boolean.FALSE);
		return this.tcustAppverDao.find(condition);
	}
	
}
