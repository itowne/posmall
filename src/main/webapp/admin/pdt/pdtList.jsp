<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>
<form id="pagerForm" method="post" action="${ctx}/admin/pdt/pdtList.do">
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<input type="hidden" name="name" value="${name}" />
	<input type="hidden" name="custStatus" value="${pdtNo}" />
</form>


<div class="pageHeader">
	<form id="pdtListForm" onsubmit="return navTabSearch(this);" action="${ctx}/admin/pdt/pdtList.do" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					产品名称：<input type="text" name="name" value="${name}">
				</td>
				<td>
					产品型号：<input type="text" name="pdtNo" value="${pdtNo}">
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
			<li><posmall:authA authCode="YPCGL_XZ" cssClass="add" href="${ctx}/admin/pdt/toPdtAdd.do" target="dialog" mask="true" rel="CPXXWH_XZ"  width="570" height="380" value="新增" /></li>
		</ul>
	</div>
	<table class="list" width="100%" layoutH="138">
		<thead>
			<tr>
				<th align="center" width="100">产品名称</th>
				<th align="center" width="80">产品单价</th>
				<th align="center" width="80">产品库存量</th>
				<th align="center" width="80">号段前缀</th>
<!-- 				<th align="center" width="160">产品号段区间</th> -->
<!-- 				<th align="center" width="160">已使用号段</th> -->
				<th align="center" width="140">登记日期</th>
				<th align="center" width="320">操作</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
		  <c:forEach var="list" items="${tpdtList}" >
			<tr>
				<td>&nbsp;
					<a href="${ctx}/admin/pdt/pdtDetail.do?ipdt=${list.ipdt}" rel="CPXXWH_XX" target="dialog" mask="true" title="详细信息" width="570" height="420"><span>${list.name}</span></a>
				</td>
				<td align="right">${list.price}&nbsp;</td>
				<td align="right">${list.stockNum}&nbsp;台</td>
				<td align="right">${list.tnoSegCfg.pre}&nbsp;</td>
<%-- 				<td align="center">${list.tnoSegCfg.start}&nbsp;--&nbsp;${list.tnoSegCfg.end}</td> --%>
<!-- 				<td align="center"> -->
<%-- 					<c:if test="${(list.tnoSegCfg.idx > 1) && (list.tnoSegCfg.start < list.tnoSegCfg.idx)}"> --%>
<%-- 						${list.tnoSegCfg.start}&nbsp;&nbsp;--&nbsp;&nbsp;${list.tnoSegCfg.idx} --%>
<%-- 					</c:if> --%>
<!-- 				</td> -->
				<td>&nbsp;<fmt:formatDate value="${list.genTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
					<div class="panelBar tablebtn">
						<ul class="toolBar">
						    <li><posmall:authA authCode="CPXXWH_XG" cssClass="edit" href="${ctx}/admin/pdt/toPdtModify.do?ipdt=${list.ipdt}" target="dialog" mask="true" rel="CPXXWH_XG"  width="570" height="420" value="修改"></posmall:authA></li>
							<li><posmall:authA authCode="CPXXWH_LSDJ" cssClass="icon" href="${ctx}/admin/pdt/pdtHisList.do?ipdt=${list.ipdt}" target="dialog" mask="true" rel="CPXXWH_LSDJ" warn="" width="570" height="420" value="历史单价"></posmall:authA></li>
							<li><posmall:authA authCode="CPXXWH_SC" cssClass="delete" href="${ctx}/admin/pdt/toPdtRemove.do?ipdt=${list.ipdt}" target="dialog" mask="true" rel="CPXXWH_SC" title="确认删除页" width="570" height="420" value="删除"></posmall:authA></li>
							<li class="line">line</li>
							<li><posmall:authA authCode="CPXXWH_XGKC" cssClass="edit" href="${ctx}/admin/pdt/toModifyStock.do?ipdt=${list.ipdt}" target="dialog" mask="true" rel="CPXXWH_XGKC" width="570" height="420" value="修改库存" title="产品库存修改"/></li>
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