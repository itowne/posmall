package org.ohuyo.rapid.base.dao;

import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.entity.TsysParam;
import org.ohuyo.rapid.base.entity.condition.TsysParamCondition;
import org.springframework.stereotype.Repository;

import com.newland.posmall.bean.dict.SysParamType;


/**
 * 
 * @author rabbit
 *
 */
@Repository
public class TsysParamDao {

	@Resource
	BaseDao baseDao;

	public TsysParam getTsysParam(String type, String code) {
		TsysParam t = new TsysParam();
		t.setSysParamType(SysParamType.OTHER_CONF);
		t.setCode(code);
		return baseDao.getHibernateDaoEx().getFirstOneByExample(t);
	}

	public List<TsysParam> findTsysParam(String type) {
		TsysParam t = new TsysParam();
		t.setSysParamType(SysParamType.OTHER_CONF);
		return baseDao.getHibernateDaoEx().findByExample(t);
	}

	@SuppressWarnings("unchecked")
	public List<TsysParam> find(TsysParamCondition tsysParamConfition, Page page) {
		return baseDao.getHibernateDaoEx().findByCriteriaEx(tsysParamConfition, page);
	}
	
	public List<TsysParam> findTsysParam() {
		TsysParam t = new TsysParam();
		return baseDao.getHibernateDaoEx().findByExample(t);
	}

	//获取session中的对象
	public TsysParam get(Long id) {
		return (TsysParam) baseDao.getSession().get(TsysParam.class, id);
	}
	
	public void update(TsysParam tsysParam) {
		baseDao.getHibernateDaoEx().getSession().update(tsysParam);
	}
}
