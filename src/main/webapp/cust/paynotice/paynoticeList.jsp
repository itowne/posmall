<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>
<form id="pagerForm" method="post" action="${ctx}/cust/paynotice/paynoticeList.do">
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<input type="hidden" name="icust" value="${icust}" />
	<input type="hidden" name="payType" value="${payType}" />
	<input type="hidden" name="payNotifyStatus" value="${payNotifyStatus}" />
	<input type="hidden" name="startDate" value="${startDate}" />
	<input type="hidden" name="endDate" value="${endDate}" />
</form>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="${ctx}/cust/paynotice/paynoticeList.do" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					账单类型：<posmall:dictSelect selectName="payType" dictType="pay_type" defaultValue="${payType}"/>
				</td>
				<td>
					支付状态： <posmall:dictSelect selectName="payNotifyStatus" dictType="pay_status" defaultValue="${payNotifyStatus}"/>
				</td>
			   <td colspan="3"> 
			     	生成日期：<input class="date" readonly="readonly" name="startDate" value="${startDate}"/> - <input class="date" readonly="readonly" name="endDate" value="${endDate}"/>
			   </td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<table class="list" width="100%" layoutH="110">
		<thead>
			<tr>
				<!-- <th width="22"><input id="allIdConcrol" type="checkbox" group="ids" class="checkboxCtrl"></th> -->
				<th width="90">账单编号</th>
				<th width="80">账单类型</th>
				<th width="90">协议编号</th>
				<th width="90">订单编号</th>
				<th width="90">物流编号</th>
				<th width="90">金额</th>
				<th width="100">状态</th>
				<th width="120">生成时间</th>
				<th width="270">操作</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="tpayNotify" items="${tpayNotifyList}" varStatus="status">
				<tr>
				<%-- <td>
					<c:if test="${tpayNotify[8] == 'WAIT_PAY' || tpayNotify[8] == 'PART_PAY' || tpayNotify[8] == 'NO_PASS'}">					
						<input id="ids${status.index}" name="ids" value="${tpayNotify[0]}" type="checkbox">
					</c:if>
				</td> --%>
				<td>${tpayNotify[4]}</td>
				<td align="center"><posmall:dict dictType="pay_type" dictCode="${tpayNotify[3]}"></posmall:dict></td>
				<td>${tpayNotify[10]}</td>
				<td>${tpayNotify[11]}</td>
				<td>${tpayNotify[12]}</td>
				<td align="right"><fmt:formatNumber value="${tpayNotify[9]}" pattern="¥#,##0.00#"/>&nbsp;</td>
				<td align="center"><posmall:dict dictType="pay_status" dictCode="${tpayNotify[8]}" /></td>
				<td align="center"><fmt:formatDate value="${tpayNotify[6]}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
					<div class="panelBar tablebtn">
						<ul class="toolBar">
							<li><posmall:authA cssClass="icon" title="账单详情" target="dialog" href="${ctx}/cust/paynotice/paynoticeDetail.do?ipayNotify=${tpayNotify[0]}&payType=${tpayNotify[3]}" width="840" height="480" mask="true" rel="FKTZSGL_XQ" value="详情"/></li>
							<c:if test="${tpayNotify[8] != 'WAIT_PAY' && tpayNotify[8] != 'REVOKED'}">
								<li><posmall:authA cssClass="icon" title="付款明细" target="dialog" href="${ctx}/cust/paynotice/payDetail.do?ipayNotify=${tpayNotify[0]}" width="560" height="540" mask="true" rel="FKTZSGL_CKFKMX" value="查看付款明细"/></li>
							</c:if>
							<c:if test="${tpayNotify[8] == 'WAIT_PAY' || tpayNotify[8] == 'PART_PAY' || tpayNotify[8] == 'NO_PASS'}">
								<li><posmall:authA cssClass="add" title="上传付款凭证" href="${ctx}/cust/file/toUploadVoucher.do?ipayNotify=${tpayNotify[0]}" target="dialog" mask="true" width="560" height="540" rel="FKTZSGL_SCFKPZ" value="上传付款凭证"/></li>
							</c:if>
						</ul>
					</div>
				</td>
				<td></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option <c:if test="${numPerPage==10}">selected="selected"</c:if> value="10" >10</option>
				<option <c:if test="${numPerPage==20}">selected="selected"</c:if> value="20">20</option>
				<option <c:if test="${numPerPage==50}">selected="selected"</c:if> value="50">50</option>
				<option <c:if test="${numPerPage==100}">selected="selected"</c:if> value="100">100</option>
			</select>
			<span>条，共${totalCount}条</span>
		</div>
		
		<div class="pagination"  targetType="navTab" totalCount="${totalCount}" numPerPage="${numPerPage}" pageNumShown="10" currentPage="${pageNum}"></div>

	</div>
</div>
<script type="text/javascript">

	var _payNotifyIds = "";
	$(function() {
		// 复合多选框
		$("#allIdConcrol").on("change", function() {
			if($(this).attr('checked') == "checked") {
				_payNotifyIds = "";
				$("input[name = 'ids']").each(function() {
					if(_payNotifyIds.trim() != "") {
						_payNotifyIds = _payNotifyIds + "," + $(this).val();
					}else {
						_payNotifyIds = $(this).val();
					}
				});
				$("#payNotifyIds").val(_payNotifyIds);
			}else {
				$("#payNotifyIds").val("");
			}
		});
		// 单个多选框
		$("input[name = 'ids']").on("change", function() {
			_payNotifyIds = "";
			$("input[name = 'ids']").each(function() {
				if($(this).attr("checked") == "checked") {
					if(_payNotifyIds.trim() != "") {
						_payNotifyIds = _payNotifyIds + "," + $(this).val();
					}else {
						_payNotifyIds = $(this).val();
					}
				}
			});
			$("#payNotifyIds").val(_payNotifyIds);
		});
	});
	
</script>