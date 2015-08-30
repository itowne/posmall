<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<div class="pageContent">
	<form method="post" action="${ctx}/sys/sys/sysModify.do"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<input name="isys" type="hidden" value="${tsys.isys}">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>登录名：</label> 
				<input name="loginName" type="text" size="30" value="${tsys.loginName}"/>
			</p>
			<p>
				<label>密码：</label>
				<input name="password" type="text" size="30" value="${tsys.password}"/>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit" >提交</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">关闭</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" >重置</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>
