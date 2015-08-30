<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>

<form id="pagerForm" method="post" action="${ctx}/sys/sysparam/sysparamList.do">
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<input type="hidden" name="name" value="${name}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="${ctx}/sys/sysparam/sysparamList.do" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					参数名称：<input type="text" name="name" value="${name}"/>
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
				<th width="150" align="center">参数名称</th>
				<th width="120" align="center">参数类型</th>
				<th width="140" align="center">参数编码</th>
				<th width="70" align="center">参数数值</th>
				<th width="140" align="center">备注</th>
				<th width="60" align="center">操作</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="tsysParam" items="${tsysParamList}">
				<tr>
					<td align="left">
						&nbsp;
						${tsysParam.name}
					</td>
					<td align="left">
						&nbsp;${tsysParam.sysParamType}
					</td>
					<td align="left">
						&nbsp;${tsysParam.code}
					</td>
					<td align="left">
						&nbsp;${tsysParam.value}
					</td>
					<td align="left">
						&nbsp;${tsysParam.memo}
					</td>
					<td>
						<div class="panelBar tablebtn">
							<ul class="toolBar">
								<li>
									<posmall:authA cssClass="edit" href="${ctx}/sys/sysparam/sysparamModify.do?isysParam=${tsysParam.isysParam}" target="dialog" title="参数信息修改" width="460" height="350" mask="true" rel="csglXg" value="修改"></posmall:authA>
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
