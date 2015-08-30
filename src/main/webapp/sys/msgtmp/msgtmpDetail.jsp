<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>
<div class="pageContent">
	<form method="post" action=""
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone)">
		<div class="pageFormContent" layoutH="68">
			<p>
				<label>模板类型：</label>
				<input type="text" name="tmpType" readonly="readonly" value="<posmall:dict dictType='msg_tmp_type' dictCode='${tmsgtmp.tmpType}'></posmall:dict>"/>
			</p>
			<p>
				<label>模板代码：</label>
				<input type="text" name="tmpCode" readonly="readonly"  value="${tmsgtmp.tmpCode}" style="width: 200px"/>
			</p>
			<p style="width:500px">
				<label>模板名称：</label>
				<textarea name="noteName" class="required" cols="35"  rows="6" style="height: 40px;width: 300px">${tmsgtmp.noteName}</textarea>
			</p>
			<p></p>
			<p style="width:500px">
				<label>内容：</label>
				<textarea name="content" readonly="readonly" cols="35"  rows="6" style="height: 120px;width: 300px">${tmsgtmp.content}</textarea>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">&nbsp;&nbsp;返&nbsp;&nbsp;&nbsp;&nbsp;回&nbsp;&nbsp;</button>
						</div>
					</div></li>
			</ul>
		</div>
	</form>
</div>