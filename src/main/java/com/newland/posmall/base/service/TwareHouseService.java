package com.newland.posmall.base.service;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.newland.posmall.base.dao.TwareHouseDao;
import com.newland.posmall.bean.basebusi.TwareHouse;
import com.newland.posmall.bean.dict.PayStatus;


@Service
public class TwareHouseService {
	
	@Resource
	private TwareHouseDao twareHouseDao;
	
	
	public void save(TwareHouse twareHouse){
		Date date = new Date();
		if(twareHouse.getGenTime() == null){
			twareHouse.setGenTime(date);
		}
		twareHouse.setUpdTime(date);
		
		this.twareHouseDao.save(twareHouse);
	}
	
	public void update(TwareHouse twareHouse){
		twareHouse.setUpdTime(new Date());
		this.twareHouseDao.update(twareHouse);		
	}
	
	public TwareHouse find(Long id){
		return this.twareHouseDao.get(id);
		
	}
	
	/**
	 * 获取 末完全支付 协议的 仓管费
	 * @param icust
	 * @param iagmt
	 * @return
	 */
	public TwareHouse findTwareHouseByIagmt(Long icust, Long iagmt){
		TwareHouse twareHouse = new TwareHouse();
		twareHouse.setIcust(icust);
		twareHouse.setIagmt(iagmt);
		List<TwareHouse> list = this.twareHouseDao.find(twareHouse);
		if(CollectionUtils.isEmpty(list)){
			return null;
		}else{
			for(TwareHouse t : list){
				if(!(t.getPayStatus() == PayStatus.HAVE_PAY || t.getPayStatus() == PayStatus.REVOKED)){
					return t;
				}
			}
			return null;
		}
		
	}
}
