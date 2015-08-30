package com.newland.posmall.biz.admin;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.entity.Tuser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newland.posmall.base.service.TlogService;
import com.newland.posmall.base.service.TlogisticsComService;
import com.newland.posmall.bean.basebusi.TlogisticsCom;
import com.newland.posmall.bean.basebusi.condition.LogisticscompanyCondition;
import com.newland.posmall.bean.common.Tlog;
import com.newland.posmall.bean.dict.LogType;
import com.newland.posmall.bean.dict.OperType;
import com.newland.posmall.bean.dict.YesNoType;

@Service
@Transactional
public class LogisticscompanyBiz {
	
	@Resource
	private TlogisticsComService tlogisticsComService;
	
	@Resource
	private TlogService tlogService;
		
	/**
	 * 查询 物流公司 列表（分页）
	 * @param tpdtConfition
	 * @param page
	 * @return
	 */
	public List<TlogisticsCom> queryAllLogisticsCom(LogisticscompanyCondition logisticscompanyCondition,Page page) {
		return this.tlogisticsComService.queryLogisticscompany(logisticscompanyCondition,page);
	}
	
	public TlogisticsCom queryLogisticsComByIpdt(Long id) {
		if(id == null)
			return null;
		return this.tlogisticsComService.findById(id);
	}
	
	public void addTlogisticsCom(TlogisticsCom tlogisticsCom, Tuser tuser){
		Tlog tlog = new Tlog();
		tlog.setGenTime(new Date());
		tlog.setIfk(tuser.getIuser());
		tlog.setLogType(LogType.USER);
		tlog.setOperType(OperType.LOGISTICS_MGR);
		tlog.setMemo("新增 物流公司");
		StringBuffer sb = new StringBuffer();
		sb.append("物流公司名称：").append(tlogisticsCom.getName()).append(",全称：").append(tlogisticsCom.getFullname())
		.append(",状态").append(tlogisticsCom.getLogisticsStatus()).append(",时效：").append(tlogisticsCom.getAging())
		.append(",是否收费").append(tlogisticsCom.getFeeFlag());
		tlog.setData(sb.toString());
		tlog.setPreData("");
		
		if(tlogisticsCom.getPrice() == null || tlogisticsCom.getPrice().compareTo(BigDecimal.ZERO) == 0){
			tlogisticsCom.setFeeFlag(YesNoType.NO);
		}else{
			tlogisticsCom.setFeeFlag(YesNoType.YES);
		}
		this.tlogisticsComService.add(tlogisticsCom);
		tlogService.save(tlog);
	}
	
	public void modifyTlogisticsComByIlogisticsCom(TlogisticsCom tlogisticsCom, Tuser tuser) {	
		
		TlogisticsCom tlogisticsComNew = this.tlogisticsComService.findById(tlogisticsCom.getIlogisticsCom());
		
		Tlog tlog = new Tlog();
		tlog.setGenTime(new Date());
		tlog.setIfk(tuser.getIuser());
		tlog.setLogType(LogType.USER);
		tlog.setOperType(OperType.LOGISTICS_MGR);
		tlog.setMemo("修改 物流公司id:"+tlogisticsComNew.getIlogisticsCom());
		StringBuffer sb = new StringBuffer();
		sb.append("物流公司名称：").append(tlogisticsComNew.getName()).append(",全称：").append(tlogisticsComNew.getFullname())
		.append(",状态").append(tlogisticsComNew.getLogisticsStatus()).append(",时效：").append(tlogisticsComNew.getAging())
		.append(",是否收费").append(tlogisticsComNew.getFeeFlag());
		tlog.setPreData(sb.toString());
	
		
		tlogisticsComNew.setName(tlogisticsCom.getName());
		tlogisticsComNew.setFullname(tlogisticsCom.getFullname());
		tlogisticsComNew.setAging(tlogisticsCom.getAging());
		tlogisticsComNew.setPrice(tlogisticsCom.getPrice());
		tlogisticsComNew.setLogisticsStatus(tlogisticsCom.getLogisticsStatus());
		tlogisticsComNew.setGenTime(tlogisticsCom.getGenTime());
		if(tlogisticsComNew.getPrice() == null || tlogisticsComNew.getPrice().compareTo(BigDecimal.ZERO) == 0){
			tlogisticsComNew.setFeeFlag(YesNoType.NO);
		}else{
			tlogisticsComNew.setFeeFlag(YesNoType.YES);
		}
		
		StringBuffer sb2 = new StringBuffer();
		sb2.append("修改后 物流公司名称：").append(tlogisticsComNew.getName()).append(",全称：").append(tlogisticsComNew.getFullname())
		.append(",状态").append(tlogisticsComNew.getLogisticsStatus()).append(",时效：").append(tlogisticsComNew.getAging())
		.append(",是否收费").append(tlogisticsComNew.getFeeFlag());
		tlog.setData(sb2.toString());
		
		this.tlogisticsComService.modify(tlogisticsComNew);
		tlogService.save(tlog);
	}
	

}
