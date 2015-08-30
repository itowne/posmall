package com.newland.posmall.biz.schedule.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.ohuyo.rapid.base.entity.TsysParam;
import org.ohuyo.rapid.base.service.TsysParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.newland.posmall.utils.HttpUtil;
/**
 * 物流同步服务
 * @author rabbit
 *
 */
@Service
public class LogisticSyncService {
	@Autowired
	TsysParamService sysparamService;
	/**
	 * 查询ERP物流信息
	 * @param beginDate 开始日期
	 * @param endDate 结束日期
	 * @param logisticNo 物流单号
	 * @param queryType 查询种类 0-按日期 1-按物流单号
	 * @param type 0-销货单信息 1-物流跟踪信息
	 * @throws Exception 
	 */
	public void queryLogisticInfo(Date beginDate, Date endDate, String logisticNo, String queryType, String type) throws Exception{
		Map<String, TsysParam> map = this.sysparamService.getV().get("OTHER_CONF");
		if (CollectionUtils.isEmpty(map)) throw new RuntimeException("配置不存在");
		TsysParam sp = map.get("ERPURL");
		if (sp == null) throw new RuntimeException("配置不存在");
		String url = sp.getValue();
		if (StringUtils.isBlank(url)) throw new RuntimeException("物流同步url未配置");
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		endDate = DateUtils.round(endDate, Calendar.DATE);
		endDate = DateUtils.addDays(endDate,  1);
		NameValuePair pair = new BasicNameValuePair("beginDate", sdf.format(beginDate));
		list.add(pair);
		pair = new BasicNameValuePair("endDate", sdf.format(endDate));
		list.add(pair);
		pair = new BasicNameValuePair("logisticNo", logisticNo);
		list.add(pair);
		pair = new BasicNameValuePair("queryType", queryType);
		list.add(pair);
		pair = new BasicNameValuePair("type", type);
		list.add(pair);
		pair = new BasicNameValuePair("actionType", "queryXHWLXXB");
		list.add(pair);
		HttpUtil.submitRequestList(url, list, "UTF-8");
	}

}
