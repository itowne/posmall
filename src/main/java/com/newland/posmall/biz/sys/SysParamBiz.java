package com.newland.posmall.biz.sys;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.Order;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.TsysSession;
import org.ohuyo.rapid.base.entity.TsysParam;
import org.ohuyo.rapid.base.entity.condition.TsysParamCondition;
import org.ohuyo.rapid.base.service.TsysParamService;
import org.ohuyo.rapid.base.servlet.AppSessionFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newland.posmall.base.service.TlogService;
import com.newland.posmall.bean.common.Tlog;
import com.newland.posmall.bean.dict.LogType;
import com.newland.posmall.bean.dict.OperType;

@Service
@Transactional
public class SysParamBiz {

	@Resource
	private TsysParamService tsysParamService;

	@Resource
	private TlogService tlogService;
	
	public List<TsysParam> queryAllTsysParam(TsysParamCondition tsysParamConfition,Page page) {
		
		tsysParamConfition.addOrders(Order.desc("isysParam"));
		List<TsysParam> tsysParamList = this.tsysParamService.queryTsysParam(tsysParamConfition,page);
		return tsysParamList;
	}
	
	public TsysParam queryTsysParamByIsysParam(Long isysParam) {
		return this.tsysParamService.get(isysParam);
	}
	
	public void modifyTsysParamByIsysParam(TsysParam tsysParam) {
		TsysParam oldTsysParam = this.tsysParamService.get(tsysParam.getIsysParam());
		
		TsysSession tsysSession = (TsysSession) AppSessionFilter.getAppSession();
		Tlog tlog = new Tlog();
		tlog.setIfk(tsysSession.getTsys().getIsys());
		tlog.setLogType(LogType.SYS);
		tlog.setOperType(OperType.SYS_MGR);
		tlog.setMemo("修改参数数值");
		StringBuffer sbPreDate = new StringBuffer();
		sbPreDate.append("参数名是");
		sbPreDate.append(oldTsysParam.getName());
		sbPreDate.append(",编码是");
		sbPreDate.append(oldTsysParam.getCode());
		sbPreDate.append(",值是");
		sbPreDate.append(oldTsysParam.getValue());
		sbPreDate.append(",有效起始时间是");
		sbPreDate.append(oldTsysParam.getStartTime());
		sbPreDate.append(",有效结束时间是");
		sbPreDate.append(oldTsysParam.getEndTime());
		
		tlog.setPreData(sbPreDate.toString());
		StringBuffer sbDate = new StringBuffer();
		sbDate.append("参数名是");
		sbDate.append(tsysParam.getName());
		sbDate.append(",编码是");
		sbDate.append(tsysParam.getCode());
		sbDate.append(",值是");
		sbDate.append(tsysParam.getValue());
		sbDate.append(",有效起始时间是");
		sbDate.append(tsysParam.getStartTime());
		sbDate.append(",有效结束时间是");
		sbDate.append(tsysParam.getEndTime());
		
		tlog.setData(sbDate.toString());
		this.tlogService.save(tlog);
		
		oldTsysParam.setValue(tsysParam.getValue());
		oldTsysParam.setStartTime(tsysParam.getStartTime());
		oldTsysParam.setEndTime(tsysParam.getEndTime());
		this.tsysParamService.update(oldTsysParam);
	}
	
	/**
	 * 将t_sys_param表中的end_time、start_time转化成java.util.Date类型，以便比较；如果end_time < start_time，则表示跨天
	 * @param tsysParam
	 */
	public void transStartAndEndTime(TsysParam tsysParam) {
		this.tsysParamService.transStartAndEndTime(tsysParam);
	}
	
}
