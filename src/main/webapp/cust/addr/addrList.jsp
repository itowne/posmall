<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/posmall-tags" prefix="posmall"%>
<form id="pagerForm" method="post" action="${ctx}/cust/addr/addrList.do">
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<input type="hidden" name="name" value="${name}" />
</form>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="${ctx}/cust/addr/addrList.do" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>收货人：<input type="text" name="name" value="${name}" />
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">查询</button>
							</div>
						</div></li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><posmall:authA cssClass="add"
					href="${ctx}/cust/addr/toAdd.do" target="dialog" mask="true"
					title="收货地址填写" width="720" height="480" rel="CYSHDZGL_XZ"
					value="新增" /></li>
		</ul>
	</div>
	<table class="list" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="100" align="center">收货人</th>
				<th width="340" align="center">所在地区</th>
				<th width="380" align="center">街道地址</th>
				<th width="60" align="center">邮编</th>
				<th width="60" align="center">手机</th>
				<th width="60" align="center">电话</th>
				<th width="80" align="center">操作</th>
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
					<td>&nbsp;${taddr.mobile}</td>
					<td>&nbsp;${taddr.tel}</td>
					<td align="center"><posmall:authA target="dialog"
							href="${ctx}/cust/addr/toModify.do?iaddr=${taddr.iaddr}"
							width="720" height="480" mask="true" rel="CYSHDZGL_XG" value="修改" />
						<posmall:authA target="ajaxTodo"
							href="${ctx}/cust/addr/delete.do?id=${taddr.iaddr}"
							title="确定要删除吗?" rel="CYSHDZGL_SC" value="删除" /></td>
					<td></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span> <select class="combox" name="numPerPage"
				onchange="navTabPageBreak({numPerPage:this.value})">
				<option <c:if test="${numPerPage==10}">selected="selected"</c:if>
					value="10">10</option>
				<option <c:if test="${numPerPage==20}">selected="selected"</c:if>
					value="20">20</option>
				<option <c:if test="${numPerPage==50}">selected="selected"</c:if>
					value="50">50</option>
				<option <c:if test="${numPerPage==100}">selected="selected"</c:if>
					value="100">100</option>
			</select> <span>条，共${totalCount}条</span>
		</div>

		<div class="pagination" targetType="navTab" totalCount="${totalCount}"
			numPerPage="${numPerPage}" pageNumShown="10" currentPage="${pageNum}"></div>

	</div>
</div>
