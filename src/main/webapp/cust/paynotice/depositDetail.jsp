<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="pageContent">
	<div class="pageFormContent" layoutH="65">
		<p>
			<label>协议起始时间：</label>
			<input type="text" readonly="readonly" value='<fmt:formatDate value="${agmt4Page.tagmt.startTime}" pattern="yyyy-MM-dd" />'>
		</p>
		<p style="margin-bottom: 20px;">
			<label>协议终止时间：</label>
			<input type="text" readonly="readonly" value='<fmt:formatDate value="${agmt4Page.tagmt.endTime}" pattern="yyyy-MM-dd" />'>
		</p>
		<div class="table">
			<table class="list" width="100%">
				<thead>
					<tr>
						<th width="200">产品名称</th>
						<th width="100">单价</th>
						<th width="100">折扣率</td>
						<th width="100">折扣价</td>
						<th width="100">订货量</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${agmt4Page.agmtDetail4PageList}" var="agmtDetail4Page" varStatus="status">
						<tr>
							<td align="center">&nbsp;${agmtDetail4Page.tpdtHis.name}</td>
							<td align="right"><fmt:formatNumber value="${agmtDetail4Page.tpdtHis.price}" pattern="¥#,##0.00#"/>&nbsp;</td>
							<td align="right">${agmtDetail4Page.tagmtDetail.rate}&nbsp;</td>
							<td align="right"><fmt:formatNumber value="${agmtDetail4Page.tagmtDetail.rate * agmtDetail4Page.tpdtHis.price}" pattern="¥#,##0.00#"/>&nbsp;</td>
							<td align="right"><span class="num">${agmtDetail4Page.tagmtDetail.num}</span>&nbsp;台</td>
							<td></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<p style="margin-top: 15px">
				<label>总量：</label>
				<input id="totalNum" readonly="readonly" style="text-align: right;"/>台
			</p>
			<p style="margin-top: 15px">
				<label>协议保证金：</label>
				<input value='<fmt:formatNumber value="${agmt4Page.tagmt.deposit}" pattern="¥#,##0.00#"/>' readonly="readonly" style="text-align: right;color: red;"/>
			</p>
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
</div>
<script type="text/javascript">
<!--
	//计算总量
	var _totalNum = 0;
	$(".num").each(function() {
		_totalNum = _totalNum + parseInt($(this).html());
	});
	$("#totalNum").val(_totalNum);
//-->
</script>