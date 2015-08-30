<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>
<div class="pageContent">
<script type="text/javascript">
  function before(){
	  if($("#paynoticeRefuseReasonForm_refuseReason").val() !=""){
		  //关闭审核dialog页面
		  $.pdialog.close("FKTZSGL_SH");
		  $("#paynoticeRefuseReasonForm").submit();
	  }else{
		  $("#paynoticeRefuseReasonForm").submit();
	  }
  }
</script>
	<form id="paynoticeRefuseReasonForm" method="post" action="${ctx}/admin/paynotice/paynoticeAuditNo.do"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone)">
		<div class="pageFormContent" layoutH="68">
			<input type="hidden" name="ipayNotify" value="${ipayNotify}" />
			<p>
				<textarea id="paynoticeRefuseReasonForm_refuseReason" name="refuseReason" class="required" rows="12" cols="73"></textarea>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="button" onclick="before();">&nbsp;&nbsp;提&nbsp;&nbsp;&nbsp;交&nbsp;&nbsp;</button>
						</div>
					</div>
				</li>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">&nbsp;&nbsp;取&nbsp;&nbsp;&nbsp;&nbsp;消&nbsp;&nbsp;</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>

