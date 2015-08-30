<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="pageContent">
		<div class="pageFormContent" layoutH="68">
		<form name="ordUpload" id="ordUpload" method="post" enctype="multipart/form-data" action="${ctx}/admin/order/saveOrdUpload.do" onsubmit="return iframeCallback(this, navTabAjaxDone);">
		<table class="list" width="100%" layoutH="100">
		<thead>
			<tr>
				<th width="80">选择一个文件</th>
				<td><input type='file' name="ordUpload" value='浏览...' />
					<input type="hidden" name="location" value="1"/>
				</td>
			</tr>
		</thead>
	</table>
		</div>
		<div class="formBar">
			<ul>
			<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">提交</button>
						</div>
					</div>
				</li>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
</div>
</form>
<script type="text/javascript">
	function checkForm(){
		
	}
</script>