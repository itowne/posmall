package com.newland.posmall.biz.admin;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Order;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.entity.condition.TdetailTraceCondition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newland.posmall.base.service.TcustRegService;
import com.newland.posmall.base.service.TdetailTraceService;
import com.newland.posmall.bean.common.TdetailTrace;

@Service
@Transactional
public class AdminDetailTraceBiz {
	
	@Resource
	private TdetailTraceService tdetailTraceService;
	
	@Resource
	private TcustRegService tcustRegService;
	
	private DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 分页查询跟踪数据
	 */
	public List<TdetailTrace> pageByCondition(TdetailTraceCondition condition, 
			Page page) {
		if(StringUtils.isNotBlank(condition.getStartTimeStr())) {
			try {
				condition.setStartTime(dFormat.parse(condition.getStartTimeStr()));
			} catch (ParseException e) {
			}
		}
		if(StringUtils.isNotBlank(condition.getEndTimeStr())) {
			try {
				condition.setEndTime(dFormat.parse(condition.getEndTimeStr()));
			} catch (ParseException e) {
			}
		}
		condition.addOrders(Order.desc("idetailTrace"));
		return this.tdetailTraceService.queryByCondition(condition, page);
	}
}
