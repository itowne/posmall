<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="pageContent">
	<form method="post" action="syssysrole/sysSysroleToMod.do" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	    <input name="isys" type="hidden" value="${tsys.isys}">
	    <input name="preIsys" type="hidden" value="${tsys.isys}">
	    <input name="preIsysrole" type="hidden" value="${isysrole}">
		<div class="pageFormContent" layoutH="65">
			<p>
				<label>客户名称：</label>
				<input type="text" readonly="readonly" value="${tsys.loginName}" />
			</p>
			<p>
				<label>角色名称：</label>
				<select name="isysrole">
				   <c:forEach var="role" items="${roleList}">
				       <option value="${role.isysrole}" <c:if test="${role.isysrole==isysrole}">selected="selected"</c:if>>
				           ${role.name}
				       </option>
				   </c:forEach>
				</select>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li>
				    <div class="button" style="margin-right:10px;">
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
</div>
