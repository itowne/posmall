package com.newland.posmall.base.service;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.springframework.stereotype.Service;
import com.newland.posmall.base.dao.TmsgTmpDao;
import com.newland.posmall.bean.basebusi.condition.TmsgTmpCondition;
import com.newland.posmall.bean.common.TmsgTmp;
import com.newland.posmall.bean.dict.MsgTmpType;


@Service
public class TmsgTmpService {
	
	@Resource
	private TmsgTmpDao tmsgTmpDao;
	
	
	public List<TmsgTmp> queryTmsgTmp(TmsgTmpCondition tmsgTmpConfition,Page page) {
		return this.tmsgTmpDao.queryTmsgTmp(tmsgTmpConfition, page);
	}
	
	public TmsgTmp queryByTypeAndCode(MsgTmpType tmpType, String tmpCode){
		TmsgTmp tt = new TmsgTmp();
		tt.setDelFlg(Boolean.FALSE);
		tt.setTmpType(tmpType);
		tt.setTmpCode(tmpCode);
		List<TmsgTmp> list = this.tmsgTmpDao.findSelect(tt);
		return (list != null && list.size() > 0)?list.get(0):null;
	}
	
	public void save(TmsgTmp tmsgTmp){
		Date date = new Date();
		tmsgTmp.setGenTime(date);
		tmsgTmp.setUpdTime(date);
		tmsgTmp.setDelFlg(Boolean.FALSE);
		this.tmsgTmpDao.save(tmsgTmp);
				
	}
	
	public void update(TmsgTmp tmsgTmp){
		tmsgTmp.setUpdTime(new Date());
		this.tmsgTmpDao.update(tmsgTmp);		
	}
	
	public void delete(TmsgTmp tmsgTmp){
		tmsgTmp.setUpdTime(new Date());
		tmsgTmp.setDelFlg(Boolean.TRUE);
		this.tmsgTmpDao.update(tmsgTmp);
	}
	
	public TmsgTmp find(Long id){
		return this.tmsgTmpDao.get(id);
		
	}
}
