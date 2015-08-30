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
				<label>日志类型：</label>
				<input name="logType" type="text" size="30" value="<posmall:dict dictType="log_type" dictCode="${tlog.logType}"/>" readonly="readonly"/>
				
			</p>
			<p>
				<label>操作类型：</label>
				<input name="operType" type="text" size="30" value="<posmall:dict dictType="oper_type" dictCode="${tlog.operType}"/>" readonly="readonly"/>
			</p>
			<p>
				<label>备注：</label>
				<input name="memo" type="text" size="30" value="${tlog.memo}" readonly="readonly"/>
			</p>
			<p style="height:auto">
				<label>修改前数据：</label>
				<textarea name="preData"  readonly="readonly" style="width:177px;height:100px;">${tlog.preData}</textarea>
			</p>
			<p>
				<label>修改后数据：</label>
				<textarea name="data" readonly="readonly" style="width:177px;height:100px;">${tlog.data}</textarea>
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
