<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<div class="pageContent">
	<form id="addForm" method="post" action="${ctx}/sys/user/userAdd.do"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="65">
			<p>
				<label>用户名：</label> <input id="loginName" name="loginName" type="text" size="30" class="required" />
			</p>
			<!-- <p>
				<label>密码：</label> <input id="password" name="password" type="password" size="30"  />
			</p> -->
			<p>
				<label>姓名：</label>
				<input name="tuserSub.name" type="text" size="30" class="required"/>
			</p>
			<p>
				<label>部门：</label>
				<input name="tuserSub.depart" type="text" size="30" class="required"/>
			</p>
			<p>
				<label>邮箱：</label>
				<input name="tuserSub.email" type="text" size="30" class="required"/>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit" class="submit">&nbsp;&nbsp;&nbsp;提&nbsp;&nbsp;交&nbsp;&nbsp;&nbsp;</button>
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
