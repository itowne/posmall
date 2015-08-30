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
		<p style="margin-bottom: 10px;">
			<label>协议终止时间：</label>
			<input type="text" readonly="readonly" value='<fmt:formatDate value="${agmt4Page.tagmt.endTime}" pattern="yyyy-MM-dd" />'>
		</p>
		
		<p style="color: red;"><label>原协议明细：</label></p>
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
					<c:forEach items="${agmt4Page.agmtDetailHis4PageList}" var="agmtDetailHis4Page" varStatus="status">
						<tr>
							<td align="center">&nbsp;${agmtDetailHis4Page.tpdtHis.name}</td>
							<td align="right"><fmt:formatNumber value="${agmtDetailHis4Page.tpdtHis.price}" pattern="¥#,##0.00#"/>&nbsp;</td>
							<td align="right">${agmtDetailHis4Page.tagmtDetailHis.rate}&nbsp;</td>
							<td align="right"><fmt:formatNumber value="${agmtDetailHis4Page.tagmtDetailHis.rate * agmtDetailHis4Page.tpdtHis.price}" pattern="¥#,##0.00#"/>&nbsp;</td>
							<td align="right"><span class="num">${agmtDetailHis4Page.tagmtDetailHis.num}</span>&nbsp;台</td>
							<td></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		
		
		<p style="float: none;color: red;padding-top: 25px;"><label>变更后协议明细：</label></p>
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
		</div>
		
		<p style="margin-top: 30px">
			<label>原协议保证金：</label>
			<input value='<fmt:formatNumber value="${agmt4Page.tagmt.deposit + agmt4Page.tagmt.redundantDeposit}" pattern="¥#,##0.00#"/>' readonly="readonly" />
		</p>
		<p style="margin-top: 30px">
			<label>协议变更后保证金：</label>
			<input value='<fmt:formatNumber value="${agmt4Page.tagmt.deposit}" pattern="¥#,##0.00#"/>' readonly="readonly" />
		</p>
		<p>
			<label>应补交保证金：</label>
			<input value='<fmt:formatNumber value="${-agmt4Page.tagmt.redundantDeposit}" pattern="¥#,##0.00#"/>' readonly="readonly" style="color: red;" />
		</p>
		
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