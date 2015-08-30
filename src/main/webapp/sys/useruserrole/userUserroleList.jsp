<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>
<form id="pagerForm" method="post" action="${ctx}/sys/useruserrole/userUserroleList.do">
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<input type="hidden" name="loginName" value="${loginName}" />
	<input type="hidden" name="name" value="${name}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="${ctx}/sys/useruserrole/userUserroleList.do" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					用户名：<input type="text" name="loginName" value="${loginName}"/>
				</td>
				<td>
					角色：<input type="text" name="name" value="${name}"/>
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
			<li><posmall:authA  cssClass="add" href="${ctx}/sys/useruserrole/userUserroleAdd.do" target="dialog" width="460" height="200" mask="true" rel="HTRYJSGLGLTJ" value="设置用户角色"/></li>
		</ul>
	</div>
	<table class="list" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="150" align="center">用户名</th>
				<th width="200" align="center">角色</th>
				<th width="200" align="center">操作</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
		  <c:forEach var="userUserrole" items="${userUserroleList}">
			<tr>
				<td align="left">&nbsp;${userUserrole[2]}</td>
				<td align="left">&nbsp;${userUserrole[3]}</td>
				<td align="center">
				  <div class="panelBar tablebtn">
						<ul class="toolBar">
					      <li><posmall:authA title="确定要删除吗?" cssClass="delete" href="${ctx}/sys/useruserrole/userUserroleDel.do?iuser=${userUserrole[0]}&iuserrole=${userUserrole[1]}" target="ajaxTodo"  mask="true"width="850" height="480" value="删除"></posmall:authA></li>
			              <li><posmall:authA title="修改"       cssClass="edit" href="${ctx}/sys/useruserrole/userUserroleMod.do?iuser=${userUserrole[0]}&iuserrole=${userUserrole[1]}" target="dialog" mask="true" width="460" height="200" rel="HTRYJSGLGLXG" value="修改"></posmall:authA></li>
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
