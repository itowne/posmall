<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>

<form id="pagerForm" method="post" action="${ctx}/sys/holiday/holidayList.do">
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<input type="hidden" name="endHoliDate" value="${endHoliDate}" />
	<input type="hidden" name="beginHoliDate" value="${beginHoliDate}" />
	<input type="hidden" name="holiStatus" value="${holiStatus}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="${ctx}/sys/holiday/holidayList.do" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					起始时间：<input name="beginHoliDate" value="${beginHoliDate}" type="text" class="date" readonly="true" />
				</td>
				<td>
					结束时间：<input name="endHoliDate" value="${endHoliDate}" type="text" class="date" readonly="true" />
				</td>
				<td>
					状态：
					<posmall:dictSelect selectName="holiStatus" dictType="holiday_status" defaultValue="${holiStatus}"/>
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
			<li><a class="add" href="${ctx}/sys/holiday/holidayAdd.do" target="dialog" width="460" height="200" mask="true" rel="holidayTj"><span>添加</span></a></li>
		</ul>
	</div>
	<table class="list" width="100%" layoutH="138">
		<thead>
		   <tr>
				<th width="90" align="center">日期</th>
				<th width="60" align="center">状态</th>
				<th width="200" align="center">备注</th>
				<th width="120" align="center">操作</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
		  <c:forEach var="holiday" items="${holidayList}">
			<tr>
				<td align="left">&nbsp;<a title="节假日详情"  href="${ctx}/sys/holiday/holidayDetail.do?iholiday=${holiday.iholiday}" target="dialog"  width="460" height="500" mask="true" rel="holidayMx"><span>${holiday.allDate}</span></a></td>
				<td align="left">&nbsp;<posmall:dict dictCode="${holiday.holiStatus}" dictType="holiday_status" /></td>
				<td align="left">&nbsp;${holiday.memo}</td>
				<td align="center">
				  <div class="panelBar tablebtn">
						<ul class="toolBar">
					       <li><a title="确定要删除吗?" class="delete" href="${ctx}/sys/holiday/holidayDel.do?iholiday=${holiday.iholiday}" target="ajaxTodo"  mask="true"width="850" height="480"><span>删除</span></a></li>
			               <li><a title="修改"       class="edit" href="${ctx}/sys/holiday/holidayMod.do?iholiday=${holiday.iholiday}" target="dialog" mask="true" width="460" height="200" rel="holidayXg"><span>修改</span></a></li>
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
