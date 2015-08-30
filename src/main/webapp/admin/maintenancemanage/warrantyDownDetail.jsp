<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>
<div class="pageContent">
		<div class="pageFormContent" layoutH="68">
<h1 align="center">报修单历史下载明细</h1>

	<div class="panel collapse" defH="">
		<table border="1" class="list" width="100%" height="100%">
			<thead>
				<tr>
					<td width="150" align="center" style="font-weight: bolder;" colspan="1">受理编号</td>
					<td width="120" align="center" style="font-weight: bolder;" colspan="1">产品序列号</td>
					<td width="120" align="center" style="font-weight: bolder;" colspan="1">产品型号</td>
					<td width="120" align="center" style="font-weight: bolder;" colspan="1">报修时间</td>
					<td width="120" align="center" style="font-weight: bolder;" colspan="1">受理时间</td>
					<td width="120" align="center" style="font-weight: bolder;" colspan="1">客户姓名</td>
					<td width="120" align="center" style="font-weight: bolder;" colspan="1">备注</td>
				</tr>
			</thead>
			<c:forEach items="${twaList}" var="node">
			<tbody>
				<tr>
					<td width="150" align="center" colspan="1">${node.iwarranty}</td>
					<td width="120" align="center" colspan="1">${node.seqNo}</td>
					<td width="120" align="center" colspan="1">${node.pdtNo}</td>
					<td width="120" align="center" colspan="1"><fmt:formatDate pattern="yyyy-MM-dd" value="${node.warrantyTime}"/></td>
					<td width="120" align="center" colspan="1"><fmt:formatDate pattern="yyyy-MM-dd" value="${node.acceptTime}"/></td>
					<td width="120" align="center" colspan="1">${node.custName}</td>
					<td width="120" align="center" colspan="1">${node.remark}</td>
				</tr>
			</tbody>
			</c:forEach>
			</table>
			</div>
			<br/>
			<br/>
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