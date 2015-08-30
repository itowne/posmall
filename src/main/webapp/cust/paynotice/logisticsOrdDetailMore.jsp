<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/posmall-tags" prefix="posmall"%>
<style>
._logistics_fieldset {
	border: 1px solid #b8d0d6;
	margin-bottom: 20px;
	padding: 8px 0 10px 10px;
}

._logistics_fieldset legend {
	margin-left: 15px;
	font-size: 15px;
	border: 1px solid #b8d0d6;
	border-radius: 5px 5px 0px 0px;
	padding: 5px 5px;
}
</style>
<script type="text/javascript">
	$(function() {
		var _allFee = 0.00;
		$(".fee").each(function(){
			var _id = $(this).attr("id");
			var _idx = _id.substr(3); //fee是3位
			var _num = $("#num" + _idx).html();
			var _price = $("#price" + _idx).html();
			var _numInt = parseInt(_num);
			var _priceFloat = parseFloat(_price);
			$(this).html("¥" + (_numInt * _priceFloat).formatCurrency());
			_allFee = _allFee + (_numInt * _priceFloat);
		});
		
		var _allNum = 0;
		$(".num").each(function() {
			if($(this).html()) {
				_allNum = _allNum + parseInt($(this).html());
			}else {
				_allNum = _allNum + 0;
			}
		});
		$("#allNum").html(_allNum);
		
		$("#allFee").html("¥" + (_allFee).formatCurrency());
	});
</script>
<div class="pageContent" selector="h1" layoutH="42">
	<input type="hidden" name="ilogisticsOrd" value="${ilogisticsOrd}">
	<div class="pageFormContent">
		<fieldset class="_logistics_fieldset">
			<legend>订单详情</legend>
			<table border="1" class="list" width="100%">
				<thead>
					<tr>
						<th width="130">订单编号</th>
						<th width="130">下单时间</th>
						<th width="130">订单金额</th>
						<th width="130">预计发货日期</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>${p.tord.ordNo}</td>
						<td align="center"><fmt:formatDate value="${p.tord.genTime}"
								pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td align="right"><fmt:formatNumber value="${p.tord.amt}"
								pattern="¥#,##0.00#" /></td>
						<td align="center"><fmt:formatDate
								value="${tlogisticsOrd.specifyDelivery}" pattern="yyyy-MM-dd" />
						</td>
						<td></td>
					</tr>
				</tbody>
			</table>
		</fieldset>
		<fieldset class="_logistics_fieldset">
			<legend>发货地址详情</legend>
			<table border="1" class="list" width="100%">
				<thead>
					<tr>
						<th width="120">产品名称</th>
						<th width="80">发货数量</th>
						<th width="380">收货地址</th>
						<th width="120">是否选用付费物流</th>
<!-- 						<th width="80">物流公司</th> -->
						<th width="100">运费单价(元/台)</th>
						<th width="80">运费</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${p.addrs}" var="addr" varStatus="st">
						<tr>
							<td align="left">&nbsp;${addr.pdtName}</td>
							<td align="right"><span id="num${st.index}" class="num">${addr.tlogisticsOrdAddr.num}</span>台&nbsp;</td>
							<td align="left">&nbsp;${addr.tlogisticsOrdAddr.consigneeAddr}</td>
							<td align="center">&nbsp;${addr.tlogisticsOrdAddr.feeFlag}</td>
<%-- 							<td align="center">${addr.tlogisticsOrdAddr.logisticsComName}</td> --%>
							<td align="right"><span id="price${st.index}">${addr.tlogisticsOrdAddr.price}</span></td>
							<td align="right"><span id="fee${st.index}" class="fee"></span></td>
						</tr>
					</c:forEach>
					<tr>
						<td align="center">合计</td>
						<td align="right"><span id="allNum"></span>台</td>
						<td></td>
						<td></td>
						<td></td>
						<td align="right"><span id="allFee"></span></td>
					</tr>
				</tbody>
			</table>
		</fieldset>
	</div>
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
