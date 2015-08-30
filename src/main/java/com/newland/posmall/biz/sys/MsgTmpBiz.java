package com.newland.posmall.biz.sys;

import java.util.List;

import javax.annotation.Resource;
import org.hibernate.criterion.Order;
import org.ohuyo.commons.query.criterion.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newland.posmall.base.service.TmsgTmpService;
import com.newland.posmall.bean.basebusi.condition.TmsgTmpCondition;
import com.newland.posmall.bean.common.TmsgTmp;
import com.newland.posmall.bean.dict.MsgTmpType;

@Service
@Transactional
public class MsgTmpBiz {

	@Resource
	private TmsgTmpService tmsgTmpService;

	public List<TmsgTmp> queryAllTmsgTmp(TmsgTmpCondition tmsgTmpConfition,Page page) {
		
		tmsgTmpConfition.addOrders(Order.desc("updTime"));
		List<TmsgTmp> tmsgTmpList = this.tmsgTmpService.queryTmsgTmp(tmsgTmpConfition,page);
		return tmsgTmpList;
	}
	
	public TmsgTmp queryTmsgTmpByImsgTmp(Long imsgTmp) {
		return this.tmsgTmpService.find(imsgTmp);
	}
	
	public void addTmsgTmp(TmsgTmp tmsgTmp){
		this.tmsgTmpService.save(tmsgTmp);
	}
	
	public void modifyTmsgTmp(TmsgTmp tmsgTmp){
		
		TmsgTmp tmsgTmpNew = tmsgTmpService.find(tmsgTmp.getImsgTmp());
		
		tmsgTmpNew.setContent(tmsgTmp.getContent());
		tmsgTmpNew.setNoteName(tmsgTmp.getNoteName());
		tmsgTmpNew.setTmpCode(tmsgTmp.getTmpCode());
		tmsgTmpNew.setTmpType(tmsgTmp.getTmpType());
		this.tmsgTmpService.update(tmsgTmpNew);
	}
	
	public void removeTmsgTmp(Long id){
		TmsgTmp tmsgTmp = this.tmsgTmpService.find(id);
		this.tmsgTmpService.delete(tmsgTmp);
	}
	
	public Boolean validateMsgTmpUniq(MsgTmpType tmpType, String tmpCode, Long id ){
		Boolean result = true;
		TmsgTmp tmsgTmp = tmsgTmpService.queryByTypeAndCode(tmpType, tmpCode);
		if(id == null){
			if(tmsgTmp != null){
				result = false;
			}
		}else{
			if(tmsgTmp != null ){
				TmsgTmp t =  tmsgTmpService.find(id);
				if(t != null && t.getTmpType() == tmpType && tmpCode.equals(t.getTmpCode())){
					result = true;
				}else{
					result = false;
				}
			}else{
				result = true;
			}
		}
		return result;
	}
	
}
