<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<form method="post" action="syssysrole/sysSysroleToAdd.do"
	class="pageForm required-validate"
	onsubmit="return validateCallback(this, dialogAjaxDone);">
	<div class="pageContent">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>用户名：</label>
				<select name="isys">
					<c:forEach var="sys" items="${sysList}">
						<option value="${sys.isys}">${sys.loginName}</option>
					</c:forEach>
				</select>
			</p>
			<p>
				<label>角色：</label>
				<select name="isysrole">
					<c:forEach var="role" items="${roleList}">
						<option value="${role.isysrole}">${role.name}</option>
					</c:forEach>
				</select>
			</p>
		</div>
	</div>
	<div class="formBar">
		<ul>
			<li>
				<div class="button" style="margin-right: 10px;">
					<div class="buttonContent">
						<button type="submit" class="submit">&nbsp;&nbsp;&nbsp;确&nbsp;&nbsp;认&nbsp;&nbsp;&nbsp;</button>
					</div>
				</div>
				<div class="button">
					<div class="buttonContent">
						<button type="button" class="close">&nbsp;&nbsp;&nbsp;关&nbsp;&nbsp;闭&nbsp;&nbsp;&nbsp;</button>
					</div>

				</div>
			</li>
		</ul>
	</div>
</form>
