package com.newland.posmall.biz.cust;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Order;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.entity.ERPMaintenance;
import org.ohuyo.rapid.base.entity.Tfile;
import org.ohuyo.rapid.base.entity.TsysParam;
import org.ohuyo.rapid.base.service.MaintenanceManageService;
import org.ohuyo.rapid.base.service.TfileService;
import org.ohuyo.rapid.base.service.TsysParamService;
import org.ohuyo.rapid.file.CsvFileTranslator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.newland.posmall.base.exception.BizException;
import com.newland.posmall.base.service.TlogService;
import com.newland.posmall.base.service.TpayNotifyService;
import com.newland.posmall.base.service.TrenewDetailService;
import com.newland.posmall.base.service.TrenewService;
import com.newland.posmall.bean.basebusi.TpayNotify;
import com.newland.posmall.bean.common.Tlog;
import com.newland.posmall.bean.customer.Tcust;
import com.newland.posmall.bean.customer.TcustReg;
import com.newland.posmall.bean.customer.Trenew;
import com.newland.posmall.bean.customer.TrenewDetail;
import com.newland.posmall.bean.customer.condition.TrenewCondition;
import com.newland.posmall.bean.dict.LogType;
import com.newland.posmall.bean.dict.OperType;
import com.newland.posmall.bean.dict.PayStatus;
import com.newland.posmall.bean.dict.PayType;
import com.newland.posmall.bean.dict.RenewStatus;
import com.newland.posmall.controller.cust.model.RenewData;
import com.newland.posmall.controller.cust.model.RenewData4Page;

@Service
@Transactional(rollbackFor = Throwable.class)
public class CustRenewBiz {
	
	@Resource
	private TrenewService trenewService;
	
	@Resource
	private TrenewDetailService trenewDetailService;
	
	@Resource
	private MaintenanceManageService maintenanceManageService;
	
	@Resource
	private TfileService tfileService;
	
	@Resource
	private TpayNotifyService tpayNotifyService;
	
	@Resource
	private TsysParamService tsysParamService;
	
	@Resource
	private TlogService tlogService;
	
	public Trenew queryById(Long id) {
		if(id == null) return null;
		return this.trenewService.findById(id);
	}
	
	/**
	 * 分页查询续保数据列表
	 * @param condition
	 * @param tcust
	 * @param page
	 * @return
	 */
	public List<Trenew> queryPageList(TrenewCondition condition, Tcust tcust, Page page) {
		if (condition == null) {
			condition = new TrenewCondition();
		}
		
		condition.setIcustList(Arrays.asList(tcust.getIcust()));
		
		condition.addOrders(Order.desc("updTime"));
		return this.trenewService.find(condition, page);
	}
	
	/**
	 * 从已保存的数据文件解析续保数据
	 * @param ifile 数据文件
	 * @return
	 * @throws BizException
	 */
	public List<RenewData> getRenewDataFromTfile(Long ifile) throws BizException {
		if(ifile == null) {
			return null;
		}
		Tfile tfile = this.tfileService.getById(ifile);

		CsvFileTranslator trans = new CsvFileTranslator(",");
		InputStreamReader reader = null;
		try {
			reader = new InputStreamReader(new ByteArrayInputStream(tfile.getContent()), "gbk");
		} catch (UnsupportedEncodingException e) {
			throw new BizException(1, "文件解析失败");
		}
		List<RenewData> dataList = trans.fromFile(reader, RenewData.class, true);
		return dataList;
	}
	
	/**
	 * 续保数据校验
	 * @param renewDataList
	 * @param tcust
	 * @return
	 */
	public String validateRenewData(List<RenewData> renewDataList, Tcust tcust) {
		if(CollectionUtils.isEmpty(renewDataList)) return "续保数据为空";
		StringBuffer errMsg = new StringBuffer("");
		StringBuffer seqNoBuffer = new StringBuffer("");
		StringBuffer resubmitSeqno = new StringBuffer(""); //重复提交申请的产品序列号
		//校验文件数据中是否有序列号重复
		for (RenewData renewData : renewDataList) {
			if(seqNoBuffer.indexOf(renewData.getSeqNo()) >= 0) {
				if(errMsg.indexOf("产品序列号[" + renewData.getSeqNo() + "]重复") < 0) {
					errMsg.append("产品序列号[" + renewData.getSeqNo() + "]重复；");
				}
				renewData.setErrTips("产品序列号重复");
				continue;
			}
			seqNoBuffer.append(renewData.getSeqNo() + ",");
		}
		if(errMsg.length() >= 1) {
			return errMsg.substring(0, errMsg.length() - 1);
		}
		for (RenewData renewData : renewDataList) {
			ERPMaintenance erpMaintenanceDB = this.maintenanceManageService.findBySeqNo(renewData.getSeqNo());
			//校验序列号
			if(erpMaintenanceDB == null) {
				errMsg.append("产品序列号[" + renewData.getSeqNo() + "]不存在；");
				renewData.setErrTips("产品序列号不存在");
				continue;
			}
			//校验产品型号
			if(erpMaintenanceDB.getPh().equals(renewData.getPdtNo()) == false) {
				errMsg.append("产品序列号[" + renewData.getSeqNo() + "]与型号[" + renewData.getPdtNo() + "]不匹配；");
				renewData.setErrTips("产品序列号与型号不匹配");
				continue;
			}
			//判断是否该用户数据
			if(erpMaintenanceDB.getIcust().equals(tcust.getIcust()) == false) {
				errMsg.append("产品序列号[" + renewData.getSeqNo() + "]或型号[" + renewData.getPdtNo() + "]有误；");
				renewData.setErrTips("产品序列号或型号有误");
				continue;
			}
			
			// 判断续保申请中，是否有已经提交续保申请但未生效的数据
			List<TrenewDetail> details = this.trenewDetailService.findBySeqNo(renewData.getSeqNo());
			if(CollectionUtils.isEmpty(details) == false) {
				for (TrenewDetail trenewDetail : details) {
					Trenew trenewDB = this.trenewService.findById(trenewDetail.getIrenew());
					if(trenewDB.getRenewStatus().equals(RenewStatus.WAIT_AUDIT)) {
						resubmitSeqno.append(renewData.getSeqNo() + "、");
						renewData.setErrTips("已提交续保申请");
						break;
					}
				}
				if (resubmitSeqno.length() >= 1) {
					continue;
				}
			}
			
			renewData.setIerpMaintenance(erpMaintenanceDB.getIerpMaintenance());
			renewData.setLifeStartTime(erpMaintenanceDB.getLifeStartTime());
			renewData.setLifeEndTime(erpMaintenanceDB.getWarrantyPeriod());
		}
		
		if(resubmitSeqno.length() >= 1) {
			errMsg.append("产品序列号[ " + resubmitSeqno.substring(0, resubmitSeqno.length() - 1) + " ]已提交续保申请，审核过程中不能重复提交;");
		}
		return (errMsg.length() >= 1) ? errMsg.substring(0, errMsg.length() - 1) : null;
	}
	
	/**
	 * 提交续保申请
	 * @param renewData4Page
	 * @param tcust
	 * @param tcustReg
	 * @throws BizException
	 */
	public void renewAdd(RenewData4Page renewData4Page, Tcust tcust, TcustReg tcustReg) throws BizException {
		if(renewData4Page == null || CollectionUtils.isEmpty(renewData4Page.getRenewDataList())) {
			throw new BizException(0, "数据获取失败，请重新导入续保数据");
		}
		if(StringUtils.isBlank(renewData4Page.getRenewLife())) {
			throw new BizException(0, "续保期限获取失败，请重新导入续保数据");
		}
		int renewLife = Integer.valueOf(renewData4Page.getRenewLife());
		
		String msg = this.validateRenewData(renewData4Page.getRenewDataList(), tcust);
		if(StringUtils.isNotBlank(msg)) {
			throw new BizException(0, msg);
		}
		
		// 计算续保费用
		TsysParam renewPriceParam = this.tsysParamService.getTsysParam("OTHER_CONF", "RENEW_PRICE");
		if(renewPriceParam == null || StringUtils.isBlank(renewPriceParam.getValue())) {
			throw new BizException(0, "【续保单价】未配置，请联系管理员");
		}
		BigDecimal renewPrice = null;
		try {
			renewPrice = new BigDecimal(renewPriceParam.getValue());
		} catch (Exception e) {
			throw new BizException(0, "【续保单价】格式化失败，请联系管理员");
		}
		BigDecimal renewAmt = renewPrice.multiply(
				new BigDecimal(renewData4Page.getRenewDataList().size())).multiply(
						new BigDecimal(renewLife)).setScale(2, RoundingMode.HALF_UP);
		
		//添加续保数据
		Trenew trenew = new Trenew();
		trenew.setIcust(tcust.getIcust());
		trenew.setCustName(tcustReg.getName());
		trenew.setRenewLife(renewLife);
		trenew.setRenewAmt(renewAmt);
		trenew.setPayStatus(PayStatus.WAIT_PAY);
		trenew.setRenewStatus(RenewStatus.WAIT_AUDIT);
		trenew.setRenewTime(new Date());
		this.trenewService.addTrenew(trenew);
		
		StringBuffer seqNos = new StringBuffer();
		//添加续保明细数据
		for (RenewData data : renewData4Page.getRenewDataList()) {
			TrenewDetail trenewDetail = new TrenewDetail();
			trenewDetail.setIerpMaintenance(data.getIerpMaintenance());
			trenewDetail.setIrenew(trenew.getIrenew());
			trenewDetail.setPdtNo(data.getPdtNo());
			trenewDetail.setSeqNo(data.getSeqNo());
			trenewDetail.setLifeEndTime(data.getLifeEndTime());
			trenewDetail.setLifeStartTime(data.getLifeStartTime());
			
			this.trenewDetailService.addTrenewDetail(trenewDetail);
			
			seqNos.append(data.getSeqNo() + ",");
		}
		
		// 生成付款通知书
		TpayNotify newTpayNotify = new TpayNotify();
		newTpayNotify.setIcust(tcust.getIcust());
		newTpayNotify.setIfk(trenew.getIrenew());
		newTpayNotify.setIhisFk(trenew.getIrenew());
		newTpayNotify.setMemo("续保费用支付通知书");
		newTpayNotify.setPayNotifyStatus(PayStatus.WAIT_PAY);
		newTpayNotify.setPayType(PayType.RENEW_AMT);
		this.tpayNotifyService.save(newTpayNotify);
		
		// 记录日志
		Tlog newTlog = new Tlog();
		newTlog.setIfk(tcust.getIcust());
		newTlog.setLogType(LogType.CUST);
		newTlog.setMemo("客户续保申请");
		newTlog.setOperType(OperType.RENEW_MGR);
		newTlog.setPreData("null");
		newTlog.setData("续保数据：id[" + trenew.getIrenew() + 
				"]，产品序列号[" + seqNos.toString() + 
				"]，续保期限[" + renewLife + "年]，续保费用[" + renewAmt + "元]");
		this.tlogService.save(newTlog);
		
		//TODO 发送邮件
	}
	
	public List<TrenewDetail> queryTrenewDetailList(Long irenew) {
		return this.trenewDetailService.findListByTrenewId(irenew);
	}
	
	public void renewCacel(Long irenew, Tcust tcust) throws BizException {
		
		// 修改续保记录状态为：已撤销
		Trenew trenewDB = this.trenewService.findById(irenew);
		if(trenewDB.getRenewStatus().equals(RenewStatus.REVOKED)) {
			throw new BizException(0, "续保申请已经撤销，不能重复操作");
		}
		if(trenewDB.getRenewStatus().equals(RenewStatus.AUDIT_PASS)) {
			throw new BizException(0, "操作有误：续保已生效");
		}
		if(trenewDB.getPayStatus().equals(PayStatus.HAVE_PAY) || 
				trenewDB.getPayStatus().equals(PayStatus.PART_PAY) ||
				trenewDB.getPayStatus().equals(PayStatus.WAIT_AUDIT)) {
			throw new BizException(0, "续保已付款，如需撤销请联系销售业务员");
		}
		if (!trenewDB.getIcust().equals(tcust.getIcust())) {
			throw new BizException(0, "非法操作：非当前用户续保申请");
		}
		trenewDB.setRenewStatus(RenewStatus.REVOKED);
		this.trenewService.update(trenewDB);
		
		// 修改续保明细删除标志
		List<TrenewDetail> details = this.trenewDetailService.findListByTrenewId(trenewDB.getIrenew());
		for (TrenewDetail trenewDetail : details) {
			trenewDetail.setDelFlag(Boolean.TRUE);
			this.trenewDetailService.update(trenewDetail);
		}
		
		// 删除付款通知书
		TpayNotify tpayNotifyDB = this.tpayNotifyService.findByifkAndPayType(trenewDB.getIrenew(), PayType.RENEW_AMT);
		this.tpayNotifyService.delete(tpayNotifyDB.getIpayNotify());
		
		// 记录日志
		Tlog newTlog = new Tlog();
		newTlog.setIfk(tcust.getIcust());
		newTlog.setLogType(LogType.CUST);
		newTlog.setMemo("客户续保申请撤销");
		newTlog.setOperType(OperType.RENEW_MGR);
		newTlog.setPreData("null");
		newTlog.setData("续保数据：id[" + trenewDB.getIrenew() + "]，"
				+ "删除付款通知书：[" + tpayNotifyDB.getIpayNotify() + "," + tpayNotifyDB.getPayNotifyNo() + "]");
		this.tlogService.save(newTlog);
	}
}
