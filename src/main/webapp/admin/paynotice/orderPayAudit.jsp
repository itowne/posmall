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
			<input type="text" value="<fmt:formatNumber value="${tord.amt}" pattern="¥#,##0.00#"/>"  readonly="readonly"/>
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


