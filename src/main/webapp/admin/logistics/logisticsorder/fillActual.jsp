<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/posmall-tags" prefix="posmall"%>
<script type="text/javascript">
function modifyNum(v){
  var amt = 0;
  $(".eachNum > input").each(function(){
	  amt +=  parseInt($(this).val());
  })
  $("#logisticsOrderAuditForm .allNum").html(amt);
}
</script>
<form id="logisticsOrderAuditForm" method="post" action="${ctx}/admin/logistics/logisticsorder/fillActual.do"
  class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
  <div class="pageContent" layoutH="48">
	<input type="hidden" name="ilogisticsOrd"
		value="${logisticsOrd4Page.tlogisticsOrd.ilogisticsOrd}">

	<div class="pageFormContent">
	  <div class="panel collapse" defH="">
			<h1 align="left">实际发货信息</h1>
			<div>
			    <p>
					 <label>真实物流单号：</label> <input type="text" name="realOrdno" class="required" value="${logisticsOrd4Page.logisticsOrdAddr4PageList[0].tlogisticsOrdAddr.realOrdno}" />
			    </p> 
			    <p>
					 <label>出库单号：</label><input type="text" name="outstockNo" class="required"  value="${logisticsOrd4Page.logisticsOrdAddr4PageList[0].tlogisticsOrdAddr.outstockNo}" />
			    </p> 
			    <p>
					 <label> 实际发货日期：</label><input type="text" name="realDeliveryDateForm" class="required date" readonly="readonly" dateFmt="yyyy-MM-dd HH:mm:ss" 
					 value="<fmt:formatDate value="${logisticsOrd4Page.logisticsOrdAddr4PageList[0].tlogisticsOrdAddr.realDelivery}" pattern="yyyy-MM-dd HH:mm:ss" />"/>
			    </p> 
			    <p>
					  <label> 实际到达日期：</label><input type="text" name="realArrivalDateForm" class="date" readonly="readonly" dateFmt="yyyy-MM-dd HH:mm:ss" 
					  value="<fmt:formatDate value="${logisticsOrd4Page.logisticsOrdAddr4PageList[0].tlogisticsOrdAddr.realArrival}" pattern="yyyy-MM-dd HH:mm:ss" />" />
			    </p> 
			    
			</div>
		</div>
		<div class="panel collapse" defH="">
			<h1 align="left">发货信息</h1>
			<div>
				<table border="1" class="list" width="100%" height="100%">
					<thead>
						<tr>
							<td width="150" align="center" style="font-weight: bolder;">产品名称</td>
							<td width="150" align="center" style="font-weight: bolder;">发货量</td>
							<td width="150" align="center" style="font-weight: bolder;">实际发货量</td>
						    <td></td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${logisticsOrd4Page.logisticsOrdAddr4PageList}"
							var="logisticsOrdAddr4Page"
							varStatus="st">
							<tr>
								<td align="left">
								<input type="hidden" name="logisticsOrdAddr4PageList[${st.index}].pdtName" value="${logisticsOrdAddr4Page.pdtName}"/>
							    <input type="hidden" name="logisticsOrdAddr4PageList[${st.index}].tlogisticsOrdAddr.ilogisticsOrdAddr" value="${logisticsOrdAddr4Page.tlogisticsOrdAddr.ilogisticsOrdAddr}"/>
								&nbsp;${logisticsOrdAddr4Page.pdtName}</td>
								<td align="center"><span>${logisticsOrdAddr4Page.tlogisticsOrdAddr.num}</span>&nbsp;台</td>
								<td align="center"><span class="eachNum">
								  <c:choose>
								  <c:when test='${logisticsOrd4Page.tlogisticsOrd.logisticsOrdStatus=="HAVE_LIBRARY"}'>
									<input type="text" size="12" class="required digits" style="float:none;text-align: center" onkeyup="modifyNum(this);" name="logisticsOrdAddr4PageList[${st.index}].tlogisticsOrdAddr.realOutstockNum" value="${logisticsOrdAddr4Page.tlogisticsOrdAddr.num}">
								  </c:when>
								  <c:otherwise>
								  <input type="text" size="12" class="required digits" style="float:none;text-align: center" onkeyup="modifyNum(this);" name="logisticsOrdAddr4PageList[${st.index}].tlogisticsOrdAddr.realOutstockNum" value="${logisticsOrdAddr4Page.tlogisticsOrdAddr.realOutstockNum}">
								  </c:otherwise>
								</c:choose>  
								</span>&nbsp;台</td>
								 <td></td>
							</tr>
						</c:forEach>
						<tr>
							<td align="center">合&nbsp;&nbsp;&nbsp;计</td>
							<td align="center"><span>${logisticsOrd4Page.tlogisticsOrd.num}</span>台</td>
							<td align="center"><span class="allNum">
								<c:choose>
									  <c:when test='${logisticsOrd4Page.tlogisticsOrd.logisticsOrdStatus=="HAVE_LIBRARY"}'>
										${logisticsOrd4Page.tlogisticsOrd.num}
									  </c:when>
									  <c:otherwise>
									  ${logisticsOrd4Page.tlogisticsOrd.realOutstockNum}
									  </c:otherwise>
									</c:choose>  
							</span>台</td>
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
					<td width="35%" align="left">${logisticsOrd4Page.logisticsOrdAddr4PageList[0].tlogisticsOrdAddr.price}(元/台)</td>
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
					<button type="submit">&nbsp;&nbsp;&nbsp;提&nbsp;&nbsp;交&nbsp;&nbsp;&nbsp;</button>
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

