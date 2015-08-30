<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>

<form id="pagerForm" method="post" action="${ctx}/cust/ord/ordList.do">
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<input type="hidden" name="ordStatus" value="${ordStatus}" />
	<input type="hidden" name="payStatus" value="${payStatus}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="${ctx}/cust/ord/ordList.do" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					订单状态：
					<posmall:dictSelect selectName="ordStatus" dictType="ord_status" defaultValue="${ordStatus}"></posmall:dictSelect>
				</td>
				<td>
					支付状态：
					<posmall:dictSelect selectName="payStatus" dictType="pay_status" defaultValue="${payStatus}"></posmall:dictSelect>
			    </td>
			    <td> 
			     	登记日期:<input type="text" name="beginTime" class="date" readonly="readonly" value='<fmt:formatDate pattern="yyyy-MM-dd" value="${beginTime}"/>'/> 
			     	- <input type="text" name="endTime" class="date" readonly="readonly" value='<fmt:formatDate pattern="yyyy-MM-dd" value="${endTime}"/>' />
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
			<li><posmall:authA cssClass="add" href="${ctx}/cust/ord/selectTagmt4Order.do" target="dialog" mask="true" width="840" height="420" rel="POSDDGL_DD" title="第一步：协议选择" value="采购订单" /></li>
		</ul>
	</div>
	<table class="list" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="100">订单编号</th>
				<th width="100">协议编号</th>
				<th width="100">金额</th>
				<th width="130">登记日期</th>
				<th width="80">订单状态</th>
				<th width="100">支付状态</th>
				<th width="100">备注</th>
				<th width="150">操作</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="tord" items="${tordList}" >
				<tr>
					<td>${tord.ordNo}</td>
					<td>${tord.tagmt.agmtNo}</td>
					<td align="right"><fmt:formatNumber value="${tord.amt}" pattern="¥#,##0.00#"/>&nbsp;</td>
					<td>&nbsp;<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${tord.genTime}"/></td>
					<td>&nbsp;<posmall:dict dictCode="${tord.ordStatus}" dictType="ord_status" /></td>
					<td>&nbsp;<posmall:dict dictCode="${tord.payStatus}" dictType="pay_status" /></td>
					<td><span title="${tord.remark}" style="display:inline-block;width:100px;white-space:nowrap;text-overflow:ellipsis;-o-text-overflow:ellipsis;overflow: hidden;">&nbsp;${tord.remark}</span></td>
				    <td>
						<div class="panelBar tablebtn">
							<ul class="toolBar">
								<li><a class="edit" href="${ctx}/cust/ord/ordDetail.do?iord=${tord.iord}&iagmt=${tord.iagmt}" target="dialog" mask="true" width="800" height="610" rel="POSDDGL_XQ"><span>详情</span></a></li>
								<c:if test='${tord.ordStatus == "WAIT_AUDIT" && tord.payStatus == "WAIT_PAY" }'>
									<li><a class="delete" href="${ctx}/cust/ord/ordDelete.do?iord=${tord.iord}&iagmt=${tord.iagmt}" target="ajaxTodo" title="确定要删除吗?" mask="true" rel="POSDDGL_CX"><span>撤销</span></a></li>
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
