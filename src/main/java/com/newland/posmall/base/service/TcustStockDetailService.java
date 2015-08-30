package com.newland.posmall.base.service;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.newland.posmall.base.dao.TcustStockDetailDao;
import com.newland.posmall.bean.customer.TcustStock;
import com.newland.posmall.bean.customer.TcustStockDetail;
import com.newland.posmall.bean.dict.CustStockDetailType;

/**
 * 客户出入库明细
 * 例如： 	
 * 				库存订单(付款前)	库存订单(付款后)	库存订单	订单总量		库存现货	 出货		退单
 * 初始-交易变化量	+1000	        0		+1000	+1000		 0		 0 		0
 * 付款-交易变化量	-1000		+1000			0		0		 0		 0      0
 * 生产-交易变化量		0			0			0		0		+1000	 0      0
 * 出库-交易变化量		0		-1000		-1000		0		-1000	+1000   0
 * 						
 * 初始-交易变化量	+1000		0			+1000	+1000		 0		0       0
 * 撤单-交易变化量	-1000		0			-1000		0		 0		0  		+1000
 * 						
 * 初始-交易变化量	+1000		0			+1000		+1000	 0		0		0
 * 部分付款-交易变化量-500	 +500				0		0		 0		0		0
 * 
 */
@Service
public class TcustStockDetailService {

	@Resource
	private TcustStockDetailDao tcustStockDetailDao;

	/**
	 * 下订单(付款前) 
	 */
	public void add(Long icust,Long ipdt,int num,TcustStock tcustStock,Long iord,Long iuser,String userName){
		TcustStockDetail csd = new TcustStockDetail();
		csd.setIcust(icust);
		csd.setIpdt(ipdt);
		csd.setIcustStock(tcustStock.getIcustStock());
		csd.setGenTime(new Date());
		csd.setNum(num);
		csd.setCustStockDetailType(CustStockDetailType.PLACE_AN_ORDER);
		csd.setDeltaOrdNum4Pay((long) num);
		csd.setDeltaOrdNumPaid(0L);
		csd.setDeltaOrdNumCancel(0L);
		csd.setDeltaOrdNum((long) num);
		csd.setDeltaOrdNumSum((long) num);
		csd.setDeltaSpotNum(0L);
		csd.setDeltaShipmentNum(0L);
		csd.setSumOrdNum4Pay(tcustStock.getSumOrdNum4Pay());
		csd.setSumOrdNumPaid(tcustStock.getSumOrdNumPaid());
		csd.setSumOrdNumCancel(tcustStock.getSumOrdNumCancel());
		csd.setSumOrdNum((long) num);
		csd.setSumOrdNumSum((long) num);
		csd.setSumSpodNum(0L);
		csd.setSumShipmentNum(0L);
		csd.setIfk(iord);
		
		csd.setIuser(iuser);//操作人 
		csd.setUserName(userName);//操作人姓名
		this.tcustStockDetailDao.save(csd);
	}
	
	/**
	 * 撤销订单 
	 */
	public void cancel(Long icust,Long ipdt,int num,TcustStock tcustStock,Long iord,Long iuser,String userName){
		TcustStockDetail csd = new TcustStockDetail();
		csd.setIcust(icust);
		csd.setIpdt(ipdt);
		csd.setIcustStock(tcustStock.getIcustStock());
		csd.setGenTime(new Date());
		csd.setNum(num);
		csd.setCustStockDetailType(CustStockDetailType.CANCEL);
		csd.setDeltaOrdNum4Pay((long) -num);
		csd.setDeltaOrdNumPaid(0L);
		csd.setDeltaOrdNumCancel((long) num);
		csd.setDeltaOrdNum((long) -num);
		csd.setDeltaOrdNumSum(0L);
		csd.setDeltaSpotNum(0L);
		csd.setDeltaShipmentNum(0L);
		csd.setSumOrdNum4Pay(tcustStock.getSumOrdNum4Pay());
		csd.setSumOrdNumPaid(tcustStock.getSumOrdNumPaid());
		csd.setSumOrdNumCancel(tcustStock.getSumOrdNumCancel());
		csd.setSumOrdNum(tcustStock.getSumOrdNum());
		csd.setSumOrdNumSum(tcustStock.getSumSpodNum());
		csd.setSumSpodNum(tcustStock.getSumSpodNum());
		csd.setSumShipmentNum(tcustStock.getSumShipmentNum());
		csd.setIfk(iord);
		
		csd.setIuser(iuser);//操作人 
		csd.setUserName(userName);//操作人姓名
		this.tcustStockDetailDao.save(csd);
	}
	
	/**
	 * 付款
	 */
	public void pay(Long icust,Long ipdt,int num,TcustStock tcustStock,Long iord,Long iuser,String userName){
		TcustStockDetail csd = new TcustStockDetail();
		csd.setIcust(icust);
		csd.setIpdt(ipdt);
		csd.setIcustStock(tcustStock.getIcustStock());
		csd.setGenTime(new Date());
		csd.setNum(num);
		csd.setCustStockDetailType(CustStockDetailType.PAYMENT);
		csd.setDeltaOrdNum4Pay((long) -num);
		csd.setDeltaOrdNumPaid((long) num);
		csd.setDeltaOrdNumCancel(0L);
		csd.setDeltaOrdNum(0L);
		csd.setDeltaOrdNumSum(0L);
		csd.setDeltaSpotNum(0L);
		csd.setDeltaShipmentNum(0L);
		csd.setSumOrdNum4Pay(tcustStock.getSumOrdNum4Pay());
		csd.setSumOrdNumPaid(tcustStock.getSumOrdNumPaid());
		csd.setSumOrdNumCancel(tcustStock.getSumOrdNumCancel());
		csd.setSumOrdNum(tcustStock.getSumOrdNum());
		csd.setSumOrdNumSum(tcustStock.getSumSpodNum());
		csd.setSumSpodNum(tcustStock.getSumSpodNum());
		csd.setSumShipmentNum(tcustStock.getSumShipmentNum());
		csd.setIfk(iord);
		
		csd.setIuser(iuser);//操作人 
		csd.setUserName(userName);//操作人姓名
		this.tcustStockDetailDao.save(csd);
	}
	
	/**
	 * 生产 
	 */
	public void produce(Long icust,Long ipdt,int num,TcustStock tcustStock,Long iord,Long iuser,String userName){
		TcustStockDetail csd = new TcustStockDetail();
		csd.setIcust(icust);
		csd.setIpdt(ipdt);
		csd.setIcustStock(tcustStock.getIcustStock());
		csd.setGenTime(new Date());
		csd.setNum(num);
		csd.setCustStockDetailType(CustStockDetailType.PRODUCE);
		csd.setDeltaOrdNum4Pay(0L);
		csd.setDeltaOrdNumPaid(0L);
		csd.setDeltaOrdNumCancel(0L);
		csd.setDeltaOrdNum(0L);
		csd.setDeltaOrdNumSum(0L);
		csd.setDeltaSpotNum((long) num);
		csd.setDeltaShipmentNum(0L);
		csd.setSumOrdNum4Pay(tcustStock.getSumOrdNum4Pay());
		csd.setSumOrdNumPaid(tcustStock.getSumOrdNumPaid());
		csd.setSumOrdNumCancel(tcustStock.getSumOrdNumCancel());
		csd.setSumOrdNum(tcustStock.getSumOrdNum());
		csd.setSumOrdNumSum(tcustStock.getSumSpodNum());
		csd.setSumSpodNum(tcustStock.getSumSpodNum());
		csd.setSumShipmentNum(tcustStock.getSumShipmentNum());
		csd.setIfk(iord);
		
		csd.setIuser(iuser);//操作人 
		csd.setUserName(userName);//操作人姓名
		this.tcustStockDetailDao.save(csd);
	}
	
	/**
	 *  出货
	 */
	public void shipment(Long icust,Long ipdt,int num,TcustStock tcustStock,Long tlogisticsOrd,Long iuser,String userName){
		TcustStockDetail csd = new TcustStockDetail();
		csd.setIcust(icust);
		csd.setIpdt(ipdt);
		csd.setIcustStock(tcustStock.getIcustStock());
		csd.setGenTime(new Date());
		csd.setNum(num);
		csd.setCustStockDetailType(CustStockDetailType.SHIPMENT);
		csd.setDeltaOrdNum4Pay(0L);
		csd.setDeltaOrdNumPaid((long) -num);
		csd.setDeltaOrdNumCancel(0L);
		csd.setDeltaOrdNum((long) -num);
		csd.setDeltaOrdNumSum(0L);
		csd.setDeltaSpotNum((long) -num);
		csd.setDeltaShipmentNum((long) num);
		csd.setSumOrdNum4Pay(tcustStock.getSumOrdNum4Pay());
		csd.setSumOrdNumPaid(tcustStock.getSumOrdNumPaid());
		csd.setSumOrdNumCancel(tcustStock.getSumOrdNumCancel());
		csd.setSumOrdNum(tcustStock.getSumOrdNum());
		csd.setSumOrdNumSum(tcustStock.getSumSpodNum());
		csd.setSumSpodNum(tcustStock.getSumSpodNum());
		csd.setSumShipmentNum(tcustStock.getSumShipmentNum());
		csd.setIfk(tlogisticsOrd);
		
		csd.setIuser(iuser);//操作人 
		csd.setUserName(userName);//操作人姓名
		this.tcustStockDetailDao.save(csd);
	}
	
}
