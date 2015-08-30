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
			<label>物流单编号：</label>
			<input type="text" value="${tlogisticsOrd.innerOrdno}"  readonly="readonly"/>
		</p>
		<p>
			<label>客户名称：</label>
			<input type="text" readonly="readonly" value="${tcustReg.name}"  />
		</p>
		<p><label>物流单状态：</label>
		   <input type="text" readonly="readonly" value='<posmall:dict dictCode="${tlogisticsOrd.logisticsOrdStatus}" dictType="logistics_ord_status" />'/>
		</p>
		<p>
			<label>支付状态：</label>
			<input type="text" readonly="readonly" value='<posmall:dict dictCode="${tlogisticsOrd.payStatus}" dictType="pay_status" />'/>
        </p>
		<p>
			<label>物流费用：</label>
			<input type="text" value="<fmt:formatNumber value="${tlogisticsOrd.logisticsFreight}" pattern="¥#,##0.00#"/>"  readonly="readonly"/>
		</p>
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


