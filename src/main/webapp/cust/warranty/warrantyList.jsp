<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%><div class="pageContent">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>
<form id="pagerForm" method="post" action="${ctx}/cust/warranty/warrantyList.do">
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	
	<input type="hidden" name="seqNo" value="${condition.seqNo}" />
	<input type="hidden" name="warrantyStatus" value="${condition.warrantyStatus}" />
	<input type="hidden" name="startTime" value='<fmt:formatDate pattern="yyyy-MM-dd" value="${startTime}"/>' />
	<input type="hidden" name="endTime" value='<fmt:formatDate pattern="yyyy-MM-dd" value="${endTime}"/>' />
</form>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="${ctx}/cust/warranty/warrantyList.do" method="post">
	<div class="searchBar">
        <table class="searchContent">
			<tr>
				<td>
					产品序列号：<input type="text" name="seqNo" value="${condition.seqNo}" />
				</td>
				<td>
					状态：
					<posmall:dictSelect selectName="warrantyStatus" defaultValue="${condition.warrantyStatus}" dictType="warranty_status" />
				</td>
				<td colspan="3"> 
			     	报修日期：<input class="date" readonly="readonly" name="startTime" value='<fmt:formatDate pattern="yyyy-MM-dd" value="${startTime}"/>'/> - 
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
			<li><posmall:authA href="${ctx}/cust/warranty/toAdd.do" target="dialog" mask="true" width="800" height="420" rel="CPBX_CPBX" cssClass="add" value=" 产品报修 " /></li>
		</ul>
	</div>
	<table class="list" width="100%" layoutH="138">
		<thead>
			<tr>
			    <th width="120">受理编号</th>
			    <th width="120">产品序列号</th>
				<th width="120">产品型号</th>
				<th width="130">报修时间</th>
				<th width="130">受理时间</th>
				<th width="130">修复时间</th>
				<th width="80">状态</th>
				<th width="120">操作</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
		  <c:forEach var="warranty" items="${list}">
			<tr>
			    <td>&nbsp;${warranty.iwarranty}</td>
			    <td>&nbsp;${warranty.seqNo}</td>
			    <td>&nbsp;${warranty.pdtNo}</td>
				<td align="center"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${warranty.warrantyTime}"/></td>
				<td align="center"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${warranty.acceptTime}"/></td>
				<td align="center"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${warranty.repairedTime}"/></td>
				<td align="center"><posmall:dict dictCode="${warranty.warrantyStatus}" dictType="warranty_status" /></td>
				
				<td>
					<div class="panelBar tablebtn">
						<ul class="toolBar">
							 <li><a class="edit" href="${ctx}/cust/warranty/warrantyDetail.do?iwarranty=${warranty.iwarranty}" target="dialog" mask="true" width="800" height="420" rel="CPBX_XQ"><span>详情</span></a></li>
					       <c:if test="${warranty.warrantyStatus == 'HAVE_SUBMIT'}">
							 <li><posmall:authA cssClass="delete" target="ajaxTodo" href="${ctx}/cust/warranty/warrantyCacel.do?iwarranty=${warranty.iwarranty}" title="确认是否撤销?" value="撤销" /></li>
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
