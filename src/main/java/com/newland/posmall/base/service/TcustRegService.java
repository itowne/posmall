package com.newland.posmall.base.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.newland.posmall.base.dao.TcustRegDao;
import com.newland.posmall.bean.basebusi.condition.TcustRegCondition;
import com.newland.posmall.bean.customer.TcustReg;
import com.newland.posmall.identifier.IdentifierService;

/**
 * 客户注册
 * @author zhouym
 *
 */
@Service
@Transactional
public class TcustRegService {

	@Resource
	private TcustRegDao tcustRegDao;
	
	@Resource(name = "identifierService")
	private IdentifierService identifierService;
	
	public TcustReg find(Long id){
		return this.tcustRegDao.get(id);
	}
	
	/**
	 *  iCustList(icust1,icust2,)
	 * @param tc
	 * @return
	 */
	public String queryIcustsByName(TcustRegCondition tc){
		List<TcustReg> list = this.tcustRegDao.findByNameCond(tc);
		if(!CollectionUtils.isEmpty(list)){
			StringBuffer sb = new StringBuffer(); 
			for(TcustReg t : list){
				sb.append(t.getIcust()).append(",");
			}
			return sb.substring(0, sb.length()-1);
		}else{
			return null;
		}
		
	}
	
	/**
	 * 相似name 的客户id
	 * @param tc
	 * @return
	 */
	public List<Long> queryIcustListByName(TcustRegCondition tc){
		List<TcustReg> list = this.tcustRegDao.findByNameCond(tc);
		List<Long> icustList = new ArrayList<Long>();
		if(!CollectionUtils.isEmpty(list)){
			for(TcustReg t : list){
				icustList.add(t.getIcust());
			}
			return icustList;
		}else{
			return null;
		}
	}
	
	public List<TcustReg> findByCondition(TcustReg tcustReg){
		return this.tcustRegDao.findByCondition(tcustReg);
	}

	public void add(TcustReg tcustReg) {
		Date date = new Date();
		tcustReg.setCustNo(this.identifierService.genCustId());
		tcustReg.setGenTime(date);
		tcustReg.setUpdTime(date);
		this.tcustRegDao.save(tcustReg);
	}
	
	public void modify(TcustReg tcustReg) {
		tcustReg.setUpdTime(new Date());
		this.tcustRegDao.update(tcustReg);
	}
	
	public Map<String, String> queryTcustRegMap(){
		Map<String, String> map = null;
		List<TcustReg> custRegList =  this.tcustRegDao.find();
		if(custRegList != null){
			 map = new TreeMap<String, String>();
			for(TcustReg t : custRegList){
				map.put(""+t.getIcust(),t.getName());
			}
		}
		return map;
	}
	
	/**
	 * 记录拒绝理由
	 * @param tcustReg
	 * @param refuseReason
	 */
	public void saveRefuseReason(TcustReg tcustReg, String refuseReason) {
		tcustReg.setUpdTime(new Date());
		tcustReg.setRefuseReason(refuseReason);
		this.tcustRegDao.update(tcustReg);
	}

	public TcustReg findByIcust(Long icust) {
		return this.tcustRegDao.get(icust);
	}
	public List<TcustReg> find() {
		return this.tcustRegDao.find();
	}
}
