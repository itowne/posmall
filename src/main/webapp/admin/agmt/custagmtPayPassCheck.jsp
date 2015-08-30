<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/posmall-tags" prefix="posmall"%>
<style type="text/css">
.table {
	margin-top: 20px 5px;
}
</style>
<script type="text/javascript">
	$(function() {
		//异常、错误信息提示 
		var _errMsg = "${errMsg}";
		if (_errMsg != null && _errMsg.trim() != '') {
			alertMsg.warn(_errMsg);
			$.pdialog.closeCurrent();
		}
	});
</script>
<div class="pageContent" layoutH="38">
	<div class="pageFormContent">
		<p>
			<label>协议编号：</label> <input type="text" value="${tagmt.agmtNo}"
				readonly="readonly" />
		</p>
		<p>
			<label>客户名称：</label> <input type="text" value="${tcustReg.name}"
				readonly="readonly" />
		</p>
		<p>
			<label>协议生效开始时间：</label> <input type="text"
				value='<fmt:formatDate value="${tagmt.startTime}" pattern="yyyy-MM-dd" />'
				readonly="readonly" />
		</p>
		<p>
			<label>协议生效结束时间：</label> <input type="text"
				value='<fmt:formatDate value="${tagmt.endTime}" pattern="yyyy-MM-dd" />'
				readonly="readonly" />
		</p>
		<p>
			<label>保证金：</label> <input type="text"
				value="<fmt:formatNumber value="${tagmt.deposit}" pattern="¥#,##0.00#"/>"
				readonly="readonly" /><label style="width: 30px"></label>
		</p>
		<p>
			<label>协议状态：</label> <input type="text"
				value="<posmall:dict dictType='agmt_status' dictCode='${tagmt.agmtStatus}' />"
				readonly="readonly" />
		</p>
		<c:if test="${tagmt.redundantDeposit > 0}">
			<p style="margin-bottom: 20px;">
				<label>多余保证金：</label>
				<input value='<fmt:formatNumber value="${tagmt.redundantDeposit}" pattern="#,##0.00#"/>' readonly="readonly" style="text-align: right;"/>
			</p>
		</c:if>
		<c:if test="${tagmt.redundantDeposit < 0}">
			<p style="margin-bottom: 20px;">
				<label>保证金补交：</label>
				<input value='<fmt:formatNumber value="${-tagmt.redundantDeposit}" pattern="#,##0.00#"/>' readonly="readonly" style="text-align: right;"/>
			</p>
		</c:if>
		<p />

		<c:if test='${agmtDetailHisList != null}'>
			<p style="float: none;">
				<label style="color: red;">原协议明细：</label>
			</p>
			<div class="table">
				<table class="list" width="100%">
					<thead>
						<tr>
							<th width="200">产品名称</th>
							<th width="100">单价</th>
							<th width="100">协议数量</th>
							<th width="100">折扣率</th>
							<th width="100">折扣价</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${agmtDetailHisList}" var="agmtTpdt"
							varStatus="status">
							<tr>
								<td>${agmtTpdt[1]}</td>
								<td align="right">${agmtTpdt[2]}元</td>
								<td align="right">${agmtTpdt[3]}台</td>
								<td align="right">${agmtTpdt[4]}</td>
								<td align="right">${agmtTpdt[6]}元</td>
								<td></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:if>

		<br />
		<br />
		<c:if test="${tagmt.agmtStatus == 'SUBMIT_CHANGE'}">
			<p>
				<label style="color: red;">变更后协议明细：</label>
			</p>
		</c:if>
		<div class="table">
			<table class="list" width="100%">
				<thead>
					<tr>
						<th width="200">产品名称</th>
						<th width="100">单价</th>
						<th width="100">协议数量</th>
						<th width="100">折扣率</th>
						<th width="100">折扣价</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${agmtTpdtList}" var="agmtTpdt"
						varStatus="status">
						<tr>
							<td>${agmtTpdt[1]}</td>
							<td align="right">${agmtTpdt[2]}元</td>
							<td align="right">${agmtTpdt[3]}台</td>
							<td align="right">${agmtTpdt[4]}</td>
							<td align="right">${agmtTpdt[6]}元</td>
							<td></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<p>
				<label>订货总量：</label> <input type="text" name="sumnum"
					value="${sumnum} 台" readonly="readonly" />
			</p>
			<p>
				<label>总金额：</label> <input type="text" name="modifyAmt"
					value="<fmt:formatNumber value="${sumamt}" pattern="¥#,##0.00#"/>"
					readonly="readonly" />
			</p>
		</div>
	</div>
</div>
<c:choose>
<c:when test='${tagmt.agmtStatus == "SUBMIT_CHANGE"}'>
<!-- 协议变更审核 -->
<form id="agmtAddForm" method="post"
	action="${ctx}/admin/agmt/agmtChangeCheck.do"
	class="pageForm required-validate"
	onsubmit="return validateCallback(this, dialogAjaxDone);">
</c:when>
<c:otherwise>
<!-- 协议正常审核 -->
<form id="agmtAddForm" method="post"
	action="${ctx}/admin/agmt/custagmtPayPassCheck.do"
	class="pageForm required-validate"
	onsubmit="return validateCallback(this, dialogAjaxDone);">
</c:otherwise>
</c:choose>
	<input type="hidden" name="iagmt" value="${tagmt.iagmt}" />
	<div class="formBar">
		<ul>
			<li>
				<div class="button">
					<div class="buttonContent">
						<button type="submit">&nbsp;&nbsp;审核通过&nbsp;&nbsp;</button>
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