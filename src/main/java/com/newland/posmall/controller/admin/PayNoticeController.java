package com.newland.posmall.controller.admin;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.TuserSession;
import org.ohuyo.rapid.base.entity.AjaxResult;
import org.ohuyo.rapid.base.entity.Tfile;
import org.ohuyo.rapid.base.service.FileUpDownService;
import org.ohuyo.rapid.base.service.TfileService;
import org.ohuyo.rapid.base.servlet.AppSessionFilter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.newland.posmall.base.exception.BizException;
import com.newland.posmall.bean.basebusi.Tagmt;
import com.newland.posmall.bean.basebusi.TlogisticsOrd;
import com.newland.posmall.bean.basebusi.Tord;
import com.newland.posmall.bean.basebusi.Tpay;
import com.newland.posmall.bean.basebusi.TpayNotify;
import com.newland.posmall.bean.basebusi.TwareHouse;
import com.newland.posmall.bean.basebusi.TwareHouseDetail;
import com.newland.posmall.bean.customer.TcustReg;
import com.newland.posmall.bean.customer.Trenew;
import com.newland.posmall.bean.dict.PayMethod;
import com.newland.posmall.bean.dict.PayStatus;
import com.newland.posmall.bean.dict.PayType;
import com.newland.posmall.biz.admin.PayNotifyBiz;
import com.newland.posmall.biz.file.FileDownload;
import com.newland.posmall.controller.BaseController;

@Scope("prototype")
@Controller("admin.paynotice")
public class PayNoticeController extends BaseController {

	private final static String NAV_TAB_ID = "FKTZSGL";

	@Resource
	private PayNotifyBiz payNotifyBiz;

	@Resource
	private TfileService tfileService;
	
	@Resource
	private FileDownload fileDownload;

	@Resource
	private FileUpDownService fileUpDownService;

	@SuppressWarnings("rawtypes")
	@RequestMapping("/admin/paynotice/paynoticeDetail.do")
	public String paynoticeDetail(Model model,
			@RequestParam(required = false) Long ipayNotify) {
		TpayNotify tpayNotify = this.payNotifyBiz
				.queryTpayNotifyByIpayNotify(ipayNotify);
		model.addAttribute("tpayNotify", tpayNotify);

		// 已审核 的凭证
		List<Tpay> tpayAuditedList = null;
		List<Tfile> tfileAuditedList = null;
		tpayAuditedList = payNotifyBiz.queryAuditedPayByfk(tpayNotify.getIfk(),
				tpayNotify.getPayType());
		if (tpayAuditedList != null && tpayAuditedList.size() > 0) {
			Tfile fileTemp = null;
			tfileAuditedList = new ArrayList<Tfile>();
			for (Tpay tpay : tpayAuditedList) {
				if(tpay.getPayMethod() == PayMethod.REMITTANCE){
					fileTemp = tfileService.getById(tpay.getIfile());
					tfileAuditedList.add(fileTemp);
				}else{
					tfileAuditedList.add(null);
				}
			}
		}

		// 末审核的凭证
		List<Tpay> tpayWaitAuditList = null;
		List<Tfile> tfileWaitAuditList = null;
		tpayWaitAuditList = payNotifyBiz.queryWaitAuditPayByfk(tpayNotify.getIfk(), tpayNotify.getPayType());
		if(tpayWaitAuditList != null && tpayWaitAuditList.size() > 0){
			Tfile fileTemp = null;
			tfileWaitAuditList = new ArrayList<Tfile>();
			for(Tpay tpay : tpayWaitAuditList ){
				if(tpay.getPayMethod() == PayMethod.REMITTANCE){
					fileTemp = tfileService.getById(tpay.getIfile());
					tfileWaitAuditList.add(fileTemp);
				}else{
					tfileWaitAuditList.add(null);
				}
			}
		}

		model.addAttribute("tpayAuditedList", tpayAuditedList);
		model.addAttribute("tfileAuditedList", tfileAuditedList);
		model.addAttribute("tpayWaitAuditList", tpayWaitAuditList);
		model.addAttribute("tfileWaitAuditList", tfileWaitAuditList);

		TcustReg tcustReg = null;

		switch (tpayNotify.getPayType()) {
		case BAIL:
			Tagmt tagmt = payNotifyBiz.findTagmtByIagmt(tpayNotify.getIfk());
			List tagmtDetailList = payNotifyBiz.findPdtByIagmt(tagmt.getIagmt());
			model.addAttribute("tagmt", tagmt);
			model.addAttribute("tagmtDetailList", tagmtDetailList);

			tcustReg = this.payNotifyBiz.findTcustReg(tagmt.getIcust());
			model.addAttribute("tcustReg", tcustReg);

			return "/admin/paynotice/agmtPayDetail";
		case LOGISTICS:
			TlogisticsOrd tlogisticsOrd = payNotifyBiz.findTlogisticsOrdById(tpayNotify.getIfk());
			tcustReg = this.payNotifyBiz.findTcustReg(tlogisticsOrd.getIcust());
			
			model.addAttribute("tcustReg", tcustReg);
			model.addAttribute("tlogisticsOrd", tlogisticsOrd);
			return "/admin/paynotice/logisticsPayDetail";
		case ORDER:

			Tord tord = payNotifyBiz.queryTordByIord(tpayNotify.getIfk());
			tcustReg = this.payNotifyBiz.findTcustReg(tord.getIcust());

			model.addAttribute("tcustReg", tcustReg);
			model.addAttribute("tord", tord);

			return "/admin/paynotice/orderPayDetail";
		case WAREHOUSE:

			TwareHouse twareHouse = payNotifyBiz.queryTwareHouseById(tpayNotify.getIfk());
			tcustReg = this.payNotifyBiz.findTcustReg(twareHouse.getIcust());
			List<TwareHouseDetail> twareHouseDetailList = this.payNotifyBiz.queryTwareHouseDetailListById(tpayNotify.getIfk());

			model.addAttribute("tcustReg", tcustReg);
			model.addAttribute("twareHouse", twareHouse);
			model.addAttribute("twareHouseDetailList", twareHouseDetailList);
			return "/admin/paynotice/warehousePayDetail";
		case RENEW_AMT:
			Trenew trenew = payNotifyBiz.findTrenewByIrenew(tpayNotify.getIfk());
			model.addAttribute("trenew", trenew);

			return "/admin/paynotice/renewPayDetail";
		case BAIL_SUPPLEMENT:
			tagmt = payNotifyBiz.findTagmtByIagmt(tpayNotify.getIfk());
			tagmtDetailList = payNotifyBiz.findPdtByIagmt(tagmt.getIagmt());
			model.addAttribute("tagmt", tagmt);
			model.addAttribute("tagmtDetailList", tagmtDetailList);

			tcustReg = this.payNotifyBiz.findTcustReg(tagmt.getIcust());
			model.addAttribute("tcustReg", tcustReg);

			return "/admin/paynotice/depositSuppleDetail";
		default:
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage("状态异常！");
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}

	}

	/**
	 * 到 付款审核页面
	 * 
	 * @param model
	 * @param ipayNotify
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/admin/paynotice/toPaynoticeAudit.do")
	public String toPaynoticeAudit(Model model,
			@RequestParam(required = false) Long ipayNotify) {

		TpayNotify tpayNotify = this.payNotifyBiz
				.queryTpayNotifyByIpayNotify(ipayNotify);
		model.addAttribute("tpayNotify", tpayNotify);

		// 已审核 的凭证
		List<Tpay> tpayAuditedList = null;
		List<Tfile> tfileAuditedList = null;
		tpayAuditedList = payNotifyBiz.queryAuditedPayByfk(tpayNotify.getIfk(),
				tpayNotify.getPayType());
		if (tpayAuditedList != null && tpayAuditedList.size() > 0) {
			Tfile fileTemp = null;
			tfileAuditedList = new ArrayList<Tfile>();
			for (Tpay tpay : tpayAuditedList) {
				if(tpay.getPayMethod() == PayMethod.REMITTANCE){
					fileTemp = tfileService.getById(tpay.getIfile());
					tfileAuditedList.add(fileTemp);
				}else{
					tfileAuditedList.add(null);
				}
			}
		}

		// 末审核的凭证
		List<Tpay> tpayWaitAuditList = null;
		List<Tfile> tfileWaitAuditList = null;
		tpayWaitAuditList = payNotifyBiz.queryWaitAuditPayByfk(tpayNotify.getIfk(), tpayNotify.getPayType());
		if(tpayWaitAuditList != null && tpayWaitAuditList.size() > 0){
			Tfile fileTemp = null;
			tfileWaitAuditList = new ArrayList<Tfile>();
			for(Tpay tpay : tpayWaitAuditList ){
				if(tpay.getPayMethod() == PayMethod.REMITTANCE){
					fileTemp = tfileService.getById(tpay.getIfile());
					tfileWaitAuditList.add(fileTemp);
				}else{
					tfileWaitAuditList.add(null);
				}
			}
		}

		model.addAttribute("tpayAuditedList", tpayAuditedList);
		model.addAttribute("tfileAuditedList", tfileAuditedList);
		model.addAttribute("tpayWaitAuditList", tpayWaitAuditList);
		model.addAttribute("tfileWaitAuditList", tfileWaitAuditList);
		
		TcustReg tcustReg = null;
		Tagmt tagmt = null;
		List tagmtDetailList = null;
		Tord tord = null;
		TlogisticsOrd tlogisticsOrd = null;
		Trenew trenew = null;
		
		Boolean flag = this.payNotifyBiz.vaidatePartPay(tpayNotify);
		switch (tpayNotify.getPayType()) {
		case BAIL:
			tagmt = payNotifyBiz.findTagmtByIagmt(tpayNotify.getIfk());
			tagmtDetailList = payNotifyBiz.findPdtByIagmt(tagmt.getIagmt());
			model.addAttribute("tagmt", tagmt);
			model.addAttribute("tagmtDetailList", tagmtDetailList);

			tcustReg = this.payNotifyBiz.findTcustReg(tagmt.getIcust());
			model.addAttribute("tcustReg", tcustReg);

			model.addAttribute("flag", flag);
			return "/admin/paynotice/agmtPayAudit";
		case LOGISTICS:
			tlogisticsOrd = payNotifyBiz.findTlogisticsOrdById(tpayNotify.getIfk());
			tcustReg = this.payNotifyBiz.findTcustReg(tlogisticsOrd.getIcust());
			
			model.addAttribute("tcustReg", tcustReg);
			model.addAttribute("tlogisticsOrd", tlogisticsOrd);
			model.addAttribute("flag", flag);
			return "/admin/paynotice/logisticsPayAudit";
		case ORDER:

			tord = payNotifyBiz.queryTordByIord(tpayNotify.getIfk());
			tcustReg = this.payNotifyBiz.findTcustReg(tord.getIcust());

			model.addAttribute("tcustReg", tcustReg);
			model.addAttribute("tord", tord);
			model.addAttribute("flag", flag);
			return "/admin/paynotice/orderPayAudit";
		case WAREHOUSE:
			TwareHouse twareHouse = payNotifyBiz.queryTwareHouseById(tpayNotify.getIfk());
			tcustReg = this.payNotifyBiz.findTcustReg(twareHouse.getIcust());
			List<TwareHouseDetail> twareHouseDetailList = this.payNotifyBiz.queryTwareHouseDetailListById(tpayNotify.getIfk());

			model.addAttribute("tcustReg", tcustReg);
			model.addAttribute("twareHouse", twareHouse);
			model.addAttribute("twareHouseDetailList", twareHouseDetailList);
			return "/admin/paynotice/warehousePayAudit";
		case RENEW_AMT:
			trenew = payNotifyBiz.findTrenewByIrenew(tpayNotify.getIfk());
			model.addAttribute("trenew", trenew);
			model.addAttribute("flag", flag);
			return "/admin/paynotice/renewPayAudit";	
		case BAIL_SUPPLEMENT:
			tagmt = payNotifyBiz.findTagmtByIagmt(tpayNotify.getIfk());
			tagmtDetailList = payNotifyBiz.findPdtByIagmt(tagmt.getIagmt());
			model.addAttribute("tagmt", tagmt);
			model.addAttribute("tagmtDetailList", tagmtDetailList);

			tcustReg = this.payNotifyBiz.findTcustReg(tagmt.getIcust());
			model.addAttribute("tcustReg", tcustReg);

			model.addAttribute("flag", flag);
			return "/admin/paynotice/depositSuppleAudit";
		default:
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage("状态异常！");
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
	}

	/**
	 * 填写不通过理由
	 * 
	 * @param model
	 * @param pageNum
	 * @param numPerPage
	 * @param ipayNotify
	 * @return
	 */
	@RequestMapping("/admin/paynotice/toRefuseReason.do")
	public String toRefuseReason(Model model,
			@RequestParam(required = false) Long ipayNotify) {

		model.addAttribute("ipayNotify", ipayNotify);
		return "/admin/paynotice/refuseReason";
	}

	/**
	 * 付款审核 通过
	 * 
	 * @param model
	 * @param pageNum
	 * @param numPerPage
	 * @param ipayNotify
	 * @return
	 */
	@RequestMapping("/admin/paynotice/paynoticeAudit.do")
	public String paynoticeAudit(Model model,
			@RequestParam Long ipayNotify,
			@RequestParam PayStatus payStatus) {
		
		TpayNotify tpayNotify = this.payNotifyBiz
				.queryTpayNotifyByIpayNotify(ipayNotify);

		try {
			TuserSession tuserSession = (TuserSession) AppSessionFilter.getAppSession();
			this.payNotifyBiz.payAudit(tpayNotify, payStatus, tuserSession.getTuser());
			
			switch (tpayNotify.getPayType()) {
			case BAIL:
				
				ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
				if (PayStatus.PART_PAY == payStatus) {
					ajaxResult.setMessage("协议付款审核部分支付");
				} else {
					ajaxResult.setMessage("协议付款审核已通过");
				}
				ajaxResult.setNavTabId(NAV_TAB_ID);
				ajaxResult
				.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
				model.addAttribute(ajaxResult);
				
				return "/common/ajaxResult";
			case LOGISTICS:
				ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
				if (PayStatus.PART_PAY == payStatus) {
					ajaxResult.setMessage("物流单付款审核部分支付");
				} else {
					ajaxResult.setMessage("物流单付款审核已通过");
				}
				ajaxResult.setNavTabId(NAV_TAB_ID);
				ajaxResult
				.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
				model.addAttribute(ajaxResult);
				
				return "/common/ajaxResult";
			case ORDER:
				ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
				if (PayStatus.PART_PAY == payStatus) {
					ajaxResult.setMessage("订单付款审核部分支付");
				} else {
					ajaxResult.setMessage("订单付款审核已通过");
				}
				ajaxResult.setNavTabId(NAV_TAB_ID);
				ajaxResult
				.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
				model.addAttribute(ajaxResult);
				
				return "/common/ajaxResult";
			case WAREHOUSE:
				ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
				if (PayStatus.PART_PAY == payStatus) {
					ajaxResult.setMessage("仓管费付款审核部分支付");
				} else {
					ajaxResult.setMessage("仓管费付款审核已通过");
				}
				ajaxResult.setNavTabId(NAV_TAB_ID);
				ajaxResult
				.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
				model.addAttribute(ajaxResult);
				
				return "/common/ajaxResult";
			case RENEW_AMT:
				ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
				if (PayStatus.PART_PAY == payStatus) {
					ajaxResult.setMessage("续保付款审核部分支付");
				} else {
					ajaxResult.setMessage("续保付款审核已通过");
				}
				ajaxResult.setNavTabId(NAV_TAB_ID);
				ajaxResult
				.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
				model.addAttribute(ajaxResult);
				
				return "/common/ajaxResult";
			case BAIL_SUPPLEMENT:
				
				ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
				if (PayStatus.PART_PAY == payStatus) {
					ajaxResult.setMessage("保证金补交付款审核部分支付");
				} else {
					ajaxResult.setMessage("保证金补交付款审核已通过");
				}
				ajaxResult.setNavTabId(NAV_TAB_ID);
				ajaxResult
				.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
				model.addAttribute(ajaxResult);
				
				return "/common/ajaxResult";
			default:
				ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
				ajaxResult.setMessage("状态异常！");
				model.addAttribute(ajaxResult);
				return "/common/ajaxResult";
			}
		} catch (BizException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
			ajaxResult.setNavTabId(NAV_TAB_ID);
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
	}

	/**
	 * 付款审核 不通过
	 * @param model
	 * @param ipayNotify
	 * @param refuseReason
	 * @return
	 */
	@RequestMapping("/admin/paynotice/paynoticeAuditNo.do")
	public String paynoticeAuditNo(Model model,
			@RequestParam Long ipayNotify,
			@RequestParam String refuseReason) {

		TpayNotify tpayNotify = this.payNotifyBiz.queryTpayNotifyByIpayNotify(ipayNotify);
		try {
			TuserSession tuserSession = (TuserSession) AppSessionFilter.getAppSession();
			this.payNotifyBiz.payAuditNo(tpayNotify, refuseReason, tuserSession.getTuser());

			switch (tpayNotify.getPayType()) {
			case BAIL:

				ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
				ajaxResult.setMessage("协议付款审核未通过");
				ajaxResult.setNavTabId(NAV_TAB_ID);
				ajaxResult
						.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
				model.addAttribute(ajaxResult);

				return "/common/ajaxResult";
			case LOGISTICS:
				ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
				ajaxResult.setMessage("物流单付款审核未通过");
				ajaxResult.setNavTabId(NAV_TAB_ID);
				ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
				model.addAttribute(ajaxResult);

				return "/common/ajaxResult";
			case ORDER:
				ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
				ajaxResult.setMessage("订单付款审核未通过");
				ajaxResult.setNavTabId(NAV_TAB_ID);
				ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
				model.addAttribute(ajaxResult);

				return "/common/ajaxResult";
			case WAREHOUSE:
				ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
				ajaxResult.setMessage("仓管费审核未通过");
				ajaxResult.setNavTabId(NAV_TAB_ID);
				ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
				model.addAttribute(ajaxResult);

				return "/common/ajaxResult";
			case RENEW_AMT:
				ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
				ajaxResult.setMessage("续保费审核未通过");
				ajaxResult.setNavTabId(NAV_TAB_ID);
				ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
				model.addAttribute(ajaxResult);

				return "/common/ajaxResult";
			case BAIL_SUPPLEMENT:
				ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
				ajaxResult.setMessage("保证金补交付款审核未通过");
				ajaxResult.setNavTabId(NAV_TAB_ID);
				ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
				model.addAttribute(ajaxResult);
				
				return "/common/ajaxResult";
			default:
				ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
				ajaxResult.setMessage("状态异常！");
				model.addAttribute(ajaxResult);
				return "/common/ajaxResult";
			}
		} catch (BizException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
			model.addAttribute(ajaxResult);
			ajaxResult.setNavTabId(NAV_TAB_ID);
			ajaxResult.setCallbackType("");
			return "/common/ajaxResult";
		}
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("/admin/paynotice/paynoticeList.do")
	public String paynoticeList(Model model,
			@RequestParam(required = false) Integer pageNum,
			@RequestParam(required = false) Integer numPerPage,
			@RequestParam(required = false) String payType,
			@RequestParam(required = false) String payNotifyStatus,
			@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate,
			@RequestParam(required = false) String name) {

		Page page = new Page();
		page.setCount(true);
		page.setCapacity(numPerPage == null ? CAPACITY : numPerPage);
		page.setPageOffset(pageNum == null ? PAGE_OFF_SET : pageNum - 1);
		

		List tpayNotifyList = payNotifyBiz.findListByInfo(page, payType,
				payNotifyStatus, startDate, endDate, name);

		model.addAttribute("tpayNotifyList", tpayNotifyList);
		model.addAttribute("payType", payType);
		model.addAttribute("payNotifyStatus", payNotifyStatus);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("name", name);

		model.addAttribute(NUM_PER_PAGE, page.getCapacity());
		model.addAttribute(PAGE_NUM, pageNum);
		model.addAttribute(TOTAL_COUNT, page.getRecAmt());
		return "/admin/paynotice/paynoticeList";
	}

	@RequestMapping("/admin/paynotice/downLoadPayNotify.do")
	public void downeLoadPayNotify(
			@RequestParam(required = false) String payType,
			@RequestParam(required = false) String payNotifyStatus,
			@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate,
			@RequestParam(required = false) Long icust,
			HttpServletResponse response) throws ParseException {

		TpayNotify notify = new TpayNotify();
		if (StringUtils.isNotBlank(payType)) {
			notify.setPayType(PayType.valueOf(payType));
		}
		if (StringUtils.isNotBlank(payNotifyStatus)) {
			notify.setPayNotifyStatus(PayStatus.valueOf(payNotifyStatus));
		}
		if (StringUtils.isNotBlank(startDate)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date start = sdf.parse(startDate);
			notify.setGenTime(start);
		}
		if (StringUtils.isNotBlank(endDate)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date end  = sdf.parse(endDate);
			notify.setGenTime(end);
		}
		if(icust!=null){
			notify.setIcust(icust);
		}
		
		List<TpayNotify> notifyList = payNotifyBiz.findBySelect(notify);
		File file = null;
		try {
			file = fileDownload.PayNotifyInfoDown(notifyList);
			fileUpDownService.download(file, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
