<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/posmall-tags" prefix="posmall"%>
<div class="pageContent">
	<form id="toAddStep5Form" method="post" action="${ctx}/cust/logistics/toAddStep5More.do"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<input type="hidden" name="ilogisticsOrd" value="${ilogisticsOrd}">
		<div class="pageContent" selector="h1" layoutH="42">
			<div class="panel collapse" defH="">
				<h1 align="left">订单详情</h1>
				<div>
					<table border="1" class="list" width="100%" >
						<thead>
							<tr>
								<th width="130">订单编号</th>
								<th width="130">下单时间</th>
								<th width="130">订单金额</th>
								<th>预计发货日期</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>${p.tord.ordNo}</td>
								<td>
									<fmt:formatDate value="${p.tord.genTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
								<td><fmt:formatNumber value="${p.tord.amt}" pattern="¥#,##0.00#"/></td>
								<td>
									<input type="text" id="specifyDelivery" name="specifyDelivery" class="date required" readonly="readonly"
									minDate="${specifyDelivery}" value="${specifyDelivery}">
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="panel collapse" defH="">
				<h1 align="left">发货地址详情</h1>
				<div>
					<table border="1" class="list" width="100%" >
						<thead>
							<tr>
								<th width="150">产品型号</th>
								<th width="90">发货数量</th>
								<th width="290">发货地址</th>
								<th width="90">联系人</th>
								<th width="90">联系电话</th>
								<th>付费物流</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${p.addrs}" var="addr" >
								<tr>
									<td align="left">&nbsp;${addr.pdtName}</td>
									<td align="right">${addr.tlogisticsOrdAddr.num}台&nbsp;</td>
									<td align="left">&nbsp;${addr.tlogisticsOrdAddr.consigneeAddr}</td>
									<td align="left">&nbsp;${addr.tlogisticsOrdAddr.consigneeName}</td>
									<td align="left">&nbsp;${addr.tlogisticsOrdAddr.consigneeMobile}</td>
									<td align="left">&nbsp;${addr.tlogisticsOrdAddr.feeFlag}</td>
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
							<button type="button" class="button" onclick="return beforeSubmit();">&nbsp;&nbsp;&nbsp;确&nbsp;&nbsp;定&nbsp;&nbsp;&nbsp;</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">&nbsp;&nbsp;&nbsp;关&nbsp;&nbsp;闭&nbsp;&nbsp;&nbsp;</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>
<script type="text/javascript">
	function beforeSubmit() {
		var _specifyDelivery = $("#specifyDelivery").val();
		if(_specifyDelivery == null || _specifyDelivery.trim() == "") {
			alertMsg.error("请预计发货日期！");
			return false;
		}
		$("#toAddStep5Form").attr("action","${ctx}/cust/logistics/toAddStep5More.do");
		$("#toAddStep5Form").attr("onsubmit","return validateCallback(this, dialogAjaxDone);");
		$("#toAddStep5Form").submit();
	}
</script>