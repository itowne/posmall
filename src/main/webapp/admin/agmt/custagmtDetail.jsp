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
			<input type="text" value='<fmt:formatDate value="${tagmt.startTime}" pattern="yyyy-MM-dd" />'  readonly="readonly"/>
		</p>
		<p>
			<label>协议生效结束时间：</label>
			<input type="text" value='<fmt:formatDate value="${tagmt.endTime}" pattern="yyyy-MM-dd" />' readonly="readonly"/>
		</p>
		<p>
			<label>保证金：</label>
			<input type="text" value="<fmt:formatNumber value="${tagmt.deposit}" pattern="¥#,##0.00#"/>"  readonly="readonly"/><label style="width:30px"></label>
		</p>
		<c:if test='${tagmt.agmtStatus=="CONFIRM"}'>
			<p>
				<label>已用保证金：</label>
				<input type="text" value="<fmt:formatNumber value="${tagmt.usedDeposit}" pattern="¥#,##0.00#"/>"  readonly="readonly"/><label style="width:30px"></label>
			</p>
			<p>
				<label>剩余保证金：</label>
				<input type="text" value="<fmt:formatNumber value="${tagmt.remainDeposit}" pattern="¥#,##0.00#"/>"  readonly="readonly"/><label style="width:30px"></label>
			</p>
		</c:if>
		<p>
			<label>协议状态：</label>
			<input type="text" value="<posmall:dict dictType='agmt_status' dictCode='${tagmt.agmtStatus}'></posmall:dict>" readonly="readonly"/>
		</p>
		<c:if test="${logisticsFlag==true}">
		    <label>物流欠费：</label>
		    <label> <a style="color: blue;text-decoration:underline; line-height: 21px" target="dialog" rel="1233" height="500" width="780" mask="true" title="物流费用明细" href="${ctx}/admin/agmt/logisticsList.do?iagmt=${tagmt.iagmt}"><fmt:formatNumber value="${logisticsPay}" pattern="¥#,##0.00#"/></a></label>
		</c:if>
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
	<p/>
	<div class="table">
		<table class="list" width="100%">
			<thead>
				<tr>
					<th width="150">产品名称</th>
					<th width="80">单价</th>
					<th width="80">折扣率</th>
					<th width="80">折扣价</th>
					<th width="80">协议数量</th>
					<th width="80">订货量</th>
					<th width="80">剩余订货量</th>
					<th width="80">出货量</th>
					<th width="80">剩余出货量</th>
<!-- 					<th></th> -->
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${agmtTpdtList}" var="agmtTpdt" varStatus="status">			
				<tr>
					<td>&nbsp;${agmtTpdt[1]}</td>
					<td align="right">${agmtTpdt[2]}&nbsp;</td>
					<td align="right">${agmtTpdt[4]}&nbsp;</td>
					<td align="right">${agmtTpdt[6]}&nbsp;</td>
					<td align="right">${agmtTpdt[3]} 台&nbsp;</td>
					<td align="right">${agmtTpdt[7]} 台&nbsp;</td>
					<td align="right">${agmtTpdt[8]} 台&nbsp;</td>
					<td align="right">${agmtTpdt[9]} 台&nbsp;</td>
					<td align="right">${agmtTpdt[10]} 台&nbsp;</td>
<!-- 					<td></td> -->
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<p/><p/>
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