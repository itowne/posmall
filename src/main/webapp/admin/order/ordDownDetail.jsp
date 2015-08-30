<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>
<div class="pageContent">
		<div class="pageFormContent" layoutH="68">
<h1 align="center">订单历史下载明细</h1>
<c:forEach items="${od4p}" var="od4p">
<div class="panel collapse" defH="">
		<table border="1" class="list" width="100%" height="100%">
			<thead>
				<tr>
					<td width="150" align="center" style="font-weight: bolder;">订单编号</td>
					<td width="120" align="center" style="font-weight: bolder;">客户名称</td>
					<td width="50" align="center" style="font-weight: bolder;">金额</td>
					<td width="150" align="center" style="font-weight: bolder;">登记时间</td>
					<td width="50" align="center" style="font-weight: bolder;">订单状态</td>
					<td width="50" align="center" style="font-weight: bolder;">支付状态</td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td width="150" align="center">${od4p.tord.ordNo}</td>
					<td width="120" align="center">${od4p.tord.icust}</td>
					<td width="150" align="center">${od4p.tord.amt}</td>
					<td width="120" align="center"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${od4p.tord.genTime}"/></td>
					<td width="120" align="center">
					<posmall:dict dictCode="${od4p.tord.ordStatus}" dictType="ord_status"/>
					</td>
					<td width="120" align="center">
					<posmall:dict dictCode="${od4p.tord.payStatus}" dictType="pay_status"/>
					</td>
				</tr>
			</tbody>
			<tbody>
				<tr>
					<td width="150" align="left" colspan="7"><h1>订单明细 :</h1></td>
				</tr>
			</tbody>
			<thead>
				<tr>
					<td width="50" align="center" style="font-weight: bolder;" colspan="2">订单编号</td>
					<td width="50" align="center" style="font-weight: bolder;" colspan="2">产品名称</td>
					<td width="100" align="center" style="font-weight: bolder;" colspan="1">数量</td>
					<td width="150" align="center" style="font-weight: bolder;" colspan="2">需求时间</td>
				</tr>
			</thead>
			<c:forEach items="${od4p.detailPdtList}" var="node">
			<tbody>
				<tr>
					<td width="100" align="center" colspan="2">${node.iord}</td>
					<td width="100" align="center" colspan="2">${node.pdtName}</td>
					<td width="100" align="center" colspan="1">${node.num}</td>
					<td width="100" align="center" colspan="2">${node.year}-${node.month}-${node.day}</td>
				</tr>
			</tbody>
			</c:forEach>
			</table>
</div>
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