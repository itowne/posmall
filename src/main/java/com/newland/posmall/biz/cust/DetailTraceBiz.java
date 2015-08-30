package com.newland.posmall.biz.cust;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.entity.condition.TdetailTraceCondition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newland.posmall.base.service.TdetailTraceService;
import com.newland.posmall.bean.common.TdetailTrace;
import com.newland.posmall.bean.customer.Tcust;

@Service
@Transactional
public class DetailTraceBiz {
	
	@Resource
	private TdetailTraceService tdetailTraceService;
	
	private DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 分页查询跟踪数据
	 * @param condition
	 * @param tcust
	 * @param page
	 * @return
	 */
	public List<TdetailTrace> pageByCondition(TdetailTraceCondition condition, Tcust tcust,
			Page page) {
		if(tcust == null) {
			return null;
		}
		condition.setIcust(tcust.getIcust());
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
		return this.tdetailTraceService.queryByCondition(condition, page);
	}
}
