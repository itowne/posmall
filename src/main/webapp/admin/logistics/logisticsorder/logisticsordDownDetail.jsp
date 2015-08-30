<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>
<div class="pageContent">
		<div class="pageFormContent" layoutH="68">
<h1 align="center">物流单历史下载明细</h1>

	<c:forEach items="${ld4pList}" var="ld4pList">
	<div class="panel collapse" defH="">
		<table border="1" class="list" width="100%" height="100%">
			<thead>
				<tr>
					<td width="150" align="center" style="font-weight: bolder;">物流内部单号</td>
					<td width="120" align="center" style="font-weight: bolder;">客户名称</td>
					<td width="50" align="center" style="font-weight: bolder;">数量</td>
					<td width="150" align="center" style="font-weight: bolder;">预计发货日期</td>
					<td width="150" align="center" style="font-weight: bolder;">预计到达日期</td>
					<td width="50" align="center" style="font-weight: bolder;">物流费用</td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td width="150" align="center">${ld4pList.tlogisticsOrd.innerOrdno}</td>
					<td width="120" align="center"><posmall:dict dictCode="${ld4pList.tlogisticsOrd.icust}" dictType="custreg_name" /></td>
					<td width="150" align="center">${ld4pList.tlogisticsOrd.num}</td>
					<td width="120" align="center"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${ld4pList.tlogisticsOrd.specifyDelivery}"/></td>
					<td width="120" align="center"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${ld4pList.tlogisticsOrd.expectedArrival}"/></td>
					<td width="120" align="center">${ld4pList.tlogisticsOrd.logisticsFreight}</td>
				</tr>
			</tbody>
			<tbody>
				<tr>
					<td width="150" align="left" colspan="6"><h1>产品明细 :</h1></td>
				</tr>
			</tbody>
			<thead>
				<tr>
					<td width="150" align="center" style="font-weight: bolder;" colspan="1">产品名称</td>
					<td width="120" align="center" style="font-weight: bolder;" colspan="1">产品数量</td>
					<td width="120" align="center" style="font-weight: bolder;" colspan="4">产品地址</td>
				</tr>
			</thead>
			<c:forEach items="${ld4pList.tlogisticsOrdAddrList}" var="node">
			<tbody>
				<tr>
					<td width="150" align="center" colspan="1">${node.pdtName}</td>
					<td width="120" align="center" colspan="1">${node.num}</td>
					<td width="120" align="center" colspan="4">${node.consigneeAddr}</td>
				</tr>
			</tbody>
			</c:forEach>
			</table>
			</div>
			<br/>
			<br/>
			</c:forEach>
</div>
<div class="formBar">
	<ul>
		<li>
			<div class="button">
				<div class="buttonContent">
					<button type="button" class="close">关闭</button>
				</div>
			</div>
		</li>
	</ul>
</div>
</div>