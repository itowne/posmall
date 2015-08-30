<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>
<form id="pagerForm" method="post" action="${ctx}/admin/cust/custCodeList.do">
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	
	<input type="hidden" name="custCode" value="${custCode}" />
	<input type="hidden" name="companyName" value="${companyName}" />
	<input type="hidden" name="custCodeStatus" value="${custCodeStatus}" />
</form>


<div class="pageHeader">
	<form id="pdtListForm" onsubmit="return navTabSearch(this);" action="${ctx}/admin/cust/custCodeList.do" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					注册码：<input type="text" name="custCode" value="${custCode}">
				</td>
				<td>
					公司名称：<input type="text" name="companyName" value="${companyName}">
				</td>
				<td>
					状态：<posmall:dictSelect selectName="custCodeStatus" dictType="cust_code_status" defaultValue="${custCodeStatus}"/>
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
			<li><posmall:authA authCode="KHMGL_SCZCM" cssClass="add" href="${ctx}/admin/cust/generateCustCode.do" rel="KHMGL_SCKHM" target="ajaxTodo" title="是否生成注册码？" value="生成注册码" /></li>
		</ul>
	</div>
	<table class="list" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="120">注册码</th>
				<th width="100">状态</th>
				<th width="250">公司名称</th>
				<th width="120">生成时间</th>
				<th width="120">修改时间</th>
				<th width="80">操作</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
		  <c:forEach var="tcustCode" items="${tcustCodeList}" >
			<tr>
				<td>&nbsp;${tcustCode.custCode}</td>
				<td align="center"><posmall:dict dictType="cust_code_status" dictCode="${tcustCode.custCodeStatus}" /></td>
				<td>&nbsp;${tcustCode.companyName}</td>
				<td align="center"><fmt:formatDate value="${tcustCode.genTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td align="center"><fmt:formatDate value="${tcustCode.updTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td></td>
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