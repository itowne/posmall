<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/posmall-tags" prefix="posmall"%>
<div class="pageContent">
<form method="post" action="${ctx}/cust/renew/uploadTempCsvAndGetData.do"
	class="pageForm"  enctype="multipart/form-data" onsubmit="return iframeCallback(this,dialogAjaxDone);">
	<div class="pageFormContent" layoutH="65">
		<p style="width: 300px;float: none;font-size: 20px;">
			<a href="${ctx}/cust/renew/downTempCsv.do" style="color: blue;text-decoration:underline; line-height: 21px; font-size: 20px">单击此处下载文件模板</a>
		</p>
		<br /><br />
		<p style="width: 400px;float: none;font-size: 16px;">上传续保数据文件：
			<input type="file" name="renewDataTmp" class="required">
			<input type="hidden" name="location" value="1">
		</p>
	</div>
	<div class="formBar">
		<ul>
			<li>
				<div class="button">
					<div class="buttonContent">
						<button type="submit" class="button">&nbsp;&nbsp;&nbsp;下一步&nbsp;&nbsp;&nbsp;</button>
					</div>
				</div>
			</li>
			<li>
				<div class="button">
					<div class="buttonContent">
						<button type="button" class="close">&nbsp;&nbsp;&nbsp;取&nbsp;&nbsp;消&nbsp;&nbsp;&nbsp;</button>
					</div>
				</div>
			</li>
		</ul>
	</div>
</form>
</div>