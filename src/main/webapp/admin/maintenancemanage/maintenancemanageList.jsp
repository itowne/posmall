<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%><div class="pageContent">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>
<form id="pagerForm" method="post" action="${ctx}/admin/maintenancemanage/maintenancemanageList.do">
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	
	<input type="hidden" name="sn" value="${sn}" />
	<input type="hidden" name="custName" value="${custName}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="${ctx}/admin/maintenancemanage/maintenancemanageList.do" method="post">
	<div class="searchBar">
        <table class="searchContent">
			<tr>
				<td>
					产品序列号：<input type="text" name="sn" value="${sn}"/>
				</td>
				<td>
					客户名称：<input type="text" name="custName" value="${custName}"/>
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
			    <th width="120">产品序列号</th>
				<th width="120">产品型号</th>
				<th width="120">产品名称</th>
				<th width="80">购买日期</th>
				<th width="80">保修期限</th>
				<th width="80">报修次数</th>
				<th width="80">最后修复日期</th>
				<th width="120">客户名称</th>
				<th width="80">是否有效</th>
				<th width="80">操作</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
		  <c:forEach var="em" items="${list}">
			<tr>
			    <td align="left">&nbsp;${em[4]}</td>
				<td align="left">&nbsp;${em[5]}</td>
				<td align="left">&nbsp;${em[6]}</td>
				<td align="left">&nbsp;<fmt:formatDate pattern="yyyy-MM-dd" value="${em[8]}"/></td>
				<td align="left">&nbsp;<fmt:formatDate pattern="yyyy-MM-dd" value="${em[10]}"/></td>
				<td align="right">${em[12]}次&nbsp;</td>
				<td align="left">&nbsp;<fmt:formatDate pattern="yyyy-MM-dd" value="${em[11]}"/></td>
				<td align="left">&nbsp;${em[17]}</td>
				<td align="center"><posmall:dict dictType="valid_status" dictCode="${em[15]}"/></td>
				<td>
					<div class="panelBar tablebtn">
						<ul class="toolBar">
						    <li><posmall:authA cssClass="edit" href="${ctx}/admin/maintenancemanage/detail.do?id=${em[0]}" target="dialog" mask="true" width="840" height="420" rel="BXCX_XQ" value="详情" /></li>
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