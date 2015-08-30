package com.newland.posmall.biz.cust;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newland.posmall.base.service.TlogisticsOrdAddrService;
import com.newland.posmall.base.service.TlogisticsService;
import com.newland.posmall.base.service.TordDetailService;
import com.newland.posmall.base.service.TordService;
import com.newland.posmall.base.service.TpdtService;
import com.newland.posmall.bean.basebusi.TlogisticsOrd;
import com.newland.posmall.bean.basebusi.TlogisticsOrdAddr;
import com.newland.posmall.controller.cust.model.LogisticsOrd4Page;
import com.newland.posmall.controller.cust.model.LogisticsOrdAddr4Page;

@Service
@Transactional
public class Logistics4PageBiz {

	@Resource
	private TlogisticsService tlogisticsService;
	
	@Resource
	private TlogisticsOrdAddrService tlogisticsOrdAddrService;
	
	@Resource
	private TpdtService tpdtService;
	
	@Resource
	private TordService tordService;
	
	@Resource
	private TordDetailService tordDetailService;
	
	public List<LogisticsOrd4Page> findLogisticsOrd4PageList(List<TlogisticsOrd> tLogisticsOrds) {
		List<LogisticsOrd4Page> list = new ArrayList<LogisticsOrd4Page>(tLogisticsOrds.size());
		for (TlogisticsOrd tLogisticsOrd : tLogisticsOrds) {
			LogisticsOrd4Page p = findLogisticsOrd4Page(tLogisticsOrd);
			list.add(p);
		}
		return list;
	}

	public LogisticsOrd4Page findLogisticsOrd4Page(TlogisticsOrd tlogisticsOrd) {
		LogisticsOrd4Page p = new LogisticsOrd4Page();
		p.setTlogisticsOrd(tlogisticsOrd);
		
		TlogisticsOrdAddr loa = new TlogisticsOrdAddr();
		loa.setIlogisticsOrd(tlogisticsOrd.getIlogisticsOrd());
		List<TlogisticsOrdAddr> loaList = this.tlogisticsOrdAddrService.findListSelect(loa);
		p.setLogisticsOrdAddr4PageList(findLogisticsOrdAddr4PageList(loaList));
		
		return p;
	}
	
	public List<LogisticsOrdAddr4Page> findLogisticsOrdAddr4PageList(List<TlogisticsOrdAddr> tlogisticsOrdAddrs) {
		List<LogisticsOrdAddr4Page> list = new ArrayList<LogisticsOrdAddr4Page>(tlogisticsOrdAddrs.size());
		for (TlogisticsOrdAddr tlogisticsOrdAddr : tlogisticsOrdAddrs) {
			LogisticsOrdAddr4Page p = findLogisticsOrdAddr4Page(tlogisticsOrdAddr);
			list.add(p);
		}
		return list;
	}

	private LogisticsOrdAddr4Page findLogisticsOrdAddr4Page(TlogisticsOrdAddr tlogisticsOrdAddr) {
		
		LogisticsOrdAddr4Page p = new LogisticsOrdAddr4Page();
		p.setTlogisticsOrdAddr(tlogisticsOrdAddr);
		
		Long ipdtHis = tlogisticsOrdAddr.getIpdtHis();
//		TlogisticsOrd tlogisticsOrd = this.tlogisticsService.findTlogisticsOrdById(tlogisticsOrdAddr.getIlogisticsOrd());
//		TordDetail td = new TordDetail();
//		td.setIpdtHis(ipdtHis);
//		td.setIord(tlogisticsOrd.getIord());
//		List<TordDetail> tordDetailList = tordDetailService.findSelect(td);
//		if(!CollectionUtils.isEmpty(tordDetailList)){
//			TordDetail tordDetail = tordDetailList.get(0);
//			tordDetail.getNum()
//			p.setNum(tordDetail.getNum());
//			p.setUsedQuota(tordDetail.getUsedQuota());
//			p.setRemainQuota(tordDetail.getRemainQuota());
//		}
		p.setPdtName(this.tpdtService.findTpdtHisById(ipdtHis).getName());
		return p;
	}
}
