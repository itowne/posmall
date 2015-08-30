<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>

<form id="pagerForm" method="post" action="${ctx}/sys/log/logList.do">
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<input type="hidden" name="logType" value="${logType}" />
	<input type="hidden" name="operType" value="${operType}" />
	<input type="hidden" name="beginTime" value="${beginTime}" />
	<input type="hidden" name="endTime" value="${endTime}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="${ctx}/sys/log/logList.do" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
			    <td>
					起始时间：<input type="text" name="beginTime" class="date" readonly="readonly" value='<fmt:formatDate pattern="yyyy-MM-dd" value="${beginTime}"/>'/>
				</td>
				<td>
					结束时间：<input type="text" name="endTime" class="date" readonly="readonly" value='<fmt:formatDate pattern="yyyy-MM-dd" value="${endTime}"/>' />
				</td>
				<td>
					日志类型：
					<posmall:dictSelect selectName="logType" dictType="log_type" defaultValue="${logType}"/>
				</td>
				<td>
					操作类型：
					<posmall:dictSelect selectName="operType" dictType="oper_type" defaultValue="${operType}"/>
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
		   <tr> <th width="160" align="center">生成时间</th>
		        <th width="100" align="center">操作人</th>
				<th width="70" align="center">日志类型</th>
				<th width="120" align="center">操作类型</th>
				<th width="200" align="center">备注</th>
				<th align="center" width="200">修改前数据</th>
				<th align="center" width="200">修改后数据</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
		  <c:forEach var="log" items="${logList}">
			<tr>
			    <td align="left">&nbsp;<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${log.genTime}"/></td>
			    <td align="left">&nbsp;
			      <c:choose>
			         <c:when test="${log.logType=='SYS'}">
			            <posmall:dict dictType="sys_loginName" dictCode="${log.ifk}"></posmall:dict>
			         </c:when>
			         <c:when test="${log.logType=='USER'}">
			            <posmall:dict dictType="user_loginName" dictCode="${log.ifk}"></posmall:dict>
			         </c:when>
			         <c:otherwise>
			            <posmall:dict dictType="cust_loginName" dictCode="${log.ifk}"></posmall:dict>
			         </c:otherwise>
			      </c:choose>
			        
			    </td>
				<td align="left">&nbsp;<posmall:dict dictCode="${log.logType}" dictType="log_type" /></td>
				<td align="left">&nbsp;<posmall:dict dictCode="${log.operType}" dictType="oper_type" /></td>
				<td align="left">&nbsp;${log.memo}</td>
				<td align="left">&nbsp;<a title="日志详情"  href="${ctx}/sys/log/logDetail.do?ilog=${log.ilog}" target="dialog"  width="460" height="500" mask="true" rel="logMx"><span style="display:inline-block;width:200px;white-space:nowrap;text-overflow:ellipsis;-o-text-overflow:ellipsis;overflow: hidden;">${log.preData}</span></a></td>
				<td align="left">&nbsp;<a title="日志详情"  href="${ctx}/sys/log/logDetail.do?ilog=${log.ilog}" target="dialog"  width="460" height="500" mask="true" rel="logMx"><span style="display:inline-block;width:200px;white-space:nowrap;text-overflow:ellipsis;-o-text-overflow:ellipsis;overflow: hidden;">${log.data}</span></a></td>
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
