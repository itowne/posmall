<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>
<form id="pagerForm" method="post" action="${ctx}/cust/logistics/logisticsList.do">
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<input type="hidden" name="innerOrdno" value="${tlogisticsOrd.innerOrdno}" />
	<input type="hidden" name="logisticsOrdStatus" value="${tlogisticsOrd.logisticsOrdStatus}" />
	<input type="hidden" name="payStatus" value="${tlogisticsOrd.payStatus}" />
	<input type="hidden" name="consigneeName" value="${consigneeName}" />
</form>
<div class="pageHeader">
	<form id="logisticsForm" name="logisticsForm" onsubmit="return navTabSearch(this);" action="" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					物流单编号：<input type="text" name="innerOrdno" value="${tlogisticsOrd.innerOrdno}" />
				</td>
				<td>
					物流单状态：
					<posmall:dictSelect selectName="logisticsOrdStatus" defaultValue="${tlogisticsOrd.logisticsOrdStatus}" dictType="logistics_ord_status"></posmall:dictSelect>
				</td>
				<td>
					支付状态：
					<posmall:dictSelect selectName="payStatus" defaultValue="${tlogisticsOrd.payStatus}" dictType="pay_status"></posmall:dictSelect>
				</td>
				<td>
					联系人：<input type="text" name="consigneeName" value="${consigneeName}" />
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<!-- <li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="toSbumit('downLoad')">下载</button></div></div></li> -->
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="toSbumit('query')">查询</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
<%-- 			<li><posmall:authA cssClass="add" href="${ctx}/cust/logistics/toAddStep1.do" target="dialog" mask="true" width="840" height="460" rel="CZWLXXGL_XZ" title="第一步：订单选择" value="制定物流计划" /></li> --%>
			<li><posmall:authA cssClass="icon" href="${ctx}/cust/logistics/toAddStep1MoreBefore.do" target="dialog" mask="true" width="840" height="460" rel="CZWLXXGL_XZ2" title="第一步：订单选择" value="发货通知" /></li>
			<li><posmall:authA cssClass="add" href="${ctx}/cust/logistics/toDebtsDetail.do" target="dialog" mask="true" width="840" height="460" rel="CZWLXXGL_WLQKMX" value="物流欠款明细" /></li>
		</ul>
	</div>
	<table class="list" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="100">物流单编号</th>
				<th width="100">订单编号</th>
				<th width="80">物流单状态</th>
				<th width="80">支付状态</th>
<!-- 				<th width="80">是否多地址</th> -->
				<th width="100">数量</th>
				<th width="100">预计发货日期</th>
				<th width="100">物流费用</th>
				<th width="100">欠款</th>
				<th width="160" align="center">操作</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${tLogisticsOrdList}" var="list">
			<tr>
				<td align="left">&nbsp;${list.innerOrdno}</td>
				<td align="left">&nbsp;${list.tord.ordNo}</td>
				<td align="center">&nbsp;<posmall:dict dictCode="${list.logisticsOrdStatus}" dictType="logistics_ord_status" /></td>
			    <td align="center">&nbsp;<posmall:dict dictCode="${list.payStatus}" dictType="pay_status" /></td>
<!-- 				<td align="center"> -->
<%-- 				   <c:choose> --%>
<%-- 				   	<c:when test='${list.ifile == "-1"}'>否</c:when> --%>
<%-- 				   	<c:otherwise>是</c:otherwise> --%>
<%-- 				   </c:choose> --%>
<!-- 				</td> -->
				<td align="right">${list.num}&nbsp;台</td>
				<td align="center">&nbsp;<fmt:formatDate value="${list.specifyDelivery}" pattern="yyyy-MM-dd"/></td>
				<td align="right"><fmt:formatNumber value="${list.logisticsFreight}" pattern="¥#,##0.00#"/>&nbsp;</td>
				<td align="right"><fmt:formatNumber value="${list.debts}" pattern="¥#,##0.00#"/>&nbsp;</td>
				<td align="left">
					<div class="panelBar tablebtn">
					<ul class="toolBar">
					   <c:choose>
						   	<c:when test='${list.ifile == "-1"}'>
								<li><posmall:authA cssClass="icon" target="dialog" href="${ctx}/cust/logistics/logisticsOrdDetail.do?ilogisticsOrd=${list.ilogisticsOrd}" width="820" height="480" mask="true" rel="WLXXGL_XQ" value="物流详情" /></li>
							</c:when>
						   	<c:otherwise>
						  		<li><posmall:authA cssClass="icon" target="dialog" href="${ctx}/cust/logistics/logisticsOrdDetailMore.do?ilogisticsOrd=${list.ilogisticsOrd}" width="1020" height="480" mask="true" rel="WLXXGL_XQ" value="物流详情" /></li>
						   	</c:otherwise>
					   </c:choose>
						
					     <c:if test='${list.logisticsOrdStatus == "WAIT_AUDIT"}'>
							   <li><posmall:authA cssClass="delete" target="ajaxTodo" href="${ctx}/cust/logistics/logisticsOrdCancel.do?ilogisticsOrd=${list.ilogisticsOrd}" title="确定要撤销吗?" rel="WLXXGL_CX" value="撤销" /></li>
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
				<option <c:if test="${numPerPage==10}">selected="selected"</c:if> value="10">10</option>
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
	function toSbumit(flag){
		if(flag=='query'){
			document.logisticsForm.action="";
			document.logisticsForm.onsubmit();
		}else{
			document.logisticsForm.action="${ctx}/cust/logistics/downLoadLogistics.do";
			$("#logisticsForm").attr("target","_blank");
			document.logisticsForm.submit();
		}
		
	}
</script>