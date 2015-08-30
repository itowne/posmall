<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%><div class="pageContent">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>
<form id="pagerForm" method="post" action="${ctx}/admin/renew/renewList.do">
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<input type="hidden" name="custNo" value="${tcustAppver.custNo}" />
	<input type="hidden" name="name" value="${tcustAppver.name}" />
	<input type="hidden" name="appver" value="${tcustAppver.appver}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="${ctx}/admin/custappver/custAppverList.do" method="post">
	<div class="searchBar">
        <table class="searchContent">
			<tr>
				<td>
					客户编号：
					<input type="text" name="custNo" value="${tcustAppver.custNo}" />
				</td>
				<td>
					公司名称：
					<input type="text" name="name" value="${tcustAppver.name}" />
				</td>
				<td>
					应用版本号：
					<input type="text" name="appver" value="${tcustAppver.appver}" />
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
			<li>
			   <a class="add" href="${ctx}/admin/custappver/toCustAppverAdd.do" target="dialog" width="460" height="170" mask="true" rel="YYBBGL_TJ"><span>添加</span></a></li>
			</li>
		</ul>
	</div>
	<table class="list" width="100%" layoutH="138">
		<thead>
			<tr>
			    <th width="120">客户编号</th>
			    <th width="120">公司名称</th>
			    <th width="120">应用版本号</th>
				<th width="160">操作</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
		  <c:forEach var="custAppver" items="${list}">
			<tr>
			    <td align="left">&nbsp;${custAppver.custNo}</td>
				<td align="left">&nbsp;${custAppver.name}</td>
				<td align="left">&nbsp;${custAppver.appver}</td>
				<td align="left">
					<div class="panelBar tablebtn">
						<ul class="toolBar">
						       <li><posmall:authA authCode="YYBBGL_XG" cssClass="edit" href="${ctx}/admin/custappver/toCustAppverMod.do?icustAppver=${custAppver.icustAppver}" target="dialog" mask="true" warn="" width="460" height="200" value="修改" /></li>
							   <li><posmall:authA authCode="YYBBGL_SC" cssClass="delete" href="${ctx}/admin/custappver/custAppverDel.do?icustAppver=${custAppver.icustAppver}" target="ajaxTodo" mask="true" title="是否确认删除" value="删除" /></li>
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