<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>

<form id="pagerForm" method="post" action="${ctx}/admin/trace/detailTraceList.do">
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<input type="hidden" name="agmtNo" value="${tdetailTraceCondition.agmtNo}" />
	<input type="hidden" name="ordNo" value="${tdetailTraceCondition.ordNo}" />
	<input type="hidden" name="custName" value="${custName}" />
	<input type="hidden" name="logisticsOrdNo" value="${tdetailTraceCondition.logisticsOrdNo}" />
	<input type="hidden" name="startTimeStr" value='<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${tdetailTraceCondition.startTime}"/>' />
	<input type="hidden" name="endTimeStr" value='<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${tdetailTraceCondition.endTime}"/>' />
</form>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="${ctx}/admin/trace/detailTraceList.do" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					客户名称：
					<input type="text" name="custName" size="15" value="${custName}" />
				</td>
				<td>
					协议编号：
					<input type="text" name="agmtNo" size="15" value="${tdetailTraceCondition.agmtNo}" />
				</td>
				<td>
					订单编号：
					<input type="text" name="ordNo" size="15" value="${tdetailTraceCondition.ordNo}" />
			    </td>
			    <td>
					物流单编号：
					<input type="text" name="logisticsOrdNo" size="15" value="${tdetailTraceCondition.logisticsOrdNo}" />
			    </td>
			</tr><tr>
			 
			    <td colspan="4"> 
			     	时间范围：<input type="text" name="startTimeStr" class="date" dateFmt="yyyy-MM-dd HH:mm:ss" readonly="readonly" value='<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${tdetailTraceCondition.startTime}"/>' /> 
			     	- <input type="text" name="endTimeStr" class="date" dateFmt="yyyy-MM-dd HH:mm:ss" readonly="readonly" value='<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${tdetailTraceCondition.endTime}"/>' />
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
	<table class="list" width="100%" layoutH="138">
		<thead>
			<tr>
			    <th width="100">客户名称</th>
				<th width="100">协议编号</th>
				<th width="100">订单编号</th>
				<th width="100">物流单编号</th>
				<th width="400">操作说明</th>
				<th width="160">生成时间</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="tdetailTrace" items="${list}" >
				<tr>
					<td>&nbsp;<posmall:dict dictType="custreg_name" dictCode="${tdetailTrace.icust}" ></posmall:dict></td>
					<td>${tdetailTrace.agmtNo}</td>
					<td>${tdetailTrace.ordNo}</td>
					<td>${tdetailTrace.logisticsOrdNo}</td>
					<td align="center">${tdetailTrace.memo}</td>
					<td align="center"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${tdetailTrace.genTime}"/></td>
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
		<div class="pagination" targetType="navTab" totalCount="${totalCount}" numPerPage="${numPerPage}" pageNumShown="10" currentPage="${pageNum}"></div>

	</div>
</div>
