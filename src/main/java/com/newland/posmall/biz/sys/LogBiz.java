package com.newland.posmall.biz.sys;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.Order;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.entity.condition.TlogCondition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.newland.posmall.base.service.TlogService;
import com.newland.posmall.bean.common.Tlog;
@Service
@Transactional
public class LogBiz {

	@Resource
	private TlogService tlogService;
	/**
	 * 
	* @Title: queryAllLog    
	* @Description: 分页查询
	* @param tholidayCondition
	* @param page
	* @return
	* @author chenwenjing    
	* @date 2014-8-25 下午8:53:53
	 */
	public List<Tlog> queryAllLog(TlogCondition tlogCondition,Page page) {
		tlogCondition.addOrders(Order.desc("ilog"));
		return this.tlogService.queryTlog(tlogCondition, page);
	}
	
	/**
	 * 
	* @Title: QueryLogByIlog    
	* @Description: 根据编号查询日志
	* @param id
	* @return
	* @throws Exception
	* @author chenwenjing    
	* @date 2014-8-26 下午3:12:20
	 */
	public Tlog QueryLogByIlog(Long id)throws Exception{
		return this.tlogService.find(id);
	}
	
	
}
