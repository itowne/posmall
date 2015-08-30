<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/posmall-tags" prefix="posmall"%>
<div class="pageContent">
<script type="text/javascript">
	//异常、错误信息提示
	var _tordList = "${ord4page}";
	if (_tordList == "[]") {
		alertMsg.error("你没有可以发货的订单");
		$.pdialog.closeCurrent();
	}
	$(".dialogHeader_c > h1").html("第四步 确认发货");
	
	function beforeSubmit() {
		var _specifyDelivery = $("#specifyDelivery").val();
		if(_specifyDelivery == null || _specifyDelivery.trim() == "") {
			alertMsg.error("预计发货日期异常不能为空！");
			return false;
		}
		$("#logisticsAddStep3MoreForm").submit();
	}
</script>
	<form id="logisticsAddStep3MoreForm" method="post" action="${ctx}/cust/logistics/toAddStep5More.do"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<input type="hidden" name="ilogisticsOrd" value="${ilogisticsOrd}">
		<div class="pageFormContent" selector="h1" layoutH="65">
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
								<th width="130">预计发货日期</th>
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
									<td>
										<input type="text" id="specifyDelivery" name="specifyDelivery" class="required" readonly="readonly"
										value="${specifyDelivery}">
									</td>
									<td></td>
								</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="panel collapse" defH="">
				<h1 align="left">发货产品信息</h1>
				<div>
					<table border="1" class="list" width="100%" >
						<thead>
							<tr>
								<th width="150">产品名称</th>
								<th width="90">发货数量</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${p.pdts}" var="pdt" >
								<tr>
									<td align="left">&nbsp;${pdt.pdtName}</td>
									<td align="right">${pdt.num}台&nbsp;</td>
									<td></td>
								</tr>
							</c:forEach>
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
								<th width="120">是否选用付费物流</th>
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
									<td align="center">&nbsp;${addr.tlogisticsOrdAddr.feeFlag}</td>
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
								rel="CZWLXXGL_XZ2" title="第二步 上传数据模板"><span>返回上一步</span></a>
					</div>
				</li>
				<li>
					<div class="button"  id="submitA">
					    <div class="buttonContent">
							<button type="button" class="button" onclick="return beforeSubmit();">&nbsp;&nbsp;确&nbsp;&nbsp;定&nbsp;&nbsp;</button>
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