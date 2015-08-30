<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="pageContent">
	<form id="agmtModifyForm" method="post" action="${ctx}/cust/agmt/agmtModify.do" class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="65">
		<p>
			<label>协议起始日期：</label>
			<input type="text" readonly="readonly" value='<fmt:formatDate value="${agmt4Page.tagmt.startTime}" pattern="yyyy-MM-dd"/>' />
		</p>
		<p>
			<label>协议结束日期：</label>
			<input type="text" readonly="readonly" value='<fmt:formatDate value="${agmt4Page.tagmt.endTime}" pattern="yyyy-MM-dd"/>' />
		</p>
		
		<br/><br/><br/><p><label>原协议内容：</label></p>
		<div>
		<table class="list" width="90%">
			<thead>
				<tr>
					<th width="150px">产品名称</th>
					<th width="150px">单价</th>
					<th width="150px">折扣价</th>
					<th width="200px">协议订货量</th>
					<th width="200px">协议剩余量</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${agmt4Page.agmtDetail4PageList}" var="agmtDetail4Page" varStatus="status">
				<tr>
					<td align="center">${agmtDetail4Page.tpdtHis.name}</td>
					<td align="right"><fmt:formatNumber value="${agmtDetail4Page.tpdtHis.price}" pattern="¥#,##0.00#" /></td>
					<td align="right"><fmt:formatNumber value="${agmtDetail4Page.tagmtDetail.rate * agmtDetail4Page.tpdtHis.price}" pattern="¥#,##0.00#"/></td>
					<td align="right">${agmtDetail4Page.tagmtDetail.num}台</td>
					<td align="right">${agmtDetail4Page.tagmtDetail.remainQuota}台</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		</div>
		<p>
			<label>总货款：</label>
			<input type="text" readonly="readonly" value='<fmt:formatNumber value="${agmt4Page.totalAmt}" pattern="¥#,##0.00#" />' />
		</p>
		<p>
			<label>保证金总额：</label>
			<input type="text" readonly="readonly" value='<fmt:formatNumber value="${agmt4Page.tagmt.deposit}" pattern="¥#,##0.00#" />' />
		</p>
		
		
		<br/><br/><br/><p><label>可变更部分：</label></p>
		<div>
		<table class="list" width="90%">
			<thead>
				<tr>
					<th width="150px">产品名称</th>
					<th width="150px">单价</th>
					<th width="150px">折扣价</th>
					<th width="200px">可变更订货量</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${agmt4Page.agmtDetail4PageList}" var="agmtDetail4Page" varStatus="status">
				<tr>
					<td align="center">${agmtDetail4Page.tpdtHis.name}</td>
					<td align="right"><fmt:formatNumber value="${agmtDetail4Page.tpdtHis.price}" pattern="¥#,##0.00#" /></td>
					<td align="right"><fmt:formatNumber value="${agmtDetail4Page.tagmtDetail.rate * agmtDetail4Page.tpdtHis.price}" pattern="¥#,##0.00#"/></td>
					<td align="right"><span class="remainedQuota">${agmtDetail4Page.tagmtDetail.remainQuota}</span>台</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		</div>
		
		<br/><br/><br/><p><label>协议变更：</label></p>
		<div>
		<table class="list" width="90%">
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
					<input type="hidden" id="priceAfterRate${status.index}" name="agmtTpdtList[${status.index}].priceAfterRate" value="${tpdt.priceAfterRate}" />
					<input type="hidden" name="agmtTpdtList[${status.index}].ipdt" value="${tpdt.ipdt}">
					<input type="hidden" name="agmtTpdtList[${status.index}].ipdtHis" value="${tpdt.ipdtHis}">
					<input type="hidden" name="agmtTpdtList[${status.index}].name" value="${tpdt.name}">
				<tr>
					<td id="tpdtName${tpdt.name}" align="center">${tpdt.name}</td>
					<td align="right"><fmt:formatNumber value="${tpdt.price}" pattern="¥#,##0.00#" /></td>
					<td align="right"><fmt:formatNumber value="${tpdt.priceAfterRate}" pattern="¥#,##0.00#"/></td>
					<td align="center">
						<input type="text" id="orderNum${status.index}" class="orderNum digits" name="agmtTpdtList[${status.index}].pdtAgmtNum" style="float: none;" maxlength="6"/>台
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		</div>
		
		<!-- <div class="total">
			<p style="width: 240px;margin-top: 20px;">
				<label style="width: 50px;">总货款：</label>
				<input id="totalAmt" type="text" readonly="readonly" value="¥0.00" style="text-align: right;"/>
			</p>
			<p style="width: 240px;margin-top: 20px;">
				<label style="width: 50px;">保证金：</label>
				<input id="depositAmt" type="text" readonly="readonly" value="¥0.00" style="text-align: right;"/>
			</p>
		</div> -->
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="submit" onclick="return beforeSubmit();">&nbsp;&nbsp;提交变更&nbsp;&nbsp;</button>
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
		
		<!-- 隐藏域 -->
		<input type="hidden" id="depositRate" value="${depositRate}"><!-- 保证金比率 -->
		<input type="hidden" name="iagmt" value="${agmt4Page.tagmt.iagmt}"/>

	</form>
</div>
<script type="text/javascript">

var _canChangeNum = 0;
$(function(){
	
	/**********************可变更量****************************/
	$(".remainedQuota").each(function() {
		if($(this).html()) {
			_canChangeNum = _canChangeNum + parseInt($(this).html());
		} else {
			_canChangeNum = _canChangeNum + 0;
		}
	});
	
	
	/*************************只能输入数字、回退键、删除键*************************/
	$(".orderNum").on("keypress", function(event) {
		var keyCode = event.which;
        if (!(keyCode >= 48 && keyCode <= 57) && (keyCode != 8)) {
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
		
		//$("#totalNum").val(_totalNum);
		
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
		var _depositRate = parseFloat($("#depositRate").val()); //保证金计算比率（配置成公共参数）
		$("#depositAmt").val("¥" + (_totalAmt * _depositRate).formatCurrency());
	});
});


function beforeSubmit() {
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
	/*  if(flag){
	    	alertMsg.info("产品的订货量为整百数");
	    	return false;
	    } */
	 
	// 订货量合计，不能超过可变更订货量
	if(_orderTotalNum != _canChangeNum) {
		alertMsg.warn("订货量合计必须等于可变更订货量【" + _canChangeNum + "】台");
		return false;
	}
	$("#agmtModifyForm").submit();
}

</script>