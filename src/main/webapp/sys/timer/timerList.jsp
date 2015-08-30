
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/posmall-tags" prefix="posmall"%>
<div class="pageContent">
	<table class="list" width="100%" layoutH="138">
		<thead>
			<tr>
				<th align="center" width="150">任务名称</th>
				<th align="center" width="80">执行时间</th>
				<th align="center" width="140">更新时间</th>
				<th align="center" width="60">执行结果</th>
				<th align="center" width="60">状态</th>
				<th align="center" width="40">次数</th>
				<th align="center" width="280">错误信息</th>
				<th align="center" width="190">操作</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="taskConf" items="${taskConfList}">
				<tr>
					<td align="left">&nbsp;${taskConf.taskName}</td>
					<td align="center"><fmt:formatDate pattern="yyyy-MM-dd"
							value="${taskConf.lastDate}" /></td>
					<td align="center"><fmt:formatDate
							pattern="yyyy-MM-dd HH:mm:ss" value="${taskConf.updTime}" /></td>
					<td align="center">&nbsp;${taskConf.endStat}</td>
					<td align="center">&nbsp;${taskConf.enabledFlag}</td>
					<td align="center">${taskConf.count}&nbsp;</td>
					<td align="left">&nbsp;${taskConf.errMsg}</td>
					<td>
						<div class="panelBar tablebtn">
							<ul class="toolBar">
								<c:choose>
									<c:when test="${taskConf.service == 'maintenanceInfoSyncTask'}">
										<li><posmall:authA title="确定要执行吗?" cssClass="delete"
												target="dialog" width="600" height="340" mask="true"
												href="${ctx}/sys/timer/toTaskManager.do?service=${taskConf.service}"
												value="手动执行"></posmall:authA></li>
									</c:when>
									<c:otherwise>
										<li><posmall:authA title="确定要执行吗?" cssClass="delete"
												target="ajaxTodo" mask="true"
												href="${ctx}/sys/timer/taskManager.do?service=${taskConf.service}"
												value="手动执行"></posmall:authA></li>
									</c:otherwise>

								</c:choose>
								<li><posmall:authA cssClass="edit" target="dialog"
										mask="true" width="600" height="340"
										href="${ctx}/sys/timer/toModify.do?itask=${taskConf.itask}"
										value="修改"></posmall:authA></li>
								<li><posmall:authA cssClass="icon" target="dialog"
										mask="true" width="1000" height="500"
										href="${ctx}/sys/timer/toDetail.do?itask=${taskConf.itask}"
										value="明细"></posmall:authA></li>
							</ul>
						</div>
					</td>
					<td></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
