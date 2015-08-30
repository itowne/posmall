package com.newland.posmall.biz.cust;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newland.posmall.base.service.TlogisticsDetailService;
import com.newland.posmall.base.service.TlogisticsOrdAddrService;
import com.newland.posmall.base.service.TlogisticsService;
import com.newland.posmall.base.service.TordService;
import com.newland.posmall.base.service.TpdtService;
import com.newland.posmall.bean.basebusi.TlogisticsOrd;
import com.newland.posmall.bean.basebusi.TlogisticsOrdAddr;
import com.newland.posmall.bean.basebusi.Tord;
import com.newland.posmall.bean.customer.Tcust;
import com.newland.posmall.controller.cust.model.LogisticsOrdAddr4Page;
import com.newland.posmall.controller.cust.model.LogisticsOrderWithMultipleAddrs4Page;

@Service
@Transactional(rollbackFor = Throwable.class)
public class LogisticsOrderWithMultipleAddrs4PageBiz {

	@Resource
	private TlogisticsService tlogisticsService;

	@Resource
	private TlogisticsOrdAddrService tlogisticsOrdAddrService;

	@Resource
	private TpdtService tpdtService;

	@Resource
	private TordService tordService;

	@Resource
	private TlogisticsDetailService tlogisticsDetailService;

	@Resource
	private Ord4PageBiz ord4PageBiz;
	
	public LogisticsOrderWithMultipleAddrs4Page getLogisticsOrderWithMultipleAddrs4Page(
			Long ilogisticsOrd, Tcust cust) {
		
		LogisticsOrderWithMultipleAddrs4Page mul = new LogisticsOrderWithMultipleAddrs4Page();
		
		TlogisticsOrd logOrd = this.tlogisticsService.findTlogisticsOrdById(ilogisticsOrd);
		if (!logOrd.getIcust().equals(cust.getIcust())) {
			return null;
		}

		List<LogisticsOrdAddr4Page> addrs = findLogisticsOrdAddr4PageList(ilogisticsOrd);
		mul.setAddrs(addrs);

		Long iord = logOrd.getIord();
		if(iord!=null){
			Tord tord = this.tordService.find(iord);
			mul.setTord(tord);
		}
		
		return mul;
	}

	private List<LogisticsOrdAddr4Page> findLogisticsOrdAddr4PageList(
			Long ilogisticsOrd) {
		// 查询地址列表
		List<LogisticsOrdAddr4Page> addrs = new ArrayList<LogisticsOrdAddr4Page>();
		TlogisticsOrdAddr loa = new TlogisticsOrdAddr();
		loa.setIlogisticsOrd(ilogisticsOrd);
		List<TlogisticsOrdAddr> tlogisticsOrdAddrList = this.tlogisticsOrdAddrService.findListSelect(loa);
		for (TlogisticsOrdAddr addr : tlogisticsOrdAddrList) {
			Long ipdtHist = addr.getIpdtHis();
			String pdtName = this.tpdtService.findTpdtHisById(ipdtHist).getName();

			LogisticsOrdAddr4Page p = new LogisticsOrdAddr4Page();
			p.setTlogisticsOrdAddr(addr);
			p.setPdtName(pdtName);

			addrs.add(p);
		}
		return addrs;
	}

	
}
