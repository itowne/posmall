
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<form id="pagerForm" method="post" action="${ctx}/sys/timer/toDetail.do">
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<input type="hidden" name="itask" value="${itask}" />
</form>
<div class="pageContent">
	<form method="post" action="" class="pageForm required-validate"
		onsubmit="return validateCallback(this, navTabAjaxDone);">
		<table class="list" width="100%" layoutH="68">
			<thead>
				<tr>
					<th align="center" width="160">任务名称</th>
					<th align="center" width="140">起始时间</th>
					<th align="center" width="140">结束时间</th>
					<th align="center" width="60">执行用户</th>
					<th align="center" width="60">执行结果</th>
					<th align="center" width="60">执行类型</th>
					<th align="center" width="320">错误信息</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="taskLog" items="${taskLogList}">
					<tr>
						<td align="left">&nbsp;${taskLog.taskName}</td>
						<td align="center"><fmt:formatDate
								pattern='yyyy-MM-dd HH:mm:ss' value='${taskLog.startTime}' /></td>
						<td align="center"><fmt:formatDate
								pattern='yyyy-MM-dd HH:mm:ss' value='${taskLog.endTime}' /></td>
						<td align="center">&nbsp;${taskLog.userName}</td>
						<td align="center">&nbsp;${taskLog.status}</td>
						<td align="center">&nbsp;${taskLog.execType}</td>
						<td align="left">&nbsp;${taskLog.errMsg}</td>
						<td></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="dialogPageBreak({numPerPage:this.value})">
				<option <c:if test="${numPerPage==10}">selected="selected"</c:if> value="10">10</option>
				<option <c:if test="${numPerPage==20}">selected="selected"</c:if> value="20">20</option>
				<option <c:if test="${numPerPage==50}">selected="selected"</c:if> value="50">50</option>
				<option <c:if test="${numPerPage==100}">selected="selected"</c:if> value="100">100</option>
			</select>
			<span>条，共${totalCount}条</span>
		</div>
		
		<div class="pagination" targetType="dialog" totalCount="${totalCount}" numPerPage="${numPerPage}" pageNumShown="10" currentPage="${pageNum}"></div>

	</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">
								&nbsp;&nbsp;关&nbsp;&nbsp;&nbsp;&nbsp;闭&nbsp;&nbsp;</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>
