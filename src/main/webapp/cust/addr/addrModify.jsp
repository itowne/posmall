<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="pageContent">
	<form id="addrAddForm" method="post" action="${ctx}/cust/addr/modify.do"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="65">
		<p align="center">
			电话号码、手机号选填一项,其余均为必填项
		</p>
		<p style="width: 635px;">
			<label>收货地址：</label>
			<label style="float: none;">
				<select id="province" name="province" style="float: none;width: 150px;"></select>
				<select id="city" name="city" style="float: none;margin-left: 4px;width: 175px;"></select>
				<select id="county" name="county" style="float: none;margin-left: 4px;width: 150px;"></select>
			</label>
		</p>
		<p>
			<label>邮政编码：</label>
			<label><input type="text" name="postalCode" class="required" value="${taddr.postalCode}"></label>
		</p>
		<p style="padding-bottom: 20px;">
			<label>街道地址：</label>
			<label><textarea rows="2" cols="65" name="area" class="required">${taddr.area}</textarea></label>
		</p>
		<p>
			<label>收货人姓名：</label>
			<label><input type="text" name="name" class="required" value="${taddr.name}"></label>
		</p>
		<p>
			<label>手机号码：</label>
			<label><input type="text" id="mobile" name="mobile" class="telphone" value="${taddr.mobile}"></label>
		</p>
		<p>
			<label>电话号码：</label>
			<label><input type="text" id="tel" name="tel" value="${taddr.tel}" class="phone"></label>
		</p>
		</div>
		<input type="hidden" name="iaddr" value="${taddr.iaddr}"/>
		<div class="formBar">
			<ul>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="submit" onclick="return beforeSubmit();">&nbsp;&nbsp;&nbsp;保&nbsp;&nbsp;存&nbsp;&nbsp;&nbsp;</button>
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
<script type="text/javascript">

	$(function(){
		_init_area();
	});
	
	function _init_area(){  //初始化函数
		document.getElementById("province").options[0]=new Option("省份", "");
		document.getElementById("city").options[0]=new Option("地级市", "");
		document.getElementById("county").options[0]=new Option("市、县级市", "");
		
		initProvince();
		document.getElementById("province").onchange=new Function("provinceChange()");
		document.getElementById("city").onchange=new Function("cityChange()");
	}
	
	function initProvince() {
		$.ajax({
			type : "POST",
			contentType : 'application/json',
			dataType : 'json',
			url : "${ctx}/cust/addr/getProvince.do",
			success : function(data) {
				var idx = 1;
				for(var code in data) {
					document.getElementById("province").options[idx]=new Option(data[code], code);
					if(code == "${taddr.province}") {
						document.getElementById("province").options[idx].selected = "selected";
						provinceChange();
					}
					idx++;
				}
			}
		});
	}
	
	function provinceChange() {
		$("#city").empty();
		document.getElementById("city").options[0]=new Option("地级市", "");
		$("#county").empty();
		document.getElementById("county").options[0]=new Option("市、县级市", "");
		
		var _provCode = $("#province").val();
		if(_provCode == null || _provCode.trim() == "") {
			return;
		}
		$.ajax({
			type : "POST",
			contentType : 'application/json',
			dataType : 'json',
			url : "${ctx}/cust/addr/getCity.do?provCode=" + _provCode,
			success : function(data) {
				var idx = 1;
				for(var code in data) {
					document.getElementById("city").options[idx]=new Option(data[code], code);
					if(code == "${taddr.city}") {
						document.getElementById("city").options[idx].selected = "selected";
						cityChange();
					}
					idx++;
				}
			}
		});
	}
	
	function cityChange() {
		$("#county").empty();
		document.getElementById("county").options[0]=new Option("市、县级市", "");
		
		var _provCode = $("#city").val();
		if(_provCode == null || _provCode.trim() == "") {
			return;
		}
		$.ajax({
			type : "POST",
			contentType : 'application/json',
			dataType : 'json',
			url : "${ctx}/cust/addr/getCity.do?provCode=" + _provCode,
			success : function(data) {
				var idx = 1;
				for(var code in data) {
					document.getElementById("county").options[idx]=new Option(data[code], code);
					if(code == "${taddr.county}") {
						document.getElementById("county").options[idx].selected = "selected";
					}
					idx++;
				}
			}
		});
	}
</script>
<script type="text/javascript">
<!--
/****************** 初始化省市区三级联动 *******************/
//_init_area();

/******************省市县自动回填******************/
/* $(function() {
	$("select[name = 'province'] > option").each(function(){
		if($(this).val() == "${taddr.province}") {
			$(this).attr("selected", "selected");
			change(1);
			$("select[name = 'city'] > option").each(function(){
				if($(this).val() == "${taddr.city}") {
					$(this).attr("selected", "selected");
					change(2);
					$("select[name = 'county'] > option").each(function(){
						if($(this).val() == "${taddr.county}") {
							$(this).attr("selected", "selected");
						}
					});
				}
			});
		}
	});
}); */


function beforeSubmit() {
	if($("#province").val() == "" || $("#city").val() == "") {
		alertMsg.error("收货地址省、市为必填项！");
		return false;
	}
	var _mobile = $("#mobile").val();
	var _tel = $("#tel").val();
	if(_mobile.trim() == "" && _tel.trim() == "") {
		alertMsg.error("电话号码、手机号选填一项！");
		return false;
	}
	$("#addrAddForm").submit();
}
//-->
</script>