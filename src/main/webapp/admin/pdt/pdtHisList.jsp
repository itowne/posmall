<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="pageContent">
	<form method="post" action="${ctx}/admin/pdt/pdtHisList.do"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone)">
		<div class="pageFormContent" layoutH="68">
			<table class="list" >
				<thead>
					<tr>				
						<th align="center" width="100">产品名称</th>
						<th align="center" width="100">产品型号</th>
						<th align="center" width="100">产品单价</th>
						<th align="center" width="150">登记日期</th>
					</tr>
				</thead>
				<tbody>
<%-- 				    <c:forEach var="list" items="${pdtHisList}"> --%>
				      <tr>
<%-- 				    </c:forEach> --%>
				      	<td rowspan="${pdtHisListNum}">${name}</td>
				      	<td rowspan="${pdtHisListNum}">${pdtNo}</td>
				    <c:forEach var="list" items="${pdtHisList}">
				        <td align="right">${list.price}&nbsp;</td>
				        <td>&nbsp;<fmt:formatDate value="${list.genTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				      </tr>
				    </c:forEach>
				
				</tbody>
			</table>
		</div>	
		<div class="formBar">
			<ul>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">&nbsp;&nbsp;返&nbsp;&nbsp;&nbsp;&nbsp;回&nbsp;&nbsp;</button>
						</div>
					</div></li>
			</ul>
		</div>
	</form>
</div>