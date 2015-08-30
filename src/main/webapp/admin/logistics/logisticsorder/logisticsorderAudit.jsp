<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/posmall-tags" prefix="posmall"%>
<form id="logisticsOrderAuditForm" method="post" action="${ctx}/admin/logistics/logisticsorder/logisticsorderAudit.do"
  class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
  <div class="pageContent" layoutH="48">
	<input type="hidden" name="ilogisticsOrd"
		value="${logisticsOrd4Page.tlogisticsOrd.ilogisticsOrd}">

	<div class="pageFormContent">
		<div class="panel collapse" defH="">
			<h1 align="left">发货信息</h1>
			<div>
				<table border="1" class="list" width="100%" height="100%">
					<thead>
						<tr>
							<td width="150" align="center" style="font-weight: bolder;">产品名称</td>
							<td width="150" align="center" style="font-weight: bolder;">发货量</td>
							<td></td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${logisticsOrd4Page.logisticsOrdAddr4PageList}"
							var="logisticsOrdAddr4Page"
							varStatus="logisticsOrdAddr4PageStatus">
							<tr>
								<td align="left">&nbsp;${logisticsOrdAddr4Page.pdtName}</td>
								<td align="center"><span class="eachNum">${logisticsOrdAddr4Page.tlogisticsOrdAddr.num}</span>&nbsp;台</td>
								<td></td>
							</tr>
						</c:forEach>
						<tr>
							<td align="center">合&nbsp;&nbsp;&nbsp;计</td>
							<td align="center"><span class="allNum"></span>台</td>
							<td></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div class="panel collapse" defH="">
			<h1 align="left">物流信息</h1>
			<div>
				<table border="1" class="list" width="100%" height="100%">
				<tr>
					<td width="15%" align="left" style="font-weight: bolder;">物流公司：</td>
					<td width="35%" align="left">${logisticsOrd4Page.logisticsOrdAddr4PageList[0].tlogisticsOrdAddr.logisticsComName}</td>
					<td width="15%" align="left" style="font-weight: bolder;">运费单价：</td>
					<td width="35%" align="left"><input type="text" size="10" name="price" value="${logisticsOrd4Page.logisticsOrdAddr4PageList[0].tlogisticsOrdAddr.price}">(元/台)</td>
				</tr>
			   </table>
			</div>
		</div>
		<div class="panel collapse" defH="">
			<h1 align="left">收货地址</h1>
			<div>
				${logisticsOrd4Page.logisticsOrdAddr4PageList[0].tlogisticsOrdAddr.consigneeAddr}
			</div>
		</div>
	</div>
</div>
<div class="formBar">
	<ul>
		<li>
			<div class="button">
				<div class="buttonContent">
					<button type="submit">&nbsp;&nbsp;&nbsp;审&nbsp;&nbsp;核&nbsp;&nbsp;&nbsp;</button>
				</div>
			</div>
		</li>
		<li>
			<div class="button">
				<div class="buttonContent">
					<button type="button" class="close">&nbsp;&nbsp;&nbsp;取&nbsp;&nbsp;消&nbsp;&nbsp;&nbsp;</button>
				</div>
			</div>
		</li>
	</ul>
</div>
</form>
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