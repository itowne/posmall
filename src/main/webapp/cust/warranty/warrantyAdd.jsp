<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/posmall-tags" prefix="posmall"%>
<style>
#warrantyAddForm span.error {
	width: 60px;
	left: 300px;
}
</style>

<form id="warrantyAddForm" method="post" action="${ctx}/cust/warranty/warrantyAdd.do" class="required-validate" 
	onsubmit="return validateCallback(this, dialogAjaxDone);">
<div class="pageContent" selector="h1" layoutH="42">
	<div class="pageFormContent">
		<input type="hidden" name="ierpMaintenance" />
		<p>
			<label>输入产品序列号：</label>
			<input type="text" id="seqNo" name="seqNo" class="required" />
		</p>
		<div class="button">
			<div class="buttonContent">
				<button type="button" class="warrant_search_button">&nbsp;&nbsp;&nbsp;查&nbsp;&nbsp;询&nbsp;&nbsp;&nbsp;</button>
			</div>
		</div>
		<fieldset style="float: left;margin-top: 20px;">
			<legend style="padding: 5px 20px;margin-left: 50px;font-size: 15px;background-color: #B8D0D6;">保&nbsp;修&nbsp;信&nbsp;息</legend>
			<p>
				<label>产品型号：</label>
				<input type="text" readonly="readonly" name="pdtNo" value="" class="required" />
			</p>
			<p>
				<label>产品名称：</label>
				<input type="text" readonly="readonly" name="pm" value="" class="required" />
			</p>
			<p>
				<label>发货日期：</label>
				<input type="text" readonly="readonly" name="purchaseDate" value="" />
			</p>
			<p>
				<label>保修期限：</label>
				<input type="text" readonly="readonly" name="warrantyPeriod" value="" />
			</p>
			<p>
				<label>报修次数：</label>
				<input type="text" readonly="readonly" name="repairNum" value="" class="required" />
			</p>
			<p>
				<label>最后修复日期：</label>
				<input type="text" readonly="readonly" name="lastRepairedDate" value="" />
			</p>
			<p style="height: 100px;">
				<label style="width: 50px;">描述：</label>
				<textarea rows="5" cols="40" name="remark"></textarea>
			</p>
		</fieldset>
	</div>
</div>
<div class="formBar">
	<ul>
		<li>
			<div class="button">
				<div class="buttonContent">
					<button type="submit" class="submit">&nbsp;&nbsp;&nbsp;报&nbsp;&nbsp;修&nbsp;&nbsp;&nbsp;</button>
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
<script type="text/javascript">
	
	$(function() {
		$(".warrant_search_button").click(function() {
			searchBySeqno($("#seqNo").val());
		});
	});
	
	function searchBySeqno(seqNo) {
		if(seqNo == null || seqNo.trim() == "") {
			alertMsg.warn("请输入产品序列号");
			return;
		}
		
		$.ajax({
			type : "POST",
			url : "${ctx}/cust/warranty/searchBySeqno.do?seqNo=" + seqNo,
			async : true,
			success : function(data) {
				if(!data || data == null || data == "null") {
					$("input[name='pdtNo']").val("");
					$("input[name='pm']").val("");
					$("input[name='purchaseDate']").val("");
					$("input[name='warrantyPeriod']").val("");
					$("input[name='repairNum']").val("");
					$("input[name='lastRepairedDate']").val("");
					$("input[name='ierpMaintenance']").val("");
					alertMsg.correct("产品序列号不存在");
					return;
				}
				//var rstObject = JSON.parse(data);
				var rstObject = $.parseJSON(data);
				$("input[name='pdtNo']").val(rstObject.pdtNo);
				$("input[name='pm']").val(rstObject.pm);
				$("input[name='purchaseDate']").val(rstObject.purchaseDate);
				$("input[name='warrantyPeriod']").val(rstObject.warrantyPeriod);
				$("input[name='repairNum']").val(rstObject.repairNum);
				$("input[name='lastRepairedDate']").val(rstObject.lastRepairedDate);
				$("input[name='ierpMaintenance']").val(rstObject.ierpMaintenance);
			}
		});
	}
</script>
