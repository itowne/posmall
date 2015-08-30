package com.newland.posmall.biz.cust;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newland.posmall.base.service.TagmtService;
import com.newland.posmall.base.service.TpdtService;
import com.newland.posmall.bean.basebusi.Tagmt;
import com.newland.posmall.bean.basebusi.TagmtDetail;
import com.newland.posmall.bean.basebusi.TagmtDetailHis;
import com.newland.posmall.bean.basebusi.TpdtHis;
import com.newland.posmall.bean.model4page.Agmt4Page;
import com.newland.posmall.bean.model4page.AgmtDetail4Page;
import com.newland.posmall.bean.model4page.AgmtDetailHis4Page;

@Service
@Transactional
public class Agmt4PageBiz {

	@Resource
	private TagmtService tagmtService;

	@Resource
	private TpdtService tpdtService;

	public List<Agmt4Page> findAgmt4PageList(List<Tagmt> tagmts) {
		List<Agmt4Page> list = new ArrayList<Agmt4Page>(tagmts.size());
		for (Tagmt tagmt : tagmts) {
			Agmt4Page p = findAgmt4Page(tagmt);
			list.add(p);
		}
		return list;
	}

	public Agmt4Page findAgmt4Page(Tagmt tagmt) {
		Agmt4Page p = new Agmt4Page();

		p.setTagmt(tagmt);

		List<TagmtDetail> tagmtDetails = this.tagmtService
				.findTagmtDetial(tagmt);
		p.setAgmtDetail4PageList(findAgmtDetail4PageList(tagmtDetails));
		
		// 协议变更明细历史
		TagmtDetailHis condition = new TagmtDetailHis();
		condition.setIagmt(tagmt.getIagmt());
		condition.setDelFlag(Boolean.FALSE);
		List<TagmtDetailHis> tagmtDetailHisList = this.tagmtService.findTagmtDetailHis(condition); 
		p.setAgmtDetailHis4PageList(this.findAgmtDetailHis4PageList(tagmtDetailHisList));

		int tagmtTpdtTotalNum = 0; // 协议订货量
		int tagmtTpdtRemainNum = 0; // 协议订货剩余量
		BigDecimal tagmtTotalAmt = new BigDecimal(0.00); // 协议总货款
		StringBuffer tpdtNamesSb = new StringBuffer();
		for (TagmtDetail tagmtDetail : tagmtDetails) {
			tagmtTpdtTotalNum += tagmtDetail.getNum();
			tagmtTpdtRemainNum += tagmtDetail.getRemainQuota();
			TpdtHis his = this.tpdtService.findTpdtHisById(tagmtDetail
					.getIpdtHis());
			tagmtTotalAmt = tagmtTotalAmt.add(his.getPrice()
					.multiply(tagmtDetail.getRate())
					.multiply(BigDecimal.valueOf(tagmtDetail.getNum())));
			tpdtNamesSb.append(his.getName()).append(",");
		}

		int remainDay = 0; // 协议剩余天数
		Calendar nowCal = Calendar.getInstance();
		Calendar endCal = Calendar.getInstance();
		nowCal.setTime(DateUtils.truncate(new Date(), Calendar.DATE));
		endCal.setTime(DateUtils.truncate(tagmt.getEndTime(), Calendar.DATE));
		remainDay = endCal.get(Calendar.DAY_OF_YEAR)
				- nowCal.get(Calendar.DAY_OF_YEAR);
		if (nowCal.get(Calendar.YEAR) < endCal.get(Calendar.YEAR)) {
			nowCal = (Calendar) nowCal.clone();
			do {
				remainDay += nowCal.getActualMaximum(Calendar.DAY_OF_YEAR);
				nowCal.add(Calendar.YEAR, 1);
			} while (nowCal.get(Calendar.YEAR) != endCal.get(Calendar.YEAR));
			remainDay += 1;
		} else if (nowCal.get(Calendar.YEAR) > endCal.get(Calendar.YEAR)) {
			remainDay = nowCal.get(Calendar.DAY_OF_YEAR)
					- endCal.get(Calendar.DAY_OF_YEAR);
			endCal = (Calendar) endCal.clone();
			do {
				remainDay += endCal.getActualMaximum(Calendar.DAY_OF_YEAR);
				endCal.add(Calendar.YEAR, 1);
			} while (nowCal.get(Calendar.YEAR) != endCal.get(Calendar.YEAR));
			remainDay = -remainDay;
		} else {
			remainDay += 1;
		}
		p.setTotalNum(tagmtTpdtTotalNum);
		p.setRemainNum(tagmtTpdtRemainNum);
		p.setTotalAmt(tagmtTotalAmt.setScale(2, RoundingMode.HALF_UP));
		p.setTpdtNames(tpdtNamesSb.toString().substring(0,
				tpdtNamesSb.toString().length() - 1));
		p.setRemainDay(remainDay);

		return p;
	}

	public List<AgmtDetail4Page> findAgmtDetail4PageList(
			List<TagmtDetail> tagmtDetails) {
		List<AgmtDetail4Page> list = new ArrayList<AgmtDetail4Page>(
				tagmtDetails.size());
		for (TagmtDetail tagmtDetail : tagmtDetails) {
			AgmtDetail4Page p = findAgmtDetail4Page(tagmtDetail);
			list.add(p);
		}
		return list;
	}

	public AgmtDetail4Page findAgmtDetail4Page(TagmtDetail tagmtDetail) {

		AgmtDetail4Page p = new AgmtDetail4Page();
		p.setTagmtDetail(tagmtDetail);

		// 查询协议明显应该关联产品历史表，而不是产品表
		TpdtHis his = this.tpdtService
				.findTpdtHisById(tagmtDetail.getIpdtHis());
		p.setTpdtHis(his);

		Object[] numArray = (Object[]) this.tagmtService
				.findPdtLogisticsByIagmt(tagmtDetail.getIagmt(),
						tagmtDetail.getIpdt());

		if (numArray != null && numArray.length == 3) {
			p.setDeliveryed((numArray[1] != null) ? Integer.valueOf(numArray[1]
					.toString()) : 0);
			p.setPendingNum((numArray[2] != null) ? Integer.valueOf(numArray[2]
					.toString()) : 0);
		} else {
			p.setDeliveryed(0);
			p.setPendingNum(0);
		}

		return p;
	}
	
	public List<AgmtDetailHis4Page> findAgmtDetailHis4PageList(List<TagmtDetailHis> detailHis) {
		List<AgmtDetailHis4Page> list = new ArrayList<AgmtDetailHis4Page>(detailHis.size());
		for (TagmtDetailHis tagmtDetailHis : detailHis) {
			AgmtDetailHis4Page p = this.findAgmtDetailHis4Page(tagmtDetailHis);
			list.add(p);
		}
		return list;
	}

	public AgmtDetailHis4Page findAgmtDetailHis4Page(TagmtDetailHis tagmtDetailHis) {

		AgmtDetailHis4Page p = new AgmtDetailHis4Page();
		p.setTagmtDetailHis(tagmtDetailHis);

		// 查询协议明显应该关联产品历史表，而不是产品表
		TpdtHis his = this.tpdtService.findTpdtHisById(tagmtDetailHis.getIpdtHis());
		p.setTpdtHis(his);

		return p;
	}
}
