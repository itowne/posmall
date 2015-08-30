<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<style type="text/css">

.txt2 {
	height: 17px;
	border: 1px solid #cdcdcd;
	/* width: 180px; */
	/* margin-left: 5px; */
}

.btn2 {
	background-color: #FFF;
	border: 1px solid #CDCDCD;
	height: 23px;
	width: 70px;
	margin-left: 5px;
}

.upload-file2 {
	position: absolute;
	top: 4px;
	right: 36px;
	height: 24px;
	filter: alpha(opacity : 0);
	opacity: 0;
	width: 214px;
}
</style>
<script type="text/javascript">
	$(function() {
		//异常、错误信息提示
		var errMsg = "${errMsg}";
		if(errMsg != null && errMsg.trim() != "") {
			alertMsg.error("${errMsg}");
			$.pdialog.closeCurrent();
		}
		
		/***********************（汇款金额 + 本次使用保证金 <= 应付款）***************************/		
		$.validator.addMethod( "remittanceAmt",function() {
			var _shouldAmtFloat = parseFloat($("#shouldAmt").val()); //应付款
			var _depositUsed4Logistics = 0;
			if($("#depositUsed4Logistics").val()) {
				_depositUsed4Logistics = parseFloat($("#depositUsed4Logistics").val()); //使用保证金
			}
			var _remittanceAmt = $.trim($("#remittanceAmt").val()); //汇款金额
			if(_remittanceAmt) {
				var _remittanceAmtFloat = parseFloat(_remittanceAmt);
				if((_remittanceAmtFloat + _depositUsed4Logistics) > _shouldAmtFloat) {
					return false;
				}
			}
			return true;
		},"金额不应大于应付款" );
		
		
		/***********************金额格式化***************************/
		$("#remittanceAmt").on("keyup", function() {
			if($(this).val().startWith("0")) { //去0化
				$(this).val("");
			}
		});
		$("#remittanceAmt").on("blur", function() {
	        var money = $(this).val();
	        if (money.length > 0) {
	            var numcheck = /^\d{1,10}(\.(\d{1,2})?)?$/;
	            if (numcheck.test(money)) {
	                var mm = parseFloat(money).toFixed(2);
	                $(this).val(mm);
	            } else {
	                $(this).val("");
	            }

	        }
	    });
	});
	
</script>
<body>
<div class="pageContent">
	<form id="orderPayForm" method="post" class="pageForm required-validate"
		action="${ctx}/cust/paynotice/pay4Logistics.do" onsubmit="return iframeCallback(this, dialogAjaxDone);" enctype="multipart/form-data">
		<div class="pageFormContent" layoutH="65">
			<p>
				<label>物流费：</label>
				<input type="text" readonly="readonly" value='<fmt:formatNumber value="${tlogisticsOrd.logisticsFreight}" pattern="#,##0.00#" />' style="text-align: right;"/><span class="info">元</span>
			</p>
			<p>
				<label>已支付：</label>
				<input type="text" readonly="readonly" value='<fmt:formatNumber value="${tpayNotify.havepayAmt}" pattern="#,##0.00#" />' style="text-align: right;"/><span class="info">元</span>
			</p>
			<p>
				<label>未支付：</label>
				<input type="text" readonly="readonly" value='<fmt:formatNumber value="${tlogisticsOrd.logisticsFreight - tpayNotify.havepayAmt}" pattern="#,##0.00#" />' style="text-align: right;color: red;"/><span class="info">元</span>
				<input id="shouldAmt" type="hidden" value="${tlogisticsOrd.logisticsFreight - tpayNotify.havepayAmt}"/>
			</p>
			<div class="divider"></div>
			<!-- 理论上不存在该情况 -->
			<c:if test="${depositUsed4Logistics != null && depositUsed4Logistics > 0}">
				<p>
					<label>支付方式：</label>
					<input readonly="readonly" value='<posmall:dict dictType="pay_method" dictCode="DEPOSIT"></posmall:dict>' />
				</p>
				<p>
					<label>剩余保证金：</label>
					<input type="text" readonly="readonly" value='<fmt:formatNumber value="${tagmt.remainDeposit}" pattern="#,##0.00#" />' style="text-align: right;"/><span class="info">元</span>
				</p>
				<p>
					<label>本次使用保证金：</label>
					<input type="text" readonly="readonly" value='<fmt:formatNumber value="${depositUsed4Logistics}" pattern="#,##0.00#" />' style="text-align: right;color: red;"/><span class="info">元</span>
					<input type="hidden" id="depositUsed4Logistics" value="${depositUsed4Logistics}"/>
				</p>
				<div class="divider"></div>
			</c:if>
			
			<div id="remittanceDIV">
				<p>
					<label>支付方式：</label>
					<input readonly="readonly" value='<posmall:dict dictType="pay_method" dictCode="REMITTANCE"></posmall:dict>' />
				</p>
				<p>
					<label>银行账号：</label>
					<input type="text" readonly="readonly" name="account" value="${tcustReg.account}" />
				</p>
				<p>
					<label>开户行：</label>
					<input type="text" readonly="readonly" name="bank" value="${tcustReg.bank}" />
				</p>
				<p>
					<label>剩余应支付：</label>
					<input type="text" readonly="readonly" value="${tlogisticsOrd.logisticsFreight - tpayNotify.havepayAmt - depositUsed4Logistics}" style="color: red;"/><span class="info">元</span>
				</p>
				<p>
					<label>汇款单编号：</label>
					<input type="text" name="remittanceNo" class="required" placeholder="请输入汇款单编号"/>
				</p>
				<p>
					<label>实际汇款金额：</label>
					<input type="text" id="remittanceAmt" name="amt" class="required money remittanceAmt" placeholder="请输入实际汇款金额" maxlength="15" style="color: red;"/><span class="info">元</span>
				</p>
				<p>
					<label>付款凭证：</label>
						<input type='text' id='textfield1' class='txt2' />
						<input type='button' class='btn2' value='浏览...' />
						<input type="file" accept="image/*" name="voucherFile" class="upload-file2 required" onchange="document.getElementById('textfield1').value=this.value" />
				</p>
			</div>
		</div>
		<input type="hidden" name="ipayNotify" value="${tpayNotify.ipayNotify}" />
		<!-- 上传文件默认存储位置：数据库 -->
		<input type="hidden" name="location" value="1">
		<div class="formBar">
		<ul>
			<li>
				<div class="buttonActive">
					<div class="buttonContent">
						<button type="submit" class="submit">&nbsp;&nbsp;&nbsp;提&nbsp;&nbsp;交&nbsp;&nbsp;&nbsp;</button>
					</div>
				</div>
			</li>
			<li>
				<div class="button">
					<div class="buttonContent">
						<button type="button" class="close">&nbsp;&nbsp;&nbsp;返&nbsp;&nbsp;回&nbsp;&nbsp;&nbsp;</button>
					</div>
				</div>
			</li>
		</ul>
	</div>
	</form>
</div>
</body>
</html>