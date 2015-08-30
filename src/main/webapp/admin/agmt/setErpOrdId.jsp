<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>

<div class="pageContent">
	<form method="post" action="${ctx}/admin/agmt/confirmErpOrdId.do" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
		    <input name="iagmt" type="hidden" value="${tagmt.iagmt}" readonly="readonly"/>
			<p>
				<label>协议编号：</label>
				<input name="agmtNo" type="text" size="30" value="${tagmt.agmtNo}" readonly="readonly"/>
			</p>
			<p>
				<label>erp订单号：</label>
				<input name="erpOrdId" type="text" size="30" value="${tagmt.erpOrdId}"/>
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
