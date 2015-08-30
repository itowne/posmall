<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/posmall-tags" prefix="posmall"%>
<style>
#warrantyAddForm span.error {
	width: 100px;
	left: 300px;
}
</style>
<script type="text/javascript">
	$(function() {
		$.validator.addMethod("newSeqNo", function() {
			var _seqNo = $("#seqNo").val();
			var _newSeqNo = $(".newSeqNo").val();
			if(_seqNo.trim() == _newSeqNo.trim()) {
				return false;
			}
			return true;
		}, "与原序列号冲突");
	});
</script>

<form id="warrantyAddForm" method="post" action="${ctx}/admin/warranty/warrantyRepaired.do" class="required-validate" 
	onsubmit="return validateCallback(this, dialogAjaxDone);">
<div class="pageContent" selector="h1" layoutH="42">
	<div class="pageFormContent">
		<input type="hidden" name="iwarranty" value="${twarranty.iwarranty}"/>
		<p>
			<label>产品序列号：</label>
			<input type="text" readonly="readonly" id="seqNo" value="${twarranty.seqNo}" />
		</p>
		<p>
			<label>保修期起始日期：</label>
			<input type="text" readonly="readonly" value='<fmt:formatDate value="${erpMaintenance.lifeStartTime}" pattern="yyyy-MM-dd"/>' />
		</p>
		<p>
			<label>保修期终止日期：</label>
			<input type="text" readonly="readonly" value='<fmt:formatDate value="${erpMaintenance.warrantyPeriod}" pattern="yyyy-MM-dd"/>' />
		</p>
		<fieldset style="float: left;margin-top: 10px;">
			<legend style="padding: 5px 20px;margin-left: 50px;font-size: 15px;background-color: #B8D0D6;">修&nbsp;复&nbsp;信&nbsp;息</legend>
			<p>
				<label>维修人员：</label>
				<input type="text" name="warrantyPerson" placeholder="可不填"/>
			</p>
			<p style="height: 83px;width: 450px;">
				<label>修复反馈：</label>
				<textarea rows="5" cols="40" name="repairedRemark" placeholder="可不填"></textarea>
			</p>
			<c:if test="${inWarrantyPeriod == true}">
				<p>
					<label>新产品序列号：</label>
					<input type="text" name="newSeqNo" class="required newSeqNo" minlength="12" maxlength="12" placeholder="(更换设备必填)"/>
				</p>
			</c:if>
		</fieldset>
	</div>
</div>
<div class="formBar">
	<ul>
		<li>
			<div class="button">
				<div class="buttonContent">
					<button type="submit" class="submit">&nbsp;&nbsp;已&nbsp;&nbsp;修&nbsp;&nbsp;复&nbsp;&nbsp;</button>
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
