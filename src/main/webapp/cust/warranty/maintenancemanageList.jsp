<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%><div class="pageContent">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>
<form id="pagerForm" method="post" action="${ctx}/cust/warranty/maintenancemanageList.do">
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<input type="hidden" name="seqNo" value="${seqNo}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="${ctx}/cust/warranty/maintenancemanageList.do" method="post">
	<div class="searchBar">
        <table class="searchContent">
			<tr>
				<td>
					产品序列号：<input type="text" name="seqNo" value="${seqNo}"/>
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
				<th width="120">保修期起始日期</th>
				<th width="120">保修期结束日期</th>
				<th width="80">报修次数</th>
				<th width="120">最后修复日期</th>
				<th width="80">是否有效</th>
				<th width="80">操作</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
		  <c:forEach var="em" items="${list}">
			<tr>
			    <td align="left">&nbsp;${em.sn}</td>
				<td align="left">&nbsp;${em.ph}</td>
				<td align="left">&nbsp;${em.pm}</td>
				<td align="center"><fmt:formatDate pattern="yyyy-MM-dd" value="${em.lifeStartTime}"/></td>
				<td align="center"><fmt:formatDate pattern="yyyy-MM-dd" value="${em.warrantyPeriod}"/></td>
				<td align="right">${em.repairNum}次&nbsp;</td>
				<td align="center"><fmt:formatDate pattern="yyyy-MM-dd" value="${em.lastRepairedDate}"/></td>
				<td align="center"><posmall:dict dictType="valid_status" dictCode="${em.validStatus}"/></td>
				<td>
					<div class="panelBar tablebtn">
						<ul class="toolBar">
						    <li><posmall:authA cssClass="edit" href="${ctx}/cust/warranty/detail.do?id=${em.ierpMaintenance}" target="dialog" mask="true" width="840" height="420" rel="BXCX_XQ" value="详情" /></li>
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