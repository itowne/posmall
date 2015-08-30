<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<style type="text/css">
	.tips {
		float: left;
		padding: 15px 0 15px 0;
	}
</style>
<div class="pageContent">
	<form id="agmtAddForm" method="post" action="${ctx}/cust/agmt/signAgree.do" class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="65">
		<p style="width: 325px;">
			<label style="width: 85px;">协议起始日期：</label>
			<input type="text" readonly="readonly" name="startDateOfAgmt" value='<fmt:formatDate value="${startDateOfAgmt}" pattern="yyyy-MM-dd"/>' />
		</p>
		<p style="width: 325px;padding-right: 130px;">
			<label style="width: 85px;">协议结束日期：</label>
			<input type="text" readonly="readonly" name="endDateOfAgmt" value='<fmt:formatDate value="${endDateOfAgmt}" pattern="yyyy-MM-dd"/>' />
		</p>
		<div class="tips"><jsp:include page="_agmtTips.jsp" /></div>
		<div class="table">
		<table class="list" width="100%">
			<thead>
				<tr>
					<th width="150px">产品名称</th>
					<th width="150px">单价</th>
					<th width="150px">折扣价</th>
					<th width="200px">订货量</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${agmtTpdtList}" var="tpdt" varStatus="status">			
					<input type="hidden" id="tpdtAmt${status.index}" class="tpdtAmt">
					<input type="hidden" name="agmtTpdtList[${status.index}].custPdtRate" value="${tpdt.custPdtRate}">
					<input type="hidden" name="agmtTpdtList[${status.index}].ipdt" value="${tpdt.ipdt}">
					<input type="hidden" name="agmtTpdtList[${status.index}].ipdtHis" value="${tpdt.ipdtHis}">
					<input type="hidden" name="agmtTpdtList[${status.index}].name" value="${tpdt.name}">
					<input type="hidden" id="priceAfterRate${status.index}" name="agmtTpdtList[${status.index}].priceAfterRate" value="${tpdt.priceAfterRate}" />
				<tr>
					<td id="tpdtName${tpdt.name}" align="center">${tpdt.name}</td>
					<td align="right"><fmt:formatNumber value="${tpdt.price}" pattern="¥#,##0.00#" /></td>
					<td align="right"><fmt:formatNumber value="${tpdt.priceAfterRate}" pattern="¥#,##0.00#" /></td>
					<td align="center">
						<input type="text" id="orderNum${status.index}" class="orderNum digits" name="agmtTpdtList[${status.index}].pdtAgmtNum" style="float: none;" maxlength="6"/>台
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		</div>
		<div class="total">
			<p style="width: 240px;margin-top: 20px;">
				<label style="width: 50px;">总量：</label>
				<input id="totalNum" type="text" readonly="readonly" value="0" style="text-align: right;"/><span class="info">台</span>
			</p>
			<p style="width: 240px;margin-top: 20px;">
				<label style="width: 50px;">总货款：</label>
				<input id="totalAmt" type="text" readonly="readonly" value="¥0.00" style="text-align: right;"/>
			</p>
			<p style="width: 240px;margin-top: 20px;">
				<label style="width: 50px;">保证金：</label>
				<input id="depositAmt" type="text" readonly="readonly" value="¥0.00" style="text-align: right;"/>
			</p>
		</div>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="submit" onclick="return beforeSubmit();">&nbsp;&nbsp;&nbsp;确&nbsp;&nbsp;定&nbsp;&nbsp;&nbsp;</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">&nbsp;&nbsp;&nbsp;取&nbsp;&nbsp;消&nbsp;&nbsp;&nbsp;</button>
						</div>
					</div>
				</li>
			</ul>
		</div>

		<!-- 隐藏属性 -->
		<input type="hidden" name="agmtTpdtTotalAmt"><!-- 总货款 -->
		<input type="hidden" name="agmtDeposit"><!-- 保证金 -->
		<input type="hidden" name="startYear" value="${startYear}"><!-- 协议起始年 -->
		<input type="hidden" name="startMonth" value="${startMonth}"><!-- 协议起始月 -->
		<input type="hidden" name="endYear" value="${endYear}"><!-- 协议终止年  -->
		<input type="hidden" name="endMonth" value="${endMonth}"><!-- 协议终止月 -->
		<input type="hidden" id="depositRate" value="${depositRate}"><!-- 保证金比率 -->
		<input type="hidden" id="minNum" value="${minNum}"><!-- 起订数量 -->
	</form>
</div>
<script type="text/javascript">
$(function(){
	//异常、错误信息提示
	if("${ajaxResult.statusCode}" == "300") {
		alertMsg.error("${ajaxResult.message}");
		$.pdialog.closeCurrent();
	}
	
	/**************************************************/
	$(".orderNum").on("keypress", function(event) {
		var keyCode = event.which;
        if (!(keyCode >= 48 && keyCode <= 57) && (keyCode != 8)) { //只能输入数字、回退键、删除键
        	return false;
        }
	});
	
	$(".orderNum").on("keyup", function() {
		/**************************************************/
		if($(this).val().startWith("0")) { //去0化
			$(this).val("");
		}
		
		var _orderNumId = $(this).attr("id");
		var _orderNumIdIndex = _orderNumId.charAt(_orderNumId.length - 1);
		var _tpdtInputNum = parseInt($(this).val()); //订货量
		var _tpdtTotalNum = 0;
		/**************************************************/
		//1、输入产品数量控制
		var _totalNum = 0;
		$(".orderNum").each(function() {
			if($(this).val()) {
				_totalNum = _totalNum + parseInt($(this).val());
			}
		});
		$("#totalNum").val(_totalNum);
		/**************************************************/
		//2、计算每类产品总价值
		var _priceAfterRate = parseFloat($("#priceAfterRate" + _orderNumIdIndex).val()); //产品单价
		if(_tpdtInputNum != null && _tpdtInputNum > 0) {
			$("#tpdtAmt" + _orderNumIdIndex).val(_priceAfterRate * _tpdtInputNum);
		}else {
			$("#tpdtAmt" + _orderNumIdIndex).val(0.00);
		}
		/**************************************************/
		//3、计算所有产品总价值、保证金
		var _totalAmt = 0.00;
		$(".tpdtAmt").each(function() {
			if($(this).val()) {
				_totalAmt = _totalAmt + parseFloat($(this).val());
			}else {
				_totalAmt = _totalAmt + 0.00;
			}
		});
		$("#totalAmt").val("¥" + _totalAmt.formatCurrency()); //总金额
		$("input[name='agmtTpdtTotalAmt']").val(_totalAmt.toFixed(2));
		var _depositRate = parseFloat($("#depositRate").val()); //保证金计算比率（配置成公共参数）
		$("#depositAmt").val("¥" + (_totalAmt * _depositRate).formatCurrency());
		$("input[name='agmtDeposit']").val((_totalAmt * _depositRate).toFixed(2));
	});
});


function beforeSubmit() {
	var _minNum = parseInt($("#minNum").val()); //起订量
	var _orderTotalNum = 0; //订单量
	   
	var flag = false;
	$(".orderNum").each(function() {
		if($(this).val()) {
			_orderTotalNum = _orderTotalNum + parseInt($(this).val());
		}
		if(! /^((([1-9][0-9]{0,8})[0]{2})||0)$/.test($(this).val())){
    		flag = (flag || true);
    	}
	});
	 if(flag){
	    	alertMsg.info("产品的订货量为整百数");
	    	return false;
	    }
	if(_orderTotalNum < _minNum) {
		alertMsg.error("预约订单总订货量不能低于" + _minNum + "台");
		return false;
	}else if( !/^(([1-9][0-9]{0,7})[0]{3})$/.test(_orderTotalNum)){
		   alertMsg.info("协议总订货量增量为1000");
		   return false;
	}else {
		$("#agmtAddForm").submit();
	}
}

</script>