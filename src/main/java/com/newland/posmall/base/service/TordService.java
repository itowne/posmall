package com.newland.posmall.base.service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.newland.posmall.base.dao.TordDao;
import com.newland.posmall.base.dao.TordHisDao;
import com.newland.posmall.bean.basebusi.Tord;
import com.newland.posmall.bean.basebusi.TordHis;
import com.newland.posmall.bean.basebusi.condition.TordCondition;
import com.newland.posmall.bean.basebusi.condition.TordHisCondition;
import com.newland.posmall.bean.dict.OrdStatus;
import com.newland.posmall.bean.dict.PayStatus;
import com.newland.posmall.identifier.IdentifierService;

@Service
public class TordService {

	@Resource
	private TordDao tordDao;

	@Resource
	private TordHisDao tordHisDao;

	@Resource(name = "identifierService")
	private IdentifierService identifierService;
	
	
	public Map<String, String> queryTordMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();

		List<Tord> list = this.tordDao.find();
		if(!CollectionUtils.isEmpty(list)){
			for (Tord tord : list) {
				map.put(tord.getIord().toString(), tord.getOrdNo());
			}
		}
		return map;
	}
	
	/**
	 * 查询 协议下是否 有 有效的订单
	 * @param iagmt
	 * @return
	 */
	public Boolean queryTordByAgmtId(Long iagmt){
		Tord tord = new Tord();
		tord.setIagmt(iagmt);
		List<Tord> list = tordDao.findSelect(tord);
		if(CollectionUtils.isEmpty(list)){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 审核订单
	 * @param tord
	 * @param ordStatus
	 */
	public void auditTord(Tord tord, PayStatus payStatus){
		tord.setPayStatus(payStatus);
		update(tord);
	}

	public void save(Tord tord) {
		Date date = new Date();
		tord.setGenTime(date);
		tord.setUpdTime(date);
		tord.setOrdNo(identifierService.genOrderId());
		tord.setDelFlag(Boolean.FALSE);
		this.tordDao.save(tord);
	}
	
	public void saveTordHis(TordHis tordHis) {
		Date date = new Date();
		tordHis.setGenTime(date);
		tordHis.setUpdTime(date);
		this.tordHisDao.save(tordHis);
	}
	
	public void updateTord(Tord ord){
		tordDao.update(ord);
	}

	public void update(Tord tord) {

		Date date = new Date();
		tord.setUpdTime(date);
		this.tordDao.update(tord);

		TordHis oh = new TordHis();
		oh.setGenTime(date);
		oh.setAmt(tord.getAmt());
		oh.setIagmt(tord.getIagmt());
		oh.setIord(tord.getIord());
		oh.setIcust(tord.getIcust());
		oh.setOrdStatus(tord.getOrdStatus());
		oh.setPayStatus(tord.getPayStatus());
		oh.setUpdTime(date);
		oh.setLockAmtOfDeposit(tord.getLockAmtOfDeposit());
		oh.setAmtOfDelivery(tord.getAmtOfDelivery());
		this.tordHisDao.save(oh);

	}
	
	/**
	 * 撤销订单
	 * @param tord
	 */
	public void delete(Tord tord) {
//		tord.setUpdTime(new Date());
//		tord.setDelFlag(Boolean.TRUE);
		tord.setOrdStatus(OrdStatus.REVOKED);
		this.update(tord);
	}

	public Tord find(Long id) {
		return this.tordDao.get(id);

	}

	public List<Tord> findBySelect(Tord tord) {
		return this.tordDao.findSelect(tord);
	}

	public List<Tord> findTordByCon(TordCondition tordCondition,Page page) {
		return this.tordDao.findTordByCon(tordCondition, page);
	}
	
	public List<Tord> findListdByCon(TordCondition tordCondition) {
		return this.tordDao.findListByCon(tordCondition);
	}
	
	public List<TordHis> findTordHisByCon(TordHisCondition tordHisCondition) {
		return this.tordHisDao.findByCon(tordHisCondition);
	}
}
