<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/posmall-tags" prefix="posmall"%>
	<form id="toAddStep5Form" method="post" action="${ctx}/admin/logistics/logisticsorder/logisticsorderAuditMore.do"
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
								<th width="130">预计发货日期</th>
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
								<td  align="center">
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
					<table border="1" class="list" width="100%" >
						<thead>
							<tr>
								<th width="150">产品名称</th>
								<th width="90">发货数量</th>
								<th width="380">收货地址</th>
								<th width="90">联系人</th>
								<th width="90">联系电话</th>
								<th width="120">是否选用付费物流</th>
								<th width="140">运费单价(元/台)</th>
								<th></th>
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
									<td>
									   <input type="hidden" name="addrs[${st.index}].tlogisticsOrdAddr.ilogisticsOrdAddr" value="${addr.tlogisticsOrdAddr.ilogisticsOrdAddr}"/>
									   <input type="text"  class="required money" name="addrs[${st.index}].tlogisticsOrdAddr.price" value='<c:choose><c:when test="${addr.tlogisticsOrdAddr.feeFlag == '是'}">5.00</c:when><c:otherwise>0</c:otherwise></c:choose>'/>
									</td>
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
   function	chanagLogisticsCom(v){
	   var str = $(v).find("option:selected").attr("id").split("_");
	   $(v).next().val(str[1]);
	   $(v).next().next().val(str[0]);
	   $(v).parent().next().find("input").val(str[2]);
	   
   }
</script>