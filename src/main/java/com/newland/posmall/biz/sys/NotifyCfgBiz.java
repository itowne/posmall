package com.newland.posmall.biz.sys;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.Order;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.service.TuserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.newland.posmall.base.service.TnotifyCfgService;
import com.newland.posmall.bean.basebusi.TnotifyCfg;
import com.newland.posmall.bean.basebusi.condition.TnotifyCfgCondition;
import com.newland.posmall.bean.dict.NotifyType;

@Service
@Transactional
public class NotifyCfgBiz {

	@Resource
	private TnotifyCfgService tnotifyCfgService;
	
	@Resource
	private TuserService tuserService;

	public List<TnotifyCfg> queryAllTnotifyCfg(TnotifyCfgCondition tnotifyCfgConfition,Page page) {
		
		tnotifyCfgConfition.addOrders(Order.desc("notifyType"));
		tnotifyCfgConfition.addOrders(Order.desc("updTime"));
		List<TnotifyCfg> tnotifyCfgList = this.tnotifyCfgService.queryTnotifyCfg(tnotifyCfgConfition,page);
		return tnotifyCfgList;
	}
	
	public TnotifyCfg queryTnotifyCfgByInotifyCfg(Long inotifyCfg) {
		return this.tnotifyCfgService.get(inotifyCfg);
	}
	
	public void addTnotifyCfg(TnotifyCfg tnotifyCfg){
		this.tnotifyCfgService.add(tnotifyCfg);
	}
	
	public void modifyTnotifyCfg(TnotifyCfg tnotifyCfg){
		TnotifyCfg tnotifyCfgNew = this.tnotifyCfgService.get(tnotifyCfg.getInotifyCfg());
		
		tnotifyCfgNew.setNotifyType(tnotifyCfg.getNotifyType());
		tnotifyCfgNew.setIuser(tnotifyCfg.getIuser());
		tnotifyCfgNew.setUserName(tnotifyCfg.getUserName());
		tnotifyCfgNew.setMemo(tnotifyCfg.getMemo());
		this.tnotifyCfgService.update(tnotifyCfgNew);
	}
	
	public void removeTnotifyCfg(Long id){
		TnotifyCfg tnotifyCfg = this.tnotifyCfgService.get(id);
		this.tnotifyCfgService.delete(tnotifyCfg);
	}
	
	public String validateNotifyType(NotifyType notifyType, Long id, Long userId){
		TnotifyCfg tnotifyCfg = new TnotifyCfg();
		if (notifyType == null) {
			return "未选择通知类型";
		}
		if (userId == null) {
			return "未输入用户编号";
		}
		
		//判断用户id是否存在
		if(this.tuserService.getTuser(userId) == null) {
			return "用户编号不存在";
		}
		
		tnotifyCfg.setNotifyType(notifyType);
		tnotifyCfg.setIuser(userId);
		tnotifyCfg.setDelFlag(Boolean.FALSE);
		List<TnotifyCfg> cfgs = this.tnotifyCfgService.findByCondition(tnotifyCfg);
		if(CollectionUtils.isEmpty(cfgs)) {
			return null;
		}else if(cfgs.size() == 1) {
			TnotifyCfg tnotifyCfgDB = cfgs.get(0);
			//修改页面没有类型及userId也可以提交
			if(tnotifyCfgDB.getInotifyCfg().equals(id)) {
				return null;
			}
		}
		return "不能重复配置";
	}
	
}
