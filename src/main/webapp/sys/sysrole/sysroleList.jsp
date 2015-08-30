<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>
<form id="pagerForm" method="post" action="${ctx}/sys/sysrole/sysroleList.do">
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<input type="hidden" name="name" value="${name}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="${ctx}/sys/sysrole/sysroleList.do" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					名          称：<input type="text" name="name" value="${name}"/>
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
			<li><posmall:authA cssClass="add" href="${ctx}/sys/sysrole/sysroleAdd.do" target="dialog" width="860" height="500" mask="true" rel="GLYJSGLTJ" value="添加"></posmall:authA></li>
		</ul>
	</div>
	<table class="list" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="200" align="center">角色名称</th>
				<th width="220" align="center">备注</th>
				<th width="200" align="center">操作</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
		  <c:forEach var="role" items="${roleList}">
			<tr>
				<td align="left">
					&nbsp;
				    <posmall:authA title="角色详情"    cssClass="edit" href="${ctx}/sys/sysrole/sysroleDetail.do?isysrole=${role.isysrole}" target="dialog"  width="860" height="500" mask="true" rel="GLYJSGLYQ" value="${role.name}"></posmall:authA>
				</td>
				<td align="left">&nbsp;${role.memo}</td>
				<td align="center">
				  <div class="panelBar tablebtn">
						<ul class="toolBar">
					      <li><posmall:authA title="确定要删除吗?" cssClass="delete" href="${ctx}/sys/sysrole/sysroleDel.do?isysrole=${role.isysrole}" target="ajaxTodo"  mask="true" value="删除"></posmall:authA></li>
			              <li><posmall:authA title="修改"       cssClass="edit" href="${ctx}/sys/sysrole/sysroleMod.do?isysrole=${role.isysrole}" target="dialog" mask="true" width="860" height="500" rel="GLYJSGLXG" value="修改"></posmall:authA></li>
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
