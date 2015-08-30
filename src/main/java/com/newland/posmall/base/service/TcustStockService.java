package com.newland.posmall.base.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.newland.posmall.base.dao.TcustStockDao;
import com.newland.posmall.bean.basebusi.TlogisticsDetail;
import com.newland.posmall.bean.basebusi.TordDetail;
import com.newland.posmall.bean.basebusi.TordDetailPdt;
import com.newland.posmall.bean.customer.TcustStock;
import com.newland.posmall.bean.customer.condition.TcustStockCondition;

/**
 * 客户库存
 * 
 * 例如：			 库存订单(付款前)  库存订单(付款后)  库存订单  订单总量  库存现货   出货   退单 
 * 初始-交易总量	1000			0			1000 1000   0     0    0
 * 付款-交易总量	0 				1000 		1000 1000   0     0    0 
 * 生产-交易总量 	0 				1000 		1000 1000   1000  0    0 
 * 出库-交易总量 	0 				0			0 	 1000   0    1000  0
 * 
 * 初始-交易总量 	1000 			0 			1000 1000	0 	  0    0 
 * 撤单-交易总量 	0 				0			0 	 1000	0	  0	  1000
 * 
 * 初始-交易总量	1000			0			1000 1000	0	  0    0 
 * 部分付款-交易总量 	500				500			1000 1000	0	  0	   0
 * 
 */
@Service
public class TcustStockService {

	@Resource
	private TcustStockDao tcustStockDao;

	@Resource
	private TcustStockDetailService tcustStockDetailService;

	@Resource
	private TordDetailService tordDetailService;
	
	@Resource
	private TordDetailPdtService tordDetailPdtService;
	
	/**
	 * 下订单(付款前) 
	 * 客户下订单时候调用此方法
	 */
	public synchronized void add4pay(Long icust, Long ipdt, int num, Long iord,Long iuser, String userName) {

		TcustStock cs = new TcustStock();
		cs.setIcust(icust);
		cs.setIpdt(ipdt);
		List<TcustStock> tcustStockList = this.tcustStockDao.find(cs);

		if (CollectionUtils.isEmpty(tcustStockList)) {
			// 添加客户库存表
			cs.setGenTime(new Date());
			cs.setUpdTime(new Date());
			cs.setSumOrdNum4Pay((long) num);
			cs.setSumOrdNumPaid(0L);
			cs.setSumOrdNumCancel(0L);
			cs.setSumOrdNum((long) num);
			cs.setSumOrdNumSum((long) num);
			cs.setSumSpodNum(0L);
			cs.setSumShipmentNum(0L);
			this.tcustStockDao.save(cs);
			// 添加出入库明细表
			this.tcustStockDetailService.add(icust, ipdt, num, cs, iord, iuser,userName);

		} else {
			// 修改客户库存表
			TcustStock newTcustStock = tcustStockList.get(0);
			newTcustStock.setUpdTime(new Date());
			newTcustStock.setSumOrdNum4Pay(newTcustStock.getSumOrdNum4Pay() + num);
			newTcustStock.setSumOrdNum(newTcustStock.getSumOrdNum() + num);
			newTcustStock.setSumOrdNumSum(newTcustStock.getSumOrdNumSum() + num);
			this.tcustStockDao.update(newTcustStock);
			// 添加出入库明细表
			this.tcustStockDetailService.add(icust, ipdt, num, newTcustStock,iord, iuser, userName);
		}
	}

	/**
	 * 撤销订单 
	 * 客户和后台用户撤销订单时候调用此方法
	 */
	public synchronized void cancel(Long icust, Long ipdt, int num, Long iord,Long iuser, String userName) {

		TcustStock cs = new TcustStock();
		cs.setIcust(icust);
		cs.setIpdt(ipdt);
		List<TcustStock> tcustStockList = this.tcustStockDao.find(cs);

		if (!CollectionUtils.isEmpty(tcustStockList)) {
			// 修改客户库存表
			TcustStock newTcustStock = tcustStockList.get(0);
			newTcustStock.setUpdTime(new Date());
			newTcustStock.setSumOrdNum4Pay(newTcustStock.getSumOrdNum4Pay() - num);
			newTcustStock.setSumOrdNum(newTcustStock.getSumOrdNum() - num);
			newTcustStock.setSumOrdNumCancel(newTcustStock.getSumOrdNumCancel() + num);
			this.tcustStockDao.update(newTcustStock);
			// 添加出入库明细表
			this.tcustStockDetailService.cancel(icust, ipdt, num, newTcustStock, iord, iuser, userName);

		}
	}

	/**
	 * 付款 
	 * 后台人员确认付款后调用此方法
	 */
	public synchronized void pay(Long icust, Long ipdt, int num, Long iord,Long iuser, String userName) {
		TcustStock cs = new TcustStock();
		cs.setIcust(icust);
		cs.setIpdt(ipdt);
		List<TcustStock> tcustStockList = this.tcustStockDao.find(cs);

		if (!CollectionUtils.isEmpty(tcustStockList)) {
			// 修改客户库存表
			TcustStock newTcustStock = tcustStockList.get(0);
			newTcustStock.setUpdTime(new Date());
			newTcustStock.setSumOrdNum4Pay(newTcustStock.getSumOrdNum4Pay() - num);
			newTcustStock.setSumOrdNumPaid(newTcustStock.getSumOrdNumPaid() + num);
			this.tcustStockDao.update(newTcustStock);
			// 添加出入库明细表
			this.tcustStockDetailService.pay(icust, ipdt, num, newTcustStock, iord, iuser, userName);
		}
	}

	/**
	 * 生产 
	 * 定时器调用
	 */
	public synchronized void produce(Long icust, Long ipdt, int num, Long iord, Long iuser, String userName) {
		TcustStock cs = new TcustStock();
		cs.setIcust(icust);
		cs.setIpdt(ipdt);
		List<TcustStock> tcustStockList = this.tcustStockDao.find(cs);

		if (!CollectionUtils.isEmpty(tcustStockList)) {
			// 修改客户库存表
			TcustStock newCs = tcustStockList.get(0);
			newCs.setUpdTime(new Date());
			newCs.setSumSpodNum(newCs.getSumSpodNum() + num);
			this.tcustStockDao.update(newCs);
			// 添加出入库明细表
			this.tcustStockDetailService.produce(icust, ipdt, num, newCs, iord, iuser, userName);
		}
	}

	/**
	 * 出库
	 * 后台人员审核物流单时调用
	 */
	public synchronized void shipment(Long icust, Long ipdt, int num, Long ilogisticsOrd, Long iuser, String userName,TlogisticsDetail tlogisticsDetail,Long ipdtHis) {
		TcustStock cs = new TcustStock();
		cs.setIcust(icust);
		cs.setIpdt(ipdt);
		List<TcustStock> tcustStockList = this.tcustStockDao.find(cs);

		// 修改客户库存表
		TcustStock newCs = tcustStockList.get(0);
		newCs.setUpdTime(new Date());
		newCs.setSumOrdNumPaid(newCs.getSumOrdNumPaid() - num);
		newCs.setSumOrdNum(newCs.getSumOrdNum() - num);
		newCs.setSumSpodNum(newCs.getSumSpodNum() - num);
		newCs.setSumShipmentNum(newCs.getSumShipmentNum() + num);
		this.tcustStockDao.update(newCs);
		// 添加出入库明细表
		this.tcustStockDetailService.shipment(icust, ipdt, num, newCs, ilogisticsOrd, iuser, userName);
		
		//修改订单产品明细2
		Long iordDetailPdt = tlogisticsDetail.getIordDetailPdt();
		TordDetailPdt tordDetailPdt = this.tordDetailPdtService.find(iordDetailPdt);
		tordDetailPdt.setDeliveryed(tordDetailPdt.getDeliveryed() + num);
		tordDetailPdt.setPendingNum(tordDetailPdt.getPendingNum() - num);
		this.tordDetailPdtService.update(tordDetailPdt);
		
		//修改订单明细
		TordDetail td = new TordDetail();
		td.setIord(tordDetailPdt.getIord());
		td.setIpdtHis(ipdtHis);
		List<TordDetail> tordDetailList = this.tordDetailService.findSelect(td);
		TordDetail tordDetail = tordDetailList.get(0);
		tordDetail.setDeliveryed(tordDetail.getDeliveryed() + num);
		tordDetail.setPendingNum(tordDetail.getPendingNum() - num);
		this.tordDetailService.update(tordDetail);
	}

	/**
	 * 根据条件查询TcustStock集合
	 */
	public List<TcustStock> findPageByCondition(TcustStockCondition condition) {
		return this.tcustStockDao.queryByCondition(condition);
	}

}
