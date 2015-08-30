<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>
<style type="text/css">
	.table{
		margin: 20px 5px;
	}
</style>
<script type="text/javascript">
  function audit(v){
	  $("#custAuditForm input[name='payStatus']").val(v);
	  $("#custAuditForm").submit();
  }
  
</script>
<form id="custAuditForm" method="post" action="${ctx}/admin/paynotice/paynoticeAudit.do"
		class="pageForm "
		onsubmit="return validateCallback(this, dialogAjaxDone)">
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
			<input type="text" value="<posmall:dict dictType='agmt_status' dictCode='${tagmt.agmtStatus}' />" readonly="readonly"/>
		</p>
		<p>
			<label>应补交保证金：</label>
			<input type="text" value="<fmt:formatNumber value="${-tagmt.redundantDeposit}" pattern="¥#,##0.00#"/>" readonly="readonly" style="color: red;" />
		</p>
<!-- 	   <div class="table"> -->
<!-- 		<table class="list" width="100%"> -->
<!-- 			<thead> -->
<!-- 				<tr> -->
<!-- 					<th width="200">产品名称</th> -->
<!-- 					<th width="100">单价</th> -->
<!-- 					<th width="100">订货量</th> -->
<!-- 					<th width="60">折扣率</th> -->
<!-- 					<th width="100">折扣价</th> -->
<!-- 					<th></th> -->
<!-- 				</tr> -->
<!-- 			</thead> -->
<!-- 			<tbody> -->
<%-- 				<c:forEach items="${tagmtDetailList}" var="list" varStatus="status">			 --%>
<!-- 				<tr> -->
<%-- 					<td>&nbsp;${list[1]}</td> --%>
<%-- 					<td align="right">${list[2]}&nbsp;</td> --%>
<%-- 					<td align="right">${list[3]}&nbsp;</td> --%>
<%-- 					<td align="right">${list[4]}&nbsp;</td> --%>
<%-- 					<td align="right">${list[6]}&nbsp;</td> --%>
<!-- 					<td></td> -->
<!-- 				</tr> -->
<%-- 				</c:forEach> --%>
<!-- 			</tbody> -->
<!-- 		</table> -->
<!-- 		<p> -->
<!-- 			<label>订货总量：</label> -->
<%-- 			<input type="text" name="sumnum" value="${sumnum}" readonly="readonly"/> --%>
<!-- 		</p> -->
<!-- 		<p> -->
<!-- 			<label>总金额：</label> -->
<%-- 			<input type="text" name="modifyAmt" value="${sumamt}" readonly="readonly"/> --%>
<!-- 		</p> -->
<!-- 	</div> -->
	  
		<jsp:include page="payLists.jsp"></jsp:include>
	  </div>
	</div>
	<div class="formBar">
	<ul>
	    <c:choose>
	       <c:when test="${flag == true}">
		         <li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" onclick="audit('HAVE_PAY');">&nbsp;&nbsp;审核通过&nbsp;&nbsp;</button>
						</div>
					</div>
				</li>
	       </c:when>
	       <c:otherwise>
	       		<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" onclick="audit('PART_PAY');">&nbsp;&nbsp;部分支付&nbsp;&nbsp;</button>
						</div>
					</div>
				</li>
	       </c:otherwise>
	    </c:choose>
		<li>
			<div class="button">
					<a type="button" target="dialog" mask="true" title="拒绝理由" href="${ctx}/admin/paynotice/toRefuseReason.do?ipayNotify=${tpayNotify.ipayNotify}" onclick=""><span>&nbsp;&nbsp;审核不通过&nbsp;&nbsp;</span></a>
			</div>
		</li>
		<li>
			<div class="button">
				<div class="buttonContent">
					<button type="button" class="close">&nbsp;&nbsp;取&nbsp;&nbsp;&nbsp;&nbsp;消&nbsp;&nbsp;</button>
				</div>
			</div>
		</li>
	</ul>
</div>
</form>


