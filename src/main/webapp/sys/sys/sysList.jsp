<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>

<form id="pagerForm" method="post" action="${ctx}/sys/sys/sysList.do">
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<input type="hidden" name="loginName" value="${loginName}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="${ctx}/sys/sys/sysList.do" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					用户名：<input type="text" name="loginName" value="${loginName}"/>
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
			<li><posmall:authA cssClass="add" href="${ctx}/sys/sys/sysAdd.do" target="dialog" mask="true" rel="yhglXZ" height="250" width="450" value="添加管理员" /></li>
		</ul>
	</div>
	<table class="list" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="120" align="center">用户名</th>
				<th width="190" align="center">操作</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="tsys" items="${tsysList}">
				<tr>
					<td align="left">
						&nbsp;
						${tsys.loginName}
					</td>
					<td>
						<div class="panelBar tablebtn">
							<ul class="toolBar">
								<li>
									<posmall:authA cssClass="delete" href="${ctx}/sys/sys/sysRemove.do?isys=${tsys.isys}" target="ajaxTodo" title="确定要删除吗?" mask="true" value="删除" />
								</li>
								<li>
									<posmall:authA cssClass="icon" href="${ctx}/sys/sys/resetPass.do?isys=${tsys.isys}" target="ajaxTodo" title="确定要重置密码吗？" mask="true" value="重置密码" />								
								</li>
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