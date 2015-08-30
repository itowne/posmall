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
				<label>通知类型：</label>
				<input type="text" name="notifyType" readonly="readonly" value="<posmall:dict dictType='notify_type' dictCode='${tnotifyCfg.notifyType}'></posmall:dict>"/>
			</p>
			<p>
				<label>用户编号：</label>
				<input type="text" name="iuser" readonly="readonly"  value="${tnotifyCfg.iuser}"    />
			</p>
			<p>
				<label>用户姓名：</label>
				<input type="text" name="userName"  readonly="readonly" value="${tnotifyCfg.userName}"  />
			</p>
			<p style="width:500px">
				<label>说明：</label>
				<textarea type="text" name="memo" readonly="readonly" cols="35"  rows="6">${tnotifyCfg.memo}</textarea>
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