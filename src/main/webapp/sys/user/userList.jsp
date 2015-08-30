<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>

<form id="pagerForm" method="post" action="${ctx}/sys/user/userList.do">
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<input type="hidden" name="loginName" value="${loginName}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="${ctx}/sys/user/userList.do" method="post">
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
			<li><a class="add" href="${ctx}/sys/user/userAdd.do" target="dialog" mask="true" rel="yhglXZ"><span>添加</span></a></li>
		</ul>
	</div>
	<table class="list" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="100" align="center">用户名</th>
				<th width="70" align="center">姓名</th>
				<th width="120" align="center">部门</th>
				<th width="240" align="center">邮箱</th>
				<th width="190" align="center">操作</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="tuser" items="${tuserList}">
				<tr>
					<td align="left">
						&nbsp;
						<a href="${ctx}/sys/user/userDetail.do?iuser=${tuser.iuser}" target="dialog" title="用户信息详情" width="460" height="300" mask="true" rel="yhglMx"><span>${tuser.loginName}</span></a>
					</td>
					<td align="left">
						&nbsp;${tuser.tuserSub.name}
					</td>
					<td align="left">
						&nbsp;${tuser.tuserSub.depart}
					</td>
					<td align="left">
						&nbsp;${tuser.tuserSub.email}
					</td>
					<td>
						<div class="panelBar tablebtn">
							<ul class="toolBar">
								<li><a class="edit" href="${ctx}/sys/user/userModify.do?iuser=${tuser.iuser}" target="dialog" title="用户信息修改" width="460" height="300" mask="true" rel="yhglXg"><span>修改</span></a>
								</li><li><a class="delete" href="${ctx}/sys/user/userRemove.do?iuser=${tuser.iuser}" target="ajaxTodo" title="确定要删除吗?" mask="true"><span>删除</span></a>
								</li><li><a class="icon" href="${ctx}/sys/user/resetPass.do?iuser=${tuser.iuser}" target="ajaxTodo" title="确定要重置吗?" mask="true"><span>重置密码</span></a>
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
