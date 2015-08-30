<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>

<div class="pageContent">
	<form method="post" action="holiday/holidayToAdd.do" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>日期：</label>
				<input name="holiDate" type="text" size="30" class="date" readonly="true"/>
			</p>
			<p>
				<label>状态：</label>
				<posmall:dictSelect selectName="holiStatus" dictType="holiday_status" defaultValue="${holiStatus}" needAll="NO" style="width:183px;"/>
			</p>
			<p>
				<label>备注：</label>
				<input name="memo" type="text" size="30"/>
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
