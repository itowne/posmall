<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>

<form id="pagerForm" method="post" action="${ctx}/admin/pdtplanmonth/pdtPlanMonthList.do">
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<input type="hidden" name="year" value="${year}" />
	<input type="hidden" name="month" value="${month}" />
	<input type="hidden" name="ipdt" value="${ipdt}" />
</form>

<div class="pageHeader">
	<form id="ppmListForm" onsubmit="return navTabSearch(this);" action="${ctx}/admin/pdtplanmonth/pdtPlanMonthList.do" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					年度：
					    <select name="year" >
					      <option value="">-请选择-</option>
					      <c:forEach var="map" items="${yearMap}">
					          <option value="${map.key}" <c:if test="${year==map.key}">selected="selected"</c:if>>${map.value}</option>
					      </c:forEach>
					    </select>
					      
				</td>
				<td>
					月份：<select name="month">
					        <option value="">-全部-</option>
							<option value="1" <c:if test='${month=="1"}'>selected="selected"</c:if>>一月</option>
							<option value="2" <c:if test='${month=="2"}'>selected="selected"</c:if>>二月</option>
							<option value="3" <c:if test='${month=="3"}'>selected="selected"</c:if>>三月</option>
							<option value="4" <c:if test='${month=="4"}'>selected="selected"</c:if>>四月</option>
							<option value="5" <c:if test='${month=="5"}'>selected="selected"</c:if>>五月</option>
							<option value="6" <c:if test='${month=="6"}'>selected="selected"</c:if>>六月</option>
							<option value="7" <c:if test='${month=="7"}'>selected="selected"</c:if>>七月</option>
							<option value="8" <c:if test='${month=="8"}'>selected="selected"</c:if>>八月</option>
							<option value="9" <c:if test='${month=="9"}'>selected="selected"</c:if>>九月</option>
							<option value="10" <c:if test='${month=="10"}'>selected="selected"</c:if>>十月</option>
							<option value="11" <c:if test='${month=="11"}'>selected="selected"</c:if>>十一月</option>
							<option value="12" <c:if test='${month=="12"}'>selected="selected"</c:if>>十二月</option>
						</select>
					      
				</td>
				<td>
					 产品:<select name="ipdt" >
					      <option value="">-全部-</option>
					      <c:forEach var="map" items="${pdtNameMap}">
					          <option value="${map.key}" <c:if test="${ipdt==map.key}">selected="selected"</c:if>>${map.value}</option>
					      </c:forEach>
					    </select>
				</td>
				
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"> 查询</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
    <div class="panelBar">
		<ul class="toolBar">
			<li><posmall:authA authCode="YPCGL_XZ" cssClass="add" href="${ctx}/admin/pdtplanmonth/toPdtPlanMonthAdd.do" target="dialog" rel="YPCGL_XZ" mask="true" warn="新增计划" width="850" height="430" value="新增计划" /></li>
		</ul>
	</div>
	<table class="list" width="100%" layoutH="138">
		<thead>
			<tr>
				<th align="center" width="120">月排产编号</th>
				<th align="center" width="80">年度</th>
				<th align="center" width="80">月份</th>
				<th align="center" width="100">产品名称</th>
				<th align="center" width="100">月份产量</th>
				<th align="center" width="118">登记日期</th>
				<th align="center" width="150">操作</th>
				<th align="center"></th>
			</tr>
		</thead>
		<tbody>
		 <c:forEach var="list" items="${tpdtPlanMonthList}" >
			<tr>
				
				<td>&nbsp;
					<a href="${ctx}/admin/pdtplanmonth/pdtPlanMonthDetail.do?ipdtPlanMonth=${list.ipdtPlanMonth}" target="dialog" rel="YPCGL_XX" mask="true" title="详细信息" width="750" height="430" ><span>${list.pdtPlanMonthNo}</span></a>
				</td>
				<td align="right">${list.year}&nbsp;</td>
				<td align="right">${list.month}&nbsp;</td>
				<td>&nbsp;<posmall:dict dictType="pdt_name_all" dictCode="${list.ipdt}"></posmall:dict></td>
				<td align="right">${list.num}&nbsp;</td>
				<td>&nbsp;<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${list.updTime}"/> </td>
				<td>
					<div class="panelBar tablebtn">
						<ul class="toolBar">
							<li><posmall:authA authCode="YPCGL_XG" cssClass="edit" href="${ctx}/admin/pdtplanmonth/toPdtPlanMonthModify.do?ipdtPlanMonth=${list.ipdtPlanMonth}" target="dialog" rel="YPCGL_XG" mask="true"  width="850" height="430" value="修改"></posmall:authA></li>
							<c:if test="${list.canDelete == true}">
								<li><posmall:authA authCode="YPCGL_SC" cssClass="delete" href="${ctx}/admin/pdtplanmonth/pdtPlanMonthRemove.do?ipdtPlanMonth=${list.ipdtPlanMonth}" target="ajaxTodo" rel="YPCGL_SC" mask="true" title="确认是否删除该产品的月排产" value="删除"></posmall:authA></li>
							</c:if>
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