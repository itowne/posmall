<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>
<div class="pageContent">
	<form id="addForm" method="post" action="${ctx}/admin/custappver/custAppverAdd.do"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="65">
			<p>
				<label>公司名称：</label>
				<posmall:dictSelect selectName="icust" dictType="custreg_name" defaultValue="${tcustAppver.icust}" needAll="NO" style="width:212px;"/>
			</p>
			<p>
				<label>应用版本号：</label>
				<input name="appver" type="text" size="30" class="required"/>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit" class="submit">&nbsp;&nbsp;&nbsp;提&nbsp;&nbsp;交&nbsp;&nbsp;&nbsp;</button>
						</div>
					</div></li>
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
