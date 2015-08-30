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
		<input type="hidden" name="ilogisticsOrd" value="${ilogisticsOrd}">
		<div class="pageFormContent">
			<div class="panel collapse" defH="">
				<h1 align="left">物流信息</h1>
				<div><table border="1" class="list" width="100%" height="100%">
				<tr>
					<td width="10%" align="left" style="font-weight: bolder;">物流单编号：</td>
					<td width="25%" align="left">${tlogisticsOrd.innerOrdno}</td>
					<td width="10%" align="left" style="font-weight: bolder;">物流单状态：</td>
					<td width="25%" align="left"><posmall:dict dictType="logistics_ord_status" dictCode="${tlogisticsOrd.logisticsOrdStatus}"></posmall:dict></td>
					<td width="10%" align="left" style="font-weight: bolder;">支付状态：</td>
					<td width="20%" align="left"><posmall:dict dictType="pay_status" dictCode="${tlogisticsOrd.payStatus}"></posmall:dict>&nbsp;</td>
				</tr>
				</table>
			  </div>
			</div>
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
								<td align="center">
									<fmt:formatDate value="${tlogisticsOrd.specifyDelivery}" pattern="yyyy-MM-dd"/>
								</td>
								<td></td>
							</tr>
						</tbody>
					</table>
				</div></div>
			<div class="panel collapse" defH="">
				<h1 align="left">物流发货详情</h1>
				<div>
					<table border="1" class="list" width="140%" >
						<thead>
							<tr>
								<th width="90">产品名称</th>
								<th width="70">发货数量</th>
								<th width="380">收货地址</th>
								<th width="60">联系人</th>
								<th width="90">联系电话</th>
								<th width="135">是否选用付费物流</th>
								<th width="90">单价(元/台)</th>
					<c:if test='${tlogisticsOrd.logisticsOrdStatus=="SHIPPED" || tlogisticsOrd.logisticsOrdStatus=="PARTIAL_DELIVERY" || tlogisticsOrd.logisticsOrdStatus=="ALL_SERVICE" }'>
						        <th width="110">实际发货量(台)</th>
								<th width="80">物流公司</th>
								<th width="80">真实物流单号</th>
								<th width="240">序列号段</th>
								<th width="130">实际发送时间</th>
								<th width="130">实际到达日期</th>
					</c:if>
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
									    &nbsp;${addr.tlogisticsOrdAddr.price} 
									</td>
							<c:if test='${tlogisticsOrd.logisticsOrdStatus=="SHIPPED" || tlogisticsOrd.logisticsOrdStatus=="PARTIAL_DELIVERY" || tlogisticsOrd.logisticsOrdStatus=="ALL_SERVICE" }'>
								    <td align="center"><span class="eachNum">
										  ${addr.tlogisticsOrdAddr.realOutstockNum}
									</span>&nbsp;</td>
									<td>${addr.tlogisticsOrdAddr.logisticsComName}</td>
									<td>${addr.tlogisticsOrdAddr.realOrdno}</td>
									<td>${addr.tlogisticsOrdAddr.realSerial}</td>
									<td><fmt:formatDate value="${addr.tlogisticsOrdAddr.realDelivery}" pattern="yyyy-MM-dd"/></td>
									<td><fmt:formatDate value="${addr.tlogisticsOrdAddr.realArrival}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							</c:if>
									<td></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
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
