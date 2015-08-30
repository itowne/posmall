<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script src="${ctx}/resources/js/rsa/jsbn.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/rsa/prng4.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/rsa/rng.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/rsa/rsa.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/rsa/password.js" type="text/javascript"></script>

<style>
<!--
	.passwordPage p{
		float: none;
	}
-->
</style>
<script type="text/javascript">
	//登录前操作
	function beforeSubmit() {
		$("#oldPassword").val(passwordEncrypt($("#oldPassword_").val()));
		$("#newPassword").val(passwordEncrypt($("#newPassword_").val()));
		$(".pageForm").submit();
	}
</script>
<div class="pageContent">
	<form method="post" action="${ctx}/admin/password/passwordToMod.do" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
	    <input type="hidden" name="oldPassword" id="oldPassword" />
	    <input type="hidden" name="newPassword" id="newPassword" />
		<div class="pageFormContent passwordPage" layoutH="56">
			   <p>   
			      <label>旧密码：</label>
			      <input id="oldPassword_" name="oldPassword_" class="required" type="password" size="30"/>
			   </p>
			   <p>   
			      <label>新密码：</label>
			      <input id="newPassword_"  name="newPassword_" class="required" type="password" size="30" minlength="6" maxlength="20" />
			   </p>
			   <p>   
			      <label>确认密码：</label>
			      <input id="confirmPassword" name="confirmPassword" class="required" type="password" size="30" equalto="#newPassword_" />
			   </p>
		</div>
		<div class="formBar">
			<ul>
				<li>
				    <div class="button" style="margin-right:10px;">
					   <div class="buttonContent">
					     <button type="button" onclick="beforeSubmit()">确认</button>
					   </div>
					</div>
					<div class="button">
					   <div class="buttonContent">
					     <button type="button" class="close">关闭</button>
					   </div>
					   
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>
