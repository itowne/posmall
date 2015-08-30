package com.newland.posmall.base.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.entity.condition.TcustCondition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.newland.posmall.base.dao.TcustDao;
import com.newland.posmall.bean.customer.Tcust;
import com.newland.posmall.bean.customer.TcustReg;

@Service
@Transactional
public class TcustService {
	@Resource
	private TcustDao tcustDao;

	public void add(Tcust tcust) {
		tcust.setGenTime(new Date());
		tcust.setUpdTime(new Date());
		this.tcustDao.save(tcust);
	}
	public void update(Tcust tcust) {
		tcust.setUpdTime(new Date());
		this.tcustDao.update(tcust);
	}
	
	public Tcust getByLoginName(String loginName) {
		return this.tcustDao.getByLoginName(loginName);
	}

	public void modifyTcust(Tcust tcust) {
		tcust.setUpdTime(new Date());
		this.tcustDao.update(tcust);
	}

	public Tcust find(Long id) {
		Tcust tcust = this.tcustDao.get(id);
		TcustReg tcustReg = this.tcustDao.getTcustReg(id);
		tcust.setTcustReg(tcustReg);
		return tcust;
	}
	
	public List<Tcust> queryTcustByLoginName(String loginName){
	    return	tcustDao.findCustByLoginName(loginName);
	}

	/**
	 * 查询 客户列表
	 * 
	 * @return
	 */

	public List<Tcust> findBySelect(Tcust tcust) {
		return this.tcustDao.findSelect(tcust);
	}
	
	public List<Tcust> queryTcust(TcustCondition tcustConfition, Page page) {
		return this.tcustDao.find(tcustConfition, page);
	}
	
	/**
	 * 获取用户注册信息
	 */
	public TcustReg getTcustReg(Long icust) {
		return this.tcustDao.getTcustReg(icust);
	}
	
//	/**
//	 * 
//	 * @param custStatus
//	 * @param creditLevel
//	 * @param name
//	 * @return
//	 */
//	@SuppressWarnings("rawtypes")
//	public List findListByCon(TcustViewCondition condition, Page page) {
//		return this.tcustDao.findCustByCon(condition, page);
//	}
	
	/**
	 * 
	 * @param custStatus
	 * @param creditStatus
	 * @param name
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List findListByInfo(Page page, String custStatus, String creditLevel, String name){
		
		return this.tcustDao.findListByInfo(page, custStatus, creditLevel, name);
	}
	
	public List<Tcust> findListByCondition(Tcust tcust){
		return tcustDao.findSelect(tcust);
	}
	
	public Map<String, String> queryTcustMap(){
		Map<String, String> map = null;
		List<Tcust> custList =  this.tcustDao.find();
		if(custList != null){
			 map = new TreeMap<String, String>();
			for(Tcust t : custList){
				map.put(""+t.getIcust(),t.getLoginName());
			}
		}
		return map;
	}
	
	/**
	 * 客户，注册码
	 * @return
	 */
	public Map<String, String> queryTcustCodeMap(){
		Map<String, String> map = null;
		List<Tcust> custList =  this.tcustDao.find();
		if(custList != null){
			 map = new TreeMap<String, String>();
			for(Tcust t : custList){
				map.put(""+t.getIcust(),t.getCustCode());
			}
		}
		return map;
	}

}
