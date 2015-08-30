package com.newland.posmall.controller.admin;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.entity.AjaxResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.newland.posmall.bean.basebusi.Tcontract;
import com.newland.posmall.biz.admin.ContractBiz;
import com.newland.posmall.controller.BaseController;

@Scope("prototype")
@Controller("admin.contract")
public class ContractController extends BaseController {
	
//	private static Logger logger = LoggerFactory.getLogger(ContractController.class);
	
	@Resource
	private ContractBiz contractBiz;
	
	private static final String NAV_TAB_ID = "HTGL";
	
	
	@RequestMapping("/admin/contract/contractList.do")
	public String contractList(Tcontract tcontract,
			@RequestParam(required = false) Integer numPerPage,
			@RequestParam(required = false) Integer pageNum,Model model) {
		
		Page page = new Page();
		page.setCount(true);
		page.setCapacity(numPerPage == null ? CAPACITY : numPerPage);
		page.setPageOffset(pageNum == null ? PAGE_OFF_SET : pageNum - 1);
		
		
		List<Tcontract> tcontractList = this.contractBiz.queryAllTcontract(tcontract, page);
		
		model.addAttribute("tcontractList",tcontractList);
		
		model.addAttribute("tcontract",tcontract);

		model.addAttribute(NUM_PER_PAGE, page.getCapacity());
		model.addAttribute(PAGE_NUM, pageNum);
		model.addAttribute(TOTAL_COUNT, page.getRecAmt());
		return "/admin/contract/contractList";
	}
	
	/**
	 * 查询明细
	 */
	@RequestMapping("/admin/contract/contractDetail.do")
	public String contractDetail(@RequestParam(required = false) Long icontract,
			Model model) {

		Tcontract tcontract = this.contractBiz.queryTcontractByIcontract(icontract);
		model.addAttribute("tcontract",tcontract);
		return "/admin/contract/contractDetail";
	}

	/**
	 * 去修改页面
	 */
	@RequestMapping("/admin/contract/toContractModify.do")
	public String contractModify(@RequestParam(required = false) Long icontract,
			Model model) {
		Tcontract tcontract = this.contractBiz.queryTcontractByIcontract(icontract);
		model.addAttribute("tcontract",tcontract);
		Map<String, String> custCodeMap = this.contractBiz.queryTcustCodeMap();
		model.addAttribute("custCodeMap", custCodeMap);
		return "/admin/contract/contractModify";
	}
	
	/**
	 * 去翻新增页面
	 */
	@RequestMapping("/admin/contract/toContractAdd.do")
	public String toContractAdd(Model model) {
		Map<String, String> custCodeMap = this.contractBiz.queryTcustCodeMap();
		model.addAttribute("custCodeMap", custCodeMap);
		return "/admin/contract/contractAdd";
	}
	
	/**
	 * 新增
	 */
	@RequestMapping(value = "/admin/contract/contractAdd.do", method = RequestMethod.POST)
	public String contractAdd(Tcontract tcontract, Model model) {

		this.contractBiz.addTcontract(tcontract);

		ajaxResult.setStatusCode(AjaxResult.STATUS_CODE_OK);
		ajaxResult.setMessage(AjaxResult.MSG_ADD);
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType(AjaxResult.CALL_BACK_TYPE_CLOSE_CURRENT);
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}

	/**
	 * 修改
	 */
	@RequestMapping(value = "/admin/contract/contractModify.do", method = RequestMethod.POST)
	public String contractModify(Tcontract tcontract, Model model) {

		this.contractBiz.modifyTcontract(tcontract);

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
	@RequestMapping("/admin/contract/contractRemove.do")
	public String contractRemove(@RequestParam(required = false) Long icontract,
			Model model) {

		this.contractBiz.removeTcontract(icontract);
		ajaxResult.setMessage(AjaxResult.MSG_DELETE);
		ajaxResult.setNavTabId(NAV_TAB_ID);
		ajaxResult.setCallbackType("");
		model.addAttribute(ajaxResult);
		return "/common/ajaxResult";
	}
	
	
    @RequestMapping(value = "/admin/contract/validateContractUniq.do", method = RequestMethod.GET) 
    public @ResponseBody String validateContractUniq(
    		@RequestParam(required = false) String name, 
    		@RequestParam(required = false) Long id) { 
    	
      	Boolean flag = contractBiz.validateContractUniq(name, id);
    	return "{\"result\": \""+flag+"\"}";
    } 
	

}
