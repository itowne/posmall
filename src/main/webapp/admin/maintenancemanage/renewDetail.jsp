<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>
<div class="pageContent">
	<form id="ordAuditForm" method="post" action=""
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">

		<div class="pageFormContent" layoutH="68">
			 <input type="hidden" name="irenew" value="${trenew.irenew}"/>
	     	<p>
				<label>续保编号：</label>
				<input type="text" value="${trenew.irenew}" readonly="readonly">
			</p>
			<p>
				<label>金额：</label>
				<input type="text" value="<fmt:formatNumber value="${trenew.renewAmt}" pattern="¥#,##0.00#"/>" readonly="readonly">
			</p>
			<p>
				<label>申请期限：</label>
				<c:if test="${trenew.renewLife == 1}"><input type="text" value="一年" readonly="readonly"></c:if>
				<c:if test="${trenew.renewLife == 2}"><input type="text" value="两年" readonly="readonly"></c:if>
				<c:if test="${trenew.renewLife == 3}"><input type="text" value="三年" readonly="readonly"></c:if>
			</p>
			<p>
				<label>申请时间：</label>
				<input type="text" value="<fmt:formatDate value="${trenew.renewTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly">
			</p>
			<p>
				<label>续保状态：</label>
				<input type="text" value="<posmall:dict dictCode="${trenew.renewStatus}" dictType="renew_status" />" readonly="readonly">
			</p>
			<p>
				<label>支付状态：</label>
				<input type="text" value="<posmall:dict dictCode="${trenew.payStatus}" dictType="pay_status" />" readonly="readonly">
			</p>
			<p>
				<label>客户名称：</label>
				<input type="text" value="${trenew.custName}" readonly="readonly">
			</p>
		<div class="panel collapse" defH="">
			<h1 align="center"></h1>
			<div>
			<table class="list" width="100%" height="100%">
				<thead>
					<tr>
						<th width="120">产品序列号</th>
						<th width="80">产品型号</th>
						<th width="120">保修起始时间</th>
						<th width="120">保修结束时间</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${detailList}" var="list">
						<tr>
							<td align="left">&nbsp;${list.seqNo}</td>
							<td align="left">&nbsp;${list.pdtNo}</td>
							<td align="left">&nbsp;<fmt:formatDate pattern="yyyy-MM-dd" value="${list.lifeStartTime}"/></td>
							<td align="left">&nbsp;<fmt:formatDate pattern="yyyy-MM-dd" value="${list.lifeEndTime}"/></td>
							<td></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			</div>
		</div>
	</div>	
		<div class="formBar">
			<ul>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">&nbsp;&nbsp;返&nbsp;&nbsp;&nbsp;&nbsp;回&nbsp;&nbsp;</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
 	</form>
</div>	
