package com.newland.posmall.controller.admin;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.Order;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.TuserSession;
import org.ohuyo.rapid.base.entity.AjaxResult;
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
import com.newland.posmall.base.service.TnoSegCfgService;
import com.newland.posmall.bean.basebusi.TnoSegCfg;
import com.newland.posmall.bean.basebusi.Tpdt;
import com.newland.posmall.bean.basebusi.TpdtHis;
import com.newland.posmall.bean.basebusi.TpdtStock;
import com.newland.posmall.bean.basebusi.condition.TpdtCondition;
import com.newland.posmall.biz.admin.PdtBiz;
import com.newland.posmall.controller.BaseController;

@Scope("prototype")
@Controller("admin.pdt")
public class PdtController extends BaseController{
	
	@Resource
	private PdtBiz pdtBiz;
	
	@Resource
	private TnoSegCfgService tnoSegCfgService;
	
	private static final String NAV_TAB_ID = "CPXXWH";
	
	@RequestMapping("/admin/pdt/pdtList.do")
	public String pdtList(@RequestParam(required = false) String name,
			@RequestParam(required = false) String pdtNo,
			@RequestParam(required = false) Integer numPerPage,
			@RequestParam(required = false) Integer pageNum, Model model) {
		
		Page page = new Page();
		page.setCount(true);
		page.setCapacity(numPerPage == null ? CAPACITY : numPerPage);
		page.setPageOffset(pageNum == null ? PAGE_OFF_SET : pageNum - 1);

		TpdtCondition tpdtCondition = new TpdtCondition();
		tpdtCondition.setDelFlag(Boolean.FALSE);
		tpdtCondition.setName(name);
		tpdtCondition.setPdtNo(pdtNo);
		
		tpdtCondition.addOrders(Order.desc("updTime"));
		List<Tpdt> tpdtList = this.pdtBiz.queryAllTpdt(tpdtCondition, page);
		this.pdtBiz.setStock4Tpdt(tpdtList);
		this.pdtBiz.setTnoSegCfg4Tpdt(tpdtList);
		
		model.addAttribute(tpdtList);
		model.addAttribute(NUM_PER_PAGE, page.getCapacity());
		model.addAttribute(PAGE_NUM, pageNum);
		model.addAttribute(TOTAL_COUNT, page.getRecAmt());
		model.addAttribute("name", name);
		model.addAttribute("pdtNo", pdtNo);
		
		return "/admin/pdt/pdtList";
	}
	
	/**
	 * 查询明细
	 * @param ipdt
	 * @param model
	 * @return
	 */
	@RequestMapping("/admin/pdt/pdtDetail.do")
	public String pdtDetail(@RequestParam(required = false) Long ipdt, Model model) {

		Tpdt tpdt = this.pdtBiz.queryTpdtByIpdt(ipdt);
		TpdtStock tpdtStock = this.pdtBiz.queryTpdtStockById(ipdt);
		if (tpdtStock == null) {
			tpdtStock = new TpdtStock();
			tpdtStock.setNum(0);
		}
		TnoSegCfg tnoSegCfg = this.tnoSegCfgService.get(tpdt.getInoSegCfg());
		
		model.addAttribute("tpdt", tpdt);
		model.addAttribute("tpdtStock", tpdtStock);
		model.addAttribute("tnoSegCfg", tnoSegCfg);
		return "/admin/pdt/pdtDetail";
	}

	/**
	 * 去修改页面
	 */
	@RequestMapping("/admin/pdt/toPdtModify.do")
	public String pdtModify(@RequestParam(required = false) Long ipdt, Model model) {
		Tpdt tpdt = this.pdtBiz.queryTpdtByIpdt(ipdt);
		
		TnoSegCfg tnoSegCfg = this.tnoSegCfgService.get(tpdt.getInoSegCfg());
		
		model.addAttribute(tpdt);
		model.addAttribute("tnoSegCfg", tnoSegCfg);
		model.addAttribute("pre_length", TnoSegCfgService.PRE_LENGTH);
		model.addAttribute("value_length", TnoSegCfgService.VALUE_LENGTH);
		
		Map<String, TnoSegCfg> noSegCfgMap = this.pdtBiz.genNoSegCfgMap();
		model.addAttribute("noSegCfgMap", noSegCfgMap);
		
		return "/admin/pdt/pdtModify";
	}
	
	/**
	 * 去翻新增页面
	 */
	@RequestMapping("/admin/pdt/toPdtAdd.do")
	public String toPdtAdd(Model model) {
		Map<String, TnoSegCfg> noSegCfgMap = this.pdtBiz.genNoSegCfgMap();
		model.addAttribute("noSegCfgMap", noSegCfgMap);
		model.addAttribute("pre_length", TnoSegCfgService.PRE_LENGTH);
		model.addAttribute("value_length", TnoSegCfgService.VALUE_LENGTH);
		return "/admin/pdt/pdtAdd";
	}
	
	/**
	 * 产品新增
	 * @param tpdt
	 * @param tnoSegCfg 号段数据
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/admin/pdt/pdtAdd.do",method = RequestMethod.POST)
	public String pdtAdd(Tpdt tpdt, @RequestParam(required=false) String pre, 
			@RequestParam(required=false) Long start, @RequestParam(required=false) Long end, Model model) {
		
		try {
			TuserSession tuserSession = (TuserSession) AppSessionFilter.getAppSession();
			this.pdtBiz.addTpdt(tpdt, pre, start, end, tuserSession.getTuser());
		} catch (BizException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setNavTabId(NAV_TAB_ID);
			ajaxResult.setCallbackType("");
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
		ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
		ajaxResult.setMessage(AjaxResult.MSG_ADD);
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}

	/**
	 * 修改产品信息
	 * @param tpdt
	 * @param tnoSegCfg 号段数据
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/admin/pdt/pdtModify.do",method = RequestMethod.POST)
	public String pdtModify(Tpdt tpdt,
			@RequestParam(required=false) Long inoSegCfg,
			@RequestParam(required=false) String pre, 
			@RequestParam(required=false) Long start, 
			@RequestParam(required=false) Long end, Model model) {
		
		try {
			TuserSession tuserSession = (TuserSession) AppSessionFilter.getAppSession();
			this.pdtBiz.modifyTpdtByIpdt(tpdt, inoSegCfg, pre, start, end, tuserSession.getTuser());
		} catch (BizException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setNavTabId(NAV_TAB_ID);
			ajaxResult.setCallbackType("");
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		} catch (Exception e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMessage());
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

	/**
	 * 删除
	 */
	@RequestMapping("/admin/pdt/pdtRemove.do")
	public String pdtRemove(@RequestParam(required = false) Long ipdt,
			Model model) {
		TuserSession tuserSession = (TuserSession) AppSessionFilter.getAppSession();
		
		try {
			this.pdtBiz.removeTpdtByIpdt(ipdt, tuserSession.getTuser());
		} catch (BizException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
			ajaxResult.setNavTabId(NAV_TAB_ID);
			ajaxResult.setCallbackType("");
			model.addAttribute(ajaxResult);
			return "/common/ajaxResult";
		}
		
		ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
		ajaxResult.setMessage(AjaxResult.MSG_DELETE);
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
	
	@RequestMapping("/admin/pdt/toPdtRemove.do")
	public String toPdtRemove(@RequestParam(required = false) Long ipdt,
		Model model) {
			Tpdt tpdt = this.pdtBiz.queryTpdtByIpdt(ipdt);
			model.addAttribute(tpdt);
			return "/admin/pdt/pdtRemove";
	}
	
	/**
	 * ajax验证
	 * @param ipdt
	 * @param model
	 * @return
	 */
	@RequestMapping("/admin/pdt/pdtHisList.do")
	public String pdtHisList(@RequestParam("ipdt") Long ipdt, Model model){
		
		Tpdt tpdt = this.pdtBiz.queryTpdtByIpdt(ipdt);
		List<TpdtHis> pdtHisList =  this.pdtBiz.queryTpdtHisByIpdt(ipdt);
		List<TpdtHis> pdtHisListPrice = new ArrayList<TpdtHis>();
		TpdtHis tpdtHisTmep = null;
		if(!CollectionUtils.isEmpty(pdtHisList)){
			if(pdtHisList.size() == 1){
				pdtHisListPrice = pdtHisList;
			}else{
				for(int i = 0; i < pdtHisList.size(); i++){
					if(tpdtHisTmep == null){
					   tpdtHisTmep = pdtHisList.get(i);
					}else{
					   if(tpdtHisTmep.getPrice().compareTo(pdtHisList.get(i).getPrice()) != 0){	
						   pdtHisListPrice.add(tpdtHisTmep);
					   }
					   tpdtHisTmep = pdtHisList.get(i);
					   if(i == pdtHisList.size()-1){
						   pdtHisListPrice.add(pdtHisList.get(i));
					   }
					}
				}
			}
		}
		
		model.addAttribute("pdtHisList",pdtHisListPrice);
		model.addAttribute("pdtHisListNum",(pdtHisListPrice!=null)?pdtHisListPrice.size():0);
		model.addAttribute("pdtNo", tpdt.getPdtNo());
		model.addAttribute("name", tpdt.getName());
		return "/admin/pdt/pdtHisList";
	}
	
	
	
    @RequestMapping(value = "/admin/pdt/validatePdtUniq.do", method = RequestMethod.GET) 
    public @ResponseBody String validatePdtUniq(
    		@RequestParam(required = false) String pdtName, 
    		@RequestParam(required = false) String pdtNo,
    		@RequestParam(required = false) Long ipdt) { 
    	
      	Boolean flag = pdtBiz.validatePdtNameOrNo(pdtName, pdtNo, ipdt);
    	return "{\"result\": \""+flag+"\"}";
    }
    
    /**
     * 跳转到产品库存修改页面
     * @param ipdt
     * @param model
     * @return
     */
	@RequestMapping("/admin/pdt/toModifyStock.do")
	public String toModifyStock(@RequestParam(required = false) Long ipdt, Model model) {
		Tpdt tpdt = this.pdtBiz.queryTpdtByIpdt(ipdt);
		TpdtStock tpdtStock = this.pdtBiz.queryTpdtStockById(ipdt);
		if (tpdtStock == null) {
			tpdtStock = new TpdtStock();
			tpdtStock.setNum(0);
		}
		model.addAttribute("tpdt", tpdt);
		model.addAttribute("tpdtStock", tpdtStock);
		return "/admin/pdt/modifyStock";
	}
	
	/**
	 * 修改产品库存
	 * @param ipdt
	 * @param num
	 * @param model
	 * @return
	 */
	@RequestMapping("/admin/pdt/modifyStock.do")
	public String modifyStock(@RequestParam(required = false) Long ipdt,
			@RequestParam(required = false) Integer num, Model model) {
		TuserSession session = (TuserSession) AppSessionFilter.getAppSession();
		try {
			this.pdtBiz.modifyStock(ipdt, num, session.getTuser());
		} catch (BizException e) {
			ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_NO);
			ajaxResult.setMessage(e.getMsg());
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
