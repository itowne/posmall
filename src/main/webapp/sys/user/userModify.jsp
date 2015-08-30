<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<div class="pageContent">
	<form method="post" action="${ctx}/sys/user/userModify.do"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<input name="iuser" type="hidden" value="${tuser.iuser}">
		<div class="pageFormContent" layoutH="65">
			<p>
				<label>用户名：</label> 
				<input name="loginName" type="text" size="30" value="${tuser.loginName}" readonly="readonly"/>
			</p>
			<p>
				<label>姓名：</label>
				<input name="tuserSub.name" type="text" size="30" value="${tuser.tuserSub.name}"/>
			</p>
			<p>
				<label>部门：</label>
				<input name="tuserSub.depart" type="text" size="30" value="${tuser.tuserSub.depart}"/>
			</p>
			<p>
				<label>邮箱：</label>
				<input name="tuserSub.email" type="text" size="30" value="${tuser.tuserSub.email}"/>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit" >&nbsp;&nbsp;&nbsp;提&nbsp;&nbsp;交&nbsp;&nbsp;&nbsp;</button>
						</div>
					</div></li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">&nbsp;&nbsp;&nbsp;关&nbsp;&nbsp;闭&nbsp;&nbsp;&nbsp;</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>
