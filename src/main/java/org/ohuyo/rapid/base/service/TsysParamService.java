package org.ohuyo.rapid.base.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.dao.TsysParamDao;
import org.ohuyo.rapid.base.entity.TsysParam;
import org.ohuyo.rapid.base.entity.condition.TsysParamCondition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 系统参数服务
 * 
 * @author rabbit
 *
 */
@Service
@Transactional
public class TsysParamService {
	@Resource
	TsysParamDao tsysParamDao;

	private boolean dirty = true;

	private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

	private ReadLock rl = rwl.readLock();

	private WriteLock wl = rwl.writeLock();

	private Map<String, Map<String, TsysParam>> tsysParamMap;
	
	public Map<String, Map<String, TsysParam>> getV() {
		rl.lock();
		try {
//			if (!dirty) {
//				return tsysParamMap;
//			}
		} finally {
			rl.unlock();
		}
		wl.lock();
		try {
//			if (!dirty) {
//				return tsysParamMap;
//			}
			tsysParamMap = getV2();
			dirty = false;
			return tsysParamMap;
		} finally {
			wl.unlock();
		}
	}

	private Map<String, Map<String, TsysParam>> getV2() {
		List<TsysParam> list = tsysParamDao.findTsysParam();
		Map<String, Map<String, TsysParam>> map = new HashMap<String, Map<String, TsysParam>>();
		for (TsysParam tsysParam : list) {
			String type = tsysParam.getSysParamType().toString();
			Map<String, TsysParam> m = map.get(type);
			if (m == null) {
				m = new HashMap<String, TsysParam>();
				map.put(type, m);
			}
			String code = tsysParam.getCode();
			m.put(code, tsysParam);
		}
		return map;
	}

	public TsysParam getTsysParam(String type, String code) {
		Map<String, Map<String, TsysParam>> map = getV();
		Map<String, TsysParam> m = map.get(type);
		return m.get(code);
	}

	Map<String, String> getValues(String type) {
		return null;
	}

	
	public List<TsysParam> queryTsysParam(TsysParamCondition tsysParamConfition, Page page) {
		return this.tsysParamDao.find(tsysParamConfition, page);
	}
	
	public void update(TsysParam tsysParam) {
		Date date = new Date();
		tsysParam.setUpdTime(date);
		this.tsysParamDao.update(tsysParam);
	}
	
	public TsysParam get(Long id) {
		return this.tsysParamDao.get(id);
	}
	
	/**
	 * 将t_sys_param表中的end_time、start_time转化成java.util.Date类型，以便比较；如果end_time < start_time，则表示跨天
	 * @param tsysParam
	 */
	public void transStartAndEndTime(TsysParam tsysParam) {
		if(tsysParam == null) {
			return;
		}
		Calendar now = Calendar.getInstance();
		String[] startTimeStr = StringUtils.split(tsysParam.getStartTime(), ":");
		String[] endTimeStr = StringUtils.split(tsysParam.getEndTime(), ":");
		if(startTimeStr == null || startTimeStr.length < 1) {
			tsysParam.setStartTimeDate(null);
		}else {
			now.set(Calendar.HOUR_OF_DAY, Integer.valueOf(startTimeStr[0]).intValue());
			now.set(Calendar.MINUTE, Integer.valueOf(startTimeStr[1]).intValue());
			tsysParam.setStartTimeDate(DateUtils.truncate(now.getTime(), Calendar.MINUTE));
		}
		if(endTimeStr == null || endTimeStr.length < 1) {
			tsysParam.setEndTimeDate(null);
		}else {
			now.set(Calendar.HOUR_OF_DAY, Integer.valueOf(endTimeStr[0]).intValue());
			now.set(Calendar.MINUTE, Integer.valueOf(endTimeStr[1]).intValue());
			tsysParam.setEndTimeDate(DateUtils.truncate(now.getTime(), Calendar.MINUTE));
		}
		
		if(tsysParam.getStartTimeDate() != null && tsysParam.getEndTimeDate() != null) {
			if(tsysParam.getEndTimeDate().before(tsysParam.getStartTimeDate())) {//如果t_sys_param表中end_time < start_time，则表示跨天
				Date newDate = DateUtils.addDays(tsysParam.getEndTimeDate(), 1);
				tsysParam.setEndTimeDate(newDate);
			}
		}
	}
	
}
