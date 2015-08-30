<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>
<form id="pagerForm" method="post" action="${ctx}/sys/notifycfg/notifycfgList.do">
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<input type="hidden" name="userName" value="${userName}" />
	<input type="hidden" name="notifyType" value="${notifyType}" />
</form>


<div class="pageHeader">
	<form id="notifycfgListForm" onsubmit="return navTabSearch(this);" action="${ctx}/sys/notifycfg/notifycfgList.do" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
				       用户名称：<input type="text" name="userName" value="${userName}">
				</td>
				<td>
				         通知类型：<posmall:dictSelect selectName="notifyType" dictType="notify_type" defaultValue="${notifyType}"></posmall:dictSelect>
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
			<li><posmall:authA cssClass="add" href="${ctx}/sys/notifycfg/toNotifycfgAdd.do" target="dialog" mask="true" rel="YJTZRGL_XZ"  width="600" height="380" value="新增"></posmall:authA></li>
		</ul>
	</div>
	<table class="list" width="100%" layoutH="138">
		<thead>
			<tr>
				<th align="center" width="200">通知类型</th>
				<th align="center" width="80">用户编号</th>
				<th align="center" width="120">用户名称</th>
				<th align="center" width="300">说明</th>
				<th align="center" width="120">更新时间</th>
				<th align="center" width="180">操作</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
		  <c:forEach var="list" items="${tnotifyCfgList}" >
			<tr>
				<td>&nbsp;<posmall:dict dictType="notify_type" dictCode="${list.notifyType}" ></posmall:dict></td>
				<td>&nbsp;${list.iuser}</td>
				<td>&nbsp;${list.userName}</td>
				<td>&nbsp;${list.memo}</td>
				<td>&nbsp;<fmt:formatDate value="${list.updTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
					<div class="panelBar tablebtn">
						<ul class="toolBar">
							<li><posmall:authA cssClass="edit" href="${ctx}/sys/notifycfg/toNotifycfgModify.do?inotifycfg=${list.inotifyCfg}" target="dialog" mask="true" rel="YJTZRGL_XG"   width="600" height="380" value="修改"></posmall:authA></li>
							<li><posmall:authA cssClass="icon" href="${ctx}/sys/notifycfg/notifycfgDetail.do?inotifycfg=${list.inotifyCfg}" target="dialog" mask="true" rel="YJTZRGL_XX"   width="600" height="380" value="明细"></posmall:authA></li>
							<li><posmall:authA cssClass="delete" href="${ctx}/sys/notifycfg/notifycfgRemove.do?inotifycfg=${list.inotifyCfg}"  target="ajaxTodo"  mask="true" rel="YJTZRGL_SC" title="是否确认删除?" value="删除"></posmall:authA></li>
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