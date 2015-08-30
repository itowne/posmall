<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<style type="text/css">
	textarea{
		width: 745px !important;
		height: 360px !important;
	}
</style>
<div class="pageContent">
	<div class="pageFormContent" layoutH="65">
	<div style="text-align: center;height: 390px;"><jsp:include page="agmtContent.jsp"/></div>
	<div style="text-align: center;">
		<input type="checkbox" id="agree" name="agree" style="margin: 14px 7px 0 0;"><span>同意</span>
	</div>
	</div>
	<div class="formBar">
		<ul>
			<li>
				<div class="button" id="agreementSmt" style="display: none;">
					<a mask="true" rel="DHXYGL_QSXY" title="第二步：协议信息填写" 
					href="${ctx}/cust/agmt/agmtAdd.do" target="dialog">
							<span>&nbsp;&nbsp;&nbsp;下一步&nbsp;&nbsp;&nbsp;</span></a>
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
</div>
<script type="text/javascript">
$(function(){
	$("#agree").bind("click", function() {
		if($(this).attr("checked") == "checked") {
			$("#agreementSmt").show();
		} else {
			$("#agreementSmt").hide();
		}
	});
	
});
</script>