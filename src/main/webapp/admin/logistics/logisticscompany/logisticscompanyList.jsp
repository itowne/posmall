<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>
<form id="pagerForm" method="post" action="logistics/logisticscompany/logisticscompanyList.do">
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<input type="hidden" name="name" value="${name}" />
	<input type="hidden" name="fullname" value="${fullname}" />
	<input type="hidden" name="feeFlag" value="${feeFlag}" />
	<input type="hidden" name="logisticsStatus" value="${logisticsStatus}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="${ctx}/admin/logistics/logisticscompany/logisticscompanyList.do" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					<label>公司简称：</label><input type="text" name="name" value="${name}">
				</td>
				<td>
					<label>状态：</label>
					<posmall:dictSelect selectName="logisticsStatus" dictType="logistics_status" defaultValue="${logisticsStatus}"></posmall:dictSelect>
				</td>
				<td>
					<label>是否收费：</label>
					<posmall:dictSelect selectName="feeFlag" dictType="yes_no_type" defaultValue="${feeFlag}"></posmall:dictSelect>
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
<div class="pageContent"  layoutH="68">
    <div class="panelBar">
		<ul class="toolBar">
			<li><posmall:authA cssClass="add" href="${ctx}/admin/logistics/logisticscompany/tologisticscompanyAdd.do" target="dialog" mask="true"  width="550" height="380" value="新增"></posmall:authA></li>
		</ul>
	</div>
	<table class="list" width="100%" layoutH="138">
		<thead>
			<tr>			
				<th width="80">物流简称</th>
				<th width="120">物流全称</th>
				<th width="80">时效</th>
				<th width="80">是否收流</th>
				<th width="80">单价</th>
				<th width="80">状态</th>
				<th width="150">添加时间</th>
				<th width="150">更新时间</th>
				<th width="60">操作</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
		  <c:forEach var="list" items="${tlogisticsComList}" >
			<tr>
				<td>&nbsp;
					<posmall:authA href="${ctx}/admin/logistics/logisticscompany/tologisticscompanyDetail.do?ilogisticsCom=${list.ilogisticsCom}" target="dialog" mask="true" rel="WLGSGL_XXXX" title="详细信息" width="450" height="380" value="${list.name}"></posmall:authA>
				</td>
				<td>&nbsp;${list.fullname}</td>
				<td align="right">${list.aging}&nbsp;</td>
				<td align="right"><posmall:dict dictType="yes_no_type" dictCode="${list.feeFlag}"/>&nbsp;</td>
				<td align="right">${list.price}元&nbsp;</td>
				<td>&nbsp;<posmall:dict dictType="logistics_status" dictCode="${list.logisticsStatus}" ></posmall:dict></td>
				<td>&nbsp;<fmt:formatDate value="${list.genTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>&nbsp;<fmt:formatDate value="${list.updTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
					<div class="panelBar tablebtn">
						<ul class="toolBar">
							<li><posmall:authA cssClass="edit" href="${ctx}/admin/logistics/logisticscompany/tologisticscompanyModify.do?ilogisticsCom=${list.ilogisticsCom}" target="dialog" mask="true" rel="WLGSGL_XG" width="550" height="380" value="修改"></posmall:authA></li>
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
