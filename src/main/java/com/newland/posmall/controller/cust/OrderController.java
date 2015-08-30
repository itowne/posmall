package com.newland.posmall.controller.cust;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.TcustSession;
import org.ohuyo.rapid.base.entity.AjaxResult;
import org.ohuyo.rapid.base.entity.TsysParam;
import org.ohuyo.rapid.base.service.TsysParamService;
import org.ohuyo.rapid.base.servlet.AppSessionFilter;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.newland.posmall.base.exception.BizException;
import com.newland.posmall.bean.basebusi.OrdPdtModel4Page;
import com.newland.posmall.bean.basebusi.OrdPdtModelList4Page;
import com.newland.posmall.bean.basebusi.Tagmt;
import com.newland.posmall.bean.basebusi.Tord;
import com.newland.posmall.bean.basebusi.TordDetail;
import com.newland.posmall.bean.basebusi.condition.TordCondition;
import com.newland.posmall.bean.customer.TcustReg;
import com.newland.posmall.bean.dict.OrdStatus;
import com.newland.posmall.bean.dict.PayStatus;
import com.newland.posmall.biz.cust.CustOrdBiz;
import com.newland.posmall.controller.BaseController;

@Scope("prototype")
@Controller("cust.order")
public class OrderController extends BaseController{

	private static final String NAV_TAB_ID = "POSDDGL";
	
	@Resource
	private CustOrdBiz custOrdBiz;
	
	@Resource
	private TsysParamService tsysParamService;
	
	/**
	 * 订单列表
	 * @param model
	 * @param pageNum
	 * @param numPerPage
	 * @param ordStatus
	 * @param payStatus
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping("/cust/ord/ordList.do")
	public String orderList( Model model, @RequestParam(required=false) Integer pageNum,
			@RequestParam(required=false) Integer numPerPage,
			@RequestParam(required=false) String ordStatus, 
			@RequestParam(required=false) String payStatus, 
			@DateTimeFormat(pattern = "yyyy-MM-dd") Date beginTime, 
			@DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime){
		
		Page page = new Page();
		page.setCount(true);
		page.setCapacity(numPerPage == null ? CAPACITY : numPerPage);
		page.setPageOffset(pageNum == null ? PAGE_OFF_SET : pageNum - 1);
		
		TordCondition tordCondition = new TordCondition();
		if(StringUtils.isNotBlank(ordStatus)){
			tordCondition.setOrdStatus(OrdStatus.valueOf(ordStatus));
		}
		if(StringUtils.isNotBlank(payStatus)){
			tordCondition.setPayStatus(PayStatus.valueOf(payStatus));
		}
		if(beginTime!=null){
			tordCondition.setBeginTime(beginTime);
		}
		if(endTime!=null){
			Calendar cal = Calendar.getInstance();
			cal.setTime(endTime);
			cal.set(Calendar.HOUR, 0);       
			cal.set(Calendar.SECOND, 0);       
			cal.set(Calendar.MINUTE, 0);
			cal.add(Calendar.DATE, 1);
			tordCondition.setEndTime(cal.getTime());
		}
		TcustSession tcustSession = (TcustSession) AppSessionFilter.getAppSession();
		Long icust = tcustSession.getTcust().getIcust();
		tordCondition.setIcust(icust);
		
		List<Tord> tordList = this.custOrdBiz.findListByCon(tordCondition, page);

		model.addAttribute("tordList",tordList);
		model.addAttribute("ordStatus", ordStatus);
		model.addAttribute("payStatus", payStatus);
		model.addAttribute("beginTime", beginTime);
		model.addAttribute("endTime", endTime);
		
		model.addAttribute(NUM_PER_PAGE, page.getCapacity());
		model.addAttribute(PAGE_NUM, pageNum);
		model.addAttribute(TOTAL_COUNT, page.getRecAmt());
		return "/cust/ord/ordList";
	}
	
	/**
	 * 点单第一步
	 * 跳转至协议选择页面
	 */
	@RequestMapping("/cust/ord/selectTagmt4Order.do")
	public String selectTagmt4Order(Model model){
		TcustSession tcustSession = (TcustSession) AppSessionFilter.getAppSession();
		TcustReg tcustReg = tcustSession.getTcustReg();//当前登陆客户的注册信息
		List<Tagmt> tagmtList = this.custOrdBiz.queryTagmtList(tcustSession.getTcust());
		model.addAttribute("tagmtList", tagmtList);
		model.addAttribute("now", new Date());
		model.addAttribute("tcustReg", tcustReg);
		return "/cust/ord/selectTagmt4Order";
	}
	
	/**
	 * 点单第二步
	 * 跳转至点单页面
	 */
	@RequestMapping("/cust/ord/toOrdAdd.do")
	public String toOrdAdd(@RequestParam(required = false) Long iagmt,Model model){
		Tagmt tagmt = this.custOrdBiz.queryAgmtById(iagmt);
		if(tagmt == null) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage("订单协议为空异常！");
			model.addAttribute(ajaxResult);
			return "/cust/ord/ordAdd2";
		}
		
		List<OrdPdtModel4Page> ordPdtModel4PageList = null;
		try {
			ordPdtModel4PageList = this.custOrdBiz.queryObjectArr(tagmt);
		} catch (BizException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
			model.addAttribute(ajaxResult);
			return "/cust/ord/ordAdd2";
		}
		
		BigDecimal depositScale4Tord = new BigDecimal(0.1); //订单支付保证金使用默认比例
		TsysParam param = this.tsysParamService.getTsysParam("OTHER_CONF", "DEPOSIT_SCALE_4_TORD"); //订单支付保证金使用比例
		if(param != null && StringUtils.isNotBlank(param.getValue())) {
			String scale = param.getValue();
			try {
				depositScale4Tord = new BigDecimal(new Float(
						scale.substring(0, scale.indexOf("%")))/100).setScale(2, RoundingMode.HALF_UP);
			} catch (Exception e) {}
		}
		
		model.addAttribute("tagmt", tagmt);
		model.addAttribute("ordPdtModel4PageList", ordPdtModel4PageList);
		model.addAttribute("depositScale4Tord", depositScale4Tord);
		model.addAttribute("today", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		return "/cust/ord/ordAdd2";
	}
	
	/**
	 * 点单第三步
	 */
	@RequestMapping(value = "/cust/ord/ordAdd.do",method = RequestMethod.POST)
	public String ordAdd(@RequestParam(required = false) Long iagmt, OrdPdtModelList4Page list4Page, 
			HttpServletRequest req,Model model){
		// 清空session页面数据
		req.getSession().removeAttribute("iagmt");
		req.getSession().removeAttribute("list4Page");
				
		TcustSession session = (TcustSession) AppSessionFilter.getAppSession();
		List<OrdPdtModel4Page> ordPdtModel4PageList = null;
		try {
			ordPdtModel4PageList = this.custOrdBiz.addOrd(iagmt, list4Page.getOrdPdtModel4PageList(), 
					session.getTcust(), session.getTcustReg());
		} catch (BizException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setNavTabId(NAV_TAB_ID);
			ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
		list4Page.setOrdPdtModel4PageList(ordPdtModel4PageList);
		req.getSession().setAttribute("list4Page", list4Page);
		req.getSession().setAttribute("iagmt", iagmt);

		ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
		ajaxResult.setMessage("");

		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_FORWARD);
		StringBuffer forwordUrlSb = new StringBuffer();
		forwordUrlSb.append(req.getContextPath());
		forwordUrlSb.append("/cust/ord/ordAddForward.do");

		ajaxResult.setForwardUrl(forwordUrlSb.toString());
		ajaxResult.setRel("POSDDGL_DD2");
		model.addAttribute(ajaxResult);
		
		return "/common/ajaxResult";
	}
	
	/**
	 * 点单第三步 跳转后
	 */
	@RequestMapping(value = "/cust/ord/ordAddForward.do")
	public String ordAddForward(HttpServletRequest req, Model model){

		Long iagmt = (Long) req.getSession().getAttribute("iagmt");
		Tagmt tagmt = this.custOrdBiz.queryAgmtById(iagmt);
		if(tagmt == null) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage("订单协议为空异常！");
			model.addAttribute(ajaxResult);
			return "/cust/ord/ordAdd2";
		}
		
		BigDecimal depositScale4Tord = new BigDecimal(0.1); //订单支付保证金使用默认比例
		TsysParam param = this.tsysParamService.getTsysParam("OTHER_CONF", "DEPOSIT_SCALE_4_TORD"); //订单支付保证金使用比例
		if(param != null && StringUtils.isNotBlank(param.getValue())) {
			String scale = param.getValue();
			try {
				depositScale4Tord = new BigDecimal(new Float(
						scale.substring(0, scale.indexOf("%")))/100).setScale(2, RoundingMode.HALF_UP);
			} catch (Exception e) {}
		}
		OrdPdtModelList4Page list4Page = (OrdPdtModelList4Page) req.getSession().getAttribute("list4Page");
		List<OrdPdtModel4Page> ordPdtModel4PageList = list4Page.getOrdPdtModel4PageList();
		if(ordPdtModel4PageList == null) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage("数据异常，请重新点单！");
			model.addAttribute(ajaxResult);
			return "/cust/ord/ordAdd2";
		}
		model.addAttribute("ordPdtModel4PageList", ordPdtModel4PageList);
		model.addAttribute("remark", list4Page.getRemark());
		model.addAttribute("tagmt", tagmt);
		model.addAttribute("depositScale4Tord", depositScale4Tord);
		model.addAttribute("today", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		
		// 清空session页面数据
		req.getSession().removeAttribute("iagmt");
		req.getSession().removeAttribute("list4Page");
		return "/cust/ord/ordAdd2Confirm";
	}
	
	/**
	 * 点单第四步
	 */
	@RequestMapping(value = "/cust/ord/ordAddConfirm.do",method = RequestMethod.POST)
	public String ordAddConfirm(@RequestParam(required = false) Long iagmt, OrdPdtModelList4Page list4Page, Model model){
		TcustSession session = (TcustSession) AppSessionFilter.getAppSession();
		try {
			this.custOrdBiz.addOrdConfirm(iagmt, list4Page, session.getTcust(), session.getTcustReg());
		} catch (BizException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setNavTabId(NAV_TAB_ID);
			ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
		
		ajaxResult.setMessage("采购订单已提交，请到“我要付款”模块进行付款凭证上传");
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
	
	@RequestMapping("/cust/ord/ordDetail.do")
	public String ordDetail(@RequestParam(required = false) Long iord,@RequestParam(required = false) Long iagmt,Model model){
		Tord tord = this.custOrdBiz.queryTordByIord(iord);
		Tagmt tagmt = custOrdBiz.queryAgmtById(tord.getIagmt());

		model.addAttribute("tord", tord);
		model.addAttribute("tagmt", tagmt);
		List<TordDetail> tordDetailList = this.custOrdBiz.queryTordDetailList(iord);
		model.addAttribute("tordDetailList", tordDetailList);
		return "/cust/ord/ordDetail";
	}
	
	@RequestMapping("/cust/ord/ordDelete.do")
	public String ordDelete(@RequestParam(required = false) Long iord,@RequestParam(required = false) Long iagmt,Model model){
		TcustSession tcustSession = (TcustSession) AppSessionFilter.getAppSession();
		try{
			this.custOrdBiz.deleteTord(iord, iagmt, tcustSession.getTcust(), tcustSession.getTcustReg());
			
		} catch (BizException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setNavTabId(NAV_TAB_ID);
			ajaxResult.setCallbackType("");
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
		
		ajaxResult.setMessage(AjaxResult.MSG_OK);
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType("");
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
}
