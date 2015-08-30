<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>

<form id="pagerForm" method="post" action="${ctx}/sys/cust/custList.do">
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<input type="hidden" name="loginName" value="${loginName}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="${ctx}/sys/cust/custList.do" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					客户名称：<input type="text" name="loginName" value="${loginName}"/>
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
		</ul>
	</div>
	<table class="list" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="100" align="center">客户名称</th>
				<th width="150" align="center">单位名称</th>
				<th width="100" align="center">联系人</th>
				<th width="100" align="center">移动电话</th>
				<th width="80" align="center">操作</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="tcust" items="${tcustList}">
				<tr>
					<td align="left">
						&nbsp;
						<posmall:authA href="${ctx}/sys/cust/custDetail.do?icust=${tcust.icust}" target="dialog" title="客户信息详情" width="460" height="300" mask="true" rel="KHJSGL_XQ" value="${tcust.loginName}"></posmall:authA>
					</td>
					<td align="left">
						&nbsp;${tcust.tcustReg.name}
					</td>
					<td align="left">
						&nbsp;${tcust.tcustReg.contactName}
					</td>
					<td align="left">
						&nbsp;${tcust.tcustReg.mobile}
					</td>
					<td>
						<div class="panelBar tablebtn">
							<ul class="toolBar">
								<li>
									<posmall:authA cssClass="icon" rel="KHXXGL_CZ" href="${ctx}/sys/cust/resetPass.do?icust=${tcust.icust}" target="ajaxTodo" title="确定要重置吗?" mask="true" value="重置密码"></posmall:authA>
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
