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
			<label>订单编号：</label>
			<input type="text" value="${tord.ordNo}"  readonly="readonly"/>
		</p>
		<p>
			<label>客户名称：</label>
			<input type="text" value="${tcustReg.name}"  readonly="readonly"/>
		</p>
		<p>
			<label>登记时间：</label>
			<input type="text" value='<fmt:formatDate value="${tord.genTime}" pattern="yyyy-MM-dd" />'  readonly="readonly"/>
		</p>
		<p>
			<label>订单金额：</label>
			<input type="text" value="<fmt:formatNumber value="${tord.amt}" pattern="¥#,##0.00#"/>"  readonly="readonly"/><label style="width:30px">元</label>
		</p>
		<p>
			<label>支付状态：</label>
			<input type="text" value="<posmall:dict dictType='pay_status' dictCode='${tord.payStatus}'></posmall:dict>" readonly="readonly"/>
		</p>
		<p>
			<label>订单状态：</label>
			<input type="text" value="<posmall:dict dictType='ord_status' dictCode='${tord.ordStatus}'></posmall:dict>" readonly="readonly"/>
		</p>
		<c:if test='${tord.payStatus =="PART_PAY"}'>
		  <p>
			<label>保证金锁定金额：</label>
			<input type="text" value="<fmt:formatNumber value="${tord.lockAmtOfDeposit}" pattern="¥#,##0.00#"/>"  readonly="readonly"/><label style="width:30px">元</label>
		</p>
		</c:if>

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