package com.newland.posmall.controller.admin;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Order;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.TuserSession;
import org.ohuyo.rapid.base.entity.AjaxResult;
import org.ohuyo.rapid.base.entity.Tfile;
import org.ohuyo.rapid.base.service.FileUpDownService;
import org.ohuyo.rapid.base.servlet.AppSessionFilter;
import org.ohuyo.rapid.file.CsvFileTranslator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.newland.posmall.base.exception.BizException;
import com.newland.posmall.bean.basebusi.OrderDownLoad;
import com.newland.posmall.bean.basebusi.Tagmt;
import com.newland.posmall.bean.basebusi.Tord;
import com.newland.posmall.bean.basebusi.TordDetail;
import com.newland.posmall.bean.basebusi.TordDetailPdt;
import com.newland.posmall.bean.basebusi.condition.OrderDownLoadCondition;
import com.newland.posmall.bean.basebusi.condition.TordCondition;
import com.newland.posmall.bean.basebusi.condition.TordDetailPdtCondition;
import com.newland.posmall.bean.customer.TcustReg;
import com.newland.posmall.bean.dict.DownLoadStat;
import com.newland.posmall.bean.dict.OrdStatus;
import com.newland.posmall.bean.dict.PayStatus;
import com.newland.posmall.biz.admin.OrderBiz;
import com.newland.posmall.biz.common.TfileBiz;
import com.newland.posmall.biz.file.FileDownload;
import com.newland.posmall.biz.file.bean.OrderInfo;
import com.newland.posmall.controller.BaseController;
import com.newland.posmall.controller.cust.model.OrdDown4Page;
import com.newland.posmall.identifier.IdentifierService;

@Controller("admin.order")
public class OrderController extends BaseController {

	@Resource
	private OrderBiz orderBiz;

	@Resource
	private FileDownload fileDownload;

	@Resource
	private FileUpDownService fileUpDownService;

	@Resource(name = "identifierService")
	private IdentifierService identifierService;

	@Resource
	private TfileBiz tfileBiz;

	private final static String NAV_TAB_ID = "KHDDGL";

	@RequestMapping("/admin/order/orderDetail.do")
	public String orderDetail(Model model,
			@RequestParam(required = false) Long iord) {

		Tord tord = this.orderBiz.queryTordByIord(iord);
		TcustReg tcustReg = orderBiz.findTcustReg(tord.getIcust());
		Tagmt tagmt = orderBiz.queryTagmtByIagmt(tord.getIagmt());

		model.addAttribute("tord", tord);
		model.addAttribute("tagmt", tagmt);
		model.addAttribute("tcustReg", tcustReg);

		List<TordDetail> tordDetailList = this.orderBiz
				.queryTordDetailList(iord);
		model.addAttribute("tordDetailList", tordDetailList);

		return "/admin/order/orderDetail";
	}
	
	@RequestMapping("/admin/order/toOrderStop.do")
	public String toOrderStop(Model model,
			@RequestParam(required = false) Long iord) {

		Tord tord = this.orderBiz.queryTordByIord(iord);
		TcustReg tcustReg = orderBiz.findTcustReg(tord.getIcust());
		Tagmt tagmt = orderBiz.queryTagmtByIagmt(tord.getIagmt());

		model.addAttribute("tord", tord);
		model.addAttribute("tagmt", tagmt);
		model.addAttribute("tcustReg", tcustReg);

		List<TordDetail> tordDetailList = this.orderBiz
				.queryTordDetailList(iord);
		model.addAttribute("tordDetailList", tordDetailList);

		return "/admin/order/orderStop";
	}
	
	@RequestMapping("/admin/order/orderStop.do")
	public String orderStop(Model model,
			@RequestParam(required = false) Long iord) {

		Tord tord = this.orderBiz.queryTordByIord(iord);

		TuserSession tuserSession = (TuserSession) AppSessionFilter
					.getAppSession();
		orderBiz.stopTord(tord, tuserSession.getTuser());

		ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
		ajaxResult.setMessage("终止订单成功");
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}

	@RequestMapping("/admin/order/toOrderAudit.do")
	public String toOrderAudit(Model model,
			@RequestParam(required = false) Long iord) {

		Tord tord = this.orderBiz.queryTordByIord(iord);
		TcustReg tcustReg = orderBiz.findTcustReg(tord.getIcust());
		Tagmt tagmt = orderBiz.queryTagmtByIagmt(tord.getIagmt());

		model.addAttribute("tord", tord);
		model.addAttribute("tagmt", tagmt);
		model.addAttribute("tcustReg", tcustReg);

		List<TordDetail> tordDetailList = this.orderBiz
				.queryTordDetailList(iord);
		model.addAttribute("tordDetailList", tordDetailList);

		return "/admin/order/orderAudit";
	}

	@RequestMapping("/admin/order/orderAudit.do")
	public String orderAudit(Model model,
			@RequestParam(required = false) Long iord) {

		Tord tord = this.orderBiz.queryTordByIord(iord);

		try {
			TuserSession tuserSession = (TuserSession) AppSessionFilter
					.getAppSession();
			orderBiz.auditTord(tord, tuserSession.getTuser());
		} catch (BizException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setNavTabId(NAV_TAB_ID);
			ajaxResult.setCallbackType("");
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}

		ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
		ajaxResult.setMessage(AjaxResult.MSG_UPDATE);
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}

	@RequestMapping("/admin/order/orderList.do")
	public String orderList(Model model,
			@RequestParam(required = false) Integer pageNum,
			@RequestParam(required = false) Integer numPerPage,
			@RequestParam(required = false) OrdStatus ordStatus,
			@RequestParam(required = false) PayStatus payStatus,
			@DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
			@DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
			@RequestParam(required = false) String name) {

		Page page = new Page();
		page.setCount(true);
		page.setCapacity(numPerPage == null ? CAPACITY : numPerPage);
		page.setPageOffset(pageNum == null ? PAGE_OFF_SET : pageNum - 1);

		List<Tord> torderList = orderBiz.findListByInfo(page, ordStatus,
				payStatus, startDate, endDate, name);

		model.addAttribute("torderList", torderList);
		model.addAttribute("ordStatus", ordStatus);
		model.addAttribute("payStatus", payStatus);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("name", name);

		model.addAttribute(NUM_PER_PAGE, page.getCapacity());
		model.addAttribute(PAGE_NUM, pageNum);
		model.addAttribute(TOTAL_COUNT, page.getRecAmt());
		return "/admin/order/orderList";
	}

	/**
	 * 协议撤销 iagmt agmtStatus
	 */
	@RequestMapping("/admin/order/removeOrder.do")
	public String removeOrder(@RequestParam(required = false) Long iord,
			Model model) {
		Tord tord = orderBiz.queryTordByIord(iord);
		TuserSession tuserSession = (TuserSession) AppSessionFilter
				.getAppSession();
		try {
			orderBiz.deleteTord(iord, tord.getIagmt(),
					tuserSession.getTuser());
		} catch (BizException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setNavTabId(NAV_TAB_ID);
			ajaxResult.setCallbackType("");
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}

		ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
		ajaxResult.setMessage("撤销订单成功");
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType("");
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}

	@RequestMapping("/admin/order/orderlogistics.do")
	public String orderlogistics() {

		return "/admin/order/orderlogistics";
	}

	@RequestMapping("/admin/order/orderlogisticsAdd.do")
	public String orderlogisticsAdd() {
		return "/admin/order/orderlogisticsAdd";
	}

	@RequestMapping("/admin/order/downLoadOrd.do")
	public void downLoadOrd(HttpServletResponse respon) throws ParseException {

		TordDetailPdtCondition cond = new TordDetailPdtCondition();

		cond.setGenTime(new Date());
		cond.setDownLoadStat(DownLoadStat.UNDOWNLOAD);
		List<TordDetailPdt> detailpdtList = orderBiz.queryListByCondition(cond);
		Set<Long> iOrd = new HashSet<Long>();
		for (TordDetailPdt obj : detailpdtList) {
			iOrd.add(obj.getIord());
		}

		List<Tord> ordList = new ArrayList<Tord>();
		if (!CollectionUtils.isEmpty(iOrd)) {
			TordCondition condition = new TordCondition();
			condition.setIord(iOrd);
			condition.addOrders(Order.asc("iord"));
			ordList = orderBiz.findListByCond(condition);
		}

		List<OrdDown4Page> od4pList = orderBiz.getOrderDownData(ordList);

		File file = null;
		try {
			String trace = identifierService.genBatchId();
			file = fileDownload.orderInfoDown(od4pList, trace);
			Tfile tfile = tfileBiz.getTfileByFile(file, "csv",
					"application/octet-stream");
			tfileBiz.save(tfile);

			// 做下载标记
			for (TordDetailPdt detailPdt : detailpdtList) {
				detailPdt.setDownloadStat(DownLoadStat.DOWNLOAD);
				orderBiz.updatOrdDetailPdt(detailPdt);
			}
			for (Tord ord : ordList) {
				ord.setTrace(trace);
				orderBiz.updateOrd(ord);
			}

			// 新增订单下载关联关系
			OrderDownLoad od = new OrderDownLoad();
			od.setBatchNo(trace);
			od.setFileUUid(tfile.getUuid());
			od.setGenTime(new Date());
			od.setUpdTime(new Date());
			od.setNum(1);
			od.setCount(detailpdtList.size());
			TuserSession tuserSession = (TuserSession) AppSessionFilter
					.getAppSession();
			od.setiUsr(tuserSession.getTuser().getIuser());
			orderBiz.save(od);

			fileUpDownService.download(file, respon);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/admin/order/ordDownLoadHis.do")
	public String ordDownLoadHis(Integer pageNum, Integer numPerPage,
			Model model) {
		Page page = new Page();
		page.setCount(true);
		page.setCapacity(numPerPage == null ? CAPACITY : numPerPage);
		page.setPageOffset(pageNum == null ? PAGE_OFF_SET : pageNum - 1);
		OrderDownLoadCondition condition = new OrderDownLoadCondition();
		condition.addOrders(Order.desc("genTime"));
		List<OrderDownLoad> list = orderBiz
				.findPageByCondition(condition, page);

		model.addAttribute("tOrdDownList", list);
		model.addAttribute(NUM_PER_PAGE, page.getCapacity());
		model.addAttribute(PAGE_NUM, pageNum);
		model.addAttribute(TOTAL_COUNT, page.getRecAmt());
		return "/admin/order/ordDownList";
	}

	@RequestMapping("/admin/order/ordDownDetail.do")
	public String ordDownLoadDetail(String id, Model model) {

		if (StringUtils.isBlank(id)) {
			throw new RuntimeException("找不到该记录!");
		}

		Tord ord = new Tord();
		ord.setTrace(id);
		List<Tord> ordList = orderBiz.findBySelect(ord);
		List<OrdDown4Page> od4p = orderBiz.getOrderDownData(ordList);
		model.addAttribute("od4p", od4p);
		return "/admin/order/ordDownDetail";
	}

	@RequestMapping("/admin/order/saveOrdUpload.do")
	public String saveOrdUploadFile(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Tfile> files = null;
		String[] limitExts = { "xls", "xlsx", "csv" };// {"gif", "png",
														// "jpg","xls","xlsx"};
		String[] limitContentType = { "application/octet-stream",
				"application/vnd.ms-excel",
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" };// {"image/gif",
																						// "image/png",
																						// "image/jpg"};
		try {
			files = this.fileUpDownService.upload(request, response, limitExts,
					limitContentType, -1);
		} catch (Exception e) {
			ajaxResult.setMessage(e.getMessage());
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setNavTabId("ord_upload");
			ajaxResult.setCallbackType("");
			model.addAttribute(ajaxResult);
		}
		Tfile tfile = files.get("ordUpload");

		CsvFileTranslator trans = new CsvFileTranslator(",");
		InputStreamReader reader = null;
		try {
			String str = new String(tfile.getContent(), "gbk");
			reader = new InputStreamReader(new ByteArrayInputStream(
					tfile.getContent()), "gbk");
		} catch (UnsupportedEncodingException e) {
			ajaxResult.setMessage(e.getMessage());
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setNavTabId("ord_upload");
			ajaxResult.setCallbackType("");
			model.addAttribute(ajaxResult);
		}
		List<OrderInfo> list = trans.fromFile(reader, OrderInfo.class, true);
		for (OrderInfo orderInfo : list) {
//			TordDetailPdt detailPdt = orderBiz.findDetailPdtById(orderInfo
//					.getiOrdDetailPdt());
			// detailPdt.setStartSn(orderInfo.getStartSn());
			// detailPdt.setEndSn(orderInfo.getEndSn());
			// orderBiz.updatOrdDetailPdt(detailPdt);
		}
		return "";
	}

	@RequestMapping("/admin/order/checkDownLoad.do")
	public String checkDownLoad() {
		TordDetailPdtCondition cond = new TordDetailPdtCondition();
		cond.setGenTime(new Date());
		cond.setDownLoadStat(DownLoadStat.UNDOWNLOAD);
		List<TordDetailPdt> detailpdtList = orderBiz.queryListByCondition(cond);
		if(CollectionUtils.isEmpty(detailpdtList)){
			throw new RuntimeException("没有可下载的数据!");
		}
		return "redirect:/admin/order/downLoadOrd.do";
	}
}
