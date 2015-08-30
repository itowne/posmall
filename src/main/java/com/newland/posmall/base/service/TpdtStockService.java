package com.newland.posmall.base.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.ohuyo.rapid.base.entity.Tuser;
import org.springframework.stereotype.Service;

import com.newland.posmall.base.dao.TpdtStockDao;
import com.newland.posmall.base.dao.TpdtStockHisDao;
import com.newland.posmall.bean.basebusi.TpdtStock;
import com.newland.posmall.bean.basebusi.TpdtStockHis;

@Service
public class TpdtStockService {
	@Resource
	private TpdtStockDao tpdtStockDao;
	
	@Resource
	private TpdtStockHisDao tpdtStockHisDao;
	
	public TpdtStock findById(Long id) {
		if(id == null) {
			return null;
		}
		return this.tpdtStockDao.get(id);
	}
	
	public TpdtStock findByExample(TpdtStock tpdtStock) {
		if(tpdtStock == null)
			return null;
		return this.tpdtStockDao.findByExample(tpdtStock);
	}
	
	public void add(TpdtStock tpdStock){
		Date date = new Date();
		tpdStock.setGenTime(date);
		tpdStock.setUpdTime(date);
		this.tpdtStockDao.save(tpdStock);
	}

	/**
	 * 修改产品库存
	 * @param tpdtStock
	 * @param tuser
	 */
	public void modify(TpdtStock tpdtStock, Tuser tuser){
		Date date = new Date();
		TpdtStock tpdStockDB = this.tpdtStockDao.get(tpdtStock.getIpdt());
		
		TpdtStockHis tpdtStockHis = new TpdtStockHis();
		tpdtStockHis.setIpdt(tpdStockDB.getIpdt());
		tpdtStockHis.setNum(tpdStockDB.getNum());
		tpdtStockHis.setDay(DateUtils.truncate(date, Calendar.DATE)); //历史表存放的是上一个时间点的产品库存
		tpdtStockHis.setGenTime(date);
		tpdtStockHis.setVer(tpdStockDB.getVersion());
		if(tuser != null) {
			tpdtStockHis.setIuser(tuser.getIuser());
			tpdtStockHis.setUserName(tuser.getLoginName());
		}
		this.tpdtStockHisDao.save(tpdtStockHis);
		
		tpdStockDB.setNum(tpdtStock.getNum());
		tpdStockDB.setUpdTime(date);
		this.tpdtStockDao.update(tpdStockDB);
	}
	
	/**
	 * 查询 产品库存历史列表
	 * @return
	 */
	
	public List<TpdtStockHis> findBySelect(TpdtStockHis tpdtStockHis){
		return this.tpdtStockHisDao.findSelect(tpdtStockHis);
	}

}
