<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib uri="/posmall-tags" prefix="posmall" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<form id="pagerForm" method="post" action="${ctx}/cust/agmt/agmtList.do">
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<input type="hidden" name="agmtNo" value="${agmtNo}" />
	<input type="hidden" name="agmtStatus" value="${agmtStatus}" />
	<input type="hidden" name="genTime" value='<fmt:formatDate pattern="yyyy-MM-dd" value="${genTime}"/>' />
</form>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="${ctx}/cust/agmt/agmtList.do" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					协议编号：<input type="text" name="agmtNo" value="${agmtNo}" />
				</td>
				<td>
					签署日期：<input type="text" name="genTime" class="date" readonly="true" value='<fmt:formatDate pattern="yyyy-MM-dd" value="${genTime}"/>' />
				</td>
				<td>
					状态：<posmall:dictSelect selectName="agmtStatus" dictType="agmt_status" defaultValue="${agmtStatus}" />
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
	<div class="panelBar">
		<ul class="toolBar">
			<li><posmall:authA href="${ctx}/cust/agmt/agreement.do" target="dialog" mask="true" width="810" height="530" rel="DHXYGL_QSXY" cssClass="add" value="预约订单" title="第一步：协议合同确定"/></li>
		</ul>
	</div>
	<table class="list" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="90" align="center">协议编号</th>
				<th width="80" align="center">订货总量</th>
				<th width="80" align="center">订货剩余量</th>
				<th width="100" align="center">保证金总额</th>
				<th width="100" align="center">剩余保证金</th>
				<th width="90" align="center">起始时间</th>
				<th width="90" align="center">终止时间</th>
				<th width="80" align="center">剩余天数</th>
				<th width="100" align="center">状态</th>
				<th width="180" align="center">操作</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="agmt4Page" items="${list}">
				<tr <c:if test="${agmt4Page.remainDay <= maxRemainDay && agmt4Page.remainDay >= minRemainDay}">style="background-color: #FFB5B5;"</c:if> title='产品[${agmt4Page.tpdtNames}]&#10;协议起始日期[<fmt:formatDate pattern="yyyy-MM-dd" value="${agmt4Page.tagmt.startTime}"/>]&#10;协议终止日期[<fmt:formatDate pattern="yyyy-MM-dd" value="${agmt4Page.tagmt.endTime}"/>]'>
					<td>&nbsp;<a title="协议详情" href="${ctx}/cust/agmt/agmtDetail.do?iagmt=${agmt4Page.tagmt.iagmt}" target="dialog" mask="true" width="840" height="420" rel="DHXYGL_CK">${agmt4Page.tagmt.agmtNo}</a></td>
					<td align="right">${agmt4Page.totalNum}&nbsp;</td>
					<td align="right">${agmt4Page.remainNum}&nbsp;</td>
					<td align="right"><fmt:formatNumber value="${agmt4Page.tagmt.deposit}" pattern="#,##0.00#"/>&nbsp;</td>
					<td align="right"><fmt:formatNumber value="${agmt4Page.tagmt.remainDeposit}" pattern="#,##0.00#"/>&nbsp;</td>
					<td align="center"><fmt:formatDate pattern="yyyy-MM-dd" value="${agmt4Page.tagmt.startTime}"/></td>
					<td align="center"><fmt:formatDate pattern="yyyy-MM-dd" value="${agmt4Page.tagmt.endTime}"/></td>
					<td align="center"><c:choose> <c:when test="${agmt4Page.remainDay < minRemainDay}">0</c:when><c:otherwise>${agmt4Page.remainDay}</c:otherwise></c:choose></td>
					<td align="center"><posmall:dict dictCode="${agmt4Page.tagmt.agmtStatus}" dictType="agmt_status" /></td>
					<td>
						<div class="panelBar tablebtn">
							<ul class="toolBar">
						    <li> <posmall:authA cssClass="icon" title="协议详情" href="${ctx}/cust/agmt/agmtDetail.do?iagmt=${agmt4Page.tagmt.iagmt}" target="dialog" mask="true" width="840" height="420" rel="DHXYGL_CK" value="查看" /></li>
					       <c:if test="${agmt4Page.tagmt.agmtStatus == 'AGMT_SUBMIT' || agmt4Page.tagmt.agmtStatus == 'AGMT_QUOTA_CONFIRM' }">
							 <li><posmall:authA cssClass="delete" target="ajaxTodo" href="${ctx}/cust/agmt/agmtRemove.do?iagmt=${agmt4Page.tagmt.iagmt}"  rel="DHXYGL_SC" title="确认是否撤销?" value="撤销"></posmall:authA></li>
						   </c:if>
						   <%-- 
						   <c:if test="${agmt4Page.tagmt.agmtStatus == 'CONFIRM'}">
						   	 <li><posmall:authA href="${ctx}/cust/agmt/toModify.do?iagmt=${agmt4Page.tagmt.iagmt}" target="dialog" mask="true" width="810" height="530" rel="DHXYGL_XYBG" cssClass="add" value="变更" title="协议变更" /></li>
						   </c:if>
						   --%>
						   <c:if test="${agmt4Page.tagmt.agmtStatus == 'SUBMIT_CHANGE'}">
						   	 <li><posmall:authA cssClass="delete" target="ajaxTodo" href="${ctx}/cust/agmt/cancelAgmtModify.do?iagmt=${agmt4Page.tagmt.iagmt}"  rel="DHXYGL_SC" title="确认是否取消协议变更?" value="取消变更"></posmall:authA></li>
						   </c:if>
						</ul></div>
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