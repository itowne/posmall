<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>

<form id="pagerForm" method="post" action="${ctx}/admin/agmt/logisticsList.do">
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage}"/>
	<input type="hidden" name="iagmt" value="${iagmt}"/>
</form>
  

<div class="pageHeader">
	<form id="" name="adminPayNoticeListForm" onsubmit="return dialogSearch(this);" action="${ctx}/admin/agmt/logisticsList.do" method="post">
	<div class="searchBar">
		<table class="searchContent">
      
		</table>
		<input type="hidden" nam="iagmt" value="${iagmt}">
	</div>
	</form>
</div>
	<div class="pageContent">
	 
		<table class="list" width="100%" layoutH="68">
			<thead>
				<tr>
					<th width="80">账单编号</th>
	<!-- 				<th width="80">客户名称</th> -->
					<th width="85">协议编号</th>
					<th width="120">订单编号</th>
					<th width="120">物流单编号</th>
					<th width="100">支付状态</th>
					<th width="100">物流费用</th>
					<th width="100">已支付金额</th>
					<th width="100">欠费金额</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="list" items="${logisticsList}" >
					<tr>
					<td>${list[0]}</td>
					<td>${list[1]}</td>
					<td>${list[2]}</td>
					<td>${list[3]}</td>
					<td><posmall:dict dictType="pay_status" dictCode="${list[4]}"></posmall:dict></td>
					<td align="right"><fmt:formatNumber value="${list[5]}" pattern="¥#,##0.00#"/>&nbsp;</td>
					<td align="right"><fmt:formatNumber value="${list[6]}" pattern="¥#,##0.00#"/>&nbsp;</td>
					<td align="right"><fmt:formatNumber value="${list[5]-list[6]}" pattern="¥#,##0.00#"/>&nbsp;</td>
					<td></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="panelBar">
			<div class="pages">
				<span>显示</span>
				<select class="combox" name="numPerPage" onchange="dialogPageBreak({numPerPage:this.value})">
					<option <c:if test="${numPerPage==10}">selected="selected"</c:if> value="10" >10</option>
					<option <c:if test="${numPerPage==20}">selected="selected"</c:if> value="20">20</option>
					<option <c:if test="${numPerPage==50}">selected="selected"</c:if> value="50">50</option>
					<option <c:if test="${numPerPage==100}">selected="selected"</c:if> value="100">100</option>
				</select>
				<span>条，共${totalCount}条</span>
			</div>
			
			<div class="pagination"  targetType="dialog" totalCount="${totalCount}" numPerPage="${numPerPage}" pageNumShown="10" currentPage="${pageNum}"></div>
		</div>
	</div>
