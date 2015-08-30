<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/posmall-tags" prefix="posmall"%>
<div class="pageContent">
<form method="post" action="${ctx}/cust/logistics/toAddStep2More.do?iord=${iord}"
	class="pageForm"  enctype="multipart/form-data" onsubmit="return iframeCallback(this,dialogAjaxDone);">
	<div class="pageFormContent" layoutH="65">
		<p style="width: 300px;float: none;font-size: 20px;">
			<a  href="${ctx}/cust/logistics/downTempCsv.do" style="color: blue;text-decoration:underline; line-height: 21px; font-size: 20px">单击此处下载物流文件模板</a>
		</p>
		<p></p>	<p></p>
		<p style="width: 400px;float: none;font-size: 16px;">
			请上传物流文件：<input type="file" name="logisticsOrdAddrUpload">
			<input type="hidden" name="location" value="1">
		</p>
	</div>
	<div class="formBar">
		<ul>
			<li>
				<div class="button">
					<a class="add" href="${ctx}/cust/logistics/toAddStep1MoreBefore.do" 
							target="dialog" mask="true" width="640" height="540"
							rel="CZWLXXGL_XZ2" title="第一步：订单选择"><span>返回上一步</span></a>
				</div>
			</li>
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
						<button type="button" class="close">&nbsp;&nbsp;&nbsp;关&nbsp;&nbsp;闭&nbsp;&nbsp;&nbsp;</button>
					</div>
				</div>
			</li>
		</ul>
	</div>
</form>
</div>