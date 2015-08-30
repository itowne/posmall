<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>
<div class="pageContent">
<script type="text/javascript">
  function before(){
	  if($("#custRegRefuseReasonForm_refuseReason").val() !=""){
		  //关闭审核dialog页面
		  $.pdialog.close("KHXXGL_SH");
		  $("#custRegRefuseReasonForm").submit();
	  }else{
		  $("#custRegRefuseReasonForm").submit();
	  }
  }
</script>
	<form id="custRegRefuseReasonForm" method="post" action="${ctx}/admin/cust/custAudit.do"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone)">
		<div class="pageFormContent" layoutH="68">
			<input type="hidden" name="icust" value="${icust}" />
			<input type="hidden" name="custStatus" value="AUDIT_NOPASS" />
				<textarea id="custRegRefuseReasonForm_refuseReason" name="refuseReason" class="required" rows="12" cols="73"></textarea>
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

