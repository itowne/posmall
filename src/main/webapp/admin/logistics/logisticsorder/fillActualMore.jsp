<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/posmall-tags" prefix="posmall"%>
<form id="logisticsOrderAuditForm" method="post" action="${ctx}/admin/logistics/logisticsorder/fillActualMore.do"
  class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">

		<div class="pageContent" selector="h1" layoutH="42">
		<input type="hidden" name="ilogisticsOrd" value="${ilogisticsOrd}">
		
			<div class="panel collapse" defH="">
				<h1 align="left">订单详情</h1>
				<div>
					<table border="1" class="list" width="100%" >
						<thead>
							<tr>
								<th width="130">订单编号</th>
								<th width="130">下单时间</th>
								<th width="130">订单金额</th>
								<th  width="130">预计发货日期</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>${p.tord.ordNo}</td>
								<td>
									<fmt:formatDate value="${p.tord.genTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
								<td><fmt:formatNumber value="${p.tord.amt}" pattern="¥#,##0.00#"/></td>
								<td align="center">
									<fmt:formatDate value="${tlogisticsOrd.specifyDelivery}" pattern="yyyy-MM-dd"/>
								</td>
								<td></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="panel collapse" defH="">
				<h1 align="left">发货地址详情</h1>
				<div>
					<table border="1" class="list" width="120%" >
						<thead>
							<tr>
								<th width="120">产品名称</th>
								<th width="100">发货数量</th>
								<th width="360">收货地址</th>
								<th width="90">联系人</th>
								<th width="90">联系电话</th>
								<th width="120">是否选用付费物流</th>
<!-- 								<th width="80">物流公司</th> -->
								<th width="100">运费单价(元/台)</th>
								<th width="100">实际发货量(台)</th>
								<th width="80">真实物流单号</th>
								<th width="80">出库单号</th>
								<th width="80">实际发送时间</th>
								<th width="80">实际到达日期</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${p.addrs}" var="addr" varStatus="st" >
								<tr>
									<td align="left">&nbsp;${addr.pdtName}</td>
									<td align="right">${addr.tlogisticsOrdAddr.num}台&nbsp;</td>
									<td align="left">&nbsp;${addr.tlogisticsOrdAddr.consigneeAddr}</td>
									<td align="left">&nbsp;${addr.tlogisticsOrdAddr.consigneeName}</td>
									<td align="left">&nbsp;${addr.tlogisticsOrdAddr.consigneeMobile}</td>
									<td align="left">&nbsp;${addr.tlogisticsOrdAddr.feeFlag}</td>
<!-- 									<td> -->
<%-- 									    &nbsp; ${addr.tlogisticsOrdAddr.logisticsComName} --%>
<!-- 									</td> -->
									<td>
									    &nbsp;${addr.tlogisticsOrdAddr.price} 
									    <input type="hidden" name="addrs[${st.index}].tlogisticsOrdAddr.ilogisticsOrdAddr" value="${addr.tlogisticsOrdAddr.ilogisticsOrdAddr}"/>
									</td>
									<td align="center"><span class="eachNum">
									     <c:choose>
										  <c:when test='${tlogisticsOrd.logisticsOrdStatus=="HAVE_LIBRARY"}'>
											<input type="text" size="8" class="required digits" style="float:none" onkeyup="modifyNum(this);" name="addrs[${st.index}].tlogisticsOrdAddr.realOutstockNum" value="${addr.tlogisticsOrdAddr.num}">
										  </c:when>
										  <c:otherwise>
										  <input type="text" size="8" class="required digits" style="float:none" onkeyup="modifyNum(this);" name="addrs[${st.index}].tlogisticsOrdAddr.realOutstockNum" value="${addr.tlogisticsOrdAddr.realOutstockNum}">
										  </c:otherwise>
										</c:choose>  
									</span>&nbsp;</td>
									<td><input type="text" size="10" name="addrs[${st.index}].tlogisticsOrdAddr.realOrdno" class="required" value="${addr.tlogisticsOrdAddr.realOrdno}" /></td>
									<td><input type="text" size="10" name="addrs[${st.index}].tlogisticsOrdAddr.outstockNo" class="required"  value="${addr.tlogisticsOrdAddr.outstockNo}" /></td>
									<td><input type="text" size="16" name="realDeliveryDateFormList[${st.index}]" class="date required" readonly="readonly" dateFmt="yyyy-MM-dd HH:mm:ss" 
					                  value="<fmt:formatDate value="${addr.tlogisticsOrdAddr.realDelivery}" pattern="yyyy-MM-dd HH:mm:ss" />"/></td>
									<td><input type="text" size="16" name="realArrivalDateFormList[${st.index}]" class="date" readonly="readonly" dateFmt="yyyy-MM-dd HH:mm:ss" 
					                  value="<fmt:formatDate value="${addr.tlogisticsOrdAddr.realArrival}" pattern="yyyy-MM-dd HH:mm:ss" />" /></td>
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
						<button type="submit">&nbsp;&nbsp;&nbsp;提&nbsp;&nbsp;交&nbsp;&nbsp;&nbsp;</button>
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
