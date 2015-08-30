<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="pageContent">
<script type="text/javascript">
	function validatPdtName(){
		var v;
		if($("#pdtAddForm input[name='name']").val()==""){
			v = true;
		}else{
		   $.ajax({  
		    type: "GET",  
		    contentType : "application/json",
		    url: "${ctx}/admin/pdt/validatePdtUniq.do?pdtName="+encodeURI($("#pdtAddForm input[name='name']").val()),
			dataType:"json",
		    async:false,  
		    success: function(data){  
		 	  if(data.result == "true"){
		 		 v = true;
		 	  }else{
		 		  alertMsg.info("产品名称不能重复");
		 		 v = false;
		 	  }
		    }
		   });
		}
		return v;
    }
	function validatePdtNo(){
		var v;
		if($("#pdtAddForm input[name='pdtNo']").val()==""){
			v= true;
		}else{
			  $.ajax({  
				type: "GET",  
				contentType : "application/json",
				url: "${ctx}/admin/pdt/validatePdtUniq.do?pdtNo="+encodeURI($("#pdtAddForm input[name='pdtNo']").val()),
				dataType:"json",
				async:false,  
				success: function(data){  
					if(data.result == "true"){
						 v = true;
					}else{
						alertMsg.info("产品型号不能重复");
						 v = false;
					}
				}
			 });
		}
		return v;
	}
	
	function validatAll(){
		if(validatPdtName() && validatePdtNo() && validateSeq()){
			$("#pdtAddForm").submit();
		}
		
	}
	
	/**
	 * 产品号段验证
	 */
	function validateSeq() {
		var _start = $("input[name = 'start']").val();
		var _end = $("input[name = 'end']").val();
		if(_start && _end) {
			var _startInt = parseInt(_start);
			var _endInt = parseInt(_end);
			if(_startInt >= _endInt) {
				alertMsg.error("号段区间最大值应大于最小值");
				return false;
			}
		}
		return true;
	}
	 
	 $(function() {
		 $("input[name = 'pre']").on("keyup", function() {
			 var _old = $(this).val();
			 _old = _old.replace(/\W/,"");
			 $(this).val(_old);
		 });
		 
		 $("input[name = 'start']").on("keyup", function() {
			 if($(this).val().startWith("0")) { //去0化
				$(this).val("");
			 }
		 });
		 
		 $("input[name = 'end']").on("keyup", function() {
			 if($(this).val().startWith("0")) { //去0化
				$(this).val("");
			 }
		 });
	 });
	 
	 function	chanagNoSegCfg(v){
		   var str = $(v).find("option:selected").attr("id").split("_");
		   $start = $(v).parent().next();
		   $end = $(v).parent().next().next();
		   $start.find("input").val(str[1]);
		   $end.find("input").val(str[2]);
		   
	   }
</script>
	<form id="pdtAddForm" method="post" action="${ctx}/admin/pdt/pdtAdd.do"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone)">
		<input type="hidden" id="pre_length" value="${pre_length}"/>
		<input type="hidden" id="value_length" value="${value_length}"/>
		<div class="pageFormContent" layoutH="68">
			<p>
				<label>产品名称：</label>
				<input type="text" name="name" size="30" class="required" onchange="validatPdtName();" />
			</p>
			<p>
				<label>产品型号：</label>
				<input type="text" name="pdtNo" size="30" class="required " onchange="validatePdtNo();" />
			</p>
			<p>
				<label>产品全称：</label>
				<input type="text" name="longName" size="30" class="required" />
			</p>
			<p>
				<label>产品单价：</label>
				<input type="text" name="price" size="30" class="required number money"/>
			</p>
			<p>
				<label>产品号段前缀：</label>
				 <select name="inoSegCfg" class="required" onchange="chanagNoSegCfg(this);">
				       <option value="" id="__" >-请选择-</option>
				      <c:forEach var="map" items="${noSegCfgMap}">
				          <option value="${map.key}" id="${map.key}_${map.value.start}_${map.value.end}" >${map.value.pre}</option>
				      </c:forEach>
			    </select>
			</p>
			<p>
				<label>号段区间最小值：</label>
				<input type="text" name="start" readonly="readonly" size="30"/>
			</p>
			<p>
				<label>号段区间最大值：</label>
				<input type="text" name="d" readonly="readonly" size="30" />
			</p>
			<p>
				<label>说明：</label>
				<input type="text" name="memo" size="30" />
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" onclick="validatAll();">&nbsp;&nbsp;提&nbsp;&nbsp;&nbsp;&nbsp;交&nbsp;&nbsp;</button>
						</div>
					</div></li>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">&nbsp;&nbsp;取&nbsp;&nbsp;&nbsp;&nbsp;消&nbsp;&nbsp;</button>
						</div>
					</div></li>
			</ul>
		</div>
	</form>
</div>