package com.newland.posmall.controller.cust;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.TcustSession;
import org.ohuyo.rapid.base.entity.AjaxResult;
import org.ohuyo.rapid.base.service.TsysParamService;
import org.ohuyo.rapid.base.servlet.AppSessionFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.newland.posmall.base.exception.BizException;
import com.newland.posmall.bean.basebusi.Tagmt;
import com.newland.posmall.bean.basebusi.Tpdt;
import com.newland.posmall.bean.basebusi.condition.TagmtCondition;
import com.newland.posmall.bean.dict.AgmtStatus;
import com.newland.posmall.bean.model4page.Agmt4Page;
import com.newland.posmall.biz.cust.Agmt4PageBiz;
import com.newland.posmall.biz.cust.AgreementBiz;
import com.newland.posmall.controller.BaseController;
import com.newland.posmall.controller.jspmodel.AgmtAddObject;

/**
 * 订货协议管理
 * 
 * @author zhouym
 *
 */
@Scope("prototype")
@Controller("cust.agmt")
public class AgreementController extends BaseController {

	private static Logger logger = LoggerFactory
			.getLogger(AgreementController.class);

	private static final String NAVTABID = "DHXYGL";

	@Resource
	private AgreementBiz agreementBiz;

	@Resource
	private TsysParamService tsysParamService;

	@Resource
	private Agmt4PageBiz agmt4PageBiz;

	/**
	 * 订货协议列表
	 */
	@RequestMapping("/cust/agmt/agmtList.do")
	public String agmtList(@RequestParam(required = false) String agmtNo,
			@DateTimeFormat(pattern = "yyyy-MM-dd") Date genTime,
			@RequestParam(required = false) String agmtStatus,
			@RequestParam(required = false) Integer pageNum,
			@RequestParam(required = false) Integer numPerPage, Model model) {
		Page page = new Page();
		page.setCount(true);
		page.setCapacity(numPerPage == null ? CAPACITY : numPerPage);
		page.setPageOffset(pageNum == null ? PAGE_OFF_SET : pageNum - 1);

		TagmtCondition condition = new TagmtCondition();
		if (StringUtils.isNotBlank(agmtNo)) {
			condition.setAgmtNo(agmtNo);
		}
		if (genTime != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(genTime);
			cal.add(Calendar.DAY_OF_MONTH, 1);
			condition.setStartDate(genTime);
			condition.setEndDate(cal.getTime());
		}
		if (StringUtils.isNotBlank(agmtStatus)) {
			condition.setAgmtStatus(AgmtStatus.valueOf(agmtStatus));
		}

		TcustSession session = (TcustSession) AppSessionFilter.getAppSession();
		condition.setIcust(session.getTcust().getIcust()); // 客户id

		List<Tagmt> tagmtList = this.agreementBiz.findTagmtPageByCondition(
				condition, page);

		List<Agmt4Page> list = this.agmt4PageBiz.findAgmt4PageList(tagmtList);
		model.addAttribute("list", list);
		model.addAttribute("agmtNo", agmtNo);
		model.addAttribute("genTime", genTime);
		model.addAttribute("agmtStatus", agmtStatus);

		model.addAttribute("maxRemainDay", this.tsysParamService.getTsysParam("OTHER_CONF", "EXPIRATION_REMINDE_MAX_DAY").getValue());
		model.addAttribute("minRemainDay", this.tsysParamService.getTsysParam("OTHER_CONF", "EXPIRATION_REMINDE_MIN_DAY").getValue());
		
		model.addAttribute(NUM_PER_PAGE, page.getCapacity());
		model.addAttribute(PAGE_NUM, pageNum);
		model.addAttribute(TOTAL_COUNT, page.getRecAmt());

		return "/cust/agmt/agmtList";
	}

	/**
	 * 新增协议-第一步
	 */
	@RequestMapping("/cust/agmt/agreement.do")
	public String agreement() {
		return "/cust/agmt/agreement";
	}

	/**
	 * 新增协议-第二步
	 */
	@RequestMapping("/cust/agmt/agmtAdd.do")
	public String agmtAdd(Model model) {
		TcustSession session = (TcustSession) AppSessionFilter.getAppSession();

		List<Tpdt> agmtTpdtList = this.agreementBiz.queryTpdtList();
		this.agreementBiz.setTpdtPrice(agmtTpdtList, session.getTcust());

		String minTpdtNumOfTagmt = this.tsysParamService.getTsysParam(
				"OTHER_CONF", "XYDHZLZXZ").getValue(); // 协议起订数量
		String depositRate = this.tsysParamService.getTsysParam("OTHER_CONF",
				"BZJBLTZ").getValue(); // 保证金计算比例
		float depositRateFloat = new Float(depositRate.substring(0,
				depositRate.indexOf("%"))) / 100;
		Date startDateOfAgmt = this.agreementBiz.getStartDateOfAgmt(); // 协议起始日期
		Date endDateOfAgmt = this.agreementBiz
				.getEndDateOfAgmt(startDateOfAgmt); // 协议终止日期


		model.addAttribute("depositRate", depositRateFloat);
		model.addAttribute("minNum", Integer.valueOf(minTpdtNumOfTagmt));
		model.addAttribute("startDateOfAgmt", startDateOfAgmt);
		model.addAttribute("endDateOfAgmt", endDateOfAgmt);
		model.addAttribute("agmtTpdtList", agmtTpdtList);
		return "/cust/agmt/agmtAdd";
	}

	/**
	 * 撤销协议
	 */
	@RequestMapping("/cust/agmt/agmtRemove.do")
	public String agmtRemove(Model model,
			@RequestParam(required = false) Long iagmt) {

		TcustSession session = (TcustSession) AppSessionFilter.getAppSession();
		try {
			this.agreementBiz.removeCustAgmtByTagmt(iagmt, session.getTcust());
		} catch (BizException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setNavTabId(NAVTABID);
			ajaxResult.setCallbackType("");
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
		
		ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
		ajaxResult.setMessage("预约订单撤销成功");
		ajaxResult.setNavTabId(NAVTABID);
		ajaxResult.setCallbackType("");
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}

	/**
	 * 去协议书 页面
	 */
	@RequestMapping("/cust/agmt/agmtContent.do")
	public String agmtContent(Model model) {
		return "/cust/agmt/agmtContent";
	}

	/**
	 * 订货协议明细
	 */
	@RequestMapping("/cust/agmt/agmtDetail.do")
	public String agmtDetail(@RequestParam(required = false) Long iagmt,
			Model model) {
		Tagmt tagmt = this.agreementBiz.findById(iagmt);
		
		Agmt4Page agmt4Page = this.agmt4PageBiz.findAgmt4Page(tagmt);
		model.addAttribute("agmt4Page", agmt4Page);
		return "/cust/agmt/agmtDetail";
	}

	/**
	 * 新增协议-第三步
	 */
	@RequestMapping("/cust/agmt/signAgree.do")
	public String signAgree(AgmtAddObject objectFromJsp, Model model) {
		List<Tpdt> lsTpdts = objectFromJsp.getAgmtTpdtList();
		TcustSession session = (TcustSession) AppSessionFilter.getAppSession();
		try {
			this.agreementBiz.signAgreement(lsTpdts, session.getTcust(),
					objectFromJsp.getStartDateOfAgmt(),
					objectFromJsp.getEndDateOfAgmt());
		} catch (BizException e) {
			logger.error(e.getMessage(), e);
			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		} catch (Exception e2) {
			logger.error(e2.getMessage(), e2);
			ajaxResult.setMessage("系统异常，请联系系统管理员");
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
		ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
		ajaxResult.setMessage("预约订单已提交，审核结果将发送至注册邮箱，请及时查收");
		ajaxResult.setNavTabId(NAVTABID);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}

	/**
	 * 跳转到协议变更页面
	 * @param iagmt
	 * @param model
	 * @return
	 */
	@RequestMapping("/cust/agmt/toModify.do")
	public String toModify(@RequestParam Long iagmt, Model model) {
		
		Tagmt tagmt = this.agreementBiz.findById(iagmt);
		Agmt4Page agmt4Page = this.agmt4PageBiz.findAgmt4Page(tagmt);
		
		TcustSession session = (TcustSession) AppSessionFilter.getAppSession();
		List<Tpdt> agmtTpdtList = this.agreementBiz.queryTpdtList();
		this.agreementBiz.setTpdtPrice(agmtTpdtList, session.getTcust());
		
		String depositRate = this.tsysParamService.getTsysParam("OTHER_CONF", "BZJBLTZ").getValue(); // 保证金计算比例
		float depositRateFloat = new Float(depositRate.substring(0, depositRate.indexOf("%"))) / 100;
		
		model.addAttribute("agmt4Page", agmt4Page);
		model.addAttribute("agmtTpdtList", agmtTpdtList);
		model.addAttribute("depositRate", depositRateFloat);
		return "/cust/agmt/agmtModify";
	}
	
	/**
	 * 协议变更
	 * @param objectFromJsp
	 * @param iagmt
	 * @param model
	 * @return
	 */
	@RequestMapping("/cust/agmt/agmtModify.do")
	public String agmtModify(AgmtAddObject objectFromJsp, @RequestParam Long iagmt, Model model) {
		List<Tpdt> lsTpdts = objectFromJsp.getAgmtTpdtList();
		TcustSession session = (TcustSession) AppSessionFilter.getAppSession();
		Tagmt changedAgmt = null;
		try {
			changedAgmt = this.agreementBiz.agmtModify(lsTpdts, iagmt, session.getTcust());
		} catch (BizException e) {
			logger.error(e.getMessage(), e);
			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		} catch (Exception e2) {
			logger.error(e2.getMessage(), e2);
			ajaxResult.setMessage("系统异常，请联系系统管理员");
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
		ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
		if (changedAgmt.getRedundantDeposit().compareTo(new BigDecimal(0)) < 0) {
			ajaxResult.setMessage("变更申请已提交，请到“我要付款”模块补交保证金");
		} else {
			ajaxResult.setMessage("变更申请已提交，审核结果将发送至注册邮箱，请及时查收");
		}
		ajaxResult.setNavTabId(NAVTABID);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
	
	/**
	 * 取消协议变更
	 * @param iagmt
	 * @param model
	 * @return
	 */
	@RequestMapping("/cust/agmt/cancelAgmtModify.do")
	public String cancelAgmtModify(@RequestParam Long iagmt, Model model) {

		TcustSession session = (TcustSession) AppSessionFilter.getAppSession();
		try {
			this.agreementBiz.cancelAgmtModify(iagmt, session.getTcust());
		} catch (BizException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setNavTabId(NAVTABID);
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
		
		ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
		ajaxResult.setMessage("变更已取消，协议恢复变更前状态");
		ajaxResult.setNavTabId(NAVTABID);
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
}
