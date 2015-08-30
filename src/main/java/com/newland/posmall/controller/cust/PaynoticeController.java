package com.newland.posmall.controller.cust;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.TcustSession;
import org.ohuyo.rapid.base.entity.AjaxResult;
import org.ohuyo.rapid.base.entity.Tfile;
import org.ohuyo.rapid.base.service.FileUpDownService;
import org.ohuyo.rapid.base.service.TsysParamService;
import org.ohuyo.rapid.base.servlet.AppSessionFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.newland.posmall.base.exception.BizException;
import com.newland.posmall.bean.basebusi.Tagmt;
import com.newland.posmall.bean.basebusi.TlogisticsOrd;
import com.newland.posmall.bean.basebusi.Tord;
import com.newland.posmall.bean.basebusi.TordDetail;
import com.newland.posmall.bean.basebusi.Tpay;
import com.newland.posmall.bean.basebusi.TpayNotify;
import com.newland.posmall.bean.basebusi.TwareHouse;
import com.newland.posmall.bean.basebusi.TwareHouseDetail;
import com.newland.posmall.bean.customer.Tcust;
import com.newland.posmall.bean.customer.Trenew;
import com.newland.posmall.bean.customer.TrenewDetail;
import com.newland.posmall.bean.dict.PayType;
import com.newland.posmall.bean.model4page.Agmt4Page;
import com.newland.posmall.biz.cust.Agmt4PageBiz;
import com.newland.posmall.biz.cust.AgreementBiz;
import com.newland.posmall.biz.cust.CustOrdBiz;
import com.newland.posmall.biz.cust.CustRenewBiz;
import com.newland.posmall.biz.cust.Logistics4PageBiz;
import com.newland.posmall.biz.cust.LogisticsBiz;
import com.newland.posmall.biz.cust.LogisticsOrderWithMultipleAddrs4PageBiz;
import com.newland.posmall.biz.cust.PaynoticeBiz;
import com.newland.posmall.controller.BaseController;
import com.newland.posmall.controller.cust.model.LogisticsOrd4Page;
import com.newland.posmall.controller.cust.model.LogisticsOrderWithMultipleAddrs4Page;
import com.newland.posmall.controller.cust.model.OrderPay4Page;

@Scope("prototype")
@Controller("cust.paynotice")
public class PaynoticeController extends BaseController {

	private static Logger logger = LoggerFactory
			.getLogger(PaynoticeController.class);

	private static final String NAV_TAB_ID = "FKTZSGL";
	
	@Resource
	private PaynoticeBiz paynoticeBiz;
	
	@Resource
	private AgreementBiz agreementBiz;
	
	@Autowired
	private FileUpDownService fileUpDownService;
	
	@Resource
	private CustOrdBiz custOrdBiz;
	
	@Resource
	private TsysParamService tsysParamService;
	
	@Resource
	private LogisticsBiz logisticsBiz;
	
	@Resource
	private Logistics4PageBiz logistics4PageBiz;
	
	@Resource
	private LogisticsOrderWithMultipleAddrs4PageBiz logisticsOrderWithMultipleAddrs4PageBiz;

	@Resource
	private Agmt4PageBiz agmt4PageBiz;
	
	@Resource
	private CustRenewBiz custRenewBiz;
	
	/**
	 * 查詢付款通知書列表
	 * 
	 * @param payType
	 * @param payNotifyStatus
	 * @param startDate
	 * @param endDate
	 * @param pageNum
	 * @param numPerPage
	 * @param model
	 * @return
	 */
	@RequestMapping("/cust/paynotice/paynoticeList.do")
	public String paynoticeList(String payType, String payNotifyStatus,
			String startDate, String endDate, Integer pageNum,
			Integer numPerPage, Model model) {
		Page page = new Page();
		page.setCount(true);
		page.setCapacity(numPerPage == null ? CAPACITY : numPerPage);
		page.setPageOffset(pageNum == null ? PAGE_OFF_SET : pageNum - 1);

		TcustSession session = (TcustSession) AppSessionFilter.getAppSession();
		List<?> tpayNotifyList = this.paynoticeBiz.findTpayNotifyByPage(page,
				payType, payNotifyStatus, startDate, endDate,
				session.getTcust());

		model.addAttribute("tpayNotifyList", tpayNotifyList);
		model.addAttribute("payType", payType);
		model.addAttribute("payNotifyStatus", payNotifyStatus);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);

		model.addAttribute(NUM_PER_PAGE, page.getCapacity());
		model.addAttribute(PAGE_NUM, pageNum);
		model.addAttribute(TOTAL_COUNT, page.getRecAmt());
		return "/cust/paynotice/paynoticeList";
	}

	/**
	 * 查看付款通知书详情
	 * @param payType
	 * @param ipayNotify
	 * @param model
	 * @return
	 */
	@RequestMapping("/cust/paynotice/paynoticeDetail.do")
	public String paynoticeDetail(PayType payType, Long ipayNotify, Model model) {
		TpayNotify tpayNotify = this.paynoticeBiz.queryTpayNotifyById(ipayNotify);
		switch (payType) {
		case BAIL:
			Tagmt tagmt = this.agreementBiz.findById(tpayNotify.getIfk());
			Agmt4Page agmt4Page = this.agmt4PageBiz.findAgmt4Page(tagmt);
			model.addAttribute("agmt4Page", agmt4Page);
			return "/cust/paynotice/depositDetail";
		case LOGISTICS:
			TlogisticsOrd tlogisticsOrd = this.logisticsBiz.findLogisticsOrdById(tpayNotify.getIfk());
			if(tlogisticsOrd.getIfile() != -1) { //批量
				TcustSession tcustSession = (TcustSession) AppSessionFilter.getAppSession();
				LogisticsOrderWithMultipleAddrs4Page p = this.logisticsOrderWithMultipleAddrs4PageBiz.
						getLogisticsOrderWithMultipleAddrs4Page(tlogisticsOrd.getIlogisticsOrd(), tcustSession.getTcust());
				
				model.addAttribute("p", p);
				model.addAttribute("tlogisticsOrd", tlogisticsOrd);
				return "/cust/paynotice/logisticsOrdDetailMore";
			}else {
				LogisticsOrd4Page logisticsOrd4Page = this.logistics4PageBiz.findLogisticsOrd4Page(tlogisticsOrd);
				this.logisticsBiz.setTordAlone(tlogisticsOrd);
				model.addAttribute("logisticsOrd4Page", logisticsOrd4Page);
				model.addAttribute("tlogisticsOrd", tlogisticsOrd);
				return "/cust/paynotice/logisticsDetail";				
			}
		case ORDER:
			Tord tord = this.custOrdBiz.queryTordByIord(tpayNotify.getIfk());
			List<TordDetail> tordDetailList = this.custOrdBiz.queryTordDetailList(tpayNotify.getIfk());
			model.addAttribute("tord", tord);
			model.addAttribute("tordDetailList", tordDetailList);
			return "/cust/paynotice/orderFormDetail";
		case WAREHOUSE:
			TwareHouse twareHouse = this.paynoticeBiz.queryTwareHouseById(tpayNotify.getIfk());
			List<TwareHouseDetail> twareHouseDetailList = this.paynoticeBiz.queryTwareHouseDetailListById(tpayNotify.getIfk());
			model.addAttribute("twareHouse", twareHouse);
			model.addAttribute("twareHouseDetailList", twareHouseDetailList);
			return "/cust/paynotice/warehouseDetail";
		case RENEW_AMT:
			Trenew trenew = this.custRenewBiz.queryById(tpayNotify.getIfk());
			List<TrenewDetail> detailList = this.custRenewBiz.queryTrenewDetailList(tpayNotify.getIfk());
			model.addAttribute("trenew", trenew);
			model.addAttribute("detailList", detailList);
			return "/cust/paynotice/renewDetail";
		case BAIL_SUPPLEMENT:
			tagmt = this.agreementBiz.findById(tpayNotify.getIfk());
			agmt4Page = this.agmt4PageBiz.findAgmt4Page(tagmt);
			
			model.addAttribute("agmt4Page", agmt4Page);
			return "/cust/paynotice/depositSupplementDetail";
		default:
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage("通知书类型异常！");
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
	}
	
	/**
	 * 查看付款明细
	 * @param ipayNotify
	 * @param model
	 * @return
	 */
	@RequestMapping("/cust/paynotice/payDetail.do")
	public String payDetail(Long ipayNotify, Model model) {
		TpayNotify tpayNotify = this.paynoticeBiz.queryTpayNotifyById(ipayNotify);
		if(tpayNotify == null) {
			model.addAttribute("errMsg", "查询不到付款通知书！");
			return "/cust/paynotice/payDetail";
		}
		model.addAttribute("tpayNotify", tpayNotify);
		switch (tpayNotify.getPayType()) {
		case BAIL:
			Tagmt tagmt = this.agreementBiz.findById(tpayNotify.getIfk());
			model.addAttribute("tagmt", tagmt);
			break;
		case LOGISTICS:
			TlogisticsOrd tlogisticsOrd = this.logisticsBiz.findLogisticsOrdById(tpayNotify.getIfk());
			model.addAttribute("tlogisticsOrd", tlogisticsOrd);
			break;
		case ORDER:
			Tord tord = this.custOrdBiz.queryTordByIord(tpayNotify.getIfk());
			model.addAttribute("tord", tord);
			break;
		case WAREHOUSE:
			TwareHouse twareHouse = this.paynoticeBiz.queryTwareHouseById(tpayNotify.getIfk());
			model.addAttribute("twareHouse", twareHouse);
			break;
		case RENEW_AMT:
			Trenew trenew = this.custRenewBiz.queryById(tpayNotify.getIfk());
			model.addAttribute("trenew", trenew);
			break;
		case BAIL_SUPPLEMENT:
			tagmt = this.agreementBiz.findById(tpayNotify.getIfk());
			model.addAttribute("tagmt", tagmt);
			break;
		default:
			model.addAttribute("errMsg", "通知书类型异常！");
			break;
		}
		List<Tpay> tpayList = this.paynoticeBiz.queryTpayByTapyNofity(tpayNotify);
		if(tpayList != null && tpayList.size() >= 1) {
			this.paynoticeBiz.setTfile4Tpay(tpayList);
			model.addAttribute("tpayList", tpayList);
		}
		return "/cust/paynotice/payDetail";
	}
	
	/**
	 * 跳转到上传付款凭证页面
	 * @param ipayNotify
	 * @param model
	 * @return
	 */
	@RequestMapping("/cust/file/toUploadVoucher.do")
	public String toUploadVoucher(Long ipayNotify, Model model) {
		TcustSession session = (TcustSession) AppSessionFilter.getAppSession();
		model.addAttribute("tcustReg", session.getTcustReg());
		TpayNotify tpayNotify = this.paynoticeBiz.queryTpayNotifyById(ipayNotify);
		if(tpayNotify == null) {
			model.addAttribute("errMsg", "查询不到付款通知书！");
			return "/cust/paynotice/depositPay";
		}
		Tagmt tagmt = null;
		Tord tord = null;
		model.addAttribute("tpayNotify", tpayNotify);
		switch (tpayNotify.getPayType()) {
		case BAIL:
			tagmt = this.agreementBiz.findById(tpayNotify.getIfk());
			model.addAttribute("tagmt", tagmt);
			return "/cust/paynotice/depositPay";
		case LOGISTICS:
			TlogisticsOrd tlogisticsOrd = this.logisticsBiz.findLogisticsOrdById(tpayNotify.getIfk());
			tord = this.custOrdBiz.queryTordByIord(tlogisticsOrd.getIord());
			tagmt = this.agreementBiz.findById(tord.getIagmt());
			BigDecimal depositUsed4Logistics = null; //物流费用支付使用保证金
			BigDecimal freight = tlogisticsOrd.getLogisticsFreight();
			if(freight != null && freight.compareTo(BigDecimal.ZERO) != 0) {
				if(tagmt.getRemainDeposit().compareTo(new BigDecimal(0)) <= 0) { //剩余保证金小于等于0
					depositUsed4Logistics = new BigDecimal(0.00).setScale(2, RoundingMode.HALF_UP);
				}else { //理论上不存在，因为如果还有剩余保证金，在审核时已经自动支付
					if(freight.compareTo(tagmt.getRemainDeposit()) > 0) {
						depositUsed4Logistics = tagmt.getRemainDeposit();
					}else {
						depositUsed4Logistics = freight;
					}
				}
				model.addAttribute("tagmt", tagmt);
				model.addAttribute("depositUsed4Logistics", depositUsed4Logistics);
				model.addAttribute("tlogisticsOrd", tlogisticsOrd);
			}else {
				model.addAttribute("errMsg", "物流费用为0");
			}
			return "/cust/paynotice/logisticsPay";
		case ORDER:
			tord = this.custOrdBiz.queryTordByIord(tpayNotify.getIfk());
			tagmt = this.agreementBiz.findById(tord.getIagmt());
			
			BigDecimal depositUsed4Tord = null;
			if(this.paynoticeBiz.canUseDeposit4Order(tpayNotify)) {
				depositUsed4Tord = this.paynoticeBiz.getDepositUse4Tord(tord);				
			}else {
				depositUsed4Tord = this.paynoticeBiz.getDepositPaid4Tord(tpayNotify);
			}
			
			// 主要是针对协议变更存在多余保证金的情况，其他情况 useRedundantDeposit = 0，不影响
			BigDecimal useRedundantDeposit = this.paynoticeBiz.getRedundantDeposit4Tord(tord, 
					depositUsed4Tord, tpayNotify.getHavepayAmt());
			
			BigDecimal remainShouldPay = tord.getAmt()
					.subtract(depositUsed4Tord)
					.subtract(useRedundantDeposit)
					.subtract(tpayNotify.getHavepayAmt())
					.setScale(2, RoundingMode.HALF_UP);
			
			tord.setDepositUsed4Tord(depositUsed4Tord); //保证金抵扣
			tord.setHavePaid(tpayNotify.getHavepayAmt());
			tord.setRemainShouldPay(remainShouldPay);
			
			model.addAttribute("tagmt", tagmt);
			model.addAttribute("tord", tord);
			model.addAttribute("useRedundantDeposit", useRedundantDeposit);
			return "/cust/paynotice/orderFormPay";
		case WAREHOUSE:
			TwareHouse twareHouse = this.paynoticeBiz.queryTwareHouseById(tpayNotify.getIfk());
			model.addAttribute("twareHouse", twareHouse);
			return "/cust/paynotice/warehousePay";
		case RENEW_AMT:
			Trenew trenew = this.custRenewBiz.queryById(tpayNotify.getIfk());
			model.addAttribute("trenew", trenew);
			return "/cust/paynotice/renewPay";
		case BAIL_SUPPLEMENT:
			tagmt = this.agreementBiz.findById(tpayNotify.getIfk());
			model.addAttribute("tagmt", tagmt);
			return "/cust/paynotice/depositSupplementPay";
		default:
			model.addAttribute("errMsg", "付款通知书类型异常！");
			return "/cust/paynotice/depositPay";
		}
	}
	
	
	/**
	 * 支付保证金
	 * @param tpayNotify
	 * @param tpay
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/cust/paynotice/pay4Deposit.do")
	public String pay4Deposit(TpayNotify tpayNotify, Tpay tpay, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		TcustSession session = (TcustSession) AppSessionFilter.getAppSession();
		Tcust tcust = session.getTcust();
		Map<String, Tfile> files = null;
		// 1、上传文件
		try {
			String[] limitExts = {"bmp", "png", "jpg", "jpeg", "jpe"}; 
			String[] limitContentType = {"image/bmp", "image/png", "image/jpeg", "image/x-png", "image/pjpeg"};
			String limitString = this.tsysParamService.getTsysParam("OTHER_CONF", "UPLOAD_FILE_LIMIT_SIZE").getValue();
			long limitSize = new Long(Integer.valueOf(limitString) * 1024 * 1024); //10M
			files = this.fileUpDownService.upload(request, response, limitExts, limitContentType, limitSize);
		} catch (Exception e) {
			logger.error("上传付款凭证异常!", e);
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMessage());
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
		// 2、添加Tpay、TpayDetail，修改TpayNotify
		try {
			this.paynoticeBiz.pay4Deposit(tpayNotify, files.get("voucherFile"), tcust, tpay);
		} catch (BizException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		} catch (Exception e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMessage());
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}

		ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
		ajaxResult.setMessage(AjaxResult.MSG_OK);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		ajaxResult.setNavTabId(NAV_TAB_ID);
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
	
	/**
	 * 订单付款
	 * @param orderPay4Page
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/cust/paynotice/pay4Order.do")
	public String pay4Order(OrderPay4Page orderPay4Page, HttpServletRequest request, 
			HttpServletResponse response, Model model) {
		TcustSession session = (TcustSession) AppSessionFilter.getAppSession();
		Tcust tcust = session.getTcust();
		Map<String, Tfile> files = null;
		// 1、上传文件
		try {
			String[] limitExts = {"bmp", "png", "jpg", "jpeg", "jpe"}; 
			String[] limitContentType = {"image/bmp", "image/png", "image/jpeg", "image/x-png", "image/pjpeg"};
			String limitString = this.tsysParamService.getTsysParam("OTHER_CONF", "UPLOAD_FILE_LIMIT_SIZE").getValue();
			long limitSize = new Long(Integer.valueOf(limitString) * 1024 * 1024); //10M
			files = this.fileUpDownService.upload(request, response, limitExts, limitContentType, limitSize);
		} catch (Exception e) {
			logger.error("上传付款凭证异常!", e);
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMessage());
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
		// 2、添加Tpay、TpayDetail，修改TpayNotify
		try {
			this.paynoticeBiz.pay4Order(files.get("voucherFile"), tcust, orderPay4Page);
		} catch (BizException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setNavTabId("FKTZSGL_SCFKPZ");
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		} catch (Exception e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage("操作异常：" + e);
			ajaxResult.setNavTabId("FKTZSGL_SCFKPZ");
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}

		ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
		ajaxResult.setMessage(AjaxResult.MSG_OK);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		ajaxResult.setNavTabId(NAV_TAB_ID);
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
	
	/**
	 * 物流费支付
	 * @param ipayNotify
	 * @param tpay
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/cust/paynotice/pay4Logistics.do")
	public String pay4Logistics(@RequestParam(required=false) Long ipayNotify, Tpay tpay, HttpServletRequest request, 
			HttpServletResponse response, Model model) {
		TcustSession session = (TcustSession) AppSessionFilter.getAppSession();
		Tcust tcust = session.getTcust();
		Map<String, Tfile> files = null;
		// 1、上传文件
		try {
			String[] limitExts = {"bmp", "png", "jpg", "jpeg", "jpe"}; 
			String[] limitContentType = {"image/bmp", "image/png", "image/jpeg", "image/x-png", "image/pjpeg"};
			String limitString = this.tsysParamService.getTsysParam("OTHER_CONF", "UPLOAD_FILE_LIMIT_SIZE").getValue();
			long limitSize = new Long(Integer.valueOf(limitString) * 1024 * 1024); //10M
			files = this.fileUpDownService.upload(request, response, limitExts, limitContentType, limitSize);
		} catch (Exception e) {
			logger.error("上传付款凭证异常!", e);
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMessage());
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
		// 2、添加Tpay、TpayDetail，修改TpayNotify
		try {
			this.paynoticeBiz.pay4Logistics(files.get("voucherFile"), ipayNotify, tpay, tcust);
		} catch (BizException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		} catch (Exception e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMessage());
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}

		ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
		ajaxResult.setMessage(AjaxResult.MSG_OK);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		ajaxResult.setNavTabId(NAV_TAB_ID);
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
	
	/**
	 * 支付仓管费
	 * @param tpayNotify
	 * @param tpay
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/cust/paynotice/pay4Warehouse.do")
	public String pay4Warehouse(TpayNotify tpayNotify, Tpay tpay, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		TcustSession session = (TcustSession) AppSessionFilter.getAppSession();
		Tcust tcust = session.getTcust();
		Map<String, Tfile> files = null;
		// 1、上传文件
		try {
			String[] limitExts = {"bmp", "png", "jpg", "jpeg", "jpe"}; 
			String[] limitContentType = {"image/bmp", "image/png", "image/jpeg", "image/x-png", "image/pjpeg"};
			String limitString = this.tsysParamService.getTsysParam("OTHER_CONF", "UPLOAD_FILE_LIMIT_SIZE").getValue();
			long limitSize = new Long(Integer.valueOf(limitString) * 1024 * 1024); //10M
			files = this.fileUpDownService.upload(request, response, limitExts, limitContentType, limitSize);
		} catch (Exception e) {
			logger.error("上传付款凭证异常!", e);
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMessage());
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
		// 2、添加Tpay、TpayDetail，修改TpayNotify
		try {
			this.paynoticeBiz.pay4Warehouse(tpayNotify, files.get("voucherFile"), tcust, tpay);
		} catch (BizException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		} catch (Exception e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMessage());
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}

		ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
		ajaxResult.setMessage(AjaxResult.MSG_OK);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		ajaxResult.setNavTabId(NAV_TAB_ID);
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
	
	/**
	 * 支付续保费用
	 * @param tpayNotify
	 * @param tpay
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/cust/paynotice/pay4RenewAmt.do")
	public String pay4RenewAmt(TpayNotify tpayNotify, Tpay tpay, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		TcustSession session = (TcustSession) AppSessionFilter.getAppSession();
		Tcust tcust = session.getTcust();
		Map<String, Tfile> files = null;
		// 1、上传文件
		try {
			String[] limitExts = {"bmp", "png", "jpg", "jpeg", "jpe"}; 
			String[] limitContentType = {"image/bmp", "image/png", "image/jpeg", "image/x-png", "image/pjpeg"};
			String limitString = this.tsysParamService.getTsysParam("OTHER_CONF", "UPLOAD_FILE_LIMIT_SIZE").getValue();
			long limitSize = new Long(Integer.valueOf(limitString) * 1024 * 1024); //10M
			files = this.fileUpDownService.upload(request, response, limitExts, limitContentType, limitSize);
		} catch (Exception e) {
			logger.error("上传付款凭证异常!", e);
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMessage());
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
		// 2、添加Tpay、TpayDetail，修改TpayNotify
		try {
			this.paynoticeBiz.pay4RenewAmt(tpayNotify, files.get("voucherFile"), tcust, tpay);
		} catch (BizException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		} catch (Exception e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMessage());
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}

		ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
		ajaxResult.setMessage(AjaxResult.MSG_OK);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		ajaxResult.setNavTabId(NAV_TAB_ID);
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
	
	@RequestMapping("/cust/paynotice/pay4SupplementDeposit.do")
	public String pay4SupplementDeposit(TpayNotify tpayNotify, Tpay tpay, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		TcustSession session = (TcustSession) AppSessionFilter.getAppSession();
		Tcust tcust = session.getTcust();
		Map<String, Tfile> files = null;
		// 1、上传文件
		try {
			String[] limitExts = {"bmp", "png", "jpg", "jpeg", "jpe"}; 
			String[] limitContentType = {"image/bmp", "image/png", "image/jpeg", "image/x-png", "image/pjpeg"};
			String limitString = this.tsysParamService.getTsysParam("OTHER_CONF", "UPLOAD_FILE_LIMIT_SIZE").getValue();
			long limitSize = new Long(Integer.valueOf(limitString) * 1024 * 1024); //10M
			files = this.fileUpDownService.upload(request, response, limitExts, limitContentType, limitSize);
		} catch (Exception e) {
			logger.error("上传付款凭证异常!", e);
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMessage());
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
		// 2、添加Tpay、TpayDetail，修改TpayNotify
		try {
			this.paynoticeBiz.pay4SupplementDeposit(tpayNotify, files.get("voucherFile"), tcust, tpay);
		} catch (BizException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		} catch (Exception e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMessage());
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}

		ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
		ajaxResult.setMessage(AjaxResult.MSG_OK);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		ajaxResult.setNavTabId(NAV_TAB_ID);
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
}
