<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>
<div class="pageContent" selector="h1" layoutH="42">
	<div class="pageFormContent">
		<p>
			<label>订单编号：</label>
			<input type="text" value="${tord.ordNo}" readonly="readonly">
		</p>
		<p>
			<label>订单状态：</label>
			<input type="text" value="<posmall:dict dictCode="${tord.ordStatus}" dictType="ord_status" />" readonly="readonly">
		</p>
		<p>
			<label>支付状态：</label>
			<input type="text" value="<posmall:dict dictCode="${tord.payStatus}" dictType="pay_status" />" readonly="readonly">
		</p>
		<p>
			<label>下单时间：</label>
			<input type="text" value="<fmt:formatDate value="${tord.genTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly">
		</p>
		<p>
			<label>订单货款：</label>
			<input type="text" value="<fmt:formatNumber value="${tord.amt}" pattern="¥#,##0.00#"/>" readonly="readonly" style="color: red;">
		</p>
	</div>
	<c:forEach items="${tordDetailList}" var="detail">
		<div class="panel collapse" defH="">
			<h1 align="center">产品名称：${detail.pdtName}&nbsp;&nbsp;&nbsp;&nbsp;
						        产品单价：<fmt:formatNumber value="${detail.price * detail.rate}" pattern="¥#,##0.00#" /></h1>
			<div>
			<table class="list" width="100%" height="100%">
				<thead>
					<tr>
						<th width="120">预约订单日期</th>
						<th width="120">点单台数</th>
						<th width="120">金&nbsp;&nbsp;&nbsp;额</th>
						<!-- <th width="200">产品号段</th> -->
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${detail.tordDetailPdtList}" var="tordDetailPdt">
						<tr>
							<td align="center">
								<c:choose>
								<c:when test='${tordDetailPdt.ordDetailPdtType=="STOCK"}'>${tordDetailPdt.year}-${tordDetailPdt.month}-${tordDetailPdt.day}库存</c:when>
								<c:otherwise>${tordDetailPdt.year}-${tordDetailPdt.month}-${tordDetailPdt.day}</c:otherwise>
								</c:choose>
							</td>
							<td align="right">${tordDetailPdt.num}台</td>
							<td align="right"><fmt:formatNumber value="${detail.price * detail.rate * tordDetailPdt.num}" pattern="¥#,##0.00#" /></td>
							<%-- <td align="center">${tordDetailPdt.startSn}&nbsp;-&nbsp;${tordDetailPdt.endSn}</td> --%>
							<td></td>
						</tr>
					</c:forEach>
					<tr style="color: red;">
				       <td align="center">合计：</td>
						<td align="right">${detail.num}台</td>
				      	<td align="right"><fmt:formatNumber value="${detail.amt}" pattern="¥#,##0.00#" /></td> 
				        <%-- <td align="center">${detail.startSn}&nbsp;-&nbsp;${detail.endSn}</td> --%>
						<td></td>
				    </tr>
				</tbody>
			</table>
			</div>
		</div>
	</c:forEach>
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
