<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%><div class="pageContent">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>
<form id="pagerForm" method="post" action="${ctx}/admin/renew/renewList.do">
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	
	<input type="hidden" name="renewStatus" value="${condition.renewStatus}" />
	<input type="hidden" name="payStatus" value="${condition.payStatus}" />
	<input type="hidden" name="custName" value="${condition.custName}" />
	<input type="hidden" name="startTime" value='<fmt:formatDate pattern="yyyy-MM-dd" value="${startTime}"/>' />
	<input type="hidden" name="endTime" value='<fmt:formatDate pattern="yyyy-MM-dd" value="${endTime}"/>' />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="${ctx}/admin/renew/renewList.do" method="post">
	<div class="searchBar">
        <table class="searchContent">
			<tr>
				<td>
					续保状态：
					<posmall:dictSelect selectName="renewStatus" defaultValue="${condition.renewStatus}" dictType="renew_status" />
				</td>
				<td>
					支付状态：
					<posmall:dictSelect selectName="payStatus" defaultValue="${condition.payStatus}" dictType="pay_status" />
				</td>
				<td>
					客户名称：<input type="text" name="custName" value="${condition.custName}" />
				</td>
				<td colspan="3"> 
			     	申请日期：<input class="date" readonly="readonly" name="startTime" value='<fmt:formatDate pattern="yyyy-MM-dd" value="${startTime}"/>'/> - 
			     	<input class="date" readonly="readonly" name="endTime" value='<fmt:formatDate pattern="yyyy-MM-dd" value="${endTime}"/>'/>
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
			    <th width="120">续保编号</th>
			    <th width="80">续保期限</th>
			    <th width="100">总金额</th>
			    <th width="180">客户名称</th>
			    <th width="140">续保申请时间</th>
				<th width="80">续保状态</th>
				<th width="80">支付状态</th>
				<th width="160">操作</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
		  <c:forEach var="renew" items="${list}">
			<tr>
			    <td align="left">&nbsp;<a href="${ctx}/admin/renew/renewDetail.do?irenew=${renew.irenew}" target="dialog" mask="true" title="详细信息" width="850" height="480">&nbsp;${renew.irenew}</a></td>
			    <td align="left">&nbsp;
						<c:if test="${renew.renewLife == 1}">一年</c:if>
						<c:if test="${renew.renewLife == 2}">两年</c:if>
						<c:if test="${renew.renewLife == 3}">三年</c:if>
				</td>
				<td align="right">&nbsp;${renew.renewAmt}</td>
				<td align="left">&nbsp;${renew.custName}</td>
				<td align="center">&nbsp;<fmt:formatDate value="${renew.renewTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td align="left">&nbsp;<posmall:dict dictCode="${renew.renewStatus}" dictType="renew_status" /></td>
				<td align="left">&nbsp;<posmall:dict dictCode="${renew.payStatus}" dictType="pay_status" /></td>
				<td align="left">
					<div class="panelBar tablebtn">
						<ul class="toolBar">
						    <c:if test='${renew.payStatus == "HAVE_PAY" && renew.renewStatus == "WAIT_AUDIT" }'>
						       <li><posmall:authA authCode="XBGL_SH" cssClass="icon" href="${ctx}/admin/renew/toRenewAudit.do?irenew=${renew.irenew}" target="dialog" mask="true" warn="" width="850" height="480" value="审核" /></li>
						    </c:if>
						    <c:if test='${renew.renewStatus == "WAIT_AUDIT" && (renew.payStatus == "WAIT_PAY"||renew.payStatus == "NO_PASS")}'>
							   <li><posmall:authA authCode="XBGL_CX" cssClass="delete" href="${ctx}/admin/renew/renewCancel.do?irenew=${renew.irenew}" target="ajaxTodo" mask="true" title="是否确认撤销订单" value="撤销" /></li>
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
		<div class="pagination" targetType="navTab" totalCount="${totalCount}" numPerPage="${numPerPage}" pageNumShown="10" currentPage="${pageNum}"></div>
	</div>
</div>