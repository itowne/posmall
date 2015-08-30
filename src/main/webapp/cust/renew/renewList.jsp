<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib uri="/posmall-tags" prefix="posmall" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<form id="pagerForm" method="post" action="${ctx}/cust/renew/renewList.do">
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	
	<input type="hidden" name="renewStatus" value="${condition.renewStatus}" />
	<input type="hidden" name="payStatus" value="${condition.payStatus}" />
	<input type="hidden" name="startTime" value='<fmt:formatDate pattern="yyyy-MM-dd" value="${startTime}"/>' />
	<input type="hidden" name="endTime" value='<fmt:formatDate pattern="yyyy-MM-dd" value="${endTime}"/>' />
</form>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="${ctx}/cust/renew/renewList.do" method="post">
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
    <div class="panelBar">
		<ul class="toolBar">
			<li><posmall:authA href="${ctx}/cust/renew/toUploadExcel.do" target="dialog" mask="true" width="810" height="530" rel="CPXB_CPXB" cssClass="add" value=" 续保  " title="第一步：上传续保数据文件" /></li>
		</ul>
	</div>
	<table class="list" width="100%" layoutH="138">
		<thead>
			<tr>
			    <th width="120">续保编号</th>
			    <th width="100">续保期限</th>
				<th width="100">续保状态</th>
				<th width="100">支付状态</th>
				<th width="140">续保申请时间</th>
				<th width="120">操作</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
		  <c:forEach var="renew" items="${trenewList}">
			<tr>
			    <td align="left">&nbsp;<posmall:authA href="${ctx}/cust/renew/renewDetail.do?irenew=${renew.irenew}" target="dialog" mask="true" title="详细信息" value="${renew.irenew}" width="850" height="480" /></td>
			    <td align="center">
						<c:if test="${renew.renewLife == 1}">一年</c:if>
						<c:if test="${renew.renewLife == 2}">两年</c:if>
						<c:if test="${renew.renewLife == 3}">三年</c:if>
				</td>
				<td align="center">&nbsp;<posmall:dict dictCode="${renew.renewStatus}" dictType="renew_status" /></td>
				<td align="center">&nbsp;<posmall:dict dictCode="${renew.payStatus}" dictType="pay_status" /></td>
				<td align="center"><fmt:formatDate value="${renew.renewTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
					<div class="panelBar tablebtn">
						<ul class="toolBar">
					       <c:if test="${renew.renewStatus == 'WAIT_AUDIT' || renew.renewStatus == 'AUDIT_NO_PASS'}">
							 <li><posmall:authA cssClass="delete" target="ajaxTodo" href="${ctx}/cust/renew/renewCacel.do?irenew=${renew.irenew}" rel="CPXB_SC" title="确认是否撤销?" value="撤销" /></li>
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
		<div class="pagination" targetType="navTab" totalCount="${totalCount}" numPerPage="${numPerPage}" pageNumShown="10" currentPage="${pageNum}"></div>
	</div>
</div>