<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>
<form id="pagerForm" method="post" action="${ctx}/admin/contract/contractList.do">
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<input type="hidden" name="contractName" value="${tcontract.contractName}" />
	<input type="hidden" name="custCode" value="${tcontract.custCode}" />
	<input type="hidden" name="contractNo" value="${tcontract.contractNo}" />
</form>


<div class="pageHeader">
	<form id="contractListForm" onsubmit="return navTabSearch(this);" action="${ctx}/admin/contract/contractList.do" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
				      注册码：<input type="text" name="custCode" value="${tcontract.custCode}">
				</td>
				<td>
				      合同名称：<input type="text" name="contractName" value="${tcontract.contractName}">
				</td>
				<td>
				       采购框架合同编号：<input type="text" name="contractNo" value="${tcontract.contractNo}">
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
			<li><posmall:authA cssClass="add" href="${ctx}/admin/contract/toContractAdd.do" target="dialog" mask="true" rel="XXMBGL_XZ"  width="600" height="380" value="新增"></posmall:authA></li>
		</ul>
	</div>
	<table class="list" width="100%" layoutH="138">
		<thead>
			<tr>
				<th align="center" width="80">注册码</th>
				<th align="center" width="80">公司名称</th>
				<th align="center" width="80">采购框架合同编号</th>
				<th align="center" width="100">合同名称</th>
				<th align="center" width="400">合同内容</th>
				<th align="center" width="120">更新时间</th>
				<th align="center" width="250">操作</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
		  <c:forEach var="list" items="${tcontractList}" >
			<tr>
				<td>&nbsp;${list.custCode}</td>
				<td>&nbsp;<posmall:dict dictType="custreg_name" dictCode="${list.icust}"/></td>
				<td>&nbsp;${list.contractNo}</td>
				<td>&nbsp;${list.contractName}</td>
				<td>&nbsp;${list.content}</td>
				<td>&nbsp;<fmt:formatDate value="${list.updTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
					<div class="panelBar tablebtn">
						<ul class="toolBar">
							<li><posmall:authA cssClass="edit" href="${ctx}/admin/contract/toContractModify.do?icontract=${list.icontract}" target="dialog" mask="true" rel="XXMBGL_XG"   width="600" height="380" value="修改"></posmall:authA></li>
							<li><posmall:authA cssClass="icon" href="${ctx}/admin/contract/contractDetail.do?icontract=${list.icontract}" target="dialog" mask="true" rel="XXMBGL_XX"   width="600" height="380" value="明细"></posmall:authA></li>
							<li><posmall:authA cssClass="delete" href="${ctx}/admin/contract/contractRemove.do?icontract=${list.icontract}"  target="ajaxTodo"  mask="true" rel="XXMBGL_SC" title="是否确认删除?" value="删除"></posmall:authA></li>
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