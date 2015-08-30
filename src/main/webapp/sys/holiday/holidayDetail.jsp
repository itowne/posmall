<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>

<div class="pageContent">
	<form method="post" action="" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>日期：</label>
				<input name="holiDate" type="text" size="30" value="${tholiday.allDate}" readonly="readonly"/>
			</p>
			<p>
				<label>状态：</label>
				<input name="memo" type="text" size="30" value="<posmall:dict dictCode="${tholiday.holiStatus}" dictType="holiday_status"/>" readonly="readonly"/>
			</p>
			<p>
				<label>备注：</label>
				<input name="memo" type="text" size="30" value="${tholiday.memo}" readonly="readonly"/>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>
