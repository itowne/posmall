<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>

<div class="pageContent">
	<form method="post" action="${ctx}/admin/custappver/custAppverMod.do" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	    <input name="icustAppver" type="hidden" value="${tcustAppver.icustAppver}">
		<div class="pageFormContent" layoutH="56">
		    <p>
				<label>客户编号：</label>
				<input name="custNo" type="text" size="30" value="${tcustAppver.custNo}" readonly="readonly"/>
			</p>
			<p>
				<label>公司名称：</label>
				<input name="name" type="text" size="30" value="${tcustAppver.name}" readonly="readonly"/>
			</p>
			<p>
				<label>应用版本号：</label>
				<input name="appver" type="text" size="30" value="${tcustAppver.appver}" class="required"/>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li>
				    <div class="button" style="margin-right:10px;">
					   <div class="buttonContent">
					     <button type="submit" class="submit">确认</button>
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
