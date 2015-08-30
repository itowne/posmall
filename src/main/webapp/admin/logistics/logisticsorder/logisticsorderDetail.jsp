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
			 <div class="panel collapse" defH="">
				<h1 align="left">物流信息</h1>
				<div>
				<table border="1" class="list" width="100%" height="100%">
				<tr>
					<td width="10%" align="left" style="font-weight: bolder;">物流单编号：</td>
					<td width="25%" align="left">${logisticsOrd4Page.tlogisticsOrd.innerOrdno}</td>
					<td width="10%" align="left" style="font-weight: bolder;">物流单状态：</td>
					<td width="25%" align="left"><posmall:dict dictType="logistics_ord_status" dictCode="${logisticsOrd4Page.tlogisticsOrd.logisticsOrdStatus}"></posmall:dict></td>
					<td width="10%" align="left" style="font-weight: bolder;">支付状态：</td>
					<td width="20%" align="left"><posmall:dict dictType="pay_status" dictCode="${logisticsOrd4Page.tlogisticsOrd.payStatus}"></posmall:dict>&nbsp;</td>
				</tr>
				<tr>
					<td width="10%" align="left" style="font-weight: bolder;">物流公司：</td>
					<td width="25%" align="left">${logisticsOrd4Page.logisticsOrdAddr4PageList[0].tlogisticsOrdAddr.logisticsComName}</td>
					<td width="10%" align="left" style="font-weight: bolder;">单价：</td>
					<td width="25%" align="left">${logisticsOrd4Page.logisticsOrdAddr4PageList[0].tlogisticsOrdAddr.price}(元/台)</td>
					<td width="10%" align="left" style="font-weight: bolder;">运费：</td>
					<td width="20%" align="left"><fmt:formatNumber value="${logisticsOrd4Page.tlogisticsOrd.logisticsFreight}" pattern="¥#,##0.00#"/>&nbsp;</td>
				</tr>
		
					</table>
			</div></div>
	  
	   	 	<div class="panel collapse" defH="">
				<h1 align="left">发货信息</h1>
				<div>
			 <c:if test='${logisticsOrd4Page.tlogisticsOrd.logisticsOrdStatus=="SHIPPED" || logisticsOrd4Page.tlogisticsOrd.logisticsOrdStatus=="PARTIAL_DELIVERY" || logisticsOrd4Page.tlogisticsOrd.logisticsOrdStatus=="ALL_SERVICE" }'>
			    <p>
					 <label>真实物流单号：</label>      <input type="text" name="realOrdno" readonly="readonly" value="${logisticsOrd4Page.logisticsOrdAddr4PageList[0].tlogisticsOrdAddr.realOrdno}" />
			    </p> 
			    <p>
					 <label>出库单号：</label><input type="text" name="outstockNo"  readonly="readonly"  value="${logisticsOrd4Page.logisticsOrdAddr4PageList[0].tlogisticsOrdAddr.outstockNo}" />
			    </p> 
			    <p>
					 <label> 实际发货日期：</label><input type="text" name="realDeliveryDateForm"  readonly="readonly"
					 value="<fmt:formatDate value="${logisticsOrd4Page.logisticsOrdAddr4PageList[0].tlogisticsOrdAddr.realDelivery}" pattern="yyyy-MM-dd HH:mm:ss" />"/>
			    </p> 
			    <p>
					  <label> 实际到达日期：</label><input type="text" name="realArrivalDateForm"  readonly="readonly"
					  value="<fmt:formatDate value="${logisticsOrd4Page.logisticsOrdAddr4PageList[0].tlogisticsOrdAddr.realArrival}" pattern="yyyy-MM-dd HH:mm:ss" />" />
			    </p> 
			  </c:if>
				<table border="1" class="list" width="100%" height="100%">
					<thead>
						<tr>
							<td width="150" align="center" style="font-weight: bolder;">产品名称</td>
							<td width="150" align="center" style="font-weight: bolder;">发货量</td>
							 <c:if test='${logisticsOrd4Page.tlogisticsOrd.logisticsOrdStatus=="SHIPPED" || logisticsOrd4Page.tlogisticsOrd.logisticsOrdStatus=="PARTIAL_DELIVERY" || logisticsOrd4Page.tlogisticsOrd.logisticsOrdStatus=="ALL_SERVICE" }'><td align="center" width="150" style="font-weight: bolder;">实际发货量</td></c:if>
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
							 <c:if test='${logisticsOrd4Page.tlogisticsOrd.logisticsOrdStatus=="SHIPPED" || logisticsOrd4Page.tlogisticsOrd.logisticsOrdStatus=="PARTIAL_DELIVERY" || logisticsOrd4Page.tlogisticsOrd.logisticsOrdStatus=="ALL_SERVICE" }'>
							 	<td align="center"><span style="float:none">${logisticsOrdAddr4Page.tlogisticsOrdAddr.realOutstockNum}</span>&nbsp;台</td>
							 </c:if>
							 <td></td>
							</tr>
						</c:forEach>
						<tr>
							<td align="center">合&nbsp;&nbsp;&nbsp;计</td>
							<td align="center"><span class="allNum"></span>台</td>
							<c:if test='${logisticsOrd4Page.tlogisticsOrd.logisticsOrdStatus=="SHIPPED" || logisticsOrd4Page.tlogisticsOrd.logisticsOrdStatus=="PARTIAL_DELIVERY" || logisticsOrd4Page.tlogisticsOrd.logisticsOrdStatus=="ALL_SERVICE" }'>
								<td align="center"><span>${logisticsOrd4Page.tlogisticsOrd.realOutstockNum}</span>台</td>
						    </c:if>
						    <td></td>
						</tr>
					</tbody>
				</table>
			</div></div>
			<div class="panel collapse" defH="">
				<h1 align="left">发货地址</h1>
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
