<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<div class="pageContent">
	<form method="post" action="" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="65">
			<p>
				<label>登录名：</label>
				<input name="loginName" type="text" size="30" value="${tcust.loginName}" readonly="readonly"/>
			</p>
			<p>
				<label>单位名称：</label>
				<input name="name" type="text" size="30" value="${tcust.tcustReg.name}" readonly="readonly"/>
			</p>
			<p>
				<label>联系人：</label>
				<input name="contactName" type="text" size="30" value="${tcust.tcustReg.contactName}" readonly="readonly"/>
			</p>
			<p>
				<label>移动电话：</label>
				<input name="mobile" type="text" size="30" value="${tcust.tcustReg.mobile}" readonly="readonly"/>
			</p>
			<p>
				<label>最近更新时间：</label>
				<input type="text" size="30" value='<fmt:formatDate value="${tcust.updTime}" pattern="yyyy-MM-dd HH:mm:ss"/>' readonly="readonly"/>
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
