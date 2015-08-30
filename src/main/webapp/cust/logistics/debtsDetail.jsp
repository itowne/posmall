<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/posmall-tags" prefix="posmall"%>
<script type="text/javascript">

	var _list = "${tLogisticsOrdList}";
	if (_list == null || _list == "[]" || _list == "") {
		alertMsg.correct("当前没有物流欠费");
		$.pdialog.closeCurrent();
	}
	
	$(function() {
		/**********************************/
		var _allnum = 0;
		$(".num").each(function() {
			if($(this).val()) {
				_allnum = _allnum + parseInt($(this).val());
			}else {
				_allnum = _allnum + 0;
			}
		});
		$(".allNum").html(_allnum);
		
		/**********************************/
		var _allfreight = 0.00;
		$(".freight").each(function() {
			if($(this).val()) {
				_allfreight = _allfreight + parseFloat($(this).val());
			}else {
				_allfreight = _allfreight + 0;
			}
		});
		$(".allFreight").html("¥" + _allfreight.formatCurrency());
		
		/**********************************/
		var _alldebts = 0.00;
		$(".debts").each(function() {
			if($(this).val()) {
				_alldebts = _alldebts + parseFloat($(this).val());
			}else {
				_alldebts = _alldebts + 0;
			}
		});
		$(".allDebts").html("¥" + _alldebts.formatCurrency());
	});
</script>
<div class="pageContent" selector="h1" layoutH="42">
	<div class="pageFormContent">
		<table class="list" width="100%" layoutH="138">
			<thead>
				<tr>
					<th width="100">物流单编号</th>
					<th width="100">订单编号</th>
					<th width="80">物流单状态</th>
					<th width="80">支付状态</th>
<!-- 					<th width="80">是否多地址</th> -->
					<th width="100">数量</th>
					<th width="100">物流费用</th>
					<th width="100">欠款</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${tLogisticsOrdList}" var="list">
					<tr>
						<td align="left">&nbsp;${list.innerOrdno}</td>
						<td align="left">&nbsp;${list.tord.ordNo}</td>
						<td align="center">&nbsp;<posmall:dict dictCode="${list.logisticsOrdStatus}" dictType="logistics_ord_status" /></td>
						<td align="center">&nbsp;<posmall:dict dictCode="${list.payStatus}" dictType="pay_status" /></td>
<!-- 						<td align="center"> -->
<%-- 							<c:choose> --%>
<%-- 								<c:when test='${list.ifile == "-1"}'>否</c:when> --%>
<%-- 								<c:otherwise>是</c:otherwise> --%>
<%-- 							</c:choose> --%>
<!-- 						</td> -->
						<td align="right">${list.num}&nbsp;台</td>
						<td align="right"><fmt:formatNumber value="${list.logisticsFreight}" pattern="¥#,##0.00#" />&nbsp;</td>
						<td align="right">
							<fmt:formatNumber value="${list.debts}" pattern="¥#,##0.00#" />&nbsp;
							<input type="hidden" class="num" value="${list.num}" />
							<input type="hidden" class="freight" value="${list.logisticsFreight}" />
							<input type="hidden" class="debts" value="${list.debts}" />
						</td>
					</tr>
				</c:forEach>
				<tr style="background-color: #33CCFF;">
					<td align="center">合计</td>
					<td></td>
					<td></td>
					<td></td>
					<td align="right"><span class="allNum"></span>&nbsp;台</td>
					<td align="right"><span class="allFreight"></span>&nbsp;</td>
					<td align="right"><span class="allDebts"></span>&nbsp;</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
<div class="formBar">
	<ul>
		<li>
			<div class="button">
				<div class="buttonContent">
					<button type="button" class="close">&nbsp;&nbsp;&nbsp;关&nbsp;&nbsp;闭&nbsp;&nbsp;&nbsp;</button>
				</div>
			</div>
		</li>
	</ul>
</div>