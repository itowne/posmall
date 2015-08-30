package com.newland.posmall.base.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.springframework.stereotype.Service;
import com.newland.posmall.base.dao.TpdtPlanMonthDao;
import com.newland.posmall.bean.basebusi.TpdtPlanMonth;
import com.newland.posmall.bean.basebusi.condition.TpdtPlanMonthCondition;
import com.newland.posmall.identifier.IdentifierService;

@Service
public class TpdtPlanMonthService {
	
	
	@Resource 
	private TpdtPlanMonthDao tpdtPlanMonthDao;
	
	
	@Resource(name = "identifierService")
	private IdentifierService identifierService;
	
	
	/**
	 * 新增月排产计划
	 * @param tpm
	 * @param tpds
	 */
	public void save(TpdtPlanMonth tpm) {
		
		Date date = new Date();
		tpm.setGenTime(date);
		tpm.setUpdTime(date);
		tpm.setDelFlag(Boolean.FALSE);
		tpm.setPdtPlanMonthNo(identifierService.genMonthId());
		
		tpdtPlanMonthDao.save(tpm);
		
	}
	
	/**
	 * 
	 * @param 
	 */
	public void update(TpdtPlanMonth tpm){
		tpm.setUpdTime(new Date());
		tpdtPlanMonthDao.update(tpm);
		
	}
	
	public void delete(TpdtPlanMonth tpm){
		tpm.setUpdTime(new Date());
		tpm.setDelFlag(Boolean.TRUE);
		this.tpdtPlanMonthDao.update(tpm);
	}
	
	public TpdtPlanMonth find(Long id){
		return this.tpdtPlanMonthDao.get(id);
		
	}
	
	
	public List<TpdtPlanMonth> findBySelect(TpdtPlanMonth tpm){
		return this.tpdtPlanMonthDao.findSelect(tpm);
	}
	/**
	 *  查询分页
	 * @param tpdtPlanMonthCondition
	 * @param page
	 * @return
	 */
	public List<TpdtPlanMonth> queryTpdtPlanMonth(TpdtPlanMonthCondition tpdtPlanMonthCondition,Page page){
		return this.tpdtPlanMonthDao.findCondition(tpdtPlanMonthCondition,page);
	}
	
	
	
	

}
