<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/posmall-tags" prefix="posmall"%>
<div class="pageContent">
<script type="text/javascript">
 $(".dialogHeader_c > h1").html("第三步 发货详情");
</script>
<form method="post" action="${ctx}/cust/logistics/toAddStep3More.do"
	class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
	<input type="hidden" name="iord" value="${iord}">
	<input type="hidden" name="ilogisticsOrd" value="${ilogisticsOrd}">
	<div class="pageFormContent" layoutH="65">
		<div class="panel collapse" defH="">
				<h1 align="left">订单信息</h1>
				<div>
					<table border="1" class="list" width="100%" >
						<thead>
							<tr>
								<th width="130">订单名称</th>
								<th width="130">下单时间</th>
								<th width="130">订单金额</th>
								<th width="130">已支付金额</th>
								<th width="130">已使用货款金额</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
								<tr title="${ord4page.tips}" class="data" style="cursor: pointer;">
									<td align="center">${ord4page.tord.ordNo}</td>
									<td align="center">
										<fmt:formatDate value="${ord4page.tord.genTime}" pattern="yyyy-MM-dd HH:mm:ss" />
									</td>
									<td align="right"><fmt:formatNumber value="${ord4page.tord.amt}" pattern="¥#,##0.00#"/></td>
									<td align="right"><fmt:formatNumber value="${ord4page.havePaid}" pattern="¥#,##0.00#"/></td>
									<td align="right"><fmt:formatNumber value="${ord4page.tord.amtOfDelivery}" pattern="¥#,##0.00#"/></td>
									<td></td>
								</tr>
						</tbody>
					</table>
				</div>
			</div>
	  <c:if test='${flag==false}'><p style="width: 700px;text-align: left;"> <label class="tips" style="width: 700px;font-size: 20px;">以下红色标识为错误信息，请修改后重新上传</label></p></c:if>
	  <div class="panel collapse" defH="">
	   <h1 align="left">发货地址详情</h1>
		<div>
		<table border="1" class="list" width="100%">
			<thead>
				<tr>
					<th width="150">产品型号</th>
					<th width="90">发货数量</th>
					<th width="290">发货地址</th>
					<th width="90">联系人</th>
					<th width="90">联系电话</th>
					<th width="120">是否选用付费物流</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list}" var="logisticsOrdAddrObj4Page" varStatus="logisticsOrdAddrObj4PageStatus">
					<tr>
						<td align="left">&nbsp;${logisticsOrdAddrObj4Page.pdtNo}</td>
						<td align="left">${logisticsOrdAddrObj4Page.num}&nbsp;台</td>
						<td align="left">${logisticsOrdAddrObj4Page.consigneeAddr}&nbsp;</td>
						<td align="left">${logisticsOrdAddrObj4Page.name}&nbsp;</td>
						<td align="left">${logisticsOrdAddrObj4Page.mobile}&nbsp;</td>
						<td align="center">${logisticsOrdAddrObj4Page.feeFlag}&nbsp;</td>
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
					<a class="add" href="${ctx}/cust/logistics/toAddStep1More.do?iord=${iord}" 
							target="dialog" mask="true" width="640" height="540"
							rel="CZWLXXGL_XZ2" title="第二步  上传数据模板"><span>返回上一步</span></a>
				</div>
			</li>
			<li>	
				  <div <c:choose> <c:when test='${flag==false}'>class="buttonDisabled"</c:when><c:otherwise>class="button"</c:otherwise> </c:choose>>
					<div class="buttonContent">
						<button  <c:choose> <c:when test='${flag==false}'> type="button"</c:when><c:otherwise> type="submit"</c:otherwise> </c:choose>>&nbsp;&nbsp;&nbsp;下一步&nbsp;&nbsp;&nbsp;</button>
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
