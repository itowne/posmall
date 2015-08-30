<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>
<style type="text/css">
	.table{
		margin: 20px 5px;
	}
</style>
<div class="pageContent" layoutH="38">
	<div class="pageFormContent">
		<p>
			<label>协议编号：</label>
			<input type="text" value="${tagmt.agmtNo}"  readonly="readonly"/>
		</p>
		<p>
			<label>客户名称：</label>
			<input type="text" value="${tcustReg.name}"  readonly="readonly"/>
		</p>
		<p>
			<label>协议生效开始时间：</label>
			<input type="text" value='<fmt:formatDate value="${tagmt.startTime}" pattern="yyyy-MM-dd" />' readonly="readonly"/>
		</p>
		<p>
			<label>协议生效结束时间：</label>
			<input type="text" value='<fmt:formatDate value="${tagmt.endTime}" pattern="yyyy-MM-dd" />' readonly="readonly"/>
		</p>
			<p>
			<label>保证金：</label>
			<input type="text" value="<fmt:formatNumber value="${tagmt.deposit}" pattern="¥#,##0.00#"/>"  readonly="readonly"/><label style="width:30px"></label>
		</p>
<!-- 		<p> -->
<!-- 			<label>已用保证金：</label> -->
<%-- 			<input type="text" value="<fmt:formatNumber value="${tagmt.usedDeposit}" pattern="¥#,##0.00#"/>"  readonly="readonly"/><label style="width:30px"></label> --%>
<!-- 		</p> -->
<!-- 		<p> -->
<!-- 			<label>剩余保证金：</label> -->
<%-- 			<input type="text" value="<fmt:formatNumber value="${tagmt.remainDeposit}" pattern="¥#,##0.00#"/>"  readonly="readonly"/><label style="width:30px"></label> --%>
<!-- 		</p> -->
		<p>
			<label>协议状态：</label>
			<input type="text" value="<posmall:dict dictType='agmt_status' dictCode='${tagmt.agmtStatus}'></posmall:dict>" readonly="readonly"/>
		</p>
	<div class="table">
		<table class="list" width="100%">
			<thead>
				<tr>
				    <th width="200">产品名称</th>
					<th width="100">单价</th>
					<th width="100">协议数量</th>
					<th width="100">折扣率</td>
					<th width="100">折扣价</td>
					<td></td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${agmtTpdtList}" var="agmtTpdt" varStatus="status">			
				<tr>
					<td>${agmtTpdt[1]}</td>
					<td align="right">${agmtTpdt[2]} 台</td>
					<td>${agmtTpdt[3]}</td>
					<td align="right">${agmtTpdt[4]}</td>
					<td align="right">${agmtTpdt[6]}</td>
					<td></td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<p>
			<label>订货总量：</label>
			<input type="text" name="sumnum" value="${sumnum} 台" readonly="readonly"/>
		</p>
		<p>
			<label>总金额：</label>
			<input type="text" name="modifyAmt" value="<fmt:formatNumber value="${sumamt}" pattern="¥#,##0.00#"/>" readonly="readonly"/>
		</p>
	</div>
   	</div>
	
</div>
<form id="agmtAddForm" method="post" action="${ctx}/admin/agmt/custagmtPayCheck.do" class="pageForm required-validate"
	onsubmit="return validateCallback(this, dialogAjaxDone);">
	<input type="hidden" name="iagmt" value="${tagmt.iagmt}"/>
<div class="formBar">
	<ul>
		<li>
			<div class="button">
				<div class="buttonContent">
					<button type="submit">&nbsp;&nbsp;付&nbsp;款&nbsp;已&nbsp;确&nbsp;认&nbsp;&nbsp;</button>
				</div>
			</div>
		</li>
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