package com.newland.posmall.biz.cust;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newland.posmall.base.service.TcustStockService;
import com.newland.posmall.base.service.TordDetailPdtService;
import com.newland.posmall.base.service.TordDetailService;
import com.newland.posmall.base.service.TordService;
import com.newland.posmall.base.service.TpayNotifyService;
import com.newland.posmall.base.service.TpdtService;
import com.newland.posmall.bean.basebusi.Tord;
import com.newland.posmall.bean.basebusi.TordDetail;
import com.newland.posmall.bean.basebusi.TordDetailPdt;
import com.newland.posmall.bean.basebusi.TpayNotify;
import com.newland.posmall.bean.basebusi.TpdtHis;
import com.newland.posmall.bean.dict.PayType;
import com.newland.posmall.bean.dict.ValidStatus;
import com.newland.posmall.controller.cust.model.Ord4Page;
import com.newland.posmall.controller.cust.model.OrdDetail4Page;
import com.newland.posmall.controller.cust.model.OrdDetailPdt4Page;

@Service
@Transactional
public class Ord4PageBiz {

	@Resource
	private TordService tordService;

	@Resource
	private TordDetailService tordDetailService;

	@Resource
	private TordDetailPdtService tordDetailPdtService;

	@Resource
	private TpdtService tpdtService;

	@Resource
	private TcustStockService tcustStockService;

	@Resource
	private TpayNotifyService tpayNotifyService;
	
	public List<Ord4Page> findOrder4PageList(List<Tord> ords) {
		List<Ord4Page> list = new ArrayList<Ord4Page>(ords.size());
		for (Tord tord : ords) {
			Ord4Page p = findOrd4Page(tord);
			list.add(p);
		}
		return list;
	}

	public Ord4Page findOrd4Page(Tord tord) {
		Ord4Page p = new Ord4Page();
		p.setTord(tord);
		TordDetail det = new TordDetail();
		det.setIord(tord.getIord());
		det.setDelFlag(false);
		det.setOrdDetailStatus(ValidStatus.VALID);
		List<TordDetail> details = tordDetailService.findSelect(det);

		List<OrdDetail4Page> ordDetail4PageList = findOrdDetail4Page(details);
		p.setOrdDetail4PageList(ordDetail4PageList);
		
		StringBuffer tips = new StringBuffer();
		tips.append("订单明细：");

		for(OrdDetail4Page ordDetail4Page:ordDetail4PageList){
			tips.append("\r\n名称:" + ordDetail4Page.getPdtName());
			tips.append(",&nbsp;&nbsp;可发货:" + ordDetail4Page.getTordDetail().getRemainQuota());
			tips.append(",&nbsp;&nbsp;现货:" + ordDetail4Page.getSpodNum());
			tips.append(",&nbsp;&nbsp;总数：" + ordDetail4Page.getTordDetail().getNum());
			tips.append(",&nbsp;&nbsp;已发货:" + ordDetail4Page.getTordDetail().getDeliveryed());
			tips.append(",&nbsp;&nbsp;未发货:" + ordDetail4Page.getTordDetail().getPendingNum());
		}
		
		p.setTips(tips.toString());

		
		BigDecimal havePaid = new BigDecimal(0);
		TpayNotify pnf = new TpayNotify();
		pnf.setIfk(tord.getIord());
		pnf.setPayType(PayType.ORDER);
		List<TpayNotify> tpayNotifyList = this.tpayNotifyService.findBySelect(pnf);
		for(TpayNotify tpayNotify:tpayNotifyList){
			havePaid = havePaid.add(tpayNotify.getHavepayAmt());
		}
		p.setHavePaid(havePaid);
		
		return p;
	}

	public List<OrdDetail4Page> findOrdDetail4Page(List<TordDetail> detail) {
		List<OrdDetail4Page> list = new ArrayList<OrdDetail4Page>(detail.size());
		for (TordDetail tordDetail : detail) {
			OrdDetail4Page p = findOrdDetail4Page(tordDetail);
			list.add(p);
		}
		return list;
	}

	public OrdDetail4Page findOrdDetail4Page(TordDetail detail) {
		OrdDetail4Page p = new OrdDetail4Page();
		p.setTordDetail(detail);

		TpdtHis tpdtHis = this.tpdtService.findTpdtHisById(detail.getIpdtHis());

		p.setIpdt(tpdtHis.getIpdt());
		p.setPdtName(tpdtHis.getName());

		TordDetailPdt tdp = new TordDetailPdt();
		tdp.setIord(detail.getIord());
		tdp.setIpdt(tpdtHis.getIpdt());
		List<TordDetailPdt> dps = tordDetailPdtService.findSelect(tdp);
		List<OrdDetailPdt4Page> ordDetailPdt4PageList = genOrdDetailPdt4Page(dps);
		p.setOrdDetailPdt4PageList(ordDetailPdt4PageList);

		sumNum(p);

		return p;
	}

	/**
	 * 计算当前现货量
	 */
	private void sumNum(OrdDetail4Page od) {
		int num = 0;
		int produced = 0;
		int deliveryed = 0;
		int pending = 0;
		int used = 0;
		int remain = 0;
		List<OrdDetailPdt4Page> odpList = od.getOrdDetailPdt4PageList();
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		for (OrdDetailPdt4Page odpg : odpList) {
			TordDetailPdt odp = odpg.getDetailPdt();
			if (compare(odp, year, month, day) < 0) {
				num += odp.getNum();
				produced += odp.getProducedNum();
				deliveryed += odp.getDeliveryed();
				used += odp.getUsedQuota();
				remain += odp.getRemainQuota();
				pending += odp.getPendingNum();
			}
		}

		od.setSpodNum((long) (num - deliveryed - pending));
	}

	private int compare(TordDetailPdt odp, int year, int month, int day) {
		int ret = odp.getYear() - year;
		if (ret != 0) {
			return ret;
		}
		ret = odp.getMonth() - month;
		if (ret != 0) {
			return ret;
		}
		return odp.getDay() - day;
	}

	public List<OrdDetailPdt4Page> genOrdDetailPdt4Page(List<TordDetailPdt> dps) {
		List<OrdDetailPdt4Page> list = new ArrayList<OrdDetailPdt4Page>();
		for (TordDetailPdt tordDetailPdt : dps) {
			OrdDetailPdt4Page p = new OrdDetailPdt4Page();
			p.setDetailPdt(tordDetailPdt);
			list.add(p);
		}
		return list;
	}

}
