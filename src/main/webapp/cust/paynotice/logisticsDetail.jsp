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
._logistics_fieldset legend{
	margin-left: 15px;
	font-size: 15px;
	border: 1px solid #b8d0d6;
	border-radius: 5px 5px 0px 0px;
	padding: 5px 5px;
}
</style>
<div class="pageContent" selector="h1" layoutH="42">
	<input type="hidden" name="tlogisticsOrd.ilogisticsOrd"
		value="${logisticsOrd4Page.tlogisticsOrd.ilogisticsOrd}">

	<div class="pageFormContent">
		<fieldset class="_logistics_fieldset">
		    <legend>发货信息</legend>
		    <table border="1" class="list" width="100%" height="100%">
				<thead>
					<tr>
						<th width="150">产品名称</th>
						<th>发货量</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${logisticsOrd4Page.logisticsOrdAddr4PageList}"
						var="logisticsOrdAddr4Page"
						varStatus="logisticsOrdAddr4PageStatus">
						<tr>
							<td align="left">&nbsp;${logisticsOrdAddr4Page.pdtName}</td>
							<td align="center"><span class="eachNum">${logisticsOrdAddr4Page.tlogisticsOrdAddr.num}</span>&nbsp;台</td>
						</tr>
					</c:forEach>
					<tr>
						<td align="center">合&nbsp;&nbsp;&nbsp;计</td>
						<td align="center"><span class="allNum"></span>台</td>
					</tr>
				</tbody>
			</table>
		</fieldset>
		<fieldset class="_logistics_fieldset">
		    <legend>物流信息</legend>
		    <table border="1" class="list" width="100%" height="100%">
				<tr>
					<td width="10%" align="left" style="font-weight: bolder;">物流公司：</td>
					<td width="20%" align="left">${logisticsOrd4Page.logisticsOrdAddr4PageList[0].tlogisticsOrdAddr.logisticsComName}</td>
					<td width="10%" align="left" style="font-weight: bolder;">单价：</td>
					<td width="20%" align="left">${logisticsOrd4Page.logisticsOrdAddr4PageList[0].tlogisticsOrdAddr.price}(元/台)</td>
					<td width="10%" align="left" style="font-weight: bolder;">运费：</td>
					<td width="20%" align="left"><fmt:formatNumber value="${tlogisticsOrd.logisticsFreight}" pattern="¥#,##0.00#"/>&nbsp;</td>
				</tr>
			</table>
		</fieldset>
		<fieldset class="_logistics_fieldset">
		    <legend>收货地址</legend>
		    ${logisticsOrd4Page.logisticsOrdAddr4PageList[0].tlogisticsOrdAddr.consigneeAddr}
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
<script type="text/javascript">
	$(function() {
		var _allNum = 0;
		$(".eachNum").each(function() {
			var _num = 0;
			if($(this).html()) {
				_num = parseInt($(this).html());
			}
			_allNum = _allNum + _num;
		});
		$(".allNum").html(_allNum);
	});
</script>