<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>
<form id="pagerForm" method="post" action="${ctx}/cust/addr/addrList.do">
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
</form>

<div class="pageContent">
	<table class="list" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="100" align="center">收货人</th>
				<th width="220" align="center">所在地区</th>
				<th width="220" align="center">街道地址</th>
				<th width="80" align="center">邮编</th>
				<th width="200" align="center">电话/手机</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${taddrList}" var="taddr">
			<tr>
				<td>&nbsp;${taddr.name}</td>
				<td>&nbsp;${taddr.province}&nbsp;&nbsp;${taddr.city}&nbsp;&nbsp;${taddr.county}</td>
				<td>&nbsp;${taddr.area}</td>
				<td>&nbsp;${taddr.postalCode}</td>
				<td>&nbsp;
					${taddr.mobile}
					<c:if test="${taddr.mobile != null && taddr.mobile != '' && taddr.tel != null && taddr.tel != ''}">&nbsp;&nbsp;&nbsp;</c:if>
					${taddr.tel}
				</td>
				<td>
					<a class="btnSelect" href="javascript:$.bringBack({iaddr:'${taddr.iaddr}', name:'${taddr.name}', province:'${taddr.province}', city:'${taddr.city}', county:'${taddr.county}', area:'${taddr.area}', postalCode:'${taddr.postalCode}', mobile:'${taddr.mobile}', tel:'${taddr.tel}'})" title="查找带回">选择</a>
				</td>
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
