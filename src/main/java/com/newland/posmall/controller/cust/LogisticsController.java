package com.newland.posmall.controller.cust;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.ohuyo.commons.format.json.JSONObject;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.TcustSession;
import org.ohuyo.rapid.base.entity.AjaxResult;
import org.ohuyo.rapid.base.entity.Tfile;
import org.ohuyo.rapid.base.service.FileUpDownService;
import org.ohuyo.rapid.base.servlet.AppSessionFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.newland.posmall.Application;
import com.newland.posmall.base.exception.BizException;
import com.newland.posmall.bean.basebusi.TlogisticsCom;
import com.newland.posmall.bean.basebusi.TlogisticsOrd;
import com.newland.posmall.bean.basebusi.TlogisticsOrdAddr;
import com.newland.posmall.bean.basebusi.Tord;
import com.newland.posmall.bean.customer.Taddr;
import com.newland.posmall.bean.customer.TcustStock;
import com.newland.posmall.bean.customer.condition.TlogisticsOrdCondition;
import com.newland.posmall.bean.dict.LogisticsOrdStatus;
import com.newland.posmall.bean.dict.PayStatus;
import com.newland.posmall.biz.cust.Logistics4PageBiz;
import com.newland.posmall.biz.cust.LogisticsBiz;
import com.newland.posmall.biz.cust.LogisticsOrderWithMultipleAddrs4PageBiz;
import com.newland.posmall.biz.cust.Ord4PageBiz;
import com.newland.posmall.biz.file.FileDownload;
import com.newland.posmall.controller.BaseController;
import com.newland.posmall.controller.cust.model.LogisticsDown4Page;
import com.newland.posmall.controller.cust.model.LogisticsOrd4Page;
import com.newland.posmall.controller.cust.model.LogisticsOrdAddrObj4Page;
import com.newland.posmall.controller.cust.model.LogisticsOrderWithMultipleAddrs4Page;
import com.newland.posmall.controller.cust.model.Ord4Page;

/**
 * 客户物流管理
 * 
 * @author Mr.Towne
 * 
 */
@Scope("prototype")
@Controller("cust.logistics")
public class LogisticsController extends BaseController {

	private static final String NAV_TAB_ID = "WLJHGL";
	
	private static final Logger logger = LoggerFactory.getLogger(LogisticsController.class);

	@Resource
	private LogisticsBiz logisticsBiz;

	@Resource
	private FileUpDownService fileUpDownService;

	@Resource
	private Ord4PageBiz ord4PageBiz;

	@Resource
	private LogisticsOrderWithMultipleAddrs4PageBiz logisticsOrderWithMultipleAddrs4PageBiz;
	
	@Resource
	private Logistics4PageBiz logistics4PageBiz;

	@Resource
	private FileDownload fileDownload;
	
	@RequestMapping("/cust/logistics/logisticsList.do")
	public String logisticsList(Integer pageNum, Integer numPerPage, Model model, TlogisticsOrd tlogisticsOrd,
			              @RequestParam(required = false) String consigneeName) {
		Page page = new Page();
		page.setCount(true);
		page.setCapacity(numPerPage == null ? CAPACITY : numPerPage);
		page.setPageOffset(pageNum == null ? PAGE_OFF_SET : pageNum - 1);

		TcustSession tcustSession = (TcustSession) AppSessionFilter
				.getAppSession();

		List<TlogisticsOrd> tLogisticsOrdList = logisticsBiz.queryOrdByCon(tlogisticsOrd,
				tcustSession.getTcust(), page,consigneeName);
		this.logisticsBiz.setTord4TlogisticsOrd(tLogisticsOrdList);

		model.addAttribute("tLogisticsOrdList", tLogisticsOrdList);
		model.addAttribute(NUM_PER_PAGE, page.getCapacity());
		model.addAttribute(PAGE_NUM, pageNum);
		model.addAttribute(TOTAL_COUNT, page.getRecAmt());
		model.addAttribute("consigneeName",consigneeName);
		return "/cust/logistics/logisticsList";
	}

	@RequestMapping("/cust/logistics/toAdd.do")
	public String toAdd(Integer pageNum, Integer numPerPage, Model model) {
		TcustSession session = (TcustSession) AppSessionFilter.getAppSession();
		List<TcustStock> tCustStockList = this.logisticsBiz
				.queryTcustStock(session.getTcust());
		model.addAttribute("tCustStockList", tCustStockList);
		return "/cust/logistics/logisticsAdd";
	}

	public String toModify(Long ilogisticsOrd, Model model) {
		return "/cust/logistics/logisticsModify";
	}

	public String logisticsModify(TlogisticsOrd ord, Model model) {
		return "/common/ajaxResult";
	}

	@RequestMapping("/cust/logistics/downLoadLogistics.do")
	public void downLoadLogistics(HttpServletResponse response) {
		TlogisticsOrd tlo = new TlogisticsOrd();
		List<TlogisticsOrd> list = logisticsBiz.findListBy(tlo);
		List<LogisticsDown4Page> ld4pList = logisticsBiz.getLogisticsData(list);

		File file = null;
		try {
			file = fileDownload.lgcsOrdInfoDown(ld4pList, null);
			fileUpDownService.download(file, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加单地址物流单 第一步
	 */
	@RequestMapping("/cust/logistics/toAddStep1.do")
	public String toAddStep1(Model model) {
		TcustSession session = (TcustSession) AppSessionFilter.getAppSession();
		List<Tord> tordList = this.logisticsBiz.findTordByCon(session
				.getTcust());
		List<Ord4Page> p = this.ord4PageBiz.findOrder4PageList(tordList);
		model.addAttribute("ord4page", p);
		return "/cust/logistics/logisticsAddStep1";
	}

	/**
	 * 添加单地址物流单 第二步
	 */
	@RequestMapping("/cust/logistics/toAddStep2.do")
	public String toAddStep2(@RequestParam(required = false) Long iord,
			Model model) {

		Tord tord = this.logisticsBiz.findTordByIord(iord);
		Ord4Page p = this.ord4PageBiz.findOrd4Page(tord);

		model.addAttribute("ord4page", p);

		model.addAttribute("tord", tord);
		return "/cust/logistics/logisticsAddStep2";
	}
	
	@RequestMapping("/cust/logistics/downTempCsv.do")
	public void downTempCsv(HttpServletResponse response){
		try{
			PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			org.springframework.core.io.Resource res = resolver.getResource("moreAddrLogistics.csv");
			if (res == null) throw new RuntimeException("");
			File file = File.createTempFile("tmp", ".csv", new File(Application.getTemplatePath()));
			FileOutputStream fout = new FileOutputStream(file);
			IOUtils.copy(res.getInputStream(), fout);
			fileUpDownService.download(file, response);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("-文件下载异常-", e);
		}
	}
	

	/**
	 * 添加单地址物流单 第三步
	 */
	@RequestMapping("/cust/logistics/toAddStep3.do")
	public String toAddStep3(Ord4Page ord4Page, HttpServletRequest req,
			Model model) {

		TlogisticsOrd tlogisticsOrd;
		try {
			TcustSession session = (TcustSession) AppSessionFilter
					.getAppSession();
			tlogisticsOrd = this.logisticsBiz.logisticsOrdAdd1(
					session.getTcust(), session.getTcustReg(), ord4Page);
		} catch (BizException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setCallbackType("");
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}

		String specifyDelivery = this.logisticsBiz
				.getSpecifyDeliveryStr(ord4Page);

		ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
		ajaxResult.setMessage("");

		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_FORWARD);
		StringBuffer forwordUrlSb = new StringBuffer();
		forwordUrlSb.append(req.getContextPath());
		forwordUrlSb.append("/cust/logistics/toAddStep3Forword.do");
		forwordUrlSb.append("?ilogisticsOrd=");
		forwordUrlSb.append(tlogisticsOrd.getIlogisticsOrd());
		forwordUrlSb.append("&specifyDelivery=");
		forwordUrlSb.append(specifyDelivery);

		ajaxResult.setForwardUrl(forwordUrlSb.toString());
		ajaxResult.setRel("CZWLXXGL_XZ");
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}

	/**
	 * 添加单地址物流单 第三步 跳转后
	 */
	@RequestMapping("/cust/logistics/toAddStep3Forword.do")
	public String toAddStep3Forword(
			@RequestParam(required = false) Long ilogisticsOrd,
			@RequestParam(required = false) String specifyDelivery,
			@RequestParam(required = false) Integer pageNum,
			@RequestParam(required = false) String name,
			LogisticsOrd4Page logisticsOrd4Page, Model model) {

		TcustSession session = (TcustSession) AppSessionFilter.getAppSession();
		TlogisticsOrd tlogisticsOrd = null;
		TlogisticsCom tlogisticsCom = null;
		BigDecimal fee = null;

		if (ilogisticsOrd != null) {
			tlogisticsOrd = this.logisticsBiz
					.findLogisticsOrdById(ilogisticsOrd);
			logisticsOrd4Page = this.logistics4PageBiz
					.findLogisticsOrd4Page(tlogisticsOrd);
		} else {
			tlogisticsOrd = this.logisticsBiz
					.findLogisticsOrdById(logisticsOrd4Page.getTlogisticsOrd()
							.getIlogisticsOrd());
			specifyDelivery = logisticsOrd4Page.getSpecifyDelivery();
			if (logisticsOrd4Page.getIlogisticsCom() != null) {
				tlogisticsCom = this.logisticsBiz
						.queryLogisticsComByIpdt(logisticsOrd4Page
								.getIlogisticsCom());
				logisticsOrd4Page = this.logistics4PageBiz
						.findLogisticsOrd4Page(tlogisticsOrd);
				logisticsOrd4Page.setIlogisticsCom(tlogisticsCom
						.getIlogisticsCom());
				fee = tlogisticsCom.getPrice()
						.multiply(new BigDecimal(tlogisticsOrd.getNum()))
						.setScale(2, RoundingMode.HALF_UP);
			} else {
				logisticsOrd4Page = this.logistics4PageBiz
						.findLogisticsOrd4Page(tlogisticsOrd);
			}
		}

		// 查询常用地址默认5个
		Page page = new Page();
		page.setCount(true);
		page.setCapacity(5);
		page.setPageOffset(pageNum == null ? PAGE_OFF_SET : pageNum - 1);

		List<Taddr> taddrList = this.logisticsBiz.findTaddrList(page,
				session.getTcust(), name);
		model.addAttribute(NUM_PER_PAGE, page.getCapacity());
		model.addAttribute(PAGE_NUM, pageNum);
		model.addAttribute(TOTAL_COUNT, page.getRecAmt());

		model.addAttribute("taddrList", taddrList);
		model.addAttribute("logisticsOrd4Page", logisticsOrd4Page);
		model.addAttribute("specifyDelivery", specifyDelivery);
		model.addAttribute("tlogisticsCom", tlogisticsCom);
		model.addAttribute("name", name);
		model.addAttribute("fee", fee);

		return "/cust/logistics/logisticsAddStep3";
	}

	/**
	 * 添加单地址物流单 第四步
	 */
	@RequestMapping("/cust/logistics/toAddStep4.do")
	public String toAddStep4(LogisticsOrd4Page logisticsOrd4Page, Model model) {
		TcustSession session = (TcustSession) AppSessionFilter.getAppSession();
		try {
			this.logisticsBiz.logisticsOrdAdd2(session.getTcust(),
					session.getTcustReg(), logisticsOrd4Page);
		} catch (BizException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setCallbackType("");
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}

		ajaxResult.setMessage(AjaxResult.MSG_ADD);
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}

	@RequestMapping("/cust/logistics/logisticsOrdDetail.do")
	public String logisticsOrdDetail(
			@RequestParam(required = false) Long ilogisticsOrd, Model model) {

		TlogisticsOrd tlogisticsOrd = this.logisticsBiz
				.findLogisticsOrdById(ilogisticsOrd);
		LogisticsOrd4Page logisticsOrd4Page = this.logistics4PageBiz
				.findLogisticsOrd4Page(tlogisticsOrd);
		this.logisticsBiz.setTordAlone(tlogisticsOrd);
		model.addAttribute("logisticsOrd4Page", logisticsOrd4Page);
		model.addAttribute("tlogisticsOrd", tlogisticsOrd);
		return "/cust/logistics/logisticsOrdDetail";
	}
	
	@RequestMapping("/cust/logistics/logisticsOrdDetailMore.do")
	public String logisticsorderDetailMore(
			@RequestParam(required = false) Long ilogisticsOrd, Model model) {
		TlogisticsOrd tlogisticsOrd = this.logisticsBiz.findLogisticsOrdById(ilogisticsOrd);
		TcustSession tcustSession = (TcustSession) AppSessionFilter.getAppSession();
		LogisticsOrderWithMultipleAddrs4Page p = this.logisticsOrderWithMultipleAddrs4PageBiz.getLogisticsOrderWithMultipleAddrs4Page(ilogisticsOrd, tcustSession.getTcust());
		
		model.addAttribute("p", p);
		model.addAttribute("ilogisticsOrd", ilogisticsOrd);
		model.addAttribute("tlogisticsOrd", tlogisticsOrd);
		return "/cust/logistics/logisticsOrdDetailMore";
	}
	
	@RequestMapping("/cust/logistics/logisticsInfo.do")
	public String logisticsInfo(@RequestParam(required = false) Long id, Model model){
		TlogisticsOrdAddr ordAddr = logisticsBiz.getTlogisticsOrdAddrById(id);
		
		if(StringUtils.isNotBlank(ordAddr.getLogisticsComName())){
			ordAddr.setLogisticsCompany(logisticsBiz.getlogisticsComCode(ordAddr.getLogisticsComName()));
		}
		model.addAttribute("ordAddr",ordAddr);
		return "/cust/logistics/logisticsInfo";
	}
	
	/**
	 * 物流欠款明细（汇总）
	 * @param model
	 * @return
	 */
	@RequestMapping("/cust/logistics/toDebtsDetail.do")
	public String toDebtsDetail(Model model) {
		TcustSession session = (TcustSession) AppSessionFilter.getAppSession();
		TlogisticsOrdCondition condition = new TlogisticsOrdCondition();
		condition.setIcust(session.getTcust().getIcust());
		condition.setTempFlag(Boolean.FALSE);
		List<TlogisticsOrd> tmpList = logisticsBiz.queryOrdListByCon(condition);
		List<TlogisticsOrd> tLogisticsOrdList = new ArrayList<TlogisticsOrd>();
		if(! CollectionUtils.isEmpty(tmpList)) {
			for (TlogisticsOrd t : tmpList) {
				if((t.getLogisticsOrdStatus() != LogisticsOrdStatus.REVOKED) && 
						(t.getLogisticsOrdStatus() != LogisticsOrdStatus.WAIT_AUDIT) &&
						(t.getPayStatus() == PayStatus.WAIT_PAY ||
						 t.getPayStatus() == PayStatus.WAIT_AUDIT ||
						 t.getPayStatus() == PayStatus.PART_PAY ||
						 t.getPayStatus() == PayStatus.NO_PASS)) {
					tLogisticsOrdList.add(t);
				}
			}
		}
		this.logisticsBiz.setTord4TlogisticsOrd(tLogisticsOrdList);
		model.addAttribute("tLogisticsOrdList", tLogisticsOrdList);
		return "/cust/logistics/debtsDetail";
	}

	@RequestMapping("/cust/logistics/logisticsOrdCancel.do")
	public String logisticsOrdCancel(
			@RequestParam(required = false) Long ilogisticsOrd, Model model) {
		TcustSession session = (TcustSession) AppSessionFilter.getAppSession();
		try {
			this.logisticsBiz.logisticsOrdCancel(session.getTcust(),
					session.getTcustReg(), ilogisticsOrd);
		} catch (BizException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setCallbackType("");
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}

		ajaxResult.setMessage(AjaxResult.MSG_DELETE);
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType("");
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}

	/**
	 * 得到客户最快发货日期
	 */
	@RequestMapping(value = "/cust/logistics/getSpecifyDelivery.do", method = RequestMethod.POST)
	public void getSpecifyDelivery(String shipmentNum, Long ipdtHis, Long iord,
			HttpServletResponse response) {

		String specifyDelivery = this.logisticsBiz.getSpecifyDeliveryStr(
				shipmentNum, ipdtHis, iord);
		Map<String, String> specifyDeliveryMap = new HashMap<String, String>();
		specifyDeliveryMap.put("specifyDelivery", specifyDelivery);
		Object rst = JSONObject.wrap(specifyDeliveryMap);
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().println(rst);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 添加单地址物流单 第一步
	 */
	@RequestMapping("/cust/logistics/toAddStep1MoreBefore.do")
	public String toAddStep1MoreBefore(Model model) {
		TcustSession session = (TcustSession) AppSessionFilter.getAppSession();
		List<Tord> tordList = this.logisticsBiz.findTordByCon(session
				.getTcust());
		List<Ord4Page> p = this.ord4PageBiz.findOrder4PageList(tordList);
		model.addAttribute("ord4page", p);
		return "/cust/logistics/logisticsAddStep1MoreBefore";
	}
	

	/**
	 * 添加多地址物流单 第一步
	 */
	@RequestMapping("/cust/logistics/toAddStep1More.do")
	public String toAddStep1More(@RequestParam(required = false) Long iord,Model model) {
		model.addAttribute("iord", iord);
		return "/cust/logistics/logisticsAddStep1More";
	}

	/**
	 * 添加多地址物流单 第二步
	 */
	@RequestMapping("/cust/logistics/toAddStep2More.do")
	public String toAddStep2More(Model model, HttpServletRequest request,
			@RequestParam(required = false) Long iord, HttpServletResponse response) {
		Map<String, Tfile> files = null;
		String[] limitExts = { "xls", "xlsx", "csv" };// {"xls","xlsx","csv"};
		String[] limitContentType = { "application/octet-stream",
				"application/vnd.ms-excel",
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" };
		try {
			files = this.fileUpDownService.upload(request, response, limitExts,
					limitContentType, -1);
		} catch (Exception e) {
			ajaxResult.setMessage(e.getMessage());
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setNavTabId("CZWLXXGL_XZ2");
			ajaxResult.setCallbackType("");
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
		
		if(files == null || files.get("logisticsOrdAddrUpload") == null) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage("上传文件失败!");
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
		
		Tfile tfile = files.get("logisticsOrdAddrUpload");

		TcustSession tcustSession = (TcustSession) AppSessionFilter
				.getAppSession();
		TlogisticsOrd tlogisticsOrd = this.logisticsBiz.saveTmpTlogisticsOrd(
				tcustSession.getTcust(), tfile.getIfile());

		ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
		ajaxResult.setMessage(AjaxResult.MSG_OK);

		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_FORWARD);
		StringBuffer forwordUrlSb = new StringBuffer();
		forwordUrlSb.append(request.getContextPath());
		forwordUrlSb.append("/cust/logistics/toAddStep2MoreForward.do");
		forwordUrlSb.append("?ilogisticsOrd=");
		forwordUrlSb.append(tlogisticsOrd.getIlogisticsOrd());
		forwordUrlSb.append("&ifile=");
		forwordUrlSb.append(tfile.getIfile());
		forwordUrlSb.append("&iord=");
		forwordUrlSb.append(iord);

		ajaxResult.setForwardUrl(forwordUrlSb.toString());
		ajaxResult.setRel("CZWLXXGL_XZ2");
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}

	/**
	 * 添加多地址物流单 第二步 跳转后
	 */
	@RequestMapping("/cust/logistics/toAddStep2MoreForward.do")
	public String toAddStep2MoreForward(
			@RequestParam(required = false) Long iord,
			@RequestParam(required = false) Long ilogisticsOrd,
			@RequestParam(required = false) Long ifile,Model model) {
		
		List<LogisticsOrdAddrObj4Page> list = null;
		List<Boolean> flagList = new ArrayList<Boolean>();
		try {
			list = this.logisticsBiz.findLogisticsOrdAddrObj4PageList(ifile, flagList);
		} catch (BizException e) {
			e.printStackTrace();
		}
		Tord tord = this.logisticsBiz.findTordByIord(iord);
		Ord4Page ord4page = this.ord4PageBiz.findOrd4Page(tord);
		
		model.addAttribute("ilogisticsOrd", ilogisticsOrd);
		model.addAttribute("list", list);
		model.addAttribute("flag", flagList.get(0));
		model.addAttribute("iord", iord);
		model.addAttribute("ord4page", ord4page);
		
		return "/cust/logistics/logisticsAddStep2More";
	}

	/**
	 * 添加多地址物流单 第三步
	 */
	@RequestMapping("/cust/logistics/toAddStep3More.do")
	public String toAddStep3More(@RequestParam(required = false) Long iord,
			@RequestParam(required = false) Long ilogisticsOrd,Model model, HttpServletRequest request) {
		String specifyDelivery = "";
		try {
			this.logisticsBiz.analyzeAddrFileAndSaveAddr(ilogisticsOrd);
			specifyDelivery = this.logisticsBiz.updateLogisticsOrdAndGetSpecifyDelivery(ilogisticsOrd,iord);
		} catch (BizException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setCallbackType("");
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
		

		ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
		ajaxResult.setMessage(AjaxResult.MSG_OK);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_FORWARD);
		StringBuffer forwordUrlSb = new StringBuffer();
		forwordUrlSb.append(request.getContextPath());
		forwordUrlSb.append("/cust/logistics/toAddStep3MoreForward.do");
		forwordUrlSb.append("?ilogisticsOrd=");
		forwordUrlSb.append(ilogisticsOrd);
		forwordUrlSb.append("&iord=");
		forwordUrlSb.append(iord);
		forwordUrlSb.append("&specifyDelivery=");
		forwordUrlSb.append(specifyDelivery);
		
		ajaxResult.setForwardUrl(forwordUrlSb.toString());
		ajaxResult.setRel("CZWLXXGL_XZ2");
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
	
	/**
	 * 跳转 第三步页面
	 */
	@RequestMapping("/cust/logistics/toAddStep3MoreForward.do")
	public String toAddStep3MoreForward(@RequestParam(required = false) Long iord,@RequestParam(required = false) String specifyDelivery,
			@RequestParam(required = false) Long ilogisticsOrd,Model model) {

		TcustSession tcustSession = (TcustSession) AppSessionFilter.getAppSession();
		Tord tord = this.logisticsBiz.findTordByIord(iord);
		Ord4Page ord4page = this.ord4PageBiz.findOrd4Page(tord);
		
		LogisticsOrderWithMultipleAddrs4Page p = this.logisticsOrderWithMultipleAddrs4PageBiz.getLogisticsOrderWithMultipleAddrs4Page(ilogisticsOrd, tcustSession.getTcust());

		model.addAttribute("ord4page", ord4page);
		model.addAttribute("ilogisticsOrd", ilogisticsOrd);
		model.addAttribute("p", p);
		model.addAttribute("iord", iord);
		model.addAttribute("specifyDelivery", specifyDelivery);
		return "/cust/logistics/logisticsAddStep3More";
	}

	/**
	 * 添加多地址物流单 第四步
	 */
	@RequestMapping("/cust/logistics/toAddStep4More.do")
	public String toAddStep4More(
			@RequestParam(required = false) Long ilogisticsOrd,
			@RequestParam(required = false) Long iord, Model model, HttpServletRequest request) {
		String specifyDelivery = "";
		try {
			specifyDelivery = this.logisticsBiz.updateLogisticsOrdAndGetSpecifyDelivery(ilogisticsOrd,iord);
		} catch (BizException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setCallbackType("");
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
		
		ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
		ajaxResult.setMessage(AjaxResult.MSG_OK);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_FORWARD);
		StringBuffer forwordUrlSb = new StringBuffer();
		forwordUrlSb.append(request.getContextPath());
		forwordUrlSb.append("/cust/logistics/toAddStep4MoreForward.do");
		forwordUrlSb.append("?ilogisticsOrd=");
		forwordUrlSb.append(ilogisticsOrd);
		forwordUrlSb.append("&iord=");
		forwordUrlSb.append(iord);
		forwordUrlSb.append("&specifyDelivery=");
		forwordUrlSb.append(specifyDelivery);
		
		ajaxResult.setForwardUrl(forwordUrlSb.toString());
		ajaxResult.setRel("CZWLXXGL_XZ2");
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
	/**
	 * 跳转到  第四步页面
	 * @param ilogisticsOrd
	 * @param iord
	 * @param model
	 * @return
	 */
	@RequestMapping("/cust/logistics/toAddStep4MoreForward.do")
	public String toAddStep4MoreForward(
			@RequestParam(required = false) Long ilogisticsOrd,
			@RequestParam(required = false) Long iord, 
			@RequestParam(required = false) String specifyDelivery,Model model) {
		
		TcustSession tcustSession = (TcustSession) AppSessionFilter.getAppSession();	
		LogisticsOrderWithMultipleAddrs4Page p = this.logisticsOrderWithMultipleAddrs4PageBiz.getLogisticsOrderWithMultipleAddrs4Page(ilogisticsOrd, tcustSession.getTcust());
		
		model.addAttribute("p", p);
		model.addAttribute("ilogisticsOrd", ilogisticsOrd);
		model.addAttribute("specifyDelivery", specifyDelivery);
		return "/cust/logistics/logisticsAddStep4More";
		
	}

	/**
	 * 添加多地址物流单 第五步
	 */
	@RequestMapping("/cust/logistics/toAddStep5More.do")
	public String toAddStep5More(
			@RequestParam(required = false) Long ilogisticsOrd,
			@RequestParam(required = false) String specifyDelivery, Model model) {
		
		TcustSession tcustSession = (TcustSession) AppSessionFilter.getAppSession();
		this.logisticsBiz.updateLogisticsOrd(ilogisticsOrd,specifyDelivery,tcustSession.getTcust(),tcustSession.getTcustReg());
		
		ajaxResult.setMessage(AjaxResult.MSG_ADD);
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
	
	/**
	 * 
	 * @param ilogisticsCom
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/cust/logistics/getLogisticsCom4Ajax.do")
	public String getLogisticsCom4Ajax(
			@RequestParam(required = false) Long ilogisticsCom,
			HttpServletRequest request, HttpServletResponse response) {
		TlogisticsCom tlogisticsCom = this.logisticsBiz
				.queryLogisticsComByIpdt(ilogisticsCom);
		try {
			response.setCharacterEncoding("UTF-8");
			if (tlogisticsCom == null) {
				response.getWriter().print("");
			} else {
				StringBuffer rst = new StringBuffer("{");
				rst.append("\"ilogisticsCom\":" + "\""
						+ tlogisticsCom.getIlogisticsCom() + "\",");
				rst.append("\"feeFlag\":" + "\"" + tlogisticsCom.getFeeFlag()
						+ "\",");
				rst.append("\"aging\":" + "\"" + tlogisticsCom.getAging()
						+ "\",");
				rst.append("\"price\":" + "\"" + tlogisticsCom.getPrice()
						+ "\"");
				rst.append("}");
				response.getWriter().print(rst);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
