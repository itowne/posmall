<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>
<style type="text/css">
	.table{
		margin: 20px 5px;
	}
</style>
<div class="pageContent" layoutH="38">
	<div class="pageFormContent">
	    <jsp:include page="payNotifyInfo.jsp"></jsp:include>
	    
	    <div class="divider"></div>
		<p>
			<label>协议编号：</label>
			<input type="text" value="${tagmt.agmtNo}"  readonly="readonly"/>
		</p>
		<p>
			<label>客户名称：</label>
			<input type="text" value="${tcustReg.name}"  readonly="readonly"/>
		</p>
		<p>
			<label>协议生效开始时间：</label>
			<input type="text" value='<fmt:formatDate value="${tagmt.startTime}" pattern="yyyy-MM-dd" />'  readonly="readonly"/>
		</p>
		<p>
			<label>协议生效结束时间：</label>
			<input type="text" value='<fmt:formatDate value="${tagmt.endTime}" pattern="yyyy-MM-dd" />' readonly="readonly"/>
		</p>
		<p>
			<label>保证金：</label>
			<input type="text" value="<fmt:formatNumber value="${tagmt.deposit}" pattern="¥#,##0.00#"/>"  readonly="readonly"/>
		</p>
<!-- 		<p> -->
<!-- 			<label>已用保证金：</label> -->
<%-- 			<input type="text" value="${tagmt.usedDeposit}"  readonly="readonly"/><label style="width:30px">元</label> --%>
<!-- 		</p> -->
<!-- 		<p> -->
<!-- 			<label>剩余保证金：</label> -->
<%-- 			<input type="text" value="${tagmt.remainDeposit}"  readonly="readonly"/><label style="width:30px">元</label> --%>
<!-- 		</p> -->
		<p>
			<label>协议状态：</label>
			<input type="text" value="<posmall:dict dictType='agmt_status' dictCode='${tagmt.agmtStatus}'></posmall:dict>" readonly="readonly"/>
		</p>
	  
	  <jsp:include page="payLists.jsp"></jsp:include>
    </div>
</div>
<div class="formBar">
	<ul>
		<li>
			<div class="button">
				<div class="buttonContent">
					<button type="button" class="close">&nbsp;&nbsp;返&nbsp;&nbsp;&nbsp;&nbsp;回&nbsp;&nbsp;</button>
				</div>
			</div>
		</li>
	</ul>
</div>