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
		$.validator.addMethod("amt", function() {
			var _shouldPayAmtFloat = parseFloat($("#shouldPayAmt").val());
			var _amt = $("#amt").val();
			if(_amt) {
				var _amtFloat = parseFloat(_amt);
				if(_amtFloat > _shouldPayAmtFloat) {
					return false;
				}
			}
			return true;
		}, "输入金额大于剩余应支付");
		
		/***********************金额格式化***************************/
		$("#amt").on("keyup", function() {
			if($(this).val().startWith("0")) { //去0化
				$(this).val("");
			}
		});
		$("#amt").on("blur", function() {
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
	<form method="post" class="pageForm required-validate"
		action="${ctx}/cust/paynotice/pay4Warehouse.do"
		onsubmit="return iframeCallback(this, dialogAjaxDone);"
		enctype="multipart/form-data">
		<div class="pageFormContent" layoutH="65">
			<p style="height: 5px;"></p>
			<p>
				<label>应支付仓管费：</label>
				<input type="text" readonly="readonly" value='<fmt:formatNumber value="${twareHouse.amt}" pattern="#,##0.00#" />' style="text-align: right;color: red;"/><span class="info">元</span>
			</p>
			<p>
				<label>已支付金额：</label>
				<input type="text" readonly="readonly" value='<fmt:formatNumber value="${tpayNotify.havepayAmt}" pattern="#,##0.00#" />' style="text-align: right;"/><span class="info">元</span>
			</p>
			<p>
				<label>剩余应支付：</label>
				<input type="text" readonly="readonly" value='<fmt:formatNumber value="${twareHouse.amt - tpayNotify.havepayAmt}" pattern="#,##0.00#" />' style="text-align: right;color: red;"/><span class="info">元</span>
				<input type="hidden" id="shouldPayAmt" value="${twareHouse.amt - tpayNotify.havepayAmt}" />
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
				<label>汇款单编号：</label>
				<input type="text" name="remittanceNo" class="required" placeholder="请输入汇款单编号"/>
			</p>
			<p>
				<label>实际汇款金额：</label>
				<input type="text" id="amt" name="amt" class="required money amt" placeholder="请输入实际汇款金额" maxlength="15" style="color: red;"/><span class="info">元</span>
			</p>
			<p>
				<label>付款凭证：</label>
					<input type='text' id='textfield1' class='txt2' />
					<input type='button' class='btn2' value='浏览...' />
					<input type="file" accept="image/*" id="voucherFile" name="voucherFile" class="upload-file2 required" onchange="document.getElementById('textfield1').value=this.value" />
			</p>
			
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