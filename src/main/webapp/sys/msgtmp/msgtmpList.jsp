<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>
<form id="pagerForm" method="post" action="${ctx}/sys/msgtmp/msgtmpList.do">
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<input type="hidden" name="noteName" value="${noteName}" />
	<input type="hidden" name="tmyType" value="${tmyType}" />
</form>


<div class="pageHeader">
	<form id="msgtmpListForm" onsubmit="return navTabSearch(this);" action="${ctx}/sys/msgtmp/msgtmpList.do" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
				       模板名称：<input type="text" name="noteName" value="${noteName}">
				</td>
				<td>
				        模板类型：<posmall:dictSelect selectName="tmpType" dictType="msg_tmp_type" defaultValue="${tmpType}"></posmall:dictSelect>
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
			<li><posmall:authA cssClass="add" href="${ctx}/sys/msgtmp/toMsgTmpAdd.do" target="dialog" mask="true" rel="XXMBGL_XZ"  width="650" height="510" value="新增"></posmall:authA></li>
		</ul>
	</div>
	<table class="list" width="100%" layoutH="138">
		<thead>
			<tr>
				<th align="center" width="70">模板类型</th>
				<th align="center" width="105">模板代码</th>
				<th align="center" width="190">模板名称</th>
				<th align="center" width="450">模板内容</th>
				<th align="center" width="150">更新时间</th>
				<th align="center" width="245">操作</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
		  <c:forEach var="list" items="${tmsgTmpList}" >
			<tr>
				<td align="center">&nbsp;<posmall:dict dictType="msg_tmp_type" dictCode="${list.tmpType}" ></posmall:dict></td>
				<td align="center">&nbsp;${list.tmpCode}</td>
				<td align="center"><span style="display:inline-block;width:180px;white-space:nowrap;text-overflow:ellipsis;-o-text-overflow:ellipsis;overflow: hidden;">&nbsp;${list.noteName}</span></td>
				<td>&nbsp;${list.content}</td>
				<td align="center">&nbsp;<fmt:formatDate value="${list.updTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td align="center">
					<div class="panelBar tablebtn">
						<ul class="toolBar">
							<li><posmall:authA cssClass="edit" href="${ctx}/sys/msgtmp/toMsgTmpModify.do?imsgtmp=${list.imsgTmp}" target="dialog" mask="true" rel="XXMBGL_XG"   width="650" height="510" value="修改"></posmall:authA></li>
							<li><posmall:authA cssClass="icon" href="${ctx}/sys/msgtmp/msgtmpDetail.do?imsgtmp=${list.imsgTmp}" target="dialog" mask="true" rel="XXMBGL_XX"   width="600" height="380" value="明细"></posmall:authA></li>
							<li><posmall:authA cssClass="delete" href="${ctx}/sys/msgtmp/msgtmpRemove.do?imsgtmp=${list.imsgTmp}"  target="ajaxTodo"  mask="true" rel="XXMBGL_SC" title="是否确认删除?" value="删除"></posmall:authA></li>
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