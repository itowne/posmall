package com.newland.posmall.controller.admin;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.TuserSession;
import org.ohuyo.rapid.base.entity.AjaxResult;
import org.ohuyo.rapid.base.service.FileUpDownService;
import org.ohuyo.rapid.base.service.TsysParamService;
import org.ohuyo.rapid.base.servlet.AppSessionFilter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.newland.posmall.base.exception.BizException;
import com.newland.posmall.bean.basebusi.Tagmt;
import com.newland.posmall.bean.customer.Tcust;
import com.newland.posmall.bean.customer.TcustReg;
import com.newland.posmall.bean.dict.AgmtDetailStatus;
import com.newland.posmall.bean.dict.AgmtStatus;
import com.newland.posmall.biz.admin.CustManagerBiz;
import com.newland.posmall.biz.admin.CustagmtBiz;
import com.newland.posmall.biz.file.FileDownload;
import com.newland.posmall.controller.BaseController;
import com.newland.posmall.controller.jspmodel.AgmtDetailObject;

@Scope("prototype")
@Controller("admin.custagmt")
public class CustagmtController extends BaseController {

	private final static String NAV_TAB_ID = "KHDHXYGL";

	@Resource
	private CustagmtBiz custagmtBiz;

	@Resource
	private CustManagerBiz custManagerBiz;

	@Resource
	private FileUpDownService fileUpDownService;

	@Resource
	private FileDownload fileDownload;
	
	@Resource
	private TsysParamService tsysParamService;

	/**
	 * 查询 分页列表
	 * 
	 * @param model
	 * @param pageNum
	 * @param numPerPage
	 * @param startTime
	 * @param ednTime
	 * @param agmtStatus
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/admin/agmt/custagmtList.do")
	public String custagmtList(Model model,
			@RequestParam(required = false) Integer pageNum,
			@RequestParam(required = false) Integer numPerPage,
			@RequestParam(required = false) String startTime,
			@RequestParam(required = false) String endTime,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String agmtStatus) {

		Page page = new Page();
		page.setCount(true);
		page.setCapacity(numPerPage == null ? CAPACITY : numPerPage);
		page.setPageOffset(pageNum == null ? PAGE_OFF_SET : pageNum - 1);
		
		List custagmtList = this.custagmtBiz.findListByInfo(page, startTime,
				endTime, agmtStatus, name);

		model.addAttribute("custagmtList", custagmtList);
		model.addAttribute("startTime", startTime);
		model.addAttribute("endTime", endTime);
		model.addAttribute("agmtStatus", agmtStatus);
		model.addAttribute("name", name);

		model.addAttribute(NUM_PER_PAGE, page.getCapacity());
		model.addAttribute(PAGE_NUM, pageNum);
		model.addAttribute(TOTAL_COUNT, page.getRecAmt());

		return "/admin/agmt/custagmtList";
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/admin/agmt/logisticsList.do")
	public String custagmtList(Model model,
			@RequestParam(required = false) Integer pageNum,
			@RequestParam(required = false) Long iagmt,
			@RequestParam(required = false) Integer numPerPage){

		Page page = new Page();
		page.setCount(true);
		page.setCapacity(numPerPage == null ? CAPACITY : numPerPage);
		page.setPageOffset(pageNum == null ? PAGE_OFF_SET : pageNum - 1);
		
		List logisticsList = this.custagmtBiz.findLogisticsListByTagmt(page, iagmt);

		model.addAttribute("logisticsList", logisticsList);
		model.addAttribute("iagmt", iagmt);

		model.addAttribute(NUM_PER_PAGE, page.getCapacity());
		model.addAttribute(PAGE_NUM, pageNum);
		model.addAttribute(TOTAL_COUNT, page.getRecAmt());

		return "/admin/agmt/logisticsList";
	}

	/**
	 * 查询明细
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/admin/agmt/custagmtDetail.do")
	public String agmtDetail(@RequestParam(required = false) Long iagmt,
			Model model) {
		Tagmt tagmt = this.custagmtBiz.findTagmtByIagmt(iagmt);
		List agmtTpdtList = this.custagmtBiz.findPdtByIagmt(iagmt);
		Tcust tcust = this.custManagerBiz.findTcust(tagmt.getIcust());
		
		//查询协议下的物流欠费情况
		if(tagmt.getRemainDeposit() != null && tagmt.getRemainDeposit().compareTo(BigDecimal.ZERO)==0){
			Object temp = this.custagmtBiz.findLogisticsAmtByTagmt(iagmt);
			if(temp != null){
				Object[] object = (Object[]) temp;
				BigDecimal logisticsPay = new BigDecimal(object[1].toString()).subtract(new BigDecimal(object[2].toString()));
				if(logisticsPay.compareTo(BigDecimal.ZERO) == 0){
					model.addAttribute("logisticsFlag", false);
				}else{
					model.addAttribute("logisticsPay", logisticsPay);
					model.addAttribute("logisticsFlag", true);
				}
			}else{
				model.addAttribute("logisticsFlag", false);
			}
		}else{
			model.addAttribute("logisticsFlag", false);
		}
		
		int sumnum = 0;
		BigDecimal sumamt = new BigDecimal(0);
		if (agmtTpdtList != null && agmtTpdtList.size() > 0) {
			for (int i = 0; i < agmtTpdtList.size(); i++) {
				Object[] obj = (Object[]) agmtTpdtList.get(i);
				sumnum += Integer.valueOf(String.valueOf(obj[3]));
				sumamt = new BigDecimal(String.valueOf(obj[6]))
						.multiply(new BigDecimal(String.valueOf(obj[3])))
						.add(sumamt).setScale(2, RoundingMode.HALF_UP);
			}
		}
		model.addAttribute("sumnum", sumnum);
		model.addAttribute("sumamt", sumamt);

		model.addAttribute("tagmt", tagmt);
		model.addAttribute("agmtTpdtList", agmtTpdtList);
		model.addAttribute("tcust", tcust);
		TcustReg tcustReg = this.custManagerBiz.findTcustReg(tagmt.getIcust());
		model.addAttribute("tcustReg", tcustReg);

		return "/admin/agmt/custagmtDetail";
	}

	/**
	 * 去协议额度确认页
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/admin/agmt/custagmtLimitCheckPage.do")
	public String custagmtLimitCheckPage(
			@RequestParam(required = false) Long iagmt, Model model) {
		Tagmt tagmt = this.custagmtBiz.findTagmtByIagmt(iagmt);
		List agmtTpdtList = this.custagmtBiz.findPdtByIagmt(iagmt);
		Tcust tcust = this.custManagerBiz.findTcust(tagmt.getIcust());
		
		//保证金比例
		String depositRate = this.tsysParamService.getTsysParam("OTHER_CONF", "BZJBLTZ").getValue();
		float depositRateFloat = new Float(depositRate.substring(0, depositRate.indexOf("%")))/100;

		model.addAttribute("tagmt", tagmt);
		model.addAttribute("agmtTpdtList", agmtTpdtList);
		model.addAttribute("tcust", tcust);
		model.addAttribute("depositRateFloat", depositRateFloat);

		int sumnum = 0;
		BigDecimal sumamt = new BigDecimal(0);
		if (agmtTpdtList != null && agmtTpdtList.size() > 0) {
			for (int i = 0; i < agmtTpdtList.size(); i++) {
				Object[] obj = (Object[]) agmtTpdtList.get(i);
				sumnum += Integer.valueOf(String.valueOf(obj[3]));
				sumamt = new BigDecimal(String.valueOf(obj[6]))
						.multiply(new BigDecimal(String.valueOf(obj[3])))
						.add(sumamt).setScale(2, RoundingMode.HALF_UP);
			}
		}
		model.addAttribute("sumnum", sumnum);
		model.addAttribute("sumamt", sumamt);
		
		// 协议最小订货额度
		int minNum = Integer.valueOf(this.tsysParamService.getTsysParam("OTHER_CONF", "XYDHZLZXZ").getValue());
		model.addAttribute("minNum", minNum);

		TcustReg tcustReg = this.custManagerBiz.findTcustReg(tagmt.getIcust());
		model.addAttribute("tcustReg", tcustReg);

		return "/admin/agmt/custagmtLimitCheck";
	}

	/**
	 * 去协议付款确认页
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/admin/agmt/custagmtPayCheckPage.do")
	public String custagmtPayCheckPage(
			@RequestParam(required = false) Long iagmt, Model model) {
		Tagmt tagmt = this.custagmtBiz.findTagmtByIagmt(iagmt);
		List agmtTpdtList = this.custagmtBiz.findPdtByIagmt(iagmt);
		Tcust tcust = this.custManagerBiz.findTcust(tagmt.getIcust());

		model.addAttribute("tagmt", tagmt);
		model.addAttribute("agmtTpdtList", agmtTpdtList);
		model.addAttribute("tcust", tcust);

		TcustReg tcustReg = this.custManagerBiz.findTcustReg(tagmt.getIcust());
		model.addAttribute("tcustReg", tcustReg);

		return "/admin/agmt/custagmtPayCheck";
	}

	/**
	 * 去协议审核页
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/admin/agmt/custagmtPayPassCheckPage.do")
	public String custagmtPayPassCheckPage(@RequestParam Long iagmt, Model model) {
		Tagmt tagmt = this.custagmtBiz.findTagmtByIagmt(iagmt);
		List agmtTpdtList = this.custagmtBiz.findPdtByIagmt(iagmt);
		Tcust tcust = this.custManagerBiz.findTcust(tagmt.getIcust());

		// 判断协议是否有未支付或者支付未审核通过（主要是针对协议变更，保证金补交）
		if (this.custagmtBiz.hasPayNotChecked(tagmt.getIagmt())) {
			model.addAttribute("errMsg", "客户存在账单未支付或者未审核通过");
			return "/admin/agmt/custagmtPayPassCheck";
		}
		
		int sumnum = 0;
		BigDecimal sumamt = new BigDecimal(0);
		if (agmtTpdtList != null && agmtTpdtList.size() > 0) {
			for (int i = 0; i < agmtTpdtList.size(); i++) {
				Object[] obj = (Object[]) agmtTpdtList.get(i);
				sumnum += Integer.valueOf(String.valueOf(obj[3]));
				sumamt = new BigDecimal(String.valueOf(obj[6]))
						.multiply(new BigDecimal(String.valueOf(obj[3])))
						.add(sumamt).setScale(2, RoundingMode.HALF_UP);
			}
		}
		model.addAttribute("sumnum", sumnum);
		model.addAttribute("sumamt", sumamt);

		model.addAttribute("tagmt", tagmt);
		model.addAttribute("agmtTpdtList", agmtTpdtList);
		model.addAttribute("tcust", tcust);
		TcustReg tcustReg = this.custManagerBiz.findTcustReg(tagmt.getIcust());
		model.addAttribute("tcustReg", tcustReg);
		
		// 如果协议有变更，则存在agmt_detail_his数据
		List agmtDetailHisList = this.custagmtBiz.findPdtHisByIagmt(iagmt);
		if (CollectionUtils.isEmpty(agmtDetailHisList) == false) {
			model.addAttribute("agmtDetailHisList", agmtDetailHisList);
		}

		return "/admin/agmt/custagmtPayPassCheck";
	}

	/**
	 * 协议确认(额度)iagmt agmtStatus
	 */
	@RequestMapping(value = "/admin/agmt/custagmtLimitCheck.do", method = RequestMethod.POST)
	public String custagmtLimitCheck(
			@RequestParam(required = false) Double modifyDeposit,
			@RequestParam(required = false) Long iagmt,
			AgmtDetailObject agmtDetailObject, Model model) {	

		Tagmt tagmt = this.custagmtBiz.findTagmtByIagmt(iagmt);
		tagmt.setDeposit(new BigDecimal(modifyDeposit));
		tagmt.setRemainDeposit(new BigDecimal(modifyDeposit));
		try {
			TuserSession tuserSession = (TuserSession) AppSessionFilter.getAppSession();
			this.custagmtBiz.custagmtCheck(tagmt, AgmtStatus.AGMT_QUOTA_CONFIRM,
					AgmtDetailStatus.AGMT_QUOTA_CONFIRM,
					agmtDetailObject.getAgmtDetailList(),tuserSession.getTuser());
		} catch (BizException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setNavTabId(NAV_TAB_ID);
			ajaxResult.setCallbackType("");
			model.addAttribute(ajaxResult);

			return "/common/ajaxResult";
		}

		ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
		ajaxResult.setMessage(AjaxResult.MSG_OK);
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		model.addAttribute(ajaxResult);

		return "/common/ajaxResult";
	}

	/**
	 * 协议确认(付款) iagmt agmtStatus
	 */
	@RequestMapping(value = "/admin/agmt/custagmtPayCheck.do", method = RequestMethod.POST)
	public String custagmtPayCheck(@RequestParam(required = false) Long iagmt,
			Model model) {
		Tagmt tagmt = this.custagmtBiz.findTagmtByIagmt(iagmt);
		try {
			TuserSession tuserSession = (TuserSession) AppSessionFilter.getAppSession();
			this.custagmtBiz.custagmtCheck(tagmt, AgmtStatus.PAY_PASS,
					AgmtDetailStatus.PAY_PASS, null, tuserSession.getTuser());
		} catch (BizException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setNavTabId(NAV_TAB_ID);
			ajaxResult.setCallbackType("");
			model.addAttribute(ajaxResult);

			return "/common/ajaxResult";
		}
		ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
		ajaxResult.setMessage(AjaxResult.MSG_OK);
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		model.addAttribute(ajaxResult);

		return "/common/ajaxResult";
	}

	/**
	 * 协议确认(审核) iagmt agmtStatus
	 */
	@RequestMapping(value = "/admin/agmt/custagmtPayPassCheck.do", method = RequestMethod.POST)
	public String custagmtPayPassCheck(@RequestParam Long iagmt, Model model) {
		Tagmt tagmt = this.custagmtBiz.findTagmtByIagmt(iagmt);
		try {
			TuserSession tuserSession = (TuserSession) AppSessionFilter.getAppSession();
			this.custagmtBiz.custagmtCheck(tagmt, AgmtStatus.CONFIRM,
					AgmtDetailStatus.CONFIRM, null, tuserSession.getTuser());
		} catch (BizException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setNavTabId(NAV_TAB_ID);
			ajaxResult.setCallbackType("");
			model.addAttribute(ajaxResult);

			return "/common/ajaxResult";
		}
		
		ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
		ajaxResult.setMessage(AjaxResult.MSG_OK);
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		model.addAttribute(ajaxResult);

		return "/common/ajaxResult";
	}
	
	/**
	 * 协议变更确认(审核)
	 * @param iagmt
	 * @param model
	 * @return
	 */
	@RequestMapping("/admin/agmt/agmtChangeCheck.do")
	public String agmtChangeCheck(@RequestParam Long iagmt, Model model) {
		
		Tagmt tagmt = this.custagmtBiz.findTagmtByIagmt(iagmt);
		TuserSession tuserSession = (TuserSession) AppSessionFilter.getAppSession();
		
		if (this.custagmtBiz.hasPayNotChecked(tagmt.getIagmt())) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage("客户存在账单未支付或者未审核通过");
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
		
		try {
			this.custagmtBiz.custagmtCheck(tagmt, AgmtStatus.HAVE_CHANGED,
					AgmtDetailStatus.HAVE_CHANGED, null, tuserSession.getTuser());
		} catch (BizException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setNavTabId(NAV_TAB_ID);
			ajaxResult.setCallbackType("");
			model.addAttribute(ajaxResult);

			return "/common/ajaxResult";
		}
		
		ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
		ajaxResult.setMessage(AjaxResult.MSG_OK);
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		model.addAttribute(ajaxResult);

		return "/common/ajaxResult";
	}
	
	/**
	 * 协议撤销 iagmt agmtStatus
	 */
	@RequestMapping(value = "/admin/agmt/removeAgmt.do", method = RequestMethod.GET)
	public @ResponseBody String removeAgmt(
			@RequestParam(required = false) Long iagmt) {
		Boolean flag = true;
		TuserSession tuserSession = (TuserSession) AppSessionFilter.getAppSession();
		custagmtBiz.removeCustAgmtByIagmt(iagmt, tuserSession);
		return "{\"result\": \""+flag+"\"}";
	}
	
	 @RequestMapping(value = "/admin/agmt/validateDeleteAgmt.do", method = RequestMethod.GET) 
    public @ResponseBody String validateDeleteAgmt(
    		@RequestParam(required = false) Long iagmt) { 
    	String result = "";
        result = custagmtBiz.validateDeleteAgmt(iagmt);
    	return "{\"result\": \""+result+"\"}";
    }

	/**
	 * 协议下载
	 * 
	 * @param startTime
	 * @param endTime
	 * @param loginName
	 * @param agmtStatus
	 * @throws ParseException
	 */
	@RequestMapping("/admin/agmt/downLoadCustAgmt.do")
	public void downLoadCustAgmt(
			@RequestParam(required = false) String startTime,
			@RequestParam(required = false) String endTime,
			@RequestParam(required = false) String loginName,
			@RequestParam(required = false) String agmtStatus,
			HttpServletResponse response) throws ParseException {

		Tagmt tagmt = new Tagmt();

		if (StringUtils.isNotBlank(loginName)) {
			Tcust tcust = custagmtBiz.findByLoginName(loginName);
			tagmt.setIcust(tcust.getIcust());
		}
		if (StringUtils.isNotBlank(agmtStatus)) {
			tagmt.setAgmtStatus(AgmtStatus.valueOf(agmtStatus));
		}
		if (StringUtils.isNotBlank(startTime)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date startDate = sdf.parse(startTime);
			tagmt.setStartTime(startDate);
		}
		if (StringUtils.isNotBlank(endTime)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date endDate = sdf.parse(endTime);
			tagmt.setEndTime(endDate);
		}

		List<Tagmt> tagmtList = custagmtBiz.findTagmtBySelect(tagmt);

		File file = null;
		try {
			file = fileDownload.agmtInfoDown(tagmtList);
			fileUpDownService.download(file, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 去设置erp订单号页面
	 */
	@RequestMapping("/admin/agmt/setErpOrdId.do")
	public String setErpOrdId(@RequestParam(required = false) Long iagmt, Model model) {
		Tagmt tagmt = this.custagmtBiz.findTagmtByIagmt(iagmt);
		model.addAttribute("tagmt", tagmt);
		return "/admin/agmt/setErpOrdId";
	}
	/**
	 * 设置erp订单号
	 */
	@RequestMapping("/admin/agmt/confirmErpOrdId.do")
	public String confirmErpOrdId(Tagmt tagmt, Model model) {
		TuserSession tuserSession = (TuserSession) AppSessionFilter.getAppSession();
		try {
			this.custagmtBiz.setErpOrdId(tagmt,tuserSession.getTuser());
		} catch (BizException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setNavTabId(NAV_TAB_ID);
			ajaxResult.setCallbackType("");
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
		
		ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
		ajaxResult.setMessage(AjaxResult.MSG_OK);
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		model.addAttribute(ajaxResult);

		return "/common/ajaxResult";
	}

}