<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<div class="pageContent">
	<form method="post" action="" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>用户名：</label>
				<input name="loginName" type="text" size="30" value="${tuser.loginName}" readonly="readonly"/>
			</p>
			<p>
				<label>姓名：</label>
				<input name="name" type="text" size="30" value="${tuser.tuserSub.name}" readonly="readonly"/>
			</p>
			<p>
				<label>部门：</label>
				<input name="depart" type="text" size="30" value="${tuser.tuserSub.depart}" readonly="readonly"/>
			</p>
			<p>
				<label>邮箱：</label>
				<input name="email" type="text" size="30" value="${tuser.tuserSub.email}" readonly="readonly"/>
			</p>
			<p>
				<label>上次登录时间：</label>
				<input type="text" size="30" value='<fmt:formatDate value="${tuser.tuserSub.lastLogin}" pattern="yyyy-MM-dd HH:mm:ss"/>' readonly="readonly"/>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">&nbsp;&nbsp;&nbsp;关&nbsp;&nbsp;闭&nbsp;&nbsp;&nbsp;</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>
